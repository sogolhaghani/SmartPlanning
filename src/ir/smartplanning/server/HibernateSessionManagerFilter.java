package ir.smartplanning.server;

import ir.smartplanning.server.dao.impl.HibernateUtil;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class HibernateSessionManagerFilter implements Filter {

	private final Provider<HibernateUtil> hibernateUtilProvider;

	@Inject
	public HibernateSessionManagerFilter(
			final Provider<HibernateUtil> hibenateUtilProvider) {
		this.hibernateUtilProvider = hibenateUtilProvider;
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				hibernateUtilProvider.get().closeSession();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
