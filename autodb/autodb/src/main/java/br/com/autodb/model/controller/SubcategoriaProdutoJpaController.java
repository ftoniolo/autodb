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
import br.com.autodb.model.dao.CategoriaProduto;
import br.com.autodb.model.dao.LinhaProduto;
import br.com.autodb.model.dao.SubcategoriaProduto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class SubcategoriaProdutoJpaController implements Serializable {

    public SubcategoriaProdutoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SubcategoriaProduto subcategoriaProduto) {
        if (subcategoriaProduto.getLinhaProdutoCollection() == null) {
            subcategoriaProduto.setLinhaProdutoCollection(new ArrayList<LinhaProduto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CategoriaProduto codigoCategoriaProduto = subcategoriaProduto.getCodigoCategoriaProduto();
            if (codigoCategoriaProduto != null) {
                codigoCategoriaProduto = em.getReference(codigoCategoriaProduto.getClass(), codigoCategoriaProduto.getCodigoCategoriaProduto());
                subcategoriaProduto.setCodigoCategoriaProduto(codigoCategoriaProduto);
            }
            Collection<LinhaProduto> attachedLinhaProdutoCollection = new ArrayList<LinhaProduto>();
            for (LinhaProduto linhaProdutoCollectionLinhaProdutoToAttach : subcategoriaProduto.getLinhaProdutoCollection()) {
                linhaProdutoCollectionLinhaProdutoToAttach = em.getReference(linhaProdutoCollectionLinhaProdutoToAttach.getClass(), linhaProdutoCollectionLinhaProdutoToAttach.getCodigoLinhaProduto());
                attachedLinhaProdutoCollection.add(linhaProdutoCollectionLinhaProdutoToAttach);
            }
            subcategoriaProduto.setLinhaProdutoCollection(attachedLinhaProdutoCollection);
            em.persist(subcategoriaProduto);
            if (codigoCategoriaProduto != null) {
                codigoCategoriaProduto.getSubcategoriaProdutoCollection().add(subcategoriaProduto);
                codigoCategoriaProduto = em.merge(codigoCategoriaProduto);
            }
            for (LinhaProduto linhaProdutoCollectionLinhaProduto : subcategoriaProduto.getLinhaProdutoCollection()) {
                SubcategoriaProduto oldCodigoSubcategoriaProdutoOfLinhaProdutoCollectionLinhaProduto = linhaProdutoCollectionLinhaProduto.getCodigoSubcategoriaProduto();
                linhaProdutoCollectionLinhaProduto.setCodigoSubcategoriaProduto(subcategoriaProduto);
                linhaProdutoCollectionLinhaProduto = em.merge(linhaProdutoCollectionLinhaProduto);
                if (oldCodigoSubcategoriaProdutoOfLinhaProdutoCollectionLinhaProduto != null) {
                    oldCodigoSubcategoriaProdutoOfLinhaProdutoCollectionLinhaProduto.getLinhaProdutoCollection().remove(linhaProdutoCollectionLinhaProduto);
                    oldCodigoSubcategoriaProdutoOfLinhaProdutoCollectionLinhaProduto = em.merge(oldCodigoSubcategoriaProdutoOfLinhaProdutoCollectionLinhaProduto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SubcategoriaProduto subcategoriaProduto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SubcategoriaProduto persistentSubcategoriaProduto = em.find(SubcategoriaProduto.class, subcategoriaProduto.getCodigoSubcategoriaProduto());
            CategoriaProduto codigoCategoriaProdutoOld = persistentSubcategoriaProduto.getCodigoCategoriaProduto();
            CategoriaProduto codigoCategoriaProdutoNew = subcategoriaProduto.getCodigoCategoriaProduto();
            Collection<LinhaProduto> linhaProdutoCollectionOld = persistentSubcategoriaProduto.getLinhaProdutoCollection();
            Collection<LinhaProduto> linhaProdutoCollectionNew = subcategoriaProduto.getLinhaProdutoCollection();
            List<String> illegalOrphanMessages = null;
            for (LinhaProduto linhaProdutoCollectionOldLinhaProduto : linhaProdutoCollectionOld) {
                if (!linhaProdutoCollectionNew.contains(linhaProdutoCollectionOldLinhaProduto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain LinhaProduto " + linhaProdutoCollectionOldLinhaProduto + " since its codigoSubcategoriaProduto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codigoCategoriaProdutoNew != null) {
                codigoCategoriaProdutoNew = em.getReference(codigoCategoriaProdutoNew.getClass(), codigoCategoriaProdutoNew.getCodigoCategoriaProduto());
                subcategoriaProduto.setCodigoCategoriaProduto(codigoCategoriaProdutoNew);
            }
            Collection<LinhaProduto> attachedLinhaProdutoCollectionNew = new ArrayList<LinhaProduto>();
            for (LinhaProduto linhaProdutoCollectionNewLinhaProdutoToAttach : linhaProdutoCollectionNew) {
                linhaProdutoCollectionNewLinhaProdutoToAttach = em.getReference(linhaProdutoCollectionNewLinhaProdutoToAttach.getClass(), linhaProdutoCollectionNewLinhaProdutoToAttach.getCodigoLinhaProduto());
                attachedLinhaProdutoCollectionNew.add(linhaProdutoCollectionNewLinhaProdutoToAttach);
            }
            linhaProdutoCollectionNew = attachedLinhaProdutoCollectionNew;
            subcategoriaProduto.setLinhaProdutoCollection(linhaProdutoCollectionNew);
            subcategoriaProduto = em.merge(subcategoriaProduto);
            if (codigoCategoriaProdutoOld != null && !codigoCategoriaProdutoOld.equals(codigoCategoriaProdutoNew)) {
                codigoCategoriaProdutoOld.getSubcategoriaProdutoCollection().remove(subcategoriaProduto);
                codigoCategoriaProdutoOld = em.merge(codigoCategoriaProdutoOld);
            }
            if (codigoCategoriaProdutoNew != null && !codigoCategoriaProdutoNew.equals(codigoCategoriaProdutoOld)) {
                codigoCategoriaProdutoNew.getSubcategoriaProdutoCollection().add(subcategoriaProduto);
                codigoCategoriaProdutoNew = em.merge(codigoCategoriaProdutoNew);
            }
            for (LinhaProduto linhaProdutoCollectionNewLinhaProduto : linhaProdutoCollectionNew) {
                if (!linhaProdutoCollectionOld.contains(linhaProdutoCollectionNewLinhaProduto)) {
                    SubcategoriaProduto oldCodigoSubcategoriaProdutoOfLinhaProdutoCollectionNewLinhaProduto = linhaProdutoCollectionNewLinhaProduto.getCodigoSubcategoriaProduto();
                    linhaProdutoCollectionNewLinhaProduto.setCodigoSubcategoriaProduto(subcategoriaProduto);
                    linhaProdutoCollectionNewLinhaProduto = em.merge(linhaProdutoCollectionNewLinhaProduto);
                    if (oldCodigoSubcategoriaProdutoOfLinhaProdutoCollectionNewLinhaProduto != null && !oldCodigoSubcategoriaProdutoOfLinhaProdutoCollectionNewLinhaProduto.equals(subcategoriaProduto)) {
                        oldCodigoSubcategoriaProdutoOfLinhaProdutoCollectionNewLinhaProduto.getLinhaProdutoCollection().remove(linhaProdutoCollectionNewLinhaProduto);
                        oldCodigoSubcategoriaProdutoOfLinhaProdutoCollectionNewLinhaProduto = em.merge(oldCodigoSubcategoriaProdutoOfLinhaProdutoCollectionNewLinhaProduto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = subcategoriaProduto.getCodigoSubcategoriaProduto();
                if (findSubcategoriaProduto(id) == null) {
                    throw new NonexistentEntityException("The subcategoriaProduto with id " + id + " no longer exists.");
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
            SubcategoriaProduto subcategoriaProduto;
            try {
                subcategoriaProduto = em.getReference(SubcategoriaProduto.class, id);
                subcategoriaProduto.getCodigoSubcategoriaProduto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The subcategoriaProduto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<LinhaProduto> linhaProdutoCollectionOrphanCheck = subcategoriaProduto.getLinhaProdutoCollection();
            for (LinhaProduto linhaProdutoCollectionOrphanCheckLinhaProduto : linhaProdutoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SubcategoriaProduto (" + subcategoriaProduto + ") cannot be destroyed since the LinhaProduto " + linhaProdutoCollectionOrphanCheckLinhaProduto + " in its linhaProdutoCollection field has a non-nullable codigoSubcategoriaProduto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            CategoriaProduto codigoCategoriaProduto = subcategoriaProduto.getCodigoCategoriaProduto();
            if (codigoCategoriaProduto != null) {
                codigoCategoriaProduto.getSubcategoriaProdutoCollection().remove(subcategoriaProduto);
                codigoCategoriaProduto = em.merge(codigoCategoriaProduto);
            }
            em.remove(subcategoriaProduto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SubcategoriaProduto> findSubcategoriaProdutoEntities() {
        return findSubcategoriaProdutoEntities(true, -1, -1);
    }

    public List<SubcategoriaProduto> findSubcategoriaProdutoEntities(int maxResults, int firstResult) {
        return findSubcategoriaProdutoEntities(false, maxResults, firstResult);
    }

    private List<SubcategoriaProduto> findSubcategoriaProdutoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SubcategoriaProduto.class));
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

    public SubcategoriaProduto findSubcategoriaProduto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SubcategoriaProduto.class, id);
        } finally {
            em.close();
        }
    }

    public int getSubcategoriaProdutoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SubcategoriaProduto> rt = cq.from(SubcategoriaProduto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
