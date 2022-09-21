package ir.smartplanning.shared.enums;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public enum QuestionTypes {
	MULTIPLECHOICE(10), READING_MULTIPLECHOICE(20), EXPLANATORY(30), READING_EXPLANATORY(40);

	private final long id;

	QuestionTypes(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getText() {
		if (this.id == 10) {
			return "تستی";
		} else if (this.id == 20) {
			return "درک مطلب تستی";
		} else if (this.id == 30) {
			return "تشریحی";
		} else if (this.id == 40) {
			return "درک مطلب تشریحی";
		} else

			return "";
	}
public static List<Long> getQuestionTypeIds(){
	List<Long> ids=new  LinkedList<Long>();
	ids.add(MULTIPLECHOICE.getId());
	ids.add(READING_MULTIPLECHOICE.getId());
	ids.add(EXPLANATORY.getId());
	ids.add(READING_EXPLANATORY.getId());
	return ids;
}
	public static Boolean isMultipleChoice(long id) {
		return id == MULTIPLECHOICE.getId();
	}

	public static Boolean isReading(long id) {
		return id == READING_MULTIPLECHOICE.getId() || id == READING_EXPLANATORY.getId();
	}

	public static QuestionTypes getEnum(long id2) {
		int id3 = (int) id2;
		switch (id3) {
		case 10:
			return QuestionTypes.MULTIPLECHOICE;
		case 20:
			return QuestionTypes.READING_MULTIPLECHOICE;
		case 30:
			return QuestionTypes.EXPLANATORY;
		case 40:
			return QuestionTypes.READING_EXPLANATORY;
		default:
			return QuestionTypes.MULTIPLECHOICE;
		}
	}

	public static List<QuestionTypes> getAsQuestionAttributeSelectorForStudent() {
		List<QuestionTypes> types = new ArrayList<QuestionTypes>();
		types.add(QuestionTypes.MULTIPLECHOICE);
		types.add(QuestionTypes.EXPLANATORY);
		return types;
	}
}
