package ir.smartplanning.server.domain.nonpersist;

import java.io.Serializable;

public class UserItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4457312571789571961L;
	
	private Long id;
	private long majorId;
	private byte grade;
	private String name;
	private String family;
	private String userName;
	private boolean sex;
	private boolean inExam;
	
	
	public UserItem() {
		// TODO Auto-generated constructor stub
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public long getMajorId() {
		return majorId;
	}
	public void setMajorId(long majorId) {
		this.majorId = majorId;
	}
	public byte getGrade() {
		return grade;
	}
	public void setGrade(byte grade) {
		this.grade = grade;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public boolean isSex() {
		return sex;
	}
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	
	public boolean isInExam() {
		return inExam;
	}
	
	public void setInExam(boolean inExam) {
		this.inExam = inExam;
	}
	
	

}
