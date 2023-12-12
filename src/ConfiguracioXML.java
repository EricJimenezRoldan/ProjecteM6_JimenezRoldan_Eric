import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ConfiguracioXML {
    private String rutaFitxerCSV;
    private String rutaSortida;
    private int limitRegistres;

    public ConfiguracioXML(String rutaFitxerXML) {
        llegirConfiguracio(rutaFitxerXML);
    }

    public void llegirConfiguracio(String rutaFitxerXML) {
        try {
            File arxiuXML = new File(rutaFitxerXML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(arxiuXML);
            doc.getDocumentElement().normalize();

            Element configuracio = (Element) doc.getElementsByTagName("configuracio").item(0);

            rutaFitxerCSV = getConfiguracioElement(configuracio, "rutaFitxerCSV");
            rutaSortida = getConfiguracioElement(configuracio, "rutaSortida");
            limitRegistres = Integer.parseInt(getConfiguracioElement(configuracio, "limitRegistres"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getConfiguracioElement(Element configuracio, String elementTag) {
        NodeList nodeList = configuracio.getElementsByTagName(elementTag);
        Element element = (Element) nodeList.item(0);
        return element.getTextContent();
    }

    public String getRutaFitxerCSV() {
        return rutaFitxerCSV;
    }

    public String getRutaSortida() {
        return rutaSortida;
    }

    public int getLimitRegistres() {
        return limitRegistres;
    }
}
