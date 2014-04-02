/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.dao;

import java.io.Serializable;
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
 * @author ftoniolo
 */
@Entity
@Table(name = "LinhaVeiculo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LinhaVeiculo.findAll", query = "SELECT l FROM LinhaVeiculo l")})
public class LinhaVeiculo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoLinhaVeiculo")
    private Integer codigoLinhaVeiculo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "linhaVeiculo")
    private String linhaVeiculo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoLinhaVeiculo")
    private Collection<TipoVeiculo> tipoVeiculoCollection;

    public LinhaVeiculo() {
    }

    public LinhaVeiculo(Integer codigoLinhaVeiculo) {
        this.codigoLinhaVeiculo = codigoLinhaVeiculo;
    }

    public LinhaVeiculo(Integer codigoLinhaVeiculo, String linhaVeiculo) {
        this.codigoLinhaVeiculo = codigoLinhaVeiculo;
        this.linhaVeiculo = linhaVeiculo;
    }

    public Integer getCodigoLinhaVeiculo() {
        return codigoLinhaVeiculo;
    }

    public void setCodigoLinhaVeiculo(Integer codigoLinhaVeiculo) {
        this.codigoLinhaVeiculo = codigoLinhaVeiculo;
    }

    public String getLinhaVeiculo() {
        return linhaVeiculo;
    }

    public void setLinhaVeiculo(String linhaVeiculo) {
        this.linhaVeiculo = linhaVeiculo;
    }

    @XmlTransient
    public Collection<TipoVeiculo> getTipoVeiculoCollection() {
        return tipoVeiculoCollection;
    }

    public void setTipoVeiculoCollection(Collection<TipoVeiculo> tipoVeiculoCollection) {
        this.tipoVeiculoCollection = tipoVeiculoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoLinhaVeiculo != null ? codigoLinhaVeiculo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LinhaVeiculo)) {
            return false;
        }
        LinhaVeiculo other = (LinhaVeiculo) object;
        if ((this.codigoLinhaVeiculo == null && other.codigoLinhaVeiculo != null) || (this.codigoLinhaVeiculo != null && !this.codigoLinhaVeiculo.equals(other.codigoLinhaVeiculo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.autodb.model.dao.LinhaVeiculo[ codigoLinhaVeiculo=" + codigoLinhaVeiculo + " ]";
    }
    
}
