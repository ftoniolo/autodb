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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ftoniolo
 */
@Entity
@Table(name = "TipoCarrocaria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoCarrocaria.findAll", query = "SELECT t FROM TipoCarrocaria t")})
public class TipoCarrocaria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoTipoCarrocaria")
    private Integer codigoTipoCarrocaria;
    @Size(max = 50)
    @Column(name = "tipoCarrocaria")
    private String tipoCarrocaria;
    @Size(max = 255)
    @Column(name = "abreviatura")
    private String abreviatura;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoTipoCarrocaria")
    private Collection<VeiculoBase> veiculoBaseCollection;

    public TipoCarrocaria() {
    }

    public TipoCarrocaria(Integer codigoTipoCarrocaria) {
        this.codigoTipoCarrocaria = codigoTipoCarrocaria;
    }

    public Integer getCodigoTipoCarrocaria() {
        return codigoTipoCarrocaria;
    }

    public void setCodigoTipoCarrocaria(Integer codigoTipoCarrocaria) {
        this.codigoTipoCarrocaria = codigoTipoCarrocaria;
    }

    public String getTipoCarrocaria() {
        return tipoCarrocaria;
    }

    public void setTipoCarrocaria(String tipoCarrocaria) {
        this.tipoCarrocaria = tipoCarrocaria;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    @XmlTransient
    public Collection<VeiculoBase> getVeiculoBaseCollection() {
        return veiculoBaseCollection;
    }

    public void setVeiculoBaseCollection(Collection<VeiculoBase> veiculoBaseCollection) {
        this.veiculoBaseCollection = veiculoBaseCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoTipoCarrocaria != null ? codigoTipoCarrocaria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoCarrocaria)) {
            return false;
        }
        TipoCarrocaria other = (TipoCarrocaria) object;
        if ((this.codigoTipoCarrocaria == null && other.codigoTipoCarrocaria != null) || (this.codigoTipoCarrocaria != null && !this.codigoTipoCarrocaria.equals(other.codigoTipoCarrocaria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.autodb.model.dao.TipoCarrocaria[ codigoTipoCarrocaria=" + codigoTipoCarrocaria + " ]";
    }
    
}
