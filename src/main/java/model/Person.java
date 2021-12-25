package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.typesForXml.JaxbPerson;
import model.typesForXml.LocalDateTimeAdapter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table
@XmlRootElement(name = "director")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @XmlElement
    private int id;

    @XmlElement
    @NotBlank
    private String name; //Поле не может быть null, Строка не может быть пустой

    @XmlElement
    @NotNull
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private java.time.LocalDateTime birthday; //Поле не может быть null

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @NotNull
    @XmlElement
    private Location location; //Поле не может быть null


    public void update(JaxbPerson personData) {
        this.name = personData.getName();
        this.birthday = personData.getBirthday();
        this.location.update(personData.getLocation());;
    }
}

