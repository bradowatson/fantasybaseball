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
    String player;
    int playerId;
    if(request.getParameter("player") != null) {
        player = request.getParameter("player");
        playerId = Worker.getPlayerIdByName(player);
        Worker.deactivatePlayer(playerId, Worker.getPlayerOwner(playerId));
    }
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<meta http-equiv="Content-Style-Type" content="text/css" />
        <title>Drop a Player</title>
        <link href="./includes/css/wide.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <div id="main">
            <div id="form">
                <form action="./DropContract.jsp" method="POST">
                    <table>
                        <tr>
                            <td align="right">Player: </td>
                            <td align="left"><input type="text" name="player"></td>
                            <td align="left"><input type="submit" value="Submit"></td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </body>
</html>