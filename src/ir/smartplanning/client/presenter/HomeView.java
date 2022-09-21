package ir.smartplanning.client.presenter;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class HomeView extends ViewImpl implements HomePresenter.MyView {

	private final Widget widget;

//	@UiField
//	Label home;
	@UiField
	Label consultation;
//	@UiField
//	Label examResult;
	@UiField
	HTMLPanel menu_slot;
	@UiField
	HTMLPanel content_slot;

	public interface Binder extends UiBinder<Widget, HomeView> {
	}

	@Inject
	public HomeView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void setInSlot(Object slot, IsWidget content) {

		if (slot == HomePresenter.content_slot) {
			content_slot.clear();
			if (content != null) {
				content_slot.add(content);
			}
		} else if (slot == HomePresenter.menuBar_slot) {
			menu_slot.clear();
			if (content != null)
				menu_slot.add(content);
		} else {
			super.setInSlot(slot, content);
		}
	}

//	public Label getHome() {
//		return home;
//	}

//	public Label getExamResult() {
//		return examResult;
//	}

	public Label getConsultation() {
		return consultation;
	}
}
