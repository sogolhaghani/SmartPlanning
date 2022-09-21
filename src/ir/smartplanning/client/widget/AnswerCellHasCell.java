package ir.smartplanning.client.widget;

import ir.smartplanning.shared.proxies.nonpersists.QuestionProxy;

import java.util.Map;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;

public class AnswerCellHasCell implements
		HasCell<QuestionProxy, QuestionProxy> {
	AbstractCell<QuestionProxy> cell;

	public AnswerCellHasCell(Map<Long, RenderingQuestionStates> answerSheet, CellListTypes cellListType) {

		this.cell = new AnswerCell(answerSheet, cellListType);
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
		return object;
	}

}
