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
 * Home object for domain model class TblStudentDetails.
 * @see com.mciter.commonbeans.TblStudentDetails
 * @author Hibernate Tools
 */
public class TblStudentDetailsHome {

	private static final Log log = LogFactory
			.getLog(TblStudentDetailsHome.class);

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

	public void persist(TblStudentDetails transientInstance) {
		log.debug("persisting TblStudentDetails instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TblStudentDetails instance) {
		log.debug("attaching dirty TblStudentDetails instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TblStudentDetails instance) {
		log.debug("attaching clean TblStudentDetails instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TblStudentDetails persistentInstance) {
		log.debug("deleting TblStudentDetails instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TblStudentDetails merge(TblStudentDetails detachedInstance) {
		log.debug("merging TblStudentDetails instance");
		try {
			TblStudentDetails result = (TblStudentDetails) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TblStudentDetails findById(java.lang.String id) {
		log.debug("getting TblStudentDetails instance with id: " + id);
		try {
			TblStudentDetails instance = (TblStudentDetails) sessionFactory
					.getCurrentSession().get(
							"com.mciter.commonbeans.TblStudentDetails", id);
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

	public List<TblStudentDetails> findByExample(TblStudentDetails instance) {
		log.debug("finding TblStudentDetails instance by example");
		try {
			List<TblStudentDetails> results = (List<TblStudentDetails>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.mciter.commonbeans.TblStudentDetails")
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
