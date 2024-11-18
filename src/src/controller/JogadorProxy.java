/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Jogador;

/**
 *
 * @author Jéssica Petersen
 */
public class JogadorProxy extends Jogador {

    public JogadorProxy() {
        super.setJogador("jogador1");
    }
    public void inverter(){
        if(super.getJogador() == "jogador1"){
            super.setJogador("jogador2");
        }else{
            super.setJogador("jogador1");
        }
    }
    
    public boolean verificarJogador1(){
        if(super.getJogador() == "jogador1")
            return true;
        return false;
    }
}
