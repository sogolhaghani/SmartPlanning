//package ir.smartplanning.client.presenter;
//
//import ir.smartplanning.client.widget.CellListTypes;
//import ir.smartplanning.client.widget.QuestionCellList;
//
//import com.google.gwt.uibinder.client.UiBinder;
//import com.google.gwt.uibinder.client.UiField;
//import com.google.gwt.user.client.ui.Button;
//import com.google.gwt.user.client.ui.HTMLPanel;
//import com.google.gwt.user.client.ui.Label;
//import com.google.gwt.user.client.ui.Widget;
//import com.google.inject.Inject;
//import com.gwtplatform.mvp.client.ViewImpl;
//
//public class TakeExamView extends ViewImpl implements TakeExamPresenter.MyView {
//
//	private final Widget widget;
//	@UiField HTMLPanel examBookletInfo;
//	@UiField
//	Button getOrGiveBooklet;
//	@UiField
//	Label examTimer;
//
//	@UiField HTMLPanel examStaticInfo;
//	@UiField(provided = true)
//	QuestionCellList questionCellList;
//	@UiField Label startTimer;
//	@UiField HTMLPanel detailWrapper;
//	@UiField HTMLPanel navigator;
//	@UiField Label major;
//	@UiField Label examType;
//	@UiField HTMLPanel details;
//	@UiField HTMLPanel body;
//	public interface Binder extends UiBinder<Widget, TakeExamView> {
//	}
//
//	@Inject
//	public TakeExamView(final Binder binder) {
//		questionCellList = new QuestionCellList(CellListTypes.Exam);
//		widget = binder.createAndBindUi(this);
//		getOrGiveBooklet.addStyleName("display_none");
//		questionCellList.addStyleName("display_none");
//		examBookletInfo.addStyleName("display_none");
//		examTimer.addStyleName("display_none");
//	}
//	@Override
//	public Widget asWidget() {
//		return widget;
//	}
//
//	public HTMLPanel getExamBookletInfo() {
//		return examBookletInfo;
//	}
//
//	public Button getGetOrGiveBooklet() {
//		return getOrGiveBooklet;
//	}
//
//	public Label getExamTimer() {
//		return examTimer;
//	}
//
//	public HTMLPanel getExamStaticInfo() {
//		return examStaticInfo;
//	}
//
//	public QuestionCellList getQuestionCellList() {
//		return questionCellList;
//	}
//
//	public Label getStartTimer() {
//		return startTimer;
//	}
//
//	public HTMLPanel getDetailWrapper() {
//		return detailWrapper;
//	}
//
//	public HTMLPanel getNavigator() {
//		return navigator;
//	}
//
//	public Label getMajor() {
//		return major;
//	}
//
//	public Label getExamType() {
//		return examType;
//	}
//
//	public HTMLPanel getDetails() {
//		return details;
//	}
//
//	public HTMLPanel getBody() {
//		return body;
//	}
//
//}
