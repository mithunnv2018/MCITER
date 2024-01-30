package com.mciter.commonbeans;

// Generated 27 Sep, 2012 9:32:54 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class TblDemousers.
 * @see com.mciter.commonbeans.TblDemousers
 * @author Hibernate Tools
 */
public class TblDemousersHome {

	private static final Log log = LogFactory.getLog(TblDemousersHome.class);

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

	public void persist(TblDemousers transientInstance) {
		log.debug("persisting TblDemousers instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TblDemousers instance) {
		log.debug("attaching dirty TblDemousers instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TblDemousers instance) {
		log.debug("attaching clean TblDemousers instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TblDemousers persistentInstance) {
		log.debug("deleting TblDemousers instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TblDemousers merge(TblDemousers detachedInstance) {
		log.debug("merging TblDemousers instance");
		try {
			TblDemousers result = (TblDemousers) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TblDemousers findById(java.lang.Integer id) {
		log.debug("getting TblDemousers instance with id: " + id);
		try {
			TblDemousers instance = (TblDemousers) sessionFactory
					.getCurrentSession().get(
							"com.mciter.commonbeans.TblDemousers", id);
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

	public List<TblDemousers> findByExample(TblDemousers instance) {
		log.debug("finding TblDemousers instance by example");
		try {
			List<TblDemousers> results = (List<TblDemousers>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.mciter.commonbeans.TblDemousers")
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
