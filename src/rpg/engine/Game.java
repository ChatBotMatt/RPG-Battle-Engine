/*
 * Decompiled with CFR 0_114.
 */
package rpg.engine;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Logger;
import mam95.tools.io.IO;
import rpg.character.Character;
import rpg.character.Enemy;
import rpg.character.PartyMember;

public class Game {
    private ArrayList<Character> characters;
    private ArrayList<Character> activeCharacters;
    private ArrayList<Enemy> enemyFighters;
    PartyMember deathsmasher = new PartyMember();
    Enemy mike = new Enemy("Monster Mike", "a dedly monsta", 70, 15, 3, 3, 3, 3, 3, 3);
    IO input;
    Random random;
    private Logger lumberjack = Logger.getLogger("Lumberjack");

    public Game() {
        this.lumberjack.info("Lumberjack is in the forest.");
        this.lumberjack.info("Moooooo");
        this.characters = new ArrayList();
        this.activeCharacters = new ArrayList();
        this.enemyFighters = new ArrayList();
        this.characters.add(this.deathsmasher);
        this.characters.add(this.mike);
        this.activeCharacters.add(this.deathsmasher);
        this.activeCharacters.add(this.mike);
        this.enemyFighters.add(this.mike);
        this.random = new Random();
        this.input = new IO();
        this.runGame();
    }

    public void runGame() {
        this.runTurns();
    }

    private void printState() {
        for (Character current : this.activeCharacters) {
            System.out.println(String.valueOf(current.getName()) + " has " + current.getHealth() + " health points and " + current.getMana() + " mana.");
        }
    }

    /*
     * Exception decompiling
     */
    private void runTurns() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.CannotPerformDecode: reachable test BLOCK was exited and re-entered.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.getFarthestReachableInRange(Misc.java:143)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:385)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:65)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:422)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
        // org.benf.cfr.reader.Main.doJar(Main.java:129)
        // org.benf.cfr.reader.Main.main(Main.java:181)
        throw new IllegalStateException("Decompilation failed");
    }

    private String printBattleMenu() {
        String[] options = new String[]{"Attack", "Heal", "Flee"};
        int actionIndex = this.input.printMenu(options);
        String action = options[actionIndex];
        return action;
    }

    private Character checkTurns(int turnDelay) {
        Character active;
        block2 : do {
            try {
                Thread.sleep(turnDelay);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            ArrayList<Character> allFighters = new ArrayList<Character>(this.activeCharacters.size() + this.enemyFighters.size());
            allFighters.addAll(this.activeCharacters);
            Iterator iterator = allFighters.iterator();
            do {
                if (!iterator.hasNext()) continue block2;
            } while ((active = (Character)iterator.next()).updateTurnPoints() != 100.0);
            break;
        } while (true);
        active.setTurnPoints(0);
        System.out.println("It is now " + active.getName() + "'s turn. \n");
        return active;
    }
}

