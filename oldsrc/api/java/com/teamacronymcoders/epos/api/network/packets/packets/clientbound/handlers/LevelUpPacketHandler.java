package com.teamacronymcoders.epos.api.network.packets.packets.clientbound.handlers;

import com.teamacronymcoders.epos.api.character.ICharacterSheet;
import com.teamacronymcoders.epos.api.network.packets.packets.clientbound.LevelUpPacket;
import com.teamacronymcoders.epos.api.path.PathInfo;
import com.teamacronymcoders.epos.api.skill.SkillInfo;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;

public class LevelUpPacketHandler {

    public static void handlePacket(LevelUpPacket packet) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel clientLevel = minecraft.level;
        if (clientLevel != null) {
            Player player = clientLevel.getPlayerByUUID(packet.getPlayer());
            ICharacterSheet sheet = EposCharacterUtil.getCharacterSheet(player);
            if (sheet != null) {
                switch (packet.getTargetType()) {
                    case CHARACTER -> sheet.levelUp(packet.getAmount());
                    case PATH -> {
                        PathInfo path = sheet.getPaths().getOrCreate(packet.getIdentifier());
                        path.setLevel(Math.min(path.getLevel() + packet.getAmount(), path.getLevel()));
                    }
                    case SKILL -> {
                        SkillInfo skill = sheet.getSkills().getOrCreate(packet.getIdentifier());
                        skill.setLevel(Math.min(skill.getLevel() + packet.getAmount(), skill.getMaxLevel()));
                    }
                }
            }
        }
    }

}
