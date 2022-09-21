package ir.smartplanning.server.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Topic", schema = "BI", catalog = "SmartPlanning")
public class Topic implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 945659794130073327L;
	private long id;
	private String name;
	private long moduleId;
	private Topic parentTopic;
	private boolean disabled;
	private boolean leaf;
	private byte topicOrder;
	private Set<StudyFeedbackTopics> studyFeedbackTopics=new HashSet<StudyFeedbackTopics>();
	private Set<Topic> topics=new HashSet<Topic>();
	
	public Topic(long id, String name, long moduleId, Topic parentTopic,
			boolean disabled, boolean leaf, byte topicOrder,
			Set<StudyFeedbackTopics> studyFeedbackTopics) {
		super();
		this.id = id;
		this.name = name;
		this.moduleId = moduleId;
		this.parentTopic = parentTopic;
		this.disabled = disabled;
		this.leaf = leaf;
		this.topicOrder = topicOrder;
		this.studyFeedbackTopics = studyFeedbackTopics;
	}

	public Topic() {
	}

	public Topic(long id, String name, long moduleId, Topic parentTopicId,
			boolean disabled, boolean leaf, byte topicOrder) {
		super();
		this.id = id;
		this.name = name;
		this.moduleId = moduleId;
		this.parentTopic = parentTopicId;
		this.disabled = disabled;
		this.leaf = leaf;
		this.topicOrder = topicOrder;
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

	@Column(name = "Name", unique = false, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ModuleID", unique = false, nullable = false)
	public long getModuleId() {
		return moduleId;
	}

	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ParentTopicID", nullable = false)
	public Topic getParentTopic() {
		return parentTopic;
	}

	public void setParentTopic(Topic parentTopic) {
		this.parentTopic = parentTopic;
	}

	@Column(name = "IsDisabled", unique = false, nullable = false)
	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	@Column(name = "IsLeaf", unique = false, nullable = false)
	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	@Column(name = "TopicOrder", unique = false, nullable = false)
	public byte getTopicOrder() {
		return topicOrder;
	}

	public void setTopicOrder(byte topicOrder) {
		this.topicOrder = topicOrder;
	}

	@OneToMany(mappedBy="topic",fetch=FetchType.LAZY)
	public Set<StudyFeedbackTopics> getStudyFeedbackTopics() {
		return studyFeedbackTopics;
	}

	public void setStudyFeedbackTopics(Set<StudyFeedbackTopics> studyFeedbackTopics) {
		this.studyFeedbackTopics = studyFeedbackTopics;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentTopic")
	public Set<Topic> getTopics() {
		return this.topics;
	}

	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}
	

}
