package model.typesForXml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import model.Location;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "location")
public class JaxbLocation {

    @NotNull
    @XmlElement
    private Long x;

    @XmlElement
    private Float y;

    @NotNull
    @XmlElement
    private Float z;

    @NotNull
    @XmlElement
    private String name;

    public Location toLocation() {
        return new Location(0, x, y, z, name);
    }
}
