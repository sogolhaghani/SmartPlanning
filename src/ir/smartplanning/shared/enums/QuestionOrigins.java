package ir.smartplanning.shared.enums;

import java.util.LinkedList;
import java.util.List;

//@formatter:off
public enum QuestionOrigins {

	TALIFI(10),	// MULTIPLECHOICE & EXPLANATORY
	GHALAMCHI(11),
	GAJ(12),
	SANJESH(13),
	SABZ(14),
	SARASARI(20), 				// MULTIPLECHOICE						HASMAJOR
	AZAD(21), 					// MULTIPLECHOICE						HASMAJOR
	AZAD_SOBH(25), 				// MULTIPLECHOICE						HASMAJOR
	AZAD_ASR(26), 				// MULTIPLECHOICE						HASMAJOR
	GOZINE2(30), 				// MULTIPLECHOICE
	PROBLEM_SOLVING(98), 		// EXPLANATORY
	EMTEHANI_MINISTRY(99), 		// EXPLANATORY
	MOBSER(100),
	SAMPLE_FINAL(110), 			// EXPLANATORY
	ABROAD(120),				// 										HASMAJOR
	TEACHER_EXAM(130),
	MIRROR_EXAM(140),
	SCHOOL(150),
	PAST_EXAMS(2000);			//
	//@formatter:on
	private long id;

	private QuestionOrigins(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public static List<Long> getOriginIds() {
		List<Long> ids = new LinkedList<Long>();
		ids.add(TALIFI.getId());
		ids.add(GHALAMCHI.getId());
		ids.add(GAJ.getId());
		ids.add(SABZ.getId());
		ids.add(SARASARI.getId());
		ids.add(AZAD.getId());
		ids.add(AZAD_SOBH.getId());
		ids.add(AZAD_ASR.getId());
		ids.add(PROBLEM_SOLVING.getId());
		ids.add(GOZINE2.getId());
		ids.add(EMTEHANI_MINISTRY.getId());
		ids.add(MOBSER.getId());
		ids.add(SAMPLE_FINAL.getId());
		ids.add(ABROAD.getId());
		ids.add(SARASARI.getId());
		ids.add(TEACHER_EXAM.getId());
		ids.add(MIRROR_EXAM.getId());
		ids.add(PAST_EXAMS.getId());
		return ids;

	}

	public String getPersianText() {
		if (this.id == TALIFI.getId()) {
			return "تالیفی";// constants.Talifi();
		} else if (this.id == SARASARI.getId()) {
			return "سراسری";// constants.Sarasari();
		} else if (this.id == AZAD.getId()) {
			return "آزاد";// constants.Azad();
		} else if (this.id == AZAD_ASR.getId()) {
			return "آزاد عصر";// constants.AzadAsr();
		} else if (this.id == AZAD_SOBH.getId()) {
			return "آزاد صبح";// constants.AzadSobh();
		} else if (this.id == GOZINE2.getId()) {
			return "گزینه 2";// constants.Gozine2();
		} else if (this.id == PROBLEM_SOLVING.getId()) {
			return "تمرین ها ی کتاب";// constants.ProblemSolving();
		} else if (this.id == EMTEHANI_MINISTRY.getId()) {
			return "سوال های امتحانی ";// constants.Emtehani();
		} else if (this.id == MOBSER.getId()) {
			return "مبصر";
		} else if (this.id == SAMPLE_FINAL.getId()) {
			return "نمونه پایان ترم";
		} else if (this.id == PAST_EXAMS.getId()) {
			return "آزمون های گذشته";// constants.PastExams();
		} else if (this.id == ABROAD.getId()) {
			return "خارج از کشور";
		} else if (this.id == TEACHER_EXAM.getId()) {
			return "آزمون اساتید";
		} else if (this.id == MIRROR_EXAM.getId()) {
			return "آزمون میرر";
		} else if (this.id == GHALAMCHI.getId()) {
			return "قلم چی";
		} else if (this.id == GAJ.getId()) {
			return "گاج";
		} else if (this.id == SABZ.getId()) {
			return "سبز";
		} else if (this.id == SCHOOL.getId()) {
			return "مدرسه";
		}
		return "";
	}

	public static String getPersianText(long originId) {
		if (originId == TALIFI.getId()) {
			return "تالیفی";// constants.Talifi();
		} else if (originId == SARASARI.getId()) {
			return "سراسری";// constants.Sarasari();
		} else if (originId == AZAD.getId()) {
			return "آزاد";// constants.Azad();
		} else if (originId == AZAD_ASR.getId()) {
			return "آزاد عصر";// constants.AzadAsr();
		} else if (originId == AZAD_SOBH.getId()) {
			return "آزاد صبح";// constants.AzadSobh();
		} else if (originId == GOZINE2.getId()) {
			return "گزینه 2";// constants.Gozine2();
		} else if (originId == PROBLEM_SOLVING.getId()) {
			return "تمرین ها ی کتاب";// constants.ProblemSolving();
		} else if (originId == EMTEHANI_MINISTRY.getId()) {
			return "سوال های امتحانی ";// constants.Emtehani();
		} else if (originId == MOBSER.getId()) {
			return "مبصر";
		} else if (originId == SAMPLE_FINAL.getId()) {
			return "نمونه پایان ترم";
		} else if (originId == PAST_EXAMS.getId()) {
			return "آزمون های گذشته";// constants.PastExams();
		} else if (originId == ABROAD.getId()) {
			return "خارج از کشور";
		} else if (originId == TEACHER_EXAM.getId()) {
			return "آزمون اساتید";
		} else if (originId == MIRROR_EXAM.getId()) {
			return "آزمون میرر";
		} else if (originId == GHALAMCHI.getId()) {
			return "قلم چی";
		} else if (originId == GAJ.getId()) {
			return "گاج";
		} else if (originId == SABZ.getId()) {
			return "سبز";
		} else if (originId == SCHOOL.getId()) {
			return "مدرسه";
		}
		return "";
	}

	public static boolean hasMajor(Long comparingId) {
		if (comparingId == null)
			return false;
		if (comparingId == QuestionOrigins.SARASARI.getId() || comparingId == QuestionOrigins.AZAD.getId() || comparingId == QuestionOrigins.AZAD_ASR.getId()
				|| comparingId == QuestionOrigins.AZAD_SOBH.getId() || comparingId == QuestionOrigins.ABROAD.getId())
			return true;
		return false;
	}

	public static Long getIdFromPName(String value) {
		if (value.trim().equals(QuestionOrigins.getPersianText(QuestionOrigins.TALIFI.getId()))) {
			return QuestionOrigins.TALIFI.getId();
		}
		if (value.trim().equals(QuestionOrigins.getPersianText(QuestionOrigins.SARASARI.getId()))) {
			return QuestionOrigins.SARASARI.getId();
		}
		if (value.trim().equals(QuestionOrigins.getPersianText(QuestionOrigins.AZAD.getId()))) {
			return QuestionOrigins.AZAD.getId();
		}
		if (value.trim().equals(QuestionOrigins.getPersianText(QuestionOrigins.ABROAD.getId()))) {
			return QuestionOrigins.ABROAD.getId();
		}

		if (value.trim().equals(QuestionOrigins.getPersianText(QuestionOrigins.PAST_EXAMS.getId()))) {
			return QuestionOrigins.PAST_EXAMS.getId();
		}
		return null;
	}

}