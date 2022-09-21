package ir.smartplanning.client.widget;

import ir.smartplanning.client.MyGateKeeper;
import ir.smartplanning.shared.enums.DifficultyLevels;
import ir.smartplanning.shared.enums.QuestionOrigins;
import ir.smartplanning.shared.enums.QuestionTypes;
import ir.smartplanning.shared.proxies.nonpersists.QuestionProxy;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.storage.client.Storage;

public class SummaryCell extends AbstractCell<QuestionProxy> {

	public SummaryCell() {
	}

	static QuestionSummaryTemplate template = GWT
			.create(QuestionSummaryTemplate.class);

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context,
			QuestionProxy value, SafeHtmlBuilder sb) {
		if (value == null)
			return;
		String topicsName = getTopicsName(value);

		sb.appendHtmlConstant("<div  class=\"question_summery_cell_box\"> </div>");

		SafeUri difficultyLevel = findDifficultyLevel(value);
		String major = value.getMajorName();
		if (MyGateKeeper.getUser().getGrade() == 1) {
			major = "عمومی";
		}
		String module = "";
		if (value.getModuleName() != null
				&& value.getModuleName().length() != 0)
			module = "کتاب " + value.getModuleName();
		String operatorInfo = "";
		String direction = "left";
		SafeStyles style = SafeStylesUtils.forFloat(Float.LEFT);
		if (isLTR(value.getQuestion())) {
			direction = "right";
			style = SafeStylesUtils.forFloat(Float.LEFT);
		}

		final Storage stockStore = Storage.getLocalStorageIfSupported();
		String isInTour = stockStore.getItem("isInTour");

		String publishYear = "سال ";
		String className = "question_summery_cell_box";
		if ((value.getQuestionTypeId() == QuestionTypes.READING_EXPLANATORY
				.getId() || value.getQuestionTypeId() == QuestionTypes.READING_MULTIPLECHOICE
				.getId())) {
			SafeHtml rendered = template.emptyCell();
			sb.append(rendered);

		} else if (operatorInfo.trim().equals("")) {
			if (isInTour != null && isInTour.trim().equals("1")
					&& value.getCnt() != null) {
				SafeHtml rendered = template.cellWithId(className, direction,
						QuestionOrigins.getPersianText(value.getOrigin()),
						"رشته " + major, value.getPublishYear(), topicsName,
						difficultyLevel, publishYear, module, style);
				sb.append(rendered);
			} else {
				SafeHtml rendered = template.cell(className, direction,
						QuestionOrigins.getPersianText(value.getOrigin()),
						"رشته " + major, value.getPublishYear(), topicsName,
						difficultyLevel, publishYear, module, style);
				sb.append(rendered);
			}

		} else {
			SafeHtml rendered = template.operatorCell(className, direction,
					QuestionOrigins.getPersianText(value.getOrigin()), "رشته "
							+ major, value.getPublishYear(), topicsName,
					difficultyLevel, operatorInfo, publishYear, module, style);
			sb.append(rendered);
		}
	}

	private String getTopicsName(QuestionProxy value) {
		if (value.getParentTopicName() != null && value.getTopicName() != null)
			return value.getParentTopicName() + ":" + value.getTopicName();
		else if (value.getTopicName() != null)
			return value.getTopicName();
		else if (value.getParentTopicName() != null)
			return value.getParentTopicName();

		return "";
	}

	private SafeUri findDifficultyLevel(QuestionProxy value) {

		if (value.getDifficultyLevelId() == null)
			return UriUtils.fromSafeConstant("----");

		if (value.getDifficultyLevelId().longValue() == DifficultyLevels.EASY
				.getId()) {
			return UriUtils
					.fromSafeConstant("client_resources/images/asan.svg");
		}
		if (value.getDifficultyLevelId().longValue() == DifficultyLevels.NORMAL
				.getId()) {
			return UriUtils
					.fromSafeConstant("client_resources/images/motevasset.svg");
		}
		if (value.getDifficultyLevelId().longValue() == DifficultyLevels.DIFFICULT
				.getId()) {
			return UriUtils
					.fromSafeConstant("client_resources/images/sakht.svg");
		}
		if (value.getDifficultyLevelId().longValue() == DifficultyLevels.VERYDIFFICULT
				.getId()) {
			return UriUtils
					.fromSafeConstant("client_resources/images/kheili-sakht.svg");
		}

		GWT.log("Diffiulty Level is null");
		return UriUtils.fromSafeConstant("");
	}

	private boolean isLTR(String text) {
		if (text == null) {
			return false;
		}
		String t = text.toLowerCase();
		return t.contains("dir=\"ltr\"");
	}

}