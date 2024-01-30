package com.mciter.commonbeans;

// Generated 1 Sep, 2012 10:42:49 AM by Hibernate Tools 3.4.0.CR1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class TblTypingtestDetails.
 * @see com.mciter.commonbeans.TblTypingtestDetails
 * @author Hibernate Tools
 */
public class TblTypingtestDetailsHome {

	private static final Log log = LogFactory
			.getLog(TblTypingtestDetailsHome.class);

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

	public void persist(TblTypingtestDetails transientInstance) {
		log.debug("persisting TblTypingtestDetails instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TblTypingtestDetails instance) {
		log.debug("attaching dirty TblTypingtestDetails instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TblTypingtestDetails instance) {
		log.debug("attaching clean TblTypingtestDetails instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TblTypingtestDetails persistentInstance) {
		log.debug("deleting TblTypingtestDetails instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TblTypingtestDetails merge(TblTypingtestDetails detachedInstance) {
		log.debug("merging TblTypingtestDetails instance");
		try {
			TblTypingtestDetails result = (TblTypingtestDetails) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TblTypingtestDetails findById(java.lang.Integer id) {
		log.debug("getting TblTypingtestDetails instance with id: " + id);
		try {
			TblTypingtestDetails instance = (TblTypingtestDetails) sessionFactory
					.getCurrentSession().get(
							"com.mciter.commonbeans.TblTypingtestDetails", id);
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

	public List<TblTypingtestDetails> findByExample(
			TblTypingtestDetails instance) {
		log.debug("finding TblTypingtestDetails instance by example");
		try {
			List<TblTypingtestDetails> results = (List<TblTypingtestDetails>) sessionFactory
					.getCurrentSession()
					.createCriteria(
							"com.mciter.commonbeans.TblTypingtestDetails")
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
