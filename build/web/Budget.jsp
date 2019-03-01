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
    String team = "";
    String extension = "";
    int val = 1;
    String activeContracts = "";
    String inactiveContracts = "";
    String budgetInfo = "";
    String msg = "<br>";
    if(request.getParameter("team") != null) {
        
        team = request.getParameter("team");
        val = Integer.parseInt(team);
    }
    if(request.getParameter("extension") != null) {
        extension = request.getParameter("extension");
        if(!extension.contains("nothing")) {
            String split[] = extension.split(",");
            int playerId = Integer.parseInt(split[0]);
            if(split[1].contains("expire")) {
                msg = Worker.expireContract(val, playerId);
            } else {
                msg = Worker.extendContract(val, playerId, Integer.parseInt(split[1]));
            }
        }
    }
    if(msg.toUpperCase().contains("UNABLE")) {
        out.println("<font color=\"red\"><b>" + msg + "</b></font>");
    } else {
        out.println("<font color=\"black\"><b>" + msg + "</b></font>");
    }
    activeContracts = HTMLHelper.displayActivePlayers(val, false);
    inactiveContracts = HTMLHelper.displayInactivePlayers(val);
    budgetInfo = HTMLHelper.displayBudgetInfo(val);
    team = Worker.getTeamNameById(val);
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<meta http-equiv="Content-Style-Type" content="text/css" />
        <title>Contracts for <%=team%></title>
        <link href="./includes/css/wide.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <div id="main">
            <div id="form">
                <%=HTMLHelper.displayTeamSelectBox(val, "AddContract.jsp")%>
            </div>
            <div id="table">
                <center>
                    <%=activeContracts%>
                </center>
            </div>
            <br>
            <div id="table3">
                <center>
                    <%=budgetInfo%>
                </center>
            </div>
            <br>
            <div id="table2">
                <center>
                    <%=inactiveContracts%>
                </center>
            </div>
        </div>
    </body>
</html>