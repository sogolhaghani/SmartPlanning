package ir.smartplanning.server.domain.nonpersist;

import java.io.Serializable;
import java.util.List;

public class PreLoadedExamObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1499520064996415895L;
	private List<Question> questions;
	private ExamBookletTotal examBookletTotals;

	public PreLoadedExamObject(List<Question> questions, ExamBookletTotal examBookletTotals) {
		super();
		this.questions = questions;
		this.examBookletTotals = examBookletTotals;
	}

	public PreLoadedExamObject() {

	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public ExamBookletTotal getExamBookletTotals() {
		return examBookletTotals;
	}

	public void setExamBookletTotals(ExamBookletTotal examBookletTotals) {
		this.examBookletTotals = examBookletTotals;
	}
}
