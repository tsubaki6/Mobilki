
package com.amal.nodelogin.model.route;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NamedValue {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("value")
    @Expose
    private Long value;

    /**
     * @return The text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return The value
     */
    public Long getValue() {
        return value;
    }

    /**
     * @param value The value
     */
    public void setValue(Long value) {
        this.value = value;
    }

}
