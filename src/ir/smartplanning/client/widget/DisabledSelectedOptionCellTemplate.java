package ir.smartplanning.client.widget;

import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;

public interface DisabledSelectedOptionCellTemplate  extends SafeHtmlTemplates {

	@SafeHtmlTemplates.Template("<div align='{0}' class='{7}'><label  dir='{2}'  name='equation' class='{5}'/>" +
			"<input checked  disabled  name='{1}'  type=\"radio\" class=\"{6}\"/><span class='{8}'>{3})</span> {4}</label></div>")
	SafeHtml cell(String align ,Long name, String dir, Integer counter, SafeHtml op,String htmlClazz, String string,String inline,String numberFont);

	@SafeHtmlTemplates.Template("<span>" +
			"<input checked disabled type=\"radio\" name='{1}' class=\"{0}\"/></span>")
	SafeHtml radioCell( String className,String nameId);


}