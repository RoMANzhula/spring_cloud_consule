package org.romanzhula.bookstoreorderservice.configurations;


import com.orbitz.consul.Consul;
import com.orbitz.consul.KeyValueClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cloud.consul.config.ConsulConfigProperties;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


@Configuration
public class ConsulConfig {

    // get aclToken from Environment
    @Autowired
    Environment env;

    public String getAclToken() {
        return env.getProperty("acl-token");
    }

    // get aclToken from properties-file
    @Value("${spring.cloud.vault.consul.acl-token}")
    private String aclToken;


    @Bean
    public BeanPostProcessor consulConfigPropertiesPostProcessor() {
        // add to Consul properties acl token from Vault
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) {
                // check if we have running Consul config section in properties file
                if (bean instanceof ConsulConfigProperties) {
                    ConsulConfigProperties consulConfigProperties = (ConsulConfigProperties) bean;
                    // add acl-token from Vault
                    consulConfigProperties.setAclToken(aclToken);

                    if (consulConfigProperties.getAclToken() == null || consulConfigProperties.getAclToken().isEmpty()) {
                        throw new IllegalArgumentException("ACL Token is not set in application.properties!");
                    }
                }

                // check if we have running Consul discovery section in properties file
                if (bean instanceof ConsulDiscoveryProperties) {
                    ConsulDiscoveryProperties consulDiscoveryProperties = (ConsulDiscoveryProperties) bean;
                    // add acl-token from Vault
                    consulDiscoveryProperties.setAclToken(aclToken);

                    if (consulDiscoveryProperties.getAclToken() == null || consulDiscoveryProperties.getAclToken().isEmpty()) {
                        throw new IllegalArgumentException("ACL Token is not set in application.properties!");
                    }
                }

                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) {
                return bean;
            }
        };
    }

            // Methods for extracting DataSource data from Consul for adding them to connection DB after app run


    public String getDbUrl() {
        Consul consul = Consul.builder()
                .withAclToken(getAclToken())  // for auth
                .build();
        KeyValueClient kvClient = consul.keyValueClient();

        // get url by endpoints
        String url = kvClient.getValueAsString("config/order-service,default/spring.datasource.url")
                .orElseThrow(() -> new RuntimeException("Datasource URL not found in Consul"));
        System.out.println("-----------------" + url);

        return url;
    }

    public String getDbUsername() {
        Consul consul = Consul.builder()
                .withAclToken(getAclToken())
                .build();
        KeyValueClient kvClient = consul.keyValueClient();

        String username = kvClient.getValueAsString("config/order-service,default/spring.datasource.username")
                .orElseThrow(() -> new RuntimeException("Datasource USERNAME not found in Consul"));
        System.out.println("-----------------" + username);

        return username;
    }

    public String getDbPassword() {
        Consul consul = Consul.builder()
                .withAclToken(getAclToken())
                .build();
        KeyValueClient kvClient = consul.keyValueClient();

        String password = kvClient.getValueAsString("config/order-service,default/spring.datasource.password")
                .orElseThrow(() -> new RuntimeException("Datasource PASSWORD not found in Consul"));
        System.out.println("-----------------" + password);

        return password;
    }

}
