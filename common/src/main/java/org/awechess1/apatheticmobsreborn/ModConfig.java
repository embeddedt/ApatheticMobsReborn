package org.awechess1.apatheticmobsreborn;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import java.util.Arrays;
import java.util.List;

@Config(name = ApatheticMobsRebornMod.MOD_ID)
public class ModConfig implements ConfigData {
    @Comment("Whether mobs take revenge when hit by a player.")
    public boolean takeRevenge = true;
    @Comment("Mobs which will be ignored by this mod.")
    public List<String> blacklistMobs = Arrays.asList(
            "minecraft:pig"
    );
    @Comment("Difficulty levels which will be ignored by this mod.")
    public List<String> blacklistDifficulties = Arrays.asList(
            "peaceful"
    );
}