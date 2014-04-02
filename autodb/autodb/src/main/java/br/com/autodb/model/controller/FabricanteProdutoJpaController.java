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
import br.com.autodb.model.dao.Produto;
import br.com.autodb.model.dao.Fabricante;
import br.com.autodb.model.dao.FabricanteProduto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class FabricanteProdutoJpaController implements Serializable {

    public FabricanteProdutoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FabricanteProduto fabricanteProduto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produto codigoProduto = fabricanteProduto.getCodigoProduto();
            if (codigoProduto != null) {
                codigoProduto = em.getReference(codigoProduto.getClass(), codigoProduto.getCodigoProduto());
                fabricanteProduto.setCodigoProduto(codigoProduto);
            }
            Fabricante codigoFabricante = fabricanteProduto.getCodigoFabricante();
            if (codigoFabricante != null) {
                codigoFabricante = em.getReference(codigoFabricante.getClass(), codigoFabricante.getCodigoFabricante());
                fabricanteProduto.setCodigoFabricante(codigoFabricante);
            }
            em.persist(fabricanteProduto);
            if (codigoProduto != null) {
                codigoProduto.getFabricanteProdutoCollection().add(fabricanteProduto);
                codigoProduto = em.merge(codigoProduto);
            }
            if (codigoFabricante != null) {
                codigoFabricante.getFabricanteProdutoCollection().add(fabricanteProduto);
                codigoFabricante = em.merge(codigoFabricante);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FabricanteProduto fabricanteProduto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FabricanteProduto persistentFabricanteProduto = em.find(FabricanteProduto.class, fabricanteProduto.getCodigoFabricanteProduto());
            Produto codigoProdutoOld = persistentFabricanteProduto.getCodigoProduto();
            Produto codigoProdutoNew = fabricanteProduto.getCodigoProduto();
            Fabricante codigoFabricanteOld = persistentFabricanteProduto.getCodigoFabricante();
            Fabricante codigoFabricanteNew = fabricanteProduto.getCodigoFabricante();
            if (codigoProdutoNew != null) {
                codigoProdutoNew = em.getReference(codigoProdutoNew.getClass(), codigoProdutoNew.getCodigoProduto());
                fabricanteProduto.setCodigoProduto(codigoProdutoNew);
            }
            if (codigoFabricanteNew != null) {
                codigoFabricanteNew = em.getReference(codigoFabricanteNew.getClass(), codigoFabricanteNew.getCodigoFabricante());
                fabricanteProduto.setCodigoFabricante(codigoFabricanteNew);
            }
            fabricanteProduto = em.merge(fabricanteProduto);
            if (codigoProdutoOld != null && !codigoProdutoOld.equals(codigoProdutoNew)) {
                codigoProdutoOld.getFabricanteProdutoCollection().remove(fabricanteProduto);
                codigoProdutoOld = em.merge(codigoProdutoOld);
            }
            if (codigoProdutoNew != null && !codigoProdutoNew.equals(codigoProdutoOld)) {
                codigoProdutoNew.getFabricanteProdutoCollection().add(fabricanteProduto);
                codigoProdutoNew = em.merge(codigoProdutoNew);
            }
            if (codigoFabricanteOld != null && !codigoFabricanteOld.equals(codigoFabricanteNew)) {
                codigoFabricanteOld.getFabricanteProdutoCollection().remove(fabricanteProduto);
                codigoFabricanteOld = em.merge(codigoFabricanteOld);
            }
            if (codigoFabricanteNew != null && !codigoFabricanteNew.equals(codigoFabricanteOld)) {
                codigoFabricanteNew.getFabricanteProdutoCollection().add(fabricanteProduto);
                codigoFabricanteNew = em.merge(codigoFabricanteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fabricanteProduto.getCodigoFabricanteProduto();
                if (findFabricanteProduto(id) == null) {
                    throw new NonexistentEntityException("The fabricanteProduto with id " + id + " no longer exists.");
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
            FabricanteProduto fabricanteProduto;
            try {
                fabricanteProduto = em.getReference(FabricanteProduto.class, id);
                fabricanteProduto.getCodigoFabricanteProduto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fabricanteProduto with id " + id + " no longer exists.", enfe);
            }
            Produto codigoProduto = fabricanteProduto.getCodigoProduto();
            if (codigoProduto != null) {
                codigoProduto.getFabricanteProdutoCollection().remove(fabricanteProduto);
                codigoProduto = em.merge(codigoProduto);
            }
            Fabricante codigoFabricante = fabricanteProduto.getCodigoFabricante();
            if (codigoFabricante != null) {
                codigoFabricante.getFabricanteProdutoCollection().remove(fabricanteProduto);
                codigoFabricante = em.merge(codigoFabricante);
            }
            em.remove(fabricanteProduto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FabricanteProduto> findFabricanteProdutoEntities() {
        return findFabricanteProdutoEntities(true, -1, -1);
    }

    public List<FabricanteProduto> findFabricanteProdutoEntities(int maxResults, int firstResult) {
        return findFabricanteProdutoEntities(false, maxResults, firstResult);
    }

    private List<FabricanteProduto> findFabricanteProdutoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FabricanteProduto.class));
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

    public FabricanteProduto findFabricanteProduto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FabricanteProduto.class, id);
        } finally {
            em.close();
        }
    }

    public int getFabricanteProdutoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FabricanteProduto> rt = cq.from(FabricanteProduto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
