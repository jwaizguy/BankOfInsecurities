package com.dao;

import java.util.HashMap;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.util.StringUtils;

import com.orm.UserDetail;

public class UserDetailDAOImpl implements UserDetailDAO
{
	private HashMap<String, Boolean> isAdminCache;
	private SessionFactory sessionFactory;
	
	public UserDetailDAOImpl() {
		this.isAdminCache = new HashMap<String, Boolean>();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public String getFullName(String userID)
	{
		return this.getFirstName(userID) + " " + this.getLastName(userID);
	}
	
	@Override
	@Transactional
	public String getFirstName(String userID)
	{
		UserDetail userDeatil = (UserDetail)sessionFactory.getCurrentSession().get(UserDetail.class, userID);
		return userDeatil.getfName();
	}
	
	@Override
	@Transactional
	public String getLastName(String userID)
	{
		UserDetail userDeatil = (UserDetail)sessionFactory.getCurrentSession().get(UserDetail.class, userID);
		return userDeatil.getlName();
	}
	
	@Override
	@Transactional
	public Boolean isAdmin(String userID) {
		if(this.isAdminCache.containsKey(userID)) {
			return this.isAdminCache.get(userID);
		}
		UserDetail userDetail = (UserDetail)sessionFactory.getCurrentSession().get(UserDetail.class, userID);
		
		boolean isAdmin = false;
		
		if(userDetail != null) {
			isAdmin = this.isCharAdmin(userDetail.getRoleID());
			this.isAdminCache.put(userID, isAdmin);
		}
		
		return isAdmin;
	}
	
	private boolean isCharAdmin(char roleID) {
		if(roleID == 'A' || roleID == 'a') {
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public UserDetail getUserDetail(String userID) {
		UserDetail userDeatil = (UserDetail)sessionFactory.getCurrentSession().get(UserDetail.class, userID);
		return userDeatil;
	}

	@Override
	@Transactional
	public boolean updateDetail(String userID,String fName, String lName, String emailID) {
		Session session=sessionFactory.getCurrentSession();
		UserDetail userDeatil = (UserDetail)session.get(UserDetail.class, userID);
		if(!StringUtils.isEmpty(fName)){
			userDeatil.setfName(fName);
		}
		if(!StringUtils.isEmpty(lName)){
			userDeatil.setlName(lName);
		}
		if(!StringUtils.isEmpty(emailID)){
			userDeatil.setEmailID(emailID);
		}
		session.update(userDeatil);
		return true;
	}
	
}
