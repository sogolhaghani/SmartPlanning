package ir.smartplanning.client.widget;

import ir.smartplanning.shared.proxies.nonpersists.QuestionProxy;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window;

public class OptionContentCell extends AbstractCell<QuestionProxy> {
	private static OptionCellTemplate template = GWT.create(OptionCellTemplate.class);

	private Integer optionNo;
	private String htmlClass;

	public OptionContentCell(Integer optionNo) {
		super("click");
		this.optionNo = optionNo;
	}
	private boolean isLTR(String text) {
		if (text == null) {
			return false;
		}
		String t = text.toLowerCase();
		return t.contains("dir=\"ltr\"");
	}

	private String getCurrentOptionDescription(QuestionProxy question) {
		if (this.optionNo == 1)
			return question.getChoiceDescription1();
		if (this.optionNo == 2)
			return question.getChoiceDescription2();
		if (this.optionNo == 3)
			return question.getChoiceDescription3();
		if (this.optionNo == 4)
			return question.getChoiceDescription4();
		return "";
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

	String displayStyle = "";
	static String displayStyle1 = "";
	static String displayStyle2 = "";
	String numberFont = "";

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, QuestionProxy value, SafeHtmlBuilder sb) {

		if (value == null || getCurrentOptionId(value) == null) {
			sb.appendHtmlConstant("<div></div>");
			return;
		}
		SafeHtml option = SafeHtmlUtils.fromTrustedString(getCurrentOptionDescription(value));
		this.htmlClass = "options_wrapper";
		if (isLTR(value.getQuestion())) {
			this.numberFont = "";
		} else {
			this.numberFont = "number_font";
		}
		SafeHtml rendered = null;

		if (optionNo == 1) {
			setCellDisplay(value);
			displayStyle = displayStyle1;
		} else if (optionNo == 2) {
			displayStyle = displayStyle2;
		} else if (optionNo == 3) {
			displayStyle = displayStyle1;
		} else {
			displayStyle = displayStyle2;
			;
		}
		rendered = template.cell_content(optionNo, option, htmlClass, value.getId().toString(),numberFont);
		sb.append(rendered);

	}

	private void setCellDisplay(QuestionProxy value) {
		int resultType = 0;
		int choiceLength1 = getChoiceLength(value.getChoiceDescription1());
		int choiceLength2 = getChoiceLength(value.getChoiceDescription2());
		int choiceLength3 = getChoiceLength(value.getChoiceDescription3());
		int choiceLength4 = getChoiceLength(value.getChoiceDescription4());
		int maxLength = getMaxLength(choiceLength1, choiceLength2, choiceLength3, choiceLength4);
		if (maxLength < 50) {
			resultType = 1;
			if (hasQuestionImage(value)) {
				resultType = 2;
				displayStyle1 ="display width_400";
			} else
				displayStyle1 ="display width_200";
		} else if (maxLength < 80) {
			resultType = 2;
			if (hasQuestionImage(value)) {
				resultType = 4;
				displayStyle1 ="display width_700";
			} else
				displayStyle1 = "display width_400";

		} else {
			resultType = 4;
			displayStyle1 = "display width_700";
		}
		if (resultType == 2 && hasQuestionImage(value)) {
			displayStyle2 = "display";
		} else
			displayStyle2 = displayStyle1;

	}

	private int getChoiceLength(String choice) {
		int actualLength = choice.length();
		int finalizeLength = 0;
		if (choice.contains("displaystyle")) {
			finalizeLength = actualLength - 40;
		} else {
			finalizeLength = actualLength;
		}
		return finalizeLength;

	}

	private boolean hasQuestionImage(QuestionProxy value) {
		if (value.getQuestion().contains("floatLeft")) {
			return true;
		}
		return false;
	}

	private int getMaxLength(int choiceLength1, int choiceLength2, int choiceLength3, int choiceLength4) {
		int max1 = Math.max(choiceLength1, choiceLength2);
		int max2 = Math.max(choiceLength3, choiceLength4);
		return Math.max(max1, max2);
	}

	@Override
	public void onBrowserEvent(com.google.gwt.cell.client.Cell.Context context, Element parent, QuestionProxy value, NativeEvent event,
			ValueUpdater<QuestionProxy> valueUpdater) {
		try {

			return;
		} catch (Exception e) {
			Window.alert("OptionCell:" + e.getMessage());
		}
	}
}
