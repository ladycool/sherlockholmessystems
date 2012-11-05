<%@ page import="MODEL._Config" %>

<!-- 
@Ehsan, Engin, Stella
Hier wird später die blockdisplace()-Methode eingeführt. Ein Datei für alle --> MVC-Pattern
 -->

<div id="<%=_Config.mainId%>">
	<table>
		<tr><td colspan="2">
			<div id="<%=_Config.headerId%>">
				<%@ include file="VIEWCONTROLLER/header.jsp" %>
			</div>
		</td></tr>
		<tr>
			<td><div id="<%=_Config.mainwestId%>">
				<%@ include file="VIEWCONTROLLER/mainwest.jsp" %>
			</div></td>
			<td>
				<div id="<%=_Config.mainnordId%>">
					<%@ include file="VIEWCONTROLLER/mainnord.jsp" %>
				</div>
				<br><hr>
				<div id="<%=_Config.mainsouthId%>">
					<%@ include file="VIEWCONTROLLER/mainsouth.jsp" %>
				</div>
			</td>
		</tr>
		<tr><td colspan="2">
			<div id="<%=_Config.progressId%>">
				<%@ include file="VIEWCONTROLLER/progress.jsp" %>
			</div>
		</td></tr>
	</table>
</div>