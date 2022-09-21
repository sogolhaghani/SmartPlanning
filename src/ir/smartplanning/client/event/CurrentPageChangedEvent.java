package ir.smartplanning.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HasHandlers;

public class CurrentPageChangedEvent extends
		GwtEvent<CurrentPageChangedEvent.CurrentPageChangedHandler> {

	public static Type<CurrentPageChangedHandler> TYPE = new Type<CurrentPageChangedHandler>();

	public interface CurrentPageChangedHandler extends EventHandler {
		void onCurrentPageChanged(CurrentPageChangedEvent event);
	}

	public CurrentPageChangedEvent() {
	}

	@Override
	protected void dispatch(CurrentPageChangedHandler handler) {
		handler.onCurrentPageChanged(this);
	}

	@Override
	public Type<CurrentPageChangedHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<CurrentPageChangedHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source) {
		source.fireEvent(new CurrentPageChangedEvent());
	}
}
