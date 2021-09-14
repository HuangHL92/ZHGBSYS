<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="���˻�����Ϣ��ѯ" description="���˻�����Ϣ��ѯ" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ attribute name="onPersonSelected" description="����Ա��ѡ��ʱ����" required="true"%>
<%@ attribute name="onPersonCleared" description="����Ա�����ʱ����" required="true"%>

<script language="JavaScript">
	var personData;
	function personSelected(psdata){
		personData=psdata;
		odin.Ajax.request(contextPath+"/com/insigma/siis/local/module/common/search/RetrieveInjuryRegInfoAction.do?method=retrieveInjuryRegInfo",
							{'aac001':psdata.aac001},retrieveInjuryRegInfoSuccess,null);		
	}
	
	function retrieveInjuryRegInfoSuccess(response){
		var ir;
		//alert(response.data.length);
		if(response.data.length<=0){
			alert("�����϶���Ϣ�����ڣ����Ȱ������϶��Ǽ�");
			intelligentSearch_clear();
			//personCleared();
		}else if(response.data.length==1){
			ir=response.data[0];
		}else{
			var rs=window.showModalDialog(contextPath+"/pages/common/InjuryRegSelect.jsp",response.data,"help:no;status:no;dialogWidth:35;dialogHeight:19");
			if(rs!=null){
				ir=response.data[rs];
			}
		}
		if(ir){
			var panel = Ext.getCmp('injuryRegInfoPanel');
			injuryRegInfoTpl.overwrite(panel.body,ir);
			setTimeout(function(){Ext.getCmp('injuryRegInfoPanel').expand(true);},1);
			<%=onPersonSelected%>(personData,ir);
		}else{
			intelligentSearch_clear();
		}
	}
	
	function injuryRegInfoTplCollapse(){
		Ext.getCmp('injuryRegInfoPanel').collapse(true);
	}
	
	function personCleared(){
		injuryRegInfoTplCollapse();
		<%=onPersonCleared%>();
	}
</script>

<tags:BasicEmpInfo onPersonCleared="personCleared" onPersonSelected="personSelected" beforeDetailsCollapse="injuryRegInfoTplCollapse"/>
<script language="JavaScript">
<odin:template name="injuryRegInfoTpl">
	'<div style="float:left;clear:both;margin-left:3px;margin-top:3px">',
	'<div><p><span class="psinfo_brief">�������&nbsp;<span id="t_alc027">{alc027Name}</span>,&nbsp;����ʱ��&nbsp;<span id="t_alc020">{alc020s}</span>,&nbsp;�˲еȼ�&nbsp;<span id="t_ala040">{ala040Name}</span>,&nbsp;����ȼ�&nbsp;<span id="t_alc060">{alc060Name}</span>,',
	'&nbsp;�˺��̶�&nbsp;<span id="t_alc021">{alc021Name}</span>,&nbsp;�˺���λ&nbsp;<span id="t_alc022">{alc022}</span>',
	'</span></div>',
	'<div><span class="psinfo_brief">��ע&nbsp;<span id="t_aae013">{aae013}</span></span></div>',
	'</div>'
</odin:template>
</script>
<odin:panel property="injuryRegInfoPanel" frame="false" collapsed="true"  collapsible="true" contentEl=""/>