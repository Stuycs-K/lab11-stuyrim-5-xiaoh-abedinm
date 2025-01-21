public class ArtMajor extends Adventurer{
    int paint, paintMax;
    int buff;
  
    /*the other constructors ultimately call the constructor
    *with all parameters.*/
    public ArtMajor(String name, int hp){
      super(name,hp);
      paintMax = 12;
      paint = paintMax/2;
      buff = 0;
    }
  
    public ArtMajor(String name){
      this(name,24);
    }
  
    public ArtMajor(){
      this("ArtMajor");
    }
  
    /*The next 8 methods are all required because they are abstract:*/
    public String getSpecialName(){
      return "Paint";
    }
  
    public int getSpecial(){
      return paint;
    }
  
    public void setSpecial(int n){
      paint = n;
    }
  
    public int getSpecialMax(){
      return paintMax;
    }
  
    /*Deal 2-7 damage to opponent, restores 2 paint*/
    public String attack(Adventurer other){
      int damage = (int)(Math.random()*6)+2;
      other.applyDamage(damage+getBuff());
      String buff = "";
      if(getBuff() > 0){
        buff = "With a " + getBuff() + "pt buff from a teammate, a total dmg of " + (damage + buff) + " is done.";
      }
      this.setBuff(0);
      restoreSpecial(1);
      return this + " throws a handful of paint at "+ other + " and dealt "+ damage +
      " points of damage alone. " + buff;
    }
  
    /*Deal 3-12 damage to opponent, only if paint is high enough.
    *Reduces paint by 8.
    */
    public String specialAttack(Adventurer other){
      if(getSpecial() >= 8){
        setSpecial(getSpecial()-8);
        int damage = (int)(Math.random()*5+Math.random()*4)+3;
        other.applyDamage(damage+getBuff());
        String buff = "";
        if(getBuff() > 0){
        buff = "With a +" + getBuff() + " damage buff from a teammate, a total damage of " + (damage + getBuff()) + " is done.";
        }
        this.setBuff(0);
        return this + " throws an extra large bucket of paint at "+ other + 
        ". Deals "+ damage +" points of damage alone. " + buff;
      }else{
        return this + " is out of extra large buckets of paint. Instead "+attack(other);
      }
  
    }
    /*Restores 5 special to other*/
    public String support(Adventurer other){
      int hp = 11;
      if (other.getHP() < other.getmaxHP()) {
        int heal = Math.min(hp, other.getmaxHP()-other.getHP());
        other.setHP(other.getHP()+heal);
        return this+" temporarily fakes death and skips their next turn to make the job market less intense "+ 
        "for a fellow teammate. Heals " + other + " for "+heal+" HP.";
      }
      else {
        return this+" temporarily fakes death and skips their next turn to make the job market less intense "+ 
        "for a fellow teammate. Tried to heal " + other + " for "+hp+" HP, but they are already at full health.";
      }
    }
    /*Restores 6 special and 1 hp to self.*/
    public String support(){
      int hp = 3;
      if (this.getHP() < this.getmaxHP()) {
        int heal = Math.min(hp, this.getmaxHP()-this.getHP());
        setHP(getHP()+heal);
        return this + " drinks some paint and heals for " + heal + "HP.";
      }
      else {
        return this + " drinks some paint and tries to heal for " + hp + " HP, but they are already at full health.";
      }
    }
    public int getBuff(){
      return buff;
    }

    public void setBuff(int n){
      buff = n;
    }
  }
  