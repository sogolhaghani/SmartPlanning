package ir.smartplanning.client.presenter;

import ir.smartplanning.client.Messages;
import ir.smartplanning.client.MyGateKeeper;
import ir.smartplanning.client.TopicHepler;
import ir.smartplanning.client.place.NameTokens;
import ir.smartplanning.client.widget.MyDialogBox;
import ir.smartplanning.shared.MyRequestFactory.StudyPeriodRequestContext;
import ir.smartplanning.shared.calender.PersianCalendar;
import ir.smartplanning.shared.calender.PersianCalendarConstants;
import ir.smartplanning.shared.proxies.StudyPeriodProxy;
import ir.smartplanning.shared.proxies.nonpersists.PlanItemProxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class ConsultationPresenter extends
		Presenter<ConsultationPresenter.MyView, ConsultationPresenter.MyProxy> {

	public interface MyView extends View {

		public HTMLPanel getWeeks();

		public HTMLPanel getDaysOfWeek();

	}

	@ProxyCodeSplit
	@NameToken(NameTokens.consultation)
	@UseGatekeeper(MyGateKeeper.class)
	public interface MyProxy extends ProxyPlace<ConsultationPresenter> {
	}

	private List<StudyPeriodProxy> studyPeriodProxies;
	private final Provider<StudyPeriodRequestContext> studyPeriodRequestContextProvider;
	private PersianCalendar pc = new PersianCalendar();
	private Map<Byte, List<PlanItemProxy>> days = new HashMap<Byte, List<PlanItemProxy>>();

	private MyDialogBox myDialogBox = new MyDialogBox();

	@Inject
	public ConsultationPresenter(
			final EventBus eventBus,
			final MyView view,
			final MyProxy proxy,
			Provider<StudyPeriodRequestContext> studyPeriodRequestContextProvider) {
		super(eventBus, view, proxy);
		this.studyPeriodRequestContextProvider = studyPeriodRequestContextProvider;
		myDialogBox.getSave().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				onSave();

			}
		});
	}

	protected void onSave() {
		int duration = myDialogBox.getDuration();
		int testNo = myDialogBox.getTesNo();
		byte inCorrectNo = myDialogBox.getInCorrectNo();
		byte correctNo = myDialogBox.getCorrectNo();
		String topics = myDialogBox.getTopics();
		if (isInputDataIsValid(duration, testNo, inCorrectNo, topics, correctNo)) {
			StudyPeriodRequestContext context = this.studyPeriodRequestContextProvider
					.get();
			PlanItemProxy planItemProxy = context.create(PlanItemProxy.class);
			planItemProxy.setDayOfWeek(myDialogBox.getDayOfWeek());
			planItemProxy.setDuration(duration);
			planItemProxy.setIncorrectNo(inCorrectNo);
			planItemProxy.setOrder(myDialogBox.getOrder());
			planItemProxy.setStudyFeedbackId(myDialogBox.getStudyFeedBackId());
			planItemProxy.setStudyFeedbackTopics(topics);
			planItemProxy.setTestNo(testNo);
			planItemProxy.setCorrectNo(correctNo);
			
			context.saveNewPlan(planItemProxy).fire(
					new Receiver<PlanItemProxy>() {

						@Override
						public void onSuccess(PlanItemProxy response) {
							List<PlanItemProxy> planItemProxies = days
									.get(response.getDayOfWeek());
							for (int i = 0; i < planItemProxies.size(); i++) {
								if (planItemProxies.get(i).getOrder() == response
										.getOrder()) {
									planItemProxies.set(i, response);
									break;
								}
							}
							getView().getDaysOfWeek().removeStyleName(
									"display_none");
							getView().getWeeks().addStyleName("display_none");
							insertPlanInUI();
							myDialogBox.hide();
						}
					});

		}
	}

	private boolean isInputDataIsValid(int duration, int testNo,
			byte inCorrectNo, String topics, byte correctNo) {
		List<String> errors = new ArrayList<String>();
		if (topics == null || topics.trim().equals("")) {
			errors.add("هیچ سر فصلی به عنوان موضوع مورد مطالعه وارد نشده است.");
			Messages.ShowNoty("موارد زیر را اصلاح کنید", errors, Messages.ERORR);
		}
		if (duration < 0) {
			errors.add("زمان وارد شده قابل قبول نیست");
		}
		if (testNo < 0) {
			errors.add("تعداد تست وارد شده قابل قبول نیست");
		}
		if (inCorrectNo < 0) {
			errors.add("تعداد تست نادرست وارد شده قابل قبول نیست.");
		}
		if (correctNo < 0) {
			errors.add("تعداد تست نادرست وارد شده قابل قبول نیست.");
		}
		if ((testNo < 0 || inCorrectNo <0 || correctNo <0)
				&& ((testNo <= 0 && inCorrectNo < 0 && correctNo <0) == false)) {
			errors.add(" برای وارد کردن تعداد تست باید همزمان هم تعداد درست و هم تعداد غلط را وارد نمایید");
		}
		
		if(testNo>0 && correctNo>0 && (correctNo>testNo )){
			errors.add(" تعداد درست نمی تواند از تعداد کل بیشتر باشد.");
		}
		if(testNo>0 && inCorrectNo>0 && (inCorrectNo>testNo )){
			errors.add(" تعداد نادرست نمی تواند از تعداد کل بیشتر باشد.");
		}
		
		if (errors.size() > 0) {
			Messages.ShowNoty("موارد زیر را اصلاح کنید", errors, Messages.ERORR);
			return false;
		}
		return true;
	}

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
		reset();
		getWeeks();
	}

	private void getWeeks() {
		if (studyPeriodProxies != null) {
			drawCell();
		} else {
			populateStudyPeriods();
		}

	}

	private void populateStudyPeriods() {
		this.studyPeriodRequestContextProvider.get().getAllStudyPeriods()
				.fire(new Receiver<List<StudyPeriodProxy>>() {

					@Override
					public void onSuccess(List<StudyPeriodProxy> response) {
						if (response == null) {

							MyGateKeeper.logout();
							return;
						}

						studyPeriodProxies = response;
						getWeeks();

					}

					@Override
					public void onFailure(ServerFailure error) {
						super.onFailure(error);
					}
				});

	}

	private void drawCell() {
		getView().getWeeks().clear();
		pc.setNewTime(new Date());
		pc.getPersianLongDate();
		for (int i = 0; i < studyPeriodProxies.size(); i++) {
			HTML label = new HTML();
			label.setHTML(getHTML(studyPeriodProxies.get(i)));
			getView().getWeeks().add(label);
			label.addStyleName("week_cell");
			label.addStyleName("inline");
			labelClickHandler(studyPeriodProxies.get(i), label);
		}
	}

	private void labelClickHandler(final StudyPeriodProxy studyPeriodProxy,
			HTML label) {
		label.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				populateWeekPlan(studyPeriodProxy);

			}
		});

	}

	protected void populateWeekPlan(StudyPeriodProxy studyPeriodProxy) {
		this.studyPeriodRequestContextProvider.get()
				.getWeekPlan(studyPeriodProxy.getId())
				.fire(new Receiver<List<PlanItemProxy>>() {

					@Override
					public void onSuccess(List<PlanItemProxy> response) {
						if (response == null) {
							return;
						}
						getView().getDaysOfWeek().removeStyleName(
								"display_none");
						getView().getWeeks().addStyleName("display_none");
						drawDayOfWeekCell(response);
					}

					@Override
					public void onFailure(ServerFailure error) {
						super.onFailure(error);
					}
				});

	}

	protected void drawDayOfWeekCell(List<PlanItemProxy> response) {
		getView().getDaysOfWeek().clear();
		days.clear();
		for (PlanItemProxy planItemProxy : response) {
			byte dayOfWeek = planItemProxy.getDayOfWeek();
			List<PlanItemProxy> planItemProxies;
			if (days.get(dayOfWeek) == null) {
				planItemProxies = new ArrayList<PlanItemProxy>();
			} else {
				planItemProxies = days.get(dayOfWeek);
			}
			planItemProxies.add(planItemProxy);
			days.put(dayOfWeek, planItemProxies);
		}
		insertPlanInUI();
	}

	private void insertPlanInUI() {
		getView().getDaysOfWeek().clear();
		FlexTable table = new FlexTable();
		table.setCellSpacing(5);
		getView().getDaysOfWeek().add(table);
		table.setText(0, 0, PersianCalendarConstants.persianWeekDays[0]);
		table.setText(1, 0, PersianCalendarConstants.persianWeekDays[1]);
		table.setText(2, 0, PersianCalendarConstants.persianWeekDays[2]);
		table.setText(3, 0, PersianCalendarConstants.persianWeekDays[3]);
		table.setText(4, 0, PersianCalendarConstants.persianWeekDays[4]);
		table.setText(5, 0, PersianCalendarConstants.persianWeekDays[5]);
		table.setText(6, 0, PersianCalendarConstants.persianWeekDays[6]);

		table.getFlexCellFormatter().addStyleName(0, 0, "week_cell");
		table.getFlexCellFormatter().addStyleName(1, 0, "week_cell");
		table.getFlexCellFormatter().addStyleName(2, 0, "week_cell");
		table.getFlexCellFormatter().addStyleName(3, 0, "week_cell");
		table.getFlexCellFormatter().addStyleName(4, 0, "week_cell");
		table.getFlexCellFormatter().addStyleName(5, 0, "week_cell");
		table.getFlexCellFormatter().addStyleName(6, 0, "week_cell");

		for (Byte key : days.keySet()) {
			List<PlanItemProxy> items = days.get(key);
			for (PlanItemProxy planItemProxy : items) {
				HTML html = new HTML();
				html.setHTML(setHTML(planItemProxy));
				table.setWidget(key, planItemProxy.getOrder(), html);
				table.getFlexCellFormatter().addStyleName(key,
						planItemProxy.getOrder(), "week_cell");
			}
		}

		tableHandler(table);
	}

	private void tableHandler(final FlexTable table) {
		table.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Cell cell = table.getCellForEvent(event);
				if (cell != null) {
					int rowIndex = cell.getRowIndex();
					int colIndex = cell.getCellIndex();
					if (colIndex == 0) {
						return;
					}
					List<PlanItemProxy> planItemProxies = days
							.get((byte) rowIndex);
					if (planItemProxies == null) {
						createEmptyPopup(rowIndex, colIndex);
					} else {
						if (planItemProxies.size() >= colIndex) {
							PlanItemProxy planItemProxy = planItemProxies
									.get(colIndex - 1);
							createPlanItemPopup(planItemProxy);
						}
					}
				}

			}
		});

	}

	protected void createPlanItemPopup(PlanItemProxy planItemProxy) {
		this.myDialogBox.setPlanItem(planItemProxy, true);
		this.myDialogBox.showPanel();
	}

	protected void createEmptyPopup(int rowIndex, int colIndex) {
	}

	private String setHTML(PlanItemProxy planItemProxy) {
		String template = "";
		if (planItemProxy.getDefultPlanTopics() != null) {
			String defTopics = "";
			String[] defultTopics = planItemProxy.getDefultPlanTopics().trim()
					.split(";");
			for (String string : defultTopics) {
				defTopics += "," + string.trim().split("#")[0];
			}
			defTopics = defTopics.substring(1);
			template += "<p><strong>مبحث پیشنهادی : </strong> "
					+ TopicHepler.getSelectedTopicName(
							planItemProxy.getModuleId(), defTopics) + "</p>";
		}
		if (planItemProxy.getStudyFeedbackTopics() != null) {
			template += "<p>مبحث مورد مطالعه :"
					+ TopicHepler.getSelectedTopicName(
							planItemProxy.getModuleId(),
							planItemProxy.getStudyFeedbackTopics()) + "</p>";
		}

		if (planItemProxy.getDuration() != null) {
			template += "<p><strong>مدت زمان مطالعه :</strong>"
					+ planItemProxy.getDuration() + "<span> دقیقه </span></p>";
		}

		if (planItemProxy.getTestNo() != null) {
			template += "<p>تعداد تست یا تمرین حل شده : "
					+ planItemProxy.getTestNo() + " تا </p>";
		}
		if (planItemProxy.getRecommendation() != null) {
			template += "<p><strong>" + planItemProxy.getRecommendation()
					+ "</strong></p>";
		}
		return template;
	}

	private String getHTML(StudyPeriodProxy studyPeriodProxy) {
		pc.setNewTime(studyPeriodProxy.getStartDate());
		String persianStartDate = pc.getPersianLongDateNoYear();
		pc.setNewTime(studyPeriodProxy.getEndDate());
		String persianEndDate = pc.getPersianLongDateNoYear();
		String order = studyPeriodProxy.getWeeksOrder() + "";
		return "<div>" + order + "</div><div><span>" + persianStartDate
				+ "</span> &nbsp;&nbsp;&nbsp;تا &nbsp;&nbsp;&nbsp;<span>"
				+ persianEndDate + "</span></div>";

	}

	private void reset() {
		getView().getDaysOfWeek().clear();
		getView().getWeeks().clear();
		getView().getDaysOfWeek().addStyleName("display_none");
		getView().getWeeks().removeStyleName("display_none");
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, HomePresenter.content_slot, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}
}
