<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<style>
/* 背景色 */
	body {
		background-color: #DFE8F6;
	}

</style>
<odin:hidden property="type" value="1"/>
<div id="groupTreeContent" >
<table width="100%">
	<tr>
		<td>
			<odin:groupBox title="选择框" property="ggBox">
			<table width="100%">
				<tr>
					<td style="width:50%;" align="center">
						<input type="radio" name="abc"  value="自定义" checked="checked"/><span>自定义</span>
					</td>
					<td style="width:50%;" align="center">
						<input type="radio" name="abc" value="共享" /><span>共享</span>
					</td>
				</tr>
			</table>
			</odin:groupBox>
			<table width="100%">
			<tr>
			<td width="50%">
			<odin:editgrid property="templateInfoGrid1"  hasRightMenu="false" title="名册报表" autoFill="false" width="590" height="380" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
			<odin:gridJsonDataModel  >   <!-- id="id" root="data" totalProperty="totalCount" -->
					<%-- <odin:gridDataCol name="personcheck1" /> --%>
					<odin:gridDataCol name="tpid" />
					<odin:gridDataCol name="tptype" />
					<odin:gridDataCol name="tpname" isLast="true" />
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					
					<odin:gridColumn dataIndex="tpid" width="130" header="模板id"
						align="center" hidden="true" />
					<odin:gridColumn dataIndex="tptype" width="130" header="模板类型"
						align="center" hidden="true" />
					<odin:gridColumn dataIndex="tpname" width="300" header="模板名"
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
			<odin:editgrid property="templateInfoGrid2"  title="表格报表"  hasRightMenu="false" autoFill="false" width="590" height="380" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
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
					<odin:gridColumn dataIndex="tpid" width="130" header="模板id"
						align="center" hidden="true" />
					<odin:gridColumn dataIndex="tptype" width="130" header="模板类型"
						align="center" hidden="true" />
					<odin:gridColumn dataIndex="tpname" width="300" header="模板名"
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
		if(value=='自定义'){
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
	    listeners:{//判断页面是否更改，
	    },
	    closable:true
        }).show();  
		
      }
      
    }

</script>
<odin:window src="/blank.htm" id="addOrgWin6" width="1200" height="900"
	title="自定义名册" modal="true"></odin:window>
<odin:window src="/blank.htm" id="addOrgWin7" width="1200" height="900"
	title="自定义表格" modal="true"></odin:window>
