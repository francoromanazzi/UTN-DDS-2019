package servicios;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Session {
    private static EntityManager em = Persistence.createEntityManagerFactory("db").createEntityManager();
    private static EntityTransaction transaction = em.getTransaction();

    public static EntityManager getEntityManager(){
        return em;
    }
    
    public static void beginTransaction(){
        transaction.begin();
    }
    
    public static void rollbackTransaction(){
        transaction.rollback();
    }
    
    public static void commitTransaction(){
        transaction.commit();
    }
}
