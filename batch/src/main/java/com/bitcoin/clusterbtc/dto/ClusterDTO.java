package com.bitcoin.clusterbtc.dto;

public class ClusterDTO {

    private Long cluster_id;
    private String name;

    public ClusterDTO() {}

    public Long getCluster_id() {
        return cluster_id;
    }

    public void setCluster_id(Long cluster_id) {
        this.cluster_id = cluster_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
