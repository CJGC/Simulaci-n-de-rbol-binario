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

    private void leerDatos(Nodo nodo) {
        boolean exit = false;
        do {
            Scanner scan = new Scanner(System.in);
            Integer num;
            System.out.print("Entra un numero: ");
            num = scan.nextInt();
            if(scan.hasNextLine()) {
                String basura = scan.nextLine();
                System.err.println(basura + "no es un numero valido.");
                continue;
            }
            nodo.setValor(num);
            exit = true;
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
                ab.insertar(nuevo_nodo, ab.getRaiz(),null);
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
        
        do {
            Integer opt;
            m.menu();
            Scanner scan = new Scanner(System.in);
            opt = scan.nextInt();
            
            if(scan.hasNextLine()) {
                String basura = scan.nextLine();
                System.err.println(basura + "no es un numero valido.");
                continue;
            }
            if (opt.equals(5)) {
                break;
            }
            m.procesarOpt(opt, ab);
            
        } while (true);
    }

}
