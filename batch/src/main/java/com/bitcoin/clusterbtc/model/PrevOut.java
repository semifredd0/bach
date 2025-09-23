package com.bitcoin.clusterbtc.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrevOut {

    private Long tx_index;
    private Long value;
    private Long n;
    private Long type;
    private Boolean spent;
    private String script;
    private List<com.bitcoin.clusterbtc.model.SpendingOutpoint> spending_outpoints = null;
    private String addr;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Long getTx_index() {
        return tx_index;
    }

    public void setTx_index(Long tx_index) {
        this.tx_index = tx_index;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getN() {
        return n;
    }

    public void setN(Long n) {
        this.n = n;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Boolean getSpent() {
        return spent;
    }

    public void setSpent(Boolean spent) {
        this.spent = spent;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public List<com.bitcoin.clusterbtc.model.SpendingOutpoint> getSpending_outpoints() {
        return spending_outpoints;
    }

    public void setSpending_outpoints(List<com.bitcoin.clusterbtc.model.SpendingOutpoint> spending_outpoints) {
        this.spending_outpoints = spending_outpoints;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
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
        sb.append(PrevOut.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("tx_index");
        sb.append('=');
        sb.append(((this.tx_index == null)?"<null>":this.tx_index));
        sb.append(',');
        sb.append("value");
        sb.append('=');
        sb.append(((this.value == null)?"<null>":this.value));
        sb.append(',');
        sb.append("n");
        sb.append('=');
        sb.append(((this.n == null)?"<null>":this.n));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("spent");
        sb.append('=');
        sb.append(((this.spent == null)?"<null>":this.spent));
        sb.append(',');
        sb.append("script");
        sb.append('=');
        sb.append(((this.script == null)?"<null>":this.script));
        sb.append(',');
        sb.append("spending_outpoints");
        sb.append('=');
        sb.append(((this.spending_outpoints == null)?"<null>":this.spending_outpoints));
        sb.append(',');
        sb.append("addr");
        sb.append('=');
        sb.append(((this.addr == null)?"<null>":this.addr));
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
