package com.bitcoin.clusterbtc.dto;

public class AddressDTO {

    private Long addressId;
    private String addressHash;
    private boolean minerAddress;
    private short addressType;
    private Long clusterId;

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getAddress_hash() {
        return addressHash;
    }

    public void setAddress_hash(String address_hash) {
        this.addressHash = address_hash;
    }

    public Long getCluster_id() {
        return clusterId;
    }

    public void setCluster_id(Long cluster_id) {
        this.clusterId = cluster_id;
    }

    public boolean isMiner_address() {
        return minerAddress;
    }

    public void setMiner_address(boolean miner_address) {
        this.minerAddress = miner_address;
    }

    public short getAddressType() {
        return addressType;
    }

    public void setAddressType(short addressType) {
        this.addressType = addressType;
    }
}
