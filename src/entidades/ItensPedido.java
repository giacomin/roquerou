package entidades;
// Generated 11/08/2016 23:28:16 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ItensPedido generated by hbm2java
 */
@Entity
@Table(name="itens_pedido"
    ,catalog="roquedb"
)
public class ItensPedido  implements java.io.Serializable {


     private Integer idItensPedido;
     private Pedido pedido;
     private Produto produto;
     private int quantidade;

    public ItensPedido() {
    }

    public ItensPedido(Pedido pedido, Produto produto, int quantidade) {
       this.pedido = pedido;
       this.produto = produto;
       this.quantidade = quantidade;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_itens_pedido", unique=true, nullable=false)
    public Integer getIdItensPedido() {
        return this.idItensPedido;
    }
    
    public void setIdItensPedido(Integer idItensPedido) {
        this.idItensPedido = idItensPedido;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_pedido", nullable=false)
    public Pedido getPedido() {
        return this.pedido;
    }
    
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_produto", nullable=false)
    public Produto getProduto() {
        return this.produto;
    }
    
    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    
    @Column(name="quantidade", nullable=false)
    public int getQuantidade() {
        return this.quantidade;
    }
    
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }




}


