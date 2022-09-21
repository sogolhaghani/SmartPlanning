package ir.smartplanning.client.place;

import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

/**
 * The central location of all name tokens for the application. All
 * {@link ProxyPlace} classes get their tokens from here. This class also makes
 * it easy to use name tokens as a resource within UIBinder xml files.
 * <p />
 * The public static final String is used within the annotation
 * {@link NameToken}, which can't use a method and the method associated with
 * this field is used within UiBinder which can't access static fields.
 * <p />
 * Also note the exclamation mark in front of the tokens, this is used for
 * search engine crawling support.
 */

public class NameTokens {
	public static String error = "error";
	public static final String login = "login";
	public static final String signup = "signup";
	public static final String home = "home";
	public static final String main = "main";
	public static final String examresult = "examresult";
	public static final String consultation = "consultation";
	public static final String takeexam = "takeexam";

	public static String getError() {
		return error;
	}

	public static String getLogin() {
		return login;
	}

	public static String getSignup() {
		return signup;
	}

	public static String getHome() {
		return home;
	}

	public static String getMain() {
		return main;
	}

	public static String getExamresult() {
		return examresult;
	}

	public static String getConsultation() {
		return consultation;
	}

	public static String getTakeexam() {
		return takeexam;
	}
}
