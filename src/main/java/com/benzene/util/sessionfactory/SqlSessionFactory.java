package com.benzene.util.sessionfactory;

import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.benzene.platform.entity.Branch;
import com.benzene.platform.entity.Chapter;
import com.benzene.platform.entity.Concept;
import com.benzene.platform.entity.Problem;
import com.benzene.platform.entity.Subject;
import com.benzene.platform.entity.Topic;
import com.benzene.platform.entity.TopicProblemMapping;
import com.benzene.util.ConfigUtils;
import com.benzene.util.LogFactory;

@Service
public class SqlSessionFactory {

	@Autowired
	private ConfigUtils configUtils;

	private Logger logger = LogFactory.getLogger(SqlSessionFactory.class);

	private SessionFactory sessionFactory = null;

	public SqlSessionFactory() {
		logger.info("initializing Sql Session Factory");
		configUtils = new ConfigUtils();

		try {
			Configuration configuration = new Configuration();
			String path = "ENV-" + configUtils.properties.getProperty("environment") + java.io.File.separator
					+ "hibernate.cfg.xml";
			configuration.configure(path);
			configuration.addAnnotatedClass(Subject.class);
			configuration.addAnnotatedClass(Branch.class);
			configuration.addAnnotatedClass(Chapter.class);
			configuration.addAnnotatedClass(Topic.class);
			configuration.addAnnotatedClass(Concept.class);
			configuration.addAnnotatedClass(Problem.class);
			configuration.addAnnotatedClass(TopicProblemMapping.class);
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @return the session
	 */
	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

}
