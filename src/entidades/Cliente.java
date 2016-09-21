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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Cliente generated by hbm2java
 */
@Entity
@Table(name = "cliente", catalog = "roquedb"
)
public class Cliente implements java.io.Serializable {

    private Integer idCliente;
    private Cidade cidade;
    private String nome;
    private String fone;
    private String email;
    private String endereco;
    private String bairro;
    private Set pedidos = new HashSet(0);

    private String campoPesquisa;

    public Cliente() {
    }

    public Cliente(Cidade cidade, String nome) {
        this.cidade = cidade;
        this.nome = nome;
    }

    public Cliente(Cidade cidade, String nome, String fone, String email, String endereco, String bairro, Set pedidos) {
        this.cidade = cidade;
        this.nome = nome;
        this.fone = fone;
        this.email = email;
        this.endereco = endereco;
        this.bairro = bairro;
        this.pedidos = pedidos;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id_cliente", unique = true, nullable = false)
    public Integer getIdCliente() {
        return this.idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cidade", nullable = false)
    public Cidade getCidade() {
        return this.cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    @Column(name = "nome", nullable = false, length = 100)
    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Column(name = "fone", length = 25)
    public String getFone() {
        return this.fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    @Column(name = "email", length = 50)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "endereco", length = 150)
    public String getEndereco() {
        return this.endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Column(name = "bairro", length = 50)
    public String getBairro() {
        return this.bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
    public Set getPedidos() {
        return this.pedidos;
    }

    public void setPedidos(Set pedidos) {
        this.pedidos = pedidos;
    }

    public void setCampoPesquisaCliente(String campoPesquisaCliente) {
        this.campoPesquisa = campoPesquisaCliente;
    }

    public String getCampoPesquisaCliente() {
        return campoPesquisa;
    }

}
