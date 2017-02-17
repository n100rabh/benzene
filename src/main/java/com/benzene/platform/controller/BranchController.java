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

import com.benzene.platform.manager.BranchManager;
import com.benzene.platform.request.BranchRequest;
import com.benzene.platform.request.ChapterRequest;
import com.benzene.platform.response.ChapterResponse;
import com.benzene.util.LogFactory;
import com.benzene.util.enums.State;
import com.benzene.util.response.BaseResponse;

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
	public BaseResponse getBranch(@PathVariable("id") Long id) throws Throwable {
		logger.info("Request received for getting Branch for db_id: " + id);
		BaseResponse response = branchManager.getBranch(id, null);
		logger.info("getBranch Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Update Branch", notes = "Returns created branch")
	@RequestMapping(value = "branch/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse updateBranch(@PathVariable("id") Long id, @RequestBody BranchRequest request) throws Throwable {
		request.setId(id);
		logger.info("updateBranch Request:" + request.toString());
		BaseResponse response = branchManager.addOrUpdateBranch(request);
		logger.info("updateBranch Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Delete Branch", notes = "Returns created branch")
	@RequestMapping(value = "branch/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteBranch(@PathVariable("id") Long id) throws Throwable {
		logger.info("deleteBranch Request:" + id);
		BranchRequest request = new BranchRequest();
		request.setId(id);
		request.setState(State.INACTIVE);
		BaseResponse response = branchManager.addOrUpdateBranch(request);
		logger.info("updateBranch Response:" + response.toString());
	}
	
	@ApiOperation(value = "Create Chapter in Branch", notes = "Returns created chapter")
	@RequestMapping(value = "branch/{id}/chapter", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse createChapter(@PathVariable("id") Long id, @RequestBody ChapterRequest request) throws Throwable {
		logger.info("createChapter Request:" + request.toString());
		BaseResponse response = branchManager.addChapter(id, request);
		logger.info("createChapter Response:" + response.toString());
		return response;
	}
	
	@ApiOperation(value = "Get Chapters By Branch", notes = "Returns list of chapters")
	@RequestMapping(value = "branch/{id}/chapters", method = RequestMethod.GET)
	@ResponseBody
	public List<ChapterResponse> getChapters(@PathVariable("id") Long id,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "size", required = false) Integer size) throws Throwable {
		logger.info("Request received for Chapteres");
		List<ChapterResponse> response = branchManager.getChapters(id);
		logger.info("Response:" + response.toString());
		return response;
	}
}
