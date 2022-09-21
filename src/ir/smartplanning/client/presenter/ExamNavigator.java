package ir.smartplanning.client.presenter;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;

public class ExamNavigator extends Composite {
	private HTMLPanel root;
	private int questionCount;
	private int previousBookletCount;
	public ExamNavigator(int questionCount, int previousBooklet) {
		this.questionCount = questionCount;
		this.previousBookletCount=previousBooklet;
		buildDisplay();
		this.initWidget(root);
	}

	private void buildDisplay() {
		this.root = new HTMLPanel("");
		this.root.addStyleName("navigator_root");
		for (int i = 0; i < questionCount; i++) {
			Label label = new Label();
			label.getElement().setAttribute("id", (i + 1+previousBookletCount) + "");
			label.addStyleName("navigator_element");
			label.addStyleName("navigator_unanswered_question");
			label.addStyleName("navigator_commite_unanswered_question");
			label.setText(((i + 1+previousBookletCount)) + "");
			this.root.add(label);
		}
	}

	public void answeredQuestion(int questionNo) {
		for (int i = 0; i < this.root.getWidgetCount(); i++) {
			Label label = (Label) this.root.getWidget(i);
			Integer labelId = Integer.parseInt(label.getElement().getAttribute("id"));
			if (labelId.intValue() == questionNo) {
				label.addStyleName("navigator_answered_question");
				label.removeStyleName("navigator_commite_unanswered_question");
				label.removeStyleName("navigator_unanswered_question");
				label.removeStyleName("navigator_commite_answered_question");
				break;
			}

		}
	}

	public void commitedAnsweredQuestion(int questionNo) {
		for (int i = 0; i < this.root.getWidgetCount(); i++) {
			Label label = (Label) this.root.getWidget(i);
			Integer labelId = Integer.parseInt(label.getElement().getAttribute("id"));
			if (labelId.intValue() == questionNo) {
				label.addStyleName("navigator_commite_answered_question");
				label.addStyleName("navigator_answered_question");
				label.removeStyleName("navigator_commite_unanswered_question");
				label.removeStyleName("navigator_unanswered_question");
				break;
			}

		}
	}

	public void unAnsweredQuestion(int questionNo) {
		for (int i = 0; i < this.root.getWidgetCount(); i++) {
			Label label = (Label) this.root.getWidget(i);
			Integer labelId = Integer.parseInt(label.getElement().getAttribute("id"));
			if (labelId.intValue() == questionNo) {
				label.removeStyleName("navigator_answered_question");
				label.removeStyleName("navigator_commite_answered_question");
				label.addStyleName("navigator_unanswered_question");
				label.removeStyleName("navigator_commite_unanswered_question");
				break;
			}

		}
	}

	public void commitedUnAnsweredQuestion(int questionNo) {
		for (int i = 0; i < this.root.getWidgetCount(); i++) {
			Label label = (Label) this.root.getWidget(i);
			Integer labelId = Integer.parseInt(label.getElement().getAttribute("id"));
			if (labelId.intValue() == questionNo) {
				label.removeStyleName("navigator_answered_question");
				label.removeStyleName("navigator_commite_answered_question");
				label.addStyleName("navigator_commite_unanswered_question");
				label.addStyleName("navigator_unanswered_question");
				break;
			}

		}
	}

	public void setQuestionNo(int questionNo) {
		if(questionNo==-1){
			return;
		}

		for (int i=0;i<root.getWidgetCount();i++) {
			Label label=(Label) root.getWidget(i);
			Integer labelId = Integer.parseInt(label.getElement().getAttribute("id"));
			if(labelId==questionNo){
				label.setText((questionNo) + "");
				return;
			}
		}
		
	}
}
