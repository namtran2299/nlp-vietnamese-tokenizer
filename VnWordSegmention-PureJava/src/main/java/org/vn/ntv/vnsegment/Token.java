/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vn.ntv.vnsegment;

import java.util.Objects;

/**
 *
 * @author namtv19
 */
public class Token {

    public static enum Type {

        WORD,
        NUMBER,
        SPACE,
        PUNCT,
        URL,
        EMAIL,
        PERCENT,
        DATE,
        IPV4,
        IPV6
    };
    
    private final String text;
    private final Type type;
    private final int startPos;
    private final int endPos;

    public Token(String text, int start, int end) {
        this.text = text;
        this.type = Type.WORD;
        this.startPos = start;
        this.endPos = end > 0 ? end : start + text.length();
    }

    public Token(String text, Type type, int start, int end) {
        this.text = text;
        this.type = type;
        this.startPos = start;
        this.endPos = end > 0 ? end : start + text.length();
    }

    public String getText() {
        return text;
    }

    public Type getType() {
        return type;
    }

    public int getPos() {
        return startPos;
    }

    public int getEndPos() {
        return endPos;
    }

    @Override
    public String toString() {
        return "Token{" + "text=" + text + ", type=" + type+ ", startPos=" + startPos + ", endPos=" + endPos + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Token that = (Token) obj;

        if (!this.text.equals(that.text)) {
            return false;
        }

        if (this.type != that.type) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.text);
        hash = 83 * hash + Objects.hashCode(this.type);
        return hash;
    }

    public boolean isWord() {
        return type == Type.WORD;
    }

    public boolean isPunct() {
        return type == Type.PUNCT;
    }

    public boolean isNumber() {
        return type == Type.NUMBER;
    }

    public boolean isSpace() {
        return type == Type.SPACE;
    }

    public boolean isWordOrNumber() {
        return isWord() || isNumber();
    }
    
    
}
