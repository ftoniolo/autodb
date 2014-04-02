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
@Table(name = "SistemaDirecao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SistemaDirecao.findAll", query = "SELECT s FROM SistemaDirecao s")})
public class SistemaDirecao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoSistemaDirecao")
    private Integer codigoSistemaDirecao;
    @Size(max = 50)
    @Column(name = "sistemaDirecao")
    private String sistemaDirecao;
    @OneToMany(mappedBy = "codigoSistemaDirecao")
    private Collection<Veiculo> veiculoCollection;

    public SistemaDirecao() {
    }

    public SistemaDirecao(Integer codigoSistemaDirecao) {
        this.codigoSistemaDirecao = codigoSistemaDirecao;
    }

    public Integer getCodigoSistemaDirecao() {
        return codigoSistemaDirecao;
    }

    public void setCodigoSistemaDirecao(Integer codigoSistemaDirecao) {
        this.codigoSistemaDirecao = codigoSistemaDirecao;
    }

    public String getSistemaDirecao() {
        return sistemaDirecao;
    }

    public void setSistemaDirecao(String sistemaDirecao) {
        this.sistemaDirecao = sistemaDirecao;
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
        hash += (codigoSistemaDirecao != null ? codigoSistemaDirecao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SistemaDirecao)) {
            return false;
        }
        SistemaDirecao other = (SistemaDirecao) object;
        if ((this.codigoSistemaDirecao == null && other.codigoSistemaDirecao != null) || (this.codigoSistemaDirecao != null && !this.codigoSistemaDirecao.equals(other.codigoSistemaDirecao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.autodb.model.dao.SistemaDirecao[ codigoSistemaDirecao=" + codigoSistemaDirecao + " ]";
    }
    
}
