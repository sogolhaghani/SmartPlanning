package ir.smartplanning.client.widget;

import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;

public interface QuestionTemplate extends SafeHtmlTemplates {
	@SafeHtmlTemplates.Template("<div class='{4}'  name='equation' elementName=\"question{5}\" dir=\"{0}\"   align='{1}'><span class=\"{6}\">{2})</span>{3}</div>")
	SafeHtml cell(String dir,String align,String counter, SafeHtml question,String htmlClass,String elementName,String classFloat);

}
