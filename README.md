hibernate-mapsid
================

A reproducer project for a bug in Hibernate with @MapsId and composite primary key.

Suppose a pair of entities defined with the same composite primary key:

```java
@Entity
public class FailingParent {

    @EmbeddedId
    public Key id;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "parent")
    public FailingChild child;

    @Basic
    public String value;
}

@Entity
public class Child {
    @EmbeddedId
    private Key id;

    @OneToOne
    @MapsId
    @JoinColumns({
            @JoinColumn(name = "id1", referencedColumnName = "id1"),
            @JoinColumn(name = "id2", referencedColumnName = "id2")
    })
    public Parent parent;

    @Basic
    public String value;
}

@Embeddable
public class Key implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id1")
    public Long id1;

    @Column(name = "id2")
    public Long id2;

    public Key() {
    }

    public Key(Long id1, Long id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id1, id2);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Key)) {
            return false;
        }
        Key that = (Key) obj;
        return Objects.equals(this.id1, that.id1) && Objects.equals(this.id2, that.id2);
    }
}
```

The bug manifests itself when loading a `Parent` entity, getting its `child`, modifying some of its field. The following error happens when flushing the entity manager:
```
Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1
```

This is because Hibernates executes the following query:
```sql
update FailingChild set value=? where id1=? and id2=?
```
And passes the *same value* for ```id1```and ```id2```, which is obviously wrong.

Interestingly enough, everything works OK if the mapping of the ```Child``` class is changed as follows (notice the second join column of the parent is renamed):
```java
@Entity
public class WorkingChild {
    @EmbeddedId
    public Key id;

    @OneToOne
    @MapsId
    @JoinColumns({
            @JoinColumn(name = "id1", referencedColumnName = "id1"),
            @JoinColumn(name = "renamed_id2", referencedColumnName = "id2")
    })
    public WorkingParent parent;

    @Basic
    public String value;
}
```
