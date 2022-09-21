package ir.smartplanning.server.dao.impl;

import ir.smartplanning.server.AuditInterceptor;
import ir.smartplanning.server.InfrastructureException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.hibernate.EmptyInterceptor;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	/** configuration. */
	private Configuration configuration;

	/** Session Factory */
	private SessionFactory sessionFactory;

	/** JNDI name of sessionfactory. */
	private final String JNDI_SESSIONFACTORY = "java:hibernate/HibernateFactory";

	/** If running unit tests set to true. */
	private boolean offlineMode = true;

	/** threadlocal. */
	private  static ThreadLocal<Session> threadSession = new ThreadLocal<Session>();

	/** threadlocal. */
	private  static ThreadLocal<Transaction> threadTransaction = new ThreadLocal<Transaction>();

	/** threadlocal. */
	private Interceptor interceptor;

	/** Interceptor class */
	private final String INTERCEPTOR_CLASS = "hibernate.util.interceptor_class";

	/**
	 * Create the initial SessionFactory from hibernate.xml.cfg or JNDI). ####
	 * Use this Function to initialize Hibernate! ####
	 * 
	 * @param offlineMode
	 *            true=hibernate.cfg.xml , false=JNDI
	 */
	public void Configure(boolean offlineMode) {
		try {
			setOfflineMode(offlineMode);
			sessionFactory = getSessionFactory();

		} catch (Throwable x) {
			// We have to catch Throwable, otherwise we will miss
			// NoClassDefFoundError and other subclasses of Error
			throw new ExceptionInInitializerError(x);
		}
	}

	/**
	 * Use hibernate.cfg.xml (true) to create sessionfactory or bound
	 * sessionfactory to JNDI (false)
	 */
	public void setOfflineMode(boolean mode) {
		if (mode == false)
			offlineMode = mode;
	}

	/**
	 * Returns the SessionFactory used for this class. If offlineMode has been
	 * set then we use hibernate.cfg.xml to create sessionfactory, if not then
	 * we use sessionfactory bound to JNDI.
	 * 
	 * @return SessionFactory
	 * @throws InfrastructureException
	 */

	public SessionFactory getSessionFactory() throws InfrastructureException {
		if (sessionFactory == null) {
			if (offlineMode == true) {
				try {
					configuration = new Configuration().configure()
							.setInterceptor(new AuditInterceptor());
					StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder()
							.applySettings(configuration.getProperties());
					sessionFactory = configuration
							.buildSessionFactory(serviceRegistryBuilder.build());
				} catch (HibernateException x) {
					throw new InfrastructureException(
							"HibernateUtil.getSessionFactory() - Error creating SessionFactory with hibernate.cfg.xml .",
							x);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				try {
					Context ctx = new InitialContext();
					sessionFactory = (SessionFactory) ctx
							.lookup(JNDI_SESSIONFACTORY);
				} catch (NamingException x) {
					throw new InfrastructureException(
							"HibernateUtil.getSessionFactory() - Error creating JNDI-SessionFactory.",
							x);
				}
			}
		}

		if (sessionFactory == null) {
			throw new IllegalStateException(
					"HibernateUtil.getSessionFactory() - SessionFactory not available.");
		}
		return sessionFactory;
	}

	/**
	 * Sets the given SessionFactory.
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Returns the original Hibernate configuration.
	 * 
	 * @return Configuration
	 */
	public Configuration getConfiguration() {
		return configuration;
	}

	/**
	 * Rebuild the SessionFactory with the Configuration.
	 * 
	 */
	/*
	 * public void rebuildSessionFactory() throws InfrastructureException {
	 * CommonLogger.log(log, Level.DEBUG, (
	 * "HibernateUtil.rebuildSessionFactory() - Rebuilding the SessionFactory with the  Configuration."
	 * )); synchronized (sessionFactory) { try { sessionFactory =
	 * getConfiguration().buildSessionFactory(); } catch (Exception x) { throw
	 * new InfrastructureException(
	 * "HibernateUtil.rebuildSessionFactory() - Error rebuilding SessionFactory with the  Configuration"
	 * , x); } } }
	 */

	/**
	 * Destroy this SessionFactory and release all resources (caches, connection
	 * pools, etc).
	 * 
	 * @author Siegfried Bolz
	 * @param cfg
	 */
	public void closeSessionFactory() throws InfrastructureException {
		synchronized (sessionFactory) {
			try {
				sessionFactory.close();
				configuration = null;
				sessionFactory = null;
			} catch (Exception x) {
				throw new InfrastructureException(
						"HibernateUtil.closeSessionFactory() - Error destroying the current SessionFactory",
						x);
			}
		}
	}

	/**
	 * Retrieves the current Session local to the thread.
	 * <p/>
	 * 
	 * If no Session is open, opens a new Session for the running thread.
	 * 
	 * @return Session
	 */
	public Session getSession() {

		Session s = threadSession.get();
		try {
			if (s == null) {
				if (this.interceptor != null) {
					s = getSessionFactory().openSession();// (this.interceptor);
				} else {
					s = getSessionFactory().openSession();
				}
				threadSession.set(s);
			}
		} catch (HibernateException x) {
			x.printStackTrace();
		} catch (InfrastructureException e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * Closes the Session local to the thread.
	 */
	public void closeSession() {
		try {
			Session s = threadSession.get();
			threadSession.set(null);
			if (s != null && s.isOpen()) {
				s.close();
				s = null;

			}
		} catch (HibernateException x) {
			x.printStackTrace();
		}
	}

	/**
	 * Start a new database transaction.
	 */
	public void beginTransaction() throws InfrastructureException {
		Transaction tx = threadTransaction.get();
		try {
			if (tx == null) {
				tx = getSession().beginTransaction();
				threadTransaction.set(tx);
			}
		} catch (HibernateException x) {
			throw new InfrastructureException(
					"HibernateUtil.beginTransaction() - Error starting a new database transaction.",
					x);
		}
	}

	/**
	 * Commit the database transaction. UserByObserv
	 */
	public void commitTransaction() throws InfrastructureException {
		Transaction tx = threadTransaction.get();
		try {
			if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
				tx.commit();
			}
			threadTransaction.set(null);
		} catch (HibernateException x) {
			rollbackTransaction();
			throw new InfrastructureException(
					"HibernateUtil.commitTransaction() - Error commiting the database transaction",
					x);
		}
	}

	/**
	 * Rollback the database transaction.
	 */
	public void rollbackTransaction() throws InfrastructureException {
		Transaction tx = threadTransaction.get();
		try {
			threadTransaction.set(null);
			if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
				tx.rollback();
			}
		} catch (HibernateException x) {
			throw new InfrastructureException(
					"HibernateUtil.commitTransaction() - Error commiting the database transaction",
					x);
		} finally {
			closeSession();
		}
	}

	/**
	 * Disconnect and return Session from current Thread.
	 * 
	 * @return Session the disconnected Session
	 */
	public Session disconnectSession() throws InfrastructureException {
		Session session = getSession();
		try {
			threadSession.set(null);
			if (session.isConnected() && session.isOpen()) {
				session.disconnect();
			}
		} catch (HibernateException x) {
			throw new InfrastructureException(
					"HibernateUtil.disconnectSession() - Error disconnecting session from current thread.",
					x);
		}
		return session;
	}

	/**
	 * Register a Hibernate interceptor with the current thread.
	 * 
	 * Every Session opened is opened with this interceptor after registration.
	 * Has no effect if the current Session of the thread is already open,
	 * effective on next close()/getSession().
	 */
	public void registerInterceptor(Interceptor interceptor) {
		this.interceptor = interceptor;
	}

	/**
	 * Get Hibernate interceptor.
	 * 
	 * @return Interceptor
	 */

	/**
	 * Resets global interceptor to default state.
	 */
	public void resetInterceptor() {
		setInterceptor(configuration, null);
	}

	/**
	 * Either sets the given intercepter on the configuration or looks it up
	 * from configuration if null.
	 */
	@SuppressWarnings("rawtypes")
	private void setInterceptor(Configuration configuration,
			Interceptor interceptor) {
		String interceptorName = configuration.getProperty(INTERCEPTOR_CLASS);
		if (interceptor == null && interceptorName != null) {
			try {
				Class interceptorClass = HibernateUtil.class.getClassLoader()
						.loadClass(interceptorName);
				interceptor = (Interceptor) interceptorClass.newInstance();
			} catch (Exception x) {
				throw new RuntimeException(
						"HibernateUtil.setInterceptor() - Error, could not configure interceptor: "
								+ interceptorName, x);
			}
		}
		if (interceptor != null) {
			configuration.setInterceptor(interceptor);
		} else {
			configuration.setInterceptor(EmptyInterceptor.INSTANCE);
		}
	}

}
