package ir.smartplanning.server;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.google.inject.Injector;
import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.shared.ServiceLocator;

public class DaoServiceLocator implements ServiceLocator {

	@Override
	public Object getInstance(Class<?> clazz) {
		HttpServletRequest request = RequestFactoryServlet
				.getThreadLocalRequest();
		ServletContext context = request.getSession().getServletContext();
		Injector injector = (Injector) context.getAttribute(Injector.class
				.getName());
		if (injector == null) {
			throw new IllegalStateException("injector is null.");
		}
		return injector.getInstance(clazz);
	}

}
