/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arboles;

/**
 *
 * @author cdavi
 */
public class Nodo <T> {
    private T dato;
    private Nodo enlaceIzquierdo;
    private Nodo enlaceDerecha;
    private int altura;

    public Nodo(T dato) {
        this.dato = dato;
        this.enlaceIzquierdo = null;
        this.enlaceDerecha = null;
        this.altura = 0;
    }

    public Nodo(T dato, Nodo enlaceIzquierdo, Nodo enlaceDerecha) {
        this.dato = dato;
        this.enlaceIzquierdo = enlaceIzquierdo;
        this.enlaceDerecha = enlaceDerecha;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public Nodo getEnlaceIzquierdo() {
        return enlaceIzquierdo;
    }

    public void setEnlaceIzquierdo(Nodo enlaceIzquierdo) {
        this.enlaceIzquierdo = enlaceIzquierdo;
    }

    public Nodo getEnlaceDerecha() {
        return enlaceDerecha;
    }

    public void setEnlaceDerecha(Nodo enlaceDerecha) {
        this.enlaceDerecha = enlaceDerecha;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    @Override
    public String toString() {
        return "Nodo{" + "dato = " + dato + '}';
    }
    
    

    
    
    
}
