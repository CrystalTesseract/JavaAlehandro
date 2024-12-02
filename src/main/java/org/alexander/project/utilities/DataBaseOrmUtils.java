package org.alexander.project.utilities;


import org.alexander.project.entity.Person;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DataBaseOrmUtils {
    Session session;
    HibernateUtil hb = new HibernateUtil();
    Transaction tx;

    public void openSession() {
        session = hb.getSessionFactory().openSession();
    }

    public void closeSession() {
        hb.shutdown();
    }

    public void startTransaction() {
        tx = session.beginTransaction();
    }

    public void commitTransaction() {
        tx.commit();
    }

    public void save(Object obj) {
        session.save(obj);
    }

    public Object load(int id) {
        return session.load(Person.class, id);
    }
}
