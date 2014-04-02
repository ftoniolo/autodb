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
@Table(name = "Motor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Motor.findAll", query = "SELECT m FROM Motor m")})
public class Motor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoMotor")
    private Integer codigoMotor;
    @Size(max = 50)
    @Column(name = "motor")
    private String motor;
    @Size(max = 255)
    @Column(name = "potenciaCV")
    private String potenciaCV;
    @Size(max = 50)
    @Column(name = "codigoDesignacao")
    private String codigoDesignacao;
    @Size(max = 50)
    @Column(name = "periodo")
    private String periodo;
    @Column(name = "quantidadeCilindros")
    private Integer quantidadeCilindros;
    @Size(max = 1)
    @Column(name = "disposicaoCilindros")
    private String disposicaoCilindros;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "capacidadeVolumetrica")
    private Double capacidadeVolumetrica;
    @Column(name = "quantidadeValvulas")
    private Integer quantidadeValvulas;
    @JoinColumn(name = "codigoTipoMotor", referencedColumnName = "codigoTipoMotor")
    @ManyToOne
    private TipoMotor codigoTipoMotor;
    @JoinColumn(name = "codigoTipoCombustivel", referencedColumnName = "codigoTipoCombustivel")
    @ManyToOne
    private TipoCombustivel codigoTipoCombustivel;
    @JoinColumn(name = "codigoTipoCabecote", referencedColumnName = "codigoTipoCabecote")
    @ManyToOne
    private TipoCabecote codigoTipoCabecote;
    @JoinColumn(name = "codigoTipoAspiracao", referencedColumnName = "codigoTipoAspiracao")
    @ManyToOne
    private TipoAspiracao codigoTipoAspiracao;
    @JoinColumn(name = "codigoInjecao", referencedColumnName = "codigoInjecao")
    @ManyToOne
    private Injecao codigoInjecao;
    @OneToMany(mappedBy = "codigoMotor")
    private Collection<Veiculo> veiculoCollection;

    public Motor() {
    }

    public Motor(Integer codigoMotor) {
        this.codigoMotor = codigoMotor;
    }

    public Integer getCodigoMotor() {
        return codigoMotor;
    }

    public void setCodigoMotor(Integer codigoMotor) {
        this.codigoMotor = codigoMotor;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getPotenciaCV() {
        return potenciaCV;
    }

    public void setPotenciaCV(String potenciaCV) {
        this.potenciaCV = potenciaCV;
    }

    public String getCodigoDesignacao() {
        return codigoDesignacao;
    }

    public void setCodigoDesignacao(String codigoDesignacao) {
        this.codigoDesignacao = codigoDesignacao;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Integer getQuantidadeCilindros() {
        return quantidadeCilindros;
    }

    public void setQuantidadeCilindros(Integer quantidadeCilindros) {
        this.quantidadeCilindros = quantidadeCilindros;
    }

    public String getDisposicaoCilindros() {
        return disposicaoCilindros;
    }

    public void setDisposicaoCilindros(String disposicaoCilindros) {
        this.disposicaoCilindros = disposicaoCilindros;
    }

    public Double getCapacidadeVolumetrica() {
        return capacidadeVolumetrica;
    }

    public void setCapacidadeVolumetrica(Double capacidadeVolumetrica) {
        this.capacidadeVolumetrica = capacidadeVolumetrica;
    }

    public Integer getQuantidadeValvulas() {
        return quantidadeValvulas;
    }

    public void setQuantidadeValvulas(Integer quantidadeValvulas) {
        this.quantidadeValvulas = quantidadeValvulas;
    }

    public TipoMotor getCodigoTipoMotor() {
        return codigoTipoMotor;
    }

    public void setCodigoTipoMotor(TipoMotor codigoTipoMotor) {
        this.codigoTipoMotor = codigoTipoMotor;
    }

    public TipoCombustivel getCodigoTipoCombustivel() {
        return codigoTipoCombustivel;
    }

    public void setCodigoTipoCombustivel(TipoCombustivel codigoTipoCombustivel) {
        this.codigoTipoCombustivel = codigoTipoCombustivel;
    }

    public TipoCabecote getCodigoTipoCabecote() {
        return codigoTipoCabecote;
    }

    public void setCodigoTipoCabecote(TipoCabecote codigoTipoCabecote) {
        this.codigoTipoCabecote = codigoTipoCabecote;
    }

    public TipoAspiracao getCodigoTipoAspiracao() {
        return codigoTipoAspiracao;
    }

    public void setCodigoTipoAspiracao(TipoAspiracao codigoTipoAspiracao) {
        this.codigoTipoAspiracao = codigoTipoAspiracao;
    }

    public Injecao getCodigoInjecao() {
        return codigoInjecao;
    }

    public void setCodigoInjecao(Injecao codigoInjecao) {
        this.codigoInjecao = codigoInjecao;
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
        hash += (codigoMotor != null ? codigoMotor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Motor)) {
            return false;
        }
        Motor other = (Motor) object;
        if ((this.codigoMotor == null && other.codigoMotor != null) || (this.codigoMotor != null && !this.codigoMotor.equals(other.codigoMotor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.autodb.model.dao.Motor[ codigoMotor=" + codigoMotor + " ]";
    }
    
}
