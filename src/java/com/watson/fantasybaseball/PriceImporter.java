/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watson.fantasybaseball;

import com.watson.io.FileManip;

/**
 *
 * @author brads
 */
public class PriceImporter {
    
    public static void main(String args[]) {
        //addFreeAgents("d:/keepers2018.txt");
        yahooImport("C:/Users/brads/OneDrive/Documents/Fantasy Baseball/relievers.txt");
    }
    
    public static void addFreeAgents(String file) {
        String lines[] = FileManip.getTextFileContents(file).split("\n");
        boolean keepers = false;
        for(int x = 0; x < lines.length; x++) {
            //System.out.println(lines[x]);
            int team = 0;
            if(lines[x].contains("Edit Keepers")) {
                keepers = true;
                team = Worker.getTeamIdByName(lines[x+1].trim().replace("'", "''"));
                System.out.println("Team: " + Worker.getTeamNameById(team));
            }
            while(keepers) {
                if((x+1) < lines.length) {
                    if(lines[x+1].contains("(")) {
                        String name = lines[x].trim().replace("'", "''");
                        //System.out.println(name);
                        int id = Worker.getPlayerIdByName(name);
                        //System.out.println(id);
                        if(!Worker.isRostered(id)) {
                            System.out.println(name);
                            Worker.insertNewContract(team, id, 1, 2018, 'Y', 'F');
                        } else if(Worker.isExpired(id, team, 'Y')) {
                            System.out.println(name);
                        }
                        /*if(!Worker.playerExists(name)) {
                            System.out.println(name);
                        }*/
                    }
                    if(lines[x].contains("Non-Keepers")) {
                        keepers = false;
                    }
                }
                x++;
            }
        }
    }
    
    public static void yahooImport(String file) {
        String lines[] = FileManip.getTextFileContents(file).split("\n");
        for(int x = 0; x < lines.length; x++) {
            if(lines[x].contains(" - ") && !lines[x].contains("$")) {
                String split[] = lines[x].split(" - ");
                String price = lines[x + 2].trim();
                if(price.contains("$")) {
                    price = lines[x + 3].trim();
                }
                String pos = split[1];
                String split2[] = split[0].split(" ");
                String name = "";
                String team = split2[split2.length - 1];
                for(int y = 0; y < (split2.length - 1); y++) {
                    name += " " + split2[y];
                }
                int p = Integer.parseInt(price);
                if(p == 0) {
                    p = 1;
                }
                name = name.trim().replace("'", "''");
                if(!Worker.playerExists(name)) {
                    //System.out.println(name);
                    //System.out.println(name + ": " + pos + ", " + p);
                    //name = name.trim().replace("'", "''");
                    Player player = new Player(name, team, pos, p);
                    //System.out.println(name + ": " + pos + ", " + p);
                    //Worker.enterPlayer(player);
                    System.out.println(name + ": " + p + ", " + team + ", " + pos);
                    
                } else {
                    name = name.trim().replace("'", "''");
                    Worker.enterPlayerESPNPrice(name, team, p);
                    int id = Worker.getPlayerIdByName(name);
                    System.out.println(name + ": " + p + ", " + Worker.getPlayerOwner(id));//Worker.getPlayerPriceForYear(id, 2019, Worker.getPlayerOwner(id)));
                }
                /*System.out.println(name);
                System.out.println(team);
                System.out.println(pos);
                System.out.println(price);*/
            }
        }
    }
    
}
