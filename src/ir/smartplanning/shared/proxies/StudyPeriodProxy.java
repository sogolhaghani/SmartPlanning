package ir.smartplanning.shared.proxies;

import ir.smartplanning.server.EntityLocator;
import ir.smartplanning.server.domain.StudyPeriod;

import java.util.Date;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value=StudyPeriod.class,locator=EntityLocator.class)
public interface StudyPeriodProxy extends EntityProxy{

	public long getId() ;
	public Date getStartDate();
	public Date getEndDate() ;
	public byte getWeeksOrder();
}
