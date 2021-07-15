package com.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.orm.Transaction;

public class TransactionDAOImpl implements TransactionDAO
{
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public List<Transaction> getTransactionHistoryInformation(String userID)
	{
		List<Transaction> exampleOutput = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Transaction.class);
		criteria.add(Restrictions.eq("userID", userID));
		exampleOutput = (List<Transaction>)criteria.list();
		return exampleOutput;
	}

	@Override
	@Transactional
	public void addNewTransaction(Transaction transaction)
	{
		Session session = sessionFactory.getCurrentSession();
		session.save(transaction);
		session.flush();
	}
}
