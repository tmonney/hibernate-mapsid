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