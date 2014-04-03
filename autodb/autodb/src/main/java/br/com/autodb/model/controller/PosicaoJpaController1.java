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
import br.com.autodb.model.dao.Aplicacao;
import br.com.autodb.model.dao.Posicao;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class PosicaoJpaController1 implements Serializable {

    public PosicaoJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Posicao posicao) {
        if (posicao.getAplicacaoCollection() == null) {
            posicao.setAplicacaoCollection(new ArrayList<Aplicacao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Aplicacao> attachedAplicacaoCollection = new ArrayList<Aplicacao>();
            for (Aplicacao aplicacaoCollectionAplicacaoToAttach : posicao.getAplicacaoCollection()) {
                aplicacaoCollectionAplicacaoToAttach = em.getReference(aplicacaoCollectionAplicacaoToAttach.getClass(), aplicacaoCollectionAplicacaoToAttach.getCodigoAplicacao());
                attachedAplicacaoCollection.add(aplicacaoCollectionAplicacaoToAttach);
            }
            posicao.setAplicacaoCollection(attachedAplicacaoCollection);
            em.persist(posicao);
            for (Aplicacao aplicacaoCollectionAplicacao : posicao.getAplicacaoCollection()) {
                Posicao oldCodigoPosicaoOfAplicacaoCollectionAplicacao = aplicacaoCollectionAplicacao.getCodigoPosicao();
                aplicacaoCollectionAplicacao.setCodigoPosicao(posicao);
                aplicacaoCollectionAplicacao = em.merge(aplicacaoCollectionAplicacao);
                if (oldCodigoPosicaoOfAplicacaoCollectionAplicacao != null) {
                    oldCodigoPosicaoOfAplicacaoCollectionAplicacao.getAplicacaoCollection().remove(aplicacaoCollectionAplicacao);
                    oldCodigoPosicaoOfAplicacaoCollectionAplicacao = em.merge(oldCodigoPosicaoOfAplicacaoCollectionAplicacao);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Posicao posicao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Posicao persistentPosicao = em.find(Posicao.class, posicao.getCodigoPosicao());
            Collection<Aplicacao> aplicacaoCollectionOld = persistentPosicao.getAplicacaoCollection();
            Collection<Aplicacao> aplicacaoCollectionNew = posicao.getAplicacaoCollection();
            Collection<Aplicacao> attachedAplicacaoCollectionNew = new ArrayList<Aplicacao>();
            for (Aplicacao aplicacaoCollectionNewAplicacaoToAttach : aplicacaoCollectionNew) {
                aplicacaoCollectionNewAplicacaoToAttach = em.getReference(aplicacaoCollectionNewAplicacaoToAttach.getClass(), aplicacaoCollectionNewAplicacaoToAttach.getCodigoAplicacao());
                attachedAplicacaoCollectionNew.add(aplicacaoCollectionNewAplicacaoToAttach);
            }
            aplicacaoCollectionNew = attachedAplicacaoCollectionNew;
            posicao.setAplicacaoCollection(aplicacaoCollectionNew);
            posicao = em.merge(posicao);
            for (Aplicacao aplicacaoCollectionOldAplicacao : aplicacaoCollectionOld) {
                if (!aplicacaoCollectionNew.contains(aplicacaoCollectionOldAplicacao)) {
                    aplicacaoCollectionOldAplicacao.setCodigoPosicao(null);
                    aplicacaoCollectionOldAplicacao = em.merge(aplicacaoCollectionOldAplicacao);
                }
            }
            for (Aplicacao aplicacaoCollectionNewAplicacao : aplicacaoCollectionNew) {
                if (!aplicacaoCollectionOld.contains(aplicacaoCollectionNewAplicacao)) {
                    Posicao oldCodigoPosicaoOfAplicacaoCollectionNewAplicacao = aplicacaoCollectionNewAplicacao.getCodigoPosicao();
                    aplicacaoCollectionNewAplicacao.setCodigoPosicao(posicao);
                    aplicacaoCollectionNewAplicacao = em.merge(aplicacaoCollectionNewAplicacao);
                    if (oldCodigoPosicaoOfAplicacaoCollectionNewAplicacao != null && !oldCodigoPosicaoOfAplicacaoCollectionNewAplicacao.equals(posicao)) {
                        oldCodigoPosicaoOfAplicacaoCollectionNewAplicacao.getAplicacaoCollection().remove(aplicacaoCollectionNewAplicacao);
                        oldCodigoPosicaoOfAplicacaoCollectionNewAplicacao = em.merge(oldCodigoPosicaoOfAplicacaoCollectionNewAplicacao);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = posicao.getCodigoPosicao();
                if (findPosicao(id) == null) {
                    throw new NonexistentEntityException("The posicao with id " + id + " no longer exists.");
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
            Posicao posicao;
            try {
                posicao = em.getReference(Posicao.class, id);
                posicao.getCodigoPosicao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The posicao with id " + id + " no longer exists.", enfe);
            }
            Collection<Aplicacao> aplicacaoCollection = posicao.getAplicacaoCollection();
            for (Aplicacao aplicacaoCollectionAplicacao : aplicacaoCollection) {
                aplicacaoCollectionAplicacao.setCodigoPosicao(null);
                aplicacaoCollectionAplicacao = em.merge(aplicacaoCollectionAplicacao);
            }
            em.remove(posicao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Posicao> findPosicaoEntities() {
        return findPosicaoEntities(true, -1, -1);
    }

    public List<Posicao> findPosicaoEntities(int maxResults, int firstResult) {
        return findPosicaoEntities(false, maxResults, firstResult);
    }

    private List<Posicao> findPosicaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Posicao.class));
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

    public Posicao findPosicao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Posicao.class, id);
        } finally {
            em.close();
        }
    }

    public int getPosicaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Posicao> rt = cq.from(Posicao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
