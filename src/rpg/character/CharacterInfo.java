/*
 * Decompiled with CFR 0_114.
 */
package rpg.character;

public class CharacterInfo {
    private int baseMaxHealth;
    private int baseAttack;
    private int baseDefence;
    private int baseStrength;
    private int baseIntelligence;
    private int baseFitness;
    private int baseDexterity;
    private int equipMaxHealth;
    private int equipAttack;
    private int equipDefence;
    private int equipStrength;
    private int equipIntelligence;
    private int equipFitness;
    private int equipDexterity;

    public int updateAttack(double attackModifier) {
        return (int)(attackModifier * (double)(this.baseAttack + this.equipAttack));
    }

    public int updateDefence(double defenceModifier) {
        return (int)(defenceModifier * (double)(this.baseDefence + this.equipDefence));
    }

    public int updateStrength(double strengthModifier) {
        return (int)(strengthModifier * (double)(this.baseStrength + this.equipStrength));
    }

    public int updateIntelligence(double intelligenceModifier) {
        return (int)(intelligenceModifier * (double)(this.baseIntelligence + this.equipIntelligence));
    }

    public int updateFitness(double fitnessModifier) {
        return (int)(fitnessModifier * (double)(this.baseFitness + this.equipFitness));
    }

    public int updateDexterity(double dexterityModifier) {
        return (int)(dexterityModifier * (double)(this.baseDexterity + this.equipDexterity));
    }

    public int updateMaxHealth(double maxHealthModifier) {
        return (int)(maxHealthModifier * (double)(this.baseMaxHealth + this.equipMaxHealth));
    }

    public int getBaseMaxHealth() {
        return this.baseMaxHealth;
    }

    public void setBaseMaxHealth(int baseMaxHealth) {
        this.baseMaxHealth = baseMaxHealth;
    }

    public int getBaseAttack() {
        return this.baseAttack;
    }

    public void setBaseAttack(int baseAttack) {
        this.baseAttack = baseAttack;
    }

    public int getBaseDefence() {
        return this.baseDefence;
    }

    public void setBaseDefence(int baseDefence) {
        this.baseDefence = baseDefence;
    }

    public int getBaseStrength() {
        return this.baseStrength;
    }

    public void setBaseStrength(int baseStrength) {
        this.baseStrength = baseStrength;
    }

    public int getBaseIntelligence() {
        return this.baseIntelligence;
    }

    public void setBaseIntelligence(int baseIntelligence) {
        this.baseIntelligence = baseIntelligence;
    }

    public int getBaseFitness() {
        return this.baseFitness;
    }

    public void setBaseFitness(int baseFitness) {
        this.baseFitness = baseFitness;
    }

    public int getBaseDexterity() {
        return this.baseDexterity;
    }

    public void setBaseDexterity(int baseDexterity) {
        this.baseDexterity = baseDexterity;
    }

    public int getEquipMaxHealth() {
        return this.equipMaxHealth;
    }

    public void setEquipMaxHealth(int equipMaxHealth) {
        this.equipMaxHealth = equipMaxHealth;
    }

    public int getEquipAttack() {
        return this.equipAttack;
    }

    public void setEquipAttack(int equipAttack) {
        this.equipAttack = equipAttack;
    }

    public int getEquipDefence() {
        return this.equipDefence;
    }

    public void setEquipDefence(int equipDefence) {
        this.equipDefence = equipDefence;
    }

    public int getEquipStrength() {
        return this.equipStrength;
    }

    public void setEquipStrength(int equipStrength) {
        this.equipStrength = equipStrength;
    }

    public int getEquipIntelligence() {
        return this.equipIntelligence;
    }

    public void setEquipIntelligence(int equipIntelligence) {
        this.equipIntelligence = equipIntelligence;
    }

    public int getEquipFitness() {
        return this.equipFitness;
    }

    public void setEquipFitness(int equipFitness) {
        this.equipFitness = equipFitness;
    }

    public int getEquipDexterity() {
        return this.equipDexterity;
    }

    public void setEquipDexterity(int equipDexterity) {
        this.equipDexterity = equipDexterity;
    }
}

