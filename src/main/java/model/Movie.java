package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.typesForXml.JaxbMovie;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import model.typesForXml.ZonedDateTimeAdapter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Root
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NotNull
    @Element
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @Element
    @NotBlank
    private String name; //Поле не может быть null, Строка не может быть пустой

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @Element
    @NotNull
    private Coordinates coordinates; //Поле не может быть null

    @Element
    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)
    @NotNull
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @Element
    @Positive
    private long oscarsCount; //Значение поля должно быть больше 0

    @Element
    @Positive
    private long goldenPalmCount; //Значение поля должно быть больше 0

    @Element
    @NotNull
    @Positive
    private Double budget; //Значение поля должно быть больше 0, Поле не может быть null

    @Element
    @Enumerated(EnumType.STRING)
    private MovieGenre genre; //Поле может быть null

    @Element
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Person director; //Поле может быть null

    public void update(JaxbMovie data) {
        this.name = data.getName();
        this.coordinates.update(data.getCoordinates());
        this.oscarsCount = data.getOscarsCount();
        this.goldenPalmCount = data.getGoldenPalmCount();
        this.budget = data.getBudget();
        this.genre = data.getGenre();
        this.director.update(data.getDirector());
    }
}
