package com.benzene.platform.response;

import com.benzene.platform.entity.Subject;
import com.benzene.util.response.BaseResponse;

public class SubjectResponse extends BaseResponse{

	public SubjectResponse() {
		super();
	}

	public SubjectResponse(Subject subject) {
		super(subject);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SubjectResponse [").append(super.toString()).append("]");
		return builder.toString();
	}
}
