package com.benzene.platform.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.benzene.util.entity.AbstractEntity;

@Entity
@Table(name = "TopicProblemMapping")
public class TopicProblemMapping extends AbstractEntity {

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "topicId")
	private Topic topic;

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "problemId")
	private Problem problem;

	private Integer problemSequenceNo;

	public TopicProblemMapping() {
		super();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TopicProblemMapping [problemSequenceNo=").append(problemSequenceNo).append(", toString()=")
				.append(super.toString()).append("]");
		return builder.toString();
	}
}