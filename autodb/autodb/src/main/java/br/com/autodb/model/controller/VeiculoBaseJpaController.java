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
import br.com.autodb.model.dao.TipoVeiculo;
import br.com.autodb.model.dao.TipoCarrocaria;
import br.com.autodb.model.dao.Modelo;
import br.com.autodb.model.dao.Marca;
import br.com.autodb.model.dao.VeiculoBaseRelacionado;
import java.util.ArrayList;
import java.util.Collection;
import br.com.autodb.model.dao.Veiculo;
import br.com.autodb.model.dao.VeiculoBase;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class VeiculoBaseJpaController implements Serializable {

    public VeiculoBaseJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VeiculoBase veiculoBase) {
        if (veiculoBase.getVeiculoBaseRelacionadoCollection() == null) {
            veiculoBase.setVeiculoBaseRelacionadoCollection(new ArrayList<VeiculoBaseRelacionado>());
        }
        if (veiculoBase.getVeiculoBaseRelacionadoCollection1() == null) {
            veiculoBase.setVeiculoBaseRelacionadoCollection1(new ArrayList<VeiculoBaseRelacionado>());
        }
        if (veiculoBase.getVeiculoCollection() == null) {
            veiculoBase.setVeiculoCollection(new ArrayList<Veiculo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoVeiculo codigoTipoVeiculo = veiculoBase.getCodigoTipoVeiculo();
            if (codigoTipoVeiculo != null) {
                codigoTipoVeiculo = em.getReference(codigoTipoVeiculo.getClass(), codigoTipoVeiculo.getCodigoTipoVeiculo());
                veiculoBase.setCodigoTipoVeiculo(codigoTipoVeiculo);
            }
            TipoCarrocaria codigoTipoCarrocaria = veiculoBase.getCodigoTipoCarrocaria();
            if (codigoTipoCarrocaria != null) {
                codigoTipoCarrocaria = em.getReference(codigoTipoCarrocaria.getClass(), codigoTipoCarrocaria.getCodigoTipoCarrocaria());
                veiculoBase.setCodigoTipoCarrocaria(codigoTipoCarrocaria);
            }
            Modelo codigoModelo = veiculoBase.getCodigoModelo();
            if (codigoModelo != null) {
                codigoModelo = em.getReference(codigoModelo.getClass(), codigoModelo.getCodigoModelo());
                veiculoBase.setCodigoModelo(codigoModelo);
            }
            Marca codigoMarca = veiculoBase.getCodigoMarca();
            if (codigoMarca != null) {
                codigoMarca = em.getReference(codigoMarca.getClass(), codigoMarca.getCodigoMarca());
                veiculoBase.setCodigoMarca(codigoMarca);
            }
            Collection<VeiculoBaseRelacionado> attachedVeiculoBaseRelacionadoCollection = new ArrayList<VeiculoBaseRelacionado>();
            for (VeiculoBaseRelacionado veiculoBaseRelacionadoCollectionVeiculoBaseRelacionadoToAttach : veiculoBase.getVeiculoBaseRelacionadoCollection()) {
                veiculoBaseRelacionadoCollectionVeiculoBaseRelacionadoToAttach = em.getReference(veiculoBaseRelacionadoCollectionVeiculoBaseRelacionadoToAttach.getClass(), veiculoBaseRelacionadoCollectionVeiculoBaseRelacionadoToAttach.getCodigoVeiculoBaseRelacionado());
                attachedVeiculoBaseRelacionadoCollection.add(veiculoBaseRelacionadoCollectionVeiculoBaseRelacionadoToAttach);
            }
            veiculoBase.setVeiculoBaseRelacionadoCollection(attachedVeiculoBaseRelacionadoCollection);
            Collection<VeiculoBaseRelacionado> attachedVeiculoBaseRelacionadoCollection1 = new ArrayList<VeiculoBaseRelacionado>();
            for (VeiculoBaseRelacionado veiculoBaseRelacionadoCollection1VeiculoBaseRelacionadoToAttach : veiculoBase.getVeiculoBaseRelacionadoCollection1()) {
                veiculoBaseRelacionadoCollection1VeiculoBaseRelacionadoToAttach = em.getReference(veiculoBaseRelacionadoCollection1VeiculoBaseRelacionadoToAttach.getClass(), veiculoBaseRelacionadoCollection1VeiculoBaseRelacionadoToAttach.getCodigoVeiculoBaseRelacionado());
                attachedVeiculoBaseRelacionadoCollection1.add(veiculoBaseRelacionadoCollection1VeiculoBaseRelacionadoToAttach);
            }
            veiculoBase.setVeiculoBaseRelacionadoCollection1(attachedVeiculoBaseRelacionadoCollection1);
            Collection<Veiculo> attachedVeiculoCollection = new ArrayList<Veiculo>();
            for (Veiculo veiculoCollectionVeiculoToAttach : veiculoBase.getVeiculoCollection()) {
                veiculoCollectionVeiculoToAttach = em.getReference(veiculoCollectionVeiculoToAttach.getClass(), veiculoCollectionVeiculoToAttach.getCodigoVeiculo());
                attachedVeiculoCollection.add(veiculoCollectionVeiculoToAttach);
            }
            veiculoBase.setVeiculoCollection(attachedVeiculoCollection);
            em.persist(veiculoBase);
            if (codigoTipoVeiculo != null) {
                codigoTipoVeiculo.getVeiculoBaseCollection().add(veiculoBase);
                codigoTipoVeiculo = em.merge(codigoTipoVeiculo);
            }
            if (codigoTipoCarrocaria != null) {
                codigoTipoCarrocaria.getVeiculoBaseCollection().add(veiculoBase);
                codigoTipoCarrocaria = em.merge(codigoTipoCarrocaria);
            }
            if (codigoModelo != null) {
                codigoModelo.getVeiculoBaseCollection().add(veiculoBase);
                codigoModelo = em.merge(codigoModelo);
            }
            if (codigoMarca != null) {
                codigoMarca.getVeiculoBaseCollection().add(veiculoBase);
                codigoMarca = em.merge(codigoMarca);
            }
            for (VeiculoBaseRelacionado veiculoBaseRelacionadoCollectionVeiculoBaseRelacionado : veiculoBase.getVeiculoBaseRelacionadoCollection()) {
                VeiculoBase oldCodigoVeiculoBaseBOfVeiculoBaseRelacionadoCollectionVeiculoBaseRelacionado = veiculoBaseRelacionadoCollectionVeiculoBaseRelacionado.getCodigoVeiculoBaseB();
                veiculoBaseRelacionadoCollectionVeiculoBaseRelacionado.setCodigoVeiculoBaseB(veiculoBase);
                veiculoBaseRelacionadoCollectionVeiculoBaseRelacionado = em.merge(veiculoBaseRelacionadoCollectionVeiculoBaseRelacionado);
                if (oldCodigoVeiculoBaseBOfVeiculoBaseRelacionadoCollectionVeiculoBaseRelacionado != null) {
                    oldCodigoVeiculoBaseBOfVeiculoBaseRelacionadoCollectionVeiculoBaseRelacionado.getVeiculoBaseRelacionadoCollection().remove(veiculoBaseRelacionadoCollectionVeiculoBaseRelacionado);
                    oldCodigoVeiculoBaseBOfVeiculoBaseRelacionadoCollectionVeiculoBaseRelacionado = em.merge(oldCodigoVeiculoBaseBOfVeiculoBaseRelacionadoCollectionVeiculoBaseRelacionado);
                }
            }
            for (VeiculoBaseRelacionado veiculoBaseRelacionadoCollection1VeiculoBaseRelacionado : veiculoBase.getVeiculoBaseRelacionadoCollection1()) {
                VeiculoBase oldCodigoVeiculoBaseAOfVeiculoBaseRelacionadoCollection1VeiculoBaseRelacionado = veiculoBaseRelacionadoCollection1VeiculoBaseRelacionado.getCodigoVeiculoBaseA();
                veiculoBaseRelacionadoCollection1VeiculoBaseRelacionado.setCodigoVeiculoBaseA(veiculoBase);
                veiculoBaseRelacionadoCollection1VeiculoBaseRelacionado = em.merge(veiculoBaseRelacionadoCollection1VeiculoBaseRelacionado);
                if (oldCodigoVeiculoBaseAOfVeiculoBaseRelacionadoCollection1VeiculoBaseRelacionado != null) {
                    oldCodigoVeiculoBaseAOfVeiculoBaseRelacionadoCollection1VeiculoBaseRelacionado.getVeiculoBaseRelacionadoCollection1().remove(veiculoBaseRelacionadoCollection1VeiculoBaseRelacionado);
                    oldCodigoVeiculoBaseAOfVeiculoBaseRelacionadoCollection1VeiculoBaseRelacionado = em.merge(oldCodigoVeiculoBaseAOfVeiculoBaseRelacionadoCollection1VeiculoBaseRelacionado);
                }
            }
            for (Veiculo veiculoCollectionVeiculo : veiculoBase.getVeiculoCollection()) {
                VeiculoBase oldCodigoVeiculoBaseOfVeiculoCollectionVeiculo = veiculoCollectionVeiculo.getCodigoVeiculoBase();
                veiculoCollectionVeiculo.setCodigoVeiculoBase(veiculoBase);
                veiculoCollectionVeiculo = em.merge(veiculoCollectionVeiculo);
                if (oldCodigoVeiculoBaseOfVeiculoCollectionVeiculo != null) {
                    oldCodigoVeiculoBaseOfVeiculoCollectionVeiculo.getVeiculoCollection().remove(veiculoCollectionVeiculo);
                    oldCodigoVeiculoBaseOfVeiculoCollectionVeiculo = em.merge(oldCodigoVeiculoBaseOfVeiculoCollectionVeiculo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VeiculoBase veiculoBase) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            VeiculoBase persistentVeiculoBase = em.find(VeiculoBase.class, veiculoBase.getCodigoVeiculoBase());
            TipoVeiculo codigoTipoVeiculoOld = persistentVeiculoBase.getCodigoTipoVeiculo();
            TipoVeiculo codigoTipoVeiculoNew = veiculoBase.getCodigoTipoVeiculo();
            TipoCarrocaria codigoTipoCarrocariaOld = persistentVeiculoBase.getCodigoTipoCarrocaria();
            TipoCarrocaria codigoTipoCarrocariaNew = veiculoBase.getCodigoTipoCarrocaria();
            Modelo codigoModeloOld = persistentVeiculoBase.getCodigoModelo();
            Modelo codigoModeloNew = veiculoBase.getCodigoModelo();
            Marca codigoMarcaOld = persistentVeiculoBase.getCodigoMarca();
            Marca codigoMarcaNew = veiculoBase.getCodigoMarca();
            Collection<VeiculoBaseRelacionado> veiculoBaseRelacionadoCollectionOld = persistentVeiculoBase.getVeiculoBaseRelacionadoCollection();
            Collection<VeiculoBaseRelacionado> veiculoBaseRelacionadoCollectionNew = veiculoBase.getVeiculoBaseRelacionadoCollection();
            Collection<VeiculoBaseRelacionado> veiculoBaseRelacionadoCollection1Old = persistentVeiculoBase.getVeiculoBaseRelacionadoCollection1();
            Collection<VeiculoBaseRelacionado> veiculoBaseRelacionadoCollection1New = veiculoBase.getVeiculoBaseRelacionadoCollection1();
            Collection<Veiculo> veiculoCollectionOld = persistentVeiculoBase.getVeiculoCollection();
            Collection<Veiculo> veiculoCollectionNew = veiculoBase.getVeiculoCollection();
            List<String> illegalOrphanMessages = null;
            for (VeiculoBaseRelacionado veiculoBaseRelacionadoCollectionOldVeiculoBaseRelacionado : veiculoBaseRelacionadoCollectionOld) {
                if (!veiculoBaseRelacionadoCollectionNew.contains(veiculoBaseRelacionadoCollectionOldVeiculoBaseRelacionado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain VeiculoBaseRelacionado " + veiculoBaseRelacionadoCollectionOldVeiculoBaseRelacionado + " since its codigoVeiculoBaseB field is not nullable.");
                }
            }
            for (VeiculoBaseRelacionado veiculoBaseRelacionadoCollection1OldVeiculoBaseRelacionado : veiculoBaseRelacionadoCollection1Old) {
                if (!veiculoBaseRelacionadoCollection1New.contains(veiculoBaseRelacionadoCollection1OldVeiculoBaseRelacionado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain VeiculoBaseRelacionado " + veiculoBaseRelacionadoCollection1OldVeiculoBaseRelacionado + " since its codigoVeiculoBaseA field is not nullable.");
                }
            }
            for (Veiculo veiculoCollectionOldVeiculo : veiculoCollectionOld) {
                if (!veiculoCollectionNew.contains(veiculoCollectionOldVeiculo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Veiculo " + veiculoCollectionOldVeiculo + " since its codigoVeiculoBase field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codigoTipoVeiculoNew != null) {
                codigoTipoVeiculoNew = em.getReference(codigoTipoVeiculoNew.getClass(), codigoTipoVeiculoNew.getCodigoTipoVeiculo());
                veiculoBase.setCodigoTipoVeiculo(codigoTipoVeiculoNew);
            }
            if (codigoTipoCarrocariaNew != null) {
                codigoTipoCarrocariaNew = em.getReference(codigoTipoCarrocariaNew.getClass(), codigoTipoCarrocariaNew.getCodigoTipoCarrocaria());
                veiculoBase.setCodigoTipoCarrocaria(codigoTipoCarrocariaNew);
            }
            if (codigoModeloNew != null) {
                codigoModeloNew = em.getReference(codigoModeloNew.getClass(), codigoModeloNew.getCodigoModelo());
                veiculoBase.setCodigoModelo(codigoModeloNew);
            }
            if (codigoMarcaNew != null) {
                codigoMarcaNew = em.getReference(codigoMarcaNew.getClass(), codigoMarcaNew.getCodigoMarca());
                veiculoBase.setCodigoMarca(codigoMarcaNew);
            }
            Collection<VeiculoBaseRelacionado> attachedVeiculoBaseRelacionadoCollectionNew = new ArrayList<VeiculoBaseRelacionado>();
            for (VeiculoBaseRelacionado veiculoBaseRelacionadoCollectionNewVeiculoBaseRelacionadoToAttach : veiculoBaseRelacionadoCollectionNew) {
                veiculoBaseRelacionadoCollectionNewVeiculoBaseRelacionadoToAttach = em.getReference(veiculoBaseRelacionadoCollectionNewVeiculoBaseRelacionadoToAttach.getClass(), veiculoBaseRelacionadoCollectionNewVeiculoBaseRelacionadoToAttach.getCodigoVeiculoBaseRelacionado());
                attachedVeiculoBaseRelacionadoCollectionNew.add(veiculoBaseRelacionadoCollectionNewVeiculoBaseRelacionadoToAttach);
            }
            veiculoBaseRelacionadoCollectionNew = attachedVeiculoBaseRelacionadoCollectionNew;
            veiculoBase.setVeiculoBaseRelacionadoCollection(veiculoBaseRelacionadoCollectionNew);
            Collection<VeiculoBaseRelacionado> attachedVeiculoBaseRelacionadoCollection1New = new ArrayList<VeiculoBaseRelacionado>();
            for (VeiculoBaseRelacionado veiculoBaseRelacionadoCollection1NewVeiculoBaseRelacionadoToAttach : veiculoBaseRelacionadoCollection1New) {
                veiculoBaseRelacionadoCollection1NewVeiculoBaseRelacionadoToAttach = em.getReference(veiculoBaseRelacionadoCollection1NewVeiculoBaseRelacionadoToAttach.getClass(), veiculoBaseRelacionadoCollection1NewVeiculoBaseRelacionadoToAttach.getCodigoVeiculoBaseRelacionado());
                attachedVeiculoBaseRelacionadoCollection1New.add(veiculoBaseRelacionadoCollection1NewVeiculoBaseRelacionadoToAttach);
            }
            veiculoBaseRelacionadoCollection1New = attachedVeiculoBaseRelacionadoCollection1New;
            veiculoBase.setVeiculoBaseRelacionadoCollection1(veiculoBaseRelacionadoCollection1New);
            Collection<Veiculo> attachedVeiculoCollectionNew = new ArrayList<Veiculo>();
            for (Veiculo veiculoCollectionNewVeiculoToAttach : veiculoCollectionNew) {
                veiculoCollectionNewVeiculoToAttach = em.getReference(veiculoCollectionNewVeiculoToAttach.getClass(), veiculoCollectionNewVeiculoToAttach.getCodigoVeiculo());
                attachedVeiculoCollectionNew.add(veiculoCollectionNewVeiculoToAttach);
            }
            veiculoCollectionNew = attachedVeiculoCollectionNew;
            veiculoBase.setVeiculoCollection(veiculoCollectionNew);
            veiculoBase = em.merge(veiculoBase);
            if (codigoTipoVeiculoOld != null && !codigoTipoVeiculoOld.equals(codigoTipoVeiculoNew)) {
                codigoTipoVeiculoOld.getVeiculoBaseCollection().remove(veiculoBase);
                codigoTipoVeiculoOld = em.merge(codigoTipoVeiculoOld);
            }
            if (codigoTipoVeiculoNew != null && !codigoTipoVeiculoNew.equals(codigoTipoVeiculoOld)) {
                codigoTipoVeiculoNew.getVeiculoBaseCollection().add(veiculoBase);
                codigoTipoVeiculoNew = em.merge(codigoTipoVeiculoNew);
            }
            if (codigoTipoCarrocariaOld != null && !codigoTipoCarrocariaOld.equals(codigoTipoCarrocariaNew)) {
                codigoTipoCarrocariaOld.getVeiculoBaseCollection().remove(veiculoBase);
                codigoTipoCarrocariaOld = em.merge(codigoTipoCarrocariaOld);
            }
            if (codigoTipoCarrocariaNew != null && !codigoTipoCarrocariaNew.equals(codigoTipoCarrocariaOld)) {
                codigoTipoCarrocariaNew.getVeiculoBaseCollection().add(veiculoBase);
                codigoTipoCarrocariaNew = em.merge(codigoTipoCarrocariaNew);
            }
            if (codigoModeloOld != null && !codigoModeloOld.equals(codigoModeloNew)) {
                codigoModeloOld.getVeiculoBaseCollection().remove(veiculoBase);
                codigoModeloOld = em.merge(codigoModeloOld);
            }
            if (codigoModeloNew != null && !codigoModeloNew.equals(codigoModeloOld)) {
                codigoModeloNew.getVeiculoBaseCollection().add(veiculoBase);
                codigoModeloNew = em.merge(codigoModeloNew);
            }
            if (codigoMarcaOld != null && !codigoMarcaOld.equals(codigoMarcaNew)) {
                codigoMarcaOld.getVeiculoBaseCollection().remove(veiculoBase);
                codigoMarcaOld = em.merge(codigoMarcaOld);
            }
            if (codigoMarcaNew != null && !codigoMarcaNew.equals(codigoMarcaOld)) {
                codigoMarcaNew.getVeiculoBaseCollection().add(veiculoBase);
                codigoMarcaNew = em.merge(codigoMarcaNew);
            }
            for (VeiculoBaseRelacionado veiculoBaseRelacionadoCollectionNewVeiculoBaseRelacionado : veiculoBaseRelacionadoCollectionNew) {
                if (!veiculoBaseRelacionadoCollectionOld.contains(veiculoBaseRelacionadoCollectionNewVeiculoBaseRelacionado)) {
                    VeiculoBase oldCodigoVeiculoBaseBOfVeiculoBaseRelacionadoCollectionNewVeiculoBaseRelacionado = veiculoBaseRelacionadoCollectionNewVeiculoBaseRelacionado.getCodigoVeiculoBaseB();
                    veiculoBaseRelacionadoCollectionNewVeiculoBaseRelacionado.setCodigoVeiculoBaseB(veiculoBase);
                    veiculoBaseRelacionadoCollectionNewVeiculoBaseRelacionado = em.merge(veiculoBaseRelacionadoCollectionNewVeiculoBaseRelacionado);
                    if (oldCodigoVeiculoBaseBOfVeiculoBaseRelacionadoCollectionNewVeiculoBaseRelacionado != null && !oldCodigoVeiculoBaseBOfVeiculoBaseRelacionadoCollectionNewVeiculoBaseRelacionado.equals(veiculoBase)) {
                        oldCodigoVeiculoBaseBOfVeiculoBaseRelacionadoCollectionNewVeiculoBaseRelacionado.getVeiculoBaseRelacionadoCollection().remove(veiculoBaseRelacionadoCollectionNewVeiculoBaseRelacionado);
                        oldCodigoVeiculoBaseBOfVeiculoBaseRelacionadoCollectionNewVeiculoBaseRelacionado = em.merge(oldCodigoVeiculoBaseBOfVeiculoBaseRelacionadoCollectionNewVeiculoBaseRelacionado);
                    }
                }
            }
            for (VeiculoBaseRelacionado veiculoBaseRelacionadoCollection1NewVeiculoBaseRelacionado : veiculoBaseRelacionadoCollection1New) {
                if (!veiculoBaseRelacionadoCollection1Old.contains(veiculoBaseRelacionadoCollection1NewVeiculoBaseRelacionado)) {
                    VeiculoBase oldCodigoVeiculoBaseAOfVeiculoBaseRelacionadoCollection1NewVeiculoBaseRelacionado = veiculoBaseRelacionadoCollection1NewVeiculoBaseRelacionado.getCodigoVeiculoBaseA();
                    veiculoBaseRelacionadoCollection1NewVeiculoBaseRelacionado.setCodigoVeiculoBaseA(veiculoBase);
                    veiculoBaseRelacionadoCollection1NewVeiculoBaseRelacionado = em.merge(veiculoBaseRelacionadoCollection1NewVeiculoBaseRelacionado);
                    if (oldCodigoVeiculoBaseAOfVeiculoBaseRelacionadoCollection1NewVeiculoBaseRelacionado != null && !oldCodigoVeiculoBaseAOfVeiculoBaseRelacionadoCollection1NewVeiculoBaseRelacionado.equals(veiculoBase)) {
                        oldCodigoVeiculoBaseAOfVeiculoBaseRelacionadoCollection1NewVeiculoBaseRelacionado.getVeiculoBaseRelacionadoCollection1().remove(veiculoBaseRelacionadoCollection1NewVeiculoBaseRelacionado);
                        oldCodigoVeiculoBaseAOfVeiculoBaseRelacionadoCollection1NewVeiculoBaseRelacionado = em.merge(oldCodigoVeiculoBaseAOfVeiculoBaseRelacionadoCollection1NewVeiculoBaseRelacionado);
                    }
                }
            }
            for (Veiculo veiculoCollectionNewVeiculo : veiculoCollectionNew) {
                if (!veiculoCollectionOld.contains(veiculoCollectionNewVeiculo)) {
                    VeiculoBase oldCodigoVeiculoBaseOfVeiculoCollectionNewVeiculo = veiculoCollectionNewVeiculo.getCodigoVeiculoBase();
                    veiculoCollectionNewVeiculo.setCodigoVeiculoBase(veiculoBase);
                    veiculoCollectionNewVeiculo = em.merge(veiculoCollectionNewVeiculo);
                    if (oldCodigoVeiculoBaseOfVeiculoCollectionNewVeiculo != null && !oldCodigoVeiculoBaseOfVeiculoCollectionNewVeiculo.equals(veiculoBase)) {
                        oldCodigoVeiculoBaseOfVeiculoCollectionNewVeiculo.getVeiculoCollection().remove(veiculoCollectionNewVeiculo);
                        oldCodigoVeiculoBaseOfVeiculoCollectionNewVeiculo = em.merge(oldCodigoVeiculoBaseOfVeiculoCollectionNewVeiculo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = veiculoBase.getCodigoVeiculoBase();
                if (findVeiculoBase(id) == null) {
                    throw new NonexistentEntityException("The veiculoBase with id " + id + " no longer exists.");
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
            VeiculoBase veiculoBase;
            try {
                veiculoBase = em.getReference(VeiculoBase.class, id);
                veiculoBase.getCodigoVeiculoBase();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The veiculoBase with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<VeiculoBaseRelacionado> veiculoBaseRelacionadoCollectionOrphanCheck = veiculoBase.getVeiculoBaseRelacionadoCollection();
            for (VeiculoBaseRelacionado veiculoBaseRelacionadoCollectionOrphanCheckVeiculoBaseRelacionado : veiculoBaseRelacionadoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This VeiculoBase (" + veiculoBase + ") cannot be destroyed since the VeiculoBaseRelacionado " + veiculoBaseRelacionadoCollectionOrphanCheckVeiculoBaseRelacionado + " in its veiculoBaseRelacionadoCollection field has a non-nullable codigoVeiculoBaseB field.");
            }
            Collection<VeiculoBaseRelacionado> veiculoBaseRelacionadoCollection1OrphanCheck = veiculoBase.getVeiculoBaseRelacionadoCollection1();
            for (VeiculoBaseRelacionado veiculoBaseRelacionadoCollection1OrphanCheckVeiculoBaseRelacionado : veiculoBaseRelacionadoCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This VeiculoBase (" + veiculoBase + ") cannot be destroyed since the VeiculoBaseRelacionado " + veiculoBaseRelacionadoCollection1OrphanCheckVeiculoBaseRelacionado + " in its veiculoBaseRelacionadoCollection1 field has a non-nullable codigoVeiculoBaseA field.");
            }
            Collection<Veiculo> veiculoCollectionOrphanCheck = veiculoBase.getVeiculoCollection();
            for (Veiculo veiculoCollectionOrphanCheckVeiculo : veiculoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This VeiculoBase (" + veiculoBase + ") cannot be destroyed since the Veiculo " + veiculoCollectionOrphanCheckVeiculo + " in its veiculoCollection field has a non-nullable codigoVeiculoBase field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TipoVeiculo codigoTipoVeiculo = veiculoBase.getCodigoTipoVeiculo();
            if (codigoTipoVeiculo != null) {
                codigoTipoVeiculo.getVeiculoBaseCollection().remove(veiculoBase);
                codigoTipoVeiculo = em.merge(codigoTipoVeiculo);
            }
            TipoCarrocaria codigoTipoCarrocaria = veiculoBase.getCodigoTipoCarrocaria();
            if (codigoTipoCarrocaria != null) {
                codigoTipoCarrocaria.getVeiculoBaseCollection().remove(veiculoBase);
                codigoTipoCarrocaria = em.merge(codigoTipoCarrocaria);
            }
            Modelo codigoModelo = veiculoBase.getCodigoModelo();
            if (codigoModelo != null) {
                codigoModelo.getVeiculoBaseCollection().remove(veiculoBase);
                codigoModelo = em.merge(codigoModelo);
            }
            Marca codigoMarca = veiculoBase.getCodigoMarca();
            if (codigoMarca != null) {
                codigoMarca.getVeiculoBaseCollection().remove(veiculoBase);
                codigoMarca = em.merge(codigoMarca);
            }
            em.remove(veiculoBase);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VeiculoBase> findVeiculoBaseEntities() {
        return findVeiculoBaseEntities(true, -1, -1);
    }

    public List<VeiculoBase> findVeiculoBaseEntities(int maxResults, int firstResult) {
        return findVeiculoBaseEntities(false, maxResults, firstResult);
    }

    private List<VeiculoBase> findVeiculoBaseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VeiculoBase.class));
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

    public VeiculoBase findVeiculoBase(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VeiculoBase.class, id);
        } finally {
            em.close();
        }
    }

    public int getVeiculoBaseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VeiculoBase> rt = cq.from(VeiculoBase.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
