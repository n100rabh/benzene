package com.benzene.platform.manager;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.benzene.platform.entity.Problem;
import com.benzene.util.LogFactory;
import com.benzene.util.dao.CommonDAO;
import com.benzene.util.enums.State;
import com.benzene.util.request.GetAbstractReq;

@Service
public class ProblemManager {

	@Autowired
	private CommonDAO commonDAO;

	@Autowired
	private LogFactory logfactory;

	@SuppressWarnings("static-access")
	private Logger logger = logfactory.getLogger(ProblemManager.class);

	public Problem addOrUpdateProblem(Problem problem) {

		Long id = problem.getId();

		if (id == null) {
			commonDAO.saveEntity(problem);
		} else {
			Problem problem1 = (Problem) commonDAO.getEntity(id, null, Problem.class);
			problem = addUpdates(problem1, problem);
			commonDAO.updateEntity(problem);
		}

		return problem;
	}

	public Problem getProblem(Long id, State state) {
		return commonDAO.getEntity(id, state, Problem.class);
	}

	public List<Problem> getProblems(GetAbstractReq req) {
		return commonDAO.getEntities(Problem.class, req, null);
	}

	public void updateProblems(List<Problem> slist) {
		commonDAO.updateEntities(slist, Problem.class.getSimpleName());
	}

	Problem addUpdates(Problem oldObj, Problem newObj) {
		if (newObj.getState() != null) {
			oldObj.setState(newObj.getState());
		}
		if (newObj.getName() != null) {
			oldObj.setName(newObj.getName());
		}
		if (newObj.getLevel() != null) {
			oldObj.setLevel(newObj.getLevel());
		}
		if (newObj.getType() != null) {
			oldObj.setType(newObj.getType());
		}
		if (newObj.getQuestion() != null) {
			oldObj.setQuestion(newObj.getQuestion());
		}
		if (newObj.getOption1() != null) {
			oldObj.setOption1(newObj.getOption1());
		}
		if (newObj.getOption2() != null) {
			oldObj.setOption2(newObj.getOption2());
		}
		if (newObj.getOption3() != null) {
			oldObj.setOption3(newObj.getOption3());
		}
		if (newObj.getOption4() != null) {
			oldObj.setOption4(newObj.getOption4());
		}
		if (newObj.getSolution() != null) {
			oldObj.setSolution(newObj.getSolution());
		}
		oldObj.setLastUpdatedBy(newObj.getLastUpdatedBy());
		return oldObj;
	}
}
