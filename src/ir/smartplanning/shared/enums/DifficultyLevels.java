package ir.smartplanning.shared.enums;

import java.util.LinkedList;
import java.util.List;

public enum DifficultyLevels {

	EASY(10), NORMAL(20), DIFFICULT(30), VERYDIFFICULT(40);

	private long id;

	public long getId() {
		return id;
	}

	private DifficultyLevels(long id) {
		this.id = id;
	}

	public static List<Long> getDifficultyIds() {
		List<Long> ids = new LinkedList<Long>();
		ids.add(EASY.getId());
		ids.add(NORMAL.getId());
		ids.add(DIFFICULT.getId());
		ids.add(VERYDIFFICULT.getId());
		return ids;

	}

	public static DifficultyLevels getEnum(long id) {
		int id2 = (int) id;
		switch (id2) {
		case 10:
			return DifficultyLevels.EASY;
		case 20:
			return DifficultyLevels.NORMAL;
		case 30:
			return DifficultyLevels.DIFFICULT;
		case 40:
			return DifficultyLevels.VERYDIFFICULT;
		default:
			return DifficultyLevels.EASY;
		}
	}

	public static String getPersianConstant(long id) {
		int idValue = (int) id;
		switch (idValue) {
		case 10:
			return "آسان";
		case 20:
			return "معمولی";
		case 30:
			return "سخت";
		case 40:
			return "بسیار سخت";
		default:
			return "نا مشخص";
		}
	}

	public static long getIdFromPName(String value) {
		if (value.trim().equals("آسان")) {
			return 10;
		}
		if (value.trim().equals("معمولی")) {
			return 20;
		}
		if (value.trim().equals("سخت")) {
			return 30;
		}
		if (value.trim().equals("بسیار سخت")) {
			return 40;
		}
		return -1;
	}

	public static long getInitialValueForDifficultyLevel2(long difficultyLevelId) {
		long result = 0;
		if (difficultyLevelId == DifficultyLevels.EASY.getId()) {
			result = 25;
		} else if (difficultyLevelId == DifficultyLevels.NORMAL.getId()) {
			result = 45;
		} else if (difficultyLevelId == DifficultyLevels.DIFFICULT.getId()) {
			result = 65;
		} else if (difficultyLevelId == DifficultyLevels.VERYDIFFICULT.getId()) {
			result = 85;
		}
		return result;
	}

}
