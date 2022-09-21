package ir.smartplanning.client.widget;

import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;

public interface ReadingContentTemplate extends SafeHtmlTemplates{
	@SafeHtmlTemplates.Template("<div class='{3}' elementName=\"question{4}\" name=\"equation\" dir=\"{0}\"   align=\"{1}\">{2}</div>")
	SafeHtml cell(String dir,String align, SafeHtml question,String htmlClazz,String elementName);
}
