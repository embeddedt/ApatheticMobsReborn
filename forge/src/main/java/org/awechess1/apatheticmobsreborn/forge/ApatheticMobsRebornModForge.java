package org.awechess1.apatheticmobsreborn.forge;

import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;
import org.awechess1.apatheticmobsreborn.ApatheticMobsRebornMod;
import net.minecraftforge.fml.common.Mod;

@Mod(ApatheticMobsRebornMod.MOD_ID)
public class ApatheticMobsRebornModForge {
    public ApatheticMobsRebornModForge() {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
        ApatheticMobsRebornMod.init();
    }
}
