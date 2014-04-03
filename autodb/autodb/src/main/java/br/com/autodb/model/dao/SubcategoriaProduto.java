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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "SubcategoriaProduto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubcategoriaProduto.findAll", query = "SELECT s FROM SubcategoriaProduto s")})
public class SubcategoriaProduto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoSubcategoriaProduto")
    private Integer codigoSubcategoriaProduto;
    @Size(max = 50)
    @Column(name = "subcategoria")
    private String subcategoria;
    @JoinColumn(name = "codigoCategoriaProduto", referencedColumnName = "codigoCategoriaProduto")
    @ManyToOne
    private CategoriaProduto codigoCategoriaProduto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoSubcategoriaProduto")
    private Collection<LinhaProduto> linhaProdutoCollection;

    public SubcategoriaProduto() {
    }

    public SubcategoriaProduto(Integer codigoSubcategoriaProduto) {
        this.codigoSubcategoriaProduto = codigoSubcategoriaProduto;
    }

    public Integer getCodigoSubcategoriaProduto() {
        return codigoSubcategoriaProduto;
    }

    public void setCodigoSubcategoriaProduto(Integer codigoSubcategoriaProduto) {
        this.codigoSubcategoriaProduto = codigoSubcategoriaProduto;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public CategoriaProduto getCodigoCategoriaProduto() {
        return codigoCategoriaProduto;
    }

    public void setCodigoCategoriaProduto(CategoriaProduto codigoCategoriaProduto) {
        this.codigoCategoriaProduto = codigoCategoriaProduto;
    }

    @XmlTransient
    public Collection<LinhaProduto> getLinhaProdutoCollection() {
        return linhaProdutoCollection;
    }

    public void setLinhaProdutoCollection(Collection<LinhaProduto> linhaProdutoCollection) {
        this.linhaProdutoCollection = linhaProdutoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoSubcategoriaProduto != null ? codigoSubcategoriaProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubcategoriaProduto)) {
            return false;
        }
        SubcategoriaProduto other = (SubcategoriaProduto) object;
        if ((this.codigoSubcategoriaProduto == null && other.codigoSubcategoriaProduto != null) || (this.codigoSubcategoriaProduto != null && !this.codigoSubcategoriaProduto.equals(other.codigoSubcategoriaProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return subcategoria;
    }
    
}
