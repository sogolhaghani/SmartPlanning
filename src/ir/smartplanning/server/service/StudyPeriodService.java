package ir.smartplanning.server.service;

import ir.smartplanning.server.domain.StudyPeriod;
import ir.smartplanning.server.domain.nonpersist.PlanItems;

import java.util.List;

public interface StudyPeriodService {
	public List<StudyPeriod> getAllStudyPeriods();
	public List<PlanItems>  getWeekPlan(long studyPeriodId);
	public PlanItems  saveNewPlan(PlanItems planItems);
}
