package com.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.orm.UserCredential;

public class UserCredentialDAOImpl implements UserCredentialDAO
{
	private Logger logger = Logger.getLogger(UserCredentialDAOImpl.class);
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	@Transactional
	public String getPassword(String id)
	{
		Criteria pCriteria = sessionFactory.getCurrentSession().createCriteria(UserCredential.class);
		pCriteria.add(Restrictions.eq("id", id)).setProjection(Projections.property("password"));
		return (String)pCriteria.uniqueResult();
	}
	
	@Override
	@Transactional
	public boolean updatePassword(String userID, String password) {
		Session session=sessionFactory.getCurrentSession();
		UserCredential userCredential = (UserCredential)session.get(UserCredential.class, userID);
		userCredential.setPassword(password);
		session.update(userCredential);
		return false;
	}
	
	@Override
	@Transactional
	public boolean authenticate(String id, String password)
	{
		String queryString = "SELECT * FROM TB_USER_CREDENTIALS "
						+ "WHERE id='" + id + "' AND password='" + password + "'";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(queryString);
		logger.error(query.getQueryString());
		if(query.list().size()>0)
		{
			return true;
		}
		return false;
	}
	
	@Override
	@Transactional
	public List<UserCredential> getUserCredentialList() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserCredential.class);
		return (List<UserCredential>)criteria.list();
	}
	
	
}
