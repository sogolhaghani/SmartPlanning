package ir.smartplanning.server.domain.nonpersist;

public enum StartExamResultType {
	ThereIsNoOpenExam(-2),
	Unknown(-1),
	Ok(0);
	
	private long id;
	
	private StartExamResultType(long id){
		this.id = id;
	}
	public long getId() {
		return id;
	}
	
	public static StartExamResultType getEnum(long id){
		if (id >= 0) {
			return Ok;
		} else if (id == -10) {
			return ThereIsNoOpenExam;
		} else {
			return Unknown;
		}
	}
}
