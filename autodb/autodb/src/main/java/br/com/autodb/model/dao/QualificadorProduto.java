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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ftoniolo
 */
@Entity
@Table(name = "QualificadorProduto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QualificadorProduto.findAll", query = "SELECT q FROM QualificadorProduto q")})
public class QualificadorProduto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoQualificadorProduto")
    private Integer codigoQualificadorProduto;
    @Size(max = 50)
    @Column(name = "valor")
    private String valor;
    @JoinColumn(name = "codigoUnidadeMedida", referencedColumnName = "codigoUnidadeMedida")
    @ManyToOne
    private UnidadeMedida codigoUnidadeMedida;
    @JoinColumn(name = "codigoQualificador", referencedColumnName = "codigoQualificador")
    @ManyToOne(optional = false)
    private Qualificador codigoQualificador;
    @JoinColumn(name = "codigoProduto", referencedColumnName = "codigoProduto")
    @ManyToOne(optional = false)
    private Produto codigoProduto;

    public QualificadorProduto() {
    }

    public QualificadorProduto(Integer codigoQualificadorProduto) {
        this.codigoQualificadorProduto = codigoQualificadorProduto;
    }

    public Integer getCodigoQualificadorProduto() {
        return codigoQualificadorProduto;
    }

    public void setCodigoQualificadorProduto(Integer codigoQualificadorProduto) {
        this.codigoQualificadorProduto = codigoQualificadorProduto;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public UnidadeMedida getCodigoUnidadeMedida() {
        return codigoUnidadeMedida;
    }

    public void setCodigoUnidadeMedida(UnidadeMedida codigoUnidadeMedida) {
        this.codigoUnidadeMedida = codigoUnidadeMedida;
    }

    public Qualificador getCodigoQualificador() {
        return codigoQualificador;
    }

    public void setCodigoQualificador(Qualificador codigoQualificador) {
        this.codigoQualificador = codigoQualificador;
    }

    public Produto getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(Produto codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoQualificadorProduto != null ? codigoQualificadorProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QualificadorProduto)) {
            return false;
        }
        QualificadorProduto other = (QualificadorProduto) object;
        if ((this.codigoQualificadorProduto == null && other.codigoQualificadorProduto != null) || (this.codigoQualificadorProduto != null && !this.codigoQualificadorProduto.equals(other.codigoQualificadorProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.autodb.model.dao.QualificadorProduto[ codigoQualificadorProduto=" + codigoQualificadorProduto + " ]";
    }
    
}
