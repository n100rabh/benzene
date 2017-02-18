package com.benzene.platform.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.benzene.platform.entity.Problem;
import com.benzene.platform.request.ProblemRequest;
import com.benzene.platform.response.ProblemResponse;
import com.benzene.util.LogFactory;
import com.benzene.util.dao.CommonDAO;
import com.benzene.util.enums.State;
import com.benzene.util.request.GetAbstractReq;
import com.benzene.util.sessionfactory.SqlSessionFactory;

@Service
public class ProblemManager {

	@Autowired
	private CommonDAO commonDAO;

	@Autowired
	private LogFactory logfactory;

	@Autowired
	private SqlSessionFactory sqlSessionfactory;

	@SuppressWarnings("static-access")
	private Logger logger = logfactory.getLogger(ProblemManager.class);

	public ProblemResponse addOrUpdateProblem(ProblemRequest request) {
		Session session = sqlSessionfactory.getSessionFactory().openSession();
		Transaction transaction = session.getTransaction();

		Long id = request.getId();
		Problem problem1 = new Problem(request);
		Problem problem = problem1;
		try {
			transaction.begin();
			if (id == null) {
				commonDAO.saveEntity(problem, session);
			} else {
				problem = (Problem) commonDAO.getEntity(id, null, Problem.class, session);
				problem.addUpdates(problem1);
				commonDAO.updateEntity(problem, session);
			}
			transaction.commit();
		} finally {
			session.close();
		}

		return new ProblemResponse(problem);
	}

	public ProblemResponse getProblem(Long id, State state) {
		Problem problem = commonDAO.getEntity(id, state, Problem.class);
		return new ProblemResponse(problem);
	}

	public List<ProblemResponse> getProblems(GetAbstractReq req) {
		List<Problem> problems = commonDAO.getEntities(Problem.class, req, null);
		return getListResponse(problems);
	}

	public void updateProblems(List<Problem> slist) {
		commonDAO.updateEntities(slist, Problem.class.getSimpleName());
	}
	
	public void deleteProblem(Long id) {
		Problem problem = commonDAO.getEntity(id, null, Problem.class);
		problem.delete();
	}

	private List<ProblemResponse> getListResponse(List<Problem> problems) {
		List<ProblemResponse> responses = new ArrayList<ProblemResponse>();
		for(Problem problem :  problems) {
			responses.add(new ProblemResponse(problem));
		}
		return responses;
	}
}
