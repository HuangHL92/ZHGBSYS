var FIELD_RXLB = {"b0234":"101","b0235":"301","bzqpdw":"1001","bzqpzf":"1004","bzqprd":"1003","bzqpzx":"1005","jcyqp":"1007","fyqp":"1006"
	,"jgmc":"Z01"};



var RXLB_FIELD = {"1":"b0234","3":"b0235","1001":"bzqpdw","1004":"bzqpzf","1003":"bzqprd","1005":"bzqpzx","1007":"jcyqp","1006":"fyqp",
		"101":"b0234","301":"b0235","Z01":"jgmc"};

var B0131DECODE = {"1":"��ְ","3":"��ְ","Z":"����","":"����","null":"����","31":"��ʦ",'1001':'��ί','1004':'����','1003':'�˴�','1005':'��Э','1006':'Ժ��','1007':'��鳤'
	,"101":"��ְ","301":"��ְ","Z01":"����","3101":"��ʦ"};

var JGMC_QXS = {'1001':'ί','1004':'����','1003':'�˴�','1005':'��Э','1006':'��Ժ','1007':'���Ժ'};


//�˶� ʵ�� ȱ��˶�
var QP_HD_SP = {"bzqpdw":["bzdw","bzspdw"],"bzqpzf":["bzzf","bzspzf"], "bzqprd":["bzrd","bzsprd"],"bzqpzx":["bzzx","bzspzx"],"fyqp":["fy","fysp"],"jcyqp":["jcy","jcysp"]
				,"b0234":["zzhd","zzsp"],"b0235":["fzhd","fzsp"]};

var NoticeSetgridScroll = 0;
function setNoticeSetgridScroll(){
	Ext.getCmp('noticeSetgrid').getView().scroller.dom.scrollTop = NoticeSetgridScroll;
}
function setNoticeSetgridScrollParm(){
	NoticeSetgridScroll = Ext.getCmp('noticeSetgrid').getView().scroller.dom.scrollTop;
}


var GridDrop2G = {
    ddGroup : 'pgridBufferDD',
    copy : false,
    init: function(grid,noticeSetgrid) {
        if (grid.rendered) {
            this.grid = grid;
            this.noticeSetgrid = noticeSetgrid;
            this.view = grid.getView();
            this.store = grid.getStore();
            this.pgrid = Ext.getCmp('pgrid');
        } else {
            grid.on('render', this.init, this);
        }
    },
    notifyEnter : function(dd, e, data){
    	delete this.dropOK;
    	var rows = data.selections;
         
        //����store
        for ( i=0; i<rows.length; i++){
            var rowData = rows[i];
            var a0200From = rowData.data.a0200;
            var fxyp07 = rowData.data.fxyp07;
            if(fxyp07=='1'){
            	return this.dropNotAllowed;
            } 
            for ( j=0; j<this.store.getCount(); j++){
                var a0200To = this.store.getAt(j).data.a0200;
                //ͬһ��ְ�� �����ǻ������Ϲ�ȥ����Ա �����ټ�
                if(a0200From==a0200To){//&&this.store.getAt(j).data.personstatus.indexOf("3")>=0
                	this.dropOK=false;
                	return this.dropNotAllowed;
                }
                
                
            }
            
        }
    	
    	this.dropOK=true;
        return this.dropAllowed;
    },
    notifyOver : function(dd, e, data){
    	return this.dropOK ? this.dropAllowed : this.dropNotAllowed;
    },
    notifyDrop : function(dd,e,data){
    	if(this.dropOK){
    		//ѡ���˶�����
            var rows = data.selections;
            //�϶�һ���˵�ʱ���������������ְ��Ҳ�ӽ�ȥ��
            var pgridStore = this.pgrid.getStore();
            var ortherRow = [],ind=0,jnd=0;
            var a0000str = '';
            for ( ind=0; ind<rows.length; ind++){
            	var rowData = rows[ind];
            	a0000str = a0000str+rowData.data.a0000+","
            	/*for ( jnd=0; jnd<pgridStore.getCount(); jnd++){
            		var pgridGridData = pgridStore.getAt(jnd);
                	if(rowData.data.a0200!=pgridGridData.data.a0200&&rowData.data.a0000==pgridGridData.data.a0000){//��ְ��
                		ortherRow.push(pgridGridData);
                	}
            	}*/
            }
            if(a0000str.length>0){
            	a0000str = a0000str.substring(0, a0000str.length - 1);
            	radow.doEvent('queryByNameAndIDS_ZCQ',a0000str);
            }
            //rows = rows.concat(ortherRow);
            
            //�޸�store
            /*for ( ind=0; ind<rows.length; ind++){
                var rowData = rows[ind];
                //if (!this.copy) dstore.remove(rowData);
                this.store.insert(this.store.getCount(), rowData);
                
                //���µ�λְ��
                for(jnd=0;jnd<this.noticeSetgrid.store.getCount();jnd++){
                	var noticeSetgridData = this.noticeSetgrid.store.getAt(jnd);
                	if(rowData.data.a0201b.indexOf(noticeSetgridData.data.b0111)==0&&rowData.data.personstatus.indexOf('3')>=0){//ͬ��λ
                		var updateField = RXLB_FIELD[rowData.data.zrrx];
                		if(!updateField){
                			break;
                		}
                		var fieldNameV = noticeSetgridData.data[updateField];
                		
            			if(fieldNameV&&fieldNameV!=''&&fieldNameV!=null){
            				fieldNameV++;
            			}else{
            				fieldNameV = 1
            			}
            			
            			noticeSetgridData.set(updateField, fieldNameV);
            			//ԭֵ
            			//noticeSetgridData.modified[updateField];
            			var colIdx = this.noticeSetgrid.getColumnModel().findColumnIndex(updateField);
            			var a = this.noticeSetgrid.getView().getCell(jnd,colIdx);
            			Ext.fly(a).addClass('mdf');
            			break;
                	}
                }
                
            }*/

            this.grid.view.refresh();
            if(typeof callback=='function'){
                callback(pgrid);
            }
    	}
        

    }
}

function updateZS(rows){
	var noticeSetgrid = Ext.getCmp('noticeSetgrid');
	//�޸�store
    for ( ind=0; ind<rows.length; ind++){
        var rowData = rows[ind];
        //if (!this.copy) dstore.remove(rowData);
        
        //���µ�λְ��
        for(jnd=0;jnd<noticeSetgrid.store.getCount();jnd++){
        	var noticeSetgridData = noticeSetgrid.store.getAt(jnd);
        	if(rowData.data.a0201b.indexOf(noticeSetgridData.data.b0111)==0&&rowData.data.personstatus.indexOf('3')>=0){//ͬ��λ
        		var updateField = RXLB_FIELD[rowData.data.zrrx];
        		if(updateField=='jgmc'){
        			continue;
        		}
        		if(!updateField){
        			break;
        		}
        		var fieldNameV = noticeSetgridData.data[updateField];
        		
    			if(fieldNameV&&fieldNameV!=''&&fieldNameV!=null){
    				fieldNameV++;
    			}else{
    				fieldNameV = 1
    			}
    			
    			noticeSetgridData.set(updateField, fieldNameV);
    			//ԭֵ
    			//noticeSetgridData.modified[updateField];
    			var colIdx = noticeSetgrid.getColumnModel().findColumnIndex(updateField);
    			var a = noticeSetgrid.getView().getCell(jnd,colIdx);
    			Ext.fly(a).addClass('mdf');
    			break;
        	}
        }
        
    }
}


/*!
 * Ext JS Library 3.0.0
 * Copyright(c) 2006-2009 Ext JS, LLC
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
// A DropZone which cooperates with DragZones whose dragData contains
// a "field" property representing a form Field. Fields may be dropped onto
// grid data cells containing a matching data type.

var GridDrop = {
    ddGroup : 'noticeSetgridDD',
    copy : true,
    init: function(grid) {
        if (grid.rendered) {
            this.grid = grid;
            this.view = grid.getView();
            this.store = grid.getStore();
        } else {
            grid.on('render', this.init, this);
        }
    },
    /**
     * Returns a custom data object associated with the DOM node that is the target of the event.  By default
     * this looks up the event target in the {@link Ext.dd.Registry}, although you can override this method to
     * provide your own custom lookup.
     * @param {Event} e The event
     * @return {Object} data The custom data
     */
    getTargetFromEvent: function(e) {
//      Ascertain whether the mousemove is within a grid cell
        var t = e.getTarget(this.view.cellSelector);
        
        if (t) {

//          We *are* within a grid cell, so ask the View exactly which one,
//          Extract data from the Model to create a target object for
//          processing in subsequent onNodeXXXX methods. Note that the target does
//          not have to be a DOM element. It can be whatever the noNodeXXX methods are
//          programmed to expect.
            var rowIndex = this.view.findRowIndex(t);
            var columnIndex = this.view.findCellIndex(t);
            if ((rowIndex !== false) && (columnIndex !== false)) {
                return {
                    node: t,
                    record: this.store.getAt(rowIndex),
                    fieldName: this.grid.getColumnModel().getDataIndex(columnIndex)
                }
            }
        }
    },

    onNodeEnter: function(target, dd, e, dragData) {
        delete this.dropOK;
        if (!target) {
            return;
        }

//      Check that a field is being dragged.
        var f = dragData.selections;
        if (f.length==0) {
            return;
        }

//      Check whether the data type of the column being dropped on accepts the
//      dragged field type. If so, set dropOK flag, and highlight the target node.
        //var type = target.record.fields.get(target.fieldName).type;
        /*switch (type) {
            case 'float':
            case 'int':
                if (!f.isXType('numberfield')) {
                    return;
                }
                break;
            case 'date':
                if (!f.isXType('datefield')) {
                    return;
                }
                break;
            case 'boolean':
                if (!f.isXType('checkbox')) {
                    return;
                }
        }*/
        this.dropOK = true;
        Ext.fly(target.node).addClass('x-drop-target-active');
    },

//  Return the class name to add to the drag proxy. This provides a visual indication
//  of drop allowed or not allowed.
    onNodeOver: function(target, dd, e, dragData) {
        return this.dropOK ? this.dropAllowed : this.dropNotAllowed;
    },

//   nhighlight the target node.
    onNodeOut: function(target, dd, e, dragData) {
        Ext.fly(target.node).removeClass('x-drop-target-active');
    },

//  Process the drop event if we have previously ascertained that a drop is OK.
    onNodeDrop: function(target, dd, e, dragData) {
        if (this.dropOK) {
            //target.record.set(target.fieldName, dragData.field.getValue());
            return true;
        }
    },

    /**
     * Called while the DropZone determines that a {@link Ext.dd.DragSource} is being dragged over it,
     * but not over any of its registered drop nodes.  The default implementation returns this.dropNotAllowed, so
     * it should be overridden to provide the proper feedback if necessary.
     * @param {Ext.dd.DragSource} source The drag source that was dragged over this drop zone
     * @param {Event} e The event
     * @param {Object} data An object containing arbitrary data supplied by the drag source
     * @return {String} status The CSS class that communicates the drop status back to the source so that the
     * underlying {@link Ext.dd.StatusProxy} can be updated
     */
    onContainerOver : function(dd, e, data){
        return this.dropNotAllowed;
    },

    /**
     * Called when the DropZone determines that a {@link Ext.dd.DragSource} has been dropped on it,
     * but not on any of its registered drop nodes.  The default implementation returns false, so it should be
     * overridden to provide the appropriate processing of the drop event if you need the drop zone itself to
     * be able to accept drops.  It should return true when valid so that the drag source's repair action does not run.
     * @param {Ext.dd.DragSource} source The drag source that was dragged over this drop zone
     * @param {Event} e The event
     * @param {Object} data An object containing arbitrary data supplied by the drag source
     * @return {Boolean} True if the drop was valid, else false
     */
    onContainerDrop : function(dd, e, data){
        return false;
    },

    /**
     * The function a {@link Ext.dd.DragSource} calls once to notify this drop zone that the source is now over
     * the zone.  The default implementation returns this.dropNotAllowed and expects that only registered drop
     * nodes can process drag drop operations, so if you need the drop zone itself to be able to process drops
     * you should override this method and provide a custom implementation.
     * @param {Ext.dd.DragSource} source The drag source that was dragged over this drop zone
     * @param {Event} e The event
     * @param {Object} data An object containing arbitrary data supplied by the drag source
     * @return {String} status The CSS class that communicates the drop status back to the source so that the
     * underlying {@link Ext.dd.StatusProxy} can be updated
     */
    notifyEnter : function(dd, e, data){
        return this.dropNotAllowed;
    },

    /**
     * The function a {@link Ext.dd.DragSource} calls continuously while it is being dragged over the drop zone.
     * This method will be called on every mouse movement while the drag source is over the drop zone.
     * It will call {@link #onNodeOver} while the drag source is over a registered node, and will also automatically
     * delegate to the appropriate node-specific methods as necessary when the drag source enters and exits
     * registered nodes ({@link #onNodeEnter}, {@link #onNodeOut}). If the drag source is not currently over a
     * registered node, it will call {@link #onContainerOver}.
     * @param {Ext.dd.DragSource} source The drag source that was dragged over this drop zone
     * @param {Event} e The event
     * @param {Object} data An object containing arbitrary data supplied by the drag source
     * @return {String} status The CSS class that communicates the drop status back to the source so that the
     * underlying {@link Ext.dd.StatusProxy} can be updated
     */
    notifyOver : function(dd, e, data){
        var n = this.getTargetFromEvent(e);
        if(!n){ // not over valid drop target
            if(this.lastOverNode){
                this.onNodeOut(this.lastOverNode, dd, e, data);
                this.lastOverNode = null;
            }
            return this.onContainerOver(dd, e, data);
        }
        if(this.lastOverNode != n){
            if(this.lastOverNode){
                this.onNodeOut(this.lastOverNode, dd, e, data);
            }
            this.onNodeEnter(n, dd, e, data);
            this.lastOverNode = n;
        }
        return this.onNodeOver(n, dd, e, data);
    },

    /**
     * The function a {@link Ext.dd.DragSource} calls once to notify this drop zone that the source has been dragged
     * out of the zone without dropping.  If the drag source is currently over a registered node, the notification
     * will be delegated to {@link #onNodeOut} for node-specific handling, otherwise it will be ignored.
     * @param {Ext.dd.DragSource} source The drag source that was dragged over this drop target
     * @param {Event} e The event
     * @param {Object} data An object containing arbitrary data supplied by the drag zone
     */
    notifyOut : function(dd, e, data){
        if(this.lastOverNode){
            this.onNodeOut(this.lastOverNode, dd, e, data);
            this.lastOverNode = null;
        }
    },

    /**
     * The function a {@link Ext.dd.DragSource} calls once to notify this drop zone that the dragged item has
     * been dropped on it.  The drag zone will look up the target node based on the event passed in, and if there
     * is a node registered for that event, it will delegate to {@link #onNodeDrop} for node-specific handling,
     * otherwise it will call {@link #onContainerDrop}.
     * @param {Ext.dd.DragSource} source The drag source that was dragged over this drop zone
     * @param {Event} e The event
     * @param {Object} data An object containing arbitrary data supplied by the drag source
     * @return {Boolean} True if the drop was valid, else false
     */
    notifyDrop : function(dd, e, data){
        if(this.lastOverNode){
            this.onNodeOut(this.lastOverNode, dd, e, data);
            this.lastOverNode = null;
        }
        var n = this.getTargetFromEvent(e);
        return n ?
            this.onNodeDrop(n, dd, e, data) :
            this.onContainerDrop(dd, e, data);
    },

    // private
    triggerCacheRefresh : function(){
        Ext.dd.DDM.refreshCache(this.groups);
    }  

}


Ext.apply(GridDrop,{
	onNodeEnter: function(target, dd, e, dragData) {
        delete this.dropOK;
        if (!target) {
            return;
        }

//      Check that a field is being dragged.
        var f = dragData.selections;
        if (f.length==0) {
            return;
        }

//      Check whether the data type of the column being dropped on accepts the
//      dragged field type. If so, set dropOK flag, and highlight the target node.
        var fieldName = target.fieldName;
        //var type = target.record.fields.get(target.fieldName).type;
        if(fieldName=='b0234'||fieldName=='b0235'||fieldName=='bzqpdw'
        	||fieldName=='bzqpzf'||fieldName=='bzqprd'||fieldName=='bzqpzx'
        		||fieldName=='jcyqp'||fieldName=='fyqp'||fieldName=='yxgw'||fieldName=='jgmc'){
        	//�Լ��ĸ�λ�������
            /*for ( iond=0; iond<f.length; iond++){
                var rowData = f[iond];
                if(rowData.data.a0201b.indexOf(target.record.data.b0111)==0&&fieldName==RXLB_FIELD[rowData.data.zrrx]){//�Լ��ĵ�λ ��ְ��
                	return;
                }

            }*/
        	
        	if(fieldName=='jgmc'){
        		if(target.record.data.b0111.length==15&&target.record.data.b0111.substring(0,11)=='001.001.004'){
        			return;
        		}
        	}
        	
        	this.dropOK = true;
            Ext.fly(target.node).addClass('x-drop-target-active');
            //console.log(target.node)
        }else{
        	return;
        }
    },
    onNodeDrop: function(target, dd, e, dragData) {
    	var DataIndex = target.fieldName;
    	var record = target.record;
    	if(dragData.selections.length!=1){
    		$h.alert("","ֻ��ѡ��һ�У�");
    		return;
    	}
    	setPageDragData(target, dd, e, dragData);
    	
        if (this.dropOK&&target.fieldName!="yxgw") {
        	
        	//ְ���ͳ�Ա����л�
        	if(DataIndex=='jgmc'){
        		$(".cylb").hide();
        		$(".zwzj").show();
        	}else{
        		$(".cylb").show();
        		if($('#mntp05').val()=='2'){
        			$(".zwzj").show();
        		}else{
        			$(".zwzj").hide();
        		}
        		
        		
        	}
        	
            //target.record.set(target.fieldName, dragData.field.getValue());
        	ygrx = record.data[DataIndex+"_ygrx"];
        	FXYP06 = FIELD_RXLB[DataIndex];
        	var b0236 = (record.data.b0236||"").replace(/([0-9]\u540d)/g,"");
        	b0236 = b0236.replace(/\r/gi,"").replace(/(\n)|(<\/br>)/gi,function(t){
        		return t+(record.data.jgmc||"");
        	});
        	
        	var dwmckqgw = record.data.jgmc;//(record.data.jgmc||"")+b0236;
        	if($('#mntp05').val()=='2'){
        		
        		dwmckqgw = dwmckqgw + JGMC_QXS[FIELD_RXLB[DataIndex]];
        	}
        	//dwmckqgw = dwmckqgw.replace(/\r/gi,"").replace(/\n/gi,"\n").replace(/<\/br>/gi,"\n");
        	//Ŀ�����id
        	openGW(FXYP06,record.data.b01id,dwmckqgw,target, dd, e, dragData);
        	return true;
        }
    }
});

function a0501bChange(rc){
	if($("#dwmckqgw").val()==''){
		$("#dwmckqgw").val(rc.data.value);
		Ext.getCmp("a0192aMN").setValue(Ext.getCmp("a0192aMN").getValue()+rc.data.value);
	}
}

function openGW(FXYP06,b01id,dwmckqgw,target, dd, e, dragData){
	refreshPageData.target=target;
	refreshPageData.dd=dd;
	refreshPageData.e=e;
	refreshPageData.dragData=dragData;
	refreshPageData.FXYP06=FXYP06;
	
	$('.ygwa02').show();
	$('.nmxx').show();
	$('.nrdwb01').hide();
	
	
	$("#dwmckqgw").val('');
	//alert(dwmckqgw)
	
	Ext.getCmp("a0192aMN").setValue(dwmckqgw);
	$("#fxyp06").val(FXYP06);
	$("#b01idkq").val(b01id);
	//�ոڵ�λ
	odin.setSelectValue('dwb01','');
	//ԭ��λ
	odin.setSelectValue('gwa02','');
	//���θ�λ
	$('.NRinfo').html('');
	if(FXYP06=='101'){
		odin.setSelectValue('a0201e','1');
	}else if(FXYP06=='301'){
		odin.setSelectValue('a0201e','3');
	}else{
		odin.setSelectValue('a0201e','');
	}
	
	//����������
	//setGWdis($("#yxgwSel")[0]);
	//���� ��ѡ��λ��Ϣ  ���ÿ����ְ��
	radow.doEvent("setGWInfo");
	/*Ext.MessageBox.buttonText.ok='ȷ��';
	Ext.MessageBox.prompt("�������λ���ƣ�",'',function(bu,txt){    
		 if(bu=="ok"&&txt!=''){
			 txt = txt.replace(/\r\n|\r|\n/g,"")
			 refreshPageData.txt=txt;
			 var addInfo = txt+"@@"+FXYP06+"@@"+b01id+"@@"+$('#mntp00').val()
			 radow.doEvent("addInfo",addInfo);
			 
			 radow.doEvent('movePB_by_nm');
		 }
		 Ext.MessageBox.buttonText.ok='ȷ��';   
	},this,true,dwmckqgw); */

	var win = Ext.getCmp("selectGW");	
	if(win){
		win.show();	
		return;
	}
	win = new Ext.Window({
		title : 'ѡ���λ',
		layout : 'fit',
		width : 750,
		height : 550,
		closeAction : 'hide',
		closable : true,
		modal : true,
		id : 'selectGW',
		collapsed:false,
		collapsible:false,
		bodyStyle : 'background-color:#FFFFFF',
		plain : true,
		titleCollapse:false,
		contentEl:"gwInfo",
		listeners:{}
		           
	});
	win.show();
}
//�½���λ
function openXJGW(FXYP06,b01id,dwmckqgw,target, dd, e, dragData){
	/*refreshPageData.target=target;
	refreshPageData.dd=dd;
	refreshPageData.e=e;
	refreshPageData.dragData=dragData;
	refreshPageData.FXYP06=FXYP06;*/
	
	$("#a0000s").val('');
	
	$('.ygwa02').hide();
	//ԭ��λ
	odin.setSelectValue('gwa02','');
	//�ոڵ�λ
	odin.setSelectValue('dwb01','');
	
	//��ǰ������λ��ְ��
	$('.nmxx').hide();
	//��ǰ������λ��ְ��
	$("#dqgzdwjzw").val('');
	
	//���ε�λ
	$('.nrdwb01').show();
	//ְ��
	$(".zwzj").show();
	//ְ��
	odin.setSelectValue('a0501b','');
	
	//������ְ������
	$('.NMinfo').html('');
	//����ְ������
	$("#nmzwqc").val('');
	//��ǰ���� ְ��ȫ��id
	$("#dqnmzwqcid").val('');
	
	//����Ա�������θ�λ
	$('.dqnrzw').html("�ޣ�");
	//��ǰ���� ְ��ȫ��id
	$("#dqnrzwqcid").val('');
	
	$('.XingMing').html("�ո�������");
	
	
	$("#dwmckqgw").val('');
	//alert(dwmckqgw)
	
	Ext.getCmp("a0192aMN").setValue(dwmckqgw);
	
	//�õ�λ�������θ�λ
	//odin.setSelectValue('yxgwSel','');
	
	//���θ�λ
	$('.NRinfo').html('');
	odin.setSelectValue('a0201e','1');
	
	//����������
	//setGWdis($("#yxgwSel")[0]);
	//���� ��ѡ��λ��Ϣ  ���ÿ����ְ��
	//radow.doEvent("setGWInfo");

	var win = Ext.getCmp("selectGW");	
	if(win){
		win.show();	
		return;
	}
	win = new Ext.Window({
		title : 'ѡ���λ',
		layout : 'fit',
		width : 750,
		height : 550,
		closeAction : 'hide',
		closable : true,
		modal : true,
		id : 'selectGW',
		collapsed:false,
		collapsible:false,
		bodyStyle : 'background-color:#FFFFFF',
		plain : true,
		titleCollapse:false,
		contentEl:"gwInfo",
		listeners:{}
		           
	});
	win.show();
}
//�����λ��Ϣ
function saveGWInfo(){
	
	//�������ص�ʱ��  �����жϸ�ֵ
	if($("#fxyp06").val().length!=4){
		var a0201e = $("#a0201e").val();
		if(a0201e=='1'){
			$("#fxyp06").val('101');
		}else if(a0201e=='3'){
			$("#fxyp06").val('301');
		}else{
			$("#fxyp06").val('Z01');
			
		}
	}
	
	//����
	var a0200NRs = "";
	$(".NRinfo input[name='NRZW']:checked").each(function (i, item) {
		var $item = $(item);
		var a0200 = $item.val();
		var fxyp07 = $item.attr('fxyp07');
		var zwqc00 = $item.attr('zwqc00');
		a0200NRs += a0200+":"+(fxyp07||'')+":"+(zwqc00||'')+":x,";
		
	});
	if(a0200NRs!=""){
		a0200NRs = a0200NRs.substring(0, a0200NRs.length - 1);
		
	}
		//$("#a0200NRs").val(a0200NRs);
	radow.doEvent("addInfo",a0200NRs);
	
	
	//����
	if($('#mxz').is(':checked')) {
		var a0200s = "";
		$(".NMinfo input[name='NMZW']:checked").each(function (i, item) {
			var $item = $(item);
			var personstatus = $item.attr('pt');
			var a0200 = $item.val();
			var zrrx = $item.attr('zrrx');
			var b01id = $item.attr('b01id');
			if(personstatus.indexOf("3")>=0){
	        	a0200s += a0200+":"+(zrrx||'')+":"+b01id+":"+personstatus+",";
	        }
			
		});
		if(a0200s!=""){
			a0200s = a0200s.substring(0, a0200s.length - 1);
			$("#a0200s").val(a0200s);
			radow.doEvent('movePB_by_nm');
		}
		
	}
	
	
	
	Ext.getCmp("selectGW").hide();
	
}



//����ո�λ��Ϣ  �ɼ������������ְ
function saveGWInfoNoClose(){
	
	//�������ص�ʱ��  �����жϸ�ֵ
	if($("#fxyp06").val().length!=4){
		var a0201e = $("#a0201e").val();
		if(a0201e=='1'){
			$("#fxyp06").val('101');
		}else if(a0201e=='3'){
			$("#fxyp06").val('301');
		}else{
			$("#fxyp06").val('Z01');
			
		}
	}
	
	//����
	var a0200NRs = "";
	
		//$("#a0200NRs").val(a0200NRs);
	radow.doEvent("addInfo",a0200NRs);
	
	
	
	
}








function openETCWin(){
	
	
	
	var win = Ext.getCmp("addetc");	
	if(win){
		win.show();	
		return;
	}
	win = new Ext.Window({
		title : '��Ա��������Ϣά��',
		layout : 'fit',
		width : 550,
		height : 321,
		closeAction : 'hide',
		closable : true,
		modal : true,
		id : 'addetc',
		collapsed:false,
		collapsible:false,
		bodyStyle : 'background-color:#FFFFFF',
		plain : true,
		titleCollapse:false,
		contentEl:"mntpbzInfo",
		listeners:{}
		           
	});
	win.show();
}
//����ģ�������Ա��ע��Ϣ
function saveMntpbzInfo(){
	var paramData = "";
	$(".zwwh input[name='gwmx']").each(function (i, item) {
		
		var fxyp02 = $(item).val();
		var fxyp00 = $(item).attr('fieldv');
		
		paramData = paramData +(fxyp00+";_;"+fxyp02+";_;x")+"@@";
	});
	$("#gwmxInfo").val(paramData);
	
	
	radow.doEvent("addETCInfo");
	Ext.getCmp("addetc").hide();
	
}




//���Ӹ�λ��ˢ��ְ����  ˢ����Ա��m.fxyp02||'@@'||m.fxyp06||'@@'||m.fxyp00
var refreshPageData = (function(){
	return {
		refresh : function (fxyp00,offset){
			 //ѡ���˶�����
			if(!this.dragData){
				//ˢ��pgrid
			    radow.doEvent('pgrid.dogridquery');
			    radow.doEvent('pgrid2.dogridquery');
			    setNoticeSetgridScrollParm();
			    radow.doEvent('noticeSetgrid.dogridquery');
				return;
			}
		    var rows = this.dragData.selections;
		    
		    /*for ( idd=0; idd<rows.length; idd++){
		        var rowData = rows[idd];
		        //�Ƴ� pgridBuffer
		        this.dragData.grid.store.remove(rowData);
		    }*/
		    //�޸��Ѿ�����״̬
		    var bufferStore = this.dragData.grid.store;
		    var a0200json = {};
		    $(".NMinfo input[name='NMZW']:checked").each(function (i, item) {
				
				var a0200 = $(item).val();
				a0200json[a0200] = true;
				
			});
		    for ( idd=0; idd<bufferStore.getCount(); idd++){
		    	var rowData = bufferStore.getAt(idd);
		    	if(a0200json[rowData.data.a0200]){
		    		rowData.set("personstatus", "2");
		    	}
		    }
		    
		    
			//ˢ��pgrid
		    radow.doEvent('pgrid.dogridquery');
		    radow.doEvent('pgrid2.dogridquery');
		    setNoticeSetgridScrollParm();
		    radow.doEvent('noticeSetgrid.dogridquery');
			
			
			/*var newData = this.txt+"@@" + this.FXYP06 +"@@" + fxyp00;
			var yxgw = this.target.record.data.yxgw;
			if(yxgw!=''&&yxgw!=null){
				yxgw+="{RN}"
			}else{
				yxgw = "";
			}
			yxgw+=newData;
			//��λ����
			this.target.record.set("yxgw", yxgw);
			//ְ������  ����
			var fieldName = this.target.fieldName+"_ygrx";
			var fieldNameV = this.target.record.data[fieldName];
			if(fieldNameV!=''&&fieldNameV!=null){
				fieldNameV = parseInt(fieldNameV) + this.dragData.selections.length;
			}else{
				fieldNameV = this.dragData.selections.length;
			}
			this.target.record.set(this.target.fieldName+"_ygrx", fieldNameV);
			//��λ
			fieldName = this.target.fieldName;
			fieldNameV = this.target.record.data[fieldName];
			if(fieldNameV!=''&&fieldNameV!=null){
				fieldNameV = parseInt(fieldNameV) + offset;
			}else{
				fieldNameV = offset;
			}
			this.target.record.set(this.target.fieldName, fieldNameV);
			
			
			refreshCellColor()*/
		}
	}
})()


function setPageDragData(target, dd, e, dragData){
	 //ѡ���˶�����
    var rows = dragData.selections;
    
    var a0000s="",a0200s="";
    for ( i=0; i<rows.length; i++){
        var rowData = rows[i];
        $(".dqgzdwjzw").html(rowData.data.a0192a);
        $(".XingMing").html("������"+rowData.data.a0101);
        
        //ѡ�е���Աid��Ϊ����   ְ��id��Ϊ����
        a0000s += rowData.data.a0000+",";
        /*if(rowData.data.personstatus.indexOf("3")>=0){
        	a0200s += rowData.data.a0200+":"+(rowData.data.zrrx||'')+":"+rowData.data.b01id+":"+rowData.data.personstatus+",";
        }*/
    }
    if(a0000s.length>0){
    	a0000s = a0000s.substring(0, a0000s.length - 1);
    }
    
    //a0200s = a0200s.substring(0, a0200s.length - 1);
	$("#a0000s").val(a0000s);
	//$("#a0200s").val(a0200s);
}




//decode(b0131,'1001','1��ί','1004','2����','1003','3�˴�','1005','4��Э','1006','5Ժ��','1007','6��鳤')
function decodeZRRY(value, params, record, rowIndex, colIndex, ds) {
	
	//return value;
	return B0131DECODE[value];
}


function decodeGW(value, params, record, rowIndex, colIndex, ds) {
	
	var zwqc01 = record.data.zwqc01;
	
	if(zwqc01&&parseInt(zwqc01)>1){
		return value + "("+zwqc01+")";
	}
	return value;
}


function decodeBZ(value, params, record, rowIndex, colIndex, ds) {
	var b01idbz = record.data.b01idbz;
	var b0101bz = record.data.b0101bz;
	//return value;
	if(value=='���н���'){
		return value+"<br/><a style='cursor:pointer;'><font color=blue>"+b0101bz+ "</a></font>";
	}else{
		return value;
	}
	
}



function refreshNoticeSetgrid(x,store){
	var noticeSetgrid = Ext.getCmp('noticeSetgrid');
	var pgridBuffer = Ext.getCmp('pgridBuffer');
	for ( ind=0; ind<pgridBuffer.store.getCount(); ind++){
		var rowData = pgridBuffer.store.getAt(ind);
        //���µ�λְ��
        for(jnd=0;jnd<noticeSetgrid.store.getCount();jnd++){
        	var noticeSetgridData = noticeSetgrid.store.getAt(jnd);
        	if(rowData.data.a0201b.indexOf(noticeSetgridData.data.b0111)==0&&rowData.data.personstatus.indexOf("3")>=0){//ͬ��λ
        		var updateField = RXLB_FIELD[rowData.data.zrrx];
        		if(updateField=='jgmc'){
        			continue;
        		}
        		var fieldNameV = noticeSetgridData.data[updateField];
    			if(fieldNameV&&fieldNameV!=''&&fieldNameV!=null){
    				fieldNameV++;
    			}else{
    				fieldNameV = 1
    			}
    			
    			noticeSetgridData.set(updateField, fieldNameV);
    			//ԭֵ
    			noticeSetgridData.modified[updateField];
    			break;
        	}
        }
    }
	
	
	refreshCellColor();
	
}

function refreshCellColor(){
	var noticeSetgrid = Ext.getCmp('noticeSetgrid');
	//�к�
	var noticeSetgridColumnModel = noticeSetgrid.getColumnModel();
	var noticeSetgridView = noticeSetgrid.getView();
	var fyqp_ColIdx = noticeSetgridColumnModel.findColumnIndex("fyqp");
	var jcyqp_ColIdx = noticeSetgridColumnModel.findColumnIndex("jcyqp");
	var bzqpdw_ColIdx = noticeSetgridColumnModel.findColumnIndex("bzqpdw");
	var bzqpzf_ColIdx = noticeSetgridColumnModel.findColumnIndex("bzqpzf");
	var bzqprd_ColIdx = noticeSetgridColumnModel.findColumnIndex("bzqprd");
	var bzqpzx_ColIdx = noticeSetgridColumnModel.findColumnIndex("bzqpzx");
	var b0234_ColIdx = noticeSetgridColumnModel.findColumnIndex("b0234");
	var b0235_ColIdx = noticeSetgridColumnModel.findColumnIndex("b0235"); 
	var jgmc_ColIdx = noticeSetgridColumnModel.findColumnIndex("jgmc"); 
	
	
	for ( rowIndex=0; rowIndex<noticeSetgrid.store.getCount(); rowIndex++){
		var record = noticeSetgrid.store.getAt(rowIndex);
		var fyqp_ygrx = record.data.fyqp_ygrx||0;
		var jcyqp_ygrx = record.data.jcyqp_ygrx||0;
		var bzqpdw_ygrx = record.data.bzqpdw_ygrx||0;
		var bzqpzf_ygrx = record.data.bzqpzf_ygrx||0;
		var bzqprd_ygrx = record.data.bzqprd_ygrx||0;
		var bzqpzx_ygrx = record.data.bzqpzx_ygrx||0;
		var b0234_ygrx = record.data.b0234_ygrx||0;
		var b0235_ygrx = record.data.b0235_ygrx||0;
		var zjqp_ygrx = record.data.zjqp_ygrx||0;
        //������ɫ
		var a;
		if(zjqp_ygrx>0){
			a = noticeSetgridView.getCell(rowIndex,jgmc_ColIdx);
			Ext.fly(a).addClass('x-grid-record-ytp');
		}
		if(fyqp_ygrx>0){
			a = noticeSetgridView.getCell(rowIndex,fyqp_ColIdx);
			Ext.fly(a).addClass('x-grid-record-ytp');
		}
		if(jcyqp_ygrx>0){
			a = noticeSetgridView.getCell(rowIndex,jcyqp_ColIdx);
			Ext.fly(a).addClass('x-grid-record-ytp');
		}
		if(bzqpdw_ygrx>0){
			a = noticeSetgridView.getCell(rowIndex,bzqpdw_ColIdx);
			Ext.fly(a).addClass('x-grid-record-ytp');
		}
		if(bzqpzf_ygrx>0){
			a = noticeSetgridView.getCell(rowIndex,bzqpzf_ColIdx);
			Ext.fly(a).addClass('x-grid-record-ytp');
		}
		if(bzqprd_ygrx>0){
			a = noticeSetgridView.getCell(rowIndex,bzqprd_ColIdx);
			Ext.fly(a).addClass('x-grid-record-ytp');
		}
		if(bzqpzx_ygrx>0){
			a = noticeSetgridView.getCell(rowIndex,bzqpzx_ColIdx);
			Ext.fly(a).addClass('x-grid-record-ytp');
		}
		if(b0234_ygrx>0){
			a = noticeSetgridView.getCell(rowIndex,b0234_ColIdx);
			Ext.fly(a).addClass('x-grid-record-ytp');
		}
		if(b0235_ygrx>0){
			a = noticeSetgridView.getCell(rowIndex,b0235_ColIdx);
			Ext.fly(a).addClass('x-grid-record-ytp');
		}
		
    }
}


function refreshNoticeSetgrid_movePB(rcid){
	var noticeSetgrid = Ext.getCmp('noticeSetgrid');
	var pgridBuffer = Ext.getCmp('pgridBuffer');
		var rowData = pgridBuffer.store.getById(rcid);
		pgridBuffer.store.remove(rowData);
		radow.doEvent('DeletePgridBuffer',rowData.data.a0000);
	//���µ�λְ��
		for(jnd=0;jnd<noticeSetgrid.store.getCount();jnd++){
			var noticeSetgridData = noticeSetgrid.store.getAt(jnd);
			if(rowData.data.a0201b.indexOf(noticeSetgridData.data.b0111)==0&&rowData.data.personstatus.indexOf("3")>=0){//ͬ��λ
				var updateField = RXLB_FIELD[rowData.data.zrrx];
				if(updateField=='jgmc'){
        			continue;
        		}
				var fieldNameV = noticeSetgridData.data[updateField];
				if(fieldNameV&&fieldNameV!=''&&fieldNameV!=null){
					fieldNameV--;
				}else{
					fieldNameV = -1
				}
				
				noticeSetgridData.set(updateField, fieldNameV);
				//ԭֵ
				//noticeSetgridData.modified[updateField];
				var colIdx = noticeSetgrid.getColumnModel().findColumnIndex(updateField);
				var a = noticeSetgrid.getView().getCell(jnd,colIdx);
				Ext.fly(a).addClass('mdf');
				break;
			}
		}
	
	
}
//�Ƴ��Ѿ����ε�ְ��
function refreshNoticeSetgrid_moveP(rcid,fxyp07,gridID){
	var pgrid = Ext.getCmp(gridID);
	var rowData = pgrid.store.getById(rcid);
	
	radow.doEvent('DeleteP',(rowData.data.tp0100||'')+"@@"+rowData.data.zwqc00+"@@x");
}
//��λ����
//ȡ������Ա�����θ�λ��ȫ��ѡ����ѡ��ȥ��ȫ�������id��ֵ
function GW_moveP(tp0100){
	radow.doEvent('DeleteP',tp0100);
	$('.dqnrzw').html("�ޣ�");
	Ext.getCmp("a0192aMN").setValue("");
	$("#dqnrzwqcid").val("");
	//Ext.getCmp('yxgwSel_combo').setDisabled(false)
	Ext.getCmp('gwa02_combo').setDisabled(false)
}
//ֱ��������
function movePB_by_nm(rcid){
	var pgridBuffer = Ext.getCmp('pgridBuffer');
	var rowData = pgridBuffer.store.getById(rcid);
	//pgridBuffer.store.remove(rowData);
	
	var a0200s = rowData.data.a0200+":"+rowData.data.zrrx+":"+rowData.data.b01id+":"+rowData.data.personstatus+":"+rowData.data.a0000;
	$("#a0200s").val(a0200s);
	rowData.set("personstatus", "2");
	radow.doEvent('movePB_by_nm','1');
}


/*function setGWdis(obj){
	var v = $('#yxgwSel').val();
	if(v==''||v=='1'){//1Ϊ��
		Ext.getCmp("dwmckqgw").setDisabled(false);
		
		Ext.getCmp("a0192aMN").setValue("");
		//ְ������
		Ext.getCmp("zwqc01").setValue(1);
		
		Ext.getCmp("gwa02_combo").setDisabled(false);
	}else{
		Ext.getCmp("dwmckqgw").setDisabled(true);
		//Ext.getCmp("dwmckqgw").setValue("");
		Ext.getCmp("a0192aMN").setValue($('#yxgwSel_combo').val());
		
		radow.doEvent('getZwqc01',$('#yxgwSel').val());
		Ext.getCmp("a0192aMN").setValue("");
		//ԭ��λ
		odin.setSelectValue('gwa02','');
		Ext.getCmp("gwa02_combo").setDisabled(true);
	}
}*/
function setGWA02(obj){
	var v = $('#gwa02').val();
	if(v==''||v=='1'){//1Ϊ��
		Ext.getCmp("a0192aMN").setValue("");
		Ext.getCmp("dwmckqgw").setValue("");
		odin.setSelectValue('a0201e','');
		odin.setSelectValue('a0501b','');
		Ext.getCmp("zwqc01").setValue('1');
		
		//���θ�λ
		$('.NRinfo').html('');
	}else{
		//a0200+"@@"+fxyp07+"@@"+fxyp00+"@@"+tp0100+"@@"+a0215a+"@@"+a0192a+"@@"+zwqc00+"@@"+zwqc01+"@@"+a0201e
		var Parray = v.split("@@");
		Ext.getCmp("dwmckqgw").setValue(Parray[4]);
		Ext.getCmp("a0192aMN").setValue(Parray[5]);
		Ext.getCmp("zwqc01").setValue(Parray[7]||'1');
		odin.setSelectValue('a0201e',Parray[8]);
		odin.setSelectValue('a0501b',Parray[9]);
		
		radow.doEvent('setNRinfo',Parray[0]);
	}
}

function setNRDWb01(obj){
	var v = $('#dwb01').val();
	var text = $('#dwb01_combo').val();
	if(v==''||v=='1'){//1Ϊ��
		Ext.getCmp("a0192aMN").setValue("");
		Ext.getCmp("dwmckqgw").setValue("");
		odin.setSelectValue('a0201e','');
		odin.setSelectValue('a0501b','');
		Ext.getCmp("zwqc01").setValue('1');
		
		//���θ�λ
		$('.NRinfo').html('');
	}else{
		//qxb01id+"@"+b0131+"@x"
		var Parray = v.split("@");
		//Ext.getCmp("dwmckqgw").setValue(Parray[4]);//�������θ�λ
		Ext.getCmp("a0192aMN").setValue(text);
		Ext.getCmp("zwqc01").setValue('1');
		$("#b01idkq").val(Parray[0])
		if(Parray[1]!=null&&Parray[1]!=''&&Parray[1]!='null'){
			$("#fxyp06").val(Parray[1]);
		}else{
			$("#fxyp06").val('');
		}
		
		
		//radow.doEvent('setNRinfo',Parray[0]);
	}
}



function reverseSearchClick(){
		
		var pgrid = Ext.getCmp('pgrid2');
		var pgridStore = pgrid.getStore();
		
		$('.reverse-search').on('click', function (e) {
			pgridStore.removeAll();
			//�������
			pgridStore.clearGrouping();
			var i,rc;
			var key = $(this).attr('dataType');
			var rsa0000s = PgridInfo.pgridRecordsRS[key];
			//����ѡ�б�����ɫ
			if($(this).hasClass('rcbgcolor')){
				pgridStore.add(PgridInfo.pgridRecords);
				$(this).removeClass('rcbgcolor');
			}else{
				$('.reverse-search').removeClass('rcbgcolor');
				$(this).addClass('rcbgcolor');
				for(i=0; i<PgridInfo.pgridRecords.length;i++ ){
					rc = PgridInfo.pgridRecords[i];
					if(rsa0000s.indexOf(rc.data.a0000)>=0){
						pgridStore.add(rc);
					}
				}
				
			}
			//���·���
			pgridStore.groupBy('zrrx');
			//alert($(this).attr('dataType'))
		});
	}

function pgridLoad(c,rcs,parms){
	PgridInfo.pgridRecords = rcs
}
var PgridInfo={};
PgridInfo.pgridRecords = [];
PgridInfo.pgridRecordsRS = {};













function rybd(){
	var grid = Ext.getCmp('pgridBuffer');
	var store = grid.getStore();
	var length = store.getCount();
	var a0000s='';
	for (var i = 0; i < length; i++) {
		var selected = store.getAt(i);
		var record = selected.data;
		a0000s = a0000s + record.a0000 + ',';
	}
	if (a0000s == '') {
		odin.alert("�������Ա��");
		return;
	}
	clearCookie("jggl.tpbj.ids");
	a0000s = a0000s.substring(0, a0000s.length - 1);
	$("#a0000sBD").val(a0000s);
	radow.doEvent('tpbj.onclick');
}



function addMNOrg(){
	$h.openWin('MNTPOrg', 'pages.fxyp.MNTPOrg', '�½���λ', 650, 260, null, g_contextpath, null, { maximizable: false,resizable: false,closeAction: 'close',mntp00:$("#mntp00").val()})
}
