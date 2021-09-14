<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>高级搜索</title>
<odin:head/>
</head>
<script type="text/javascript">
	function complexQuery(){
		var querydates=document.all.querydates.value;
		var querydatee=document.all.querydatee.value;
		var digest=document.all.digest.value;
		var where="";
		if(querydates!=""){
			where=where+" and a.aae036>=to_date('"+querydates+"','yyyy-mm-dd')";
		}
		if(querydatee!=""){
			where=where+" and a.aae036<to_date('"+querydatee+"','yyyy-mm-dd')+1";
		}
		if(digest!=""){
			where=where+" and a.digest like '%"+digest+"%'";
		}
		if(!document.all.rbflag.checked){
			where=where+" and a.eae024='0'";
		}
		//alert(where);
		//alert(parent.iframe_complex_win.document.documentElement.innerHTML);
		parent.win_complex_win.hide();
		parent.queryLog(where);	
	}
	
	function doClose(){
		parent.win_complex_win.hide();
	}
</script>
<body>
<odin:base>
		<table >
			<tr>
				<td>
					<table width="410">
					<tr>
						<odin:dateEdit property="querydates" label="查询日期"/>
						<odin:dateEdit property="querydatee" label="&nbsp;-" />
					</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table>
					<tr>
						<td>
							<table width="230" align="left">
							<tr>
								<odin:textEdit property="digest" label="&nbsp;&nbsp;&nbsp;&nbsp;摘要" width="343"/>
							</tr>
							</table>
						</td>
					</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td><odin:checkbox property="rbflag" label="包括已回退的日志"/></td>
			</tr>
			<tr><td height="80"></td></tr>
			<tr>
				<td align="center">
					<table>
					<tr>
						<td><odin:button property="complexquery" text="&nbsp;搜索&nbsp;" handler ="complexQuery" /></td>
						<td width="10"></td>
						<td><odin:button property="complexquery" text="&nbsp;关闭&nbsp;" handler ="doClose" /></td>
					</tr>
					</table>
				</td>
			</tr>
		</table>
</odin:base>
</body>
</html>