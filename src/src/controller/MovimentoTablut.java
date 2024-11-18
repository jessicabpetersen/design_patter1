/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.swing.ImageIcon;
import model.Campo;
import model.CampoNormal;
import model.Preto;
import model.Refugio;

/**
 *
 * @author Jéssica Petersen
 */
public class MovimentoTablut extends Movimento {

    private static MovimentoTablut instance;

    public synchronized static MovimentoTablut getInstance() {
        if (instance == null) {
            instance = new MovimentoTablut();
        }
        return instance;
    }

    @Override
    public Campo[][] selecionarPreto(int coluna, int linha) {
        if (this.refugio[coluna][linha].getClass() == Refugio.class) {
            this.tabuleiro[coluna][linha].setImagem(new ImageIcon("img/pretorefugioclicado.png"));
        } else {
            this.tabuleiro[coluna][linha].setImagem(new ImageIcon("img/pretoclicado.png"));
        }
        return this.tabuleiro;
    }

    @Override
    public Campo[][] deselecionarPreto(int coluna, int linha) {
        if (this.refugio[coluna][linha].getClass() == Refugio.class) {
            this.tabuleiro[coluna][linha].setImagem(new ImageIcon("img/pretorefugio.png"));
        } else {
            this.tabuleiro[coluna][linha].setImagem(new ImageIcon("img/preto.png"));
        }
        return this.tabuleiro;
    }

    @Override
    public boolean verificaLinhaMovel(int menor, int maior, boolean pCima, int colunaClicada, int linhaClicada) {
        boolean achou = false;
        if (pCima) {
            for (int i = menor; i < maior; i++) {
                if(this.tabuleiro[colunaClicada][i].getClass() != CampoNormal.class && this.tabuleiro[colunaClicada][i].getClass() != Refugio.class){
                    achou = true; 
                    break;
                }
            }
        }else{
            for (int i = menor + 1; i <= maior; i++) {
                if(this.tabuleiro[colunaClicada][i].getClass() != CampoNormal.class && this.tabuleiro[colunaClicada][i].getClass() != Refugio.class){
                    achou = true; 
                    break;
                }
            }
        }
        return achou;
    }

    @Override
    public boolean verificaLColunaMovel(int menor, int maior, boolean pEsquerda, int linhaClicada, int colunaClicada) {
        boolean achou = false;
        if (pEsquerda) {
            for (int i = menor; i < maior; i++) {
                if (this.tabuleiro[i][linhaClicada].getClass() != CampoNormal.class && this.tabuleiro[i][linhaClicada].getClass() != Refugio.class) {
                    achou = true;
                    break;
                }
            }
        } else {
            for (int i = menor + 1; i <= maior; i++) {
                if (this.tabuleiro[i][linhaClicada].getClass() != CampoNormal.class && this.tabuleiro[i][linhaClicada].getClass() != Refugio.class) {
                    achou = true;
                    break;
                }
            }
        }
        return achou;
    }

    @Override
    public Campo[][] moverPreto(int coluna, int linha, int colunaAnterior, int linhaAnterior) {
        //verificar se o anterior era refugio
        if(this.refugio[colunaAnterior][linhaAnterior].getClass() == Refugio.class){
            //verificar se o proximo será refugio
            this.tabuleiro[colunaAnterior][linhaAnterior] = new Refugio();
            this.tabuleiro[coluna][linha] = new Preto();
            if(this.tabuleiro[coluna][linha].getClass() == Refugio.class){
                // era refugio e será refugio novamente
                this.tabuleiro[coluna][linha].setImagem(new ImageIcon("img/pretorefugio.png"));
            }
            
        }else{// nao era refugio anteriormente
            this.tabuleiro[colunaAnterior][linhaAnterior] = new CampoNormal();
            this.tabuleiro[coluna][linha] = new Preto();
            //verificar se o proximo será refugio
            if(this.refugio[coluna][linha].getClass() == Refugio.class){
                 // nao era refugio e vai ser refugio
                 this.tabuleiro[coluna][linha].setImagem(new ImageIcon("img/pretorefugio.png"));
            }
        }
        return this.tabuleiro;
    }

}
