package AVL;
/**
 *
 * @author cj
 */
public class Nodo extends binarytree.Nodo{
    
        private Integer left_level;
        private Integer right_level;
        private String status;
        
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
}
