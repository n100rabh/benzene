package com.benzene.platform.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.benzene.util.entity.AbstractEntity;

@Entity
@Table(name = "Subject")
@Component
@Scope("prototype")
public class Subject extends AbstractEntity {

	@OneToMany(mappedBy="subject", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<Topic> topics;
	
	public Subject() {
		super();
	}

	public Set<Topic> getTopics() {
		return topics;
	}

	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}
	
	public void addTopic(Topic topic) {
		this.topics.add(topic);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Subject [topics=").append(topics).append(", toString()=").append(super.toString()).append("]");
		return builder.toString();
	}
}