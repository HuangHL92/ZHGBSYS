<%@ tag pageEncoding="GBK" body-content="empty" small-icon="" display-name="��������" description="��������" %>
<%@ attribute name="property" description="����ID" required="true" %>
<%@ attribute name="queryClass" description="�������������õĲ�ѯ����ʵ����" required="true" %>
<%@ attribute name="onSelectedEvent" description="����ѡ��ʱ����" required="false"%>
<%@ attribute name="dataColNames" description="��ѯ���ݵ��ֶΣ�����м��ö��Ÿ���" required="true"%>
<%@ attribute name="showColNames" description="��ʾ���ݵ��ֶΣ�����м��ö��Ÿ���" required="true"%>
<%@ attribute name="width" description="�����Ŀ��ȣ�Ĭ��160" required="false"%>
<%@ attribute name="listWidth" description="��������б��Ŀ��ȣ�Ĭ��200" required="false"%>
<%@ attribute name="emptyText" description="��û�������κ�ֵʱ��Ĭ���ı���ʾ��Ϣ" required="false"%>
<%@ attribute name="pageSize" description="ÿҳ��ʾ������������Ĭ��20��" required="false"%>
<%@ attribute name="onselect" description="��ѡ��ĳ������ʱ������JS����" required="false"%>
<%@ attribute name="displayField" description="ѡ��ʱĬ����ʾ�Ǹ��ֶ�����������" required="false"%>
<%@ attribute name="enterKeyDown" description="�س������´������js�������÷����޲���" required="false"%>
<%@ attribute name="autoJumpNextInput" description="�����»س�ʱ�Ƿ��Զ������¸��ɻ�ý���������Ĭ�����Զ�����Ϊtrue����ֵΪtrue��false" required="false"%>
<%@ attribute name="queryParams" description="��ѯ����Ҫ������������������ﴫ��input��id��name��������ö��Ÿ���" required="false"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<% 
	String isearch_contextpath = request.getContextPath();
	if(width==null || width.equals("")){
		width = "160";
	}
	if(listWidth==null || listWidth.equals("")){
		listWidth = "200";
	}
	if(emptyText == null || emptyText.equals("")){
		emptyText = "";
	}
	if(pageSize == null || pageSize.equals("")){
		pageSize = "20";
	}
	if(autoJumpNextInput==null || autoJumpNextInput.equals("")){
		autoJumpNextInput = "true";
	}
%>
<style>
	.listnormal {
		font-family: "΢���ź�", "����", "����";
		font-size: 14px;
		color:#000
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
<input type="text" size=30 onkeydown="<%=property%>_key()" name="<%=property%>_intelligentSearch_InputBox" id="<%=property%>_intelligentSearch_InputBox" />
<script>
	var <%=property%>_intelligentSearch_searchBox = null;
	var <%=property%>_store_dataModel = [];
	var <%=property%>_dataNames = "<%=dataColNames%>".split(",");
	for(var i=0;i<<%=property%>_dataNames.length;i++){
		<%=property%>_store_dataModel[i] = {name:<%=property%>_dataNames[i]};
	}
	
	var <%=property%>_intelligentSearch_Reader = new Ext.data.JsonReader({root: 'data',totalProperty: 'totalCount',id: 'id'},<%=property%>_store_dataModel);
	
	var <%=property%>_intelligentSearch_DataStore = new Ext.data.Store({url:'<%=isearch_contextpath%>/IntelligentSearchAction.do?method=search&queryClass=<%=queryClass%>&functionid='+MDParam.functionid,reader: <%=property%>_intelligentSearch_Reader});
	<%=property%>_intelligentSearch_DataStore.on('beforeload', function(ds) {
		  var names = "<%=queryParams==null?"":queryParams%>";
		  var ps = names==""?[]:names.split(",");
		  for(var i=0;i<ps.length;i++){
		  	ds.baseParams[ps[i]]=document.getElementById(ps[i]).value;
		  }
    });
	var <%=property%>_showTpl = "";
	var <%=property%>_showNames = "<%=showColNames%>".split(",");
	for(var i=0;i<<%=property%>_showNames.length;i++){
		if(i==0){
			<%=property%>_showTpl = "{"+<%=property%>_showNames[i]+"}";
		}else{
			<%=property%>_showTpl += "��" + "{"+<%=property%>_showNames[i]+"}";
		}
	}
	var <%=property%>_intelligentSearch_Tpl = new Ext.XTemplate(
		'<tpl for="."><div class="search-item">',
		'<p><span class="listnormal">'+<%=property%>_showTpl+'</span></p>',
		'</div></tpl>'
	); 

	function <%=property%>_intelligentSearch_FillDetails(response){
		<%if(onSelectedEvent!=null&&!onSelectedEvent.equals("")){%>
		  radow.doEvent('<%=onSelectedEvent%>',response.data.id);
		<%}%>
		<%if(onselect!=null&&!onselect.equals("")){%>
		  <%=onselect%>(response.data);
		<%}%>
	}

	function <%=property%>_key(e){
		var t = <%=property%>_intelligentSearch_DataStore;
		var o = <%=property%>_intelligentSearch_searchBox;
		var bar = o.pageTb;
		var event = window.event || e;
		if(event.keyCode == 37){
			bar.doLoad(Math.max(0, bar.cursor-bar.pageSize));
			event.returnValue = "";
		}else if(event.keyCode == 39){
			bar.doLoad(bar.cursor+bar.pageSize);
			event.returnValue = "";
		}else if(event.keyCode == 32){ //�س�13���ո�32
			<%=property%>_doQuery();
			event.returnValue = "";
		}
	}
	
	function <%=property%>_doQuery(){
		var o = <%=property%>_intelligentSearch_searchBox;
		var cueValue = document.getElementById('<%=property%>_intelligentSearch_InputBox').value;
		console.log("query"+cueValue+"==="+o.getValue());
		if(cueValue!=""){
			o.doQuery(Ext.util.Format.trim(cueValue),true);
		}
	}

	Ext.onReady(function(){
			<%=property%>_intelligentSearch_searchBox = new Ext.form.ComboBox({
			store:<%=property%>_intelligentSearch_DataStore,
			id:'<%=property%>_intelligentSearch_InputBox',
			typeAhead: false,
			loadingText: '������...',
			hideTrigger: false,
			typeAhead:false,
			emptyText: '<%=emptyText%>',
			width: <%=width%>,
			listWidth: <%=listWidth%>,
			tpl: <%=property%>_intelligentSearch_Tpl,
			minChars:9999,
			queryDelay:1000,   //��ѯ�ӳ�ʱ��
			pageSize:<%=pageSize%>,
			itemSelector: 'div.search-item',
			applyTo: '<%=property%>_intelligentSearch_InputBox',
			triggerClass:'x-form-search-trigger',
			onTriggerClick:function(){
				<%=property%>_doQuery();
			}
		    });
			<%=property%>_intelligentSearch_searchBox.on('focus',function(){odin.comboFocus(<%=property%>_intelligentSearch_searchBox);});
			<%=property%>_intelligentSearch_searchBox.on('select',<%=property%>_onSelect);
			<%=property%>_intelligentSearch_searchBox.on('specialkey',function(the,e){if(e.getKey()==e.ENTER){this.doQuery(this.getValue(), true);}});
			odin.ext.get("<%=property%>_intelligentSearch_InputBox").on("paste",function(e){
				var key = "";
				if(window.clipboardData){
					key = window.clipboardData.getData('Text');
				}else if(e.browserEvent && e.browserEvent.clipboardData){
					key = e.browserEvent.clipboardData.getData("text/plain");
				}else if(e.clipboardData){
					key = e.clipboardData.getData("text/plain");
				}
				<%=property%>_intelligentSearch_searchBox.doQuery(key,true);
			});
	});
	<%=property%>_onSelect = function(combo,record,index){
		var o = <%=property%>_intelligentSearch_searchBox;
		var b = document.getElementById('<%=property%>_intelligentSearch_InputBox');
		var cueValue = b.value;
		console.log("select"+cueValue+"==="+o.getValue());
		<%=(displayField!=null && !displayField.equals(""))?"b.value=record.get('"+displayField+"');":"combo.clearValue();"%>
		console.log("row:"+index+","+odin.encode(record.data));
	};
	
</script> 