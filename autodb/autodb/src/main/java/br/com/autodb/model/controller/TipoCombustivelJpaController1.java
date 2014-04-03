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
import br.com.autodb.model.dao.Motor;
import br.com.autodb.model.dao.TipoCombustivel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class TipoCombustivelJpaController1 implements Serializable {

    public TipoCombustivelJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoCombustivel tipoCombustivel) {
        if (tipoCombustivel.getMotorCollection() == null) {
            tipoCombustivel.setMotorCollection(new ArrayList<Motor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Motor> attachedMotorCollection = new ArrayList<Motor>();
            for (Motor motorCollectionMotorToAttach : tipoCombustivel.getMotorCollection()) {
                motorCollectionMotorToAttach = em.getReference(motorCollectionMotorToAttach.getClass(), motorCollectionMotorToAttach.getCodigoMotor());
                attachedMotorCollection.add(motorCollectionMotorToAttach);
            }
            tipoCombustivel.setMotorCollection(attachedMotorCollection);
            em.persist(tipoCombustivel);
            for (Motor motorCollectionMotor : tipoCombustivel.getMotorCollection()) {
                TipoCombustivel oldCodigoTipoCombustivelOfMotorCollectionMotor = motorCollectionMotor.getCodigoTipoCombustivel();
                motorCollectionMotor.setCodigoTipoCombustivel(tipoCombustivel);
                motorCollectionMotor = em.merge(motorCollectionMotor);
                if (oldCodigoTipoCombustivelOfMotorCollectionMotor != null) {
                    oldCodigoTipoCombustivelOfMotorCollectionMotor.getMotorCollection().remove(motorCollectionMotor);
                    oldCodigoTipoCombustivelOfMotorCollectionMotor = em.merge(oldCodigoTipoCombustivelOfMotorCollectionMotor);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoCombustivel tipoCombustivel) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoCombustivel persistentTipoCombustivel = em.find(TipoCombustivel.class, tipoCombustivel.getCodigoTipoCombustivel());
            Collection<Motor> motorCollectionOld = persistentTipoCombustivel.getMotorCollection();
            Collection<Motor> motorCollectionNew = tipoCombustivel.getMotorCollection();
            Collection<Motor> attachedMotorCollectionNew = new ArrayList<Motor>();
            for (Motor motorCollectionNewMotorToAttach : motorCollectionNew) {
                motorCollectionNewMotorToAttach = em.getReference(motorCollectionNewMotorToAttach.getClass(), motorCollectionNewMotorToAttach.getCodigoMotor());
                attachedMotorCollectionNew.add(motorCollectionNewMotorToAttach);
            }
            motorCollectionNew = attachedMotorCollectionNew;
            tipoCombustivel.setMotorCollection(motorCollectionNew);
            tipoCombustivel = em.merge(tipoCombustivel);
            for (Motor motorCollectionOldMotor : motorCollectionOld) {
                if (!motorCollectionNew.contains(motorCollectionOldMotor)) {
                    motorCollectionOldMotor.setCodigoTipoCombustivel(null);
                    motorCollectionOldMotor = em.merge(motorCollectionOldMotor);
                }
            }
            for (Motor motorCollectionNewMotor : motorCollectionNew) {
                if (!motorCollectionOld.contains(motorCollectionNewMotor)) {
                    TipoCombustivel oldCodigoTipoCombustivelOfMotorCollectionNewMotor = motorCollectionNewMotor.getCodigoTipoCombustivel();
                    motorCollectionNewMotor.setCodigoTipoCombustivel(tipoCombustivel);
                    motorCollectionNewMotor = em.merge(motorCollectionNewMotor);
                    if (oldCodigoTipoCombustivelOfMotorCollectionNewMotor != null && !oldCodigoTipoCombustivelOfMotorCollectionNewMotor.equals(tipoCombustivel)) {
                        oldCodigoTipoCombustivelOfMotorCollectionNewMotor.getMotorCollection().remove(motorCollectionNewMotor);
                        oldCodigoTipoCombustivelOfMotorCollectionNewMotor = em.merge(oldCodigoTipoCombustivelOfMotorCollectionNewMotor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoCombustivel.getCodigoTipoCombustivel();
                if (findTipoCombustivel(id) == null) {
                    throw new NonexistentEntityException("The tipoCombustivel with id " + id + " no longer exists.");
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
            TipoCombustivel tipoCombustivel;
            try {
                tipoCombustivel = em.getReference(TipoCombustivel.class, id);
                tipoCombustivel.getCodigoTipoCombustivel();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoCombustivel with id " + id + " no longer exists.", enfe);
            }
            Collection<Motor> motorCollection = tipoCombustivel.getMotorCollection();
            for (Motor motorCollectionMotor : motorCollection) {
                motorCollectionMotor.setCodigoTipoCombustivel(null);
                motorCollectionMotor = em.merge(motorCollectionMotor);
            }
            em.remove(tipoCombustivel);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoCombustivel> findTipoCombustivelEntities() {
        return findTipoCombustivelEntities(true, -1, -1);
    }

    public List<TipoCombustivel> findTipoCombustivelEntities(int maxResults, int firstResult) {
        return findTipoCombustivelEntities(false, maxResults, firstResult);
    }

    private List<TipoCombustivel> findTipoCombustivelEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoCombustivel.class));
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

    public TipoCombustivel findTipoCombustivel(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoCombustivel.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoCombustivelCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoCombustivel> rt = cq.from(TipoCombustivel.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
