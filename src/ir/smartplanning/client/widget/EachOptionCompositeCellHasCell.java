package ir.smartplanning.client.widget;

import ir.smartplanning.shared.proxies.nonpersists.QuestionProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;
import com.google.web.bindery.event.shared.EventBus;

public class EachOptionCompositeCellHasCell implements
		HasCell<QuestionProxy, QuestionProxy> {
	EachOptionCompositeCell cell;
	List<HasCell<QuestionProxy, ?>> hasCells;
	Map<Long, RenderingQuestionStates> answerSheet;
	int optionNo;

	public EachOptionCompositeCellHasCell(int optionNo,EventBus eventBus,CellListTypes cellListType) {
		this.hasCells = new ArrayList<HasCell<QuestionProxy, ?>>();
		this.hasCells.add(new OptionContentCellHasCell(optionNo));	
		this.hasCells.add(new OptionCellHasCell(optionNo, eventBus,cellListType));
		this.cell = new EachOptionCompositeCell(this.hasCells,optionNo);
	}

	public EachOptionCompositeCellHasCell(int optionNo,EventBus eventBus ,Map<Long, RenderingQuestionStates> answerSheet,CellListTypes cellListType) {
		this.hasCells = new ArrayList<HasCell<QuestionProxy, ?>>();
		this.hasCells.add(new OptionCellHasCell(optionNo, eventBus, answerSheet,cellListType));
		this.hasCells.add(new OptionContentCellHasCell(optionNo));
		this.cell = new EachOptionCompositeCell(this.hasCells,optionNo);
		this.answerSheet = answerSheet;
	}

	@Override
	public Cell<QuestionProxy> getCell() {
		return this.cell;
	}

	@Override
	public FieldUpdater<QuestionProxy,QuestionProxy> getFieldUpdater() {
		return null;
	}

	@Override
	public QuestionProxy getValue(QuestionProxy object) {
		return object;
	}

}


