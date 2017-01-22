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

import com.benzene.platform.entity.Concept;
import com.benzene.platform.manager.ConceptManager;
import com.benzene.platform.request.GetAbstractReq;
import com.benzene.util.LogFactory;
import com.benzene.util.enums.State;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "concept", description = "Concept APIs")
@RequestMapping("concept")
public class ConceptController {

	@Autowired
	private ConceptManager conceptManager;

	@Autowired
	private LogFactory logfactory;
	
	@SuppressWarnings("static-access")
	private Logger logger = logfactory.getLogger(ConceptController.class);

	@ApiOperation(value = "Create Concept", notes = "Returns created concept")
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Concept createConcept(@RequestBody Concept concept) throws Throwable {
		logger.info("createConcept Request:" + concept.toString());
		Concept response = conceptManager.addOrUpdateConcept(concept);
		logger.info("createConcept Response:" + response.toString());
		return response;
	}
	
	@ApiOperation(value = "Get a Concept", notes = "Returns concept")
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseBody
	public Concept getConcept(@PathVariable("id") Long id) throws Throwable {
		logger.info("Request received for getting Concept for db_id: " + id);
		Concept response = conceptManager.getConcept(id, null);
		logger.info("getConcept Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Get Concepts", notes = "Returns list of concepts")
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Concept> getConcepts(
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "size", required = false) Integer size) throws Throwable {
		logger.info("Request received for Concepts by TeacherId and StudentId");
		GetAbstractReq req = new GetAbstractReq(start, size);
		List<Concept> response = conceptManager.getConcepts(req);
		logger.info("Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Update Concept", notes = "Returns created concept")
	@RequestMapping(value = "{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Concept updateConcept(@PathVariable("id") Long id, @RequestBody Concept concept) throws Throwable {
		concept.setId(id);
		logger.info("updateConcept Request:" + concept.toString());
		Concept response = conceptManager.addOrUpdateConcept(concept);
		logger.info("updateConcept Response:" + response.toString());
		return response;
	}
	
	@ApiOperation(value = "Delete Concept", notes = "Returns created concept")
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteConcept(@PathVariable("id") Long id) throws Throwable {
		logger.info("deleteConcept Request:" + id);
		Concept concept = new Concept();
		concept.setId(id);
		concept.setState(State.INACTIVE);
		Concept response = conceptManager.addOrUpdateConcept(concept);
		logger.info("updateConcept Response:" + response.toString());
	}
}
