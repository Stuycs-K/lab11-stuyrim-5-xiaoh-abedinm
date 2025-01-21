public class CSMajor extends Adventurer{
  int caffeine, caffeineMax;
  String preferredLanguage;
  int buff;

  /*the other constructors ultimately call the constructor
  *with all parameters.*/
  public CSMajor(String name, int hp, String language){
    super(name,hp);
    caffeineMax = 12;
    caffeine = caffeineMax/2;
    preferredLanguage = language;
    buff = 0;
  }

  public CSMajor(String name, int hp){
    this(name,hp,"C#");
  }

  public CSMajor(String name){
    this(name,24);
  }

  public CSMajor(){
    this("CSMajor");
  }

  /*The next 8 methods are all required because they are abstract:*/
  public String getSpecialName(){
    return "Caffeine";
  }

  public int getSpecial(){
    return caffeine;
  }

  public void setSpecial(int n){
    caffeine = n;
  }

  public int getSpecialMax(){
    return caffeineMax;
  }

  /*Deal 2-7 damage to opponent, restores 2 caffeine*/
  public String attack(Adventurer other){
    int damage = (int)(Math.random()*6)+2;
    other.applyDamage(damage+getBuff());
    String buff = "";
    if(getBuff() > 0){
      buff = "With a +" + getBuff() + " damage buff from a teammate, a total damage of "
      + (damage + getBuff()) + " is done.";
    }
    this.setBuff(0);
    restoreSpecial(1);
    return this + " doesn't shower for "+ damage +
    " days. Deals "+ damage + " points of stink damage to " + other + " alone. " + buff;
  }

  /*Deal 3-12 damage to opponent, only if caffeine is high enough.
  *Reduces caffeine by 8.
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

      int steal = (int)(Math.random()*5)+3;
      if (steal > other.getSpecial()){
        steal = other.getSpecial();
      }
      setSpecial(getSpecial()+steal);
      other.setSpecial(other.getSpecial() - steal);

      return this + " used their "+preferredLanguage+
      " skills to hack "+ other + "'s mind. Transferred "+steal+ " " + other.getSpecialName()+
      " to themselves and dealt "+ damage +" points of damage alone. " + buff;
    }else{
      return "Not enough caffeine to use the ultimate code. Instead "+attack(other);
    }

  }
  /*Restores 5 special to other*/
  public String support(Adventurer other){
    return this + " chugged a coffee and worked a shift at McDonalds. Used wages to donate "
    + other.restoreSpecial(5)+" "+other.getSpecialName() + " to " + other + ".";
  }
  /*Restores 6 special and 1 hp to self.*/
  public String support(){
    int hp = 3;
    if (this.getHP() < this.getmaxHP()) {
      int heal = Math.min(hp, this.getmaxHP()-this.getHP());
      setHP(getHP()+heal);
      return this+" drinks a coffee to restore "+restoreSpecial(2)+" "
      + getSpecialName()+ " and "+heal+" HP.";
    }
    else {
      return this+" drinks a coffee to restore "+restoreSpecial(2)+" "
      + getSpecialName()+ " and tried to heal, but they are already at full health.";
    }
  }
  public int getBuff(){
    return buff;
  }
  public void setBuff(int n){
    buff = n;
  }
}
