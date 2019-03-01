<%-- 
    Document   : UserStats
    Created on : Apr 26, 2011, 10:27:51 AM
    Author     : BWatson
--%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.watson.fantasybaseball.Worker"%>
<%@page import="java.io.File"%>
<%@page import="com.watson.html.HTMLHelper"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%
    int team = 1;
    int newTeamId;
    int playerId;
    String activeContracts = "";
    String inactiveContracts = "";
    String msg = "";
    if(request.getParameter("team") != null) {
        team = Integer.parseInt(request.getParameter("team"));
    }
    if(request.getParameter("moveContract") != null) {
        String[] split = request.getParameter("moveContract").split(",");
        playerId = Integer.parseInt(split[0]);
        if(split[1].trim().matches("ACTIVATE")) {
            Worker.activatePlayer(playerId, team);
        } else if(split[1].trim().matches("DROP")) {
            Worker.deactivatePlayer(playerId, team);
        } else if(split[1].trim().matches("DELETE")) {
            Worker.deleteContract(playerId, team);
        } else {
            newTeamId = Integer.parseInt(split[1]);
            msg = Worker.moveContract(team, newTeamId, playerId);
        }
    }
    activeContracts = HTMLHelper.displayPlayers(team, true, 'A');
    inactiveContracts = HTMLHelper.displayPlayers(team, true, 'I');
    String teamName = Worker.getTeamNameById(team);
%>
<br>
<%=msg%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<meta http-equiv="Content-Style-Type" content="text/css" />
        <title>Contracts for <%=teamName%></title>
        <link href="./includes/css/table.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <div id="main">
            <div id="form">
                <%=HTMLHelper.displayTeamSelectBox(team, "./ContractChanges.jsp")%>
            </div>
            <center>
                <br>
                <font color="white"><b>Active Contracts</b></font>
                <%=activeContracts%>
                <font color="white"><b>Inactive Contracts</b></font>
                <%=inactiveContracts%>
            </center>
        </div>
    </body>
</html>