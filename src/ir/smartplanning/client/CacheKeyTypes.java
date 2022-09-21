package ir.smartplanning.client;

public enum CacheKeyTypes {
	SchoolAdvisorStudent((byte) -5), ClassModuleInfo((byte) -4), StudetInfo(
			(byte) -2), SchoolInfo((byte) -1), Major((byte) -3), ModuleGroup(
			(byte) 0), Module((byte) 1), Topic((byte) 2), SubTopic((byte) 3), SubTopic1(
			(byte) 4), SubTopic2((byte) 5), SubTopic3((byte) 6), Origin(
			(byte) 7), DifficultyLevel((byte) 8), QuestionType((byte) 9), ModuleGrade(
			(byte) 10);

	private byte id;

	private CacheKeyTypes(byte id) {
		this.id = id;
	}

	public byte getId() {
		return id;
	}

	public static CacheKeyTypes getEnum(byte id) {
		switch (id) {
		case -5:
			return CacheKeyTypes.SchoolAdvisorStudent;
		case -4:
			return CacheKeyTypes.ClassModuleInfo;
		case -3:
			return CacheKeyTypes.Major;
		case -2:
			return CacheKeyTypes.StudetInfo;
		case -1:
			return CacheKeyTypes.SchoolInfo;
		case 0:
			return CacheKeyTypes.ModuleGroup;
		case 1:
			return CacheKeyTypes.Module;
		case 2:
			return CacheKeyTypes.Topic;
		case 3:
			return CacheKeyTypes.SubTopic;
		case 4:
			return CacheKeyTypes.SubTopic1;
		case 5:
			return CacheKeyTypes.SubTopic2;
		case 6:
			return CacheKeyTypes.SubTopic3;
		case 7:
			return CacheKeyTypes.Origin;
		case 8:
			return CacheKeyTypes.DifficultyLevel;
		case 9:
			return CacheKeyTypes.QuestionType;
		case 10:
			return CacheKeyTypes.ModuleGrade;
		default:
			return null;
		}
	}

}