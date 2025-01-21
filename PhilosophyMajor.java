public class PhilosophyMajor extends Adventurer{
  int purpose, purposeMax, buff;
  boolean skip;

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
    return "Purpose";
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
    if (this.getSkip() == true){
      this.setSkip(false);
      return this + " is currently stunned and cannot perform any actions.";
    }
    int damage = (int)(Math.random()*6)+2;
    other.applyDamage(damage+getBuff());

    String buff = "";
    int total = getBuff() + damage;
    if(getBuff() > 0){
      buff = " total, thanks to a " + getBuff() + "pt buff from a teammate.";
    }
    this.setBuff(0);
    restoreSpecial(1);

    return this + " wastes "+ other + "'s time to talk about nonsense, dealing "+ total +
    " points of damage" + buff;
  }

  /*Need to make opponent skip a turn
  */
  public String specialAttack(Adventurer other){
    if (this.getSkip() == true){
      this.setSkip(false);
      return this + " is currently stunned and cannot perform any actions.";
    }
    if(getSpecial() >= 8){
      setSpecial(getSpecial()-8);
      int damage = (int)(Math.random()*5+Math.random()*3)+3;
      other.applyDamage(damage+getBuff());

      String buff = "";
      int total = getBuff() + damage;
      if(getBuff() > 0){
        buff = "With a +" + getBuff() + " damage buff from a teammate, a total damage of " + (damage + getBuff()) + " is done.";
      }
      this.setBuff(0);
      other.setSkip(true);

      return this + " sends "+other+
      " into an existential crisis. "+
      "This paralyzes "+other+" for 1 turn and does "+ total +" points of damage" + buff;
    }else{
      return this + " is currently wallowing in their own existential dread. Instead, "+attack(other);
    }

  }

  public String support(Adventurer other){
    if (this.getSkip() == true){
      this.setSkip(false);
      return this + " is currently stunned and cannot perform any actions.";
    }
    int boost = (int)(Math.random()*3)+1;
    other.setBuff(boost);
    return "'No one saves us but ourselves.' "+ this +" inspires and buffs "+ other +" by "+ boost +"dmg on their next turn. ";
  }
  
  public String support(){
    if (this.getSkip() == true){
      this.setSkip(false);
      return this + " is currently stunned and cannot perform any actions.";
    }
    int hp = 3;
    if (this.getHP() < this.getmaxHP()) {
      int heal = Math.min(hp, this.getmaxHP()-this.getHP());
      setHP(getHP()+heal);
      return this+" stops wallowing in existential dread for a moment. Heals self for "+ heal + " HP.";
    }
    else {
      return this+" stops wallowing in existential dread for a moment. Tries to heal self for "+hp + " HP, but they are already at full health.";
    }
  }

  public int getBuff(){
    return buff;
  }
  public void setBuff(int n){
    buff = n;
  }

  public boolean getSkip(){
    return skip;
  }
  public void setSkip(boolean n){
    skip = n;
  }
}
