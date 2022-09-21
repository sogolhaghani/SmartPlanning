package ir.smartplanning.server.dao.impl;

import ir.smartplanning.server.InfrastructureException;
import ir.smartplanning.server.dao.GenericDao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;

import com.google.inject.Inject;
import com.google.inject.Provider;

public abstract class GenericDaoImpl<T, Id extends Serializable> implements
		GenericDao<T, Id> {

	private Class<T> persistentClass;
	private final Provider<HibernateUtil> hibernateUtilProvider;

	@Inject
	@SuppressWarnings("unchecked")
	public GenericDaoImpl(Provider<HibernateUtil> hibernateUtilProvider)
			throws InfrastructureException {
		this.hibernateUtilProvider = hibernateUtilProvider;
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findById(Id id) {
		T entity = null;
		try {
			entity = (T) getSession().get(getPersistentClass(), id);
		} catch (InfrastructureException e) {
			e.printStackTrace();
		}
		return entity;
	}

	private Class<T> getPersistentClass() {
		return persistentClass;
	}

	@Override
	public Session getSession() throws InfrastructureException {
		return hibernateUtilProvider.get().getSession();
	}

	@Override
	public List<T> findAll() {
		return findByCriteria();
	}

	@SuppressWarnings("unchecked")
	private List<T> findByCriteria(Criterion... criterion) {
		Criteria crit = null;
		try {
			crit = getSession().createCriteria(getPersistentClass());
		} catch (InfrastructureException e) {
			e.printStackTrace();
		}
		for (Criterion c : criterion) {
			crit.add(c);
		}
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByExample(T exampleInstance) {
		Criteria crit = null;
		try {
			crit = getSession().createCriteria(getPersistentClass());
		} catch (InfrastructureException e) {
			e.printStackTrace();
		}
		Example example = Example.create(exampleInstance);
		crit.add(example);
		return crit.list();
	}

	@Override
	public T makePersistent(T entity) {
		try {
			beginTransaction();
			getSession().saveOrUpdate(entity);
			commitTransaction();
		} catch (InfrastructureException e) {
			e.printStackTrace();
			try {
				rollbackTransaction();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return entity;
	}

	@Override
	public Class<T> makeTransient(T entity) {
		return persistentClass;
	}

	@Override
	public void closeSession() throws InfrastructureException {
		this.hibernateUtilProvider.get().closeSession();

	}

	@Override
	public void beginTransaction() throws InfrastructureException {
		this.hibernateUtilProvider.get().beginTransaction();
	}

	@Override
	public void commitTransaction() throws InfrastructureException {
		this.hibernateUtilProvider.get().commitTransaction();
	}
	
	@Override
	public void rollbackTransaction() throws InfrastructureException {
		this.hibernateUtilProvider.get().rollbackTransaction();
	}
	
	public void flush() {
		try {
			getSession().flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (InfrastructureException e) {
			e.printStackTrace();
		}
	}

	public void clear() {
		try {
			getSession().clear();
		} catch (InfrastructureException e) {
			e.printStackTrace();
		}
	}

}
