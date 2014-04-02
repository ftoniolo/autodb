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
@Table(name = "TipoAspiracao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoAspiracao.findAll", query = "SELECT t FROM TipoAspiracao t")})
public class TipoAspiracao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoTipoAspiracao")
    private Integer codigoTipoAspiracao;
    @Size(max = 50)
    @Column(name = "tipoAspiracao")
    private String tipoAspiracao;
    @OneToMany(mappedBy = "codigoTipoAspiracao")
    private Collection<Motor> motorCollection;

    public TipoAspiracao() {
    }

    public TipoAspiracao(Integer codigoTipoAspiracao) {
        this.codigoTipoAspiracao = codigoTipoAspiracao;
    }

    public Integer getCodigoTipoAspiracao() {
        return codigoTipoAspiracao;
    }

    public void setCodigoTipoAspiracao(Integer codigoTipoAspiracao) {
        this.codigoTipoAspiracao = codigoTipoAspiracao;
    }

    public String getTipoAspiracao() {
        return tipoAspiracao;
    }

    public void setTipoAspiracao(String tipoAspiracao) {
        this.tipoAspiracao = tipoAspiracao;
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
        hash += (codigoTipoAspiracao != null ? codigoTipoAspiracao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoAspiracao)) {
            return false;
        }
        TipoAspiracao other = (TipoAspiracao) object;
        if ((this.codigoTipoAspiracao == null && other.codigoTipoAspiracao != null) || (this.codigoTipoAspiracao != null && !this.codigoTipoAspiracao.equals(other.codigoTipoAspiracao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.autodb.model.dao.TipoAspiracao[ codigoTipoAspiracao=" + codigoTipoAspiracao + " ]";
    }
    
}
