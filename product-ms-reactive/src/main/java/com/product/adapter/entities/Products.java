package com.product.adapter.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(value = "products")
public class Products extends BaseEntity {

	private String name;
	private String desc;
	private Double amount;

	public Products() {
		super();
		this.active = true;
	}

	public Products(String name, String desc, Double amount) {
		this();
		this.name = name;
		this.desc = desc;
		this.amount = amount;
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

	@JsonIgnore
	@Override
	public boolean isValid() {
		return (this.name != null && this.amount != null && this.name.length() > 5 && this.amount > 0.0);
	}
	
	public static String invalidMsg() {
		return "Please enter valid product data (Name/Amount)";
	}

	@Override
	public String toString() {
		return name + "|" + id;
	}
}
