<%@ page import="SERVICE.Config" %>
<%
	final String
	spanbackId = "spanbackId"
	;
%>


<form>
	<table>
		<tr>
			<td><%=Config.shsdb.text("Username")%></td>
			<td><%=Config.shsgui.createInput("text","username",50)%></td>
		</tr>
		<tr>
			<td><%=Config.shsdb.text("Password")%></td>
			<td><%=Config.shsgui.createInput("password","username",50)%></td>
		</tr>
		<span id='<%=Config.signupId%>' style='dislay:none'>
			<%@ include file="signup.jsp" %>
		</span>
		<tr>
			<td colspan="2" style="text-align:right;">
				<!-- Not a memeber yet -->	
				<%=	"<span id='"+spanbackId+"'>"+//Config.shsgui.createA("blockToggle('"+Config.signupId+"')",1000000000) +						
					Config.shsgui.createA("onIndexAclick('tosignup')",1000000000)+
					Config.shsgui.space(10,Config.horiz) +
					"</span>"+
					Config.shsgui.createInput("submit","usercreator",Config.shsdb.text(2222)) %>
			</td>
			<script>
			function onIndexAclick(action){
				blockToggle("'"+<%=Config.signupId%>+"'");
				if(action ="tosignup"){
					
					$('"#'+<%=spanbackId%>+'"').html('"'+
												<%=Config.shsgui.createInput("button","",Config.shsdb.text(333),"onIndexAclick('')")%>
												+'"');
					$('#usercreator').val("'"+<%=Config.shsdb.text(11111)%>+"'");
				
				}else{
					
					$('"#'+<%=spanbackId%>+'"').html("\'"+<%=
													Config.shsgui.createA("onIndexAclick('tosignup')",1000000000) 
													%>+"\'");
					$('#usercreator').val("'"+<%=Config.shsdb.text(2222)%>+"'");
				
				}
				
			}
			</script>
		</tr>
	</table>
</form>