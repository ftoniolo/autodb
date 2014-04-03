/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.controller;

import br.com.autodb.model.controller.exceptions.NonexistentEntityException;
import br.com.autodb.model.dao.TipoTransmissao;
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
public class TipoTransmissaoJpaController1 implements Serializable {

    public TipoTransmissaoJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoTransmissao tipoTransmissao) {
        if (tipoTransmissao.getVeiculoCollection() == null) {
            tipoTransmissao.setVeiculoCollection(new ArrayList<Veiculo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Veiculo> attachedVeiculoCollection = new ArrayList<Veiculo>();
            for (Veiculo veiculoCollectionVeiculoToAttach : tipoTransmissao.getVeiculoCollection()) {
                veiculoCollectionVeiculoToAttach = em.getReference(veiculoCollectionVeiculoToAttach.getClass(), veiculoCollectionVeiculoToAttach.getCodigoVeiculo());
                attachedVeiculoCollection.add(veiculoCollectionVeiculoToAttach);
            }
            tipoTransmissao.setVeiculoCollection(attachedVeiculoCollection);
            em.persist(tipoTransmissao);
            for (Veiculo veiculoCollectionVeiculo : tipoTransmissao.getVeiculoCollection()) {
                TipoTransmissao oldCodigoTipoTransmissaoOfVeiculoCollectionVeiculo = veiculoCollectionVeiculo.getCodigoTipoTransmissao();
                veiculoCollectionVeiculo.setCodigoTipoTransmissao(tipoTransmissao);
                veiculoCollectionVeiculo = em.merge(veiculoCollectionVeiculo);
                if (oldCodigoTipoTransmissaoOfVeiculoCollectionVeiculo != null) {
                    oldCodigoTipoTransmissaoOfVeiculoCollectionVeiculo.getVeiculoCollection().remove(veiculoCollectionVeiculo);
                    oldCodigoTipoTransmissaoOfVeiculoCollectionVeiculo = em.merge(oldCodigoTipoTransmissaoOfVeiculoCollectionVeiculo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoTransmissao tipoTransmissao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoTransmissao persistentTipoTransmissao = em.find(TipoTransmissao.class, tipoTransmissao.getCodigoTipoTransmissao());
            Collection<Veiculo> veiculoCollectionOld = persistentTipoTransmissao.getVeiculoCollection();
            Collection<Veiculo> veiculoCollectionNew = tipoTransmissao.getVeiculoCollection();
            Collection<Veiculo> attachedVeiculoCollectionNew = new ArrayList<Veiculo>();
            for (Veiculo veiculoCollectionNewVeiculoToAttach : veiculoCollectionNew) {
                veiculoCollectionNewVeiculoToAttach = em.getReference(veiculoCollectionNewVeiculoToAttach.getClass(), veiculoCollectionNewVeiculoToAttach.getCodigoVeiculo());
                attachedVeiculoCollectionNew.add(veiculoCollectionNewVeiculoToAttach);
            }
            veiculoCollectionNew = attachedVeiculoCollectionNew;
            tipoTransmissao.setVeiculoCollection(veiculoCollectionNew);
            tipoTransmissao = em.merge(tipoTransmissao);
            for (Veiculo veiculoCollectionOldVeiculo : veiculoCollectionOld) {
                if (!veiculoCollectionNew.contains(veiculoCollectionOldVeiculo)) {
                    veiculoCollectionOldVeiculo.setCodigoTipoTransmissao(null);
                    veiculoCollectionOldVeiculo = em.merge(veiculoCollectionOldVeiculo);
                }
            }
            for (Veiculo veiculoCollectionNewVeiculo : veiculoCollectionNew) {
                if (!veiculoCollectionOld.contains(veiculoCollectionNewVeiculo)) {
                    TipoTransmissao oldCodigoTipoTransmissaoOfVeiculoCollectionNewVeiculo = veiculoCollectionNewVeiculo.getCodigoTipoTransmissao();
                    veiculoCollectionNewVeiculo.setCodigoTipoTransmissao(tipoTransmissao);
                    veiculoCollectionNewVeiculo = em.merge(veiculoCollectionNewVeiculo);
                    if (oldCodigoTipoTransmissaoOfVeiculoCollectionNewVeiculo != null && !oldCodigoTipoTransmissaoOfVeiculoCollectionNewVeiculo.equals(tipoTransmissao)) {
                        oldCodigoTipoTransmissaoOfVeiculoCollectionNewVeiculo.getVeiculoCollection().remove(veiculoCollectionNewVeiculo);
                        oldCodigoTipoTransmissaoOfVeiculoCollectionNewVeiculo = em.merge(oldCodigoTipoTransmissaoOfVeiculoCollectionNewVeiculo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoTransmissao.getCodigoTipoTransmissao();
                if (findTipoTransmissao(id) == null) {
                    throw new NonexistentEntityException("The tipoTransmissao with id " + id + " no longer exists.");
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
            TipoTransmissao tipoTransmissao;
            try {
                tipoTransmissao = em.getReference(TipoTransmissao.class, id);
                tipoTransmissao.getCodigoTipoTransmissao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoTransmissao with id " + id + " no longer exists.", enfe);
            }
            Collection<Veiculo> veiculoCollection = tipoTransmissao.getVeiculoCollection();
            for (Veiculo veiculoCollectionVeiculo : veiculoCollection) {
                veiculoCollectionVeiculo.setCodigoTipoTransmissao(null);
                veiculoCollectionVeiculo = em.merge(veiculoCollectionVeiculo);
            }
            em.remove(tipoTransmissao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoTransmissao> findTipoTransmissaoEntities() {
        return findTipoTransmissaoEntities(true, -1, -1);
    }

    public List<TipoTransmissao> findTipoTransmissaoEntities(int maxResults, int firstResult) {
        return findTipoTransmissaoEntities(false, maxResults, firstResult);
    }

    private List<TipoTransmissao> findTipoTransmissaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoTransmissao.class));
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

    public TipoTransmissao findTipoTransmissao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoTransmissao.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoTransmissaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoTransmissao> rt = cq.from(TipoTransmissao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
