package ch.tmonney.hibernate.mapsid;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class UpdateTest extends AbstractTest {

    @Test
    public void updateWorking() {
        em.getTransaction().begin();
        em.persist(createWorkingEntity(1L, 2L));
        em.getTransaction().commit();

        em.clear();

        em.getTransaction().begin();
        WorkingParent entity = em.find(WorkingParent.class, new Key(1L, 2L));
        entity.getChild().setValue("new value");
        em.getTransaction().commit();

        em.clear();
        entity = em.find(WorkingParent.class, new Key(1L, 2L));
        assertThat(entity.getChild().getValue(), is("new value"));
        em.getTransaction().begin();

    }

    @Test
    public void updateFailing() {
        em.getTransaction().begin();
        em.persist(createFailingEntity(1L, 2L));
        em.getTransaction().commit();

        em.clear();

        // this fails when committing: Hibernate tries to update a child with key (1L, 1L)
        // and reports an optimistic lock exception since this child does not exist
        em.getTransaction().begin();
        FailingParent entity = em.find(FailingParent.class, new Key(1L, 2L));
        entity.getChild().setValue("new value");
        em.getTransaction().commit();
    }
}
