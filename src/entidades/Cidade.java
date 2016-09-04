package entidades;
// Generated 11/08/2016 23:28:16 by Hibernate Tools 4.3.1


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
import javax.persistence.Transient;

/**
 * Cidade generated by hbm2java
 */
@Entity
@Table(name="cidade"
    ,catalog="roquedb"
)
public class Cidade implements java.io.Serializable {
    
     private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
     private Integer idCidade;
     private String nome;
     private String uf;
     private Integer cep;
     private Set fornecedors = new HashSet(0);
     private Set clientes = new HashSet(0);
     //pesquisa
     private String comboPesquisa;
     private String campoPesquisa;
     

    public Cidade() {
    }

	
    public Cidade(String nome, String uf) {
        this.nome = nome;
        this.uf = uf;
    }
    public Cidade(String nome, String uf, Integer cep, Set fornecedors, Set clientes) {
       this.nome = nome;
       this.uf = uf;
       this.cep = cep;
       this.fornecedors = fornecedors;
       this.clientes = clientes;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id_cidade", unique=true, nullable=false)
    public Integer getIdCidade() {
        return this.idCidade;
    }
    
    public void setIdCidade(Integer idCidade) {
        Integer oldIdCidade = this.idCidade;
        this.idCidade = idCidade;
        changeSupport.firePropertyChange("idCidade", oldIdCidade, idCidade);
    }

    
    @Column(name="nome", nullable=false, length=50)
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        String oldNome = this.nome;
        this.nome = nome;
        changeSupport.firePropertyChange("nome", oldNome, nome);
    }

    
    @Column(name="uf", nullable=false, length=2)
    public String getUf() {
        return this.uf;
    }
    
    public void setUf(String uf) {
        String oldUf = this.uf;
        this.uf = uf;
        changeSupport.firePropertyChange("uf", oldUf, uf);
    }

    
    @Column(name="cep")
    public Integer getCep() {
        return this.cep;
    }
    
    public void setCep(Integer cep) {
        Integer oldCep = this.cep;
        this.cep = cep;
        changeSupport.firePropertyChange("cep", oldCep, cep);
    }

    // Pesquisa
    public void setComboPesquisaCidade(String comboPesquisaCidade) {
        this.comboPesquisa = comboPesquisaCidade;
    }
    
    public void setCampoPesquisaCidade(String campoPesquisaCidade) {
        this.campoPesquisa = campoPesquisaCidade;
    }
    
    
    public String getComboPesquisaCidade() {
        return comboPesquisa;
    }
    
    public String getCampoPesquisaCidade() {
        return campoPesquisa;
    }
    
@OneToMany(fetch=FetchType.LAZY, mappedBy="cidade")
    public Set getFornecedors() {
        return this.fornecedors;
    }
    
    public void setFornecedors(Set fornecedors) {
        this.fornecedors = fornecedors;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="cidade")
    public Set getClientes() {
        return this.clientes;
    }
    
    public void setClientes(Set clientes) {
        this.clientes = clientes;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }


    
}


