package ch.tmonney.hibernate.mapsid;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;

public class AbstractTest {

    protected EntityManagerFactory emf;
    protected EntityManager em;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("test");
        em = emf.createEntityManager();
    }

    @After
    public void tearDown() {
        em.close();
        emf.close();
    }

    protected <T> void persistEntities(T... entities) {
        em.getTransaction().begin();
        for (T entity : entities) {
            em.persist(entity);
        }
        em.getTransaction().commit();
        em.clear();
        emf.getCache().evictAll();
    }

    protected WorkingParent createWorkingEntity(long id1, long id2) {
        WorkingParent parent = new WorkingParent();
        parent.id = new Key(id1, id2);
        parent.value = "parent" + id1 + "_" + id2;

        WorkingChild child = new WorkingChild();
        child.value = "child" + id1 + "_" + id2;

        parent.child = child;
        child.parent = parent;

        return parent;
    }

    protected FailingParent createFailingEntity(long id1, long id2) {
        FailingParent parent = new FailingParent();
        parent.id = new Key(id1, id2);
        parent.value = "parent" + id1 + "_" + id2;

        FailingChild child = new FailingChild();
        child.value = "child" + id1 + "_" + id2;

        parent.child = child;
        child.parent = parent;

        return parent;
    }
}
