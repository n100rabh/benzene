package com.benzene.platform.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.benzene.platform.entity.Concept;
import com.benzene.platform.entity.Problem;
import com.benzene.platform.entity.Topic;
import com.benzene.platform.entity.TopicProblemMapping;
import com.benzene.platform.request.ConceptRequest;
import com.benzene.platform.request.ProblemRequest;
import com.benzene.platform.request.TopicRequest;
import com.benzene.platform.response.ConceptResponse;
import com.benzene.platform.response.ProblemResponse;
import com.benzene.platform.response.TopicResponse;
import com.benzene.util.LogFactory;
import com.benzene.util.dao.CommonDAO;
import com.benzene.util.enums.State;
import com.benzene.util.request.GetAbstractReq;
import com.benzene.util.sessionfactory.SqlSessionFactory;

@Service
public class TopicManager {

	@Autowired
	private CommonDAO commonDAO;

	@Autowired
	private LogFactory logfactory;

	@Autowired
	private SqlSessionFactory sqlSessionfactory;

	@SuppressWarnings("static-access")
	private Logger logger = logfactory.getLogger(TopicManager.class);

	public TopicResponse addOrUpdateTopic(TopicRequest request) {
		Session session = sqlSessionfactory.getSessionFactory().openSession();
		Transaction transaction = session.getTransaction();

		Long id = request.getId();
		Topic topic1 = new Topic(request);
		Topic topic = topic1;
		try {
			transaction.begin();
			if (id == null) {
				commonDAO.saveEntity(topic, session);
			} else {
				topic = (Topic) commonDAO.getEntity(id, null, Topic.class, session);
				topic.addUpdates(topic1);
				commonDAO.updateEntity(topic, session);
			}
			transaction.commit();
		} finally {
			session.close();
		}

		return new TopicResponse(topic);
	}

	public TopicResponse getTopic(Long id, State state) {
		Topic topic = commonDAO.getEntity(id, state, Topic.class);
		return new TopicResponse(topic);
	}

	public List<TopicResponse> getTopics(GetAbstractReq req) {
		List<Topic> topics = commonDAO.getEntities(Topic.class, req, null);
		return getListResponse(topics);
	}

	public void updateTopics(List<Topic> slist) {
		commonDAO.updateEntities(slist, Topic.class.getSimpleName());
	}

	public void deleteTopic(Long id) {
		Topic topic = commonDAO.getEntity(id, null, Topic.class);
		topic.delete();
	}

	public ConceptResponse addConcept(Long id, ConceptRequest request) {
		Session session = sqlSessionfactory.getSessionFactory().openSession();
		Transaction transaction = session.getTransaction();

		Concept concept = new Concept(request);
		try {
			transaction.begin();
			Topic topic = (Topic) commonDAO.getEntity(id, null, Topic.class, session);
			topic.addConcept(concept);
			concept.setTopic(topic);
			commonDAO.setEntityDefaultProperties(concept);
			transaction.commit();
		} finally {
			session.close();
		}
		return new ConceptResponse(concept);
	}

	public List<ConceptResponse> getConcepts(Long id) {
		Topic topic = commonDAO.getEntity(id, null, Topic.class);
		Set<Concept> concepts = topic.getConcepts();
		return getConceptList(concepts);
	}

	public ProblemResponse addProblem(Long id, ProblemRequest request) {
		Session session = sqlSessionfactory.getSessionFactory().openSession();
		Transaction transaction = session.getTransaction();

		Problem problem = new Problem(request);
		TopicProblemMapping topicProblemMapping = new TopicProblemMapping(request);
		try {
			transaction.begin();
			Topic topic = (Topic) commonDAO.getEntity(id, null, Topic.class, session);
			commonDAO.saveEntity(problem, session);
//			problem = (Problem) commonDAO.getEntity(id, null, Problem.class, session);
			commonDAO.setEntityDefaultProperties(topicProblemMapping);
			topic.addTopicProblemMapping(topicProblemMapping);
			problem.addTopicProblemMapping(topicProblemMapping);
			topicProblemMapping.setProblem(problem);
			topicProblemMapping.setTopic(topic);
			transaction.commit();
		} finally {
			session.close();
		}
		ProblemResponse response = new ProblemResponse(problem);
		response.setSequenceNo(topicProblemMapping.getSequenceNo());
		return response;
	}

	public List<ProblemResponse> getProblems(Long id) {
		Topic topic = commonDAO.getEntity(id, null, Topic.class);
		Set<TopicProblemMapping> topicProblemMappings = topic.getTopicProblemMappings();
		return getProblemList(topicProblemMappings);
	}

	private List<TopicResponse> getListResponse(List<Topic> topics) {
		List<TopicResponse> responses = new ArrayList<TopicResponse>();
		for (Topic topic : topics) {
			responses.add(new TopicResponse(topic));
		}
		return responses;
	}

	private List<ConceptResponse> getConceptList(Set<Concept> concepts) {
		List<ConceptResponse> responses = new ArrayList<ConceptResponse>();
		for (Concept concept : concepts) {
			responses.add(new ConceptResponse(concept));
		}
		return responses;
	}

	private List<ProblemResponse> getProblemList(Set<TopicProblemMapping> topicProblemMappings) {
		List<ProblemResponse> responses = new ArrayList<ProblemResponse>();
		for(TopicProblemMapping topicProblemMapping : topicProblemMappings) {
			ProblemResponse response = new ProblemResponse(topicProblemMapping.getProblem());
			response.setSequenceNo(topicProblemMapping.getSequenceNo());
			responses.add(response);
		}
		return responses;
	}

}
