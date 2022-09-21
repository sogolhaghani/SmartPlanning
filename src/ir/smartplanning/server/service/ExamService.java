package ir.smartplanning.server.service;

import ir.smartplanning.server.domain.nonpersist.PreLoadedExamObject;
import ir.smartplanning.server.domain.nonpersist.Question;
import ir.smartplanning.server.domain.nonpersist.QuestionAnswer;

import java.util.List;

public interface ExamService {

	public List<Question>  getNextPart(int start);
	public void submitAndLogut(List<QuestionAnswer> questions);
	public boolean submitAnswerAsync(List<QuestionAnswer> questions);
	public  PreLoadedExamObject startExam(Long examId);
	public List<QuestionAnswer>  getSubmittedAnswers();
	public PreLoadedExamObject getNextBooklet(List<QuestionAnswer> questions);
}
