/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Campo;

/**
 *
 * @author Jéssica Petersen
 */
public abstract class Tabuleiro  {
    
    /**
     * Distribui os Campos na tela (CampoNormal ou Refugio) de acordo com o tabuleiro
     * Especifico para os 3
     * @param normal
     * @param rei
     * @return tabuleiro
     */
    public abstract Campo[][] distribuirCampos(int normal, int rei);
    
    /**
     * Distribui as peças (Branco - Preto - Rei) nas casas
     * Especifico para os 3
     * @return tabuleiro
     */
    public abstract Campo[][] distribuiPecas();
    
    public abstract Campo[][] getRefugio();
    
}
