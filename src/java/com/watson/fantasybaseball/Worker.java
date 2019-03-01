/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.watson.fantasybaseball;

import com.watson.database.mysql.SQLConnection;
import com.watson.io.FileManip;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class Worker {
    
    public static final int STARTING_BUDGET = 275;
    public static final int ROSTER_SPOTS = 28;
    public static final int FINAL_YEAR = 2025;
    
    public static void main (String args[]) throws ParseException, SQLException {
        //System.out.println(getPlayerIdByName("bo bichette"));
        //enterESPNPrices();
        //System.out.println(expirationYear(getPlayerIdByName("Danny Salazar"), 1));
        //System.out.println(isExtendable(getPlayerIdByName("Andrew McCutchen"), 9, 'Y'));
        //System.out.println(getYear());
        //System.out.println(playerExists("Ryan O'Rourke"));
        //parseContracts();
        //System.out.println(extensionStartYear(6, 1));
        //System.out.println(getPlayerPriceForYear(getPlayerIdByName("Chris Davis"), 2017, 2));
        //System.out.println(isExtendable(getPlayerIdByName("Albert Pujols"), 2, 'Y'));
        //System.out.println(extendContract(getTeamIdByName("Highland Hammer"), getPlayerIdByName("Matt Carpenter"), 2));
        //System.out.println(getContractInsertsFromCSV("d:/2015freeagents.csv"));
        //activatePlayer(76, 11);
        //updateContract(11, 130, 13, 1, 2015, 'F');
        //deleteContract(getPlayerIdByName("Oscar Taveras"), getTeamIdByName("QC Laloosh"));
        //deleteExpiredInactiveContracts();
        /*String contents = FileManip.getTextFileContents("D:/fantasyplayers.csv");
        String players[] = contents.split("\n");
        for(String text : players) {
            Player player = parsePlayer(text);
            System.out.println(player.getName() + " " + player.getTeam() + " " + player.getPosition() + "\n");
            enterPlayer(player);
        }*/
        //insertNewContractsFromDraftResultsTxt("D:/documents/fantasy baseball/draftresults2018.txt");
        //System.out.println(expirationYear(getPlayerIdByName("Kenley Jansen"), getTeamIdByName("HGH")));
        /*String teams[] = (getTeamsList());
        for(String team : teams) {
            System.out.println(team);
        }*/
        //System.out.println(extensionPrice(494, 9));
        //extendContract(1, getPlayerIdByName("Matt Holliday"), 3);
        //System.out.println(isFranchisePlayer(getPlayerIdByName("Mike Trout"), 2));
        //System.out.println(expireContract(getTeamIdByName("ManCrushed"), getPlayerIdByName("Matt Holliday")));
        //setFranchisePlayer(getPlayerIdByName("Troy Tulowitzki"), 2);
        //System.out.println(getPlayerList());
        //System.out.println(getAvgBid(1));
        //insertContract(getTeamIdByName("Ron Gant Enthusiast"), "Mike Trout", 26, 1, 'Y', 'N');
        //deactivatePlayer(getPlayerIdByName("Brandon Beachy"), getTeamIdByName("Highland Hammer"));
        //System.out.println(contractExtensionLength(getTeamIdByName("ManCrushed"), getPlayerIdByName("Matt Holliday")));
        //addESPNPlayersAndPrices("d:/espnlist.csv");
        //updatePositions();
        //System.out.println(getPlayerPriceForYear(1, 2014));
        System.out.println(getActiveContractsForTeam(1));
        /*while (results.next()) {
            System.out.println("Value: " + results.getString(1));
        }*/
        
        /*
        String text[] = FileManip.getTextFileContents("c:/hitters.txt").split("\n");
        ArrayList<String> list = new ArrayList<String>();
        String players[] = getPlayerNamesAsArray();
        for(String player : players) {
            list.add(player);
        }
        ArrayList<String> hitters = new ArrayList<String>();
        int val = 0;
        for(String s : text) {
            String split[] = s.split(",", 2);
            String value = split[1].replace("1B", "")
                    .replace("2B", "")
                    .replace("3B", "")
                    .replace("SS", "")
                    .replace("OF", "")
                    .replace("DH", "")
                    .replace("C", "")
                    .trim();
            if(value.contains("-")) {
                val = 1;
            } else if(value.contains(":")) {
                val = Integer.parseInt(value.substring(value.indexOf(":") + 1).replace(",", "").trim());
                hitters.add(split[0]);
            } else {
                val = Integer.parseInt(value.replaceAll("[^0-9]", ""));
            }
            if(val == 0) {
                val = 1;
            }
            hitters.add(split[0]);
            //System.out.println(split[0] + "," + val);
        }
        for(String hitter : hitters) {
            if(!list.contains(hitter)) {
                System.out.println(hitter);
            }
        }*/
    }
    
    public static int getTeamIdByName(String name) {
        return getDBResultAsInt("SELECT TeamID FROM fantasybaseball.teams WHERE TeamName = '" + name.trim() + "'");
    }
    
    public static int getPlayerOwner(int playerId) {
        return getDBResultAsInt("SELECT TeamID FROM fantasybaseball.contracts WHERE PlayerID = '" + playerId + "' AND CurrentlyRostered = 'Y'");
    }
    
    public static String getTeamNameById(int ID) {
        return getDBResult("SELECT TeamName FROM fantasybaseball.teams WHERE TeamID = '" + ID + "'");
    }
    
    public static String[] getTeamsList() {
        return getDBResult("SELECT TeamName FROM fantasybaseball.teams").split("\n");
    }
    
    public static int[] getTeamIdsList() {
        String[] teams = getDBResult("SELECT TeamID FROM fantasybaseball.teams").split("\n");
        int teamIds[] = new int[teams.length];
        for(int x = 0; x < teamIds.length; x++) {
            teamIds[x] = Integer.parseInt(teams[x]);
        }
        return teamIds;
    }
    
    public static void insertNewPlayers() {
        String[] players = FileManip.getTextFileContents("d:/newplayers.txt").split("\n");
        for(String line : players) {
            String[] split = line.split(";");
            String name = split[0];
            String team = split[1];
            String position = split[2];
            Player player = new Player(name, team, position);
            if(getPlayerIdByName(name) == 0) {
                enterPlayer(player);
            } else {
                System.out.println(name + ";" + split[1]);
            }
        }
    }
    
    public static void enterESPNPrices() {
        String[] players = FileManip.getTextFileContents("d:/prices.txt").split("\n");
        for(String player : players) {
            
            String[] split = player.split(";");
            //System.out.println(split[2]);
            String name = split[0];
            int price = Integer.parseInt(split[2].trim());
            if(getPlayerIdByName(name) == 0) {
                System.out.println(name + ";" + split[1]);
            } else {
                enterPlayerESPNPrice(name, split[1], price);
            }
        }
    }
    
    public static void enterPlayerESPNPrice(String player, String team, int price) {
        int id = getPlayerIdByName(player);
        System.out.println("Name: " + player + ", ID: " + id + ". Team: " + team + ", Price: " + price);
        writeStatementToDb("UPDATE fantasybaseball.players SET ESPNPrice = '" + price + "', Team = '" + team + "' WHERE PlayerID = '" + getPlayerIdByName(player) + "';");
    }
    
    public static int getPlayerPriceForYear(int playerId, int year, int team) {
        return getDBResultAsInt("SELECT " + year + "Price FROM fantasybaseball.contracts WHERE PlayerID = '" + playerId + "' AND TeamID = '" + team + "' AND CurrentlyRostered = 'Y'");
    }
    
    public static int ESPNPlayerPrice(int playerId) {
        return getDBResultAsInt("SELECT ESPNPrice FROM fantasybaseball.players WHERE PlayerID = '" + playerId + "'");
    }
    
    public static void updatePosition(int playerID, String position) {
        writeStatementToDb("UPDATE fantasybaseball.players SET Position = '" + position + "' WHERE PlayerID = '" + playerID + "';");
    }
    
    public static void updateTeam(int playerID, String team) {
        writeStatementToDb("UPDATE fantasybaseball.players SET Team = '" + team + "' WHERE PlayerID = '" + playerID + "';");
    }
      
    public static String getPlayerList() {
        return getDBResult("SELECT Name, Team FROM fantasybaseball.players");
    }
    
    public static String getPlayerNames() {
        return getDBResult("SELECT Name from fantasybaseball.players");
    }
    
    public static String[] getPlayerNamesAsArray() {
        return getDBResult("SELECT Name from fantasybaseball.players").split("\n");
    }
    
    public static String getTeamList() {
        return getDBResult("SELECT TeamID, TeamName FROM fantasybaseball.teams");
    }
    
    public static String getPlayerListWithPositions() {
        return getDBResult("SELECT PlayerID, Name, Team, Position FROM fantasybaseball.players");
    }
    
    public static int getPlayerIdByName(String name) {
        return getDBResultAsInt("SELECT PlayerID FROM fantasybaseball.players WHERE Name = '" + name.trim().replace("'", "''") + "'");
    }
    
    public static boolean playerExists(String name) {
        int x = getDBResultAsInt("SELECT PlayerID FROM fantasybaseball.players WHERE Name = '" + name.trim().replace("'", "''") + "'");
        if(x > 0) {
            return true;
        }
        return false;
    }
    
    public static String getPlayerNameTeamAndPositionById(int playerID) {
        return getDBResult("SELECT Name, Team, Position FROM fantasybaseball.players WHERE PlayerID = '" + playerID + "';");
    }
    
    public static String getPlayerNameById(int playerID) {
        return getDBResult("SELECT Name FROM fantasybaseball.players WHERE PlayerID = '" + playerID + "';");
    }
    
    public static int teamBudget(int teamId) {
        return teamBudgetForYear(teamId, getYear());
    }
    
    public static int teamBudgetForYear(int teamId, int year) {
        String prices[] = getDBResult("SELECT " + year + "Price FROM fantasybaseball.contracts WHERE TeamID = '" + teamId + "';").split("\n");
        int total = 0;
        for(String price : prices) {
            total += Integer.parseInt(price);
        }
        return total;
    }
    
    public static int teamOpenRosterSpots(int teamId) {
        return (ROSTER_SPOTS - getDBResultAsInt("SELECT COUNT(*) FROM fantasybaseball.contracts WHERE TeamID = '" + teamId + "' AND CurrentlyRostered = 'Y';"));
    }
    
    public static int teamActiveRosterCount(int teamId) {
        return getDBResultAsInt("SELECT COUNT(*) FROM fantasybaseball.contracts WHERE TeamID = '" + teamId + "' AND CurrentlyRostered = 'Y';");
    }
    
    public static int teamInactiveRosterCount(int teamId) {
        return getDBResultAsInt("SELECT COUNT(*) FROM fantasybaseball.contracts WHERE TeamID = '" + teamId + "' AND CurrentlyRostered = 'N';");
    }
    
    public static int teamAvailableDollars(int teamId) {
        return (STARTING_BUDGET - teamBudget(teamId));
    }
    
    public static int contractExtensionLength(int teamId, int playerId) {
        //System.out.println("HERE");
        return getDBResultAsInt("SELECT ExtensionLength FROM fantasybaseball.contracts WHERE PlayerID = '" + playerId + "' AND TeamID = '" + teamId + "' AND CurrentlyRostered = 'Y'");
    }
    
    public static int maxBid(int teamId) {
        if(teamOpenRosterSpots(teamId) > 0) {        
            return (teamAvailableDollars(teamId) - teamOpenRosterSpots(teamId) + 1);
        }
        return 0;
    }

    public static int avgBid(int teamId) {
        if(teamOpenRosterSpots(teamId) > 0) {
            return (teamAvailableDollars(teamId) / teamOpenRosterSpots(teamId));
        }
        return 0;
    }
    
    public static String getContractsForTeam(int teamId) {
        StringBuilder stmt = new StringBuilder();
        stmt.append("SELECT fantasybaseball.players.Name, fantasybaseball.players.Team, fantasybaseball.players.Position, fantasybaseball.contracts.2017Price, fantasybaseball.contracts.2018Price, fantasybaseball.contracts.2019Price\n")
            .append("FROM fantasybaseball.contracts\n")
            .append("INNER JOIN fantasybaseball.players\n")
            .append("ON fantasybaseball.players.PlayerID=fantasybaseball.contracts.PlayerID\n")
            .append("WHERE fantasybaseball.contracts.TeamID = '").append(teamId).append("'");
        String result = getDBResult(stmt.toString());
        return result.replaceAll("null", "0").replaceAll("NULL", "0").trim();
    }
    
    public static String getActiveContractsForTeam(int teamId) {
        StringBuilder stmt = new StringBuilder();
        stmt.append("SELECT fantasybaseball.players.Name, fantasybaseball.players.Team, fantasybaseball.players.Position, fantasybaseball.contracts.2017Price, fantasybaseball.contracts.2018Price, fantasybaseball.contracts.2019Price, fantasybaseball.contracts.2020Price, fantasybaseball.contracts.2021Price, fantasybaseball.contracts.2022Price, fantasybaseball.contracts.2023Price, fantasybaseball.contracts.Franchise, fantasybaseball.contracts.AdditionType, fantasybaseball.contracts.Length, fantasybaseball.contracts.StartYear, fantasybaseball.contracts.ExtensionLength, fantasybaseball.contracts.ExtensionStartYear\n")
            .append("FROM fantasybaseball.contracts\n")
            .append("INNER JOIN fantasybaseball.players\n")
            .append("ON fantasybaseball.players.PlayerID=fantasybaseball.contracts.PlayerID\n")
            .append("WHERE fantasybaseball.contracts.TeamID = '").append(teamId).append("'\n")
            .append("AND fantasybaseball.contracts.CurrentlyRostered = 'Y'");
        System.out.println(stmt.toString());
        String result = getDBResult(stmt.toString());
        return result.replace("null", "0").replace("NULL", "0").trim();
    }
    
    public static String getInactiveContractsForTeam(int teamId) {
        StringBuilder stmt = new StringBuilder();
        stmt.append("SELECT fantasybaseball.players.Name, fantasybaseball.players.Team, fantasybaseball.players.Position, fantasybaseball.contracts.2017Price, fantasybaseball.contracts.2018Price, fantasybaseball.contracts.2019Price, fantasybaseball.contracts.2020Price, fantasybaseball.contracts.2021Price, fantasybaseball.contracts.2022Price, fantasybaseball.contracts.2023Price, fantasybaseball.contracts.Franchise, fantasybaseball.contracts.AdditionType, fantasybaseball.contracts.Length, fantasybaseball.contracts.StartYear, fantasybaseball.contracts.ExtensionLength, fantasybaseball.contracts.ExtensionStartYear\n")
            .append("FROM fantasybaseball.contracts\n")
            .append("INNER JOIN fantasybaseball.players\n")
            .append("ON fantasybaseball.players.PlayerID=fantasybaseball.contracts.PlayerID\n")
            .append("WHERE fantasybaseball.contracts.TeamID = '").append(teamId).append("'\n")
            .append("AND fantasybaseball.contracts.CurrentlyRostered = 'N'");
        String result = getDBResult(stmt.toString());
        return result.replaceAll("null", "0").replaceAll("NULL", "0").trim();
    }
    
    public static void enterPlayer(Player player) {
        writeStatementToDb("INSERT into fantasybaseball.players values (null, '" + player.getName() + "', '" + player.getTeam() + "', '" + player.getPosition() + "', '" + player.getESPNPrice() + "')");
    }
    
    public static int getYear() {
        Calendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.YEAR);
    }
    
    public static String insertContract(int teamId, String name, int price, int length, int startYear, char active, char type) {
        StringBuilder statement = new StringBuilder();
        int year = getYear();
        char franchise = ' ';
        if(length == 5) {
            franchise = 'Y';
        } else {
            franchise = 'N';
        }
        statement.append("INSERT INTO fantasybaseball.contracts (TeamID, PlayerID, ").append(year).append("Price, ");
        for(int x = 1; x < length; x++) {
            statement.append(year + x).append("Price, ");
        }
        statement.append("CurrentlyRostered, Length, StartYear, Franchise, AdditionType)\n")
            .append("SELECT '").append(teamId).append("', fantasybaseball.players.PlayerID, '");
        for(int x = 0; x < length; x++) {
            statement.append(price).append("', '");
        }
        statement.append(active).append("', '").append(length).append("', '").append(startYear).append("', '")
            .append(franchise).append("', '").append(type).append("'\n")
            .append("FROM fantasybaseball.players").append("\n")
            .append("WHERE Name='").append(name).append("';").append("\n\n");
        System.out.println(statement.toString());
        return writeToDb(statement.toString());
    }
    
    public static String moveContract(int oldTeamId, int newTeamId, int playerId) {
        if((isFranchisePlayer(playerId, oldTeamId)) && (franchiseContractCount(newTeamId) > 4)) {
            return "Unable to move " + getPlayerNameById(playerId) + " to " + getTeamNameById(newTeamId) + " because franchise contract limit of 5 would be exceeded!";
        }
        StringBuilder statement = new StringBuilder();
        statement.append("UPDATE fantasybaseball.contracts\n");
        statement.append("SET TeamID = '").append(newTeamId).append("'\n");
        statement.append("WHERE fantasybaseball.contracts.PlayerId = '").append(playerId).append("'\n")
            .append("AND fantasybaseball.contracts.CurrentlyRostered = 'Y'\n")
            .append("AND fantasybaseball.contracts.TeamID = '").append(oldTeamId).append("'");
        writeStatementToDb(statement.toString());
        return statement.toString();
    }
    
    public static void updateContract(int teamId, int playerId, int price, int length, int startYear, char type) {
        StringBuilder statement = new StringBuilder();
        char franchise = 'N';
        if(length == 5) {
            franchise = 'Y';
        }
        int year = getYear();
        int finalYear = startYear + length - 1;
        statement.append("UPDATE fantasybaseball.contracts\n");
        statement.append("SET TeamID = '").append(teamId).append("', PlayerID = '").append(playerId).append("', AdditionType = '").append(type).append("', ");
        for(int x = year; x <= FINAL_YEAR; x++) {
            if(x > finalYear) {
                price = 0;
            }
            statement.append(x).append("Price = '").append(price).append("', ");
        }
        statement.append("Franchise = '").append(franchise).append("', StartYear = '").append(startYear).append("', Length = '").append(length).append("'\n");
        statement.append("WHERE fantasybaseball.contracts.PlayerId = '").append(playerId).append("'\n")
            .append("AND fantasybaseball.contracts.CurrentlyRostered = 'Y'\n")
            .append("AND fantasybaseball.contracts.TeamID = '").append(teamId).append("'");
        //System.out.println(statement.toString());
        writeStatementToDb(statement.toString());
    }
    
    public static int franchiseContractCount(int teamId) {
        return getDBResultAsInt("SELECT COUNT(*) FROM fantasybaseball.contracts WHERE TeamID = '" + teamId + "' AND CurrentlyRostered = 'Y' AND Franchise ='Y';");
    }
    
    public static String expireContract(int teamId, int playerId) {
        int curYear = getYear();
        //if(isExtendable(playerId, teamId, 'Y')) {
            char type = contractType(playerId, teamId, 'Y');
            StringBuilder builder = new StringBuilder();
            builder.append("UPDATE fantasybaseball.contracts SET\n")
                .append("ExtensionLength = '0',\n");
                builder.append("ExtensionStartYear = null,\n");
            int x = curYear + 1;
            for(int year = x; year <= FINAL_YEAR; year++) {
                builder.append(year).append("Price = '0'");
                if(year < FINAL_YEAR) {
                    builder.append(",\n");
                }
            }
            builder.append("\n").append("WHERE fantasybaseball.contracts.PlayerId = '").append(playerId).append("'\n")
                .append("AND fantasybaseball.contracts.CurrentlyRostered = 'Y'\n")
                .append("AND fantasybaseball.contracts.TeamID = '").append(teamId).append("'");
            writeStatementToDb(builder.toString());
            unFranchisePlayer(playerId, teamId);
            return getPlayerNameById(playerId) + " has been set to expire following " + (curYear) + ".";
        //} 
        //return "Unable to expire " + getPlayerNameById(playerId) + "!";
    }
    
    public static String extendContract(int teamId, int playerId, int length, int extensionStartYear, int expirationYear, int extensionLength) {
        int curYear = getYear();
        if((franchiseContractCount(teamId) >= 5) && !isFranchisePlayer(playerId, teamId) && (length == 5)) {
            return "Unable to sign " + getPlayerNameById(playerId) + " for 5 years because " + getTeamNameById(teamId) + " already has 5 franchise players!";
        }
        if(isExtendable(playerId, teamId, 'Y', extensionStartYear, expirationYear, extensionLength, length)) {
            int price;
            char type = contractType(playerId, teamId, 'Y');
            StringBuilder builder = new StringBuilder();
            builder.append("UPDATE fantasybaseball.contracts SET\n")
                .append("ExtensionLength = '").append(length).append("',\n");
            if((getPlayerPriceForYear(playerId, (curYear - 1), teamId) == 0) || type == 'F') {
                builder.append("ExtensionStartYear = '").append(curYear).append("',\n");
                price = currentPrice(playerId, teamId);
            } else {
                builder.append("ExtensionStartYear = '").append(curYear + 1).append("',\n");
                price = ESPNPlayerPrice(playerId);
            } 
            for(int x = 0; x < length; x++) {
                int year;
                if(((type == 'F') && (contractStartYear(playerId, teamId) == (curYear - 1))) || type == 'N') {
                    year = curYear + x;
                } else {
                    year = curYear + x + 1;
                }
                builder.append(year).append("Price = '").append(price).append("'");
                if(year < FINAL_YEAR) {
                    builder.append(",");
                }
                builder.append("\n");
            }
            int x = curYear + 1 + length;
            if(((type == 'F') && (contractStartYear(playerId, teamId) == (curYear - 1))) || type == 'N') {
                x -= 1;
            }
            for(int year = x; year <= FINAL_YEAR; year++) {
                builder.append(year).append("Price = '0'");
                if(year < FINAL_YEAR) {
                    builder.append(",\n");
                }
            }
            builder.append("\n").append("WHERE fantasybaseball.contracts.PlayerId = '").append(playerId).append("'\n")
                .append("AND fantasybaseball.contracts.CurrentlyRostered = 'Y'\n")
                .append("AND fantasybaseball.contracts.TeamID = '").append(teamId).append("'");
            writeStatementToDb(builder.toString());
            //System.out.println(builder.toString());
            if(length == 5) {
                setFranchisePlayer(playerId, teamId);
            } else {
                unFranchisePlayer(playerId, teamId);
            }
            if(contractType(playerId, teamId, 'Y') == 'N') {
                return getPlayerNameById(playerId) + " extended through " + (curYear + length - 1) + ".";
            }
            return getPlayerNameById(playerId) + " extended through " + (curYear + length) + ".";
        } 
        return "Unable to extend " + getPlayerNameById(playerId);
    }
    
    public static boolean isRostered(int playerId) {
        boolean rostered = false;
        String result = getDBResult("SELECT CurrentlyRostered FROM fantasybaseball.contracts WHERE PlayerID = '" + playerId + "'");
        if(result.toUpperCase().trim().contains("Y")) {
            rostered = true;
        }
        return rostered;
    }
    
    public static boolean isRosteredOnTeam(int playerId, int teamId) {
        boolean rostered = false;
        String result = getDBResult("SELECT CurrentlyRostered FROM fantasybaseball.contracts WHERE PlayerID = '" + playerId + "' AND TeamID = '" + teamId + "'");
        if(result.toUpperCase().trim().contains("Y")) {
            rostered = true;
        }
        return rostered;
    }
    
    public static boolean isFranchisePlayer(int playerId, int teamId) {
        boolean franchise = false;
        String result = getDBResult("SELECT Franchise FROM fantasybaseball.contracts WHERE PlayerID = '" + playerId + "' AND TeamID = '" + teamId + "' AND CurrentlyRostered = 'Y'");
        if(result.toUpperCase().trim().contains("Y")) {
            franchise = true;
        }
        return franchise;
    }
    
    public static boolean isBeingExtended(int playerId, int teamId, int extensionLength) {
        //System.out.println("I'm here");
        boolean extended = false;
        //int extLength = getDBResultAsInt("SELECT ExtensionLength FROM fantasybaseball.contracts WHERE PlayerID = '" + playerId + "' AND TeamID = '" + teamId + "' AND CurrentlyRostered = 'Y'");
        if(extensionLength > 0) {
            extended = true;
        }
        return extended;
    }
    
    public static void deleteContract(int playerId, int team) {
        writeStatementToDb("DELETE FROM fantasybaseball.contracts WHERE PlayerID = '" + playerId + "' AND TeamID = '" + team + "' AND CurrentlyRostered = 'N';");
    }
    
    public static void deleteExpiredContracts() {
        writeStatementToDb("DELETE FROM fantasybaseball.contracts WHERE " + getYear() + "Price = '0';");
    }
    
    public static void deleteExpiredInactiveContracts() {
        writeStatementToDb("DELETE FROM fantasybaseball.contracts WHERE " + getYear() + "Price = '0' and CurrentlyRostered = 'N';");
    }
    
    public static void setFranchisePlayer(int playerId, int team) {
        writeStatementToDb("UPDATE fantasybaseball.contracts SET Franchise = 'Y' WHERE PlayerID = '" + playerId + "' AND TeamID = '" + team + "' AND CurrentlyRostered = 'Y';");
    }
    
    public static void deactivatePlayer(int playerId, int team) {
        writeStatementToDb("UPDATE fantasybaseball.contracts SET CurrentlyRostered = 'N' WHERE PlayerID = '" + playerId + "' AND TeamID = '" + team + "';");
    }
    
    public static void activatePlayer(int playerId, int team) {
        writeStatementToDb("UPDATE fantasybaseball.contracts SET CurrentlyRostered = 'Y' WHERE PlayerID = '" + playerId + "' AND TeamID = '" + team + "';");
    }
    
    public static void unFranchisePlayer(int playerId, int team) {
        writeStatementToDb("UPDATE fantasybaseball.contracts SET Franchise = 'N' WHERE PlayerID = '" + playerId + "' AND TeamID = '" + team + "' AND CurrentlyRostered = 'Y';");
    }
    
    public static int contractStartYear(int playerId, int teamId) {
        return getDBResultAsInt("SELECT StartYear FROM fantasybaseball.contracts WHERE PlayerID = '" + playerId + "' AND TeamID = '" + teamId + "' AND CurrentlyRostered = 'Y'");
    }
    
    public static int extensionStartYear(int playerId, int teamId) {
        return getDBResultAsInt("SELECT ExtensionStartYear FROM fantasybaseball.contracts WHERE PlayerID = '" + playerId + "' AND TeamID = '" + teamId + "' AND CurrentlyRostered = 'Y'");
    }
    
    public static int contractLength(int playerId, int teamId) {
        int length = getDBResultAsInt("SELECT Length FROM fantasybaseball.contracts WHERE PlayerID = '" + playerId + "' AND TeamID = '" + teamId + "' AND CurrentlyRostered = 'Y'");
        if(length == 0) {
            return extensionLength(playerId, teamId);
        }
        return length;
    }
    
    public static int extensionLength(int playerId, int teamId) {
        //System.out.println("I'm here");
        return getDBResultAsInt("SELECT ExtensionLength FROM fantasybaseball.contracts WHERE PlayerID = '" + playerId + "' AND TeamID = '" + teamId + "' AND CurrentlyRostered = 'Y'");
    }
    
    public static char contractType(int playerId, int teamId, char rostered) {
        return getDBResultAsChar("SELECT AdditionType FROM fantasybaseball.contracts WHERE PlayerID = '" + playerId + "' AND TeamID = '" + teamId + "' AND CurrentlyRostered = '" + rostered + "'");
    }
    
    public static int expirationYear(int playerId, int teamId, int contractLength, int extensionStartYear, int extensionLength) {
        //System.out.println(getPlayerNameById(playerId));
        int curYear = getYear();
        if(isBeingExtended(playerId, teamId, extensionLength)) {
            int year = ((extensionStartYear + extensionLength) - 1);
            if(year < curYear) {
                return curYear;
            }
            return year;
        }
        if(contractLength == 0) {
            return curYear;
        }
        int year = ((contractStartYear(playerId, teamId) + contractLength) - 1);
        if(year < curYear) {
            return curYear;
        }
        return year;
    }
    
    public static int currentPrice(int playerId, int teamId) {
        return getPlayerPriceForYear(playerId, getYear(), teamId);
    }
    
    public static int extensionPrice(int playerId, int teamId) {
        char rostered = 'Y';
        char type = contractType(playerId, teamId, rostered);
        /*if((type == 'F')) {
            if(contractStartYear(playerId, teamId) == (getYear() - 1)) {
                return currentPrice(playerId, teamId);
            }
        } else if(type == 'N') {
            if(contractStartYear(playerId, teamId) == getYear()) {
                return currentPrice(playerId, teamId);
            }
        }*/
        if(type=='F' || type == 'N') {
            return currentPrice(playerId, teamId);
        }
        return ESPNPlayerPrice(playerId);
    }
    
    /*public static boolean isExtendable(int playerId, int teamId, char rostered) {
        int year = getYear();
        if(isRosteredOnTeam(playerId, teamId)) {
            char type = contractType(playerId, teamId, rostered);
            int contractLength = Worker.contractLength(playerId, teamId);
            int extensionStartYear = Worker.extensionStartYear(playerId, teamId);
            int extensionLength = Worker.extensionLength(playerId, teamId);
            int expirationYear = Worker.expirationYear(playerId, teamId);
            if(extensionLength == 1) {
                return false;
            } else if(type == 'F') {
                //Do something here
            } else if(type == 'N') {
                return true;
            } else if()
        }
        return false;
    }*/
    /*
    public static boolean displayContractBox(int playerId, int teamId, char rostered) {
        char type = contractType(playerId, teamId, rostered);
        if(type == 'N') {
            return true;
        }
        if(type == 'F') {
            return true;
        }
        int contractLength = Worker.contractLength(playerId, teamId);
        if(contractLength == 1) {
            return false;
        }
        int extensionLength = Worker.extensionLength(playerId, teamId);
        int year = getYear();
        int startYear = contractStartYear(playerId, teamId);
        int extYear = extensionStartYear(playerId, teamId);
        int expirationYear = Worker.expirationYear(playerId, teamId);
        if(extensionLength == 1 && extYear == year) {
            return false;
        }
        return false;
    }
    */
    public static boolean isExpired(int playerId, int teamId, char rostered) {
        if(Worker.getPlayerPriceForYear(playerId, getYear(), teamId) == 0) {
            return true;
        }
        return false;
    }
    
    public static boolean isExtendable(int playerId, int teamId, char rostered, int extensionStartYear, int expirationYear, int extensionLength, int contractLength) {
        //System.out.println("ID: " + playerId);
        //System.out.println("Name: " + getPlayerNameById(playerId));
        char type = contractType(playerId, teamId, rostered);
        if(type == 'N' || type == 'F') {
            return true;
        }
        int curYear = getYear();
        if(isRosteredOnTeam(playerId, teamId)) {
            //int contractLength = Worker.contractLength(playerId, teamId);
            //System.out.println("Contract length: " + contractLength);
            //int extensionStartYear = Worker.extensionStartYear(playerId, teamId);
            //System.out.println("Extension start year: " + extensionStartYear);
            //int extensionLength = Worker.extensionLength(playerId, teamId);
            //System.out.println("Extension length: " + extensionLength);
            //int expirationYear = Worker.expirationYear(playerId, teamId);
            //System.out.println("Expiration year: " + expirationYear);
            if((extensionLength == 1)) {// && (extensionStartYear == curYear)) {
                return false;
            } else if(extensionStartYear == (curYear + 1)) {
                return true;
            } else if((extensionStartYear == 0) && (contractLength > 1)) {
                return true;
            } else if((expirationYear == curYear) && (extensionLength > 1)) {
                return true;
            } else if(extensionLength > 1) {
                return true;
            } else if(extensionStartYear == 0) {
                return true;
            }
        }
        return false;
    }
    /*
    public static boolean isExtendable(int playerId, int teamId, char rostered) {
        //System.out.println("ID: " + playerId);
        char type = contractType(playerId, teamId, rostered);
        if(type == 'N' || type == 'F') {
            return true;
        }
        int curYear = getYear();
        if(isRosteredOnTeam(playerId, teamId)) {
            int contractLength = Worker.contractLength(playerId, teamId);
            System.out.println("Contract length: " + contractLength);
            int extensionStartYear = Worker.extensionStartYear(playerId, teamId);
            System.out.println("Extension start year: " + extensionStartYear);
            int extensionLength = Worker.extensionLength(playerId, teamId);
            System.out.println("Extension length: " + extensionLength);
            int expirationYear = Worker.expirationYear(playerId, teamId);
            System.out.println("Expiration year: " + expirationYear);
            if((extensionLength == 1)) {// && (extensionStartYear == curYear)) {
                return false;
            } else if(extensionStartYear == (curYear + 1)) {
                return true;
            } else if((extensionStartYear == 0) && (contractLength > 1)) {
                return true;
            } else if((expirationYear == curYear) && (extensionLength > 1)) {
                return true;
            } else if(extensionLength > 1) {
                return true;
            } else if(extensionStartYear == 0) {
                return true;
            }
        }
        return false;
    }
    */
    public static String getContractInsertsFromCSV(String file) {
        String players[] = FileManip.getTextFileContents(file).split("\n");
        StringBuilder statement = new StringBuilder();
        for(String player: players) {
            String fields[] = player.split(",");
            for(int x = 0; x < fields.length; x++) {
                if(fields[x].trim().length() < 1) {
                    fields[x] = "0";
                }
                fields[x] = fields[x].trim();
            }
            statement.append("INSERT INTO fantasybaseball.contracts (TeamID, PlayerID, 2015Price, 2016Price, 2017Price, 2018Price, CurrentlyRostered, Franchise, AdditionType, Length, StartYear)\n")
                    .append("SELECT '").append(fields[0]).append("', fantasybaseball.players.PlayerID, '")
                    .append(fields[2]).append("', '")
                    .append(fields[3]).append("', '")
                    .append(fields[4]).append("', '")
                    .append(fields[5]).append("', '")
                    .append(fields[6]).append("', '")
                    .append(fields[7]).append("', '")
                    .append(fields[8]).append("', '")
                    .append(fields[9]).append("', '")
                    .append(fields[10]).append("'").append("\n")
                    .append("FROM fantasybaseball.players").append("\n")
                    .append("WHERE Name='").append(fields[1].replace("'", "''")).append("';").append("\n\n");
        }
        return statement.toString();
    }
    
    public static void insertNewContractsFromDraftResultsTxt(String file) {
        String lines[] = FileManip.getTextFileContents(file).split("\n");
        int curTeam = 0;
        for(String line : lines) {
            if(!line.contains(".") && !line.contains("Budget") && !line.contains("Unused")) {
                curTeam = getTeamIdByName(line.trim().replace("'", "\\'"));
                //System.out.println(curTeam);
            } else if(line.contains(".")) {
                boolean keeper = line.contains("KEEPER");
                String name = line.substring((line.indexOf(")") + 1), line.lastIndexOf("(")).replace("KEEPER", "").trim();
                name = name.replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("Á", "A");
                int id = getPlayerIdByName(name);
                String team = line.substring((line.lastIndexOf("(") + 1), line.lastIndexOf("-")).trim();
                String pos = line.substring((line.lastIndexOf("-") + 1), line.lastIndexOf(")")).trim();
                int price = Integer.parseInt(line.substring(line.lastIndexOf("$") + 1).trim());
                
                if(id == 0) {
                    //System.out.println(name + ";" + team + ";" + pos + ";" + price);
                    Player player = new Player(name, team, pos, price);
                    enterPlayer(player);
                } else {
                    if(!isRosteredOnTeam(id, curTeam)) {
                        if(keeper) {
                            insertNewContract(curTeam, id, price, getYear(), 'Y', 'F');
                        } else {
                            insertNewContract(curTeam, id, price, getYear(), 'Y', 'N');
                        }
                    } else {
                        //System.out.println(name + ";" + team + ";" + pos + ";" + price);
                    }
                }
                //System.out.println(name + ";" + team + ";" + pos + ";" + price);
            }
        }
    }
    
    public static String getContractInsertsFromDraftResultsCSV(String file) {
        String players[] = FileManip.getTextFileContents(file).split("\n");
        StringBuilder statement = new StringBuilder();
        for(String player: players) {
            String fields[] = player.split(",");
            for(int x = 0; x < fields.length; x++) {
                if(fields[x].trim().length() < 1) {
                    fields[x] = "0";
                }
                fields[x] = fields[x].trim();
            }
            statement.append("INSERT INTO fantasybaseball.contracts (TeamID, PlayerID, 2014Price, CurrentlyRostered, AdditionType, StartYear)\n")
                    .append("SELECT '").append(fields[0]).append("', fantasybaseball.players.PlayerID, '")
                    .append(fields[2]).append("', 'Y', 'N', '").append(getYear()).append("'\n")
                    .append("FROM fantasybaseball.players").append("\n")
                    .append("WHERE Name='").append(fields[1].replace("'", "''")).append("';").append("\n\n");
        }
        return statement.toString();
    }
    
    public static String getDBResult(String query) {
        try {
            ResultSet results = null;
            SQLConnection conn = new SQLConnection("localhost/fantasybaseball?autoReconnect=true&useSSL=false", "root", "clemson");
            results = conn.executeQuery(query);
            ResultSetMetaData rsmd = results.getMetaData();
            int count = rsmd.getColumnCount();
            StringBuilder builder = new StringBuilder();
            while (results.next()) {
                for(int x = 1; x <= count; x++) {
                    builder.append(results.getString(x));
                    if(x < count) {
                        builder.append(";");
                    } else {
                        builder.append("\n");
                    }
                }
            }
            results.close();
            conn.close();
            String result = builder.toString();
            if(null == result || result.isEmpty()) {
                return "";
            }
            result = result.substring(0, result.lastIndexOf("\n"));
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static int getDBResultAsInt(String query) {
        String result = getDBResult(query);
        if(result == null) {
            return 0;
        } else if (result.toUpperCase().contains("NULL")) {
            return 0;
        } else if (result.length() == 0) {
            return 0;
        }
        return Integer.parseInt(getDBResult(query));
    }
    
    public static char getDBResultAsChar(String query) {
        return getDBResult(query).charAt(0);
    }
    
    public static void writeStatementToDb(String statement) {
        try {
            SQLConnection conn = new SQLConnection("localhost/fantasybaseball?autoReconnect=true&useSSL=false", "root", "clemson");
            conn.executeUpdate(statement);
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String writeToDb(String statement) {
        try {
            SQLConnection conn = new SQLConnection("localhost/fantasybaseball?autoReconnect=true&useSSL=false", "root", "clemson");
            conn.executeUpdate(statement);
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        }
        return "SUCCESS";
    }
    
    public static Player parsePlayer(String text) {
        String split[] = text.replaceAll("\"", "").replaceAll("'", "''").split(",", 3);
        String name = split[0];
        String team = split[1];
        String position = split[2];
        Player player = new Player(name, team, position);
        return player;
    }
    
    public static void addESPNPlayersAndPrices(String file) {
        String players[] = FileManip.getTextFileContents(file).split("\n");
        ArrayList <String> espnList = new ArrayList<String>();
        Properties espnListAndPrice = new Properties();
        for(String line : players) {
            String player = line.substring(0, line.lastIndexOf(",")).replaceAll(",", ";");
            player = player.substring(0, player.lastIndexOf(";"));
            String split[] = player.split(";");
            String team = split[1].toUpperCase();
            //System.out.println(team);
            player = split[0] + ";" + team;
            //System.out.println(player);
            espnList.add(player.trim());
            String price = line.substring(line.lastIndexOf(",") + 1).trim();
            price = price.replaceAll("\n", "").replace("$", "");
            espnListAndPrice.put(player.trim(), price);
        }
        String playerList[] = getPlayerList().split("\n");
        ArrayList <String> dbList = new ArrayList<String>();
        for(String line : playerList) {
            dbList.add(line.trim());
        }
        ArrayList <String> priceList = new ArrayList<String>();
        for(String player : dbList) {
            if(espnListAndPrice.containsKey(player)) {
                //System.out.println(player + ": " + espnListAndPrice.getProperty(player));
            } else {
                espnListAndPrice.put(player.trim(), "1");
            }
        }
        Enumeration <?> enumer = espnListAndPrice.propertyNames();
        while(enumer.hasMoreElements()) {
            String key = (String) enumer.nextElement();
            //System.out.println(key + ": " + espnListAndPrice.getProperty(key));
            String split[] = key.split(";");
            //enterPlayerESPNPrice(split[0].replace("'", "''"), Integer.parseInt(espnListAndPrice.getProperty(key).replace("$", "")));
        }
    }
    
    public static String auditTeam(String file) throws ParseException {
        //System.out.println(file);
        String transactions = FileManip.getTextFileContents(file);
        //System.out.println(FileManip.getTextFileContents(file));
        char chars[] = {'�', '�', '�', '�', '�', '�', 'c', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�'};
        for(char c : chars) {
            if(c == '�' || c == '�') {
                transactions = transactions.replace(c, 'e');
            } else if(c == '�' || c == '�') {
                transactions = transactions.replace(c, 'u');
            } else if(c == '�' || c == '�') {
                transactions = transactions.replace(c, 'u');
            } else if(c == '�' || c == '�' || c == '�') {
                transactions = transactions.replace(c, 'a');
            } else if(c == '�' || c == '�' || c == 'o') {
                transactions = transactions.replace(c, 'a');
            } else if(c == '�' || c == '�') {
                transactions = transactions.replace(c, 'A');
            } else if(c == '�' || c == '�') {
                transactions = transactions.replace(c, 'E');
            } else if(c == '�' || c == '�') {
                transactions = transactions.replace(c, 'O');
            }
        }
        String lines[] = transactions.split("\n");
        ArrayList <String> trans = new ArrayList <String>();
        for(int x = 0; x < lines.length; x++) {
            StringBuilder line = new StringBuilder();
            line.append(lines[x]);
            int y = x + 1;
            if(y != lines.length) {
                if(lines[y].startsWith(",")) {
                    while(lines[y].trim().startsWith(",")) {
                        line.append(lines[y].replaceAll("\n", ""));
                        lines[y] = "";
                        y++;
                    }
                    lines[x] = line.toString();
                } else if(!lines[x].isEmpty() || !lines[x].equals("")){
                      trans.add(lines[x]);
                }
            if(line.toString().length() > 1)
                trans.add(line.toString().replaceAll("\n", "").replaceAll("\r", "").trim());
            }
        }
        StringBuilder sb = new StringBuilder();
        ArrayList <Contract> list = getContracts("d:/contracts.csv");
        List <Transaction> txns = new ArrayList<Transaction>();
        
        for(Contract contract : list) {
            //System.out.println(contract.getName() + " " + contract.getPrice() + " " + contract.getLength());
            for(String line : trans) {
                
                if(line.toUpperCase().contains(contract.getName().toUpperCase()) && line.toUpperCase().contains("DROP") && (contract.getLength() > 1)) {
                    sb.append(line).append("\n");
                    String split[] = line.split(",", 2);
                    //System.out.println(split[1]);
                    String move = split[1] + ",NAME:" + contract.getName() + ",PRICE:" + contract.getPrice() + ",LENGTH:" + contract.getLength();
                    
                    //Get all moves
                    ArrayList <String> moves = new ArrayList<String>();
                    for(Transaction txn: txns) {
                        moves.add(txn.getMove());
                    }
                    if(!moves.contains(move)) {
                        txns.add(new Transaction(split[0], move));
                    }
                }
            }
        }
        Collections.sort(txns, new Comparator<Transaction>() {
            public int compare(Transaction o1, Transaction o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        StringBuilder builder = new StringBuilder();
        for(Transaction txn : txns) {
            builder.append(txn.getDate() + "," + txn.getMove()).append("\n");
        }
        return builder.toString();
    }
    
    public static ArrayList<Contract> getContracts(String file) {
        String contents = FileManip.getTextFileContents(file);
        String lines[] = contents.split("\n");
        ArrayList <Contract> contracts = new ArrayList<Contract>();
        for(String line : lines) {
            String remove = line.substring(line.indexOf("("), line.indexOf(")"));
            //System.out.println(remove);
            line = line.replace("\"", "").replace(remove, "").replace(")", "").trim();
            //System.out.println(line);
            String split[] = line.split(",");
            Contract contract = new Contract(split[0].trim(), Integer.parseInt(split[1].trim().replace("$", "")), Integer.parseInt(split[2].trim()));
            contracts.add(contract);
        }
        return contracts;
    }
    
    public static void updatePositions() {
        String players[] = getPlayerListWithPositions().split("\n");
        for(String player : players) {
            String split[] = player.split(";");
            ArrayList <String> positions = new ArrayList<String>();
            //System.out.println(player);
            String pos[] = split[(split.length - 1)].split(",");
            for(String position : pos) {
                position = position.replace("LF", "OF").replace("CF", "OF").replace("RF", "OF");
                if(!positions.contains(position.trim())) {
                    positions.add(position.trim());
                }
            }
            StringBuilder builder = new StringBuilder();
            for(String position : positions) {
                builder.append(position).append(",");
            }
            String position = builder.toString();
            position = position.substring(0, (position.length() - 1)).replace(",DH", "").replace("DH,", "").trim();
            updatePosition(Integer.parseInt(split[0]), position);
        }
    }
    
    public static void parseContracts() {
        String text = FileManip.getTextFileContents("d:/contracts.txt");
        String[] players = text.split("\\. \\(");
        for(String player : players) {
            if(player.contains(" ")) {
                player = player.substring(player.indexOf(" ") + 1);
                if(player.contains("$")) {
                    if(player.lastIndexOf(" ") > player.indexOf("$")) {
                        player = player.substring(0, player.indexOf(",")) + ";" + player.substring(player.indexOf(", ") +2, player.indexOf("$") - 1).trim() + ";" + player.substring(player.indexOf("$") + 1, player.lastIndexOf(" "));
                    } else {
                        player = player.substring(0, player.indexOf(",")) + ";" + player.substring(player.indexOf(", ") +2, player.indexOf("$") - 1).trim() + ";" + player.substring(player.indexOf("$") + 1).trim();
                    }
                } else {
                    player = player.substring(0, player.indexOf(",")) + ";" + player.substring(player.indexOf(", ") + 2, player.indexOf("-") - 1).trim() + ";1";
                }
            }
            if(player.length() > 5) {
                //System.out.println(player);
            }
        }
        //System.out.println(text);
    }

    public static void insertNewContract(int team, int id, int price, int year, char rostered, char type) {
        StringBuilder statement = new StringBuilder();
        statement.append("INSERT INTO fantasybaseball.contracts (TeamID, PlayerID, ").append(year).append("Price, StartYear, CurrentlyRostered, AdditionType)\n")
            .append("VALUES ('").append(team).append("', '").append(id)
            .append("', '").append(price).append("', '").append(year).append("', '").append(rostered)
            .append("', '").append(type).append("')");
        System.out.println(statement.toString());
        writeStatementToDb(statement.toString());
    }
}