/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Log;

/**
 *
 * @author Jéssica Petersen
 */
public class AddCommand extends MovimentoCommand{
    
    public AddCommand(Log log, String jogador, String posicaoInicial, String posicaoFInal){
        super(log, jogador, posicaoInicial, posicaoFInal);
    }

    @Override
    public void execute() {
        log.add(jogador, posicaoInicial, posicaoFinal);
    }
    
    @Override
    public String toString(){
         return "Jogador: "+jogador+"\nposição inicial: "+ posicaoInicial+"\n posição final:"+posicaoFinal+"\n\n";
    }
    
}
