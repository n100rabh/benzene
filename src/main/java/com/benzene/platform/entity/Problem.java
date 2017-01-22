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

	public ProblemLevel getLevel() {
		return level;
	}

	public void setLevel(ProblemLevel level) {
		this.level = level;
	}

	public ProblemType getType() {
		return type;
	}

	public void setType(ProblemType type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getOption4() {
		return option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Problem [level=").append(level).append(", type=").append(type).append(", text=").append(text)
				.append(", question=").append(question).append(", option1=").append(option1).append(", option2=")
				.append(option2).append(", option3=").append(option3).append(", option4=").append(option4)
				.append(", solution=").append(solution).append(", topicProblemMappings=").append(topicProblemMappings)
				.append(", toString()=").append(super.toString()).append("]");
		return builder.toString();
	}
}