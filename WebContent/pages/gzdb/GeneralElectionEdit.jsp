<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/comOpenWinInit.jsp" %>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.insigma.siis.local.pagemodel.gzdb.GeneralElectionPageModel"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_004.LogManagePageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<script src="<%=request.getContextPath()%>/basejs/jquery-ui/jquery-1.10.2.js" type="text/javascript"></script>
<script  type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';
</script>
<style>
#zwzcInfo input{
	border: 1px solid #c0d1e3 !important;
	
}
#mxInfo input{
	border: 1px solid #c0d1e3 !important;
	
}
.x-grid3-cell-inner, .x-grid3-hd-inner{
  white-space:normal !important;
}
#zwqc textarea{
	width:200px;
	height:60px
}
#zwqq textarea{
	width:160px;
	height:60px
}
#zwmx textarea{
	width:200px;
	height:40px
}
#zwmz textarea{
	width:200px;
	height:100px
}
#zwmq textarea{
	width:200px;
	height:100px
}
.table-td {
	border: 1px solid #c0d1e3 !important;
}
</style>
<odin:toolBar property="btnToolBar" >
	<odin:fill />
<%-- 	<odin:buttonForToolBar text="����ָ�귽��" icon="images/add.gif" id="addScheme" handler="addScheme"/>
	<odin:buttonForToolBar text="�޸�ָ�귽��" icon="image/d02.png" id="editScheme" handler="addScheme"/> --%>
	<%-- <odin:buttonForToolBar text="ȫ��λ����" icon="image/d02.png" id="searchBtn" handler="flash" /> --%>
<%-- 	<odin:buttonForToolBar text="ְ���������" icon="image/zjyt.png" id="orgBtn" handler="gljg" /> --%>
	<odin:buttonForToolBar text="ɾ��" icon="image/delete2.png" id="deleteBtn" handler="deleteBtn"/>
	<odin:buttonForToolBar text="����" icon="images/add.gif" id="addBtn" handler="addzwzc" isLast="true"/>
</odin:toolBar>
<odin:toolBar property="btnToolBarET" >
	<odin:fill />
	<%-- <odin:buttonForToolBar text="ɾ��" icon="images/delete.gif" id="delBtnET" handler="delet" /> --%>
	<%-- <odin:buttonForToolBar id="sumbitbtn" text="ȷ�����"  icon="images/save.gif" handler="sumbitHandler" /> --%>
	<odin:buttonForToolBar text="ɾ��" icon="image/delete2.png" id="deleteBtn1" handler="deleteBtn1"/>
	<odin:buttonForToolBar text="����" icon="images/add.gif" id="addBtnET" handler="addmx" isLast="true"/>
</odin:toolBar>
<odin:toolBar property="btnToolBarbET" >
	<odin:fill />
	<odin:buttonForToolBar text="ɾ��" icon="image/delete2.png" id="deleteBtnn1" handler="deleteBtnn1"/>
	<odin:buttonForToolBar text="����" icon="images/add.gif" id="addBtnnET" handler="addmm"  isLast="true"/>
</odin:toolBar>
<odin:hidden property="hjxjid"/>
<odin:hidden property="jdid"/>
<odin:hidden property="zbid"/>
<odin:hidden property="wcbz"/>
<odin:hidden property="jd"/>
<odin:hidden property="zbwcbz"/>
<%-- <%
	String publishname="";
	String publishname1="";
	String publishname2="";
	String publishname3="";
	String publishname4="";
	String publishname5="";
	String publishname6="";
	String publishname7="";
	String publishname8="";
	GeneralElectionPageModel mpp=new GeneralElectionPageModel();
	try{
		String hjxjid=request.getParameter("hjxjid");
		String xjqy=request.getParameter("xjqy");
		if(xjqy!=null||!"".equals(xjqy)){
			List<HashMap<String, Object>> list_publish=mpp.queryPublish(xjqy);
			if(list_publish!=null&&list_publish.size()>0) {
				publishname=list_publish.get(0).get("dwzs")==null?"":list_publish.get(0).get("dwzs").toString();
			}
			List<HashMap<String, Object>> list_publish1=mpp.queryPublish1(xjqy);
			if(list_publish1!=null&&list_publish1.size()>0) {
				publishname1=list_publish1.get(0).get("dwzs")==null?"":list_publish1.get(0).get("dwzs").toString();
			}
			List<HashMap<String, Object>> list_publish2=mpp.queryPublish2(xjqy);
			if(list_publish2!=null&&list_publish2.size()>0) {
				publishname2=list_publish2.get(0).get("dwzs")==null?"":list_publish2.get(0).get("dwzs").toString();
			}
			List<HashMap<String, Object>> list_publish3=mpp.queryPublish3(xjqy);
			if(list_publish3!=null&&list_publish3.size()>0) {
				publishname3=list_publish3.get(0).get("dwzs")==null?"":list_publish3.get(0).get("dwzs").toString();
			}
			List<HashMap<String, Object>> list_publish5=mpp.queryPublish5(xjqy);
			if(list_publish5!=null&&list_publish5.size()>0) {
				publishname5=list_publish5.get(0).get("count").toString()==null?"":list_publish5.get(0).get("count").toString();
			}
			List<HashMap<String, Object>> list_publish6=mpp.queryPublish6(xjqy);
			if(list_publish6!=null&&list_publish6.size()>0) {
				publishname6=list_publish6.get(0).get("count")==null?"":list_publish6.get(0).get("count").toString();
			}
			List<HashMap<String, Object>> list_publish7=mpp.queryPublish7(xjqy);
			if(list_publish7!=null&&list_publish7.size()>0) {
				publishname7=list_publish7.get(0).get("count")==null?"":list_publish7.get(0).get("count").toString();
			}
			List<HashMap<String, Object>> list_publish8=mpp.queryPublish8(xjqy);
			if(list_publish8!=null&&list_publish8.size()>0) {
				publishname8=list_publish8.get(0).get("count")==null?"":list_publish8.get(0).get("count").toString();
			}
%> --%>
<table width='100%' style="overflow:hidden">
	<tr>
		<%-- <td >
			<table width='100%' style="overflow-y: auto; overflow-x:hidden;"><tr>
		<td  width="30%" >
			<table width='80%'  height='90%'>
        <tr>
						<!-- <font size="-1">��ίְ��</font> -->
						<td noWrap="nowrap" align=right><span id="ageASpanId" style="FONT-SIZE: 12px">��ίְ��</span>&nbsp;</td>
						<td >
							<table  ><tr>
								<odin:textEdit property="ageA" value="<%=publishname5%>" readonly="true"  width="72" />
								<td><span style="font: 12px">��</span></td>
							</tr></table>
					</td>
		 </tr>
		  <tr>
			<odin:textarea property="dwzs" value="<%=publishname%>" readonly="true"  cols="80" rows="6" maxlength="800" required="false" ></odin:textarea>
			
		  </tr>
		  <tr id="zwqc">
						<!-- <font size="-1">��ίְ��</font> -->
						<td noWrap="nowrap" align=right><span id="" style="FONT-SIZE: 12px">����ְ��</span>&nbsp;</td>
						<td >
							<table  ><tr>
								<odin:textEdit property="ageh" value="<%=publishname6%>" readonly="true" width="72" />
								<td><span style="font: 12px">��</span></td>
							</tr></table>
					</td>
		 </tr>
		  <tr>
		  	<odin:textarea property="zfzs" value="<%=publishname1%>" readonly="true"  cols="80" rows="6" maxlength="800" required="false" ></odin:textarea>
		  </tr>
		  <tr>
						<!-- <font size="-1">��ίְ��</font> -->
						<td noWrap="nowrap" align=right><span id="" style="FONT-SIZE: 12px">�˴�ְ��</span>&nbsp;</td>
						<td >
							<table  ><tr>
								<odin:textEdit property="agef"  value="<%=publishname7%>" readonly="true"   width="72" />
								<td><span style="font: 12px">��</span></td>
							</tr></table>
					</td>
		 </tr>
		  <tr>
		  	<odin:textarea property="rdzs" value="<%=publishname2%>"  readonly="true" cols="80" rows="6" maxlength="800" required="false"></odin:textarea>
		  </tr>
		  <tr>
						<!-- <font size="-1">��ίְ��</font> -->
						<td noWrap="nowrap" align=right><span id="" style="FONT-SIZE: 12px">��Эְ��</span>&nbsp;</td>
						<td >
							<table  ><tr>
								<odin:textEdit property="ageg" value="<%=publishname8%>" readonly="true"  width="72" />
								<td><span style="font: 12px">��</span></td>
							</tr></table>
					</td>
		 </tr>
		  <tr>
		  	<odin:textarea property="zxzs" value="<%=publishname3%>" readonly="true" cols="80" rows="6" maxlength="800" required="false"></odin:textarea>
		  	
		  </tr>
			</table>
        </td> --%>
			<td width="40%" >
				<odin:editgrid2 property="zwzcGrid" hasRightMenu="false" topBarId="btnToolBar" bbarId="pageToolBar" 
				 width="650" height="570" isFirstLoadData="false"  url="/" title="�׶�" >
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="checked"/>
						<odin:gridDataCol name="jdid"/>
						<odin:gridDataCol name="hjxjid"/>
						<odin:gridDataCol name="j0808"/>
						<odin:gridDataCol name="j0809"/>
						<odin:gridDataCol name="jdmc"/>
						<odin:gridDataCol name="jd"/>
						<odin:gridDataCol name="wcbz" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 header="selectall" width="20" gridName="monthGrid" dataIndex="checked" editor="checkbox" edited="true" hidden="false"/>
						<odin:gridEditColumn2 dataIndex="jdid"  header="id" hidden="true" edited="false"  editor="text"/>
						<%-- <odin:gridEditColumn2 dataIndex="jdmc" width="60" header="�׶�����" align="left"  selectData="['0','׼���׶�'],['1','�ɲ�����'],['2','�ɲ�����']"  editor="select"  edited="false" /> --%>
						<odin:gridEditColumn2 dataIndex="jdmc" width="120" codeType="JDMC"  header="�׶�����" align="left"  editor="select"  edited="false" />
						<odin:gridEditColumn2 dataIndex="j0808" width="50"  header="�׶ο�ʼʱ��"  editor="select" align="center"  edited="false"/>
						<odin:gridEditColumn2 dataIndex="j0809" width="50"  header="�׶ν���ʱ��"  editor="select" align="center"  edited="false"/>
						<odin:gridEditColumn2 dataIndex="jd"  header="����ռ��%" width="40"  align="center" edited="false"  editor="text" />
						<odin:gridEditColumn2 dataIndex="wcbz"  width="40" header="��ɱ�־"  editor="select" selectData="['1','��'],['0','��'],['2','�ƽ���']" align="center"  edited="false"/>
					</odin:gridColumnModel>
				</odin:editgrid2>
			</td>
			<td width="40%" >
				<odin:editgrid2 property="zwzcMxGrid" hasRightMenu="false" autoFill="true" bbarId="pageToolBar" pageSize="20" topBarId="btnToolBarET"
				height="570"  isFirstLoadData="false" url="/" title="����" >
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="checked"/>
						<odin:gridDataCol name="zbid" />
						<odin:gridDataCol name="jdbt" />
						<%-- <odin:gridDataCol name="jhsj" /> --%>
						<odin:gridDataCol name="z0808" />
						<odin:gridDataCol name="z0809" />
						<odin:gridDataCol name="jdid" isLast="true"/>
						<%-- <odin:gridDataCol name="zbjd" /> --%>
						<%-- <odin:gridDataCol name="zbwcbz" /> --%>
						<%-- <odin:gridDataCol name="RowRenderer" /> --%>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 header="selectall" width="30" gridName="monthGrid" dataIndex="checked" editor="checkbox" edited="true" hidden="false"/>
						<odin:gridEditColumn2 dataIndex="zbid"  header="id" hidden="true" edited="false"  editor="text"/>
						<odin:gridEditColumn2 dataIndex="jdbt" width="150" header="׼���׶����� " codeType="JDBT" align="center" edited="false"  editor="select"/>
						<%-- <odin:gridEditColumn2 dataIndex="jhsj" width="90" header="�ƻ�ʱ�� "   align="center"  edited="false"  editor="text" /> --%>
						<odin:gridEditColumn2 dataIndex="z0808" width="80" header="�ƻ���ʼʱ�� "   align="center"  edited="false"  editor="text" />
						<odin:gridEditColumn2 dataIndex="z0809" width="80" header="�ƻ�����ʱ�� "   align="center"  edited="false"  editor="text" isLast="true"/>
						<%-- <odin:gridEditColumn2 dataIndex="zbjd"  header="����ռ��%"  width="40"align="center" edited="false"  editor="text" /> --%>
						<%-- <odin:gridEditColumn2 dataIndex="RowRenderer" width="60" header="����"   editor="text" align="center"  edited="false" renderer="RowRenderer"  /> --%>
						<%-- <odin:gridEditColumn2 dataIndex="zbwcbz" width="40" header="��ɱ�־"  align="center" edited="false"  editor="select" selectData="['1','��'],['0','��'],['2','�ƽ���']" isLast="true"/> --%>
					</odin:gridColumnModel>
				</odin:editgrid2>
			</td>
			<td width="20%" >
				<odin:editgrid2 property="zxGrid" hasRightMenu="false" autoFill="true" bbarId="pageToolBar" pageSize="20" topBarId="btnToolBarbET"
				 height="570"  isFirstLoadData="false" url="/" title="����" >
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="checked"/>
						<odin:gridDataCol name="fjid" />
						<%-- <odin:gridDataCol name="jdbt" /> --%>
						<odin:gridDataCol name="zbmc" />
						<odin:gridDataCol name="zbid" />
						<odin:gridDataCol name="filename" />
						<odin:gridDataCol name="fileurl" isLast="true"/>
						<%-- <odin:gridDataCol name="RowRendere" /> --%>
						<%-- <odin:gridDataCol name="RowRenderer" /> --%>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 header="selectall" width="30" gridName="monthGrid" dataIndex="checked" editor="checkbox" edited="true" hidden="false"/>
						<odin:gridEditColumn2 dataIndex="fjid"  header="id" hidden="true" edited="false"  editor="text"/>
						<%-- <odin:gridEditColumn2 dataIndex="jdbt" width="140" header="׼���׶����� " codeType="JDBT" align="center" edited="false"  editor="select"/> --%>
						<odin:gridEditColumn2 dataIndex="zbmc" width="180" header="��������" align="center" edited="false" editor="text"/>
						<odin:gridEditColumn2 dataIndex="filename" width="150" header="�ļ���" align="center" edited="false"  editor="text" renderer="file"/>
						<%-- <odin:gridEditColumn2 dataIndex="RowRendere" width="40" header="�ϴ�"   editor="text" align="center"  edited="false" renderer="RowRendere"/> --%>
						<odin:gridEditColumn2 dataIndex="fileurl"  header="�ļ���ַ"  align="center" edited="false"  editor="text" hidden="true" isLast="true"/>
						<%-- <odin:gridEditColumn2 dataIndex="RowRenderer" width="60" header="����"   editor="text" align="center"  edited="false" renderer="RowRenderer"  /> --%>
					</odin:gridColumnModel>
				</odin:editgrid2>
			</td>
			</tr></table>


<div id="zwzcInfo">
	<div style="margin-left: 20px;margin-top: 10px;font-size:20px">
		<table>
		  <%-- <tr id="zwqc">
			<odin:select2  property="jdmc"   label="�׶�����"  data="['0','׼���׶�'],['1','�ɲ�����'],['2','�ɲ�����']"/>
		  </tr> --%>
		   <tr id="zwqq">
		  	<%-- <odin:textarea property="jdmc"  label="�׶�����" cols="2" rows="1" maxlength="550"></odin:textarea> --%>
		  	<odin:select2 property="jdmc" label="�׶�����" multiSelect="false" 
							codeType="JDMC" width="300"></odin:select2>
			<%-- <tags:ComBoxWithTree property="jdmc" label="�׶�����" codetype="JDMC" readonly="true" ischecked="true" width="160"></tags:ComBoxWithTree> --%>
		  </tr>
		  <tr>
		  	<odin:dateEdit property="j0808" width="300" label="�ƻ���ʼʱ��"
				selectOnFocus="true" format="Y-m-d"
				onkeyup="odin.commInputMask(this,'Y-m-d')" />
			</tr>
			<tr>
		  	<odin:dateEdit property="j0809" width="300" label="�ƻ�����ʱ��"
				selectOnFocus="true" format="Y-m-d"
				onkeyup="odin.commInputMask(this,'Y-m-d')" />
				<!-- <td >
					<div style="width: 120 px; height: 20px;">
						<font>�׶����ʱ��</font>
					</div>
				</td> -->
			<!-- <td class="table-td">
			<div >
				<textarea title="��˫���㿪" id="jdwc" name="jdwc"
				style="border: none; overflow: hidden; width: 200px; height: 20px; 
				readonly="readonly" rows="1" cols="20"
			ondblclick="TimeSelected('j0808','j0809','jdwc')"></textarea>
			</div>
			</td> -->
		  </tr>
		  <tr>
		  	<odin:select2 property="wcbz"  width="300" value="0" label="��ɱ�־"  data="['1','��'],['0','��'],['2','�ƽ���']" ></odin:select2> 
		  </tr>
		  <%-- <tr>
		  		<odin:textEdit property="jd" width="600" value="10" label="����" readonly="true">%</odin:textEdit>
		  </tr> --%>
		</table>
		<odin:hidden property="hjxjid"/>
		<odin:hidden property="jdid"/>
		<div style="margin-left: 100px;margin-top: 15px;">
			<odin:button text="ȷ��" property="saveZWZCInfo" handler="saveZWZCInfo" />
		</div>
	</div>
</div>
<div id="mxInfo">
	<div style="margin-left: 20px;margin-top: 10px;">
		<table>
		  <tr id="zwmx">
			<%-- <odin:textarea property="jdbt"   label="��&nbsp;&nbsp;��"></odin:textarea> --%>
			<odin:select2 property="jdbt" label="׼���׶�����" multiSelect="false" 
							 width="300"></odin:select2>
			<%-- <tags:ComBoxWithTree property="jdbt" label="׼���׶�����"  readonly="true" ischecked="true" width="200"></tags:ComBoxWithTree> --%>
			<%-- <tags:ComBoxWithTree property="jdbt" label="׼���׶�����" codetype="SXLY2" readonly="true" ischecked="true" width="160"></tags:ComBoxWithTree> --%>
		  </tr>
		  <%-- <tr id="zwmz">
			<odin:textarea property="zbmc"  label="����"></odin:textarea>
		  </tr> --%>
		  <tr >
		  	<odin:dateEdit property="z0808" width="300" label="�ƻ���ʼʱ��"
				selectOnFocus="true" format="Y-m-d"
				onkeyup="odin.commInputMask(this,'Y-m-d')" />
		 </tr>
		 <tr >
		  	<odin:dateEdit property="z0809" width="300" label="�ƻ�����ʱ��"
				selectOnFocus="true" format="Y-m-d"
				onkeyup="odin.commInputMask(this,'Y-m-d')" />
				<!-- <td >
					<div style="width: 120 px; height: 20px;">
						<font size="-1">�ƻ�ʱ��</font>
					</div>
				</td> -->
			<!-- <td class="table-td">
			<div >
				<textarea title="��˫���㿪" id="jhsj" name="jhsj"
				style="border: none; overflow: hidden; width: 200px; height: 20px; 
				readonly="readonly" rows="1" cols="20"
			ondblclick="TimeSelected('z0808','z0809','jhsj')"></textarea>
			</div>
			</td> -->
		  </tr>
		  <%-- <tr>
		  		<odin:textEdit property="zbjd" width="300" label="����" >%</odin:textEdit>
		  </tr> --%>
		</table>
		<odin:hidden property="zbid"/>
		<div style="margin-left: 120px;margin-top: 15px;">
			<odin:button text="ȷ��" property="saveMXInfo" handler="saveMXInfo" />
		</div>
	</div>
</div>

<%-- 		<% 		}
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		
	}
%> --%>
<odin:hidden property="zbid"/>
<script type="text/javascript">
<%
String RrmbCodeType = (String)session.getAttribute("RrmbCodeType");
//RrmbCodeType = CodeType2js.getRrmbCodeType();

%>
<%=RrmbCodeType%>
function gllbM(value) {
	var returnV="";
	if(value){
		var v = value.split(",");
		for(i=0;i<v.length;i++){
			if(CodeTypeJson.ZB130[v[i]]){
				returnV += CodeTypeJson.ZB130[v[i]]+","
			}
		}
		returnV = returnV.substring(0,returnV.length-1);
	}
	
	return returnV;
	
}

Ext.onReady(function() {
	$("#hjxjid").val(GetQueryString("hjxjid"));
	$h.initGridSort('zwzcGrid',function(g){
		radow.doEvent('rolesort');
	});	
	$h.initGridSort('zwzcMxGrid',function(g){
		radow.doEvent('rolesort1');
	});
	var pgrid = Ext.getCmp('zwzcGrid');
	var bbar = pgrid.getTopToolbar();
	
	
	openZwzcWin();
	openMxWin();
	hideWin();
	
	//ҳ�����
	 Ext.getCmp('zwzcMxGrid').setHeight((Ext.getBody().getViewSize().height-$h.pos(document.getElementById('forView_ZwzcMxGrid')).top-4)*0.99);
	 Ext.getCmp('zwzcGrid').setHeight(Ext.getCmp('zwzcMxGrid').getHeight());
	 Ext.getCmp('zxGrid').setHeight(Ext.getCmp('zwzcMxGrid').getHeight());
	 
	 
	 Ext.getCmp('zwzcGrid').on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		$('#jdid').val(rc.data.jdid);
		$('#hjxjid').val(rc.data.hjxjid);
		odin.setSelectValue('jdmc',rc.data.jdmc);
		odin.setSelectValue('wcbz',rc.data.wcbz);
		odin.setSelectValue('j0808',rc.data.j0808);
		odin.setSelectValue('j0809',rc.data.j0809);
		//$('#a0165').val(rc.data.a0165); 
		/* odin.setSelectValue('a0192a',rc.data.a0192a);
		odin.setSelectValue('zwzc00',rc.data.zwzc00);
		odin.setSelectValue('a0165',rc.data.a0165);
		odin.setSelectValue('a0221',rc.data.a0221);
		odin.setSelectValue('a0192e',rc.data.a0192e);
		odin.setSelectValue('jzaz',rc.data.jzaz);
		odin.setSelectValue('gfhjc',rc.data.gfhjc);
		odin.setSelectValue('xzzw',rc.data.xzzw); */
/* 		$('#a0221').val(rc.data.a0221);
		$('#a0192e').val(rc.data.a0192e); */
		//$('#zwzc00id').val(rc.data.zwzc00);
		openZwzcWin();
		//radow.doEvent("zwzcMxGrid.dogridquery");
	});
	 Ext.getCmp('zwzcMxGrid').on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		if($('#zbid').val==''){
			$h.alert('','��ѡ��')
			return;
		}
		odin.setSelectValue('zbid',rc.data.zbid);
		//odin.setSelectValue('zbjd',rc.data.zbjd);
		odin.setSelectValue('jdid',rc.data.jdid);
		odin.setSelectValue('z0808',rc.data.z0808);
		odin.setSelectValue('z0809',rc.data.z0809);
		odin.setSelectValue('jdbt',rc.data.jdbt);
		var zbwcbz = rc.data.zbwcbz;
		if(zbwcbz=='1'){
			var win = Ext.getCmp("addmx");	
			if(win){
				win.hide();
				return;
			}
		}else{
			var win = Ext.getCmp("addmx");	
			if(win){
				win.show();
				return;
			}
		}
		openMxWin();
	});
	 
});
function TimeSelected(ID1,ID2,ID3)
{   
	
	$h.openWin('rewardPunish','pages.gzdb.TimeSelected&&'+ID1+'&&'+ID2+'&&'+ID3+'&&','ʱ��ѡ��',300,300,111,/hzb/,null,{maximizable:false,resizable:false});
}
//grid��������ʾ�Ĳ������
function RowRenderer(value, params, record,rowIndex,colIndex,ds){
	var zbid=record.get("zbid");
	return "<a href=\"javascript:sumbitHandlerl('"+zbid+"')\">ȷ�����</a>";
}
//grid��������ʾ�Ĳ������
function RowRendere(value, params, record,rowIndex,colIndex,ds){
	var zbid=record.get("zbid");
	return "<a href=\"javascript:sq('"+zbid+"')\">�ϴ�</a>";
}
function sumbitHandler() {
	//window.close();
	//var parentWin = window.opener;
	radow.doEvent("sumbitRegister");
}
function sumbitHandlerl() {
	//window.close();
	//var parentWin = window.opener;
	radow.doEvent("sumbitRegisterl");
}
//�ύ֮���¼�������״̬
function afterSubmit(wcbz) {
	$("#btnToolBarET").hide();// ����class����
	$("#btnToolBarbET").hide();// ����class����
}
//�ύ֮���¼�������״̬
function Submit() {
	$("#btnToolBarET").show();// ����class����
	$("#btnToolBarbET").show();// ����class����
}
//ɾ��
function deleteRow(zbid) {
	// ȷ��ɾ��
	$h.confirm("ϵͳ��ʾ��",'�Ƿ�ȷ��ɾ����',200,function(id) { 
		if("ok"==id){
			radow.doEvent("deleteFile", zbid);
		}else{
			return false;
		}		
	});
}

//ɾ��
function deleteBtn() {
	var a=document.getElementById("jdid").value;
	if(a==null || a==''){
		$h.alert('','��ѡ��Ҫɾ���Ľ׶����ƣ�')
		return;
	}
	// ȷ��ɾ��
	$h.confirm("ϵͳ��ʾ��",'�Ƿ�ȷ��ɾ����',200,function(id) { 
		if("ok"==id){
			radow.doEvent("deleteBtn");
		}else{
			return false;
		}		
	});
}
//ɾ��
function deleteBtn1() {
	var a=document.getElementById("zbid").value;
	if(a==null || a==''){
		$h.alert('','��ѡ��Ҫɾ����׼���׶����ƣ�')
		return;
	}
	// ȷ��ɾ��
	$h.confirm("ϵͳ��ʾ��",'�Ƿ�ȷ��ɾ����',200,function(id) { 
		if("ok"==id){
			radow.doEvent("deleteBtn1");
		}else{
			return false;
		}		
	});
}
//ɾ��
function deleteBtnn1() {
	var a=document.getElementById("zbid").value;
	if(a==null || a==''){
		$h.alert('','��ѡ��Ҫɾ���Ĳ��ϣ�')
		return;
	}
	// ȷ��ɾ��
	$h.confirm("ϵͳ��ʾ��",'�Ƿ�ȷ��ɾ����',200,function(id) { 
		if("ok"==id){
			radow.doEvent("deleteBtnn1");
		}else{
			return false;
		}		
	});
}
function gg() {
	//window.close();
	//var parentWin = window.opener;
   radow.doEvent("zwzcMxGrid.dogridquery");
}
function gg2() {
	//window.close();
	//var parentWin = window.opener;
   radow.doEvent("zxGrid.dogridquery");
}
function gg1() {
	//window.close();
	//var parentWin = window.opener;
   radow.doEvent("zwzcGrid.dogridquery");
   radow.doEvent("zwzcMxGrid.dogridquery");
}
function gg3() {
	//window.close();
	//var parentWin = window.opener;
	//parentWin.radow.doEvent("memberGrid.dogridquery");
	realParent.Ext.getCmp('memberGrid').getStore().reload();
}
function sq(zbid){ 
	//radow.doEvent('sq',meeting_id);	
	$h.openPageModeWin('publishWin','pages.meeting.HjxjPlanning','����ѡ����Ϣ',760, 500,zbid,g_contextpath);
	
}
//����ԭ��
function file(value, params, rs, rowIndex, colIndex, ds){
	
	var fileurl = rs.get('fileurl');
	var name = rs.get('filename');
	var url=fileurl.replace(/\\/g,"/");
	 if(name != null && name != ''){
		return "<a href=\"javascript:downloads('" +url +"')\">"+name+"</a>";
	} 
	

} 
function downloads(url){
	var downfile = url;
	//w = window.open("ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile)));
	window.location.href="ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile));
}

function flash() {
	document.getElementById("checkedgroupid").value = '';
	radow.doEvent('zwzcGrid.dogridquery');
	
}


function addzwzc(){
	odin.setSelectValue('jdmc','');
	//odin.setSelectValue('jdwc','');
	odin.setSelectValue('wcbz','');
	odin.setSelectValue('j0808','');
	odin.setSelectValue('j0809','');
	odin.setSelectValue('jdid','');
	/* $('#a0192a').val('');
	$('#zwzc00').val('');
	$('#a0165').val('');
	$('#a0221').val('');
	$('#a0192e').val(''); */
	openZwzcWin();
	
}

function delmx(){
	var a=document.getElementById("jdid").value;
	if(a==null || a==''){
		$h.alert('','��ѡ��������ϸ��')
		return;
	}
	$h.confirm("ϵͳ��ʾ��","�Ƿ�ȷ��ɾ����",400,function(id) { 
		if("ok"==id){
			radow.doEvent("delMxInfo");
		}else{
			return false;
		}		
	});
}
function deldx(){
	var a=document.getElementById("zbid").value;
	if(a==null || a==''){
		$h.alert('','��ѡ��׶���ϸ��')
		return;
	}
	$h.confirm("ϵͳ��ʾ��","�Ƿ�ȷ��ɾ����",400,function(id) { 
		if("ok"==id){
			radow.doEvent("delMmInfo");
		}else{
			return false;
		}		
	});
}
function del1(){
	var a=document.getElementById("zbid").value;
	if(a==null || a==''){
		$h.alert('','��ѡ��׶���ϸ��')
		return;
	}
	$h.confirm("ϵͳ��ʾ��","�Ƿ�ȷ��ɾ����",400,function(id) { 
		if("ok"==id){
			radow.doEvent("delMmInfo");
		}else{
			return false;
		}		
	});
}


function addmx(){
	if(document.getElementById("jdid").value =='' || document.getElementById("jdid").value ==null){
		$h.alert('ϵͳ��ʾ','����ѡ��Ҫ�����Ľ׶Σ�')
		return;
	}
	var g1=document.getElementById("jdmc").value
	/* var jdid = document.getElementById("jdid").value;
	if(jdid==""){
		 $h.alert("ϵͳ��ʾ","����ѡ��Ҫ�����Ľ׶�")
		 return;
	} */
	odin.setSelectValue('jdbt','');
	odin.setSelectValue('z0808','');
	odin.setSelectValue('z0809','');
	//odin.setSelectValue('zbjd','');
	odin.setSelectValue('zbid','');
	radow.doEvent("addf",g1);
	
	
}
function gg6(){
	openMxWin();
}
function addmm(){
	var zbid=document.getElementById("zbid").value;
	if(document.getElementById("zbid").value =='' || document.getElementById("zbid").value ==null){
		$h.alert('ϵͳ��ʾ','����ѡ��Ҫ�����Ĳ��ϣ�')
		return;
	}
	radow.doEvent("pd",zbid);
}
function openZwzcWin(){
	var win = Ext.getCmp("addzwzc");	
	if(win){
		win.show();	
		return;
	}
	win = new Ext.Window({
		title : '�׶�',
		layout : 'fit',
		width : 450,
		height : 281,
		closeAction : 'hide',
		closable : true,
		modal : true,
		id : 'addzwzc',
		collapsed:false,
		collapsible:false,
		bodyStyle : 'background-color:#FFFFFF',
		plain : true,
		titleCollapse:false,
		contentEl:"zwzcInfo",
		listeners:{}
		           
	});
	win.show();
}
function openMxWin(){
		var win = Ext.getCmp("addmx");	
		if(win){
			win.show();	
			return;
		}
	win = new Ext.Window({
		title : '����',
		layout : 'fit',
		width : 450,
		height : 321,
		closeAction : 'hide',
		closable : true,
		modal : true,
		id : 'addmx',
		collapsed:false,
		collapsible:false,
		bodyStyle : 'background-color:#FFFFFF',
		plain : true,
		titleCollapse:false,
		contentEl:"mxInfo",
		listeners:{}
		           
	});
	win.show();
}

function saveZWZCInfo(){
	var j0808 = document.getElementById("j0808").value;
	var j0809 = document.getElementById("j0809").value;
	if(j0808==""){
		 $h.alert("ϵͳ��ʾ","����ѡ��ͳ�ƿ�ʼ�·�")
		 return;
	}
	if(j0809==""){
		 $h.alert("ϵͳ��ʾ","����ѡ��ͳ�ƽ�ֹ�·�")
		 return;
	}
	if(parseInt(j0809.replace(/\-/g,""))<parseInt(j0808.replace(/\-/g,""))){
		odin.alert("��ֹʱ�䲻��С�ڿ�ʼʱ�䣡");
		return;
	} 
	radow.doEvent("addZWZCInfo");
	Ext.getCmp("addzwzc").hide();
}
function saveMXInfo(){
	var z0808 = document.getElementById("z0808").value;
	var z0809 = document.getElementById("z0809").value;
	if(z0808==""){
		 $h.alert("ϵͳ��ʾ","����ѡ��ͳ�ƿ�ʼ�·�")
		 return;
	}
	if(z0809==""){
		 $h.alert("ϵͳ��ʾ","����ѡ��ͳ�ƽ�ֹ�·�")
		 return;
	}
	if(parseInt(z0809.replace(/\-/g,""))<parseInt(z0808.replace(/\-/g,""))){
		odin.alert("��ֹʱ�䲻��С�ڿ�ʼʱ�䣡");
		return;
	} 
	radow.doEvent("addMxInfo");
	Ext.getCmp("addmx").hide();
}



function showPerct(value, params, record, rowIndex, colIndex, ds) {
	
	return value+"%";
	
	
}

function hideWin(){
	var win = Ext.getCmp("addmx");	
	if(win){
		win.hide();	
	}
	var win = Ext.getCmp("addzwzc");	
	if(win){
		win.hide();	
	}
}

var tree;
var ctxPath = '<%= request.getContextPath() %>';
Ext.onReady(function () {
    var Tree = Ext.tree;
    tree = new Tree.TreePanel({
        id: 'group',
        el: 'tree-div',//Ŀ��div����
        split: false,
        width: 270,
        height: 600,
        minSize: 164,
        maxSize: 164,
        rootVisible: false,//�Ƿ���ʾ���ϼ��ڵ�
        autoScroll: true,
        animate: true,
        border: false,
        enableDD: false,
        containerScroll: true,
        loader: new Tree.TreeLoader({
            dataUrl: 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataPeople&unsort=1'
        })
    });
    tree.on('click', treeClick);

    var root = new Tree.AsyncTreeNode({
        id: "-1"
    });
    tree.setRootNode(root);
    tree.render();
    root.expand(false, true, callback);

    var viewSize = Ext.getBody().getViewSize();
    var editgrid = Ext.getCmp('editgrid');

    var resizeobj = Ext.getCmp('tab');
    resizeobj.setHeight(viewSize.height - 19);//34 - 29
    var tableTab1 = document.getElementById("tableTab1");
    tableTab1.style.height = viewSize.height - 49 + "px";//87 82
    editgrid.setHeight(viewSize.height - 50);
    editgrid.setWidth(viewSize.width - 270);
});

function treeClick(node, e) {
    document.getElementById("checkedgroupid").value = node.id;
    radow.doEvent('zwzcGrid.dogridquery');
}



</script>


