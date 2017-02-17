package com.benzene.platform.response;

import com.benzene.platform.entity.Topic;
import com.benzene.platform.enums.BranchViewType;
import com.benzene.util.response.SequencedResponse;

public class TopicResponse extends SequencedResponse{

	private BranchViewType viewType;
	
	public TopicResponse() {
	}

	public TopicResponse(Topic topic) {
		super(topic);
		this.viewType = topic.getViewType();
	}

	public BranchViewType getViewType() {
		return viewType;
	}
	public void setViewType(BranchViewType viewType) {
		this.viewType = viewType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TopicResponse [").append(super.toString()).append(", viewType=").append(viewType)
				.append("]");
		return builder.toString();
	}
}
