package com.bitcoin.clusterbtc.model;

import java.util.HashMap;
import java.util.Map;

public class Input {

    private Long sequence;
    private String witness;
    private String script;
    private Long index;
    private PrevOut prev_out;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public String getWitness() {
        return witness;
    }

    public void setWitness(String witness) {
        this.witness = witness;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public PrevOut getPrev_out() {
        return prev_out;
    }

    public void setPrev_out(PrevOut prev_out) {
        this.prev_out = prev_out;
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
        sb.append(Input.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("sequence");
        sb.append('=');
        sb.append(((this.sequence == null)?"<null>":this.sequence));
        sb.append(',');
        sb.append("witness");
        sb.append('=');
        sb.append(((this.witness == null)?"<null>":this.witness));
        sb.append(',');
        sb.append("script");
        sb.append('=');
        sb.append(((this.script == null)?"<null>":this.script));
        sb.append(',');
        sb.append("index");
        sb.append('=');
        sb.append(((this.index == null)?"<null>":this.index));
        sb.append(',');
        sb.append("prev_out");
        sb.append('=');
        sb.append(((this.prev_out == null)?"<null>":this.prev_out));
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
