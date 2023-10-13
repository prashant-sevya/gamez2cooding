package com.gamez2coding.dto;

public class CodeRequest {
    private String code;
    private String language;

    public CodeRequest() {
        // Default constructor
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
