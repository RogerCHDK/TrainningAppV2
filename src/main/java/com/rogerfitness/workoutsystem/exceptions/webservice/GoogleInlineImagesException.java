package com.rogerfitness.workoutsystem.exceptions.webservice;

import lombok.Getter;

@Getter
public class GoogleInlineImagesException extends Exception{
    private final String serviceName;

    public GoogleInlineImagesException(Throwable t,String serviceName) {
        super(String.format("Failed to execute call to GoogleInlineImages [%s]: %s", serviceName, t.getMessage()), t);
        this.serviceName = serviceName;
    }
}
