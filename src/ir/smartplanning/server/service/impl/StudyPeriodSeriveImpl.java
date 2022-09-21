package ir.smartplanning.server.service.impl;

import ir.smartplanning.server.ConstantName;
import ir.smartplanning.server.InfrastructureException;
import ir.smartplanning.server.dao.StudyFeedBackDao;
import ir.smartplanning.server.dao.StudyFeedbackTopicsDao;
import ir.smartplanning.server.dao.StudyPeriodDao;
import ir.smartplanning.server.dao.impl.HibernateUtil;
import ir.smartplanning.server.domain.StudyFeedback;
import ir.smartplanning.server.domain.StudyFeedbackTopics;
import ir.smartplanning.server.domain.StudyPeriod;
import ir.smartplanning.server.domain.Topic;
import ir.smartplanning.server.domain.User;
import ir.smartplanning.server.domain.nonpersist.PlanItems;
import ir.smartplanning.server.domain.nonpersist.UserItem;
import ir.smartplanning.server.service.StudyPeriodService;
import ir.smartplanning.shared.enums.Recommendation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import CLIPSJNI.Environment;
import CLIPSJNI.PrimitiveValue;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class StudyPeriodSeriveImpl extends
		GenericDomainServiceImpl<StudyPeriodDao> implements StudyPeriodService {

	private Environment clips;
	private final Provider<StudyFeedBackDao> studyFeedBackDaoProvider;
	private final Provider<StudyFeedbackTopicsDao> studyFeedbackTopicsDaoProvider;
	private static final String URL_STRING = "1.clp";

	@Inject
	public StudyPeriodSeriveImpl(StudyPeriodDao clazz,
			Provider<HibernateUtil> hibernateUtilProvider,
			Provider<StudyFeedBackDao> studyFeedBackDaoProvider,
			Provider<StudyFeedbackTopicsDao> studyFeedbackTopicsDaoProvider)
			throws InfrastructureException {
		super(clazz, hibernateUtilProvider);
		this.studyFeedBackDaoProvider = studyFeedBackDaoProvider;
		this.studyFeedbackTopicsDaoProvider = studyFeedbackTopicsDaoProvider;
		clips = new Environment();

	}

	@Override
	public List<StudyPeriod> getAllStudyPeriods() {
		Object object = getHttpSession()
				.getAttribute(ConstantName.CURRENR_USER);
		if (object == null) {
			return null;
		}
		if (object instanceof UserItem == false) {
			return null;
		}
		UserItem currentUser = (UserItem) object;
		long majorId = currentUser.getMajorId();
		byte grade = currentUser.getGrade();
		List<StudyPeriod> studyPeriods = this.dao.getAllStudyPeriods(majorId,
				grade);
		return studyPeriods;
	}

	@Override
	public List<PlanItems> getWeekPlan(long studyPeriodId) {
		Object object = getHttpSession()
				.getAttribute(ConstantName.CURRENR_USER);
		if (object == null) {
			return null;
		}
		if (object instanceof UserItem == false) {
			return null;
		}
		UserItem currentUser = (UserItem) object;
		long userId = currentUser.getId();

		List<PlanItems> planItems = this.dao.finWeekPlan(studyPeriodId, userId,
				null);
		if (planItems == null || planItems.size() == 0) {
			return planItems;
		}
		setRecommendation(planItems);
		getHttpSession().setAttribute(ConstantName.CURRENT_STUDY_PERIOD_ID,
				studyPeriodId);
		return planItems;
	}

	private void setRecommendation(List<PlanItems> planItems) {
		try {
			clips.clear();
			clips.load(URL_STRING);
			for (PlanItems planItems2 : planItems) {
				if (planItems2.getStudyFeedbackTopics() == null) {
					continue;
				}
				String facts = getFacts(planItems2);
				System.out.println(facts);
				clips.reset();
				clips.assertString(facts);
				clips.run();
				String evalStr = "(find-all-facts ((?f Recommendation)) TRUE)";
				PrimitiveValue pv = clips.eval(evalStr);
				if(pv.size()==0){
					continue;
				}
				String recom = pv.get(0).getFactSlot("recom").toString();
				planItems2.setRecommendation(Recommendation
						.getRecommendationText(Recommendation
								.getRecommendationNumber(Integer
										.parseInt(recom))));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private String getFacts(PlanItems planItems2) {
		// clips.assertString("(Student (course-code 2)
		// (study-hour 95)
		// (course-type general)
		// (test-no 26)
		// (total-mark 80)
		// (wrong-ansewr 0))");

		String courseCode = getCourseCode(planItems2.getStudyFeedbackTopics(),
				planItems2.getDefultPlanTopics());
		String studyHour = planItems2.getDuration() == null ? ""
				: ("" + planItems2.getDuration());
		String courseType = planItems2.isGeneral() ? "general" : "exclusive";
		String testNo = planItems2.getTestNo() == null ? "" : ("" + planItems2
				.getTestNo());
		String totalMark = getTotalMark(planItems2.getTestNo(),
				planItems2.getIncorrectNo(),planItems2.getCorrectNo());
		String wrongAnswer = getWrongAnswer(planItems2.getTestNo(),
				planItems2.getIncorrectNo());

		StringBuffer sb = new StringBuffer(200);
		sb.append("(Student ");

		sb.append("(course-code ");
		sb.append(courseCode);
		sb.append(") ");

		sb.append("(study-hour ");
		sb.append(studyHour);
		sb.append(") ");

		sb.append("(course-type ");
		sb.append(courseType);
		sb.append(") ");

		sb.append("(test-no ");
		sb.append(testNo);
		sb.append(") ");

		sb.append("(total-mark ");
		sb.append(totalMark);
		sb.append(") ");

		sb.append("(wrong-ansewr ");
		sb.append(wrongAnswer);
		sb.append(") ");

		sb.append(" ) ");

		sb.trimToSize();

		return sb.toString();
	}

	private String getWrongAnswer(Integer testNo, Byte incorrectNo) {
		if (incorrectNo == null || testNo == null) {
			return "";
		}
		double mark = ((double) incorrectNo / testNo) * 100;
		return mark + "";
	}

	private String getTotalMark(Integer testNo, Byte incorrectNo,Byte correctNo) {
		if (incorrectNo == null || testNo == null|| correctNo==null) {
			return "-100";
		}
		double mark = ((double) ((correctNo * 3) - incorrectNo) / (testNo * 3)) * 100;
		return mark + "";
	}

	private String getCourseCode(String studyFeedbackTopics,
			String defultPlanTopics) {
		List<Long> studyFeedbackTopicIds = getFeedBackTopicIds(studyFeedbackTopics);
		List<Long> defultPlanTopicIds = new ArrayList<Long>();
		List<Long> defultPlanSubTopicIds = new ArrayList<Long>();
		List<Long> defultPlanSuperTopicIds = new ArrayList<Long>();
		parsDefultPlanTopics(defultPlanSubTopicIds, defultPlanSuperTopicIds,
				defultPlanTopicIds, defultPlanTopics);

		if (isTwoPlanEqual(studyFeedbackTopicIds, defultPlanTopicIds)) {
			return "1";
		}
		if (isTwoPlanCompletelyDifferent(studyFeedbackTopicIds,
				defultPlanSubTopicIds, defultPlanSuperTopicIds,
				defultPlanTopicIds)) {
			return -1 + "";
		}
		if (isFeedBackPlanCompletelyLessThanDefultPlan(studyFeedbackTopicIds,
				defultPlanSubTopicIds)) {
			return "0";
		}
		if (isStudyMoreThan(defultPlanTopics, studyFeedbackTopicIds)) {
			return "2";
		}
		return -3 + "";
	}

	private boolean isStudyMoreThan(String defultPlanTopics,
			List<Long> studyFeedbackTopicIds) {
		String[] tem = defultPlanTopics.trim().split(";");
		for (String string : tem) {
			String[] topics = string.trim().split("#");
			long main = Long.parseLong(topics[0]);
			if (studyFeedbackTopicIds.contains(main)) {
				continue;
			} else {
				String[] superIds = topics[2].trim().split(",");
				boolean exist = false;
				for (String string2 : superIds) {
					long superId = Long.parseLong(string2);
					if (studyFeedbackTopicIds.contains(superId)) {
						exist = true;
						break;
					}
				}
				if (exist == false) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean isFeedBackPlanCompletelyLessThanDefultPlan(
			List<Long> studyFeedbackTopicIds, List<Long> defultPlanSubTopicIds) {
		for (Long long1 : studyFeedbackTopicIds) {
			if (defultPlanSubTopicIds.contains(long1.longValue()) == false) {
				return false;
			}
		}
		return true;
	}

	private boolean isTwoPlanCompletelyDifferent(
			List<Long> studyFeedbackTopicIds, List<Long> defultPlanSubTopicIds,
			List<Long> defultPlanSuperTopicIds, List<Long> defultPlanTopicIds) {

		for (Long long1 : studyFeedbackTopicIds) {
			if (defultPlanSubTopicIds.contains(long1.longValue())) {
				return false;
			}
			if (defultPlanSuperTopicIds.contains(long1.longValue())) {
				return false;
			}
			if (defultPlanTopicIds.contains(long1.longValue())) {
				return false;
			}
		}
		return true;
	}

	private boolean isTwoPlanEqual(List<Long> studyFeedbackTopicIds,
			List<Long> defultPlanTopicIds) {
		for (Long defultId : defultPlanTopicIds) {
			if (studyFeedbackTopicIds.contains(defultId.longValue()) == false) {
				return false;
			}
		}

		for (Long feedbackId : studyFeedbackTopicIds) {
			if (defultPlanTopicIds.contains(feedbackId) == false) {
				return false;
			}
		}
		return true;
	}

	private void parsDefultPlanTopics(List<Long> defultPlanSubTopicIds,
			List<Long> defultPlanSuperTopicIds, List<Long> defultPlanTopicIds,
			String defultPlanTopics) {

		String[] temp = defultPlanTopics.trim().split(";");
		for (String string : temp) {

			String[] treeIds = string.trim().split("#");
			defultPlanTopicIds.add(Long.parseLong(treeIds[0]));
			String[] sub = treeIds[1].trim().split(",");
			for (String string2 : sub) {
				long parseLong = Long.parseLong(string2);
				if (parseLong > 0)
					defultPlanSubTopicIds.add(parseLong);
			}

			String[] superIds = treeIds[2].trim().split(",");
			for (String string2 : superIds) {
				long parseLong = Long.parseLong(string2);
				if (parseLong > 0)
					defultPlanSuperTopicIds.add(parseLong);
			}
		}

	}

	private List<Long> getFeedBackTopicIds(String studyFeedbackTopics) {

		List<Long> ids = new ArrayList<Long>();
		String[] topics = studyFeedbackTopics.trim().split(",");
		for (String string : topics) {
			try {
				long id = Long.parseLong(string.trim());
				ids.add(id);
			} catch (Exception e) {
			}
		}
		return ids;
	}

	@Override
	public PlanItems saveNewPlan(PlanItems planItems) {
		Object object = getHttpSession()
				.getAttribute(ConstantName.CURRENR_USER);
		if (object == null) {
			return null;
		}
		if (object instanceof UserItem == false) {
			return null;
		}
		UserItem currentUser = (UserItem) object;
		long userId = currentUser.getId();
		StudyFeedback studyFeedback = null;
		if (planItems.getStudyFeedbackId() != null) {
			studyFeedback = this.studyFeedBackDaoProvider.get().findById(
					planItems.getStudyFeedbackId());
			Set<StudyFeedbackTopics> studyFeedbackTopics = studyFeedback
					.getStudyFeedbackTopics();
			studyFeedback.getStudyFeedbackTopics().clear();
			try {
				for (StudyFeedbackTopics studyFeedbackTopics2 : studyFeedbackTopics) {
					this.studyFeedbackTopicsDaoProvider.get()
							.beginTransaction();
					this.studyFeedbackTopicsDaoProvider.get().makeTransient(
							studyFeedbackTopics2);
					this.studyFeedbackTopicsDaoProvider.get()
							.commitTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			studyFeedback = new StudyFeedback();
		}
		studyFeedback.setOrder(planItems.getOrder());
		studyFeedback.setDayOfWeek(planItems.getDayOfWeek());
		if (planItems.getDuration() > 0)
			studyFeedback.setDuration(planItems.getDuration());
		else {
			studyFeedback.setDuration(null);
		}

		if (planItems.getTestNo()!=null &&planItems.getTestNo() > 0)
			studyFeedback.setTestNo(planItems.getTestNo());
		else {
			studyFeedback.setTestNo(null);
		}
		if (planItems.getIncorrectNo()!=null && planItems.getIncorrectNo() > 0) {
			studyFeedback.setInCorrectNo(planItems.getIncorrectNo());
		} else {
			studyFeedback.setInCorrectNo((byte) 0);
		}

		if (planItems.getCorrectNo()!=null &&planItems.getCorrectNo() > 0) {
			studyFeedback.setCorrectNo(planItems.getCorrectNo());
		} else {
			studyFeedback.setCorrectNo((byte) 0);
		}
		Long currentStudyPeriodId = (Long) getHttpSession().getAttribute(
				ConstantName.CURRENT_STUDY_PERIOD_ID);
		StudyPeriod studyPeriod = new StudyPeriod();
		studyPeriod.setId(currentStudyPeriodId);
		studyFeedback.setStudyPeriod(studyPeriod);
		User student = new User();
		student.setId(userId);
		studyFeedback.setStudent(student);
		studyFeedback.setStudyFeedbackTopics(getStudtFeedBackTopics(
				planItems.getStudyFeedbackTopics(), studyFeedback));
		StudyFeedback studyFeedback2 = this.studyFeedBackDaoProvider.get()
				.makePersistent(studyFeedback);

		List<PlanItems> items = this.dao.finWeekPlan(currentStudyPeriodId,
				userId, studyFeedback2.getId());
		setRecommendation(items);
		return items.get(0);
	}

	private Set<StudyFeedbackTopics> getStudtFeedBackTopics(
			String studyFeedbackTopics, StudyFeedback studyFeedback) {
		Set<StudyFeedbackTopics> studyFeedbackTopics2 = new HashSet<StudyFeedbackTopics>();
		String[] topicString = studyFeedbackTopics.trim().split(",");
		List<Long> topicIds = new ArrayList<Long>();
		for (String string : topicString) {
			topicIds.add(Long.parseLong(string));
		}
		for (Long long1 : topicIds) {
			StudyFeedbackTopics st = new StudyFeedbackTopics();
			st.setStudyFeedback(studyFeedback);
			Topic topic = new Topic();
			topic.setId(long1);
			st.setTopic(topic);
			studyFeedbackTopics2.add(st);
		}
		return studyFeedbackTopics2;
	}
}
