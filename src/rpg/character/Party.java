/*
 * Decompiled with CFR 0_114.
 */
package rpg.character;

import java.util.ArrayList;
import rpg.character.PartyMember;
import rpg.world.Item;

public class Party {
    private static Party instance = null;
    long money;
    ArrayList<Item> inventory;
    PartyMember[] roster;
    PartyMember[] activeRoster;
    private int maxRoster = 10;
    private int maxActive = 4;

    private Party() {
        this.roster = new PartyMember[this.maxRoster];
        this.activeRoster = new PartyMember[this.maxActive];
        this.inventory = new ArrayList(30);
    }

    public Party makeParty() {
        if (instance == null) {
            instance = new Party();
        }
        return instance;
    }
}

