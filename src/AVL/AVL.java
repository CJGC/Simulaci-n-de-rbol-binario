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
        nodo_hijo_der.setNivel_izq(nodo.getNivel_izq() + 1);

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

    public void rotacion_der(Nodo nodo) {
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
        nodo_hijo_izq.setNivel_der(nodo.getNivel_der() + 1);

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
            rotacion_izq(hijo_izq);
            rotacion_der(nodo_actual);
        } /* rotacion simple a la derecha */ else {
            rotacion_der(nodo_actual);
        }
    }

    public void determinar_tipo_de_balance_der(Nodo nodo_actual) {

        Nodo hijo_der = nodo_actual.getHijo_der();
        Integer nivel_izq = hijo_der.getNivel_izq();
        Integer nivel_der = hijo_der.getNivel_der();

        /* doble rotacion, primero a la derecha y luego a la izquierda */
        if (nivel_izq > nivel_der) {
            rotacion_der(hijo_der);
            rotacion_izq(nodo_actual);
        } /* rotacion simple a la izquierda */ else {
            rotacion_izq(nodo_actual);
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
    protected void configurar_nodo(Nodo nodo_actual, Nodo nodo_sustituto) {

        /* obtenga la informacion de nodo actual */
        Nodo nodo_padre = nodo_actual.getPadre();
        Nodo nodo_hijo_izq = nodo_actual.getHijo_izq();
        Nodo nodo_hijo_der = nodo_actual.getHijo_der();
        Integer nivel_izq = nodo_actual.getNivel_izq();
        Integer nivel_der = nodo_actual.getNivel_der();

        /* configure el papel del nodo sustituto en el arbol */
        nodo_sustituto.setPadre(nodo_padre);
        nodo_sustituto.setHijo_izq(nodo_hijo_izq);
        nodo_sustituto.setHijo_der(nodo_hijo_der);
        nodo_sustituto.setNivel_izq(nivel_izq);
        nodo_sustituto.setNivel_izq(nivel_der);

        /* los nuevos hijos del nodo sustituto (si existen) deben saber 
        que el sera su nuevo padre */
        if (nodo_hijo_izq != null) {
            nodo_hijo_izq.setPadre(nodo_sustituto);
            nodo_hijo_izq.setEstado(" hijo izq de " + nodo_sustituto.getValor());
        }
        if (nodo_hijo_der != null) {
            nodo_hijo_der.setPadre(nodo_sustituto);
            nodo_hijo_der.setEstado(" hijo der de " + nodo_sustituto.getValor());
        }

        Nodo nodo_padre_sustituto;
        nodo_padre_sustituto = nodo_padre;

        /* si nodo actual es raiz  entonces nodo sustituto no le reporta a nin
        gun nuevo padre*/
        if (nodo_actual == getRaiz()) {
            setRaiz(nodo_sustituto);
            nodo_sustituto.setEstado(" nodo raiz");
            return;
        }

        /* sino entonces decirle a mi nuevo padre que yo, soy su nuevo hijo */
        if (nodo_padre_sustituto.getHijo_izq() == nodo_actual) {
            nodo_padre_sustituto.setHijo_izq(nodo_sustituto);
            nodo_sustituto.setEstado(" hijo izq de "
                    + nodo_padre_sustituto.getValor());
        } else {
            nodo_padre_sustituto.setHijo_der(nodo_sustituto);
            nodo_sustituto.setEstado(" hijo der de "
                    + nodo_padre_sustituto.getValor());
        }
    }

    public Object eliminar(Nodo nodo, Nodo nodo_actual, Nodo nodo_sustituto,
            boolean nodo_encontrado) {

        if (getRaiz() == null) {
            System.out.println("El arbol esta vacio.");
            return 0;
        }

        /* busqueda para saber si el nodo solicitado existe, si existe entonces
           nodo actual apuntara a ese nodo */
        if (!nodo_encontrado) {
            
            if (nodo_actual == null) {
                System.out.println("Nodo con valor \"" + nodo.getValor() 
                        + "\" no existe.");
                return null;
            }
            
            if (nodo.getValor() < nodo_actual.getValor()) {
                Object nivel;
                nivel = eliminar(nodo, nodo_actual.getHijo_izq(), nodo_sustituto,
                        nodo_encontrado);
                
                /* si nivel es null significa que el nodo solicitado no fue 
                encontrado en el arbol */
                if (nivel == null) {
                    return nivel;
                }
                
                /* seccion de balance */
                Integer factor_de_balance =
                    calculo_factor_de_balance_izq(nodo_actual, (Integer) nivel);
                
                /* si el factor de balance no es 1 o 0 sigifica que el arbol
                esta desbalanceado */
                if(!factor_de_balance.equals(0) && 
                        !factor_de_balance.equals(1)) {
                    determinar_tipo_de_balance_izq(nodo_actual);
                    return 0;
                }
                
                /* indicar al nodo padre que decremente o no */
                if (nivel.equals(1)) {
                    return 1;
                } else {
                    return 0;
                }
                
            } else if (nodo.getValor() > nodo_actual.getValor()) {
                Object nivel;
                nivel = eliminar(nodo, nodo_actual.getHijo_der(), nodo_sustituto,
                        nodo_encontrado);
                
                /* si nivel es null significa que el nodo solicitado no fue 
                encontrado en el arbol */
                if (nivel == null) {
                    return nivel;
                }
                
                /* seccion de balance */
                Integer factor_de_balance =
                    calculo_factor_de_balance_der(nodo_actual, (Integer) nivel);
                
                /* si el factor de balance no es 1 o 0 sigifica que el arbol
                esta desbalanceado */                
                if(!factor_de_balance.equals(0) && 
                        !factor_de_balance.equals(1)) {
                    determinar_tipo_de_balance_der(nodo_actual);
                    return 0;
                }
                
                /* indicar al nodo padre que decremente o no */
                if (nivel.equals(1)) {
                    return 1;
                } else {
                    return 0;
                }
            }
            
            System.out.println("Nodo con valor \"" + nodo.getValor() 
                    + "\" fue eliminado correctamente.");
            
            nodo_encontrado = true;
            nodo_sustituto = nodo_actual;                
            /* la inicializacion de nodo_sustituto varia segun el caso, por 
            ejemplo, al no tener un hijo izquierdo el nodo que se piensa 
            eliminar significa una de dos situaciones:
            Primero: el nodo solo tiene hijo derecho o
            Segundo: el nodo es una hoja. */
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
                return 0;
                /* si no tenia padre entonces no hay que notificarle
                            sobre el nuevo indice de nivel a nadie */
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
            /* si tenia padre entonces significa que un nivel del 
                        arbol decrementa en esa rama */
        }

        /* caso 2 (caso base) el sustituto es una hoja */
        if (nodo_sustituto.getHijo_izq() == null
                && nodo_sustituto.getHijo_der() == null) {

            /* si el nodo actual es raiz y no tiene hijos configurelo a null*/
            if (nodo_actual == getRaiz() && nodo_actual == nodo_sustituto) {
                setRaiz(null);
                return 0;
                /* valor irrelevante ya que el arbol esta completamen
                           te vacio*/
            }

            /* sino decirle al padre del sustituto que ya no es mas su hijo */
            Nodo nodo_padre_sustituto = nodo_sustituto.getPadre();
            if (nodo_padre_sustituto.getHijo_izq() == nodo_sustituto) {
                nodo_padre_sustituto.setHijo_izq(null);
            } else {
                nodo_padre_sustituto.setHijo_der(null);
            }

            /* si mi hijo sustituto soy yo mismo significa que el nodo que se va
            a eliminar en el arbol es un nodo hoja, entonces simplemente muero*/
            if (nodo_actual == nodo_sustituto) {
                return -1;
                /* como yo desaparezco como nodo hoja significa que 
                            ese nivel desaparece, por lo tanto mi padre debe
                            decrementar su indice de nivel al cual pertenezco */
            }

            /* sino quiere decir que debo configurar a mi nodo sustituto antes
            de morir */
            configurar_nodo(nodo_actual, nodo_sustituto);
            return -1;
            /* como nodo sustituto esta cambiando de nivel, significa
                        que el antiguo padre del nodo sustituto debe saber que 
                        su hijo ha dejado de serlo, por lo tanto se pierde ese 
                        nivel */
        }

        /* caso 3 el nodo sustituto tiene un hijo derecho */
        if (nodo_sustituto.getHijo_der() != null) {
            Object nivel;
            nivel = eliminar(nodo, nodo_actual, nodo_sustituto.getHijo_der(),
                    nodo_encontrado);
            Integer factor_de_balance
                    = calculo_factor_de_balance_der(nodo_sustituto, 
                            (Integer) nivel);

            /* si el factor de balance es 1 significa que hubo un nivel que desa
            parecio y por lo tanto el padre debe decrementar ese indice */
            if (factor_de_balance.equals(1)) {
                return -1;
            } else if (factor_de_balance.equals(0)) {
                return 0;
            }

            determinar_tipo_de_balance_der(nodo_actual);
            return 0;
        }

        /* caso 4 el nodo sustituto tiene un hijo izquierdo */
        Object nivel;
        nivel = eliminar(nodo, nodo_sustituto, nodo_sustituto.getHijo_izq(),
                nodo_encontrado);

        /* en este punto el lugar que tenia nodo sustituto fue tomado por otro
        nodo, por lo tanto, antes de realizar la nueva configuracion del nodo
        sustituto es necesario saber que nodo lo ha remplazado, para eso se uti
        liza el estado antiguo de nodo sustituto asi como a su padre antiguo */
        String estado_antiguo_de_sustituto = nodo_sustituto.getEstado();
        Nodo antiguo_padre_de_sustituto = nodo_sustituto.getPadre();

        configurar_nodo(nodo_actual, nodo_sustituto);

        Nodo nodo_que_remplazo_sustituto;
        if (estado_antiguo_de_sustituto.equals(" hijo izq de "
                + antiguo_padre_de_sustituto.getValor())) {
            nodo_que_remplazo_sustituto
                    = antiguo_padre_de_sustituto.getHijo_izq();
        } else {
            nodo_que_remplazo_sustituto
                    = antiguo_padre_de_sustituto.getHijo_der();
        }

        /* una vez que sepamos que nodo ha remplazo al sustituto, realizamos los
        ajustes habituales de balance (si es el caso), mediante el calculo del
        factor de balance */
        Integer factor_de_balance;
        factor_de_balance
                = calculo_factor_de_balance_izq(nodo_que_remplazo_sustituto,
                        (Integer) nivel);

        /* si el factor de balance es 1 significa que hubo un nivel que desapare
        cio y por lo tanto el padre debe decrementar ese indice */
        if (factor_de_balance.equals(1)) {
            return -1;
        } else if (factor_de_balance.equals(0)) {
            return 0;
        }

        determinar_tipo_de_balance_izq(nodo_que_remplazo_sustituto);
        return 0;
    }
}
