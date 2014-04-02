/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.controller;

import br.com.autodb.model.controller.exceptions.IllegalOrphanException;
import br.com.autodb.model.controller.exceptions.NonexistentEntityException;
import br.com.autodb.model.dao.LinhaProduto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.autodb.model.dao.SubcategoriaProduto;
import br.com.autodb.model.dao.Produto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class LinhaProdutoJpaController implements Serializable {

    public LinhaProdutoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LinhaProduto linhaProduto) {
        if (linhaProduto.getProdutoCollection() == null) {
            linhaProduto.setProdutoCollection(new ArrayList<Produto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SubcategoriaProduto codigoSubcategoriaProduto = linhaProduto.getCodigoSubcategoriaProduto();
            if (codigoSubcategoriaProduto != null) {
                codigoSubcategoriaProduto = em.getReference(codigoSubcategoriaProduto.getClass(), codigoSubcategoriaProduto.getCodigoSubcategoriaProduto());
                linhaProduto.setCodigoSubcategoriaProduto(codigoSubcategoriaProduto);
            }
            Collection<Produto> attachedProdutoCollection = new ArrayList<Produto>();
            for (Produto produtoCollectionProdutoToAttach : linhaProduto.getProdutoCollection()) {
                produtoCollectionProdutoToAttach = em.getReference(produtoCollectionProdutoToAttach.getClass(), produtoCollectionProdutoToAttach.getCodigoProduto());
                attachedProdutoCollection.add(produtoCollectionProdutoToAttach);
            }
            linhaProduto.setProdutoCollection(attachedProdutoCollection);
            em.persist(linhaProduto);
            if (codigoSubcategoriaProduto != null) {
                codigoSubcategoriaProduto.getLinhaProdutoCollection().add(linhaProduto);
                codigoSubcategoriaProduto = em.merge(codigoSubcategoriaProduto);
            }
            for (Produto produtoCollectionProduto : linhaProduto.getProdutoCollection()) {
                LinhaProduto oldCodigoLinhaProdutoOfProdutoCollectionProduto = produtoCollectionProduto.getCodigoLinhaProduto();
                produtoCollectionProduto.setCodigoLinhaProduto(linhaProduto);
                produtoCollectionProduto = em.merge(produtoCollectionProduto);
                if (oldCodigoLinhaProdutoOfProdutoCollectionProduto != null) {
                    oldCodigoLinhaProdutoOfProdutoCollectionProduto.getProdutoCollection().remove(produtoCollectionProduto);
                    oldCodigoLinhaProdutoOfProdutoCollectionProduto = em.merge(oldCodigoLinhaProdutoOfProdutoCollectionProduto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LinhaProduto linhaProduto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LinhaProduto persistentLinhaProduto = em.find(LinhaProduto.class, linhaProduto.getCodigoLinhaProduto());
            SubcategoriaProduto codigoSubcategoriaProdutoOld = persistentLinhaProduto.getCodigoSubcategoriaProduto();
            SubcategoriaProduto codigoSubcategoriaProdutoNew = linhaProduto.getCodigoSubcategoriaProduto();
            Collection<Produto> produtoCollectionOld = persistentLinhaProduto.getProdutoCollection();
            Collection<Produto> produtoCollectionNew = linhaProduto.getProdutoCollection();
            List<String> illegalOrphanMessages = null;
            for (Produto produtoCollectionOldProduto : produtoCollectionOld) {
                if (!produtoCollectionNew.contains(produtoCollectionOldProduto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Produto " + produtoCollectionOldProduto + " since its codigoLinhaProduto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codigoSubcategoriaProdutoNew != null) {
                codigoSubcategoriaProdutoNew = em.getReference(codigoSubcategoriaProdutoNew.getClass(), codigoSubcategoriaProdutoNew.getCodigoSubcategoriaProduto());
                linhaProduto.setCodigoSubcategoriaProduto(codigoSubcategoriaProdutoNew);
            }
            Collection<Produto> attachedProdutoCollectionNew = new ArrayList<Produto>();
            for (Produto produtoCollectionNewProdutoToAttach : produtoCollectionNew) {
                produtoCollectionNewProdutoToAttach = em.getReference(produtoCollectionNewProdutoToAttach.getClass(), produtoCollectionNewProdutoToAttach.getCodigoProduto());
                attachedProdutoCollectionNew.add(produtoCollectionNewProdutoToAttach);
            }
            produtoCollectionNew = attachedProdutoCollectionNew;
            linhaProduto.setProdutoCollection(produtoCollectionNew);
            linhaProduto = em.merge(linhaProduto);
            if (codigoSubcategoriaProdutoOld != null && !codigoSubcategoriaProdutoOld.equals(codigoSubcategoriaProdutoNew)) {
                codigoSubcategoriaProdutoOld.getLinhaProdutoCollection().remove(linhaProduto);
                codigoSubcategoriaProdutoOld = em.merge(codigoSubcategoriaProdutoOld);
            }
            if (codigoSubcategoriaProdutoNew != null && !codigoSubcategoriaProdutoNew.equals(codigoSubcategoriaProdutoOld)) {
                codigoSubcategoriaProdutoNew.getLinhaProdutoCollection().add(linhaProduto);
                codigoSubcategoriaProdutoNew = em.merge(codigoSubcategoriaProdutoNew);
            }
            for (Produto produtoCollectionNewProduto : produtoCollectionNew) {
                if (!produtoCollectionOld.contains(produtoCollectionNewProduto)) {
                    LinhaProduto oldCodigoLinhaProdutoOfProdutoCollectionNewProduto = produtoCollectionNewProduto.getCodigoLinhaProduto();
                    produtoCollectionNewProduto.setCodigoLinhaProduto(linhaProduto);
                    produtoCollectionNewProduto = em.merge(produtoCollectionNewProduto);
                    if (oldCodigoLinhaProdutoOfProdutoCollectionNewProduto != null && !oldCodigoLinhaProdutoOfProdutoCollectionNewProduto.equals(linhaProduto)) {
                        oldCodigoLinhaProdutoOfProdutoCollectionNewProduto.getProdutoCollection().remove(produtoCollectionNewProduto);
                        oldCodigoLinhaProdutoOfProdutoCollectionNewProduto = em.merge(oldCodigoLinhaProdutoOfProdutoCollectionNewProduto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = linhaProduto.getCodigoLinhaProduto();
                if (findLinhaProduto(id) == null) {
                    throw new NonexistentEntityException("The linhaProduto with id " + id + " no longer exists.");
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
            LinhaProduto linhaProduto;
            try {
                linhaProduto = em.getReference(LinhaProduto.class, id);
                linhaProduto.getCodigoLinhaProduto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The linhaProduto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Produto> produtoCollectionOrphanCheck = linhaProduto.getProdutoCollection();
            for (Produto produtoCollectionOrphanCheckProduto : produtoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This LinhaProduto (" + linhaProduto + ") cannot be destroyed since the Produto " + produtoCollectionOrphanCheckProduto + " in its produtoCollection field has a non-nullable codigoLinhaProduto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            SubcategoriaProduto codigoSubcategoriaProduto = linhaProduto.getCodigoSubcategoriaProduto();
            if (codigoSubcategoriaProduto != null) {
                codigoSubcategoriaProduto.getLinhaProdutoCollection().remove(linhaProduto);
                codigoSubcategoriaProduto = em.merge(codigoSubcategoriaProduto);
            }
            em.remove(linhaProduto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LinhaProduto> findLinhaProdutoEntities() {
        return findLinhaProdutoEntities(true, -1, -1);
    }

    public List<LinhaProduto> findLinhaProdutoEntities(int maxResults, int firstResult) {
        return findLinhaProdutoEntities(false, maxResults, firstResult);
    }

    private List<LinhaProduto> findLinhaProdutoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LinhaProduto.class));
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

    public LinhaProduto findLinhaProduto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LinhaProduto.class, id);
        } finally {
            em.close();
        }
    }

    public int getLinhaProdutoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LinhaProduto> rt = cq.from(LinhaProduto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
