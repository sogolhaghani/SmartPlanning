package ir.smartplanning.client.widget;

import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeUri;

public interface QuestionSummaryTemplate extends SafeHtmlTemplates {
	@SafeHtmlTemplates.Template("<div ><img src=\"{6}\" style=\"{9}\"/><div class='{0}' align='{1}'>[{2},{3},{7} {4},{8},{5}]</div></div>")
	SafeHtml cell(String className,String align, String origin, String major, short publishYear,String topic, SafeUri difficultLevel,String publishYearText,String module,SafeStyles style);
	
	@SafeHtmlTemplates.Template("<div id=\"summery_id\"><img src=\"{6}\" style=\"{9}\"/><div class='{0}' align='{1}'>[{2},{3},{7} {4},{8},{5}]</div></div>")
	SafeHtml cellWithId(String className,String align, String origin, String major, short publishYear,String topic, SafeUri difficultLevel,String publishYearText,String module,SafeStyles style);
	
	
	@SafeHtmlTemplates.Template("<div ><img src=\"{6}\" style=\"{10}\"/><div class='{0}' align='{1}'>[{2},{3},{4},{8},{5},{7}]</div></div>")
	SafeHtml operatorCell(String className,String align, String origin, String major, short publishYear,String topic, SafeUri difficultLevel,String operatorInfo,String publishYearText,String module,SafeStyles style);
	
	
	@SafeHtmlTemplates.Template("<div ><img src=\"{6}\" style=\"{7}\"/><div class='{0}' align='{1}'>[{2},{3} {4},{5}]</div><div>")
	SafeHtml problemSolvingCell(String className,String align,String module ,String publishYearText,short pageNo,String topic, SafeUri difficultLevel,SafeStyles style);
	
	@SafeHtmlTemplates.Template("<div><div>")
	SafeHtml emptyCell();
}
