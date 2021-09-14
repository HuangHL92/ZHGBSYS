<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gbk">
    <title>UI Demo</title>
	<odin:head />
	<style type="text/css">
	    .listname {
		font-family: "΢���ź�", "����", "����";
		font-size: 14px;
		font-weight: bold;
	}
	    .listsex {
		font-family: "����";
		font-size: 12px;
	}
	    .listtitle {
		font-family: "����", "����";
		font-size: 13px;
	}
	    .listnormal {
		font-family: "����";
		font-size: 12px;
		text-decoration: none;
	}
		psinfo{
			background:transparent;
		}
	    .psinfo_name {
		font-family: "΢���ź�", "����", "����";
		font-size: 16px;
		font-weight: bold;
		color: #333333;
	}
	    .psinfo_title {
		font-family: "����";
		color: #000000;
		width: 60px;
		font-weight: 400;
		font-size: 13px;
	}
	.psinfo_brief {
		font-family: "����";
		margin-left: 3px;
		margin-right: 15px;
		color: #000000;
		font-size: 12px;
		font-weight:bold;
	}
	    .psinfo_data {
		font-family: "����";
		margin-left: 5px;
		margin-right: 15px;
		color: #333333;
		font-size: 12px;
	}
	.search-item {
	    font:normal 11px tahoma, arial, helvetica, sans-serif;
	    padding:3px 10px 3px 10px;
	    border:1px solid #fff;
	    border-bottom:1px solid #eeeeee;
	    white-space:normal;
	    color:#555;
	}
	.search-item h3 {
	    display:block;
	    font:inherit;
	    font-weight:bold;
	    color:#222;
	}
	
	.search-item h3 span {
	    float: right;
	    font-weight:normal;
	    margin:0 0 5px 5px;
	    width:100px;
	    display:block;
	    clear:none;
	}
	</style>
	<script type="text/javascript">
		var initialize_i;
		Ext.onReady(function(){
			//alert('ok');
			initialize_i=window.setInterval("initialize()",100);
		});
		
		function initialize(){	
		    if(document.readyState=="complete"){
     			window.clearInterval(initialize_i);
   			}
   			else
   			  return;
			
			var sm = new Ext.grid.RowSelectionModel();
			var reader = new Ext.data.ArrayReader({}, [
				{name: 'EKD007'},
				{name: 'EKA034'},
				{name: 'AKA074'},
				{name: 'AKA067'},
				{name: 'AKC225', type: 'float'},
				{name: 'AKC226', type: 'float'},
				{name: 'AKC227', type: 'float'},
				{name: 'AKA069', type: 'float'},
				{name: 'EKC109', type: 'float'},
				{name: 'AKC228', type: 'float'},
				{name: 'EKC126', type: 'float'}
			]);
			var colModel = new Ext.grid.ColumnModel([
				{id:'EKD007',header: '���', width: 140, sortable: true,hidden:false, dataIndex: 'EKD007'},
				{header: '����',hidden:false, width: 140, sortable: true, dataIndex: 'EKA034', editor: new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank:true }))},
				{header: "���", width: 140, sortable: true,hidden:false, dataIndex: 'AKA074'},
				{header: "��λ", width: 40, sortable: true,hidden:false, dataIndex: 'AKA067'},
				{header: "����", width: 140, sortable: true,hidden:false, dataIndex: 'AKC225'},
				{header: "����",hidden:false, width: 40, sortable: true, dataIndex: 'AKC226', editor: new Ext.grid.GridEditor(new Ext.form.NumberField({allowBlank:true }))},
				{header: "���", width: 140, sortable: true,hidden:false, dataIndex: 'AKC227'},
				{header: "�Ը�����", width: 40, sortable: true,hidden:false, dataIndex: 'AKA069'},
				{header: "�������", width: 140, sortable: true,hidden:false, dataIndex: 'EKC109'},
				{header: '����',hidden:false, width: 40, sortable: true, dataIndex: 'EKA034', editor: new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank:true }))},
				{header: "���", width: 140, sortable: true,hidden:false, dataIndex: 'AKA074'},
				{header: "��λ", width: 40, sortable: true,hidden:false, dataIndex: 'AKA067'},
				{header: "����", width: 140, sortable: true,hidden:false, dataIndex: 'AKC225'},
				{header: "����",hidden:false, width: 40, sortable: true, dataIndex: 'AKC226', editor: new Ext.grid.GridEditor(new Ext.form.NumberField({allowBlank:true }))},
				{header: "���", width: 40, sortable: true,hidden:false, dataIndex: 'AKC227'},
				{header: "�Ը�����", width: 140, sortable: true,hidden:false, dataIndex: 'AKA069'},
				{header: "�������", width: 40, sortable: true,hidden:false, dataIndex: 'EKC109'},
				{header: "�Էѷ���", width: 140, sortable: true,hidden:false, dataIndex: 'AKC228'},
				{header: "�޳�����",hidden:false, width: 140, sortable: true, dataIndex: 'EKC126', editor: new Ext.grid.GridEditor(new Ext.form.NumberField({allowBlank:true }))}
			]);
			 var gridData=[
	        ['1','��Ī����','100g','��',20,5,100,0.1,10,0,0]
			];
			var grid_grid1 = new Ext.grid.GridPanel({
								ds: new Ext.data.Store({
									reader: reader,data: gridData}),
								cm:colModel,
								id:'grid1',
								selModel:sm,
								viewConfig: {forceFit:false,autoFill:false},
								width:788,
								height: 140,
								title:'������ϸ��Ϣ',
								collapsible:false,
								renderTo:div_property});		
				
			var panel_digist = new Ext.Panel({
				//html: '<p>Digist Area</p>',
				title: 'ҵ��ժҪ',
				frame:true,
				autoWidth : true,
				collapsible: false,
				collapsed: true,
				ctCls:'.x-panel-mc {background:#ffffff;}',
				//contentEl:'digist',
				renderTo:'digistdiv'
			});

			var panel_main = new Ext.Panel({
				//title: 'ҵ�����',
				frame:false,
				autoWidth : true,
				collapsible: false,
				renderTo:'maincontainer',
				contentEl:'maindiv',
				tbar:[
				      '<h3>ҵ��������</h3>',
					  '->',
					 {text:'����',
					  handler:function(){alert(panel_psinfo.body.dom.style.backgroundColor)},
					  icon:'../../img/icon/save.gif',
					  cls:'x-btn-text-icon' },
					 {text:'��ӡ',
					  icon:'../../img/icon/print.gif',
					  cls:'x-btn-text-icon'},
					 {text:'��־',
					  icon:'../../img/icon/comment.gif',
					  cls:'x-btn-text-icon'}
				]
			});			
			
			var panel_psinfo = new Ext.Panel({
				frame:true,
				border:true,
				collapsible:true,
				collapsed:true,
				renderTo:'psinfodiv'
			});
			
			var ds = new Ext.data.Store({
				proxy: new Ext.data.MemoryProxy({'result' : 2,'rows': [
				 { 'psid': 1, 'name': '�ΰ��', 'sex': '��', 'iscode': '3301831972120700763', 'pscode': '12345678', 'birthdate':'1972-12-07','workdate':'1994-07-01','laborrel':'�ڱ�','psstatus':'��ְ','cpname':'IVAO China Division','mdtype':'��ҵ��ְ','account':'251.45'},
				 { 'psid': 2, 'name': '����', 'sex': 'Ů', 'iscode': '3122311978042876123', 'pscode': '12345679', 'birthdate':'1978-04-28','workdate':'1999-07-15','laborrel':'�ڱ�',  'psstatus':'��ְ','cpname':'���Ե�λ11111111111111111111111111111111111111111111111111111111','mdtype':'����Ա��ְ','account':'65.15'}
				]}
				),
				reader: new Ext.data.JsonReader({
					root: 'rows',
					totalProperty: 'results',
					id: 'psid'
				}, [
					{name: 'name', mapping: 'name'},
					{name: 'psid', mapping: 'psid'},
					{name: 'sex', mapping: 'sex'},
					{name: 'iscode', mapping: 'iscode'},
					{name: 'pscode', mapping: 'pscode'},
					{name: 'birthdate', mapping: 'birthdate'},
					{name: 'workdate', mapping: 'workdate'},
					{name: 'laborrel', mapping: 'laborrel'},
					{name: 'psstatus', mapping: 'psstatus'},
					{name: 'cpname', mapping: 'cpname'},
					{name: 'mdtype'},
					{name: 'account'}
				])
			});
			ds.load();
			// Custom rendering Template
			var resultTpl = new Ext.XTemplate(
				'<tpl for="."><div class="search-item" style="width=400px">',
				'<p><span class="listname">{name}</span>,<span class="listsex">{sex}</span>,&nbsp;{pscode},',
				'<span class="listnormal">{iscode}</span><br>',
				'<span class="listnormal">{cpname}</span></p>',
				'</div></tpl>'
			);
			
			var psinfoTpl = new Ext.XTemplate(
				'<div style="float:left;clear:both;margin-left:3px"><div><p><span class="psinfo_name">{name}</span><span class="psinfo_brief">&nbsp;&nbsp;{sex},&nbsp;&nbsp;{iscode},&nbsp;&nbsp;{birthdate}&nbsp;����,&nbsp;&nbsp;{workdate}&nbsp;�μӹ���,&nbsp;&nbsp;{laborrel},&nbsp;&nbsp;{psstatus}</span></div>',
				'<div style="margin-top:3px"><span class="psinfo_title">�籣����:</span>',
				'<span class="psinfo_data">{pscode}</span>',
				'<span class="psinfo_title">���ڵ�λ:</span>',
				'<span class="psinfo_data"><a href="#">{cpname}</a></span>',
				'</div>',
				'<div style="margin-top:3px">',
				'<span class="psinfo_title">ҽ�����:</span>',
				'<span class="psinfo_data">{mdtype}</span>',
				'<span class="psinfo_title">�ʺ����:</span>',
				'<span class="psinfo_data">{account}</span>',
				'<span class="psinfo_data" style="text-align:right"><a href=#>��ϸ��Ϣ</a></span>',
				'</div>',
				'</div>',
				'<div style="float:right;clear:both;margin-top:15px;margin-right:3px;"><img src="/insiis/img/kfind.gif" /></div>'
			);
			
			var digistdata = {
				facts:[{fname:'סԺ�ۼ�',fvalue:192.12 },{fname:'�����ۼ�',fvalue:22.12 },{fname:'���ⲡ�ۼ�',fvalue:0.0}],
				regist:[{rname:'��ذ���',rid:10},{rname:'���ⲡ',rid:11},{rname:'ת���ҽ',rid:10}]
			}; 
			
			var digistTpl = new Ext.XTemplate(
				'<div style="margin-top:2px;margin-bottom:2px"><span class="psinfo_title">�ۼ���Ϣ</span>',
				'<span class="psinfo_data">&nbsp;&nbsp',
				'<tpl for="facts">',
				'{fname}:{fvalue}&nbsp;',
				'</tpl>',
				'</span>',
				'</div>',
				'<img src="<%=request.getContextPath()%>/img/separator.gif" width="100%" height="2" style="margin-top:0;margin-bottom:0"/>',
				'<div style="margin-top:2px;margin-bottom:2px"><span class="psinfo_title">�Ǽ���Ϣ</span>',
				'<span class="psinfo_data">&nbsp;&nbsp',
				'<tpl for="regist">',
				'<a href=#>{rname}</a>&nbsp;',
				'</tpl>',
				'</span>',
				'</div>'
			);
			
			var search = new Ext.form.ComboBox({
				store: ds,
				displayField:'name',
				typeAhead: false,
				loadingText: 'Searching...',
				width: 300,
				mode: 'local',
				pageSize:10,
				hideTrigger:true,
				triggerAction : 'all', 
				tpl: resultTpl,
				itemSelector: 'div.search-item',
 				onSelect: function(record){ // override default onSelect to do redirect
					//Ext.get('psinfo').highlight();
					//psinfo.innerHTML=psinfoTpl.applyTemplate(record.data);
					search.collapse();
					search.clearValue();
					panel_psinfo.collapse(true);
					psinfoTpl.overwrite(panel_psinfo.body,record.data);
					setTimeout(function(){panel_psinfo.expand(true);},300);
					digistTpl.overwrite(panel_digist.body, digistdata);
					setTimeout(function(){panel_digist.expand(true);},300);
				}, 
		        listeners : { 

		            'beforequery':function(e){  
		                   
		                var combo = e.combo; 
		                combo.collapse();//����
//		                combo.onLoad();//���ӵ�һ�λ���ʾ������
		                if(!e.forceAll){   
		                	combo.store.clearFilter();
		                    var input = e.query;    
		                    // ����������  
		                    var regExp = new RegExp(".*" + input + ".*");  
		                    // ִ�м���  
		                    combo.store.filterBy(function(record,id){    
		                        // �õ�ÿ��record����Ŀ����ֵ  
		                        var text = record.get(combo.displayField);    
		                        return regExp.test(text);   
		                    });  
		                    
		                    combo.expand();    
		                    return false;  
		                }  
		                if(!combo.getValue()) {
		                    //����ı���ûֵ�����������
		                    combo.store.clearFilter();
		                }
		            }  
		        } 
			});
			
			var searchbar = new Ext.Toolbar({
				items:[
						'<img src="/insiis/img/icon/member.gif" />',
						'��Ա����:',
						search,
						' ',
						'<a href=#>�߼�</a>',
						'->',
						new Ext.Toolbar.Button({
							text:'���',
							handler :function(b, pressed){
								search.clearValue();
								panel_psinfo.collapse(true);
								panel_digist.collapse(true);
							},
					  		icon:'../../basejs/ext/resources/images/default/dd/drop-no.gif',
					  		cls:'x-btn-text-icon'
						}),
						new Ext.Toolbar.Button({
							text:'����',
							icon:'../../basejs/ext/resources/images/default/form/exclamation.gif',
					  		cls:'x-btn-text-icon'
						})						
				],
				renderTo:'searchdiv'
			});
		};
	</script>

</head>
<body>
<odin:base> 
<div id="searchdiv" >
</div>
<div id="psinfodiv">
</div>
<div id="digistdiv" style="margin-top:3px">
</div>
<div id="maincontainer" style="margin-top:3px">
<div id="maindiv">
	<div style="margin-left:5px;margin-right:5px">
	<odin:groupBox title="������Ϣ">
	
	<table  id="myform2" align="center" width="100%" cellpadding="0" cellspacing="0">
		<odin:tabLayOut />
		<tr>
			<odin:select property="AKA130" label="��������"  required="true" editor="true" data="['1', '���ﱨ��'],['2', 'סԺ����'],['3', '�������ⲡ����'],['4', '�������ⲡ����']"/>
			<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
		</tr>
		<tr>
			<odin:dateEdit property="AKC192" label="����ʱ��" required="true" mask="yyyy-mm-dd"/>
			<odin:dateEdit property="AKC194" label="����ʱ��" required="true" mask="yyyy-mm-dd"/>
			<odin:textIconEdit property="AKB020" label="����ҽԺ" required="true"/>
		</tr>
		<tr>
			<odin:textEdit property="AKC190"  required="true" label="�����"/>
			<odin:textIconEdit property="AKA121" label="���Ｒ��" required="true"/>
			<odin:textEdit property="EKC113"  required="true" label="��λ��"/>
		</tr>
		<tr>
			<odin:textEdit property="EKC122" label="��Ժ����" required="false"/>
			<odin:select disabled="true" property="AKC195" editor="true"  data="['1', '������Ժ'],['2', '����'],['3', '����']" label="��Ժԭ��" required="false"/>
			<odin:numberEdit property="FPZS" label="��Ʊ����" required="false"/>
		</tr>
		<tr>
			<odin:textEdit property="EKC124" label="��������" colspan="6" required="false"/>
		</tr>
	</table>
	</odin:groupBox>
	</div>

	<div id="div_property" style="margin-bottom:5px"></div>

</div>
</div>



 <input type="button" value="debug"  onclick="Ext.log('Hello from the Ext console.');return false;">
 <table><tr>
 <odin:select property="sAKC195" editor="true"  data="['1', '������Ժ'],['2', '����'],['3', '����']" label="��Ժԭ��" required="false"/>
 </tr></table> 
</odin:base>
</body>
</html>
