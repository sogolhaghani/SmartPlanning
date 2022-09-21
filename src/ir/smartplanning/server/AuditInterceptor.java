package ir.smartplanning.server;

import org.hibernate.EmptyInterceptor;

import com.google.inject.Inject;

public class AuditInterceptor extends EmptyInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6983613917997240760L;
	
	
	@Inject
	public AuditInterceptor() {
		// TODO Auto-generated constructor stub
	}

}
