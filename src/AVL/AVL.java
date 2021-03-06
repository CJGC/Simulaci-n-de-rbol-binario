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

    private Nodo copiar_nodo(Nodo nodo) {
        Nodo copia_nodo = new Nodo();
        copia_nodo.setEstado(nodo.getEstado());
        copia_nodo.setHijo_izq(nodo.getHijo_izq());
        copia_nodo.setHijo_der(nodo.getHijo_der());
        copia_nodo.setPadre(nodo.getPadre());
        copia_nodo.setEstado(nodo.getEstado());
        copia_nodo.setNivel_izq(nodo.getNivel_izq());
        copia_nodo.setNivel_der(nodo.getNivel_der());
        copia_nodo.setProfundidad(nodo.getProfundidad());
        return copia_nodo;
    }

    private int calculo_factor_balance_izq(Nodo nodo, Integer nivel) {
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

    private int calculo_factor_balance_der(Nodo nodo, Integer nivel) {
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
    
    public void rotacion_izq(Nodo nodo, String modo) {
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

        if (modo.equals("modo 1")) {
            nodo_hijo_der.setNivel_izq(nodo.getNivel_izq() + 1);
        } else {
            nodo_hijo_der.setNivel_izq(nodo_hijo_der.getNivel_izq() + 1);
        }

        /* actualizacion de parametro de profundidad */
        configurar_profundidad(nodo);
        configurar_profundidad(nodo_hijo_der);

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

    public void rotacion_der(Nodo nodo, String modo) {
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

        /* actualizacion de indices de nivel */
        nodo.setNivel_izq(nodo_hijo_izq.getNivel_der());
        if (modo.equals("modo 1")) {
            nodo_hijo_izq.setNivel_der(nodo.getNivel_der() + 1);
        } else {
            nodo_hijo_izq.setNivel_der(nodo_hijo_izq.getNivel_der() + 1);
        }

        /* actualizacion de parametro de profundidad */
        configurar_profundidad(nodo);
        configurar_profundidad(nodo_hijo_izq);

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

    private void configurar_profundidad(Nodo nodo_actual) {
        if (nodo_actual.getNivel_izq() > nodo_actual.getNivel_der()) {
            nodo_actual.setProfundidad(nodo_actual.getNivel_izq());
        } else {
            nodo_actual.setProfundidad(nodo_actual.getNivel_der());
        }
    }

    public void determinar_tipo_de_balance_izq_insert(Nodo nodo_actual) {

        Nodo hijo_izq = nodo_actual.getHijo_izq();
        Integer nivel_izq = hijo_izq.getNivel_izq();
        Integer nivel_der = hijo_izq.getNivel_der();

        /* doble rotacion, primero a la izquierda y luego a la derecha */
        if (nivel_der > nivel_izq) {
            rotacion_izq(hijo_izq, "modo 1");
            rotacion_der(nodo_actual, "modo 1");
        } /* rotacion simple a la derecha */ else {
            rotacion_der(nodo_actual, "modo 1");
        }
    }

    public void determinar_tipo_de_balance_der_insert(Nodo nodo_actual) {

        Nodo hijo_der = nodo_actual.getHijo_der();
        Integer nivel_izq = hijo_der.getNivel_izq();
        Integer nivel_der = hijo_der.getNivel_der();

        /* doble rotacion, primero a la derecha y luego a la izquierda */
        if (nivel_izq > nivel_der) {
            rotacion_der(hijo_der, "modo 1");
            rotacion_izq(nodo_actual, "modo 1");
        } /* rotacion simple a la izquierda */ else {
            rotacion_izq(nodo_actual, "modo 1");
        }
    }

    public int _insertar(Nodo nuevo_nodo, Nodo nodo_actual, Nodo nodo_padre) {
        if (getRaiz() == null) {
            setRaiz(nuevo_nodo);
            nuevo_nodo.setEstado(" nodo raiz");
            return 0;
        }

        /* llegamos a una hoja y ahi es donde se inserta el nuevo nodo */
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

        /* si no es hoja entonces compare su valor y dirijase por alguna rama */
        if (nuevo_nodo.getValor() < nodo_actual.getValor()) {
            Integer nivel;
            nivel = _insertar(nuevo_nodo, nodo_actual.getHijo_izq(),
                    nodo_actual);

            Integer factor_de_balance;
            factor_de_balance
                    = calculo_factor_balance_izq(nodo_actual, nivel);

            /* configure la profundidad */
            configurar_profundidad(nodo_actual);

            /* si el valor retornado por el calculo de balance no es 1 o 0
            significa que el arbol esta desbalanceado */
            if (!factor_de_balance.equals(1) && !factor_de_balance.equals(0)) {
                determinar_tipo_de_balance_izq_insert(nodo_actual);
                return 0;
            }

            /* si el nivel es 1 y el factor de balance no es cero 
            significa que el padre debe amentar su indice de nivel */
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
                    = calculo_factor_balance_der(nodo_actual, nivel);

            /* configure la profundidad */
            configurar_profundidad(nodo_actual);

            /* si el valor retornado por el calculo de balance no es 1 o 0
            significa que el arbol esta desbalanceado */
            if (!factor_de_balance.equals(1) && !factor_de_balance.equals(0)) {
                determinar_tipo_de_balance_der_insert(nodo_actual);
                return 0;
            }

            /* si el nivel es 1 y el factor de balance no es cero 
            significa que el padre debe amentar su indice de nivel */
            if (nivel.equals(1) && !factor_de_balance.equals(0)) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    @Override
    protected void configurar_nodo(Nodo nodo_fuente, Nodo nodo_objetivo) {

        /* obtenga la informacion del nodo que se eliminara */
        Nodo nodo_padre = nodo_fuente.getPadre();
        Nodo nodo_hijo_izq = nodo_fuente.getHijo_izq();
        Nodo nodo_hijo_der = nodo_fuente.getHijo_der();
        Integer nivel_izq = nodo_fuente.getNivel_izq();
        Integer nivel_der = nodo_fuente.getNivel_der();
        Integer profundidad = nodo_fuente.getProfundidad();

        /* configure el papel del nodo sutituto en el arbol */
        nodo_objetivo.setPadre(nodo_padre);
        nodo_objetivo.setHijo_izq(nodo_hijo_izq);
        nodo_objetivo.setHijo_der(nodo_hijo_der);
        nodo_objetivo.setNivel_izq(nivel_izq);
        nodo_objetivo.setNivel_der(nivel_der);
        nodo_objetivo.setProfundidad(profundidad);

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

    private void determinar_tipo_de_balance_izq_elim(Nodo nodo_actual) {
        Nodo hijo_der = nodo_actual.getHijo_der();

        /* rotacion simple a la izq si el indice del hijo der es mayor */
        if (hijo_der.getNivel_der() >= hijo_der.getNivel_izq()) {
            rotacion_izq(nodo_actual, "modo 2");
        } /* doble rotacion der - izq */ else {
            rotacion_der(nodo_actual.getHijo_der(), "modo 1");
            rotacion_izq(nodo_actual, "modo 1");
        }
    }

    private void determinar_tipo_de_balance_der_elim(Nodo nodo_actual) {
        Nodo hijo_izq = nodo_actual.getHijo_izq();

        /* rotacion simple a la der si el indice de hijo izq es mayor */
        if (hijo_izq.getNivel_izq() >= hijo_izq.getNivel_der()) {
            rotacion_der(nodo_actual, "modo 2");
        } /* doble rotacion izq - der */ else {
            rotacion_izq(nodo_actual.getHijo_izq(), "modo 1");
            rotacion_der(nodo_actual, "modo 1");
        }
    }

    private Integer proceso_de_balance_izq(Nodo nodo_actual, Integer nivel) {
        Integer factor_de_balance
                = calculo_factor_balance_izq(nodo_actual, nivel);

        if (!factor_de_balance.equals(0) && !factor_de_balance.equals(1)) {
            /* obtener la antigua profundidad de nodo actual */
            Integer antigua_profundidad = nodo_actual.getProfundidad();
            Nodo copia_nodo_actual = copiar_nodo(nodo_actual);
            determinar_tipo_de_balance_izq_elim(nodo_actual);

            /* encontrar al nodo sustituto de ese nivel */
            nodo_actual = encontrar_sustituto(copia_nodo_actual);

            /* si la nueva profundidad no es igual a la antigua, significa que 
            se elimino un nivel y debe ser reportado al padre */
            if (!nodo_actual.getProfundidad().equals(antigua_profundidad)) {
                return -1;
            }
            return 0;
        }

        /* Si la profundidad no coincide significa que se elimino un nivel y 
        debe ser reportado al padre */
        if (!nodo_actual.getProfundidad().equals(nodo_actual.getNivel_izq())
                && !nodo_actual.getProfundidad().
                        equals(nodo_actual.getNivel_der())) {
            configurar_profundidad(nodo_actual);
            return -1;
        } else {
            return 0;
        }
    }

    private Integer proceso_de_balance_der(Nodo nodo_actual, Integer nivel) {
        /* seccion de balance */
        Integer factor_de_balance
                = calculo_factor_balance_der(nodo_actual, nivel);

        /* si el factor de balance no es 1 o 0 sigifica que el arbol
        esta desbalanceado */
        if (!factor_de_balance.equals(0) && !factor_de_balance.equals(1)) {
            /* obtener la antigua profundidad de nodo actual */
            Integer antigua_profundidad = nodo_actual.getProfundidad();
            Nodo copia_nodo_actual = copiar_nodo(nodo_actual);
            determinar_tipo_de_balance_der_elim(nodo_actual);

            /* encontrar al nodo sustituto de ese nivel */
            nodo_actual = encontrar_sustituto(copia_nodo_actual);

            /* si la nueva profundidad no es igual a la antigua, significa que 
            se elimino un nivel y debe ser reportado al padre */
            if (!nodo_actual.getProfundidad().equals(antigua_profundidad)) {
                return -1;
            }
            return 0;
        }

        /* Si la profundidad no coincide significa que se elimino un nivel y 
        debe ser reportado al padre */
        if (!nodo_actual.getProfundidad().equals(nodo_actual.getNivel_izq())
                && !nodo_actual.getProfundidad().
                        equals(nodo_actual.getNivel_der())) {
            configurar_profundidad(nodo_actual);
            return -1;
        } else {
            return 0;
        }
    }

    private Nodo encontrar_sustituto(Nodo nodo_actual) {
        Nodo nodo_sustituto;

        /* si nodo actual no es raiz entonces */
        if (nodo_actual.getPadre() != null) {
            Nodo antiguo_nodo_padre_actual = nodo_actual.getPadre();
            String antiguo_estado_nodo_actual = nodo_actual.getEstado();

            /* obtenga al nodo que sustituyo al nodo actual de ese nivel */
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

        /* seccion de busqueda y de acciones de eliminacion especiales */
        if (!nodo_encontrado) {

            /* si nodo_actual llega a ser null significa que el nodo no 
            existe */
            if (nodo_actual == null) {
                System.out.println("Nodo con valor \"" + nodo.getValor()
                        + "\" no existe.");
                return null;
            }

            /* navegue recursivamente hasta que encuentre el valor (izq) */
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
                Integer nuevo_indice_de_nivel
                        = proceso_de_balance_izq(nodo_actual, (Integer) nivel);
                return nuevo_indice_de_nivel;

            } /* (der) */else if (nodo.getValor() > nodo_actual.getValor()) {
                Object nivel;
                nivel = eliminar(nodo, null, nodo_actual.getHijo_der(),
                        nodo_encontrado);

                /* si nivel es null significa que el nodo solicitado no fue 
                encontrado en el arbol */
                if (nivel == null) {
                    return nivel;
                }

                /* seccion de balance para el nodo_actual de esa iteracion */
                Integer nuevo_indice_de_nivel
                        = proceso_de_balance_der(nodo_actual, (Integer) nivel);
                return nuevo_indice_de_nivel;
            }

            /* si el nodo fue encontrado hacemos lo siguiente */
            System.out.println("Nodo con valor \"" + nodo.getValor()
                    + "\" fue eliminado correctamente.");

            /* compruebe si nodo a eliminar es una hoja */
            if (nodo_actual.getHijo_izq() == null
                    && nodo_actual.getHijo_der() == null) {

                /* si es raiz entonces el arbol queda vacio */
                if (nodo_actual == getRaiz()) {
                    setRaiz(null);
                    return null;
                }

                /* decirle al padre de nodo actual que ya no es mas su hijo */
                Nodo nodo_padre = nodo_actual.getPadre();
                if (nodo_padre.getHijo_izq() == nodo_actual) {
                    nodo_padre.setHijo_izq(null);
                } else {
                    nodo_padre.setHijo_der(null);
                }

                return -1;
            }

            /* caso especial (hijo derecho sustituto inmediato) */
            if (nodo_actual.getHijo_izq() == null
                    && nodo_actual.getHijo_der() != null) {
                Object nivel;
                nivel = derecho_inmediato(nodo_actual);
                return nivel;
            }

            /* sino cumple ninguno de los casos anteriores, debe realizarse una
            busqueda de algun nodo por la izquierda que lo reemplace */
            nodo_encontrado = true;
            Object nivel;
            nivel = eliminar(nodo, nodo_actual, nodo_actual.getHijo_izq(),
                    nodo_encontrado);
            Nodo nodo_sustituto;

            /* encontrar al nodo que sustituyo a nodo actual */
            nodo_sustituto = encontrar_sustituto(nodo_actual);
            Integer nuevo_indice_de_nivel
                    = proceso_de_balance_izq(nodo_sustituto, (Integer) nivel);
            return nuevo_indice_de_nivel;
        }

        /* caso 1 (caso base) nodo hoja */
        if (nodo_actual.getHijo_izq() == null
                && nodo_actual.getHijo_der() == null) {

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
            Integer nuevo_indice_de_nivel
                    = proceso_de_balance_der(nodo_actual, (Integer) nivel);
            return nuevo_indice_de_nivel;
        }

        /* caso 3 tengo solamente un hijo izq */
        Object nivel;
        nivel = eliminar(nodo, nodo_actual, nodo_actual.getHijo_izq(),
                nodo_encontrado);

        Nodo nodo_sustituto;
        nodo_sustituto = encontrar_sustituto(nodo_actual);

        configurar_nodo(nodo_objetivo, nodo_actual);

        Integer nuevo_indice_de_nivel
                = proceso_de_balance_izq(nodo_sustituto, (Integer) nivel);
        return nuevo_indice_de_nivel;
    }
}
