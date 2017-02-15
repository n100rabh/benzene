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

import com.benzene.platform.entity.Branch;
import com.benzene.platform.manager.BranchManager;
import com.benzene.util.LogFactory;
import com.benzene.util.enums.State;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Branch", description = "Branch APIs")
@RequestMapping("")
public class BranchController {

	@Autowired
	private BranchManager branchManager;

	@Autowired
	private LogFactory logfactory;

	@SuppressWarnings("static-access")
	private Logger logger = logfactory.getLogger(BranchController.class);

	@ApiOperation(value = "Get a Branch", notes = "Returns branch")
	@RequestMapping(value = "branch/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Branch getBranch(@PathVariable("id") Long id) throws Throwable {
		logger.info("Request received for getting Branch for db_id: " + id);
		Branch response = branchManager.getBranch(id, null);
		response.setSubject(null);
		logger.info("getBranch Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Update Branch", notes = "Returns created branch")
	@RequestMapping(value = "branch/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Branch updateBranch(@PathVariable("id") Long id, @RequestBody Branch branch) throws Throwable {
		branch.setId(id);
		logger.info("updateBranch Request:" + branch.toString());
		Branch response = branchManager.addOrUpdateBranch(branch);
		logger.info("updateBranch Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Delete Branch", notes = "Returns created branch")
	@RequestMapping(value = "branch/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteBranch(@PathVariable("id") Long id) throws Throwable {
		logger.info("deleteBranch Request:" + id);
		Branch branch = new Branch();
		branch.setId(id);
		branch.setState(State.INACTIVE);
		Branch response = branchManager.addOrUpdateBranch(branch);
		logger.info("updateBranch Response:" + response.toString());
	}
}
