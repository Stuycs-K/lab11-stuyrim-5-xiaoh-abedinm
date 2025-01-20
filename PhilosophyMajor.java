public class PhilosophyMajor extends Adventurer{
  int purpose, purposeMax;

  String[] quotes = new String[]{""};

  /*the other constructors ultimately call the constructor
  *with all parameters.*/
  public PhilosophyMajor(String name, int hp){
    super(name,hp);
    purposeMax = 12;
    purpose = purposeMax/2;
  }


  public PhilosophyMajor(String name){
    this(name,24);
  }

  public PhilosophyMajor(){
    this("John");
  }

  /*The next 8 methods are all required because they are abstract:*/
  public String getSpecialName(){
    return "purpose";
  }

  public int getSpecial(){
    return purpose;
  }

  public void setSpecial(int n){
    purpose = n;
  }

  public int getSpecialMax(){
    return purposeMax;
  }

  /*Deal 2-7 damage to opponent, restores 2 caffeine*/
  public String attack(Adventurer other){
    int damage = (int)(Math.random()*6)+2;
    other.applyDamage(damage);
    restoreSpecial(1);
    return this + " wastes "+ other + "'s time to talk about nonsense, dealing "+ damage +
    " points of damage.";
  }

  /*Need to make opponent skip a turn
  */
  public String specialAttack(Adventurer other){
    if(getSpecial() >= 8){
      setSpecial(getSpecial()-8);
      int damage = (int)(Math.random()*5+Math.random()*3)+3;
      other.applyDamage(damage);
      return this + " sends "+other+
      " into an existential crisis. "+
      " This paralyzes "+other+" for 1 turn and does "+ damage +" points of damage.";
    }else{
      return this + "is currently wallowing in their own existential dread. Instead "+attack(other);
    }

  }

  // ***NEED TO SOMEHOW BUFF TEAMMATES ON THEIR NEXT TURN**
  public String support(Adventurer other){
    int buff = (int)(Math.random()*5)+2;
    return "'No one saves us but ourselves.' Inspires and buffs "+ other +" on their next turn. ";
  }
  /*Restores 6 special and 1 hp to self.*/
  public String support(){
    int hp = 4;
    setHP(getHP()+hp);
    return this+" stops wallowing in existential dread for a moment. Heals self for "+hp + "hp";
  }
}
