package ir.smartplanning.client;

public class CacheKey {

	private CacheKeyTypes keyType;
	private Long keyId;
	private Byte grade;
	private Long majorId;
	private String cname;
	private Long schoolId;

	public CacheKey() {
	}

	public CacheKey(CacheKeyTypes keyType, Long keyId) {
		super();
		this.keyType = keyType;
		this.keyId = keyId;
		this.grade = MyGateKeeper.getUser().getGrade();
	}

	public CacheKey(CacheKeyTypes keyType, Long keyId, Byte grade, Long majorId) {
		super();
		this.keyType = keyType;
		this.keyId = keyId;
		this.grade = grade;
		this.majorId = majorId;
	}

	public CacheKey(CacheKeyTypes keyType, Long keyId, Long schoolId) {
		super();
		this.keyType = keyType;
		this.keyId = keyId;
		this.schoolId = schoolId;

	}

	public CacheKey(CacheKeyTypes enum1, Long cId, String cname, Long majorId,
			byte grade2) {
		super();
		this.keyType = enum1;
		this.keyId = cId;
		this.grade = grade2;
		this.cname = cname;
		this.majorId = majorId;
	}

	public CacheKeyTypes getKeyType() {
		return keyType;
	}

	public void setKeyType(CacheKeyTypes keyType) {
		this.keyType = keyType;
	}

	public Long getKeyId() {
		return keyId;
	}

	public void setKeyId(Long keyId) {
		this.keyId = keyId;
	}

	public Byte getGrade() {
		return grade;

	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public void setGrade(Byte grade) {
		this.grade = grade;
	}

	public Long getMajorId() {
		return majorId;
	}

	public void setMajorId(Long majorId) {
		this.majorId = majorId;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof CacheKey) {
			CacheKey operand1 = this;
			CacheKey operand2 = (CacheKey) obj;
			if (operand2 != null) {
				boolean keyTypeComparing = (operand1.getKeyType() == null && operand2
						.getKeyType() == null)
						|| ((operand1.getKeyType() != null && operand2
								.getKeyType() != null) && ((operand1
								.getKeyType().getId() == operand2.getKeyType()
								.getId())));

				boolean keyIdComparing = (operand1.getKeyId() == null && operand2
						.getKeyId() == null)
						|| ((operand1.getKeyId() != null && operand2.getKeyId() != null) && ((operand1
								.getKeyId().equals(operand2.getKeyId()))));
				boolean keyGradeComparison = (operand1.getGrade() == null && operand2
						.getGrade() == null)
						|| ((operand1.getGrade() != null && operand2.getGrade() != null) && ((operand1
								.getGrade().equals(operand2.getGrade()))));

				boolean keyMajorComparison = (operand1.getMajorId() == null && operand2
						.getMajorId() == null)
						|| ((operand1.getMajorId() != null && operand2
								.getMajorId() != null) && ((operand1
								.getMajorId().equals(operand2.getMajorId()))));

				boolean keySchoolIdComparison = (operand1.getSchoolId() == null && operand2
						.getSchoolId() == null)
						|| ((operand1.getSchoolId() != null && operand2
								.getSchoolId() != null) && ((operand1
								.getSchoolId().equals(operand2.getSchoolId()))));
				boolean keyClassNameComparison = (operand1.getCname() == null && operand2
						.getCname() == null)
						|| ((operand1.getCname() != null && operand2.getCname() != null) && ((operand1
								.getCname().equals(operand2.getCname()))));

				if (keyTypeComparing && keyIdComparing && keyGradeComparison
						&& keyMajorComparison && keySchoolIdComparison
						&& keyClassNameComparison) {
					result = true;
				}
			}
		}
		return result;
	}

	@Override
	public int hashCode() {
		String hashString = "" + this.keyId;
		hashString += "@";
		if (this.keyType == null) {
			hashString += "null";
		} else {

			hashString += this.keyType.getId();
		}
		if (this.keyType != null
				&& this.keyType.getId() == CacheKeyTypes.SchoolInfo.getId()) {
			hashString += "@";
			hashString += this.grade;

		}
		if (this.keyType != null
				&& this.keyType.getId() == CacheKeyTypes.ClassModuleInfo
						.getId()) {
			hashString += "@";
			hashString += this.schoolId;

		}
		return hashString.hashCode();
	}

}
