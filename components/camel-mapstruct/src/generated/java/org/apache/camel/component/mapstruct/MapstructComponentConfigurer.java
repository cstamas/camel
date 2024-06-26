/* Generated by camel build tools - do NOT edit this file! */
package org.apache.camel.component.mapstruct;

import javax.annotation.processing.Generated;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.spi.ExtendedPropertyConfigurerGetter;
import org.apache.camel.spi.PropertyConfigurerGetter;
import org.apache.camel.spi.ConfigurerStrategy;
import org.apache.camel.spi.GeneratedPropertyConfigurer;
import org.apache.camel.util.CaseInsensitiveMap;
import org.apache.camel.support.component.PropertyConfigurerSupport;

/**
 * Generated by camel build tools - do NOT edit this file!
 */
@Generated("org.apache.camel.maven.packaging.EndpointSchemaGeneratorMojo")
@SuppressWarnings("unchecked")
public class MapstructComponentConfigurer extends PropertyConfigurerSupport implements GeneratedPropertyConfigurer, PropertyConfigurerGetter {

    @Override
    public boolean configure(CamelContext camelContext, Object obj, String name, Object value, boolean ignoreCase) {
        MapstructComponent target = (MapstructComponent) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "autowiredenabled":
        case "autowiredEnabled": target.setAutowiredEnabled(property(camelContext, boolean.class, value)); return true;
        case "lazystartproducer":
        case "lazyStartProducer": target.setLazyStartProducer(property(camelContext, boolean.class, value)); return true;
        case "mapstructconverter":
        case "mapStructConverter": target.setMapStructConverter(property(camelContext, org.apache.camel.component.mapstruct.MapStructMapperFinder.class, value)); return true;
        case "mapperpackagename":
        case "mapperPackageName": target.setMapperPackageName(property(camelContext, java.lang.String.class, value)); return true;
        default: return false;
        }
    }

    @Override
    public String[] getAutowiredNames() {
        return new String[]{"mapStructConverter"};
    }

    @Override
    public Class<?> getOptionType(String name, boolean ignoreCase) {
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "autowiredenabled":
        case "autowiredEnabled": return boolean.class;
        case "lazystartproducer":
        case "lazyStartProducer": return boolean.class;
        case "mapstructconverter":
        case "mapStructConverter": return org.apache.camel.component.mapstruct.MapStructMapperFinder.class;
        case "mapperpackagename":
        case "mapperPackageName": return java.lang.String.class;
        default: return null;
        }
    }

    @Override
    public Object getOptionValue(Object obj, String name, boolean ignoreCase) {
        MapstructComponent target = (MapstructComponent) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "autowiredenabled":
        case "autowiredEnabled": return target.isAutowiredEnabled();
        case "lazystartproducer":
        case "lazyStartProducer": return target.isLazyStartProducer();
        case "mapstructconverter":
        case "mapStructConverter": return target.getMapStructConverter();
        case "mapperpackagename":
        case "mapperPackageName": return target.getMapperPackageName();
        default: return null;
        }
    }
}

