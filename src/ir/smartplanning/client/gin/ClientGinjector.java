package ir.smartplanning.client.gin;

import ir.smartplanning.client.presenter.ConsultationPresenter;
import ir.smartplanning.client.presenter.ExamResultPresenter;
import ir.smartplanning.client.presenter.HomePresenter;
import ir.smartplanning.client.presenter.LoginPresenter;
import ir.smartplanning.client.presenter.MainPresenter;
import ir.smartplanning.client.presenter.RegisterPresenter;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.gwtplatform.dispatch.client.gin.DispatchAsyncModule;

@GinModules({ DispatchAsyncModule.class, ClientModule.class })
public interface ClientGinjector extends Ginjector {

	AsyncProvider<LoginPresenter> getLoginPresenter();

	AsyncProvider<RegisterPresenter> getRegisterPresenter();

	AsyncProvider<HomePresenter> getHomePresenter();

	AsyncProvider<MainPresenter> getMainPresenter();

	AsyncProvider<ExamResultPresenter> getExamResultPresenter();

	AsyncProvider<ConsultationPresenter> getConsultationPresenter();

//	AsyncProvider<TakeExamPresenter> getTakeExamPresenter();

}
