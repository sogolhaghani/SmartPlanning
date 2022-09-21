package ir.smartplanning.client.presenter;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.NameToken;

import ir.smartplanning.client.place.NameTokens;

import com.gwtplatform.mvp.client.annotations.UseGatekeeper;

import ir.smartplanning.client.MyGateKeeper;

import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

import ir.smartplanning.client.presenter.HomePresenter;

public class ExamResultPresenter extends
		Presenter<ExamResultPresenter.MyView, ExamResultPresenter.MyProxy> {

	public interface MyView extends View {
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.examresult)
	@UseGatekeeper(MyGateKeeper.class)
	public interface MyProxy extends ProxyPlace<ExamResultPresenter> {
	}

	@Inject
	public ExamResultPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, HomePresenter.content_slot, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}
}
