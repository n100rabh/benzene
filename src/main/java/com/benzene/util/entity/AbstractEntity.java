package com.benzene.util.entity;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.format.annotation.DateTimeFormat;

import com.benzene.util.enums.State;

@MappedSuperclass
public abstract class AbstractEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	@Enumerated(EnumType.STRING)
	private State state;
	@DateTimeFormat(style="F")
	private Date creationDate;
	private Long createdBy;
	@DateTimeFormat(style="F")
	private Date lastUpdatedDate;
	private Long lastUpdatedBy;

	public AbstractEntity() {
		super();
	}

	public AbstractEntity(Long id, String name, State state, Date creationDate, Long createdBy, Date lastUpdatedDate,
			Long lastUpdatedBy) {
		super();
		this.id = id;
		this.name = name;
		this.state = state;
		this.creationDate = creationDate;
		this.createdBy = createdBy;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Override
	public String toString() {
		return "AbstractEntity [id=" + id + ", name=" + name + ", state=" + state + ", creationDate=" + creationDate
				+ ", createdBy=" + createdBy + ", lastUpdatedDate=" + lastUpdatedDate + ", lastUpdatedBy="
				+ lastUpdatedBy + "]";
	}
}
