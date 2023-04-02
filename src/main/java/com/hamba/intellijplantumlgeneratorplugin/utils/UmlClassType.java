package com.hamba.intellijplantumlgeneratorplugin.utils;

public enum UmlClassType {
    BASIC_CLASS("class"),
    ENUM("enum"),
    ABSTRACT_CLASS("abstract class"),
    INTERFACE("interface");

    private String umlString;

    UmlClassType(String umlString) {
        this.umlString = umlString;
    }

    @Override
    public String toString() {
        return umlString;
    }
}
