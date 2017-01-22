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

import com.benzene.platform.entity.Equation;
import com.benzene.platform.manager.EquationManager;
import com.benzene.platform.request.GetAbstractReq;
import com.benzene.util.LogFactory;
import com.benzene.util.enums.State;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "equation", description = "Equation APIs")
@RequestMapping("equation")
public class EquationController {

	@Autowired
	private EquationManager equationManager;

	@Autowired
	private LogFactory logfactory;
	
	@SuppressWarnings("static-access")
	private Logger logger = logfactory.getLogger(EquationController.class);

	@ApiOperation(value = "Create Equation", notes = "Returns created equation")
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Equation createEquation(@RequestBody Equation equation) throws Throwable {
		logger.info("createEquation Request:" + equation.toString());
		Equation response = equationManager.addOrUpdateEquation(equation);
		logger.info("createEquation Response:" + response.toString());
		return response;
	}
	
	@ApiOperation(value = "Get a Equation", notes = "Returns equation")
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseBody
	public Equation getEquation(@PathVariable("id") Long id) throws Throwable {
		logger.info("Request received for getting Equation for db_id: " + id);
		Equation response = equationManager.getEquation(id, null);
		logger.info("getEquation Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Get Equations", notes = "Returns list of equations")
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Equation> getEquations(
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "size", required = false) Integer size) throws Throwable {
		logger.info("Request received for Equations by TeacherId and StudentId");
		GetAbstractReq req = new GetAbstractReq(start, size);
		List<Equation> response = equationManager.getEquations(req);
		logger.info("Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Update Equation", notes = "Returns created equation")
	@RequestMapping(value = "{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Equation updateEquation(@PathVariable("id") Long id, @RequestBody Equation equation) throws Throwable {
		equation.setId(id);
		logger.info("updateEquation Request:" + equation.toString());
		Equation response = equationManager.addOrUpdateEquation(equation);
		logger.info("updateEquation Response:" + response.toString());
		return response;
	}
	
	@ApiOperation(value = "Delete Equation", notes = "Returns created equation")
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteEquation(@PathVariable("id") Long id) throws Throwable {
		logger.info("deleteEquation Request:" + id);
		Equation equation = new Equation();
		equation.setId(id);
		equation.setState(State.INACTIVE);
		Equation response = equationManager.addOrUpdateEquation(equation);
		logger.info("updateEquation Response:" + response.toString());
	}
}
