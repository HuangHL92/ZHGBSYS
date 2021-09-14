<%@ tag pageEncoding="GBK" body-content="empty" small-icon="" display-name="��������" description="��������" %>
<%@ attribute name="property" description="����ID" required="true" %>
<%@ attribute name="queryClass" description="�������������õĲ�ѯ����ʵ����" required="true" %>
<%@ attribute name="onSelectedEvent" description="����ѡ��ʱ����" required="false"%>
<%@ attribute name="dataColNames" description="��ѯ���ݵ��ֶΣ�����м��ö��Ÿ���" required="true"%>
<%@ attribute name="showColNames" description="��ʾ���ݵ��ֶΣ�����м��ö��Ÿ���" required="true"%>
<%@ attribute name="showColHeads" description="��ʾ���ݵ�ͷ��Ϣ�������Ʊ��ͷ��д���磺����λ���֣�300�����200��" required="true"%>
<%@ attribute name="width" description="�����Ŀ�ȣ�Ĭ��160" required="false"%>
<%@ attribute name="listWidth" description="��������б�Ŀ�ȣ�Ĭ��200" required="false"%>
<%@ attribute name="emptyText" description="��û�������κ�ֵʱ��Ĭ���ı���ʾ��Ϣ" required="false"%>
<%@ attribute name="pageSize" description="ÿҳ��ʾ������������Ĭ��20��" required="false"%>
<%@ attribute name="onselect" description="��ѡ��ĳ������ʱ������JS����" required="false"%>
<%@ attribute name="minChars" description="��������С�����ַ�����Ĭ��Ϊ2��" required="false"%>
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
	if(minChars == null || minChars.equals("")){
		minChars = "2"; 
	}
	if(autoJumpNextInput==null || autoJumpNextInput.equals("")){
		autoJumpNextInput = "true";
	}
%>
<style>
	.search-head{
		padding:3px 10px 3px 10px;
		background-color: #9EE09D;
	}
	.search-head td{
		font-weight: bold;
		font-size:12px;
		color:red;
		/*text-align: center*/
	}
	.listnormal {
		font-family: "΢���ź�", "����", "����";
		color:#000
	}
	.search-item {
	    font:normal 9px tahoma, arial, helvetica, sans-serif;
	    padding:3px 10px 3px 10px;
	    border:1px solid #fff;
	    border-bottom:1px solid #eeeeee;
	    white-space:normal;
	    color:#555;
	}
	.search-item td{
		font-size:12px;
	}
	
</style>
<input type="text" size=30 onkeydown="<%=property%>_key();" name="<%=property%>_intelligentSearch_InputBox" id="<%=property%>_intelligentSearch_InputBox" />
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
	
	var <%=property%>_headCName = [];
	var <%=property%>_colWidth = [];
	function <%=property%>_getHW(){
		var temp = "<%=showColHeads%>".split(",");
		for(var i=0;i<temp.length;i++){
			var info = temp[i].split(":");
			<%=property%>_headCName[i] = info[0];
			<%=property%>_colWidth[i] = info[1];
		}	
	}
	<%=property%>_getHW();
	
	for(var i=0;i<<%=property%>_showNames.length;i++){
		<%=property%>_showTpl += "<td width='"+<%=property%>_colWidth[i]+"px'>{"+<%=property%>_showNames[i]+"}</td>";
	}
	var <%=property%>_head = "";
	for(var i=0;i<<%=property%>_headCName.length;i++){
		<%=property%>_head += "<td width='"+<%=property%>_colWidth[i]+"px'>"+<%=property%>_headCName[i]+"</td>";
	}
	var <%=property%>_intelligentSearch_Tpl = new Ext.XTemplate(
		'<div class="search-head"><table><tr height=24>'+<%=property%>_head+'</tr></table></div>',
		'<tpl for="."><div class="search-item">',
		'<p><table><tr>'+<%=property%>_showTpl+'</tr></table></p>',
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
		if(event.keyCode == 37){
			bar.doLoad(Math.max(0, bar.cursor-bar.pageSize));
			event.returnValue = "";
		}else if(event.keyCode == 39){
			bar.doLoad(bar.cursor+bar.pageSize);
			event.returnValue = "";
		}else if(event.keyCode == 13){
			<%if(enterKeyDown!=null&&!enterKeyDown.equals("")){%>
			  <%=enterKeyDown%>();
			<%}%>
			<%if(autoJumpNextInput.equals("true")){%>
				event.keyCode=9;
			<%}else{%>
				event.returnValue = "";
			<%}%>
		}
	}

	Ext.onReady(function(){
			<%=property%>_intelligentSearch_searchBox = new Ext.form.ComboBox({
			store:<%=property%>_intelligentSearch_DataStore,
			id:'<%=property%>_intelligentSearch_InputBox',
			typeAhead: false,
			loadingText: '������...',
			hideTrigger: true,
			emptyText: '<%=emptyText%>',
			width: <%=width%>,
			listWidth: <%=listWidth%>,
			tpl: <%=property%>_intelligentSearch_Tpl,
			minChars:<%=minChars%>,
			queryDelay:1000,   //��ѯ�ӳ�ʱ��
			pageSize:<%=pageSize%>,
			itemSelector: 'div.search-item',
			applyTo: '<%=property%>_intelligentSearch_InputBox',
			onSelect: function(record){ 
				var id = record.data.id;
				<%=property%>_intelligentSearch_FillDetails(record);
				this.collapse();
				<%=(displayField!=null && !displayField.equals(""))?"this.setValue(record.get('"+displayField+"'));":"this.clearValue();"%>	
			}
			});
			<%=property%>_intelligentSearch_searchBox.on("specialkey",<%=property%>_key);
			odin.ext.get("<%=property%>_intelligentSearch_InputBox").on("paste",function(e){
				var key = "";
				if(window.clipboardData){
					key = window.clipboardData.getData('Text');
				}else{
					key = e.clipboardData.getData("text/plain");
				}
				<%=property%>_intelligentSearch_searchBox.doQuery(key,true);
			});
	});
	
</script>
