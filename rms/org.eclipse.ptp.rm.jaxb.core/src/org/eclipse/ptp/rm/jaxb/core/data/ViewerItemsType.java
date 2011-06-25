//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.5-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.06.24 at 12:50:38 PM CDT 
//

package org.eclipse.ptp.rm.jaxb.core.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for viewer-items-type complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="viewer-items-type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="include" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="exclude" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="state-rule" type="{http://org.eclipse.ptp/rm}control-state-rule-type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="allDiscovered" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="allPredefined" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "viewer-items-type", propOrder = { "include", "exclude", "stateRule" })
public class ViewerItemsType {

	protected List<String> include;
	protected List<String> exclude;
	@XmlElement(name = "state-rule")
	protected List<ControlStateRuleType> stateRule;
	@XmlAttribute
	protected Boolean allDiscovered;
	@XmlAttribute
	protected Boolean allPredefined;

	/**
	 * Gets the value of the exclude property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the exclude property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getExclude().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link String }
	 * 
	 * 
	 */
	public List<String> getExclude() {
		if (exclude == null) {
			exclude = new ArrayList<String>();
		}
		return this.exclude;
	}

	/**
	 * Gets the value of the include property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the include property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getInclude().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link String }
	 * 
	 * 
	 */
	public List<String> getInclude() {
		if (include == null) {
			include = new ArrayList<String>();
		}
		return this.include;
	}

	/**
	 * Gets the value of the stateRule property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the stateRule property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getStateRule().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link ControlStateRuleType }
	 * 
	 * 
	 */
	public List<ControlStateRuleType> getStateRule() {
		if (stateRule == null) {
			stateRule = new ArrayList<ControlStateRuleType>();
		}
		return this.stateRule;
	}

	/**
	 * Gets the value of the allDiscovered property.
	 * 
	 * @return possible object is {@link Boolean }
	 * 
	 */
	public boolean isAllDiscovered() {
		if (allDiscovered == null) {
			return false;
		} else {
			return allDiscovered;
		}
	}

	/**
	 * Gets the value of the allPredefined property.
	 * 
	 * @return possible object is {@link Boolean }
	 * 
	 */
	public boolean isAllPredefined() {
		if (allPredefined == null) {
			return false;
		} else {
			return allPredefined;
		}
	}

	/**
	 * Sets the value of the allDiscovered property.
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 * 
	 */
	public void setAllDiscovered(Boolean value) {
		this.allDiscovered = value;
	}

	/**
	 * Sets the value of the allPredefined property.
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 * 
	 */
	public void setAllPredefined(Boolean value) {
		this.allPredefined = value;
	}

}