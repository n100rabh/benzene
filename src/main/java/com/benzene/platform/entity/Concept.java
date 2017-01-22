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
public class Concept extends AbstractEntity implements Comparable<Concept>, Comparator<Concept> {

	private String heading; // heading ques
	@Lob
	private String text;
	private Integer sequenceNo; // Ordering in a Topic
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "topicId")
	private Topic topic;

	public Concept() {
		super();
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	@Override
	public int compareTo(Concept o) {
		return (int) (this.sequenceNo.compareTo(o.getSequenceNo()));
	}

	@Override
	public int compare(Concept o1, Concept o2) {
		return (int) (o1.compareTo(o2));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Concept [heading=").append(heading).append(", text=").append(text).append(", sequenceNo=")
				.append(sequenceNo).append(", toString()=").append(super.toString()).append("]");
		return builder.toString();
	}
}