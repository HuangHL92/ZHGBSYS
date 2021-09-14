<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
	
		<span style="position:absolute;top:26;left:250;z-index:1000">
			<table>
				<tr>
					<td>
						<table width="160">
							<tr>
								<td>
									<odin:dateEdit property="querydate" label=""/>
								</td>
								<td>
									<odin:textEdit property="functiontitle" label="业务模块名称"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</span>
	<odin:gridSelectColJs name="cueauflag" codeType="YESNO"></odin:gridSelectColJs>
	<odin:gridSelectColJs name="auendflag" codeType="YESNO"></odin:gridSelectColJs>
	<odin:gridWithPagingTool property="AuditListGrid" title="多级审核"  isFirstLoadData="false"
							 autoFill="false" forceNoScroll="false" width="788" height="490" 
							 topToolBar="true" counting="true" grouping="true" groupCol="title">
		<odin:gridJsonDataModel id="auopseno" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="opseno" />
			<odin:gridDataCol name="aaa027" />
			<odin:gridDataCol name="title" />
			<odin:gridDataCol name="auflag" />
			
			<odin:gridDataCol name="auendflag" />
			
			<odin:gridDataCol name="cueauflag" />
			<odin:gridDataCol name="cueaulevel" />
			<odin:gridDataCol name="cueaudesc" />
			<odin:gridDataCol name="username" />
			<odin:gridDataCol name="aae036" isLast="true"/>
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridColumn  hidden="true" id="yes" header="日志流水号" dataIndex="cueauflag" width="70"/>
			<odin:gridColumn  header="日志流水号" dataIndex="opseno" width="90"/>
			<odin:gridColumn  hidden="true"  header="统筹区编码" dataIndex="aaa027" width="50" />
			<odin:gridColumn  header="业务模块" dataIndex="title" width="90"/>
			<odin:gridColumn  header="审核备注" dataIndex="cueaudesc" width="100"/>
			
			<odin:gridColumn  header="审核是否结束" align="center" dataIndex="auendflag" width="100" editor="select" codeType="YESNO"/>
			
			<odin:gridColumn  header="审核是否通过" align="center" dataIndex="cueauflag" width="80" editor="select" codeType="YESNO"/>
			<odin:gridColumn  header="审核级数" align="center" dataIndex="cueaulevel" width="70"/>
			<odin:gridColumn  header="审核总级数" align="center" dataIndex="auflag" width="70"/>
			<odin:gridColumn  header="经办人" dataIndex="username" width="90"/>
			<odin:gridColumn  header="经办日期" dataIndex="aae036" width="110" isLast="true"/>
		</odin:gridColumnModel>
	</odin:gridWithPagingTool>