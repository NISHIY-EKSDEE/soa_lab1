package util;

//import model.typesForXml.MoviesResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;


public class Jaxb {

    public static <T> T fromStr(String str, Class<T> tClass) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(tClass);

        Unmarshaller unmarshaller = jc.createUnmarshaller();

        return (T) unmarshaller.unmarshal(new StringReader(str));
    }
}

