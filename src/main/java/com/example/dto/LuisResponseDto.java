package com.example.dto;

import java.util.List;

import lombok.Data;

@Data
public class LuisResponseDto {
    private LuisIntent topScoringIntent;
    private List<LuisIntent> intents;
    private List<LuisEntity> entities;
}
