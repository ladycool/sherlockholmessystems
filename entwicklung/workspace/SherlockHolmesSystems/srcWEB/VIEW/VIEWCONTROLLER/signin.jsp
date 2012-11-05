<%@ page import="SERVICE.Config" %>
<%
	final String
	spanId = "spanbackId",
	submitId = "usercreator",
	hiddenbufId = "hiddenbuffer",
	hiddenjudgeId= ""
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
				<%=	"<span id='"+spanId+"'>"+//Config.shsgui.createA("blockToggle('"+Config.signupId+"')",1000000000) +						
					Config.shsgui.createA("clicktosignup('tosignup')",1000000000)+
					Config.shsgui.space(10,Config.horiz) +
					"</span>"+
					Config.shsgui.createInput("submit",submitId,Config.shsdb.text(2222)) %>
				
				<input type="hidden" id="<%=hiddenbufId %>" value=""/>
				<script>
					function clicktosignup(action){
						blockToggle("'"+<%=Config.signupId%>+"'");
						if(action ="tosignup"){
							tohtml="<input type='button' value='"+<%=Config.shsdb.text(333)%>+"' onclick='clicktosignup(\'\')'/>";
							
							$("'#"+<%=hiddenbufId %>+"'").val($('"#'+<%=spanId%>+'"').html());
							
							$('"#'+<%=submitId%>+'"').val("'"+<%=Config.shsdb.text(11111)%>+"'");
						
						}else{
							tohtml= $("'#"+<%=hiddenbufId %>+"'").val();
							
							$("'#"+<%=hiddenbufId %>+"'").val('');
							
							$('"#'+<%=submitId%>+'"').val("'"+<%=Config.shsdb.text(2222)%>+"'");
						
						}
						$('"#'+<%=spanId%>+'"').html(tohtml);
						
					}
				</script>
			</td>
					
		</tr>		
	</table>
</form>