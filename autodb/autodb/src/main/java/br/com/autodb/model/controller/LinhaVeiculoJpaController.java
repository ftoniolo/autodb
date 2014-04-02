/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.controller;

import br.com.autodb.model.controller.exceptions.IllegalOrphanException;
import br.com.autodb.model.controller.exceptions.NonexistentEntityException;
import br.com.autodb.model.dao.LinhaVeiculo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.autodb.model.dao.TipoVeiculo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class LinhaVeiculoJpaController implements Serializable {

    public LinhaVeiculoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LinhaVeiculo linhaVeiculo) {
        if (linhaVeiculo.getTipoVeiculoCollection() == null) {
            linhaVeiculo.setTipoVeiculoCollection(new ArrayList<TipoVeiculo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<TipoVeiculo> attachedTipoVeiculoCollection = new ArrayList<TipoVeiculo>();
            for (TipoVeiculo tipoVeiculoCollectionTipoVeiculoToAttach : linhaVeiculo.getTipoVeiculoCollection()) {
                tipoVeiculoCollectionTipoVeiculoToAttach = em.getReference(tipoVeiculoCollectionTipoVeiculoToAttach.getClass(), tipoVeiculoCollectionTipoVeiculoToAttach.getCodigoTipoVeiculo());
                attachedTipoVeiculoCollection.add(tipoVeiculoCollectionTipoVeiculoToAttach);
            }
            linhaVeiculo.setTipoVeiculoCollection(attachedTipoVeiculoCollection);
            em.persist(linhaVeiculo);
            for (TipoVeiculo tipoVeiculoCollectionTipoVeiculo : linhaVeiculo.getTipoVeiculoCollection()) {
                LinhaVeiculo oldCodigoLinhaVeiculoOfTipoVeiculoCollectionTipoVeiculo = tipoVeiculoCollectionTipoVeiculo.getCodigoLinhaVeiculo();
                tipoVeiculoCollectionTipoVeiculo.setCodigoLinhaVeiculo(linhaVeiculo);
                tipoVeiculoCollectionTipoVeiculo = em.merge(tipoVeiculoCollectionTipoVeiculo);
                if (oldCodigoLinhaVeiculoOfTipoVeiculoCollectionTipoVeiculo != null) {
                    oldCodigoLinhaVeiculoOfTipoVeiculoCollectionTipoVeiculo.getTipoVeiculoCollection().remove(tipoVeiculoCollectionTipoVeiculo);
                    oldCodigoLinhaVeiculoOfTipoVeiculoCollectionTipoVeiculo = em.merge(oldCodigoLinhaVeiculoOfTipoVeiculoCollectionTipoVeiculo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LinhaVeiculo linhaVeiculo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LinhaVeiculo persistentLinhaVeiculo = em.find(LinhaVeiculo.class, linhaVeiculo.getCodigoLinhaVeiculo());
            Collection<TipoVeiculo> tipoVeiculoCollectionOld = persistentLinhaVeiculo.getTipoVeiculoCollection();
            Collection<TipoVeiculo> tipoVeiculoCollectionNew = linhaVeiculo.getTipoVeiculoCollection();
            List<String> illegalOrphanMessages = null;
            for (TipoVeiculo tipoVeiculoCollectionOldTipoVeiculo : tipoVeiculoCollectionOld) {
                if (!tipoVeiculoCollectionNew.contains(tipoVeiculoCollectionOldTipoVeiculo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TipoVeiculo " + tipoVeiculoCollectionOldTipoVeiculo + " since its codigoLinhaVeiculo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TipoVeiculo> attachedTipoVeiculoCollectionNew = new ArrayList<TipoVeiculo>();
            for (TipoVeiculo tipoVeiculoCollectionNewTipoVeiculoToAttach : tipoVeiculoCollectionNew) {
                tipoVeiculoCollectionNewTipoVeiculoToAttach = em.getReference(tipoVeiculoCollectionNewTipoVeiculoToAttach.getClass(), tipoVeiculoCollectionNewTipoVeiculoToAttach.getCodigoTipoVeiculo());
                attachedTipoVeiculoCollectionNew.add(tipoVeiculoCollectionNewTipoVeiculoToAttach);
            }
            tipoVeiculoCollectionNew = attachedTipoVeiculoCollectionNew;
            linhaVeiculo.setTipoVeiculoCollection(tipoVeiculoCollectionNew);
            linhaVeiculo = em.merge(linhaVeiculo);
            for (TipoVeiculo tipoVeiculoCollectionNewTipoVeiculo : tipoVeiculoCollectionNew) {
                if (!tipoVeiculoCollectionOld.contains(tipoVeiculoCollectionNewTipoVeiculo)) {
                    LinhaVeiculo oldCodigoLinhaVeiculoOfTipoVeiculoCollectionNewTipoVeiculo = tipoVeiculoCollectionNewTipoVeiculo.getCodigoLinhaVeiculo();
                    tipoVeiculoCollectionNewTipoVeiculo.setCodigoLinhaVeiculo(linhaVeiculo);
                    tipoVeiculoCollectionNewTipoVeiculo = em.merge(tipoVeiculoCollectionNewTipoVeiculo);
                    if (oldCodigoLinhaVeiculoOfTipoVeiculoCollectionNewTipoVeiculo != null && !oldCodigoLinhaVeiculoOfTipoVeiculoCollectionNewTipoVeiculo.equals(linhaVeiculo)) {
                        oldCodigoLinhaVeiculoOfTipoVeiculoCollectionNewTipoVeiculo.getTipoVeiculoCollection().remove(tipoVeiculoCollectionNewTipoVeiculo);
                        oldCodigoLinhaVeiculoOfTipoVeiculoCollectionNewTipoVeiculo = em.merge(oldCodigoLinhaVeiculoOfTipoVeiculoCollectionNewTipoVeiculo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = linhaVeiculo.getCodigoLinhaVeiculo();
                if (findLinhaVeiculo(id) == null) {
                    throw new NonexistentEntityException("The linhaVeiculo with id " + id + " no longer exists.");
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
            LinhaVeiculo linhaVeiculo;
            try {
                linhaVeiculo = em.getReference(LinhaVeiculo.class, id);
                linhaVeiculo.getCodigoLinhaVeiculo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The linhaVeiculo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TipoVeiculo> tipoVeiculoCollectionOrphanCheck = linhaVeiculo.getTipoVeiculoCollection();
            for (TipoVeiculo tipoVeiculoCollectionOrphanCheckTipoVeiculo : tipoVeiculoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This LinhaVeiculo (" + linhaVeiculo + ") cannot be destroyed since the TipoVeiculo " + tipoVeiculoCollectionOrphanCheckTipoVeiculo + " in its tipoVeiculoCollection field has a non-nullable codigoLinhaVeiculo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(linhaVeiculo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LinhaVeiculo> findLinhaVeiculoEntities() {
        return findLinhaVeiculoEntities(true, -1, -1);
    }

    public List<LinhaVeiculo> findLinhaVeiculoEntities(int maxResults, int firstResult) {
        return findLinhaVeiculoEntities(false, maxResults, firstResult);
    }

    private List<LinhaVeiculo> findLinhaVeiculoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LinhaVeiculo.class));
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

    public LinhaVeiculo findLinhaVeiculo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LinhaVeiculo.class, id);
        } finally {
            em.close();
        }
    }

    public int getLinhaVeiculoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LinhaVeiculo> rt = cq.from(LinhaVeiculo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
