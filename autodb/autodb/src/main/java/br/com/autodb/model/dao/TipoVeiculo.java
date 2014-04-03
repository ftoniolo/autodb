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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "TipoVeiculo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoVeiculo.findAll", query = "SELECT t FROM TipoVeiculo t")})
public class TipoVeiculo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoTipoVeiculo")
    private Integer codigoTipoVeiculo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "tipoVeiculo")
    private String tipoVeiculo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoTipoVeiculo")
    private Collection<VeiculoBase> veiculoBaseCollection;
    @JoinColumn(name = "codigoLinhaVeiculo", referencedColumnName = "codigoLinhaVeiculo")
    @ManyToOne(optional = false)
    private LinhaVeiculo codigoLinhaVeiculo;

    public TipoVeiculo() {
    }

    public TipoVeiculo(Integer codigoTipoVeiculo) {
        this.codigoTipoVeiculo = codigoTipoVeiculo;
    }

    public TipoVeiculo(Integer codigoTipoVeiculo, String tipoVeiculo) {
        this.codigoTipoVeiculo = codigoTipoVeiculo;
        this.tipoVeiculo = tipoVeiculo;
    }

    public Integer getCodigoTipoVeiculo() {
        return codigoTipoVeiculo;
    }

    public void setCodigoTipoVeiculo(Integer codigoTipoVeiculo) {
        this.codigoTipoVeiculo = codigoTipoVeiculo;
    }

    public String getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setTipoVeiculo(String tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }

    @XmlTransient
    public Collection<VeiculoBase> getVeiculoBaseCollection() {
        return veiculoBaseCollection;
    }

    public void setVeiculoBaseCollection(Collection<VeiculoBase> veiculoBaseCollection) {
        this.veiculoBaseCollection = veiculoBaseCollection;
    }

    public LinhaVeiculo getCodigoLinhaVeiculo() {
        return codigoLinhaVeiculo;
    }

    public void setCodigoLinhaVeiculo(LinhaVeiculo codigoLinhaVeiculo) {
        this.codigoLinhaVeiculo = codigoLinhaVeiculo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoTipoVeiculo != null ? codigoTipoVeiculo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoVeiculo)) {
            return false;
        }
        TipoVeiculo other = (TipoVeiculo) object;
        if ((this.codigoTipoVeiculo == null && other.codigoTipoVeiculo != null) || (this.codigoTipoVeiculo != null && !this.codigoTipoVeiculo.equals(other.codigoTipoVeiculo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tipoVeiculo;
    }
    
}
