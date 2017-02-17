package com.benzene.platform.request;

import com.benzene.util.request.SequencedRequest;

public class ConceptRequest extends SequencedRequest {

	private String heading; 
	private String text;
	private String baseImageUrl;
	
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
		builder.append("ConceptRequest [").append(super.toString()).append(", heading=").append(heading)
				.append(", text=").append(text).append(", baseImageUrl=").append(baseImageUrl).append("]");
		return builder.toString();
	}
}
