package com.benzene.util.request;

public class SequencedRequest extends BaseRequest {

	private Integer sequenceNo;

	public SequencedRequest() {
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
