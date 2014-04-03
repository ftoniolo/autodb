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
@Table(name = "Fabricante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fabricante.findAll", query = "SELECT f FROM Fabricante f")})
public class Fabricante implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoFabricante")
    private Integer codigoFabricante;
    @Size(max = 50)
    @Column(name = "fabricante")
    private String fabricante;
    @Size(max = 50)
    @Column(name = "nomeAbreviado")
    private String nomeAbreviado;
    @Size(max = 50)
    @Column(name = "cidade")
    private String cidade;
    @Size(max = 50)
    @Column(name = "telefone")
    private String telefone;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "email")
    private String email;
    @Size(max = 50)
    @Column(name = "website")
    private String website;
    @Size(max = 50)
    @Column(name = "observacao")
    private String observacao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "oem")
    private boolean oem;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoFabricante")
    private Collection<FabricanteProduto> fabricanteProdutoCollection;
    @JoinColumn(name = "codigoPais", referencedColumnName = "codigoPais")
    @ManyToOne
    private Pais codigoPais;

    public Fabricante() {
    }

    public Fabricante(Integer codigoFabricante) {
        this.codigoFabricante = codigoFabricante;
    }

    public Fabricante(Integer codigoFabricante, boolean oem) {
        this.codigoFabricante = codigoFabricante;
        this.oem = oem;
    }

    public Integer getCodigoFabricante() {
        return codigoFabricante;
    }

    public void setCodigoFabricante(Integer codigoFabricante) {
        this.codigoFabricante = codigoFabricante;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getNomeAbreviado() {
        return nomeAbreviado;
    }

    public void setNomeAbreviado(String nomeAbreviado) {
        this.nomeAbreviado = nomeAbreviado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public boolean getOem() {
        return oem;
    }

    public void setOem(boolean oem) {
        this.oem = oem;
    }

    @XmlTransient
    public Collection<FabricanteProduto> getFabricanteProdutoCollection() {
        return fabricanteProdutoCollection;
    }

    public void setFabricanteProdutoCollection(Collection<FabricanteProduto> fabricanteProdutoCollection) {
        this.fabricanteProdutoCollection = fabricanteProdutoCollection;
    }

    public Pais getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(Pais codigoPais) {
        this.codigoPais = codigoPais;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoFabricante != null ? codigoFabricante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fabricante)) {
            return false;
        }
        Fabricante other = (Fabricante) object;
        if ((this.codigoFabricante == null && other.codigoFabricante != null) || (this.codigoFabricante != null && !this.codigoFabricante.equals(other.codigoFabricante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return fabricante;
    }
    
}
