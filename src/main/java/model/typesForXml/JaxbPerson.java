package model.typesForXml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import model.Location;
import model.Person;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "director")
public class JaxbPerson {
    @XmlElement
    private String name; //Поле не может быть null, Строка не может быть пустой

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private java.time.LocalDateTime birthday; //Поле не может быть null

    @NotNull
    @XmlElement
    private JaxbLocation location = new JaxbLocation(0L, 0F, 0f, "Hollywood"); //Поле не может быть null

    public Person toHuman() {
        return new Person(0, name, birthday, location.toLocation());
    }
}
