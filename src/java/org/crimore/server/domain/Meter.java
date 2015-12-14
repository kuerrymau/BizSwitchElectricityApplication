/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.crimore.server.domain;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Chayanne
 */
@Entity
@Table(name = "METER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Meter.findAll", query = "SELECT m FROM Meter m"),
    @NamedQuery(name = "Meter.findByMeterId", query = "SELECT m FROM Meter m WHERE m.meterId = :meterId"),
    @NamedQuery(name = "Meter.findByMeterNumber", query = "SELECT m FROM Meter m WHERE m.meterNumber = :meterNumber"),
    @NamedQuery(name = "Meter.findByAddress", query = "SELECT m FROM Meter m WHERE m.address = :address"),
    @NamedQuery(name = "Meter.findByEnabled", query = "SELECT m FROM Meter m WHERE m.enabled = :enabled")})
public class Meter implements IsSerializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "METER_ID")
    private Integer meterId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "METER_NUMBER")
    private String meterNumber;
    @Basic(optional = false)
//    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ADDRESS")
    private String address;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ENABLED")
    private int enabled;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "meter")
    private Collection<ElecTrans> elecTransCollection;

    public Meter() {
    }

    public Meter(Integer meterId) {
        this.meterId = meterId;
    }

    public Meter(String meterNumber) {
        this.meterNumber = meterNumber;
    }

    public Meter(String meterNumber, String address) {
        this.meterNumber = meterNumber;
        this.address = address;
    }

    public Meter(Integer meterId, String meterNumber, String address, int enabled) {
        this.meterId = meterId;
        this.meterNumber = meterNumber;
        this.address = address;
        this.enabled = enabled;
    }

    public Integer getMeterId() {
        return meterId;
    }

    public void setMeterId(Integer meterId) {
        this.meterId = meterId;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    @XmlTransient
    public Collection<ElecTrans> getElecTransCollection() {
        return elecTransCollection;
    }

    public void setElecTransCollection(Collection<ElecTrans> elecTransCollection) {
        this.elecTransCollection = elecTransCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (meterId != null ? meterId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Meter)) {
            return false;
        }
        Meter other = (Meter) object;
        if ((this.meterId == null && other.meterId != null) || (this.meterId != null && !this.meterId.equals(other.meterId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.crimore.server.domain.Meter[ meterId=" + meterId + " ]";
    }
    
}
