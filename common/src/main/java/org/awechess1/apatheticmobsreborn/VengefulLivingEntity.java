package org.awechess1.apatheticmobsreborn;

import java.util.Set;
import java.util.UUID;

public interface VengefulLivingEntity {
    Set<UUID> getPlayersToTakeRevengeOn();
    boolean shouldBeApathetic();
}
