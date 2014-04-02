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
@Table(name = "VeiculoBaseRelacionado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VeiculoBaseRelacionado.findAll", query = "SELECT v FROM VeiculoBaseRelacionado v")})
public class VeiculoBaseRelacionado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoVeiculoBaseRelacionado")
    private Integer codigoVeiculoBaseRelacionado;
    @JoinColumn(name = "codigoVeiculoBaseB", referencedColumnName = "codigoVeiculoBase")
    @ManyToOne(optional = false)
    private VeiculoBase codigoVeiculoBaseB;
    @JoinColumn(name = "codigoVeiculoBaseA", referencedColumnName = "codigoVeiculoBase")
    @ManyToOne(optional = false)
    private VeiculoBase codigoVeiculoBaseA;

    public VeiculoBaseRelacionado() {
    }

    public VeiculoBaseRelacionado(Integer codigoVeiculoBaseRelacionado) {
        this.codigoVeiculoBaseRelacionado = codigoVeiculoBaseRelacionado;
    }

    public Integer getCodigoVeiculoBaseRelacionado() {
        return codigoVeiculoBaseRelacionado;
    }

    public void setCodigoVeiculoBaseRelacionado(Integer codigoVeiculoBaseRelacionado) {
        this.codigoVeiculoBaseRelacionado = codigoVeiculoBaseRelacionado;
    }

    public VeiculoBase getCodigoVeiculoBaseB() {
        return codigoVeiculoBaseB;
    }

    public void setCodigoVeiculoBaseB(VeiculoBase codigoVeiculoBaseB) {
        this.codigoVeiculoBaseB = codigoVeiculoBaseB;
    }

    public VeiculoBase getCodigoVeiculoBaseA() {
        return codigoVeiculoBaseA;
    }

    public void setCodigoVeiculoBaseA(VeiculoBase codigoVeiculoBaseA) {
        this.codigoVeiculoBaseA = codigoVeiculoBaseA;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoVeiculoBaseRelacionado != null ? codigoVeiculoBaseRelacionado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VeiculoBaseRelacionado)) {
            return false;
        }
        VeiculoBaseRelacionado other = (VeiculoBaseRelacionado) object;
        if ((this.codigoVeiculoBaseRelacionado == null && other.codigoVeiculoBaseRelacionado != null) || (this.codigoVeiculoBaseRelacionado != null && !this.codigoVeiculoBaseRelacionado.equals(other.codigoVeiculoBaseRelacionado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.autodb.model.dao.VeiculoBaseRelacionado[ codigoVeiculoBaseRelacionado=" + codigoVeiculoBaseRelacionado + " ]";
    }
    
}
