package com.benzene.platform.manager;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.benzene.platform.entity.Concept;
import com.benzene.util.LogFactory;
import com.benzene.util.dao.CommonDAO;
import com.benzene.util.enums.State;
import com.benzene.util.request.GetAbstractReq;

@Service
public class ConceptManager {

	@Autowired
	private CommonDAO commonDAO;

	@Autowired
	private LogFactory logfactory;

	@SuppressWarnings("static-access")
	private Logger logger = logfactory.getLogger(ConceptManager.class);

	public Concept addOrUpdateConcept(Concept concept) {

		Long id = concept.getId();

		if (id == null) {
			commonDAO.saveEntity(concept);
		} else {
			Concept concept1 = (Concept) commonDAO.getEntity(id, null, Concept.class);
			concept = addUpdates(concept1, concept);
			commonDAO.updateEntity(concept);
		}

		return concept;
	}

	public Concept getConcept(Long id, State state) {
		return commonDAO.getEntity(id, state, Concept.class);
	}

	public List<Concept> getConcepts(GetAbstractReq req) {
		return commonDAO.getEntities(Concept.class, req, null);
	}

	public void updateConcepts(List<Concept> slist) {
		commonDAO.updateEntities(slist, Concept.class.getSimpleName());
	}

	Concept addUpdates(Concept oldObj, Concept newObj) {
		if (newObj.getState() != null) {
			oldObj.setState(newObj.getState());
		}
		if (newObj.getName() != null) {
			oldObj.setName(newObj.getName());
		}
		if (newObj.getHeading() != null) {
			oldObj.setHeading(newObj.getHeading());
		}
		if (newObj.getText() != null) {
			oldObj.setText(newObj.getText());
		}
		if (newObj.getSequenceNo() != null) {
			oldObj.setSequenceNo(newObj.getSequenceNo());
		}
		oldObj.setLastUpdatedBy(newObj.getLastUpdatedBy());
		return oldObj;
	}
}
