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
@Table(name = "Pais")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pais.findAll", query = "SELECT p FROM Pais p")})
public class Pais implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoPais")
    private Integer codigoPais;
    @Size(max = 50)
    @Column(name = "pais")
    private String pais;
    @Size(max = 50)
    @Column(name = "abreviatura")
    private String abreviatura;
    @OneToMany(mappedBy = "codigoPais")
    private Collection<Aplicacao> aplicacaoCollection;
    @OneToMany(mappedBy = "codigoPaisFabricacao")
    private Collection<Veiculo> veiculoCollection;
    @OneToMany(mappedBy = "codigoPais")
    private Collection<Fabricante> fabricanteCollection;

    public Pais() {
    }

    public Pais(Integer codigoPais) {
        this.codigoPais = codigoPais;
    }

    public Integer getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(Integer codigoPais) {
        this.codigoPais = codigoPais;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    @XmlTransient
    public Collection<Aplicacao> getAplicacaoCollection() {
        return aplicacaoCollection;
    }

    public void setAplicacaoCollection(Collection<Aplicacao> aplicacaoCollection) {
        this.aplicacaoCollection = aplicacaoCollection;
    }

    @XmlTransient
    public Collection<Veiculo> getVeiculoCollection() {
        return veiculoCollection;
    }

    public void setVeiculoCollection(Collection<Veiculo> veiculoCollection) {
        this.veiculoCollection = veiculoCollection;
    }

    @XmlTransient
    public Collection<Fabricante> getFabricanteCollection() {
        return fabricanteCollection;
    }

    public void setFabricanteCollection(Collection<Fabricante> fabricanteCollection) {
        this.fabricanteCollection = fabricanteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoPais != null ? codigoPais.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pais)) {
            return false;
        }
        Pais other = (Pais) object;
        if ((this.codigoPais == null && other.codigoPais != null) || (this.codigoPais != null && !this.codigoPais.equals(other.codigoPais))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.autodb.model.dao.Pais[ codigoPais=" + codigoPais + " ]";
    }
    
}
