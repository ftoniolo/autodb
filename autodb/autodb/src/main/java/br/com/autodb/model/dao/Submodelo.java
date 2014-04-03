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
@Table(name = "Submodelo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Submodelo.findAll", query = "SELECT s FROM Submodelo s")})
public class Submodelo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoSubmodelo")
    private Integer codigoSubmodelo;
    @Size(max = 50)
    @Column(name = "submodelo")
    private String submodelo;
    @OneToMany(mappedBy = "codigoSubmodelo")
    private Collection<Veiculo> veiculoCollection;

    public Submodelo() {
    }

    public Submodelo(Integer codigoSubmodelo) {
        this.codigoSubmodelo = codigoSubmodelo;
    }

    public Integer getCodigoSubmodelo() {
        return codigoSubmodelo;
    }

    public void setCodigoSubmodelo(Integer codigoSubmodelo) {
        this.codigoSubmodelo = codigoSubmodelo;
    }

    public String getSubmodelo() {
        return submodelo;
    }

    public void setSubmodelo(String submodelo) {
        this.submodelo = submodelo;
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
        hash += (codigoSubmodelo != null ? codigoSubmodelo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Submodelo)) {
            return false;
        }
        Submodelo other = (Submodelo) object;
        if ((this.codigoSubmodelo == null && other.codigoSubmodelo != null) || (this.codigoSubmodelo != null && !this.codigoSubmodelo.equals(other.codigoSubmodelo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return submodelo;
    }
    
}
