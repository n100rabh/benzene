package com.benzene.util.response;

import com.benzene.util.entity.SequencedEntity;

public abstract class SequencedResponse extends BaseResponse {

	private Integer sequenceNo;

	public SequencedResponse(SequencedEntity entity) {
		super(entity);
		this.sequenceNo = entity.getSequenceNo();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(", sequenceNo=").append(sequenceNo);
		return builder.toString();
	}
}
