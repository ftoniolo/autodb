/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ftoniolo
 */
@Entity
@Table(name = "Produto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Produto.findAll", query = "SELECT p FROM Produto p")})
public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoProduto")
    private Integer codigoProduto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "referencia")
    private String referencia;
    @Column(name = "dataLancamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataLancamento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dataCadastramento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastramento;
    @Size(max = 50)
    @Column(name = "eixo")
    private String eixo;
    @Size(max = 50)
    @Column(name = "lado")
    private String lado;
    @JoinColumn(name = "codigoStatusProduto", referencedColumnName = "codigoStatusProduto")
    @ManyToOne(optional = false)
    private StatusProduto codigoStatusProduto;
    @JoinColumn(name = "codigoLinhaProduto", referencedColumnName = "codigoLinhaProduto")
    @ManyToOne(optional = false)
    private LinhaProduto codigoLinhaProduto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoProduto")
    private Collection<Aplicacao> aplicacaoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoProduto")
    private Collection<QualificadorProduto> qualificadorProdutoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoProduto")
    private Collection<FabricanteProduto> fabricanteProdutoCollection;
    @OneToMany(mappedBy = "codigoProdutoFilho")
    private Collection<ComponenteProduto> componenteProdutoCollection;
    @OneToMany(mappedBy = "codigoProdutoPai")
    private Collection<ComponenteProduto> componenteProdutoCollection1;

    public Produto() {
    }

    public Produto(Integer codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public Produto(Integer codigoProduto, String referencia, Date dataCadastramento) {
        this.codigoProduto = codigoProduto;
        this.referencia = referencia;
        this.dataCadastramento = dataCadastramento;
    }

    public Integer getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(Integer codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Date getDataCadastramento() {
        return dataCadastramento;
    }

    public void setDataCadastramento(Date dataCadastramento) {
        this.dataCadastramento = dataCadastramento;
    }

    public String getEixo() {
        return eixo;
    }

    public void setEixo(String eixo) {
        this.eixo = eixo;
    }

    public String getLado() {
        return lado;
    }

    public void setLado(String lado) {
        this.lado = lado;
    }

    public StatusProduto getCodigoStatusProduto() {
        return codigoStatusProduto;
    }

    public void setCodigoStatusProduto(StatusProduto codigoStatusProduto) {
        this.codigoStatusProduto = codigoStatusProduto;
    }

    public LinhaProduto getCodigoLinhaProduto() {
        return codigoLinhaProduto;
    }

    public void setCodigoLinhaProduto(LinhaProduto codigoLinhaProduto) {
        this.codigoLinhaProduto = codigoLinhaProduto;
    }

    @XmlTransient
    public Collection<Aplicacao> getAplicacaoCollection() {
        return aplicacaoCollection;
    }

    public void setAplicacaoCollection(Collection<Aplicacao> aplicacaoCollection) {
        this.aplicacaoCollection = aplicacaoCollection;
    }

    @XmlTransient
    public Collection<QualificadorProduto> getQualificadorProdutoCollection() {
        return qualificadorProdutoCollection;
    }

    public void setQualificadorProdutoCollection(Collection<QualificadorProduto> qualificadorProdutoCollection) {
        this.qualificadorProdutoCollection = qualificadorProdutoCollection;
    }

    @XmlTransient
    public Collection<FabricanteProduto> getFabricanteProdutoCollection() {
        return fabricanteProdutoCollection;
    }

    public void setFabricanteProdutoCollection(Collection<FabricanteProduto> fabricanteProdutoCollection) {
        this.fabricanteProdutoCollection = fabricanteProdutoCollection;
    }

    @XmlTransient
    public Collection<ComponenteProduto> getComponenteProdutoCollection() {
        return componenteProdutoCollection;
    }

    public void setComponenteProdutoCollection(Collection<ComponenteProduto> componenteProdutoCollection) {
        this.componenteProdutoCollection = componenteProdutoCollection;
    }

    @XmlTransient
    public Collection<ComponenteProduto> getComponenteProdutoCollection1() {
        return componenteProdutoCollection1;
    }

    public void setComponenteProdutoCollection1(Collection<ComponenteProduto> componenteProdutoCollection1) {
        this.componenteProdutoCollection1 = componenteProdutoCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoProduto != null ? codigoProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produto)) {
            return false;
        }
        Produto other = (Produto) object;
        if ((this.codigoProduto == null && other.codigoProduto != null) || (this.codigoProduto != null && !this.codigoProduto.equals(other.codigoProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.autodb.model.dao.Produto[ codigoProduto=" + codigoProduto + " ]";
    }
    
}
