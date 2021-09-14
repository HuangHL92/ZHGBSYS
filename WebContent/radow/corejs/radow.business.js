/***
 * **************************
 * 浙大网新恩普软件有限公司
 * 核三Radow框架标签库的核心js文件
 * version:2.0
 * auther:jinwei
 * date:2009-10-21
 * **************************
 * 修改记录
 * **************************
 * cueversion:2.0.1   修改了某些不兼容的js写法(2011.1.7)
 * **************************
 */
odin.ext.namespace('radow.business'); 
radow.business = {
	/**
	*查询使用
	*/
	doOnChange:function(item){
		var name = item.name;
		commParams = {};
		commParams.currentValue = item.value;	
		// query的处理
		if (name=="psquery") {// 以psquery开头的人员查询
			//Ext.get(document.body).mask("正在查询，请稍等..."); // 加上阴影
			radow.business.doOpenPupWin("/radowAction.do?method=doEvent&pageModel=pages.comm.PSQuery",
					"人员查询--选择好后双击或回车进行确定", 750, 323, commParams.currentValue);
			radow.business.doHiddenPupWin()// 先隐藏
			return;
		} else if (name=="cpquery") {// 以cpquery开头的单位查询
			//Ext.get(document.body).mask("正在查询，请稍等..."); // 加上阴影
			radow.business.doOpenPupWin("/radowAction.do?method=doEvent&pageModel=pages.comm.CPQuery",
					"村（社区）查询--选择好后双击或回车进行确定", 650, 323, commParams.currentValue);
			radow.business.doHiddenPupWin()// 先隐藏
			return;
		}else if (name=="cpquery_1") {// 以cpquery开头的单位查询
			//Ext.get(document.body).mask("正在查询，请稍等..."); // 加上阴影
			radow.business.doOpenPupWin("/radowAction.do?method=doEvent&pageModel=pages.comm.CPQuery1",
					"村（社区）查询--选择好后双击或回车进行确定", 650, 323, commParams.currentValue);
			radow.business.doHiddenPupWin()// 先隐藏
			return;
		}
		
	},
	/**
	 * 打开弹出窗口
	 */
	doOpenPupWin:function(src, title, width, height, initParams) {
		var windowId = "win_pup";
		var popWindow = Ext.getCmp(windowId);
		expReadyState = ""; // 导出时初始化
		popWindow.setTitle(title); // 标题
		popWindow.setSize(width, height); // 宽度 高度
		radow.business.showWindowWithSrc(windowId, src, initParams);
		popWindow.center(); // 居中
	},
	showWindowWithSrc:function(windowId, newSrc, initParams, onreadystatechange) {
		if (newSrc.indexOf("http:") == -1 && newSrc.indexOf("www.") == -1
				&& newSrc.indexOf(contextPath) != 0) {
			newSrc = contextPath + newSrc;
		}
		if (document.getElementById("iframe_" + windowId)) {
			document.getElementById("iframe_" + windowId).src = newSrc;
		} else {
			Ext.getCmp(windowId).html = "<iframe width=\"100%\" height=\"100%\" id=\"iframe_"
					+ windowId
					+ "\" name=\"iframe_"
					+ windowId
					+ (onreadystatechange == null ? "" : "\" onreadystatechange=\""
							+ onreadystatechange) + "\" src=\"" + newSrc + "\">";
		}
		if (initParams) {
			commParams.initParams = initParams;
		}
		Ext.getCmp(windowId).show(Ext.getCmp(windowId));
		Ext.getCmp(windowId).focus();
	},
	/**
	 * 打开弹出窗口并隐藏
	 */
	doHiddenPupWin:function() {
		var windowId = "win_pup";
		var pupWindow = Ext.getCmp(windowId);
		pupWindow.hide();
	},
	cpQueryLoad:function(store){
		radow.business.queryGridLoad(store,'cpquery');
	},
	psQueryLoad:function(store){
		radow.business.queryGridLoad(store,'psquery');
	},
	queryGridLoad:function(store,id){
		var count = store.getCount();
		if(count==1){
			radow.autoFillElementValue(odin.ext.encode(store.getAt(0).data),true);
			radow.business.commQueryNextEvent(id);
		}else if(count==0){
			parent.odin.ext.getCmp('win_pup').hide();
		}else{
			parent.odin.ext.getCmp('win_pup').show(parent.odin.ext.getCmp('win_pup'));
		}
	},
	cpRDbClick:function(grid,rowIndex,event){
		radow.business.queryGridRDbClick(grid,rowIndex,event,'cpquery');
	},
	psRDbClick:function(grid,rowIndex,event){
		radow.business.queryGridRDbClick(grid,rowIndex,event,'psquery');
	},
	queryGridRDbClick:function(grid,rowIndex,event,id){
		var store = grid.store;
		radow.autoFillElementValue(odin.ext.encode(store.getAt(rowIndex).data),true);
		parent.odin.ext.getCmp('win_pup').hide();
		radow.business.commQueryNextEvent(id);
	},
	commQueryNextEvent:function(type){
		var nextEvent = null;
		if(type=='psquery'){
			nextEvent = parent.psquery_nextEvent;
		}else if(type == 'cpquery'){
			if(typeof parent.cpquery_nextEvent == 'undefined'){
				nextEvent = "";
			}else{
				nextEvent = parent.cpquery_nextEvent;
			}
		}
		if(nextEvent!=""){
			parent.radow.doEvent(nextEvent);
		}
	}
};
function readExcel(paraNum)//paraNum代表盘片导入时有多少个属性（即一行有多少字段）
{
	var filePath="";
	filePath=document.getElementById("upfile").value;
	odin.msg="导盘处理中....";	
	if(filePath!=null&&filePath!=""){
		filePath=filePath.replace("\\","\\\\");
		var i;
		i = 1;
	    var oXL = new ActiveXObject("Excel.Application"); 	   
	    var oWB = oXL.Workbooks.open(filePath);	   
	    var oSheet = oWB.ActiveSheet;
		var colcount=oWB.Worksheets(1).UsedRange.Cells.Rows.Count;
		var colcolumn=oWB.Worksheets(1).UsedRange.Columns.Count;	
		var gridData=new Array();
		//alert(paraNum);
		var length=parseInt(paraNum);
		while(i<=colcount)
		{	
		   var obj=new Array(length);
		   for(var j=1;j<=length;j++){ 		   	       	
		     	obj[j-1]=oSheet.Cells(i,j).value;
		   } 
		   gridData[i-1]=obj;
		   i=i+1;
		}
		oWB.close();
        return gridData;
	}
}