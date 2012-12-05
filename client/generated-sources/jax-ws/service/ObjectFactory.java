
package service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AddHighscore_QNAME = new QName("http://Service/", "AddHighscore");
    private final static QName _DeleteHighscore_QNAME = new QName("http://Service/", "DeleteHighscore");
    private final static QName _GetLimitedHighscores_QNAME = new QName("http://Service/", "getLimitedHighscores");
    private final static QName _GetLimitedHighscoresResponse_QNAME = new QName("http://Service/", "getLimitedHighscoresResponse");
    private final static QName _AddHighscoreResponse_QNAME = new QName("http://Service/", "AddHighscoreResponse");
    private final static QName _GetAllHighscores_QNAME = new QName("http://Service/", "getAllHighscores");
    private final static QName _GetAllHighscoresResponse_QNAME = new QName("http://Service/", "getAllHighscoresResponse");
    private final static QName _DeleteHighscoreResponse_QNAME = new QName("http://Service/", "DeleteHighscoreResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetLimitedHighscores }
     * 
     */
    public GetLimitedHighscores createGetLimitedHighscores() {
        return new GetLimitedHighscores();
    }

    /**
     * Create an instance of {@link DeleteHighscore }
     * 
     */
    public DeleteHighscore createDeleteHighscore() {
        return new DeleteHighscore();
    }

    /**
     * Create an instance of {@link AddHighscore }
     * 
     */
    public AddHighscore createAddHighscore() {
        return new AddHighscore();
    }

    /**
     * Create an instance of {@link GetAllHighscoresResponse }
     * 
     */
    public GetAllHighscoresResponse createGetAllHighscoresResponse() {
        return new GetAllHighscoresResponse();
    }

    /**
     * Create an instance of {@link DeleteHighscoreResponse }
     * 
     */
    public DeleteHighscoreResponse createDeleteHighscoreResponse() {
        return new DeleteHighscoreResponse();
    }

    /**
     * Create an instance of {@link GetAllHighscores }
     * 
     */
    public GetAllHighscores createGetAllHighscores() {
        return new GetAllHighscores();
    }

    /**
     * Create an instance of {@link AddHighscoreResponse }
     * 
     */
    public AddHighscoreResponse createAddHighscoreResponse() {
        return new AddHighscoreResponse();
    }

    /**
     * Create an instance of {@link GetLimitedHighscoresResponse }
     * 
     */
    public GetLimitedHighscoresResponse createGetLimitedHighscoresResponse() {
        return new GetLimitedHighscoresResponse();
    }

    /**
     * Create an instance of {@link Highscore }
     * 
     */
    public Highscore createHighscore() {
        return new Highscore();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddHighscore }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "AddHighscore")
    public JAXBElement<AddHighscore> createAddHighscore(AddHighscore value) {
        return new JAXBElement<AddHighscore>(_AddHighscore_QNAME, AddHighscore.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteHighscore }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "DeleteHighscore")
    public JAXBElement<DeleteHighscore> createDeleteHighscore(DeleteHighscore value) {
        return new JAXBElement<DeleteHighscore>(_DeleteHighscore_QNAME, DeleteHighscore.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLimitedHighscores }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "getLimitedHighscores")
    public JAXBElement<GetLimitedHighscores> createGetLimitedHighscores(GetLimitedHighscores value) {
        return new JAXBElement<GetLimitedHighscores>(_GetLimitedHighscores_QNAME, GetLimitedHighscores.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLimitedHighscoresResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "getLimitedHighscoresResponse")
    public JAXBElement<GetLimitedHighscoresResponse> createGetLimitedHighscoresResponse(GetLimitedHighscoresResponse value) {
        return new JAXBElement<GetLimitedHighscoresResponse>(_GetLimitedHighscoresResponse_QNAME, GetLimitedHighscoresResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddHighscoreResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "AddHighscoreResponse")
    public JAXBElement<AddHighscoreResponse> createAddHighscoreResponse(AddHighscoreResponse value) {
        return new JAXBElement<AddHighscoreResponse>(_AddHighscoreResponse_QNAME, AddHighscoreResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllHighscores }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "getAllHighscores")
    public JAXBElement<GetAllHighscores> createGetAllHighscores(GetAllHighscores value) {
        return new JAXBElement<GetAllHighscores>(_GetAllHighscores_QNAME, GetAllHighscores.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllHighscoresResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "getAllHighscoresResponse")
    public JAXBElement<GetAllHighscoresResponse> createGetAllHighscoresResponse(GetAllHighscoresResponse value) {
        return new JAXBElement<GetAllHighscoresResponse>(_GetAllHighscoresResponse_QNAME, GetAllHighscoresResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteHighscoreResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "DeleteHighscoreResponse")
    public JAXBElement<DeleteHighscoreResponse> createDeleteHighscoreResponse(DeleteHighscoreResponse value) {
        return new JAXBElement<DeleteHighscoreResponse>(_DeleteHighscoreResponse_QNAME, DeleteHighscoreResponse.class, null, value);
    }

}
