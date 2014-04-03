/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.util;

import java.util.List;

/**
 *
 * @author ftoniolo
 */
public class MyStringBuilder {
    
    public static final int FORMAT_ALONE = 0;
    public static final int FORMAT_SPACE_BEFORE = 1;
    public static final int FORMAT_BETWEEN_PARENTHESIS = 2;
    public static final int FORMAT_HIPHEN_BEFORE = 3;
    public static final int FORMAT_SLASH_BEFORE = 4;
    
    public static String format(List<StringBuilderItem> items) {
        StringBuffer sb = new StringBuffer("");
        for (StringBuilderItem stringBuilderItem : items) {
            sb = build(sb, stringBuilderItem);
        }
        return sb.toString().trim();
    }
    
    private static StringBuffer build(StringBuffer sb, StringBuilderItem item) {
        if (item.getValue() != null && !item.getValue().equals("")) {
            switch (item.getFormattingType()) {
                case FORMAT_ALONE:
                    sb.append(item.getValue());
                    break;
                case FORMAT_SPACE_BEFORE:
                    sb.append(' ');
                    sb.append(item.getValue());
                    break;
                case FORMAT_BETWEEN_PARENTHESIS:
                    sb.append(" (");
                    sb.append(item.getValue());
                    sb.append(')');
                    break;
                case FORMAT_HIPHEN_BEFORE:
                    sb.append(" - ");
                    sb.append(item.getValue());
                    break;
                case FORMAT_SLASH_BEFORE:
                    sb.append(" / ");
                    sb.append(item.getValue());
                    break;
                default:
                    break;
            }
            if (item.getSuffix() != null) {
                sb.append(item.getSuffix());
            }
        }
        return sb;
    }
}
