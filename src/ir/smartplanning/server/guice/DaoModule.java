package ir.smartplanning.server.guice;

import ir.smartplanning.server.dao.ExamDao;
import ir.smartplanning.server.dao.StudyFeedBackDao;
import ir.smartplanning.server.dao.StudyFeedbackTopicsDao;
import ir.smartplanning.server.dao.StudyPeriodDao;
import ir.smartplanning.server.dao.UserDao;
import ir.smartplanning.server.dao.impl.ExamDaoImpl;
import ir.smartplanning.server.dao.impl.StudyFeedBackDaoImpl;
import ir.smartplanning.server.dao.impl.StudyFeedbackTopicsDaoImpl;
import ir.smartplanning.server.dao.impl.StudyPeriodDaoImpl;
import ir.smartplanning.server.dao.impl.UserDaoImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class DaoModule extends AbstractModule{

	@Override
	protected void configure() {
	
		bind(UserDao.class).to(UserDaoImpl.class).in(Singleton.class);
		bind(StudyPeriodDao.class).to(StudyPeriodDaoImpl.class).in(Singleton.class);
		bind(ExamDao.class).to(ExamDaoImpl.class).in(Singleton.class);
		bind(StudyFeedBackDao.class).to(StudyFeedBackDaoImpl.class).in(Singleton.class);
		bind(StudyFeedbackTopicsDao.class).to(StudyFeedbackTopicsDaoImpl.class).in(Singleton.class);
	}

}
