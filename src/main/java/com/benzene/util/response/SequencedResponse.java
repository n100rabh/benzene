package com.benzene.util.response;

import com.benzene.util.entity.SequencedEntity;

public abstract class SequencedResponse extends BaseResponse {

	private Integer sequenceNo;

	public SequencedResponse() {
	}
	
	public SequencedResponse(SequencedEntity entity) {
		super(entity);
		this.sequenceNo = entity.getSequenceNo();
	}

	public Integer getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(", sequenceNo=").append(sequenceNo);
		return builder.toString();
	}
}
