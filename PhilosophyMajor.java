public class PhilosophyMajor extends Adventurer{
  int purpose, purposeMax;
  int buff;

  String[] quotes = new String[]{""};

  /*the other constructors ultimately call the constructor
  *with all parameters.*/
  public PhilosophyMajor(String name, int hp){
    super(name,hp);
    purposeMax = 12;
    purpose = purposeMax/2;
    buff =0;
  }

  public PhilosophyMajor(String name){
    this(name,24);
  }

  public PhilosophyMajor(){
    this("PhilosophyMajor");
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
    other.applyDamage(damage+getBuff());
    this.setBuff(0);
    String buff = "";
    if(getBuff() > 0){
      buff = "With a " + getBuff() + "pt buff from a teammate, a total dmg of " + (damage + buff) + " is done.";
    }
    this.setBuff(0);
    restoreSpecial(1);
    return this + " wastes "+ other + "'s time to talk about nonsense, dealing "+ damage +
    " points of damage alone. " + buff;
  }

  /*Need to make opponent skip a turn
  */
  public String specialAttack(Adventurer other){
    if(getSpecial() >= 8){
      setSpecial(getSpecial()-8);
      int damage = (int)(Math.random()*5+Math.random()*3)+3;
      other.applyDamage(damage+getBuff());
      String buff = "";
      if(getBuff() > 0){
        buff = "With a " + getBuff() + "pt buff from a teammate, a total dmg of " + (damage + buff) + " is done.";
      }
      this.setBuff(0);
      return this + " sends "+other+
      " into an existential crisis. "+
      " This paralyzes "+other+" for 1 turn and does "+ damage +" points of damage alone. " + buff;
    }else{
      return this + " is currently wallowing in their own existential dread. Instead "+attack(other);
    }

  }

  // ***NEED TO SOMEHOW BUFF TEAMMATES ON THEIR NEXT TURN**
  public String support(Adventurer other){
    int boost = (int)(Math.random()*3)+1;
    other.setBuff(boost);
    return "'No one saves us but ourselves.' "+ this +" inspires and buffs "+ other +" by "+ boost +"dmg on their next turn. ";
  }
  
  public String support(){
    int hp = 3;
    setHP(getHP()+hp);
    return this+" stops wallowing in existential dread for a moment. Heals self for "+hp + "hp";
  }

  public int getBuff(){
    return buff;
  }
  public void setBuff(int n){
    buff = n;
  }
}
