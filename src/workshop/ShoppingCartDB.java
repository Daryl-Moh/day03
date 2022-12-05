package workshop;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ShoppingCartDB {

    public static final String LOGIN = "login";
    public static final String ADD = "add";
    public static final String LIST = "list";
    public static final String SAVE = "save";
    public static final String STOP = "stop";
    public static final String USERS = "users";

    public static final List<String> VALID_COMMANDS = Arrays.asList("login", "add", "list", "save", "users", "stop");

    private CartDBinMemory db;
    private String currentUser;
    private String baseFolder;

    public ShoppingCartDB() {
        this.baseFolder = "db";
        this.setUp();
        this.db = new CartDBinMemory(this.baseFolder);
    }

    public ShoppingCartDB(String baseFolder) {
        this.baseFolder = baseFolder;
        this.setUp();
        this.db = new CartDBinMemory(this.baseFolder);

    }

    public void setUp() {
        Path p = Paths.get(this.baseFolder);
        if (Files.isDirectory(p)) {
            // SKip if directory
        } else {
                try{
                Files.createDirectories(p);
                } catch (IOException e) {
                    System.out.println("Error : " + e.getMessage());
                } 
        }
    }

    public void startShell() {

        System.out.println("Welcome to MultiUser shopping cart\n");
        System.out.println("Commands available:\n login <name>\n add <item>\n list\n save\n users\n stop\n");

        Scanner sc = new Scanner(System.in);
        String line;
        Boolean stop = false;

        while (!stop) {

            line = sc.nextLine();
            line = line.trim();
            System.out.println("--> " + line);
            
            if (line.equalsIgnoreCase("stop")) {
                System.out.println("Program has been stopped\n");
                stop = true;
                
            }

            // validation
            if (!this.ValidateInput(line)) {
                System.out.println("Invalid Input. Please enter valid commands\n");
            } else {
                System.out.println("Processing: " + line);
                this.ProcessInput(line);
            }         
        }
        sc.close();     
    }

    public boolean ValidateInput(String input) {
            String [] parts = input.split(" ");
            String command = parts[0].trim();

            // Another way of doing
            //Scanner lsc = new Scanner(input);
            //String command = lsc.next().trim();

            return VALID_COMMANDS.contains(command);

    }
    
    // Process command
    public void ProcessInput(String input) {
            Scanner sc = new Scanner(input);
            String command = sc.next().trim();

            switch (command) {

                case LOGIN:
                    String username = sc.nextLine().trim();
                    this.loginAction(username);
                    System.out.println("Current account user: " + currentUser);
                    break;

                case LIST:
                    this.listAction();
                    break;

                case ADD:
                    String[] items = sc.nextLine().trim().split(",");
                    this.addAction(items);
                    break;

                case SAVE:
                    // write out to the db 
                    this.saveAction();

                    break;

                case USERS:
                    // read users from db and output to screen
                    this.listUsers();
                    break;

                default:
                    break;
            }
            sc.close();
    }

    // Command: Login <username>
    // Program needs to know whose account to look at
    public void loginAction(String username) {

        if(!this.db.userMap.containsKey(username)) {
            this.db.userMap.put(username, new ArrayList<String>());

        }
        this.currentUser = username;
    }

    // Command: add <item1>, <item2>
    // Add items function: push items for the current user
    public void addAction(String[] items) {

        for (String item: items) {
            this.db.userMap.get(this.currentUser).add(item);
        }
    }

    // Command: list
    // list item function: show the items addedd for the current user
    public void listAction() {
        if (this.db.userMap.get(this.currentUser).isEmpty()) {
            System.out.println("Your shopping cart is empty! \nPlease add items using the add command\n");
        }
        for (String item: this.db.userMap.get(this.currentUser)) {
            System.out.println("--> " + item);
        }
    }
    // Command: users
    // list all the users in the system
    public void listUsers() {
     
        List<String> user = new ArrayList<String>();
        
        File[] files = new File("C:/Users/Daryl/src/day03/cartdb").listFiles();

        for(File file: files) {
            if (file.isFile()) {
                user.add(file.getName());
                System.out.println("User: " + user);
            }
        }
    }

    
        /*
        File file = Paths.get("/cartdb/%s/.db", this.db.userMap.get(this.currentUser));
        for (File f: file.listFiles())
            System.out.printf("filename: %s\n", f.getName());
    
        
        // Path object
        Path pth = Paths.get(fname);
        File fobj = pth.toFile();

        if (fobj.exists()) {
            
            System.out.println("File Exists");
        }   else {

            System.out.println("File Not Found");
        }
        // File object
        // Reader object
        
        try {
            FileReader fr = new FileReader(fobj);
            BufferedReader bdf = new BufferedReader(fr);
            String line;
            //String line = bdf.readLine();
            //System.out.println("First line --> " + line);

            //line = bdf.readLine();
            //System.out.println("Second line --> " + line);

            while (null != (line = bdf.readLine())) {
                System.out.println("--> " + line);
            }

            bdf.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found. Please check input file: " + e.getMessage());

        }
        catch(IOException e) {
            System.out.println("Unable to read line: " + e.getMessage());
        }*/
        


    // Command: save
    // save function: dump the contents of current user to a file base_folder/username.db
    public void saveAction() {
        // Prepare the filePath = "db/<username>.db"
        String outputFileName = String.format("%s/%s.db", this.baseFolder, this.currentUser);

        try {

            FileWriter fw = new FileWriter(outputFileName);
            // Save the contents for this user in Map to a file.
            for (String item: this.db.userMap.get(this.currentUser)) {
                fw.write(item + "\n");
            }
            fw.flush();
            fw.close();
        }   catch (IOException e) {
                e.printStackTrace();
        }
    }     


}
