package ir.smartplanning.server.dao;

import java.util.List;

import ir.smartplanning.server.domain.User;

public interface UserDao extends GenericDao<User, Long> {

	public User findUserByUserName(String trim);

	public User login(byte[] pass, String userName);

	public List<String> findBookInfo(long majorId, byte grade);

}
