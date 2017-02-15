package com.benzene.platform.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.benzene.platform.enums.BranchViewType;
import com.benzene.platform.request.BranchRequest;
import com.benzene.util.entity.SequencedEntity;

@Entity
@Table(name = "Branch")
@Component
@Scope("prototype")
public class Branch extends SequencedEntity {

	@Enumerated(EnumType.STRING)
	private BranchViewType viewType;
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "subjectId")
	private Subject subject;
	@OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
	private Set<Chapter> chapters;

	public Branch() {
		super();
	}
	
	public Branch(BranchRequest request) {
		super(request);
		this.viewType = request.getViewType();
	}

	public BranchViewType getViewType() {
		return viewType;
	}

	public void setViewType(BranchViewType viewType) {
		this.viewType = viewType;
	}

	public Set<Chapter> getChapters() {
		return chapters;
	}

	public void setChapters(Set<Chapter> chapters) {
		this.chapters = chapters;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public void addChapter(Chapter chapter) {
		this.chapters.add(chapter);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Branch [").append(super.toString()).append(", viewType=").append(viewType).append(", subject=")
				.append(subject).append("]");
		return builder.toString();
	}
}