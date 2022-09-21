package ir.smartplanning.shared.proxies.nonpersists;

import ir.smartplanning.server.domain.nonpersist.ListItem;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;


@ProxyFor(value=ListItem.class)
public interface ListItemProxy extends ValueProxy{

	public Long getId();
	public void setId(Long id);
	public String getName();
	public void setName(String name);

}
