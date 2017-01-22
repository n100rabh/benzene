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

import com.benzene.platform.entity.Text;
import com.benzene.platform.manager.TextManager;
import com.benzene.platform.request.GetAbstractReq;
import com.benzene.util.LogFactory;
import com.benzene.util.enums.State;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "text", description = "Text APIs")
@RequestMapping("text")
public class TextController {

	@Autowired
	private TextManager textManager;

	@Autowired
	private LogFactory logfactory;
	
	@SuppressWarnings("static-access")
	private Logger logger = logfactory.getLogger(TextController.class);

	@ApiOperation(value = "Create Text", notes = "Returns created text")
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Text createText(@RequestBody Text text) throws Throwable {
		logger.info("createText Request:" + text.toString());
		Text response = textManager.addOrUpdateText(text);
		logger.info("createText Response:" + response.toString());
		return response;
	}
	
	@ApiOperation(value = "Get a Text", notes = "Returns text")
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseBody
	public Text getText(@PathVariable("id") Long id) throws Throwable {
		logger.info("Request received for getting Text for db_id: " + id);
		Text response = textManager.getText(id, null);
		logger.info("getText Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Get Texts", notes = "Returns list of texts")
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Text> getTexts(
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "size", required = false) Integer size) throws Throwable {
		logger.info("Request received for Texts by TeacherId and StudentId");
		GetAbstractReq req = new GetAbstractReq(start, size);
		List<Text> response = textManager.getTexts(req);
		logger.info("Response:" + response.toString());
		return response;
	}

	@ApiOperation(value = "Update Text", notes = "Returns created text")
	@RequestMapping(value = "{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Text updateText(@PathVariable("id") Long id, @RequestBody Text text) throws Throwable {
		text.setId(id);
		logger.info("updateText Request:" + text.toString());
		Text response = textManager.addOrUpdateText(text);
		logger.info("updateText Response:" + response.toString());
		return response;
	}
	
	@ApiOperation(value = "Delete Text", notes = "Returns created text")
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteText(@PathVariable("id") Long id) throws Throwable {
		logger.info("deleteText Request:" + id);
		Text text = new Text();
		text.setId(id);
		text.setState(State.INACTIVE);
		Text response = textManager.addOrUpdateText(text);
		logger.info("updateText Response:" + response.toString());
	}
}
