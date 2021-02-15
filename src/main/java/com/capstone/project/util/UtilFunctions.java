package com.capstone.project.util;

import static com.capstone.project.ProjectApplication.random;
public class UtilFunctions {

    private UtilFunctions(){}

    public static String generateRandomWords()
    {
        String randomStrings ;

        char[] word = new char[random.nextInt(8)+3];
        for(int j = 0; j < word.length; j++)
        {
            word[j] = (char)('a' + random.nextInt(26));
        }
        randomStrings = new String(word);
        return randomStrings;
    }


}
