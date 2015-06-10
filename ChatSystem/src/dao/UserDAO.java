package dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import Exception.DBOperatorException;
import model.User;
import hibernateUtil.HibernateUtil;

public class UserDAO {
	private static UserDAO instance = null;

	private Session session = null;

	private UserDAO() {
	}

	public static UserDAO getInstance() {
		if (instance == null) {
			instance = new UserDAO();
		}
		return instance;
	}

	public void add(User user) {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.save(user);
		tx.commit();
	}

	public void update(User user) {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.update(user);
		tx.commit();
	}

	public void remove(User user) {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.delete(user);
		tx.commit();
	}

	public List<User> listAll() {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		List<User> list = session.createQuery("from User").list();
		tx.commit();
		return list;
	}

	public  User  loadbyuserid(String id) {
		User user=null;
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Iterator<User> dataItor=session.createQuery("from User where UserId = ?").setParameter(0, id).iterate();
		while(dataItor.hasNext()){
			user=dataItor.next();
		}
		//System.out.println(user.getUserPwd());
		Hibernate.initialize(user);
		tx.commit();
		return user;

	}

}
