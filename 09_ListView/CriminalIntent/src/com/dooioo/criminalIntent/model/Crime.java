package com.dooioo.criminalIntent.model;

import java.util.Date;
import java.util.UUID;

/**
 * 功能说明：Crime
 * 作者：liuxing(2014-11-27 23:42)
 */
public class Crime {

    private UUID id;
    private String title;
    private Date date;
    private boolean solved;
    private String apiBody;

    public Crime() {
        id = UUID.randomUUID();
        date = new Date();
    }

    @Override
    public String toString() {
        return title;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getApiBody() {
        return apiBody;
    }

    public void setApiBody(String apiBody) {
        this.apiBody = apiBody;
    }
}
