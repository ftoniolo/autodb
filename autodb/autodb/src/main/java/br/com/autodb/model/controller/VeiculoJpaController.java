/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.controller;

import br.com.autodb.model.controller.exceptions.IllegalOrphanException;
import br.com.autodb.model.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.autodb.model.dao.VeiculoBase;
import br.com.autodb.model.dao.TipoTransmissao;
import br.com.autodb.model.dao.TipoTracao;
import br.com.autodb.model.dao.Submodelo;
import br.com.autodb.model.dao.SistemaDirecao;
import br.com.autodb.model.dao.Pais;
import br.com.autodb.model.dao.Motor;
import br.com.autodb.model.dao.ConfiguracaoFreio;
import br.com.autodb.model.dao.AnexoVeiculo;
import java.util.ArrayList;
import java.util.Collection;
import br.com.autodb.model.dao.Aplicacao;
import br.com.autodb.model.dao.Veiculo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class VeiculoJpaController implements Serializable {

    public VeiculoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Veiculo veiculo) {
        if (veiculo.getAnexoVeiculoCollection() == null) {
            veiculo.setAnexoVeiculoCollection(new ArrayList<AnexoVeiculo>());
        }
        if (veiculo.getAplicacaoCollection() == null) {
            veiculo.setAplicacaoCollection(new ArrayList<Aplicacao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            VeiculoBase codigoVeiculoBase = veiculo.getCodigoVeiculoBase();
            if (codigoVeiculoBase != null) {
                codigoVeiculoBase = em.getReference(codigoVeiculoBase.getClass(), codigoVeiculoBase.getCodigoVeiculoBase());
                veiculo.setCodigoVeiculoBase(codigoVeiculoBase);
            }
            TipoTransmissao codigoTipoTransmissao = veiculo.getCodigoTipoTransmissao();
            if (codigoTipoTransmissao != null) {
                codigoTipoTransmissao = em.getReference(codigoTipoTransmissao.getClass(), codigoTipoTransmissao.getCodigoTipoTransmissao());
                veiculo.setCodigoTipoTransmissao(codigoTipoTransmissao);
            }
            TipoTracao codigoTipoTracao = veiculo.getCodigoTipoTracao();
            if (codigoTipoTracao != null) {
                codigoTipoTracao = em.getReference(codigoTipoTracao.getClass(), codigoTipoTracao.getCodigoTipoTracao());
                veiculo.setCodigoTipoTracao(codigoTipoTracao);
            }
            Submodelo codigoSubmodelo = veiculo.getCodigoSubmodelo();
            if (codigoSubmodelo != null) {
                codigoSubmodelo = em.getReference(codigoSubmodelo.getClass(), codigoSubmodelo.getCodigoSubmodelo());
                veiculo.setCodigoSubmodelo(codigoSubmodelo);
            }
            SistemaDirecao codigoSistemaDirecao = veiculo.getCodigoSistemaDirecao();
            if (codigoSistemaDirecao != null) {
                codigoSistemaDirecao = em.getReference(codigoSistemaDirecao.getClass(), codigoSistemaDirecao.getCodigoSistemaDirecao());
                veiculo.setCodigoSistemaDirecao(codigoSistemaDirecao);
            }
            Pais codigoPaisFabricacao = veiculo.getCodigoPaisFabricacao();
            if (codigoPaisFabricacao != null) {
                codigoPaisFabricacao = em.getReference(codigoPaisFabricacao.getClass(), codigoPaisFabricacao.getCodigoPais());
                veiculo.setCodigoPaisFabricacao(codigoPaisFabricacao);
            }
            Motor codigoMotor = veiculo.getCodigoMotor();
            if (codigoMotor != null) {
                codigoMotor = em.getReference(codigoMotor.getClass(), codigoMotor.getCodigoMotor());
                veiculo.setCodigoMotor(codigoMotor);
            }
            ConfiguracaoFreio codigoConfiguracaoFreio = veiculo.getCodigoConfiguracaoFreio();
            if (codigoConfiguracaoFreio != null) {
                codigoConfiguracaoFreio = em.getReference(codigoConfiguracaoFreio.getClass(), codigoConfiguracaoFreio.getCodigoConfiguracaoFreio());
                veiculo.setCodigoConfiguracaoFreio(codigoConfiguracaoFreio);
            }
            Collection<AnexoVeiculo> attachedAnexoVeiculoCollection = new ArrayList<AnexoVeiculo>();
            for (AnexoVeiculo anexoVeiculoCollectionAnexoVeiculoToAttach : veiculo.getAnexoVeiculoCollection()) {
                anexoVeiculoCollectionAnexoVeiculoToAttach = em.getReference(anexoVeiculoCollectionAnexoVeiculoToAttach.getClass(), anexoVeiculoCollectionAnexoVeiculoToAttach.getCodigoAnexoVeiculo());
                attachedAnexoVeiculoCollection.add(anexoVeiculoCollectionAnexoVeiculoToAttach);
            }
            veiculo.setAnexoVeiculoCollection(attachedAnexoVeiculoCollection);
            Collection<Aplicacao> attachedAplicacaoCollection = new ArrayList<Aplicacao>();
            for (Aplicacao aplicacaoCollectionAplicacaoToAttach : veiculo.getAplicacaoCollection()) {
                aplicacaoCollectionAplicacaoToAttach = em.getReference(aplicacaoCollectionAplicacaoToAttach.getClass(), aplicacaoCollectionAplicacaoToAttach.getCodigoAplicacao());
                attachedAplicacaoCollection.add(aplicacaoCollectionAplicacaoToAttach);
            }
            veiculo.setAplicacaoCollection(attachedAplicacaoCollection);
            em.persist(veiculo);
            if (codigoVeiculoBase != null) {
                codigoVeiculoBase.getVeiculoCollection().add(veiculo);
                codigoVeiculoBase = em.merge(codigoVeiculoBase);
            }
            if (codigoTipoTransmissao != null) {
                codigoTipoTransmissao.getVeiculoCollection().add(veiculo);
                codigoTipoTransmissao = em.merge(codigoTipoTransmissao);
            }
            if (codigoTipoTracao != null) {
                codigoTipoTracao.getVeiculoCollection().add(veiculo);
                codigoTipoTracao = em.merge(codigoTipoTracao);
            }
            if (codigoSubmodelo != null) {
                codigoSubmodelo.getVeiculoCollection().add(veiculo);
                codigoSubmodelo = em.merge(codigoSubmodelo);
            }
            if (codigoSistemaDirecao != null) {
                codigoSistemaDirecao.getVeiculoCollection().add(veiculo);
                codigoSistemaDirecao = em.merge(codigoSistemaDirecao);
            }
            if (codigoPaisFabricacao != null) {
                codigoPaisFabricacao.getVeiculoCollection().add(veiculo);
                codigoPaisFabricacao = em.merge(codigoPaisFabricacao);
            }
            if (codigoMotor != null) {
                codigoMotor.getVeiculoCollection().add(veiculo);
                codigoMotor = em.merge(codigoMotor);
            }
            if (codigoConfiguracaoFreio != null) {
                codigoConfiguracaoFreio.getVeiculoCollection().add(veiculo);
                codigoConfiguracaoFreio = em.merge(codigoConfiguracaoFreio);
            }
            for (AnexoVeiculo anexoVeiculoCollectionAnexoVeiculo : veiculo.getAnexoVeiculoCollection()) {
                Veiculo oldCodigoVeiculoOfAnexoVeiculoCollectionAnexoVeiculo = anexoVeiculoCollectionAnexoVeiculo.getCodigoVeiculo();
                anexoVeiculoCollectionAnexoVeiculo.setCodigoVeiculo(veiculo);
                anexoVeiculoCollectionAnexoVeiculo = em.merge(anexoVeiculoCollectionAnexoVeiculo);
                if (oldCodigoVeiculoOfAnexoVeiculoCollectionAnexoVeiculo != null) {
                    oldCodigoVeiculoOfAnexoVeiculoCollectionAnexoVeiculo.getAnexoVeiculoCollection().remove(anexoVeiculoCollectionAnexoVeiculo);
                    oldCodigoVeiculoOfAnexoVeiculoCollectionAnexoVeiculo = em.merge(oldCodigoVeiculoOfAnexoVeiculoCollectionAnexoVeiculo);
                }
            }
            for (Aplicacao aplicacaoCollectionAplicacao : veiculo.getAplicacaoCollection()) {
                Veiculo oldCodigoVeiculoOfAplicacaoCollectionAplicacao = aplicacaoCollectionAplicacao.getCodigoVeiculo();
                aplicacaoCollectionAplicacao.setCodigoVeiculo(veiculo);
                aplicacaoCollectionAplicacao = em.merge(aplicacaoCollectionAplicacao);
                if (oldCodigoVeiculoOfAplicacaoCollectionAplicacao != null) {
                    oldCodigoVeiculoOfAplicacaoCollectionAplicacao.getAplicacaoCollection().remove(aplicacaoCollectionAplicacao);
                    oldCodigoVeiculoOfAplicacaoCollectionAplicacao = em.merge(oldCodigoVeiculoOfAplicacaoCollectionAplicacao);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Veiculo veiculo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Veiculo persistentVeiculo = em.find(Veiculo.class, veiculo.getCodigoVeiculo());
            VeiculoBase codigoVeiculoBaseOld = persistentVeiculo.getCodigoVeiculoBase();
            VeiculoBase codigoVeiculoBaseNew = veiculo.getCodigoVeiculoBase();
            TipoTransmissao codigoTipoTransmissaoOld = persistentVeiculo.getCodigoTipoTransmissao();
            TipoTransmissao codigoTipoTransmissaoNew = veiculo.getCodigoTipoTransmissao();
            TipoTracao codigoTipoTracaoOld = persistentVeiculo.getCodigoTipoTracao();
            TipoTracao codigoTipoTracaoNew = veiculo.getCodigoTipoTracao();
            Submodelo codigoSubmodeloOld = persistentVeiculo.getCodigoSubmodelo();
            Submodelo codigoSubmodeloNew = veiculo.getCodigoSubmodelo();
            SistemaDirecao codigoSistemaDirecaoOld = persistentVeiculo.getCodigoSistemaDirecao();
            SistemaDirecao codigoSistemaDirecaoNew = veiculo.getCodigoSistemaDirecao();
            Pais codigoPaisFabricacaoOld = persistentVeiculo.getCodigoPaisFabricacao();
            Pais codigoPaisFabricacaoNew = veiculo.getCodigoPaisFabricacao();
            Motor codigoMotorOld = persistentVeiculo.getCodigoMotor();
            Motor codigoMotorNew = veiculo.getCodigoMotor();
            ConfiguracaoFreio codigoConfiguracaoFreioOld = persistentVeiculo.getCodigoConfiguracaoFreio();
            ConfiguracaoFreio codigoConfiguracaoFreioNew = veiculo.getCodigoConfiguracaoFreio();
            Collection<AnexoVeiculo> anexoVeiculoCollectionOld = persistentVeiculo.getAnexoVeiculoCollection();
            Collection<AnexoVeiculo> anexoVeiculoCollectionNew = veiculo.getAnexoVeiculoCollection();
            Collection<Aplicacao> aplicacaoCollectionOld = persistentVeiculo.getAplicacaoCollection();
            Collection<Aplicacao> aplicacaoCollectionNew = veiculo.getAplicacaoCollection();
            List<String> illegalOrphanMessages = null;
            for (AnexoVeiculo anexoVeiculoCollectionOldAnexoVeiculo : anexoVeiculoCollectionOld) {
                if (!anexoVeiculoCollectionNew.contains(anexoVeiculoCollectionOldAnexoVeiculo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AnexoVeiculo " + anexoVeiculoCollectionOldAnexoVeiculo + " since its codigoVeiculo field is not nullable.");
                }
            }
            for (Aplicacao aplicacaoCollectionOldAplicacao : aplicacaoCollectionOld) {
                if (!aplicacaoCollectionNew.contains(aplicacaoCollectionOldAplicacao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Aplicacao " + aplicacaoCollectionOldAplicacao + " since its codigoVeiculo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codigoVeiculoBaseNew != null) {
                codigoVeiculoBaseNew = em.getReference(codigoVeiculoBaseNew.getClass(), codigoVeiculoBaseNew.getCodigoVeiculoBase());
                veiculo.setCodigoVeiculoBase(codigoVeiculoBaseNew);
            }
            if (codigoTipoTransmissaoNew != null) {
                codigoTipoTransmissaoNew = em.getReference(codigoTipoTransmissaoNew.getClass(), codigoTipoTransmissaoNew.getCodigoTipoTransmissao());
                veiculo.setCodigoTipoTransmissao(codigoTipoTransmissaoNew);
            }
            if (codigoTipoTracaoNew != null) {
                codigoTipoTracaoNew = em.getReference(codigoTipoTracaoNew.getClass(), codigoTipoTracaoNew.getCodigoTipoTracao());
                veiculo.setCodigoTipoTracao(codigoTipoTracaoNew);
            }
            if (codigoSubmodeloNew != null) {
                codigoSubmodeloNew = em.getReference(codigoSubmodeloNew.getClass(), codigoSubmodeloNew.getCodigoSubmodelo());
                veiculo.setCodigoSubmodelo(codigoSubmodeloNew);
            }
            if (codigoSistemaDirecaoNew != null) {
                codigoSistemaDirecaoNew = em.getReference(codigoSistemaDirecaoNew.getClass(), codigoSistemaDirecaoNew.getCodigoSistemaDirecao());
                veiculo.setCodigoSistemaDirecao(codigoSistemaDirecaoNew);
            }
            if (codigoPaisFabricacaoNew != null) {
                codigoPaisFabricacaoNew = em.getReference(codigoPaisFabricacaoNew.getClass(), codigoPaisFabricacaoNew.getCodigoPais());
                veiculo.setCodigoPaisFabricacao(codigoPaisFabricacaoNew);
            }
            if (codigoMotorNew != null) {
                codigoMotorNew = em.getReference(codigoMotorNew.getClass(), codigoMotorNew.getCodigoMotor());
                veiculo.setCodigoMotor(codigoMotorNew);
            }
            if (codigoConfiguracaoFreioNew != null) {
                codigoConfiguracaoFreioNew = em.getReference(codigoConfiguracaoFreioNew.getClass(), codigoConfiguracaoFreioNew.getCodigoConfiguracaoFreio());
                veiculo.setCodigoConfiguracaoFreio(codigoConfiguracaoFreioNew);
            }
            Collection<AnexoVeiculo> attachedAnexoVeiculoCollectionNew = new ArrayList<AnexoVeiculo>();
            for (AnexoVeiculo anexoVeiculoCollectionNewAnexoVeiculoToAttach : anexoVeiculoCollectionNew) {
                anexoVeiculoCollectionNewAnexoVeiculoToAttach = em.getReference(anexoVeiculoCollectionNewAnexoVeiculoToAttach.getClass(), anexoVeiculoCollectionNewAnexoVeiculoToAttach.getCodigoAnexoVeiculo());
                attachedAnexoVeiculoCollectionNew.add(anexoVeiculoCollectionNewAnexoVeiculoToAttach);
            }
            anexoVeiculoCollectionNew = attachedAnexoVeiculoCollectionNew;
            veiculo.setAnexoVeiculoCollection(anexoVeiculoCollectionNew);
            Collection<Aplicacao> attachedAplicacaoCollectionNew = new ArrayList<Aplicacao>();
            for (Aplicacao aplicacaoCollectionNewAplicacaoToAttach : aplicacaoCollectionNew) {
                aplicacaoCollectionNewAplicacaoToAttach = em.getReference(aplicacaoCollectionNewAplicacaoToAttach.getClass(), aplicacaoCollectionNewAplicacaoToAttach.getCodigoAplicacao());
                attachedAplicacaoCollectionNew.add(aplicacaoCollectionNewAplicacaoToAttach);
            }
            aplicacaoCollectionNew = attachedAplicacaoCollectionNew;
            veiculo.setAplicacaoCollection(aplicacaoCollectionNew);
            veiculo = em.merge(veiculo);
            if (codigoVeiculoBaseOld != null && !codigoVeiculoBaseOld.equals(codigoVeiculoBaseNew)) {
                codigoVeiculoBaseOld.getVeiculoCollection().remove(veiculo);
                codigoVeiculoBaseOld = em.merge(codigoVeiculoBaseOld);
            }
            if (codigoVeiculoBaseNew != null && !codigoVeiculoBaseNew.equals(codigoVeiculoBaseOld)) {
                codigoVeiculoBaseNew.getVeiculoCollection().add(veiculo);
                codigoVeiculoBaseNew = em.merge(codigoVeiculoBaseNew);
            }
            if (codigoTipoTransmissaoOld != null && !codigoTipoTransmissaoOld.equals(codigoTipoTransmissaoNew)) {
                codigoTipoTransmissaoOld.getVeiculoCollection().remove(veiculo);
                codigoTipoTransmissaoOld = em.merge(codigoTipoTransmissaoOld);
            }
            if (codigoTipoTransmissaoNew != null && !codigoTipoTransmissaoNew.equals(codigoTipoTransmissaoOld)) {
                codigoTipoTransmissaoNew.getVeiculoCollection().add(veiculo);
                codigoTipoTransmissaoNew = em.merge(codigoTipoTransmissaoNew);
            }
            if (codigoTipoTracaoOld != null && !codigoTipoTracaoOld.equals(codigoTipoTracaoNew)) {
                codigoTipoTracaoOld.getVeiculoCollection().remove(veiculo);
                codigoTipoTracaoOld = em.merge(codigoTipoTracaoOld);
            }
            if (codigoTipoTracaoNew != null && !codigoTipoTracaoNew.equals(codigoTipoTracaoOld)) {
                codigoTipoTracaoNew.getVeiculoCollection().add(veiculo);
                codigoTipoTracaoNew = em.merge(codigoTipoTracaoNew);
            }
            if (codigoSubmodeloOld != null && !codigoSubmodeloOld.equals(codigoSubmodeloNew)) {
                codigoSubmodeloOld.getVeiculoCollection().remove(veiculo);
                codigoSubmodeloOld = em.merge(codigoSubmodeloOld);
            }
            if (codigoSubmodeloNew != null && !codigoSubmodeloNew.equals(codigoSubmodeloOld)) {
                codigoSubmodeloNew.getVeiculoCollection().add(veiculo);
                codigoSubmodeloNew = em.merge(codigoSubmodeloNew);
            }
            if (codigoSistemaDirecaoOld != null && !codigoSistemaDirecaoOld.equals(codigoSistemaDirecaoNew)) {
                codigoSistemaDirecaoOld.getVeiculoCollection().remove(veiculo);
                codigoSistemaDirecaoOld = em.merge(codigoSistemaDirecaoOld);
            }
            if (codigoSistemaDirecaoNew != null && !codigoSistemaDirecaoNew.equals(codigoSistemaDirecaoOld)) {
                codigoSistemaDirecaoNew.getVeiculoCollection().add(veiculo);
                codigoSistemaDirecaoNew = em.merge(codigoSistemaDirecaoNew);
            }
            if (codigoPaisFabricacaoOld != null && !codigoPaisFabricacaoOld.equals(codigoPaisFabricacaoNew)) {
                codigoPaisFabricacaoOld.getVeiculoCollection().remove(veiculo);
                codigoPaisFabricacaoOld = em.merge(codigoPaisFabricacaoOld);
            }
            if (codigoPaisFabricacaoNew != null && !codigoPaisFabricacaoNew.equals(codigoPaisFabricacaoOld)) {
                codigoPaisFabricacaoNew.getVeiculoCollection().add(veiculo);
                codigoPaisFabricacaoNew = em.merge(codigoPaisFabricacaoNew);
            }
            if (codigoMotorOld != null && !codigoMotorOld.equals(codigoMotorNew)) {
                codigoMotorOld.getVeiculoCollection().remove(veiculo);
                codigoMotorOld = em.merge(codigoMotorOld);
            }
            if (codigoMotorNew != null && !codigoMotorNew.equals(codigoMotorOld)) {
                codigoMotorNew.getVeiculoCollection().add(veiculo);
                codigoMotorNew = em.merge(codigoMotorNew);
            }
            if (codigoConfiguracaoFreioOld != null && !codigoConfiguracaoFreioOld.equals(codigoConfiguracaoFreioNew)) {
                codigoConfiguracaoFreioOld.getVeiculoCollection().remove(veiculo);
                codigoConfiguracaoFreioOld = em.merge(codigoConfiguracaoFreioOld);
            }
            if (codigoConfiguracaoFreioNew != null && !codigoConfiguracaoFreioNew.equals(codigoConfiguracaoFreioOld)) {
                codigoConfiguracaoFreioNew.getVeiculoCollection().add(veiculo);
                codigoConfiguracaoFreioNew = em.merge(codigoConfiguracaoFreioNew);
            }
            for (AnexoVeiculo anexoVeiculoCollectionNewAnexoVeiculo : anexoVeiculoCollectionNew) {
                if (!anexoVeiculoCollectionOld.contains(anexoVeiculoCollectionNewAnexoVeiculo)) {
                    Veiculo oldCodigoVeiculoOfAnexoVeiculoCollectionNewAnexoVeiculo = anexoVeiculoCollectionNewAnexoVeiculo.getCodigoVeiculo();
                    anexoVeiculoCollectionNewAnexoVeiculo.setCodigoVeiculo(veiculo);
                    anexoVeiculoCollectionNewAnexoVeiculo = em.merge(anexoVeiculoCollectionNewAnexoVeiculo);
                    if (oldCodigoVeiculoOfAnexoVeiculoCollectionNewAnexoVeiculo != null && !oldCodigoVeiculoOfAnexoVeiculoCollectionNewAnexoVeiculo.equals(veiculo)) {
                        oldCodigoVeiculoOfAnexoVeiculoCollectionNewAnexoVeiculo.getAnexoVeiculoCollection().remove(anexoVeiculoCollectionNewAnexoVeiculo);
                        oldCodigoVeiculoOfAnexoVeiculoCollectionNewAnexoVeiculo = em.merge(oldCodigoVeiculoOfAnexoVeiculoCollectionNewAnexoVeiculo);
                    }
                }
            }
            for (Aplicacao aplicacaoCollectionNewAplicacao : aplicacaoCollectionNew) {
                if (!aplicacaoCollectionOld.contains(aplicacaoCollectionNewAplicacao)) {
                    Veiculo oldCodigoVeiculoOfAplicacaoCollectionNewAplicacao = aplicacaoCollectionNewAplicacao.getCodigoVeiculo();
                    aplicacaoCollectionNewAplicacao.setCodigoVeiculo(veiculo);
                    aplicacaoCollectionNewAplicacao = em.merge(aplicacaoCollectionNewAplicacao);
                    if (oldCodigoVeiculoOfAplicacaoCollectionNewAplicacao != null && !oldCodigoVeiculoOfAplicacaoCollectionNewAplicacao.equals(veiculo)) {
                        oldCodigoVeiculoOfAplicacaoCollectionNewAplicacao.getAplicacaoCollection().remove(aplicacaoCollectionNewAplicacao);
                        oldCodigoVeiculoOfAplicacaoCollectionNewAplicacao = em.merge(oldCodigoVeiculoOfAplicacaoCollectionNewAplicacao);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = veiculo.getCodigoVeiculo();
                if (findVeiculo(id) == null) {
                    throw new NonexistentEntityException("The veiculo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Veiculo veiculo;
            try {
                veiculo = em.getReference(Veiculo.class, id);
                veiculo.getCodigoVeiculo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The veiculo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<AnexoVeiculo> anexoVeiculoCollectionOrphanCheck = veiculo.getAnexoVeiculoCollection();
            for (AnexoVeiculo anexoVeiculoCollectionOrphanCheckAnexoVeiculo : anexoVeiculoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Veiculo (" + veiculo + ") cannot be destroyed since the AnexoVeiculo " + anexoVeiculoCollectionOrphanCheckAnexoVeiculo + " in its anexoVeiculoCollection field has a non-nullable codigoVeiculo field.");
            }
            Collection<Aplicacao> aplicacaoCollectionOrphanCheck = veiculo.getAplicacaoCollection();
            for (Aplicacao aplicacaoCollectionOrphanCheckAplicacao : aplicacaoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Veiculo (" + veiculo + ") cannot be destroyed since the Aplicacao " + aplicacaoCollectionOrphanCheckAplicacao + " in its aplicacaoCollection field has a non-nullable codigoVeiculo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            VeiculoBase codigoVeiculoBase = veiculo.getCodigoVeiculoBase();
            if (codigoVeiculoBase != null) {
                codigoVeiculoBase.getVeiculoCollection().remove(veiculo);
                codigoVeiculoBase = em.merge(codigoVeiculoBase);
            }
            TipoTransmissao codigoTipoTransmissao = veiculo.getCodigoTipoTransmissao();
            if (codigoTipoTransmissao != null) {
                codigoTipoTransmissao.getVeiculoCollection().remove(veiculo);
                codigoTipoTransmissao = em.merge(codigoTipoTransmissao);
            }
            TipoTracao codigoTipoTracao = veiculo.getCodigoTipoTracao();
            if (codigoTipoTracao != null) {
                codigoTipoTracao.getVeiculoCollection().remove(veiculo);
                codigoTipoTracao = em.merge(codigoTipoTracao);
            }
            Submodelo codigoSubmodelo = veiculo.getCodigoSubmodelo();
            if (codigoSubmodelo != null) {
                codigoSubmodelo.getVeiculoCollection().remove(veiculo);
                codigoSubmodelo = em.merge(codigoSubmodelo);
            }
            SistemaDirecao codigoSistemaDirecao = veiculo.getCodigoSistemaDirecao();
            if (codigoSistemaDirecao != null) {
                codigoSistemaDirecao.getVeiculoCollection().remove(veiculo);
                codigoSistemaDirecao = em.merge(codigoSistemaDirecao);
            }
            Pais codigoPaisFabricacao = veiculo.getCodigoPaisFabricacao();
            if (codigoPaisFabricacao != null) {
                codigoPaisFabricacao.getVeiculoCollection().remove(veiculo);
                codigoPaisFabricacao = em.merge(codigoPaisFabricacao);
            }
            Motor codigoMotor = veiculo.getCodigoMotor();
            if (codigoMotor != null) {
                codigoMotor.getVeiculoCollection().remove(veiculo);
                codigoMotor = em.merge(codigoMotor);
            }
            ConfiguracaoFreio codigoConfiguracaoFreio = veiculo.getCodigoConfiguracaoFreio();
            if (codigoConfiguracaoFreio != null) {
                codigoConfiguracaoFreio.getVeiculoCollection().remove(veiculo);
                codigoConfiguracaoFreio = em.merge(codigoConfiguracaoFreio);
            }
            em.remove(veiculo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Veiculo> findVeiculoEntities() {
        return findVeiculoEntities(true, -1, -1);
    }

    public List<Veiculo> findVeiculoEntities(int maxResults, int firstResult) {
        return findVeiculoEntities(false, maxResults, firstResult);
    }

    private List<Veiculo> findVeiculoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Veiculo.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Veiculo findVeiculo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Veiculo.class, id);
        } finally {
            em.close();
        }
    }

    public int getVeiculoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Veiculo> rt = cq.from(Veiculo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
