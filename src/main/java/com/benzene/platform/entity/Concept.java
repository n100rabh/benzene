package com.benzene.platform.entity;

import java.util.Comparator;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.benzene.util.entity.AbstractEntity;

@Entity
@Table(name = "Concept")
@Component
@Scope("prototype")
public class Concept extends AbstractEntity implements Comparable<Concept>, Comparator<Concept>{

	private String heading; //heading ques
	@Lob
	private String text;
	private Integer sequenceNo; //Ordering in a Topic
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE) 
	@JoinColumn(name = "topicId")
	private Topic topic;

	public Concept() {
		super();
	}
	
	public Integer getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	@Override
	public int compareTo(Concept o) {
		return (int) (this.sequenceNo.compareTo(o.getSequenceNo()));
	}

	@Override
	public int compare(Concept o1, Concept o2) {
		return (int) (o1.compareTo(o2));
	}
}