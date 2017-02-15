package com.benzene.util.request;

import com.benzene.util.enums.State;

public abstract class BaseRequest {
	private Long id;
	private String name;
	private State state;
	private String description;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("id=").append(id).append(", name=").append(name).append(", state=").append(state)
				.append(", description=").append(description);
		return builder.toString();
	}
}
