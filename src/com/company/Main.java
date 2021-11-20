package com.company;

import java.text.DecimalFormat;

public class Main {
    // Constants
    public static final String GERMAN_LANGUAGE_PATTERN = "Werden zwei Glasstaebe mit einem Wolltuch gerieben, dann kann man feststellen, dass sich die beiden Staebe gegenseitig abstossen. Wird das gleiche Experiment mit zwei Kunststoffstaeben wiederholt, dann bleibt das Ergebnis gleich, auch diese beiden Staebe stossen sich gegenseitig ab. Im Gegensatz dazu ziehen sich ein Glas und ein Kunststoffstab gegenseitig an. Diese mit den Gesetzen der Mechanik nicht zu erklaerende Erscheinung fuehrt man auf Ladungen zurueck. Da sowohl Anziehung als auch Abstossung auftritt, muessen zwei verschiedene Arten von Ladungen existieren. Man unterscheidet daher positive und negative Ladungen.";
    public static final String ENCRYPTED_MESSAGE = "ugjt iwv! fw jcuv fgp eqfg igmpcemv wpf fkt uq twjo wpf gjtg gtyqtdgp. pqtocngtygkug mcpp ocp jkgt inwgjygkp qfgt cgjpnkejgu igykppgp. fkgu hcgnnv kp fkgugo ugoguvgt ngkfgt cwu... uqtt{";
    public static final int ASCII_CHART_LENGTH = 128;
    public static final char SEPARATOR = ' ';

    public static void main(String[] args) {
        // Decode text
        String decodedText = decode(ENCRYPTED_MESSAGE, GERMAN_LANGUAGE_PATTERN);

        // Print result
        System.out.println("Unreadable, encrypted input text: ");
        System.out.println(ENCRYPTED_MESSAGE);
        System.out.println();
        System.out.println("Readable, decoded input text:");
        System.out.println(decodedText);
    }

    // Methods
    public static int getIndexOfMaximumEntry(int[] values) {
        int max = 0;
        int maxIndex = 0;

        // Get max index
        for(int i = 0; i < values.length; i++) {
            if (values[i] > max) {
                max = values[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public static int[] getHistogram(String text) {
        int[] histogram = new int[ASCII_CHART_LENGTH];
        String lowercaseText = text.toLowerCase();

        // Loop through text and set values of histogram
        for (int i = 0; i < lowercaseText.length(); i++) {
            if (lowercaseText.charAt(i) != SEPARATOR) {
                int index = lowercaseText.charAt(i);
                histogram[index]++;
            }
        }

        return histogram;
    }

    public static char getSignificantLetter(String text) {
        // Get histogram
        int[] histogram = getHistogram(text);
        DecimalFormat PercentFormat = new DecimalFormat(".#%");

        // Determine most significant letter, quantity and quota
        char significantLetter = (char) getIndexOfMaximumEntry(histogram);
        int quantity = histogram[getIndexOfMaximumEntry(histogram)];
        double quota = (double) quantity / (text.length());

        // Print result
        System.out.println("Most significant letter: " + significantLetter);
        System.out.println("Quantity: " + quantity + " times (" + PercentFormat.format(quota) + " of whole text).");

        return significantLetter;
    }

    public static int getShift(String encryptedText, String languagePattern) {
        // Variables
        char sigOfChiffre = getSignificantLetter(encryptedText);
        char sigOfPattern = getSignificantLetter(languagePattern);
        int shift = sigOfChiffre - sigOfPattern;

        // Print result
        System.out.println("Most significant letter in the pattern text: " + sigOfPattern);
        System.out.println("Most significant letter in the encrypted text: " + sigOfChiffre);
        System.out.println("Resulting shift: " + shift);

        return shift;
    }

    public static String decode(String encryptedText, String languagePattern) {
        // Variables
        int shift = getShift(encryptedText, languagePattern);
        char[] lettersEncryptedText = encryptedText.toCharArray();

        // Decrypt text
        for (int i = 0; i < lettersEncryptedText.length; i++) {
            if (lettersEncryptedText[i] > 96  - Math.abs(shift) && lettersEncryptedText[i] < 122 + Math.abs(shift)) {
                if (shift > 0) {
                    lettersEncryptedText[i] -= shift;
                }
                else {
                    lettersEncryptedText[i] += shift;
                }
            }
        }

        // Transform array to string and return
        return String.copyValueOf(lettersEncryptedText);
    }
}
