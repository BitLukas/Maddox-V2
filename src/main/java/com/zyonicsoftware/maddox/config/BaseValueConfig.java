/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.config;

import java.awt.*;

public class BaseValueConfig {
    String token;
    int amountShards;
    String defaultPrefix;
    Color defaultColor;
    String defaultBotName;

    public void setToken(Object token) {
        this.token = (String) token;
    }

    public void setAmountShards(Object amountShards) {
        this.amountShards = Integer.parseInt((String) amountShards);
    }

    public void setDefaultColor(Object defaultColor) {
        this.defaultColor = Color.decode((String) defaultColor);
    }

    public void setDefaultPrefix(Object defaultPrefix) {
        this.defaultPrefix = (String) defaultPrefix;
    }

    public void setDefaultBotName(Object defaultBotName) {
        this.defaultBotName = (String) defaultBotName;
    }

    public String getToken() {
        return token;
    }

    public int getAmountShards() {
        return amountShards;
    }

    public String getDefaultPrefix() {
        return defaultPrefix;
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public String getDefaultBotName() {
        return defaultBotName;
    }
}
