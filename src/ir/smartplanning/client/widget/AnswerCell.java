package ir.smartplanning.client.widget;

import ir.smartplanning.shared.enums.QuestionTypes;
import ir.smartplanning.shared.proxies.nonpersists.QuestionProxy;

import java.util.Map;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

public class AnswerCell extends AbstractCell<QuestionProxy> {

	private static AnswerCellTemplate template = GWT
			.create(AnswerCellTemplate.class);

	Map<Long, RenderingQuestionStates> answerSheet;
	private String align;

	private String htmlClass;
	private CellListTypes cellListType;

	public AnswerCell(Map<Long, RenderingQuestionStates> answerSheet,
			CellListTypes cellListType) {
		super("click");
		this.answerSheet = answerSheet;
		this.cellListType = cellListType;
	}

	@Override
	public void render(Context context, QuestionProxy value, SafeHtmlBuilder sb) {
		if (value == null)

			return;
		String answerString = value.getAnswer();
		if (value.getQuestionTypeId().longValue() == QuestionTypes.MULTIPLECHOICE
				.getId()) {
			answerString = "گزینه  " + getCorrectChoiceNumber(value)
					+ answerString;
		}

		SafeHtml answer = SafeHtmlUtils.fromTrustedString(answerString);
		if (isLTR(value.getQuestion())) {
			this.align = "left";
		} else {
			this.align = "right";
		}
		RenderingQuestionStates state = answerSheet.get(value.getId());
		this.htmlClass = "display_none";
		if (state.isAnswerVisible()
				|| (cellListType.getId() != CellListTypes.Exam.getId())) {
			this.htmlClass = "answer_wrapper";
		}

		answerSheet.put(value.getId(), state);
		SafeHtml rendered = null;
		if (value.getQuestionTypeId() != QuestionTypes.READING_EXPLANATORY
				.getId()
				&& value.getQuestionTypeId() != QuestionTypes.READING_MULTIPLECHOICE
						.getId())
			rendered = template.cell(htmlClass, align, answer, value.getId()
					.toString());
		else {
			rendered = template.emptyCell();
		}
		sb.append(rendered);
	}

	private String getCorrectChoiceNumber(QuestionProxy question) {
		if (question.getAnswerId() == null)
			return "نامشخص";
		if (question.getAnswerId().longValue() == question.getChoiceId1()
				.longValue())
			return "1";
		if (question.getAnswerId().longValue() == question.getChoiceId2()
				.longValue())
			return "2";
		if (question.getAnswerId().longValue() == question.getChoiceId3()
				.longValue())
			return "3";
		if (question.getAnswerId().longValue() == question.getChoiceId4()
				.longValue())
			return "4";
		return "نامشخص";

	}

	private boolean isLTR(String text) {
		if (text == null) {
			return false;
		}
		String t = text.toLowerCase();
		return t.contains("dir=\"ltr\"");
	}

}