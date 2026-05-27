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
public class ArbolBinarioAVL extends ArbolBinario {

    private int fe;

    public ArbolBinarioAVL() {
        super();
        this.fe = 0;

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

        return balance(dato, raiz);
    }

    private <T extends Comparable> Nodo balance(T dato, Nodo raiz) {
        if (raiz != null) {

            if (dato.compareTo(raiz.getDato()) > 0) {
                balance(dato, raiz.getEnlaceDerecha());
            } else if (dato.compareTo(raiz.getDato()) < 0) {
                balance(dato, raiz.getEnlaceIzquierdo());
            }

            fe = alturaHijo(raiz.getEnlaceDerecha()) - alturaHijo(raiz.getEnlaceIzquierdo());

            switch (fe) {
                case -2:
                    if (alturaHijo(raiz.getEnlaceIzquierdo().getEnlaceIzquierdo()) > alturaHijo(
                            raiz.getEnlaceIzquierdo().getEnlaceDerecha())) {
                        raiz = RII(raiz, raiz.getEnlaceIzquierdo());
                    } else {
                        raiz = RID(raiz, raiz.getEnlaceIzquierdo());
                    }

                    break;
                case 2:
                    if (alturaHijo(raiz.getEnlaceDerecha().getEnlaceDerecha()) > alturaHijo(
                            raiz.getEnlaceDerecha().getEnlaceIzquierdo())) {
                        raiz = RDD(raiz, raiz.getEnlaceDerecha());
                    } else {
                        raiz = RDI(raiz, raiz.getEnlaceDerecha());
                    }
                    break;
                default:
                    raiz = actualizarAlturaHijo(raiz);

            }
        }

        return raiz;

    }

    private int alturaHijo(Nodo rama) {
        if (rama != null) {
            return rama.getAltura();
        }

        return -1;
    }

    private Nodo RII(Nodo raiz, Nodo ramaIzq) {
        raiz.setEnlaceIzquierdo(ramaIzq.getEnlaceDerecha());
        ramaIzq.setEnlaceDerecha(raiz);

        actualizarAlturaHijo(raiz);
        actualizarAlturaHijo(ramaIzq);

        return ramaIzq;
    }

    private Nodo RDD(Nodo raiz, Nodo ramaDrch) {
        raiz.setEnlaceDerecha(ramaDrch.getEnlaceIzquierdo());
        ramaDrch.setEnlaceIzquierdo(raiz);

        actualizarAlturaHijo(raiz);
        actualizarAlturaHijo(ramaDrch);

        return ramaDrch;
    }

    private Nodo RID(Nodo raiz, Nodo ramaIzq) {
        Nodo n2 = ramaIzq.getEnlaceDerecha();
        raiz.setEnlaceIzquierdo(n2.getEnlaceDerecha());
        n2.setEnlaceDerecha(raiz);
        ramaIzq.setEnlaceDerecha(n2.getEnlaceIzquierdo());
        n2.setEnlaceIzquierdo(ramaIzq);

        actualizarAlturaHijo(ramaIzq);
        actualizarAlturaHijo(raiz);
        actualizarAlturaHijo(n2);

        return n2;
    }

    private Nodo RDI(Nodo raiz, Nodo ramaDrch) {
        Nodo n2 = ramaDrch.getEnlaceIzquierdo();
        raiz.setEnlaceDerecha(n2.getEnlaceIzquierdo());
        n2.setEnlaceIzquierdo(raiz);
        ramaDrch.setEnlaceIzquierdo(n2.getEnlaceDerecha());
        n2.setEnlaceDerecha(ramaDrch);

        actualizarAlturaHijo(ramaDrch);
        actualizarAlturaHijo(raiz);
        actualizarAlturaHijo(n2);

        return n2;
    }

    private Nodo actualizarAlturaHijo(Nodo rama) {
        if (rama != null) {
            actualizarAlturaHijo(rama.getEnlaceIzquierdo());
            actualizarAlturaHijo(rama.getEnlaceDerecha());
            rama.setAltura(super.altura(rama) - 1);
        }
        return rama;
    }

   

    public <T extends Comparable> void eliminar(T dato) {
        super.setRaiz(eliminar(dato, super.getRaiz()));
    }

    private <T extends Comparable> Nodo eliminar(T dato, Nodo raiz) {
        if (raiz == null) {
            System.out.println("El dato no existe");
        }

        else if (dato.compareTo(raiz.getDato()) < 0) {
            Nodo izq;
            izq = eliminar(dato, raiz.getEnlaceIzquierdo());
            raiz.setEnlaceIzquierdo(izq);

        }

        else if (dato.compareTo(raiz.getDato()) > 0) {
            Nodo drch;
            drch = eliminar(dato, raiz.getEnlaceDerecha());
            raiz.setEnlaceDerecha(drch);
        }

        else {
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

        return balance(dato, raiz);
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
    
    public ArrayList padreDosHijos() {
        ArrayList a = new ArrayList<>();
        return padreDosHijos(super.getRaiz(), a);
    }
    
    public int peso(){
        int a = 0;
        
        return peso(super.getRaiz(), a);
        
    }
    
    public int mayor(){
        
        return mayor(super.getRaiz());
    }
    
    public int sumatoriaHojas(){
    
        return sumatoriaHojas(super.getRaiz(), 0);
    }
}
