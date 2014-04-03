/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.controller;

import br.com.autodb.model.controller.exceptions.IllegalOrphanException;
import br.com.autodb.model.controller.exceptions.NonexistentEntityException;
import br.com.autodb.model.dao.TipoCarrocaria;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class TipoCarrocariaJpaController1 implements Serializable {

    public TipoCarrocariaJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoCarrocaria tipoCarrocaria) {
        if (tipoCarrocaria.getVeiculoBaseCollection() == null) {
            tipoCarrocaria.setVeiculoBaseCollection(new ArrayList<VeiculoBase>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<VeiculoBase> attachedVeiculoBaseCollection = new ArrayList<VeiculoBase>();
            for (VeiculoBase veiculoBaseCollectionVeiculoBaseToAttach : tipoCarrocaria.getVeiculoBaseCollection()) {
                veiculoBaseCollectionVeiculoBaseToAttach = em.getReference(veiculoBaseCollectionVeiculoBaseToAttach.getClass(), veiculoBaseCollectionVeiculoBaseToAttach.getCodigoVeiculoBase());
                attachedVeiculoBaseCollection.add(veiculoBaseCollectionVeiculoBaseToAttach);
            }
            tipoCarrocaria.setVeiculoBaseCollection(attachedVeiculoBaseCollection);
            em.persist(tipoCarrocaria);
            for (VeiculoBase veiculoBaseCollectionVeiculoBase : tipoCarrocaria.getVeiculoBaseCollection()) {
                TipoCarrocaria oldCodigoTipoCarrocariaOfVeiculoBaseCollectionVeiculoBase = veiculoBaseCollectionVeiculoBase.getCodigoTipoCarrocaria();
                veiculoBaseCollectionVeiculoBase.setCodigoTipoCarrocaria(tipoCarrocaria);
                veiculoBaseCollectionVeiculoBase = em.merge(veiculoBaseCollectionVeiculoBase);
                if (oldCodigoTipoCarrocariaOfVeiculoBaseCollectionVeiculoBase != null) {
                    oldCodigoTipoCarrocariaOfVeiculoBaseCollectionVeiculoBase.getVeiculoBaseCollection().remove(veiculoBaseCollectionVeiculoBase);
                    oldCodigoTipoCarrocariaOfVeiculoBaseCollectionVeiculoBase = em.merge(oldCodigoTipoCarrocariaOfVeiculoBaseCollectionVeiculoBase);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoCarrocaria tipoCarrocaria) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoCarrocaria persistentTipoCarrocaria = em.find(TipoCarrocaria.class, tipoCarrocaria.getCodigoTipoCarrocaria());
            Collection<VeiculoBase> veiculoBaseCollectionOld = persistentTipoCarrocaria.getVeiculoBaseCollection();
            Collection<VeiculoBase> veiculoBaseCollectionNew = tipoCarrocaria.getVeiculoBaseCollection();
            List<String> illegalOrphanMessages = null;
            for (VeiculoBase veiculoBaseCollectionOldVeiculoBase : veiculoBaseCollectionOld) {
                if (!veiculoBaseCollectionNew.contains(veiculoBaseCollectionOldVeiculoBase)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain VeiculoBase " + veiculoBaseCollectionOldVeiculoBase + " since its codigoTipoCarrocaria field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<VeiculoBase> attachedVeiculoBaseCollectionNew = new ArrayList<VeiculoBase>();
            for (VeiculoBase veiculoBaseCollectionNewVeiculoBaseToAttach : veiculoBaseCollectionNew) {
                veiculoBaseCollectionNewVeiculoBaseToAttach = em.getReference(veiculoBaseCollectionNewVeiculoBaseToAttach.getClass(), veiculoBaseCollectionNewVeiculoBaseToAttach.getCodigoVeiculoBase());
                attachedVeiculoBaseCollectionNew.add(veiculoBaseCollectionNewVeiculoBaseToAttach);
            }
            veiculoBaseCollectionNew = attachedVeiculoBaseCollectionNew;
            tipoCarrocaria.setVeiculoBaseCollection(veiculoBaseCollectionNew);
            tipoCarrocaria = em.merge(tipoCarrocaria);
            for (VeiculoBase veiculoBaseCollectionNewVeiculoBase : veiculoBaseCollectionNew) {
                if (!veiculoBaseCollectionOld.contains(veiculoBaseCollectionNewVeiculoBase)) {
                    TipoCarrocaria oldCodigoTipoCarrocariaOfVeiculoBaseCollectionNewVeiculoBase = veiculoBaseCollectionNewVeiculoBase.getCodigoTipoCarrocaria();
                    veiculoBaseCollectionNewVeiculoBase.setCodigoTipoCarrocaria(tipoCarrocaria);
                    veiculoBaseCollectionNewVeiculoBase = em.merge(veiculoBaseCollectionNewVeiculoBase);
                    if (oldCodigoTipoCarrocariaOfVeiculoBaseCollectionNewVeiculoBase != null && !oldCodigoTipoCarrocariaOfVeiculoBaseCollectionNewVeiculoBase.equals(tipoCarrocaria)) {
                        oldCodigoTipoCarrocariaOfVeiculoBaseCollectionNewVeiculoBase.getVeiculoBaseCollection().remove(veiculoBaseCollectionNewVeiculoBase);
                        oldCodigoTipoCarrocariaOfVeiculoBaseCollectionNewVeiculoBase = em.merge(oldCodigoTipoCarrocariaOfVeiculoBaseCollectionNewVeiculoBase);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoCarrocaria.getCodigoTipoCarrocaria();
                if (findTipoCarrocaria(id) == null) {
                    throw new NonexistentEntityException("The tipoCarrocaria with id " + id + " no longer exists.");
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
            TipoCarrocaria tipoCarrocaria;
            try {
                tipoCarrocaria = em.getReference(TipoCarrocaria.class, id);
                tipoCarrocaria.getCodigoTipoCarrocaria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoCarrocaria with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<VeiculoBase> veiculoBaseCollectionOrphanCheck = tipoCarrocaria.getVeiculoBaseCollection();
            for (VeiculoBase veiculoBaseCollectionOrphanCheckVeiculoBase : veiculoBaseCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoCarrocaria (" + tipoCarrocaria + ") cannot be destroyed since the VeiculoBase " + veiculoBaseCollectionOrphanCheckVeiculoBase + " in its veiculoBaseCollection field has a non-nullable codigoTipoCarrocaria field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoCarrocaria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoCarrocaria> findTipoCarrocariaEntities() {
        return findTipoCarrocariaEntities(true, -1, -1);
    }

    public List<TipoCarrocaria> findTipoCarrocariaEntities(int maxResults, int firstResult) {
        return findTipoCarrocariaEntities(false, maxResults, firstResult);
    }

    private List<TipoCarrocaria> findTipoCarrocariaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoCarrocaria.class));
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

    public TipoCarrocaria findTipoCarrocaria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoCarrocaria.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoCarrocariaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoCarrocaria> rt = cq.from(TipoCarrocaria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
