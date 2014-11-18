package ch.tmonney.hibernate.mapsid;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

public class QueryTest extends AbstractTest {

    private static final String QUERY = "from %s p join fetch p.child order by p.id.id1, p.id.id2";

    // This test passes
    @Test
    public void testId1DifferentWorking() {
        // persist entities with different values for the *first* field of the composite key
        persistEntities(createWorkingEntity(11L, 1L), createWorkingEntity(12L, 1L), createWorkingEntity(13L, 1L));

        em.getTransaction().begin();
        List<WorkingParent> reloaded = loadEntities(WorkingParent.class);
        assertThat(reloaded.get(0).child.value, is("child11_1"));
        assertThat(reloaded.get(1).child.value, is("child12_1"));
        assertThat(reloaded.get(2).child.value, is("child13_1"));
        em.getTransaction().commit();
    }

    // This test passes
    @Test
    public void testId2DifferentWorking() {
        // persist entities with different values for the *second* field of the composite key
        persistEntities(createWorkingEntity(1L, 11L), createWorkingEntity(1L, 12L), createWorkingEntity(1L, 13L));

        em.getTransaction().begin();
        List<WorkingParent> reloaded = loadEntities(WorkingParent.class);
        assertThat(reloaded.get(0).child.value, is("child1_11"));
        assertThat(reloaded.get(1).child.value, is("child1_12"));
        assertThat(reloaded.get(2).child.value, is("child1_13"));
        em.getTransaction().commit();
    }

    // This test passes
    @Test
    public void testId1DifferentFailing() {
        // persist entities with different values for the *first* field of the composite key
        persistEntities(createFailingEntity(11L, 1L), createFailingEntity(12L, 1L), createFailingEntity(13L, 1L));

        em.getTransaction().begin();
        List<FailingParent> reloaded = loadEntities(FailingParent.class);
        assertThat(reloaded.get(0).child.value, is("child11_1"));
        assertThat(reloaded.get(1).child.value, is("child12_1"));
        assertThat(reloaded.get(2).child.value, is("child13_1"));
        em.getTransaction().commit();
    }

    // This test fails: all all FailingParent instances are linked to the *same instance* of FailingChild!
    @Test
    public void testId2DifferentFailing() {
        // persist entities with different values for the *second* field of the composite key
        persistEntities(createFailingEntity(1L, 11L), createFailingEntity(1L, 12L), createFailingEntity(1L, 13L));

        em.getTransaction().begin();
        List<FailingParent> reloaded = loadEntities(FailingParent.class);
        assertThat(reloaded.get(0).child.value, is("child1_11"));
        assertThat(reloaded.get(1).child.value, is("child1_12"));
        assertThat(reloaded.get(2).child.value, is("child1_13"));
        em.getTransaction().commit();
    }

    private <T> List<T> loadEntities(Class<T> entityClass) {
        return em.createQuery(String.format(QUERY, entityClass.getSimpleName()), entityClass).getResultList();
    }
}