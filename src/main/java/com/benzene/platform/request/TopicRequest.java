package com.benzene.platform.request;

import com.benzene.platform.enums.BranchViewType;
import com.benzene.util.request.SequencedRequest;

public class TopicRequest extends SequencedRequest {

	private BranchViewType viewType;

	public BranchViewType getViewType() {
		return viewType;
	}

	public void setViewType(BranchViewType viewType) {
		this.viewType = viewType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TopicRequest [").append(super.toString()).append(", viewType=").append(viewType).append("]");
		return builder.toString();
	}
}
