<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/basejs/jquery-ui/jquery-1.10.2.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/fxyp/js/field-to-grid-dd.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cookie.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">

<style type="text/css">
.x-grid-record-nm{
	background: #ccccff;
}
.x-grid-record-zr{
	background: #ff5555;
}
#bdbutton button{
	margin-right:1px
}
table{
	overflow:auto;
}
.x-grid3-col{
/*   vertical-align: middle; */
     vertical-align: middle!important; 
}
.quxianshi{
	font-family: SimHei;
	font-size:15;
}

</style>
<script type="text/javascript">

</script>
<odin:hidden property="mntp00"/>
<odin:hidden property="fxyp07"/>
<odin:hidden property="a0200"/>
<odin:hidden property="a0215a"/>
<odin:hidden property="b01id"/>
<odin:hidden property="zrrx"/>
<div id="totaldiv">
<table style="width: 100%;" id="totaltable">
	<tr>
		<td >
			<div  style="align:left top;overflow:auto;"  id="selectable">
				<table id="jgDiv" width='100%'>
					<tr>
						<odin:select2 property="b0111" onchange="zrrxQuery()" label="ѡ������λ" ></odin:select2>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
						<td class="quxianshi" id="dwxz">
							<label><input type="radio" name="zrrx_r" id="zrrx1" onclick="changeZSB()" value="1001" checked>��ί</label>
							<label><input type="radio" name="zrrx_r" onclick="changeZSB()" value="1004">����</label>
							<label><input type="radio" name="zrrx_r" onclick="changeZSB()" value="1003">�˴�</label>
							<label><input type="radio" name="zrrx_r" onclick="changeZSB()" value="1005">��Э</label>
							<label><input type="radio" name="zrrx_r" onclick="changeZSB()" value="1006">��Ժ</label>
							<label><input type="radio" name="zrrx_r" onclick="changeZSB()" value="1007">���Ժ</label>
							<label><input type="radio" name="zrrx_r" onclick="changeZSB()" value="Z01">����</label>
						</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
						
						<td>
							<div class="btns"> 
							<button onclick="savesort()" type="button">���浱ǰ����</button>
							</div>
						</td>
<%-- 						<odin:select2 property="zrrx"  label="ѡ���������(������)" onchange="gwQuery()"></odin:select2> --%>
						<%-- <td>
							<odin:button text="��ѯ" property="gwQuery" handler="gwQuery"></odin:button>
						</td> --%>
						<%-- <td>
							<odin:button text="����ְ������" property="nmjcbd" handler="nmjcbd"></odin:button>
						</td>
						<td>&nbsp;&nbsp;&nbsp;
						</td>
						<td>
							<odin:button text="����ְ���" property="nmbd" handler="nmbd"></odin:button>
						</td> --%>
					</tr>
				</table>
			</div>
		</td>
	</tr>
	<tr>
		<td width="100%" height="670"  valign='top'>
				<odin:editgrid2 property="gwGrid" hasRightMenu="false" bbarId="pageToolBar" pageSize="50"  width="1000" height="670" isFirstLoadData="false" pageSize="200" url="/">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="sortnum"/>
						<odin:gridDataCol name="zrrx" />
						<odin:gridDataCol name="fxyp07"/>
						<odin:gridDataCol name="a0101"/>
						<odin:gridDataCol name="a0215a"/>
						<odin:gridDataCol name="a0200"/>
						<odin:gridDataCol name="a0000s"/>
						<odin:gridDataCol name="a0101s"/>
						<odin:gridDataCol name="a0104s"/>
						<odin:gridDataCol name="a0141s"/>
						<odin:gridDataCol name="a0192as"/>
						<odin:gridDataCol name="ryxx" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 dataIndex="sortnum"  width="50" header="���"  editor="text"  align="center"  edited="false"/>
						<odin:gridEditColumn2 dataIndex="zrrx"  header="��������" hidden="true" edited="false"  editor="text"/>
						<odin:gridEditColumn2 dataIndex="fxyp07" hidden="true" header="ְ��ȫ��" editor="text"  edited="false" />
						<odin:gridEditColumn2 dataIndex="a0215a"  header="ְ������" width="250" editor="text" align="left"  edited="false"/>
						<odin:gridEditColumn2 dataIndex="a0200" hidden="true"  header="��Աid" editor="text"  edited="false"    />
						<odin:gridEditColumn2 dataIndex="a0000s"  hidden="true" header="��Ա����"  editor="text"  align="center"  edited="false"/>
						<odin:gridEditColumn2 dataIndex="a0101s"   hidden="true"   header="��Ա����" editor="text"  edited="false"  align="left"    />
						<odin:gridEditColumn2 dataIndex="a0104s"   hidden="true"   header="�Ա�" editor="text"  edited="false"  align="left"    />
						<odin:gridEditColumn2 dataIndex="a0141s"   hidden="true"   header="����" editor="text"  edited="false"  align="left"    />
						<odin:gridEditColumn2 dataIndex="a0192as"   hidden="true"   header="����ְ��" editor="text"  edited="false"  align="left"    />
						<odin:gridEditColumn2 dataIndex="ryxx"   width="700"   header="��Ա��Ϣ" editor="text"  edited="false" renderer="ryxxRenderer" align="left"  isLast="true"  />
					</odin:gridColumnModel>
				</odin:editgrid2>
		</td>
		<%-- <td width="60%" height="670" >
			<odin:editgrid2 property="rxGrid" hasRightMenu="false" bbarId="pageToolBar" pageSize="50" width="600"  height="670" isFirstLoadData="false" pageSize="200" url="/">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="sortnum" />
						<odin:gridDataCol name="a0101" />
						<odin:gridDataCol name="a0104"/>
						<odin:gridDataCol name="a0107"/>
						<odin:gridDataCol name="a0192a"/>
						<odin:gridDataCol name="fxyp07"/>
						<odin:gridDataCol name="a0141" isLast="true"/>
				</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 dataIndex="sortnum"  header="���" align="center"   width="50" edited="false"  editor="text"/>
						<odin:gridEditColumn2 dataIndex="a0101"  header="����" align="center"   width="100" edited="false"  editor="text"/>
						<odin:gridEditColumn2 dataIndex="a0104"  header="�Ա�" width="50"  align="center" editor="text"  edited="false" />
						<odin:gridEditColumn2 dataIndex="a0107"  header="��������" width="100"  align="center" editor="text"  edited="false"/>
						<odin:gridEditColumn2 dataIndex="a0192a"  width="200" header="����ְ��"  editor="text"  align="left"  edited="false"/>
						<odin:gridEditColumn2 dataIndex="fxyp07"  hidden="true" header="�������"  editor="text"  align="left"  edited="false"/>
						<odin:gridEditColumn2 dataIndex="a0141"  header="����" width="100" editor="text" align="center" edited="false"  isLast="true"  />
					</odin:gridColumnModel>
			</odin:editgrid2>
		</td> --%>
	</tr>
</table>
</div>>


<script type="text/javascript">



Ext.onReady(function(){
	$('#mntp00').val(parent.Ext.getCmp(subWinId).initialConfig.mntp00);
	$('#b01id').val(parent.Ext.getCmp(subWinId).initialConfig.b01id);
	$h.initGridSort('gwGrid',function(g){
		radow.doEvent('rolesort');
	});	
	
	
	/* $h.initGridSort('rxGrid',function(g){
		radow.doEvent('rolesort1');
	}); */	
	Ext.getCmp('gwGrid').getView().getRowClass=function(record,rowIndex,rowParams,store){
		var fxyp07 = record.data.fxyp07;
		if(fxyp07=='-1'){//����
			return 'x-grid-record-nm';
        }else if(fxyp07=='1'){//����
			return 'x-grid-record-zr';
        }else{
        	return '';
        }
		
	};
	
	
	var viewSize = Ext.getBody().getViewSize();
	var height=viewSize.height;
	var gwGrid = Ext.getCmp('gwGrid');
	gwGrid.setHeight(viewSize.height-40);
	$("#totaldiv").css('width',viewSize.width);
	/* Ext.getCmp('rxGrid').getView().getRowClass=function(record,rowIndex,rowParams,store){
		var fxyp07 = record.data.fxyp07;
		if(fxyp07=='-1'){//����
			return 'x-grid-record-nm';
        }else if(fxyp07=='1'){//����
			return 'x-grid-record-zr';
        }else{
        	return '';
        }
		
	}; */
	
	/*  Ext.getCmp('rxGrid').setHeight((Ext.getBody().getViewSize().height-$h.pos(document.getElementById('forView_rxGridGrid')).top-4)*0.99);
	 Ext.getCmp('gwGrid').setHeight(Ext.getCmp('rxGrid').getHeight()); */
});

function zrrxQuery(){
	if(b0111 == null || b0111 == ""){
		$h.alert('ϵͳ��ʾ��','��ѡ��λ��',null,150);
		return;
	}
	document.getElementById("zrrx1").checked='true';
	document.getElementById("zrrx").value='1001';
	radow.doEvent('gwGrid.dogridquery');
}

/* function gwQuery(){
	var b0111=document.getElementById("b0111").value;
	var zrrx=document.getElementById("zrrx").value;
	if(b0111 == null || b0111 == ""){
		$h.alert('ϵͳ��ʾ��','��ѡ��λ��',null,150);
		return;
	}
	if((zrrx==null || zrrx=="") && b0111.length==15 && b0111.substring(0,11)=="001.001.004"){
		$h.alert('ϵͳ��ʾ��','��ѡ�������л������ʣ�',null,150);
		return;
	}
	radow.doEvent('gwGrid.dogridquery');
} */


/* function nmjcbd(){
	var fxyp07=document.getElementById("fxyp07").value;
	var a0200=document.getElementById("a0200").value;
	if(fxyp07!=-1 || a0200==null || a0200==''){
		$h.alert('ϵͳ��ʾ��','��ѡ������ְ��',null,150);
		return;
	}
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ�Ͻ���󶨣�",function(id) { 
		if("yes"==id){
			radow.doEvent('removebind');
		}else{
			return;
		}		
	});	
} */

function PersonQuery(){
	radow.doEvent('rxGrid.dogridquery');
}
<%-- function nmbd(){
	var mntp00=document.getElementById("mntp00").value;
	var fxyp07=document.getElementById("fxyp07").value;
	var a0200=document.getElementById("a0200").value;
	var zrrx=document.getElementById("zrrx").value;
	var b0111=document.getElementById("b0111").value;
	var a0215a=document.getElementById("a0215a").value;
	if(fxyp07!=-1 || a0200==null || a0200==''){
		$h.alert('ϵͳ��ʾ��','��ѡ������ְ��',null,150);
		return;
	}
	$h.openWin('ViewNMBDWin','pages.fxyp.NMGWBD',a0215a,500,720,'','<%=request.getContextPath()%>',null,
			{mntp00:mntp00,b0111:b0111,a0200:a0200,fxyp07:fxyp07,zrrx:zrrx,a0215a:a0215a},true);
} --%>

function changeZSB(){
	var b0111=document.getElementById("b0111").value;
	var zrrx = $("input[name='zrrx_r']:checked").val();
	$("#zrrx").val(zrrx);
	if(b0111 == null || b0111 == ""){
		$h.alert('ϵͳ��ʾ��','��ѡ��λ��',null,150);
		document.getElementById("zrrx1").checked='true';
		return;
	}
	if(b0111.indexOf('@')==-1){
		if(b0111.length!=15 || b0111.substring(0,11)!="001.001.004"){
			$h.alert('ϵͳ��ʾ��','��ѡ�������к��л���',null,150);
			document.getElementById("zrrx1").checked='true';
			return;
		}
	}else{
		var type=b0111.split("@");
		if(type[1]==1){
			$h.alert('ϵͳ��ʾ��','��ѡ�������к��л���',null,150);
			document.getElementById("zrrx1").checked='true';
			return;
		}
	}
	
	radow.doEvent('gwGrid.dogridquery');
}	
	

function zwryRenderer(value, params, record, rowIndex, colIndex, ds) {
	value = (value||"")+"<br/>"+record.data.a0101+"";
	return value;
}


function savesort() {
/* 	var a=parent.Ext.getCmp(subWinId).initialConfig.b01id;
	if(a==null || a==''){
		$h.alert('ϵͳ��ʾ��','����ѡ�������',null,150);
		return;
	} */
	radow.doEvent('rolesort');
}

function ryxxRenderer(value, params, record, rowIndex, colIndex, ds) {
	if(record.get("a0000s")==null){
		return result;
	}
	if(record.get("a0000s").indexOf(",")==-1){
		result = "<table width='100%'><tr><td align='center' width='50' valign='middle' >"+record.get("a0101s")+"</td><td align='center' width='30' valign='middle'>"+record.get("a0104s")+"</td><td  align='center' width='70' valign='middle'>"+record.get("a0141s")+"</td><td align='left'  width='400' valign='middle'>"+record.get("a0192as")+"</td><td width='100'></td></tr></table>";
		return result;
	}
	var a0000s = record.get("a0000s").split(",");
	var a0101s = record.get("a0101s").split(",");
	var a0104s = record.get("a0104s").split(",");
	var a0141s = record.get("a0141s").split(",");
	var a0192as = record.get("a0192as").split("-");
	var result = "<table width='100%'>";
	for(var i=0;i<a0000s.length;i++){
		/* result = result+"<div align='center' width='100%' ><font color=blue>"
		+ "<a href=\"javascript:deleteRow2()\">����</a>&nbsp; &nbsp; &nbsp;<a href=\"javascript:deleteRow2()\">����</a>&nbsp; &nbsp; &nbsp;<a style='cursor:pointer;' onclick=\"outfile('"+pat00s[i]+"');\">"+pat04s[i]+"</a>"
		+ "</font></div><br>"; */
		/* result = result+"<tr><td align='left'><a href=\"javascript:topordown('"+pat00s[i]+"','1')\">����</a>&nbsp;&nbsp;&nbsp;<a href=\"javascript:topordown('"+pat00s[i]+"','2')\">����</a><td><font color=blue><a style='cursor:pointer;' onclick=\"outfile('"+pat00s[i]+"');\">"+pat04s[i]+"</a></font></td></tr>"; */
		if(i==0){
			result = result+"<tr><td align='center' width='50' valign='middle' >"+a0101s[i]+"</td><td align='center' width='30' valign='middle'>"+a0104s[i]+"</td><td align='center' width='70' valign='middle'>"+a0141s[i]+"</td><td align='left' width='400' valign='middle'>"+a0192as[i]+"</td><td width='100' align='center'><u style=\"color:#ff5555\">����</u>&nbsp;&nbsp;&nbsp;<a href=\"javascript:topordown('"+a0000s[i]+"','2','"+record.get("a0200")+"')\">����</a>&nbsp;&nbsp;&nbsp;</td></tr>";
			continue;
		}
		if(i==a0000s.length-1){
			result = result+"<tr><td align='center' width='50' valign='middle'>"+a0101s[i]+"</td><td align='center' width='30' valign='middle'>"+a0104s[i]+"</td><td align='center' width='70' valign='middle'>"+a0141s[i]+"</td><td align='left' width='400' valign='middle'>"+a0192as[i]+"</td><td width='100'  align='center'><a href=\"javascript:topordown('"+a0000s[i]+"','1','"+record.get("a0200")+"')\">����</a>&nbsp;&nbsp;&nbsp;<u style=\"color:#ff5555\">����</u>&nbsp;&nbsp;&nbsp;</td></tr>";
			continue;
		}
		
		/* result = result+"<tr><td align='left'><a href=\"javascript:topordown('"+pat00s[i]+"','1')\">����</a>&nbsp;&nbsp;&nbsp;<a href=\"javascript:topordown('"+pat00s[i]+"','2')\">����</a>&nbsp;&nbsp;&nbsp;<a href=\"javascript:deleteAtt('"+pat00s[i]+"')\">ɾ��</a><td><font color=blue><a style='cursor:pointer;' onclick=\"outfile('"+pat00s[i]+"');\">"+pat04s[i]+"</a></font></td></tr>"; */
		result = result+"<tr><td align='center' width='50' valign='middle'>"+a0101s[i]+"</td><td align='center' width='30' valign='middle'>"+a0104s[i]+"</td><td align='center' width='70' valign='middle'>"+a0141s[i]+"</td><td align='left' width='400' valign='middle'>"+a0192as[i]+"</td><td width='100'  align='center'><a href=\"javascript:topordown('"+a0000s[i]+"','1','"+record.get("a0200")+"')\">����</a>&nbsp;&nbsp;&nbsp;<a href=\"javascript:topordown('"+a0000s[i]+"','2','"+record.get("a0200")+"')\">����</a>&nbsp;&nbsp;&nbsp;</td></tr>";
	}
	/* result = result.substring(0,result.length-4); */
	result = result+"</table>"
	return result;
}

function topordown(a0000,type,fxyp00){
	radow.doEvent('sort',a0000+"@"+type+"@"+fxyp00);
}
</script>
