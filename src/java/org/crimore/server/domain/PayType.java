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
@Table(name = "PAY_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PayType.findAll", query = "SELECT p FROM PayType p"),
    @NamedQuery(name = "PayType.findByPayTypeId", query = "SELECT p FROM PayType p WHERE p.payTypeId = :payTypeId"),
    @NamedQuery(name = "PayType.findByName", query = "SELECT p FROM PayType p WHERE p.name = :name"),
    @NamedQuery(name = "PayType.findByDescription", query = "SELECT p FROM PayType p WHERE p.description = :description"),
    @NamedQuery(name = "PayType.findByEnabled", query = "SELECT p FROM PayType p WHERE p.enabled = :enabled")})
public class PayType implements IsSerializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PAY_TYPE_ID")
    private Integer payTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NAME")
    private String name;
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ENABLED")
    private int enabled;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "payType")
    private Collection<ElecTrans> elecTransCollection;

    public PayType() {
    }

    public PayType(Integer payTypeId) {
        this.payTypeId = payTypeId;
    }

    public PayType(String name) {
        this.name = name;
    }

    public PayType(Integer payTypeId, String name, int enabled) {
        this.payTypeId = payTypeId;
        this.name = name;
        this.enabled = enabled;
    }

    public Integer getPayTypeId() {
        return payTypeId;
    }

    public void setPayTypeId(Integer payTypeId) {
        this.payTypeId = payTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        hash += (payTypeId != null ? payTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PayType)) {
            return false;
        }
        PayType other = (PayType) object;
        if ((this.payTypeId == null && other.payTypeId != null) || (this.payTypeId != null && !this.payTypeId.equals(other.payTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.crimore.server.domain.PayType[ payTypeId=" + payTypeId + " ]";
    }
    
}
