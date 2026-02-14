package com.bot.factory;

import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

public class StringSelectMenuFactory 
{
    public StringSelectMenu createUnitMenu()
    {
        return StringSelectMenu.create("create-unit-menu")
            .setPlaceholder("Selecciona una clase")
            .addOption("Mirmidón", "Mirmidón")
            .build();   
    }
}