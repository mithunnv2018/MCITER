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
 * Home object for domain model class TblPapermainqDetails.
 * @see com.mciter.commonbeans.TblPapermainqDetails
 * @author Hibernate Tools
 */
public class TblPapermainqDetailsHome {

	private static final Log log = LogFactory
			.getLog(TblPapermainqDetailsHome.class);

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

	public void persist(TblPapermainqDetails transientInstance) {
		log.debug("persisting TblPapermainqDetails instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TblPapermainqDetails instance) {
		log.debug("attaching dirty TblPapermainqDetails instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TblPapermainqDetails instance) {
		log.debug("attaching clean TblPapermainqDetails instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TblPapermainqDetails persistentInstance) {
		log.debug("deleting TblPapermainqDetails instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TblPapermainqDetails merge(TblPapermainqDetails detachedInstance) {
		log.debug("merging TblPapermainqDetails instance");
		try {
			TblPapermainqDetails result = (TblPapermainqDetails) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TblPapermainqDetails findById(java.lang.Integer id) {
		log.debug("getting TblPapermainqDetails instance with id: " + id);
		try {
			TblPapermainqDetails instance = (TblPapermainqDetails) sessionFactory
					.getCurrentSession().get(
							"com.mciter.commonbeans.TblPapermainqDetails", id);
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

	public List<TblPapermainqDetails> findByExample(
			TblPapermainqDetails instance) {
		log.debug("finding TblPapermainqDetails instance by example");
		try {
			List<TblPapermainqDetails> results = (List<TblPapermainqDetails>) sessionFactory
					.getCurrentSession()
					.createCriteria(
							"com.mciter.commonbeans.TblPapermainqDetails")
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
