/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.controller;

import br.com.autodb.model.controller.exceptions.NonexistentEntityException;
import br.com.autodb.model.dao.ConfiguracaoFreio;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.autodb.model.dao.TipoFreio;
import br.com.autodb.model.dao.TipoAssistenciaFreio;
import br.com.autodb.model.dao.FreioABS;
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
public class ConfiguracaoFreioJpaController1 implements Serializable {

    public ConfiguracaoFreioJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfiguracaoFreio configuracaoFreio) {
        if (configuracaoFreio.getVeiculoCollection() == null) {
            configuracaoFreio.setVeiculoCollection(new ArrayList<Veiculo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoFreio codigoTipoFreioTraseiro = configuracaoFreio.getCodigoTipoFreioTraseiro();
            if (codigoTipoFreioTraseiro != null) {
                codigoTipoFreioTraseiro = em.getReference(codigoTipoFreioTraseiro.getClass(), codigoTipoFreioTraseiro.getCodigoTipoFreio());
                configuracaoFreio.setCodigoTipoFreioTraseiro(codigoTipoFreioTraseiro);
            }
            TipoFreio codigoTipoFreioDianteiro = configuracaoFreio.getCodigoTipoFreioDianteiro();
            if (codigoTipoFreioDianteiro != null) {
                codigoTipoFreioDianteiro = em.getReference(codigoTipoFreioDianteiro.getClass(), codigoTipoFreioDianteiro.getCodigoTipoFreio());
                configuracaoFreio.setCodigoTipoFreioDianteiro(codigoTipoFreioDianteiro);
            }
            TipoAssistenciaFreio codigoTipoAssistenciaFreio = configuracaoFreio.getCodigoTipoAssistenciaFreio();
            if (codigoTipoAssistenciaFreio != null) {
                codigoTipoAssistenciaFreio = em.getReference(codigoTipoAssistenciaFreio.getClass(), codigoTipoAssistenciaFreio.getCodigoTipoAssistenciaFreio());
                configuracaoFreio.setCodigoTipoAssistenciaFreio(codigoTipoAssistenciaFreio);
            }
            FreioABS codigoFreioABS = configuracaoFreio.getCodigoFreioABS();
            if (codigoFreioABS != null) {
                codigoFreioABS = em.getReference(codigoFreioABS.getClass(), codigoFreioABS.getCodigoFreioABS());
                configuracaoFreio.setCodigoFreioABS(codigoFreioABS);
            }
            Collection<Veiculo> attachedVeiculoCollection = new ArrayList<Veiculo>();
            for (Veiculo veiculoCollectionVeiculoToAttach : configuracaoFreio.getVeiculoCollection()) {
                veiculoCollectionVeiculoToAttach = em.getReference(veiculoCollectionVeiculoToAttach.getClass(), veiculoCollectionVeiculoToAttach.getCodigoVeiculo());
                attachedVeiculoCollection.add(veiculoCollectionVeiculoToAttach);
            }
            configuracaoFreio.setVeiculoCollection(attachedVeiculoCollection);
            em.persist(configuracaoFreio);
            if (codigoTipoFreioTraseiro != null) {
                codigoTipoFreioTraseiro.getConfiguracaoFreioCollection().add(configuracaoFreio);
                codigoTipoFreioTraseiro = em.merge(codigoTipoFreioTraseiro);
            }
            if (codigoTipoFreioDianteiro != null) {
                codigoTipoFreioDianteiro.getConfiguracaoFreioCollection().add(configuracaoFreio);
                codigoTipoFreioDianteiro = em.merge(codigoTipoFreioDianteiro);
            }
            if (codigoTipoAssistenciaFreio != null) {
                codigoTipoAssistenciaFreio.getConfiguracaoFreioCollection().add(configuracaoFreio);
                codigoTipoAssistenciaFreio = em.merge(codigoTipoAssistenciaFreio);
            }
            if (codigoFreioABS != null) {
                codigoFreioABS.getConfiguracaoFreioCollection().add(configuracaoFreio);
                codigoFreioABS = em.merge(codigoFreioABS);
            }
            for (Veiculo veiculoCollectionVeiculo : configuracaoFreio.getVeiculoCollection()) {
                ConfiguracaoFreio oldCodigoConfiguracaoFreioOfVeiculoCollectionVeiculo = veiculoCollectionVeiculo.getCodigoConfiguracaoFreio();
                veiculoCollectionVeiculo.setCodigoConfiguracaoFreio(configuracaoFreio);
                veiculoCollectionVeiculo = em.merge(veiculoCollectionVeiculo);
                if (oldCodigoConfiguracaoFreioOfVeiculoCollectionVeiculo != null) {
                    oldCodigoConfiguracaoFreioOfVeiculoCollectionVeiculo.getVeiculoCollection().remove(veiculoCollectionVeiculo);
                    oldCodigoConfiguracaoFreioOfVeiculoCollectionVeiculo = em.merge(oldCodigoConfiguracaoFreioOfVeiculoCollectionVeiculo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfiguracaoFreio configuracaoFreio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ConfiguracaoFreio persistentConfiguracaoFreio = em.find(ConfiguracaoFreio.class, configuracaoFreio.getCodigoConfiguracaoFreio());
            TipoFreio codigoTipoFreioTraseiroOld = persistentConfiguracaoFreio.getCodigoTipoFreioTraseiro();
            TipoFreio codigoTipoFreioTraseiroNew = configuracaoFreio.getCodigoTipoFreioTraseiro();
            TipoFreio codigoTipoFreioDianteiroOld = persistentConfiguracaoFreio.getCodigoTipoFreioDianteiro();
            TipoFreio codigoTipoFreioDianteiroNew = configuracaoFreio.getCodigoTipoFreioDianteiro();
            TipoAssistenciaFreio codigoTipoAssistenciaFreioOld = persistentConfiguracaoFreio.getCodigoTipoAssistenciaFreio();
            TipoAssistenciaFreio codigoTipoAssistenciaFreioNew = configuracaoFreio.getCodigoTipoAssistenciaFreio();
            FreioABS codigoFreioABSOld = persistentConfiguracaoFreio.getCodigoFreioABS();
            FreioABS codigoFreioABSNew = configuracaoFreio.getCodigoFreioABS();
            Collection<Veiculo> veiculoCollectionOld = persistentConfiguracaoFreio.getVeiculoCollection();
            Collection<Veiculo> veiculoCollectionNew = configuracaoFreio.getVeiculoCollection();
            if (codigoTipoFreioTraseiroNew != null) {
                codigoTipoFreioTraseiroNew = em.getReference(codigoTipoFreioTraseiroNew.getClass(), codigoTipoFreioTraseiroNew.getCodigoTipoFreio());
                configuracaoFreio.setCodigoTipoFreioTraseiro(codigoTipoFreioTraseiroNew);
            }
            if (codigoTipoFreioDianteiroNew != null) {
                codigoTipoFreioDianteiroNew = em.getReference(codigoTipoFreioDianteiroNew.getClass(), codigoTipoFreioDianteiroNew.getCodigoTipoFreio());
                configuracaoFreio.setCodigoTipoFreioDianteiro(codigoTipoFreioDianteiroNew);
            }
            if (codigoTipoAssistenciaFreioNew != null) {
                codigoTipoAssistenciaFreioNew = em.getReference(codigoTipoAssistenciaFreioNew.getClass(), codigoTipoAssistenciaFreioNew.getCodigoTipoAssistenciaFreio());
                configuracaoFreio.setCodigoTipoAssistenciaFreio(codigoTipoAssistenciaFreioNew);
            }
            if (codigoFreioABSNew != null) {
                codigoFreioABSNew = em.getReference(codigoFreioABSNew.getClass(), codigoFreioABSNew.getCodigoFreioABS());
                configuracaoFreio.setCodigoFreioABS(codigoFreioABSNew);
            }
            Collection<Veiculo> attachedVeiculoCollectionNew = new ArrayList<Veiculo>();
            for (Veiculo veiculoCollectionNewVeiculoToAttach : veiculoCollectionNew) {
                veiculoCollectionNewVeiculoToAttach = em.getReference(veiculoCollectionNewVeiculoToAttach.getClass(), veiculoCollectionNewVeiculoToAttach.getCodigoVeiculo());
                attachedVeiculoCollectionNew.add(veiculoCollectionNewVeiculoToAttach);
            }
            veiculoCollectionNew = attachedVeiculoCollectionNew;
            configuracaoFreio.setVeiculoCollection(veiculoCollectionNew);
            configuracaoFreio = em.merge(configuracaoFreio);
            if (codigoTipoFreioTraseiroOld != null && !codigoTipoFreioTraseiroOld.equals(codigoTipoFreioTraseiroNew)) {
                codigoTipoFreioTraseiroOld.getConfiguracaoFreioCollection().remove(configuracaoFreio);
                codigoTipoFreioTraseiroOld = em.merge(codigoTipoFreioTraseiroOld);
            }
            if (codigoTipoFreioTraseiroNew != null && !codigoTipoFreioTraseiroNew.equals(codigoTipoFreioTraseiroOld)) {
                codigoTipoFreioTraseiroNew.getConfiguracaoFreioCollection().add(configuracaoFreio);
                codigoTipoFreioTraseiroNew = em.merge(codigoTipoFreioTraseiroNew);
            }
            if (codigoTipoFreioDianteiroOld != null && !codigoTipoFreioDianteiroOld.equals(codigoTipoFreioDianteiroNew)) {
                codigoTipoFreioDianteiroOld.getConfiguracaoFreioCollection().remove(configuracaoFreio);
                codigoTipoFreioDianteiroOld = em.merge(codigoTipoFreioDianteiroOld);
            }
            if (codigoTipoFreioDianteiroNew != null && !codigoTipoFreioDianteiroNew.equals(codigoTipoFreioDianteiroOld)) {
                codigoTipoFreioDianteiroNew.getConfiguracaoFreioCollection().add(configuracaoFreio);
                codigoTipoFreioDianteiroNew = em.merge(codigoTipoFreioDianteiroNew);
            }
            if (codigoTipoAssistenciaFreioOld != null && !codigoTipoAssistenciaFreioOld.equals(codigoTipoAssistenciaFreioNew)) {
                codigoTipoAssistenciaFreioOld.getConfiguracaoFreioCollection().remove(configuracaoFreio);
                codigoTipoAssistenciaFreioOld = em.merge(codigoTipoAssistenciaFreioOld);
            }
            if (codigoTipoAssistenciaFreioNew != null && !codigoTipoAssistenciaFreioNew.equals(codigoTipoAssistenciaFreioOld)) {
                codigoTipoAssistenciaFreioNew.getConfiguracaoFreioCollection().add(configuracaoFreio);
                codigoTipoAssistenciaFreioNew = em.merge(codigoTipoAssistenciaFreioNew);
            }
            if (codigoFreioABSOld != null && !codigoFreioABSOld.equals(codigoFreioABSNew)) {
                codigoFreioABSOld.getConfiguracaoFreioCollection().remove(configuracaoFreio);
                codigoFreioABSOld = em.merge(codigoFreioABSOld);
            }
            if (codigoFreioABSNew != null && !codigoFreioABSNew.equals(codigoFreioABSOld)) {
                codigoFreioABSNew.getConfiguracaoFreioCollection().add(configuracaoFreio);
                codigoFreioABSNew = em.merge(codigoFreioABSNew);
            }
            for (Veiculo veiculoCollectionOldVeiculo : veiculoCollectionOld) {
                if (!veiculoCollectionNew.contains(veiculoCollectionOldVeiculo)) {
                    veiculoCollectionOldVeiculo.setCodigoConfiguracaoFreio(null);
                    veiculoCollectionOldVeiculo = em.merge(veiculoCollectionOldVeiculo);
                }
            }
            for (Veiculo veiculoCollectionNewVeiculo : veiculoCollectionNew) {
                if (!veiculoCollectionOld.contains(veiculoCollectionNewVeiculo)) {
                    ConfiguracaoFreio oldCodigoConfiguracaoFreioOfVeiculoCollectionNewVeiculo = veiculoCollectionNewVeiculo.getCodigoConfiguracaoFreio();
                    veiculoCollectionNewVeiculo.setCodigoConfiguracaoFreio(configuracaoFreio);
                    veiculoCollectionNewVeiculo = em.merge(veiculoCollectionNewVeiculo);
                    if (oldCodigoConfiguracaoFreioOfVeiculoCollectionNewVeiculo != null && !oldCodigoConfiguracaoFreioOfVeiculoCollectionNewVeiculo.equals(configuracaoFreio)) {
                        oldCodigoConfiguracaoFreioOfVeiculoCollectionNewVeiculo.getVeiculoCollection().remove(veiculoCollectionNewVeiculo);
                        oldCodigoConfiguracaoFreioOfVeiculoCollectionNewVeiculo = em.merge(oldCodigoConfiguracaoFreioOfVeiculoCollectionNewVeiculo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = configuracaoFreio.getCodigoConfiguracaoFreio();
                if (findConfiguracaoFreio(id) == null) {
                    throw new NonexistentEntityException("The configuracaoFreio with id " + id + " no longer exists.");
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
            ConfiguracaoFreio configuracaoFreio;
            try {
                configuracaoFreio = em.getReference(ConfiguracaoFreio.class, id);
                configuracaoFreio.getCodigoConfiguracaoFreio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configuracaoFreio with id " + id + " no longer exists.", enfe);
            }
            TipoFreio codigoTipoFreioTraseiro = configuracaoFreio.getCodigoTipoFreioTraseiro();
            if (codigoTipoFreioTraseiro != null) {
                codigoTipoFreioTraseiro.getConfiguracaoFreioCollection().remove(configuracaoFreio);
                codigoTipoFreioTraseiro = em.merge(codigoTipoFreioTraseiro);
            }
            TipoFreio codigoTipoFreioDianteiro = configuracaoFreio.getCodigoTipoFreioDianteiro();
            if (codigoTipoFreioDianteiro != null) {
                codigoTipoFreioDianteiro.getConfiguracaoFreioCollection().remove(configuracaoFreio);
                codigoTipoFreioDianteiro = em.merge(codigoTipoFreioDianteiro);
            }
            TipoAssistenciaFreio codigoTipoAssistenciaFreio = configuracaoFreio.getCodigoTipoAssistenciaFreio();
            if (codigoTipoAssistenciaFreio != null) {
                codigoTipoAssistenciaFreio.getConfiguracaoFreioCollection().remove(configuracaoFreio);
                codigoTipoAssistenciaFreio = em.merge(codigoTipoAssistenciaFreio);
            }
            FreioABS codigoFreioABS = configuracaoFreio.getCodigoFreioABS();
            if (codigoFreioABS != null) {
                codigoFreioABS.getConfiguracaoFreioCollection().remove(configuracaoFreio);
                codigoFreioABS = em.merge(codigoFreioABS);
            }
            Collection<Veiculo> veiculoCollection = configuracaoFreio.getVeiculoCollection();
            for (Veiculo veiculoCollectionVeiculo : veiculoCollection) {
                veiculoCollectionVeiculo.setCodigoConfiguracaoFreio(null);
                veiculoCollectionVeiculo = em.merge(veiculoCollectionVeiculo);
            }
            em.remove(configuracaoFreio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ConfiguracaoFreio> findConfiguracaoFreioEntities() {
        return findConfiguracaoFreioEntities(true, -1, -1);
    }

    public List<ConfiguracaoFreio> findConfiguracaoFreioEntities(int maxResults, int firstResult) {
        return findConfiguracaoFreioEntities(false, maxResults, firstResult);
    }

    private List<ConfiguracaoFreio> findConfiguracaoFreioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfiguracaoFreio.class));
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

    public ConfiguracaoFreio findConfiguracaoFreio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfiguracaoFreio.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfiguracaoFreioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfiguracaoFreio> rt = cq.from(ConfiguracaoFreio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
