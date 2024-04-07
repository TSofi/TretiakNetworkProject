package com.tretiak.project.tretiaknetworkproject.infrastrucuture.dtos.user;

import org.openapitools.jackson.nullable.JsonNullable;

public class UpdateUserDto {
    private JsonNullable<String> fName;
    private JsonNullable<String> lName;
    private JsonNullable<String> email;


    public UpdateUserDto(JsonNullable<String> fName, JsonNullable<String> lName, JsonNullable<String> email) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
    }

    public JsonNullable<String> getfName() {
        return fName;
    }
    public JsonNullable<String> getlName() {
        return lName;
    }
    public JsonNullable<String> getEmail() {
        return email;
    }
    }
