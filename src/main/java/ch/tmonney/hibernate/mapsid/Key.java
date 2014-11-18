package ch.tmonney.hibernate.mapsid;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

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