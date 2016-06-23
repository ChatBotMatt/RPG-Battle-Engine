/*
 * Decompiled with CFR 0_114.
 */
package mam95.tools.io;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public class IO {
    private Scanner input = new Scanner(System.in);
    private Random random = new Random();

    public String getStringValue(String valueFor) {
        System.out.println("Please enter a " + valueFor + ".");
        String value = this.input.nextLine();
        while (!this.checkString(value)) {
            System.out.println("Sorry, a " + valueFor + " must have a length of at least 1 character. Try again: ");
            value = this.input.nextLine();
        }
        return value;
    }

    public int getNumValue(String valueFor, int lowerBound, int upperBound) {
        System.out.println("Please enter " + valueFor + ".");
        while (!this.input.hasNextInt()) {
            System.out.println("Enter a positive numerical value, from " + lowerBound + " to " + upperBound + " inclusive: ");
            this.input.nextLine();
        }
        int value = this.input.nextInt();
        this.input.nextLine();
        while (!this.checkNum(value, lowerBound, upperBound)) {
            System.out.println("Enter a positive numerical value, from " + lowerBound + " to " + upperBound + " inclusive: ");
            while (!this.input.hasNextInt()) {
                this.input.nextLine();
            }
            value = this.input.nextInt();
            this.input.nextLine();
        }
        return value;
    }

    public String leftPad(String inputString, int length) {
        int inputLength = inputString.length();
        int lengthNeeded = length - inputLength;
        String newString = "";
        int i = 0;
        while (i < lengthNeeded) {
            newString = newString.concat("*");
            ++i;
        }
        newString = newString.concat(inputString);
        return newString;
    }

    public int printMenu(String[] options) {
        System.out.println("Please enter one of the following commands by number: ");
        int i = 0;
        while (i < options.length) {
            System.out.println(String.valueOf(i) + ". " + options[i]);
            ++i;
        }
        int playerChoice = this.getNumValue("a menu option's index", 0, options.length);
        return playerChoice;
    }

    public boolean enableRule(String ruleName, String ruleDescription) {
        int playerChoice;
        boolean enabled = false;
        System.out.println("Please choose to enable/disable " + ruleName + ", by number (E.g. To enable " + ruleName + ", type 1): ");
        System.out.println("Rules are disabled, by default.");
        System.out.println("1- ENABLE " + ruleName + "(" + ruleDescription + ")");
        System.out.println("2- DISABLE " + ruleName);
        System.out.println("3- PRINT Whether " + ruleName + " is enabled or not. (Enabled (True)/Disabled(False))");
        System.out.println("4- CONFIRM your choice");
        do {
            if ((playerChoice = this.getNumValue("a menu option's index", 1, 4)) == 1) {
                enabled = true;
                System.out.println("Rule enabled.");
                continue;
            }
            if (playerChoice == 2) {
                enabled = false;
                System.out.println("Rule disabled.");
                continue;
            }
            if (playerChoice != 3) continue;
            System.out.println("The rule is: " + enabled);
        } while (playerChoice != 4);
        return enabled;
    }

    public int reverseSign(int number, boolean always) {
        boolean reverse = true;
        if (!always) {
            reverse = this.random.nextBoolean();
        }
        if (reverse) {
            number = - number;
        }
        return number;
    }

    private boolean checkNum(double numToCheck, int lowerBound, int upperBound) {
        if ((double)lowerBound <= numToCheck && numToCheck <= (double)upperBound) {
            return true;
        }
        return false;
    }

    private boolean checkString(String stringToCheck) {
        int stringLength = stringToCheck.length();
        if (stringLength > 0) {
            return true;
        }
        return false;
    }
}

