<%@page import="java.util.HashMap"%>
<%@ page import="CONTROLLER.Controller" %>
<%
	final String //Skriptspezifische Ids
	spanId = "spanbackId",
	submitId = "usercreator",
	bakupId = "backup",
	defaultAction = "'" + Controller.shsconfig.signactionB + "'" //signup
	;
%>


<table>
	<tr>
		<td class="loginlabel"><%=Controller.shsdb.text("Username")%></td>
		<td class="logininput"><%=Controller.shsgui.defaultTXTInput(Controller.shsconfig.usernameId)%></td>
	</tr>
	<tr>
		<td class="loginlabel"><%=Controller.shsdb.text("Password")%></td>
		<td class="logininput"><%=Controller.shsgui.createInput("password",Controller.shsconfig.passwordId,50)%></td>
	</tr>
</table>

<div id="<%=Controller.shsconfig.signupId%>" style="display:none;">
	<%@ include file="signup.jsp" %>
</div>

<table>
	<tr><td style="text-align:right;">
		<!-- Not a memeber yet -->	
		<%=	
			"<span id='"+spanId+"'>"+					
			Controller.shsgui.createA("clicktochange('signup')",1000000000)+
			Controller.shsgui.space(10,Controller.shsconfig.horiz) +
			"</span>"+
			Controller.shsgui.createInput("submit",submitId,Controller.shsdb.text(2222)) 
		%>
		
		<input type="hidden" id="<%=bakupId %>" name="<%=bakupId %>" value=""/>
		<input type="hidden" id="<%=Controller.shsconfig.signactionId %>" name="<%=Controller.shsconfig.signactionId %>" value="signin"/><!-- id="signaction" -->
		<script>
			function clicktochange(action){					
				bakupId = "#backup";
				signactionId = "#signaction";
				spanId = "#spanbackId";
				submitId = "#usercreator";
											
				if(action == "signup"){
					tohtml = "<input type='button' title='"+<%=Controller.shsdb.text(333)%>+"' value='"+<%=Controller.shsdb.text(333)%>+"' onclick='clicktochange(\"signin\")'/>";
					tohtml += "&nbsp;&nbsp;<input type='reset' value='"+<%=Controller.shsdb.text(55)%>+"'/>&nbsp;&nbsp;";
					
					if($(bakupId).val() == ''){//So wird es sichergestellt dass die ursprüngliche Form des span-Tags aufbewahren wird
						$(bakupId).val($(spanId).html());
					}
					$(submitId).val(<%=Controller.shsdb.text(11111)%>);
				}else if(action == "signin"){
					tohtml = $(bakupId).val();
					$(submitId).val(<%=Controller.shsdb.text(2222)%>);
				}				
				animateToggle("signup","verti");
				$(spanId).html(tohtml);
				$(signactionId).val(action);
				
			}
		</script>
	</td></tr>		
</table>

