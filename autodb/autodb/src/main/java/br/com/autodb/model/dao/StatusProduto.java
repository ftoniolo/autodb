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
@Table(name = "StatusProduto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StatusProduto.findAll", query = "SELECT s FROM StatusProduto s")})
public class StatusProduto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoStatusProduto")
    private Integer codigoStatusProduto;
    @Size(max = 255)
    @Column(name = "status")
    private String status;
    @Size(max = 255)
    @Column(name = "observacao")
    private String observacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoStatusProduto")
    private Collection<Produto> produtoCollection;

    public StatusProduto() {
    }

    public StatusProduto(Integer codigoStatusProduto) {
        this.codigoStatusProduto = codigoStatusProduto;
    }

    public Integer getCodigoStatusProduto() {
        return codigoStatusProduto;
    }

    public void setCodigoStatusProduto(Integer codigoStatusProduto) {
        this.codigoStatusProduto = codigoStatusProduto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @XmlTransient
    public Collection<Produto> getProdutoCollection() {
        return produtoCollection;
    }

    public void setProdutoCollection(Collection<Produto> produtoCollection) {
        this.produtoCollection = produtoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoStatusProduto != null ? codigoStatusProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StatusProduto)) {
            return false;
        }
        StatusProduto other = (StatusProduto) object;
        if ((this.codigoStatusProduto == null && other.codigoStatusProduto != null) || (this.codigoStatusProduto != null && !this.codigoStatusProduto.equals(other.codigoStatusProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return status;
    }
    
}
