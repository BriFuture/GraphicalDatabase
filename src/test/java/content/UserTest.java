package content;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author future
 *
 */

public class UserTest {
	@Before
	public void before() {
		
	}
	
	
//	@Test
	public void test1() {
		  //1. 配置类型安全的准服务注册类，这是当前应用的单例对象，不作修改，所以声明为final
	    //在configure("cfg/hibernate.cfg.xml")方法中，如果不指定资源路径，默认在类路径下寻找名为hibernate.cfg.xml的文件
	    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("/hibernate.cfg.xml").build();
	    //2. 根据服务注册类创建一个元数据资源集，同时构建元数据并生成应用一般唯一的的session工厂
	    SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

	    /****开始数据库操作******/
	    Session session = sessionFactory.openSession();//从会话工厂获取一个session

	    Transaction transaction = session.beginTransaction();//开启一个新的事务
	    User user = new User();
	    user.setName("zengh");
	    session.save(user);
	    transaction.commit();//提交事务
		
	}
	
//	@Test
	public void test2() {
		try {
		    Configuration lConf = new Configuration();
//		    lConf.addAnnotatedClass (eDocument.class);
		    lConf.setProperty("hibernate.dialect", "app.sqlite.SQLiteDialect");
		    lConf.setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC");
		    lConf.setProperty("hibernate.connection.url", "jdbc:sqlite:sfOrders.db");
		    lConf.setProperty("hibernate.connection.username", "");
		    lConf.setProperty("hibernate.connection.password", "");

		    StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(lConf.getProperties());
		    SessionFactory sessionFactory = lConf.buildSessionFactory(builder.build());
		    
		    /****开始数据库操作******/
		    Session session = sessionFactory.openSession();//从会话工厂获取一个session

		    Transaction transaction = session.beginTransaction();//开启一个新的事务
		    User user = new User();
		    user.setName("zengh");
		    session.save(user);
		    transaction.commit();//提交事务
		} catch (Throwable ex) {
		    throw new ExceptionInInitializerError(ex);
		}
	}

}
