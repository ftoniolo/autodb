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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ftoniolo
 */
@Entity
@Table(name = "ConfiguracaoFreio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConfiguracaoFreio.findAll", query = "SELECT c FROM ConfiguracaoFreio c")})
public class ConfiguracaoFreio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoConfiguracaoFreio")
    private Integer codigoConfiguracaoFreio;
    @JoinColumn(name = "codigoTipoFreioTraseiro", referencedColumnName = "codigoTipoFreio")
    @ManyToOne
    private TipoFreio codigoTipoFreioTraseiro;
    @JoinColumn(name = "codigoTipoFreioDianteiro", referencedColumnName = "codigoTipoFreio")
    @ManyToOne
    private TipoFreio codigoTipoFreioDianteiro;
    @JoinColumn(name = "codigoTipoAssistenciaFreio", referencedColumnName = "codigoTipoAssistenciaFreio")
    @ManyToOne
    private TipoAssistenciaFreio codigoTipoAssistenciaFreio;
    @JoinColumn(name = "codigoFreioABS", referencedColumnName = "codigoFreioABS")
    @ManyToOne
    private FreioABS codigoFreioABS;
    @OneToMany(mappedBy = "codigoConfiguracaoFreio")
    private Collection<Veiculo> veiculoCollection;

    public ConfiguracaoFreio() {
    }

    public ConfiguracaoFreio(Integer codigoConfiguracaoFreio) {
        this.codigoConfiguracaoFreio = codigoConfiguracaoFreio;
    }

    public Integer getCodigoConfiguracaoFreio() {
        return codigoConfiguracaoFreio;
    }

    public void setCodigoConfiguracaoFreio(Integer codigoConfiguracaoFreio) {
        this.codigoConfiguracaoFreio = codigoConfiguracaoFreio;
    }

    public TipoFreio getCodigoTipoFreioTraseiro() {
        return codigoTipoFreioTraseiro;
    }

    public void setCodigoTipoFreioTraseiro(TipoFreio codigoTipoFreioTraseiro) {
        this.codigoTipoFreioTraseiro = codigoTipoFreioTraseiro;
    }

    public TipoFreio getCodigoTipoFreioDianteiro() {
        return codigoTipoFreioDianteiro;
    }

    public void setCodigoTipoFreioDianteiro(TipoFreio codigoTipoFreioDianteiro) {
        this.codigoTipoFreioDianteiro = codigoTipoFreioDianteiro;
    }

    public TipoAssistenciaFreio getCodigoTipoAssistenciaFreio() {
        return codigoTipoAssistenciaFreio;
    }

    public void setCodigoTipoAssistenciaFreio(TipoAssistenciaFreio codigoTipoAssistenciaFreio) {
        this.codigoTipoAssistenciaFreio = codigoTipoAssistenciaFreio;
    }

    public FreioABS getCodigoFreioABS() {
        return codigoFreioABS;
    }

    public void setCodigoFreioABS(FreioABS codigoFreioABS) {
        this.codigoFreioABS = codigoFreioABS;
    }

    @XmlTransient
    public Collection<Veiculo> getVeiculoCollection() {
        return veiculoCollection;
    }

    public void setVeiculoCollection(Collection<Veiculo> veiculoCollection) {
        this.veiculoCollection = veiculoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoConfiguracaoFreio != null ? codigoConfiguracaoFreio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfiguracaoFreio)) {
            return false;
        }
        ConfiguracaoFreio other = (ConfiguracaoFreio) object;
        if ((this.codigoConfiguracaoFreio == null && other.codigoConfiguracaoFreio != null) || (this.codigoConfiguracaoFreio != null && !this.codigoConfiguracaoFreio.equals(other.codigoConfiguracaoFreio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.autodb.model.dao.ConfiguracaoFreio[ codigoConfiguracaoFreio=" + codigoConfiguracaoFreio + " ]";
    }
    
}
