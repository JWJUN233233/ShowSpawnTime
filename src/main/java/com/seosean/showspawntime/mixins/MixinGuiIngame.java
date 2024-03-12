package com.seosean.showspawntime.mixins;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.seosean.showspawntime.ShowSpawnTime;
import com.seosean.showspawntime.config.MainConfiguration;
import com.seosean.showspawntime.features.Renderer;
import com.seosean.showspawntime.features.leftnotice.LeftNotice;
import com.seosean.showspawntime.features.powerups.Powerup;
import com.seosean.showspawntime.features.powerups.PowerupPredict;
import com.seosean.showspawntime.features.timerecorder.TimeRecorder;
import com.seosean.showspawntime.utils.DebugUtils;
import com.seosean.showspawntime.utils.DelayedTask;
import com.seosean.showspawntime.utils.LanguageUtils;
import com.seosean.showspawntime.utils.PlayerUtils;
import com.seosean.showspawntime.utils.StringUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Score;
import net.minecraft.util.EnumChatFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Mixin(GuiIngame.class)
public abstract class MixinGuiIngame {


    @Inject(method = "displayTitle", at = @At(value = "RETURN"))
    private void displayTitle(String p_175178_1_, String p_175178_2_, int p_175178_3_, int p_175178_4_, int p_175178_5_, CallbackInfo callbackInfo){
        String roundTitle = p_175178_1_ == null ? "" : StringUtils.trim(p_175178_1_);
        boolean flag = LanguageUtils.contains(roundTitle, "zombies.game.youwin") || LanguageUtils.contains(roundTitle, "zombies.game.gameover");
        if (LanguageUtils.isRoundTitle(roundTitle) || flag) {
            TimeRecorder.recordGameTime();
            TimeRecorder.recordRedundantTime();
            ShowSpawnTime.getGameTickHandler().setGameStarted(true);
            ShowSpawnTime.getGameTickHandler().startOrSplit();

            if (!PlayerUtils.isInZombiesTitle()) {
                return;
            }

            int round = LanguageUtils.getRoundNumber(roundTitle);
            ShowSpawnTime.getSpawnTimes().setCurrentRound(round);

            Renderer.setShouldRender(true);

            ShowSpawnTime.getSpawnNotice().update(round);

            Powerup.incPowerups.clear();

            if (ShowSpawnTime.getPowerupDetect().isInsRound(round)) {
                Powerup.deserialize(Powerup.PowerupType.INSTA_KILL);
            }

            if (ShowSpawnTime.getPowerupDetect().isMaxRound(round)) {
                Powerup.deserialize(Powerup.PowerupType.MAX_AMMO);
            }

            if (ShowSpawnTime.getPowerupDetect().isSSRound(round)) {
                Powerup.deserialize(Powerup.PowerupType.SHOPPING_SPREE);
            }

            new DelayedTask() {
                @Override
                public void run() {
                    PowerupPredict.detectNextPowerupRound();
                }
            }.runTaskLater(40);
        }


    }

    @ModifyArg(method = "renderScoreboard", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I", ordinal = 0), index = 0)
    private String modifyArgumentText(String text) {
        String textTrimed = StringUtils.trim(text);
        if(MainConfiguration.Wave3LeftNotice) {
            if (PlayerUtils.isInZombies()) {
                if (textTrimed.contains(":") || textTrimed.contains("：")) {
                    if (StringUtils.contains(LanguageUtils.ZOMBIES_LEFT, textTrimed)) {
                        String amount = "";
                        if (textTrimed.contains(":")) {
                            amount = StringUtils.trim(textTrimed.split(":")[1]);
                        } else if (textTrimed.contains("：")) {
                            amount = StringUtils.trim(textTrimed.split("：")[1]);
                        }

                        int left = LeftNotice.getLeft(ShowSpawnTime.getSpawnTimes().currentRound);
                        boolean isCleared = Integer.parseInt(amount) <= left;
                        if (LanguageUtils.getMap().equals(LanguageUtils.ZombiesMap.DEAD_END) || LanguageUtils.getMap().equals(LanguageUtils.ZombiesMap.BAD_BLOOD)) {
                            return text.concat(EnumChatFormatting.WHITE + " | " + (isCleared ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + ((left == 0) ? "" : left));
                        }
                    }
                }
            }
        }
        //§
        if (MainConfiguration.PlayerHealthNotice) {
            if (PlayerUtils.isInZombies()) {
                if ((textTrimed.contains(":") || textTrimed.contains("："))) {
                    String playerName = "";
                    if (textTrimed.contains(":")) {
                        playerName = StringUtils.trim(textTrimed.split(":")[0]);
                    } else if (textTrimed.contains("：")){
                        playerName = StringUtils.trim(textTrimed.split("：")[0]);
                    }
                    if (playerName.length() >= 2) {
                        EntityPlayer entityPlayer = getPlayerEntity(playerName);
                        if (entityPlayer != null) {
                            if (!text.contains("§6")) {
                                return text;
                            }
                            float health = entityPlayer.getHealth();
                            return EnumChatFormatting.WHITE + "(" + getColor(entityPlayer) + (int) health + EnumChatFormatting.WHITE + ")" + " " + text;
                        }
                    }
                }
            }
        }
        return text;
    }

    @Shadow
    public abstract FontRenderer getFontRenderer();

    //Contents
    @ModifyArg(method = "renderScoreboard", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I", ordinal = 0), index = 1)
    private int modifyArgumentWidth0(int l1){
        if(MainConfiguration.PlayerHealthNotice || MainConfiguration.Wave3LeftNotice) {
            if (PlayerUtils.isInZombies()) {
                String text = "(00)";
                return l1 - getFontRenderer().getStringWidth(text);
            }
        }
        return l1;
    }

    //Title
    @ModifyArg(method = "renderScoreboard", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I", ordinal = 2), index = 1)
    private int modifyArgumentWidth1(int l1){
        if (MainConfiguration.PlayerHealthNotice || MainConfiguration.Wave3LeftNotice) {
            if (PlayerUtils.isInZombies()) {
                String text = "(00)";
                return l1 - getFontRenderer().getStringWidth(text) / 2;
            }
        }
        return l1;
    }

    //Contents GUI
    @ModifyArg(method = "renderScoreboard", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawRect(IIIII)V", ordinal = 0), index = 0)
    private int modifyArgumentLeft0(int l1){
        if (MainConfiguration.PlayerHealthNotice || MainConfiguration.Wave3LeftNotice) {
            if (PlayerUtils.isInZombies()) {
                String text = "(00)";
                return l1 - getFontRenderer().getStringWidth(text);
            }
        }
        return l1;
    }

    //Title GUI
    @ModifyArg(method = "renderScoreboard", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawRect(IIIII)V", ordinal = 1), index = 0)
    private int modifyArgumentLeft1(int l1){
        if (MainConfiguration.PlayerHealthNotice || MainConfiguration.Wave3LeftNotice) {
            if (PlayerUtils.isInZombies()) {
                String text = "(00)";
                return l1 - getFontRenderer().getStringWidth(text);
            }
        }
        return l1;
    }

    //Bottom GUI
    @ModifyArg(method = "renderScoreboard", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawRect(IIIII)V", ordinal = 2), index = 0)
    private int modifyArgumentLeft2(int l1){
        if(MainConfiguration.PlayerHealthNotice || MainConfiguration.Wave3LeftNotice) {
            if (PlayerUtils.isInZombies()) {
                String text = "(00)";
                return l1 - getFontRenderer().getStringWidth(text);
            }
        }
        return l1;
    }


    @Unique
    private List<EntityPlayer> getPlayerList() {
        return new CopyOnWriteArrayList<>(Minecraft.getMinecraft().theWorld.playerEntities);
    }


    @Unique
    private EntityPlayer getPlayerEntity(String name) {
        List<EntityPlayer> playerList = this.getPlayerList();
        for (EntityPlayer entity: playerList){
            String entityName = entity.getDisplayNameString().trim();
            if (name.equals(entityName.trim())) {
                return entity;
            }
        }
        return null;
    }

    @Unique
    private EnumChatFormatting getColor(EntityPlayer entityPlayer) {
        int currentHealth = (int)entityPlayer.getHealth();
        if (currentHealth > entityPlayer.getMaxHealth() / 2) {
            return EnumChatFormatting.GREEN;
        } else if (currentHealth > entityPlayer.getMaxHealth() / 4) {
            return EnumChatFormatting.YELLOW;
        } else return EnumChatFormatting.RED;
    }

//    @Redirect(method = "renderScoreboard", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Lists;newArrayList(Ljava/lang/Iterable;)Ljava/util/ArrayList;", ordinal = 1))
//    private <E extends Score> ArrayList<Score> extendSidebarDisplayCap(Iterable<? extends E> elements){
//        if (PlayerUtils.isInZombiesTitle()) {
//            return elements.;
//        }
//        else return Lists.newArrayList(elements);
//    }

    @Redirect(method = "renderScoreboard", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Iterables;skip(Ljava/lang/Iterable;I)Ljava/lang/Iterable;"))
    private <E extends Score> Iterable<E> extendSidebarDisplayCap(Iterable<E> list, int iterable) {
        if (PlayerUtils.isInZombiesTitle()) {
            return list;
        }
        else return Iterables.skip(list, iterable);
    }
}
