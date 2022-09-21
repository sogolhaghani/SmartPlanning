package ir.smartplanning.server.guice;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.servlet.ServletContextEvent;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class GuiceServletConfig extends GuiceServletContextListener {

	public static final String KEY = Injector.class.getName();
	public static final String THREAD_POOL = "thread_pool";
	private ScheduledExecutorService scheduledExecutorService;

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new ServerModule(),
				new DispatchServletModule(), new DaoModule(),
				new ServiceModule());
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		servletContextEvent.getServletContext()
				.setAttribute(KEY, getInjector());
		scheduledExecutorService = Executors.newScheduledThreadPool(32);
		servletContextEvent.getServletContext().setAttribute(THREAD_POOL,
				scheduledExecutorService);
		super.contextInitialized(servletContextEvent);
	}

}
