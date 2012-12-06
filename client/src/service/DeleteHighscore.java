
package service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DeleteHighscore complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeleteHighscore">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="highscore" type="{http://Service/}highscore" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeleteHighscore", propOrder = {
    "highscore"
})
public class DeleteHighscore {

    protected Highscore highscore;

    /**
     * Gets the value of the highscore property.
     * 
     * @return
     *     possible object is
     *     {@link Highscore }
     *     
     */
    public Highscore getHighscore() {
        return highscore;
    }

    /**
     * Sets the value of the highscore property.
     * 
     * @param value
     *     allowed object is
     *     {@link Highscore }
     *     
     */
    public void setHighscore(Highscore value) {
        this.highscore = value;
    }

}
