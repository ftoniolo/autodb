/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.controller;

import br.com.autodb.model.controller.exceptions.NonexistentEntityException;
import br.com.autodb.model.dao.AnexoVeiculo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.autodb.model.dao.Veiculo;
import br.com.autodb.model.dao.TipoAnexo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class AnexoVeiculoJpaController1 implements Serializable {

    public AnexoVeiculoJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AnexoVeiculo anexoVeiculo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Veiculo codigoVeiculo = anexoVeiculo.getCodigoVeiculo();
            if (codigoVeiculo != null) {
                codigoVeiculo = em.getReference(codigoVeiculo.getClass(), codigoVeiculo.getCodigoVeiculo());
                anexoVeiculo.setCodigoVeiculo(codigoVeiculo);
            }
            TipoAnexo codigoTipoAnexo = anexoVeiculo.getCodigoTipoAnexo();
            if (codigoTipoAnexo != null) {
                codigoTipoAnexo = em.getReference(codigoTipoAnexo.getClass(), codigoTipoAnexo.getCodigoTipoAnexo());
                anexoVeiculo.setCodigoTipoAnexo(codigoTipoAnexo);
            }
            em.persist(anexoVeiculo);
            if (codigoVeiculo != null) {
                codigoVeiculo.getAnexoVeiculoCollection().add(anexoVeiculo);
                codigoVeiculo = em.merge(codigoVeiculo);
            }
            if (codigoTipoAnexo != null) {
                codigoTipoAnexo.getAnexoVeiculoCollection().add(anexoVeiculo);
                codigoTipoAnexo = em.merge(codigoTipoAnexo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AnexoVeiculo anexoVeiculo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AnexoVeiculo persistentAnexoVeiculo = em.find(AnexoVeiculo.class, anexoVeiculo.getCodigoAnexoVeiculo());
            Veiculo codigoVeiculoOld = persistentAnexoVeiculo.getCodigoVeiculo();
            Veiculo codigoVeiculoNew = anexoVeiculo.getCodigoVeiculo();
            TipoAnexo codigoTipoAnexoOld = persistentAnexoVeiculo.getCodigoTipoAnexo();
            TipoAnexo codigoTipoAnexoNew = anexoVeiculo.getCodigoTipoAnexo();
            if (codigoVeiculoNew != null) {
                codigoVeiculoNew = em.getReference(codigoVeiculoNew.getClass(), codigoVeiculoNew.getCodigoVeiculo());
                anexoVeiculo.setCodigoVeiculo(codigoVeiculoNew);
            }
            if (codigoTipoAnexoNew != null) {
                codigoTipoAnexoNew = em.getReference(codigoTipoAnexoNew.getClass(), codigoTipoAnexoNew.getCodigoTipoAnexo());
                anexoVeiculo.setCodigoTipoAnexo(codigoTipoAnexoNew);
            }
            anexoVeiculo = em.merge(anexoVeiculo);
            if (codigoVeiculoOld != null && !codigoVeiculoOld.equals(codigoVeiculoNew)) {
                codigoVeiculoOld.getAnexoVeiculoCollection().remove(anexoVeiculo);
                codigoVeiculoOld = em.merge(codigoVeiculoOld);
            }
            if (codigoVeiculoNew != null && !codigoVeiculoNew.equals(codigoVeiculoOld)) {
                codigoVeiculoNew.getAnexoVeiculoCollection().add(anexoVeiculo);
                codigoVeiculoNew = em.merge(codigoVeiculoNew);
            }
            if (codigoTipoAnexoOld != null && !codigoTipoAnexoOld.equals(codigoTipoAnexoNew)) {
                codigoTipoAnexoOld.getAnexoVeiculoCollection().remove(anexoVeiculo);
                codigoTipoAnexoOld = em.merge(codigoTipoAnexoOld);
            }
            if (codigoTipoAnexoNew != null && !codigoTipoAnexoNew.equals(codigoTipoAnexoOld)) {
                codigoTipoAnexoNew.getAnexoVeiculoCollection().add(anexoVeiculo);
                codigoTipoAnexoNew = em.merge(codigoTipoAnexoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = anexoVeiculo.getCodigoAnexoVeiculo();
                if (findAnexoVeiculo(id) == null) {
                    throw new NonexistentEntityException("The anexoVeiculo with id " + id + " no longer exists.");
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
            AnexoVeiculo anexoVeiculo;
            try {
                anexoVeiculo = em.getReference(AnexoVeiculo.class, id);
                anexoVeiculo.getCodigoAnexoVeiculo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The anexoVeiculo with id " + id + " no longer exists.", enfe);
            }
            Veiculo codigoVeiculo = anexoVeiculo.getCodigoVeiculo();
            if (codigoVeiculo != null) {
                codigoVeiculo.getAnexoVeiculoCollection().remove(anexoVeiculo);
                codigoVeiculo = em.merge(codigoVeiculo);
            }
            TipoAnexo codigoTipoAnexo = anexoVeiculo.getCodigoTipoAnexo();
            if (codigoTipoAnexo != null) {
                codigoTipoAnexo.getAnexoVeiculoCollection().remove(anexoVeiculo);
                codigoTipoAnexo = em.merge(codigoTipoAnexo);
            }
            em.remove(anexoVeiculo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AnexoVeiculo> findAnexoVeiculoEntities() {
        return findAnexoVeiculoEntities(true, -1, -1);
    }

    public List<AnexoVeiculo> findAnexoVeiculoEntities(int maxResults, int firstResult) {
        return findAnexoVeiculoEntities(false, maxResults, firstResult);
    }

    private List<AnexoVeiculo> findAnexoVeiculoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AnexoVeiculo.class));
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

    public AnexoVeiculo findAnexoVeiculo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AnexoVeiculo.class, id);
        } finally {
            em.close();
        }
    }

    public int getAnexoVeiculoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AnexoVeiculo> rt = cq.from(AnexoVeiculo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
