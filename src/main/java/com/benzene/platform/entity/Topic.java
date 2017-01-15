package com.benzene.platform.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.benzene.util.entity.AbstractEntity;

@Entity
@Table(name = "Topic")
@Component
@Scope("prototype")
public class Topic extends AbstractEntity {

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE) 
	@JoinColumn(name = "subjectId")
	private Subject subject;
	
	@OneToMany(mappedBy="topic", cascade=CascadeType.ALL)
	private Set<Concept> concepts;
	
	@OneToMany(mappedBy="topic", cascade=CascadeType.ALL)
	private Set<TopicProblemMapping> topicProblemMappings;
	
	public Topic() {
		super();
	}
}