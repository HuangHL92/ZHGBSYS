<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<style>
/* ����ɫ */
	body {
		background-color: #DFE8F6;
	}

</style>
<odin:hidden property="type" value="1"/>
<div id="groupTreeContent" >
<table width="100%">
	<tr>
		<td>
			<odin:groupBox title="ѡ���" property="ggBox">
			<table width="100%">
				<tr>
					<td style="width:50%;" align="center">
						<input type="radio" name="abc"  value="�Զ���" checked="checked"/><span>�Զ���</span>
					</td>
					<td style="width:50%;" align="center">
						<input type="radio" name="abc" value="����" /><span>����</span>
					</td>
				</tr>
			</table>
			</odin:groupBox>
			<table width="100%">
			<tr>
			<td width="50%">
			<odin:editgrid property="templateInfoGrid1"  hasRightMenu="false" title="���ᱨ��" autoFill="false" width="590" height="380" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
			<odin:gridJsonDataModel  >   <!-- id="id" root="data" totalProperty="totalCount" -->
					<%-- <odin:gridDataCol name="personcheck1" /> --%>
					<odin:gridDataCol name="tpid" />
					<odin:gridDataCol name="tptype" />
					<odin:gridDataCol name="tpname" isLast="true" />
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					
					<odin:gridColumn dataIndex="tpid" width="130" header="ģ��id"
						align="center" hidden="true" />
					<odin:gridColumn dataIndex="tptype" width="130" header="ģ������"
						align="center" hidden="true" />
					<odin:gridColumn dataIndex="tpname" width="300" header="ģ����"
						align="center" isLast="true"/> 
				</odin:gridColumnModel>
				<odin:gridJsonData> 
					{
				        data:[]
				    } 
				</odin:gridJsonData>
			</odin:editgrid>
			</td> 
			<td width="50%">
			<odin:editgrid property="templateInfoGrid2"  title="��񱨱�"  hasRightMenu="false" autoFill="false" width="590" height="380" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
			<odin:gridJsonDataModel  >    <!-- id="id" root="data" totalProperty="totalCount" -->
					<%-- <odin:gridDataCol name="personcheck2" /> --%>
					<odin:gridDataCol name="tpid" />
					<odin:gridDataCol name="tptype" />
					<odin:gridDataCol name="tpname" isLast="true" />
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<%-- <odin:gridEditColumn2 header="selectall" width="40"
							editor="checkbox" dataIndex="personcheck2" edited="true"
							hideable="false" gridName="templateInfoGrid2"
							checkBoxClick="getCheckList2()"
							checkBoxSelectAllClick="getCheckList2()" /> --%>
					<odin:gridColumn dataIndex="tpid" width="130" header="ģ��id"
						align="center" hidden="true" />
					<odin:gridColumn dataIndex="tptype" width="130" header="ģ������"
						align="center" hidden="true" />
					<odin:gridColumn dataIndex="tpname" width="300" header="ģ����"
						align="center" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
			</td>
			</tr></table>
		
		</td>
	</tr>
</table>
</div>

<script type="text/javascript" >
$(function(){
	$(":radio").click(function(){
		
		var value = $(this).val();
		if(value=='�Զ���'){
			document.getElementById('type').value = '1';
			
			radow.doEvent('templateInfoGrid1.dogridquery');
			radow.doEvent('templateInfoGrid2.dogridquery');
			
		}else{
			document.getElementById('type').value = '2';

			radow.doEvent('templateInfoGrid1.dogridquery');
			radow.doEvent('templateInfoGrid2.dogridquery');
		}
		 
	});
});


/* function rowDbClik1(){

	radow.doEvent('dbClick1');
}
function rowDbClik2(){

	radow.doEvent('dbClick2');
} */

var personTabsId=[];
function addTab(atitle,aid,src,forced,autoRefresh){
		//alert(aid);
      var tab=parent.parent.tabs.getItem(aid);
      if (!forced)
      	aid = 'R'+(Math.random()*Math.random()*100000000);
      if (tab && !forced){ 
 		parent.tabs.activate(tab);
		if(typeof autoRefresh!='undefined' && autoRefresh){
			document.getElementById('I'+aid).src = src;
		}
      }else{
      	//alert(aid);
    	src = src+'&'+Ext.urlEncode({'a0000':aid});
      	personTabsId.push(aid);
        parent.parent.tabs.add({
        title: (atitle),
        id: aid,
        tabid:aid,
        personid:aid,
        html: '<Iframe width="100%" height="100%" scrolling="auto" id="I'+aid+'" frameborder="0" src="'+src+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
	    listeners:{//�ж�ҳ���Ƿ���ģ�
	    },
	    closable:true
        }).show();  
		
      }
      
    }

</script>
<odin:window src="/blank.htm" id="addOrgWin6" width="1200" height="900"
	title="�Զ�������" modal="true"></odin:window>
<odin:window src="/blank.htm" id="addOrgWin7" width="1200" height="900"
	title="�Զ�����" modal="true"></odin:window>
