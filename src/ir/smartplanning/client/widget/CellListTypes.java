package ir.smartplanning.client.widget;

public enum CellListTypes {
	Exam(10), AfterExam(20);

	private int id;

	public int getId() {
		return id;
	}

	private CellListTypes(int id) {
		this.id = id;
	}

}
