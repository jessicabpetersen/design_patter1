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
import model.Refugio;
import model.Rei;
import model.Trono;

/**
 *
 * @author Jéssica Petersen
 */
public abstract class Movimento {
    protected Campo[][] tabuleiro;
    protected Campo[][] refugio;
    
    
    public void setTabuleiros(Campo[][] tabuleiro, Campo[][] refugio){
        this.tabuleiro = tabuleiro;
        this.refugio = refugio;
    }
    
    public Campo[][] moverBranco(int coluna, int linha, int linhaAnterior, int colunaAnterior, boolean rei){
        if(rei){//rei
            //verificar se ele estava em refugio
            if(this.refugio[colunaAnterior][linhaAnterior].getClass() == Refugio.class || this.refugio[colunaAnterior][linhaAnterior].getClass() == Trono.class){//estava em refugio
                //verificar se vai para refugio
                if(this.refugio[colunaAnterior][linhaAnterior].getClass() == Refugio.class ){
                    this.tabuleiro[colunaAnterior][linhaAnterior] = new Refugio();
                }else{
                    this.tabuleiro[colunaAnterior][linhaAnterior] = new Trono();
                }
                this.tabuleiro[coluna][linha] = new Rei();
                if(this.refugio[coluna][linha].getClass() == Refugio.class){ //estava em refugio e vai para refugio
                    this.tabuleiro[coluna][linha].setImagem(new ImageIcon("img/reibrancoregufio.png"));
                    
                }
                
            }else{//nao estava em refugio
                this.tabuleiro[colunaAnterior][linhaAnterior] = new CampoNormal();
                this.tabuleiro[coluna][linha] = new Rei();
                //verificar se vai para refugio
                if(this.refugio[coluna][linha].getClass() == Refugio.class || this.refugio[coluna][linha].getClass() == Trono.class){ //nao estava em refugio e vai para refugio
                    this.tabuleiro[coluna][linha].setImagem(new ImageIcon("img/reibrancoregufio.png"));
                }
            }
        }else{//normal
            this.tabuleiro[coluna][linha] = new Branco();
            this.tabuleiro[colunaAnterior][linhaAnterior] = new CampoNormal();
            
        }
        return this.tabuleiro;
    }
    
    public abstract Campo[][] selecionarPreto(int coluna, int linha);
    
    public abstract Campo[][] deselecionarPreto(int coluna, int linha);
    
    /**
     *  Verifica se possui alguma peça entre tais casas na linha movel -- coluna estatica
     *  i clicado = i selecionar
     * j clicado != j selecionado
     * 
     * @param menor
     * @param maior
     * @param pCima
     * @param colunaClicada
     * @param linhaClicada
     * @param rei
     * @return boolean true: achou uma casa no meio do movimento
     */
    public abstract boolean verificaLinhaMovel(int menor, int maior, boolean pCima, int colunaClicada, int linhaClicada);
    
    /**
    *  Verifica se possui alguma peça entre tais casas na coluna movel -- linha estatica
     *  i clicado != i selecionar
     * j clicado = j selecionado
     * 
     * @param menor
     * @param maior
     * @param pEsquerda
     * @param linhaClicada
     * @param colunaClicada
     * @param rei  
     * @return boolean true: achou uma casa no meio do movimento
     */
     public abstract boolean verificaLColunaMovel(int menor, int maior, boolean pEsquerda, int linhaClicada, int colunaClicada);
     
     public abstract Campo[][] moverPreto(int coluna, int linha, int colunaAnterior, int linhaAnterior);
    
}
