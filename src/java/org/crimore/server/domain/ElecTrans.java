/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.crimore.server.domain;

import com.google.gwt.user.client.rpc.IsSerializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Chayanne
 */
@Entity
@Table(name = "ELEC_TRANS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ElecTrans.findAll", query = "SELECT e FROM ElecTrans e"),
    @NamedQuery(name = "ElecTrans.findByElecTransId", query = "SELECT e FROM ElecTrans e WHERE e.elecTransId = :elecTransId"),
    @NamedQuery(name = "ElecTrans.findByNoOfTokens", query = "SELECT e FROM ElecTrans e WHERE e.tokenNumber = :tokenNumber"),
    @NamedQuery(name = "ElecTrans.findByAmount", query = "SELECT e FROM ElecTrans e WHERE e.amount = :amount")})
public class ElecTrans implements IsSerializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ELEC_TRANS_ID")
    private Integer elecTransId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    private int amount;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "TOKEN_NUMBER")
    private int tokenNumber;
    @JoinColumn(name = "PAY_TYPE_ID", referencedColumnName = "PAY_TYPE_ID")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private PayType payType;
    @JoinColumn(name = "METER_ID", referencedColumnName = "METER_ID")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Meter meter;

    public ElecTrans() {
    }

    public ElecTrans(Integer elecTransId) {
        this.elecTransId = elecTransId;
    }

    public ElecTrans(Integer elecTransId, int tokenNumber, int amount) {
        this.elecTransId = elecTransId;
        this.tokenNumber = tokenNumber;
        this.amount = amount;
    }

    public ElecTrans(Integer elecTransId, int tokenNumber, int amount, int payType, int meterId) {
        this.elecTransId = elecTransId;
        this.tokenNumber = tokenNumber;
        this.amount = amount;
        this.payType = new PayType(payType);
        this.meter = new Meter(meterId);
    }

    public ElecTrans(Integer elecTransId, int tokenNumber, int amount, PayType payType, Meter meterId) {
        this.elecTransId = elecTransId;
        this.tokenNumber = tokenNumber;
        this.amount = amount;
        this.payType = payType;
        this.meter = meterId;
    }

    public Integer getElecTransId() {
        return elecTransId;
    }

    public int getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(int tokenNumber) {
        this.tokenNumber = tokenNumber;
    }
        
    public void setElecTransId(Integer elecTransId) {
        this.elecTransId = elecTransId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public Meter getMeter() {
        return meter;
    }

    public void setMeter(Meter meter) {
        this.meter = meter;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (elecTransId != null ? elecTransId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ElecTrans)) {
            return false;
        }
        ElecTrans other = (ElecTrans) object;
        if ((this.elecTransId == null && other.elecTransId != null) || (this.elecTransId != null && !this.elecTransId.equals(other.elecTransId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.crimore.server.domain.ElecTrans[ elecTransId=" + elecTransId + " ]";
    }
    
}
