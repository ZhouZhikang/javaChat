package dao;

import hibernateUtil.HibernateUtil;

import java.util.Iterator;
import java.util.List;

import model.*;

import org.hibernate.SessionFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class FriendDAO {
	private static FriendDAO instance = null;

	private Session session = null;

	private FriendDAO() {
	}

	public static FriendDAO getInstance() {
		if (instance == null) {
			instance = new FriendDAO();
		}
		return instance;
	}

	public void add(Friend friend) {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.save(friend);
		tx.commit();
	}

	public void update(Friend friend) {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.update(friend);
		tx.commit();
	}
		

	public void remove(Friend friend) {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.delete(friend);
		tx.commit();
	}

	public List<Friend> listAll(String id) {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		List<Friend> list = session.createQuery("from Friend where Whos =?").setParameter(0, id).list();
		Hibernate.initialize(list);
		tx.commit();
		return list;
	}

	public  List<Friend> loadByFriendId(String id) {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		List<Friend> list=session.createQuery("from Friend where FriendId = ?").setParameter(0, id).list();
		tx.commit();
		return list;

	}
	
	public Friend findFriend(String friendId,String whos){
		Friend fr=null;
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Iterator<Friend> dataItor=session.createQuery("from Friend where FriendId=? and Whos=?").setParameter(0, friendId)
				.setParameter(1, whos).iterate();
		while(dataItor.hasNext()){
			fr=dataItor.next();
		}
		//System.out.println(user.getUserPwd());
		Hibernate.initialize(fr);
		tx.commit();
		return fr;
	}

}
