package dao;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import model.*;
import model.Message;
import hibernateUtil.HibernateUtil;

public class MessageDAO {
private static MessageDAO instance = null;
	
	private Session session = null;
	
	private MessageDAO() {}
	
	public static MessageDAO getInstance() {
		if (instance == null) {
			instance = new MessageDAO();
		}
		return instance;
	}
	public void add(Message msg) {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.save(msg);
		tx.commit();
	}
	
	public void update(Message msg) {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.update(msg);
		tx.commit();
	}
	
	public void remove(Message msg) {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.delete(msg);
		tx.commit();
	}
	
	public List<Message> listAll() {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		List<Message> list = session.createQuery("from Messages").list();
		tx.commit();
		return list;
	}
	
	public List<Message> findMessage(String from,String to){
		Message msg=null;
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		List<Message> list=session.createQuery("from Message where fromUser=? and toUser=?").setParameter(0, from)
				.setParameter(1, to).list();
		Hibernate.initialize(msg);
		tx.commit();
		return list;
	}
	
}
