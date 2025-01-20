public class ArtMajor extends Adventurer{
    int paint, paintMax;
  
    /*the other constructors ultimately call the constructor
    *with all parameters.*/
    public ArtMajor(String name, int hp){
      super(name,hp);
      paintMax = 12;
      paint = paintMax/2;
    }
  
    public ArtMajor(String name){
      this(name,24);
    }
  
    public ArtMajor(){
      this("ArtMajor");
    }
  
    /*The next 8 methods are all required because they are abstract:*/
    public String getSpecialName(){
      return "paint";
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
      other.applyDamage(damage);
      restoreSpecial(1);
      return this + " throws a handful of paint at "+ other + " and dealt "+ damage +
      " points of damage.";
    }
  
    /*Deal 3-12 damage to opponent, only if paint is high enough.
    *Reduces paint by 8.
    */
    public String specialAttack(Adventurer other){
      if(getSpecial() >= 8){
        setSpecial(getSpecial()-8);
        int damage = (int)(Math.random()*5+Math.random()*4)+3;
        other.applyDamage(damage);
  
        return this + " throws an extra large bucket of paint at "+ other + 
        ". Deals "+ damage +" points of damage.";
      }else{
        return this + " is out of extra large buckets of paint paint. Instead "+attack(other);
      }
  
    }
    /*Restores 5 special to other*/
    public String support(Adventurer other){
      int hp = 12;
      other.setHP(other.getHP()+hp);
      return this+" temporarily fakes death (skips next turn) to make the job market less intense "+ 
      "for a fellow teammate. Heals "+other + " for "+hp+"HP";
      /* 
      return this + "chugged a coffee and worked a shift at McDonalds. Used wages to donate "
      + other.restoreSpecial(5)+" "+other.getSpecialName() + " to " + other;
      */
    }
    /*Restores 6 special and 1 hp to self.*/
    public String support(){
      int hp = 3;
      setHP(getHP()+hp);
      return this + " drinks some paint and heals for " + hp + "hp";
    }
  }
  