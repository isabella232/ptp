//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.03.12 at 12:49:52 PM CET 
//


package org.eclipse.ptp.rm.lml.internal.core.elements;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *     			A type for collecting request information. LML can be
 *     			used as communication language. The client sends layout
 *     			information and empty data-tags to the server. The
 *     			server returns the corresponding data.
 *     		
 * 
 * <p>Java class for RequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="driver" type="{http://eclipse.org/ptp/schemas}DriverType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="layoutManagement" type="{http://eclipse.org/ptp/schemas}LayoutRequestType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestType", propOrder = {
    "driver",
    "layoutManagement"
})
public class RequestType {

    protected List<DriverType> driver;
    protected LayoutRequestType layoutManagement;

    /**
     * Gets the value of the driver property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the driver property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDriver().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DriverType }
     * 
     * 
     */
    public List<DriverType> getDriver() {
        if (driver == null) {
            driver = new ArrayList<DriverType>();
        }
        return this.driver;
    }

    /**
     * Gets the value of the layoutManagement property.
     * 
     * @return
     *     possible object is
     *     {@link LayoutRequestType }
     *     
     */
    public LayoutRequestType getLayoutManagement() {
        return layoutManagement;
    }

    /**
     * Sets the value of the layoutManagement property.
     * 
     * @param value
     *     allowed object is
     *     {@link LayoutRequestType }
     *     
     */
    public void setLayoutManagement(LayoutRequestType value) {
        this.layoutManagement = value;
    }

}
