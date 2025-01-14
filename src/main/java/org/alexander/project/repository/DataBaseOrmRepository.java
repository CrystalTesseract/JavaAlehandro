package org.alexander.project.repository;


import org.alexander.project.entity.Person;
import org.alexander.project.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;



@Repository
public class DataBaseOrmRepository {
    private Session session;
    private HibernateUtil hb = new HibernateUtil();
    private Transaction tx;

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
