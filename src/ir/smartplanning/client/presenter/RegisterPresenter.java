package ir.smartplanning.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.NameToken;

import ir.smartplanning.client.Messages;
import ir.smartplanning.client.MyGateKeeper;
import ir.smartplanning.client.place.NameTokens;
import ir.smartplanning.shared.MyRequestFactory.UserRequestContext;
import ir.smartplanning.shared.proxies.nonpersists.UserItemProxy;

import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;

public class RegisterPresenter extends
		Presenter<RegisterPresenter.MyView, RegisterPresenter.MyProxy> {

	public interface MyView extends View {
		public TextBox getName();

		public TextBox getFamily();

		public TextBox getUserName();

		public PasswordTextBox getPassword();

		public ListBox getGrade();

		public ListBox getMajor();

		public RadioButton getGirl();

		public RadioButton getBoy();

		public Button getRegister();
	}

	private boolean uniqeUserName = false;
	private final Provider<UserRequestContext> userRequestContextProvider;
	private boolean lock = false;
	private final PlaceManager placeManager;

	@ProxyCodeSplit
	@NameToken(NameTokens.signup)
	public interface MyProxy extends ProxyPlace<RegisterPresenter> {
	}

	@Inject
	public RegisterPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy,
			Provider<UserRequestContext> userRequestContextProvider,
			PlaceManager placeManager) {
		super(eventBus, view, proxy);
		this.userRequestContextProvider = userRequestContextProvider;
		this.placeManager = placeManager;
	}

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
		reset();
	}

	private void reset() {
		getView().getName().setText("");
		getView().getFamily().setText("");
		getView().getBoy().setValue(false);
		getView().getGirl().setValue(false);
		getView().getGrade().clear();
		getView().getMajor().clear();
		getView().getPassword().setText("");
		getView().getUserName().setText("");
		lock = false;
		fillGradeBox();
		fillMajorBox();

	}

	private void fillGradeBox() {
		getView().getMajor().insertItem("????????", Direction.RTL, "0", 0);
		getView().getMajor().insertItem("?????????? ??????????", Direction.RTL, "1", 1);
		getView().getMajor().insertItem("???????? ??????????", Direction.RTL, "2", 2);
		getView().getMajor().insertItem("???????? ????????????", Direction.RTL, "3", 3);

	}

	private void fillMajorBox() {
		getView().getGrade().insertItem("?????? ????????", Direction.RTL, "0", 0);
		getView().getGrade().insertItem("?????? ????????????????", Direction.RTL, "2", 1);
		getView().getGrade().insertItem("?????? ????????????????", Direction.RTL, "3", 2);
		getView().getGrade()
				.insertItem("?????????? ????????????????", Direction.RTL, "4", 3);
	}

	@Override
	protected void revealInParent() {
		RevealRootLayoutContentEvent.fire(this, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
		onRegister();
		onCheckUserName();
	}

	private void onCheckUserName() {
		getView().getUserName().addBlurHandler(new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				if (getView().getUserName().getText().trim().isEmpty()) {
					return;
				}
				userRequestContextProvider
						.get()
						.checkUserNameIsUniqe(
								getView().getUserName().getText().trim())
						.fire(new Receiver<Boolean>() {

							@Override
							public void onSuccess(Boolean response) {
								uniqeUserName = response;
							}

							@Override
							public void onFailure(ServerFailure error) {
								// TODO Auto-generated method stub
								super.onFailure(error);
							}
						});

			}
		});

	}

	private void onRegister() {
		getView().getRegister().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (userIsValid()) {
					if (lock == true) {
						return;
					} else {
						lock = true;
					}
					UserRequestContext context = userRequestContextProvider
							.get();
					UserItemProxy proxy = createUserItem(context);
					context.register(getView().getPassword().getText().trim(),
							proxy).fire(new Receiver<UserItemProxy>() {

						@Override
						public void onSuccess(UserItemProxy response) {
							lock = false;
							if (response != null) {
								MyGateKeeper.setUser(response);
								Messages.ShowNoty("succes", Messages.SUCCESS);
								PlaceRequest request = new PlaceRequest.Builder()
										.nameToken(NameTokens.consultation).build();
								placeManager.revealPlace(request, true);
							} else {
								Messages.ShowNoty("?????????? ?????? ???????? ??????????",
										Messages.ERORR);
							}

						}

						public void onFailure(
								com.google.web.bindery.requestfactory.shared.ServerFailure error) {
							lock = false;
							Messages.ShowNoty(error.getMessage(),
									Messages.ERORR);
						};
					});
				}

			}
		});
	}

	protected UserItemProxy createUserItem(UserRequestContext context) {
		UserItemProxy proxy = context.create(UserItemProxy.class);
		proxy.setName(getView().getName().getText().trim());
		proxy.setFamily(getView().getFamily().getText().trim());
		proxy.setGrade(Byte.parseByte(getView().getGrade().getSelectedValue()));
		proxy.setSex(getView().getBoy().getValue());
		proxy.setUserName(getView().getUserName().getText().trim());
		proxy.setMajorId(Long
				.parseLong(getView().getMajor().getSelectedValue()));
		return proxy;
	}

	protected boolean userIsValid() {
		List<String> errors = new ArrayList<String>();
		if (getView().getName().getText().trim().isEmpty() == true) {
			errors.add("?????? ?????? ?????????? ???????? ????????.");
		} else if (getView().getName().getText().trim().length() > 20) {
			errors.add("???????????? ?????????? ???????????? ???????? ?????? 20 ???? ????????.");
		}
		if (getView().getFamily().getText().trim().isEmpty() == true) {
			errors.add("?????? ???????????????? ?????? ?????????? ???????? ????????.");
		} else if (getView().getFamily().getText().trim().length() > 20) {
			errors.add("???????????? ?????????? ???????????? ???????? ?????? ???????????????? 20 ???? ????????.");
		}
		if (getView().getUserName().getText().trim().isEmpty() == true) {
			errors.add("?????? ???????????? ?????? ?????????? ???????? ????????.");
		} else if (uniqeUserName == false) {
			errors.add("?????? ???????????? ???????????? ???? ????????.");
		} else if (getView().getUserName().getText().trim().length() > 20
				|| getView().getUserName().getText().trim().length() < 3) {
			errors.add("???????????? ?????????? ???????????? ???????? ?????? ??????????  20 ?? ?????????? 3 ???? ????????.");
		}
		if (getView().getPassword().getText().trim().isEmpty() == true) {
			errors.add("?????? ???????? ?????? ?????????? ???????? ????????");
		}
		if (getView().getGirl().getValue() == getView().getBoy().getValue()) {
			errors.add("?????????? ???????????? ???????? ??????.");
		}
		if (getView().getGrade().getSelectedIndex() == 0) {
			errors.add("?????? ???????? ???????????? ???????? ??????.");
		}
		if (getView().getMajor().getSelectedIndex() == 0) {
			errors.add("???????? ???????????? ???????????? ???????? ??????.");
		}
		if (errors.size() == 0) {
			return true;
		}
		Messages.ShowNoty("?????????? ?????? ???? ?????????? ????????", errors, Messages.ERORR);
		return false;
	}
}
