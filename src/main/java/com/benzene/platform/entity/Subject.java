package com.benzene.platform.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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

	@OneToMany(mappedBy="subject", cascade=CascadeType.ALL)
	private Set<Topic> topics;
	
	public Subject() {
		super();
	}
}