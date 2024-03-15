# ShowSpawnTime  
  
## Latest Version of ShowSpawnTime
[**2.0.0 Download**](https://github.com/Seosean/ShowSpawnTime/releases/download/2.0.0/ShowSpawnTime-2.0.0.jar)  

## The most necessary module to this mod.
[**ZombiesAutoSplits by Seosean**](https://github.com/Seosean/ZombiesAutoSplits/tree/Mixin)  
  
What's the difference between Seosean's and Thamid's?  
Seosean's one was forked from thamid's, I added a feature that you can now edit the position of HUD(the render of timer) with /splitshud or /ssthud.  
(if you installed ShowSpawnTime, you can edit ZombiesAutoSplits's HUD with ShowSpawnTime's /ssthud command, and edit their HUDs together.)  
  
[**Download ZombiesAutoSplits by Seosean**](https://github.com/Seosean/ZombiesAutoSplits/releases/download/1.2/ZombiesAutoSplits-1.2.jar)  
· ZombiesAutoSplits by Seosean 1.2 update: Add compatibility for ShowSpawnTime 2.0.0.
  
****
## Updates Logs
### Update 2.0.0 2024/3/15  
· All codes are completely refactored!  
· New feature: Fast revive cooldown display: Players' fast revive cooldown will display on sidebar now, you can choose the position you want to set the countdown in.  
· New feature: Now your individual DPS will be displayed on screen! It only counts your own DPS instead of whole team.  
· New feature: You can now get when will the next powerup spawns in chat as a round starts.  
· Sidebar optimized. Now if there is an amount of side bar contents more than 15 rows, everything will be shown on screen instead of partly hided.  
· DE/BB Final Wave Count Down Optimized: You can now customize whatever sound and pitch you want for counting down.  
As well, you can now toggle if this feature is on by config instead of hotkey, so that you dont need to retoggle it after restarting Minecraft.   
· More language supported! Basically whatever language you are using on Hypixel, features of ShowSpawnTime will run normally.  
· Config GUI optimized: Everything changed in config will be saved if you directly close the config gui with ESC or anything.  
· Vanilla bug fixed: A bug caused texts after any full-width character do not render, a fixing method is now applied in this mod.  

### Update 1.15.1 2023/10/5  
· Fixed a bug that your game will crash when you are playing at Simplified Chinese in hypixel Zombies. Its certainly hypixel's problem because it doesnt happen to Traditional Chinese i promise.  
· Fixed a bug that log spammed crazily.  
· Adjusted the description of Chinese, it's now looked much more normal.  
· Added a new feature that you can now get when will the next powerup spawns in chat bar as a round starts.  
  
### Update 1.15.0 2023/10/3  
· A more accurate internal timer. Redundant time recorder will be now splited with 0.01 instead of 0.05.  
· Fixed a bug that you probably get kicked by server when a game starts.  
· You can now move HUD over the screen with /ssthud.  
· You can now edit config with /sstconfig.  
· Added a new feature that the countdown of exist powerups will now display over the screen.  
· Added a new feature that players' health will now display in the sidebar.  
· Added a new feature that the amount of 3rd wave zombies will now display in the sidebar.  
· Now if you installed ZombiesAutoSplits, you can directly edit its HUD position with /ssthud.  
· Added commands of /sst mode normal/hard/rip and /sst ins/max/ss 2/3/4/5/6/7 to correct related feature if a incorrection or a closing of minecraft happens.  
· Added an auto-check of updates and a command of /sst checkupdate to check if you are using on a latest version.  
· Added a new feature that player invisibility is now integrated into ShowSpawnTime, it has a transparent effect when a player approuches. Turn on it in keyboard control.
· Enhanced some features.  
· Fixed few internal bugs.  
  
### Update 1.14.4 2023/8/28  
· Fixed a rare bug that you probably get kicked by server when a round ends.  
· Now when a wave passed, it will become darker instead of reserving its color.  
· Added Simplified Chinese and Traditional Chinese language supports.  
· Optimized lots of codes  

## Features
### Show Waves Intervals
![image](https://github.com/Seosean/ShowSpawnTime/assets/88036696/2b1458da-346c-4347-a4f0-b7ca5f751c04)

At the lower right corner, the wave information of the round will be displayed.  
When a certain wave is reached, there will be a sound of note triggerred,  
the yellow highlight will transfer to next wave (in a delay if in DE/BB, which can be cancelled in config),  
and the passed wave text will become darker gray.

### Powerup Alert (UPCOMING)
![UDE9GPF@XV0 LWY9S0$B9)M](https://github.com/Seosean/ShowSpawnTime/assets/88036696/ae500dfe-951f-4e14-b03f-ebee293aa8a9)

When a powerup spawned, the count down of powerup will instantly display in the screen.  
When they are nearly expiring in 10 seconds, they will blink for alert.  
If in a game you used to be in r2/r3/..., and met the dropping of insta or max,  
the mod will remember the pattern of ins/max/ss, then remind you in the next ins/max/ss round with "Insta Kill - Round". (Max Ammo and Shopping Spree as well.)
If you find that the pattern is incorrect, it happened when you restart Minecraft, or leave the game when it's in r2/r3/... and missed the first ins/max/ss.
**Note: I have already marked almost all special moments when a powerup drops, like when the last zombie died and the powerup drops in next round.**  
**In normal case, it should get patterns correctly. If you actually get a incorrect pattern, you can use "/sst ins 3", "/sst max 2" or "/sst ss 5" and so on to recorrect it.**  

### Game Time Recorder
![image](https://github.com/Seosean/ShowSpawnTime/assets/88036696/411b4903-1e00-4223-8460-ec0a6a2331ab)

After each round, mod will catch the game time from the scoreboard until the end of the round and send it to the chat.  
You may copy the text if you click the text in the chat.   
If you think its annoying to show the time in every round, you can switch the record mode in config.  
There are 4 modes for you: Tenfold, Quintuple, All, Off.

### Redundant Time Recorder
![image](https://github.com/Seosean/ShowSpawnTime/assets/88036696/33617cc8-5c66-4a4a-a482-a3ab9ab63646)

After each round, mod will record the time of the round, and caculate the redundant time from the final wave to the end when you clear up all zombies.
Note: A much more accurate timer as showcase is coming soon, it will be update in 1.15 ver.

### Lightning Rod Queue
![D8QOCILJS(E2EFEYH)A{~B0](https://github.com/Seosean/ShowSpawnTime/assets/88036696/9155d5a1-d1e6-4c91-aed1-54fb5ba19a00)
  
After using the lightning rod, a progress bar will be displayed in the middle of the screen, displayed by four extinguished lights.  
Each lightning strike will increase the progress bar by one and highlight one extinguished light.  
If there is no lightning strike within five seconds, all lights will turn off.  
If no lightning strike occurs within five seconds after extinguishing, the progress bar disappears.  
Note: Due to a judgment issue, the boss generation will result in a progress bar being displayed that goes out, which does not affect anything.

### Boss Alert (AA)


Besides the wave intervals feature, the text not only has a combination of gray and yellow.  
But in AA, waves with the old one will turn into a combination of dark green and green,  
waves with giants will turn into a combination of deep blue purple and sky blue,  
waves with both will turn into a combination of dark red and red,  
in order to alert the upcoming the old one and giants.
