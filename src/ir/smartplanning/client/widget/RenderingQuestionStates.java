package ir.smartplanning.client.widget;

public class RenderingQuestionStates {
	private Long selectedOption;
	private boolean answerVisible;
	private int questionNo;
	private boolean isReading = false;

	public RenderingQuestionStates() {
		this.answerVisible = false;

	}

	public Long getSelectedOption() {
		return selectedOption;
	}

	public void setSelectedOption(Long selectedOption) {
		this.selectedOption = selectedOption;
	}

	public boolean isAnswerVisible() {
		return answerVisible;
	}

	public void setAnswerVisible(boolean answerVisible) {
		this.answerVisible = answerVisible;
	}

	public int getQuestionNo() {
		return questionNo;
	}

	public void setQuestionNo(int questionNo) {
		this.questionNo = questionNo;
	}

	public boolean isReading() {
		return isReading;
	}

	public void setReading(boolean isReading) {
		this.isReading = isReading;
	}
}
