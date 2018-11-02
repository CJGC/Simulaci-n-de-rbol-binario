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

    public void rotacion_izq(Nodo nodo, String rotacion_para) {
        Nodo nodo_padre = nodo.getPadre();
        Nodo nodo_hijo_der = nodo.getHijo_der();
        Nodo hijo_izq_de_nodo_derecho = nodo_hijo_der.getHijo_izq();

        /* el nuevo hijo derecho de nodo es el antiguo hijo izquierdo de
        nodo derecho si existe */
        if (hijo_izq_de_nodo_derecho != null) {
            hijo_izq_de_nodo_derecho.setPadre(nodo);
            nodo.setHijo_der(hijo_izq_de_nodo_derecho);
            hijo_izq_de_nodo_derecho.setEstado(" hijo der de "
                    + nodo.getValor());
        } else {
            nodo.setHijo_der(null);
        }

        /* actualizacion de indices de nivel */
        nodo.setNivel_der(nodo_hijo_der.getNivel_izq());
        
        if (rotacion_para.equals("insercion")) {
            nodo_hijo_der.setNivel_izq(nodo.getNivel_izq() + 1);
        }
        else {
            nodo_hijo_der.setNivel_izq(nodo_hijo_der.getNivel_izq() + 1);
        }
        
            /* decirle al nodo quien es su nuevo padre y al nuevo padre decirle 
        quien es su nuevo hijo */
        nodo.setPadre(nodo_hijo_der);
        nodo_hijo_der.setHijo_izq(nodo);
        nodo.setEstado(" hijo izq de " + nodo_hijo_der.getValor());

        /* si el nodo que se roto era raiz significa que no tenia padre por lo
        tanto el nuevo padre es null */
        if (nodo == getRaiz()) {
            nodo_hijo_der.setPadre(null);
            setRaiz(nodo_hijo_der);
            nodo_hijo_der.setEstado(" nodo raiz");
            return;
        }

        /* sino era raiz entonces configure su padre como el antiguo padre de
        nodo y digale a ese nuevo padre sobre su nuevo hijo */
        if (nodo_padre.getHijo_izq() == nodo) {
            nodo_padre.setHijo_izq(nodo_hijo_der);
            nodo_hijo_der.setEstado(" hijo izq de " + nodo_padre.getValor());
        } else {
            nodo_padre.setHijo_der(nodo_hijo_der);
            nodo_hijo_der.setEstado(" hijo der de " + nodo_padre.getValor());
        }

        nodo_hijo_der.setPadre(nodo_padre);
    }

    public void rotacion_der(Nodo nodo, String rotacion_para) {
        Nodo nodo_padre = nodo.getPadre();
        Nodo nodo_hijo_izq = nodo.getHijo_izq();
        Nodo hijo_der_de_nodo_izquierdo = nodo_hijo_izq.getHijo_der();

        /* el nuevo hijo izquierdo de nodo es el antiguo hijo derecho de
        nodo izquierdo si existe */
        if (hijo_der_de_nodo_izquierdo != null) {
            hijo_der_de_nodo_izquierdo.setPadre(nodo);
            nodo.setHijo_izq(hijo_der_de_nodo_izquierdo);
            hijo_der_de_nodo_izquierdo.setEstado(" hijo izq de "
                    + nodo.getValor());
        } else {
            nodo.setHijo_izq(null);
        }

        nodo.setNivel_izq(nodo_hijo_izq.getNivel_der());
        /* actualizacion de indices de nivel */
        if (rotacion_para.equals("insercion")) {
            nodo_hijo_izq.setNivel_der(nodo.getNivel_der() + 1);
        } else {
            nodo_hijo_izq.setNivel_der(nodo_hijo_izq.getNivel_der() + 1);
        }

        /* decirle al nodo quien es su nuevo padre y al nuevo padre decirle 
        quien es su nuevo hijo */
        nodo.setPadre(nodo_hijo_izq);
        nodo_hijo_izq.setHijo_der(nodo);
        nodo.setEstado(" hijo der de " + nodo_hijo_izq.getValor());

        /* si el nodo que se roto era raiz significa que no tenia padre por lo
        tanto el nuevo padre es null */
        if (nodo == getRaiz()) {
            nodo_hijo_izq.setPadre(null);
            setRaiz(nodo_hijo_izq);
            nodo_hijo_izq.setEstado(" nodo raiz");
            return;
        }

        /* sino era raiz entonces configure su padre como el antiguo padre de
        nodo y digale a ese nuevo padre sobre su nuevo hijo */
        if (nodo_padre.getHijo_izq() == nodo) {
            nodo_padre.setHijo_izq(nodo_hijo_izq);
            nodo_hijo_izq.setEstado(" hijo izq de " + nodo_padre.getValor());
        } else {
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
            rotacion_izq(hijo_izq, "insercion");
            rotacion_der(nodo_actual, "insercion");
        } /* rotacion simple a la derecha */ else {
            rotacion_der(nodo_actual, "insercion");
        }
    }

    public void determinar_tipo_de_balance_der(Nodo nodo_actual) {

        Nodo hijo_der = nodo_actual.getHijo_der();
        Integer nivel_izq = hijo_der.getNivel_izq();
        Integer nivel_der = hijo_der.getNivel_der();
        
        /* doble rotacion, primero a la derecha y luego a la izquierda */
        if (nivel_izq > nivel_der) {
            rotacion_der(hijo_der, "insercion");
            rotacion_izq(nodo_actual, "insercion");
        } /* rotacion simple a la izquierda */ else {
            rotacion_izq(nodo_actual, "insercion");
        }
    }

    private int calculo_factor_de_balance_izq(Nodo nodo, Integer nivel) {
        Integer nuevo_nivel = nodo.getNivel_izq() + nivel;
        nodo.setNivel_izq(nuevo_nivel);

        Integer nivel_izq = nuevo_nivel;
        Integer nivel_der = nodo.getNivel_der();

        Integer factor_de_balance = nivel_izq - nivel_der;

        /* si el factor de balance esta entre [-1 a 1] significa que el 
        arbol esta balancado en el nivel actual */
        if (factor_de_balance.equals(0)) {
            return 0;
        } else if (factor_de_balance.equals(-1) || factor_de_balance.equals(1)) {
            return 1;
        }
        return -1;
    }

    private int calculo_factor_de_balance_der(Nodo nodo, Integer nivel) {
        Integer nuevo_nivel = nodo.getNivel_der() + nivel;
        nodo.setNivel_der(nuevo_nivel);

        Integer nivel_der = nuevo_nivel;
        Integer nivel_izq = nodo.getNivel_izq();

        Integer factor_de_balance = nivel_izq - nivel_der;

        /* si el factor de balance esta entre [-1 a 1] significa que el 
        arbol esta balancado en el nivel actual */
        if (factor_de_balance.equals(0)) {
            return 0;
        } else if (factor_de_balance.equals(-1) || factor_de_balance.equals(1)) {
            return 1;
        }

        return -1;
    }

    public int _insertar(Nodo nuevo_nodo, Nodo nodo_actual, Nodo nodo_padre) {
        if (getRaiz() == null) {
            setRaiz(nuevo_nodo);
            nuevo_nodo.setEstado(" nodo raiz");
            return 0;
        }

        if (nodo_actual == null) {
            if (nuevo_nodo.getValor() < nodo_padre.getValor()) {
                nodo_padre.setHijo_izq(nuevo_nodo);
                nuevo_nodo.setEstado(" hijo izq de "
                        + nodo_padre.getValor());
            } else {
                nodo_padre.setHijo_der(nuevo_nodo);
                nuevo_nodo.setEstado(" hijo der de "
                        + nodo_padre.getValor());
            }
            nuevo_nodo.setPadre(nodo_padre);
            return 1;
        }

        if (nuevo_nodo.getValor() < nodo_actual.getValor()) {
            Integer nivel;
            nivel = _insertar(nuevo_nodo, nodo_actual.getHijo_izq(),
                    nodo_actual);

            Integer factor_de_balance;
            factor_de_balance
                    = calculo_factor_de_balance_izq(nodo_actual, nivel);

            /* si el valor retornado por el calculo de la balance es 1 o 0
            significa que el arbol esta balanceado */
            if (!factor_de_balance.equals(1) && !factor_de_balance.equals(0)) {
                determinar_tipo_de_balance_izq(nodo_actual);
                return 0;
            }
            
            /* indicar al nodo padre que aumente o no */
            if (nivel.equals(1) && !factor_de_balance.equals(0)) {
                return 1;
            } else {
                return 0;
            }
        } else {
            Integer nivel;
            nivel = _insertar(nuevo_nodo, nodo_actual.getHijo_der(),
                    nodo_actual);

            Integer factor_de_balance;
            factor_de_balance
                    = calculo_factor_de_balance_der(nodo_actual, nivel);

            /* si el valor retornado por el calculo de la balance es 1 o 0
            significa que el arbol esta balanceado */
            if (!factor_de_balance.equals(1) && !factor_de_balance.equals(0)) {
                determinar_tipo_de_balance_der(nodo_actual);
                return 0;
            }
            
            /* indicar al nodo padre que aumente o no */
            if (nivel.equals(1) && !factor_de_balance.equals(0)) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    @Override
    protected void configurar_nodo(Nodo nodo_fuente, Nodo nodo_objetivo) {

        /* obtenga la informacion de nodo que se eliminara */
        Nodo nodo_padre = nodo_fuente.getPadre();
        Nodo nodo_hijo_izq = nodo_fuente.getHijo_izq();
        Nodo nodo_hijo_der = nodo_fuente.getHijo_der();
        Integer nivel_izq = nodo_fuente.getNivel_izq();
        Integer nivel_der = nodo_fuente.getNivel_der();

        /* configure el papel del nodo sutituto en el arbol */
        nodo_objetivo.setPadre(nodo_padre);
        nodo_objetivo.setHijo_izq(nodo_hijo_izq);
        nodo_objetivo.setHijo_der(nodo_hijo_der);
        nodo_objetivo.setNivel_izq(nivel_izq);
        nodo_objetivo.setNivel_der(nivel_der);

        /* los nuevos hijos del nodo sustituto (si existen) deben saber 
        que el sera su nuevo padre */
        if (nodo_hijo_izq != null) {
            nodo_hijo_izq.setPadre(nodo_objetivo);
            nodo_hijo_izq.setEstado(" hijo izq de " + nodo_objetivo.getValor());
        }
        if (nodo_hijo_der != null) {
            nodo_hijo_der.setPadre(nodo_objetivo);
            nodo_hijo_der.setEstado(" hijo der de " + nodo_objetivo.getValor());
        }

        Nodo nodo_padre_objetivo;
        nodo_padre_objetivo = nodo_padre;

        /* si nodo actual es raiz  entonces nodo sustituto no le reporta a nin
        gun nuevo padre*/
        if (nodo_fuente == getRaiz()) {
            setRaiz(nodo_objetivo);
            nodo_objetivo.setEstado(" nodo raiz");
            return;
        }

        /* sino entonces decirle a mi nuevo padre que yo, soy su nuevo hijo */
        if (nodo_padre_objetivo.getHijo_izq() == nodo_fuente) {
            nodo_padre_objetivo.setHijo_izq(nodo_objetivo);
            nodo_objetivo.setEstado(" hijo izq de "
                    + nodo_padre_objetivo.getValor());
        } else {
            nodo_padre_objetivo.setHijo_der(nodo_objetivo);
            nodo_objetivo.setEstado(" hijo der de "
                    + nodo_padre_objetivo.getValor());
        }
    }

    private Integer derecho_inmediato(Nodo nodo_actual) {
        Nodo nodo_padre = nodo_actual.getPadre();
        Nodo nodo_hijo_der = nodo_actual.getHijo_der();

        /* si soy raiz significa que no tengo padre, por lo tanto solo le
        digo a mi hijo derecho que el sera raiz*/
        if (nodo_actual == getRaiz()) {
            nodo_hijo_der.setPadre(null);
            setRaiz(nodo_hijo_der);
            nodo_hijo_der.setEstado(" nodo raiz");
            return 0;
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
        return -1;
    }
    
    private void determinar_tipo_de_balance_izq_metodo_eli(Nodo nodo_actual) {
        Integer nivel_izq = nodo_actual.getNivel_izq();
        Integer nivel_der = nodo_actual.getNivel_der();
        
        /* rotacion a la derecha */
        if (nivel_der > nivel_izq) {
            /* podria ser necesario la implementacion de un nuevo metodo de 
            rotacion*/
            rotacion_izq(nodo_actual, "eliminacion");
        }
    }
    
    private void determinar_tipo_de_balance_der_metodo_eli(Nodo nodo_actual) {
        Integer nivel_izq = nodo_actual.getNivel_izq();
        Integer nivel_der = nodo_actual.getNivel_der();
        
        /* rotacion a la izquierda */
        if (nivel_izq > nivel_der) {
            rotacion_der(nodo_actual, "eliminacion");
        }
    }
    
    private Integer proceso_de_balance_izq(Nodo nodo_actual, Integer nivel) {
        Integer factor_de_balance = 
                calculo_factor_de_balance_izq(nodo_actual, nivel);

        if (!factor_de_balance.equals(0) && !factor_de_balance.equals(1)) {
            determinar_tipo_de_balance_izq_metodo_eli(nodo_actual);
            return 0;
        }

        if (factor_de_balance.equals(0)) {
            return -1;
        }
        else {
            return 0;
        }
    }
    
    private Integer proceso_de_balance_der(Nodo nodo_actual, Integer nivel) {
        /* seccion de balance */
        Integer factor_de_balance =
            calculo_factor_de_balance_der(nodo_actual, nivel);

        /* si el factor de balance no es 1 o 0 sigifica que el arbol
        esta desbalanceado */
        if(!factor_de_balance.equals(0) && 
                !factor_de_balance.equals(1)) {
            determinar_tipo_de_balance_der_metodo_eli(nodo_actual);
            return 0;
        }

        /* indicar al nodo padre que decremente o no */
        if (factor_de_balance.equals(0)) {
            return -1;
        } else {
            return 0;
        }
    }
    
    private Nodo encontrar_sustituto(Nodo nodo_actual) {
        Nodo nodo_sustituto;
        
        /* si nodo actual no es raiz */
        if (nodo_actual.getPadre() != null) {
            Nodo antiguo_nodo_padre_actual = nodo_actual.getPadre();
            String antiguo_estado_nodo_actual = nodo_actual.getEstado();

            /* nodo que sustituto al nodo actual de ese nivel */
            if (antiguo_estado_nodo_actual.equals(" hijo izq de " 
                    + antiguo_nodo_padre_actual.getValor())) {
                nodo_sustituto = antiguo_nodo_padre_actual.getHijo_izq();
            } else {
                nodo_sustituto = antiguo_nodo_padre_actual.getHijo_der();
            }
        } /* si fue raiz entonces el sustituto es raiz */ else {
            nodo_sustituto = getRaiz();
        }
        
        return nodo_sustituto;
    }
    
    public Object eliminar(Nodo nodo, Nodo nodo_objetivo, Nodo nodo_actual, 
            boolean nodo_encontrado) {
        
        if (getRaiz() == null) {
            System.out.println("El arbol esta vacio.");
            return 0;
        }

        /* seccion de busqueda y acciones de eliminacion simples */
        if (!nodo_encontrado) {
            
            if (nodo_actual == null) {
                System.out.println("Nodo con valor \"" + nodo.getValor() 
                        + "\" no existe.");
                return null;
            }
            
            if (nodo.getValor() < nodo_actual.getValor()) {
                Object nivel;
                nivel = eliminar(nodo, null, nodo_actual.getHijo_izq(),
                        nodo_encontrado);
                
                /* si nivel es null significa que el nodo solicitado no fue 
                encontrado en el arbol */
                if (nivel == null) {
                    return nivel;
                }
                
                /* seccion de balance para el nodo_actual de esa iteracion */
                Integer nuevo_indice_de_nivel =
                    proceso_de_balance_izq(nodo_actual, (Integer) nivel);
                return nuevo_indice_de_nivel;
                
            } else if (nodo.getValor() > nodo_actual.getValor()) {
                Object nivel;
                nivel = eliminar(nodo, null, nodo_actual.getHijo_der(), 
                        nodo_encontrado);
                
                /* si nivel es null significa que el nodo solicitado no fue 
                encontrado en el arbol */
                if (nivel == null) {
                    return nivel;
                }
                
                /* seccion de balance para el nodo_actual de esa iteracion */
                Integer nuevo_indice_de_nivel =
                    proceso_de_balance_der(nodo_actual,(Integer) nivel);
                return nuevo_indice_de_nivel;
            }
            
            /* una vez el nodo haya sido encontrado hacemos lo siguiente */
            System.out.println("Nodo con valor \"" + nodo.getValor() 
                    + "\" fue eliminado correctamente.");

            /* nodo a eliminar es una hoja */
            if (nodo_actual.getHijo_izq() == null &&
                    nodo_actual.getHijo_der() == null) {
                
                /* si es raiz entonces el arbol queda vacio */
                if (nodo_actual == getRaiz()) {
                    setRaiz(null);
                    return null;
                }
                
                /* decirle al padre de nodo actual que ya no es mas su hijo */
                Nodo nodo_padre = nodo_actual.getPadre();
                if (nodo_padre.getHijo_izq() == nodo_actual) {
                    nodo_padre.setHijo_izq(null);
                }
                else {
                    nodo_padre.setHijo_der(null);
                }
                
                return -1;
            }
            
            /* caso especial (hijo derecho sustituto inmediato) */
            if (nodo_actual.getHijo_izq() == null &&
                    nodo_actual.getHijo_der() != null) {
                System.out.println("No tiene hijo izquierdo");
                Object nivel;
                nivel = derecho_inmediato(nodo_actual);
                return nivel;
            }
            
            nodo_encontrado = true;
            Object nivel;
            
            /* eliminacion por la izquierda del nodo encontrado */
            nivel = eliminar(nodo, nodo_actual, nodo_actual.getHijo_izq(), 
                    nodo_encontrado);
            Nodo nodo_sustituto;
            
            /* encontrar al nodo que sustituyo a nodo actual */
            nodo_sustituto = encontrar_sustituto(nodo_actual);
            Integer nuevo_indice_de_nivel =
                proceso_de_balance_izq(nodo_sustituto, (Integer) nivel);
            return nuevo_indice_de_nivel;
        }
        
        /* caso 1 (caso base) nodo hoja */
        if (nodo_actual.getHijo_izq() == null && 
                nodo_actual.getHijo_der() == null) {

            /* decirle al padre de nodo actual que ya no es mas su hijo */
            Nodo nodo_padre_de_actual = nodo_actual.getPadre();
            if (nodo_padre_de_actual.getHijo_izq() == nodo_actual) {
                nodo_padre_de_actual.setHijo_izq(null);
            } else {
                nodo_padre_de_actual.setHijo_der(null);
            }
            
            configurar_nodo(nodo_objetivo, nodo_actual);
            return -1;
        }
        
        /* caso 2 tengo hijo derecho */
        if (nodo_actual.getHijo_der() != null) {
            Object nivel;
            nivel = eliminar(nodo, nodo_objetivo, nodo_actual.getHijo_der(),
                    nodo_encontrado);
            Integer nuevo_indice_de_nivel =
                    proceso_de_balance_der(nodo_actual, (Integer) nivel);
            return nuevo_indice_de_nivel;
        }
        
        /* caso 3 tengo solamente un hijo izq */
        Object nivel;
        nivel = eliminar(nodo, nodo_actual, nodo_actual.getHijo_izq(),
                nodo_encontrado);
        
        Nodo nodo_sustituto;
        nodo_sustituto = encontrar_sustituto(nodo_actual);
        
        configurar_nodo(nodo_objetivo, nodo_actual);
        
        Integer nuevo_indice_de_nivel =
                proceso_de_balance_izq(nodo_sustituto, (Integer) nivel);
        return nuevo_indice_de_nivel;
    }
}