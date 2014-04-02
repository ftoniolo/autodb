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
import br.com.autodb.model.dao.QualificadorAplicacao;
import java.util.ArrayList;
import java.util.Collection;
import br.com.autodb.model.dao.QualificadorProduto;
import br.com.autodb.model.dao.UnidadeMedida;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class UnidadeMedidaJpaController implements Serializable {

    public UnidadeMedidaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UnidadeMedida unidadeMedida) {
        if (unidadeMedida.getQualificadorAplicacaoCollection() == null) {
            unidadeMedida.setQualificadorAplicacaoCollection(new ArrayList<QualificadorAplicacao>());
        }
        if (unidadeMedida.getQualificadorProdutoCollection() == null) {
            unidadeMedida.setQualificadorProdutoCollection(new ArrayList<QualificadorProduto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<QualificadorAplicacao> attachedQualificadorAplicacaoCollection = new ArrayList<QualificadorAplicacao>();
            for (QualificadorAplicacao qualificadorAplicacaoCollectionQualificadorAplicacaoToAttach : unidadeMedida.getQualificadorAplicacaoCollection()) {
                qualificadorAplicacaoCollectionQualificadorAplicacaoToAttach = em.getReference(qualificadorAplicacaoCollectionQualificadorAplicacaoToAttach.getClass(), qualificadorAplicacaoCollectionQualificadorAplicacaoToAttach.getCodigoQualificadorAplicacao());
                attachedQualificadorAplicacaoCollection.add(qualificadorAplicacaoCollectionQualificadorAplicacaoToAttach);
            }
            unidadeMedida.setQualificadorAplicacaoCollection(attachedQualificadorAplicacaoCollection);
            Collection<QualificadorProduto> attachedQualificadorProdutoCollection = new ArrayList<QualificadorProduto>();
            for (QualificadorProduto qualificadorProdutoCollectionQualificadorProdutoToAttach : unidadeMedida.getQualificadorProdutoCollection()) {
                qualificadorProdutoCollectionQualificadorProdutoToAttach = em.getReference(qualificadorProdutoCollectionQualificadorProdutoToAttach.getClass(), qualificadorProdutoCollectionQualificadorProdutoToAttach.getCodigoQualificadorProduto());
                attachedQualificadorProdutoCollection.add(qualificadorProdutoCollectionQualificadorProdutoToAttach);
            }
            unidadeMedida.setQualificadorProdutoCollection(attachedQualificadorProdutoCollection);
            em.persist(unidadeMedida);
            for (QualificadorAplicacao qualificadorAplicacaoCollectionQualificadorAplicacao : unidadeMedida.getQualificadorAplicacaoCollection()) {
                UnidadeMedida oldCodigoUnidadeMedidaOfQualificadorAplicacaoCollectionQualificadorAplicacao = qualificadorAplicacaoCollectionQualificadorAplicacao.getCodigoUnidadeMedida();
                qualificadorAplicacaoCollectionQualificadorAplicacao.setCodigoUnidadeMedida(unidadeMedida);
                qualificadorAplicacaoCollectionQualificadorAplicacao = em.merge(qualificadorAplicacaoCollectionQualificadorAplicacao);
                if (oldCodigoUnidadeMedidaOfQualificadorAplicacaoCollectionQualificadorAplicacao != null) {
                    oldCodigoUnidadeMedidaOfQualificadorAplicacaoCollectionQualificadorAplicacao.getQualificadorAplicacaoCollection().remove(qualificadorAplicacaoCollectionQualificadorAplicacao);
                    oldCodigoUnidadeMedidaOfQualificadorAplicacaoCollectionQualificadorAplicacao = em.merge(oldCodigoUnidadeMedidaOfQualificadorAplicacaoCollectionQualificadorAplicacao);
                }
            }
            for (QualificadorProduto qualificadorProdutoCollectionQualificadorProduto : unidadeMedida.getQualificadorProdutoCollection()) {
                UnidadeMedida oldCodigoUnidadeMedidaOfQualificadorProdutoCollectionQualificadorProduto = qualificadorProdutoCollectionQualificadorProduto.getCodigoUnidadeMedida();
                qualificadorProdutoCollectionQualificadorProduto.setCodigoUnidadeMedida(unidadeMedida);
                qualificadorProdutoCollectionQualificadorProduto = em.merge(qualificadorProdutoCollectionQualificadorProduto);
                if (oldCodigoUnidadeMedidaOfQualificadorProdutoCollectionQualificadorProduto != null) {
                    oldCodigoUnidadeMedidaOfQualificadorProdutoCollectionQualificadorProduto.getQualificadorProdutoCollection().remove(qualificadorProdutoCollectionQualificadorProduto);
                    oldCodigoUnidadeMedidaOfQualificadorProdutoCollectionQualificadorProduto = em.merge(oldCodigoUnidadeMedidaOfQualificadorProdutoCollectionQualificadorProduto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UnidadeMedida unidadeMedida) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UnidadeMedida persistentUnidadeMedida = em.find(UnidadeMedida.class, unidadeMedida.getCodigoUnidadeMedida());
            Collection<QualificadorAplicacao> qualificadorAplicacaoCollectionOld = persistentUnidadeMedida.getQualificadorAplicacaoCollection();
            Collection<QualificadorAplicacao> qualificadorAplicacaoCollectionNew = unidadeMedida.getQualificadorAplicacaoCollection();
            Collection<QualificadorProduto> qualificadorProdutoCollectionOld = persistentUnidadeMedida.getQualificadorProdutoCollection();
            Collection<QualificadorProduto> qualificadorProdutoCollectionNew = unidadeMedida.getQualificadorProdutoCollection();
            Collection<QualificadorAplicacao> attachedQualificadorAplicacaoCollectionNew = new ArrayList<QualificadorAplicacao>();
            for (QualificadorAplicacao qualificadorAplicacaoCollectionNewQualificadorAplicacaoToAttach : qualificadorAplicacaoCollectionNew) {
                qualificadorAplicacaoCollectionNewQualificadorAplicacaoToAttach = em.getReference(qualificadorAplicacaoCollectionNewQualificadorAplicacaoToAttach.getClass(), qualificadorAplicacaoCollectionNewQualificadorAplicacaoToAttach.getCodigoQualificadorAplicacao());
                attachedQualificadorAplicacaoCollectionNew.add(qualificadorAplicacaoCollectionNewQualificadorAplicacaoToAttach);
            }
            qualificadorAplicacaoCollectionNew = attachedQualificadorAplicacaoCollectionNew;
            unidadeMedida.setQualificadorAplicacaoCollection(qualificadorAplicacaoCollectionNew);
            Collection<QualificadorProduto> attachedQualificadorProdutoCollectionNew = new ArrayList<QualificadorProduto>();
            for (QualificadorProduto qualificadorProdutoCollectionNewQualificadorProdutoToAttach : qualificadorProdutoCollectionNew) {
                qualificadorProdutoCollectionNewQualificadorProdutoToAttach = em.getReference(qualificadorProdutoCollectionNewQualificadorProdutoToAttach.getClass(), qualificadorProdutoCollectionNewQualificadorProdutoToAttach.getCodigoQualificadorProduto());
                attachedQualificadorProdutoCollectionNew.add(qualificadorProdutoCollectionNewQualificadorProdutoToAttach);
            }
            qualificadorProdutoCollectionNew = attachedQualificadorProdutoCollectionNew;
            unidadeMedida.setQualificadorProdutoCollection(qualificadorProdutoCollectionNew);
            unidadeMedida = em.merge(unidadeMedida);
            for (QualificadorAplicacao qualificadorAplicacaoCollectionOldQualificadorAplicacao : qualificadorAplicacaoCollectionOld) {
                if (!qualificadorAplicacaoCollectionNew.contains(qualificadorAplicacaoCollectionOldQualificadorAplicacao)) {
                    qualificadorAplicacaoCollectionOldQualificadorAplicacao.setCodigoUnidadeMedida(null);
                    qualificadorAplicacaoCollectionOldQualificadorAplicacao = em.merge(qualificadorAplicacaoCollectionOldQualificadorAplicacao);
                }
            }
            for (QualificadorAplicacao qualificadorAplicacaoCollectionNewQualificadorAplicacao : qualificadorAplicacaoCollectionNew) {
                if (!qualificadorAplicacaoCollectionOld.contains(qualificadorAplicacaoCollectionNewQualificadorAplicacao)) {
                    UnidadeMedida oldCodigoUnidadeMedidaOfQualificadorAplicacaoCollectionNewQualificadorAplicacao = qualificadorAplicacaoCollectionNewQualificadorAplicacao.getCodigoUnidadeMedida();
                    qualificadorAplicacaoCollectionNewQualificadorAplicacao.setCodigoUnidadeMedida(unidadeMedida);
                    qualificadorAplicacaoCollectionNewQualificadorAplicacao = em.merge(qualificadorAplicacaoCollectionNewQualificadorAplicacao);
                    if (oldCodigoUnidadeMedidaOfQualificadorAplicacaoCollectionNewQualificadorAplicacao != null && !oldCodigoUnidadeMedidaOfQualificadorAplicacaoCollectionNewQualificadorAplicacao.equals(unidadeMedida)) {
                        oldCodigoUnidadeMedidaOfQualificadorAplicacaoCollectionNewQualificadorAplicacao.getQualificadorAplicacaoCollection().remove(qualificadorAplicacaoCollectionNewQualificadorAplicacao);
                        oldCodigoUnidadeMedidaOfQualificadorAplicacaoCollectionNewQualificadorAplicacao = em.merge(oldCodigoUnidadeMedidaOfQualificadorAplicacaoCollectionNewQualificadorAplicacao);
                    }
                }
            }
            for (QualificadorProduto qualificadorProdutoCollectionOldQualificadorProduto : qualificadorProdutoCollectionOld) {
                if (!qualificadorProdutoCollectionNew.contains(qualificadorProdutoCollectionOldQualificadorProduto)) {
                    qualificadorProdutoCollectionOldQualificadorProduto.setCodigoUnidadeMedida(null);
                    qualificadorProdutoCollectionOldQualificadorProduto = em.merge(qualificadorProdutoCollectionOldQualificadorProduto);
                }
            }
            for (QualificadorProduto qualificadorProdutoCollectionNewQualificadorProduto : qualificadorProdutoCollectionNew) {
                if (!qualificadorProdutoCollectionOld.contains(qualificadorProdutoCollectionNewQualificadorProduto)) {
                    UnidadeMedida oldCodigoUnidadeMedidaOfQualificadorProdutoCollectionNewQualificadorProduto = qualificadorProdutoCollectionNewQualificadorProduto.getCodigoUnidadeMedida();
                    qualificadorProdutoCollectionNewQualificadorProduto.setCodigoUnidadeMedida(unidadeMedida);
                    qualificadorProdutoCollectionNewQualificadorProduto = em.merge(qualificadorProdutoCollectionNewQualificadorProduto);
                    if (oldCodigoUnidadeMedidaOfQualificadorProdutoCollectionNewQualificadorProduto != null && !oldCodigoUnidadeMedidaOfQualificadorProdutoCollectionNewQualificadorProduto.equals(unidadeMedida)) {
                        oldCodigoUnidadeMedidaOfQualificadorProdutoCollectionNewQualificadorProduto.getQualificadorProdutoCollection().remove(qualificadorProdutoCollectionNewQualificadorProduto);
                        oldCodigoUnidadeMedidaOfQualificadorProdutoCollectionNewQualificadorProduto = em.merge(oldCodigoUnidadeMedidaOfQualificadorProdutoCollectionNewQualificadorProduto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = unidadeMedida.getCodigoUnidadeMedida();
                if (findUnidadeMedida(id) == null) {
                    throw new NonexistentEntityException("The unidadeMedida with id " + id + " no longer exists.");
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
            UnidadeMedida unidadeMedida;
            try {
                unidadeMedida = em.getReference(UnidadeMedida.class, id);
                unidadeMedida.getCodigoUnidadeMedida();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The unidadeMedida with id " + id + " no longer exists.", enfe);
            }
            Collection<QualificadorAplicacao> qualificadorAplicacaoCollection = unidadeMedida.getQualificadorAplicacaoCollection();
            for (QualificadorAplicacao qualificadorAplicacaoCollectionQualificadorAplicacao : qualificadorAplicacaoCollection) {
                qualificadorAplicacaoCollectionQualificadorAplicacao.setCodigoUnidadeMedida(null);
                qualificadorAplicacaoCollectionQualificadorAplicacao = em.merge(qualificadorAplicacaoCollectionQualificadorAplicacao);
            }
            Collection<QualificadorProduto> qualificadorProdutoCollection = unidadeMedida.getQualificadorProdutoCollection();
            for (QualificadorProduto qualificadorProdutoCollectionQualificadorProduto : qualificadorProdutoCollection) {
                qualificadorProdutoCollectionQualificadorProduto.setCodigoUnidadeMedida(null);
                qualificadorProdutoCollectionQualificadorProduto = em.merge(qualificadorProdutoCollectionQualificadorProduto);
            }
            em.remove(unidadeMedida);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UnidadeMedida> findUnidadeMedidaEntities() {
        return findUnidadeMedidaEntities(true, -1, -1);
    }

    public List<UnidadeMedida> findUnidadeMedidaEntities(int maxResults, int firstResult) {
        return findUnidadeMedidaEntities(false, maxResults, firstResult);
    }

    private List<UnidadeMedida> findUnidadeMedidaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UnidadeMedida.class));
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

    public UnidadeMedida findUnidadeMedida(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UnidadeMedida.class, id);
        } finally {
            em.close();
        }
    }

    public int getUnidadeMedidaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UnidadeMedida> rt = cq.from(UnidadeMedida.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
