package ir.smartplanning.server.dao.impl;

import com.google.inject.Inject;
import com.google.inject.Provider;

import ir.smartplanning.server.InfrastructureException;
import ir.smartplanning.server.dao.StudyFeedbackTopicsDao;
import ir.smartplanning.server.domain.StudyFeedbackTopics;

public class StudyFeedbackTopicsDaoImpl extends GenericDaoImpl<StudyFeedbackTopics, Long> implements StudyFeedbackTopicsDao{

	@Inject
	public StudyFeedbackTopicsDaoImpl(
			Provider<HibernateUtil> hibernateUtilProvider)
			throws InfrastructureException {
		super(hibernateUtilProvider);
		// TODO Auto-generated constructor stub
	}

}
