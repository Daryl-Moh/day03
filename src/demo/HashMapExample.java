package demo;

import java.util.HashMap;
import java.util.ArrayList;

public class HashMapExample {

    public static void main(String[] args) {

        HashMap<String, ArrayList<String>> userMap = new HashMap<String, ArrayList<String>>();

        userMap.put("bala", new ArrayList<String>());
        userMap.put("fred", new ArrayList<String>());
        userMap.put("ken", new ArrayList<String>());

        HashMap<String, Integer> mymap = new HashMap<String, Integer>();

        mymap.put("bala",30);
        mymap.put ("ken",40);
    
        String key = "bala";
        System.out.println("Value for key = " + key + "--> " + mymap.get(key));
    
        mymap.put("bala",31);
        System.out.println("New Value for key = " + key + "--> " + mymap.get(key));
    
        // check if a key exists in map
        System.out.println("check if fred exists: " + mymap.containsKey("fred"));










    }



    


    
}
