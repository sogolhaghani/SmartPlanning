package ir.smartplanning.client.widget;

import ir.smartplanning.shared.proxies.nonpersists.QuestionProxy;

import java.util.Map;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;
import com.google.web.bindery.event.shared.EventBus;

public class OptionCellHasCell implements
		HasCell<QuestionProxy, QuestionProxy> {
	AbstractCell<QuestionProxy> cell;
	Integer optionNo;
	boolean isClickable;
	public Integer getOptionNo() {
		return optionNo;
	}
	public boolean isClickable() {
		return isClickable;
	}
	public OptionCellHasCell(Integer optionNo, EventBus eventBus,
			Map<Long, RenderingQuestionStates> answerSheet, CellListTypes cellTypes) {
		
		this.optionNo = optionNo;
		if (cellTypes.getId() == CellListTypes.Exam.getId() )
			isClickable=true;
		else 
			isClickable=false;
			this.cell = new OptionCell(eventBus, optionNo, answerSheet,isClickable);
		
	}

	public OptionCellHasCell(Integer optionNo, EventBus eventBus, CellListTypes cellTypes) {
		if (cellTypes.getId() == CellListTypes.Exam.getId())
			isClickable=true;
		else 
			isClickable=false;
		this.optionNo = optionNo;
		this.cell = new OptionCell(eventBus, optionNo,isClickable);

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

