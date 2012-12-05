
package service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for device complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="device">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="divide_by" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="logo_url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="photo_url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sensor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="watt_total" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "device", propOrder = {
    "divideBy",
    "id",
    "logoUrl",
    "name",
    "photoUrl",
    "sensor",
    "wattTotal"
})
public class Device {

    @XmlElement(name = "divide_by")
    protected int divideBy;
    protected int id;
    @XmlElement(name = "logo_url")
    protected String logoUrl;
    protected String name;
    @XmlElement(name = "photo_url")
    protected String photoUrl;
    protected String sensor;
    @XmlElement(name = "watt_total")
    protected double wattTotal;

    /**
     * Gets the value of the divideBy property.
     * 
     */
    public int getDivideBy() {
        return divideBy;
    }

    /**
     * Sets the value of the divideBy property.
     * 
     */
    public void setDivideBy(int value) {
        this.divideBy = value;
    }

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the logoUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * Sets the value of the logoUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogoUrl(String value) {
        this.logoUrl = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the photoUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * Sets the value of the photoUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhotoUrl(String value) {
        this.photoUrl = value;
    }

    /**
     * Gets the value of the sensor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSensor() {
        return sensor;
    }

    /**
     * Sets the value of the sensor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSensor(String value) {
        this.sensor = value;
    }

    /**
     * Gets the value of the wattTotal property.
     * 
     */
    public double getWattTotal() {
        return wattTotal;
    }

    /**
     * Sets the value of the wattTotal property.
     * 
     */
    public void setWattTotal(double value) {
        this.wattTotal = value;
    }

}
