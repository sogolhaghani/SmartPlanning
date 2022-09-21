package ir.smartplanning.client.widget;

import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;

public interface AnswerCellTemplate extends SafeHtmlTemplates {
	@SafeHtmlTemplates.Template("<div class='{0}'  name='equation' elementName=\"answer{3}\"  align='{1}'>{2}</div>")
	SafeHtml cell(String clazz,String align, SafeHtml question,String elementName);
	
	@SafeHtmlTemplates.Template("<div></div>")
	SafeHtml emptyCell();
	
}