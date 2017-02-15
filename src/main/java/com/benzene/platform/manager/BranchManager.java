package com.benzene.platform.manager;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.benzene.platform.entity.Branch;
import com.benzene.platform.entity.Chapter;
import com.benzene.util.LogFactory;
import com.benzene.util.dao.CommonDAO;
import com.benzene.util.enums.State;
import com.benzene.util.request.GetAbstractReq;
import com.benzene.util.sessionfactory.SqlSessionFactory;

@Service
public class BranchManager {

	@Autowired
	private CommonDAO commonDAO;

	@Autowired
	private LogFactory logfactory;

	@Autowired
	private SqlSessionFactory sqlSessionfactory;

	@SuppressWarnings("static-access")
	private Logger logger = logfactory.getLogger(BranchManager.class);

	public Branch addOrUpdateBranch(Branch branch) {
		Session session = sqlSessionfactory.getSessionFactory().openSession();
		Transaction transaction = session.getTransaction();

		Long id = branch.getId();
		try {
			transaction.begin();
			if (id == null) {
				commonDAO.saveEntity(branch, session);
			} else {
				Branch branch1 = (Branch) commonDAO.getEntity(id, null, Branch.class, session);
				branch = addUpdates(branch1, branch);
				commonDAO.updateEntity(branch, session);
			}
			transaction.commit();
		} finally {
			session.close();
		}

		return branch;
	}

	public Branch getBranch(Long id, State state) {
		return commonDAO.getEntity(id, state, Branch.class);
	}

	public List<Branch> getBranchs(GetAbstractReq req) {
		return commonDAO.getEntities(Branch.class, req, null);
	}

	public void updateBranchs(List<Branch> slist) {
		commonDAO.updateEntities(slist, Branch.class.getSimpleName());
	}

	public Chapter addChapter(Long id, Chapter chapter) {
		Session session = sqlSessionfactory.getSessionFactory().openSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			Branch branch = (Branch) commonDAO.getEntity(id, null, Branch.class, session);
			branch.addChapter(chapter);
			chapter.setBranch(branch);
			commonDAO.setEntityDefaultProperties(chapter);
			transaction.commit();
		} finally {
			session.close();
		}
		chapter.setBranch(null);
		return chapter;
	}

	Branch addUpdates(Branch oldObj, Branch newObj) {
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
