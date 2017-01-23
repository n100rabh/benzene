package com.benzene.util.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.benzene.platform.request.GetAbstractReq;
import com.benzene.util.LogFactory;
import com.benzene.util.entity.AbstractEntity;
import com.benzene.util.enums.State;
import com.benzene.util.sessionfactory.SqlSessionFactory;

@Repository
public class CommonDAO {

	@Autowired
	private LogFactory logfactory;

	@Autowired
	private SqlSessionFactory sqlSessionfactory;

	@SuppressWarnings("static-access")
	private Logger logger = logfactory.getLogger(CommonDAO.class);

	public CommonDAO() {
		super();
	}

	public <E extends AbstractEntity> void saveEntity(E entity) {
		SessionFactory sessionFactory = sqlSessionfactory.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			saveEntity(entity, session);
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public <E extends AbstractEntity> void saveEntity(E entity, Session session) {
		setEntityDefaultProperties(entity);
		session.save(entity);
	}

	public <E extends AbstractEntity> void saveEntities(Collection<E> entities, String collectionName) {
		SessionFactory sessionFactory = sqlSessionfactory.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.getTransaction();

		try {
			transaction.begin();
			saveEntities(entities, collectionName, session);
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public <E extends AbstractEntity> void saveEntities(Collection<E> entities, String collectionName,
			Session session) {
		setEntityDefaultProperties(entities);
		if (entities != null && !entities.isEmpty()) {
			for (E entity : entities) {
				session.save(entity);
			}
		}
	}

	public <T extends AbstractEntity> T getEntity(Long id, State state, Class<T> calzz) {
		SessionFactory sessionFactory = sqlSessionfactory.getSessionFactory();
		Session session = sessionFactory.openSession();
		T result = null;
		try {
			result = getEntity(id, state, calzz, session);
		} catch (Exception e) {
			logger.error("getEntityById Error: " + e.getMessage());
			throw e;
		} finally {
			session.close();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractEntity> T getEntity(Long id, State state, Class<T> calzz, Session session) {
		List<T> results = null;
		Criteria cr = session.createCriteria(calzz);
		cr.add(Restrictions.eq("id", id));
		if (state != null) {
			cr.add(Restrictions.eq("state", state));
		}
		results = cr.list();
		if (results != null && !results.isEmpty()) {
			return results.get(0);
		}
		return null;
	}

	public <T extends AbstractEntity> List<T> getEntities(Class<T> calzz, GetAbstractReq req, State state) {
		SessionFactory sessionFactory = sqlSessionfactory.getSessionFactory();
		Session session = sessionFactory.openSession();
		List<T> results = null;
		try {
			results = getEntities(calzz, req, state, session);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractEntity> List<T> getEntities(Class<T> calzz, GetAbstractReq req, State state,
			Session session) {
		List<T> results = null;
		Criteria cr = session.createCriteria(calzz);
		if (state != null) {
			cr.add(Restrictions.eq("state", state));
		}
		Integer start = req.getStart();
		Integer size = req.getSize();
		if (start == null || start < 0) {
			start = 0;
		}
		if (size == null || size <= 0) {
			size = 20;
		}
		cr.setFirstResult(start);
		cr.setMaxResults(size);
		results = cr.list();
		return results;
	}

	public <E extends AbstractEntity> void updateEntity(E entity) {
		SessionFactory sessionFactory = sqlSessionfactory.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			updateEntity(entity, session);
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public <E extends AbstractEntity> void updateEntity(E entity, Session session) {
		setEntityDefaultProperties(entity);
		session.update(entity);
	}

	public <E extends AbstractEntity> void updateEntities(Collection<E> entities, String collectionName) {
		SessionFactory sessionFactory = sqlSessionfactory.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			updateEntities(entities, collectionName, session);
			transaction.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public <E extends AbstractEntity> void updateEntities(Collection<E> entities, String collectionName,
			Session session) {
		setEntityDefaultProperties(entities);
		if (entities != null && !entities.isEmpty()) {
			for (E entity : entities) {
				session.saveOrUpdate(entity);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractEntity> List<T> runQuery(Session session, Criteria cr, Class<T> calzz) {
		List<T> results = null;
		results = cr.list();
		return results;
	}

	public <T extends AbstractEntity> Long queryCount(Class<T> calzz) {
		SessionFactory sessionFactory = sqlSessionfactory.getSessionFactory();
		Session session = sessionFactory.openSession();
		Long count;
		try {
			count = queryCount(calzz, session);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return count;
	}

	public <T extends AbstractEntity> Long queryCount(Class<T> calzz, Session session) {
		Long count;
		Criteria cr = session.createCriteria(calzz);
		count = (Long) cr.setProjection(Projections.rowCount()).uniqueResult();
		return count;
	}

	public <E extends AbstractEntity> void setEntityDefaultProperties(Collection<E> entities) {
		if (entities != null && !entities.isEmpty()) {
			for (E entity : entities) {
				setEntityDefaultProperties(entity);
			}
		}
	}

	public <E extends AbstractEntity> void setEntityDefaultProperties(E entity) {
		if (entity.getId() == null) {
			entity.setState(State.ACTIVE);
			entity.setCreationDate(new Date());
			entity.setCreatedBy(entity.getCreatedBy());
			entity.setLastUpdatedBy(entity.getLastUpdatedBy());
			entity.setLastUpdatedDate(new Date());
		} else {
			entity.setLastUpdatedBy(entity.getLastUpdatedBy());
			entity.setLastUpdatedDate(new Date());
		}
	}
}
