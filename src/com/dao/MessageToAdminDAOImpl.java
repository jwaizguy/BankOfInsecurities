package com.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.orm.UserMessage;

public class MessageToAdminDAOImpl implements MessageToAdminDAO
{
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	@Override
	@Transactional
	public void saveMessage(UserMessage userMsg)
	{	
		// Persist the UserMessage into DB
		Session session = sessionFactory.getCurrentSession();
		session.save(userMsg);
		session.flush();
	}

	@Override
	@Transactional
	public List<UserMessage> getAllMessages()
	{
		List<UserMessage> output = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserMessage.class);
		output = (List<UserMessage>)criteria.list();
		return output;
	}

	@Override
	@Transactional
	public UserMessage getUserMessageByID(String msgID) {
		Session currentSession = sessionFactory.getCurrentSession();
		return (UserMessage)currentSession.get(UserMessage.class, msgID);
	}
	
	@Override
	@Transactional
	public void deleteUserMessage(String msgID)
	{
		Session session = sessionFactory.getCurrentSession();
		// First load the UserMessge from DB based on msgID
		UserMessage userMsg = (UserMessage)session.load(UserMessage.class, msgID);
		// If DB contains the entry, delete it
		if(userMsg != null)
		{
			session.delete(userMsg);
		}
	}
}
