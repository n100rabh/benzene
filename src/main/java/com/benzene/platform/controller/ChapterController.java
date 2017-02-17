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

import com.benzene.platform.manager.ChapterManager;
import com.benzene.platform.request.ChapterRequest;
import com.benzene.platform.request.TopicRequest;
import com.benzene.platform.response.TopicResponse;
import com.benzene.util.LogFactory;
import com.benzene.util.enums.State;
import com.benzene.util.response.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Chapter", description = "Chapter APIs")
@RequestMapping("")
public class ChapterController {

	@Autowired
	private ChapterManager chapterManager;

	@Autowired
	private LogFactory logfactory;

	@SuppressWarnings("static-access")
	private Logger logger = logfactory.getLogger(ChapterController.class);

	@ApiOperation(value = "Get a Chapter", notes = "Returns chapter")
	@RequestMapping(value = "chapter/{id}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse getChapter(@PathVariable("id") Long id) throws Throwable {
		logger.info("Request received for getting Chapter for db_id: " + id);
		BaseResponse response = chapterManager.getChapter(id, null);
		logger.info("getChapter Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Update Chapter", notes = "Returns created chapter")
	@RequestMapping(value = "chapter/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse updateChapter(@PathVariable("id") Long id, @RequestBody ChapterRequest request) throws Throwable {
		request.setId(id);
		logger.info("updateChapter Request:" + request.toString());
		BaseResponse response = chapterManager.addOrUpdateChapter(request);
		logger.info("updateChapter Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Delete Chapter", notes = "Returns created chapter")
	@RequestMapping(value = "chapter/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteChapter(@PathVariable("id") Long id) throws Throwable {
		logger.info("deleteChapter Request:" + id);
		ChapterRequest request = new ChapterRequest();
		request.setId(id);
		request.setState(State.INACTIVE);
		BaseResponse response = chapterManager.addOrUpdateChapter(request);
		logger.info("updateChapter Response:" + response.toString());
	}
	
	@ApiOperation(value = "Create Topic in Chapter", notes = "Returns created topic")
	@RequestMapping(value = "chapter/{id}/topic", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse createTopic(@PathVariable("id") Long id, @RequestBody TopicRequest request) throws Throwable {
		logger.info("createTopic Request:" + request.toString());
		BaseResponse response = chapterManager.addTopic(id, request);
		logger.info("createTopic Response:" + response.toString());
		return response;
	}
	
	@ApiOperation(value = "Get Topics By Chapter", notes = "Returns list of topics")
	@RequestMapping(value = "chapter/{id}/topics", method = RequestMethod.GET)
	@ResponseBody
	public List<TopicResponse> getTopics(@PathVariable("id") Long id,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "size", required = false) Integer size) throws Throwable {
		logger.info("Request received for Topices");
		List<TopicResponse> response = chapterManager.getTopics(id);
		logger.info("Response:" + response.toString());
		return response;
	}
}
