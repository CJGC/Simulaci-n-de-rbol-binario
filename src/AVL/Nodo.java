package AVL;
/**
 *
 * @author cj
 */
public class Nodo extends binarytree.Nodo{
    
        private Integer nivel_izq;
        private Integer nivel_der;
        private String estado;
        
        public Nodo() {
            nivel_izq = null;
            nivel_der = null;
            estado = null;
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
}
