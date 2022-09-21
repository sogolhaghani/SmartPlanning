package ir.smartplanning.shared.enums;

public enum Recommendation {
	RECOMMENDATION_1(1), RECOMMENDATION_2(2), RECOMMENDATION_3(3), RECOMMENDATION_4(
			4), RECOMMENDATION_5(5), RECOMMENDATION_6(6), RECOMMENDATION_7(7), RECOMMENDATION_8(
			8), RECOMMENDATION_9(9), RECOMMENDATION_10(10), RECOMMENDATION_11(
			11), RECOMMENDATION_12(12), RECOMMENDATION_13(13), RECOMMENDATION_14(
			14), RECOMMENDATION_15(15), RECOMMENDATION_16(16), RECOMMENDATION_17(
			17), RECOMMENDATION_18(18), RECOMMENDATION_19(19), RECOMMENDATION_20(
			20), RECOMMENDATION_21(21), RECOMMENDATION_22(22), RECOMMENDATION_23(
			23);

	private int id;

	private Recommendation(int id) {
		this.id = id;
	}

	public static String getRecommendationText(Recommendation recommendation) {
		String text = "";
		switch (recommendation) {
		case RECOMMENDATION_1:
			text = "وضعیتت تو این مبحث خوبه و نسبت به دانش آموزای دیگه خیلی جلو هستی . به همین روندت ادامه بده . بهت پیشنهاد میکنم  بیشتر تست هایی که حل میکنی درجه سختیشون یه سطح بالا تر از اینایی باشه که تا الان حل کردی"
					+ ". می تونی اگه مطمئنی که این قسمت رو خوب یاد گرفتی بخشی از وقتت رو بزاری روی درسای دیگه ات ";
			break;
		case RECOMMENDATION_2:
			text = "وضعیتت تو این مبحث خوبه  . تمرین ها رو هم خوب حل کردی ولی برای اینکه درصدت بالا بره بهتره وقت بیشتری برای تست حل کردن میزاشتی  ";
			break;
		case RECOMMENDATION_3:
			text = " معلومه که درس رو خوب خوندی ولی تو تست زنی بی دقتی داری چون تست های غلط به نسبت داشتی. بهتره به جای اینکه وقت رو بزاری و جلوتر از برنامه پیش بری روی این بی دقتی هات تمرکز کنی ";
			break;
		case RECOMMENDATION_4:
			text = " درسته که وقت زیادی رو صرف مطالعه این مبحث کردی ولی باید بجای اینکه جلوتر از برنامه پیش بری این مبحث رو خوبه خوب یاد بگیری چون درصدهات قابل قبول نیست";
			break;
		case RECOMMENDATION_5:
			text = "دوباره این مبحث رو باید بخونی ، ولی این بار فقط برروی مطلب گفته شده تمرکز کن تا بهتر یاد بگیری. قبل از اینکه تست حل کنی چند تا مثال با پاسخ رو بخون. شاید ضعفت توی حل تمرین باشه";
			break;
		case RECOMMENDATION_6:
			text = "وضعیتت تو این مبحث خوبه و نسبت به دانش آموزای دیگه خیلی جلو هستی . به همین روندت ادامه بده . بهت پیشنهاد میکنم  اکه وقت داشتی تست بیشتر حل کنی تا کاملا به همه ابعاد این مبحث تسلط داشته باشی";
			break;
		case RECOMMENDATION_7:
			text = "با اینکه درصدت خوبه ولی نمی تونم دقیق بگم که تو این مبحث خیلی خوبی . یکی از مهمترین راه هایی که باعث میشه تا توی یه مبحث تسلط کامل داشته باشی حل تست به اندازه کافیه. بهتره به جای اینکه جلوتر از برنامه پیش بری تست بیشتر حل کنی";
			break;
		case RECOMMENDATION_8:
			text = "این تعداد تستی که حل کردی اصلا کافی نیست. به جای اینکه وققت رو صرف مبحثای دیگه کنی باید تست بیشتر حل کنی تا بشه گفت این مبحث رو خوب یاد گرفتی";
			break;
		case RECOMMENDATION_9:
			text = "تقریبا میتونم بگم که روی این موضوع تسلط داری. فقط بهتره از این به بعد مدیریت زمان داشته باشی. حجم درسا زیاده و باید به همش برسی.";
			break;
		case RECOMMENDATION_10:
			text = "خیلی خوبه کاملا داری با برنامه پیش میری . به همین روند ادامه بده. ";
			break;
		case RECOMMENDATION_11:
			text = " در صدت نشون میده که این قسمت رو خوب یا گرفتی. ولی الان سال کنکوره و برای اینکه نتیجه بهتری بگیری لازمه که تو زمان استاندارد نمره خوبی بگیری. روی مدیریت زمانت بیشتر کار کن.";
			break;
		case RECOMMENDATION_12:
			text = " درسته که تو تمرین ها نمره خوبی گرفتی و اینجا دو تا مسئله وجود داره هم این که وقت زیادی برای این قسمت گذاشتی و تست خیلی کم حل کردی. اگه قبلا اصلا این مبحث رو نخوندی خیلی اشکالی نداره. ولی در کل باید تست زیاد حل کنی و مدیریت زمان رو هم رعایت کنی.";
			break;
		case RECOMMENDATION_13:
			text="اگه دانش آموز سال پایه بودی خیلی مهم نبود که تست یا تمرین حل نکردی ولی الان شرایط فرق میکنه.فقط با درس خوندن بدون تمرین و تست نمی تونی آماده باشی";
			break;
		case RECOMMENDATION_14:
			text="";
		default:
			break;
		}
		return text;
	}

	public static Recommendation getRecommendationNumber(int id) {
		switch (id) {
		case 1:
			return RECOMMENDATION_1;
		case 2:
			return RECOMMENDATION_2;
		case 3:
			return RECOMMENDATION_3;
		case 4:
			return RECOMMENDATION_4;
		case 5:
			return RECOMMENDATION_5;
		case 6:
			return RECOMMENDATION_6;
		case 7:
			return RECOMMENDATION_7;
		case 8:
			return RECOMMENDATION_8;
		case 9:
			return RECOMMENDATION_9;
		case 10:
			return RECOMMENDATION_10;
		case 11:
			return RECOMMENDATION_11;
		case 12:
			return RECOMMENDATION_12;
		case 13:
			return RECOMMENDATION_13;
		case 14:
			return RECOMMENDATION_14;
		case 15:
			return RECOMMENDATION_15;

		default:
			break;
		}
		return null;
	}

	public void setId(int id) {
		this.id = id;
	}
}
