package com.benzene.platform.request;

import com.benzene.platform.enums.OptionId;
import com.benzene.platform.enums.ProblemLevel;
import com.benzene.platform.enums.ProblemType;
import com.benzene.util.request.SequencedRequest;

public class ProblemRequest extends SequencedRequest {

	private ProblemLevel level;
	private ProblemType type;
	private String question;
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	private String solution;
	private OptionId correctOption;
	private String baseImageUrl;

	public ProblemRequest() {
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProblemRequest [").append(super.toString()).append(", level=").append(level).append(", type=")
				.append(type).append(", question=").append(question).append(", option1=").append(option1)
				.append(", option2=").append(option2).append(", option3=").append(option3).append(", option4=")
				.append(option4).append(", solution=").append(solution).append(", correctOption=").append(correctOption)
				.append(", baseImageUrl=").append(baseImageUrl).append("]");
		return builder.toString();
	}
}
