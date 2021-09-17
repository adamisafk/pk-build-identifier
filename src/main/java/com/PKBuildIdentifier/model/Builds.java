package com.PKBuildIdentifier.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Builds {
    NONE("None"),
    DEF_PURE("1 Def Pure"),
    VOIDER("Voider"),
    ZERKER("Zerker");

    private final String name;

    @Override
    public String toString()
    {
        return name;
    }
}
