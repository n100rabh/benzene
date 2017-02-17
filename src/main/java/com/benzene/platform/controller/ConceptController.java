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

import com.benzene.platform.manager.ConceptManager;
import com.benzene.platform.request.ConceptRequest;
import com.benzene.util.LogFactory;
import com.benzene.util.enums.State;
import com.benzene.util.response.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Concept", description = "Concept APIs")
@RequestMapping("")
public class ConceptController {

	@Autowired
	private ConceptManager conceptManager;

	@Autowired
	private LogFactory logfactory;

	@SuppressWarnings("static-access")
	private Logger logger = logfactory.getLogger(ConceptController.class);

	@ApiOperation(value = "Get a Concept", notes = "Returns concept")
	@RequestMapping(value = "concept/{id}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse getConcept(@PathVariable("id") Long id) throws Throwable {
		logger.info("Request received for getting Concept for db_id: " + id);
		BaseResponse response = conceptManager.getConcept(id, null);
		logger.info("getConcept Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Update Concept", notes = "Returns created concept")
	@RequestMapping(value = "concept/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse updateConcept(@PathVariable("id") Long id, @RequestBody ConceptRequest request) throws Throwable {
		request.setId(id);
		logger.info("updateConcept Request:" + request.toString());
		BaseResponse response = conceptManager.addOrUpdateConcept(request);
		logger.info("updateConcept Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Delete Concept", notes = "Returns created concept")
	@RequestMapping(value = "concept/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteConcept(@PathVariable("id") Long id) throws Throwable {
		logger.info("deleteConcept Request:" + id);
		ConceptRequest request = new ConceptRequest();
		request.setId(id);
		request.setState(State.INACTIVE);
		BaseResponse response = conceptManager.addOrUpdateConcept(request);
		logger.info("updateConcept Response:" + response.toString());
	}
}
