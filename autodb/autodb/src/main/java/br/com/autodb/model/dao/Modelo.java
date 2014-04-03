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
@Table(name = "Modelo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modelo.findAll", query = "SELECT m FROM Modelo m")})
public class Modelo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoModelo")
    private Integer codigoModelo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "modelo")
    private String modelo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoModelo")
    private Collection<VeiculoBase> veiculoBaseCollection;

    public Modelo() {
    }

    public Modelo(Integer codigoModelo) {
        this.codigoModelo = codigoModelo;
    }

    public Modelo(Integer codigoModelo, String modelo) {
        this.codigoModelo = codigoModelo;
        this.modelo = modelo;
    }

    public Integer getCodigoModelo() {
        return codigoModelo;
    }

    public void setCodigoModelo(Integer codigoModelo) {
        this.codigoModelo = codigoModelo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
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
        hash += (codigoModelo != null ? codigoModelo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Modelo)) {
            return false;
        }
        Modelo other = (Modelo) object;
        if ((this.codigoModelo == null && other.codigoModelo != null) || (this.codigoModelo != null && !this.codigoModelo.equals(other.codigoModelo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return modelo;
    }
    
}
