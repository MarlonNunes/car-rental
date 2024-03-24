package com.marlonnunes.carrental.dto.vehicle;

import com.marlonnunes.carrental.model.enums.Color;

public record ColorDTO(
        Color value,
        String label
) {

    public static ColorDTO fromColor(Color color){
        return new ColorDTO(
                color,
                color.getLabel()
        );
    }
}
