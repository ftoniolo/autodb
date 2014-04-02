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
@Table(name = "Qualificador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Qualificador.findAll", query = "SELECT q FROM Qualificador q")})
public class Qualificador implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoQualificador")
    private Integer codigoQualificador;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "qualificador")
    private String qualificador;
    @Size(max = 255)
    @Column(name = "exemplo")
    private String exemplo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoQualificador")
    private Collection<QualificadorAplicacao> qualificadorAplicacaoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoQualificador")
    private Collection<QualificadorProduto> qualificadorProdutoCollection;

    public Qualificador() {
    }

    public Qualificador(Integer codigoQualificador) {
        this.codigoQualificador = codigoQualificador;
    }

    public Qualificador(Integer codigoQualificador, String qualificador) {
        this.codigoQualificador = codigoQualificador;
        this.qualificador = qualificador;
    }

    public Integer getCodigoQualificador() {
        return codigoQualificador;
    }

    public void setCodigoQualificador(Integer codigoQualificador) {
        this.codigoQualificador = codigoQualificador;
    }

    public String getQualificador() {
        return qualificador;
    }

    public void setQualificador(String qualificador) {
        this.qualificador = qualificador;
    }

    public String getExemplo() {
        return exemplo;
    }

    public void setExemplo(String exemplo) {
        this.exemplo = exemplo;
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
        hash += (codigoQualificador != null ? codigoQualificador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Qualificador)) {
            return false;
        }
        Qualificador other = (Qualificador) object;
        if ((this.codigoQualificador == null && other.codigoQualificador != null) || (this.codigoQualificador != null && !this.codigoQualificador.equals(other.codigoQualificador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.autodb.model.dao.Qualificador[ codigoQualificador=" + codigoQualificador + " ]";
    }
    
}
