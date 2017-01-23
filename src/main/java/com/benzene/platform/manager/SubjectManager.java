package com.benzene.platform.manager;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.benzene.platform.entity.Subject;
import com.benzene.platform.entity.Topic;
import com.benzene.platform.request.GetAbstractReq;
import com.benzene.util.LogFactory;
import com.benzene.util.dao.CommonDAO;
import com.benzene.util.enums.State;
import com.benzene.util.sessionfactory.SqlSessionFactory;

@Service
public class SubjectManager {

	@Autowired
	private CommonDAO commonDAO;

	@Autowired
	private LogFactory logfactory;

	@Autowired
	private SqlSessionFactory sqlSessionfactory;

	@SuppressWarnings("static-access")
	private Logger logger = logfactory.getLogger(SubjectManager.class);

	public Subject addOrUpdateSubject(Subject subject) {
		Session session = sqlSessionfactory.getSessionFactory().openSession();
		Transaction transaction = session.getTransaction();

		Long id = subject.getId();
		try {
			transaction.begin();
			if (id == null) {
				commonDAO.saveEntity(subject, session);
			} else {
				Subject subject1 = (Subject) commonDAO.getEntity(id, null, Subject.class, session);
				subject = addUpdates(subject1, subject);
				commonDAO.updateEntity(subject, session);
			}
			transaction.commit();
		} finally {
			session.close();
		}

		return subject;
	}

	public Subject getSubject(Long id, State state) {
		return commonDAO.getEntity(id, state, Subject.class);
	}

	public List<Subject> getSubjects(GetAbstractReq req) {
		return commonDAO.getEntities(Subject.class, req, null);
	}

	public void updateSubjects(List<Subject> slist) {
		commonDAO.updateEntities(slist, Subject.class.getSimpleName());
	}

	public Topic addTopic(Long id, Topic topic) {
		Session session = sqlSessionfactory.getSessionFactory().openSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			Subject subject = (Subject) commonDAO.getEntity(id, null, Subject.class, session);
			subject.addTopic(topic);
			topic.setSubject(subject);
			transaction.commit();
		} finally {
			session.close();
		}
		topic.setSubject(null);
		return topic;
	}

	Subject addUpdates(Subject oldObj, Subject newObj) {
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
