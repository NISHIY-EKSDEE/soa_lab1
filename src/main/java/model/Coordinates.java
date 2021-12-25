package model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.typesForXml.JaxbCoordinates;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@XmlRootElement
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Max(523)
    @XmlElement
    private double x; //Максимальное значение поля: 523

    @XmlElement
    private float y;

    public void update(JaxbCoordinates data) {
        this.x = data.getX();
        this.y = data.getY();
    }
}

