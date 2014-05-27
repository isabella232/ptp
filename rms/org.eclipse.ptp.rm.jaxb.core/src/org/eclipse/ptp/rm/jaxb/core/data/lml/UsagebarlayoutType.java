//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.02.14 at 08:46:16 AM CET 
//

package org.eclipse.ptp.rm.jaxb.core.data.lml;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * Define special layout-options for a usagebar
 * 
 * 
 * <p>
 * Java class for usagebarlayout_type complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="usagebarlayout_type">
 *   &lt;complexContent>
 *     &lt;extension base="{http://eclipse.org/ptp/lml}componentlayout_type">
 *       &lt;attribute name="scale" default="nodes">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="nodes"/>
 *             &lt;enumeration value="cpus"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="interval" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" default="4" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "usagebarlayout_type")
public class UsagebarlayoutType
		extends ComponentlayoutType
{

	@XmlAttribute(name = "scale")
	protected String scale;
	@XmlAttribute(name = "interval")
	@XmlSchemaType(name = "positiveInteger")
	protected BigInteger interval;

	/**
	 * Gets the value of the scale property.
	 * 
	 * @return
	 *         possible object is {@link String }
	 * 
	 */
	public String getScale() {
		if (scale == null) {
			return "nodes";
		} else {
			return scale;
		}
	}

	/**
	 * Sets the value of the scale property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setScale(String value) {
		this.scale = value;
	}

	/**
	 * Gets the value of the interval property.
	 * 
	 * @return
	 *         possible object is {@link BigInteger }
	 * 
	 */
	public BigInteger getInterval() {
		if (interval == null) {
			return new BigInteger("4");
		} else {
			return interval;
		}
	}

	/**
	 * Sets the value of the interval property.
	 * 
	 * @param value
	 *            allowed object is {@link BigInteger }
	 * 
	 */
	public void setInterval(BigInteger value) {
		this.interval = value;
	}

}