<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<%@include file="/comOpenWinInit2.jsp" %>
<script  type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';
</script>
<odin:hidden property="checkregid"/>
<odin:hidden property="crp000"/>
<%-- <odin:toolBar property="tbar1" applyTo="div_tbar">
	<odin:fill></odin:fill>
	<odin:separator/>
	<odin:buttonForToolBar text="����" id="editSavePerson" icon="images/save.gif" />
	<odin:separator isLast="true"/>
</odin:toolBar>
<div id="div_tbar"></div> --%>
<div>
<table style="height: 100%;">
  <tr>
    <td style="height: 100%;" valign="top">
    	<div style="height: 100%;">
    	<odin:editgrid2 property="memberGrid"  load="selectRow" hasRightMenu="false" title="��Ա��Ϣ" 
			autoFill="true"  bbarId="pageToolBar" pageSize="200"
			url="/"  >
			<odin:gridJsonDataModel>
				<odin:gridDataCol name="pcheck" />
				<odin:gridDataCol name="a0000" />
				<odin:gridDataCol name="crp000" />
				<odin:gridDataCol name="a3600" />
				<odin:gridDataCol name="crp001" />
				<odin:gridDataCol name="crp002" />
				<odin:gridDataCol name="crp003" />
				<odin:gridDataCol name="crp004" />
				<odin:gridDataCol name="crp005" />
				<odin:gridDataCol name="crp006" />
				<odin:gridDataCol name="crp007" />
				<odin:gridDataCol name="crp008" />
				<odin:gridDataCol name="crp014" />
				<odin:gridDataCol name="sortid1" />
				
				<odin:gridDataCol name="checkregid" isLast="true"/>
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn></odin:gridRowNumColumn>
				<odin:gridEditColumn2 dataIndex="sortid1" header="���" menuDisabled="true" edited="false" editor="text" hidden="true"/> 
				<%-- <odin:gridEditColumn2 dataIndex="crp002" header="��ν" width="70" sortable="false" menuDisabled="true" edited="false" editor="select" selectData="['��ż','��ż'],['��Ů','��Ů']" align="center" /> --%>
				
				<odin:gridEditColumn2 dataIndex="pcheck" header="selectall" width="70" sortable="false" menuDisabled="true" gridName="memberGrid" edited="true" editor="checkbox" align="center" />
				
				<odin:gridEditColumn2 dataIndex="crp001" header="����" width="70" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
				<odin:gridEditColumn2 dataIndex="crp006" header="���֤" width="170" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
				<odin:gridEditColumn2 dataIndex="crp005" header="������ò" width="70" sortable="false" menuDisabled="true" edited="false" editor="select" codeType="GB4762" align="center" />
				<odin:gridEditColumn2 dataIndex="crp004" header="ְ��" width="180" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
				<odin:gridEditColumn2 dataIndex="crp014" header="�������" width="180" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
				
				<odin:gridEditColumn2 dataIndex="crp007" header="" menuDisabled="true" edited="false" editor="text" hidden="true"/>
				<odin:gridEditColumn2 dataIndex="crp008" header="" menuDisabled="true" edited="false" editor="text" hidden="true"/>
				<odin:gridEditColumn2 dataIndex="a0000" header="��Աid" menuDisabled="true" edited="false" editor="text" hidden="true"/>
				<odin:gridEditColumn2 dataIndex="crp000" header="id" menuDisabled="true" edited="false" editor="text" hidden="true"/>
				<odin:gridEditColumn2 dataIndex="a3600" header="��ͥ��Աid"  menuDisabled="true" edited="false" editor="text" hidden="true" isLast="true" />
				
			</odin:gridColumnModel>
			<odin:gridJsonData>
				{
			        data:[]
			    }
			</odin:gridJsonData>
		</odin:editgrid2>
		</div>
    </td>
    <td valign="top">
    	<odin:groupBox title="���÷������">
			<div style="width: 300px;align-content: center;text-align: center;">
				<table style="width: 100%;">
					<%-- <tr>
						<odin:textEdit property="crp012" label="��Ա���" maxlength="25"></odin:textEdit>
					</tr>
					<tr>
						<odin:textEdit property="crp011" label="ְ��" maxlength="25"></odin:textEdit>
					</tr>
					<tr>
						<odin:dateEdit property="crp018" label="�������" format="Ymd" maxlength="8"></odin:dateEdit>
					</tr> --%>
					<tr>
						<odin:select2 property="crp016" label="������" canOutSelectList="true" data="['1','����λ��һ��ʹ��'],['2','����ֵ���ְ����'],['3','��������������ɲ�'],['4','����']"></odin:select2>
					</tr>
					<tr>
						<odin:textarea property="crp015" label="��ϸ˵��" rows="10" cols="26" ></odin:textarea>
					</tr>
					<tr>
						<odin:textEdit property="crp013" label="�������" required="true"></odin:textEdit>
					</tr>
					<tr>
						<odin:select2 property="crp014" label="�϶����" required="true" canOutSelectList="true" data="['1','��ʵ�ϱ�'],['2','����Ϊͨ��'],['3','��˵���϶�����Ϊͨ��'],['4','����©��������أ��ݻ�����'],['5','����©��������أ�����'],['6','����']"></odin:select2>
					
						<%-- <odin:textarea property="crp014" label="�϶����" required="true" rows="3" cols="26" ></odin:textarea> --%>
					</tr>
					<tr>
						<odin:textarea property="crp017" label="��ע" rows="5" cols="26" ></odin:textarea>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<odin:button text="&nbsp;��&nbsp;��&nbsp;" handler="saveBtn"></odin:button>
						</td>
					</tr>
				</table>
			</div>
			</odin:groupBox>
    </td>
  </tr>
</table>
</div>
<odin:hidden property="crp011"/>
<odin:hidden property="crp012"/>
<odin:hidden property="crp018"/>


<script type="text/javascript">


function saveBtn(){
	var grid=Ext.getCmp("memberGrid");
	var store = grid.getStore();
	var rowCount = store .getCount();
	var count = 0;
	for(var i=0;i<rowCount;i++) {
		var record=store.getAt(i);
		if(record.data.pcheck == true){
			count ++ ;
		}
	}
	if(count==0){
		radow.doEvent('save2Btn');
	} else {
		$h.confirm("ϵͳ��ʾ��",'��ѡ�˶�����¼���Ƿ���£�ѡ��ȷ�����������ݣ�ѡ��ȡ�������������ݡ�',400,function(id) { 
			if("ok"==id){
				radow.doEvent('saveBtn');
			}else{
				return false;
			}		
		});
	}
	
}
function selectRow(a,store){
	var peopleInfoGrid =Ext.getCmp('memberGrid');
	var len = peopleInfoGrid.getStore().data.length;
	if( len > 0 ){//Ĭ��ѡ���һ�����ݡ�
		var flag = true;
		for(var i=0;i<len;i++){
			var rc = peopleInfoGrid.getStore().getAt(i);
			if(rc.data.crp000==$('#crp000').val()){
				peopleInfoGrid.getSelectionModel().selectRow(i,true);
				peopleInfoGrid.fireEvent('rowclick',peopleInfoGrid,i,this);
				flag= false;
				setTimeout(function(){peopleInfoGrid.getView().scroller.dom.scrollTop = (i-12)*27;},100);
				return;
			}
		}
		peopleInfoGrid.getSelectionModel().selectRow(0,true);
		peopleInfoGrid.fireEvent('rowclick',peopleInfoGrid,0,this);
	} else {
		radow.doEvent('rowPclick','');
	}
}

Ext.onReady(function() {
	var height=document.body.clientHeight;
	var width=document.body.clientWidth;
	var memberGrid = Ext.getCmp('memberGrid');
	memberGrid.setHeight(height+45);
	memberGrid.setWidth(width-300);
	document.getElementById('checkregid').value = parentParam.checkregid;
	
	memberGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		radow.doEvent('rowPclick',rc.data.crp000);
	});
});
function infoSearch(){
	radow.doEvent('memberGrid.dogridquery');
}
</script>


