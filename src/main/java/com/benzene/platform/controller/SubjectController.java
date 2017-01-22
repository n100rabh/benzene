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

import com.benzene.platform.entity.Subject;
import com.benzene.platform.manager.SubjectManager;
import com.benzene.platform.request.GetAbstractReq;
import com.benzene.util.LogFactory;
import com.benzene.util.enums.State;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "subject", description = "Subject APIs")
@RequestMapping("subject")
public class SubjectController {

	@Autowired
	private SubjectManager subjectManager;

	@Autowired
	private LogFactory logfactory;
	
	@SuppressWarnings("static-access")
	private Logger logger = logfactory.getLogger(SubjectController.class);

	@ApiOperation(value = "Create Subject", notes = "Returns created subject")
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Subject createSubject(@RequestBody Subject subject) throws Throwable {
		logger.info("createSubject Request:" + subject.toString());
		Subject response = subjectManager.addOrUpdateSubject(subject);
		logger.info("createSubject Response:" + response.toString());
		return response;
	}
	
	@ApiOperation(value = "Get a Subject", notes = "Returns subject")
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseBody
	public Subject getSubject(@PathVariable("id") Long id) throws Throwable {
		logger.info("Request received for getting Subject for db_id: " + id);
		Subject response = subjectManager.getSubject(id, null);
		logger.info("getSubject Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Get Subjects", notes = "Returns list of subjects")
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Subject> getSubjects(
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "size", required = false) Integer size) throws Throwable {
		logger.info("Request received for Subjects by TeacherId and StudentId");
		GetAbstractReq req = new GetAbstractReq(start, size);
		List<Subject> response = subjectManager.getSubjects(req);
		logger.info("Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Update Subject", notes = "Returns created subject")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Subject updateSubject(@PathVariable("id") Long id, @RequestBody Subject subject) throws Throwable {
		subject.setId(id);
		logger.info("updateSubject Request:" + subject.toString());
		Subject response = subjectManager.addOrUpdateSubject(subject);
		logger.info("updateSubject Response:" + response.toString());
		return response;
	}
	
	@ApiOperation(value = "Delete Subject", notes = "Returns created subject")
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteSubject(@PathVariable("id") Long id) throws Throwable {
		logger.info("deleteSubject Request:" + id);
		Subject subject = new Subject();
		subject.setId(id);
		subject.setState(State.INACTIVE);
		Subject response = subjectManager.addOrUpdateSubject(subject);
		logger.info("updateSubject Response:" + response.toString());
	}
}
