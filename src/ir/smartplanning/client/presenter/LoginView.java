package ir.smartplanning.client.presenter;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class LoginView extends ViewImpl implements LoginPresenter.MyView {

	private final Widget widget;

	@UiField
	TextBox userName;
	@UiField
	PasswordTextBox password;
	@UiField
	Button login;
	@UiField
	Button register;
	

	public interface Binder extends UiBinder<Widget, LoginView> {
	}

	@Inject
	public LoginView(final Binder binder) {
		widget = binder.createAndBindUi(this);
		userName.getElement().setAttribute("placeholder", "نام کاربری را وارد کن");
		password.getElement().setAttribute("placeholder", "رمز عبور را وارد کن");
	}
	@Override
	public Widget asWidget() {
		return widget;
	}

	public Button getLogin() {
		return login;
	}

	public PasswordTextBox getPassword() {
		return password;
	}

	public Button getRegister() {
		return register;
	}

	public TextBox getUserName() {
		return userName;
	}
}
