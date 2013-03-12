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
 *     			Collects data of a chart describing a histogram or other
 *     			diagrams.
 *     		
 * 
 * <p>Java class for chart_type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="chart_type">
 *   &lt;complexContent>
 *     &lt;extension base="{http://eclipse.org/ptp/schemas}gobject_type">
 *       &lt;sequence>
 *         &lt;element name="axes" type="{http://eclipse.org/ptp/schemas}axes_type" minOccurs="0"/>
 *         &lt;element name="data" type="{http://eclipse.org/ptp/schemas}datacollection_type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "chart_type", propOrder = {
    "axes",
    "data"
})
public class ChartType
    extends GobjectType
{

    protected AxesType axes;
    protected List<DatacollectionType> data;

    /**
     * Gets the value of the axes property.
     * 
     * @return
     *     possible object is
     *     {@link AxesType }
     *     
     */
    public AxesType getAxes() {
        return axes;
    }

    /**
     * Sets the value of the axes property.
     * 
     * @param value
     *     allowed object is
     *     {@link AxesType }
     *     
     */
    public void setAxes(AxesType value) {
        this.axes = value;
    }

    /**
     * Gets the value of the data property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the data property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatacollectionType }
     * 
     * 
     */
    public List<DatacollectionType> getData() {
        if (data == null) {
            data = new ArrayList<DatacollectionType>();
        }
        return this.data;
    }

}
