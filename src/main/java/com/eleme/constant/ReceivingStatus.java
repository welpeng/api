package com.eleme.constant;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ReceivingStatus {

    ING, SUCCESS, FAILURE;

    @JsonValue
    public int getJsonValue() {
        return ordinal();
    }

}
