package ir.smartplanning.shared;

public class ValidationMethods {

	public static boolean isJustDigit(String data){
		boolean result = true;
		try {
			Long.parseLong(data);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

}
