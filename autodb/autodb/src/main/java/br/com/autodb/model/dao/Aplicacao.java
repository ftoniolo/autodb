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
@Table(name = "Aplicacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aplicacao.findAll", query = "SELECT a FROM Aplicacao a")})
public class Aplicacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoAplicacao")
    private Integer codigoAplicacao;
    @Size(max = 50)
    @Column(name = "periodo")
    private String periodo;
    @Size(max = 50)
    @Column(name = "detalhe")
    private String detalhe;
    @Size(max = 50)
    @Column(name = "sistema")
    private String sistema;
    @JoinColumn(name = "codigoVeiculo", referencedColumnName = "codigoVeiculo")
    @ManyToOne(optional = false)
    private Veiculo codigoVeiculo;
    @JoinColumn(name = "codigoProduto", referencedColumnName = "codigoProduto")
    @ManyToOne(optional = false)
    private Produto codigoProduto;
    @JoinColumn(name = "codigoPosicao", referencedColumnName = "codigoPosicao")
    @ManyToOne
    private Posicao codigoPosicao;
    @JoinColumn(name = "codigoPais", referencedColumnName = "codigoPais")
    @ManyToOne
    private Pais codigoPais;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoAplicacao")
    private Collection<QualificadorAplicacao> qualificadorAplicacaoCollection;

    public Aplicacao() {
    }

    public Aplicacao(Integer codigoAplicacao) {
        this.codigoAplicacao = codigoAplicacao;
    }

    public Integer getCodigoAplicacao() {
        return codigoAplicacao;
    }

    public void setCodigoAplicacao(Integer codigoAplicacao) {
        this.codigoAplicacao = codigoAplicacao;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getDetalhe() {
        return detalhe;
    }

    public void setDetalhe(String detalhe) {
        this.detalhe = detalhe;
    }

    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public Veiculo getCodigoVeiculo() {
        return codigoVeiculo;
    }

    public void setCodigoVeiculo(Veiculo codigoVeiculo) {
        this.codigoVeiculo = codigoVeiculo;
    }

    public Produto getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(Produto codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public Posicao getCodigoPosicao() {
        return codigoPosicao;
    }

    public void setCodigoPosicao(Posicao codigoPosicao) {
        this.codigoPosicao = codigoPosicao;
    }

    public Pais getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(Pais codigoPais) {
        this.codigoPais = codigoPais;
    }

    @XmlTransient
    public Collection<QualificadorAplicacao> getQualificadorAplicacaoCollection() {
        return qualificadorAplicacaoCollection;
    }

    public void setQualificadorAplicacaoCollection(Collection<QualificadorAplicacao> qualificadorAplicacaoCollection) {
        this.qualificadorAplicacaoCollection = qualificadorAplicacaoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoAplicacao != null ? codigoAplicacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aplicacao)) {
            return false;
        }
        Aplicacao other = (Aplicacao) object;
        if ((this.codigoAplicacao == null && other.codigoAplicacao != null) || (this.codigoAplicacao != null && !this.codigoAplicacao.equals(other.codigoAplicacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.autodb.model.dao.Aplicacao[ codigoAplicacao=" + codigoAplicacao + " ]";
    }
    
}
