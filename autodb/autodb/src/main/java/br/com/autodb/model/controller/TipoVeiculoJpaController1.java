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
import br.com.autodb.model.dao.LinhaVeiculo;
import br.com.autodb.model.dao.TipoVeiculo;
import br.com.autodb.model.dao.VeiculoBase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class TipoVeiculoJpaController1 implements Serializable {

    public TipoVeiculoJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoVeiculo tipoVeiculo) {
        if (tipoVeiculo.getVeiculoBaseCollection() == null) {
            tipoVeiculo.setVeiculoBaseCollection(new ArrayList<VeiculoBase>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LinhaVeiculo codigoLinhaVeiculo = tipoVeiculo.getCodigoLinhaVeiculo();
            if (codigoLinhaVeiculo != null) {
                codigoLinhaVeiculo = em.getReference(codigoLinhaVeiculo.getClass(), codigoLinhaVeiculo.getCodigoLinhaVeiculo());
                tipoVeiculo.setCodigoLinhaVeiculo(codigoLinhaVeiculo);
            }
            Collection<VeiculoBase> attachedVeiculoBaseCollection = new ArrayList<VeiculoBase>();
            for (VeiculoBase veiculoBaseCollectionVeiculoBaseToAttach : tipoVeiculo.getVeiculoBaseCollection()) {
                veiculoBaseCollectionVeiculoBaseToAttach = em.getReference(veiculoBaseCollectionVeiculoBaseToAttach.getClass(), veiculoBaseCollectionVeiculoBaseToAttach.getCodigoVeiculoBase());
                attachedVeiculoBaseCollection.add(veiculoBaseCollectionVeiculoBaseToAttach);
            }
            tipoVeiculo.setVeiculoBaseCollection(attachedVeiculoBaseCollection);
            em.persist(tipoVeiculo);
            if (codigoLinhaVeiculo != null) {
                codigoLinhaVeiculo.getTipoVeiculoCollection().add(tipoVeiculo);
                codigoLinhaVeiculo = em.merge(codigoLinhaVeiculo);
            }
            for (VeiculoBase veiculoBaseCollectionVeiculoBase : tipoVeiculo.getVeiculoBaseCollection()) {
                TipoVeiculo oldCodigoTipoVeiculoOfVeiculoBaseCollectionVeiculoBase = veiculoBaseCollectionVeiculoBase.getCodigoTipoVeiculo();
                veiculoBaseCollectionVeiculoBase.setCodigoTipoVeiculo(tipoVeiculo);
                veiculoBaseCollectionVeiculoBase = em.merge(veiculoBaseCollectionVeiculoBase);
                if (oldCodigoTipoVeiculoOfVeiculoBaseCollectionVeiculoBase != null) {
                    oldCodigoTipoVeiculoOfVeiculoBaseCollectionVeiculoBase.getVeiculoBaseCollection().remove(veiculoBaseCollectionVeiculoBase);
                    oldCodigoTipoVeiculoOfVeiculoBaseCollectionVeiculoBase = em.merge(oldCodigoTipoVeiculoOfVeiculoBaseCollectionVeiculoBase);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoVeiculo tipoVeiculo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoVeiculo persistentTipoVeiculo = em.find(TipoVeiculo.class, tipoVeiculo.getCodigoTipoVeiculo());
            LinhaVeiculo codigoLinhaVeiculoOld = persistentTipoVeiculo.getCodigoLinhaVeiculo();
            LinhaVeiculo codigoLinhaVeiculoNew = tipoVeiculo.getCodigoLinhaVeiculo();
            Collection<VeiculoBase> veiculoBaseCollectionOld = persistentTipoVeiculo.getVeiculoBaseCollection();
            Collection<VeiculoBase> veiculoBaseCollectionNew = tipoVeiculo.getVeiculoBaseCollection();
            List<String> illegalOrphanMessages = null;
            for (VeiculoBase veiculoBaseCollectionOldVeiculoBase : veiculoBaseCollectionOld) {
                if (!veiculoBaseCollectionNew.contains(veiculoBaseCollectionOldVeiculoBase)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain VeiculoBase " + veiculoBaseCollectionOldVeiculoBase + " since its codigoTipoVeiculo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codigoLinhaVeiculoNew != null) {
                codigoLinhaVeiculoNew = em.getReference(codigoLinhaVeiculoNew.getClass(), codigoLinhaVeiculoNew.getCodigoLinhaVeiculo());
                tipoVeiculo.setCodigoLinhaVeiculo(codigoLinhaVeiculoNew);
            }
            Collection<VeiculoBase> attachedVeiculoBaseCollectionNew = new ArrayList<VeiculoBase>();
            for (VeiculoBase veiculoBaseCollectionNewVeiculoBaseToAttach : veiculoBaseCollectionNew) {
                veiculoBaseCollectionNewVeiculoBaseToAttach = em.getReference(veiculoBaseCollectionNewVeiculoBaseToAttach.getClass(), veiculoBaseCollectionNewVeiculoBaseToAttach.getCodigoVeiculoBase());
                attachedVeiculoBaseCollectionNew.add(veiculoBaseCollectionNewVeiculoBaseToAttach);
            }
            veiculoBaseCollectionNew = attachedVeiculoBaseCollectionNew;
            tipoVeiculo.setVeiculoBaseCollection(veiculoBaseCollectionNew);
            tipoVeiculo = em.merge(tipoVeiculo);
            if (codigoLinhaVeiculoOld != null && !codigoLinhaVeiculoOld.equals(codigoLinhaVeiculoNew)) {
                codigoLinhaVeiculoOld.getTipoVeiculoCollection().remove(tipoVeiculo);
                codigoLinhaVeiculoOld = em.merge(codigoLinhaVeiculoOld);
            }
            if (codigoLinhaVeiculoNew != null && !codigoLinhaVeiculoNew.equals(codigoLinhaVeiculoOld)) {
                codigoLinhaVeiculoNew.getTipoVeiculoCollection().add(tipoVeiculo);
                codigoLinhaVeiculoNew = em.merge(codigoLinhaVeiculoNew);
            }
            for (VeiculoBase veiculoBaseCollectionNewVeiculoBase : veiculoBaseCollectionNew) {
                if (!veiculoBaseCollectionOld.contains(veiculoBaseCollectionNewVeiculoBase)) {
                    TipoVeiculo oldCodigoTipoVeiculoOfVeiculoBaseCollectionNewVeiculoBase = veiculoBaseCollectionNewVeiculoBase.getCodigoTipoVeiculo();
                    veiculoBaseCollectionNewVeiculoBase.setCodigoTipoVeiculo(tipoVeiculo);
                    veiculoBaseCollectionNewVeiculoBase = em.merge(veiculoBaseCollectionNewVeiculoBase);
                    if (oldCodigoTipoVeiculoOfVeiculoBaseCollectionNewVeiculoBase != null && !oldCodigoTipoVeiculoOfVeiculoBaseCollectionNewVeiculoBase.equals(tipoVeiculo)) {
                        oldCodigoTipoVeiculoOfVeiculoBaseCollectionNewVeiculoBase.getVeiculoBaseCollection().remove(veiculoBaseCollectionNewVeiculoBase);
                        oldCodigoTipoVeiculoOfVeiculoBaseCollectionNewVeiculoBase = em.merge(oldCodigoTipoVeiculoOfVeiculoBaseCollectionNewVeiculoBase);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoVeiculo.getCodigoTipoVeiculo();
                if (findTipoVeiculo(id) == null) {
                    throw new NonexistentEntityException("The tipoVeiculo with id " + id + " no longer exists.");
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
            TipoVeiculo tipoVeiculo;
            try {
                tipoVeiculo = em.getReference(TipoVeiculo.class, id);
                tipoVeiculo.getCodigoTipoVeiculo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoVeiculo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<VeiculoBase> veiculoBaseCollectionOrphanCheck = tipoVeiculo.getVeiculoBaseCollection();
            for (VeiculoBase veiculoBaseCollectionOrphanCheckVeiculoBase : veiculoBaseCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoVeiculo (" + tipoVeiculo + ") cannot be destroyed since the VeiculoBase " + veiculoBaseCollectionOrphanCheckVeiculoBase + " in its veiculoBaseCollection field has a non-nullable codigoTipoVeiculo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            LinhaVeiculo codigoLinhaVeiculo = tipoVeiculo.getCodigoLinhaVeiculo();
            if (codigoLinhaVeiculo != null) {
                codigoLinhaVeiculo.getTipoVeiculoCollection().remove(tipoVeiculo);
                codigoLinhaVeiculo = em.merge(codigoLinhaVeiculo);
            }
            em.remove(tipoVeiculo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoVeiculo> findTipoVeiculoEntities() {
        return findTipoVeiculoEntities(true, -1, -1);
    }

    public List<TipoVeiculo> findTipoVeiculoEntities(int maxResults, int firstResult) {
        return findTipoVeiculoEntities(false, maxResults, firstResult);
    }

    private List<TipoVeiculo> findTipoVeiculoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoVeiculo.class));
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

    public TipoVeiculo findTipoVeiculo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoVeiculo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoVeiculoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoVeiculo> rt = cq.from(TipoVeiculo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
