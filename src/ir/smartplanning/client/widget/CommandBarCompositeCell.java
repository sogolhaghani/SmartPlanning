package ir.smartplanning.client.widget;

import java.util.List;

import ir.smartplanning.shared.enums.QuestionTypes;
import ir.smartplanning.shared.proxies.nonpersists.QuestionProxy;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;

public class CommandBarCompositeCell extends CompositeCell<QuestionProxy> {
	List<HasCell<QuestionProxy, ?>> hasCells;

	public CommandBarCompositeCell(List<HasCell<QuestionProxy, ?>> hasCells) {
		super(hasCells);
		this.hasCells = hasCells;
	}

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context,
			QuestionProxy value, SafeHtmlBuilder sb) {
		if (value == null)
			return;
		String styleName = "command_bar_composite_cell";

		if (value.getQuestionTypeId() == QuestionTypes.READING_EXPLANATORY
				.getId()
				|| value.getQuestionTypeId() == QuestionTypes.READING_MULTIPLECHOICE
						.getId())
			sb.appendHtmlConstant("<div><table><tr>");
		else {
			if (value.getCnt() != null) {
				sb.appendHtmlConstant("<div style=\"height:30px;border-bottom:1px dotted rgb(255,153,0);clear:both\"><table  width=\"100%\"><tr>");
			} else
				sb.appendHtmlConstant("<div style=\"height:30px;border-bottom:1px dotted rgb(255,153,0);clear:both\"><table  class=\""
						+ styleName + "\" width=\"100%\"><tr>");
		}

		for (HasCell<QuestionProxy, ?> hasCell : hasCells) {

			render(context, value, sb, hasCell);
		}
		sb.appendHtmlConstant("</tr></table ></div>");
	}

	@Override
	protected <X> void render(com.google.gwt.cell.client.Cell.Context context,
			QuestionProxy value, SafeHtmlBuilder sb,
			HasCell<QuestionProxy, X> hasCell) {
		if (value == null)
			return;
		Cell<X> cell = hasCell.getCell();
		sb.appendHtmlConstant("<td>");
		cell.render(context, hasCell.getValue(value), sb);
		sb.appendHtmlConstant("</td>");
	}

	@Override
	protected Element getContainerElement(Element parent) {
		try {
			return super.getContainerElement(parent).getFirstChildElement()
					.getFirstChildElement().getFirstChildElement();
		} catch (Exception e) {
			Window.alert(e.getMessage());
			return null;
		}

	}

	@Override
	public void onBrowserEvent(com.google.gwt.cell.client.Cell.Context context,
			Element parent, QuestionProxy value, NativeEvent event,
			ValueUpdater<QuestionProxy> valueUpdater) {
		super.onBrowserEvent(context, parent, value, event, valueUpdater);
	}

}
