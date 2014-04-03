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
@Table(name = "TipoAnexo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoAnexo.findAll", query = "SELECT t FROM TipoAnexo t")})
public class TipoAnexo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoTipoAnexo")
    private Integer codigoTipoAnexo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "tipoAnexo")
    private String tipoAnexo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoTipoAnexo")
    private Collection<AnexoVeiculo> anexoVeiculoCollection;

    public TipoAnexo() {
    }

    public TipoAnexo(Integer codigoTipoAnexo) {
        this.codigoTipoAnexo = codigoTipoAnexo;
    }

    public TipoAnexo(Integer codigoTipoAnexo, String tipoAnexo) {
        this.codigoTipoAnexo = codigoTipoAnexo;
        this.tipoAnexo = tipoAnexo;
    }

    public Integer getCodigoTipoAnexo() {
        return codigoTipoAnexo;
    }

    public void setCodigoTipoAnexo(Integer codigoTipoAnexo) {
        this.codigoTipoAnexo = codigoTipoAnexo;
    }

    public String getTipoAnexo() {
        return tipoAnexo;
    }

    public void setTipoAnexo(String tipoAnexo) {
        this.tipoAnexo = tipoAnexo;
    }

    @XmlTransient
    public Collection<AnexoVeiculo> getAnexoVeiculoCollection() {
        return anexoVeiculoCollection;
    }

    public void setAnexoVeiculoCollection(Collection<AnexoVeiculo> anexoVeiculoCollection) {
        this.anexoVeiculoCollection = anexoVeiculoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoTipoAnexo != null ? codigoTipoAnexo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoAnexo)) {
            return false;
        }
        TipoAnexo other = (TipoAnexo) object;
        if ((this.codigoTipoAnexo == null && other.codigoTipoAnexo != null) || (this.codigoTipoAnexo != null && !this.codigoTipoAnexo.equals(other.codigoTipoAnexo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tipoAnexo;
    }
    
}
