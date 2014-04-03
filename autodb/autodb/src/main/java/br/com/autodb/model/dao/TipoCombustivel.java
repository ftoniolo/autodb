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
@Table(name = "TipoCombustivel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoCombustivel.findAll", query = "SELECT t FROM TipoCombustivel t")})
public class TipoCombustivel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoTipoCombustivel")
    private Integer codigoTipoCombustivel;
    @Size(max = 50)
    @Column(name = "tipoCombustivel")
    private String tipoCombustivel;
    @Size(max = 255)
    @Column(name = "abreviatura")
    private String abreviatura;
    @OneToMany(mappedBy = "codigoTipoCombustivel")
    private Collection<Motor> motorCollection;

    public TipoCombustivel() {
    }

    public TipoCombustivel(Integer codigoTipoCombustivel) {
        this.codigoTipoCombustivel = codigoTipoCombustivel;
    }

    public Integer getCodigoTipoCombustivel() {
        return codigoTipoCombustivel;
    }

    public void setCodigoTipoCombustivel(Integer codigoTipoCombustivel) {
        this.codigoTipoCombustivel = codigoTipoCombustivel;
    }

    public String getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(String tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
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
        hash += (codigoTipoCombustivel != null ? codigoTipoCombustivel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoCombustivel)) {
            return false;
        }
        TipoCombustivel other = (TipoCombustivel) object;
        if ((this.codigoTipoCombustivel == null && other.codigoTipoCombustivel != null) || (this.codigoTipoCombustivel != null && !this.codigoTipoCombustivel.equals(other.codigoTipoCombustivel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return abreviatura;
    }
    
}
