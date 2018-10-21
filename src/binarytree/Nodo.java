package binarytree;

/**
 *
 * @author cj
 */
public class Nodo {

        private Integer left_level;
        private Integer right_level;
        private String status;
	private Integer valor;
	private Nodo hijo_der;
	private Nodo hijo_izq;
	private Nodo padre;
	
	public Nodo() {
            valor = null;
            hijo_der = null;
            hijo_izq = null;
            padre = null;
	}
	
        public Nodo(Integer valor) {
            this.valor = valor;
            hijo_izq = null;
            hijo_der = null;
            padre = null;
        }

        public Integer getLeft_level() {
            return left_level;
        }
        public void setLeft_level(Integer left_level) {
            this.left_level = left_level;
        }
        public Integer getRight_level() {
            return right_level;
        }
        public void setRight_level(Integer right_level) {
            this.right_level = right_level;
        }
        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
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
