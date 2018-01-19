package com.yhyt.health.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
//@ConfigurationProperties(prefix = "url")
public class PathConfiguration {

    @Value("${url.dialogUrl}")
    private String dialogUrl;
    @Value("${url.systemUrl}")
    private String systemUrl;
    @Value("${url.gateWay}")
    private String gateWay;
    @Value("${url.patientUrl}")
    private String patientUrl;
    @Value("${url.newhealthUrl}")
    private String newhealthUrl;


    public String getNewhealthUrl() {
        return newhealthUrl;
    }

    public String getDialogUrl() {
        return dialogUrl;
    }

    public String getSystemUrl() {
        return systemUrl;
    }

    public String getGateWay() {
        return gateWay;
    }

    public String getPatientUrl() {
        return patientUrl;
    }

    @Bean
    public PathConfiguration getPathConfiguration(){
        PathConfiguration pathConfiguration = new PathConfiguration();
        pathConfiguration.dialogUrl=dialogUrl;
        pathConfiguration.gateWay=gateWay;
        pathConfiguration.patientUrl=patientUrl;
        pathConfiguration.systemUrl=systemUrl;
        pathConfiguration.newhealthUrl=newhealthUrl;
        return pathConfiguration;
    }
}
