package ir.smartplanning.server;

import ir.smartplanning.server.dao.impl.HibernateUtil;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class TransactionInterceptor implements MethodInterceptor{
	private final Provider<HibernateUtil> hibernateUtilProvider;

	@Inject
	public TransactionInterceptor(Provider<HibernateUtil> hibernateUtilProvider) {
		this.hibernateUtilProvider = hibernateUtilProvider;
	}

	@Override
	public Object invoke(MethodInvocation arg0) throws Throwable {
		hibernateUtilProvider.get().beginTransaction();
		Object result = null;
		try {
			result = arg0.proceed();
			hibernateUtilProvider.get().commitTransaction();
		} catch (Exception ex) {
			ex.getMessage();
			hibernateUtilProvider.get().rollbackTransaction();
			throw ex;
		}
		return result;
	}

}
