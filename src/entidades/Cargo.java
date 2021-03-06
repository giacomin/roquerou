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

/**
 * Cidade generated by hbm2java
 */
@Entity
@Table(name = "cargo", catalog = "roquedb"
)
public class Cargo implements java.io.Serializable {

    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private Integer idCargo;
    private String nome;
    private Set usuarios = new HashSet(0);
    //pesquisa
    private String comboPesquisa;
    private String campoPesquisa;

    public Cargo() {
    }

    public Cargo(String nome) {
        this.nome = nome;
    }

    public Cargo(String nome, Set usuarios) {
        this.nome = nome;
        this.usuarios = usuarios;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id_cargo", unique = true, nullable = false)
    public Integer getIdCargo() {
        return this.idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        Integer oldIdCargo = this.idCargo;
        this.idCargo = idCargo;
        changeSupport.firePropertyChange("idCargo", oldIdCargo, idCargo);
    }

    @Column(name = "nome", nullable = false, length = 50)
    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        String oldNome = this.nome;
        this.nome = nome;
        changeSupport.firePropertyChange("nome", oldNome, nome);
    }

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

    // Hibernate gerou automaticamente
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cargo")
    public Set getUsuarios() {
        return this.usuarios;
    }

    public void setUsuarios(Set usuarios) {
        this.usuarios = usuarios;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
