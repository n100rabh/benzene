package com.benzene.platform.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.benzene.platform.entity.Concept;
import com.benzene.platform.request.ConceptRequest;
import com.benzene.platform.response.ConceptResponse;
import com.benzene.util.LogFactory;
import com.benzene.util.dao.CommonDAO;
import com.benzene.util.enums.State;
import com.benzene.util.request.GetAbstractReq;
import com.benzene.util.sessionfactory.SqlSessionFactory;

@Service
public class ConceptManager {

	@Autowired
	private CommonDAO commonDAO;

	@Autowired
	private LogFactory logfactory;

	@Autowired
	private SqlSessionFactory sqlSessionfactory;

	@SuppressWarnings("static-access")
	private Logger logger = logfactory.getLogger(ConceptManager.class);

	public ConceptResponse addOrUpdateConcept(ConceptRequest request) {
		Session session = sqlSessionfactory.getSessionFactory().openSession();
		Transaction transaction = session.getTransaction();

		Long id = request.getId();
		Concept concept1 = new Concept(request);
		Concept concept = concept1;
		try {
			transaction.begin();
			if (id == null) {
				commonDAO.saveEntity(concept, session);
			} else {
				concept = (Concept) commonDAO.getEntity(id, null, Concept.class, session);
				concept.addUpdates(concept1);
				commonDAO.updateEntity(concept, session);
			}
			transaction.commit();
		} finally {
			session.close();
		}

		return new ConceptResponse(concept);
	}

	public ConceptResponse getConcept(Long id, State state) {
		Concept concept = commonDAO.getEntity(id, state, Concept.class);
		return new ConceptResponse(concept);
	}

	public List<ConceptResponse> getConcepts(GetAbstractReq req) {
		List<Concept> concepts = commonDAO.getEntities(Concept.class, req, null);
		return getListResponse(concepts);
	}

	public void updateConcepts(List<Concept> slist) {
		commonDAO.updateEntities(slist, Concept.class.getSimpleName());
	}
	
	public void deleteConcept(Long id) {
		Concept concept = commonDAO.getEntity(id, null, Concept.class);
		concept.delete();
	}

	private List<ConceptResponse> getListResponse(List<Concept> concepts) {
		List<ConceptResponse> responses = new ArrayList<ConceptResponse>();
		for(Concept concept :  concepts) {
			responses.add(new ConceptResponse(concept));
		}
		return responses;
	}
}
