package AVL;

import binarytree.Nodo;
import java.util.Scanner;

/**
 *
 * @author cj
 */
public class Main {

    public void menu() {
        System.out.println("\n============Arbol AVL============");
        System.out.println("\t1) Imprimir arbol");
        System.out.println("\t2) Agregar nodo");
        System.out.println("\t3) Eliminar nodo");
        System.out.println("\t4) Encontrar nodo");
        System.out.println("\t5) Salir");
        System.out.println("=================================");
        System.out.println();
        System.out.print("Entra numero de opcion: ");
    }

    private void leerDatos(Nodo nodo) {
        boolean exit = false;
        do {
            try {
                Scanner scan = new Scanner(System.in);
                String num;
                System.out.print("Entra un numero: ");
                num = scan.nextLine();
                nodo.setValor(Integer.parseInt(num));
                exit = true;
            } catch (NumberFormatException e) {
                System.err.println("Por favor ingrese un numero.");
            }
        } while (!exit);
    }

    public void procesarOpt(Integer opt, AVL avl) {
        Nodo nuevo_nodo = new Nodo();

        switch (opt) {
            case 1:
                avl.listar(avl.getRaiz(), null);
                break;
            case 2:
                leerDatos(nuevo_nodo);
                avl._insertar(nuevo_nodo, avl.getRaiz(), null);
                break;
            case 3:
                leerDatos(nuevo_nodo);
                avl.eliminar(nuevo_nodo, null, null);
                break;
            case 4:
                leerDatos(nuevo_nodo);
                avl.buscar(nuevo_nodo, avl.getRaiz());
                break;
            default:
                System.out.println("Opcion no valida");
        }
    }

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Main m = new Main();
        AVL avl = new AVL();
        Scanner scan = new Scanner(System.in);
        Integer opt;

        do {
            try {
                m.menu();
                String userInput = scan.nextLine();
                opt = Integer.parseInt(userInput);
                if (opt == 5) {
                    break;
                }
                m.procesarOpt(opt, avl);
            } catch (NumberFormatException e) {
                System.err.println("Por favor ingrese un numero.");
            }
        } while (true);
    }
}
