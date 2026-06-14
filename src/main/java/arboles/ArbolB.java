/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arboles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Estudiante
 */
public class ArbolB<T extends Comparable<T>> {

    private Pagina<T> raiz;
    private int orden;

    public ArbolB(int orden) {
        if (orden < 2) {
            System.out.println("El orden debe ser dos o mayor");
        }
        this.orden = orden;
        raiz = null;
    }

    public T buscar(T clave) {
        return buscarRecursive(raiz, clave);
    }

    private T buscarRecursive(Pagina<T> pagina, T clave) {
        if (pagina == null) {
            return null;
        }
        int i = 0;
        buscarPosicion(pagina, clave);

        if (i < pagina.getClaves().size() && clave.compareTo(pagina.getClaves().get(i)) == 0) {
            return pagina.getClaves().get(i);
        }

        if (pagina.isHoja()) {
            return null;
        }

        return buscarRecursive(pagina.getHijos().get(i), clave);
    }

    public List<T> obtenerDatosArbolB() {
        List<T> lista = new ArrayList<>();
        recorrerInOrder(raiz, lista);
        return lista;
    }

    private void recorrerInOrder(Pagina<T> pagina, List<T> lista) {
        if (pagina == null) {
            return;
        }

        List<T> claves = pagina.getClaves();
        List<Pagina<T>> hijos = pagina.getHijos();

        int i = 0;
        for (i = 0; i < claves.size(); i++) {
            
            if (hijos != null && i < hijos.size()) {
                recorrerInOrder(hijos.get(i), lista);
            }
            lista.add(claves.get(i));
        }

        if (hijos != null && i < hijos.size()) {
            recorrerInOrder(hijos.get(i), lista);
        }
    }

    public void insertar(T clave) {
        if (buscar(clave) != null) {
            System.out.println("La clave yap existe");
            return;
        }

        if (raiz == null) {
            raiz = new Pagina(true);
            raiz.getClaves().add(clave);
            return;
        }
        insertarRecursive(null, raiz, clave, -1);

        if (raiz.getClaves().size() > 2 * orden) {
            Pagina<T> nuevaRaiz = new Pagina(false);
            nuevaRaiz.getHijos().add(raiz);
            dividirHijo(nuevaRaiz, 0);
            raiz = nuevaRaiz;
        }
    }

    public void insertarRecursive(Pagina<T> padre, Pagina<T> pagina, T clave, int indic) {
        if (pagina.isHoja()) {
            pagina.getClaves().add(clave);
            Collections.sort(pagina.getClaves());
            if (pagina.getClaves().size() > 2 * orden && padre != null) {
                dividirHijo(padre, indic);
            }
            return;
        }
        int pos = buscarPosicion(pagina, clave);
        Pagina hijo = pagina.getHijos().get(pos);
        insertarRecursive(pagina, hijo, clave, pos);
        if (pagina.getClaves().size() > 2 * orden && padre != null) {
            dividirHijo(padre, indic);
        }
    }

    private int buscarPosicion(Pagina<T> pagina, T clave) {
        int i = 0;
        while (i < pagina.getClaves().size() && clave.compareTo(pagina.getClaves().get(i)) > 0) {
            i++;
        }
        return i;
    }

    private void dividirHijo(Pagina<T> padre, int indicHijo) {
        Pagina<T> hijo = padre.getHijos().get(indicHijo);
        Pagina<T> izq = new Pagina(hijo.isHoja());
        Pagina<T> derc = new Pagina(hijo.isHoja());
        int indiceCentral = orden;
        T claveCentral = hijo.getClaves().get(indiceCentral);

        for (int i = indiceCentral + 1; i < hijo.getClaves().size(); i++) {
            derc.getClaves().add(hijo.getClaves().get(i));
        }
        if (!hijo.isHoja()) {
            for (int i = 0; i < orden; i++) {
                izq.getHijos().add(hijo.getHijos().get(i));
            }
            for (int i = orden + 1; i < hijo.getHijos().size(); i++) {
                derc.getHijos().add(hijo.getHijos().get(i));
            }
        }
        padre.getHijos().set(indicHijo, izq);
        padre.getHijos().set(indicHijo + 1, derc);
        padre.getClaves().add(indicHijo, claveCentral);
    }

    public void mostrar() {
        mostrarRecursive(raiz);
    }

    private void mostrarRecursive(Pagina<T> pagina) {
        if (pagina == null) {
            return;
        }
        int i;
        for (i = 0; i < pagina.getClaves().size(); i++) {
            if (!pagina.isHoja()) {
                mostrarRecursive(pagina.getHijos().get(i));
            }
            System.out.println(pagina.getClaves().get(i) + " ");
        }
    }

    public void mostrarNiveles() {
        int altura = obtenerAltura(raiz);

        for (int i = 0; i < altura; i++) {
            mostrarNivelesRecursive(raiz, i);
            System.out.println("");
        }

    }

    private void mostrarNivelesRecursive(Pagina<T> pagina, int nivel) {
        if (pagina == null) {
            return;
        }
        if (nivel == 0) {
            System.out.println(pagina.getClaves() + " ");
        } else {
            for (Pagina hijo : pagina.getHijos()) {
                mostrarNivelesRecursive(hijo, nivel - 1);
            }
        }
    }

    private int obtenerAltura(Pagina<T> pagina) {
        if (pagina == null) {
            return 0;
        }

        if (pagina.isHoja()) {
            return 1;
        }

        return 1 + obtenerAltura(pagina.getHijos().get(0));
    }
}
