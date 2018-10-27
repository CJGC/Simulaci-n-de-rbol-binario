package AVL;
import binarytree.*;

/**
 *
 * @author cj
 */
public class AVL extends Arbol_binario {
    
    public AVL() {
        super();
    }
    
    public void rotacion_izq(Nodo nodo) {
        Nodo nodo_padre = nodo.getPadre();
        Nodo nodo_hijo_der = nodo.getHijo_der();
        Nodo hijo_izq_de_nodo_derecho = nodo_hijo_der.getHijo_izq();
        
        /* el nuevo hijo derecho de nodo es el antiguo hijo izquierdo de
        nodo derecho si existe */
        if(hijo_izq_de_nodo_derecho != null) {
            hijo_izq_de_nodo_derecho.setPadre(nodo);
            nodo.setHijo_der(hijo_izq_de_nodo_derecho);
            hijo_izq_de_nodo_derecho.setEstado(" hijo der de " 
                    + nodo.getValor());
        }
        else {
            nodo.setHijo_der(null);
        }
        
        /* actualizacion de indices de nivel */
        nodo.setNivel_der(nodo_hijo_der.getNivel_izq());
        nodo_hijo_der.setNivel_izq(nodo.getNivel_izq()+1);
        
        /* decirle al nodo quien es su nuevo padre y al nuevo padre decirle 
        quien es su nuevo hijo */
        nodo.setPadre(nodo_hijo_der);
        nodo_hijo_der.setHijo_izq(nodo);
        nodo.setEstado(" hijo izq de " + nodo_hijo_der.getValor());
        
        /* si el nodo que se roto era raiz significa que no tenia padre por lo
        tanto el nuevo padre es null */
        if(nodo == getRaiz()) {
            nodo_hijo_der.setPadre(null);
            setRaiz(nodo_hijo_der);
            nodo_hijo_der.setEstado(" nodo raiz");
            return;
        }
        
        /* sino era raiz entonces configure su padre como el antiguo padre de
        nodo y digale a ese nuevo padre sobre su nuevo hijo */
        if(nodo_padre.getHijo_izq() == nodo) {
            nodo_padre.setHijo_izq(nodo_hijo_der);
            nodo_hijo_der.setEstado(" hijo izq de " + nodo_padre.getValor());
        }
        else {
            nodo_padre.setHijo_der(nodo_hijo_der);
            nodo_hijo_der.setEstado(" hijo der de " + nodo_padre.getValor());
        }
        
        nodo_hijo_der.setPadre(nodo_padre);
    }

    public void rotacion_der(Nodo nodo) {
        Nodo nodo_padre = nodo.getPadre();
        Nodo nodo_hijo_izq = nodo.getHijo_izq();
        Nodo hijo_der_de_nodo_izquierdo = nodo_hijo_izq.getHijo_der();

        /* el nuevo hijo izquierdo de nodo es el antiguo hijo derecho de
        nodo izquierdo si existe */
        if(hijo_der_de_nodo_izquierdo != null) {
            hijo_der_de_nodo_izquierdo.setPadre(nodo);
            nodo.setHijo_izq(hijo_der_de_nodo_izquierdo);
            hijo_der_de_nodo_izquierdo.setEstado(" hijo izq de " 
                    + nodo.getValor());
        }
        else {
            nodo.setHijo_izq(null);
        }
        
        /* actualizacion de indices de nivel */
        nodo.setNivel_izq(nodo_hijo_izq.getNivel_der());
        nodo_hijo_izq.setNivel_der(nodo.getNivel_der()+1);
        
        /* decirle al nodo quien es su nuevo padre y al nuevo padre decirle 
        quien es su nuevo hijo */
        nodo.setPadre(nodo_hijo_izq);
        nodo_hijo_izq.setHijo_der(nodo);
        nodo.setEstado(" hijo der de " + nodo_hijo_izq.getValor());
        
        /* si el nodo que se roto era raiz significa que no tenia padre por lo
        tanto el nuevo padre es null */
        if(nodo == getRaiz()) {
            nodo_hijo_izq.setPadre(null);
            setRaiz(nodo_hijo_izq);
            nodo_hijo_izq.setEstado(" nodo raiz");
            return;
        }
        
        /* sino era raiz entonces configure su padre como el antiguo padre de
        nodo y digale a ese nuevo padre sobre su nuevo hijo */
        if(nodo_padre.getHijo_izq() == nodo) {
            nodo_padre.setHijo_izq(nodo_hijo_izq);
            nodo_hijo_izq.setEstado(" hijo izq de " + nodo_padre.getValor());
        }
        else{
            nodo_padre.setHijo_der(nodo_hijo_izq);
            nodo_hijo_izq.setEstado(" hijo der de " + nodo_padre.getValor());
        }
        nodo_hijo_izq.setPadre(nodo_padre);
    }
    
    public void determinar_tipo_de_balance_izq(Nodo nodo_actual) {
        
        Nodo hijo_izq = nodo_actual.getHijo_izq();
        Integer nivel_izq = hijo_izq.getNivel_izq();
        Integer nivel_der = hijo_izq.getNivel_der();
        
        /* doble rotacion, primero a la izquierda y luego a la derecha */
        if (nivel_der > nivel_izq) {
            rotacion_izq(nodo_actual.getHijo_izq());
            rotacion_der(nodo_actual);
        }
        /* rotacion simple a la derecha */
        else {
            rotacion_der(nodo_actual);
        }
    }
    
    public void determinar_tipo_de_balance_der(Nodo nodo_actual) {
        
        Nodo hijo_der = nodo_actual.getHijo_der();
        Integer nivel_izq = hijo_der.getNivel_izq();
        Integer nivel_der = hijo_der.getNivel_der();
        
        /* doble rotacion, primero a la derecha y luego a la izquierda */
        if(nivel_izq > nivel_der) {
            rotacion_der(nodo_actual.getHijo_der());
            rotacion_izq(nodo_actual);
        }
        /* rotacion simple a la izquierda */
        else {
            rotacion_izq(nodo_actual);
        }
    }
    
    private int calculo_de_indice_de_balance_izq(Nodo nodo, Integer nivel) {
        Integer nuevo_nivel = nodo.getNivel_izq() + nivel;
        nodo.setNivel_izq(nuevo_nivel);

        Integer nivel_izq = nuevo_nivel;
        Integer nivel_der = nodo.getNivel_der();

        Integer factor_de_balance = nivel_izq - nivel_der;

        /* si el factor de balance esta entre [-1 a 1] significa que el 
        arbol esta balancado en el nivel actual */
        if(factor_de_balance.equals(0)) {
            return 0;
        }
        else if(factor_de_balance.equals(-1) || factor_de_balance.equals(1))
        {
            return 1;
        }
        return -1;
    }
    
    private int calculo_de_indice_de_balance_der(Nodo nodo, Integer nivel) {
        Integer nuevo_nivel = nodo.getNivel_der() + nivel;
        nodo.setNivel_der(nuevo_nivel);

        Integer nivel_der = nuevo_nivel;
        Integer nivel_izq = nodo.getNivel_izq();

        Integer factor_de_balance = nivel_izq - nivel_der;

        /* si el factor de balance esta entre [-1 a 1] significa que el 
        arbol esta balancado en el nivel actual */
        if(factor_de_balance.equals(0)) {
            return 0;
        }
        else if(factor_de_balance.equals(-1) || factor_de_balance.equals(1))
        {
            return 1;
        }

        return -1;
    }
    
    public int _insertar(Nodo nuevo_nodo,Nodo nodo_actual,Nodo nodo_padre) {
        if(getRaiz() == null) {
            setRaiz(nuevo_nodo);
            nuevo_nodo.setEstado(" nodo raiz");
            return 0;
        }
        
        if(nodo_actual == null) {
            if(nuevo_nodo.getValor() < nodo_padre.getValor()) {
                nodo_padre.setHijo_izq(nuevo_nodo);
                nuevo_nodo.setEstado(" hijo izq de "
                        + nodo_padre.getValor());
            }
            else {
                nodo_padre.setHijo_der(nuevo_nodo);
                nuevo_nodo.setEstado(" hijo der de " 
                        + nodo_padre.getValor());
            }
            nuevo_nodo.setPadre(nodo_padre);
            return 1;
        }
        
        if(nuevo_nodo.getValor() < nodo_actual.getValor()) {
            Integer nivel;
            nivel = _insertar(nuevo_nodo,nodo_actual.getHijo_izq(),nodo_actual);
            
            Integer factor_de_balance;
            factor_de_balance = 
                    calculo_de_indice_de_balance_izq(nodo_actual,nivel);
            
            if(factor_de_balance.equals(1) || factor_de_balance.equals(0)) {
                return factor_de_balance;
            }
            determinar_tipo_de_balance_izq(nodo_actual);
        }
        else {
            Integer nivel;
            nivel = _insertar(nuevo_nodo,nodo_actual.getHijo_der(),nodo_actual);
            
            Integer factor_de_balance;
            factor_de_balance = 
                    calculo_de_indice_de_balance_der(nodo_actual,nivel);
            
            if(factor_de_balance.equals(1) || factor_de_balance.equals(0)) {
                return factor_de_balance;
            }
            determinar_tipo_de_balance_der(nodo_actual);
        }
        return 0;
    }
    
    @Override
    public Nodo eliminar(Nodo nodo, Nodo nodo_actual, Nodo nodo_sustituto) {

        if (getRaiz() == null) {
            System.out.println("El arbol esta vacio.");
            return null;
        }

        /* busqueda para saber si el nodo solicitado existe, si existe entonces
           nodo actual apuntara a ese nodo */
        if (nodo_actual == null) {
            nodo_actual = buscar(nodo, getRaiz());
            if (nodo_actual == null) {
                return null;
            }
            /* la inicializacion de nodo_sustituto varia segun el caso, por 
            ejemplo, al no tener un hijo izquierdo el nodo que se piensa 
            eliminar significa una de dos situaciones:
            Primero: el nodo solo tiene hijo derecho o
            Segundo: el nodo es una hoja. */
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

            /* si soy raiz significa que no tengo padre, por lo tanto solo le
            digo a mi hijo derecho que el sera raiz*/
            if (nodo_actual == getRaiz()) {
                nodo_hijo_der.setPadre(null);
                setRaiz(nodo_hijo_der);
                nodo_hijo_der.setEstado(" nodo raiz");
                return nodo_actual;
            }

            /* sino soy raiz le notifico a mi padre que voy a morir y quien
            es su nuevo hijo */
            if (nodo_padre.getHijo_izq() == nodo_actual) {
                nodo_padre.setHijo_izq(nodo_hijo_der);
                nodo_hijo_der.setEstado(" hijo izq de " + nodo_padre.getValor());
            } else {
                nodo_padre.setHijo_der(nodo_hijo_der);
                nodo_hijo_der.setEstado(" hijo der de " + nodo_padre.getValor());
            }

            /* y a mi hijo le digo quien es su nuevo padre */
            nodo_hijo_der.setPadre(nodo_padre);
            return nodo_actual;
        }

        /* caso 2 (caso base) el sustituto es una hoja */
        if (nodo_sustituto.getHijo_izq() == null
                && nodo_sustituto.getHijo_der() == null) {
            
            /* si el nodo actual es raiz y no tiene hijos configurelo a null*/
            if (nodo_actual == getRaiz() && nodo_actual == nodo_sustituto) {
                setRaiz(null);
                return nodo_actual;
            }

            /* sino decirle al padre del sustituto que ya no es mas su hijo */
            Nodo nodo_padre_sustituto = nodo_sustituto.getPadre();
            if (nodo_padre_sustituto.getHijo_izq() == nodo_sustituto) {
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
}