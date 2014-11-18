package ch.tmonney.hibernate.mapsid;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class WorkingParent {

    @EmbeddedId
    public Key id;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "parent")
    public WorkingChild child;

    @Basic
    public String value;
}