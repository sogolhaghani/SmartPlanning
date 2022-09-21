package ir.smartplanning.server.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.type.StringType;

import com.google.inject.Inject;
import com.google.inject.Provider;

import ir.smartplanning.server.InfrastructureException;
import ir.smartplanning.server.dao.UserDao;
import ir.smartplanning.server.domain.User;

public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {

	@Inject
	public UserDaoImpl(Provider<HibernateUtil> hibernateUtilProvider)
			throws InfrastructureException {
		super(hibernateUtilProvider);
	}

	@Override
	public User findUserByUserName(String userName) {
		try {
			Query q = this.getSession()
					.createQuery("from User where userName like :userName ")
					.setString("userName", userName);
			User user = (User) q.uniqueResult();
			return user;
		} catch (InfrastructureException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User login(byte[] pass, String userName) {
		try {
			Query q = this
					.getSession()
					.createQuery(
							"from User Where UserName like :userName and Password = :password")
					.setString("userName", userName)
					.setBinary("password", pass);
			User user = (User) q.uniqueResult();
			return user;
		} catch (InfrastructureException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findBookInfo(long majorId, byte grade) {
		try {
			Query q = this
					.getSession()
					.createSQLQuery(
							"EXEC	[BI].[GetBookInfo] @MajorID = :majorId, @Grade = :grade ")
					.addScalar("name", StringType.INSTANCE)
					.setLong("majorId", majorId).setByte("grade", grade);
			List<String> list = q.list();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
