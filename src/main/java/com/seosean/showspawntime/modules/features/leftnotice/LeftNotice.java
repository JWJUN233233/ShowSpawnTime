package com.seosean.showspawntime.modules.features.leftnotice;

import com.seosean.showspawntime.utils.LanguageUtils;
import com.seosean.showspawntime.utils.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.List;

public class LeftNotice {
    private static Integer[] deNormalLeft = new Integer[]{4, 5, 3, 4, 7, 6, 5, 6, 8, 9, 7, 7, 8, 8, 8, 11, 12, 11, 14, 16, 19, 21, 18, 22, 23, 26, 25, 27, 30, 33};
    private static Integer[] deHardLeft = new Integer[]{4, 6, 4, 5, 8, 10, 6, 7, 10, 9, 8, 8, 10, 13, 8, 12, 14, 13, 19, 19, 21, 22, 22, 24, 23, 29, 25, 29, 31, 33};
    private static Integer[] deRipLeft = new Integer[]{5, 7, 5, 6, 10, 12, 8, 10, 12, 9, 10, 10, 12, 16, 11, 15, 17, 15, 25, 22, 24, 23, 26, 26, 27, 33, 31, 34, 36, 36};
    private static Integer[] bbNormalLeft = new Integer[]{5, 6, 7, 6, 4, 5, 6, 5, 7, 9, 9, 10, 10, 11, 11, 10, 11, 10, 12, 12, 13, 13, 14, 14, 16, 12, 14, 18, 21, 23};
    private static Integer[] bbHardLeft = new Integer[]{6, 6, 8, 6, 6, 7, 7, 7, 10, 11, 12, 12, 12, 12, 12, 13, 14, 12, 14, 12, 15, 14, 15, 15, 17, 13, 16, 21, 24, 25};
    private static Integer[] bbRipLeft = new Integer[]{6, 8, 8, 8, 7, 7, 10, 9, 11, 15, 12, 12, 12, 13, 13, 13, 16, 12, 14, 14, 18, 16, 16, 18, 25, 15, 20, 25, 25, 27};

    public static int getLeft(int round) {
        if (LanguageUtils.getMap().equals(LanguageUtils.ZombiesMap.DEAD_END)) {
            switch (diff) {
                case NORMAL: return Arrays.asList(deNormalLeft).get(round - 1);
                case HARD: return Arrays.asList(deHardLeft).get(round - 1);
                case RIP: return Arrays.asList(deRipLeft).get(round - 1);
            }
        } else if (LanguageUtils.getMap().equals(LanguageUtils.ZombiesMap.BAD_BLOOD)) {
            switch (diff) {
                case NORMAL: return Arrays.asList(bbNormalLeft).get(round - 1);
                case HARD: return Arrays.asList(bbHardLeft).get(round - 1);
                case RIP: return Arrays.asList(bbRipLeft).get(round - 1);
            }
        }
        return 0;
    }
    public static Difficulty diff = Difficulty.NORMAL;

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        String message = StringUtils.trim(event.message.getUnformattedText());
        if (message.contains(":")) {
            return;
        }
        if (message.contains("The game starts in 10 seconds!") || message.contains("游戏将在10秒开始") || message.contains("遊戲將在 10 秒後開始")) {
            diff = Difficulty.NORMAL;
        }

        if (message.contains("Hard Difficulty") || message.contains("困难") || message.contains("困難")) {
            diff = Difficulty.HARD;
        } else if (message.contains("RIP Difficulty") || message.contains("安息") || message.contains("RIP") ) {
            diff = Difficulty.RIP;
        }
    }
    public enum Difficulty {
        NORMAL,
        HARD,
        RIP
    }
}