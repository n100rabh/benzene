package com.benzene.platform.manager;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.benzene.platform.entity.Topic;
import com.benzene.platform.request.GetAbstractReq;
import com.benzene.util.LogFactory;
import com.benzene.util.dao.CommonDAO;
import com.benzene.util.enums.State;

@Service
public class TopicManager {

	@Autowired
	private CommonDAO commonDAO;

	@Autowired
	private LogFactory logfactory;

	@SuppressWarnings("static-access")
	private Logger logger = logfactory.getLogger(TopicManager.class);

	public Topic addOrUpdateTopic(Topic topic) {

		Long id = topic.getId();

		if (id == null) {
			commonDAO.saveEntity(topic);
		} else {
			Topic topic1 = (Topic) commonDAO.getEntity(id, null, Topic.class);
			topic = addUpdates(topic1, topic);
			commonDAO.updateEntity(topic);
		}

		return topic;
	}

	public Topic getTopic(Long id, State state) {
		return commonDAO.getEntity(id, state, Topic.class);
	}

	public List<Topic> getTopics(GetAbstractReq req) {
		return commonDAO.getEntities(Topic.class, req, null);
	}

	public void updateTopics(List<Topic> slist) {
		commonDAO.updateEntities(slist, Topic.class.getSimpleName());
	}

	Topic addUpdates(Topic oldObj, Topic newObj) {
		if (newObj.getState() != null) {
			oldObj.setState(newObj.getState());
		}
		if (newObj.getName() != null) {
			oldObj.setName(newObj.getName());
		}
		oldObj.setLastUpdatedBy(newObj.getLastUpdatedBy());
		return oldObj;
	}
}
