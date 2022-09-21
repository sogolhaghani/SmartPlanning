package ir.smartplanning.server.guice;

import com.gwtplatform.dispatch.server.guice.HandlerModule;

public class ServerModule  extends HandlerModule{

	@Override
	protected void configureHandlers() {
		install(new ServiceModule());
		install(new DaoModule());
		
	}

}
