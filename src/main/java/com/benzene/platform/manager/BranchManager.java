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
import com.benzene.platform.entity.Chapter;
import com.benzene.platform.request.BranchRequest;
import com.benzene.platform.request.ChapterRequest;
import com.benzene.platform.response.BranchResponse;
import com.benzene.platform.response.ChapterResponse;
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

	public BranchResponse addOrUpdateBranch(BranchRequest request) {
		Session session = sqlSessionfactory.getSessionFactory().openSession();
		Transaction transaction = session.getTransaction();

		Long id = request.getId();
		Branch branch1 = new Branch(request);
		Branch branch = branch1;
		try {
			transaction.begin();
			if (id == null) {
				commonDAO.saveEntity(branch, session);
			} else {
				branch = (Branch) commonDAO.getEntity(id, null, Branch.class, session);
				branch.addUpdates(branch1);
				commonDAO.updateEntity(branch, session);
			}
			transaction.commit();
		} finally {
			session.close();
		}

		return new BranchResponse(branch);
	}

	public BranchResponse getBranch(Long id, State state) {
		Branch branch = commonDAO.getEntity(id, state, Branch.class);
		return new BranchResponse(branch);
	}

	public List<BranchResponse> getBranches(GetAbstractReq req) {
		List<Branch> branchs = commonDAO.getEntities(Branch.class, req, null);
		return getListResponse(branchs);
	}

	public void updateBranchs(List<Branch> slist) {
		commonDAO.updateEntities(slist, Branch.class.getSimpleName());
	}
	
	public void deleteBranch(Long id) {
		Branch branch = commonDAO.getEntity(id, null, Branch.class);
		branch.delete();
	}

	public ChapterResponse addChapter(Long id, ChapterRequest request) {
		Session session = sqlSessionfactory.getSessionFactory().openSession();
		Transaction transaction = session.getTransaction();
		
		Chapter chapter = new Chapter(request);
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
		return new ChapterResponse(chapter);
	}
	
	public List<ChapterResponse> getChapters(Long id) {
		Branch branch = commonDAO.getEntity(id, null, Branch.class);
		Set<Chapter> chapters = branch.getChapters();
		return getChapterList(chapters);
	}

	private List<BranchResponse> getListResponse(List<Branch> branches) {
		List<BranchResponse> responses = new ArrayList<BranchResponse>();
		for(Branch branch :  branches) {
			responses.add(new BranchResponse(branch));
		}
		return responses;
	}
	
	private List<ChapterResponse> getChapterList(Set<Chapter> chapters) {
		List<ChapterResponse> responses = new ArrayList<ChapterResponse>();
		for(Chapter chapter :  chapters) {
			responses.add(new ChapterResponse(chapter));
		}
		return responses;
	}
}
