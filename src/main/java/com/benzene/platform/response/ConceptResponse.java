package com.benzene.platform.response;

import com.benzene.platform.entity.Concept;
import com.benzene.util.entity.SequencedEntity;
import com.benzene.util.response.SequencedResponse;

public class ConceptResponse extends SequencedResponse {

	private String heading; 
	private String text;
	private String baseImageUrl;
	
	public ConceptResponse() {
	}

	public ConceptResponse(Concept concept) {
		super(concept);
		this.heading = concept.getHeading();
		this.text = concept.getText();
		this.baseImageUrl = concept.getBaseImageUrl();
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
	public String getBaseImageUrl() {
		return baseImageUrl;
	}
	public void setBaseImageUrl(String baseImageUrl) {
		this.baseImageUrl = baseImageUrl;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConceptResponse [").append(super.toString()).append(", heading=").append(heading)
				.append(", text=").append(text).append(", baseImageUrl=").append(baseImageUrl).append("]");
		return builder.toString();
	}
}
