package ir.smartplanning.client.widget;

import ir.smartplanning.shared.proxies.nonpersists.QuestionProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;

public class OptionCompositeCellHasCell implements
		HasCell<QuestionProxy, QuestionProxy> {
	OptionCompositeCell cell;
	List<HasCell<QuestionProxy, ?>> hasCells;
	Map<Long, RenderingQuestionStates> answerSheet;

	public OptionCompositeCellHasCell(EventBus eventBus,CellListTypes cellListType) {
		this.hasCells = new ArrayList<HasCell<QuestionProxy, ?>>();
		this.hasCells.add(new EachOptionCompositeCellHasCell(1, eventBus,cellListType));
		this.hasCells.add(new EachOptionCompositeCellHasCell(2, eventBus,cellListType));
		this.hasCells.add(new EachOptionCompositeCellHasCell(3, eventBus,cellListType));
		this.hasCells.add(new EachOptionCompositeCellHasCell(4, eventBus,cellListType));
		this.cell = new OptionCompositeCell(this.hasCells);
	}

	public OptionCompositeCellHasCell(EventBus eventBus ,Map<Long, RenderingQuestionStates> answerSheet,CellListTypes cellListType) {
		this.hasCells = new ArrayList<HasCell<QuestionProxy, ?>>();
		this.hasCells.add(new EachOptionCompositeCellHasCell(1, eventBus, answerSheet,cellListType));
		this.hasCells.add(new EachOptionCompositeCellHasCell(2, eventBus, answerSheet,cellListType));
		this.hasCells.add(new EachOptionCompositeCellHasCell(3, eventBus, answerSheet,cellListType));
		this.hasCells.add(new EachOptionCompositeCellHasCell(4, eventBus, answerSheet,cellListType));
		this.cell = new OptionCompositeCell(this.hasCells);
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
