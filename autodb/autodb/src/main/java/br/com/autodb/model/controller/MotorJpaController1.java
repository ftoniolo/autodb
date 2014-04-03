/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.controller;

import br.com.autodb.model.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.autodb.model.dao.TipoMotor;
import br.com.autodb.model.dao.TipoCombustivel;
import br.com.autodb.model.dao.TipoCabecote;
import br.com.autodb.model.dao.TipoAspiracao;
import br.com.autodb.model.dao.Injecao;
import br.com.autodb.model.dao.Motor;
import br.com.autodb.model.dao.Veiculo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class MotorJpaController1 implements Serializable {

    public MotorJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Motor motor) {
        if (motor.getVeiculoCollection() == null) {
            motor.setVeiculoCollection(new ArrayList<Veiculo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoMotor codigoTipoMotor = motor.getCodigoTipoMotor();
            if (codigoTipoMotor != null) {
                codigoTipoMotor = em.getReference(codigoTipoMotor.getClass(), codigoTipoMotor.getCodigoTipoMotor());
                motor.setCodigoTipoMotor(codigoTipoMotor);
            }
            TipoCombustivel codigoTipoCombustivel = motor.getCodigoTipoCombustivel();
            if (codigoTipoCombustivel != null) {
                codigoTipoCombustivel = em.getReference(codigoTipoCombustivel.getClass(), codigoTipoCombustivel.getCodigoTipoCombustivel());
                motor.setCodigoTipoCombustivel(codigoTipoCombustivel);
            }
            TipoCabecote codigoTipoCabecote = motor.getCodigoTipoCabecote();
            if (codigoTipoCabecote != null) {
                codigoTipoCabecote = em.getReference(codigoTipoCabecote.getClass(), codigoTipoCabecote.getCodigoTipoCabecote());
                motor.setCodigoTipoCabecote(codigoTipoCabecote);
            }
            TipoAspiracao codigoTipoAspiracao = motor.getCodigoTipoAspiracao();
            if (codigoTipoAspiracao != null) {
                codigoTipoAspiracao = em.getReference(codigoTipoAspiracao.getClass(), codigoTipoAspiracao.getCodigoTipoAspiracao());
                motor.setCodigoTipoAspiracao(codigoTipoAspiracao);
            }
            Injecao codigoInjecao = motor.getCodigoInjecao();
            if (codigoInjecao != null) {
                codigoInjecao = em.getReference(codigoInjecao.getClass(), codigoInjecao.getCodigoInjecao());
                motor.setCodigoInjecao(codigoInjecao);
            }
            Collection<Veiculo> attachedVeiculoCollection = new ArrayList<Veiculo>();
            for (Veiculo veiculoCollectionVeiculoToAttach : motor.getVeiculoCollection()) {
                veiculoCollectionVeiculoToAttach = em.getReference(veiculoCollectionVeiculoToAttach.getClass(), veiculoCollectionVeiculoToAttach.getCodigoVeiculo());
                attachedVeiculoCollection.add(veiculoCollectionVeiculoToAttach);
            }
            motor.setVeiculoCollection(attachedVeiculoCollection);
            em.persist(motor);
            if (codigoTipoMotor != null) {
                codigoTipoMotor.getMotorCollection().add(motor);
                codigoTipoMotor = em.merge(codigoTipoMotor);
            }
            if (codigoTipoCombustivel != null) {
                codigoTipoCombustivel.getMotorCollection().add(motor);
                codigoTipoCombustivel = em.merge(codigoTipoCombustivel);
            }
            if (codigoTipoCabecote != null) {
                codigoTipoCabecote.getMotorCollection().add(motor);
                codigoTipoCabecote = em.merge(codigoTipoCabecote);
            }
            if (codigoTipoAspiracao != null) {
                codigoTipoAspiracao.getMotorCollection().add(motor);
                codigoTipoAspiracao = em.merge(codigoTipoAspiracao);
            }
            if (codigoInjecao != null) {
                codigoInjecao.getMotorCollection().add(motor);
                codigoInjecao = em.merge(codigoInjecao);
            }
            for (Veiculo veiculoCollectionVeiculo : motor.getVeiculoCollection()) {
                Motor oldCodigoMotorOfVeiculoCollectionVeiculo = veiculoCollectionVeiculo.getCodigoMotor();
                veiculoCollectionVeiculo.setCodigoMotor(motor);
                veiculoCollectionVeiculo = em.merge(veiculoCollectionVeiculo);
                if (oldCodigoMotorOfVeiculoCollectionVeiculo != null) {
                    oldCodigoMotorOfVeiculoCollectionVeiculo.getVeiculoCollection().remove(veiculoCollectionVeiculo);
                    oldCodigoMotorOfVeiculoCollectionVeiculo = em.merge(oldCodigoMotorOfVeiculoCollectionVeiculo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Motor motor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Motor persistentMotor = em.find(Motor.class, motor.getCodigoMotor());
            TipoMotor codigoTipoMotorOld = persistentMotor.getCodigoTipoMotor();
            TipoMotor codigoTipoMotorNew = motor.getCodigoTipoMotor();
            TipoCombustivel codigoTipoCombustivelOld = persistentMotor.getCodigoTipoCombustivel();
            TipoCombustivel codigoTipoCombustivelNew = motor.getCodigoTipoCombustivel();
            TipoCabecote codigoTipoCabecoteOld = persistentMotor.getCodigoTipoCabecote();
            TipoCabecote codigoTipoCabecoteNew = motor.getCodigoTipoCabecote();
            TipoAspiracao codigoTipoAspiracaoOld = persistentMotor.getCodigoTipoAspiracao();
            TipoAspiracao codigoTipoAspiracaoNew = motor.getCodigoTipoAspiracao();
            Injecao codigoInjecaoOld = persistentMotor.getCodigoInjecao();
            Injecao codigoInjecaoNew = motor.getCodigoInjecao();
            Collection<Veiculo> veiculoCollectionOld = persistentMotor.getVeiculoCollection();
            Collection<Veiculo> veiculoCollectionNew = motor.getVeiculoCollection();
            if (codigoTipoMotorNew != null) {
                codigoTipoMotorNew = em.getReference(codigoTipoMotorNew.getClass(), codigoTipoMotorNew.getCodigoTipoMotor());
                motor.setCodigoTipoMotor(codigoTipoMotorNew);
            }
            if (codigoTipoCombustivelNew != null) {
                codigoTipoCombustivelNew = em.getReference(codigoTipoCombustivelNew.getClass(), codigoTipoCombustivelNew.getCodigoTipoCombustivel());
                motor.setCodigoTipoCombustivel(codigoTipoCombustivelNew);
            }
            if (codigoTipoCabecoteNew != null) {
                codigoTipoCabecoteNew = em.getReference(codigoTipoCabecoteNew.getClass(), codigoTipoCabecoteNew.getCodigoTipoCabecote());
                motor.setCodigoTipoCabecote(codigoTipoCabecoteNew);
            }
            if (codigoTipoAspiracaoNew != null) {
                codigoTipoAspiracaoNew = em.getReference(codigoTipoAspiracaoNew.getClass(), codigoTipoAspiracaoNew.getCodigoTipoAspiracao());
                motor.setCodigoTipoAspiracao(codigoTipoAspiracaoNew);
            }
            if (codigoInjecaoNew != null) {
                codigoInjecaoNew = em.getReference(codigoInjecaoNew.getClass(), codigoInjecaoNew.getCodigoInjecao());
                motor.setCodigoInjecao(codigoInjecaoNew);
            }
            Collection<Veiculo> attachedVeiculoCollectionNew = new ArrayList<Veiculo>();
            for (Veiculo veiculoCollectionNewVeiculoToAttach : veiculoCollectionNew) {
                veiculoCollectionNewVeiculoToAttach = em.getReference(veiculoCollectionNewVeiculoToAttach.getClass(), veiculoCollectionNewVeiculoToAttach.getCodigoVeiculo());
                attachedVeiculoCollectionNew.add(veiculoCollectionNewVeiculoToAttach);
            }
            veiculoCollectionNew = attachedVeiculoCollectionNew;
            motor.setVeiculoCollection(veiculoCollectionNew);
            motor = em.merge(motor);
            if (codigoTipoMotorOld != null && !codigoTipoMotorOld.equals(codigoTipoMotorNew)) {
                codigoTipoMotorOld.getMotorCollection().remove(motor);
                codigoTipoMotorOld = em.merge(codigoTipoMotorOld);
            }
            if (codigoTipoMotorNew != null && !codigoTipoMotorNew.equals(codigoTipoMotorOld)) {
                codigoTipoMotorNew.getMotorCollection().add(motor);
                codigoTipoMotorNew = em.merge(codigoTipoMotorNew);
            }
            if (codigoTipoCombustivelOld != null && !codigoTipoCombustivelOld.equals(codigoTipoCombustivelNew)) {
                codigoTipoCombustivelOld.getMotorCollection().remove(motor);
                codigoTipoCombustivelOld = em.merge(codigoTipoCombustivelOld);
            }
            if (codigoTipoCombustivelNew != null && !codigoTipoCombustivelNew.equals(codigoTipoCombustivelOld)) {
                codigoTipoCombustivelNew.getMotorCollection().add(motor);
                codigoTipoCombustivelNew = em.merge(codigoTipoCombustivelNew);
            }
            if (codigoTipoCabecoteOld != null && !codigoTipoCabecoteOld.equals(codigoTipoCabecoteNew)) {
                codigoTipoCabecoteOld.getMotorCollection().remove(motor);
                codigoTipoCabecoteOld = em.merge(codigoTipoCabecoteOld);
            }
            if (codigoTipoCabecoteNew != null && !codigoTipoCabecoteNew.equals(codigoTipoCabecoteOld)) {
                codigoTipoCabecoteNew.getMotorCollection().add(motor);
                codigoTipoCabecoteNew = em.merge(codigoTipoCabecoteNew);
            }
            if (codigoTipoAspiracaoOld != null && !codigoTipoAspiracaoOld.equals(codigoTipoAspiracaoNew)) {
                codigoTipoAspiracaoOld.getMotorCollection().remove(motor);
                codigoTipoAspiracaoOld = em.merge(codigoTipoAspiracaoOld);
            }
            if (codigoTipoAspiracaoNew != null && !codigoTipoAspiracaoNew.equals(codigoTipoAspiracaoOld)) {
                codigoTipoAspiracaoNew.getMotorCollection().add(motor);
                codigoTipoAspiracaoNew = em.merge(codigoTipoAspiracaoNew);
            }
            if (codigoInjecaoOld != null && !codigoInjecaoOld.equals(codigoInjecaoNew)) {
                codigoInjecaoOld.getMotorCollection().remove(motor);
                codigoInjecaoOld = em.merge(codigoInjecaoOld);
            }
            if (codigoInjecaoNew != null && !codigoInjecaoNew.equals(codigoInjecaoOld)) {
                codigoInjecaoNew.getMotorCollection().add(motor);
                codigoInjecaoNew = em.merge(codigoInjecaoNew);
            }
            for (Veiculo veiculoCollectionOldVeiculo : veiculoCollectionOld) {
                if (!veiculoCollectionNew.contains(veiculoCollectionOldVeiculo)) {
                    veiculoCollectionOldVeiculo.setCodigoMotor(null);
                    veiculoCollectionOldVeiculo = em.merge(veiculoCollectionOldVeiculo);
                }
            }
            for (Veiculo veiculoCollectionNewVeiculo : veiculoCollectionNew) {
                if (!veiculoCollectionOld.contains(veiculoCollectionNewVeiculo)) {
                    Motor oldCodigoMotorOfVeiculoCollectionNewVeiculo = veiculoCollectionNewVeiculo.getCodigoMotor();
                    veiculoCollectionNewVeiculo.setCodigoMotor(motor);
                    veiculoCollectionNewVeiculo = em.merge(veiculoCollectionNewVeiculo);
                    if (oldCodigoMotorOfVeiculoCollectionNewVeiculo != null && !oldCodigoMotorOfVeiculoCollectionNewVeiculo.equals(motor)) {
                        oldCodigoMotorOfVeiculoCollectionNewVeiculo.getVeiculoCollection().remove(veiculoCollectionNewVeiculo);
                        oldCodigoMotorOfVeiculoCollectionNewVeiculo = em.merge(oldCodigoMotorOfVeiculoCollectionNewVeiculo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = motor.getCodigoMotor();
                if (findMotor(id) == null) {
                    throw new NonexistentEntityException("The motor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Motor motor;
            try {
                motor = em.getReference(Motor.class, id);
                motor.getCodigoMotor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The motor with id " + id + " no longer exists.", enfe);
            }
            TipoMotor codigoTipoMotor = motor.getCodigoTipoMotor();
            if (codigoTipoMotor != null) {
                codigoTipoMotor.getMotorCollection().remove(motor);
                codigoTipoMotor = em.merge(codigoTipoMotor);
            }
            TipoCombustivel codigoTipoCombustivel = motor.getCodigoTipoCombustivel();
            if (codigoTipoCombustivel != null) {
                codigoTipoCombustivel.getMotorCollection().remove(motor);
                codigoTipoCombustivel = em.merge(codigoTipoCombustivel);
            }
            TipoCabecote codigoTipoCabecote = motor.getCodigoTipoCabecote();
            if (codigoTipoCabecote != null) {
                codigoTipoCabecote.getMotorCollection().remove(motor);
                codigoTipoCabecote = em.merge(codigoTipoCabecote);
            }
            TipoAspiracao codigoTipoAspiracao = motor.getCodigoTipoAspiracao();
            if (codigoTipoAspiracao != null) {
                codigoTipoAspiracao.getMotorCollection().remove(motor);
                codigoTipoAspiracao = em.merge(codigoTipoAspiracao);
            }
            Injecao codigoInjecao = motor.getCodigoInjecao();
            if (codigoInjecao != null) {
                codigoInjecao.getMotorCollection().remove(motor);
                codigoInjecao = em.merge(codigoInjecao);
            }
            Collection<Veiculo> veiculoCollection = motor.getVeiculoCollection();
            for (Veiculo veiculoCollectionVeiculo : veiculoCollection) {
                veiculoCollectionVeiculo.setCodigoMotor(null);
                veiculoCollectionVeiculo = em.merge(veiculoCollectionVeiculo);
            }
            em.remove(motor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Motor> findMotorEntities() {
        return findMotorEntities(true, -1, -1);
    }

    public List<Motor> findMotorEntities(int maxResults, int firstResult) {
        return findMotorEntities(false, maxResults, firstResult);
    }

    private List<Motor> findMotorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Motor.class));
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

    public Motor findMotor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Motor.class, id);
        } finally {
            em.close();
        }
    }

    public int getMotorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Motor> rt = cq.from(Motor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
