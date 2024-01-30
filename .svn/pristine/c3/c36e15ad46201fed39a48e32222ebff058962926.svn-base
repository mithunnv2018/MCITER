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
 * Home object for domain model class TblCategoryMaster.
 * @see com.mciter.commonbeans.TblCategoryMaster
 * @author Hibernate Tools
 */
public class TblCategoryMasterHome {

	private static final Log log = LogFactory
			.getLog(TblCategoryMasterHome.class);

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

	public void persist(TblCategoryMaster transientInstance) {
		log.debug("persisting TblCategoryMaster instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TblCategoryMaster instance) {
		log.debug("attaching dirty TblCategoryMaster instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TblCategoryMaster instance) {
		log.debug("attaching clean TblCategoryMaster instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TblCategoryMaster persistentInstance) {
		log.debug("deleting TblCategoryMaster instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TblCategoryMaster merge(TblCategoryMaster detachedInstance) {
		log.debug("merging TblCategoryMaster instance");
		try {
			TblCategoryMaster result = (TblCategoryMaster) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TblCategoryMaster findById(java.lang.Integer id) {
		log.debug("getting TblCategoryMaster instance with id: " + id);
		try {
			TblCategoryMaster instance = (TblCategoryMaster) sessionFactory
					.getCurrentSession().get(
							"com.mciter.commonbeans.TblCategoryMaster", id);
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

	public List<TblCategoryMaster> findByExample(TblCategoryMaster instance) {
		log.debug("finding TblCategoryMaster instance by example");
		try {
			List<TblCategoryMaster> results = (List<TblCategoryMaster>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.mciter.commonbeans.TblCategoryMaster")
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
