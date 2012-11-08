<%@page import="java.util.HashMap"%>
<%@ page import="CONTROLLER.Controller" %>
<%
	final String //Skriptspezifische Ids
	spanId = "spanbackId",
	submitId = "usercreator",
	usernameId = "username",
	passwordId = "password"
	;
	
	String action = request.getParameter(Controller.shsconfig.hiddensignId);//ICH BIN HIER:::::::::::::::::::::::::::::::::::
	Controller.shsuser = Controller.shsconfig.login(action,new HashMap());
%>


<table>
	<tr>
		<td class="loginlabel"><%=Controller.shsdb.text("Username")%></td>
		<td class="logininput"><%=Controller.shsgui.defaultTXTInput(usernameId)%></td>
	</tr>
	<tr>
		<td class="loginlabel"><%=Controller.shsdb.text("Password")%></td>
		<td class="logininput"><%=Controller.shsgui.createInput("password",passwordId,50)%></td>
	</tr>
</table>

<div id="<%=Controller.shsconfig.signupId%>" style="display:none;">
	<%@ include file="signup.jsp" %>
</div>

<table>
	<tr><td style="text-align:right;">
		<!-- Not a memeber yet -->	
		<%=	"<span id='"+spanId+"'>"+					
			Controller.shsgui.createA("clicktosignup('tosignup')",1000000000)+
			Controller.shsgui.space(10,Controller.shsconfig.horiz) +
			"</span>"+
			Controller.shsgui.createInput("submit",submitId,Controller.shsdb.text(2222)) %>
		
		<input type="hidden" id="<%=Controller.shsconfig.hiddensignId %>" name="<%=Controller.shsconfig.hiddensignId %>" value=""/>
		<script>
			function clicktosignup(action){
				simpleToggle("signup");	
				if(action == "tosignup"){
					tohtml="<input type='button' title='"+<%=Controller.shsdb.text(333)%>+"' value='"+<%=Controller.shsdb.text(333)%>+"' onclick='clicktosignup(\"\")'/>";							
					$("#signaction").val($("#spanbackId").html());				
					$("#usercreator").val(<%=Controller.shsdb.text(11111)%>);
				
				}else{
					tohtml= $("#signaction").val();							
					$("#signaction").val("");							
					$("#usercreator").val(<%=Controller.shsdb.text(2222)%>);
				
				}
				
				$("#spanbackId").html(tohtml);
			}
		</script>
	</td></tr>		
</table>

