
package service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updateWattTotal complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateWattTotal">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="deviceId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="watt" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateWattTotal", propOrder = {
    "deviceId",
    "watt"
})
public class UpdateWattTotal {

    protected int deviceId;
    protected double watt;

    /**
     * Gets the value of the deviceId property.
     * 
     */
    public int getDeviceId() {
        return deviceId;
    }

    /**
     * Sets the value of the deviceId property.
     * 
     */
    public void setDeviceId(int value) {
        this.deviceId = value;
    }

    /**
     * Gets the value of the watt property.
     * 
     */
    public double getWatt() {
        return watt;
    }

    /**
     * Sets the value of the watt property.
     * 
     */
    public void setWatt(double value) {
        this.watt = value;
    }

}
