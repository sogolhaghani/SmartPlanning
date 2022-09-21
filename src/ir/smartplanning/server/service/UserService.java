package ir.smartplanning.server.service;

import java.util.List;

import ir.smartplanning.server.domain.nonpersist.UserItem;

public interface UserService {

	public UserItem login(String userName,String password);
	public UserItem register(String password,UserItem userItem);
	public boolean checkUserNameIsUniqe(String userName);
	public List<String> getBookInfo();
}
