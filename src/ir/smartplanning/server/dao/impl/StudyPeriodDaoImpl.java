package ir.smartplanning.server.dao.impl;

import ir.smartplanning.server.InfrastructureException;
import ir.smartplanning.server.dao.StudyPeriodDao;
import ir.smartplanning.server.domain.StudyPeriod;
import ir.smartplanning.server.domain.nonpersist.PlanItems;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.ByteType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class StudyPeriodDaoImpl extends GenericDaoImpl<StudyPeriod, Long>
		implements StudyPeriodDao {

	@Inject
	public StudyPeriodDaoImpl(Provider<HibernateUtil> hibernateUtilProvider)
			throws InfrastructureException {
		super(hibernateUtilProvider);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyPeriod> getAllStudyPeriods(long majorId, byte grade) {
		try {

			Query q = this
					.getSession()
					.createQuery(
							"from StudyPeriod where MajorID =:majorId and Grade = :grade ")
					.setLong("majorId", majorId).setByte("grade", grade);
			List<StudyPeriod> studyPeriods = q.list();
			return studyPeriods;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlanItems> finWeekPlan(long studyPeriodId, long userId,
			Long studyFeedBackId) {
		StringBuilder sb = new StringBuilder();
		sb.append("  EXEC BI.GetPlanWeek @PeriodID = :periodId , @StudentID = :studentId , @StudyFeedBackID = :studyFeedBackId");
		sb.trimToSize();

		Query q = null;
		try {
			q = this.getSession()
					.createSQLQuery(sb.toString())
					.addScalar("defultPlanId", LongType.INSTANCE)
					.addScalar("dayOfWeek", ByteType.INSTANCE)
					.addScalar("order", ByteType.INSTANCE)
					.addScalar("studyFeedbackId", LongType.INSTANCE)
					.addScalar("testNo", IntegerType.INSTANCE)
					.addScalar("duration", IntegerType.INSTANCE)
					.addScalar("studyFeedbackTopics", StringType.INSTANCE)
					.addScalar("defultPlanTopics", StringType.INSTANCE)
					.addScalar("incorrectNo", ByteType.INSTANCE)
					.addScalar("moduleId", LongType.INSTANCE)
					.addScalar("correctNo", ByteType.INSTANCE)
					.addScalar("general",BooleanType.INSTANCE)
					.setLong("studentId", userId)
					.setLong("periodId", studyPeriodId)
					.setParameter("studyFeedBackId", studyFeedBackId)
					.setResultTransformer(
							Transformers.aliasToBean(PlanItems.class));
			List<PlanItems> list = q.list();
			return list;
		} catch (InfrastructureException e) {
			e.printStackTrace();
		}
		return null;
	}

}
