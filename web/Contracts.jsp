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
            int length = Integer.parseInt(split[1]);
            if(split[1].contains("expire")) {
                msg = Worker.expireContract(val, playerId);
            } else {
                msg = Worker.extendContract(val, playerId, length, (Worker.getYear() + 1), (Worker.getYear() + 1 + length), length);
            }
        }
    }
    if(msg.toUpperCase().contains("UNABLE")) {
        out.println("<center><font color=\"red\"><b>" + msg + "</b></font></center>");
    } else {
        out.println("<center><font color=\"white\"><b>" + msg + "</b></font></center>");
    }
    activeContracts = HTMLHelper.displayPlayersWithEdits(val, false, 'A');
    //activeContracts = HTMLHelper.displayPlayers(val, false, 'A');
    inactiveContracts = HTMLHelper.displayPlayers(val, false, 'I');
    budgetInfo = HTMLHelper.displayBudgetInfo(val);
    team = Worker.getTeamNameById(val);
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<meta http-equiv="Content-Style-Type" content="text/css" />
        <title>Contracts for <%=team%></title>
        <link href="./includes/css/table.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <div id="main">
            <div id="form">
                <%=HTMLHelper.displayTeamSelectBox(val, "./Contracts.jsp")%>
            </div>
            <center>
                <br>
                <font color="white"><b>Pre-Draft Budget Information</b></font>
                <%=budgetInfo%>
                <font color="white"><b>Active Contracts</b></font>
                <%=activeContracts%>
                <font color="white"><b>Inactive Contracts</b></font>
                <%=inactiveContracts%>
            </center>
        </div>
    </body>
</html>