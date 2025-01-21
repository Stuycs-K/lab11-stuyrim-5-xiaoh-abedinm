import java.util.*;
public class Game{
  private static final int WIDTH = 80;
  private static final int HEIGHT = 30;
  private static final int BORDER_COLOR = Text.BLACK;
  private static final int BORDER_BACKGROUND = Text.WHITE + Text.BACKGROUND;
  private static final int HISTORY_SIZE = 6;
  private static LinkedList<String> turnHistory = new LinkedList<String>();

  public static void main(String[] args) {
    run();
  }

  // DRAWING FUNCTIONS
  public static void drawBackground(){
    for (int row = 1; row < 31; row++){
      for (int c = 1; c < 81; c++){
        if (row == 1 || row == 5 || row == 30 || row == 24 || row == 28){
          Text.go(row, c);
          System.out.print(Text.colorize("═", BORDER_COLOR+BORDER_BACKGROUND));
        }
        if (c == 1 || c == 80){
          Text.go(row, c);
          System.out.print(Text.colorize("║", BORDER_COLOR+BORDER_BACKGROUND));
        }
      }
      if (row == 1){
        Text.go(row, 1);
        System.out.print(Text.colorize("╔", BORDER_COLOR+BORDER_BACKGROUND));
        Text.go(row, 80);
        System.out.print(Text.colorize("╗", BORDER_COLOR+BORDER_BACKGROUND));
      }
      if (row == 30){
        Text.go(row, 1);
        System.out.print(Text.colorize("╚", BORDER_COLOR+BORDER_BACKGROUND));
        Text.go(row, 80);
        System.out.print(Text.colorize("╝", BORDER_COLOR+BORDER_BACKGROUND));
      }
      if (row == 5 || row == 24 || row == 28){
        Text.go(row, 1);
        System.out.print(Text.colorize("╠", BORDER_COLOR+BORDER_BACKGROUND));
        Text.go(row, 80);
        System.out.print(Text.colorize("╣", BORDER_COLOR+BORDER_BACKGROUND));
      }
    }
  }

  public static void drawScreen(ArrayList<Adventurer> party, ArrayList<Adventurer> enemies){
    removeDeadAdventurers(enemies);
    removeDeadAdventurers(party);

    drawParty(party, HEIGHT-5);
    drawParty(enemies, 2);
    drawBackground();
  }

  public static void drawText(String s,int startRow, int startCol){
    Text.go(startRow, startCol);
    System.out.print(s);
  }

  public static void drawTurnHistory() {
    int startRow = 6;
    for (int i = 0; i < turnHistory.size();i++) {
      TextBox(startRow+i*3, 2, 77, 3, turnHistory.get(i));
    }
  }

  public static void drawParty(ArrayList<Adventurer> party,int startRow){
    if (party.isEmpty()) {
      return;
    }
    int width = WIDTH / party.size();
    for (int i = 0; i < party.size(); i++) {
      Adventurer temp = party.get(i);
      int startCol = i * width + 2;

      for (int row = startRow; row < startRow + 3; row++) {
        Text.go(row, startCol);
        for (int repeat = 0; repeat < width; repeat++) {
          System.out.print(" ");
        }
      }

      drawText(temp.getName(), startRow, startCol);

      String hp = "HP: " + colorByPercent(temp.getHP(), temp.getmaxHP());
      drawText(hp, startRow + 1, startCol);

      String special = temp.getSpecialName() + ": " + colorSpecial(temp.getSpecial(), 8);
      drawText(special, startRow + 2, startCol);
    }
  }

  public static void TextBox(int row, int col, int width, int height, String text){
    String[] words = text.split(" ");
    int r = row;
    int c = col;

    for (int i = 0; i < height; i++) {
      Text.go(row + i, col);
      System.out.print(" ".repeat(width));
    }

    for (String word : words) {
      String plainWord = Text.stripANSI(word);
      int wordLength = plainWord.length();
      if (c + wordLength > col + width) {
        r++;
        c = col;
      }
      if (r >= row + height) {
        return;
      }
        Text.go(r, c);
        System.out.print(word + " ");
        c += wordLength + 1;
    }
  }

  // ADDING TO PREVIOUS TURNS LIST
  public static void addTurnMessage(String text) {
    if (turnHistory.size() >= HISTORY_SIZE) {
      turnHistory.removeLast();
    }
    turnHistory.addFirst(text);
  }

  // COLOR FUNCTIONS
  public static String colorByPercent(int hp, int maxHP){
    String output = String.format("%2s", hp+"")+"/"+String.format("%2s", maxHP+"");
    double hpPercent = (double) hp / maxHP;
    if (hpPercent < 0.25) {
      return(Text.colorize(output, Text.RED));
    }
    else if (hpPercent < 0.75) {
      return(Text.colorize(output, Text.YELLOW));
    }
    else {
      return(Text.colorize(output, Text.GREEN));
    }
  }

  public static String colorSpecial(int special, int requiredSpecial) {
    String output = "" + special;
    if (special >= requiredSpecial) {
      return(Text.colorize(output, Text.BLUE));
    }
    else {
      return output;
    }
  }

  // ADVENTURER FUNCTIONS
  public static Boolean isDead(Adventurer adventurer){
    return (adventurer.getHP() <= 0); 
  }

  public static void removeDeadAdventurers(ArrayList<Adventurer> adventurers) {
    for (int i = 0; i < adventurers.size(); i++) {
      if (isDead(adventurers.get(i))) {
        adventurers.remove(i);
        i--;
      }
    }
  }

  public static Adventurer createRandomAdventurer(){
    Adventurer[] randomAdventurers = new Adventurer[] {
      new ArtMajor(),
      new CSMajor(),
      new PhilosophyMajor(),
    };
    int randomIndex = (int) (Math.random()*randomAdventurers.length);
    return randomAdventurers[randomIndex];
  }

  // USER INPUT
  public static String userInput(Scanner in, int start){
      int row = 29;
      int col = 2 + start;
      Text.go(row,col);

      Text.showCursor();
      String input = in.nextLine();

      Text.go(row,col);
      for (int i = 0; i < 78-start; i++) {
        System.out.print(" ");
      }
      Text.go(row,col);
      Text.hideCursor();

      return input;
  }

  // START AND END GAME
  public static void quit(){
    Text.clear();
    Text.reset();
    Text.showCursor();
    Text.go(1,1);
  }

  public static void run(){
    Text.hideCursor();
    Text.clear();

    // ENEMY ADVENTURERS
    ArrayList<Adventurer> enemies = new ArrayList<Adventurer>();

    // List of boss names
    String[] ceoNames = {
      "Elon Musk", "Jeff Bezos", "Tim Cook", "Sundar Pichai", "Satya Nadella", 
      "Mark Zuckerberg", "Larry Page", "Sergey Brin", "Warren Buffett", 
      "Jack Dorsey", "Sheryl Sandberg", "Brian Chesky", "Reed Hastings", 
      "Travis Kalanick", "Evan Spiegel"
    };
    String bossName = ceoNames[(int)(Math.random() * ceoNames.length)];

    int numEnemies = (int)(Math.random()*3)+1;

    if (numEnemies == 1){
      enemies.add(new Boss(bossName));
    } else {
      for (int i = 0; i < 3; i++) {
        enemies.add(createRandomAdventurer());
      }
    }

    // PARTY ADVENTURERS
    ArrayList<Adventurer> party = new ArrayList<>();
    Scanner in = new Scanner(System.in);

    int numAdventurers = 0;
    while (numAdventurers < 2 || numAdventurers > 4) {
        System.out.print("Enter the number of adventurers in your party (2-4): ");
        numAdventurers = Integer.parseInt(in.nextLine());
        if (numAdventurers < 2 || numAdventurers > 4) {
            System.out.println("Error: Number of adventurers must be between 2 and 4. Please try again.");
        }
    }

    for (int i = 0; i < numAdventurers; i++) {
      int choice = 0;
      while (choice < 1 || choice > 3) { 
        System.out.print("Choose adventurer type for party member " + (i+1) + " (1: ArtMajor, 2: CSMajor, 3: PhilosophyMajor, Empty: Random): ");
        String input = in.nextLine();

        if (input.isEmpty()) {
          choice = (int) (Math.random() * 3) + 1;
        } else {
            try {
              choice = Integer.parseInt(input);
              if (choice < 1 || choice > 3) {
                  System.out.println(Text.colorize("Error: Adventurer type must be one of the choices. Please try again.", Text.RED));
              }
            } catch (NumberFormatException e) {
              System.out.println(Text.colorize("Error: Invalid input. Please enter a number between 1 and 3.", Text.RED));
            }
        }
      }

      System.out.print("Enter a name for your adventurer (empty for default): ");
      String name = in.nextLine();

      if (choice == 1) {
        if (name.isEmpty()) {
          party.add(new ArtMajor());
        }
        else {
          party.add(new ArtMajor(name));
        }
      }
      else if (choice == 2) {
        if (name.isEmpty()) {
          party.add(new CSMajor());
        }
        else {
          party.add(new CSMajor(name));
        }
      }
      else if (choice == 3) {
        if (name.isEmpty()) {
          party.add(new PhilosophyMajor());
        }
        else {
          party.add(new PhilosophyMajor(name));
        }
      }
      else {
        if (name.isEmpty()) {
          party.add(new ArtMajor());
        }
        else {
          party.add(new ArtMajor(name));
        }
      }
    }

    // PRE-LOOP
    Text.clear();
    boolean partyTurn = true;
    int whichPlayer = 0;
    int whichOpponent = 0;
    int turn = 0;
    String input = "";
    drawScreen(party, enemies);
    String prompt = "Enter command for " + party.get(whichPlayer).getName() + " (attack #/special #/support #/quit): ";
    TextBox(29, 2, 77, 1, prompt);

    //MAIN GAME LOOP
    while(! (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit"))){
      if (whichPlayer >= party.size()) {
        whichPlayer = 0;
      }

      input = userInput(in, prompt.length());

      // PARTY TURN
      if(partyTurn){
        boolean breakOut = false;
        boolean validInput = false;
        while (validInput == false){
          if(input.startsWith("attack ") || input.startsWith("a ")){
            String[] temp = input.split(" ");
            int num = Integer.valueOf(temp[1])-1;
            Adventurer target = enemies.get(num);
            Adventurer attacker = party.get(whichPlayer);
            String result = attacker.attack(target);
            addTurnMessage(result);
            TextBox(6, 2, 77,  3, result);
            validInput = true;
          }
          else if(input.startsWith("special ") || input.startsWith("sp ")){
            String[] temp = input.split(" ");
            int num = Integer.valueOf(temp[1])-1;
            Adventurer target = enemies.get(num);
            Adventurer attacker = party.get(whichPlayer);
            String result = attacker.specialAttack(target);
            addTurnMessage(result);
            TextBox(6, 2, 77,  3, result);
            validInput = true;
          }
          else if(input.startsWith("su ") || input.startsWith("support ")){
            String result = "";
            String[] temp = input.split(" ");
            if (temp.length > 1){
              int num = Integer.valueOf(temp[1])-1;
              Adventurer target = party.get(num);
              Adventurer attacker = party.get(whichPlayer);
              result = attacker.support(target);
            }
            else{
              result = (party.get(whichPlayer)).support();
            }
            addTurnMessage(result);
            TextBox(6, 2, 77,  3, result);
            validInput = true;
          }
          else if(input.startsWith("attack") || input.startsWith("a")){
            Adventurer target = enemies.get((int)(Math.random()*enemies.size()));
            Adventurer attacker = party.get(whichPlayer);
            String result = attacker.attack(target);
            addTurnMessage(result);
            TextBox(6, 2, 77,  3, result);
            validInput = true;
          }
          else if(input.startsWith("special") || input.startsWith("sp")){
            Adventurer target = enemies.get((int)(Math.random()*enemies.size()));
            Adventurer attacker = party.get(whichPlayer);
            String result = attacker.specialAttack(target);
            addTurnMessage(result);
            TextBox(6, 2, 77,  3, result);
            validInput = true;
          }
          else if(input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")){
            breakOut = true;
            break;
          }
          else{
            String errMessage = "INVALID INPUT. Enter command for " + party.get(whichPlayer).getName() + " (attack/special/support/quit): ";
            TextBox(29, 2, 77, 1, Text.colorize(errMessage, Text.RED));
            input = userInput(in, errMessage.length());
          }
        }
        if (breakOut == true){
          break;
        }

        whichPlayer++;

        if(whichPlayer < party.size()){
          prompt = "Enter command for " + party.get(whichPlayer).getName() + " (attack #/special #/support #/quit): ";
          TextBox(29, 2, 77, 1, prompt);
        }else{
          prompt = "Press enter to see enemy team's turn";
          TextBox(29, 2, 77, 1, prompt);
          partyTurn = false;
          whichOpponent = 0;
        }
      // ENEMY TURN
      }else{
        Adventurer attacker = enemies.get(whichOpponent);
        int action = (int) (Math.random()*2);

        Adventurer target;
        String result = "";

        if (attacker.getSpecial() >= 8) {
          target = party.get((int)(Math.random()*party.size()));
          result = attacker.specialAttack(target);
        }
        else {
          if (action == 0) {
            target = party.get((int)(Math.random()*party.size()));
            result = attacker.attack(target);
          }
          else {
            target = enemies.get((int)(Math.random()*enemies.size()));
            result = attacker.support(target);
          }
        }
        
        addTurnMessage(Text.colorize(result, Text.RED));
        TextBox(6, 2, 76,  3, Text.colorize(result, Text.RED));

        whichOpponent++;

        if(whichOpponent >= enemies.size()){
          whichPlayer = 0;
          whichOpponent=0;
          partyTurn=true;
          turn++;
          prompt = "Enter command for " + party.get(whichPlayer).getName() + " (attack #/special #/support #/quit): ";
          TextBox(29,2,77,1,prompt);
        }
      }

      // PREPARE FOR NEXT TURN
      removeDeadAdventurers(enemies);
      removeDeadAdventurers(party);

      if (enemies.isEmpty()) {
          Text.clear();
          Text.go(10, 10);
          System.out.println(Text.colorize("Congratulations!", Text.GREEN, Text.BOLD));
          Text.go(12, 10);
          System.out.println(Text.colorize("You Won after " + turn + " turns! All enemies are defeated.", Text.GREEN));
          Text.go(14, 10);
          System.out.println(Text.colorize("Press Enter to exit.", Text.YELLOW));
          new Scanner(System.in).nextLine();
          return;
      } else if (party.isEmpty()) {
        Text.clear();
        Text.go(10, 10);
        System.out.println(Text.colorize("Game Over!", Text.RED, Text.BOLD));
        Text.go(12, 10);
        System.out.println(Text.colorize("You Lost after " + turn + " turns! All party members are dead.", Text.RED));
        Text.go(14, 10);
        System.out.println(Text.colorize("Press Enter to exit.", Text.YELLOW));
        new Scanner(System.in).nextLine();
        return;
        }

      drawScreen(party, enemies);
      drawTurnHistory();

    }
    // END OF MAIN GAME LOOP
    quit();
  }
}
