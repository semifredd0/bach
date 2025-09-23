package com.bitcoin.clusterbtc.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Block {

    private String hash;
    private Long ver;
    private String prev_block;
    private String mrkl_root;
    private Long time;
    private Long bits;
    private List<String> next_block = null;
    private Long fee;
    private Long nonce;
    private Long n_tx;
    private Long size;
    private Long block_index;
    private Boolean main_chain;
    private Long height;
    private Long weight;
    private List<com.bitcoin.clusterbtc.model.Tx> tx = null;
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

    public String getPrev_block() {
        return prev_block;
    }

    public void setPrev_block(String prev_block) {
        this.prev_block = prev_block;
    }

    public String getMrkl_root() {
        return mrkl_root;
    }

    public void setMrkl_root(String mrkl_root) {
        this.mrkl_root = mrkl_root;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getBits() {
        return bits;
    }

    public void setBits(Long bits) {
        this.bits = bits;
    }

    public List<String> getNext_block() {
        return next_block;
    }

    public void setNext_block(List<String> next_block) {
        this.next_block = next_block;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public Long getNonce() {
        return nonce;
    }

    public void setNonce(Long nonce) {
        this.nonce = nonce;
    }

    public Long getN_tx() {
        return n_tx;
    }

    public void setN_tx(Long n_tx) {
        this.n_tx = n_tx;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getBlock_index() {
        return block_index;
    }

    public void setBlock_index(Long block_index) {
        this.block_index = block_index;
    }

    public Boolean getMain_chain() {
        return main_chain;
    }

    public void setMain_chain(Boolean main_chain) {
        this.main_chain = main_chain;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public List<Tx> getTx() {
        return tx;
    }

    public void setTx(List<Tx> tx) {
        this.tx = tx;
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
        sb.append(Block.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("hash");
        sb.append('=');
        sb.append(((this.hash == null)?"<null>":this.hash));
        sb.append(',');
        sb.append("ver");
        sb.append('=');
        sb.append(((this.ver == null)?"<null>":this.ver));
        sb.append(',');
        sb.append("prev_block");
        sb.append('=');
        sb.append(((this.prev_block == null)?"<null>":this.prev_block));
        sb.append(',');
        sb.append("mrkl_root");
        sb.append('=');
        sb.append(((this.mrkl_root == null)?"<null>":this.mrkl_root));
        sb.append(',');
        sb.append("time");
        sb.append('=');
        sb.append(((this.time == null)?"<null>":this.time));
        sb.append(',');
        sb.append("bits");
        sb.append('=');
        sb.append(((this.bits == null)?"<null>":this.bits));
        sb.append(',');
        sb.append("next_block");
        sb.append('=');
        sb.append(((this.next_block == null)?"<null>":this.next_block));
        sb.append(',');
        sb.append("fee");
        sb.append('=');
        sb.append(((this.fee == null)?"<null>":this.fee));
        sb.append(',');
        sb.append("nonce");
        sb.append('=');
        sb.append(((this.nonce == null)?"<null>":this.nonce));
        sb.append(',');
        sb.append("n_tx");
        sb.append('=');
        sb.append(((this.n_tx == null)?"<null>":this.n_tx));
        sb.append(',');
        sb.append("size");
        sb.append('=');
        sb.append(((this.size == null)?"<null>":this.size));
        sb.append(',');
        sb.append("block_index");
        sb.append('=');
        sb.append(((this.block_index == null)?"<null>":this.block_index));
        sb.append(',');
        sb.append("main_chain");
        sb.append('=');
        sb.append(((this.main_chain == null)?"<null>":this.main_chain));
        sb.append(',');
        sb.append("height");
        sb.append('=');
        sb.append(((this.height == null)?"<null>":this.height));
        sb.append(',');
        sb.append("weight");
        sb.append('=');
        sb.append(((this.weight == null)?"<null>":this.weight));
        sb.append(',');
        sb.append("tx");
        sb.append('=');
        sb.append(((this.tx == null)?"<null>":this.tx));
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
