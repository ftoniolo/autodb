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
@Table(name = "Injecao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Injecao.findAll", query = "SELECT i FROM Injecao i")})
public class Injecao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoInjecao")
    private Integer codigoInjecao;
    @Size(max = 50)
    @Column(name = "injecao")
    private String injecao;
    @OneToMany(mappedBy = "codigoInjecao")
    private Collection<Motor> motorCollection;

    public Injecao() {
    }

    public Injecao(Integer codigoInjecao) {
        this.codigoInjecao = codigoInjecao;
    }

    public Integer getCodigoInjecao() {
        return codigoInjecao;
    }

    public void setCodigoInjecao(Integer codigoInjecao) {
        this.codigoInjecao = codigoInjecao;
    }

    public String getInjecao() {
        return injecao;
    }

    public void setInjecao(String injecao) {
        this.injecao = injecao;
    }

    @XmlTransient
    public Collection<Motor> getMotorCollection() {
        return motorCollection;
    }

    public void setMotorCollection(Collection<Motor> motorCollection) {
        this.motorCollection = motorCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoInjecao != null ? codigoInjecao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Injecao)) {
            return false;
        }
        Injecao other = (Injecao) object;
        if ((this.codigoInjecao == null && other.codigoInjecao != null) || (this.codigoInjecao != null && !this.codigoInjecao.equals(other.codigoInjecao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return injecao;
    }
    
}
