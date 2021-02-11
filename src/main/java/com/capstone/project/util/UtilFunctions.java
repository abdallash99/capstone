package com.capstone.project.util;

import java.util.Random;

public class UtilFunctions {
    private static Random random=new Random();
    public static String generateRandomWords()
    {
        String randomStrings ;

        char[] word = new char[random.nextInt(8)+3]; // words of length 3 through 10. (1 and 2 letter words are boring.)
        for(int j = 0; j < word.length; j++)
        {
            word[j] = (char)('a' + random.nextInt(26));
        }
        randomStrings = new String(word);
        return randomStrings;
    }
}
