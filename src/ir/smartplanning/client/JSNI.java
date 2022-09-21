package ir.smartplanning.client;

import com.google.gwt.user.client.Window;

public class JSNI {

	public static Object callNative(String methodName, Object value1,
			Object value2) {
		try {
			Object obj = jsCallNative(methodName, value1, value2);
			return obj;
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static native Object jsCallNative(String methodName, Object value1,
			Object value2) /*-{
		return $wnd[methodName](value1, value2);
	}-*/;

	public static Object mathJaxNative(String methodName, Object value1,
			Object value2) {
		try {
			Object obj = jsCallNative(methodName, value1, value2);
			return obj;
		} catch (RuntimeException e) {
			Window.alert("به علت کندی اینترنت در حال حاضر امکان مشاهده درست فرمول های ریاضی را ندارید.\n بعد از رفع ایراد سرعت اینترنت لطفا از ابتدا وارد سایت شوید.");
		}
		return null;
	}
}
