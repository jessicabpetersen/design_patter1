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
public abstract class MovimentoCommand implements Command {
    
    protected Log log;
    protected String jogador;
    protected String posicaoInicial;
    protected String posicaoFinal;
    
    public MovimentoCommand(Log log, String jogador, String posicaoInicial, String posicaoFInal){
        this.log = log;
        this.jogador = jogador;
        this.posicaoFinal = posicaoFInal;
        this.posicaoInicial = posicaoInicial;
    }
}
