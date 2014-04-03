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
import br.com.autodb.model.dao.AnexoVeiculo;
import br.com.autodb.model.dao.TipoAnexo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class TipoAnexoJpaController1 implements Serializable {

    public TipoAnexoJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoAnexo tipoAnexo) {
        if (tipoAnexo.getAnexoVeiculoCollection() == null) {
            tipoAnexo.setAnexoVeiculoCollection(new ArrayList<AnexoVeiculo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<AnexoVeiculo> attachedAnexoVeiculoCollection = new ArrayList<AnexoVeiculo>();
            for (AnexoVeiculo anexoVeiculoCollectionAnexoVeiculoToAttach : tipoAnexo.getAnexoVeiculoCollection()) {
                anexoVeiculoCollectionAnexoVeiculoToAttach = em.getReference(anexoVeiculoCollectionAnexoVeiculoToAttach.getClass(), anexoVeiculoCollectionAnexoVeiculoToAttach.getCodigoAnexoVeiculo());
                attachedAnexoVeiculoCollection.add(anexoVeiculoCollectionAnexoVeiculoToAttach);
            }
            tipoAnexo.setAnexoVeiculoCollection(attachedAnexoVeiculoCollection);
            em.persist(tipoAnexo);
            for (AnexoVeiculo anexoVeiculoCollectionAnexoVeiculo : tipoAnexo.getAnexoVeiculoCollection()) {
                TipoAnexo oldCodigoTipoAnexoOfAnexoVeiculoCollectionAnexoVeiculo = anexoVeiculoCollectionAnexoVeiculo.getCodigoTipoAnexo();
                anexoVeiculoCollectionAnexoVeiculo.setCodigoTipoAnexo(tipoAnexo);
                anexoVeiculoCollectionAnexoVeiculo = em.merge(anexoVeiculoCollectionAnexoVeiculo);
                if (oldCodigoTipoAnexoOfAnexoVeiculoCollectionAnexoVeiculo != null) {
                    oldCodigoTipoAnexoOfAnexoVeiculoCollectionAnexoVeiculo.getAnexoVeiculoCollection().remove(anexoVeiculoCollectionAnexoVeiculo);
                    oldCodigoTipoAnexoOfAnexoVeiculoCollectionAnexoVeiculo = em.merge(oldCodigoTipoAnexoOfAnexoVeiculoCollectionAnexoVeiculo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoAnexo tipoAnexo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoAnexo persistentTipoAnexo = em.find(TipoAnexo.class, tipoAnexo.getCodigoTipoAnexo());
            Collection<AnexoVeiculo> anexoVeiculoCollectionOld = persistentTipoAnexo.getAnexoVeiculoCollection();
            Collection<AnexoVeiculo> anexoVeiculoCollectionNew = tipoAnexo.getAnexoVeiculoCollection();
            List<String> illegalOrphanMessages = null;
            for (AnexoVeiculo anexoVeiculoCollectionOldAnexoVeiculo : anexoVeiculoCollectionOld) {
                if (!anexoVeiculoCollectionNew.contains(anexoVeiculoCollectionOldAnexoVeiculo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AnexoVeiculo " + anexoVeiculoCollectionOldAnexoVeiculo + " since its codigoTipoAnexo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<AnexoVeiculo> attachedAnexoVeiculoCollectionNew = new ArrayList<AnexoVeiculo>();
            for (AnexoVeiculo anexoVeiculoCollectionNewAnexoVeiculoToAttach : anexoVeiculoCollectionNew) {
                anexoVeiculoCollectionNewAnexoVeiculoToAttach = em.getReference(anexoVeiculoCollectionNewAnexoVeiculoToAttach.getClass(), anexoVeiculoCollectionNewAnexoVeiculoToAttach.getCodigoAnexoVeiculo());
                attachedAnexoVeiculoCollectionNew.add(anexoVeiculoCollectionNewAnexoVeiculoToAttach);
            }
            anexoVeiculoCollectionNew = attachedAnexoVeiculoCollectionNew;
            tipoAnexo.setAnexoVeiculoCollection(anexoVeiculoCollectionNew);
            tipoAnexo = em.merge(tipoAnexo);
            for (AnexoVeiculo anexoVeiculoCollectionNewAnexoVeiculo : anexoVeiculoCollectionNew) {
                if (!anexoVeiculoCollectionOld.contains(anexoVeiculoCollectionNewAnexoVeiculo)) {
                    TipoAnexo oldCodigoTipoAnexoOfAnexoVeiculoCollectionNewAnexoVeiculo = anexoVeiculoCollectionNewAnexoVeiculo.getCodigoTipoAnexo();
                    anexoVeiculoCollectionNewAnexoVeiculo.setCodigoTipoAnexo(tipoAnexo);
                    anexoVeiculoCollectionNewAnexoVeiculo = em.merge(anexoVeiculoCollectionNewAnexoVeiculo);
                    if (oldCodigoTipoAnexoOfAnexoVeiculoCollectionNewAnexoVeiculo != null && !oldCodigoTipoAnexoOfAnexoVeiculoCollectionNewAnexoVeiculo.equals(tipoAnexo)) {
                        oldCodigoTipoAnexoOfAnexoVeiculoCollectionNewAnexoVeiculo.getAnexoVeiculoCollection().remove(anexoVeiculoCollectionNewAnexoVeiculo);
                        oldCodigoTipoAnexoOfAnexoVeiculoCollectionNewAnexoVeiculo = em.merge(oldCodigoTipoAnexoOfAnexoVeiculoCollectionNewAnexoVeiculo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoAnexo.getCodigoTipoAnexo();
                if (findTipoAnexo(id) == null) {
                    throw new NonexistentEntityException("The tipoAnexo with id " + id + " no longer exists.");
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
            TipoAnexo tipoAnexo;
            try {
                tipoAnexo = em.getReference(TipoAnexo.class, id);
                tipoAnexo.getCodigoTipoAnexo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoAnexo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<AnexoVeiculo> anexoVeiculoCollectionOrphanCheck = tipoAnexo.getAnexoVeiculoCollection();
            for (AnexoVeiculo anexoVeiculoCollectionOrphanCheckAnexoVeiculo : anexoVeiculoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoAnexo (" + tipoAnexo + ") cannot be destroyed since the AnexoVeiculo " + anexoVeiculoCollectionOrphanCheckAnexoVeiculo + " in its anexoVeiculoCollection field has a non-nullable codigoTipoAnexo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoAnexo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoAnexo> findTipoAnexoEntities() {
        return findTipoAnexoEntities(true, -1, -1);
    }

    public List<TipoAnexo> findTipoAnexoEntities(int maxResults, int firstResult) {
        return findTipoAnexoEntities(false, maxResults, firstResult);
    }

    private List<TipoAnexo> findTipoAnexoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoAnexo.class));
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

    public TipoAnexo findTipoAnexo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoAnexo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoAnexoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoAnexo> rt = cq.from(TipoAnexo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
