/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.dao;

import br.com.autodb.util.MyStringBuilder;
import br.com.autodb.util.StringBuilderItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
@Table(name = "Veiculo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Veiculo.findAll", query = "SELECT v FROM Veiculo v")})
public class Veiculo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoVeiculo")
    private Integer codigoVeiculo;
    @Size(max = 9)
    @Column(name = "periodoFabricacao")
    private String periodoFabricacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoVeiculo")
    private Collection<AnexoVeiculo> anexoVeiculoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoVeiculo")
    private Collection<Aplicacao> aplicacaoCollection;
    @JoinColumn(name = "codigoVeiculoBase", referencedColumnName = "codigoVeiculoBase")
    @ManyToOne(optional = false)
    private VeiculoBase codigoVeiculoBase;
    @JoinColumn(name = "codigoTipoTransmissao", referencedColumnName = "codigoTipoTransmissao")
    @ManyToOne
    private TipoTransmissao codigoTipoTransmissao;
    @JoinColumn(name = "codigoTipoTracao", referencedColumnName = "codigoTipoTracao")
    @ManyToOne
    private TipoTracao codigoTipoTracao;
    @JoinColumn(name = "codigoSubmodelo", referencedColumnName = "codigoSubmodelo")
    @ManyToOne
    private Submodelo codigoSubmodelo;
    @JoinColumn(name = "codigoSistemaDirecao", referencedColumnName = "codigoSistemaDirecao")
    @ManyToOne
    private SistemaDirecao codigoSistemaDirecao;
    @JoinColumn(name = "codigoPaisFabricacao", referencedColumnName = "codigoPais")
    @ManyToOne
    private Pais codigoPaisFabricacao;
    @JoinColumn(name = "codigoMotor", referencedColumnName = "codigoMotor")
    @ManyToOne
    private Motor codigoMotor;
    @JoinColumn(name = "codigoConfiguracaoFreio", referencedColumnName = "codigoConfiguracaoFreio")
    @ManyToOne
    private ConfiguracaoFreio codigoConfiguracaoFreio;

    public Veiculo() {
    }

    public Veiculo(Integer codigoVeiculo) {
        this.codigoVeiculo = codigoVeiculo;
    }

    public Integer getCodigoVeiculo() {
        return codigoVeiculo;
    }

    public void setCodigoVeiculo(Integer codigoVeiculo) {
        this.codigoVeiculo = codigoVeiculo;
    }

    public String getPeriodoFabricacao() {
        return periodoFabricacao;
    }

    public void setPeriodoFabricacao(String periodoFabricacao) {
        this.periodoFabricacao = periodoFabricacao;
    }

    @XmlTransient
    public Collection<AnexoVeiculo> getAnexoVeiculoCollection() {
        return anexoVeiculoCollection;
    }

    public void setAnexoVeiculoCollection(Collection<AnexoVeiculo> anexoVeiculoCollection) {
        this.anexoVeiculoCollection = anexoVeiculoCollection;
    }

    @XmlTransient
    public Collection<Aplicacao> getAplicacaoCollection() {
        return aplicacaoCollection;
    }

    public void setAplicacaoCollection(Collection<Aplicacao> aplicacaoCollection) {
        this.aplicacaoCollection = aplicacaoCollection;
    }

    public VeiculoBase getCodigoVeiculoBase() {
        return codigoVeiculoBase;
    }

    public void setCodigoVeiculoBase(VeiculoBase codigoVeiculoBase) {
        this.codigoVeiculoBase = codigoVeiculoBase;
    }

    public TipoTransmissao getCodigoTipoTransmissao() {
        return codigoTipoTransmissao;
    }

    public void setCodigoTipoTransmissao(TipoTransmissao codigoTipoTransmissao) {
        this.codigoTipoTransmissao = codigoTipoTransmissao;
    }

    public TipoTracao getCodigoTipoTracao() {
        return codigoTipoTracao;
    }

    public void setCodigoTipoTracao(TipoTracao codigoTipoTracao) {
        this.codigoTipoTracao = codigoTipoTracao;
    }

    public Submodelo getCodigoSubmodelo() {
        return codigoSubmodelo;
    }

    public void setCodigoSubmodelo(Submodelo codigoSubmodelo) {
        this.codigoSubmodelo = codigoSubmodelo;
    }

    public SistemaDirecao getCodigoSistemaDirecao() {
        return codigoSistemaDirecao;
    }

    public void setCodigoSistemaDirecao(SistemaDirecao codigoSistemaDirecao) {
        this.codigoSistemaDirecao = codigoSistemaDirecao;
    }

    public Pais getCodigoPaisFabricacao() {
        return codigoPaisFabricacao;
    }

    public void setCodigoPaisFabricacao(Pais codigoPaisFabricacao) {
        this.codigoPaisFabricacao = codigoPaisFabricacao;
    }

    public Motor getCodigoMotor() {
        return codigoMotor;
    }

    public void setCodigoMotor(Motor codigoMotor) {
        this.codigoMotor = codigoMotor;
    }

    public ConfiguracaoFreio getCodigoConfiguracaoFreio() {
        return codigoConfiguracaoFreio;
    }

    public void setCodigoConfiguracaoFreio(ConfiguracaoFreio codigoConfiguracaoFreio) {
        this.codigoConfiguracaoFreio = codigoConfiguracaoFreio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoVeiculo != null ? codigoVeiculo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Veiculo)) {
            return false;
        }
        Veiculo other = (Veiculo) object;
        if ((this.codigoVeiculo == null && other.codigoVeiculo != null) || (this.codigoVeiculo != null && !this.codigoVeiculo.equals(other.codigoVeiculo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        List<StringBuilderItem> itemList = new ArrayList();
        
        itemList.add(new StringBuilderItem(codigoSubmodelo, MyStringBuilder.FORMAT_ALONE));
        itemList.add(new StringBuilderItem(codigoMotor, MyStringBuilder.FORMAT_SPACE_BEFORE));
        itemList.add(new StringBuilderItem(codigoTipoTracao, MyStringBuilder.FORMAT_SPACE_BEFORE));
        itemList.add(new StringBuilderItem(codigoTipoTransmissao, MyStringBuilder.FORMAT_SPACE_BEFORE));
        itemList.add(new StringBuilderItem(codigoSistemaDirecao, MyStringBuilder.FORMAT_SPACE_BEFORE));
        itemList.add(new StringBuilderItem(codigoConfiguracaoFreio, MyStringBuilder.FORMAT_SPACE_BEFORE));
        itemList.add(new StringBuilderItem(codigoPaisFabricacao, MyStringBuilder.FORMAT_SPACE_BEFORE));
        itemList.add(new StringBuilderItem(periodoFabricacao, MyStringBuilder.FORMAT_SPACE_BEFORE));
        
        return MyStringBuilder.format(itemList);
    }
    
}
