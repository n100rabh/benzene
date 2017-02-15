package com.benzene.platform.manager;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.benzene.platform.entity.Topic;
import com.benzene.platform.entity.Chapter;
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

	public Chapter addOrUpdateChapter(Chapter chapter) {
		Session session = sqlSessionfactory.getSessionFactory().openSession();
		Transaction transaction = session.getTransaction();

		Long id = chapter.getId();
		try {
			transaction.begin();
			if (id == null) {
				commonDAO.saveEntity(chapter, session);
			} else {
				Chapter chapter1 = (Chapter) commonDAO.getEntity(id, null, Chapter.class, session);
				chapter = addUpdates(chapter1, chapter);
				commonDAO.updateEntity(chapter, session);
			}
			transaction.commit();
		} finally {
			session.close();
		}

		return chapter;
	}

	public Chapter getChapter(Long id, State state) {
		return commonDAO.getEntity(id, state, Chapter.class);
	}

	public List<Chapter> getChapters(GetAbstractReq req) {
		return commonDAO.getEntities(Chapter.class, req, null);
	}

	public void updateChapters(List<Chapter> slist) {
		commonDAO.updateEntities(slist, Chapter.class.getSimpleName());
	}

	public Topic addTopic(Long id, Topic topic) {
		Session session = sqlSessionfactory.getSessionFactory().openSession();
		Transaction transaction = session.getTransaction();
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
		topic.setChapter(null);
		return topic;
	}

	Chapter addUpdates(Chapter oldObj, Chapter newObj) {
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
