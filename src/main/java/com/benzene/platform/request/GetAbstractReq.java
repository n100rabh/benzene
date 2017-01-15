package com.benzene.platform.request;

public class GetAbstractReq {

	private Integer start;
	private Integer size;

	public GetAbstractReq() {
		super();
	}

	public GetAbstractReq(Integer start, Integer size) {
		this.setStart(start);
		this.setSize(size);
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

}
