/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.dao;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ftoniolo
 */
@Entity
@Table(name = "TipoCabecote")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoCabecote.findAll", query = "SELECT t FROM TipoCabecote t")})
public class TipoCabecote implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoTipoCabecote")
    private Integer codigoTipoCabecote;
    @Size(max = 50)
    @Column(name = "tipoCabecote")
    private String tipoCabecote;
    @OneToMany(mappedBy = "codigoTipoCabecote")
    private Collection<Motor> motorCollection;

    public TipoCabecote() {
    }

    public TipoCabecote(Integer codigoTipoCabecote) {
        this.codigoTipoCabecote = codigoTipoCabecote;
    }

    public Integer getCodigoTipoCabecote() {
        return codigoTipoCabecote;
    }

    public void setCodigoTipoCabecote(Integer codigoTipoCabecote) {
        this.codigoTipoCabecote = codigoTipoCabecote;
    }

    public String getTipoCabecote() {
        return tipoCabecote;
    }

    public void setTipoCabecote(String tipoCabecote) {
        this.tipoCabecote = tipoCabecote;
    }

    @XmlTransient
    public Collection<Motor> getMotorCollection() {
        return motorCollection;
    }

    public void setMotorCollection(Collection<Motor> motorCollection) {
        this.motorCollection = motorCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoTipoCabecote != null ? codigoTipoCabecote.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoCabecote)) {
            return false;
        }
        TipoCabecote other = (TipoCabecote) object;
        if ((this.codigoTipoCabecote == null && other.codigoTipoCabecote != null) || (this.codigoTipoCabecote != null && !this.codigoTipoCabecote.equals(other.codigoTipoCabecote))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.autodb.model.dao.TipoCabecote[ codigoTipoCabecote=" + codigoTipoCabecote + " ]";
    }
    
}
