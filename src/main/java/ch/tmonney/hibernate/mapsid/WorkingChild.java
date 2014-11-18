package ch.tmonney.hibernate.mapsid;

import javax.persistence.Basic;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class WorkingChild {
    @EmbeddedId
    private Key id;

    @OneToOne
    @MapsId
    @JoinColumns({
            @JoinColumn(name = "id1", referencedColumnName = "id1"),
            @JoinColumn(name = "renamed_id2", referencedColumnName = "id2")
    })
    private WorkingParent parent;

    @Basic
    private String value;

    public Key getId() {
        return id;
    }

    public void setId(Key id) {
        this.id = id;
    }

    public WorkingParent getParent() {
        return parent;
    }

    public void setParent(WorkingParent parent) {
        this.parent = parent;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}