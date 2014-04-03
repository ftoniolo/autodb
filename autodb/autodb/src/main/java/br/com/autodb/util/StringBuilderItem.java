/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.autodb.util;

/**
 *
 * @author ftoniolo
 */
public class StringBuilderItem {

    private String value = null;
    private int formattingType = -1;
    private String suffix = null;
    private String prefix = null;

    public StringBuilderItem(Object value, int formattingType) {
        if (value != null) {
            this.value = value.toString().trim();
        }
        this.formattingType = formattingType;
    }

    public StringBuilderItem(Object value, int formattingType, String suffix) {
        if (value != null) {
            this.value = value.toString().trim();
        }
        this.formattingType = formattingType;
        this.suffix = suffix;
    }

    public StringBuilderItem(Object value, int formattingType, String prefix, String suffix) {
        if (value != null) {
            this.value = value.toString().trim();
        }
        this.formattingType = formattingType;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getFormattingType() {
        return formattingType;
    }

    public void setFormattingType(int formattingType) {
        this.formattingType = formattingType;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

}
