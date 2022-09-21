package ir.smartplanning.client.presenter;

import ir.smartplanning.client.Messages;
import ir.smartplanning.client.MyGateKeeper;
import ir.smartplanning.client.place.NameTokens;
import ir.smartplanning.shared.MyRequestFactory.UserRequestContext;
import ir.smartplanning.shared.proxies.nonpersists.UserItemProxy;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;

public class LoginPresenter extends
		Presenter<LoginPresenter.MyView, LoginPresenter.MyProxy> {

	public interface MyView extends View {
		public Button getLogin();

		public PasswordTextBox getPassword();

		public TextBox getUserName();

		public Button getRegister();
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.login)
	public interface MyProxy extends ProxyPlace<LoginPresenter> {
	}

	private final Provider<UserRequestContext> userRequestContextProvider;
	private final PlaceManager placeManager;
	private boolean lock=false;

	@Inject
	public LoginPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy, PlaceManager placeManager,
			Provider<UserRequestContext> userRequestContextProvider) {
		super(eventBus, view, proxy);
		this.userRequestContextProvider = userRequestContextProvider;
		this.placeManager = placeManager;
	}

	@Override
	protected void revealInParent() {
		RevealRootLayoutContentEvent.fire(this, this);
	}
	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
		lock=false;
	}
	

	@Override
	protected void onBind() {
		super.onBind();

		getView().getRegister().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				PlaceRequest request = new PlaceRequest.Builder().nameToken(
						NameTokens.signup).build();
				placeManager.revealPlace(request, true);

			}
		});

		getView().getLogin().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (loginIsValidate() == true) {
					if(lock==true){
						return;
					}else{
						lock=true;
					}
					String userName = getView().getUserName().getText().trim();
					String password = getView().getPassword().getText().trim();
					userRequestContextProvider.get().login(userName, password)
							.fire(new Receiver<UserItemProxy>() {

								@Override
								public void onSuccess(UserItemProxy response) {
									
									if (response != null
											&& response.getId() > 0) {
										MyGateKeeper.setUser(response);
										Messages.ShowNoty("succes",
												Messages.SUCCESS);
										PlaceRequest request = new PlaceRequest.Builder()
												.nameToken(NameTokens.consultation)
												.build();
										placeManager.revealPlace(request, true);
									} else {
										if (response == null) {
											Messages.ShowNoty(
													"ارتباط اینترنتی شما ضعیف می باشد. لطفا پس از بررسی دوباره تلاش نمایید.",
													Messages.ERORR);
										} else if (response.getId() == -1) {
											Messages.ShowNoty(
													"رمز عبور یا نام کاربری صحیح نمی باشد.",
													Messages.ERORR);
										}

									}
									lock=false;

								}

								public void onFailure(
										com.google.web.bindery.requestfactory.shared.ServerFailure error) {
									Messages.ShowNoty(error.getMessage(),
											Messages.ERORR);
									lock=false;
								};
							});
				}

			}
		});
	}

	protected boolean loginIsValidate() {
		List<String> errors = new ArrayList<String>();

		if (getView().getUserName().getText().trim().equals("")) {
			errors.add("نام کاربری وارد نشده است.");
		}
		if (getView().getPassword().getText().trim().equals("")) {
			errors.add("رمز عبور وارد نشده است.");
		}
		if (errors.size() == 0) {
			return true;
		}
		Messages.ShowNoty("موارد زیر را تصحیح کنید.", errors, Messages.ERORR);
		return false;
	}
}
