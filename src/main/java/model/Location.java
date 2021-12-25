package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.typesForXml.JaxbLocation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table
@XmlRootElement
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @NotNull
    @XmlElement
    private Long x; //Поле не может быть null

    @XmlElement
    private float y;

    @NotNull
    @XmlElement
    private Float z; //Поле не может быть null

    @NotNull
    @XmlElement
    private String name; //Поле не может быть null

    public void update(JaxbLocation data){
        this.x = data.getX();
        this.y = data.getY();
        this.z = data.getZ();
        this.name = data.getName();
    }
}