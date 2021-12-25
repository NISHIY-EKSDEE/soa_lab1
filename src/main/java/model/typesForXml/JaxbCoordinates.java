package model.typesForXml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import model.Coordinates;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "coordinates")
public class JaxbCoordinates {
    @XmlElement
    private Double x; //Максимальное значение поля: 523

    @XmlElement
    private Float y;

    public Coordinates toCoordinates() {
        return new Coordinates(0, x, y);
    }
}
