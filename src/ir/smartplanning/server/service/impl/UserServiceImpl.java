package ir.smartplanning.server.service.impl;

import ir.smartplanning.server.ConstantName;
import ir.smartplanning.server.InfrastructureException;
import ir.smartplanning.server.dao.UserDao;
import ir.smartplanning.server.dao.impl.HibernateUtil;
import ir.smartplanning.server.domain.Major;
import ir.smartplanning.server.domain.User;
import ir.smartplanning.server.domain.nonpersist.UserItem;
import ir.smartplanning.server.service.UserService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class UserServiceImpl extends GenericDomainServiceImpl<UserDao>
		implements UserService {

	@Inject
	public UserServiceImpl(UserDao clazz,
			Provider<HibernateUtil> hibernateUtilProvider)
			throws InfrastructureException {
		super(clazz, hibernateUtilProvider);
	}

	@Override
	public UserItem login(String userName, String password) {
		if (userName == null || userName.trim().isEmpty() || password == null
				|| password.trim().isEmpty()) {
			UserItem user = new UserItem();
			user.setId((long) -1);
			return user;
		}
		byte[] pass = digestPassword(password);
		User user = this.dao.login(pass, userName);
		UserItem userItem = new UserItem();
		if (user == null) {
			userItem.setId(-1L);
			return userItem;
		}
		setUserItem(user, userItem);
		userItem.setInExam(checkStudentWasInExam());
		getHttpSession().setAttribute(ConstantName.CURRENR_USER, userItem);
		return userItem;
	}

	private boolean checkStudentWasInExam() {
		// TODO Auto-generated method stub
		return false;
	}

	private void setUserItem(User user, UserItem userItem) {
		userItem.setFamily(user.getFamily());
		userItem.setGrade(user.getGrade());
		userItem.setId(user.getId());
		userItem.setMajorId(user.getMajor().getId());
		userItem.setName(user.getName());
		userItem.setSex(user.isSex());
		userItem.setUserName(user.getUserName());
	}

	//@Transactional
	@Override
	public UserItem register(String password, UserItem userItem) {
		User user = new User();
		user.setName(userItem.getName());
		user.setFamily(userItem.getFamily());
		user.setSex(userItem.isSex());
		user.setGrade(userItem.getGrade());
		Major major = new Major();
		major.setId(userItem.getMajorId());
		user.setMajor(major);
		user.setUserName(userItem.getUserName());
		byte[] pass = digestPassword(password);
		user.setPassword(pass);
		if (userIsValid(user) == false) {
			return null;
		}
		if (checkUserNameIsUniqe(user.getUserName()) == false) {
			return null;
		}
		User newUser=null;
		try {
			this.dao.beginTransaction();
			 newUser = this.dao.makePersistent(user);
			this.dao.commitTransaction();
		} catch (InfrastructureException e) {
			e.printStackTrace();
		}
		UserItem createdUserItem = new UserItem();
		setUserItem(newUser, createdUserItem);
		getHttpSession().setAttribute(ConstantName.CURRENR_USER, userItem);
		return createdUserItem;
	}

	private boolean userIsValid(User user) {
		if (user.getName() == null || user.getName().trim().isEmpty() == true) {
			return false;
		}
		if (user.getFamily() == null
				|| user.getFamily().trim().isEmpty() == true) {
			return false;
		}
		if (user.getUserName() == null
				|| user.getUserName().trim().isEmpty() == true) {
			return false;
		}
		if (user.getGrade() < 2 || user.getGrade() > 4) {
			return false;
		}
		if (user.getPassword().length == 0) {
			return false;
		}
		return true;
	}

	private byte[] digestPassword(String password) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(password.getBytes());
			byte[] dataBytes = messageDigest.digest();
			return dataBytes;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return new byte[0];
		}
	}

	@Override
	public boolean checkUserNameIsUniqe(String userName) {
		User user = this.dao.findUserByUserName(userName.trim());
		if (user == null) {
			return true;
		}
		return false;
	}

	@Override
	public List<String> getBookInfo() {
		Object object=getHttpSession().getAttribute(ConstantName.CURRENR_USER);
		if(object==null){
			return null;
		}
		if(object instanceof UserItem ==false){
			return null;
		}
		UserItem currentUser=(UserItem) object;
		long majorId=currentUser.getMajorId();
		byte grade=currentUser.getGrade();
		List<String> list=this.dao.findBookInfo(majorId,grade);
		return list;
	}

}
