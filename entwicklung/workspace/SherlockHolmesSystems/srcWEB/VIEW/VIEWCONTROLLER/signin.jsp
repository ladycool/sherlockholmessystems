<%@page import="java.util.HashMap"%>
<%@ page import="CONTROLLER.Controller" %>
<%
	final String //Skriptspezifische Ids
	spanId = "spanbackId",
	submitId = "usercreator",
	bakupId = "backup",
	defaultAction = "'" + Controller.shsconfig.signactionB + "'" //signup
	;

	String
	username = (String)session.getAttribute(Controller.shsconfig.usernameId)
	;
	
	if(username == null){ username="";}
   //Controller.shsdb.text("username")
   //out.print(Controller.shsdb != null);
   
%>


<table>
	<tr><td class="shstitle" colspan="2">
			<%=Controller.shsconfig.programName %>
			<%=Controller.shsgui.createImg("","",Controller.shsconfig.print) %>
		</td>
	</tr>
	<tr>
		<td class="loginlabel"><%=Controller.shsdb.text("Benutzername") %></td>
		<td class="logininput"><%=Controller.shsgui.createInput("text",Controller.shsconfig.usernameId,username,50,"")%></td>
	</tr>
	
	<tr>
		<td class="loginlabel"><%=Controller.shsdb.text("Passwort")%></td>
		<td class="logininput"><%=Controller.shsgui.createInput("password",Controller.shsconfig.passwordId,50)%></td>
	</tr>
	
</table>

<div id="<%=Controller.shsconfig.signupId%>" style="display:none;">
	<%@ include file="signup.jsp" %>
</div>

<table>
	<tr><td class="right" style="padding-top:10px;">
		<!-- Not a memeber yet -->	
		<%=	
			"<span id='"+spanId+"'>"+					
			Controller.shsgui.createA("clicktochange('signup')",Controller.shsdb.text("Noch kein Mitglied?"))+
			Controller.shsgui.space(10,Controller.shsconfig.horiz) +
			"</span>"+
			Controller.shsgui.createInput("submit",submitId,Controller.shsdb.text("Anmelden")) +
			Controller.shsgui.createInput("hidden", Controller.shsconfig.signactionId, "signin") //name="signaction"
		%>
		<input type="hidden" id="<%=bakupId%>" value="">
		
		<script>
			function clicktochange(action){					
				bakupId = "#backup";
				signactionId = "#signaction";
				spanId = "#spanbackId";
				submitId = "#usercreator";
											
				if(action == "signup"){
					tohtml = "<input type='button' title='"+<%=Controller.shsdb.text("\"Zur&uuml;ck&nbsp;zum&nbsp;Anmeldeformular\"")%>+"' value='"+<%=Controller.shsdb.text("\"Zur&uuml;ck\"")%>+"' onclick='clicktochange(\"signin\")'/>";
					tohtml += "&nbsp;&nbsp;<input type='reset' value='"+<%=Controller.shsdb.text("\"Reset\"")%>+"'/>&nbsp;&nbsp;";
					
					if($(bakupId).val() == ''){//So wird es sichergestellt dass die ursprüngliche Form des span-Tags aufbewahren wird
						$(bakupId).val($(spanId).html());
					}
					$(submitId).val(<%=Controller.shsdb.text("'Registrieren'")%>);
				}else if(action == "signin"){
					tohtml = $(bakupId).val();
					$(submitId).val(<%=Controller.shsdb.text("'Anmelden'")%>);
				}				
				animateToggle("signup","verti");
				$(spanId).html(tohtml);
				$(signactionId).val(action);
				
			}
		</script>
	</td></tr>		
</table>

