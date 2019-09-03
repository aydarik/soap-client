package ru.gumerbaev.soapclient;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * SOAP web service config class.
 */

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    /**
     * Servlet registration
     * @param applicationContext context
     * @return servlet registration bean
     */
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/v1/filesearch/*");
    }

    /**
     * WSDL generation
     * @param fileSearchSchema XSD schema object with specified file location
     * @return WSDL definition bean
     */
    @Bean(name = "filesearch")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema fileSearchSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("FileSearchPort");
        wsdl11Definition.setLocationUri("/v1/filesearch");
        wsdl11Definition.setTargetNamespace("http://gumerbaev.ru/soapclient/filesearch");
        wsdl11Definition.setSchema(fileSearchSchema);
        return wsdl11Definition;
    }

    /**
     * XSD schema location
     * @return XSD schema object
     */
    @Bean
    public XsdSchema fileSearchSchema() {
        return new SimpleXsdSchema(new ClassPathResource("schema/filesearch.xsd"));
    }
}
