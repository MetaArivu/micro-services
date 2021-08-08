//package com.product;
//
//import static reactor.core.scheduler.Schedulers.parallel;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import reactor.core.publisher.Flux;
//import reactor.test.StepVerifier;
//
//@SpringBootTest
//class ProductMsReactiveApplicationTests {
//
//	List<String> data = Arrays.asList("Apple 5","Apple 6","Apple 7","Apple 8","Apple 9","Apple 10","Apple 11","Apple 12");
//	
//	@Test
//	void test1() {
//		System.out.println("TEST1");
//		Flux<String> products = Flux.fromIterable(data)
//									.filter(f-> f.length()>7)
//									.map(s-> s.toUpperCase())
//									.log();
//		
//		StepVerifier.create(products)
//					.expectNext("APPLE 10","APPLE 11","APPLE 12")
//					.verifyComplete();
//		
//	}
//	
//	@Test
//	void test2_flat_Map() {
//		System.out.println("TEST2");
//
//		Flux<String> products = Flux.fromIterable(data)
//									.filter(f-> f.length()>7)
//									.flatMap((s) -> Flux.fromIterable(addElement(s)))
//									.log();
//		
//		StepVerifier.create(products)
//					.expectNextCount(6)
//					.verifyComplete();
//		
//	}
//	
//	@Test
//	void test4_flat_Map_parallel_stream() {
//		System.out.println("TEST4");
//
//		Flux<String> products = Flux.fromIterable(data)
//									.window(2)
//									.flatMap((s)-> s.map(this::addElement).subscribeOn(parallel()))
//									.flatMap((s)-> Flux.fromIterable(s))
//									.log();
//									
//		
//		StepVerifier.create(products)
//					.expectNextCount(16)
//					.verifyComplete();
//		
//	}
//	
//	@Test
//	void test3_flat_Map_parallel_stream_maintain_order() {
//		System.out.println("TEST3");
//
//		Flux<String> products = Flux.fromIterable(data)
//									.window(2)
//									.flatMapSequential((s)-> s.map(this::addElement).subscribeOn(parallel()))
//									.flatMap((s)-> Flux.fromIterable(s))
//									.log();
//									
//		
//		StepVerifier.create(products)
//					.expectNextCount(16)
//					.verifyComplete();
//		
//	}
//	
//	public List<String> addElement(String s){
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return Arrays.asList(s,"s-"+System.currentTimeMillis());
//	}
//
//}
