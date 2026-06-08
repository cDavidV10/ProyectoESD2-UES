/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arboles;

import java.util.ArrayList;

/**
 *
 * @author cdavi
 */
public class ArbolBinarioBusqueda<T> extends ArbolBinario {

    public ArbolBinarioBusqueda() {
        super();
    }

    public <T extends Comparable> void insertar(T dato) {
        super.setRaiz(insertar(dato, super.getRaiz()));
    }

    private <T extends Comparable> Nodo insertar(T dato, Nodo raiz) {
        if (raiz == null) {
            raiz = new Nodo(dato);
        }

        if (dato.compareTo(raiz.getDato()) < 0) {
            Nodo izd;
            izd = insertar(dato, raiz.getEnlaceIzquierdo());
            raiz.setEnlaceIzquierdo(izd);
        }

        if (dato.compareTo(raiz.getDato()) > 0) {
            Nodo derecha;
            derecha = insertar(dato, raiz.getEnlaceDerecha());
            raiz.setEnlaceDerecha(derecha);
        }

        return raiz;
    }

    public <T extends Comparable> void eliminar(T dato) {
        super.setRaiz(eliminar(dato, super.getRaiz()));
    }

    private <T extends Comparable> Nodo eliminar(T dato, Nodo raiz) {
        if (raiz == null) {
            System.out.println("El dato no existe");
            return raiz;
        }

        if (dato.compareTo(raiz.getDato()) < 0) {
            Nodo izq;
            izq = eliminar(dato, raiz.getEnlaceIzquierdo());
            raiz.setEnlaceIzquierdo(izq);

        }

        if (dato.compareTo(raiz.getDato()) > 0) {
            Nodo drch;
            drch = eliminar(dato, raiz.getEnlaceDerecha());
            raiz.setEnlaceDerecha(drch);
        }

        if (dato.compareTo(raiz.getDato()) == 0) {
            Nodo quitar;
            quitar = raiz;

            if (quitar.getEnlaceIzquierdo() == null) {
                raiz = quitar.getEnlaceDerecha();
            }

            if (quitar.getEnlaceDerecha() == null) {
                raiz = quitar.getEnlaceIzquierdo();
            } else {
                quitar = aplicarReglaDosHijos(quitar);
            }

            quitar = null;
        }

        return raiz;
    }

    private Nodo aplicarReglaDosHijos(Nodo actual) {
        Nodo aux, ant;
        ant = actual;
        aux = actual.getEnlaceIzquierdo();
        while (aux.getEnlaceDerecha() != null) {
            ant = aux;
            aux = aux.getEnlaceDerecha();
        }

        actual.setDato(aux.getDato());

        if (ant == actual) {
            ant.setEnlaceIzquierdo(aux.getEnlaceIzquierdo());
        } else {
            ant.setEnlaceDerecha(aux.getEnlaceIzquierdo());
        }

        return aux;
    }

    public ArrayList NID() {
        ArrayList a = new ArrayList<>();
        return preOrdenNID(super.getRaiz(), a);
    }

    public ArrayList IND() {
        ArrayList a = new ArrayList<>();
        return inOrdenIND(super.getRaiz(), a);
    }

    public ArrayList IDN() {
        ArrayList a = new ArrayList<>();
        return postOrdenIDN(super.getRaiz(), a);
    }

    public ArrayList hojas() {
        ArrayList a = new ArrayList<>();
        return verHojas(super.getRaiz(), a);
    }

    public ArrayList padre() {
        ArrayList a = new ArrayList<>();
        return padres(super.getRaiz(), a);
    }

}
