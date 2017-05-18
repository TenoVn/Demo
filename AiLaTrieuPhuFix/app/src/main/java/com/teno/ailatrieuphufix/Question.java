package com.teno.ailatrieuphufix;

/**
 * Created by Asus on 4/19/2017.
 */

public class Question {
    private int id;
    private String aks;
    private String ra;
    private String rb;
    private String rc;
    private String rd;
    private int level;
    private int trueCase;

    public Question() {
    }

    public Question(int id, String aks, String ra, String rb, String rc, String rd, int level,
                    int trueCase) {
        this.id = id;
        this.aks = aks;
        this.ra = ra;
        this.rb = rb;
        this.rc = rc;
        this.rd = rd;
        this.level = level;
        this.trueCase = trueCase;
    }

    public int getId() {
        return id;
    }

    public String getAks() {
        return aks;
    }

    public String getRa() {
        return ra;
    }

    public String getRb() {
        return rb;
    }

    public String getRc() {
        return rc;
    }

    public String getRd() {
        return rd;
    }

    public int getLevel() {
        return level;
    }

    public int getTrueCase() {
        return trueCase;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAks(String aks) {
        this.aks = aks;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public void setRb(String rb) {
        this.rb = rb;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public void setRd(String rd) {
        this.rd = rd;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setTrueCase(int trueCase) {
        this.trueCase = trueCase;
    }
}
