package com.teamacronymcoders.eposmajorum.content.utility;

//TODO: Um.... Implement this x)
//TODO: Wait for Sky to implement things :)
//TODO: See: https://github.com/K-4U/DoubleJumper/tree/master/src/k4unl/minecraft/doubleJump
//public class WindJumpSkill {
//    private static final ResourceLocation NAME = new ResourceLocation(EposAPI.ID, "wind_jump");
//    public static final Feat FEAT =
//            FeatBuilder.start(NAME)
//                    .withEventHandler(LivingEvent.LivingJumpEvent.class,
//                            (livingJumpEvent, entity, iCharacterStats) -> {
//                                if (entity instanceof PlayerEntity) {
//                                    PlayerEntity player = (PlayerEntity) entity;
//                                    CompoundNBT nbt = player.getEntityData();
//                                    if (!nbt.hasUniqueId("hasJumped")) {
//                                        nbt.putInt("hasJumped", 0);
//                                    }
//                                    nbt.putInt("hasJumped", nbt.getInt("hasJumped") + 1);
//                                }
//                            })
//                    .withEventHandler(InputEvent.KeyInputEvent.class,
//                            (keyInputEvent, entity, iCharacterStats) -> {
//                                if (!Minecraft.getInstance().ingameGUI.getChatGUI().getChatOpen() && Minecraft.getInstance().gameSettings.keyBindJump.isKeyDown()) {
//                                    if (Minecraft.getInstance().player.getMotion().y < 0.04 && Minecraft.getInstance().player.isAirBorne) {
//
//                                    }
//                                }
//                            })
//                    .withEventHandler(TickEvent.PlayerTickEvent.class,
//                            (playerTickEvent, entity, iCharacterStats) -> {
//                                if (playerTickEvent.phase == TickEvent.Phase.END && playerTickEvent.side.isServer() && playerTickEvent.player.onGround) {
//                                    CompoundNBT compoundNBT = entity.getEntityData();
//                                    compoundNBT.remove("hasJumped");
//                                }
//                            })
//                    .withEventHandler(FeatAcquiredEvent.class,
//                            (featAcquiredEvent, entity, iCharacterStats) -> {
//                                if (featAcquiredEvent.getFeatAcquired().getRegistryName() == NAME) {
//                                    iCharacterStats.getSkills().putSkill(NAME);
//                                }
//                            }).finish();
//}