package ir.smartplanning.client.widget;


import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;

public interface DisabledOptionCellTemplate extends SafeHtmlTemplates {

	@SafeHtmlTemplates.Template("<div align='{0}' class='{8}'><label   dir='{2}'  name='equation' elementName=\"question{6}\" class='{5}'/>" +
			"<input name='{1}' disabled type=\"radio\" class=\"{7}\"/><span class='{9}'>{3})</span> {4}</label></div>")
	SafeHtml cell(String align, Long name, String dir, Integer counter, SafeHtml op, String clazz, String elementName, String string, String inline,String numberFont);
	
	
	@SafeHtmlTemplates.Template("<span>" +
			"<input  disabled type=\"radio\" name='{1}' class=\"{0}\"/></span>")
	SafeHtml radioCell( String className,String nameId);

}
