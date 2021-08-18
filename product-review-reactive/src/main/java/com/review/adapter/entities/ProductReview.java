package com.review.adapter.entities;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(value = "product_review")
public class ProductReview extends BaseEntity {

	private String productId;
	private String productName;
	private String comment;
	private Integer rating;
	private String userId;
	private String userName;
	
	
	public ProductReview() {
		super();
		this.active = true;
		this.createdDate = new Date();
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void updateUser(String userName) {
		this.userId = userName;
		this.userName = userName;
		this.active = true;
		this.createdBy = userName;
		this.createdDate = new Date();
	}
	@JsonIgnore
	@Override
	public boolean isValid() {
		return (this.productId != null && this.comment != null && this.rating != null);
	}

	public static String invalidMsg() {
		return "Please enter valid product reviewe data (Comment/Rating/Product)";
	}

	@Override
	public String toString() {
		return this.id + "|" + this.comment + "|" + this.productId;
	}
}
