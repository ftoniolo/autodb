/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.controller;

import br.com.autodb.model.dao.TipoAspiracao;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ftoniolo
 */
@Stateless
public class TipoAspiracaoFacade extends AbstractFacade<TipoAspiracao> {
    @PersistenceContext(unitName = "br.com.autodb_autodb_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoAspiracaoFacade() {
        super(TipoAspiracao.class);
    }
    
}
