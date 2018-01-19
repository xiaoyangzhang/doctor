package com.yhyt.health;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import com.github.tobato.fastdfs.FdfsClientConfig;

//解决jmx重复注册bean的问题
@Import(FdfsClientConfig.class)
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
@SpringBootApplication
@EnableDiscoveryClient
//@EnableFeignClients
@MapperScan("com.yhyt.health.mapper")
@EnableTransactionManagement
public class ProviderDoctorApplication {

    private static Logger logger = LoggerFactory.getLogger(ProviderDoctorApplication.class);

    @Bean(name = "loadBalanced")
    @LoadBalanced
    public RestTemplate loadBalancedTemplate() {
    	HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(3000);
        httpRequestFactory.setConnectTimeout(3000);
        httpRequestFactory.setReadTimeout(3000);
        return new RestTemplate(httpRequestFactory);
    }
    
    @Bean(name="restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    public static void main(String[] args) {
        SpringApplication.run(ProviderDoctorApplication.class,args);
        logger.info("provider doctor 启动成功");
    }

//    @Autowired
//    private JsonObjectMapper jsonObjectMapper;
//    @Bean
//    @LoadBalanced
//    RestTemplate restTemplate() {
//        return new RestTemplate(); 
//    }
    /*@Bean
    @Primary
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(jsonObjectMapper);
        return mappingJackson2HttpMessageConverter;
    }*/
}
