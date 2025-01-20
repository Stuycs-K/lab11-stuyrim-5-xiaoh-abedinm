public class Boss extends Adventurer{
  int rage, rageMax;

  public Boss(String name, int hp){
    super(name,hp);
    rageMax = 12;
    rage = rageMax/2;
  }

  public Boss(String name){
    this(name,24);
  }

  public Boss(){
    this("Hugh");
  }

  /*The next 8 methods are all required because they are abstract:*/
  public String getSpecialName(){
    return "rage";
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
    int damage = (int)(Math.random()*6)+3;
    other.applyDamage(damage);
    restoreSpecial(2);
    return this + " underpays "+ other + " and dealt "+ damage +
    " points of damage. " + other + " cannot afford dinner.";
  }

  //hurt or hinder the target adventurer
  //heal or buff the target adventurer
  public String support(Adventurer other){
    int heal = (int)(Math.random()*3)+1;
    other.setHP(other.getHP() + heal);
    return this + " granted " + other + " a raise and PTO. " + other + " heals by " + 
    heal + "hp.";
  };

  //heal or buff self
  public String support(){
    int heal = (int)(Math.random()*5)+2;
    this.setHP(this.getHP() + heal);
    return this + " degrades another and increases self esteem (hp) by "+ heal +
    " points. ";
  }

  public String specialAttack(Adventurer other){
    if(getSpecial() >= 8){
      setSpecial(getSpecial()-8);
      int damage = (int)(Math.random()*5+Math.random()*5)+3;
      other.applyDamage(damage);
      return this + " fired "+ other + ", dealing "+ damage +" points of damage.";
    }else{
      return this + "does not have enough rage to fire people. Instead, "+attack(other);
    }
  }
}
