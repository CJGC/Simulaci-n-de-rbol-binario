package AVL;
/**
 *
 * @author cj
 */
public class AVL extends binarytree.Arbol_binario {
    
    private Nodo raiz;
    
    public AVL() {
        raiz = null;
    }

    @Override
    public Nodo getRaiz() {
        return raiz;
    }
    
    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
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
        }
        
        /* actualizacion de indices de nivel */
        nodo.setNivel_der(nodo_hijo_der.getNivel_izq());
        nodo_hijo_der.setNivel_izq(nodo.getNivel_izq()+1);
        
        /* decirle al nodo quien es su nuevo padre y al nuevo padre decirle 
        quien es su nuevo hijo */
        nodo.setPadre(nodo_hijo_der);
        nodo_hijo_der.setHijo_izq(nodo);
        
        /* si el nodo que se roto era raiz significa que no tenia padre por lo
        tanto el nuevo padre es null */
        if(nodo == raiz) {
            nodo_hijo_der.setPadre(null);
            raiz = nodo_hijo_der;
            return;
        }
        
        /* sino era raiz entonces configure su padre como el antiguo padre de
        nodo */
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
        }
        
        /* actualizacion de indices de nivel */
        nodo.setNivel_izq(nodo_hijo_izq.getNivel_der());
        nodo_hijo_izq.setNivel_der(nodo.getNivel_der()+1);
        
        /* decirle al nodo quien es su nuevo padre y al nuevo padre decirle 
        quien es su nuevo hijo */
        nodo.setPadre(nodo_hijo_izq);
        nodo_hijo_izq.setHijo_der(nodo);
        
        /* si el nodo que se roto era raiz significa que no tenia padre por lo
        tanto el nuevo padre es null */
        if(nodo == raiz) {
            nodo_hijo_izq.setPadre(null);
            raiz = nodo_hijo_izq;
            return;
        }
        
        /* sino era raiz entonces configure su padre como el antiguo padre de
        nodo */
        nodo_hijo_izq.setPadre(nodo_padre);
    }
    
    public void determinar_tipo_de_balance_izq(Nodo nodo_actual) {
        
        /* rotacion simple a la derecha */
        if (nodo_actual.getHijo_izq().getHijo_izq() != null) {
            rotacion_der(nodo_actual);
        }
        
        /* doble rotacion, primero a la izquierda y luego a la derecha */
        else {
            rotacion_izq(nodo_actual.getHijo_izq());
            rotacion_der(nodo_actual);
        }
    }
    
    public void determinar_tipo_de_balance_der(Nodo nodo_actual) {
        
        /* rotacion simple a la izquierda */
        if (nodo_actual.getHijo_der().getHijo_der() != null) {
            rotacion_izq(nodo_actual);
        }
        
        /* doble rotacion, primero a la derecha y luego a la izquierda */
        else {
            rotacion_der(nodo_actual.getHijo_der());
            rotacion_izq(nodo_actual);
        }
    }
    
    public int insertar(Nodo nuevo_nodo,Nodo nodo_actual,Nodo nodo_padre) {
        if(raiz == null) {
            raiz = nuevo_nodo;
            return 0;
        }
        
        if(nodo_actual == null) {
            if(nuevo_nodo.getValor() < nodo_padre.getValor()) {
                nodo_padre.setHijo_izq(nuevo_nodo);
            }
            else {
                nodo_padre.setHijo_der(nuevo_nodo);
            }
            nuevo_nodo.setPadre(nodo_padre);
            return 1;
        }
        
        if(nuevo_nodo.getValor() < nodo_actual.getValor()) {
            Integer nivel;
            nivel = insertar(nuevo_nodo,nodo_actual.getHijo_izq(),nodo_actual);
            
            Integer nuevo_nivel = nodo_actual.getNivel_izq() + nivel;
            nodo_actual.setNivel_izq(nuevo_nivel);
            
            Integer nivel_izq = nuevo_nivel;
            Integer nivel_der = nodo_actual.getNivel_der();
            
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
            
            determinar_tipo_de_balance_izq(nodo_actual);
        }
        else {
            Integer nivel;
            nivel = insertar(nuevo_nodo,nodo_actual.getHijo_der(),nodo_actual);
            Integer nuevo_nivel = nodo_actual.getNivel_der() + nivel;
            nodo_actual.setNivel_der(nuevo_nivel);
            
            Integer nivel_der = nuevo_nivel;
            Integer nivel_izq = nodo_actual.getNivel_izq();
            
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
            
            determinar_tipo_de_balance_der(nodo_actual);
        }
        return 0;
    }
}