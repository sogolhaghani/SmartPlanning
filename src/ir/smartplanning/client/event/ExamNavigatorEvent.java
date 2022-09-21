package ir.smartplanning.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class ExamNavigatorEvent extends
		GwtEvent<ExamNavigatorEvent.ExamNavigatorHandler> {

	public static Type<ExamNavigatorHandler> TYPE = new Type<ExamNavigatorHandler>();

	public interface ExamNavigatorHandler extends EventHandler {
		void onExamNavigator(ExamNavigatorEvent event);
	}

	public ExamNavigatorEvent() {
	}

	@Override
	protected void dispatch(ExamNavigatorHandler handler) {
		handler.onExamNavigator(this);
	}

	@Override
	public Type<ExamNavigatorHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<ExamNavigatorHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source) {
		source.fireEvent(new ExamNavigatorEvent());
	}
}
