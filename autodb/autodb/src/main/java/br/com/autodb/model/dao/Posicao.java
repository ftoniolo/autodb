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
@Table(name = "Posicao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Posicao.findAll", query = "SELECT p FROM Posicao p")})
public class Posicao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoPosicao")
    private Integer codigoPosicao;
    @Size(max = 50)
    @Column(name = "posicao")
    private String posicao;
    @Size(max = 255)
    @Column(name = "abreviatura")
    private String abreviatura;
    @OneToMany(mappedBy = "codigoPosicao")
    private Collection<Aplicacao> aplicacaoCollection;

    public Posicao() {
    }

    public Posicao(Integer codigoPosicao) {
        this.codigoPosicao = codigoPosicao;
    }

    public Integer getCodigoPosicao() {
        return codigoPosicao;
    }

    public void setCodigoPosicao(Integer codigoPosicao) {
        this.codigoPosicao = codigoPosicao;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoPosicao != null ? codigoPosicao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Posicao)) {
            return false;
        }
        Posicao other = (Posicao) object;
        if ((this.codigoPosicao == null && other.codigoPosicao != null) || (this.codigoPosicao != null && !this.codigoPosicao.equals(other.codigoPosicao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return posicao;
    }
    
}
