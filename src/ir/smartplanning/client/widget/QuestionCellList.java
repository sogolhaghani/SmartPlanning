package ir.smartplanning.client.widget;

import ir.smartplanning.client.JSNI;
import ir.smartplanning.client.Messages;
import ir.smartplanning.client.event.CurrentPageChangedEvent;
import ir.smartplanning.client.event.ExamNavigatorEvent;
import ir.smartplanning.shared.MyRequestFactory.ExamRequestContext;
import ir.smartplanning.shared.enums.QuestionTypes;
import ir.smartplanning.shared.proxies.nonpersists.QuestionAnswerProxy;
import ir.smartplanning.shared.proxies.nonpersists.QuestionProxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.cell.client.HasCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ScrollEvent;
import com.google.gwt.user.client.Window.ScrollHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.RangeChangeEvent;
import com.google.gwt.view.client.RangeChangeEvent.Handler;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class QuestionCellList extends Composite {

	boolean isSearchGoing = false;
	private Provider<ExamRequestContext> examRequestContext;
//	private Long afterExamExamRequestId;
	private Map<Long, RenderingQuestionStates> answerSheet;
	private CellList<QuestionProxy> questionList;
	private PagerPanel pager;
	private VerticalPanel panel;
	private List<HasCell<QuestionProxy, ?>> cells;
	private FlexTable commandTable;
	private QuestionCellListCache cache;
	final EventBus eventBus;
	private CellListTypes cellListType;
	private Label lblSearchReport;
	private HorizontalPanel fixPager;
	private Label previous;
	private Label next;
	private Label info;

	@SuppressWarnings("deprecation")
	public QuestionCellList(final CellListTypes cellListTypes) {
		this.cache = new QuestionCellListCache();
		this.eventBus = new SimpleEventBus();
		this.commandTable = new FlexTable();
		this.cellListType = cellListTypes;
		this.lblSearchReport = new Label();
		lblSearchReport.setDirection(Direction.RTL);
		this.answerSheet = new HashMap<Long, RenderingQuestionStates>();
		this.panel = new VerticalPanel();
		panel.setStyleName("scrollpanel");
		this.panel.addStyleName("mainpanel");
		prepareCells();
		initializeQuestionCellList();
		addHandlers();
		this.pager = new PagerPanel("سوال", "سوال", "pager_detail_wide");
		this.pager.getElement().setAttribute("id", "question_list_pager_id");
		initializePager();
		commandTable.setStyleName("command_table");
		commandTable.getCellFormatter().addStyleName(0, 0,
				".command_table_cell_righ");
		commandTable.getCellFormatter().addStyleName(0, 1,
				"command_table_cell_cente");
		commandTable.getCellFormatter().addStyleName(0, 2,
				"command_table_cell_left");
		commandTable.setWidget(0, 0, pager);
		commandTable.setWidget(0, 2, this.lblSearchReport);

		this.panel.setHorizontalAlignment(HorizontalAlignmentConstant
				.startOf(Direction.RTL));
		this.panel.add(questionList);
		VerticalPanel vp = new VerticalPanel();

		vp.add(commandTable);
		if (this.cellListType.getId() != CellListTypes.AfterExam.getId())
			vp.add(this.panel);
		else {
			vp.add(panel);
		}
		scrollHandlerForShowingPager(vp);
		this.initWidget(vp);
	}

	private void scrollHandlerForShowingPager(final VerticalPanel vp) {
		info = new Label();
		next = new Label();
		previous = new Label();
		next.addStyleName("pager_buttons");
		previous.addStyleName("pager_buttons");
		fixPager = sliderPager(next, previous, info);
		vp.add(fixPager);

		if (cellListType.getId() == CellListTypes.AfterExam.getId()) {
			return;
		}
		Window.addWindowScrollHandler(new ScrollHandler() {

			@Override
			public void onWindowScroll(ScrollEvent event) {
				fixPager.getElement().setAttribute("style",
						"width:" + vp.getOffsetWidth() + "px");
				if (pager.getOffsetHeight() != 0)
					if (pager.getAbsoluteTop() <= Window.getScrollTop()) {
						fixPager.addStyleName("fix_pager_visible");
						next.addStyleName("next");
						previous.addStyleName("previous");
						info.addStyleName("info");
						info.setText(pager.getDetail());

					} else {
						hidePagerPanel();
					}
			}

		});
	}

	public void hidePagerPanel() {
		fixPager.removeStyleName("fix_pager_visible");
		next.removeStyleName("next");
		previous.removeStyleName("previous");
		info.removeStyleName("info");
		info.setText("");

	}

	private HorizontalPanel sliderPager(Label next, Label previous,
			final Label info) {
		final HorizontalPanel fixPager = new HorizontalPanel();
		fixPager.addStyleName("fix_pager_root");
		info.setText(pager.getDetail());
		fixPager.add(previous);
		fixPager.add(info);
		fixPager.setCellHorizontalAlignment(previous,
				HasHorizontalAlignment.ALIGN_RIGHT);
		fixPager.setCellHorizontalAlignment(info,
				HasHorizontalAlignment.ALIGN_CENTER);
		fixPager.setCellHorizontalAlignment(next,
				HasHorizontalAlignment.ALIGN_LEFT);
		fixPager.add(next);
		next.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (pager.hasNextPage())
					pager.nextPage();
				info.setText(pager.getDetail());
			}
		});

		previous.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				pager.previousPage();
				info.setText(pager.getDetail());
			}
		});
		return fixPager;
	}

	private void initializeQuestionCellList() {
		QuestionCompositeCell qCell = new QuestionCompositeCell(cells,
				this.answerSheet);
		this.questionList = new CellList<QuestionProxy>(qCell);
		this.questionList
				.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
		this.questionList.setStyleName("mainpanel");

	}

	public void clearQuestionCellList() {
		for (int i = pager.getPage(); i >= 0; i--) {
			questionList.setRowCount(0);
		}

	}

	protected void setQuestionRowNumbers(List<CacheItem> questions) {
		for (CacheItem item : questions) {
			RenderingQuestionStates state = answerSheet.get(item.getQuestion()
					.getId());
			if (state == null) {
				state = new RenderingQuestionStates();
				state.setAnswerVisible(false);
			}
			if (item.getQuestion().getAnswerId() != null) {
				state.setSelectedOption(item.getQuestion().getAnswerId());
			}
			if (item.getQuestion().getQuestionTypeId().longValue() == QuestionTypes.READING_MULTIPLECHOICE
					.getId()
					|| item.getQuestion().getQuestionTypeId().longValue() == QuestionTypes.READING_EXPLANATORY
							.getId()) {
				state.setReading(true);
			}
			state.setQuestionNo(item.getQuestionNo());
			answerSheet.put(item.getQuestion().getId(), state);

		}
		if (this.cellListType.getId() == CellListTypes.Exam.getId()) {
			this.eventBus.fireEvent(new ExamNavigatorEvent());
		}

	}

	private void initializePager() {

		this.pager.setStyleName("pager");
		this.pager.setDisplay(questionList);
		this.pager.startLoading();

	}

	private void addHandlers() {
		if (cellListType.getId() == CellListTypes.Exam.getId()) {
			this.questionList.addRangeChangeHandler(new examRangeChange());
		}
	}

	public CellListTypes getCellListType() {
		return cellListType;

	}

	public EventBus getEventBus() {
		return eventBus;
	}

	private void prepareCells() {
		this.cells = new ArrayList<HasCell<QuestionProxy, ?>>();
		OptionCompositeCellHasCell options = new OptionCompositeCellHasCell(
				eventBus, answerSheet, cellListType);
		this.cells.add(new ContentCellHasCell(this.answerSheet));
		this.cells.add(options);
		if (cellListType.getId() != CellListTypes.Exam.getId()) {
			this.cells
					.add(new AnswerCellHasCell(this.answerSheet, cellListType));
			this.cells.add(new CommandBarCompositeCellHasCell(answerSheet,
					eventBus, cellListType));
		}

	}

	Timer timer = new Timer() {
		@Override
		public void run() {
			try {
				JSNI.mathJaxNative("refreshMath", "equation", "");
			} catch (Exception e) {
				Messages.ShowNoty(e.getMessage(), Messages.ERORR);
				try {
					JSNI.mathJaxNative("refreshMath", "equation", "");
				} catch (Exception ex) {
				}
			}

		}
	};
	private int currentBookletQuestionNo;

	private void renderFormula() {
		timer.schedule(400);
		timer.run();
	}

	public void resetCellList(Long examId,
			Provider<ExamRequestContext> examQuestionRequestContextProvider,
			int questionCount, int rowCount) {
		currentBookletQuestionNo = questionCount;
//		isSimilarFinderPage = false;
		this.cellListType = CellListTypes.AfterExam;
		this.examRequestContext = examQuestionRequestContextProvider;
//		this.afterExamExamRequestId = examId;
		initializePager();
		pager.getDisplay().setRowCount(rowCount, true);
		pager.setQuestionCount(questionCount);
		clearTheCache();
		this.questionList.setVisibleRangeAndClearData(new Range(0, 5), true);
		this.answerSheet.clear();

	}

	public void redrawCurrentPage() {
		this.questionList.setVisibleRangeAndClearData(
				questionList.getVisibleRange(), true);

	}

	public void resetCellListForExam(
			Provider<ExamRequestContext> examQuestionRequestContextProvider,
			int currentBookletTotalQuestionNo,
			List<QuestionAnswerProxy> submittedAnswers, int rowCount) {
		currentBookletQuestionNo = currentBookletTotalQuestionNo;
		lblSearchReport.setText("");
		this.examRequestContext = examQuestionRequestContextProvider;
		initializePager();
		clearTheCache();
		this.answerSheet.clear();
		if (submittedAnswers != null)
			insertSubmittedAnswersIntoAnswerSheet(submittedAnswers);
		questionList.setRowCount(rowCount, true);
		this.questionList.setVisibleRangeAndClearData(new Range(0, 5), true);

	}

	public void clearTheCache() {
		if (this.cache != null) {
			this.cache.clear();
		} else {
			cache = new QuestionCellListCache();
		}
	}

	private void insertSubmittedAnswersIntoAnswerSheet(
			List<QuestionAnswerProxy> submittedAnswers) {
		for (QuestionAnswerProxy questionAnswer : submittedAnswers) {
			RenderingQuestionStates renderingQustionState = new RenderingQuestionStates();
			renderingQustionState.setSelectedOption(questionAnswer
					.getSelectedChoiceID());
			this.answerSheet.put(questionAnswer.getQuestionID().longValue(),
					renderingQustionState);
		}
	}

	class examRangeChange implements Handler {

		@Override
		public void onRangeChange(RangeChangeEvent event) {
			Window.scrollTo(0, 0);
			try {
				eventBus.fireEvent(new CurrentPageChangedEvent());
				Range range = event.getNewRange();

				final int start = range.getStart();
				int len = range.getLength();
				if (cache.isEmpty() == false) {
					List<QuestionProxy> result = cache.getQuestions(start, len);
					if (result == null || result.size() == 0) {
						loadFromServer(start);
					} else {

						pager.setStartQuestionNo(result.get(0).getRowNo());
						if (QuestionTypes.isReading(result.get(
								result.size() - 1).getQuestionTypeId()) == true
								&& result.size() > 1) {
							pager.setEndQuestionNo(result
									.get(result.size() - 2).getRowNo());
						} else
							pager.setEndQuestionNo(result
									.get(result.size() - 1).getRowNo());
						setQuestionRowNumbers(cache.getCachedItems(start, len));
						questionList.setRowData(start, result);

						pager.refreshPagerInfo();
						renderFormula();
					}
				} else {
					loadFromServer(start);
				}
			} catch (Exception e) {
			}

		}

		private void loadFromServer(int start) {
			examRequestContext
					.get()
					.getNextPart(start)
					.fire(new examQuestionReceiver(start,
							currentBookletQuestionNo));
		}

		short noReload = 0;

		class examQuestionReceiver extends Receiver<List<QuestionProxy>> {

			final int start;
			final int currentBookletQuestionNo;

			public examQuestionReceiver(int start,
					int currentBookletTotalQuestionNo) {
				this.currentBookletQuestionNo = currentBookletTotalQuestionNo;
				this.start = start;
			}

			@Override
			public void onFailure(ServerFailure error) {

			}

			@Override
			public void onSuccess(List<QuestionProxy> response) {
				try {
					noReload = 0;
					if (response == null) {
						return;
					}
					if (response.size() > 0) {
						cache.populateCache(response, start);
						List<QuestionProxy> questions = cache.getQuestions(
								start, response.size());
						setQuestionRowNumbers(cache.getCachedItems(start,
								response.size()));
						if (start == 0) {
							pager.setQuestionCount(response.get(0).getRowNo()
									+ currentBookletQuestionNo - 1);
						}
						pager.setStartQuestionNo(response.get(0).getRowNo());
						if (QuestionTypes.isReading(response.get(
								response.size() - 1).getQuestionTypeId()) == true
								&& response.size() > 1) {
							pager.setEndQuestionNo(response.get(
									response.size() - 2).getRowNo());
						} else
							pager.setEndQuestionNo(response.get(
									response.size() - 1).getRowNo());
						questionList.setRowData(this.start, questions);
						pager.refreshPagerInfo();
					} else {

					}

				} catch (Exception e) {
					Messages.ShowNoty(e.getMessage(), Messages.ERORR);
				} finally {
					isSearchGoing = false;
					renderFormula();
				}
			}

		}
	}

	public QuestionCellListCache getCache() {
		return cache;
	}

	public Map<Long, RenderingQuestionStates> getAnswerSheet() {
		return answerSheet;
	}
}
