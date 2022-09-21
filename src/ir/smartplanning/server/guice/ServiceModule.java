package ir.smartplanning.server.guice;

import ir.smartplanning.server.service.ExamService;
import ir.smartplanning.server.service.StudyPeriodService;
import ir.smartplanning.server.service.UserService;
import ir.smartplanning.server.service.impl.ExamServiceImpl;
import ir.smartplanning.server.service.impl.StudyPeriodSeriveImpl;
import ir.smartplanning.server.service.impl.UserServiceImpl;

import com.google.inject.AbstractModule;

public class ServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(UserService.class).to(UserServiceImpl.class);
		bind(StudyPeriodService.class).to(StudyPeriodSeriveImpl.class);
		bind(ExamService.class).to(ExamServiceImpl.class);
	}

}
