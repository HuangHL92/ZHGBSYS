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
									<odin:textEdit property="functiontitle" label="ҵ��ģ������"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</span>
	<odin:gridSelectColJs name="cueauflag" codeType="YESNO"></odin:gridSelectColJs>
	<odin:gridSelectColJs name="auendflag" codeType="YESNO"></odin:gridSelectColJs>
	<odin:gridWithPagingTool property="AuditListGrid" title="�༶���"  isFirstLoadData="false"
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
			<odin:gridColumn  hidden="true" id="yes" header="��־��ˮ��" dataIndex="cueauflag" width="70"/>
			<odin:gridColumn  header="��־��ˮ��" dataIndex="opseno" width="90"/>
			<odin:gridColumn  hidden="true"  header="ͳ��������" dataIndex="aaa027" width="50" />
			<odin:gridColumn  header="ҵ��ģ��" dataIndex="title" width="90"/>
			<odin:gridColumn  header="��˱�ע" dataIndex="cueaudesc" width="100"/>
			
			<odin:gridColumn  header="����Ƿ����" align="center" dataIndex="auendflag" width="100" editor="select" codeType="YESNO"/>
			
			<odin:gridColumn  header="����Ƿ�ͨ��" align="center" dataIndex="cueauflag" width="80" editor="select" codeType="YESNO"/>
			<odin:gridColumn  header="��˼���" align="center" dataIndex="cueaulevel" width="70"/>
			<odin:gridColumn  header="����ܼ���" align="center" dataIndex="auflag" width="70"/>
			<odin:gridColumn  header="������" dataIndex="username" width="90"/>
			<odin:gridColumn  header="��������" dataIndex="aae036" width="110" isLast="true"/>
		</odin:gridColumnModel>
	</odin:gridWithPagingTool>