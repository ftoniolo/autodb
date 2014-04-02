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
@Table(name = "QualificadorAplicacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QualificadorAplicacao.findAll", query = "SELECT q FROM QualificadorAplicacao q")})
public class QualificadorAplicacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoQualificadorAplicacao")
    private Integer codigoQualificadorAplicacao;
    @JoinColumn(name = "codigoUnidadeMedida", referencedColumnName = "codigoUnidadeMedida")
    @ManyToOne
    private UnidadeMedida codigoUnidadeMedida;
    @JoinColumn(name = "codigoQualificador", referencedColumnName = "codigoQualificador")
    @ManyToOne(optional = false)
    private Qualificador codigoQualificador;
    @JoinColumn(name = "codigoAplicacao", referencedColumnName = "codigoAplicacao")
    @ManyToOne(optional = false)
    private Aplicacao codigoAplicacao;

    public QualificadorAplicacao() {
    }

    public QualificadorAplicacao(Integer codigoQualificadorAplicacao) {
        this.codigoQualificadorAplicacao = codigoQualificadorAplicacao;
    }

    public Integer getCodigoQualificadorAplicacao() {
        return codigoQualificadorAplicacao;
    }

    public void setCodigoQualificadorAplicacao(Integer codigoQualificadorAplicacao) {
        this.codigoQualificadorAplicacao = codigoQualificadorAplicacao;
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

    public Aplicacao getCodigoAplicacao() {
        return codigoAplicacao;
    }

    public void setCodigoAplicacao(Aplicacao codigoAplicacao) {
        this.codigoAplicacao = codigoAplicacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoQualificadorAplicacao != null ? codigoQualificadorAplicacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QualificadorAplicacao)) {
            return false;
        }
        QualificadorAplicacao other = (QualificadorAplicacao) object;
        if ((this.codigoQualificadorAplicacao == null && other.codigoQualificadorAplicacao != null) || (this.codigoQualificadorAplicacao != null && !this.codigoQualificadorAplicacao.equals(other.codigoQualificadorAplicacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.autodb.model.dao.QualificadorAplicacao[ codigoQualificadorAplicacao=" + codigoQualificadorAplicacao + " ]";
    }
    
}
