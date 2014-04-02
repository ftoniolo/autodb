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
@Table(name = "AnexoVeiculo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AnexoVeiculo.findAll", query = "SELECT a FROM AnexoVeiculo a")})
public class AnexoVeiculo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoAnexoVeiculo")
    private Integer codigoAnexoVeiculo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "caminhoAnexo")
    private String caminhoAnexo;
    @Size(max = 255)
    @Column(name = "observacao")
    private String observacao;
    @JoinColumn(name = "codigoVeiculo", referencedColumnName = "codigoVeiculo")
    @ManyToOne(optional = false)
    private Veiculo codigoVeiculo;
    @JoinColumn(name = "codigoTipoAnexo", referencedColumnName = "codigoTipoAnexo")
    @ManyToOne(optional = false)
    private TipoAnexo codigoTipoAnexo;

    public AnexoVeiculo() {
    }

    public AnexoVeiculo(Integer codigoAnexoVeiculo) {
        this.codigoAnexoVeiculo = codigoAnexoVeiculo;
    }

    public AnexoVeiculo(Integer codigoAnexoVeiculo, String caminhoAnexo) {
        this.codigoAnexoVeiculo = codigoAnexoVeiculo;
        this.caminhoAnexo = caminhoAnexo;
    }

    public Integer getCodigoAnexoVeiculo() {
        return codigoAnexoVeiculo;
    }

    public void setCodigoAnexoVeiculo(Integer codigoAnexoVeiculo) {
        this.codigoAnexoVeiculo = codigoAnexoVeiculo;
    }

    public String getCaminhoAnexo() {
        return caminhoAnexo;
    }

    public void setCaminhoAnexo(String caminhoAnexo) {
        this.caminhoAnexo = caminhoAnexo;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Veiculo getCodigoVeiculo() {
        return codigoVeiculo;
    }

    public void setCodigoVeiculo(Veiculo codigoVeiculo) {
        this.codigoVeiculo = codigoVeiculo;
    }

    public TipoAnexo getCodigoTipoAnexo() {
        return codigoTipoAnexo;
    }

    public void setCodigoTipoAnexo(TipoAnexo codigoTipoAnexo) {
        this.codigoTipoAnexo = codigoTipoAnexo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoAnexoVeiculo != null ? codigoAnexoVeiculo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnexoVeiculo)) {
            return false;
        }
        AnexoVeiculo other = (AnexoVeiculo) object;
        if ((this.codigoAnexoVeiculo == null && other.codigoAnexoVeiculo != null) || (this.codigoAnexoVeiculo != null && !this.codigoAnexoVeiculo.equals(other.codigoAnexoVeiculo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.autodb.model.dao.AnexoVeiculo[ codigoAnexoVeiculo=" + codigoAnexoVeiculo + " ]";
    }
    
}
