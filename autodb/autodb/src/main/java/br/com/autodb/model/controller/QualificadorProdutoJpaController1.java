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
import br.com.autodb.model.dao.UnidadeMedida;
import br.com.autodb.model.dao.Qualificador;
import br.com.autodb.model.dao.Produto;
import br.com.autodb.model.dao.QualificadorProduto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class QualificadorProdutoJpaController1 implements Serializable {

    public QualificadorProdutoJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(QualificadorProduto qualificadorProduto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UnidadeMedida codigoUnidadeMedida = qualificadorProduto.getCodigoUnidadeMedida();
            if (codigoUnidadeMedida != null) {
                codigoUnidadeMedida = em.getReference(codigoUnidadeMedida.getClass(), codigoUnidadeMedida.getCodigoUnidadeMedida());
                qualificadorProduto.setCodigoUnidadeMedida(codigoUnidadeMedida);
            }
            Qualificador codigoQualificador = qualificadorProduto.getCodigoQualificador();
            if (codigoQualificador != null) {
                codigoQualificador = em.getReference(codigoQualificador.getClass(), codigoQualificador.getCodigoQualificador());
                qualificadorProduto.setCodigoQualificador(codigoQualificador);
            }
            Produto codigoProduto = qualificadorProduto.getCodigoProduto();
            if (codigoProduto != null) {
                codigoProduto = em.getReference(codigoProduto.getClass(), codigoProduto.getCodigoProduto());
                qualificadorProduto.setCodigoProduto(codigoProduto);
            }
            em.persist(qualificadorProduto);
            if (codigoUnidadeMedida != null) {
                codigoUnidadeMedida.getQualificadorProdutoCollection().add(qualificadorProduto);
                codigoUnidadeMedida = em.merge(codigoUnidadeMedida);
            }
            if (codigoQualificador != null) {
                codigoQualificador.getQualificadorProdutoCollection().add(qualificadorProduto);
                codigoQualificador = em.merge(codigoQualificador);
            }
            if (codigoProduto != null) {
                codigoProduto.getQualificadorProdutoCollection().add(qualificadorProduto);
                codigoProduto = em.merge(codigoProduto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(QualificadorProduto qualificadorProduto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QualificadorProduto persistentQualificadorProduto = em.find(QualificadorProduto.class, qualificadorProduto.getCodigoQualificadorProduto());
            UnidadeMedida codigoUnidadeMedidaOld = persistentQualificadorProduto.getCodigoUnidadeMedida();
            UnidadeMedida codigoUnidadeMedidaNew = qualificadorProduto.getCodigoUnidadeMedida();
            Qualificador codigoQualificadorOld = persistentQualificadorProduto.getCodigoQualificador();
            Qualificador codigoQualificadorNew = qualificadorProduto.getCodigoQualificador();
            Produto codigoProdutoOld = persistentQualificadorProduto.getCodigoProduto();
            Produto codigoProdutoNew = qualificadorProduto.getCodigoProduto();
            if (codigoUnidadeMedidaNew != null) {
                codigoUnidadeMedidaNew = em.getReference(codigoUnidadeMedidaNew.getClass(), codigoUnidadeMedidaNew.getCodigoUnidadeMedida());
                qualificadorProduto.setCodigoUnidadeMedida(codigoUnidadeMedidaNew);
            }
            if (codigoQualificadorNew != null) {
                codigoQualificadorNew = em.getReference(codigoQualificadorNew.getClass(), codigoQualificadorNew.getCodigoQualificador());
                qualificadorProduto.setCodigoQualificador(codigoQualificadorNew);
            }
            if (codigoProdutoNew != null) {
                codigoProdutoNew = em.getReference(codigoProdutoNew.getClass(), codigoProdutoNew.getCodigoProduto());
                qualificadorProduto.setCodigoProduto(codigoProdutoNew);
            }
            qualificadorProduto = em.merge(qualificadorProduto);
            if (codigoUnidadeMedidaOld != null && !codigoUnidadeMedidaOld.equals(codigoUnidadeMedidaNew)) {
                codigoUnidadeMedidaOld.getQualificadorProdutoCollection().remove(qualificadorProduto);
                codigoUnidadeMedidaOld = em.merge(codigoUnidadeMedidaOld);
            }
            if (codigoUnidadeMedidaNew != null && !codigoUnidadeMedidaNew.equals(codigoUnidadeMedidaOld)) {
                codigoUnidadeMedidaNew.getQualificadorProdutoCollection().add(qualificadorProduto);
                codigoUnidadeMedidaNew = em.merge(codigoUnidadeMedidaNew);
            }
            if (codigoQualificadorOld != null && !codigoQualificadorOld.equals(codigoQualificadorNew)) {
                codigoQualificadorOld.getQualificadorProdutoCollection().remove(qualificadorProduto);
                codigoQualificadorOld = em.merge(codigoQualificadorOld);
            }
            if (codigoQualificadorNew != null && !codigoQualificadorNew.equals(codigoQualificadorOld)) {
                codigoQualificadorNew.getQualificadorProdutoCollection().add(qualificadorProduto);
                codigoQualificadorNew = em.merge(codigoQualificadorNew);
            }
            if (codigoProdutoOld != null && !codigoProdutoOld.equals(codigoProdutoNew)) {
                codigoProdutoOld.getQualificadorProdutoCollection().remove(qualificadorProduto);
                codigoProdutoOld = em.merge(codigoProdutoOld);
            }
            if (codigoProdutoNew != null && !codigoProdutoNew.equals(codigoProdutoOld)) {
                codigoProdutoNew.getQualificadorProdutoCollection().add(qualificadorProduto);
                codigoProdutoNew = em.merge(codigoProdutoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = qualificadorProduto.getCodigoQualificadorProduto();
                if (findQualificadorProduto(id) == null) {
                    throw new NonexistentEntityException("The qualificadorProduto with id " + id + " no longer exists.");
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
            QualificadorProduto qualificadorProduto;
            try {
                qualificadorProduto = em.getReference(QualificadorProduto.class, id);
                qualificadorProduto.getCodigoQualificadorProduto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The qualificadorProduto with id " + id + " no longer exists.", enfe);
            }
            UnidadeMedida codigoUnidadeMedida = qualificadorProduto.getCodigoUnidadeMedida();
            if (codigoUnidadeMedida != null) {
                codigoUnidadeMedida.getQualificadorProdutoCollection().remove(qualificadorProduto);
                codigoUnidadeMedida = em.merge(codigoUnidadeMedida);
            }
            Qualificador codigoQualificador = qualificadorProduto.getCodigoQualificador();
            if (codigoQualificador != null) {
                codigoQualificador.getQualificadorProdutoCollection().remove(qualificadorProduto);
                codigoQualificador = em.merge(codigoQualificador);
            }
            Produto codigoProduto = qualificadorProduto.getCodigoProduto();
            if (codigoProduto != null) {
                codigoProduto.getQualificadorProdutoCollection().remove(qualificadorProduto);
                codigoProduto = em.merge(codigoProduto);
            }
            em.remove(qualificadorProduto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<QualificadorProduto> findQualificadorProdutoEntities() {
        return findQualificadorProdutoEntities(true, -1, -1);
    }

    public List<QualificadorProduto> findQualificadorProdutoEntities(int maxResults, int firstResult) {
        return findQualificadorProdutoEntities(false, maxResults, firstResult);
    }

    private List<QualificadorProduto> findQualificadorProdutoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(QualificadorProduto.class));
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

    public QualificadorProduto findQualificadorProduto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(QualificadorProduto.class, id);
        } finally {
            em.close();
        }
    }

    public int getQualificadorProdutoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<QualificadorProduto> rt = cq.from(QualificadorProduto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
