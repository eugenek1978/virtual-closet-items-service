package com.yevgeniy.virtualclosetitemsservice;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class VirtualClosetItemModel {
    public static final String NAME_IS_MANDATORY = "Name is mandatory";

    @NotEmpty(message = NAME_IS_MANDATORY)
    private String name;
}
