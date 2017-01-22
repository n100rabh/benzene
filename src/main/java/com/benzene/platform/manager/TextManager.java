package com.benzene.platform.manager;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.benzene.platform.entity.Text;
import com.benzene.platform.request.GetAbstractReq;
import com.benzene.util.LogFactory;
import com.benzene.util.dao.CommonDAO;
import com.benzene.util.enums.State;

@Service
public class TextManager {

	@Autowired
	private CommonDAO commonDAO;

	@Autowired
	private LogFactory logfactory;

	@SuppressWarnings("static-access")
	private Logger logger = logfactory.getLogger(TextManager.class);

	public Text addOrUpdateText(Text text) {

		Long id = text.getId();

		if (id == null) {
			commonDAO.saveEntity(text);
		} else {
			Text text1 = (Text) commonDAO.getEntity(id, null, Text.class);
			text = addUpdates(text1, text);
			commonDAO.updateEntity(text);
		}

		return text;
	}

	public Text getText(Long id, State state) {
		return commonDAO.getEntity(id, state, Text.class);
	}

	public List<Text> getTexts(GetAbstractReq req) {
		return commonDAO.getEntities(Text.class, req, null);
	}

	public void updateTexts(List<Text> slist) {
		commonDAO.updateEntities(slist, Text.class.getSimpleName());
	}

	Text addUpdates(Text oldObj, Text newObj) {
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
