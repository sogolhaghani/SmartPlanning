package ir.smartplanning.server.domain.nonpersist;

import java.io.Serializable;

public class ListItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5874726990030875519L;
	private Long id;
	private String name;
	
	public ListItem() {
	}

	public ListItem(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
