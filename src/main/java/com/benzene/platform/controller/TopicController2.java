//package com.benzene.platform.controller;
//
//import java.util.List;
//
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.benzene.platform.entity.Topic;
//import com.benzene.platform.manager.TopicManager;
//import com.benzene.platform.request.GetAbstractReq;
//import com.benzene.util.LogFactory;
//import com.benzene.util.enums.State;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//
//@RestController
//@Api(value = "topic", description = "Topic APIs")
//@RequestMapping("topic")
//public class TopicController2 {
//
//	@Autowired
//	private TopicManager topicManager;
//
//	@Autowired
//	private LogFactory logfactory;
//
//	@SuppressWarnings("static-access")
//	private Logger logger = logfactory.getLogger(TopicController2.class);
//
//	@ApiOperation(value = "Create Topic", notes = "Returns created topic")
//	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public Topic createTopic(@RequestBody Topic topic) throws Throwable {
//		logger.info("createTopic Request:" + topic.toString());
//		Topic response = topicManager.addOrUpdateTopic(topic);
//		logger.info("createTopic Response:" + response.toString());
//		return response;
//	}
//
//	@ApiOperation(value = "Get a Topic", notes = "Returns topic")
//	@RequestMapping(value = "{id}", method = RequestMethod.GET)
//	@ResponseBody
//	public Topic getTopic(@PathVariable("id") Long id) throws Throwable {
//		logger.info("Request received for getting Topic for db_id: " + id);
//		Topic response = topicManager.getTopic(id, null);
//		logger.info("getTopic Response:" + response.toString());
//		return response;
//	}
//
//	@ApiOperation(value = "Get Topics", notes = "Returns list of topics")
//	@RequestMapping(method = RequestMethod.GET)
//	@ResponseBody
//	public List<Topic> getTopics(@RequestParam(value = "start", required = false) Integer start,
//			@RequestParam(value = "size", required = false) Integer size) throws Throwable {
//		logger.info("Request received for Topics by TeacherId and StudentId");
//		GetAbstractReq req = new GetAbstractReq(start, size);
//		List<Topic> response = topicManager.getTopics(req);
//		logger.info("Response:" + response.toString());
//		return response;
//	}
//
//	@ApiOperation(value = "Update Topic", notes = "Returns created topic")
//	@RequestMapping(value = "{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public Topic updateTopic(@PathVariable("id") Long id, @RequestBody Topic topic) throws Throwable {
//		topic.setId(id);
//		logger.info("updateTopic Request:" + topic.toString());
//		Topic response = topicManager.addOrUpdateTopic(topic);
//		logger.info("updateTopic Response:" + response.toString());
//		return response;
//	}
//
//	@ApiOperation(value = "Delete Topic", notes = "Returns created topic")
//	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
//	@ResponseBody
//	public void deleteTopic(@PathVariable("id") Long id) throws Throwable {
//		logger.info("deleteTopic Request:" + id);
//		Topic topic = new Topic();
//		topic.setId(id);
//		topic.setState(State.INACTIVE);
//		Topic response = topicManager.addOrUpdateTopic(topic);
//		logger.info("updateTopic Response:" + response.toString());
//	}
//}
