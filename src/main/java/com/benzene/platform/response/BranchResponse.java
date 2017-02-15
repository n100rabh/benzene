package com.benzene.platform.response;

import com.benzene.platform.entity.Branch;
import com.benzene.platform.enums.BranchViewType;
import com.benzene.util.response.BaseResponse;

public class BranchResponse extends BaseResponse{

	private BranchViewType viewType;
	
	public BranchResponse() {
		super();
	}

	public BranchResponse(Branch branch) {
		super(branch);
		this.viewType = branch.getViewType();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BranchResponse [").append(super.toString()).append(", viewType=").append(viewType)
				.append("]");
		return builder.toString();
	}
}
