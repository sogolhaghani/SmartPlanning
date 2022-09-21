package ir.smartplanning.shared.proxies.nonpersists;

import ir.smartplanning.server.domain.nonpersist.UserItem;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;


@ProxyFor(value=UserItem.class)
public interface UserItemProxy extends ValueProxy{
	public Long getId() ;
	public void setId(Long id) ;
	public long getMajorId() ;
	public void setMajorId(long majorId) ;
	public byte getGrade() ;
	public void setGrade(byte grade) ;
	public String getName() ;
	public void setName(String name);
	public String getFamily();
	public void setFamily(String family);
	public String getUserName();
	public void setUserName(String userName);
	public boolean isSex();
	public void setSex(boolean sex) ;
}
