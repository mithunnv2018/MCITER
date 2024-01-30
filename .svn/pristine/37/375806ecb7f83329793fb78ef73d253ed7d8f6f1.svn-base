package com.mciter.commonbeans;

// Generated 31 Aug, 2012 5:51:31 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class TblTypingtestMaster.
 * @see com.mciter.commonbeans.TblTypingtestMaster
 * @author Hibernate Tools
 */
public class TblTypingtestMasterHome {

	private static final Log log = LogFactory
			.getLog(TblTypingtestMasterHome.class);

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

	public void persist(TblTypingtestMaster transientInstance) {
		log.debug("persisting TblTypingtestMaster instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TblTypingtestMaster instance) {
		log.debug("attaching dirty TblTypingtestMaster instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TblTypingtestMaster instance) {
		log.debug("attaching clean TblTypingtestMaster instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TblTypingtestMaster persistentInstance) {
		log.debug("deleting TblTypingtestMaster instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TblTypingtestMaster merge(TblTypingtestMaster detachedInstance) {
		log.debug("merging TblTypingtestMaster instance");
		try {
			TblTypingtestMaster result = (TblTypingtestMaster) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TblTypingtestMaster findById(java.lang.Integer id) {
		log.debug("getting TblTypingtestMaster instance with id: " + id);
		try {
			TblTypingtestMaster instance = (TblTypingtestMaster) sessionFactory
					.getCurrentSession().get(
							"com.mciter.commonbeans.TblTypingtestMaster", id);
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

	public List<TblTypingtestMaster> findByExample(TblTypingtestMaster instance) {
		log.debug("finding TblTypingtestMaster instance by example");
		try {
			List<TblTypingtestMaster> results = (List<TblTypingtestMaster>) sessionFactory
					.getCurrentSession()
					.createCriteria(
							"com.mciter.commonbeans.TblTypingtestMaster")
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
