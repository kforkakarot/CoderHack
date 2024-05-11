package com.personal.coderhack.dto;

public enum Badges {
    CODEMASTER("codemaster"),
    CODECHAMP("codechamp"),
    CODENINJA("codeninja");

    private final String levelName;

    Badges(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelName() {
        return levelName;
    }
}
