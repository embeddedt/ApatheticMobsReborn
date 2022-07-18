package org.awechess1.apatheticmobsreborn;

import com.google.common.collect.ImmutableList;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import java.util.*;

@Config(name = ApatheticMobsRebornMod.MOD_ID)
public class ModConfig implements ConfigData {
    @Comment("Whether mobs take revenge when hit by a player.")
    public boolean takeRevenge = false;
    @Comment("Mobs which will be ignored by this mod.")
    public List<String> blacklistMobs = new ArrayList<>(Collections.singletonList(
            "minecraft:pig"
    ));

    @Comment("If 'true', the list above is considered to be a blacklist, and these mobs will be ignored.\r\nIf 'false', the list is a whitelist and only these mobs will be apathetic.")
    public boolean isMobListBlacklist = true;

    @Comment("Difficulty levels which will be ignored by this mod.")
    public List<String> blacklistDifficulties = new ArrayList<>(Collections.singletonList(
            "peaceful"
    ));
    @Comment("Players to treat specially (see below).")
    public List<String> playerList = new ArrayList<>();
    @Comment("If 'true', the list above is considered to be a blacklist, and mobs will not be apathetic towards those players.\r\nIf 'false', only players in the list above will receive apathetic mobs.")
    public boolean isPlayerListBlacklist = true;
    @Comment("Mobs to treat as hostile even if they don't despawn in peaceful.")
    public List<String> hostileOverrideEntities = new ArrayList<>(ImmutableList.of("minecraft:hoglin"));
}