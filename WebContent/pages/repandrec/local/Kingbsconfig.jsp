<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<odin:toolBar property="btnToolBar">
	<odin:fill />
	<odin:buttonForToolBar id="save" text="初始化" icon="image/u53.png" isLast="true" cls="x-btn-text-icon" />
</odin:toolBar>
<odin:panel contentEl="ftpUpContent" property="ftpUpManagePanel" topBarId="btnToolBar" />
<div id="ftpUpContent">
<odin:hidden property="dataexechangeconfid" />

</div>

		<odin:groupBox property="s10" title="信息配置">
			<table cellspacing="2" width="98%" align="center">
				<%-- <tr>
					<odin:textEdit property="zzbthreepath" label="ZZB3文件存储路径" colspan="4" size="78" maxlength="300" required="true"></odin:textEdit>
				</tr> --%>
				<tr>
					<odin:textEdit property="kingrestorepath" label="数据恢复程序路径" colspan="4" size="78" maxlength="300" required="true" ></odin:textEdit>
				</tr>
				<tr>
					<td height="20px"></td>
				</tr>
				<%-- <tr>
					<odin:textEdit property="bzbpath" label="标准版程序安装路径" colspan="4" size="78" maxlength="300" required="true" ></odin:textEdit>
				</tr> --%>
				
				<tr>
					<td colspan="4">
						<label id="bz1" style="font-size: 12;color: red">注：服务器需要安装标准版数据库，并保证数据库服务开启。</label><br>
						<label id="bz1" style="font-size: 12;color: red">注：路径以反斜杠“/”作为分隔符（如：c：/）。</label><br>
						<!-- <label id="bz2" style="font-size: 12;color: red">注：ZZB3文件存储路径：路径名只能采用数字、小写字母的组合（如：D:/temp01/data）。</label><br> -->
						<label id="bz3" style="font-size: 12;color: red">注：数据恢复程序路径：采用标准版安装金仓数据库路径，如将标准版软件安装到 D盘，则 D盘 下出现Components Data文件夹，该文件夹为标准版软件安装路径，配置此路径（如：D:/Components Data）。</label>
						<%-- 
						<label id="bz4" style="font-size: 12;color: red">注：标准版程序安装路径举例，如：D:/全国公务员管理信息系统（标准版）单机版。</label><br>
						--%>
					</td>
				</tr>
			</table>
		</odin:groupBox>
<script type="text/javascript">


</script>