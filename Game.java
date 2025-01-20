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

  //Display the borders of your screen that will not change.
  //Do not write over the blank areas where text will appear or parties will appear.
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

  //Display a line of text starting at
  //(columns and rows start at 1 (not zero) in the terminal)
  //use this method in your other text drawing methods to make things simpler.
  public static void drawText(String s,int startRow, int startCol){
    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    Text.go(startRow, startCol);
    System.out.print(s);
    /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
  }

  public static void addTurnMessage(String text) {
    if (turnHistory.size() >= HISTORY_SIZE) {
      turnHistory.removeLast();
    }
    turnHistory.addFirst(text);
  }

  public static void drawTurnHistory() {
    int startRow = 6;
    for (int i = 0; i < turnHistory.size();i++) {
      TextBox(startRow+i*3, 2, 77, 3, turnHistory.get(i));
    }
  }

  /*Use this method to place text on the screen at a particular location.
  *When the length of the text exceeds width, continue on the next line
  *for up to height lines.
  *All remaining locations in the text box should be written with spaces to
  *clear previously written text.
  *@param row the row to start the top left corner of the text box.
  *@param col the column to start the top left corner of the text box.
  *@param width the number of characters per row
  *@param height the number of rows
  */
  public static void TextBox(int row, int col, int width, int height, String text){
    String[] words = text.split(" ");
    int r = row;
    int c = col;

    for (int i = 0; i < height; i++) {
      Text.go(row + i, col);
      System.out.print(" ".repeat(width));
    }

    for (String word : words) {
      if (c + word.length() > col + width) {
        r++;
        c = col;
      }
      if (r >= row + height) {
        return;
      }
        Text.go(r, c);
        System.out.print(word + " ");
        c += word.length() + 1;
    }
  }

    //return a random adventurer (choose between all available subclasses)
    //feel free to overload this method to allow specific names/stats.
    public static Adventurer createRandomAdventurer(){
      Adventurer[] randomAdventurers = new Adventurer[] {
        new ArtMajor(),
        new CSMajor(),
        new PhilosophyMajor(),
      };
      int randomIndex = (int) (Math.random()*randomAdventurers.length);
      return randomAdventurers[randomIndex];
    }

    /*Display a List of 2-4 adventurers on the rows row through row+3 (4 rows max)
    *Should include Name HP and Special on 3 separate lines.
    *Note there is one blank row reserved for your use if you choose.
    *Format:
    *Bob          Amy        Jun
    *HP: 10       HP: 15     HP:19
    *Caffeine: 20 Mana: 10   Snark: 1
    * ***THIS ROW INTENTIONALLY LEFT BLANK***
    */
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

        String special = temp.getSpecialName() + ": " + temp.getSpecial();
        drawText(special, startRow + 2, startCol);
      }
    }


  //Use this to create a colorized number string based on the % compared to the max value.
  public static String colorByPercent(int hp, int maxHP){
    String output = String.format("%2s", hp+"")+"/"+String.format("%2s", maxHP+"");
    //COLORIZE THE OUTPUT IF HIGH/LOW:
    // under 25% : red
    // under 75% : yellow
    // otherwise : white
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
  public static void drawScreen(ArrayList<Adventurer> party, ArrayList<Adventurer> enemies){
    removeDeadAdventurers(enemies);
    removeDeadAdventurers(party);

    drawParty(party, HEIGHT-5);
    drawParty(enemies, 2);
    drawBackground();
  }

  public static String userInput(Scanner in, int start){
      int row = 29;
      int col = 2 + start;
      //Move cursor to prompt location
      Text.go(row,col);

      //show cursor
      Text.showCursor();
      String input = in.nextLine();

      //clear the text that was written
      Text.go(row,col);
      for (int i = 0; i < 78-start; i++) {
        System.out.print(" ");
      }
      Text.go(row,col);
      Text.hideCursor();

      return input;
  }

  public static void quit(){
    Text.clear();
    Text.reset();
    Text.showCursor();
    Text.go(1,1);
  }

  public static void run(){
    Text.hideCursor();
    Text.clear();

    ArrayList<Adventurer> enemies = new ArrayList<Adventurer>();

    // List of CEO names, can remove and just have "Boss" as the name
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

    //Adventurers you control:
    //Make an ArrayList of Adventurers and add 2-4 Adventurers to it.
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
                  System.out.println("Error: Adventurer type must be one of the choices. Please try again.");
              }
            } catch (NumberFormatException e) {
              System.out.println("Error: Invalid input. Please enter a number between 1 and 3.");
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

    Text.clear();

    boolean partyTurn = true;
    int whichPlayer = 0;
    int whichOpponent = 0;
    int turn = 0;
    String input = "";//blank to get into the main loop.
    //Draw the window border

    //You can add parameters to draw screen!
    drawScreen(party, enemies);//initial state.

    //Main loop

    //display this prompt at the start of the game.

    String preprompt = "Enter command for " + party.get(whichPlayer).getName() + " (attack/special/support/quit): ";
    TextBox(29, 2, 77, 1, preprompt);

    while(! (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit"))){
      if (whichPlayer >= party.size()) {
        whichPlayer = 0;
      }
      //Read user input//YOUR CODE HERE
      input = userInput(in, preprompt.length());

      //example debug statment
      // TextBox(24,2,1,78,"input: "+input+" partyTurn:"+partyTurn+ " whichPlayer="+whichPlayer+ " whichOpp="+whichOpponent );

      //display event based on last turn's input
      if(partyTurn){
        boolean breakOut = false;
        boolean validInput = false;
        while (validInput == false){
          //Process user input for the last Adventurer:
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
            String[] temp = input.split(" ");
            int num = Integer.valueOf(temp[1])-1;
            Adventurer target = party.get(num);
            Adventurer attacker = party.get(whichPlayer);
            String result = attacker.support(target);
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
          //re-asks for input
          else{
            String errMessage = "INVALID INPUT. Enter command for " + party.get(whichPlayer).getName() + " (attack/special/quit): ";
            TextBox(29, 2, 77, 1, errMessage);
            input = userInput(in, errMessage.length());
          }
        }
        if (breakOut == true){
          break;
        }
        //You should decide when you want to re-ask for user input
        //If no errors:
        whichPlayer++;

        if(whichPlayer < party.size()){
          //This is a player turn.
          //Decide where to draw the following prompt:
          preprompt = "Enter command for " + party.get(whichPlayer).getName() + " (attack/special/support/quit): ";
          TextBox(29, 2, 77, 1, preprompt);
        }else{
          //This is after the player's turn, and allows the user to see the enemy turn
          //Decide where to draw the following prompt:
          String prompt = "Press enter to see enemy team's turn";
          TextBox(29, 2, 77, 1, prompt);
          partyTurn = false;
          whichOpponent = 0;
        }
        //done with one party member
      }else{
        Adventurer attacker = enemies.get(whichOpponent);
        int action = (int) (Math.random()*3);

        Adventurer target;
        String result = "";

        if (action == 0 || (action == 1 && attacker.getSpecial() < 8)) {
          target = party.get((int)(Math.random()*party.size()));
          result = attacker.attack(target);
        }
        else if (action == 1 && attacker.getSpecial() >= 8) {
          target = party.get((int)(Math.random()*party.size()));
          result = attacker.specialAttack(target);
        }
        else {
          target = enemies.get((int)(Math.random()*enemies.size()));
          result = attacker.support(target);
        }

        addTurnMessage(Text.colorize(result, Text.RED));
        TextBox(6, 2, 76,  3, result);

        whichOpponent++;

        if(whichOpponent >= enemies.size()){
          whichPlayer = 0;
          turn++;
          whichOpponent=0;
          partyTurn=true;
          String prompt = "Enter command for " + party.get(whichPlayer).getName() + " (attack/special/quit): ";
          TextBox(29,2,77,1,prompt);
        }
      }

      removeDeadAdventurers(enemies);
      removeDeadAdventurers(party);

      if (enemies.isEmpty()) {
          Text.clear();
          Text.go(10, 10);
          System.out.println(Text.colorize("Congratulations!", Text.GREEN, Text.BOLD));
          Text.go(12, 10);
          System.out.println(Text.colorize("You Win! All enemies are defeated.", Text.GREEN));
          Text.go(14, 10);
          System.out.println(Text.colorize("Enter any key to exit.", Text.YELLOW));
          new Scanner(System.in).nextLine();
          return;
      } else if (party.isEmpty()) {
        Text.clear();
        Text.go(10, 10);
        System.out.println(Text.colorize("Game Over!", Text.RED, Text.BOLD));
        Text.go(12, 10);
        System.out.println(Text.colorize("You Lose! All party members are dead.", Text.RED));
        Text.go(14, 10);
        System.out.println(Text.colorize("Enter any key to exit.", Text.YELLOW));
        new Scanner(System.in).nextLine();
        return;
        }
      //display the updated screen after input has been processed.
      drawScreen(party, enemies);
      drawTurnHistory();

    }//end of main game loop


    //After quit reset things:
    quit();
  }
}
