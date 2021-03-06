//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.02.26 at 06:17:52 PM IST 
//


package com.notification.sms.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TimeMetrics.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TimeMetrics"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Millisecond"/&gt;
 *     &lt;enumeration value="Second"/&gt;
 *     &lt;enumeration value="Minute"/&gt;
 *     &lt;enumeration value="Hour"/&gt;
 *     &lt;enumeration value="Day"/&gt;
 *     &lt;enumeration value="Week"/&gt;
 *     &lt;enumeration value="Month"/&gt;
 *     &lt;enumeration value="Year"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TimeMetrics", namespace = "http://www.csapi.org/schema/parlayx/common/v2_1")
@XmlEnum
public enum TimeMetrics {

    @XmlEnumValue("Millisecond")
    MILLISECOND("Millisecond"),
    @XmlEnumValue("Second")
    SECOND("Second"),
    @XmlEnumValue("Minute")
    MINUTE("Minute"),
    @XmlEnumValue("Hour")
    HOUR("Hour"),
    @XmlEnumValue("Day")
    DAY("Day"),
    @XmlEnumValue("Week")
    WEEK("Week"),
    @XmlEnumValue("Month")
    MONTH("Month"),
    @XmlEnumValue("Year")
    YEAR("Year");
    private final String value;

    TimeMetrics(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TimeMetrics fromValue(String v) {
        for (TimeMetrics c: TimeMetrics.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
