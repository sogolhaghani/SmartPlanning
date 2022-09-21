package ir.smartplanning.client.widget;

import ir.smartplanning.shared.proxies.nonpersists.QuestionProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;
import com.google.web.bindery.event.shared.EventBus;

public class CommandBarCompositeCellHasCell implements
		HasCell<QuestionProxy, QuestionProxy> {
	CommandBarCompositeCell cell;
	List<HasCell<QuestionProxy, ?>> hasCells;

	public CommandBarCompositeCellHasCell(
			Map<Long, RenderingQuestionStates> answerSheet, EventBus eventBus,
			CellListTypes cellListType) {
		this.hasCells = new ArrayList<HasCell<QuestionProxy, ?>>();


		if (cellListType.getId() != CellListTypes.Exam.getId()) {
			this.hasCells.add(new SummaryCellHasCell());
		}
		this.cell = new CommandBarCompositeCell(this.hasCells);
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
