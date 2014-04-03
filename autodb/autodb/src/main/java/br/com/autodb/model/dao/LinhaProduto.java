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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ftoniolo
 */
@Entity
@Table(name = "LinhaProduto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LinhaProduto.findAll", query = "SELECT l FROM LinhaProduto l")})
public class LinhaProduto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoLinhaProduto")
    private Integer codigoLinhaProduto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "linhaProduto")
    private String linhaProduto;
    @Column(name = "ordem")
    private Integer ordem;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoLinhaProduto")
    private Collection<Produto> produtoCollection;
    @JoinColumn(name = "codigoSubcategoriaProduto", referencedColumnName = "codigoSubcategoriaProduto")
    @ManyToOne(optional = false)
    private SubcategoriaProduto codigoSubcategoriaProduto;

    public LinhaProduto() {
    }

    public LinhaProduto(Integer codigoLinhaProduto) {
        this.codigoLinhaProduto = codigoLinhaProduto;
    }

    public LinhaProduto(Integer codigoLinhaProduto, String linhaProduto) {
        this.codigoLinhaProduto = codigoLinhaProduto;
        this.linhaProduto = linhaProduto;
    }

    public Integer getCodigoLinhaProduto() {
        return codigoLinhaProduto;
    }

    public void setCodigoLinhaProduto(Integer codigoLinhaProduto) {
        this.codigoLinhaProduto = codigoLinhaProduto;
    }

    public String getLinhaProduto() {
        return linhaProduto;
    }

    public void setLinhaProduto(String linhaProduto) {
        this.linhaProduto = linhaProduto;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    @XmlTransient
    public Collection<Produto> getProdutoCollection() {
        return produtoCollection;
    }

    public void setProdutoCollection(Collection<Produto> produtoCollection) {
        this.produtoCollection = produtoCollection;
    }

    public SubcategoriaProduto getCodigoSubcategoriaProduto() {
        return codigoSubcategoriaProduto;
    }

    public void setCodigoSubcategoriaProduto(SubcategoriaProduto codigoSubcategoriaProduto) {
        this.codigoSubcategoriaProduto = codigoSubcategoriaProduto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoLinhaProduto != null ? codigoLinhaProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LinhaProduto)) {
            return false;
        }
        LinhaProduto other = (LinhaProduto) object;
        if ((this.codigoLinhaProduto == null && other.codigoLinhaProduto != null) || (this.codigoLinhaProduto != null && !this.codigoLinhaProduto.equals(other.codigoLinhaProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return linhaProduto;
    }
    
}
