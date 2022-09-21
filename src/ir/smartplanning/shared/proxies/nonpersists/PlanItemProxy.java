package ir.smartplanning.shared.proxies.nonpersists;

import ir.smartplanning.server.domain.nonpersist.PlanItems;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(value=PlanItems.class)
public interface PlanItemProxy extends ValueProxy{
	public Long getDefultPlanId();
	public void setDefultPlanId(Long defultPlanId) ;
	public byte getDayOfWeek();
	public void setDayOfWeek(byte dayOfWeek);
	public byte getOrder() ;
	public void setOrder(byte order) ;
	public Long getStudyFeedbackId() ;
	public void setStudyFeedbackId(Long studyFeedbackId) ;
	public Integer getTestNo() ;
	public void setTestNo(Integer testNo);
	public Integer getDuration() ;
	public void setDuration(Integer duration) ;
	public String getStudyFeedbackTopics();
	public void setStudyFeedbackTopics(String studyFeedbackTopics) ;
	public String getDefultPlanTopics() ;
	public void setDefultPlanTopics(String defultPlanTopics) ;
	public Byte getIncorrectNo() ;
	public void setIncorrectNo(Byte incorrectNo);
	public long getModuleId();
	public void setModuleId(long moduleId);
	public String getRecommendation();
	public Byte getCorrectNo() ;
	public void setCorrectNo(Byte correctNo);
}
