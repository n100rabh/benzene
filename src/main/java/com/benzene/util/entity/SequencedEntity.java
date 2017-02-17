package com.benzene.util.entity;

import java.util.Comparator;

import javax.persistence.MappedSuperclass;

import com.benzene.util.request.SequencedRequest;

@MappedSuperclass
public abstract class SequencedEntity extends BaseEntity
		implements Comparable<SequencedEntity>, Comparator<SequencedEntity> {

	private Integer sequenceNo;

	public SequencedEntity() {
	}

	public SequencedEntity(SequencedRequest request) {
		super(request);
		this.sequenceNo = request.getSequenceNo();
	}

	public Integer getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	
	public void addUpdates(SequencedEntity entity) {
		super.addUpdates(entity);
		if(entity.getSequenceNo() != null) {
			this.setSequenceNo(entity.getSequenceNo());
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(", sequenceNo=").append(sequenceNo);
		return builder.toString();
	}

	@Override
	public int compare(SequencedEntity o1, SequencedEntity o2) {
		return (int) (o1.compareTo(o2));
	}

	@Override
	public int compareTo(SequencedEntity o) {
		return (int) (this.sequenceNo.compareTo(o.getSequenceNo()));
	}
}