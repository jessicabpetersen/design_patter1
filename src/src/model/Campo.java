/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.swing.Icon;

/**
 *
 * @author Jéssica Petersen
 */
public abstract class Campo {

    private Icon imagem;

    public Campo(Icon imagem) {
        this.imagem = imagem;
    }

    public void setImagem(Icon imagem) {
        this.imagem = imagem;
    }

    public Icon getImagem() {
        return imagem;
    }
    
}
