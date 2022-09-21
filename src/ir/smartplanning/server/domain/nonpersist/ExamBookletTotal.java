package ir.smartplanning.server.domain.nonpersist;

import java.io.Serializable;
import java.util.List;

public class ExamBookletTotal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8874331812292482809L;

	public long getId(){ return 1L; }
	public static ExamBookletTotal findExamBookletTotal(long id){ return null;}
	
	
	private List<ListItem> details = null;
	private ExamRequestOverallInfo overallInfo = null;
	private StartExamResultType resultType;
	
	public StartExamResultType getResultType() {
		return resultType;
	}
	public void setResultType(StartExamResultType resultType) {
		this.resultType = resultType;
	}
	public ExamRequestOverallInfo getOverallInfo() {
		return overallInfo;
	}
	public void setOverallInfo(ExamRequestOverallInfo overallInfo) {
		this.overallInfo = overallInfo;
	}
	
	public List<ListItem> getDetails() {
		return details;
	}
	public void setDetails(List<ListItem> details) {
		this.details = details;
	}
}
