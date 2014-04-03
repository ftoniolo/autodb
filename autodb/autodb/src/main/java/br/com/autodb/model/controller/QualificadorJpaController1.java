/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.controller;

import br.com.autodb.model.controller.exceptions.IllegalOrphanException;
import br.com.autodb.model.controller.exceptions.NonexistentEntityException;
import br.com.autodb.model.dao.Qualificador;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.autodb.model.dao.QualificadorAplicacao;
import java.util.ArrayList;
import java.util.Collection;
import br.com.autodb.model.dao.QualificadorProduto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class QualificadorJpaController1 implements Serializable {

    public QualificadorJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Qualificador qualificador) {
        if (qualificador.getQualificadorAplicacaoCollection() == null) {
            qualificador.setQualificadorAplicacaoCollection(new ArrayList<QualificadorAplicacao>());
        }
        if (qualificador.getQualificadorProdutoCollection() == null) {
            qualificador.setQualificadorProdutoCollection(new ArrayList<QualificadorProduto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<QualificadorAplicacao> attachedQualificadorAplicacaoCollection = new ArrayList<QualificadorAplicacao>();
            for (QualificadorAplicacao qualificadorAplicacaoCollectionQualificadorAplicacaoToAttach : qualificador.getQualificadorAplicacaoCollection()) {
                qualificadorAplicacaoCollectionQualificadorAplicacaoToAttach = em.getReference(qualificadorAplicacaoCollectionQualificadorAplicacaoToAttach.getClass(), qualificadorAplicacaoCollectionQualificadorAplicacaoToAttach.getCodigoQualificadorAplicacao());
                attachedQualificadorAplicacaoCollection.add(qualificadorAplicacaoCollectionQualificadorAplicacaoToAttach);
            }
            qualificador.setQualificadorAplicacaoCollection(attachedQualificadorAplicacaoCollection);
            Collection<QualificadorProduto> attachedQualificadorProdutoCollection = new ArrayList<QualificadorProduto>();
            for (QualificadorProduto qualificadorProdutoCollectionQualificadorProdutoToAttach : qualificador.getQualificadorProdutoCollection()) {
                qualificadorProdutoCollectionQualificadorProdutoToAttach = em.getReference(qualificadorProdutoCollectionQualificadorProdutoToAttach.getClass(), qualificadorProdutoCollectionQualificadorProdutoToAttach.getCodigoQualificadorProduto());
                attachedQualificadorProdutoCollection.add(qualificadorProdutoCollectionQualificadorProdutoToAttach);
            }
            qualificador.setQualificadorProdutoCollection(attachedQualificadorProdutoCollection);
            em.persist(qualificador);
            for (QualificadorAplicacao qualificadorAplicacaoCollectionQualificadorAplicacao : qualificador.getQualificadorAplicacaoCollection()) {
                Qualificador oldCodigoQualificadorOfQualificadorAplicacaoCollectionQualificadorAplicacao = qualificadorAplicacaoCollectionQualificadorAplicacao.getCodigoQualificador();
                qualificadorAplicacaoCollectionQualificadorAplicacao.setCodigoQualificador(qualificador);
                qualificadorAplicacaoCollectionQualificadorAplicacao = em.merge(qualificadorAplicacaoCollectionQualificadorAplicacao);
                if (oldCodigoQualificadorOfQualificadorAplicacaoCollectionQualificadorAplicacao != null) {
                    oldCodigoQualificadorOfQualificadorAplicacaoCollectionQualificadorAplicacao.getQualificadorAplicacaoCollection().remove(qualificadorAplicacaoCollectionQualificadorAplicacao);
                    oldCodigoQualificadorOfQualificadorAplicacaoCollectionQualificadorAplicacao = em.merge(oldCodigoQualificadorOfQualificadorAplicacaoCollectionQualificadorAplicacao);
                }
            }
            for (QualificadorProduto qualificadorProdutoCollectionQualificadorProduto : qualificador.getQualificadorProdutoCollection()) {
                Qualificador oldCodigoQualificadorOfQualificadorProdutoCollectionQualificadorProduto = qualificadorProdutoCollectionQualificadorProduto.getCodigoQualificador();
                qualificadorProdutoCollectionQualificadorProduto.setCodigoQualificador(qualificador);
                qualificadorProdutoCollectionQualificadorProduto = em.merge(qualificadorProdutoCollectionQualificadorProduto);
                if (oldCodigoQualificadorOfQualificadorProdutoCollectionQualificadorProduto != null) {
                    oldCodigoQualificadorOfQualificadorProdutoCollectionQualificadorProduto.getQualificadorProdutoCollection().remove(qualificadorProdutoCollectionQualificadorProduto);
                    oldCodigoQualificadorOfQualificadorProdutoCollectionQualificadorProduto = em.merge(oldCodigoQualificadorOfQualificadorProdutoCollectionQualificadorProduto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Qualificador qualificador) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Qualificador persistentQualificador = em.find(Qualificador.class, qualificador.getCodigoQualificador());
            Collection<QualificadorAplicacao> qualificadorAplicacaoCollectionOld = persistentQualificador.getQualificadorAplicacaoCollection();
            Collection<QualificadorAplicacao> qualificadorAplicacaoCollectionNew = qualificador.getQualificadorAplicacaoCollection();
            Collection<QualificadorProduto> qualificadorProdutoCollectionOld = persistentQualificador.getQualificadorProdutoCollection();
            Collection<QualificadorProduto> qualificadorProdutoCollectionNew = qualificador.getQualificadorProdutoCollection();
            List<String> illegalOrphanMessages = null;
            for (QualificadorAplicacao qualificadorAplicacaoCollectionOldQualificadorAplicacao : qualificadorAplicacaoCollectionOld) {
                if (!qualificadorAplicacaoCollectionNew.contains(qualificadorAplicacaoCollectionOldQualificadorAplicacao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain QualificadorAplicacao " + qualificadorAplicacaoCollectionOldQualificadorAplicacao + " since its codigoQualificador field is not nullable.");
                }
            }
            for (QualificadorProduto qualificadorProdutoCollectionOldQualificadorProduto : qualificadorProdutoCollectionOld) {
                if (!qualificadorProdutoCollectionNew.contains(qualificadorProdutoCollectionOldQualificadorProduto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain QualificadorProduto " + qualificadorProdutoCollectionOldQualificadorProduto + " since its codigoQualificador field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<QualificadorAplicacao> attachedQualificadorAplicacaoCollectionNew = new ArrayList<QualificadorAplicacao>();
            for (QualificadorAplicacao qualificadorAplicacaoCollectionNewQualificadorAplicacaoToAttach : qualificadorAplicacaoCollectionNew) {
                qualificadorAplicacaoCollectionNewQualificadorAplicacaoToAttach = em.getReference(qualificadorAplicacaoCollectionNewQualificadorAplicacaoToAttach.getClass(), qualificadorAplicacaoCollectionNewQualificadorAplicacaoToAttach.getCodigoQualificadorAplicacao());
                attachedQualificadorAplicacaoCollectionNew.add(qualificadorAplicacaoCollectionNewQualificadorAplicacaoToAttach);
            }
            qualificadorAplicacaoCollectionNew = attachedQualificadorAplicacaoCollectionNew;
            qualificador.setQualificadorAplicacaoCollection(qualificadorAplicacaoCollectionNew);
            Collection<QualificadorProduto> attachedQualificadorProdutoCollectionNew = new ArrayList<QualificadorProduto>();
            for (QualificadorProduto qualificadorProdutoCollectionNewQualificadorProdutoToAttach : qualificadorProdutoCollectionNew) {
                qualificadorProdutoCollectionNewQualificadorProdutoToAttach = em.getReference(qualificadorProdutoCollectionNewQualificadorProdutoToAttach.getClass(), qualificadorProdutoCollectionNewQualificadorProdutoToAttach.getCodigoQualificadorProduto());
                attachedQualificadorProdutoCollectionNew.add(qualificadorProdutoCollectionNewQualificadorProdutoToAttach);
            }
            qualificadorProdutoCollectionNew = attachedQualificadorProdutoCollectionNew;
            qualificador.setQualificadorProdutoCollection(qualificadorProdutoCollectionNew);
            qualificador = em.merge(qualificador);
            for (QualificadorAplicacao qualificadorAplicacaoCollectionNewQualificadorAplicacao : qualificadorAplicacaoCollectionNew) {
                if (!qualificadorAplicacaoCollectionOld.contains(qualificadorAplicacaoCollectionNewQualificadorAplicacao)) {
                    Qualificador oldCodigoQualificadorOfQualificadorAplicacaoCollectionNewQualificadorAplicacao = qualificadorAplicacaoCollectionNewQualificadorAplicacao.getCodigoQualificador();
                    qualificadorAplicacaoCollectionNewQualificadorAplicacao.setCodigoQualificador(qualificador);
                    qualificadorAplicacaoCollectionNewQualificadorAplicacao = em.merge(qualificadorAplicacaoCollectionNewQualificadorAplicacao);
                    if (oldCodigoQualificadorOfQualificadorAplicacaoCollectionNewQualificadorAplicacao != null && !oldCodigoQualificadorOfQualificadorAplicacaoCollectionNewQualificadorAplicacao.equals(qualificador)) {
                        oldCodigoQualificadorOfQualificadorAplicacaoCollectionNewQualificadorAplicacao.getQualificadorAplicacaoCollection().remove(qualificadorAplicacaoCollectionNewQualificadorAplicacao);
                        oldCodigoQualificadorOfQualificadorAplicacaoCollectionNewQualificadorAplicacao = em.merge(oldCodigoQualificadorOfQualificadorAplicacaoCollectionNewQualificadorAplicacao);
                    }
                }
            }
            for (QualificadorProduto qualificadorProdutoCollectionNewQualificadorProduto : qualificadorProdutoCollectionNew) {
                if (!qualificadorProdutoCollectionOld.contains(qualificadorProdutoCollectionNewQualificadorProduto)) {
                    Qualificador oldCodigoQualificadorOfQualificadorProdutoCollectionNewQualificadorProduto = qualificadorProdutoCollectionNewQualificadorProduto.getCodigoQualificador();
                    qualificadorProdutoCollectionNewQualificadorProduto.setCodigoQualificador(qualificador);
                    qualificadorProdutoCollectionNewQualificadorProduto = em.merge(qualificadorProdutoCollectionNewQualificadorProduto);
                    if (oldCodigoQualificadorOfQualificadorProdutoCollectionNewQualificadorProduto != null && !oldCodigoQualificadorOfQualificadorProdutoCollectionNewQualificadorProduto.equals(qualificador)) {
                        oldCodigoQualificadorOfQualificadorProdutoCollectionNewQualificadorProduto.getQualificadorProdutoCollection().remove(qualificadorProdutoCollectionNewQualificadorProduto);
                        oldCodigoQualificadorOfQualificadorProdutoCollectionNewQualificadorProduto = em.merge(oldCodigoQualificadorOfQualificadorProdutoCollectionNewQualificadorProduto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = qualificador.getCodigoQualificador();
                if (findQualificador(id) == null) {
                    throw new NonexistentEntityException("The qualificador with id " + id + " no longer exists.");
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
            Qualificador qualificador;
            try {
                qualificador = em.getReference(Qualificador.class, id);
                qualificador.getCodigoQualificador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The qualificador with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<QualificadorAplicacao> qualificadorAplicacaoCollectionOrphanCheck = qualificador.getQualificadorAplicacaoCollection();
            for (QualificadorAplicacao qualificadorAplicacaoCollectionOrphanCheckQualificadorAplicacao : qualificadorAplicacaoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Qualificador (" + qualificador + ") cannot be destroyed since the QualificadorAplicacao " + qualificadorAplicacaoCollectionOrphanCheckQualificadorAplicacao + " in its qualificadorAplicacaoCollection field has a non-nullable codigoQualificador field.");
            }
            Collection<QualificadorProduto> qualificadorProdutoCollectionOrphanCheck = qualificador.getQualificadorProdutoCollection();
            for (QualificadorProduto qualificadorProdutoCollectionOrphanCheckQualificadorProduto : qualificadorProdutoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Qualificador (" + qualificador + ") cannot be destroyed since the QualificadorProduto " + qualificadorProdutoCollectionOrphanCheckQualificadorProduto + " in its qualificadorProdutoCollection field has a non-nullable codigoQualificador field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(qualificador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Qualificador> findQualificadorEntities() {
        return findQualificadorEntities(true, -1, -1);
    }

    public List<Qualificador> findQualificadorEntities(int maxResults, int firstResult) {
        return findQualificadorEntities(false, maxResults, firstResult);
    }

    private List<Qualificador> findQualificadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Qualificador.class));
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

    public Qualificador findQualificador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Qualificador.class, id);
        } finally {
            em.close();
        }
    }

    public int getQualificadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Qualificador> rt = cq.from(Qualificador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
