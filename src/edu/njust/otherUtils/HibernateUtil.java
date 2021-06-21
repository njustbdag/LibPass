package edu.njust.otherUtils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate��ܵĹ�����
 * @author Administrator
 */
public class HibernateUtil {
	
	// ctrl + shift + x
	private static final Configuration CONFIG;
	private static final SessionFactory FACTORY;
	
	// ��д��̬�����
	static{
		// ����XML�������ļ�
		CONFIG = new Configuration().configure();
		// ���칤��
		FACTORY = CONFIG.buildSessionFactory();
	}
	
	/**
	 * �ӹ����л�ȡSession����
	 * @return
	 */
	public static Session getSession(){
		return FACTORY.openSession();
	}
	
	/**
	 * // ��ThreadLocal���л�ȡ��session�Ķ���
	 * @return
	 */
	public static Session getCurrentSession(){
		return FACTORY.getCurrentSession();
	}
	
	public static void main(String[] args) {
		// ���û�ȡsession�ķ���
		getSession();
	}
	
}
