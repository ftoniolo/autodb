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
import br.com.autodb.model.dao.Produto;
import br.com.autodb.model.dao.StatusProduto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class StatusProdutoJpaController implements Serializable {

    public StatusProdutoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(StatusProduto statusProduto) {
        if (statusProduto.getProdutoCollection() == null) {
            statusProduto.setProdutoCollection(new ArrayList<Produto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Produto> attachedProdutoCollection = new ArrayList<Produto>();
            for (Produto produtoCollectionProdutoToAttach : statusProduto.getProdutoCollection()) {
                produtoCollectionProdutoToAttach = em.getReference(produtoCollectionProdutoToAttach.getClass(), produtoCollectionProdutoToAttach.getCodigoProduto());
                attachedProdutoCollection.add(produtoCollectionProdutoToAttach);
            }
            statusProduto.setProdutoCollection(attachedProdutoCollection);
            em.persist(statusProduto);
            for (Produto produtoCollectionProduto : statusProduto.getProdutoCollection()) {
                StatusProduto oldCodigoStatusProdutoOfProdutoCollectionProduto = produtoCollectionProduto.getCodigoStatusProduto();
                produtoCollectionProduto.setCodigoStatusProduto(statusProduto);
                produtoCollectionProduto = em.merge(produtoCollectionProduto);
                if (oldCodigoStatusProdutoOfProdutoCollectionProduto != null) {
                    oldCodigoStatusProdutoOfProdutoCollectionProduto.getProdutoCollection().remove(produtoCollectionProduto);
                    oldCodigoStatusProdutoOfProdutoCollectionProduto = em.merge(oldCodigoStatusProdutoOfProdutoCollectionProduto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(StatusProduto statusProduto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            StatusProduto persistentStatusProduto = em.find(StatusProduto.class, statusProduto.getCodigoStatusProduto());
            Collection<Produto> produtoCollectionOld = persistentStatusProduto.getProdutoCollection();
            Collection<Produto> produtoCollectionNew = statusProduto.getProdutoCollection();
            List<String> illegalOrphanMessages = null;
            for (Produto produtoCollectionOldProduto : produtoCollectionOld) {
                if (!produtoCollectionNew.contains(produtoCollectionOldProduto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Produto " + produtoCollectionOldProduto + " since its codigoStatusProduto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Produto> attachedProdutoCollectionNew = new ArrayList<Produto>();
            for (Produto produtoCollectionNewProdutoToAttach : produtoCollectionNew) {
                produtoCollectionNewProdutoToAttach = em.getReference(produtoCollectionNewProdutoToAttach.getClass(), produtoCollectionNewProdutoToAttach.getCodigoProduto());
                attachedProdutoCollectionNew.add(produtoCollectionNewProdutoToAttach);
            }
            produtoCollectionNew = attachedProdutoCollectionNew;
            statusProduto.setProdutoCollection(produtoCollectionNew);
            statusProduto = em.merge(statusProduto);
            for (Produto produtoCollectionNewProduto : produtoCollectionNew) {
                if (!produtoCollectionOld.contains(produtoCollectionNewProduto)) {
                    StatusProduto oldCodigoStatusProdutoOfProdutoCollectionNewProduto = produtoCollectionNewProduto.getCodigoStatusProduto();
                    produtoCollectionNewProduto.setCodigoStatusProduto(statusProduto);
                    produtoCollectionNewProduto = em.merge(produtoCollectionNewProduto);
                    if (oldCodigoStatusProdutoOfProdutoCollectionNewProduto != null && !oldCodigoStatusProdutoOfProdutoCollectionNewProduto.equals(statusProduto)) {
                        oldCodigoStatusProdutoOfProdutoCollectionNewProduto.getProdutoCollection().remove(produtoCollectionNewProduto);
                        oldCodigoStatusProdutoOfProdutoCollectionNewProduto = em.merge(oldCodigoStatusProdutoOfProdutoCollectionNewProduto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = statusProduto.getCodigoStatusProduto();
                if (findStatusProduto(id) == null) {
                    throw new NonexistentEntityException("The statusProduto with id " + id + " no longer exists.");
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
            StatusProduto statusProduto;
            try {
                statusProduto = em.getReference(StatusProduto.class, id);
                statusProduto.getCodigoStatusProduto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The statusProduto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Produto> produtoCollectionOrphanCheck = statusProduto.getProdutoCollection();
            for (Produto produtoCollectionOrphanCheckProduto : produtoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This StatusProduto (" + statusProduto + ") cannot be destroyed since the Produto " + produtoCollectionOrphanCheckProduto + " in its produtoCollection field has a non-nullable codigoStatusProduto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(statusProduto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<StatusProduto> findStatusProdutoEntities() {
        return findStatusProdutoEntities(true, -1, -1);
    }

    public List<StatusProduto> findStatusProdutoEntities(int maxResults, int firstResult) {
        return findStatusProdutoEntities(false, maxResults, firstResult);
    }

    private List<StatusProduto> findStatusProdutoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(StatusProduto.class));
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

    public StatusProduto findStatusProduto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(StatusProduto.class, id);
        } finally {
            em.close();
        }
    }

    public int getStatusProdutoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<StatusProduto> rt = cq.from(StatusProduto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
