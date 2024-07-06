package com.github.Neelic.demo.javarushclient.dto;

import lombok.Data;

import java.util.List;

@Data
public class StatisticDTO {
    private final int activeUserCount;
    private final int inactiveUserCount;
    private final List<GroupStatDTO> groupStatDTOs;
    private final double averageGroupCountByUser;
}
