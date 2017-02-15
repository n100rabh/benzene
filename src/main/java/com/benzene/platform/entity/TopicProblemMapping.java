package com.benzene.platform.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.benzene.util.entity.SequencedEntity;

@Entity
@Table(name = "TopicProblemMapping")
public class TopicProblemMapping extends SequencedEntity {

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "topicId")
	private Topic topic;

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "problemId")
	private Problem problem;

	public TopicProblemMapping() {
		super();
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TopicProblemMapping [").append(super.toString()).append(", topic=").append(topic)
				.append(", problem=").append(problem).append("]");
		return builder.toString();
	}
}