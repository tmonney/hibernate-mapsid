package ch.tmonney.hibernate.mapsid;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Key implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id1")
    private Long id1;

    @Column(name = "id2")
    private Long id2;

    public Key() {
    }

    public Key(Long id1, Long id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

    public Long getId1() {
        return id1;
    }

    public void setId1(Long id1) {
        this.id1 = id1;
    }

    public Long getId2() {
        return id2;
    }

    public void setId2(Long id2) {
        this.id2 = id2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId1(), getId2());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Key)) {
            return false;
        }
        Key that = (Key) obj;
        return Objects.equals(this.getId1(), that.getId1()) && Objects.equals(this.getId2(), that.getId2());
    }
}