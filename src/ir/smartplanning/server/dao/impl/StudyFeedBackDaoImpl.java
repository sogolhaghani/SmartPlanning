package ir.smartplanning.server.dao.impl;

import com.google.inject.Inject;
import com.google.inject.Provider;

import ir.smartplanning.server.InfrastructureException;
import ir.smartplanning.server.dao.StudyFeedBackDao;
import ir.smartplanning.server.domain.StudyFeedback;

public class StudyFeedBackDaoImpl extends GenericDaoImpl<StudyFeedback,Long> implements StudyFeedBackDao{

	
	@Inject
	public StudyFeedBackDaoImpl(Provider<HibernateUtil> hibernateUtilProvider)
			throws InfrastructureException {
		super(hibernateUtilProvider);
		// TODO Auto-generated constructor stub
	}

}
