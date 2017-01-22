package com.benzene.platform.manager;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.benzene.platform.entity.Equation;
import com.benzene.platform.request.GetAbstractReq;
import com.benzene.util.LogFactory;
import com.benzene.util.dao.CommonDAO;
import com.benzene.util.enums.State;

@Service
public class EquationManager {

	@Autowired
	private CommonDAO commonDAO;

	@Autowired
	private LogFactory logfactory;

	@SuppressWarnings("static-access")
	private Logger logger = logfactory.getLogger(EquationManager.class);

	public Equation addOrUpdateEquation(Equation equation) {

		Long id = equation.getId();

		if (id == null) {
			commonDAO.saveEntity(equation);
		} else {
			Equation equation1 = (Equation) commonDAO.getEntity(id, null, Equation.class);
			equation = addUpdates(equation1, equation);
			commonDAO.updateEntity(equation);
		}

		return equation;
	}

	public Equation getEquation(Long id, State state) {
		return commonDAO.getEntity(id, state, Equation.class);
	}

	public List<Equation> getEquations(GetAbstractReq req) {
		return commonDAO.getEntities(Equation.class, req, null);
	}

	public void updateEquations(List<Equation> slist) {
		commonDAO.updateEntities(slist, Equation.class.getSimpleName());
	}

	Equation addUpdates(Equation oldObj, Equation newObj) {
		if (newObj.getState() != null) {
			oldObj.setState(newObj.getState());
		}
		if (newObj.getName() != null) {
			oldObj.setName(newObj.getName());
		}
		if (newObj.getText() != null) {
			oldObj.setText(newObj.getText());
		}
		oldObj.setLastUpdatedBy(newObj.getLastUpdatedBy());
		return oldObj;
	}
}
