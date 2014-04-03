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
@Table(name = "TipoTracao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoTracao.findAll", query = "SELECT t FROM TipoTracao t")})
public class TipoTracao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoTipoTracao")
    private Integer codigoTipoTracao;
    @Size(max = 50)
    @Column(name = "tipoTracao")
    private String tipoTracao;
    @OneToMany(mappedBy = "codigoTipoTracao")
    private Collection<Veiculo> veiculoCollection;

    public TipoTracao() {
    }

    public TipoTracao(Integer codigoTipoTracao) {
        this.codigoTipoTracao = codigoTipoTracao;
    }

    public Integer getCodigoTipoTracao() {
        return codigoTipoTracao;
    }

    public void setCodigoTipoTracao(Integer codigoTipoTracao) {
        this.codigoTipoTracao = codigoTipoTracao;
    }

    public String getTipoTracao() {
        return tipoTracao;
    }

    public void setTipoTracao(String tipoTracao) {
        this.tipoTracao = tipoTracao;
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
        hash += (codigoTipoTracao != null ? codigoTipoTracao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoTracao)) {
            return false;
        }
        TipoTracao other = (TipoTracao) object;
        if ((this.codigoTipoTracao == null && other.codigoTipoTracao != null) || (this.codigoTipoTracao != null && !this.codigoTipoTracao.equals(other.codigoTipoTracao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tipoTracao;
    }
    
}
