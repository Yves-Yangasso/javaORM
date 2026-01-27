package yang.bao.app_java_diti4.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "type_assurance")
public class TypeAssurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "label", nullable = false, unique = true)
    private String label;

    public TypeAssurance() {}

    public TypeAssurance(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}