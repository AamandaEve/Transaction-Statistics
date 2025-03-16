package com.savemy.money.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Statistics {
    private long count;
    private double sum;
    private double avg;
    private double min;
    private double max;

    public static Statistics empty(){
        return new Statistics(0, 0.0, 0.0, 0.0, 0.0);
    }
}
