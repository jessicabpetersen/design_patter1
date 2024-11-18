/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.swing.Icon;

/**
 *
 * @author Jéssica Petersen
 */
public interface Observado {

    void addObservador(Observador obs);

    void inicializar();

    Icon getPeca(int col, int row);

    int getTamanho();

    void onMouseClicado(int linha, int coluna);

    void escolhaJogo(int tabuleiro, int movimentoNormal, int movimentoRei);

    void distribuiPecas();

    void novoJogo();

    String logMovimento();

}
