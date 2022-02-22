package com.myblog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author li192
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PluginMenu {

    private String name;
    private String slug;
    private String icon;

}
