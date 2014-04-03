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
@Table(name = "CategoriaProduto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CategoriaProduto.findAll", query = "SELECT c FROM CategoriaProduto c")})
public class CategoriaProduto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoCategoriaProduto")
    private Integer codigoCategoriaProduto;
    @Size(max = 80)
    @Column(name = "categoria")
    private String categoria;
    @OneToMany(mappedBy = "codigoCategoriaProduto")
    private Collection<SubcategoriaProduto> subcategoriaProdutoCollection;

    public CategoriaProduto() {
    }

    public CategoriaProduto(Integer codigoCategoriaProduto) {
        this.codigoCategoriaProduto = codigoCategoriaProduto;
    }

    public Integer getCodigoCategoriaProduto() {
        return codigoCategoriaProduto;
    }

    public void setCodigoCategoriaProduto(Integer codigoCategoriaProduto) {
        this.codigoCategoriaProduto = codigoCategoriaProduto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @XmlTransient
    public Collection<SubcategoriaProduto> getSubcategoriaProdutoCollection() {
        return subcategoriaProdutoCollection;
    }

    public void setSubcategoriaProdutoCollection(Collection<SubcategoriaProduto> subcategoriaProdutoCollection) {
        this.subcategoriaProdutoCollection = subcategoriaProdutoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoCategoriaProduto != null ? codigoCategoriaProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriaProduto)) {
            return false;
        }
        CategoriaProduto other = (CategoriaProduto) object;
        if ((this.codigoCategoriaProduto == null && other.codigoCategoriaProduto != null) || (this.codigoCategoriaProduto != null && !this.codigoCategoriaProduto.equals(other.codigoCategoriaProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return categoria;
    }
    
}
