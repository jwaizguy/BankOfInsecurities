package com.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.orm.Account;

public class AccountDAOImpl implements AccountDAO
{
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public Account getAccountByID(long accountID)
	{
		Session currentSession = sessionFactory.getCurrentSession();
		return (Account)currentSession.get(Account.class, accountID);
	}

	@Transactional
	public List<Account> getAccountsByUserID(String userID)
	{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Account.class);
		criteria.add(Restrictions.eq("userID", userID));
		return (List<Account>)criteria.list();
	}
	
	@Override
	@Transactional
	// modify the balance of a given account by the input amount
	public void modifyBalance(long accountID, double amount)
	{
		Session session = sessionFactory.getCurrentSession();
		Account account = (Account)session.get(Account.class, accountID);
		account.setAccountBalance(account.getAccountBalance() + amount);
		session.update(account);
		session.flush();
	}
	
	@Override
	@Transactional
	public String getUserID(long accountID)
	{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Account.class);
		criteria.add(Restrictions.eq("accountID", accountID));
		criteria.setProjection(Projections.property("userID"));
		return (String)criteria.uniqueResult();
	}
	
	@Override
	@Transactional
	public boolean doesAccountIDExist(long accountID) {
		Session session = sessionFactory.getCurrentSession();
		Account account = (Account)session.get(Account.class, accountID);
		return account != null;
	}
	

}
