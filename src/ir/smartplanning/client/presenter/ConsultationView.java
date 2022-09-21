package ir.smartplanning.client.presenter;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class ConsultationView extends ViewImpl implements
		ConsultationPresenter.MyView {

	private final Widget widget;
	
	@UiField HTMLPanel weeks;
	@UiField HTMLPanel daysOfWeek;

	public interface Binder extends UiBinder<Widget, ConsultationView> {
	}

	@Inject
	public ConsultationView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	
	public HTMLPanel getWeeks() {
		return weeks;
	}
	
	public HTMLPanel getDaysOfWeek() {
		return daysOfWeek;
	}
	
}
