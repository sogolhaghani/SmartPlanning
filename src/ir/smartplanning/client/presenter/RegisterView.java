package ir.smartplanning.client.presenter;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class RegisterView extends ViewImpl implements RegisterPresenter.MyView {

	private final Widget widget;
	@UiField
	TextBox name;
	@UiField
	TextBox family;
	@UiField
	TextBox userName;
	@UiField
	PasswordTextBox password;
	@UiField
	ListBox grade;
	@UiField
	ListBox major;
	@UiField
	RadioButton girl;
	@UiField
	RadioButton boy;
	@UiField
	Button register;

	public interface Binder extends UiBinder<Widget, RegisterView> {
	}

	@Inject
	public RegisterView(final Binder binder) {
		widget = binder.createAndBindUi(this);
		name.getElement().setAttribute("placeholder", "نام");
		family.getElement().setAttribute("placeholder", "نام خانوادگی");
		userName.getElement().setAttribute("placeholder", "نام کاربری");
		password.getElement().setAttribute("placeholder", "رمز عبور");

	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	public TextBox getName() {
		return name;
	}

	public TextBox getFamily() {
		return family;
	}

	public TextBox getUserName() {
		return userName;
	}

	public PasswordTextBox getPassword() {
		return password;
	}

	public ListBox getGrade() {
		return grade;
	}

	public ListBox getMajor() {
		return major;
	}

	public RadioButton getGirl() {
		return girl;
	}

	public RadioButton getBoy() {
		return boy;
	}

	public Button getRegister() {
		return register;
	}

}
