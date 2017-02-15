package com.benzene.platform.request;

import com.benzene.util.request.BaseRequest;

public class SubjectRequest extends BaseRequest {

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SubjectRequest [").append(super.toString()).append("]");
		return builder.toString();
	}
}
