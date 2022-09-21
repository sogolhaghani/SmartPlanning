package ir.smartplanning.client.widget;

import ir.smartplanning.shared.enums.QuestionTypes;
import ir.smartplanning.shared.proxies.nonpersists.QuestionProxy;

import java.util.Map;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window;

public class ContentCell extends AbstractCell<QuestionProxy> {

	private static QuestionTemplate template = GWT
			.create(QuestionTemplate.class);
	private static ReadingContentTemplate readingTemplate = GWT
			.create(ReadingContentTemplate.class);
	Map<Long, RenderingQuestionStates> answerSheet;
	private String dir;
	private String align;
	private String htmlClazz;
	private String floatClass = "";

	public ContentCell(Map<Long, RenderingQuestionStates> answerSheet) {
		super("click");
		this.answerSheet = answerSheet;
	}

	@Override
	public void render(Context context, QuestionProxy value, SafeHtmlBuilder sb) {
		if (value == null)
			return;

		boolean hasQuestionNumber = false;
		if (value.getQuestionTypeId().longValue() == QuestionTypes.READING_MULTIPLECHOICE
				.getId()
				|| value.getQuestionTypeId().longValue() == QuestionTypes.READING_EXPLANATORY
						.getId()
				|| (value.getQuestionTypeId().longValue() == QuestionTypes.EXPLANATORY
						.getId() && value.getReadingQuestionId() != null)) {
			hasQuestionNumber = true;
		}
		if (QuestionTypes.isReading(value.getQuestionTypeId()))
			this.htmlClazz = "reading_wrppaer";
		else
			this.htmlClazz = "question_wrapper";
		RenderingQuestionStates state = answerSheet.get(value.getId());
		String qString = value.getQuestion();
		SafeHtml question = SafeHtmlUtils.fromTrustedString(qString);
		if (isLTR(qString)) {
			this.dir = "LTR";
			this.align = "left";
			this.floatClass = "";
			// }
		} else {
			this.dir = "RTL";
			this.align = "right";
			this.floatClass = "number_font";
		}
		if (hasQuestionNumber) {
			if (isLTR(qString)) {
				this.floatClass = "float_left";
			} else {
				this.floatClass = "";
			}
		}
		String questionNo = state.getQuestionNo() + "";
		if (value.getReadingQuestionId() != null) {
			questionNo = "*" + state.getQuestionNo();
		}
		SafeHtml rendered;
		if (state.getQuestionNo() == -1
				|| QuestionTypes.isReading(value.getQuestionTypeId())) {

			rendered = readingTemplate.cell(dir, align, question,
					this.htmlClazz, value.getId().toString());
		} else {
			rendered = template.cell(this.dir, align, questionNo, question,
					this.htmlClazz, value.getId().toString(), floatClass);
		}

		sb.append(rendered);

	}

	@Override
	public void onBrowserEvent(com.google.gwt.cell.client.Cell.Context context,
			Element parent, QuestionProxy value, NativeEvent event,
			ValueUpdater<QuestionProxy> valueUpdater) {
		try {
			super.onBrowserEvent(context, parent, value, event, valueUpdater);
		} catch (Exception e) {
			Window.alert(e.getMessage());
		}
	}

	private boolean isLTR(String text) {
		if (text == null) {
			return false;
		}
		String t = text.toLowerCase();
		return t.contains("<div class=\"question\" dir=\"ltr\">");
	}
}