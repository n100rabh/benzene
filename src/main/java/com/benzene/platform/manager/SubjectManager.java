package com.benzene.platform.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.benzene.platform.entity.Branch;
import com.benzene.platform.entity.Subject;
import com.benzene.platform.request.BranchRequest;
import com.benzene.platform.request.SubjectRequest;
import com.benzene.platform.response.BranchResponse;
import com.benzene.platform.response.SubjectResponse;
import com.benzene.util.LogFactory;
import com.benzene.util.dao.CommonDAO;
import com.benzene.util.enums.State;
import com.benzene.util.request.GetAbstractReq;
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

	public SubjectResponse addOrUpdateSubject(SubjectRequest request) {
		Session session = sqlSessionfactory.getSessionFactory().openSession();
		Transaction transaction = session.getTransaction();

		Long id = request.getId();
		Subject subject1 = new Subject(request);
		Subject subject = subject1;
		try {
			transaction.begin();
			if (id == null) {
				commonDAO.saveEntity(subject, session);
			} else {
				subject = (Subject) commonDAO.getEntity(id, null, Subject.class, session);
				subject.addUpdates(subject1);
				commonDAO.updateEntity(subject, session);
			}
			transaction.commit();
		} finally {
			session.close();
		}

		return new SubjectResponse(subject);
	}

	public SubjectResponse getSubject(Long id, State state) {
		Subject subject = commonDAO.getEntity(id, state, Subject.class);
		return new SubjectResponse(subject);
	}

	public List<SubjectResponse> getSubjects(GetAbstractReq req) {
		List<Subject> subjects = commonDAO.getEntities(Subject.class, req, null);
		return getListResponse(subjects);
	}

	public void updateSubjects(List<Subject> slist) {
		commonDAO.updateEntities(slist, Subject.class.getSimpleName());
	}
	
	public void deleteSubject(Long id) {
		Subject subject = commonDAO.getEntity(id, null, Subject.class);
		subject.delete();
	}

	public BranchResponse addBranch(Long id, BranchRequest request) {
		Session session = sqlSessionfactory.getSessionFactory().openSession();
		Transaction transaction = session.getTransaction();
		
		Branch branch = new Branch(request);
		try {
			transaction.begin();
			Subject subject = (Subject) commonDAO.getEntity(id, null, Subject.class, session);
			subject.addBranch(branch);
			branch.setSubject(subject);
			commonDAO.setEntityDefaultProperties(branch);
			transaction.commit();
		} finally {
			session.close();
		}
		return new BranchResponse(branch);
	}
	
	public List<BranchResponse> getBranches(Long id) {
		Subject subject = commonDAO.getEntity(id, null, Subject.class);
		Set<Branch> branches = subject.getBranches();
		return getBranchList(branches);
	}

	private List<SubjectResponse> getListResponse(List<Subject> subjects) {
		List<SubjectResponse> responses = new ArrayList<SubjectResponse>();
		for(Subject subject :  subjects) {
			responses.add(new SubjectResponse(subject));
		}
		return responses;
	}
	
	private List<BranchResponse> getBranchList(Set<Branch> branches) {
		List<BranchResponse> responses = new ArrayList<BranchResponse>();
		for(Branch branch :  branches) {
			responses.add(new BranchResponse(branch));
		}
		return responses;
	}
}
