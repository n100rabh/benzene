package com.benzene.platform.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.benzene.platform.enums.ProblemLevel;
import com.benzene.platform.enums.ProblemType;
import com.benzene.util.entity.AbstractEntity;

@Entity
@Table(name = "Problem")
@Component
@Scope("prototype")
public class Problem extends AbstractEntity {

	@Enumerated(EnumType.STRING)
	private ProblemLevel level;
	@Enumerated(EnumType.STRING)
	private ProblemType type;
	@Lob
	private String text;
	@Lob
	private String question;
	@Lob
	private String option1;
	@Lob
	private String option2;
	@Lob
	private String option3;
	@Lob
	private String option4;
	@Lob
	private String solution;
	@OneToMany(mappedBy = "problem", cascade=CascadeType.ALL)
	private Set<TopicProblemMapping> topicProblemMappings;

	public Problem() {
		super();
	}
}