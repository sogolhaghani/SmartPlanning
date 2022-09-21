package ir.smartplanning.server.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "StudyFeedbackTopics", schema = "BI", catalog = "SmartPlanning")
public class StudyFeedbackTopics implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8882907414127056665L;
	
	private long id;
	private StudyFeedback studyFeedback;
	private Topic topic;
	
	public StudyFeedbackTopics() {
		// TODO Auto-generated constructor stub
	}

	public StudyFeedbackTopics(long id, StudyFeedback studyFeedback, Topic topic) {
		super();
		this.id = id;
		this.studyFeedback = studyFeedback;
		this.topic = topic;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "StudyFeedbackID", nullable = false)
	public StudyFeedback getStudyFeedback() {
		return studyFeedback;
	}

	public void setStudyFeedback(StudyFeedback studyFeedback) {
		this.studyFeedback = studyFeedback;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TopicID", nullable = false)
	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	
	

}
