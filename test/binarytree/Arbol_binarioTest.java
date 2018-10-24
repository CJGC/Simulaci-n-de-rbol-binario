package binarytree;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author cj
 */
public class Arbol_binarioTest {
    
    final private Arbol_binario ab = new Arbol_binario();
    
    /**
     * Test of insert method, of class Binary_tree.
     */
    @Test
    public void testInsertar() {
        
        /* casos de insercion base */
        Nodo nodo_padre = new Nodo(15);
        Nodo nodo_hijo_izq = new Nodo(10);
        Nodo nodo_hijo_der = new Nodo(20);
        
        ab.insertar(nodo_padre,ab.getRaiz(),null);
        assertEquals(nodo_padre,ab.getRaiz());
        
        ab.insertar(nodo_hijo_izq,ab.getRaiz(),null);
        assertEquals(nodo_hijo_izq,ab.getRaiz().getHijo_izq());
        
        ab.insertar(nodo_hijo_der,ab.getRaiz(),null);
        assertEquals(nodo_hijo_der,ab.getRaiz().getHijo_der());
        
        /* casos de insercion profunda */
        Nodo nodo5 = new Nodo(5);
        Nodo nodo13 = new Nodo(13);
        Nodo nodo17 = new Nodo(17);
        Nodo nodo25 = new Nodo(25);
        Nodo negNodo20 = new Nodo(-20);
        
        ab.insertar(nodo5,ab.getRaiz(),null);
        assertEquals(nodo5,ab.getRaiz().getHijo_izq().getHijo_izq());
        
        ab.insertar(nodo13,ab.getRaiz(),null);
        assertEquals(nodo13,ab.getRaiz().getHijo_izq().getHijo_der());
        
        ab.insertar(nodo17,ab.getRaiz(),null);
        assertEquals(nodo17,ab.getRaiz().getHijo_der().getHijo_izq());
        
        ab.insertar(nodo25,ab.getRaiz(),null);
        assertEquals(nodo25,ab.getRaiz().getHijo_der().getHijo_der());

        ab.insertar(negNodo20,ab.getRaiz(),null);
        assertEquals(negNodo20,ab.getRaiz().getHijo_izq().getHijo_izq().
                getHijo_izq());
    }

    /**
     * Test of delete method, of class Binary_tree.
     */
    @Test
    public void testEliminar() {
        
        /* creacion de los nodos */     
        Nodo[] nodos = {new Nodo(15),new Nodo(10),new Nodo(-20), new Nodo(5),
            new Nodo(1), new Nodo(7), new Nodo(13), new Nodo(11), new Nodo(10),
            new Nodo(14), new Nodo(13),new Nodo(17), new Nodo(25), new Nodo(25)};
        
        /* creacion del arbol */
        for(Nodo nodo : nodos) {
            ab.insertar(nodo, ab.getRaiz(), null);
        }
        
        /* seccion de pruebas de eliminacion de nodos */
        assertEquals(nodos[0],ab.eliminar(nodos[0], null, null));
        assertEquals(nodos[3],ab.eliminar(nodos[3], null, null));
        assertEquals(nodos[1],ab.eliminar(nodos[1], null, null));
        assertEquals(nodos[12],ab.eliminar(nodos[12], null, null));
        assertEquals(nodos[13],ab.eliminar(nodos[13], null, null));
        assertEquals(nodos[6],ab.eliminar(nodos[6], null, null));
        assertEquals(nodos[2],ab.eliminar(nodos[2], null, null));
        assertEquals(nodos[8],ab.eliminar(nodos[8], null, null));
        assertEquals(nodos[11],ab.eliminar(nodos[11], null, null));
        assertEquals(nodos[7],ab.eliminar(nodos[7], null, null));
        assertEquals(nodos[9],ab.eliminar(nodos[9], null, null));
        assertEquals(nodos[10],ab.eliminar(nodos[10], null, null));
        assertEquals(nodos[4],ab.eliminar(nodos[4], null, null));
        assertEquals(nodos[5],ab.eliminar(nodos[5], null, null));
        
        /* despues de que el arbol se encuentre vacio */
        assertEquals(null,ab.eliminar(nodos[10], null, null));
        assertEquals(null,ab.eliminar(nodos[3], null, null));
        assertEquals(null,ab.eliminar(nodos[7], null, null));
        
    }

    /**
     * Test of find method, of class Binary_tree.
     */
    @Test
    public void testBuscar() {
        /* En este test no se recomienda utilizar nodos con valores iguales
        ya que la primera coincidencia podria no ser igual al nodo con el que
        se detono la busqueda */
        
        /* creacion de los nodos */ 
        Nodo[] nodos = {new Nodo(15),new Nodo(10),new Nodo(-20), new Nodo(5),
            new Nodo(1), new Nodo(7), new Nodo(13), new Nodo(11), new Nodo(14), 
            new Nodo(17), new Nodo(25)};
        
        /* creacion del arbol */
        for(Nodo nodo : nodos) {
            ab.insertar(nodo, ab.getRaiz(),null);
        }
        
        /* seccion de pruebas para la busqueda de nodos */
        assertEquals(nodos[0],ab.buscar(nodos[0], ab.getRaiz()));
        assertEquals(nodos[2],ab.buscar(nodos[2], ab.getRaiz()));
        assertEquals(nodos[6],ab.buscar(nodos[6], ab.getRaiz()));
        assertEquals(nodos[3],ab.buscar(nodos[3], ab.getRaiz()));
        assertEquals(nodos[4],ab.buscar(nodos[4], ab.getRaiz()));
        assertEquals(nodos[5],ab.buscar(nodos[5], ab.getRaiz()));
        assertEquals(nodos[7],ab.buscar(nodos[7], ab.getRaiz()));
        assertEquals(nodos[8],ab.buscar(nodos[8], ab.getRaiz()));
        assertEquals(nodos[9],ab.buscar(nodos[9], ab.getRaiz()));
        assertEquals(nodos[1],ab.buscar(nodos[1], ab.getRaiz()));
        assertEquals(nodos[10],ab.buscar(nodos[10], ab.getRaiz()));
        
        /* seccion de pruebas para busqueda de nodos no existentes */
        Nodo nodo100 = new Nodo(100);
        Nodo negNodo100 = new Nodo(-100);
        Nodo nodo200 = new Nodo(200);
        
        assertEquals(null,ab.buscar(nodo100, ab.getRaiz()));
        assertEquals(null,ab.buscar(negNodo100, ab.getRaiz()));
        assertEquals(null,ab.buscar(nodo200, ab.getRaiz()));
        
    }

    /**
     * Test of list method, of class Binary_tree.
     */
    @Test
    public void testListar() {  
        /* creacion de nodos */
        Nodo[] nodos = {new Nodo(15),new Nodo(10),new Nodo(-20), new Nodo(5),
            new Nodo(1), new Nodo(7), new Nodo(13), new Nodo(11), new Nodo(10),
            new Nodo(14), new Nodo(13),new Nodo(17), new Nodo(25), new Nodo(25)};
        
        /* creacion del arbol */
        for(Nodo nodo : nodos) {
            ab.insertar(nodo, ab.getRaiz(), null);
        }
        
        /* seccion de prueba de listar arbol */
        List<Nodo> lista_retornada = new ArrayList<>();
        ab.listar(ab.getRaiz(),lista_retornada);
        
        int i=0;
        for(Nodo nodo : lista_retornada) {
            assertEquals(nodos[i],nodo);
            i++;
        }
        
    }
    
}
