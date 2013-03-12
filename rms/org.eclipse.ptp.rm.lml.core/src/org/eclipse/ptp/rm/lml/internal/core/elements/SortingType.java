//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.03.12 at 12:49:52 PM CET 
//


package org.eclipse.ptp.rm.lml.internal.core.elements;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sorting_type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="sorting_type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="numeric"/>
 *     &lt;enumeration value="alpha"/>
 *     &lt;enumeration value="date"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "sorting_type")
@XmlEnum
public enum SortingType {

    @XmlEnumValue("numeric")
    NUMERIC("numeric"),
    @XmlEnumValue("alpha")
    ALPHA("alpha"),
    @XmlEnumValue("date")
    DATE("date");
    private final String value;

    SortingType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SortingType fromValue(String v) {
        for (SortingType c: SortingType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
