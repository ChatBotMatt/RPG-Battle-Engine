/*
 * Decompiled with CFR 0_114.
 */
package rpg.system;

import rpg.engine.Game;

public class Main {
    Game gameEngine;

    public void runApp() throws InterruptedException {
        this.gameEngine = new Game();
        this.gameEngine.runGame();
    }

    public static void main(String[] args) throws InterruptedException {
        Main main = new Main();
        main.runApp();
    }
}

