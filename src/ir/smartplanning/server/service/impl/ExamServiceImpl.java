package ir.smartplanning.server.service.impl;

import ir.smartplanning.server.InfrastructureException;
import ir.smartplanning.server.dao.ExamDao;
import ir.smartplanning.server.dao.impl.HibernateUtil;
import ir.smartplanning.server.domain.nonpersist.PreLoadedExamObject;
import ir.smartplanning.server.domain.nonpersist.Question;
import ir.smartplanning.server.domain.nonpersist.QuestionAnswer;
import ir.smartplanning.server.service.ExamService;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ExamServiceImpl extends GenericDomainServiceImpl<ExamDao> implements ExamService{

	@Inject
	public ExamServiceImpl(ExamDao clazz,
			Provider<HibernateUtil> hibernateUtilProvider)
			throws InfrastructureException {
		super(clazz, hibernateUtilProvider);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Question> getNextPart(int start) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void submitAndLogut(List<QuestionAnswer> questions) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean submitAnswerAsync(List<QuestionAnswer> questions) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PreLoadedExamObject startExam(Long examId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QuestionAnswer> getSubmittedAnswers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreLoadedExamObject getNextBooklet(List<QuestionAnswer> questions) {
		// TODO Auto-generated method stub
		return null;
	}

}
