/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.swing.ImageIcon;
import model.Branco;
import model.Campo;
import model.CampoNormal;
import model.Preto;
import model.Refugio;
import model.Rei;
import model.Trono;

/**
 *
 * @author Jéssica Petersen
 */
public class TabuleiroBrandubh extends Tabuleiro {
    private Campo[][] tabuleiro;
    private Campo[][] refugio;

    @Override
    public Campo[][] distribuirCampos(int normal, int rei) {
        this.tabuleiro = new Campo[7][7];
        this.refugio = new Campo[7][7];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if ((i == j && (i == 0 || i == tabuleiro.length - 1)) || (i == 0 && j == tabuleiro.length - 1) || (j == 0 && i == tabuleiro.length - 1)) {
                    this.tabuleiro[i][j] = new Refugio();
                    this.refugio[i][j] = new Refugio();
                } else {
                    if(i == j && i == tabuleiro.length / 2){
                        this.tabuleiro[i][j] = new Trono();
                        this.refugio[i][j] = new Trono();
                    }else{
                        this.tabuleiro[i][j] = new CampoNormal();
                        this.refugio[i][j] = new CampoNormal();
                    }
                }
            }
        }
        return this.tabuleiro;
    }

    @Override
    public Campo[][] distribuiPecas() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if ((j == 3 && (i == 0 || i == 1 || i == 5 || i == 6)) || (i == 3 && (j == 0 || j == 1 || j == 5 || j == 6))) {
                    tabuleiro[i][j] = new Preto();
                } else {
                    if (j == 3 && i == 3) {
                        this.tabuleiro[i][j] = new Rei();
                        this.tabuleiro[i][j].setImagem(new ImageIcon("img/reibrancoregufio.png"));
                    } else {
                        if ((i == 3 && (j == 2 || j == 4)) || (j == 3 && (i == 2 || i == 4))) {
                            tabuleiro[i][j] = new Branco();
                        }
                    }
                }
            }
        }
        return tabuleiro;
    }
    
    @Override
    public Campo[][] getRefugio() {
         return refugio;
    }
}
