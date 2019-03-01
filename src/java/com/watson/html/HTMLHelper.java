/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.watson.html;

import com.watson.fantasybaseball.Worker;
import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class HTMLHelper {
    
    public static void main(String args[]) {
        //System.out.println(displayPlayers(1, false, 'I'));
    }
    
    public static String displayBudgetRow(int teamId, boolean trades)
    {
        //int year = Worker.getYear();
        int year = 2017;
        StringBuilder result = new StringBuilder();
        result.append("     <div class=\"row\">\n");
        result.append("         <div class=\"cell\"></div>\n");
        result.append("         <div class=\"cell\"></div>\n");
        result.append("         <div class=\"cell\"><font color=\"#000099\"><b>Totals:</b></font></div>\n");
        result.append("         <div class=\"cell\"><font color=\"#000099\"><b>$").append(Worker.teamBudgetForYear(teamId, year)).append("</b></font></div>\n");
        result.append("         <div class=\"cell\"><font color=\"#000099\"><b>$").append(Worker.teamBudgetForYear(teamId, year + 1)).append("</b></font></div>\n");
        result.append("         <div class=\"cell\"><font color=\"#000099\"><b>$").append(Worker.teamBudgetForYear(teamId, year + 2)).append("</b></font></div>\n");
        result.append("         <div class=\"cell\"><font color=\"#000099\"><b>$").append(Worker.teamBudgetForYear(teamId, year + 3)).append("</b></font></div>\n");
        result.append("         <div class=\"cell\"><font color=\"#000099\"><b>$").append(Worker.teamBudgetForYear(teamId, year + 4)).append("</b></font></div>\n");
        result.append("         <div class=\"cell\"><font color=\"#000099\"><b>$").append(Worker.teamBudgetForYear(teamId, year + 5)).append("</b></font></div>\n");
        result.append("         <div class=\"cell\"><font color=\"#000099\"><b>$").append(Worker.teamBudgetForYear(teamId, year + 6)).append("</b></font></div>\n");
        if(Worker.teamInactiveRosterCount(teamId) > 0) {
            result.append("         <div class=\"cell\"><font color=\"#000099\"><b>Totals include unrostered players listed below.</b></font></div>\n");
        } else {
            result.append("         <div class=\"cell\"></div>\n");
        }
        if(trades) {
            result.append("<div class=\"cell\"></div>\n");
        }
        result.append("     </div>\n");
        return result.toString();
    }
    
    public static String displayBudgetInfo(int teamId)
    {
        StringBuilder result = new StringBuilder();
        result.append("<div class=\"table\">\n");
	result.append("     <div class=\"row header green\">\n");
        result.append("     	<div class=\"cell\">Starting Budget</div><div class=\"cell\">Current Costs</div><div class=\"cell\">Open Roster Spots</div><div class=\"cell\">Draft $ Available</div><div class=\"cell\">Max Bid</div><div class=\"cell\">Avg Bid</div>\n");
	result.append("     </div>\n");
        result.append("     <div class=\"row\">\n");
        result.append("          <div class=\"cell\">$").append(Worker.STARTING_BUDGET).append("</div>\n");
        result.append("          <div class=\"cell\">$").append(Worker.teamBudget(teamId)).append("</div>\n");
        result.append("          <div class=\"cell\">").append(Worker.teamOpenRosterSpots(teamId)).append("</div>\n");
        result.append("          <div class=\"cell\">$").append(Worker.teamAvailableDollars(teamId)).append("</div>\n");
        result.append("          <div class=\"cell\">$").append(Worker.maxBid(teamId)).append("</div>\n");
        result.append("          <div class=\"cell\">$").append(Worker.avgBid(teamId)).append("</div>\n");
        result.append("     </div>\n");
        result.append("</div>");
        return result.toString();
    }
    
    public static String displayTeamSelectBox(int teamId, String jsp) {
        StringBuilder result = new StringBuilder();
        result.append("<center><table>\n");
        result.append("     <tr class=\"new\"><td align=\"right\"><form name=\"dropdown\" action=\"").append(jsp).append("\" method=\"POST\">\n");
        result.append("     <select style=\"width: auto;\" name=\"team\" onchange=\"this.form.submit()\">\n");
        String teams[] = Worker.getTeamList().split("\n");
        for(String team : teams) {
            String split[] = team.split(";");
            result.append("          <option value=\"").append(split[0]).append("\"");
            if(teamId == Integer.parseInt(split[0])) {
                result.append(" selected");
            }
            result.append(">").append(split[1]).append("</option>\n");
        }
        result.append("     </select>\n")
            .append("</form></td>").append("<td class=\"new\" align=\"right\"></td></tr>")
            .append("</table></center>");
        return result.toString();
    }
    
    public static String displayTradeBox(int playerId, int teamId) {
        
        StringBuilder result = new StringBuilder();
        result.append("<form name=\"tradeDropDown\" action=\"./ContractChanges.jsp\" method=\"POST\">\n")
            .append("     <select name=\"moveContract\" onchange=\"this.form.submit()\">\n")
            .append("          <option value=\"nothing\"></option>\n");
        int[] teams = Worker.getTeamIdsList();
        for(int team : teams) {
            result.append("          <option value=\"").append(playerId).append(",").append(team).append("\">").append(Worker.getTeamNameById(team)).append("</option>");
        }
        result.append("          <option value=\"").append(playerId).append(",ACTIVATE\">Activate Player</option>\n");
        result.append("          <option value=\"").append(playerId).append(",DROP\">Drop Player!</option>\n");
        result.append("          <option value=\"").append(playerId).append(",DELETE\">Delete Contract!</option>\n");
        result.append("     </select>\n")
            .append("     <input type=\"hidden\" name=\"team\" value=\"").append(teamId).append("\">\n")
            .append("</form>");
        return result.toString();
    }
    
    public static String displayExtensionBox(int playerId, int teamId, char rostered, int contractStartYear, int extensionStartYear, char contractType, int extensionLength) {
        
        StringBuilder result = new StringBuilder();
        boolean untouched = true;
        int year = Worker.getYear();
        //int contractStartYear = Worker.contractStartYear(playerId, teamId);
        //int extensionStartYear = Worker.extensionStartYear(playerId, teamId);
        //char contractType = Worker.contractType(playerId, teamId, rostered);
        int price = Worker.extensionPrice(playerId, teamId);
        //int extensionLength = Worker.contractExtensionLength(teamId, playerId);
        String option = "                       <option value=\"";
        String selected = "                     <option selected value=\"";
        result.append("\n               <form name=\"extensionDropdown\" action=\"./Contracts.jsp\" method=\"POST\">\n")
            .append("                   <select name=\"extension\" style=\"width: 350px\" onchange=\"this.form.submit()\">\n")
            .append("                       <option value=\"nothing\"></option>\n");
        //if((contractType == 'F') && ((contractStartYear == (year - 1)) || (extensionStartYear == year))) {
        if(contractType == 'F') {
            if(extensionLength == 2) {
                result.append(selected);
                untouched = false;
            } else {
                result.append(option);
            }
            result.append(playerId).append(",2\">Extend 2 years through ").append(year + 1).append(" at $").append(price).append(". (Up for renewal in ").append(year + 1).append(")</option>\n");
        //} else if((contractType == 'N') && ((contractStartYear == year) || (extensionStartYear == (year + 1)))) {
        } else if(contractType == 'N') {
            if(extensionLength == 2) {
                result.append(selected);
                untouched = false;
            } else {
                result.append(option);
            }
            result.append(playerId).append(",2\">Sign 2 years through ").append(year + 1).append(" at $").append(price).append(". (Up for renewal in ").append(year + 1).append(")</option>\n");
            if(extensionLength == 3) {
                result.append(selected);
                untouched = false;
            } else {
                result.append(option);
            }
            result.append(playerId).append(",3\">Sign 3 years through ").append(year + 2).append(" at $").append(price).append(". (Up for renewal in ").append(year + 2).append(")</option>\n");
            if(extensionLength == 5) {
                result.append(selected);
                untouched = false;
            } else {
                result.append(option);
            }
            result.append(playerId).append(",5\">Sign 5 years through ").append(year + 4).append(" at $").append(price).append(". (Up for renewal in ").append(year + 4).append(")</option>\n");
        } else {
            if(extensionLength == 1) {
                result.append(selected);
                untouched = false;
            } else {
                result.append(option);
            }
            result.append(playerId).append(",1\">Renew 1 year through ").append(year + 1).append(" at $").append(price).append(". (Non-renewable)</option>\n");
            if((extensionLength == 2) && (extensionStartYear != (year - 1))) {
                result.append(selected);
                untouched = false;
            } else {
                result.append(option);
            }
            result.append(playerId).append(",2\">Renew 2 years through ").append(year + 2).append(" at $").append(price).append(". (Up for renewal in ").append(year + 2).append(")</option>\n");
            if((extensionLength == 3) && (extensionStartYear == (year + 1))) {
                result.append(selected);
                untouched = false;
            } else {
                result.append(option);
            }
            result.append(playerId).append(",3\">Renew 3 years through ").append(year + 3).append(" at $").append(price).append(". (Up for renewal in ").append(year + 3).append(")</option>\n");
            if((extensionLength == 5) && (extensionStartYear == (year + 1))) {
                result.append(selected);
                untouched = false;
            } else {
                result.append(option);
            }
            result.append(playerId).append(",5\">Renew 5 years through ").append(year + 5).append(" at $").append(price).append(". (Up for renewal in ").append(year + 5).append(")</option>\n");
        }
        if(extensionLength == 0 || untouched) {
            result.append(selected);
        } else {
            result.append(option);
        }
        result.append(playerId).append(",expire\">Release after ").append(year).append("</option>\n");
        result.append("                 </select>\n")
            .append("                   <input type=\"hidden\" name=\"team\" value=\"").append(teamId).append("\">\n")
            .append("               </form>\n");
        return result.toString();
    }
    
    public static String displayPlayers(int teamId, boolean trades, char type)
    {
        String text = "";
        char rostered = ' ';
        String header = "     <div class=\"row header\">\n";
        if(type == 'A') {
            text = Worker.getActiveContractsForTeam(teamId);
            rostered = 'Y';
        } else {
            text = Worker.getInactiveContractsForTeam(teamId);
            header = "     <div class=\"row header red\">\n";
            rostered = 'N';
        }
        if(text.isEmpty() || null == text) {
            return "";
        }
        String contractHeader = "";
        if(type == 'A') {
            contractHeader = "<div class=\"cell\">Contract</div>";
        }
        String tradeHeader = "";
        if(trades) {
            tradeHeader = "<div class=\"cell\">Move Contract</div>";
        }
        String contracts[] = text.split("\n");
        StringBuilder result = new StringBuilder();
        result.append("<div class=\"table\">\n");
        result.append(header);
        result.append("         <div class=\"cell\">Player Name</div><div class=\"cell\">Team</div><div class=\"cell\">Position</div><div class=\"cell\">2017 Price</div><div class=\"cell\">2018 Price</div><div class=\"cell\">2019 Price</div><div class=\"cell\">2020 Price</div><div class=\"cell\">2021 Price</div><div class=\"cell\">2022 Price</div><div class=\"cell\">2023 Price</div>");
        result.append(contractHeader).append(tradeHeader).append("\n");
        result.append("     </div>\n");
        Arrays.sort(contracts);
        for(String contract : contracts) {
            String split[] = contract.split(";");
            int playerId = Worker.getPlayerIdByName(split[0]);
            //char conType = Worker.contractType(playerId, teamId, rostered);
            //System.out.println(split[0]);
            char conType = split[11].trim().charAt(0);
            //int contractLength = Worker.contractLength(playerId, teamId);
            int contractLength = Integer.parseInt(split[12]);
            if(contractLength == 0) {
                contractLength = Integer.parseInt(split[14]);
            }
            //int extensionStartYear = Worker.extensionStartYear(playerId, teamId);
            int extensionStartYear = Integer.parseInt(split[15]);
            //int extensionLength = Worker.extensionLength(playerId, teamId);
            int extensionLength = Integer.parseInt(split[14]);
            int expirationYear = Worker.expirationYear(playerId, teamId, contractLength, extensionStartYear, extensionLength);
            boolean isExtendable = Worker.isExtendable(playerId, teamId, rostered, extensionStartYear, expirationYear, extensionLength, contractLength);
            //boolean franchise = Worker.isFranchisePlayer(playerId, teamId);
            boolean franchise = false;
            if(split[10].toUpperCase().trim().contains("Y")) {
                franchise = true;
            }
            String td = "<div class=\"cell\" align=\"left\">";
            String tdEnd = "</div>\n";
            result.append("     <div class=\"row\">\n");
            if(franchise) {
                tdEnd = "</b></div>\n";
            }
            for(int x = 0; x < 10; x++) {
                if(x == 0 || x == 10) {
                    td = "<div class=\"cell\" align=\"left\">";
                } else {
                    td = "<div class=\"cell\" align=\"center\">";
                }
                if(franchise && (type == 'A')) {
                    td += "<b>";
                }
                if(x > 2) {
                    split[x] = "$" + split[x];
                }
                result.append("          ").append(td).append(split[x]).append(tdEnd);
            }
            int year = Worker.getYear();
            if(type == 'A') {
                td = "<div class=\"cell\" align=\"left\">";
                if(franchise) {
                    td += "<b>";
                }
                if(conType == 'N' || conType == 'F') {
                    //result.append("          ").append(td).append(displayExtensionBox(playerId, teamId, rostered, contractStartYear, extensionStartYear, conType, extensionLength)).append(tdEnd);
                } else if(Worker.getPlayerPriceForYear(playerId, year, teamId) == 0) {
                    result.append("          ").append(td).append("EXPIRED!").append(tdEnd);
                } else if(expirationYear == year) {
                    if(!isExtendable) {
                        result.append("          ").append(td).append("Expiring after current season").append(tdEnd);
                    } else {
                        //result.append("          ").append(td).append("Up for extension before current season").append(tdEnd);
                        result.append("          ").append(td).append("Expiring after current season").append(tdEnd);
                        //System.out.println(playerId);
                        //result.append("          ").append(td).append(displayExtensionBox(playerId, teamId, rostered, contractStartYear, extensionStartYear, conType, extensionLength)).append(tdEnd);
                    }
                } else if(extensionStartYear == (year + 1)) {
                    
                    //result.append("          ").append(td).append("Expiring following ").append(Worker.expirationYear(playerId, teamId)).append(tdEnd);
                    if(!isExtendable) {
                        //result.append("          ").append(td).append(displayExtensionBox(playerId, teamId, rostered, contractStartYear, extensionStartYear, conType, extensionLength)).append(tdEnd);
                        result.append("          ").append(td).append("Expiring following ").append(expirationYear).append(tdEnd);
                        //result.append("          ").append(td).append("Up for extension in ").append(expirationYear).append(tdEnd);
                    } else if((contractLength > 1) || (extensionLength > 1)){
                        //result.append("          ").append(td).append(displayExtensionBox(playerId, teamId, rostered, contractStartYear, extensionStartYear, conType, extensionLength)).append(tdEnd);
                        result.append("          ").append(td).append("Up for extension in ").append(expirationYear).append(tdEnd);
                    } else {
                        result.append("          ").append(td).append("Expiring following ").append(expirationYear).append(tdEnd);
                        //result.append("          ").append(td).append("Up for extension in ").append(expirationYear).append(tdEnd);
                        //result.append("          ").append(td).append(displayExtensionBox(playerId, teamId, rostered, contractStartYear, extensionStartYear, conType, extensionLength)).append(tdEnd);
                    }
                } else if((extensionLength == 1) || (extensionStartYear == year)) {
                    //result.append("          ").append(td).append("Expiring following ").append(Worker.expirationYear(playerId, teamId)).append(tdEnd);
                    if(!isExtendable) {
                        //result.append("          ").append(td).append(displayExtensionBox(playerId, teamId, rostered, contractStartYear, extensionStartYear, conType, extensionLength)).append(tdEnd);
                        result.append("          ").append(td).append("Expiring following ").append(expirationYear).append(tdEnd);
                        //result.append("          ").append(td).append(displayExtensionBox(playerId, teamId, rostered, contractStartYear, extensionStartYear, conType, extensionLength)).append(tdEnd);
                        //result.append("          ").append(td).append("Up for extension in ").append(expirationYear).append(tdEnd);
                    } else if((contractLength > 1) || (extensionLength > 1)){
                        result.append("          ").append(td).append("Up for extension in ").append(expirationYear).append(tdEnd);
                    } else {
                        result.append("          ").append(td).append("Expiring following ").append(expirationYear).append(tdEnd);
                        //result.append("          ").append(td).append("Up for extension in ").append(expirationYear).append(tdEnd);
                        //result.append("          ").append(td).append(displayExtensionBox(playerId, teamId, rostered, contractStartYear, extensionStartYear, conType, extensionLength)).append(tdEnd);
                    }
                } else {
                    result.append("          ").append(td).append("Up for extension in ").append(expirationYear).append(tdEnd);
                }
            }
            if(trades) {
                result.append("          ").append(td).append(displayTradeBox(playerId, teamId)).append(tdEnd);
            }
            result.append("     </div>\n");
        }
        if(type == 'A') {
            result.append(displayBudgetRow(teamId, trades)).append("\n");
        }
        result.append("</div>");
        return result.toString();
    }
    
    public static String displayPlayersWithEdits(int teamId, boolean trades, char type)
    {
        String text = "";
        char rostered = ' ';
        String header = "     <div class=\"row header\">\n";
        if(type == 'A') {
            text = Worker.getActiveContractsForTeam(teamId);
            rostered = 'Y';
        } else {
            text = Worker.getInactiveContractsForTeam(teamId);
            header = "     <div class=\"row header red\">\n";
            rostered = 'N';
        }
        if(text.isEmpty() || null == text) {
            return "";
        }
        String contractHeader = "";
        if(type == 'A') {
            contractHeader = "<div class=\"cell\">Contract</div>";
        }
        String tradeHeader = "";
        if(trades) {
            tradeHeader = "<div class=\"cell\">Move Contract</div>";
        }
        String contracts[] = text.split("\n");
        StringBuilder result = new StringBuilder();
        result.append("<div class=\"table\">\n");
        result.append(header);
        result.append("         <div class=\"cell\">Player Name</div><div class=\"cell\">Team</div><div class=\"cell\">Position</div><div class=\"cell\">2017 Price</div><div class=\"cell\">2018 Price</div><div class=\"cell\">2019 Price</div><div class=\"cell\">2020 Price</div><div class=\"cell\">2021 Price</div><div class=\"cell\">2022 Price</div><div class=\"cell\">2023 Price</div>");
        result.append(contractHeader).append(tradeHeader).append("\n");
        result.append("     </div>\n");
        Arrays.sort(contracts);
        for(String contract : contracts) {
            String split[] = contract.split(";");
            int playerId = Worker.getPlayerIdByName(split[0]);
            //char conType = Worker.contractType(playerId, teamId, rostered);
            char conType = split[11].trim().charAt(0);
            //int contractLength = Worker.contractLength(playerId, teamId);
            int contractLength = Integer.parseInt(split[12]);
            if(contractLength == 0) {
                contractLength = Integer.parseInt(split[14]);
            }
            //int extensionStartYear = Worker.extensionStartYear(playerId, teamId);
            int contractStartYear = Integer.parseInt(split[13]);
            int extensionStartYear = Integer.parseInt(split[15]);
            //int extensionLength = Worker.extensionLength(playerId, teamId);
            int extensionLength = Integer.parseInt(split[14]);
            int expirationYear = Worker.expirationYear(playerId, teamId, contractLength, extensionStartYear, extensionLength);
            boolean isExtendable = Worker.isExtendable(playerId, teamId, rostered, extensionStartYear, expirationYear, extensionLength, contractLength);
            //boolean franchise = Worker.isFranchisePlayer(playerId, teamId);
            boolean franchise = false;
            if(split[10].toUpperCase().trim().contains("Y")) {
                franchise = true;
            }
            String td = "<div class=\"cell\" align=\"left\">";
            String tdEnd = "</div>\n";
            result.append("     <div class=\"row\">\n");
            if(franchise) {
                tdEnd = "</b></div>\n";
            }
            for(int x = 0; x < 10; x++) {
                if(x == 0 || x == 10) {
                    td = "<div class=\"cell\" align=\"left\">";
                } else {
                    td = "<div class=\"cell\" align=\"center\">";
                }
                if(franchise && (type == 'A')) {
                    td += "<b>";
                }
                if(x > 2) {
                    split[x] = "$" + split[x];
                }
                result.append("          ").append(td).append(split[x]).append(tdEnd);
            }
            int year = Worker.getYear();
            if(type == 'A') {
                td = "<div class=\"cell\" align=\"left\">";
                if(franchise) {
                    td += "<b>";
                }
                if(conType == 'N' || conType == 'F') {
                    result.append("          ").append(td).append(displayExtensionBox(playerId, teamId, rostered, contractStartYear, extensionStartYear, conType, extensionLength)).append(tdEnd);
                } else if(Worker.getPlayerPriceForYear(playerId, year, teamId) == 0) {
                    result.append("          ").append(td).append("EXPIRED!").append(tdEnd);
                } else if(expirationYear == year) {
                    if(!isExtendable) {
                        result.append("          ").append(td).append("Expiring after current season").append(tdEnd);
                    } else {
                        //result.append("          ").append(td).append("Up for extension before current season").append(tdEnd);
                        //result.append("          ").append(td).append("Expiring after current season").append(tdEnd);
                        //System.out.println(playerId);
                        result.append("          ").append(td).append(displayExtensionBox(playerId, teamId, rostered, contractStartYear, extensionStartYear, conType, extensionLength)).append("          ").append(tdEnd);
                    }
                } else if(extensionStartYear == (year + 1)) {
                    
                    //result.append("          ").append(td).append("Expiring following ").append(Worker.expirationYear(playerId, teamId)).append(tdEnd);
                    if(!isExtendable) {
                        result.append("          ").append(td).append(displayExtensionBox(playerId, teamId, rostered, contractStartYear, extensionStartYear, conType, extensionLength)).append("          ").append(tdEnd);
                        //result.append("          ").append(td).append("Expiring following ").append(expirationYear).append(tdEnd);
                        //result.append("          ").append(td).append("Up for extension in ").append(expirationYear).append(tdEnd);
                    } else if((contractLength > 1) || (extensionLength > 1)){
                        result.append("          ").append(td).append(displayExtensionBox(playerId, teamId, rostered, contractStartYear, extensionStartYear, conType, extensionLength)).append("          ").append(tdEnd);
                        //result.append("          ").append(td).append("Up for extension in ").append(expirationYear).append(tdEnd);
                    } else {
                        //result.append("          ").append(td).append("Expiring following ").append(expirationYear).append(tdEnd);
                        //result.append("          ").append(td).append("Up for extension in ").append(expirationYear).append(tdEnd);
                        result.append("          ").append(td).append(displayExtensionBox(playerId, teamId, rostered, contractStartYear, extensionStartYear, conType, extensionLength)).append("          ").append(tdEnd);
                    }
                } else if((extensionLength == 1) || (extensionStartYear == year)) {
                    //result.append("          ").append(td).append("Expiring following ").append(Worker.expirationYear(playerId, teamId)).append(tdEnd);
                    if(!isExtendable) {
                        //result.append("          ").append(td).append(displayExtensionBox(playerId, teamId, rostered, contractStartYear, extensionStartYear, conType, extensionLength)).append(tdEnd);
                        //result.append("          ").append(td).append("Expiring following ").append(expirationYear).append(tdEnd);
                        result.append("          ").append(td).append(displayExtensionBox(playerId, teamId, rostered, contractStartYear, extensionStartYear, conType, extensionLength)).append("          ").append(tdEnd);
                        //result.append("          ").append(td).append("Up for extension in ").append(expirationYear).append(tdEnd);
                    } else if((contractLength > 1) || (extensionLength > 1)){
                        result.append("          ").append(td).append("Up for extension in ").append(expirationYear).append(tdEnd);
                    } else {
                        //result.append("          ").append(td).append("Expiring following ").append(expirationYear).append(tdEnd);
                        //result.append("          ").append(td).append("Up for extension in ").append(expirationYear).append(tdEnd);
                        result.append("          ").append(td).append(displayExtensionBox(playerId, teamId, rostered, contractStartYear, extensionStartYear, conType, extensionLength)).append(tdEnd);
                    }
                } else {
                    result.append("          ").append(td).append("Up for extension in ").append(expirationYear).append(tdEnd);
                }
            }
            if(trades) {
                result.append("          ").append(td).append(displayTradeBox(playerId, teamId)).append(tdEnd);
            }
            result.append("     </div>\n");
        }
        if(type == 'A') {
            result.append(displayBudgetRow(teamId, trades)).append("\n");
        }
        result.append("</div>");
        return result.toString();
    }
    
    public static String displayInactivePlayers(int teamId, boolean trades)
    {
        String text = Worker.getInactiveContractsForTeam(teamId);
        if(text.isEmpty() || null == text) {
            return "";
        }
        String contracts[] = text.split("\n");
        StringBuilder result = new StringBuilder();
        result.append("<b>On Contract but not Rostered:</b>");
        result.append("<div class=\"table\">\n");
        result.append("     <div class=\"row header red\">\n");
        result.append("         <div class=\"cell\">Player Name</div><div class=\"cell\">Team</div><div class=\"cell\">Position</div><div class=\"cell\">2015 Price</div><div class=\"cell\">2016 Price</div><div class=\"cell\">2017 Price</div><div class=\"cell\">2018 Price</div><div class=\"cell\">2019 Price</div><div class=\"cell\">Contract</div>\n");
        result.append("     </div>");
        Arrays.sort(contracts);
        for(String contract : contracts) {
            String split[] = contract.split(";");
            int playerId = Worker.getPlayerIdByName(split[0]);
            result.append("     <div class=\"row\">\n");
            for(int x = 0; x < split.length; x++) {
                if(x > 2) {
                    split[x] = "$" + split[x];
                }
                result.append("          <div class=\"cell\">").append(split[x]).append("</div>\n");
            }
            if(trades) {
                result.append("          <div class=\"cell\">").append(displayTradeBox(playerId, teamId)).append("</div>\n");
            }
            result.append("     </div>\n");
        }
        result.append("</div>");
        return result.toString();
    }
    
}