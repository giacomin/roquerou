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
 * Fornecedor generated by hbm2java
 */
@Entity
@Table(name = "fornecedor", catalog = "roquedb"
)
public class Fornecedor implements java.io.Serializable {

    private Integer idFornecedor;
    private Cidade cidade;
    private String nome;
    private String fone;
    private String email;
    private int cnpj;
    private String endereco;
    private String bairro;
    private Set compras = new HashSet(0);

    private String campoPesquisa;

    public Fornecedor() {
    }

    public Fornecedor(Cidade cidade, String nome, String fone, int cnpj, String endereco) {
        this.cidade = cidade;
        this.nome = nome;
        this.fone = fone;
        this.cnpj = cnpj;
        this.endereco = endereco;
    }

    public Fornecedor(Cidade cidade, String nome, String fone, String email, int cnpj, String endereco, String bairro, Set compras) {
        this.cidade = cidade;
        this.nome = nome;
        this.fone = fone;
        this.email = email;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.bairro = bairro;
        this.compras = compras;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id_fornecedor", unique = true, nullable = false)
    public Integer getIdFornecedor() {
        return this.idFornecedor;
    }

    public void setIdFornecedor(Integer idFornecedor) {
        this.idFornecedor = idFornecedor;
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

    @Column(name = "fone", nullable = false, length = 25)
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

    @Column(name = "cnpj", nullable = false)
    public int getCnpj() {
        return this.cnpj;
    }

    public void setCnpj(int cnpj) {
        this.cnpj = cnpj;
    }

    @Column(name = "endereco", nullable = false, length = 150)
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fornecedor")
    public Set getCompras() {
        return this.compras;
    }

    public void setCompras(Set compras) {
        this.compras = compras;
    }

    public void setCampoPesquisaFornecedor(String campoPesquisaFornecedor) {
        this.campoPesquisa = campoPesquisaFornecedor;
    }

    public String getCampoPesquisaFornecedor() {
        return campoPesquisa;
    }
    
    @Override
    public String toString() {
        return getNome();
    }

}
