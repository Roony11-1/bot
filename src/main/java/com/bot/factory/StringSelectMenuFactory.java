package com.bot.factory;

import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

public class StringSelectMenuFactory 
{
    public StringSelectMenu createUnitMenu(String unitName)
    {
        return StringSelectMenu.create("create-unit-menu:"+unitName)
            .setPlaceholder("Selecciona una clase")
            .addOption("Mirmidón", "Mirmidón")
            .build();   
    }
}