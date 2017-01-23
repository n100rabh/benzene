package com.benzene.platform.controller;

import java.util.Set;

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
import com.benzene.platform.entity.Topic;
import com.benzene.platform.manager.SubjectManager;
import com.benzene.platform.manager.TopicManager;
import com.benzene.platform.request.GetAbstractReq;
import com.benzene.util.LogFactory;
import com.benzene.util.enums.State;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Topic", description = "Topic APIs")
@RequestMapping("subject/{id}/topics")
public class TopicController {

	@Autowired
	private SubjectManager subjectManager;
	
	@Autowired
	private TopicManager topicManager;

	@Autowired
	private LogFactory logfactory;

	@SuppressWarnings("static-access")
	private Logger logger = logfactory.getLogger(TopicController.class);

	@ApiOperation(value = "Create Topic", notes = "Returns created topic")
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Topic createTopic(@PathVariable("id") Long id, @RequestBody Topic topic) throws Throwable {
		logger.info("createTopic Request:" + topic.toString());
		Topic response = subjectManager.addTopic(id, topic);
		logger.info("createTopic Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Get a Topic", notes = "Returns topic")
	@RequestMapping(value = "{topicId}", method = RequestMethod.GET)
	@ResponseBody
	public Topic getTopic(@PathVariable("topicId") Long topicId) throws Throwable {
		logger.info("Request received for getting Topic for db_id: " + topicId);
		Topic response = topicManager.getTopic(topicId, null);
		logger.info("getTopic Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Get Topics", notes = "Returns list of topics")
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Set<Topic> getTopics(@PathVariable("id") Long id, @RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "size", required = false) Integer size) throws Throwable {
		logger.info("Request received for Topics by TeacherId and StudentId");
		GetAbstractReq req = new GetAbstractReq(start, size);
		Subject subject = subjectManager.getSubject(id, null);
		Set<Topic> response = subject.getTopics();
		logger.info("Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Update Topic", notes = "Returns created topic")
	@RequestMapping(value = "{topicId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Topic updateTopic(@PathVariable("topicId") Long topicId, @RequestBody Topic topic) throws Throwable {
		topic.setId(topicId);
		logger.info("updateTopic Request:" + topic.toString());
		Topic response = topicManager.addOrUpdateTopic(topic);
		logger.info("updateTopic Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Delete Topic", notes = "Returns created topic")
	@RequestMapping(value = "{topicId}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteTopic(@PathVariable("topicId") Long topicId) throws Throwable {
		logger.info("deleteTopic Request:" + topicId);
		Topic topic = new Topic();
		topic.setId(topicId);
		topic.setState(State.INACTIVE);
		Topic response = topicManager.addOrUpdateTopic(topic);
		logger.info("updateTopic Response:" + response.toString());
	}
}
