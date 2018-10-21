package binarytree;

/**
 *
 * @author cj
 */
public class Nodo {

    private Integer nivel_izq;
    private Integer nivel_der;
    private String estado;
    private Integer valor;
    private Nodo hijo_der;
    private Nodo hijo_izq;
    private Nodo padre;

    public Nodo() {
        nivel_izq = 0;
        nivel_der = 0;
        estado = "";
        valor = null;
        hijo_der = null;
        hijo_izq = null;
        padre = null;
    }

    public Nodo(Integer valor) {
        nivel_izq = 0;
        nivel_der = 0;
        estado = "";
        this.valor = valor;
        hijo_izq = null;
        hijo_der = null;
        padre = null;
    }

    public Integer getNivel_izq() {
        return nivel_izq;
    }
    public void setNivel_izq(Integer nivel_izq) {
        this.nivel_izq = nivel_izq;
    }
    public Integer getNivel_der() {
        return nivel_der;
    }
    public void setNivel_der(Integer nivel_der) {
        this.nivel_der = nivel_der;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Integer getValor() {
            return valor;
    }
    public void setValor(int valor) {
            this.valor = valor;
    }
    public Nodo getHijo_der() {
            return hijo_der;
    }
    public void setHijo_der(Nodo hijo_der) {
            this.hijo_der = hijo_der;
    }
    public Nodo getHijo_izq() {
            return hijo_izq;
    }
    public void setHijo_izq(Nodo hijo_izq) {
            this.hijo_izq = hijo_izq;
    }
    public Nodo getPadre() {
            return padre;
    }
    public void setPadre(Nodo padre) {
            this.padre = padre;
    }
}
