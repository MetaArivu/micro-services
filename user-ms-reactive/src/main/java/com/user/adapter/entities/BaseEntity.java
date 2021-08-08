package com.user.adapter.entities;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class BaseEntity {

	@Id
	protected String id;

	@JsonIgnore
	protected boolean active;

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

	@JsonIgnore	
	public boolean isNew() {
		return this.id == null;
	}
	
	@JsonIgnore
	public abstract boolean isValid();
}
