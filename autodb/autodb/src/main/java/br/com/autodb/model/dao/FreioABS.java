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
@Table(name = "FreioABS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FreioABS.findAll", query = "SELECT f FROM FreioABS f")})
public class FreioABS implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoFreioABS")
    private Integer codigoFreioABS;
    @Size(max = 50)
    @Column(name = "freioABS")
    private String freioABS;
    @OneToMany(mappedBy = "codigoFreioABS")
    private Collection<ConfiguracaoFreio> configuracaoFreioCollection;

    public FreioABS() {
    }

    public FreioABS(Integer codigoFreioABS) {
        this.codigoFreioABS = codigoFreioABS;
    }

    public Integer getCodigoFreioABS() {
        return codigoFreioABS;
    }

    public void setCodigoFreioABS(Integer codigoFreioABS) {
        this.codigoFreioABS = codigoFreioABS;
    }

    public String getFreioABS() {
        return freioABS;
    }

    public void setFreioABS(String freioABS) {
        this.freioABS = freioABS;
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
        hash += (codigoFreioABS != null ? codigoFreioABS.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FreioABS)) {
            return false;
        }
        FreioABS other = (FreioABS) object;
        if ((this.codigoFreioABS == null && other.codigoFreioABS != null) || (this.codigoFreioABS != null && !this.codigoFreioABS.equals(other.codigoFreioABS))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return freioABS;
    }
    
}
