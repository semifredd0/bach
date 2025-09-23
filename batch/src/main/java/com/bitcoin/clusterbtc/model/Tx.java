package com.bitcoin.clusterbtc.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tx {

    private String hash;
    private Long ver;
    private Long vin_sz;
    private Long vout_sz;
    private Long size;
    private Long weight;
    private Long fee;
    private String relayed_by;
    private Long lock_time;
    private Long tx_index;
    private Boolean double_spend;
    private Long time;
    private Long block_index;
    private Long block_height;
    private List<com.bitcoin.clusterbtc.model.Input> inputs = null;
    private List<com.bitcoin.clusterbtc.model.Out> out = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Long getVer() {
        return ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }

    public Long getVin_sz() {
        return vin_sz;
    }

    public void setVin_sz(Long vin_sz) {
        this.vin_sz = vin_sz;
    }

    public Long getVout_sz() {
        return vout_sz;
    }

    public void setVout_sz(Long vout_sz) {
        this.vout_sz = vout_sz;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public String getRelayed_by() {
        return relayed_by;
    }

    public void setRelayed_by(String relayed_by) {
        this.relayed_by = relayed_by;
    }

    public Long getLock_time() {
        return lock_time;
    }

    public void setLock_time(Long lock_time) {
        this.lock_time = lock_time;
    }

    public Long getTx_index() {
        return tx_index;
    }

    public void setTx_index(Long tx_index) {
        this.tx_index = tx_index;
    }

    public Boolean getDouble_spend() {
        return double_spend;
    }

    public void setDouble_spend(Boolean double_spend) {
        this.double_spend = double_spend;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getBlock_index() {
        return block_index;
    }

    public void setBlock_index(Long block_index) {
        this.block_index = block_index;
    }

    public Long getBlock_height() {
        return block_height;
    }

    public void setBlock_height(Long block_height) {
        this.block_height = block_height;
    }

    public List<com.bitcoin.clusterbtc.model.Input> getInputs() {
        return inputs;
    }

    public void setInputs(List<com.bitcoin.clusterbtc.model.Input> inputs) {
        this.inputs = inputs;
    }

    public List<com.bitcoin.clusterbtc.model.Out> getOut() {
        return out;
    }

    public void setOut(List<com.bitcoin.clusterbtc.model.Out> out) {
        this.out = out;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Tx.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("hash");
        sb.append('=');
        sb.append(((this.hash == null)?"<null>":this.hash));
        sb.append(',');
        sb.append("ver");
        sb.append('=');
        sb.append(((this.ver == null)?"<null>":this.ver));
        sb.append(',');
        sb.append("vin_sz");
        sb.append('=');
        sb.append(((this.vin_sz == null)?"<null>":this.vin_sz));
        sb.append(',');
        sb.append("vout_sz");
        sb.append('=');
        sb.append(((this.vout_sz == null)?"<null>":this.vout_sz));
        sb.append(',');
        sb.append("size");
        sb.append('=');
        sb.append(((this.size == null)?"<null>":this.size));
        sb.append(',');
        sb.append("weight");
        sb.append('=');
        sb.append(((this.weight == null)?"<null>":this.weight));
        sb.append(',');
        sb.append("fee");
        sb.append('=');
        sb.append(((this.fee == null)?"<null>":this.fee));
        sb.append(',');
        sb.append("relayed_by");
        sb.append('=');
        sb.append(((this.relayed_by == null)?"<null>":this.relayed_by));
        sb.append(',');
        sb.append("lock_time");
        sb.append('=');
        sb.append(((this.lock_time == null)?"<null>":this.lock_time));
        sb.append(',');
        sb.append("tx_index");
        sb.append('=');
        sb.append(((this.tx_index == null)?"<null>":this.tx_index));
        sb.append(',');
        sb.append("double_spend");
        sb.append('=');
        sb.append(((this.double_spend == null)?"<null>":this.double_spend));
        sb.append(',');
        sb.append("time");
        sb.append('=');
        sb.append(((this.time == null)?"<null>":this.time));
        sb.append(',');
        sb.append("block_index");
        sb.append('=');
        sb.append(((this.block_index == null)?"<null>":this.block_index));
        sb.append(',');
        sb.append("block_height");
        sb.append('=');
        sb.append(((this.block_height == null)?"<null>":this.block_height));
        sb.append(',');
        sb.append("inputs");
        sb.append('=');
        sb.append(((this.inputs == null)?"<null>":this.inputs));
        sb.append(',');
        sb.append("out");
        sb.append('=');
        sb.append(((this.out == null)?"<null>":this.out));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }
}
