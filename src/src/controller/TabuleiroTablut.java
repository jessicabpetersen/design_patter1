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
public class TabuleiroTablut extends Tabuleiro {
    private Campo[][] tabuleiro;
    private Campo[][] refugio;

    @Override
    public Campo[][] distribuirCampos(int normal, int rei) {
        this.tabuleiro = new Campo[9][9];
        this.refugio = new Campo[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (((i == 0 || i == 8) && (j == 3 || j == 4 || j == 5)) || ((i == 1 || i == 7) && j == 4)
                        || ((j == 0 || j == 8) && (i == 3 || i == 4 || i == 5)) || ((j == 1 || j == 7) && i == 4)) {
                    this.tabuleiro[i][j] = new Refugio();
                    this.refugio[i][j] = new Refugio();

                } else {
                    if( i == j && i == tabuleiro.length / 2){
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
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if ((i == 4 && (j == 2 || j == 3 || j == 5 || j == 6)) || (j == 4 && (i == 2 || i == 3 || i == 5 || i == 6))) {
                    this.tabuleiro[i][j] = new Branco();
                } else {
                    if (i == 4 && j == 4) {
                        this.tabuleiro[i][j] = new Rei();
                        this.tabuleiro[i][j].setImagem(new ImageIcon("img/reibrancoregufio.png"));
                    } else {
                        if (((i == 0 || i == 8) && (j == 3 || j == 4 || j == 5)) || ((i == 1 || i == 7) && j == 4)
                                || ((j == 0 || j == 8) && (i == 3 || i == 4 || i == 5)) || ((j == 1 || j == 7) && i == 4)) {
                            this.tabuleiro[i][j] = new Preto();
                            this.tabuleiro[i][j].setImagem(new ImageIcon("img/pretorefugio.png"));
                        }
                    }
                }
            }
        }
        return this.tabuleiro;
    }

    @Override
    public Campo[][] getRefugio() {
         return refugio;
    }


}
