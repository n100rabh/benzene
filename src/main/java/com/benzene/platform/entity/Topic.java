package com.benzene.platform.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.benzene.platform.enums.BranchViewType;
import com.benzene.platform.request.TopicRequest;
import com.benzene.util.entity.SequencedEntity;

@Entity
@Table(name = "Topic")
@Component
@Scope("prototype")
public class Topic extends SequencedEntity {

	private BranchViewType viewType;
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "chapterId")
	private Chapter chapter;
	@OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Concept> concepts;
	@OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<TopicProblemMapping> topicProblemMappings;

	public Topic() {
		super();
	}

	public Topic(TopicRequest request) {
		super(request);
		this.viewType = request.getViewType();
	}

	public BranchViewType getViewType() {
		return viewType;
	}

	public void setViewType(BranchViewType viewType) {
		this.viewType = viewType;
	}

	public Chapter getChapter() {
		return chapter;
	}

	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}

	public Set<Concept> getConcepts() {
		return concepts;
	}

	public void setConcepts(Set<Concept> concepts) {
		this.concepts = concepts;
	}

	public Set<TopicProblemMapping> getTopicProblemMappings() {
		return topicProblemMappings;
	}

	public void setTopicProblemMappings(Set<TopicProblemMapping> topicProblemMappings) {
		this.topicProblemMappings = topicProblemMappings;
	}

	public void addConcept(Concept concept) {
		this.concepts.add(concept);
	}

	public void addTopicProblemMapping(TopicProblemMapping topicProblemMapping) {
		this.topicProblemMappings.add(topicProblemMapping);
	}

	public void addUpdates(Topic topic) {
		super.addUpdates(topic);
		if (topic.getViewType() != null) {
			this.setViewType(topic.getViewType());
		}
	}

	@Override
	public void delete() {
		super.delete();
		for (Concept concept : this.concepts) {
			concept.delete();
		}
		for (TopicProblemMapping topicProblemMapping : this.topicProblemMappings) {
			topicProblemMapping.delete();
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Topic [").append(super.toString()).append(", viewType=").append(viewType).append(", chapter=")
				.append(chapter).append("]");
		return builder.toString();
	}
}