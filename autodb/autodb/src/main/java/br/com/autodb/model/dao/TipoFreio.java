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
@Table(name = "TipoFreio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoFreio.findAll", query = "SELECT t FROM TipoFreio t")})
public class TipoFreio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoTipoFreio")
    private Integer codigoTipoFreio;
    @Size(max = 50)
    @Column(name = "tipoFreio")
    private String tipoFreio;
    @Size(max = 255)
    @Column(name = "abreviatura")
    private String abreviatura;
    @OneToMany(mappedBy = "codigoTipoFreioTraseiro")
    private Collection<ConfiguracaoFreio> configuracaoFreioCollection;
    @OneToMany(mappedBy = "codigoTipoFreioDianteiro")
    private Collection<ConfiguracaoFreio> configuracaoFreioCollection1;

    public TipoFreio() {
    }

    public TipoFreio(Integer codigoTipoFreio) {
        this.codigoTipoFreio = codigoTipoFreio;
    }

    public Integer getCodigoTipoFreio() {
        return codigoTipoFreio;
    }

    public void setCodigoTipoFreio(Integer codigoTipoFreio) {
        this.codigoTipoFreio = codigoTipoFreio;
    }

    public String getTipoFreio() {
        return tipoFreio;
    }

    public void setTipoFreio(String tipoFreio) {
        this.tipoFreio = tipoFreio;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    @XmlTransient
    public Collection<ConfiguracaoFreio> getConfiguracaoFreioCollection() {
        return configuracaoFreioCollection;
    }

    public void setConfiguracaoFreioCollection(Collection<ConfiguracaoFreio> configuracaoFreioCollection) {
        this.configuracaoFreioCollection = configuracaoFreioCollection;
    }

    @XmlTransient
    public Collection<ConfiguracaoFreio> getConfiguracaoFreioCollection1() {
        return configuracaoFreioCollection1;
    }

    public void setConfiguracaoFreioCollection1(Collection<ConfiguracaoFreio> configuracaoFreioCollection1) {
        this.configuracaoFreioCollection1 = configuracaoFreioCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoTipoFreio != null ? codigoTipoFreio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoFreio)) {
            return false;
        }
        TipoFreio other = (TipoFreio) object;
        if ((this.codigoTipoFreio == null && other.codigoTipoFreio != null) || (this.codigoTipoFreio != null && !this.codigoTipoFreio.equals(other.codigoTipoFreio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tipoFreio;
    }
    
}
