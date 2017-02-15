package com.benzene.platform.controller;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.benzene.platform.manager.SubjectManager;
import com.benzene.platform.request.BranchRequest;
import com.benzene.platform.request.SubjectRequest;
import com.benzene.platform.response.BranchResponse;
import com.benzene.platform.response.SubjectResponse;
import com.benzene.util.LogFactory;
import com.benzene.util.enums.State;
import com.benzene.util.request.GetAbstractReq;
import com.benzene.util.response.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Subject", description = "Subject APIs")
@RequestMapping("")
public class SubjectController {

	@Autowired
	private SubjectManager subjectManager;

	@Autowired
	private LogFactory logfactory;

	@SuppressWarnings("static-access")
	private Logger logger = logfactory.getLogger(SubjectController.class);

	@ApiOperation(value = "Create Subject", notes = "Returns created subject")
	@RequestMapping(value = "subject", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse createSubject(@RequestBody SubjectRequest request) throws Throwable {
		logger.info("createSubject Request:" + request.toString());
		BaseResponse response = subjectManager.addOrUpdateSubject(request);
		logger.info("createSubject Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Get a Subject", notes = "Returns subject")
	@RequestMapping(value = "subject/{id}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse getSubject(@PathVariable("id") Long id) throws Throwable {
		logger.info("Request received for getting Subject for db_id: " + id);
		BaseResponse response = subjectManager.getSubject(id, null);
		logger.info("getSubject Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Get Subjects", notes = "Returns list of subjects")
	@RequestMapping(value = "subjects", method = RequestMethod.GET)
	@ResponseBody
	public List<SubjectResponse> getSubjects(@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "size", required = false) Integer size) throws Throwable {
		logger.info("Request received for Subjects by TeacherId and StudentId");
		GetAbstractReq req = new GetAbstractReq(start, size);
		List<SubjectResponse> response = subjectManager.getSubjects(req);
		logger.info("Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Update Subject", notes = "Returns created subject")
	@RequestMapping(value = "subject/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse updateSubject(@PathVariable("id") Long id, @RequestBody SubjectRequest request) throws Throwable {
		request.setId(id);
		logger.info("updateSubject Request:" + request.toString());
		BaseResponse response = subjectManager.addOrUpdateSubject(request);
		logger.info("updateSubject Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Delete Subject", notes = "Returns created subject")
	@RequestMapping(value = "subject/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteSubject(@PathVariable("id") Long id) throws Throwable {
		logger.info("deleteSubject Request:" + id);
		SubjectRequest request = new SubjectRequest();
		request.setId(id);
		request.setState(State.INACTIVE);
		BaseResponse response = subjectManager.addOrUpdateSubject(request);
		logger.info("updateSubject Response:" + response.toString());
	}
	
	@ApiOperation(value = "Create Branch in Subject", notes = "Returns created branch")
	@RequestMapping(value = "subject/{id}/branch", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse createBranch(@PathVariable("id") Long id, @RequestBody BranchRequest request) throws Throwable {
		logger.info("createBranch Request:" + request.toString());
		BaseResponse response = subjectManager.addBranch(id, request);
		logger.info("createBranch Response:" + response.toString());
		return response;
	}
	
	@ApiOperation(value = "Get Branches By Subject", notes = "Returns list of branchs")
	@RequestMapping(value = "subject/{id}/branches", method = RequestMethod.GET)
	@ResponseBody
	public List<BranchResponse> getBranches(@PathVariable("id") Long id,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "size", required = false) Integer size) throws Throwable {
		logger.info("Request received for Branches");
		List<BranchResponse> response = subjectManager.getBranches(id);
		logger.info("Response:" + response.toString());
		return response;
	}
}
