package binarytree;

import java.util.List;

public class Arbol_binario {

    private Nodo raiz;
    private boolean permitir_insercion;

    Arbol_binario() {
        raiz = null;
        permitir_insercion = true;
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }

    public boolean isPermitir_insercion() {
        return permitir_insercion;
    }

    public void setPermitir_insercion(boolean permitir_insercion) {
        this.permitir_insercion = permitir_insercion;
    }

    public void insertar(Nodo nuevo_nodo, Nodo nodo_actual) {

        if (raiz == null) {
            raiz = nuevo_nodo;
            return;
        }

        /* si nodo actual es null, quiere decir que llegamos a una hoja */
        if (nodo_actual == null) {
            return;
        }

        /* si el valor del nuevo nodo es menor que el valor del nodo actual
        de un nivel, entonces llame recursivamente al hijo izquierdo de ese 
        nodo actual, en caso contrario al derecho */
        if (nuevo_nodo.getValor() < nodo_actual.getValor()) {
            insertar(nuevo_nodo, nodo_actual.getHijo_izq());
        } else {
            insertar(nuevo_nodo, nodo_actual.getHijo_der());
        }

        /* permitir insercion es un flag que solo autoriza una vez la inser
        cion del nuevo nodo, cuando se haya encontrado el nivel en el arbol
        adecuado */
        if (permitir_insercion) {
            if (nuevo_nodo.getValor() < nodo_actual.getValor()) {
                nodo_actual.setHijo_izq(nuevo_nodo);
            } else {
                nodo_actual.setHijo_der(nuevo_nodo);
            }
            nuevo_nodo.setPadre(nodo_actual);
            permitir_insercion = false;
        }
    }

    private void eliminar_nodo(Nodo nodo_actual, Nodo nodo_sustituto) {
        Nodo nodo_padre_sustituto;

        /* configure el papel del nodo sustituto en el arbol */
        Nodo nodo_hijo_izq = nodo_actual.getHijo_izq();
        Nodo nodo_hijo_der = nodo_actual.getHijo_der();
        Nodo nodo_padre = nodo_actual.getPadre();

        nodo_sustituto.setPadre(nodo_padre);
        nodo_sustituto.setHijo_izq(nodo_hijo_izq);
        nodo_sustituto.setHijo_der(nodo_hijo_der);

        /* los nuevos hijos del nodo sustituto (si existen) deben saber 
        que el sera su nuevo padre */
        if (nodo_hijo_izq != null) {
            nodo_hijo_izq.setPadre(nodo_sustituto);
        }
        if (nodo_hijo_der != null) {
            nodo_hijo_der.setPadre(nodo_sustituto);
        }

        nodo_padre_sustituto = nodo_padre;

        /* si nodo actual es raiz  entonces nodo sustituto no le reporta a nin
        gun nuevo padre*/
        if (nodo_actual == raiz) {
            raiz = nodo_sustituto;
            return;
        }

        /* sino entonces decirle a mi nuevo padre que yo, soy su nuevo hijo */
        if (nodo_padre_sustituto.getHijo_izq() == nodo_actual) {
            nodo_padre_sustituto.setHijo_izq(nodo_sustituto);
        } else {
            nodo_padre_sustituto.setHijo_der(nodo_sustituto);
        }
    }

    public Nodo eliminar(Nodo nodo, Nodo nodo_actual, Nodo nodo_sustituto) {

        if (raiz == null) {
            System.out.println("El arbol esta vacio.");
            return null;
        }

        /* busqueda para saber si el nodo solicitado existe, si existe entonces
           nodo actual apuntara a ese nodo */
        if (nodo_actual == null) {
            nodo_actual = buscar(nodo, raiz);
            if (nodo_actual == null) {
                return null;
            }

            /* la inicializacion de nodo_sustituto varia segun el caso, por 
            ejemplo, al no tener un hijo izquierdo el nodo que se piensa 
            eliminar significa una de dos situaciones, el nodo solo tiene hijo 
            derecho o es un nodo hoja.
            si tiene hijo derecho, ese hijo es sustituto inmediato */
            nodo_sustituto = nodo_actual;
            if (nodo_actual.getHijo_izq() != null) {
                nodo_sustituto = nodo_actual.getHijo_izq();
            }
        }

        /* caso 1 (caso base), el nodo actual solo tiene un hijo derecho
           entonces ese hijo derecho es sustituto inmediato */
        if (nodo_actual.getHijo_izq() == null
                && nodo_actual.getHijo_der() != null) {
            /* este caso simula que el hijo derecho sepa que su padre de
            jara de existir y que el sera el heredero */
            Nodo nodo_padre = nodo_actual.getPadre();
            Nodo nodo_hijo_der = nodo_actual.getHijo_der();

            /* si soy raiz no le digo a mi padre que voy a morir */
            if (nodo_actual == raiz) {
                nodo_hijo_der.setPadre(null);
                raiz = nodo_hijo_der;
                return nodo_actual;
            }

            /* sino soy raiz debo notificarle a mi padre que voy a morir y quien
            es su nuevo hijo */
            if (nodo_padre.getHijo_izq() == nodo_actual) {
                nodo_padre.setHijo_izq(nodo_hijo_der);
            } else {
                nodo_padre.setHijo_der(nodo_hijo_der);
            }

            /* y a mi hijo le digo quien es su nuevo padre */
            nodo_hijo_der.setPadre(nodo_padre);
            return nodo_actual;
        }

        /* caso 2 (caso base) el sustituto es una hoja */
        if (nodo_sustituto.getHijo_izq() == null
                && nodo_sustituto.getHijo_der() == null) {
            /* si el nodo actual es raiz y no tiene hijos configurelo a null*/
            if (nodo_actual == raiz && nodo_actual == nodo_sustituto) {
                raiz = null;
                return nodo_actual;
            }

            /* sino decirle a mi padre viejo que ya no soy su hijo */
            Nodo nodo_padre_sustituto = nodo_sustituto.getPadre();
            if (nodo_sustituto == nodo_padre_sustituto.getHijo_izq()) {
                nodo_padre_sustituto.setHijo_izq(null);
            } else {
                nodo_padre_sustituto.setHijo_der(null);
            }

            /* si mi hijo sustituto soy yo mismo, pues entonces simplemente 
               muero */
            if (nodo_actual == nodo_sustituto) {
                return nodo_actual;
            }

            /* sino quiere decir que debo configurar a mi nodo sustituto antes
            de morir */
            eliminar_nodo(nodo_actual, nodo_sustituto);
            return nodo_actual;
        }

        /* caso 3 el nodo sustituto tiene un hijo derecho */
        if (nodo_sustituto.getHijo_der() != null) {
            return eliminar(nodo, nodo_actual, nodo_sustituto.getHijo_der());
        }

        /* caso 4 el nodo sustituto tiene un hijo izquierdo */
        eliminar(nodo, nodo_sustituto, nodo_sustituto.getHijo_izq());
        eliminar_nodo(nodo_actual, nodo_sustituto);
        return nodo_actual;
    }

    public Nodo buscar(Nodo nodo, Nodo nodo_actual) {

        if (raiz == null) {
            System.out.println("El arbol esta vacio");
            return null;
        }

        if (nodo_actual == null) {
            System.out.println("Nodo con valor \"" + nodo.getValor()
                    + "\" no fue encontrado.");
            return null;
        }

        if (nodo_actual.getValor().equals(nodo.getValor())) {
            System.out.println("Nodo con valor \"" + nodo.getValor()
                    + "\" fue encontrado.");
            return nodo_actual;
        }

        if (nodo.getValor() < nodo_actual.getValor()) {
            return buscar(nodo, nodo_actual.getHijo_izq());
        } else {
            return buscar(nodo, nodo_actual.getHijo_der());
        }

    }

    public void listar(Nodo nodo, List<Nodo> lista_nodos) {
        if (raiz == null) {
            System.out.println("El arbol esta vacio");
            return;
        }

        if (nodo == null) {
            return;
        }

        if (lista_nodos != null) {
            lista_nodos.add(nodo);
        }

        System.out.println(nodo.getValor());
        listar(nodo.getHijo_izq(), lista_nodos);
        listar(nodo.getHijo_der(), lista_nodos);
    }

}
