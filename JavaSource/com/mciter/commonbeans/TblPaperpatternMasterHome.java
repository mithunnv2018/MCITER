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
 * Home object for domain model class TblPaperpatternMaster.
 * @see com.mciter.commonbeans.TblPaperpatternMaster
 * @author Hibernate Tools
 */
public class TblPaperpatternMasterHome {

	private static final Log log = LogFactory
			.getLog(TblPaperpatternMasterHome.class);

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

	public void persist(TblPaperpatternMaster transientInstance) {
		log.debug("persisting TblPaperpatternMaster instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TblPaperpatternMaster instance) {
		log.debug("attaching dirty TblPaperpatternMaster instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TblPaperpatternMaster instance) {
		log.debug("attaching clean TblPaperpatternMaster instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TblPaperpatternMaster persistentInstance) {
		log.debug("deleting TblPaperpatternMaster instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TblPaperpatternMaster merge(TblPaperpatternMaster detachedInstance) {
		log.debug("merging TblPaperpatternMaster instance");
		try {
			TblPaperpatternMaster result = (TblPaperpatternMaster) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TblPaperpatternMaster findById(java.lang.Integer id) {
		log.debug("getting TblPaperpatternMaster instance with id: " + id);
		try {
			TblPaperpatternMaster instance = (TblPaperpatternMaster) sessionFactory
					.getCurrentSession().get(
							"com.mciter.commonbeans.TblPaperpatternMaster", id);
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

	public List<TblPaperpatternMaster> findByExample(
			TblPaperpatternMaster instance) {
		log.debug("finding TblPaperpatternMaster instance by example");
		try {
			List<TblPaperpatternMaster> results = (List<TblPaperpatternMaster>) sessionFactory
					.getCurrentSession()
					.createCriteria(
							"com.mciter.commonbeans.TblPaperpatternMaster")
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
