package ir.smartplanning.server.dao.impl;

import com.google.inject.Inject;
import com.google.inject.Provider;

import ir.smartplanning.server.InfrastructureException;
import ir.smartplanning.server.domain.Exam;
import ir.smartplanning.server.dao.ExamDao;

public class ExamDaoImpl extends GenericDaoImpl<Exam, Long> implements ExamDao{

	
	@Inject
	public ExamDaoImpl(Provider<HibernateUtil> hibernateUtilProvider)
			throws InfrastructureException {
		super(hibernateUtilProvider);
		// TODO Auto-generated constructor stub
	}

}
