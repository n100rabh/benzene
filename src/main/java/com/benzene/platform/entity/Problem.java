package com.benzene.platform.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.benzene.platform.enums.OptionId;
import com.benzene.platform.enums.ProblemLevel;
import com.benzene.platform.enums.ProblemType;
import com.benzene.platform.request.ProblemRequest;
import com.benzene.util.entity.BaseEntity;

@Entity
@Table(name = "Problem")
@Component
@Scope("prototype")
public class Problem extends BaseEntity {

	@Enumerated(EnumType.STRING)
	private ProblemLevel level;
	@Enumerated(EnumType.STRING)
	private ProblemType type;
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
	@Enumerated(EnumType.STRING)
	private OptionId correctOption;
	private String baseImageUrl;
	@OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<TopicProblemMapping> topicProblemMappings;

	public Problem() {
		super();
	}

	public Problem(ProblemRequest request) {
		super(request);
		this.level = request.getLevel();
		this.type = request.getType();
		this.question = request.getQuestion();
		this.option1 = request.getOption1();
		this.option2 = request.getOption2();
		this.option3 = request.getOption3();
		this.option4 = request.getOption4();
		this.solution = request.getSolution();
		this.correctOption = request.getCorrectOption();
		this.baseImageUrl = request.getBaseImageUrl();
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

	public OptionId getCorrectOption() {
		return correctOption;
	}

	public void setCorrectOption(OptionId correctOption) {
		this.correctOption = correctOption;
	}

	public String getBaseImageUrl() {
		return baseImageUrl;
	}

	public void setBaseImageUrl(String baseImageUrl) {
		this.baseImageUrl = baseImageUrl;
	}

	public Set<TopicProblemMapping> getTopicProblemMappings() {
		return topicProblemMappings;
	}

	public void setTopicProblemMappings(Set<TopicProblemMapping> topicProblemMappings) {
		this.topicProblemMappings = topicProblemMappings;
	}

	public void addTopicProblemMapping(TopicProblemMapping topicProblemMapping) {
		if(this.topicProblemMappings == null) {
			this.topicProblemMappings = new HashSet<TopicProblemMapping>();
		}
		this.topicProblemMappings.add(topicProblemMapping);
	}
	
	void addUpdates(Problem problem) {
		super.addUpdates(problem);
		if (problem.getLevel() != null) {
			this.setLevel(problem.getLevel());
		}
		if (problem.getType() != null) {
			this.setType(problem.getType());
		}
		if (problem.getQuestion() != null) {
			this.setQuestion(problem.getQuestion());
		}
		if (problem.getOption1() != null) {
			this.setOption1(problem.getOption1());
		}
		if (problem.getOption2() != null) {
			this.setOption2(problem.getOption2());
		}
		if (problem.getOption3() != null) {
			this.setOption3(problem.getOption3());
		}
		if (problem.getOption4() != null) {
			this.setOption4(problem.getOption4());
		}
		if (problem.getSolution() != null) {
			this.setSolution(problem.getSolution());
		}
	}
	
	@Override
	public void delete() {
		super.delete();
		for(TopicProblemMapping topicProblemMapping : this.topicProblemMappings) {
			topicProblemMapping.delete();
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Problem [").append(super.toString()).append(", level=").append(level).append(", type=")
				.append(type).append(", question=").append(question).append(", option1=").append(option1)
				.append(", option2=").append(option2).append(", option3=").append(option3).append(", option4=")
				.append(option4).append(", solution=").append(solution).append(", correctOption=").append(correctOption)
				.append(", baseImageUrl=").append(baseImageUrl).append("]");
		return builder.toString();
	}
}