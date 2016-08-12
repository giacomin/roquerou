package entidades;
// Generated 11/08/2016 23:28:16 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Produto generated by hbm2java
 */
@Entity
@Table(name="produto"
    ,catalog="roquedb"
)
public class Produto  implements java.io.Serializable {


     private Integer idProduto;
     private String nome;
     private String descricao;
     private String unidade;
     private float valorUnit;
     private Integer estoque;
     private Set compras = new HashSet(0);
     private Set itensPedidos = new HashSet(0);

    public Produto() {
    }

	
    public Produto(String nome, String unidade, float valorUnit) {
        this.nome = nome;
        this.unidade = unidade;
        this.valorUnit = valorUnit;
    }
    public Produto(String nome, String descricao, String unidade, float valorUnit, Integer estoque, Set compras, Set itensPedidos) {
       this.nome = nome;
       this.descricao = descricao;
       this.unidade = unidade;
       this.valorUnit = valorUnit;
       this.estoque = estoque;
       this.compras = compras;
       this.itensPedidos = itensPedidos;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_produto", unique=true, nullable=false)
    public Integer getIdProduto() {
        return this.idProduto;
    }
    
    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    
    @Column(name="nome", nullable=false, length=100)
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    
    @Column(name="descricao", length=65535)
    public String getDescricao() {
        return this.descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    
    @Column(name="unidade", nullable=false, length=25)
    public String getUnidade() {
        return this.unidade;
    }
    
    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    
    @Column(name="valor_unit", nullable=false, precision=12, scale=0)
    public float getValorUnit() {
        return this.valorUnit;
    }
    
    public void setValorUnit(float valorUnit) {
        this.valorUnit = valorUnit;
    }

    
    @Column(name="estoque")
    public Integer getEstoque() {
        return this.estoque;
    }
    
    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="produto")
    public Set getCompras() {
        return this.compras;
    }
    
    public void setCompras(Set compras) {
        this.compras = compras;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="produto")
    public Set getItensPedidos() {
        return this.itensPedidos;
    }
    
    public void setItensPedidos(Set itensPedidos) {
        this.itensPedidos = itensPedidos;
    }




}


