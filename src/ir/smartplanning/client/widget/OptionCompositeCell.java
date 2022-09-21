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


public class OptionCompositeCell extends CompositeCell<QuestionProxy> {
	List<HasCell<QuestionProxy, ?>> hasCells;


	public OptionCompositeCell(List<HasCell<QuestionProxy, ?>> hasCells) {
		super(hasCells);
		this.hasCells = hasCells;
	}
	private String dir;

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, QuestionProxy value, SafeHtmlBuilder sb) {

		String className = "";
		if (isLTR(value)) {
			this.dir = "LTR";
			className = "option_composit_cell_box text_align_left";
		} else {
			this.dir = "RTL";
			className = "option_composit_cell_box";
		}
		if (value.getQuestionTypeId().longValue() != QuestionTypes.MULTIPLECHOICE.getId()) {
			className="";
		}

		sb.appendHtmlConstant("<div elementname='question" + value.getId() + "' dir='" + this.dir + "'><div class=\"" + className + "\"  dir='" + this.dir
				+ "'>");
		for (int i = 0; i < hasCells.size(); i++) {
			render(context, value, sb, hasCells.get(i));
		}
		sb.appendHtmlConstant("</div></div>");
	}

	private boolean isLTR(QuestionProxy value) {

		String t = value.getQuestion().toLowerCase();
		return t.contains("dir=\"ltr\"");
	}

	@Override
	protected <X> void render(com.google.gwt.cell.client.Cell.Context context, QuestionProxy value, SafeHtmlBuilder sb, HasCell<QuestionProxy, X> hasCell) {

		Cell<X> cell = hasCell.getCell();
		cell.render(context, hasCell.getValue(value), sb);
	}

	@Override
	protected Element getContainerElement(Element parent) {
		try {
			return super.getContainerElement(parent).getFirstChildElement();
			// .getFirstChildElement();
		} catch (Exception e) {
			Window.alert(e.getMessage());
			return null;
		}

	}

	@Override
	public void onBrowserEvent(com.google.gwt.cell.client.Cell.Context context, Element parent, QuestionProxy value, NativeEvent event,
			ValueUpdater<QuestionProxy> valueUpdater) {
		try {
			super.onBrowserEvent(context, parent, value, event, valueUpdater);
		} catch (Exception e) {
			Window.alert(e.getMessage());
		}
	}
}
