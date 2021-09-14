<%@ tag pageEncoding="GBK" body-content="empty" small-icon="" display-name="智能搜索" description="智能搜索" %>
<%@ attribute name="property" description="属性ID" required="true" %>
<%@ attribute name="queryClass" description="智能搜索所调用的查询具体实现类" required="true" %>
<%@ attribute name="onSelectedEvent" description="当被选择时调用" required="false"%>
<%@ attribute name="dataColNames" description="查询数据的字段，多个中间用逗号隔开" required="true"%>
<%@ attribute name="showColNames" description="显示数据的字段，多个中间用逗号隔开" required="true"%>
<%@ attribute name="showColHeads" description="显示数据的头信息，即类似表格头，写法如：‘单位名字：300，类别：200’" required="true"%>
<%@ attribute name="width" description="输入框的宽度，默认160" required="false"%>
<%@ attribute name="listWidth" description="索索结果列表的宽度，默认200" required="false"%>
<%@ attribute name="emptyText" description="当没有输入任何值时的默认文本提示信息" required="false"%>
<%@ attribute name="pageSize" description="每页显示的数据条数，默认20条" required="false"%>
<%@ attribute name="onselect" description="当选中某行数据时触发的JS函数" required="false"%>
<%@ attribute name="minChars" description="搜索的最小输入字符数，默认为2个" required="false"%>
<%@ attribute name="displayField" description="选中时默认显示那个字段在搜索框中" required="false"%>
<%@ attribute name="enterKeyDown" description="回车键按下触发这个js方法，该方法无参数" required="false"%>
<%@ attribute name="autoJumpNextInput" description="当按下回车时是否自动跳到下个可获得焦点的输入框，默认是自动跳即为true。该值为true或false" required="false"%>
<%@ attribute name="queryParams" description="查询所需要的其它额外参数，这里传递input的id或name名，多个用逗号隔开" required="false"%>
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
		font-family: "微软雅黑", "黑体", "宋体";
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
			loadingText: '搜索中...',
			hideTrigger: true,
			emptyText: '<%=emptyText%>',
			width: <%=width%>,
			listWidth: <%=listWidth%>,
			tpl: <%=property%>_intelligentSearch_Tpl,
			minChars:<%=minChars%>,
			queryDelay:1000,   //查询延迟时间
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
