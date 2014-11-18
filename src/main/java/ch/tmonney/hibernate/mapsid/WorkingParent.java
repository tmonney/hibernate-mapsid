package ch.tmonney.hibernate.mapsid;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class WorkingParent {

    @EmbeddedId
    private Key id;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "parent")
    private WorkingChild child;

    @Basic
    private String value;

    public Key getId() {
        return id;
    }

    public void setId(Key id) {
        this.id = id;
    }

    public WorkingChild getChild() {
        return child;
    }

    public void setChild(WorkingChild child) {
        this.child = child;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}