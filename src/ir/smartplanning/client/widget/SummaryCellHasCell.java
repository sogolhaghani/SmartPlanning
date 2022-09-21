package ir.smartplanning.client.widget;

import ir.smartplanning.shared.proxies.nonpersists.QuestionProxy;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;

public class SummaryCellHasCell implements
		HasCell<QuestionProxy, QuestionProxy> {
	AbstractCell<QuestionProxy> cell;

	public SummaryCellHasCell() {
		cell=new SummaryCell();
	}
	@Override
	public Cell<QuestionProxy> getCell() {
		return this.cell;
	}

	@Override
	public FieldUpdater<QuestionProxy, QuestionProxy> getFieldUpdater() {
		return null;
	}

	@Override
	public QuestionProxy getValue(QuestionProxy object) {
		// TODO Auto-generated method stub
		return object;
	}

}

