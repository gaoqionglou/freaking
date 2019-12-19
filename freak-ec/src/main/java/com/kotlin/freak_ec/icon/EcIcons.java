package com.kotlin.freak_ec.icon;

import com.joanzapata.iconify.Icon;


public enum EcIcons implements Icon {
    icon_animal_1('\ue629'),icon_animal_2('\uec89');

    private  char character;

    EcIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}