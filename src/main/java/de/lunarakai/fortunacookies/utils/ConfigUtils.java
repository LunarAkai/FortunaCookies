package de.lunarakai.fortunacookies.utils;

import de.lunarakai.fortunacookies.FortunaCookies;

import java.util.Random;

public class ConfigUtils {
    // gets random string from list in config.yml
    public String getRandomFortuneTellingString(FortunaCookies main, String yamlList) {
        String[] fortuneTellingsList = main.getConfig().getStringList(yamlList)
                .toArray(new String[main.getConfig().getStringList(yamlList).size()]);
        Random random = new Random();

        String randomFortuneTellingString = fortuneTellingsList[random.nextInt(fortuneTellingsList.length)];
        return randomFortuneTellingString;
    }

}
