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

import com.benzene.platform.manager.TopicManager;
import com.benzene.platform.request.ConceptRequest;
import com.benzene.platform.request.ProblemRequest;
import com.benzene.platform.request.TopicRequest;
import com.benzene.platform.response.ConceptResponse;
import com.benzene.platform.response.ProblemResponse;
import com.benzene.util.LogFactory;
import com.benzene.util.enums.State;
import com.benzene.util.response.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Topic", description = "Topic APIs")
@RequestMapping("")
public class TopicController {

	@Autowired
	private TopicManager topicManager;

	@Autowired
	private LogFactory logfactory;

	@SuppressWarnings("static-access")
	private Logger logger = logfactory.getLogger(TopicController.class);

	@ApiOperation(value = "Get a Topic", notes = "Returns topic")
	@RequestMapping(value = "topic/{id}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse getTopic(@PathVariable("id") Long id) throws Throwable {
		logger.info("Request received for getting Topic for db_id: " + id);
		BaseResponse response = topicManager.getTopic(id, null);
		logger.info("getTopic Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Update Topic", notes = "Returns created topic")
	@RequestMapping(value = "topic/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse updateTopic(@PathVariable("id") Long id, @RequestBody TopicRequest request) throws Throwable {
		request.setId(id);
		logger.info("updateTopic Request:" + request.toString());
		BaseResponse response = topicManager.addOrUpdateTopic(request);
		logger.info("updateTopic Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Delete Topic", notes = "Returns created topic")
	@RequestMapping(value = "topic/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteTopic(@PathVariable("id") Long id) throws Throwable {
		logger.info("deleteTopic Request:" + id);
		TopicRequest request = new TopicRequest();
		request.setId(id);
		request.setState(State.INACTIVE);
		BaseResponse response = topicManager.addOrUpdateTopic(request);
		logger.info("updateTopic Response:" + response.toString());
	}
	
	@ApiOperation(value = "Create Concept in Topic", notes = "Returns created concept")
	@RequestMapping(value = "topic/{id}/concept", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse createConcept(@PathVariable("id") Long id, @RequestBody ConceptRequest request) throws Throwable {
		logger.info("createConcept Request:" + request.toString());
		BaseResponse response = topicManager.addConcept(id, request);
		logger.info("createConcept Response:" + response.toString());
		return response;
	}
	
	@ApiOperation(value = "Get Concepts By Topic", notes = "Returns list of concepts")
	@RequestMapping(value = "topic/{id}/concepts", method = RequestMethod.GET)
	@ResponseBody
	public List<ConceptResponse> getConcepts(@PathVariable("id") Long id,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "size", required = false) Integer size) throws Throwable {
		logger.info("Request received for Concepts");
		List<ConceptResponse> response = topicManager.getConcepts(id);
		logger.info("Response:" + response.toString());
		return response;
	}
	
	@ApiOperation(value = "Create Problem in Topic", notes = "Returns created problem")
	@RequestMapping(value = "topic/{id}/problem", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse createProblem(@PathVariable("id") Long id, @RequestBody ProblemRequest request) throws Throwable {
		logger.info("createProblem Request:" + request.toString());
		BaseResponse response = topicManager.addProblem(id, request);
		logger.info("createProblem Response:" + response.toString());
		return response;
	}
	
	@ApiOperation(value = "Add an existing Problem to Topic", notes = "Returns added problem")
	@RequestMapping(value = "topic/{id}/problem/{problemId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse addProblem(@PathVariable("id") Long id,@PathVariable("problemId") Long problemId, @RequestBody Integer sequenceNo) throws Throwable {
		logger.info("createProblem Request:" + sequenceNo.toString());
		BaseResponse response = topicManager.addExistingProblem(id, problemId, sequenceNo);
		logger.info("createProblem Response:" + response.toString());
		return response;
	}
	
	@ApiOperation(value = "Get Problems By Topic", notes = "Returns list of problems")
	@RequestMapping(value = "topic/{id}/problems", method = RequestMethod.GET)
	@ResponseBody
	public List<ProblemResponse> getProblems(@PathVariable("id") Long id,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "size", required = false) Integer size) throws Throwable {
		logger.info("Request received for Problems");
		List<ProblemResponse> response = topicManager.getProblems(id);
		logger.info("Response:" + response.toString());
		return response;
	}
}
