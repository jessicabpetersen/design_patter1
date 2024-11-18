/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.MovimentoTablut.getInstance;

/**
 *
 * @author Jéssica Petersen
 */
public class FactoryTablut extends Factory{
    
    private Tabuleiro Tablut;
    private Movimento movimento;
    
    @Override
    public Tabuleiro createTabuleiro() {
        if(Tablut == null)
            Tablut = new TabuleiroTablut();
        return Tablut;
    }

    @Override
    public Movimento createMovimento() {
        if(movimento == null)
            movimento = getInstance();
        return movimento;
    }
}
