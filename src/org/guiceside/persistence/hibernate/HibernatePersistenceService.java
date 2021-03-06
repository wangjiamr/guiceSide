package org.guiceside.persistence.hibernate;

import com.google.inject.Inject;
import org.guiceside.persistence.PersistenceService;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * <p>
 * 继承PersistenceService<br/>
 * 实现start()方法初始化SessionFactory
 * </p>
 * @author zhenjia  <a href='mailto:zhenjiaWang@gmail.com'>email</a>
 * @since JDK1.5
 * @version 1.0 $Date:200808
 */
public class HibernatePersistenceService extends PersistenceService {
	private final SessionFactoryHolder sessionFactoryHolder;

	private final Configuration configuration;

	@Inject
	public HibernatePersistenceService(
			SessionFactoryHolder sessionFactoryHolder,
			Configuration configuration) {
		this.sessionFactoryHolder = sessionFactoryHolder;
		this.configuration = configuration;
	}

	@Override
	public void start() {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		try {
			SessionFactory sessionFactory = configuration.buildSessionFactory(registry);
			sessionFactoryHolder.setSessionFactory(sessionFactory);

		}catch (Exception e){
			StandardServiceRegistryBuilder.destroy( registry );
			e.printStackTrace();
		}
	}

	@Override
	public boolean equals(Object obj) {
		return sessionFactoryHolder
				.equals(((HibernatePersistenceService) obj).sessionFactoryHolder);
	}

	@Override
	public int hashCode() {
		return (sessionFactoryHolder != null ? sessionFactoryHolder.hashCode()
				: 0);
	}
}
