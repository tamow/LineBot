package com.example.dto;

import lombok.Data;

@Data
public class LuisIntent {
    private String intent;
    private double score;
}