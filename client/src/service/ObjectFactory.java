
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

    private final static QName _GetLiveDataResponse_QNAME = new QName("http://Service/", "getLiveDataResponse");
    private final static QName _UpdateDeviceResponse_QNAME = new QName("http://Service/", "updateDeviceResponse");
    private final static QName _GetDeviceById_QNAME = new QName("http://Service/", "getDeviceById");
    private final static QName _UpdateWattTotal_QNAME = new QName("http://Service/", "updateWattTotal");
    private final static QName _UpdateDevice_QNAME = new QName("http://Service/", "updateDevice");
    private final static QName _GetAllDevicesResponse_QNAME = new QName("http://Service/", "getAllDevicesResponse");
    private final static QName _GetDeviceByIdResponse_QNAME = new QName("http://Service/", "getDeviceByIdResponse");
    private final static QName _RemoveDeviceResponse_QNAME = new QName("http://Service/", "removeDeviceResponse");
    private final static QName _AddDeviceResponse_QNAME = new QName("http://Service/", "addDeviceResponse");
    private final static QName _AddDevice_QNAME = new QName("http://Service/", "addDevice");
    private final static QName _UpdateWattTotalResponse_QNAME = new QName("http://Service/", "updateWattTotalResponse");
    private final static QName _GetLiveData_QNAME = new QName("http://Service/", "getLiveData");
    private final static QName _RemoveDevice_QNAME = new QName("http://Service/", "removeDevice");
    private final static QName _GetAllDevices_QNAME = new QName("http://Service/", "getAllDevices");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateWattTotalResponse }
     * 
     */
    public UpdateWattTotalResponse createUpdateWattTotalResponse() {
        return new UpdateWattTotalResponse();
    }

    /**
     * Create an instance of {@link AddDevice }
     * 
     */
    public AddDevice createAddDevice() {
        return new AddDevice();
    }

    /**
     * Create an instance of {@link GetAllDevices }
     * 
     */
    public GetAllDevices createGetAllDevices() {
        return new GetAllDevices();
    }

    /**
     * Create an instance of {@link RemoveDevice }
     * 
     */
    public RemoveDevice createRemoveDevice() {
        return new RemoveDevice();
    }

    /**
     * Create an instance of {@link GetLiveData }
     * 
     */
    public GetLiveData createGetLiveData() {
        return new GetLiveData();
    }

    /**
     * Create an instance of {@link GetDeviceById }
     * 
     */
    public GetDeviceById createGetDeviceById() {
        return new GetDeviceById();
    }

    /**
     * Create an instance of {@link UpdateDeviceResponse }
     * 
     */
    public UpdateDeviceResponse createUpdateDeviceResponse() {
        return new UpdateDeviceResponse();
    }

    /**
     * Create an instance of {@link GetLiveDataResponse }
     * 
     */
    public GetLiveDataResponse createGetLiveDataResponse() {
        return new GetLiveDataResponse();
    }

    /**
     * Create an instance of {@link GetDeviceByIdResponse }
     * 
     */
    public GetDeviceByIdResponse createGetDeviceByIdResponse() {
        return new GetDeviceByIdResponse();
    }

    /**
     * Create an instance of {@link RemoveDeviceResponse }
     * 
     */
    public RemoveDeviceResponse createRemoveDeviceResponse() {
        return new RemoveDeviceResponse();
    }

    /**
     * Create an instance of {@link AddDeviceResponse }
     * 
     */
    public AddDeviceResponse createAddDeviceResponse() {
        return new AddDeviceResponse();
    }

    /**
     * Create an instance of {@link GetAllDevicesResponse }
     * 
     */
    public GetAllDevicesResponse createGetAllDevicesResponse() {
        return new GetAllDevicesResponse();
    }

    /**
     * Create an instance of {@link UpdateDevice }
     * 
     */
    public UpdateDevice createUpdateDevice() {
        return new UpdateDevice();
    }

    /**
     * Create an instance of {@link UpdateWattTotal }
     * 
     */
    public UpdateWattTotal createUpdateWattTotal() {
        return new UpdateWattTotal();
    }

    /**
     * Create an instance of {@link Device }
     * 
     */
    public Device createDevice() {
        return new Device();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLiveDataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "getLiveDataResponse")
    public JAXBElement<GetLiveDataResponse> createGetLiveDataResponse(GetLiveDataResponse value) {
        return new JAXBElement<GetLiveDataResponse>(_GetLiveDataResponse_QNAME, GetLiveDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDeviceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "updateDeviceResponse")
    public JAXBElement<UpdateDeviceResponse> createUpdateDeviceResponse(UpdateDeviceResponse value) {
        return new JAXBElement<UpdateDeviceResponse>(_UpdateDeviceResponse_QNAME, UpdateDeviceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDeviceById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "getDeviceById")
    public JAXBElement<GetDeviceById> createGetDeviceById(GetDeviceById value) {
        return new JAXBElement<GetDeviceById>(_GetDeviceById_QNAME, GetDeviceById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateWattTotal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "updateWattTotal")
    public JAXBElement<UpdateWattTotal> createUpdateWattTotal(UpdateWattTotal value) {
        return new JAXBElement<UpdateWattTotal>(_UpdateWattTotal_QNAME, UpdateWattTotal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDevice }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "updateDevice")
    public JAXBElement<UpdateDevice> createUpdateDevice(UpdateDevice value) {
        return new JAXBElement<UpdateDevice>(_UpdateDevice_QNAME, UpdateDevice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllDevicesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "getAllDevicesResponse")
    public JAXBElement<GetAllDevicesResponse> createGetAllDevicesResponse(GetAllDevicesResponse value) {
        return new JAXBElement<GetAllDevicesResponse>(_GetAllDevicesResponse_QNAME, GetAllDevicesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDeviceByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "getDeviceByIdResponse")
    public JAXBElement<GetDeviceByIdResponse> createGetDeviceByIdResponse(GetDeviceByIdResponse value) {
        return new JAXBElement<GetDeviceByIdResponse>(_GetDeviceByIdResponse_QNAME, GetDeviceByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveDeviceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "removeDeviceResponse")
    public JAXBElement<RemoveDeviceResponse> createRemoveDeviceResponse(RemoveDeviceResponse value) {
        return new JAXBElement<RemoveDeviceResponse>(_RemoveDeviceResponse_QNAME, RemoveDeviceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddDeviceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "addDeviceResponse")
    public JAXBElement<AddDeviceResponse> createAddDeviceResponse(AddDeviceResponse value) {
        return new JAXBElement<AddDeviceResponse>(_AddDeviceResponse_QNAME, AddDeviceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddDevice }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "addDevice")
    public JAXBElement<AddDevice> createAddDevice(AddDevice value) {
        return new JAXBElement<AddDevice>(_AddDevice_QNAME, AddDevice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateWattTotalResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "updateWattTotalResponse")
    public JAXBElement<UpdateWattTotalResponse> createUpdateWattTotalResponse(UpdateWattTotalResponse value) {
        return new JAXBElement<UpdateWattTotalResponse>(_UpdateWattTotalResponse_QNAME, UpdateWattTotalResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLiveData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "getLiveData")
    public JAXBElement<GetLiveData> createGetLiveData(GetLiveData value) {
        return new JAXBElement<GetLiveData>(_GetLiveData_QNAME, GetLiveData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveDevice }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "removeDevice")
    public JAXBElement<RemoveDevice> createRemoveDevice(RemoveDevice value) {
        return new JAXBElement<RemoveDevice>(_RemoveDevice_QNAME, RemoveDevice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllDevices }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "getAllDevices")
    public JAXBElement<GetAllDevices> createGetAllDevices(GetAllDevices value) {
        return new JAXBElement<GetAllDevices>(_GetAllDevices_QNAME, GetAllDevices.class, null, value);
    }

}
