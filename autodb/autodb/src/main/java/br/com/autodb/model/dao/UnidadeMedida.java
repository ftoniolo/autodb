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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ftoniolo
 */
@Entity
@Table(name = "UnidadeMedida")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UnidadeMedida.findAll", query = "SELECT u FROM UnidadeMedida u")})
public class UnidadeMedida implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoUnidadeMedida")
    private Integer codigoUnidadeMedida;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "unidadeMedida")
    private String unidadeMedida;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "abreviatura")
    private String abreviatura;
    @OneToMany(mappedBy = "codigoUnidadeMedida")
    private Collection<QualificadorAplicacao> qualificadorAplicacaoCollection;
    @OneToMany(mappedBy = "codigoUnidadeMedida")
    private Collection<QualificadorProduto> qualificadorProdutoCollection;

    public UnidadeMedida() {
    }

    public UnidadeMedida(Integer codigoUnidadeMedida) {
        this.codigoUnidadeMedida = codigoUnidadeMedida;
    }

    public UnidadeMedida(Integer codigoUnidadeMedida, String unidadeMedida, String abreviatura) {
        this.codigoUnidadeMedida = codigoUnidadeMedida;
        this.unidadeMedida = unidadeMedida;
        this.abreviatura = abreviatura;
    }

    public Integer getCodigoUnidadeMedida() {
        return codigoUnidadeMedida;
    }

    public void setCodigoUnidadeMedida(Integer codigoUnidadeMedida) {
        this.codigoUnidadeMedida = codigoUnidadeMedida;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    @XmlTransient
    public Collection<QualificadorAplicacao> getQualificadorAplicacaoCollection() {
        return qualificadorAplicacaoCollection;
    }

    public void setQualificadorAplicacaoCollection(Collection<QualificadorAplicacao> qualificadorAplicacaoCollection) {
        this.qualificadorAplicacaoCollection = qualificadorAplicacaoCollection;
    }

    @XmlTransient
    public Collection<QualificadorProduto> getQualificadorProdutoCollection() {
        return qualificadorProdutoCollection;
    }

    public void setQualificadorProdutoCollection(Collection<QualificadorProduto> qualificadorProdutoCollection) {
        this.qualificadorProdutoCollection = qualificadorProdutoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoUnidadeMedida != null ? codigoUnidadeMedida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UnidadeMedida)) {
            return false;
        }
        UnidadeMedida other = (UnidadeMedida) object;
        if ((this.codigoUnidadeMedida == null && other.codigoUnidadeMedida != null) || (this.codigoUnidadeMedida != null && !this.codigoUnidadeMedida.equals(other.codigoUnidadeMedida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.autodb.model.dao.UnidadeMedida[ codigoUnidadeMedida=" + codigoUnidadeMedida + " ]";
    }
    
}
