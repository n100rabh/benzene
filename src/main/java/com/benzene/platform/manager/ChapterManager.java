package com.benzene.platform.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.benzene.platform.entity.Topic;
import com.benzene.platform.entity.Chapter;
import com.benzene.platform.request.TopicRequest;
import com.benzene.platform.request.ChapterRequest;
import com.benzene.platform.response.TopicResponse;
import com.benzene.platform.response.ChapterResponse;
import com.benzene.util.LogFactory;
import com.benzene.util.dao.CommonDAO;
import com.benzene.util.enums.State;
import com.benzene.util.request.GetAbstractReq;
import com.benzene.util.sessionfactory.SqlSessionFactory;

@Service
public class ChapterManager {

	@Autowired
	private CommonDAO commonDAO;

	@Autowired
	private LogFactory logfactory;

	@Autowired
	private SqlSessionFactory sqlSessionfactory;

	@SuppressWarnings("static-access")
	private Logger logger = logfactory.getLogger(ChapterManager.class);

	public ChapterResponse addOrUpdateChapter(ChapterRequest request) {
		Session session = sqlSessionfactory.getSessionFactory().openSession();
		Transaction transaction = session.getTransaction();

		Long id = request.getId();
		Chapter chapter1 = new Chapter(request);
		Chapter chapter = chapter1;
		try {
			transaction.begin();
			if (id == null) {
				commonDAO.saveEntity(chapter, session);
			} else {
				chapter = (Chapter) commonDAO.getEntity(id, null, Chapter.class, session);
				chapter.addUpdates(chapter1);
				commonDAO.updateEntity(chapter, session);
			}
			transaction.commit();
		} finally {
			session.close();
		}

		return new ChapterResponse(chapter);
	}

	public ChapterResponse getChapter(Long id, State state) {
		Chapter chapter = commonDAO.getEntity(id, state, Chapter.class);
		return new ChapterResponse(chapter);
	}

	public List<ChapterResponse> getChapters(GetAbstractReq req) {
		List<Chapter> chapters = commonDAO.getEntities(Chapter.class, req, null);
		return getListResponse(chapters);
	}

	public void updateChapters(List<Chapter> slist) {
		commonDAO.updateEntities(slist, Chapter.class.getSimpleName());
	}
	
	public void deleteChapter(Long id) {
		Chapter chapter = commonDAO.getEntity(id, null, Chapter.class);
		chapter.delete();
	}

	public TopicResponse addTopic(Long id, TopicRequest request) {
		Session session = sqlSessionfactory.getSessionFactory().openSession();
		Transaction transaction = session.getTransaction();
		
		Topic topic = new Topic(request);
		try {
			transaction.begin();
			Chapter chapter = (Chapter) commonDAO.getEntity(id, null, Chapter.class, session);
			chapter.addTopic(topic);
			topic.setChapter(chapter);
			commonDAO.setEntityDefaultProperties(topic);
			transaction.commit();
		} finally {
			session.close();
		}
		return new TopicResponse(topic);
	}
	
	public List<TopicResponse> getTopics(Long id) {
		Chapter chapter = commonDAO.getEntity(id, null, Chapter.class);
		Set<Topic> topices = chapter.getTopics();
		return getTopicList(topices);
	}

	private List<ChapterResponse> getListResponse(List<Chapter> chapters) {
		List<ChapterResponse> responses = new ArrayList<ChapterResponse>();
		for(Chapter chapter :  chapters) {
			responses.add(new ChapterResponse(chapter));
		}
		return responses;
	}
	
	private List<TopicResponse> getTopicList(Set<Topic> topices) {
		List<TopicResponse> responses = new ArrayList<TopicResponse>();
		for(Topic topic :  topices) {
			responses.add(new TopicResponse(topic));
		}
		return responses;
	}
}
