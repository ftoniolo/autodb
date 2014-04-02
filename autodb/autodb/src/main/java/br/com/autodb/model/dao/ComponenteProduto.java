/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.dao;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ftoniolo
 */
@Entity
@Table(name = "ComponenteProduto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComponenteProduto.findAll", query = "SELECT c FROM ComponenteProduto c")})
public class ComponenteProduto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoProdutoProduto")
    private Integer codigoProdutoProduto;
    @Column(name = "ordem")
    private Integer ordem;
    @JoinColumn(name = "codigoProdutoFilho", referencedColumnName = "codigoProduto")
    @ManyToOne
    private Produto codigoProdutoFilho;
    @JoinColumn(name = "codigoProdutoPai", referencedColumnName = "codigoProduto")
    @ManyToOne
    private Produto codigoProdutoPai;

    public ComponenteProduto() {
    }

    public ComponenteProduto(Integer codigoProdutoProduto) {
        this.codigoProdutoProduto = codigoProdutoProduto;
    }

    public Integer getCodigoProdutoProduto() {
        return codigoProdutoProduto;
    }

    public void setCodigoProdutoProduto(Integer codigoProdutoProduto) {
        this.codigoProdutoProduto = codigoProdutoProduto;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Produto getCodigoProdutoFilho() {
        return codigoProdutoFilho;
    }

    public void setCodigoProdutoFilho(Produto codigoProdutoFilho) {
        this.codigoProdutoFilho = codigoProdutoFilho;
    }

    public Produto getCodigoProdutoPai() {
        return codigoProdutoPai;
    }

    public void setCodigoProdutoPai(Produto codigoProdutoPai) {
        this.codigoProdutoPai = codigoProdutoPai;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoProdutoProduto != null ? codigoProdutoProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComponenteProduto)) {
            return false;
        }
        ComponenteProduto other = (ComponenteProduto) object;
        if ((this.codigoProdutoProduto == null && other.codigoProdutoProduto != null) || (this.codigoProdutoProduto != null && !this.codigoProdutoProduto.equals(other.codigoProdutoProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.autodb.model.dao.ComponenteProduto[ codigoProdutoProduto=" + codigoProdutoProduto + " ]";
    }
    
}
