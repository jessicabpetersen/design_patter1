/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.MovimentoOutros.getInstance;

/**
 *
 * @author Jéssica Petersen
 */
public class FactoryBrandubh extends Factory{
    
    private Tabuleiro Brandubh;
    private Movimento movimento;
    
    @Override
    public Tabuleiro createTabuleiro() {
        if(Brandubh == null)
            Brandubh = new TabuleiroBrandubh();
        return Brandubh;
    }

    @Override
    public Movimento createMovimento() {
        if(movimento == null)
            movimento = getInstance();
        return movimento;
    }
}
