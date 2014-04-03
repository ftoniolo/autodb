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
@Table(name = "Marca")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Marca.findAll", query = "SELECT m FROM Marca m")})
public class Marca implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoMarca")
    private Integer codigoMarca;
    @Size(max = 50)
    @Column(name = "marca")
    private String marca;
    @Size(max = 50)
    @Column(name = "abreviatura")
    private String abreviatura;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoMarca")
    private Collection<VeiculoBase> veiculoBaseCollection;

    public Marca() {
    }

    public Marca(Integer codigoMarca) {
        this.codigoMarca = codigoMarca;
    }

    public Integer getCodigoMarca() {
        return codigoMarca;
    }

    public void setCodigoMarca(Integer codigoMarca) {
        this.codigoMarca = codigoMarca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
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
        hash += (codigoMarca != null ? codigoMarca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Marca)) {
            return false;
        }
        Marca other = (Marca) object;
        if ((this.codigoMarca == null && other.codigoMarca != null) || (this.codigoMarca != null && !this.codigoMarca.equals(other.codigoMarca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return marca;
    }
}
