/* Generated by camel build tools - do NOT edit this file! */
package org.apache.camel.dataformat.swift.mx;

import javax.annotation.processing.Generated;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.spi.GeneratedPropertyConfigurer;
import org.apache.camel.support.component.PropertyConfigurerSupport;

/**
 * Generated by camel build tools - do NOT edit this file!
 */
@Generated("org.apache.camel.maven.packaging.PackageDataFormatMojo")
@SuppressWarnings("unchecked")
public class SwiftMxDataFormatConfigurer extends PropertyConfigurerSupport implements GeneratedPropertyConfigurer {

    @Override
    public boolean configure(CamelContext camelContext, Object target, String name, Object value, boolean ignoreCase) {
        SwiftMxDataFormat dataformat = (SwiftMxDataFormat) target;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "writeinjson":
        case "writeInJson": dataformat.setWriteInJson(property(camelContext, boolean.class, value)); return true;
        case "readmessageid":
        case "readMessageId": dataformat.setReadMessageId(property(camelContext, com.prowidesoftware.swift.model.MxId.class, value)); return true;
        default: return false;
        }
    }

}

