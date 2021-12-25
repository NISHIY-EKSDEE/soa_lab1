package model.typesForXml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "movie")
public class JaxbMovie implements Serializable {
    @XmlElement
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @XmlElement
    private String name; //Поле не может быть null, Строка не может быть пустой

    @XmlElement
    private JaxbCoordinates coordinates; //Поле не может быть null

    @XmlElement
    private Long oscarsCount; //Значение поля должно быть больше 0

    @XmlElement
    private Long goldenPalmCount; //Значение поля должно быть больше 0

    @XmlElement
    private Double budget; //Значение поля должно быть больше 0, Поле не может быть null

    @XmlElement
    private MovieGenre genre; //Поле может быть null

    @XmlElement(name = "director")
    private JaxbPerson director; //Поле может быть null

    public Movie toMovie() {
        return new Movie(
                id,
                name,
                coordinates.toCoordinates(),
                ZonedDateTime.now(),
                oscarsCount,
                goldenPalmCount,
                budget,
                genre,
                director.toHuman()
        );
    }
}

