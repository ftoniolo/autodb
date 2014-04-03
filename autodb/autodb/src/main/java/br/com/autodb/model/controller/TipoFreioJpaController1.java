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
import br.com.autodb.model.dao.TipoFreio;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class TipoFreioJpaController1 implements Serializable {

    public TipoFreioJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoFreio tipoFreio) {
        if (tipoFreio.getConfiguracaoFreioCollection() == null) {
            tipoFreio.setConfiguracaoFreioCollection(new ArrayList<ConfiguracaoFreio>());
        }
        if (tipoFreio.getConfiguracaoFreioCollection1() == null) {
            tipoFreio.setConfiguracaoFreioCollection1(new ArrayList<ConfiguracaoFreio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<ConfiguracaoFreio> attachedConfiguracaoFreioCollection = new ArrayList<ConfiguracaoFreio>();
            for (ConfiguracaoFreio configuracaoFreioCollectionConfiguracaoFreioToAttach : tipoFreio.getConfiguracaoFreioCollection()) {
                configuracaoFreioCollectionConfiguracaoFreioToAttach = em.getReference(configuracaoFreioCollectionConfiguracaoFreioToAttach.getClass(), configuracaoFreioCollectionConfiguracaoFreioToAttach.getCodigoConfiguracaoFreio());
                attachedConfiguracaoFreioCollection.add(configuracaoFreioCollectionConfiguracaoFreioToAttach);
            }
            tipoFreio.setConfiguracaoFreioCollection(attachedConfiguracaoFreioCollection);
            Collection<ConfiguracaoFreio> attachedConfiguracaoFreioCollection1 = new ArrayList<ConfiguracaoFreio>();
            for (ConfiguracaoFreio configuracaoFreioCollection1ConfiguracaoFreioToAttach : tipoFreio.getConfiguracaoFreioCollection1()) {
                configuracaoFreioCollection1ConfiguracaoFreioToAttach = em.getReference(configuracaoFreioCollection1ConfiguracaoFreioToAttach.getClass(), configuracaoFreioCollection1ConfiguracaoFreioToAttach.getCodigoConfiguracaoFreio());
                attachedConfiguracaoFreioCollection1.add(configuracaoFreioCollection1ConfiguracaoFreioToAttach);
            }
            tipoFreio.setConfiguracaoFreioCollection1(attachedConfiguracaoFreioCollection1);
            em.persist(tipoFreio);
            for (ConfiguracaoFreio configuracaoFreioCollectionConfiguracaoFreio : tipoFreio.getConfiguracaoFreioCollection()) {
                TipoFreio oldCodigoTipoFreioTraseiroOfConfiguracaoFreioCollectionConfiguracaoFreio = configuracaoFreioCollectionConfiguracaoFreio.getCodigoTipoFreioTraseiro();
                configuracaoFreioCollectionConfiguracaoFreio.setCodigoTipoFreioTraseiro(tipoFreio);
                configuracaoFreioCollectionConfiguracaoFreio = em.merge(configuracaoFreioCollectionConfiguracaoFreio);
                if (oldCodigoTipoFreioTraseiroOfConfiguracaoFreioCollectionConfiguracaoFreio != null) {
                    oldCodigoTipoFreioTraseiroOfConfiguracaoFreioCollectionConfiguracaoFreio.getConfiguracaoFreioCollection().remove(configuracaoFreioCollectionConfiguracaoFreio);
                    oldCodigoTipoFreioTraseiroOfConfiguracaoFreioCollectionConfiguracaoFreio = em.merge(oldCodigoTipoFreioTraseiroOfConfiguracaoFreioCollectionConfiguracaoFreio);
                }
            }
            for (ConfiguracaoFreio configuracaoFreioCollection1ConfiguracaoFreio : tipoFreio.getConfiguracaoFreioCollection1()) {
                TipoFreio oldCodigoTipoFreioDianteiroOfConfiguracaoFreioCollection1ConfiguracaoFreio = configuracaoFreioCollection1ConfiguracaoFreio.getCodigoTipoFreioDianteiro();
                configuracaoFreioCollection1ConfiguracaoFreio.setCodigoTipoFreioDianteiro(tipoFreio);
                configuracaoFreioCollection1ConfiguracaoFreio = em.merge(configuracaoFreioCollection1ConfiguracaoFreio);
                if (oldCodigoTipoFreioDianteiroOfConfiguracaoFreioCollection1ConfiguracaoFreio != null) {
                    oldCodigoTipoFreioDianteiroOfConfiguracaoFreioCollection1ConfiguracaoFreio.getConfiguracaoFreioCollection1().remove(configuracaoFreioCollection1ConfiguracaoFreio);
                    oldCodigoTipoFreioDianteiroOfConfiguracaoFreioCollection1ConfiguracaoFreio = em.merge(oldCodigoTipoFreioDianteiroOfConfiguracaoFreioCollection1ConfiguracaoFreio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoFreio tipoFreio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoFreio persistentTipoFreio = em.find(TipoFreio.class, tipoFreio.getCodigoTipoFreio());
            Collection<ConfiguracaoFreio> configuracaoFreioCollectionOld = persistentTipoFreio.getConfiguracaoFreioCollection();
            Collection<ConfiguracaoFreio> configuracaoFreioCollectionNew = tipoFreio.getConfiguracaoFreioCollection();
            Collection<ConfiguracaoFreio> configuracaoFreioCollection1Old = persistentTipoFreio.getConfiguracaoFreioCollection1();
            Collection<ConfiguracaoFreio> configuracaoFreioCollection1New = tipoFreio.getConfiguracaoFreioCollection1();
            Collection<ConfiguracaoFreio> attachedConfiguracaoFreioCollectionNew = new ArrayList<ConfiguracaoFreio>();
            for (ConfiguracaoFreio configuracaoFreioCollectionNewConfiguracaoFreioToAttach : configuracaoFreioCollectionNew) {
                configuracaoFreioCollectionNewConfiguracaoFreioToAttach = em.getReference(configuracaoFreioCollectionNewConfiguracaoFreioToAttach.getClass(), configuracaoFreioCollectionNewConfiguracaoFreioToAttach.getCodigoConfiguracaoFreio());
                attachedConfiguracaoFreioCollectionNew.add(configuracaoFreioCollectionNewConfiguracaoFreioToAttach);
            }
            configuracaoFreioCollectionNew = attachedConfiguracaoFreioCollectionNew;
            tipoFreio.setConfiguracaoFreioCollection(configuracaoFreioCollectionNew);
            Collection<ConfiguracaoFreio> attachedConfiguracaoFreioCollection1New = new ArrayList<ConfiguracaoFreio>();
            for (ConfiguracaoFreio configuracaoFreioCollection1NewConfiguracaoFreioToAttach : configuracaoFreioCollection1New) {
                configuracaoFreioCollection1NewConfiguracaoFreioToAttach = em.getReference(configuracaoFreioCollection1NewConfiguracaoFreioToAttach.getClass(), configuracaoFreioCollection1NewConfiguracaoFreioToAttach.getCodigoConfiguracaoFreio());
                attachedConfiguracaoFreioCollection1New.add(configuracaoFreioCollection1NewConfiguracaoFreioToAttach);
            }
            configuracaoFreioCollection1New = attachedConfiguracaoFreioCollection1New;
            tipoFreio.setConfiguracaoFreioCollection1(configuracaoFreioCollection1New);
            tipoFreio = em.merge(tipoFreio);
            for (ConfiguracaoFreio configuracaoFreioCollectionOldConfiguracaoFreio : configuracaoFreioCollectionOld) {
                if (!configuracaoFreioCollectionNew.contains(configuracaoFreioCollectionOldConfiguracaoFreio)) {
                    configuracaoFreioCollectionOldConfiguracaoFreio.setCodigoTipoFreioTraseiro(null);
                    configuracaoFreioCollectionOldConfiguracaoFreio = em.merge(configuracaoFreioCollectionOldConfiguracaoFreio);
                }
            }
            for (ConfiguracaoFreio configuracaoFreioCollectionNewConfiguracaoFreio : configuracaoFreioCollectionNew) {
                if (!configuracaoFreioCollectionOld.contains(configuracaoFreioCollectionNewConfiguracaoFreio)) {
                    TipoFreio oldCodigoTipoFreioTraseiroOfConfiguracaoFreioCollectionNewConfiguracaoFreio = configuracaoFreioCollectionNewConfiguracaoFreio.getCodigoTipoFreioTraseiro();
                    configuracaoFreioCollectionNewConfiguracaoFreio.setCodigoTipoFreioTraseiro(tipoFreio);
                    configuracaoFreioCollectionNewConfiguracaoFreio = em.merge(configuracaoFreioCollectionNewConfiguracaoFreio);
                    if (oldCodigoTipoFreioTraseiroOfConfiguracaoFreioCollectionNewConfiguracaoFreio != null && !oldCodigoTipoFreioTraseiroOfConfiguracaoFreioCollectionNewConfiguracaoFreio.equals(tipoFreio)) {
                        oldCodigoTipoFreioTraseiroOfConfiguracaoFreioCollectionNewConfiguracaoFreio.getConfiguracaoFreioCollection().remove(configuracaoFreioCollectionNewConfiguracaoFreio);
                        oldCodigoTipoFreioTraseiroOfConfiguracaoFreioCollectionNewConfiguracaoFreio = em.merge(oldCodigoTipoFreioTraseiroOfConfiguracaoFreioCollectionNewConfiguracaoFreio);
                    }
                }
            }
            for (ConfiguracaoFreio configuracaoFreioCollection1OldConfiguracaoFreio : configuracaoFreioCollection1Old) {
                if (!configuracaoFreioCollection1New.contains(configuracaoFreioCollection1OldConfiguracaoFreio)) {
                    configuracaoFreioCollection1OldConfiguracaoFreio.setCodigoTipoFreioDianteiro(null);
                    configuracaoFreioCollection1OldConfiguracaoFreio = em.merge(configuracaoFreioCollection1OldConfiguracaoFreio);
                }
            }
            for (ConfiguracaoFreio configuracaoFreioCollection1NewConfiguracaoFreio : configuracaoFreioCollection1New) {
                if (!configuracaoFreioCollection1Old.contains(configuracaoFreioCollection1NewConfiguracaoFreio)) {
                    TipoFreio oldCodigoTipoFreioDianteiroOfConfiguracaoFreioCollection1NewConfiguracaoFreio = configuracaoFreioCollection1NewConfiguracaoFreio.getCodigoTipoFreioDianteiro();
                    configuracaoFreioCollection1NewConfiguracaoFreio.setCodigoTipoFreioDianteiro(tipoFreio);
                    configuracaoFreioCollection1NewConfiguracaoFreio = em.merge(configuracaoFreioCollection1NewConfiguracaoFreio);
                    if (oldCodigoTipoFreioDianteiroOfConfiguracaoFreioCollection1NewConfiguracaoFreio != null && !oldCodigoTipoFreioDianteiroOfConfiguracaoFreioCollection1NewConfiguracaoFreio.equals(tipoFreio)) {
                        oldCodigoTipoFreioDianteiroOfConfiguracaoFreioCollection1NewConfiguracaoFreio.getConfiguracaoFreioCollection1().remove(configuracaoFreioCollection1NewConfiguracaoFreio);
                        oldCodigoTipoFreioDianteiroOfConfiguracaoFreioCollection1NewConfiguracaoFreio = em.merge(oldCodigoTipoFreioDianteiroOfConfiguracaoFreioCollection1NewConfiguracaoFreio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoFreio.getCodigoTipoFreio();
                if (findTipoFreio(id) == null) {
                    throw new NonexistentEntityException("The tipoFreio with id " + id + " no longer exists.");
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
            TipoFreio tipoFreio;
            try {
                tipoFreio = em.getReference(TipoFreio.class, id);
                tipoFreio.getCodigoTipoFreio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoFreio with id " + id + " no longer exists.", enfe);
            }
            Collection<ConfiguracaoFreio> configuracaoFreioCollection = tipoFreio.getConfiguracaoFreioCollection();
            for (ConfiguracaoFreio configuracaoFreioCollectionConfiguracaoFreio : configuracaoFreioCollection) {
                configuracaoFreioCollectionConfiguracaoFreio.setCodigoTipoFreioTraseiro(null);
                configuracaoFreioCollectionConfiguracaoFreio = em.merge(configuracaoFreioCollectionConfiguracaoFreio);
            }
            Collection<ConfiguracaoFreio> configuracaoFreioCollection1 = tipoFreio.getConfiguracaoFreioCollection1();
            for (ConfiguracaoFreio configuracaoFreioCollection1ConfiguracaoFreio : configuracaoFreioCollection1) {
                configuracaoFreioCollection1ConfiguracaoFreio.setCodigoTipoFreioDianteiro(null);
                configuracaoFreioCollection1ConfiguracaoFreio = em.merge(configuracaoFreioCollection1ConfiguracaoFreio);
            }
            em.remove(tipoFreio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoFreio> findTipoFreioEntities() {
        return findTipoFreioEntities(true, -1, -1);
    }

    public List<TipoFreio> findTipoFreioEntities(int maxResults, int firstResult) {
        return findTipoFreioEntities(false, maxResults, firstResult);
    }

    private List<TipoFreio> findTipoFreioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoFreio.class));
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

    public TipoFreio findTipoFreio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoFreio.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoFreioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoFreio> rt = cq.from(TipoFreio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
