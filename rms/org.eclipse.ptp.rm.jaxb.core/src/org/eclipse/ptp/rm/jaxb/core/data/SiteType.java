//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.14 at 03:08:09 PM EDT 
//


package org.eclipse.ptp.rm.jaxb.core.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for site-type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="site-type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="control-connection" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="monitor-connection" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "site-type", propOrder = {
    "controlConnection",
    "monitorConnection"
})
public class SiteType {

    @XmlElement(name = "control-connection")
    protected String controlConnection;
    @XmlElement(name = "monitor-connection")
    protected String monitorConnection;

    /**
     * Gets the value of the controlConnection property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getControlConnection() {
        return controlConnection;
    }

    /**
     * Sets the value of the controlConnection property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setControlConnection(String value) {
        this.controlConnection = value;
    }

    /**
     * Gets the value of the monitorConnection property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMonitorConnection() {
        return monitorConnection;
    }

    /**
     * Sets the value of the monitorConnection property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMonitorConnection(String value) {
        this.monitorConnection = value;
    }

}
