/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AVL;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author cj
 */
import binarytree.Nodo;
import java.util.ArrayList;
import java.util.List;
public class AVLTest {
    
    final private AVL avl = new AVL();

    @Test
    public void testRotacion_izq() {
        Nodo nodo1 = new Nodo(1);
        Nodo nodo2 = new Nodo(2);
        Nodo nodo3 = new Nodo(3);
        
        avl._insertar(nodo1, avl.getRaiz(), null);
        avl._insertar(nodo2, avl.getRaiz(), null);
        avl._insertar(nodo3, avl.getRaiz(), null);
        
        Nodo [] esperado = {nodo2, nodo1, nodo3};
        List<Nodo> resultado = new ArrayList<>();
        avl.listar(avl.getRaiz(), resultado);
        
        int i=0;
        for(Nodo nodo : resultado) {
            assertEquals(esperado[i],nodo);
            i++;
        }
    }

    @Test
    public void testRotacion_der() {
        Nodo nodo1 = new Nodo(-1);
        Nodo nodo2 = new Nodo(-2);
        Nodo nodo3 = new Nodo(-3);
        
        avl._insertar(nodo1, avl.getRaiz(), null);
        avl._insertar(nodo2, avl.getRaiz(), null);
        avl._insertar(nodo3, avl.getRaiz(), null);
        
        Nodo [] esperado = {nodo2, nodo3, nodo1};
        List<Nodo> resultado = new ArrayList<>();
        avl.listar(avl.getRaiz(), resultado);
        
        int i=0;
        for(Nodo nodo : resultado) {
            assertEquals(esperado[i],nodo);
            i++;
        }
    }
    
}
