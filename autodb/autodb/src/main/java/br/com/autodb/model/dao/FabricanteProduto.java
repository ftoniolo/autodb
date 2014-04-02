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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ftoniolo
 */
@Entity
@Table(name = "FabricanteProduto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FabricanteProduto.findAll", query = "SELECT f FROM FabricanteProduto f")})
public class FabricanteProduto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoFabricanteProduto")
    private Integer codigoFabricanteProduto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "numero")
    private String numero;
    @Size(max = 255)
    @Column(name = "observacao")
    private String observacao;
    @JoinColumn(name = "codigoProduto", referencedColumnName = "codigoProduto")
    @ManyToOne(optional = false)
    private Produto codigoProduto;
    @JoinColumn(name = "codigoFabricante", referencedColumnName = "codigoFabricante")
    @ManyToOne(optional = false)
    private Fabricante codigoFabricante;

    public FabricanteProduto() {
    }

    public FabricanteProduto(Integer codigoFabricanteProduto) {
        this.codigoFabricanteProduto = codigoFabricanteProduto;
    }

    public FabricanteProduto(Integer codigoFabricanteProduto, String numero) {
        this.codigoFabricanteProduto = codigoFabricanteProduto;
        this.numero = numero;
    }

    public Integer getCodigoFabricanteProduto() {
        return codigoFabricanteProduto;
    }

    public void setCodigoFabricanteProduto(Integer codigoFabricanteProduto) {
        this.codigoFabricanteProduto = codigoFabricanteProduto;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Produto getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(Produto codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public Fabricante getCodigoFabricante() {
        return codigoFabricante;
    }

    public void setCodigoFabricante(Fabricante codigoFabricante) {
        this.codigoFabricante = codigoFabricante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoFabricanteProduto != null ? codigoFabricanteProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FabricanteProduto)) {
            return false;
        }
        FabricanteProduto other = (FabricanteProduto) object;
        if ((this.codigoFabricanteProduto == null && other.codigoFabricanteProduto != null) || (this.codigoFabricanteProduto != null && !this.codigoFabricanteProduto.equals(other.codigoFabricanteProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.autodb.model.dao.FabricanteProduto[ codigoFabricanteProduto=" + codigoFabricanteProduto + " ]";
    }
    
}
