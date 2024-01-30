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
 * Home object for domain model class TblCenterDetails.
 * @see com.mciter.commonbeans.TblCenterDetails
 * @author Hibernate Tools
 */
public class TblCenterDetailsHome {

	private static final Log log = LogFactory
			.getLog(TblCenterDetailsHome.class);

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

	public void persist(TblCenterDetails transientInstance) {
		log.debug("persisting TblCenterDetails instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TblCenterDetails instance) {
		log.debug("attaching dirty TblCenterDetails instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TblCenterDetails instance) {
		log.debug("attaching clean TblCenterDetails instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TblCenterDetails persistentInstance) {
		log.debug("deleting TblCenterDetails instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TblCenterDetails merge(TblCenterDetails detachedInstance) {
		log.debug("merging TblCenterDetails instance");
		try {
			TblCenterDetails result = (TblCenterDetails) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TblCenterDetails findById(java.lang.String id) {
		log.debug("getting TblCenterDetails instance with id: " + id);
		try {
			TblCenterDetails instance = (TblCenterDetails) sessionFactory
					.getCurrentSession().get(
							"com.mciter.commonbeans.TblCenterDetails", id);
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

	public List<TblCenterDetails> findByExample(TblCenterDetails instance) {
		log.debug("finding TblCenterDetails instance by example");
		try {
			List<TblCenterDetails> results = (List<TblCenterDetails>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.mciter.commonbeans.TblCenterDetails")
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
