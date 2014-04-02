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
@Table(name = "VeiculoBase")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VeiculoBase.findAll", query = "SELECT v FROM VeiculoBase v")})
public class VeiculoBase implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoVeiculoBase")
    private Integer codigoVeiculoBase;
    @Size(max = 50)
    @Column(name = "geracao")
    private String geracao;
    @Size(max = 50)
    @Column(name = "serie")
    private String serie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "periodoFabricacao")
    private String periodoFabricacao;
    @JoinColumn(name = "codigoTipoVeiculo", referencedColumnName = "codigoTipoVeiculo")
    @ManyToOne(optional = false)
    private TipoVeiculo codigoTipoVeiculo;
    @JoinColumn(name = "codigoTipoCarrocaria", referencedColumnName = "codigoTipoCarrocaria")
    @ManyToOne(optional = false)
    private TipoCarrocaria codigoTipoCarrocaria;
    @JoinColumn(name = "codigoModelo", referencedColumnName = "codigoModelo")
    @ManyToOne(optional = false)
    private Modelo codigoModelo;
    @JoinColumn(name = "codigoMarca", referencedColumnName = "codigoMarca")
    @ManyToOne(optional = false)
    private Marca codigoMarca;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoVeiculoBaseB")
    private Collection<VeiculoBaseRelacionado> veiculoBaseRelacionadoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoVeiculoBaseA")
    private Collection<VeiculoBaseRelacionado> veiculoBaseRelacionadoCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoVeiculoBase")
    private Collection<Veiculo> veiculoCollection;

    public VeiculoBase() {
    }

    public VeiculoBase(Integer codigoVeiculoBase) {
        this.codigoVeiculoBase = codigoVeiculoBase;
    }

    public VeiculoBase(Integer codigoVeiculoBase, String periodoFabricacao) {
        this.codigoVeiculoBase = codigoVeiculoBase;
        this.periodoFabricacao = periodoFabricacao;
    }

    public Integer getCodigoVeiculoBase() {
        return codigoVeiculoBase;
    }

    public void setCodigoVeiculoBase(Integer codigoVeiculoBase) {
        this.codigoVeiculoBase = codigoVeiculoBase;
    }

    public String getGeracao() {
        return geracao;
    }

    public void setGeracao(String geracao) {
        this.geracao = geracao;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getPeriodoFabricacao() {
        return periodoFabricacao;
    }

    public void setPeriodoFabricacao(String periodoFabricacao) {
        this.periodoFabricacao = periodoFabricacao;
    }

    public TipoVeiculo getCodigoTipoVeiculo() {
        return codigoTipoVeiculo;
    }

    public void setCodigoTipoVeiculo(TipoVeiculo codigoTipoVeiculo) {
        this.codigoTipoVeiculo = codigoTipoVeiculo;
    }

    public TipoCarrocaria getCodigoTipoCarrocaria() {
        return codigoTipoCarrocaria;
    }

    public void setCodigoTipoCarrocaria(TipoCarrocaria codigoTipoCarrocaria) {
        this.codigoTipoCarrocaria = codigoTipoCarrocaria;
    }

    public Modelo getCodigoModelo() {
        return codigoModelo;
    }

    public void setCodigoModelo(Modelo codigoModelo) {
        this.codigoModelo = codigoModelo;
    }

    public Marca getCodigoMarca() {
        return codigoMarca;
    }

    public void setCodigoMarca(Marca codigoMarca) {
        this.codigoMarca = codigoMarca;
    }

    @XmlTransient
    public Collection<VeiculoBaseRelacionado> getVeiculoBaseRelacionadoCollection() {
        return veiculoBaseRelacionadoCollection;
    }

    public void setVeiculoBaseRelacionadoCollection(Collection<VeiculoBaseRelacionado> veiculoBaseRelacionadoCollection) {
        this.veiculoBaseRelacionadoCollection = veiculoBaseRelacionadoCollection;
    }

    @XmlTransient
    public Collection<VeiculoBaseRelacionado> getVeiculoBaseRelacionadoCollection1() {
        return veiculoBaseRelacionadoCollection1;
    }

    public void setVeiculoBaseRelacionadoCollection1(Collection<VeiculoBaseRelacionado> veiculoBaseRelacionadoCollection1) {
        this.veiculoBaseRelacionadoCollection1 = veiculoBaseRelacionadoCollection1;
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
        hash += (codigoVeiculoBase != null ? codigoVeiculoBase.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VeiculoBase)) {
            return false;
        }
        VeiculoBase other = (VeiculoBase) object;
        if ((this.codigoVeiculoBase == null && other.codigoVeiculoBase != null) || (this.codigoVeiculoBase != null && !this.codigoVeiculoBase.equals(other.codigoVeiculoBase))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.autodb.model.dao.VeiculoBase[ codigoVeiculoBase=" + codigoVeiculoBase + " ]";
    }
    
}
