package ir.smartplanning.client.widget;

import ir.smartplanning.client.event.SelectingOptionEvent;
import ir.smartplanning.shared.proxies.nonpersists.QuestionProxy;

import java.util.Map;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;

public class OptionCell extends AbstractCell<QuestionProxy> {
	Map<Long, RenderingQuestionStates> answerSheet;
	private static OptionCellTemplate template = GWT.create(OptionCellTemplate.class);
	private static SelectedOptionCellTemplate selectedtemplate = GWT.create(SelectedOptionCellTemplate.class);
	private static DisabledOptionCellTemplate disabledTemplate = GWT.create(DisabledOptionCellTemplate.class);
	private static DisabledSelectedOptionCellTemplate disabledSelectedtemplate = GWT.create(DisabledSelectedOptionCellTemplate.class);
	private Integer optionNo;
	private EventBus eventBus;
	private boolean isClickable;

	public OptionCell(EventBus eventBus, Integer optionNo, boolean isClickable) {
		super("click");
		this.eventBus = eventBus;
		this.optionNo = optionNo;
		this.isClickable = isClickable;
	}

	public OptionCell(EventBus eventBus, Integer optionNo, Map<Long, RenderingQuestionStates> answerSheet, boolean isClickable) {
		super("click");
		this.isClickable = isClickable;
		this.eventBus = eventBus;
		this.answerSheet = answerSheet;
		this.optionNo = optionNo;
	}

	private Long getCurrentOptionId(QuestionProxy question) {
		if (this.optionNo == 1 || this.optionNo == -1)
			return question.getChoiceId1();
		if (this.optionNo == 2 || this.optionNo == -2)
			return question.getChoiceId2();
		if (this.optionNo == 3 || this.optionNo == -3)
			return question.getChoiceId3();
		if (this.optionNo == 4 || this.optionNo == -4)
			return question.getChoiceId4();
		return null;
	}

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, QuestionProxy value, SafeHtmlBuilder sb) {

		if (value == null || getCurrentOptionId(value) == null) {
			sb.appendHtmlConstant("<div></div>");
			return;
		}
		RenderingQuestionStates state = answerSheet.get(value.getId());
		SafeHtml rendered = null;
		Long selectedOption = -1L;
		if (state != null)
			selectedOption = state.getSelectedOption();
		if (value.getSelectedChoiceId() != null) {
			selectedOption = value.getSelectedChoiceId();
		}
		if (isClickable) {

			if (selectedOption != null && selectedOption.longValue() == getCurrentOptionId(value).longValue())
				rendered = selectedtemplate.radioCell("question_option_cell_radio_button", value.getId().toString());
			else
				rendered = template.radioCell("question_option_cell_radio_button", value.getId().toString());

		} else {
			if (selectedOption != null && selectedOption.longValue() == getCurrentOptionId(value).longValue())
				rendered = disabledSelectedtemplate.radioCell("question_option_cell_radio_button", value.getId().toString());
			else
				rendered = disabledTemplate.radioCell("question_option_cell_radio_button", value.getId().toString());
		}

		sb.append(rendered);
	}

	@Override
	public void onBrowserEvent(com.google.gwt.cell.client.Cell.Context context, Element parent, QuestionProxy value, NativeEvent event,
			ValueUpdater<QuestionProxy> valueUpdater) {
		try {

			if (!isClickable)
				return;
			if (answerSheet != null) {

				RenderingQuestionStates state = answerSheet.get(value.getId());
				if (state == null) {
					state = new RenderingQuestionStates();

				}
				if (state.getSelectedOption() != null && state.getSelectedOption().longValue() == getCurrentOptionId(value).longValue()) {
					String innerHTML = parent.getInnerHTML();
					innerHTML = innerHTML.replaceAll("checked", "");
					innerHTML = innerHTML.replaceAll("CHECKED=\"\"", "");
					state.setSelectedOption(null);
					parent.setInnerHTML(innerHTML);
					// CommonLogger.log(logger, Level.INFO,
					// "selected option:","  ",value.getId(),"unselect");
					this.eventBus.fireEvent(new SelectingOptionEvent(value.getId(), null));

				} else {
					String innerHTML = parent.getInnerHTML();
					innerHTML = innerHTML.replace("<input", "<input checked");
					parent.setInnerHTML(innerHTML);
					this.eventBus.fireEvent(new SelectingOptionEvent(value.getId(), getCurrentOptionId(value).longValue()));
				}
				answerSheet.put(value.getId(), state);
			}

		} catch (Exception e) {
			Window.alert("OptionCell:" + e.getMessage());
		}
	}
}
