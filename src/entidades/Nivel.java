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


@Entity
@Table(name = "nivel", catalog = "roquedb"
)
public class Nivel implements java.io.Serializable {

    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private Integer idNivel;
    private Integer nivelacesso;

    public Nivel() {
    }

    public Nivel(Integer nivelacesso) {
        this.nivelacesso = nivelacesso;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id_nivel", unique = true, nullable = false)
    public Integer getIdNivel() {
        return this.idNivel;
    }

    public void setIdNivel(Integer idNivel) {
        Integer oldIdNivel = this.idNivel;
        this.idNivel = idNivel;
        changeSupport.firePropertyChange("idNivel", oldIdNivel, idNivel);
    }

    @Column(name = "nivelacesso", nullable = false, length = 11)
    public Integer getNivelacesso() {
        return this.nivelacesso;
    }

    public void setNivelacesso(Integer nivelacesso) {
        Integer oldNivelacesso = this.nivelacesso;
        this.nivelacesso = nivelacesso;
        changeSupport.firePropertyChange("nivelacesso", oldNivelacesso, nivelacesso);
    }

}
    // Hibernate gerou automaticamente
    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "nivel")
    
/*
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
 */
