public class Boss extends Adventurer{
  int rage, rageMax, buff;
  boolean skip;

  public Boss(String name, int hp){
    super(name,hp);
    rageMax = 14;
    rage = rageMax/2;
    buff = 0;
  }

  public Boss(String name){
    this(name,60);
  }

  public Boss(){
    this("Hugh");
  }

  /*The next 8 methods are all required because they are abstract:*/
  public String getSpecialName(){
    return "Rage";
  }

  public int getSpecial(){
    return rage;
  }

  public void setSpecial(int n){

    rage = n;
  }

  public int getSpecialMax(){
    return rageMax;
  }

  /*Deal 2-7 damage to opponent, restores 2 caffeine*/
  public String attack(Adventurer other){
    if (this.getSkip() == true){
      this.setSkip(false);
      return this + " is currently stunned and cannot perform any actions.";
    }
    int damage = (int)(Math.random()*6)+4;
    other.applyDamage(damage+getBuff());
    String buff = "";
    if(getBuff() > 0){
      buff = "With a " + getBuff() + "pt buff from a teammate, a total dmg of " + (damage + buff) + " is done. ";
    }
    this.setBuff(0);
    restoreSpecial(2);
    return this + " underpays "+ other + " and dealt "+ damage +
    " points of damage alone. " + buff + other + " cannot afford dinner.";
  }

  //hurt or hinder the target adventurer
  //heal or buff the target adventurer
  public String support(Adventurer other){
    if (this.getSkip() == true){
      this.setSkip(false);
      return this + " is currently stunned and cannot perform any actions.";
    }
    int heal = (int)(Math.random()*5)+1;
    other.setHP(other.getHP() + heal);
    return this + " granted " + other + " a raise and PTO. " + other + " heals by " + 
    heal + "hp.";
  };

  //heal or buff self
  public String support(){
    if (this.getSkip() == true){
      this.setSkip(false);
      return this + " is currently stunned and cannot perform any actions.";
    }
    int heal = (int)(Math.random()*7)+5;
    this.setHP(this.getHP() + heal);
    return this + " degrades another and increases self esteem (hp) by "+ heal +
    " points. ";
  }

  public String specialAttack(Adventurer other){
    if (this.getSkip() == true){
      this.setSkip(false);
      return this + " is currently stunned and cannot perform any actions.";
    }
    if(getSpecial() >= 8){
      setSpecial(getSpecial()-8);
      int damage = (int)(Math.random()*5+Math.random()*7)+3;
      other.applyDamage(damage+getBuff());
      String buff = "";
      if(getBuff() > 0){
        buff = " thanks to a " + getBuff() + "pt buff from a teammate. ";
      }
      this.setBuff(0);
      other.setSkip(true);
      return this + " fired "+ other + ", dealing "+ damage +" points of damage" + buff 
      + ". Immobilizes " + other;
    }else{
      return this + "does not have enough rage to fire people. Instead, "+attack(other);
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
