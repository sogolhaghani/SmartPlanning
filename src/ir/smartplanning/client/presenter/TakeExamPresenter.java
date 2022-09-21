//package ir.smartplanning.client.presenter;
//
//import ir.smartplanning.client.MyGateKeeper;
//import ir.smartplanning.client.event.CurrentPageChangedEvent;
//import ir.smartplanning.client.event.ExamNavigatorEvent;
//import ir.smartplanning.client.event.SelectingOptionEvent;
//import ir.smartplanning.client.place.NameTokens;
//import ir.smartplanning.client.widget.QuestionCellList;
//import ir.smartplanning.shared.proxies.nonpersists.ExamBookletTotalProxy;
//import ir.smartplanning.shared.proxies.nonpersists.QuestionAnswerProxy;
//
//import java.util.List;
//import java.util.Map;
//
//import com.google.gwt.event.dom.client.ClickEvent;
//import com.google.gwt.event.dom.client.ClickHandler;
//import com.google.gwt.user.client.Window;
//import com.google.gwt.user.client.Window.ClosingEvent;
//import com.google.gwt.user.client.Window.ClosingHandler;
//import com.google.gwt.user.client.ui.Button;
//import com.google.gwt.user.client.ui.HTMLPanel;
//import com.google.gwt.user.client.ui.Label;
//import com.google.inject.Inject;
//import com.google.web.bindery.event.shared.EventBus;
//import com.google.web.bindery.event.shared.HandlerRegistration;
//import com.gwtplatform.mvp.client.Presenter;
//import com.gwtplatform.mvp.client.View;
//import com.gwtplatform.mvp.client.annotations.NameToken;
//import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
//import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
//import com.gwtplatform.mvp.client.proxy.PlaceManager;
//import com.gwtplatform.mvp.client.proxy.ProxyPlace;
//
//public class TakeExamPresenter extends
//		Presenter<TakeExamPresenter.MyView, TakeExamPresenter.MyProxy> {
//
//	public interface MyView extends View {
//		public HTMLPanel getExamBookletInfo();
//
//		public Button getGetOrGiveBooklet();
//
//		public Label getExamTimer();
//
//		public HTMLPanel getExamStaticInfo();
//
//		public QuestionCellList getQuestionCellList();
//
//		public Label getStartTimer();
//
//		public HTMLPanel getDetailWrapper();
//
//		public HTMLPanel getNavigator();
//
//		public Label getMajor();
//
//		public Label getExamType();
//
//		public HTMLPanel getDetails();
//
//		public HTMLPanel getBody();
//	}
//
//	@Inject
//	PlaceManager placeManager;
////	private final Provider<ExamRequestContext> examRequestContextProvider;
//	private String caller;
//	private ExamBookletTotalProxy currentBookletTotal;
//	private byte currentBookletNo = 1;
//	private boolean wasAtExam = false;
//	private boolean isSelfExam;
////	private States currentBookletState;
//	private final int SCHEDULE_SECONDS = 10;
//	private int currentBookletTotalQuestionNo;
//	private int cellListRowCount;
//	private List<QuestionAnswerProxy> submittedAnswers;
//	private EventBus eventBus;
//	private int bookletCount;
//	private Map<Long, QuestionAnswerProxy> notSentQAs;
//	private Map<Long, Long> sentAnswers;
//	private HandlerRegistration handlerRegistarion;
//	private short StartingExamFailsCounter = 0;
//	private short gettingNextBookletCounter = 0;
//	private short deliveringLastBookletCounter = 0;
//	private short submitAnswerCounter = 0;
//	private Long refId = null;
//	private Byte bookletType = null;
//	private int prevState = 0; // 1 back browser button has been clicked
//	private Long examSerialNo = null;
//	protected int preloadSeconedBookletTime = ((Long) (MyGateKeeper.getUser()
//			.getId() % 115)).intValue();
//	private boolean deliveringBookeltFlag;
//
//	@ProxyCodeSplit
//	@NameToken(NameTokens.takeexam)
//	@UseGatekeeper(MyGateKeeper.class)
//	public interface MyProxy extends ProxyPlace<TakeExamPresenter> {
//	}
//
//	@Inject
//	public TakeExamPresenter(final EventBus eventBus, final MyView view,
//			/*final Provider<ExamRequestContext> examRequestContext,*/
//			final MyProxy proxy) {
//		super(eventBus, view, proxy);
////		this.examRequestContextProvider = examRequestContext;
//		this.eventBus = eventBus;
//		
//	getView().getGetOrGiveBooklet().addClickHandler(new ClickHandler() {
//		
//		@Override
//		public void onClick(ClickEvent event) {
//			getView().getGetOrGiveBooklet().getElement().setAttribute("diabled", "disabled");
//			if (isSelfExam && deliveringBookeltFlag == false) {
//				deliveringBookeltFlag = true;
//				if (currentBookletNo < bookletCount) {
////					gettingTheNextBooklet();
//				} else {
//					deliveringTheLastBooklet();
//				}
//			}
//		}
//	})	;
//		
//
//		getView()
//				.getQuestionCellList()
//				.getEventBus()
//				.addHandler(SelectingOptionEvent.TYPE,
//						new SelectingOptionEvent.SelectingOptionHandler() {
//							@Override
//							public void onSelectingOption(
//									SelectingOptionEvent event) {/*
//
//								ExamRequestContext context = examRequestContextProvider
//										.get();
//								QuestionAnswerProxy questionAnswer = context
//										.create(QuestionAnswerProxy.class);
//								questionAnswer.setSelectedChoiceID(event
//										.getOptionId());
//								questionAnswer.setQuestionID(event
//										.getQuestionId());
//								questionAnswer.setState(AnswerDBState.INSERTING
//										.getId());
//								notSentQAs.put(event.getQuestionId(),
//										questionAnswer);
//								int questionNo = getView()
//										.getQuestionCellList().getAnswerSheet()
//										.get(event.getQuestionId())
//										.getQuestionNo();
//								ExamNavigator examNavigator = (ExamNavigator) getView()
//										.getNavigator().getWidget(0);
//								if (event.getOptionId() == null) {
//									examNavigator
//											.unAnsweredQuestion(questionNo);
//								} else {
//									examNavigator.answeredQuestion(questionNo);
//								}
//
//							*/}
//						});
//
//		getView()
//				.getQuestionCellList()
//				.getEventBus()
//				.addHandler(ExamNavigatorEvent.TYPE,
//						new ExamNavigatorEvent.ExamNavigatorHandler() {
//
//							@Override
//							public void onExamNavigator(ExamNavigatorEvent event) {
//								ExamNavigator examNavigator = (ExamNavigator) getView()
//										.getNavigator().getWidget(0);
//								for (Long qId : getView().getQuestionCellList()
//										.getAnswerSheet().keySet()) {
//									if (getView().getQuestionCellList()
//											.getAnswerSheet().get(qId)
//											.isReading() == true) {
//										continue;
//									}
//									int questionNo = getView()
//											.getQuestionCellList()
//											.getAnswerSheet().get(qId)
//											.getQuestionNo();
//									if (questionNo == 0) {
//										continue;
//									}
//									examNavigator.setQuestionNo(questionNo);
//									if (getView().getQuestionCellList()
//											.getAnswerSheet().get(qId)
//											.getSelectedOption() == null) {
//										examNavigator
//												.commitedUnAnsweredQuestion(questionNo);
//									} else {
//										examNavigator
//												.commitedAnsweredQuestion(questionNo);
//									}
//
//								}
//
//							}
//						});
//		getView()
//				.getQuestionCellList()
//				.getEventBus()
//				.addHandler(
//						CurrentPageChangedEvent.TYPE,
//						new CurrentPageChangedEvent.CurrentPageChangedHandler() {
//
//							@Override
//							public void onCurrentPageChanged(
//									CurrentPageChangedEvent event) {
////								submitAnswer();
//
//							}
//						});
//
//		Window.addWindowClosingHandler(new ClosingHandler() {
//
//			@Override
//			public void onWindowClosing(ClosingEvent event) {
//				prevState = 2;
//				MyGateKeeper.logout();
//
//			}
//
//		});
//
//	}
//
//	protected void deliveringTheLastBooklet() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/*@Override
//	protected void onHide() {
//		if (MyGateKeeper.isInExam()) {
//			submitAnswer();
//		}
//		if (prevState != 2) {
//
//			prevState = 1;
//		} else {
//			prevState = 0;
//			getView().getQuestionCellList().clearTheCache();
//		}
//		handlerRegistarion.removeHandler();
//		getView().getQuestionCellList().hidePagerPanel();
//		super.onHide();
//	}
//
//	@Override
//	public void prepareFromRequest(PlaceRequest request) {
//		super.prepareFromRequest(request);
//		if (prevState == 1) {
//			prevState = 0;
//			return;
//		}
//		clearAllStyles();
//		refId = null;
//		bookletType = null;
//		examSerialNo = null;
//		StartingExamFailsCounter = 0;
//		gettingNextBookletCounter = 0;
//		deliveringLastBookletCounter = 0;
//		MyGateKeeper.setInExam(true);
//		String callerParameter = request.getParameter("caller", "");
//		caller = callerParameter;
//		try {
//			refId = Long.parseLong(request.getParameter("refID", ""));
//
//		} catch (Exception ex) {
//			refId = null;
//		}
//		MyGateKeeper.setCurrentExamRequestId(refId);
//		try {
//			bookletType = Byte.parseByte(request
//					.getParameter("bookletType", ""));
//		} catch (Exception e) {
//			bookletType = null;
//		}
//		try {
//			examSerialNo = Long.parseLong(request.getParameter("examSerialNo",
//					""));
//		} catch (Exception e) {
//			examSerialNo = null;
//		}
//		startExam();
//	}
//
//	int counterSubmitAndLogoutFailure = 0;
//
//	private void submitAnswerAndLogout() {
//		ExamRequestContext context = examRequestContextProvider.get();
//		List<QuestionAnswerProxy> questions = getNotSentQAs(context);
//
//		context.submitAndLogut(questions).fire(new Receiver<Void>() {
//
//			@Override
//			public void onSuccess(Void response) {
//				counterSubmitAndLogoutFailure = 0;
//			}
//
//			@Override
//			public void onFailure(ServerFailure error) {
//
//				if (counterSubmitAndLogoutFailure < 5) {
//					counterSubmitAndLogoutFailure++;
//					submitAnswerAndLogout();
//				}
//			}
//		});
//
//	}
//
//	private List<QuestionAnswerProxy> getNotSentQAs(ExamRequestContext context) {
//		List<QuestionAnswerProxy> result = new ArrayList<QuestionAnswerProxy>();
//		for (Long key : notSentQAs.keySet()) {
//			if (notSentQAs.get(key) != null
//					&& notSentQAs.get(key).getState().intValue() == AnswerDBState.INSERTED
//							.getId()) {
//				continue;
//			}
//			if (notSentQAs.get(key) != null
//					&& notSentQAs.get(key).getSelectedChoiceID() != null
//					&& notSentQAs.get(key).getSelectedChoiceID()
//							.equals(sentAnswers.get(key))) {
//				continue;
//			}
//			if (sentAnswers.get(key) != null
//					&& sentAnswers.get(key).equals(
//							notSentQAs.get(key).getSelectedChoiceID())) {
//				continue;
//			}
//			if (notSentQAs.get(key) == null && sentAnswers.get(key) == null) {
//				continue;
//			}
//			notSentQAs.get(key).setState(AnswerDBState.INSERTED.getId());
//			QuestionAnswerProxy questionAnswer = context
//					.create(QuestionAnswerProxy.class);
//			questionAnswer.setSelectedChoiceID(notSentQAs.get(key)
//					.getSelectedChoiceID());
//			questionAnswer.setQuestionID(notSentQAs.get(key).getQuestionID());
//
//			result.add(questionAnswer);
//		}
//		StringBuilder sb = new StringBuilder();
//
//		for (int i = 0; i < result.size(); i++) {
//			sb.append(result.get(i).getSelectedChoiceID());
//			sb.append(" ");
//		}
//		return result;
//	}
//
//	private void startExam() {
//		notSentQAs = new HashMap<Long, QuestionAnswerProxy>();
//		sentAnswers = new HashMap<Long, Long>();
//		examRequestContextProvider
//				.get()
//				.startExam(refId)
//				.with("questions", "examBookletTotals",
//						"examBookletTotals.overallInfo",
//						"examBookletTotals.details")
//				.fire(new Receiver<PreLoadedExamObjectProxy>() {
//					@Override
//					public void onFailure(ServerFailure error) {
//					}
//
//					@Override
//					public void onSuccess(PreLoadedExamObjectProxy response) {
//						if (checkRecevingBookletValidity(response) == false) {
//							clearExamAttributes();
//							returnToCaller();
//							return;
//						}
//						StartingExamFailsCounter = 0;
//						resetClientTimeWithFreshServerTime(response
//								.getExamBookletTotals());
//						currentBookletTotal = response.getExamBookletTotals();
//						settingFrontPageInfo(response.getExamBookletTotals());
//						if (wasAtExam == false) {
//							if (currentBookletState != States.FrontPage) {
//								setFrontPageStyles();
//							}
//							addClockHandler();
//						} else {
//							loadSubmittedAnswersFromServer();
//						}
//					}
//
//				});
//
//	}
//
//	protected void settingFrontPageInfo(ExamBookletTotalProxy response) {
//		isSelfExam = !response.getOverallInfo().getHasMultipleParticipants();
//		analyseExamBooklet(response);
//		setStaticInfo(response.getOverallInfo());
//		setOverallInfo(response);
//		setExamDetailsInfo(response.getDetails(), response.getOverallInfo());
//		getView().getMajor().setText(Majors.getMajors(MyGateKeeper.getUser().getMajorId()).getName());
//		if (ClientTimer.getServerTime().getTime() < response.getOverallInfo().getEndDateTime1().getTime()) {
//			getView().getExamType().setText("دفترچه " + "عمومی");
//		} else {
//			getView().getExamType().setText("دفترچه " + "اختصاصی");
//		}
//		final int questionCount = getBookletTotalQuestionNo(response.getDetails());
//		int previousBooklet = 0;
//		if (currentBookletNo == 2) {
//			previousBooklet = response.getOverallInfo().getQuestionCount() - questionCount;
//		}
//		ExamNavigator examNavigator = new ExamNavigator(questionCount, previousBooklet);
//		getView().getNavigator().clear();
//		getView().getNavigator().add(examNavigator);
//		currentBookletTotalQuestionNo = questionCount;
//		cellListRowCount = getCurrentBookletTotalCount(response.getOverallInfo());
//		submittedAnswers = null;
//
//	}
//
//	private int getCurrentBookletTotalCount(
//			ExamRequestOverallInfoProxy overallInfo) {
//		if (ClientTimer.getServerTime().getTime() <= overallInfo.getEndDateTime1().getTime()) {
//			if (overallInfo.getEndDateTime1().getTime() == overallInfo.getStartDateTime().getTime()) {
//				return overallInfo.getQuestionCount2();
//			}
//			return overallInfo.getQuestionCount1();
//		}
//
//		if (ClientTimer.getServerTime().getTime() <= overallInfo.getEndDateTime2().getTime()) {
//			return overallInfo.getQuestionCount2();
//		}
//		return overallInfo.getQuestionCount();
//	}
//
//	private int getBookletTotalQuestionNo(List<ListItemProxy> details) {
//		int sum = 0;
//		if (details != null) {
//			for (ListItemProxy examDetailProxy : details) {
//				sum += examDetailProxy.getId().intValue();
//			}
//		}
//		return sum;
//	}
//
//	private void setExamDetailsInfo(List<ListItemProxy> details,
//			ExamRequestOverallInfoProxy overallInfo) {
//		getView().getDetails().clear();
//		if (details == null || details.size() == 0) {
//			return;
//		}
//
//		HTMLPanel row1 = new HTMLPanel("");
//		Label cell1 = new Label();
//		Label cell2 = new Label();
//		HTMLPanel cell3 = new HTMLPanel("");
//		Label cell3_1 = new Label();
//		Label cell3_2 = new Label();
//		cell3.add(cell3_1);
//		cell3.add(cell3_2);
//		row1.add(cell1);
//		row1.add(cell2);
//		row1.add(cell3);
//
//		cell1.setStyleName("cell");
//		cell2.setStyleName("cell");
//		cell3.setStyleName("cell");
//
//		cell1.addStyleName("title_cell");
//		cell2.addStyleName("title_cell");
//		cell3.addStyleName("title_cell");
//
//		cell1.addStyleName("col_1");
//		cell2.addStyleName("col_2");
//		cell3.addStyleName("width_132");
//
//		cell3_1.addStyleName("label");
//		cell3_2.addStyleName("label");
//
//		cell1.setText("مواد امتحانی");
//		cell2.setText("تعداد سوالات");
//		cell3_1.setText("از شماره");
//		cell3_2.setText("تا شماره");
//
//		getView().getDetails().add(row1);
//
//		int start = 0;
//		int end = 0;
//		int prevBookletQuestionCount = 0;
//		if (currentBookletNo == 2) {
//			prevBookletQuestionCount = overallInfo.getQuestionCount();
//
//			for (ListItemProxy item : details) {
//				prevBookletQuestionCount -= item.getId().intValue();
//			}
//			start = prevBookletQuestionCount;
//			end = prevBookletQuestionCount;
//		}
//
//		for (int i = 0; i < details.size(); i++) {
//			if (i != 0) {
//				start = end;
//				end = start + details.get(i).getId().intValue();
//			} else {
//				end = details.get(i).getId().intValue() + prevBookletQuestionCount;
//			}
//			HTMLPanel panel = new HTMLPanel("");
//			Label l1 = new Label();
//			Label l2 = new Label();
//			Label l3 = new Label();
//			Label l4 = new Label();
//
//			panel.add(l1);
//			panel.add(l2);
//			panel.add(l3);
//			panel.add(l4);
//
//			getView().getDetails().add(panel);
//			l1.setStyleName("cell");
//			l2.setStyleName("cell");
//			l3.setStyleName("cell");
//			l4.setStyleName("cell");
//
//			l1.addStyleName("title_cell");
//			l2.addStyleName("title_inner_cell");
//			l3.addStyleName("title_inner_cell");
//			l4.addStyleName("title_inner_cell");
//
//			l1.addStyleName("col_1");
//			l2.addStyleName("col_2");
////			l3.addStyleName(getView().getResStyle().examDetailInfoStyle().col_3_4());
////			l4.addStyleName(getView().getResStyle().examDetailInfoStyle().col_3_4());
//
//			l1.setText(details.get(i).getName().trim());
////			if (details.get(i).getName().trim().length() > 15) {
////				l1.addStyleName(getView().getResStyle().examDetailInfoStyle().line_height());
////			}
//			l2.setText(String.valueOf(details.get(i).getId()));
//			l3.setText(String.valueOf(start + 1));
//			l4.setText(String.valueOf(end));
//		}
//		HTMLPanel lastRow = new HTMLPanel("");
//		Label totalQuestion = new Label();
//		Label totalAnswering = new Label();
//
//		totalQuestion.setText("تعداد کل سوال ها " + " : " + String.valueOf(end));
//		if (overallInfo.getCurrentDate().getTime() < overallInfo.getEndDateTime1().getTime()) {
//			if (((overallInfo.getEndDateTime1().getTime() - overallInfo.getStartDateTime().getTime()) / 1000) % 60 > 0) {
//				totalAnswering.setText(" مدت زمان پاسخگویی "
//						+ String.valueOf(((overallInfo.getEndDateTime1().getTime() - overallInfo.getStartDateTime().getTime()) / 1000) / 60) + "  "
//						+ "دقیقه" + "  "
//						+ (((overallInfo.getEndDateTime1().getTime() - overallInfo.getStartDateTime().getTime()) / 1000) % 60) + "  "
//						+ "ثانیه");
//			} else {
//				totalAnswering.setText(" مدت زمان پاسخگویی "
//						+ String.valueOf(((overallInfo.getEndDateTime1().getTime() - overallInfo.getStartDateTime().getTime()) / 1000) / 60) + "  "
//						+ "دقیقه");
//			}
//		} else {
//			if (overallInfo.getCurrentDate().getTime() < overallInfo.getEndDateTime2().getTime()) {
//				if ((((overallInfo.getEndDateTime1().getTime() - overallInfo.getStartDateTime().getTime()) / 1000) % 60) > 0) {
//					totalAnswering.setText(" مدت زمان پاسخگویی "
//							+ String.valueOf(((overallInfo.getEndDateTime1().getTime() - overallInfo.getStartDateTime().getTime()) / 1000) / 60) + "  "
//							+ "دقیقه" + "  "
//							+ (((overallInfo.getEndDateTime1().getTime() - overallInfo.getStartDateTime().getTime()) / 1000) % 60) + "  "
//							+ "ثانیه");
//				} else {
//					totalAnswering.setText(" مدت زمان پاسخگویی "
//							+ String.valueOf(((overallInfo.getEndDateTime1().getTime() - overallInfo.getStartDateTime().getTime()) / 1000) / 60) + "  "
//							+ "دقیقه");
//				}
//			}
//			if ((((overallInfo.getEndDateTime2().getTime() - overallInfo.getEndDateTime1().getTime()) / 1000) % 60) > 0) {
//				totalAnswering.setText(" مدت زمان پاسخگویی "
//						+ (((overallInfo.getEndDateTime2().getTime() - overallInfo.getEndDateTime1().getTime()) / 1000) / 60) + "  "
//						+ "دقیقه" + "  "
//						+ (((overallInfo.getEndDateTime2().getTime() - overallInfo.getEndDateTime1().getTime()) / 1000) % 60) + "  "
//						+ "ثانیه");
//			} else {
//				totalAnswering.setText(" مدت زمان پاسخگویی "
//						+ (((overallInfo.getEndDateTime2().getTime() - overallInfo.getEndDateTime1().getTime()) / 1000) / 60)
//						+ "دقیقه");
//			}
//		}
//
//		lastRow.add(totalQuestion);
//		lastRow.add(totalAnswering);
//
//		totalQuestion.setStyleName("cell");
//		totalAnswering.setStyleName("cell");
//
//		totalQuestion.addStyleName("title_cell");
//		totalAnswering.addStyleName("title_cell");
//
//		totalQuestion.addStyleName("width_200");
//		totalAnswering.addStyleName("width_200");
//
//		getView().getDetails().add(lastRow);		
//	}
//
//	private void setOverallInfo(ExamBookletTotalProxy booklet) {
//		getView().getExamBookletInfo().clear();
//		Label cell1 = new Label();
//		Label cell2 = new Label();
//		Label cell3 = new Label();
//		cell1.setStyleName("cell");
//		cell2.setStyleName("cell");
//		cell3.setStyleName("cell");
//		getView().getExamBookletInfo().addStyleName("table");
//		getView().getExamBookletInfo().add(cell1);
//		getView().getExamBookletInfo().add(cell2);
//		getView().getExamBookletInfo().add(cell3);
//
//		cell1.getElement().setAttribute("style", "width:31%;text-align:right");
//		cell2.getElement().setAttribute("style", "width:31%;text-align:center");
//		cell3.getElement().setAttribute("style", "width:31%;text-align:left");
//
//		int start = 0;
//		int end = 0;
//		if (booklet != null && booklet.getDetails() != null) {
//			for (int i = 0; i < booklet.getDetails().size(); i++) {
//				if (i != 0) {
//					start = end;
//					end = (int) (start + booklet.getDetails().get(i).getId());
//				} else {
//					end = booklet.getDetails().get(i).getId().intValue();
//				}
//			}
//			cell1.setText("دفترچه " + currentBookletNo);
//			cell2.setText("تعداد سوالات دفترچه " + String.valueOf(end));
//			if (booklet.getOverallInfo().getCurrentDate().getTime() < booklet.getOverallInfo().getEndDateTime1().getTime()) {
//				if (((booklet.getOverallInfo().getEndDateTime1().getTime() - booklet.getOverallInfo().getStartDateTime().getTime()) / 1000) % 60 > 0) {
//					cell3.setText(" مدت زمان پاسخگویی "
//							+ String.valueOf(((booklet.getOverallInfo().getEndDateTime1().getTime() - booklet.getOverallInfo().getStartDateTime().getTime()) / 1000) / 60)
//							+ "  " + "دقیقه" + "  "
//							+ (((booklet.getOverallInfo().getEndDateTime1().getTime() - booklet.getOverallInfo().getStartDateTime().getTime()) / 1000) % 60)
//							+ "  " + "ثانیه");
//				} else {
//					cell3.setText(" مدت زمان پاسخگویی "
//							+ String.valueOf(((booklet.getOverallInfo().getEndDateTime1().getTime() - booklet.getOverallInfo().getStartDateTime().getTime()) / 1000) / 60)
//							+ "  " + "دقیقه");
//				}
//			} else {
//				if (booklet.getOverallInfo().getCurrentDate().getTime() < booklet.getOverallInfo().getEndDateTime2().getTime()) {
//					if ((((booklet.getOverallInfo().getEndDateTime1().getTime() - booklet.getOverallInfo().getStartDateTime().getTime()) / 1000) % 60) > 0) {
//						cell3.setText(" مدت زمان پاسخگویی "
//								+ String.valueOf(((booklet.getOverallInfo().getEndDateTime1().getTime() - booklet.getOverallInfo().getStartDateTime().getTime()) / 1000) / 60)
//								+ "  "
//								+ "دقیقه"
//								+ "  "
//								+ (((booklet.getOverallInfo().getEndDateTime1().getTime() - booklet.getOverallInfo().getStartDateTime().getTime()) / 1000) % 60)
//								+ "  " + "ثانیه");
//					} else {
//						cell3.setText(" مدت زمان پاسخگویی "
//								+ String.valueOf(((booklet.getOverallInfo().getEndDateTime1().getTime() - booklet.getOverallInfo().getStartDateTime().getTime()) / 1000) / 60)
//								+ "  " + "دقیقه");
//					}
//				}
//				if ((((booklet.getOverallInfo().getEndDateTime2().getTime() - booklet.getOverallInfo().getEndDateTime1().getTime()) / 1000) % 60) > 0) {
//					cell3.setText(" مدت زمان پاسخگویی "
//							+ (((booklet.getOverallInfo().getEndDateTime2().getTime() - booklet.getOverallInfo().getEndDateTime1().getTime()) / 1000) / 60)
//							+ "  " + "دقیقه" + "  "
//							+ (((booklet.getOverallInfo().getEndDateTime2().getTime() - booklet.getOverallInfo().getEndDateTime1().getTime()) / 1000) % 60)
//							+ "  " + "ثانیه");
//				} else {
//					cell3.setText(" مدت زمان پاسخگویی "
//							+ (((booklet.getOverallInfo().getEndDateTime2().getTime() - booklet.getOverallInfo().getEndDateTime1().getTime()) / 1000) / 60)
//							+ "دقیقه");
//				}
//			}
//
//		}		
//	}
//
//	private void setStaticInfo(ExamRequestOverallInfoProxy overallInfo) {
//		getView().getExamStaticInfo().clear();
//		Label cell1 = new Label();
//		Label cell2 = new Label();
//		Label cell3 = new Label();
//		Label cell4 = new Label();
//
//		cell1.setStyleName(".cell_label ");
//		cell2.setStyleName(".cell_label ");
//		cell3.setStyleName(".cell_label ");
//		cell4.setStyleName(".cell_label ");
//
//		getView().getExamStaticInfo().add(cell1);
//		getView().getExamStaticInfo().add(cell2);
//		getView().getExamStaticInfo().add(cell3);
//		getView().getExamStaticInfo().add(cell4);
//
//		cell1.getElement().setAttribute("style", "width:23%;text-align:right");
//		cell2.getElement().setAttribute("style", "width:23%;text-align:center");
//		cell3.getElement().setAttribute("style", "width:23%;text-align:center");
//		cell4.getElement().setAttribute("style", "width:23%;text-align:left");
//
//		getView().getExamStaticInfo().addStyleName("table");
//		cell1.setText("نام داوطلب : " + MyGateKeeper.getUser().getName() + " " + MyGateKeeper.getUser().getFamily());
////		cell2.setText("نوع آزمون : " + ExamRequestTypes.getName(overallInfo.getExamRequestTypeID().longValue()));
////		cell3.setText("نام آزمون : " + ExamSubjectTypes.getEnum(Integer.valueOf(overallInfo.getExamSubjectTypeID().toString())).getName());
//
//		if (overallInfo.getEndDateTime1().equals(overallInfo.getStartDateTime())) {
//			cell4.setText("تعداد دفترچه : 1");
//		} else if (overallInfo.getEndDateTime1().equals(overallInfo.getEndDateTime2())) {
//			cell4.setText("تعداد دفترچه : 1");
//		} else {
//			cell4.setText("تعداد دفترچه : 2");
//		}		
//	}
//
//	private void analyseExamBooklet(ExamBookletTotalProxy examTotal) {
//		ExamRequestOverallInfoProxy overallInfo = examTotal.getOverallInfo();
//		wasAtExam = overallInfo.isWasAtExam();
//		currentBookletNo = getCurrentBookletNo(overallInfo);
//		if (overallInfo.getEndDateTime1().equals(overallInfo.getStartDateTime())) {
//			bookletCount = 1;
//		} else if (overallInfo.getEndDateTime1().equals(overallInfo.getEndDateTime2())) {
//			bookletCount = 1;
//		} else {
//			bookletCount = 2;
//		}
//
//		return;		
//	}
//
//	private byte getCurrentBookletNo(ExamRequestOverallInfoProxy overallInfo) {
//		if (ClientTimer.getServerTime().getTime() <= overallInfo.getEndDateTime1().getTime()) {
//			return 1;
//		}
//		if (ClientTimer.getServerTime().getTime() < overallInfo.getEndDateTime2().getTime()) {
//			if (overallInfo.getStartDateTime().getTime() == overallInfo.getEndDateTime1().getTime()) {
//				return 1;
//			}
//			return 2;
//		}
//		return -1;
//
//	}
//
//	protected void resetClientTimeWithFreshServerTime(
//			ExamBookletTotalProxy response) {
//		if (response.getOverallInfo() == null) {
//			return;
//		}
//		Date serverDate = response.getOverallInfo().getCurrentDate();
//		if (serverDate != null) {
//			ClientTimer.setServerTime(serverDate);
//		}
//	}
//
//	protected boolean checkRecevingBookletValidity(
//			PreLoadedExamObjectProxy response) {
//		if (response == null || response.getExamBookletTotals() == null) {
//			return false;
//		}
//		if (response.getExamBookletTotals().getOverallInfo() == null) {
//			return false;
//		}
//		if (response.getExamBookletTotals().getResultType() != StartExamResultType.Ok) {
//			switch (response.getExamBookletTotals().getResultType()) {
//			case ThereIsNoOpenExam:
//				Messages.ShowNoty("آزمونی موجود نیست", Messages.STANDARD);
//				return false;
//			default:
//				return false;
//			}
//		}
//		return true;
//	}
//
//	private void gettingTheNextBooklet() {
//		loadNextBookletFromServer();
//	}
//
//	private void loadNextBookletFromServer() {
//		ExamRequestContext context = examRequestContextProvider.get();
//		final List<QuestionAnswerProxy> questions = getNotSentQAs(context);
//
//		context.getNextBooklet(questions).with("questions", "examBookletTotals", "examBookletTotals.overallInfo", "examBookletTotals.details")
//
//		.fire(new Receiver<PreLoadedExamObjectProxy>() {
//
//
//			@Override
//			public void onSuccess(PreLoadedExamObjectProxy response) {
//				getView().getGetOrGiveBooklet().getElement().removeAttribute("disabled");
//				gettingNextBookletCounter = 0;
//				if (response != null) {
//					resetClientTimeWithFreshServerTime(response.getExamBookletTotals());
//					updatingSentAnswers();
//					currentBookletTotal = response.getExamBookletTotals();
//					showTheBooklet(response);
//				}
//				deliveringBookeltFlag = false;
//			}
//
//			@Override
//			public void onFailure(ServerFailure error) {}
//		});
//
//	}
//
//	protected void showTheBooklet(PreLoadedExamObjectProxy response) {
//		settingFrontPageInfo(response.getExamBookletTotals());
//		if (checkRecevingBookletValidity(response) == false) {
//			clearExamAttributes();
//			returnToCaller();
//			return;
//		}
//		analyseExamBooklet(response.getExamBookletTotals());
//		wasAtExam = false;
//		setOverallInfo(response.getExamBookletTotals());
//		setExamDetailsInfo(response.getExamBookletTotals().getDetails(), response.getExamBookletTotals().getOverallInfo());
//		if (ClientTimer.getServerTime().getTime() < response.getExamBookletTotals().getOverallInfo().getEndDateTime1().getTime()) {
//			getView().getExamType().setText("عمومی");
//		} else {
//			getView().getExamType().setText("اختصاصی");
//		}
//		currentBookletTotalQuestionNo = getBookletTotalQuestionNo(response.getExamBookletTotals().getDetails());
//		cellListRowCount = getCurrentBookletTotalCount(response.getExamBookletTotals().getOverallInfo());
//		setFrontPageStyles();
//		
//	}
//
//	protected void clearExamAttributes() {
//		MyGateKeeper.setInExam(false);
//		MyGateKeeper.setCurrentExamRequestId(null);
//		getView().getNavigator().clear();
//	}
//
//	protected void returnToCaller() {
//		if (caller.equals("ClientTimer"))
//			caller = NameTokens.home;
//		PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken(caller).build();
//		placeManager.revealPlace(placeRequest);
//	}
//
//	protected void setFrontPageStyles() {
//		currentBookletState = States.FrontPage;
//		getView().getDetailWrapper().removeStyleName("display_none");
//		getView().getBody().addStyleName("complete_height");
//		getView().getQuestionCellList().addStyleName("display_none");
//		getView().getExamTimer().addStyleName("display_none");
//		getView().getGetOrGiveBooklet().addStyleName("display_none");
//		getView().getExamBookletInfo().addStyleName("display_none");
//		getView().getExamStaticInfo().addStyleName("display_none");
//		getView().getNavigator().addStyleName("display_none");
//
//	}
//
//	protected void addClockHandler() {
//		handlerRegistarion = Globals.clientTimer.addHandler(CurrentTimeEvent.TYPE, new CurrentTimeEvent.CurrentTimeHandler() {
//
//			@Override
//			public void onCurrentTime(CurrentTimeEvent event) {
//				if ((event.getCurrentTime().getTime() - currentBookletTotal.getOverallInfo().getStartDateTime().getTime())
//						% ((45 + (LoggedInGatekeeper.getCurrentUser().getId() % 30)) * 1000) == 0) {
//					CommonLogger.log(logger, Level.FINE, "submitting answers each ", (LoggedInGatekeeper.getCurrentUser().getId() % 46), " seconds...");
//					submitAnswer();
//				}
//				if ((event.getCurrentTime().getTime() <= (currentBookletTotal.getOverallInfo().getStartDateTime().getTime() + (SCHEDULE_SECONDS * 1000)))) {
//					if (currentBookletState != States.FrontPage) {
//						setFrontPageStyles();
//					}
//
//					refreshScheduleTimer((currentBookletTotal.getOverallInfo().getStartDateTime().getTime() + (SCHEDULE_SECONDS * 1000))
//							- event.getCurrentTime().getTime());
//					if (event.getCurrentTime().getTime() == (currentBookletTotal.getOverallInfo().getStartDateTime().getTime() + (SCHEDULE_SECONDS * 1000))) {
//						prepareContent(event);
//					}
//					return;
//				}
//				if (event.getCurrentTime().getTime() <= currentBookletTotal.getOverallInfo().getEndDateTime1().getTime()) {
//					refreshExamTime(currentBookletTotal.getOverallInfo().getEndDateTime1().getTime() - event.getCurrentTime().getTime());
//
//					prepareContent(event);
//					if (currentBookletTotal.getOverallInfo().getHasMultipleParticipants() == true)
//						if (event.getCurrentTime().getTime() == currentBookletTotal.getOverallInfo().getEndDateTime1().getTime() - preloadSeconedBookletTime
//								* 1000
//								&& currentBookletTotal.getOverallInfo().getEndDateTime1().getTime() != currentBookletTotal.getOverallInfo().getEndDateTime2()
//										.getTime()) {
//							CommonLogger.log(logger, Level.FINE, "preload the second booklet");
//							ClientTimer.preloadTheExamQuestions(preloadExamContextProvider.get(), refId, bookletType, examSerialNo,
//									(byte) (currentBookletNo + 1));
//							return;
//						}
//					return;
//				}
//
//				if (event.getCurrentTime().getTime() <= (currentBookletTotal.getOverallInfo().getEndDateTime1().getTime() + (SCHEDULE_SECONDS * 1000))) {
//
//					refreshScheduleTimer((currentBookletTotal.getOverallInfo().getEndDateTime1().getTime() + (SCHEDULE_SECONDS * 1000))
//							- event.getCurrentTime().getTime());
//					if (currentBookletState != States.FrontPage) {
//						currentBookletState = States.FrontPage;
//						if (currentBookletNo < bookletCount) {
//							gettingTheNextBooklet();
//						} else {
//							deliveringTheLastBooklet();
//						}
//						return;
//					}
//					if (event.getCurrentTime().getTime() == (currentBookletTotal.getOverallInfo().getEndDateTime1().getTime() + (SCHEDULE_SECONDS * 1000))) {
//						CommonLogger.log(logger, Level.INFO, "going to second content page");
//						prepareContent(event);
//					}
//					return;
//				}
//				if (event.getCurrentTime().getTime() <= currentBookletTotal.getOverallInfo().getEndDateTime2().getTime()) {
//					refreshExamTime(currentBookletTotal.getOverallInfo().getEndDateTime2().getTime() - event.getCurrentTime().getTime());
//					prepareContent(event);
//
//					if (event.getCurrentTime().getTime() == currentBookletTotal.getOverallInfo().getEndDateTime2().getTime()) {
//						deliveringTheLastBooklet();
//					}
//					return;
//				}
//				if (event.getCurrentTime().getTime() > currentBookletTotal.getOverallInfo().getEndDateTime2().getTime()) {
//					deliveringTheLastBooklet();
//				}
//			}
//
//			private void prepareContent(CurrentTimeEvent event) {
//				if (currentBookletState == States.Content) {
//					return;
//				}
//				refreshExamTime(currentBookletTotal.getOverallInfo().getEndDateTime1().getTime() - event.getCurrentTime().getTime());
//				setContentPagesStyles();
//
//				getView().getQuestionCellList().resetCellListForExam(examRequestContextProvider, currentBookletTotalQuestionNo, submittedAnswers,
//						cellListRowCount);
//
//			}
//		});
//
//	}
//
//	protected void loadSubmittedAnswersFromServer() {
//		examRequestContextProvider.get().getSubmittedAnswers()
//				.fire(new Receiver<List<QuestionAnswerProxy>>() {
//					@Override
//					public void onSuccess(List<QuestionAnswerProxy> response) {
//
//						if (response == null) {
//						} else {
//
//							submittedAnswers = response;
//							if (currentBookletState != States.FrontPage) {
//								setFrontPageStyles();
//							}
//							addClockHandler();
//						}
//					}
//
//					public void onFailure(ServerFailure error) {
//					};
//				});
//	}
//
//	private void clearAllStyles() {
//		getView().getQuestionCellList().addStyleName("display_none");
//		getView().getExamTimer().addStyleName("display_none");
//		getView().getGetOrGiveBooklet().addStyleName("display_none");
//		getView().getExamBookletInfo().addStyleName("display_none");
//		getView().getExamStaticInfo().addStyleName("display_none");
//		getView().getDetailWrapper().addStyleName("display_none");
//	}
//
//	protected void submitAnswer() {
//		ExamRequestContext context = examRequestContextProvider.get();
//		final List<QuestionAnswerProxy> questions = getNotSentQAs(context);
//
//		if (questions.size() == 0) {
//			return;
//		}
//		context.submitAnswerAsync(questions).fire(new Receiver<Boolean>() {
//			@Override
//			public void onSuccess(Boolean response) {
//				submitAnswerCounter = 0;
//				updatingSentAnswers();
//
//				try {
//					getView().getGetOrGiveBooklet().getElement()
//							.removeAttribute("disabled");
//				} catch (Exception ex) {
//				}
//
//			}
//
//			@Override
//			public void onFailure(ServerFailure error) {
//				for (QuestionAnswerProxy questionAnswerProxy : questions) {
//					QuestionAnswerProxy submittingQuestion = notSentQAs
//							.get(questionAnswerProxy.getQuestionID()
//									.longValue());
//					if (submittingQuestion != null) {
//						submittingQuestion.setState(AnswerDBState.INSERTING
//								.getId());
//					}
//				}
//				if (submitAnswerCounter < 3) {
//					submitAnswerCounter++;
//					submitAnswer();
//				} else {
//					submitAnswerCounter = 0;
//				}
//
//			}
//
//		});
//
//	}
//
//	protected void updatingSentAnswers() {
//		ExamNavigator examNavigator = null;
//		if (getView().getNavigator() == null) {
//		} else if (getView().getNavigator().getWidgetCount() > 0) {
//			examNavigator = (ExamNavigator) getView().getNavigator().getWidget(
//					0);
//		}
//		HashSet<Long> submitteds = new HashSet<Long>();
//		if (notSentQAs == null) {
//			return;
//		}
//		for (Long qId : notSentQAs.keySet()) {
//			if (notSentQAs.get(qId) == null) {
//				continue;
//			}
//			if (notSentQAs.get(qId).getState() == null) {
//			} else if (notSentQAs.get(qId).getState() == AnswerDBState.INSERTED
//					.getId()) {
//				sentAnswers.put(qId, notSentQAs.get(qId).getSelectedChoiceID());
//				submitteds.add(qId);
//				if (getView().getQuestionCellList().getAnswerSheet() == null
//						|| getView().getQuestionCellList().getAnswerSheet()
//								.get(qId) == null) {
//					continue;
//				}
//				int questionNo = getView().getQuestionCellList()
//						.getAnswerSheet().get(qId).getQuestionNo();
//				if (notSentQAs.get(qId).getSelectedChoiceID() == null) {
//					examNavigator.commitedUnAnsweredQuestion(questionNo);
//				} else {
//					examNavigator.commitedAnsweredQuestion(questionNo);
//				}
//			}
//		}
//		for (Long key : submitteds) {
//			notSentQAs.remove(key);
//		}
//
//	}
//
//	@Override
//	protected void revealInParent() {
//		RevealRootLayoutContentEvent.fire(this, this);
//	}
//
//	@Override
//	protected void onBind() {
//		super.onBind();
//	}
//	private void setContentPagesStyles() {
//
//		currentBookletState = States.Content;
//
//		getView().getDetailWrapper().addStyleName("display_none");
//		getView().getBody().removeStyleName("complete_height");
//		getView().getQuestionCellList().removeStyleName("display_none");
//		getView().getExamTimer().removeStyleName("display_none");
//		getView().getExamBookletInfo().removeStyleName("display_none");
//		getView().getExamStaticInfo().removeStyleName("display_none");
//		getView().getNavigator().removeStyleName("display_none");
//		if (isSelfExam) {
//			getView().getExamTimer().removeStyleName("display_none");
//			getView().getGetOrGiveBooklet().removeStyleName("display_none");
//			getView().getGetOrGiveBooklet().setText("تحویل دفترچه");
//		} else {
//			getView().getGetOrGiveBooklet().addStyleName("display_none");
//		}
//
//	}
//
//	public enum States {
//		FrontPage(0), Content(10);
//
//		private long id;
//
//		private States(long id) {
//			this.id = id;
//		}
//
//		public long getId() {
//			return id;
//		}
//
//		public void setId(long id) {
//			this.id = id;
//		}
//
//	}*/
//}
