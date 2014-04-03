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
import br.com.autodb.model.dao.TipoAspiracao;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class TipoAspiracaoJpaController1 implements Serializable {

    public TipoAspiracaoJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoAspiracao tipoAspiracao) {
        if (tipoAspiracao.getMotorCollection() == null) {
            tipoAspiracao.setMotorCollection(new ArrayList<Motor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Motor> attachedMotorCollection = new ArrayList<Motor>();
            for (Motor motorCollectionMotorToAttach : tipoAspiracao.getMotorCollection()) {
                motorCollectionMotorToAttach = em.getReference(motorCollectionMotorToAttach.getClass(), motorCollectionMotorToAttach.getCodigoMotor());
                attachedMotorCollection.add(motorCollectionMotorToAttach);
            }
            tipoAspiracao.setMotorCollection(attachedMotorCollection);
            em.persist(tipoAspiracao);
            for (Motor motorCollectionMotor : tipoAspiracao.getMotorCollection()) {
                TipoAspiracao oldCodigoTipoAspiracaoOfMotorCollectionMotor = motorCollectionMotor.getCodigoTipoAspiracao();
                motorCollectionMotor.setCodigoTipoAspiracao(tipoAspiracao);
                motorCollectionMotor = em.merge(motorCollectionMotor);
                if (oldCodigoTipoAspiracaoOfMotorCollectionMotor != null) {
                    oldCodigoTipoAspiracaoOfMotorCollectionMotor.getMotorCollection().remove(motorCollectionMotor);
                    oldCodigoTipoAspiracaoOfMotorCollectionMotor = em.merge(oldCodigoTipoAspiracaoOfMotorCollectionMotor);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoAspiracao tipoAspiracao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoAspiracao persistentTipoAspiracao = em.find(TipoAspiracao.class, tipoAspiracao.getCodigoTipoAspiracao());
            Collection<Motor> motorCollectionOld = persistentTipoAspiracao.getMotorCollection();
            Collection<Motor> motorCollectionNew = tipoAspiracao.getMotorCollection();
            Collection<Motor> attachedMotorCollectionNew = new ArrayList<Motor>();
            for (Motor motorCollectionNewMotorToAttach : motorCollectionNew) {
                motorCollectionNewMotorToAttach = em.getReference(motorCollectionNewMotorToAttach.getClass(), motorCollectionNewMotorToAttach.getCodigoMotor());
                attachedMotorCollectionNew.add(motorCollectionNewMotorToAttach);
            }
            motorCollectionNew = attachedMotorCollectionNew;
            tipoAspiracao.setMotorCollection(motorCollectionNew);
            tipoAspiracao = em.merge(tipoAspiracao);
            for (Motor motorCollectionOldMotor : motorCollectionOld) {
                if (!motorCollectionNew.contains(motorCollectionOldMotor)) {
                    motorCollectionOldMotor.setCodigoTipoAspiracao(null);
                    motorCollectionOldMotor = em.merge(motorCollectionOldMotor);
                }
            }
            for (Motor motorCollectionNewMotor : motorCollectionNew) {
                if (!motorCollectionOld.contains(motorCollectionNewMotor)) {
                    TipoAspiracao oldCodigoTipoAspiracaoOfMotorCollectionNewMotor = motorCollectionNewMotor.getCodigoTipoAspiracao();
                    motorCollectionNewMotor.setCodigoTipoAspiracao(tipoAspiracao);
                    motorCollectionNewMotor = em.merge(motorCollectionNewMotor);
                    if (oldCodigoTipoAspiracaoOfMotorCollectionNewMotor != null && !oldCodigoTipoAspiracaoOfMotorCollectionNewMotor.equals(tipoAspiracao)) {
                        oldCodigoTipoAspiracaoOfMotorCollectionNewMotor.getMotorCollection().remove(motorCollectionNewMotor);
                        oldCodigoTipoAspiracaoOfMotorCollectionNewMotor = em.merge(oldCodigoTipoAspiracaoOfMotorCollectionNewMotor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoAspiracao.getCodigoTipoAspiracao();
                if (findTipoAspiracao(id) == null) {
                    throw new NonexistentEntityException("The tipoAspiracao with id " + id + " no longer exists.");
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
            TipoAspiracao tipoAspiracao;
            try {
                tipoAspiracao = em.getReference(TipoAspiracao.class, id);
                tipoAspiracao.getCodigoTipoAspiracao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoAspiracao with id " + id + " no longer exists.", enfe);
            }
            Collection<Motor> motorCollection = tipoAspiracao.getMotorCollection();
            for (Motor motorCollectionMotor : motorCollection) {
                motorCollectionMotor.setCodigoTipoAspiracao(null);
                motorCollectionMotor = em.merge(motorCollectionMotor);
            }
            em.remove(tipoAspiracao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoAspiracao> findTipoAspiracaoEntities() {
        return findTipoAspiracaoEntities(true, -1, -1);
    }

    public List<TipoAspiracao> findTipoAspiracaoEntities(int maxResults, int firstResult) {
        return findTipoAspiracaoEntities(false, maxResults, firstResult);
    }

    private List<TipoAspiracao> findTipoAspiracaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoAspiracao.class));
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

    public TipoAspiracao findTipoAspiracao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoAspiracao.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoAspiracaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoAspiracao> rt = cq.from(TipoAspiracao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
