package com.review.adapter.entities;

import java.util.Date;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class BaseEntity {

	@Id
	protected String id;

	@JsonIgnore
	protected boolean active;

	@JsonIgnore
	protected String createdBy;
	
	@JsonIgnore
	protected Date createdDate;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@JsonIgnore	
	public boolean isNew() {
		return this.id == null;
	}
	
	@JsonIgnore
	public abstract boolean isValid();
}
