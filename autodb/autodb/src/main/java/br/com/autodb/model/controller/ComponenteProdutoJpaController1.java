/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.controller;

import br.com.autodb.model.controller.exceptions.NonexistentEntityException;
import br.com.autodb.model.dao.ComponenteProduto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.autodb.model.dao.Produto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class ComponenteProdutoJpaController1 implements Serializable {

    public ComponenteProdutoJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ComponenteProduto componenteProduto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produto codigoProdutoFilho = componenteProduto.getCodigoProdutoFilho();
            if (codigoProdutoFilho != null) {
                codigoProdutoFilho = em.getReference(codigoProdutoFilho.getClass(), codigoProdutoFilho.getCodigoProduto());
                componenteProduto.setCodigoProdutoFilho(codigoProdutoFilho);
            }
            Produto codigoProdutoPai = componenteProduto.getCodigoProdutoPai();
            if (codigoProdutoPai != null) {
                codigoProdutoPai = em.getReference(codigoProdutoPai.getClass(), codigoProdutoPai.getCodigoProduto());
                componenteProduto.setCodigoProdutoPai(codigoProdutoPai);
            }
            em.persist(componenteProduto);
            if (codigoProdutoFilho != null) {
                codigoProdutoFilho.getComponenteProdutoCollection().add(componenteProduto);
                codigoProdutoFilho = em.merge(codigoProdutoFilho);
            }
            if (codigoProdutoPai != null) {
                codigoProdutoPai.getComponenteProdutoCollection().add(componenteProduto);
                codigoProdutoPai = em.merge(codigoProdutoPai);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ComponenteProduto componenteProduto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ComponenteProduto persistentComponenteProduto = em.find(ComponenteProduto.class, componenteProduto.getCodigoProdutoProduto());
            Produto codigoProdutoFilhoOld = persistentComponenteProduto.getCodigoProdutoFilho();
            Produto codigoProdutoFilhoNew = componenteProduto.getCodigoProdutoFilho();
            Produto codigoProdutoPaiOld = persistentComponenteProduto.getCodigoProdutoPai();
            Produto codigoProdutoPaiNew = componenteProduto.getCodigoProdutoPai();
            if (codigoProdutoFilhoNew != null) {
                codigoProdutoFilhoNew = em.getReference(codigoProdutoFilhoNew.getClass(), codigoProdutoFilhoNew.getCodigoProduto());
                componenteProduto.setCodigoProdutoFilho(codigoProdutoFilhoNew);
            }
            if (codigoProdutoPaiNew != null) {
                codigoProdutoPaiNew = em.getReference(codigoProdutoPaiNew.getClass(), codigoProdutoPaiNew.getCodigoProduto());
                componenteProduto.setCodigoProdutoPai(codigoProdutoPaiNew);
            }
            componenteProduto = em.merge(componenteProduto);
            if (codigoProdutoFilhoOld != null && !codigoProdutoFilhoOld.equals(codigoProdutoFilhoNew)) {
                codigoProdutoFilhoOld.getComponenteProdutoCollection().remove(componenteProduto);
                codigoProdutoFilhoOld = em.merge(codigoProdutoFilhoOld);
            }
            if (codigoProdutoFilhoNew != null && !codigoProdutoFilhoNew.equals(codigoProdutoFilhoOld)) {
                codigoProdutoFilhoNew.getComponenteProdutoCollection().add(componenteProduto);
                codigoProdutoFilhoNew = em.merge(codigoProdutoFilhoNew);
            }
            if (codigoProdutoPaiOld != null && !codigoProdutoPaiOld.equals(codigoProdutoPaiNew)) {
                codigoProdutoPaiOld.getComponenteProdutoCollection().remove(componenteProduto);
                codigoProdutoPaiOld = em.merge(codigoProdutoPaiOld);
            }
            if (codigoProdutoPaiNew != null && !codigoProdutoPaiNew.equals(codigoProdutoPaiOld)) {
                codigoProdutoPaiNew.getComponenteProdutoCollection().add(componenteProduto);
                codigoProdutoPaiNew = em.merge(codigoProdutoPaiNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = componenteProduto.getCodigoProdutoProduto();
                if (findComponenteProduto(id) == null) {
                    throw new NonexistentEntityException("The componenteProduto with id " + id + " no longer exists.");
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
            ComponenteProduto componenteProduto;
            try {
                componenteProduto = em.getReference(ComponenteProduto.class, id);
                componenteProduto.getCodigoProdutoProduto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The componenteProduto with id " + id + " no longer exists.", enfe);
            }
            Produto codigoProdutoFilho = componenteProduto.getCodigoProdutoFilho();
            if (codigoProdutoFilho != null) {
                codigoProdutoFilho.getComponenteProdutoCollection().remove(componenteProduto);
                codigoProdutoFilho = em.merge(codigoProdutoFilho);
            }
            Produto codigoProdutoPai = componenteProduto.getCodigoProdutoPai();
            if (codigoProdutoPai != null) {
                codigoProdutoPai.getComponenteProdutoCollection().remove(componenteProduto);
                codigoProdutoPai = em.merge(codigoProdutoPai);
            }
            em.remove(componenteProduto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ComponenteProduto> findComponenteProdutoEntities() {
        return findComponenteProdutoEntities(true, -1, -1);
    }

    public List<ComponenteProduto> findComponenteProdutoEntities(int maxResults, int firstResult) {
        return findComponenteProdutoEntities(false, maxResults, firstResult);
    }

    private List<ComponenteProduto> findComponenteProdutoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ComponenteProduto.class));
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

    public ComponenteProduto findComponenteProduto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ComponenteProduto.class, id);
        } finally {
            em.close();
        }
    }

    public int getComponenteProdutoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ComponenteProduto> rt = cq.from(ComponenteProduto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
