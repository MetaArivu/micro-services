package com.order.adapter.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.adapter.entities.Order;
import com.order.adapter.entities.OrderItems;
import com.order.adapter.repository.OrderRepository;
import com.order.domainlayer.service.OrderService;
import com.order.dto.ProductDTO;
import com.order.server.secutiry.JWTUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger log = (Logger) LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private ProductIntegrationServiceImpl prdIntgerationSvc;

	@Autowired
	private JWTUtil jwtUtil;

	private String userName() {
		String authHeader = MDC.get("Authorization");
		String authToken = authHeader.substring(7);
		log.debug("Authorization=" + authToken);
		return jwtUtil.getUsernameFromToken(authToken);
	}

	@Override
	public Mono<Order> createOrder(Order _order) {
		log.info("Placing order");

		_order.updateData(this.userName());

		return this.fetchProductDetails(_order.productIds())
				.flatMap((map)->{
					System.out.println("MAP="+map);
					for (OrderItems orderItem : _order.getItems()) {
						String prd = orderItem.getPrdId();
						ProductDTO prdDto = map.get(prd);
						if(prdDto!=null) {
							orderItem.setProductName(prdDto.getName());
							orderItem.setPricePerUnit(prdDto.getAmount());
						}else {
							Mono<Order> fallback = Mono.error(new com.order.exceptions.InvalidInputException("Invalid Id=" + orderItem.getProductName()));
							return fallback;
						}
					}
					
					return Mono.just(_order);
					//return orderRepo.save(_order);
				}).flatMap(ord->{
					return orderRepo.save(ord);
				});
		
		
		
	}

	private Mono<Map<String, ProductDTO>> fetchProductDetails(List<String> productIds) {
		
		log.info("Fetching product details for {}", productIds);
		return prdIntgerationSvc.getProductDetails(productIds)
			.flatMap(prds ->{
				System.out.println(prds.getClass()+"<=prds=>"+prds);

				Map<String, ProductDTO> prdMap = new HashMap<String, ProductDTO>();
				for (ProductDTO dto : prds) {
					prdMap.put(dto.getId(), dto);
				}
				return Mono.just(prdMap);
			});
		 
	}
	
	@Override
	public Flux<Order> userOrderDetails() {
		return orderRepo.findByUserIdAndActive(this.userName(), true);
	}

}
