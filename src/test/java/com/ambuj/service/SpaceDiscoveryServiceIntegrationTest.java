package com.ambuj.service;

import org.openspaces.core.cluster.ClusterInfo;
import org.openspaces.pu.container.standalone.StandaloneProcessingUnitContainerProvider;


public class SpaceDiscoveryServiceIntegrationTest {


    public static void main(String... args) {

        StandaloneProcessingUnitContainerProvider standaloneProcessingUnitContainerProvider =
                new StandaloneProcessingUnitContainerProvider("E:\\project_work\\gs-dashboard\\src\\test\\resources");
        ClusterInfo clusterInfo = new ClusterInfo();
        clusterInfo.setNumberOfInstances(1);
        clusterInfo.setSchema("partitioned-sync2backup");
        clusterInfo.setNumberOfBackups(0);
        clusterInfo.setInstanceId(1);

        standaloneProcessingUnitContainerProvider.addConfigLocation("file:/E:\\project_work\\gs-dashboard\\src\\test\\resources\\pu.xml");
        standaloneProcessingUnitContainerProvider.setClusterInfo(clusterInfo);

        standaloneProcessingUnitContainerProvider.createContainer();
    }
}