package ir.smartplanning.client.widget;

import ir.smartplanning.shared.proxies.nonpersists.QuestionProxy;

import java.util.List;
import java.util.Map;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;

public class QuestionCompositeCell extends CompositeCell<QuestionProxy> {

	List<HasCell<QuestionProxy, ?>> hasCells;
	Map<Long, RenderingQuestionStates> answerSheet;

	public QuestionCompositeCell(List<HasCell<QuestionProxy, ?>> hasCells,
			Map<Long, RenderingQuestionStates> answerSheet) {
		super(hasCells);
		this.hasCells = hasCells;
		this.answerSheet = answerSheet;
	}

	private boolean isLTR(QuestionProxy value) {

		String t = value.getQuestion().toLowerCase();
		return t.contains("dir=\"ltr\"");
	}

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context,
			QuestionProxy value, SafeHtmlBuilder sb) {
		if (value == null)
			return;
		if (isLTR(value))
			sb.appendHtmlConstant("<div  class=\""
					+ "question_composite_cell_box_english" + "\">");
		else if (value.getModuleName() != null)
			if (value.getModuleName().contains("عربي")) {

				sb.appendHtmlConstant("<div  class=\""
						+ "question_composite_cell_box_arabic\">");
			} else {
				sb.appendHtmlConstant("<div  class=\""
						+ "question_composite_cell_box\">");
			}
		else {
			sb.appendHtmlConstant("<div  class=\"question_composite_cell_box\">");
		}

		for (HasCell<QuestionProxy, ?> hasCell : hasCells) {

			render(context, value, sb, hasCell);
		}
		sb.appendHtmlConstant("</div>");
	}

	@Override
	protected <X> void render(com.google.gwt.cell.client.Cell.Context context,
			QuestionProxy value, SafeHtmlBuilder sb,
			HasCell<QuestionProxy, X> hasCell) {
		if (value == null)
			return;
		Cell<X> cell = hasCell.getCell();
		cell.render(context, hasCell.getValue(value), sb);
	}

	@Override
	protected Element getContainerElement(Element parent) {
		try {
			return super.getContainerElement(parent).getFirstChildElement();
		} catch (Exception e) {
			Window.alert(e.getMessage());
			return null;
		}

	}

}
