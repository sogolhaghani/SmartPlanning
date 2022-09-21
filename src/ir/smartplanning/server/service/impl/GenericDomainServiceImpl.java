package ir.smartplanning.server.service.impl;

import ir.smartplanning.server.InfrastructureException;
import ir.smartplanning.server.dao.GenericDao;
import ir.smartplanning.server.dao.impl.HibernateUtil;
import ir.smartplanning.server.service.GenericDomainService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;

public class GenericDomainServiceImpl<T extends GenericDao<?, ?>> implements GenericDomainService<T> {

	protected final T dao;
	
	
	@Inject
	public GenericDomainServiceImpl(T clazz, Provider<HibernateUtil> hibernateUtilProvider) throws InfrastructureException {
		this.dao = clazz;
	}
	
	public HttpSession getHttpSession() {
		HttpServletRequest request = RequestFactoryServlet.getThreadLocalRequest();
		return request.getSession();

	}
	
}
