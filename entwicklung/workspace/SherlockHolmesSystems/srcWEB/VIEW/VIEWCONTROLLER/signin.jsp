<%@ page import="SERVICE.Config" %>
<%
	final String //Skriptspezifische Ids
	spanId = "spanbackId",
	submitId = "usercreator",
	hiddenbufId = "hiddenbuffer",
	usernameId = "username",
	passwordId = "password"
	;
%>


<form action="">
	<table>
		<tr>
			<td class="loginlabel"><%=Config.shsdb.text("Username")%></td>
			<td class="logininput"><%=Config.shsgui.defaultTXTInput(usernameId)%></td>
		</tr>
		<tr>
			<td class="loginlabel"><%=Config.shsdb.text("Password")%></td>
			<td class="logininput"><%=Config.shsgui.createInput("password",passwordId,50)%></td>
		</tr>
	</table>
	
	<div id="<%=Config.signupId%>" style="display:none;">
		<%@ include file="signup.jsp" %>
	</div>
	
	<table>
		<tr><td style="text-align:right;">
			<!-- Not a memeber yet -->	
			<%=	"<span id='"+spanId+"'>"+					
				Config.shsgui.createA("clicktosignup('tosignup')",1000000000)+
				Config.shsgui.space(10,Config.horiz) +
				"</span>"+
				Config.shsgui.createInput("submit",submitId,Config.shsdb.text(2222)) %>
			
			<input type="hidden" id="<%=hiddenbufId %>" value=""/>
			<script>
				function clicktosignup(action){
					simpleToggle("signup");	
					if(action == "tosignup"){
						tohtml="<input type='button' title='"+<%=Config.shsdb.text(333)%>+"' value='"+<%=Config.shsdb.text(333)%>+"' onclick='clicktosignup(\"\")'/>";							
						$("#hiddenbuffer").val($("#spanbackId").html());				
						$("#usercreator").val(<%=Config.shsdb.text(11111)%>);
					
					}else{
						tohtml= $("#hiddenbuffer").val();							
						$("#hiddenbuffer").val("");							
						$("#usercreator").val(<%=Config.shsdb.text(2222)%>);
					
					}
					
					$("#spanbackId").html(tohtml);
				}
			</script>
		</td></tr>		
	</table>
</form>