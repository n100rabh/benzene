package com.benzene.platform.controller;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.benzene.platform.manager.ProblemManager;
import com.benzene.platform.request.ProblemRequest;
import com.benzene.util.LogFactory;
import com.benzene.util.enums.State;
import com.benzene.util.response.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Problem", description = "Problem APIs")
@RequestMapping("")
public class ProblemController {

	@Autowired
	private ProblemManager problemManager;

	@Autowired
	private LogFactory logfactory;

	@SuppressWarnings("static-access")
	private Logger logger = logfactory.getLogger(ProblemController.class);

	@ApiOperation(value = "Get a Problem", notes = "Returns problem")
	@RequestMapping(value = "problem/{id}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse getProblem(@PathVariable("id") Long id) throws Throwable {
		logger.info("Request received for getting Problem for db_id: " + id);
		BaseResponse response = problemManager.getProblem(id, null);
		logger.info("getProblem Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Update Problem", notes = "Returns created problem")
	@RequestMapping(value = "problem/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse updateProblem(@PathVariable("id") Long id, @RequestBody ProblemRequest request) throws Throwable {
		request.setId(id);
		logger.info("updateProblem Request:" + request.toString());
		BaseResponse response = problemManager.addOrUpdateProblem(request);
		logger.info("updateProblem Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Delete Problem", notes = "Returns created problem")
	@RequestMapping(value = "problem/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteProblem(@PathVariable("id") Long id) throws Throwable {
		logger.info("deleteProblem Request:" + id);
		ProblemRequest request = new ProblemRequest();
		request.setId(id);
		request.setState(State.INACTIVE);
		BaseResponse response = problemManager.addOrUpdateProblem(request);
		logger.info("updateProblem Response:" + response.toString());
	}
}
