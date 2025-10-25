package net.transpo.spring.command;

import java.io.Serializable;

/**
 * @author shoebakib
 * @since 5/2/24
 */
public abstract class Command implements Serializable {

    private String backLink;

    private boolean readOnly;

    public String getBackLink() {
        return backLink;
    }

    public void setBackLink(String backLink) {
        this.backLink = backLink;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

}
