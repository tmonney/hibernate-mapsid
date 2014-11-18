package ch.tmonney.hibernate.mapsid;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class FailingParent {

    @EmbeddedId
    private Key id;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "parent")
    private FailingChild child;

    @Basic
    private String value;

    public Key getId() {
        return id;
    }

    public void setId(Key id) {
        this.id = id;
    }

    public FailingChild getChild() {
        return child;
    }

    public void setChild(FailingChild child) {
        this.child = child;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}