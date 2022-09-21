package ir.smartplanning.client;

import java.util.List;

public class Messages {

	public static String SUCCESS = "success";
	public static String ERORR = "error";
	public static String STANDARD = "standard";

	public static void ShowNoty(String firstLine, List<String> messages,
			String type) {
		String renderedMessage = renderMessages(firstLine, messages);
		JSNI.callNative(type, renderedMessage, 10000);

	}

	public static void ShowNoty(String message, String type) {
		String renderedMessage = renderMessages(message);
		JSNI.callNative(type, renderedMessage, 10000);
	}

	private static String renderMessages(String firstLine, List<String> messages) {
		String content = "";
		for (String message : messages) {
			content += "<div class=\"noty_messages_line\">" + message
					+ "</div>";
		}
		return "<div width=\"100%\" dir=\"rtl\" style=\"text-align : right\"><h3>"
				+ firstLine + "</h3>" + content + "</div>";
	}

	private static String renderMessages(String message) {
		String content = "<div class=\"noty_messages_line\">" + message
				+ "</div>";
		return content;
	}

}
