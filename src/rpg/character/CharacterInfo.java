package rpg.character;

/**
 * Stores the information about a character, such as their stats.
 * 
 * @author Matthew
 *
 */
public class CharacterInfo {
	
	//Base stats, affected by levelling.
	private int baseMaxHealth;
	private int baseAttack;
	private int baseDefence;
	private int baseStrength;
	private int baseIntelligence;
	private int baseFitness;
	private int baseDexterity;
	
	//Stat bonuses given by equipment. E.g. A longsword gives +2 to fitness.
	private int equipMaxHealth;
	private int equipAttack;
	private int equipDefence;
	private int equipStrength;
	private int equipIntelligence;
	private int equipFitness;
	private int equipDexterity;

	/**
	 * Called when equipment changes, or a stat-affecting action (E.g. (de)buff, tactics) occurs.
	 */
	public int updateAttack(double attackModifier){
		return (int)(attackModifier*(baseAttack + equipAttack));
	}
	
	public int updateDefence(double defenceModifier){
		return (int)(defenceModifier*(baseDefence + equipDefence));
	}
	
	public int updateStrength(double strengthModifier){
		return (int)(strengthModifier*(baseStrength + equipStrength));
	}
	
	public int updateIntelligence(double intelligenceModifier){
		return (int)(intelligenceModifier*(baseIntelligence + equipIntelligence));
	}
	
	public int updateFitness(double fitnessModifier){
		return (int)(fitnessModifier*(baseFitness + equipFitness));
	}
	
	public int updateDexterity(double dexterityModifier){
		return (int)(dexterityModifier*(baseDexterity + equipDexterity));
	}
	
	public int updateMaxHealth(double maxHealthModifier){
		 return (int)(maxHealthModifier*(baseMaxHealth + equipMaxHealth));
	}
	
	public int getBaseMaxHealth() {
		return baseMaxHealth;
	}
	public void setBaseMaxHealth(int baseMaxHealth) {
		this.baseMaxHealth = baseMaxHealth;
	}
	public int getBaseAttack() {
		return baseAttack;
	}
	public void setBaseAttack(int baseAttack) {
		this.baseAttack = baseAttack;
	}
	public int getBaseDefence() {
		return baseDefence;
	}
	public void setBaseDefence(int baseDefence) {
		this.baseDefence = baseDefence;
	}
	public int getBaseStrength() {
		return baseStrength;
	}
	public void setBaseStrength(int baseStrength) {
		this.baseStrength = baseStrength;
	}
	public int getBaseIntelligence() {
		return baseIntelligence;
	}
	public void setBaseIntelligence(int baseIntelligence) {
		this.baseIntelligence = baseIntelligence;
	}
	public int getBaseFitness() {
		return baseFitness;
	}
	public void setBaseFitness(int baseFitness) {
		this.baseFitness = baseFitness;
	}
	public int getBaseDexterity() {
		return baseDexterity;
	}
	public void setBaseDexterity(int baseDexterity) {
		this.baseDexterity = baseDexterity;
	}
	public int getEquipMaxHealth() {
		return equipMaxHealth;
	}
	public void setEquipMaxHealth(int equipMaxHealth) {
		this.equipMaxHealth = equipMaxHealth;
	}
	public int getEquipAttack() {
		return equipAttack;
	}
	public void setEquipAttack(int equipAttack) {
		this.equipAttack = equipAttack;
	}
	public int getEquipDefence() {
		return equipDefence;
	}
	public void setEquipDefence(int equipDefence) {
		this.equipDefence = equipDefence;
	}
	public int getEquipStrength() {
		return equipStrength;
	}
	public void setEquipStrength(int equipStrength) {
		this.equipStrength = equipStrength;
	}
	public int getEquipIntelligence() {
		return equipIntelligence;
	}
	public void setEquipIntelligence(int equipIntelligence) {
		this.equipIntelligence = equipIntelligence;
	}
	public int getEquipFitness() {
		return equipFitness;
	}
	public void setEquipFitness(int equipFitness) {
		this.equipFitness = equipFitness;
	}
	public int getEquipDexterity() {
		return equipDexterity;
	}
	public void setEquipDexterity(int equipDexterity) {
		this.equipDexterity = equipDexterity;
	}
}

