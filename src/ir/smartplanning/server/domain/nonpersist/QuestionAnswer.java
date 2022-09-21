package ir.smartplanning.server.domain.nonpersist;

import java.io.Serializable;

public class QuestionAnswer implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -3561752890799384270L;

	public QuestionAnswer() {
		this.state =AnswerDBState.NOT_SET.getId();
				
	}

	public QuestionAnswer(Long questionID, Long selectedChoiceID) {
		this();
		this.questionID = questionID;
		this.selectedChoiceID = selectedChoiceID;
		this.state =  AnswerDBState.NOT_SET.getId(); /* state can be null =>it has not inserted to DB
		 									   0 =>inserting must insert a record into DB
		 									   1 =>inserted it does not need to be inserted to db.
		 									   2 =>updating must update DB record */
	}

	public QuestionAnswer(Long questionID,Long selectedChoiceID, Long answerID) {
		this();
		this.questionID = questionID;
		this.selectedChoiceID = selectedChoiceID;
		this.answerId = answerID;
		this.state =  AnswerDBState.NOT_SET.getId(); /* state can be null =>it has not inserted to DB
		 									   0 =>inserting must insert a record into DB
		 									   1 =>inserted it does not need to be inserted to db.
		 									   2 =>updating must update DB record */
	}

	
	private Long questionID;
	private Long selectedChoiceID;
	private Integer state;
	private Long answerId;
	private byte bookletNo;
	
	public byte getBookletNo() {
		return bookletNo;
	}
	public void setBookletNo(byte bookletNo) {
		this.bookletNo = bookletNo;
	}

	public Long getQuestionID() {
		return questionID;
	}
	public void setQuestionID(Long questionID) {
		this.questionID = questionID;
	}

	public Long getSelectedChoiceID() {
		return selectedChoiceID;
	}
	public void setSelectedChoiceID(Long selectedChoiceID) {
		this.selectedChoiceID = selectedChoiceID;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Long getAnswerId() {
		return answerId;
	}
	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}
}
