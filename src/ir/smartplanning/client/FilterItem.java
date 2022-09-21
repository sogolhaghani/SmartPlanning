package ir.smartplanning.client;

import java.io.Serializable;

public class FilterItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6060381360854153377L;
	private String name;
	private Long id;

	public FilterItem() {
		// TODO Auto-generated constructor stub
	}

	public FilterItem(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
