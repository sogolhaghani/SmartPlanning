package ir.smartplanning.client.widget;

import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;

public interface OptionCellTemplate extends SafeHtmlTemplates {

	@SafeHtmlTemplates.Template("<span>" +
			"<input   type=\"radio\" name='{1}' class=\"{0}\"/></span>")
	SafeHtml radioCell( String className,String nameId);

	
	
	@SafeHtmlTemplates.Template("<label  name='equation' " +
			"elementName=\"question{3}\" class='{2}'/><span class='{4}'>{0})</span> {1}</label>")
	SafeHtml cell_content(Integer counter, SafeHtml op,String clazz,String elementName,String numberFont);



}