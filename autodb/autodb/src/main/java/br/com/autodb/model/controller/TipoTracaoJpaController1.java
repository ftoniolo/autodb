/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.controller;

import br.com.autodb.model.controller.exceptions.NonexistentEntityException;
import br.com.autodb.model.dao.TipoTracao;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.autodb.model.dao.Veiculo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class TipoTracaoJpaController1 implements Serializable {

    public TipoTracaoJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoTracao tipoTracao) {
        if (tipoTracao.getVeiculoCollection() == null) {
            tipoTracao.setVeiculoCollection(new ArrayList<Veiculo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Veiculo> attachedVeiculoCollection = new ArrayList<Veiculo>();
            for (Veiculo veiculoCollectionVeiculoToAttach : tipoTracao.getVeiculoCollection()) {
                veiculoCollectionVeiculoToAttach = em.getReference(veiculoCollectionVeiculoToAttach.getClass(), veiculoCollectionVeiculoToAttach.getCodigoVeiculo());
                attachedVeiculoCollection.add(veiculoCollectionVeiculoToAttach);
            }
            tipoTracao.setVeiculoCollection(attachedVeiculoCollection);
            em.persist(tipoTracao);
            for (Veiculo veiculoCollectionVeiculo : tipoTracao.getVeiculoCollection()) {
                TipoTracao oldCodigoTipoTracaoOfVeiculoCollectionVeiculo = veiculoCollectionVeiculo.getCodigoTipoTracao();
                veiculoCollectionVeiculo.setCodigoTipoTracao(tipoTracao);
                veiculoCollectionVeiculo = em.merge(veiculoCollectionVeiculo);
                if (oldCodigoTipoTracaoOfVeiculoCollectionVeiculo != null) {
                    oldCodigoTipoTracaoOfVeiculoCollectionVeiculo.getVeiculoCollection().remove(veiculoCollectionVeiculo);
                    oldCodigoTipoTracaoOfVeiculoCollectionVeiculo = em.merge(oldCodigoTipoTracaoOfVeiculoCollectionVeiculo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoTracao tipoTracao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoTracao persistentTipoTracao = em.find(TipoTracao.class, tipoTracao.getCodigoTipoTracao());
            Collection<Veiculo> veiculoCollectionOld = persistentTipoTracao.getVeiculoCollection();
            Collection<Veiculo> veiculoCollectionNew = tipoTracao.getVeiculoCollection();
            Collection<Veiculo> attachedVeiculoCollectionNew = new ArrayList<Veiculo>();
            for (Veiculo veiculoCollectionNewVeiculoToAttach : veiculoCollectionNew) {
                veiculoCollectionNewVeiculoToAttach = em.getReference(veiculoCollectionNewVeiculoToAttach.getClass(), veiculoCollectionNewVeiculoToAttach.getCodigoVeiculo());
                attachedVeiculoCollectionNew.add(veiculoCollectionNewVeiculoToAttach);
            }
            veiculoCollectionNew = attachedVeiculoCollectionNew;
            tipoTracao.setVeiculoCollection(veiculoCollectionNew);
            tipoTracao = em.merge(tipoTracao);
            for (Veiculo veiculoCollectionOldVeiculo : veiculoCollectionOld) {
                if (!veiculoCollectionNew.contains(veiculoCollectionOldVeiculo)) {
                    veiculoCollectionOldVeiculo.setCodigoTipoTracao(null);
                    veiculoCollectionOldVeiculo = em.merge(veiculoCollectionOldVeiculo);
                }
            }
            for (Veiculo veiculoCollectionNewVeiculo : veiculoCollectionNew) {
                if (!veiculoCollectionOld.contains(veiculoCollectionNewVeiculo)) {
                    TipoTracao oldCodigoTipoTracaoOfVeiculoCollectionNewVeiculo = veiculoCollectionNewVeiculo.getCodigoTipoTracao();
                    veiculoCollectionNewVeiculo.setCodigoTipoTracao(tipoTracao);
                    veiculoCollectionNewVeiculo = em.merge(veiculoCollectionNewVeiculo);
                    if (oldCodigoTipoTracaoOfVeiculoCollectionNewVeiculo != null && !oldCodigoTipoTracaoOfVeiculoCollectionNewVeiculo.equals(tipoTracao)) {
                        oldCodigoTipoTracaoOfVeiculoCollectionNewVeiculo.getVeiculoCollection().remove(veiculoCollectionNewVeiculo);
                        oldCodigoTipoTracaoOfVeiculoCollectionNewVeiculo = em.merge(oldCodigoTipoTracaoOfVeiculoCollectionNewVeiculo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoTracao.getCodigoTipoTracao();
                if (findTipoTracao(id) == null) {
                    throw new NonexistentEntityException("The tipoTracao with id " + id + " no longer exists.");
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
            TipoTracao tipoTracao;
            try {
                tipoTracao = em.getReference(TipoTracao.class, id);
                tipoTracao.getCodigoTipoTracao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoTracao with id " + id + " no longer exists.", enfe);
            }
            Collection<Veiculo> veiculoCollection = tipoTracao.getVeiculoCollection();
            for (Veiculo veiculoCollectionVeiculo : veiculoCollection) {
                veiculoCollectionVeiculo.setCodigoTipoTracao(null);
                veiculoCollectionVeiculo = em.merge(veiculoCollectionVeiculo);
            }
            em.remove(tipoTracao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoTracao> findTipoTracaoEntities() {
        return findTipoTracaoEntities(true, -1, -1);
    }

    public List<TipoTracao> findTipoTracaoEntities(int maxResults, int firstResult) {
        return findTipoTracaoEntities(false, maxResults, firstResult);
    }

    private List<TipoTracao> findTipoTracaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoTracao.class));
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

    public TipoTracao findTipoTracao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoTracao.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoTracaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoTracao> rt = cq.from(TipoTracao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
