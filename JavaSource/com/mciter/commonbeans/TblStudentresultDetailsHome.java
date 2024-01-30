package com.mciter.commonbeans;

// Generated 6 Aug, 2012 8:03:33 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class TblStudentresultDetails.
 * @see com.mciter.commonbeans.TblStudentresultDetails
 * @author Hibernate Tools
 */
public class TblStudentresultDetailsHome {

	private static final Log log = LogFactory
			.getLog(TblStudentresultDetailsHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(TblStudentresultDetails transientInstance) {
		log.debug("persisting TblStudentresultDetails instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TblStudentresultDetails instance) {
		log.debug("attaching dirty TblStudentresultDetails instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TblStudentresultDetails instance) {
		log.debug("attaching clean TblStudentresultDetails instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TblStudentresultDetails persistentInstance) {
		log.debug("deleting TblStudentresultDetails instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TblStudentresultDetails merge(
			TblStudentresultDetails detachedInstance) {
		log.debug("merging TblStudentresultDetails instance");
		try {
			TblStudentresultDetails result = (TblStudentresultDetails) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TblStudentresultDetails findById(java.lang.Integer id) {
		log.debug("getting TblStudentresultDetails instance with id: " + id);
		try {
			TblStudentresultDetails instance = (TblStudentresultDetails) sessionFactory
					.getCurrentSession().get(
							"com.mciter.commonbeans.TblStudentresultDetails",
							id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TblStudentresultDetails> findByExample(
			TblStudentresultDetails instance) {
		log.debug("finding TblStudentresultDetails instance by example");
		try {
			List<TblStudentresultDetails> results = (List<TblStudentresultDetails>) sessionFactory
					.getCurrentSession()
					.createCriteria(
							"com.mciter.commonbeans.TblStudentresultDetails")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
