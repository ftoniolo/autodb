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
import br.com.autodb.model.dao.ConfiguracaoFreio;
import br.com.autodb.model.dao.TipoAssistenciaFreio;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class TipoAssistenciaFreioJpaController1 implements Serializable {

    public TipoAssistenciaFreioJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoAssistenciaFreio tipoAssistenciaFreio) {
        if (tipoAssistenciaFreio.getConfiguracaoFreioCollection() == null) {
            tipoAssistenciaFreio.setConfiguracaoFreioCollection(new ArrayList<ConfiguracaoFreio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<ConfiguracaoFreio> attachedConfiguracaoFreioCollection = new ArrayList<ConfiguracaoFreio>();
            for (ConfiguracaoFreio configuracaoFreioCollectionConfiguracaoFreioToAttach : tipoAssistenciaFreio.getConfiguracaoFreioCollection()) {
                configuracaoFreioCollectionConfiguracaoFreioToAttach = em.getReference(configuracaoFreioCollectionConfiguracaoFreioToAttach.getClass(), configuracaoFreioCollectionConfiguracaoFreioToAttach.getCodigoConfiguracaoFreio());
                attachedConfiguracaoFreioCollection.add(configuracaoFreioCollectionConfiguracaoFreioToAttach);
            }
            tipoAssistenciaFreio.setConfiguracaoFreioCollection(attachedConfiguracaoFreioCollection);
            em.persist(tipoAssistenciaFreio);
            for (ConfiguracaoFreio configuracaoFreioCollectionConfiguracaoFreio : tipoAssistenciaFreio.getConfiguracaoFreioCollection()) {
                TipoAssistenciaFreio oldCodigoTipoAssistenciaFreioOfConfiguracaoFreioCollectionConfiguracaoFreio = configuracaoFreioCollectionConfiguracaoFreio.getCodigoTipoAssistenciaFreio();
                configuracaoFreioCollectionConfiguracaoFreio.setCodigoTipoAssistenciaFreio(tipoAssistenciaFreio);
                configuracaoFreioCollectionConfiguracaoFreio = em.merge(configuracaoFreioCollectionConfiguracaoFreio);
                if (oldCodigoTipoAssistenciaFreioOfConfiguracaoFreioCollectionConfiguracaoFreio != null) {
                    oldCodigoTipoAssistenciaFreioOfConfiguracaoFreioCollectionConfiguracaoFreio.getConfiguracaoFreioCollection().remove(configuracaoFreioCollectionConfiguracaoFreio);
                    oldCodigoTipoAssistenciaFreioOfConfiguracaoFreioCollectionConfiguracaoFreio = em.merge(oldCodigoTipoAssistenciaFreioOfConfiguracaoFreioCollectionConfiguracaoFreio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoAssistenciaFreio tipoAssistenciaFreio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoAssistenciaFreio persistentTipoAssistenciaFreio = em.find(TipoAssistenciaFreio.class, tipoAssistenciaFreio.getCodigoTipoAssistenciaFreio());
            Collection<ConfiguracaoFreio> configuracaoFreioCollectionOld = persistentTipoAssistenciaFreio.getConfiguracaoFreioCollection();
            Collection<ConfiguracaoFreio> configuracaoFreioCollectionNew = tipoAssistenciaFreio.getConfiguracaoFreioCollection();
            Collection<ConfiguracaoFreio> attachedConfiguracaoFreioCollectionNew = new ArrayList<ConfiguracaoFreio>();
            for (ConfiguracaoFreio configuracaoFreioCollectionNewConfiguracaoFreioToAttach : configuracaoFreioCollectionNew) {
                configuracaoFreioCollectionNewConfiguracaoFreioToAttach = em.getReference(configuracaoFreioCollectionNewConfiguracaoFreioToAttach.getClass(), configuracaoFreioCollectionNewConfiguracaoFreioToAttach.getCodigoConfiguracaoFreio());
                attachedConfiguracaoFreioCollectionNew.add(configuracaoFreioCollectionNewConfiguracaoFreioToAttach);
            }
            configuracaoFreioCollectionNew = attachedConfiguracaoFreioCollectionNew;
            tipoAssistenciaFreio.setConfiguracaoFreioCollection(configuracaoFreioCollectionNew);
            tipoAssistenciaFreio = em.merge(tipoAssistenciaFreio);
            for (ConfiguracaoFreio configuracaoFreioCollectionOldConfiguracaoFreio : configuracaoFreioCollectionOld) {
                if (!configuracaoFreioCollectionNew.contains(configuracaoFreioCollectionOldConfiguracaoFreio)) {
                    configuracaoFreioCollectionOldConfiguracaoFreio.setCodigoTipoAssistenciaFreio(null);
                    configuracaoFreioCollectionOldConfiguracaoFreio = em.merge(configuracaoFreioCollectionOldConfiguracaoFreio);
                }
            }
            for (ConfiguracaoFreio configuracaoFreioCollectionNewConfiguracaoFreio : configuracaoFreioCollectionNew) {
                if (!configuracaoFreioCollectionOld.contains(configuracaoFreioCollectionNewConfiguracaoFreio)) {
                    TipoAssistenciaFreio oldCodigoTipoAssistenciaFreioOfConfiguracaoFreioCollectionNewConfiguracaoFreio = configuracaoFreioCollectionNewConfiguracaoFreio.getCodigoTipoAssistenciaFreio();
                    configuracaoFreioCollectionNewConfiguracaoFreio.setCodigoTipoAssistenciaFreio(tipoAssistenciaFreio);
                    configuracaoFreioCollectionNewConfiguracaoFreio = em.merge(configuracaoFreioCollectionNewConfiguracaoFreio);
                    if (oldCodigoTipoAssistenciaFreioOfConfiguracaoFreioCollectionNewConfiguracaoFreio != null && !oldCodigoTipoAssistenciaFreioOfConfiguracaoFreioCollectionNewConfiguracaoFreio.equals(tipoAssistenciaFreio)) {
                        oldCodigoTipoAssistenciaFreioOfConfiguracaoFreioCollectionNewConfiguracaoFreio.getConfiguracaoFreioCollection().remove(configuracaoFreioCollectionNewConfiguracaoFreio);
                        oldCodigoTipoAssistenciaFreioOfConfiguracaoFreioCollectionNewConfiguracaoFreio = em.merge(oldCodigoTipoAssistenciaFreioOfConfiguracaoFreioCollectionNewConfiguracaoFreio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoAssistenciaFreio.getCodigoTipoAssistenciaFreio();
                if (findTipoAssistenciaFreio(id) == null) {
                    throw new NonexistentEntityException("The tipoAssistenciaFreio with id " + id + " no longer exists.");
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
            TipoAssistenciaFreio tipoAssistenciaFreio;
            try {
                tipoAssistenciaFreio = em.getReference(TipoAssistenciaFreio.class, id);
                tipoAssistenciaFreio.getCodigoTipoAssistenciaFreio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoAssistenciaFreio with id " + id + " no longer exists.", enfe);
            }
            Collection<ConfiguracaoFreio> configuracaoFreioCollection = tipoAssistenciaFreio.getConfiguracaoFreioCollection();
            for (ConfiguracaoFreio configuracaoFreioCollectionConfiguracaoFreio : configuracaoFreioCollection) {
                configuracaoFreioCollectionConfiguracaoFreio.setCodigoTipoAssistenciaFreio(null);
                configuracaoFreioCollectionConfiguracaoFreio = em.merge(configuracaoFreioCollectionConfiguracaoFreio);
            }
            em.remove(tipoAssistenciaFreio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoAssistenciaFreio> findTipoAssistenciaFreioEntities() {
        return findTipoAssistenciaFreioEntities(true, -1, -1);
    }

    public List<TipoAssistenciaFreio> findTipoAssistenciaFreioEntities(int maxResults, int firstResult) {
        return findTipoAssistenciaFreioEntities(false, maxResults, firstResult);
    }

    private List<TipoAssistenciaFreio> findTipoAssistenciaFreioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoAssistenciaFreio.class));
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

    public TipoAssistenciaFreio findTipoAssistenciaFreio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoAssistenciaFreio.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoAssistenciaFreioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoAssistenciaFreio> rt = cq.from(TipoAssistenciaFreio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
