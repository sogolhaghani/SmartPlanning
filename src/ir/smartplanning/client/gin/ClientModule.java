package ir.smartplanning.client.gin;

import ir.smartplanning.client.place.NameTokens;
import ir.smartplanning.client.presenter.ConsultationPresenter;
import ir.smartplanning.client.presenter.ConsultationView;
import ir.smartplanning.client.presenter.ExamResultPresenter;
import ir.smartplanning.client.presenter.ExamResultView;
import ir.smartplanning.client.presenter.HomePresenter;
import ir.smartplanning.client.presenter.HomeView;
import ir.smartplanning.client.presenter.LoginPresenter;
import ir.smartplanning.client.presenter.LoginView;
import ir.smartplanning.client.presenter.MainPresenter;
import ir.smartplanning.client.presenter.MainView;
import ir.smartplanning.client.presenter.RegisterPresenter;
import ir.smartplanning.client.presenter.RegisterView;
import ir.smartplanning.shared.MyRequestFactory;
import ir.smartplanning.shared.MyRequestFactory.ExamRequestContext;
import ir.smartplanning.shared.MyRequestFactory.StudyPeriodRequestContext;
import ir.smartplanning.shared.MyRequestFactory.UserRequestContext;

import com.google.gwt.core.client.GWT;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.client.gin.DispatchAsyncModule;
import com.gwtplatform.mvp.client.annotations.DefaultPlace;
import com.gwtplatform.mvp.client.annotations.ErrorPlace;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

public class ClientModule extends AbstractPresenterModule{

	@Override
	protected void configure() {
		install(new DefaultModule());
		install(new DispatchAsyncModule());
		bindConstant().annotatedWith(ErrorPlace.class).to(NameTokens.login);
		bindConstant().annotatedWith(UnauthorizedPlace.class).to(
				NameTokens.login);
		bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.login);

		bindPresenter(LoginPresenter.class, LoginPresenter.MyView.class,
				LoginView.class, LoginPresenter.MyProxy.class);

		bindPresenter(RegisterPresenter.class, RegisterPresenter.MyView.class,
				RegisterView.class, RegisterPresenter.MyProxy.class);

		bindPresenter(HomePresenter.class, HomePresenter.MyView.class,
				HomeView.class, HomePresenter.MyProxy.class);

		bindPresenter(MainPresenter.class, MainPresenter.MyView.class,
				MainView.class, MainPresenter.MyProxy.class);

		bindPresenter(ExamResultPresenter.class,
				ExamResultPresenter.MyView.class, ExamResultView.class,
				ExamResultPresenter.MyProxy.class);

		bindPresenter(ConsultationPresenter.class,
				ConsultationPresenter.MyView.class, ConsultationView.class,
				ConsultationPresenter.MyProxy.class);

//		bindPresenter(TakeExamPresenter.class, TakeExamPresenter.MyView.class,
//				TakeExamView.class, TakeExamPresenter.MyProxy.class);
	}
	
	@Singleton
	@Provides
	public MyRequestFactory createMyRequestFactory(EventBus eventBus){
		MyRequestFactory myRF = GWT.create(MyRequestFactory.class);
		myRF.initialize(eventBus);
		return myRF;
	}
	
	@Provides
	public UserRequestContext createUserRequestContext(MyRequestFactory factory){
		return factory.getUserRequestContext();
	}
	
	@Provides
	public StudyPeriodRequestContext createStudyPeriodRequestContext(MyRequestFactory factory){
		return factory.getStudPeriodRequestContext();
	}

	@Provides
	public ExamRequestContext createExamRequestContext(MyRequestFactory factory){
		return factory.getExamRequestContext();
	}
}
