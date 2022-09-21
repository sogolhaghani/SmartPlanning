package ir.smartplanning.client.widget;

import ir.smartplanning.shared.proxies.nonpersists.QuestionProxy;

import com.google.gwt.cell.client.HasCell;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;

public class OptionContentCellHasCell implements
		HasCell<QuestionProxy, QuestionProxy> {
	AbstractCell<QuestionProxy> cell;
	Integer optionNo;

	public Integer getOptionNo() {
		return optionNo;
	}

	public OptionContentCellHasCell(Integer optionNo) {
		
		this.optionNo = optionNo;

			this.cell = new OptionContentCell( optionNo);
		
	}

	
	@Override
	public Cell<QuestionProxy> getCell() {
		return this.cell;
	}


	@Override
	public QuestionProxy getValue(QuestionProxy object) {
		return object;
	}

	@Override
	public FieldUpdater<QuestionProxy, QuestionProxy> getFieldUpdater() {
		return null;
	}
}