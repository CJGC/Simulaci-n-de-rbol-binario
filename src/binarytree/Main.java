package binarytree;

import java.util.Scanner;

/**
 *
 * @author cj
 */
public class Main {

    public void menu() {
        System.out.println("\n=================================");
        System.out.println("\t1) Imprimir arbol");
        System.out.println("\t2) Agregar nodo");
        System.out.println("\t3) Eliminar nodo");
        System.out.println("\t4) Encontrar nodo");
        System.out.println("\t5) Salir");
        System.out.println("=================================");
        System.out.println();
        System.out.print("Entra numero de opcion: ");
    }

    protected void leerDatos(Nodo nodo) {
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

    public void procesarOpt(Integer opt, Arbol_binario ab) {
        Nodo nuevo_nodo = new Nodo();

        switch (opt) {
            case 1:
                ab.listar(ab.getRaiz(), null);
                break;
            case 2:
                leerDatos(nuevo_nodo);
                ab.setPermitir_insercion(true);
                ab.insertar(nuevo_nodo, ab.getRaiz());
                break;
            case 3:
                leerDatos(nuevo_nodo);
                ab.eliminar(nuevo_nodo, null, null);
                break;
            case 4:
                leerDatos(nuevo_nodo);
                ab.buscar(nuevo_nodo, ab.getRaiz());
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
        Arbol_binario ab = new Arbol_binario();
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
                m.procesarOpt(opt, ab);
            } catch (NumberFormatException e) {
                System.err.println("Por favor ingrese un numero.");
            }
        } while (true);
    }

}
