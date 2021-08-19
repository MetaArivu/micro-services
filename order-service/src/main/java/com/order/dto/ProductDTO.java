package com.order.dto;

import java.util.LinkedHashMap;

public class ProductDTO {

	private String id;
	private String name;
	private String desc;
	private Double amount;
	private String image;
	private Integer quantity;

	public ProductDTO() {

	}

	public ProductDTO(LinkedHashMap prd) {
		this.id = prd.get("id") != null ? prd.get("id").toString() : null;
		this.name = prd.get("name") != null ? prd.get("name").toString() : null;
		this.desc = prd.get("desc") != null ? prd.get("desc").toString() : null;
		this.amount = prd.get("amount") != null ? (Double) prd.get("amount") : null;
		this.image = prd.get("image") != null ? prd.get("image").toString() : null;
		this.quantity = prd.get("quantity") != null ? (Integer) prd.get("quantity") : null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Product=" + id + "|" + name + "|" + amount;
	}
}
