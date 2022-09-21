package ir.smartplanning.shared.enums;


public enum Majors {
	GENERAL(0), MATHEMATICS(1), SCIRENCE(2), HUMANITIES(3), DABESTAN_3_SALE_AVAL(52), DABESTAN_3_SALE_DOVOM(53), RAHNAMAI(54), HONARESTAN(55), DABIRESTAN_3_SALE_AVAL(
			56), DABIRESTAN_3_SALE_DOVOM_MATH(60), DABIRESTAN_3_SALE_DOVOM_SCIRENCE(61), DABIRESTAN_3_SALE_DOVOM_HUMANITIES(62);

	private int id;

	public int getId() {
		return id;
	}

	
	private Majors(int id) {
		this.id = id;
	}

	
	
	public static Majors getMajors(Long id) {
		if(id.longValue()==Majors.DABESTAN_3_SALE_AVAL.getId()){
			return Majors.DABESTAN_3_SALE_AVAL;
		}
		if(id.longValue()==Majors.DABESTAN_3_SALE_DOVOM.getId()){
			return Majors.DABESTAN_3_SALE_DOVOM;
		}
		if(id.longValue()==Majors.DABIRESTAN_3_SALE_AVAL.getId()){
			return Majors.DABIRESTAN_3_SALE_AVAL;
		}
		if(id.longValue()==Majors.DABIRESTAN_3_SALE_DOVOM_HUMANITIES.getId()){
			return Majors.DABIRESTAN_3_SALE_DOVOM_HUMANITIES;
		}
		if(id.longValue()==Majors.DABIRESTAN_3_SALE_DOVOM_MATH.getId()){
			return Majors.DABIRESTAN_3_SALE_DOVOM_SCIRENCE;
		}
		if(id.longValue()==Majors.GENERAL.getId()){
			return Majors.GENERAL;
		}
		if(id.longValue()==Majors.HONARESTAN.getId()){
			return Majors.HONARESTAN;
		}
		if(id.longValue()==Majors.HUMANITIES.getId()){
			return Majors.HUMANITIES;
		}
		if(id.longValue()==Majors.MATHEMATICS.getId()){
			return Majors.MATHEMATICS;
		}
		if(id.longValue()==Majors.RAHNAMAI.getId()){
			return Majors.RAHNAMAI;
		}
		if(id.longValue()==Majors.SCIRENCE.getId()){
			return Majors.SCIRENCE;
		}
		return null;
	}
	
	public String getName() {
		String name = "";
		switch (this) {
		case DABESTAN_3_SALE_AVAL:
			name = "دبستان دوره اول";
			break;
		case DABESTAN_3_SALE_DOVOM:
			name = "دبستان دوره دوم";
			break;
		case DABIRESTAN_3_SALE_AVAL:
			name = "دوره اول دبیرستان";
			break;
		case DABIRESTAN_3_SALE_DOVOM_HUMANITIES:
			name = "انسانی دوره جدید";
			break;
		case DABIRESTAN_3_SALE_DOVOM_MATH:
			name = "ریاضی دوره جدید";
			break;
		case DABIRESTAN_3_SALE_DOVOM_SCIRENCE:
			name = "علوم تجربی دوره جدید";
			break;
		case GENERAL:
			name = "عمومی";
			break;
		case HONARESTAN:
			name = "هنرستان";
			break;
		case HUMANITIES:
			name = "علوم انسانی";
			break;
		case MATHEMATICS:
			name = "ریاضی";
			break;
		case RAHNAMAI:
			name = "راهنمایی";
			break;
		case SCIRENCE:
			name = "علوم تجربی";
			break;
		default:
			break;
		}
		return name;
	}
}
