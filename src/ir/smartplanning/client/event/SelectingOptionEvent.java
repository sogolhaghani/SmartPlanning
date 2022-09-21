package ir.smartplanning.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class SelectingOptionEvent extends
		GwtEvent<SelectingOptionEvent.SelectingOptionHandler> {

	public static Type<SelectingOptionHandler> TYPE = new Type<SelectingOptionHandler>();
	private Long questionId;
	private Long optionId;

	public interface SelectingOptionHandler extends EventHandler {
		void onSelectingOption(SelectingOptionEvent event);
	}

	public interface SelectingOptionHasHandlers extends HasHandlers {
		HandlerRegistration addSelectingOptionHandler(SelectingOptionHandler handler);
	}

	public SelectingOptionEvent(Long questionId,Long optionId) {
		this.optionId = optionId;
		this.questionId = questionId;
	}

	public Long getOptionId() {
		return optionId;
	}
	public Long getQuestionId() {
		return questionId;
	}

	@Override
	protected void dispatch(SelectingOptionHandler handler) {
		handler.onSelectingOption(this);
	}

	@Override
	public Type<SelectingOptionHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<SelectingOptionHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source,Long questionId ,Long optionId) {
		source.fireEvent(new SelectingOptionEvent(questionId,optionId));
	}
}
