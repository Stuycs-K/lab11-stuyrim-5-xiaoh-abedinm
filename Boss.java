public class Boss extends Adventurer{
  int rage, rageMax;

  public Boss(String name, int hp, String language){
    super(name,hp);
    rageMax = 12;
    rage = rageMax/2;
  }

  public Boss(String name, int hp){
    this(name,hp);
  }

  public Boss(String name){
    this(name,24);
  }

  public Boss(){
    this("Carmack");
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
    int damage = (int)(Math.random()*6)+2;
    other.applyDamage(damage);
    restoreSpecial(2);
    return this + " attacked "+ other + " and dealt "+ damage +
    " points of damage. They then take a sip of their coffee.";
  }

  //hurt or hinder the target adventurer
  //heal or buff the target adventurer
  public abstract String support(Adventurer other);

  //heal or buff self
  public abstract String support();

  //hurt or hinder the target adventurer, consume some special resource
  public abstract String specialAttack(Adventurer other);
}
