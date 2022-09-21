package ir.smartplanning.server.domain.nonpersist;

public enum AnswerDBState {
	NOT_SET(0),
	INSERTING(1),
	INSERTED(2);
	private final int id;
	AnswerDBState(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	
}
