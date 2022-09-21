package ir.smartplanning.client.presenter;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class ExamResultView extends ViewImpl implements
		ExamResultPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, ExamResultView> {
	}

	@Inject
	public ExamResultView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
}
