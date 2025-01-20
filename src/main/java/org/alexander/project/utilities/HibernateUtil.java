package org.alexander.project.utilities;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

@Component
public class HibernateUtil {
    private final SessionFactory sessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory();
    }

    public void shutdown() {
        getSessionFactory().close();
    }
}
