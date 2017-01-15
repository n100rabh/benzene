package com.benzene.platform.manager;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.benzene.platform.entity.Subject;
import com.benzene.platform.request.GetAbstractReq;
import com.benzene.util.LogFactory;
import com.benzene.util.dao.CommonDAO;
import com.benzene.util.enums.State;

@Service
public class SubjectManager {

	@Autowired
	private CommonDAO commonDAO;

	@Autowired
	private LogFactory logfactory;

	@SuppressWarnings("static-access")
	private Logger logger = logfactory.getLogger(SubjectManager.class);

	public Subject addOrUpdateSubject(Subject subject) {

		Long id = subject.getId();

		if (id == null) {
			commonDAO.saveEntity(subject);
		} else {
			Subject subject1 = (Subject) commonDAO.getEntity(id, null,
					Subject.class);
			subject = addUpdates(subject1, subject);
			commonDAO.updateEntity(subject);
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
