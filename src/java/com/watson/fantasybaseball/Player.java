/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.watson.fantasybaseball;

/**
 *
 * @author Administrator
 */
public class Player {
    
    public String name = "";
    public String team = "";
    public String position = "";
    public int ESPNPrice;
    
    public Player() {
        }
    
    public Player(String name, String team, String position) {
        this.name = name;
        this.team = team;
        this.position = position;
    }
    
    public Player(String name, String team, String position, int price) {
        this.name = name;
        this.team = team;
        this.position = position;
        this.ESPNPrice = price;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getESPNPrice() {
        return ESPNPrice;
    }

    public void setESPNPrice(int ESPNPrice) {
        this.ESPNPrice = ESPNPrice;
    }
    
    
}
