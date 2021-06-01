package com.teamacronymcoders.epos.api.locks;

import com.teamacronymcoders.epos.api.locks.key.NBTLockKey;
import javax.annotation.Nullable;
import net.minecraft.nbt.INBT;

/**
 * This class is used to expose {@link NBTLockKey#similarNBT(INBT, INBT)} to the Test package
 */
abstract class NBTLockKeyTestHelper extends NBTLockKey {

    private NBTLockKeyTestHelper() {
        super(null);
    }

    static boolean isNBTSimilar(@Nullable INBT full, @Nullable INBT partial) {
        return NBTLockKey.similarNBT(full, partial);
    }
}