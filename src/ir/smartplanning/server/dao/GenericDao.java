package ir.smartplanning.server.dao;

import ir.smartplanning.server.InfrastructureException;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

public interface GenericDao<T, ID extends Serializable> {
	T findById(ID id);
	Session getSession() throws InfrastructureException;
	List<T> findAll();
	List<T> findByExample(T exampleInstance);
	T makePersistent(T entity);
	Class<T> makeTransient(T entity);
	public void closeSession() throws InfrastructureException;
	public void beginTransaction() throws InfrastructureException;
	public void commitTransaction() throws InfrastructureException;
	public void rollbackTransaction() throws InfrastructureException;

}
