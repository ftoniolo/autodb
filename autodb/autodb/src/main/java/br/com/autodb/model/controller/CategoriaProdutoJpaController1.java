/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.controller;

import br.com.autodb.model.controller.exceptions.NonexistentEntityException;
import br.com.autodb.model.dao.CategoriaProduto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class CategoriaProdutoJpaController1 implements Serializable {

    public CategoriaProdutoJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CategoriaProduto categoriaProduto) {
        if (categoriaProduto.getSubcategoriaProdutoCollection() == null) {
            categoriaProduto.setSubcategoriaProdutoCollection(new ArrayList<SubcategoriaProduto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<SubcategoriaProduto> attachedSubcategoriaProdutoCollection = new ArrayList<SubcategoriaProduto>();
            for (SubcategoriaProduto subcategoriaProdutoCollectionSubcategoriaProdutoToAttach : categoriaProduto.getSubcategoriaProdutoCollection()) {
                subcategoriaProdutoCollectionSubcategoriaProdutoToAttach = em.getReference(subcategoriaProdutoCollectionSubcategoriaProdutoToAttach.getClass(), subcategoriaProdutoCollectionSubcategoriaProdutoToAttach.getCodigoSubcategoriaProduto());
                attachedSubcategoriaProdutoCollection.add(subcategoriaProdutoCollectionSubcategoriaProdutoToAttach);
            }
            categoriaProduto.setSubcategoriaProdutoCollection(attachedSubcategoriaProdutoCollection);
            em.persist(categoriaProduto);
            for (SubcategoriaProduto subcategoriaProdutoCollectionSubcategoriaProduto : categoriaProduto.getSubcategoriaProdutoCollection()) {
                CategoriaProduto oldCodigoCategoriaProdutoOfSubcategoriaProdutoCollectionSubcategoriaProduto = subcategoriaProdutoCollectionSubcategoriaProduto.getCodigoCategoriaProduto();
                subcategoriaProdutoCollectionSubcategoriaProduto.setCodigoCategoriaProduto(categoriaProduto);
                subcategoriaProdutoCollectionSubcategoriaProduto = em.merge(subcategoriaProdutoCollectionSubcategoriaProduto);
                if (oldCodigoCategoriaProdutoOfSubcategoriaProdutoCollectionSubcategoriaProduto != null) {
                    oldCodigoCategoriaProdutoOfSubcategoriaProdutoCollectionSubcategoriaProduto.getSubcategoriaProdutoCollection().remove(subcategoriaProdutoCollectionSubcategoriaProduto);
                    oldCodigoCategoriaProdutoOfSubcategoriaProdutoCollectionSubcategoriaProduto = em.merge(oldCodigoCategoriaProdutoOfSubcategoriaProdutoCollectionSubcategoriaProduto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CategoriaProduto categoriaProduto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CategoriaProduto persistentCategoriaProduto = em.find(CategoriaProduto.class, categoriaProduto.getCodigoCategoriaProduto());
            Collection<SubcategoriaProduto> subcategoriaProdutoCollectionOld = persistentCategoriaProduto.getSubcategoriaProdutoCollection();
            Collection<SubcategoriaProduto> subcategoriaProdutoCollectionNew = categoriaProduto.getSubcategoriaProdutoCollection();
            Collection<SubcategoriaProduto> attachedSubcategoriaProdutoCollectionNew = new ArrayList<SubcategoriaProduto>();
            for (SubcategoriaProduto subcategoriaProdutoCollectionNewSubcategoriaProdutoToAttach : subcategoriaProdutoCollectionNew) {
                subcategoriaProdutoCollectionNewSubcategoriaProdutoToAttach = em.getReference(subcategoriaProdutoCollectionNewSubcategoriaProdutoToAttach.getClass(), subcategoriaProdutoCollectionNewSubcategoriaProdutoToAttach.getCodigoSubcategoriaProduto());
                attachedSubcategoriaProdutoCollectionNew.add(subcategoriaProdutoCollectionNewSubcategoriaProdutoToAttach);
            }
            subcategoriaProdutoCollectionNew = attachedSubcategoriaProdutoCollectionNew;
            categoriaProduto.setSubcategoriaProdutoCollection(subcategoriaProdutoCollectionNew);
            categoriaProduto = em.merge(categoriaProduto);
            for (SubcategoriaProduto subcategoriaProdutoCollectionOldSubcategoriaProduto : subcategoriaProdutoCollectionOld) {
                if (!subcategoriaProdutoCollectionNew.contains(subcategoriaProdutoCollectionOldSubcategoriaProduto)) {
                    subcategoriaProdutoCollectionOldSubcategoriaProduto.setCodigoCategoriaProduto(null);
                    subcategoriaProdutoCollectionOldSubcategoriaProduto = em.merge(subcategoriaProdutoCollectionOldSubcategoriaProduto);
                }
            }
            for (SubcategoriaProduto subcategoriaProdutoCollectionNewSubcategoriaProduto : subcategoriaProdutoCollectionNew) {
                if (!subcategoriaProdutoCollectionOld.contains(subcategoriaProdutoCollectionNewSubcategoriaProduto)) {
                    CategoriaProduto oldCodigoCategoriaProdutoOfSubcategoriaProdutoCollectionNewSubcategoriaProduto = subcategoriaProdutoCollectionNewSubcategoriaProduto.getCodigoCategoriaProduto();
                    subcategoriaProdutoCollectionNewSubcategoriaProduto.setCodigoCategoriaProduto(categoriaProduto);
                    subcategoriaProdutoCollectionNewSubcategoriaProduto = em.merge(subcategoriaProdutoCollectionNewSubcategoriaProduto);
                    if (oldCodigoCategoriaProdutoOfSubcategoriaProdutoCollectionNewSubcategoriaProduto != null && !oldCodigoCategoriaProdutoOfSubcategoriaProdutoCollectionNewSubcategoriaProduto.equals(categoriaProduto)) {
                        oldCodigoCategoriaProdutoOfSubcategoriaProdutoCollectionNewSubcategoriaProduto.getSubcategoriaProdutoCollection().remove(subcategoriaProdutoCollectionNewSubcategoriaProduto);
                        oldCodigoCategoriaProdutoOfSubcategoriaProdutoCollectionNewSubcategoriaProduto = em.merge(oldCodigoCategoriaProdutoOfSubcategoriaProdutoCollectionNewSubcategoriaProduto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categoriaProduto.getCodigoCategoriaProduto();
                if (findCategoriaProduto(id) == null) {
                    throw new NonexistentEntityException("The categoriaProduto with id " + id + " no longer exists.");
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
            CategoriaProduto categoriaProduto;
            try {
                categoriaProduto = em.getReference(CategoriaProduto.class, id);
                categoriaProduto.getCodigoCategoriaProduto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoriaProduto with id " + id + " no longer exists.", enfe);
            }
            Collection<SubcategoriaProduto> subcategoriaProdutoCollection = categoriaProduto.getSubcategoriaProdutoCollection();
            for (SubcategoriaProduto subcategoriaProdutoCollectionSubcategoriaProduto : subcategoriaProdutoCollection) {
                subcategoriaProdutoCollectionSubcategoriaProduto.setCodigoCategoriaProduto(null);
                subcategoriaProdutoCollectionSubcategoriaProduto = em.merge(subcategoriaProdutoCollectionSubcategoriaProduto);
            }
            em.remove(categoriaProduto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CategoriaProduto> findCategoriaProdutoEntities() {
        return findCategoriaProdutoEntities(true, -1, -1);
    }

    public List<CategoriaProduto> findCategoriaProdutoEntities(int maxResults, int firstResult) {
        return findCategoriaProdutoEntities(false, maxResults, firstResult);
    }

    private List<CategoriaProduto> findCategoriaProdutoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CategoriaProduto.class));
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

    public CategoriaProduto findCategoriaProduto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CategoriaProduto.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaProdutoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CategoriaProduto> rt = cq.from(CategoriaProduto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
