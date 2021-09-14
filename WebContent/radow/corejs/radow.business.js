/***
 * **************************
 * ������¶���������޹�˾
 * ����Radow��ܱ�ǩ��ĺ���js�ļ�
 * version:2.0
 * auther:jinwei
 * date:2009-10-21
 * **************************
 * �޸ļ�¼
 * **************************
 * cueversion:2.0.1   �޸���ĳЩ�����ݵ�jsд��(2011.1.7)
 * **************************
 */
odin.ext.namespace('radow.business'); 
radow.business = {
	/**
	*��ѯʹ��
	*/
	doOnChange:function(item){
		var name = item.name;
		commParams = {};
		commParams.currentValue = item.value;	
		// query�Ĵ���
		if (name=="psquery") {// ��psquery��ͷ����Ա��ѯ
			//Ext.get(document.body).mask("���ڲ�ѯ�����Ե�..."); // ������Ӱ
			radow.business.doOpenPupWin("/radowAction.do?method=doEvent&pageModel=pages.comm.PSQuery",
					"��Ա��ѯ--ѡ��ú�˫����س�����ȷ��", 750, 323, commParams.currentValue);
			radow.business.doHiddenPupWin()// ������
			return;
		} else if (name=="cpquery") {// ��cpquery��ͷ�ĵ�λ��ѯ
			//Ext.get(document.body).mask("���ڲ�ѯ�����Ե�..."); // ������Ӱ
			radow.business.doOpenPupWin("/radowAction.do?method=doEvent&pageModel=pages.comm.CPQuery",
					"�壨��������ѯ--ѡ��ú�˫����س�����ȷ��", 650, 323, commParams.currentValue);
			radow.business.doHiddenPupWin()// ������
			return;
		}else if (name=="cpquery_1") {// ��cpquery��ͷ�ĵ�λ��ѯ
			//Ext.get(document.body).mask("���ڲ�ѯ�����Ե�..."); // ������Ӱ
			radow.business.doOpenPupWin("/radowAction.do?method=doEvent&pageModel=pages.comm.CPQuery1",
					"�壨��������ѯ--ѡ��ú�˫����س�����ȷ��", 650, 323, commParams.currentValue);
			radow.business.doHiddenPupWin()// ������
			return;
		}
		
	},
	/**
	 * �򿪵�������
	 */
	doOpenPupWin:function(src, title, width, height, initParams) {
		var windowId = "win_pup";
		var popWindow = Ext.getCmp(windowId);
		expReadyState = ""; // ����ʱ��ʼ��
		popWindow.setTitle(title); // ����
		popWindow.setSize(width, height); // ��� �߶�
		radow.business.showWindowWithSrc(windowId, src, initParams);
		popWindow.center(); // ����
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
	 * �򿪵������ڲ�����
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
function readExcel(paraNum)//paraNum������Ƭ����ʱ�ж��ٸ����ԣ���һ���ж����ֶΣ�
{
	var filePath="";
	filePath=document.getElementById("upfile").value;
	odin.msg="���̴�����....";	
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