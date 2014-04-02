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
@Table(name = "TipoMotor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoMotor.findAll", query = "SELECT t FROM TipoMotor t")})
public class TipoMotor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoTipoMotor")
    private Integer codigoTipoMotor;
    @Size(max = 50)
    @Column(name = "tipoMotor")
    private String tipoMotor;
    @OneToMany(mappedBy = "codigoTipoMotor")
    private Collection<Motor> motorCollection;

    public TipoMotor() {
    }

    public TipoMotor(Integer codigoTipoMotor) {
        this.codigoTipoMotor = codigoTipoMotor;
    }

    public Integer getCodigoTipoMotor() {
        return codigoTipoMotor;
    }

    public void setCodigoTipoMotor(Integer codigoTipoMotor) {
        this.codigoTipoMotor = codigoTipoMotor;
    }

    public String getTipoMotor() {
        return tipoMotor;
    }

    public void setTipoMotor(String tipoMotor) {
        this.tipoMotor = tipoMotor;
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
        hash += (codigoTipoMotor != null ? codigoTipoMotor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoMotor)) {
            return false;
        }
        TipoMotor other = (TipoMotor) object;
        if ((this.codigoTipoMotor == null && other.codigoTipoMotor != null) || (this.codigoTipoMotor != null && !this.codigoTipoMotor.equals(other.codigoTipoMotor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.autodb.model.dao.TipoMotor[ codigoTipoMotor=" + codigoTipoMotor + " ]";
    }
    
}
