package com.benzene.platform.response;

import com.benzene.platform.entity.Chapter;
import com.benzene.platform.enums.BranchViewType;
import com.benzene.util.response.SequencedResponse;

public class ChapterResponse extends SequencedResponse{

	private BranchViewType viewType;
	
	public ChapterResponse() {
	}

	public ChapterResponse(Chapter chapter) {
		super(chapter);
		this.viewType = chapter.getViewType();
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
		builder.append("ChapterResponse [").append(super.toString()).append(", viewType=").append(viewType)
				.append("]");
		return builder.toString();
	}
}
