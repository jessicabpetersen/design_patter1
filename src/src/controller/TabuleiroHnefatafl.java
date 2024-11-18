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
public class TabuleiroHnefatafl extends Tabuleiro {
    
    private Campo[][] tabuleiro;
    private Campo[][] refugio;

    @Override
    public Campo[][] distribuirCampos(int normal, int rei) {
        this.tabuleiro = new Campo[11][11];
        this.refugio = new Campo[11][11];
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
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
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (((i == 0 || i == 10) && (j == 3 || j == 4 || j == 5 || j == 6 || j == 7)) || ((i == 1 || i == 9) && j == 5)
                        || ((j == 0 || j == 10) && (i == 3 || i == 4 || i == 5 || i == 6 || i == 7)) || ((j == 1 || j == 9) && i == 5)) {
                    this.tabuleiro[i][j] = new Preto();
                } else {
                    if (((i == 3 || i == 7) && j == 5) || ((i == 4 || i == 6) && (j == 4 || j == 5 || j == 6)) || (i == 5 && (j == 3 || j == 4 || j == 6 || j == 7))) {
                        this.tabuleiro[i][j] = new Branco();
                    } else {
                        if (i == 5 && j == 5) {
                            this.tabuleiro[i][j] = new Rei();
                            this.tabuleiro[i][j].setImagem(new ImageIcon("img/reibrancoregufio.png"));
                        }
                    }
                }
            }
        }
        return tabuleiro;
    }

    @Override
    public Campo[][] getRefugio() {
        return this.refugio;
    }
    
}
