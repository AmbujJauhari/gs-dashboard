package com.ambuj.service;

import com.ambuj.domain.GsLookUpDetails;
import com.ambuj.util.ResourceLoadUtil;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SpaceDiscoveryService {
    public static final String ENVCONFIG_DIR = "envconfig/";

    @Autowired
    private ResourceLoadUtil resourceLoadUtil;

    private List<GsLookUpDetails> lookUpDetails = new ArrayList<>();

    Map<String, GigaSpace> envProxyMap = new HashMap<>();

    @PostConstruct
    public void getAllConfigs() {
        try {
            Resource[] confFiles = resourceLoadUtil.loadResources("classpath*:" + ENVCONFIG_DIR + "*.conf");
            for (Resource resource : confFiles) {
                String fileName = resource.getFilename();
                String baseName = fileName.substring(0, fileName.indexOf("."));
                Config config = ConfigFactory.load(ENVCONFIG_DIR + baseName);
                lookUpDetails.add(new GsLookUpDetails.GsLookUpDetailsBuilder().buildWithConfig(config));
            }

            for (GsLookUpDetails gsLookUpDetails : lookUpDetails) {
                if(gsLookUpDetails.isSecured()) {
                    UrlSpaceConfigurer urlSpaceConfigurer = new UrlSpaceConfigurer(gsLookUpDetails.getUrl())
                            .credentials(gsLookUpDetails.getUserName(), gsLookUpDetails.getPassword());
                    envProxyMap.put(gsLookUpDetails.getEnvName(), new GigaSpaceConfigurer(urlSpaceConfigurer).gigaSpace());
                } else {
                    UrlSpaceConfigurer urlSpaceConfigurer = new UrlSpaceConfigurer(gsLookUpDetails.getUrl());
                    envProxyMap.put(gsLookUpDetails.getEnvName(), new GigaSpaceConfigurer(urlSpaceConfigurer).gigaSpace());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public GigaSpace getSpace(String envName) {
        return envProxyMap.get(envName);
    }

    public List<GsLookUpDetails> gsLookUpDetails() {
        return lookUpDetails;
    }

}
