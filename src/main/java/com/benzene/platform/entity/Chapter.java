package com.benzene.platform.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import com.benzene.platform.request.ChapterRequest;
import com.benzene.util.entity.SequencedEntity;

@Entity
@Table(name = "Chapter")
@Component
@Scope("prototype")
public class Chapter extends SequencedEntity {

	@Enumerated(EnumType.STRING)
	private BranchViewType viewType;
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "branchId")
	private Branch branch;
	@OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Topic> topics;

	public Chapter() {
		super();
	}

	public Chapter(ChapterRequest request) {
		super(request);
		this.viewType = request.getViewType();
	}

	public BranchViewType getViewType() {
		return viewType;
	}

	public void setViewType(BranchViewType viewType) {
		this.viewType = viewType;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
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
	
	public void addUpdates(Chapter chapter) {
		super.addUpdates(chapter);
		if (chapter.getViewType() != null) {
			this.setViewType(chapter.getViewType());
		}
	}
	
	@Override
	public void delete() {
		super.delete();
		for(Topic topic : this.topics) {
			topic.delete();
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Chapter [").append(super.toString()).append(", branch=").append(branch).append(", viewType=")
				.append(viewType).append("]");
		return builder.toString();
	}
}