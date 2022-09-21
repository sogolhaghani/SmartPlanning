package ir.smartplanning.client.widget;

import ir.smartplanning.shared.proxies.nonpersists.QuestionProxy;

public class CacheItem {
	private QuestionProxy question;
	int questionNo;

	public CacheItem(QuestionProxy questionProxy, int currentQuestionNo) {

		this.question = questionProxy;
		this.questionNo = currentQuestionNo;
	}

	public QuestionProxy getQuestion() {
		return question;
	}

	public int getQuestionNo() {
		return questionNo;
	}

	public void setQuestion(QuestionProxy question) {
		this.question = question;
	}
}
