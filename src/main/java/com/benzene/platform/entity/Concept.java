package com.benzene.platform.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.benzene.platform.request.ConceptRequest;
import com.benzene.util.entity.SequencedEntity;

@Entity
@Table(name = "Concept")
@Component
@Scope("prototype")
public class Concept extends SequencedEntity {

	private String heading; // heading ques
	@Lob
	private String text;
	private String baseImageUrl;
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "topicId")
	private Topic topic;

	public Concept() {
		super();
	}

	public Concept(ConceptRequest request) {
		super(request);
		this.heading = request.getHeading();
		this.text = request.getText();
		this.baseImageUrl = request.getBaseImageUrl();
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

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public String getBaseImageUrl() {
		return baseImageUrl;
	}

	public void setBaseImageUrl(String baseImageUrl) {
		this.baseImageUrl = baseImageUrl;
	}

	public void addUpdates(Concept concept) {
		super.addUpdates(concept);
		if (concept.getHeading() != null) {
			this.setHeading(concept.getHeading());
		}
		if (concept.getText() != null) {
			this.setText(concept.getText());
		}
		if(concept.getBaseImageUrl() != null) {
			this.setBaseImageUrl(concept.getBaseImageUrl());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Concept [").append(super.toString()).append(", heading=").append(heading).append(", text=")
				.append(text).append(", baseImageUrl=").append(baseImageUrl).append(", topic=").append(topic)
				.append("]");
		return builder.toString();
	}
}