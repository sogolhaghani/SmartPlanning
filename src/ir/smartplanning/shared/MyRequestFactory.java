package ir.smartplanning.shared;

import ir.smartplanning.server.DaoServiceLocator;
import ir.smartplanning.server.service.ExamService;
import ir.smartplanning.server.service.StudyPeriodService;
import ir.smartplanning.server.service.UserService;
import ir.smartplanning.shared.proxies.StudyPeriodProxy;
import ir.smartplanning.shared.proxies.nonpersists.PlanItemProxy;
import ir.smartplanning.shared.proxies.nonpersists.PreLoadedExamObjectProxy;
import ir.smartplanning.shared.proxies.nonpersists.QuestionAnswerProxy;
import ir.smartplanning.shared.proxies.nonpersists.QuestionProxy;
import ir.smartplanning.shared.proxies.nonpersists.UserItemProxy;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.Service;

public interface MyRequestFactory extends RequestFactory {

	@Service(value = UserService.class, locator = DaoServiceLocator.class)
	public interface UserRequestContext extends RequestContext {
		Request<UserItemProxy> login(String userName,String password);
		Request<UserItemProxy> register(String password,UserItemProxy userItemProxy);
		Request<Boolean> checkUserNameIsUniqe(String userName);
		Request<List<String>> getBookInfo();
	}
	
	@Service(value=StudyPeriodService.class,locator=DaoServiceLocator.class)
	public interface StudyPeriodRequestContext extends RequestContext{
		Request<List<StudyPeriodProxy>> getAllStudyPeriods();

		Request<List<PlanItemProxy>> getWeekPlan(long studyPeriodId);
		
		Request<PlanItemProxy> saveNewPlan(PlanItemProxy planItemProxy);
	}
	
	@Service(value=ExamService.class,locator=DaoServiceLocator.class)
	public interface ExamRequestContext extends RequestContext{
		Request<Void> submitAndLogut(List<QuestionAnswerProxy> questions);
		Request<List<QuestionProxy>>  getNextPart(int start);
		Request<Boolean> submitAnswerAsync(List<QuestionAnswerProxy> questions);
		Request<PreLoadedExamObjectProxy> startExam(Long examId);
		Request<List<QuestionAnswerProxy>> getSubmittedAnswers();
		Request<PreLoadedExamObjectProxy> getNextBooklet(List<QuestionAnswerProxy> questions);
	}

	public UserRequestContext getUserRequestContext();
	public StudyPeriodRequestContext getStudPeriodRequestContext();
	public ExamRequestContext getExamRequestContext();
}
