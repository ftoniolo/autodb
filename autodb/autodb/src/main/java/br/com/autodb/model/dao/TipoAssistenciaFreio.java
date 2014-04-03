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
@Table(name = "TipoAssistenciaFreio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoAssistenciaFreio.findAll", query = "SELECT t FROM TipoAssistenciaFreio t")})
public class TipoAssistenciaFreio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoTipoAssistenciaFreio")
    private Integer codigoTipoAssistenciaFreio;
    @Size(max = 50)
    @Column(name = "tipoAssistenciaFreio")
    private String tipoAssistenciaFreio;
    @OneToMany(mappedBy = "codigoTipoAssistenciaFreio")
    private Collection<ConfiguracaoFreio> configuracaoFreioCollection;

    public TipoAssistenciaFreio() {
    }

    public TipoAssistenciaFreio(Integer codigoTipoAssistenciaFreio) {
        this.codigoTipoAssistenciaFreio = codigoTipoAssistenciaFreio;
    }

    public Integer getCodigoTipoAssistenciaFreio() {
        return codigoTipoAssistenciaFreio;
    }

    public void setCodigoTipoAssistenciaFreio(Integer codigoTipoAssistenciaFreio) {
        this.codigoTipoAssistenciaFreio = codigoTipoAssistenciaFreio;
    }

    public String getTipoAssistenciaFreio() {
        return tipoAssistenciaFreio;
    }

    public void setTipoAssistenciaFreio(String tipoAssistenciaFreio) {
        this.tipoAssistenciaFreio = tipoAssistenciaFreio;
    }

    @XmlTransient
    public Collection<ConfiguracaoFreio> getConfiguracaoFreioCollection() {
        return configuracaoFreioCollection;
    }

    public void setConfiguracaoFreioCollection(Collection<ConfiguracaoFreio> configuracaoFreioCollection) {
        this.configuracaoFreioCollection = configuracaoFreioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoTipoAssistenciaFreio != null ? codigoTipoAssistenciaFreio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoAssistenciaFreio)) {
            return false;
        }
        TipoAssistenciaFreio other = (TipoAssistenciaFreio) object;
        if ((this.codigoTipoAssistenciaFreio == null && other.codigoTipoAssistenciaFreio != null) || (this.codigoTipoAssistenciaFreio != null && !this.codigoTipoAssistenciaFreio.equals(other.codigoTipoAssistenciaFreio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tipoAssistenciaFreio;
    }
    
}
