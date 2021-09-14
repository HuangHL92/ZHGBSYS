<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<div id="addMeetingContent">	
<table style="width: 100%;height: 96%;">
	<tr>
		<td width="350" >
			<table style="width: 100%;height: 100%;">
				<tr><td>
					<odin:groupBox title="重点岗位列表" >
						<table style="width: 100%;height: 100%;">
							<tr>
								<odin:select2 property="zdgw_type"  label="重点岗位类别" codeType="ZDGWTYPE"  width="250" onchange="updateZdgw"></odin:select2>
							</tr>
							<tr>
								<td colspan="2">
									<odin:editgrid2 property="zdgwGrid" hasRightMenu="false" title="" forceNoScroll="true" height="350" autoFill="true"  pageSize="500" url="/">
										<odin:gridJsonDataModel>
											<%-- <odin:gridDataCol name="checked"></odin:gridDataCol> --%>
											<odin:gridDataCol name="zdgwname" />
											<odin:gridDataCol name="dwtype"/>
											<odin:gridDataCol name="zdgwtype"/>
											<odin:gridDataCol name="userid"/>
											<odin:gridDataCol name="zdgwid"/>
										</odin:gridJsonDataModel>
										<odin:gridColumnModel>
											<odin:gridRowNumColumn></odin:gridRowNumColumn>
											<odin:gridEditColumn2 dataIndex="zdgwname" width="140" header="岗位名" editor="text" edited="false" align="center" />
											<odin:gridEditColumn2 dataIndex="dwtype" width="50" header="单位类型" editor="text" edited="false" align="center"  hidden="true"  />
											<odin:gridEditColumn2 dataIndex="zdgwtype" width="50" header="成员类别" editor="text" edited="false" align="center" />
											<odin:gridEditColumn2 dataIndex="delzdgw" width="50" header="操作" editor="text" edited="false"  align="center" renderer="delzdgw" />
											<odin:gridEditColumn2 dataIndex="userid" width="50" header="创建人" editor="text" edited="false" align="center" hidden="true"  />
											<odin:gridEditColumn2 dataIndex="zdgwid" width="30" header="主键" editor="text" edited="false" align="center" hidden="true" isLast="true" />
										</odin:gridColumnModel>
									</odin:editgrid2> 
								</td>
							</tr>
						</table> 
					</odin:groupBox>
				</td></tr>
				<tr><td>
					<odin:groupBox title="方案列表" >
						<table style="width: 100%;height: 100%;">
							<tr>
								<td colspan="2">
									<odin:editgrid2 property="waysGrid" hasRightMenu="false" title="" forceNoScroll="true" height="200" autoFill="true"  pageSize="500" url="/">
										<odin:gridJsonDataModel>
											<%-- <odin:gridDataCol name="checked"></odin:gridDataCol> --%>
											<odin:gridDataCol name="wayname" />
											<odin:gridDataCol name="dwtype"/>
											<odin:gridDataCol name="userid"/>
											<odin:gridDataCol name="wayid"/>
										</odin:gridJsonDataModel>
										<odin:gridColumnModel>
											<odin:gridRowNumColumn></odin:gridRowNumColumn>
											<odin:gridEditColumn2 dataIndex="wayname" width="140" header="方案名" editor="text" edited="false" align="center" />
											<odin:gridEditColumn2 dataIndex="dwtype" width="80" header="单位类型" editor="text" edited="false" align="center" hidden="true"  />
											<odin:gridEditColumn2 dataIndex="delway" width="60" header="操作" editor="text" edited="false"  align="center" renderer="delway" />
											<odin:gridEditColumn2 dataIndex="userid" width="50" header="创建人" editor="text" edited="false" align="center" hidden="true"  />
											<odin:gridEditColumn2 dataIndex="wayid" width="30" header="主键" editor="text" edited="false" align="center" hidden="true" isLast="true" />
										</odin:gridColumnModel>
									</odin:editgrid2> 
								</td>
							</tr>
						</table> 
					</odin:groupBox>
				</td></tr>
			</table>
		</td>
		<td>
			<table style="width: 100%;height: 100%;">
				
				<tr>
					<td rowspan='2' width="250">
						<odin:groupBox title="待选条件列表" >
							<table style="width: 100%;height: 100%;">
								<tr>
									<odin:select2 property="query_type"  label="待选条件类别" data="['9', '常用条件'],['1', '资格条件'],['2', '约束性条件'],['3', '岗位必要条件'],['4', '专业要求'],['5', '优先性条件'],['6', '班子需要'],['7', '来源']" width="300" onchange="changeDXMX"></odin:select2>
								</tr>
								<tr>
									<td colspan="2">
										<odin:editgrid2 property="DXcondGrid" hasRightMenu="false" title="" forceNoScroll="true"  height="620" autoFill="true" ddGroup="dxtomx"  pageSize="50" url="/">
											<odin:gridJsonDataModel>
												<%-- <odin:gridDataCol name="checked"></odin:gridDataCol> --%>
												<odin:gridDataCol name="dcondname" />
												<odin:gridDataCol name="dconddesc"/>
												<odin:gridDataCol name="mxtype"/>
												<odin:gridDataCol name="dxmxid"/>
											</odin:gridJsonDataModel>
											<odin:gridColumnModel>
												<odin:gridRowNumColumn></odin:gridRowNumColumn>
												<odin:gridEditColumn2 dataIndex="dcondname" width="120" header="条件名" editor="text" edited="false" align="center" />
												<odin:gridEditColumn2 dataIndex="dconddesc" width="170" header="条件描述" editor="text" edited="false" align="center"  hidden="true" />
												<odin:gridEditColumn2 dataIndex="dxmxid" width="30" header="主键" editor="text" edited="false" align="center" hidden="true" isLast="true" />
												<%-- <odin:gridEditColumn2 dataIndex="addcond" width="35" header="操作" editor="text" edited="false" align="center" isLast="true" renderer="addconditon" />
											 --%></odin:gridColumnModel>
										</odin:editgrid2>  
									</td>
								</tr>
							</table>
						</odin:groupBox>
					</td>
					<td  width="650">
						<odin:groupBox title="资格条件列表" >
							<odin:editgrid2 property="ZGcondGrid" hasRightMenu="false" title="" forceNoScroll="true"  height="290" autoFill="true"  pageSize="50" url="/">
								<odin:gridJsonDataModel>
									<odin:gridDataCol name="zgcondname" />
									<odin:gridDataCol name="zgconddesc"/>
									<odin:gridDataCol name="zgcondcs1"/>
									<odin:gridDataCol name="zgcondcs2"/>
									<odin:gridDataCol name="zgtjtype"/>
									<odin:gridDataCol name="zgmxid"/>
								</odin:gridJsonDataModel>
								<odin:gridColumnModel>
									<odin:gridRowNumColumn></odin:gridRowNumColumn>
									<odin:gridEditColumn2 dataIndex="zgcondname" width="100" header="资格条件名" editor="text" edited="false" align="center" />
									<odin:gridEditColumn2 dataIndex="zgconddesc" width="130" header="资格条件描述" editor="text" edited="false" hidden="true" align="center" />
									<odin:gridEditColumn2 dataIndex="zgcondcs1" width="50" header="关系" editor="select" codeType="TC05" edited="false" align="center" />
									<odin:gridEditColumn2 dataIndex="zgcondcs2" width="120" header="数值" editor="text" edited="false" align="center" />
									<odin:gridEditColumn2 dataIndex="zgmxid" width="30" header="主键" editor="text" edited="false" align="center" hidden="true" />
									<odin:gridEditColumn2 dataIndex="delzgcond" width="35" header="操作" editor="text" edited="false" align="center" isLast="true" renderer="delzgcond" />
								</odin:gridColumnModel>
							</odin:editgrid2>  
						</odin:groupBox>
					</td>
				</tr>
				<tr>
					<td  width="550">
						<odin:groupBox title="权重条件列表" >
							<odin:editgrid2 property="QZcondGrid" hasRightMenu="false" title="" forceNoScroll="true"  height="290" autoFill="true"  pageSize="50" url="/">
								<odin:gridJsonDataModel>
									<%-- <odin:gridDataCol name="checked"></odin:gridDataCol> --%>
									<odin:gridDataCol name="qzcondname" />
									<odin:gridDataCol name="qzconddesc"/>
									<odin:gridDataCol name="qzcondcs1"/>
									<odin:gridDataCol name="qzcondcs2"/>
									<odin:gridDataCol name="qztjtype"/>
									<odin:gridDataCol name="qzgrade"/>
									<odin:gridDataCol name="qzmxid"/>
								</odin:gridJsonDataModel>
								<odin:gridColumnModel>
									<odin:gridRowNumColumn></odin:gridRowNumColumn>
									<odin:gridEditColumn2 dataIndex="qzcondname" width="100" header="权重条件名" editor="text" edited="false" align="center" />
									<odin:gridEditColumn2 dataIndex="qzconddesc" width="130" header="权重条件描述" editor="text" hidden="true" edited="false" align="center" />
									<odin:gridEditColumn2 dataIndex="qzcondcs1" width="50" header="关系" editor="select" codeType="TC05" edited="false"  align="center" />
									<odin:gridEditColumn2 dataIndex="qzcondcs2" width="120" header="数值" editor="text" edited="false" align="center" />
									<odin:gridEditColumn2 dataIndex="qzgrade" width="40" header="分数" editor="text" edited="false" align="center" />
									<odin:gridEditColumn2 dataIndex="qzmxid" width="30" header="主键" editor="text" edited="false" align="center" hidden="true"  />
									<odin:gridEditColumn2 dataIndex="delqzcond" width="35" header="操作" editor="text" edited="false" align="center" isLast="true" renderer="delqzcond" />
								</odin:gridColumnModel>
							</odin:editgrid2>  
						</odin:groupBox>
					</td>
				</tr>
				
			</table>
		</td>
	</tr>
</table>
</div>
<odin:hidden property="wayid"/>
<odin:hidden property="zdgwid"/>
<odin:toolBar property="btnToolBar">
	<odin:fill/>
	<odin:buttonForToolBar id="btnopenMate" text="预览"  icon="images/save.gif" cls="x-btn-text-icon" handler="openMate" />
	<odin:fill/>
	<odin:buttonForToolBar id="btnSave" text="保存方案"  icon="images/save.gif" cls="x-btn-text-icon" handler="save"/>
	<odin:fill/>
	<odin:buttonForToolBar id="btnAdd" text="新增方案"  icon="images/save.gif" cls="x-btn-text-icon" handler="addway" />
	<odin:fill/>
	<odin:buttonForToolBar id="btnAddZdgw" text="新增重点岗位"  icon="images/save.gif" cls="x-btn-text-icon" handler="addzdgw"  isLast="true"/>
	</odin:toolBar>
<odin:panel contentEl="addMeetingContent" property="addMeetingPanel" topBarId="btnToolBar"></odin:panel>
<script type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';

function addconditon(value, params, record, rowIndex, colIndex, ds){
	//return "<font color=blue><a style='cursor:pointer;' onclick=\"updatepel('"+record.get("sh000")+"','"+record.get("yy_flag")+"');\">维护</a>&nbsp&nbsp<a style='cursor:pointer;' onclick=\"deletepel('"+record.get("sh000")+"','"+record.get("a0000")+"');\">删除</a></font>";
	return "<font color=blue><a style='cursor:pointer;' onclick=\"addconditon2('"+record.get("dxmxid")+"','1');\">资格</a>&nbsp&nbsp<a style='cursor:pointer;' onclick=\"addconditon2('"+record.get("dxmxid")+"','2');\">权重</a></font>";
}

function addconditon2(dxmxid,type){
	if(type==1){
		radow.doEvent('addZGMX',dxmxid);
	}else if(type==2){
		radow.doEvent('addQZMX',dxmxid);
	}
}

function delzgcond(value, params, record, rowIndex, colIndex, ds){
	return "<font color=blue><a style='cursor:pointer;' onclick=\"deletecond('"+record.get("zgmxid")+"','1');\">删除</a></font>";
}

function delqzcond(value, params, record, rowIndex, colIndex, ds){
	return "<font color=blue><a style='cursor:pointer;' onclick=\"deletecond('"+record.get("qzmxid")+"','2');\">删除</a></font>";
}

function addconditon2(dxmxid,type){
	var wayid=document.getElementById('wayid').value;
	if(wayid==''){
		alert("请先选择方案");
		return ;
	}
	if(type==1){
		radow.doEvent('addZGMX',dxmxid);
	}else if(type==2){
		radow.doEvent('addQZMX',dxmxid);
	}
}

function deletecond(mxid,type){
	if(type==1){
		radow.doEvent('deleteZGMX',mxid);
	}else if(type==2){
		radow.doEvent('deleteQZMX',mxid);
	}
}

Ext.onReady(function(){
	$h.initGridSort('zdgwGrid',function(g){
	    radow.doEvent('zdgwsort');
	  });
	var zdgwGrid = Ext.getCmp('zdgwGrid');
	zdgwGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		var zdgwid=rc.data.zdgwid;
		document.getElementById('zdgwid').value = zdgwid;
		document.getElementById('wayid').value = "";
		radow.doEvent('waysGrid.dogridquery');
		radow.doEvent('ZGcondGrid.dogridquery');
		radow.doEvent('QZcondGrid.dogridquery');
	});
	  
	zdgwGrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		var zdgwid=rc.data.zdgwid;
		$h.openPageModeWin('addzdgw','pages.mntpsj.PZZDGW','重点岗位维护',500,350,{zdgwid:zdgwid},g_contextpath);
	});
	
	
	$h.initGridSort('waysGrid',function(g){
	    radow.doEvent('rolesort');
	  });
	var waysGrid = Ext.getCmp('waysGrid');
	waysGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		var wayid=rc.data.wayid;
		document.getElementById('wayid').value = wayid;
		radow.doEvent('ZGcondGrid.dogridquery');
		radow.doEvent('QZcondGrid.dogridquery');
	});
	  
	waysGrid.on('rowdblclick',function(gridobj,index,e){
		var zdgwid=document.getElementById('zdgwid').value;
		var rc = gridobj.getStore().getAt(index);
		var wayid=rc.data.wayid;
		$h.openPageModeWin('addway','pages.mntpsj.PZZDGWWAY','方案维护',400,250,{wayid:wayid,zdgwid:zdgwid},g_contextpath);
	});
	  
	Ext.getCmp('DXcondGrid').on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		var dxmxid=rc.data.dxmxid;
		var mxtype=rc.data.mxtype;
		if(mxtype=='41'||mxtype=='931'){
			dxmxid=dxmxid+"##1";
			radow.doEvent('addQZMX',dxmxid);
		}else if(mxtype=='71'||mxtype=='932'){
			dxmxid=dxmxid+"##1";
			radow.doEvent('addZGMX',dxmxid);
		}else{
			radow.doEvent('addZGMX',dxmxid);
		}
	 });
	
	 Ext.getCmp('ZGcondGrid').on('rowdblclick',function(gridobj,index,e){
	 	var rc = gridobj.getStore().getAt(index);
	 	if(rc.data.zgtjtype=='3'){
			return;
	 	}else{
	 		$h.openPageModeWin('updateMX','pages.mntpsj.PZZDGWMX','修改条件',500,315,{type1:'zg',id:rc.data.zgmxid,cs:rc.data.zgcondcs1,query:''},g_contextpath);
	 	}
	 });
	 
	 Ext.getCmp('QZcondGrid').on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		$h.openPageModeWin('updateMX','pages.mntpsj.PZZDGWMX','修改条件',500,315,{type1:'qz',id:rc.data.qzmxid,cs:rc.data.qzcondcs1,query:''},g_contextpath);
	 });
	
  	 GridDrop2G.init("ZGcondGrid");
	 new Ext.dd.DropTarget(Ext.getCmp('ZGcondGrid').container,GridDrop2G);
	 GridDrop2G.init("QZcondGrid");
	 new Ext.dd.DropTarget(Ext.getCmp('QZcondGrid').container,GridDrop2G);
}); 

function save(){
	var wayid=document.getElementById('wayid').value;
	if(wayid==''){
		alert("请先选择方案");
		return ;
	}
	radow.doEvent('save');
}

function updateZdgw(){
	document.getElementById('zdgwid').value="";
	document.getElementById('wayid').value="";
	radow.doEvent('zdgwGrid.dogridquery');
}

function changeDXMX(){
	radow.doEvent('DXcondGrid.dogridquery');
}

function updateZDGWZG(){
	radow.doEvent('ZGcondGrid.dogridquery');
}

function updateZDGWQZ(){
	radow.doEvent('QZcondGrid.dogridquery');
}

function updateWay(){
	document.getElementById('wayid').value="";
	radow.doEvent('waysGrid.dogridquery');
}

function openMate(){
	var wayid=document.getElementById('wayid').value;
	if(wayid==''){
		alert("请先选择方案");
		return ;
	}
  	$h.openWin('gbmainListWin','pages.amain.GBMainList','人员列表',1410,900,'','<%=request.getContextPath()%>',window,{query_type:'zdgw'+'##'+wayid},true);
}

function addzdgw(){
	$h.openPageModeWin('addzdgw','pages.mntpsj.PZZDGW','重点岗位维护',500,350,{zdgwid:''},g_contextpath);
}

function addway(){
	var zdgwid=document.getElementById('zdgwid').value;
	if(zdgwid==''){
		alert("请先选择重点岗位");
		return ;
	}
	$h.openPageModeWin('addway','pages.mntpsj.PZZDGWWAY','方案维护',400,250,{wayid:'',zdgwid:zdgwid},g_contextpath);
}


function delzdgw(value, params, record, rowIndex, colIndex, ds){
	return "<font color=blue><a style='cursor:pointer;' onclick=\"delzdgw2('"+record.get("zdgwid")+"','"+record.get("zdgwname")+"');\">删除</a></font>";
}

function delzdgw2(zdgwid,zdgwname){
	if(confirm("确认删除重点岗位："+zdgwname+"?")){
		radow.doEvent("delzdgw",zdgwid);
	}
}

function delway(value, params, record, rowIndex, colIndex, ds){
	return "<font color=blue><a style='cursor:pointer;' onclick=\"delway2('"+record.get("wayid")+"');\">删除</a></font>";
}

function delway2(wayid){
	radow.doEvent("delway",wayid);
}

var GridDrop2G = {
    ddGroup : 'dxtomx',
    copy : false,
    init: function(gridid) {
    	this.gridid = gridid;
    },
    notifyEnter : function(dd, e, data){
      delete this.dropOK;
      var rows = data.selections;
         
        //遍历store
        for ( i=0; i<rows.length; i++){
            var rowData = rows[i];
            
            
        }
      
      this.dropOK=true;
        return this.dropAllowed;
    },
    notifyOver : function(dd, e, data){
      return this.dropOK ? this.dropAllowed : this.dropNotAllowed;
    },
    notifyDrop : function(dd,e,data){
      if(this.dropOK){
        //选中了多少行
            var rows = data.selections;
            //拖动一个人的时候，如果该人有其他职务，也加进去。
            if(this.gridid=='ZGcondGrid'){
	            for ( ind=0; ind<rows.length; ind++){
	              var rowData = rows[ind];
	              var dxmxid=rowData.data.dxmxid;
	              radow.doEvent('addZGMX',dxmxid);
	            }
            }
            if(this.gridid=='QZcondGrid'){
	            for ( ind=0; ind<rows.length; ind++){
	              var rowData = rows[ind];
	              var dxmxid=rowData.data.dxmxid;
	              radow.doEvent('addQZMX',dxmxid);
	            }
            }
            if(typeof callback=='function'){
                callback(pgrid);
            }
      }
        

    }
}
</script>
