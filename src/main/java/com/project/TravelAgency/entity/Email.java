package com.project.TravelAgency.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Email {
    private String to;
    private String from;
    private String subject;
    private String content;
    private Map<String, Object> model;
}
