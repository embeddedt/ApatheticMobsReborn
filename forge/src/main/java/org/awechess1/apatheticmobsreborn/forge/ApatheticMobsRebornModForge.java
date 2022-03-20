package org.awechess1.apatheticmobsreborn.forge;

import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.network.NetworkConstants;
import org.awechess1.apatheticmobsreborn.ApatheticMobsRebornMod;
import net.minecraftforge.fml.common.Mod;

@Mod(ApatheticMobsRebornMod.MOD_ID)
public class ApatheticMobsRebornModForge {
    public ApatheticMobsRebornModForge() {
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));
        ApatheticMobsRebornMod.init();
    }
}
