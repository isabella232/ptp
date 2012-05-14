//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.14 at 03:08:09 PM EDT 
//


package org.eclipse.ptp.rm.jaxb.core.data;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for managed-file-type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="managed-file-type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;sequence>
 *           &lt;element name="line" type="{http://org.eclipse.ptp/rm}line-type" maxOccurs="unbounded"/>
 *         &lt;/sequence>
 *         &lt;element name="contents" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/choice>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="uniqueIdPrefix" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="resolveContents" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="deleteSourceAfterUse" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="deleteTargetAfterUse" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="mode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="flags" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "managed-file-type", propOrder = {
    "line",
    "contents",
    "path"
})
public class ManagedFileType {

    protected List<LineType> line;
    protected String contents;
    protected String path;
    @XmlAttribute(required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String name;
    @XmlAttribute
    protected Boolean uniqueIdPrefix;
    @XmlAttribute
    protected Boolean resolveContents;
    @XmlAttribute
    protected Boolean deleteSourceAfterUse;
    @XmlAttribute
    protected Boolean deleteTargetAfterUse;
    @XmlAttribute
    protected String mode;
    @XmlAttribute
    protected String flags;

    /**
     * Gets the value of the line property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the line property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LineType }
     * 
     * 
     */
    public List<LineType> getLine() {
        if (line == null) {
            line = new ArrayList<LineType>();
        }
        return this.line;
    }

    /**
     * Gets the value of the contents property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContents() {
        return contents;
    }

    /**
     * Sets the value of the contents property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContents(String value) {
        this.contents = value;
    }

    /**
     * Gets the value of the path property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPath(String value) {
        this.path = value;
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
     * Gets the value of the uniqueIdPrefix property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isUniqueIdPrefix() {
        if (uniqueIdPrefix == null) {
            return false;
        } else {
            return uniqueIdPrefix;
        }
    }

    /**
     * Sets the value of the uniqueIdPrefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUniqueIdPrefix(Boolean value) {
        this.uniqueIdPrefix = value;
    }

    /**
     * Gets the value of the resolveContents property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isResolveContents() {
        if (resolveContents == null) {
            return true;
        } else {
            return resolveContents;
        }
    }

    /**
     * Sets the value of the resolveContents property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setResolveContents(Boolean value) {
        this.resolveContents = value;
    }

    /**
     * Gets the value of the deleteSourceAfterUse property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isDeleteSourceAfterUse() {
        if (deleteSourceAfterUse == null) {
            return false;
        } else {
            return deleteSourceAfterUse;
        }
    }

    /**
     * Sets the value of the deleteSourceAfterUse property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDeleteSourceAfterUse(Boolean value) {
        this.deleteSourceAfterUse = value;
    }

    /**
     * Gets the value of the deleteTargetAfterUse property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isDeleteTargetAfterUse() {
        if (deleteTargetAfterUse == null) {
            return false;
        } else {
            return deleteTargetAfterUse;
        }
    }

    /**
     * Sets the value of the deleteTargetAfterUse property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDeleteTargetAfterUse(Boolean value) {
        this.deleteTargetAfterUse = value;
    }

    /**
     * Gets the value of the mode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMode() {
        return mode;
    }

    /**
     * Sets the value of the mode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMode(String value) {
        this.mode = value;
    }

    /**
     * Gets the value of the flags property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlags() {
        return flags;
    }

    /**
     * Sets the value of the flags property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlags(String value) {
        this.flags = value;
    }

}
