//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.03.21 at 03:37:10 PM EDT 
//


package org.eclipse.ptp.etfw.jaxb.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BuildToolType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BuildToolType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="global" type="{http://eclipse.org/ptp/etfw}ToolAppType" minOccurs="0"/>
 *         &lt;element name="cc-compiler" type="{http://eclipse.org/ptp/etfw}ToolAppType" minOccurs="0"/>
 *         &lt;element name="cxx-compiler" type="{http://eclipse.org/ptp/etfw}ToolAppType" minOccurs="0"/>
 *         &lt;element name="f90-compiler" type="{http://eclipse.org/ptp/etfw}ToolAppType" minOccurs="0"/>
 *         &lt;element name="all-compilers" type="{http://eclipse.org/ptp/etfw}ToolAppType" minOccurs="0"/>
 *         &lt;element name="upc-compiler" type="{http://eclipse.org/ptp/etfw}ToolAppType" minOccurs="0"/>
 *         &lt;element name="tool-state" type="{http://eclipse.org/ptp/etfw}ToolStateType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="tool-id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="tool-name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="tool-type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="require-true" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="replace-compiler" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="set-success-attribute" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BuildToolType", propOrder = {
    "global",
    "ccCompiler",
    "cxxCompiler",
    "f90Compiler",
    "allCompilers",
    "upcCompiler",
    "toolState"
})
public class BuildToolType {

    protected ToolAppType global;
    @XmlElement(name = "cc-compiler")
    protected ToolAppType ccCompiler;
    @XmlElement(name = "cxx-compiler")
    protected ToolAppType cxxCompiler;
    @XmlElement(name = "f90-compiler")
    protected ToolAppType f90Compiler;
    @XmlElement(name = "all-compilers")
    protected ToolAppType allCompilers;
    @XmlElement(name = "upc-compiler")
    protected ToolAppType upcCompiler;
    @XmlElement(name = "tool-state")
    protected ToolStateType toolState;
    @XmlAttribute(name = "tool-id")
    protected String toolId;
    @XmlAttribute(name = "tool-name")
    protected String toolName;
    @XmlAttribute(name = "tool-type")
    protected String toolType;
    @XmlAttribute(name = "require-true")
    protected String requireTrue;
    @XmlAttribute(name = "replace-compiler")
    protected Boolean replaceCompiler;
    @XmlAttribute(name = "set-success-attribute")
    protected String setSuccessAttribute;

    /**
     * Gets the value of the global property.
     * 
     * @return
     *     possible object is
     *     {@link ToolAppType }
     *     
     */
    public ToolAppType getGlobal() {
        return global;
    }

    /**
     * Sets the value of the global property.
     * 
     * @param value
     *     allowed object is
     *     {@link ToolAppType }
     *     
     */
    public void setGlobal(ToolAppType value) {
        this.global = value;
    }

    /**
     * Gets the value of the ccCompiler property.
     * 
     * @return
     *     possible object is
     *     {@link ToolAppType }
     *     
     */
    public ToolAppType getCcCompiler() {
        return ccCompiler;
    }

    /**
     * Sets the value of the ccCompiler property.
     * 
     * @param value
     *     allowed object is
     *     {@link ToolAppType }
     *     
     */
    public void setCcCompiler(ToolAppType value) {
        this.ccCompiler = value;
    }

    /**
     * Gets the value of the cxxCompiler property.
     * 
     * @return
     *     possible object is
     *     {@link ToolAppType }
     *     
     */
    public ToolAppType getCxxCompiler() {
        return cxxCompiler;
    }

    /**
     * Sets the value of the cxxCompiler property.
     * 
     * @param value
     *     allowed object is
     *     {@link ToolAppType }
     *     
     */
    public void setCxxCompiler(ToolAppType value) {
        this.cxxCompiler = value;
    }

    /**
     * Gets the value of the f90Compiler property.
     * 
     * @return
     *     possible object is
     *     {@link ToolAppType }
     *     
     */
    public ToolAppType getF90Compiler() {
        return f90Compiler;
    }

    /**
     * Sets the value of the f90Compiler property.
     * 
     * @param value
     *     allowed object is
     *     {@link ToolAppType }
     *     
     */
    public void setF90Compiler(ToolAppType value) {
        this.f90Compiler = value;
    }

    /**
     * Gets the value of the allCompilers property.
     * 
     * @return
     *     possible object is
     *     {@link ToolAppType }
     *     
     */
    public ToolAppType getAllCompilers() {
        return allCompilers;
    }

    /**
     * Sets the value of the allCompilers property.
     * 
     * @param value
     *     allowed object is
     *     {@link ToolAppType }
     *     
     */
    public void setAllCompilers(ToolAppType value) {
        this.allCompilers = value;
    }

    /**
     * Gets the value of the upcCompiler property.
     * 
     * @return
     *     possible object is
     *     {@link ToolAppType }
     *     
     */
    public ToolAppType getUpcCompiler() {
        return upcCompiler;
    }

    /**
     * Sets the value of the upcCompiler property.
     * 
     * @param value
     *     allowed object is
     *     {@link ToolAppType }
     *     
     */
    public void setUpcCompiler(ToolAppType value) {
        this.upcCompiler = value;
    }

    /**
     * Gets the value of the toolState property.
     * 
     * @return
     *     possible object is
     *     {@link ToolStateType }
     *     
     */
    public ToolStateType getToolState() {
        return toolState;
    }

    /**
     * Sets the value of the toolState property.
     * 
     * @param value
     *     allowed object is
     *     {@link ToolStateType }
     *     
     */
    public void setToolState(ToolStateType value) {
        this.toolState = value;
    }

    /**
     * Gets the value of the toolId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToolId() {
        return toolId;
    }

    /**
     * Sets the value of the toolId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToolId(String value) {
        this.toolId = value;
    }

    /**
     * Gets the value of the toolName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToolName() {
        return toolName;
    }

    /**
     * Sets the value of the toolName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToolName(String value) {
        this.toolName = value;
    }

    /**
     * Gets the value of the toolType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToolType() {
        return toolType;
    }

    /**
     * Sets the value of the toolType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToolType(String value) {
        this.toolType = value;
    }

    /**
     * Gets the value of the requireTrue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequireTrue() {
        return requireTrue;
    }

    /**
     * Sets the value of the requireTrue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequireTrue(String value) {
        this.requireTrue = value;
    }

    /**
     * Gets the value of the replaceCompiler property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isReplaceCompiler() {
        return replaceCompiler;
    }

    /**
     * Sets the value of the replaceCompiler property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReplaceCompiler(Boolean value) {
        this.replaceCompiler = value;
    }

    /**
     * Gets the value of the setSuccessAttribute property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSetSuccessAttribute() {
        return setSuccessAttribute;
    }

    /**
     * Sets the value of the setSuccessAttribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSetSuccessAttribute(String value) {
        this.setSuccessAttribute = value;
    }

}