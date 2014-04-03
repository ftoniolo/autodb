/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.test;

import br.com.autodb.model.controller.MarcaJpaController;
import br.com.autodb.model.dao.Marca;
import br.com.autodb.model.dao.Veiculo;
import br.com.autodb.model.dao.VeiculoBase;
import br.com.autodb.util.EMFUtil;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class TesteMarca {
    public static void main(String[] args) {
        new TesteMarca();
    }
    
    public TesteMarca() {
        EntityManagerFactory emf = EMFUtil.getEMF();
        MarcaJpaController marcaController = new MarcaJpaController(emf);
        List<Marca> marcas = marcaController.findMarcaEntities();
        for (Marca marca : marcas) {
            System.out.println(marca);
            Collection<VeiculoBase> veiculoBases = marca.getVeiculoBaseCollection();
            for (VeiculoBase veiculoBase : veiculoBases) {
                System.out.println("\t" + veiculoBase);
                Collection<Veiculo> veiculos = veiculoBase.getVeiculoCollection();
                for (Veiculo veiculo : veiculos) {
                    System.out.println("\t\t" + veiculo);
                }
            }
        }
        //System.out.println(marcas);
    }
}
