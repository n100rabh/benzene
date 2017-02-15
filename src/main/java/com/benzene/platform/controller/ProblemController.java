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

import com.benzene.platform.entity.Problem;
import com.benzene.platform.manager.ProblemManager;
import com.benzene.util.LogFactory;
import com.benzene.util.enums.State;
import com.benzene.util.request.GetAbstractReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "problem", description = "Problem APIs")
@RequestMapping("problem")
public class ProblemController {

	@Autowired
	private ProblemManager problemManager;

	@Autowired
	private LogFactory logfactory;
	
	@SuppressWarnings("static-access")
	private Logger logger = logfactory.getLogger(ProblemController.class);

	@ApiOperation(value = "Create Problem", notes = "Returns created problem")
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Problem createProblem(@RequestBody Problem problem) throws Throwable {
		logger.info("createProblem Request:" + problem.toString());
		Problem response = problemManager.addOrUpdateProblem(problem);
		logger.info("createProblem Response:" + response.toString());
		return response;
	}
	
	@ApiOperation(value = "Get a Problem", notes = "Returns problem")
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseBody
	public Problem getProblem(@PathVariable("id") Long id) throws Throwable {
		logger.info("Request received for getting Problem for db_id: " + id);
		Problem response = problemManager.getProblem(id, null);
		logger.info("getProblem Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Get Problems", notes = "Returns list of problems")
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Problem> getProblems(
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "size", required = false) Integer size) throws Throwable {
		logger.info("Request received for Problems by TeacherId and StudentId");
		GetAbstractReq req = new GetAbstractReq(start, size);
		List<Problem> response = problemManager.getProblems(req);
		logger.info("Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Update Problem", notes = "Returns created problem")
	@RequestMapping(value = "{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Problem updateProblem(@PathVariable("id") Long id, @RequestBody Problem problem) throws Throwable {
		problem.setId(id);
		logger.info("updateProblem Request:" + problem.toString());
		Problem response = problemManager.addOrUpdateProblem(problem);
		logger.info("updateProblem Response:" + response.toString());
		return response;
	}
	
	@ApiOperation(value = "Delete Problem", notes = "Returns created problem")
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteProblem(@PathVariable("id") Long id) throws Throwable {
		logger.info("deleteProblem Request:" + id);
		Problem problem = new Problem();
		problem.setId(id);
		problem.setState(State.INACTIVE);
		Problem response = problemManager.addOrUpdateProblem(problem);
		logger.info("updateProblem Response:" + response.toString());
	}
}
