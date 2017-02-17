package com.benzene.platform.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.benzene.platform.request.SubjectRequest;
import com.benzene.util.entity.BaseEntity;

@Entity
@Table(name = "Subject")
@Component
@Scope("prototype")
public class Subject extends BaseEntity {

	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Branch> branches;

	public Subject() {
		super();
	}

	public Subject(SubjectRequest request) {
		super(request);
	}

	public Set<Branch> getBranches() {
		return branches;
	}

	public void setBranches(Set<Branch> branches) {
		this.branches = branches;
	}

	public void addBranch(Branch branch) {
		this.branches.add(branch);
	}

	@Override
	public void delete() {
		super.delete();
		for (Branch branch : this.branches) {
			branch.delete();
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Subject [").append(super.toString()).append("]");
		return builder.toString();
	}
}