package ir.smartplanning.server.dao;

import java.util.List;

import ir.smartplanning.server.domain.StudyPeriod;
import ir.smartplanning.server.domain.nonpersist.PlanItems;

public interface StudyPeriodDao extends GenericDao<StudyPeriod	, Long>{
	List<StudyPeriod> getAllStudyPeriods(long majorId, byte grade);
	List<PlanItems> finWeekPlan(long studyPeriodId, long userId,Long studyFeedBackId);

}
