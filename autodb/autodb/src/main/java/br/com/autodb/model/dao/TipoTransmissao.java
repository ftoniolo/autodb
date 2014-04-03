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
@Table(name = "TipoTransmissao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoTransmissao.findAll", query = "SELECT t FROM TipoTransmissao t")})
public class TipoTransmissao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoTipoTransmissao")
    private Integer codigoTipoTransmissao;
    @Size(max = 50)
    @Column(name = "tipoTransmissao")
    private String tipoTransmissao;
    @Size(max = 255)
    @Column(name = "nome")
    private String nome;
    @Column(name = "velocidades")
    private Integer velocidades;
    @OneToMany(mappedBy = "codigoTipoTransmissao")
    private Collection<Veiculo> veiculoCollection;

    public TipoTransmissao() {
    }

    public TipoTransmissao(Integer codigoTipoTransmissao) {
        this.codigoTipoTransmissao = codigoTipoTransmissao;
    }

    public Integer getCodigoTipoTransmissao() {
        return codigoTipoTransmissao;
    }

    public void setCodigoTipoTransmissao(Integer codigoTipoTransmissao) {
        this.codigoTipoTransmissao = codigoTipoTransmissao;
    }

    public String getTipoTransmissao() {
        return tipoTransmissao;
    }

    public void setTipoTransmissao(String tipoTransmissao) {
        this.tipoTransmissao = tipoTransmissao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getVelocidades() {
        return velocidades;
    }

    public void setVelocidades(Integer velocidades) {
        this.velocidades = velocidades;
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
        hash += (codigoTipoTransmissao != null ? codigoTipoTransmissao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoTransmissao)) {
            return false;
        }
        TipoTransmissao other = (TipoTransmissao) object;
        if ((this.codigoTipoTransmissao == null && other.codigoTipoTransmissao != null) || (this.codigoTipoTransmissao != null && !this.codigoTipoTransmissao.equals(other.codigoTipoTransmissao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tipoTransmissao;
    }
    
}
