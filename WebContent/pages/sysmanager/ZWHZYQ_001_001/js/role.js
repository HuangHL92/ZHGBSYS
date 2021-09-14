var ids="";

var tree1;
Ext.onReady(function(){
	var userid = '';
	var tree = new Ext.tree.ColumnTree({
        el:'orgDiv',
        width:565,
        height:320,
        containerScroll: true,
//        autoHeight:true,
        rootVisible:false,
        autoScroll:true,
//        title: '机构树列表',
        
        columns:[{
            header:'机构名称',
            width:350,
            dataIndex:'task'
        },{
            header:'浏览',
            width:107,
            dataIndex:'duration'
        },{
            header:'维护',
            width:106,
            dataIndex:'user'
        }],

        loader: new Ext.tree.TreeLoader({
            dataUrl:'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.MechanismComWindow&eventNames=orgTreeJsonData&userid='+userid,
            uiProviders:{
                'col': Ext.tree.ColumnNodeUI
            }
        }),

         	root: new Ext.tree.AsyncTreeNode({
	            text:'Tasks',
	            id: document.getElementById('ereaid').value

        }),
        listeners:{
        	'expandnode':function(node){
        		//获取展开节点的信息  
        		var th_user=node.attributes.user;
        		var th_duration=node.attributes.duration;
//        		var a = th_user.split('"');
//        		var b = th_duration.split('"');
        		var a = '';
        		var b = '';
        		var type1 =  document.getElementById('type1');
        		if(type1.checked){
        			var fid = document.getElementById('tree');
	      			var box = fid.getElementsByTagName('input');
	      			for (var i = 0; i < box.length; i++) {
	      				if(a[5] == box[i].id || b[5] == box[i].id){//当前节点时，处理勾选操作
		      				if(box[i].checked == true){
								if(isHaveChilden(box[i]) == true){
									if(box[i].value=='1'){
			      						checkChild1(box[i]);
									}
								}
		      				}else if(box[i].checked == false){
		      					if(isHaveChilden1(box[i]) == true){
									if(box[i].value=='2'){
			      						checkChild2(box[i]);
									}
								}
		      				}
	      				}
					}
				}
        	}
        }
    	/* rootVisible:false; */
    });
     tree.render();
 //	var userid = getValue();
     tree1 = new Ext.tree.ColumnTree({
        el:'tree1',
        width:565,
        height:320,
        containerScroll: true,
//        autoHeight:true,
        rootVisible:false,
        autoScroll:true,
//        title: '信息项权限组列表',
        
        columns:[{
            header:'信息项权限组名称',
            width:350,
            dataIndex:'task'
        },{
            header:'浏览',
            width:107,
            dataIndex:'duration'
        },{
            header:'维护',
            width:106,
            dataIndex:'user'
        }],

        loader: new Ext.tree.TreeLoader({
            dataUrl:'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.InfoComWindow&eventNames=orgTreeJsonData&userid='+userid,
            uiProviders:{
                'col': Ext.tree.ColumnNodeUI
            }
        }),

         	root: new Ext.tree.AsyncTreeNode({
            text:'Tasks' 

        })
    });
//    tree1.render();
//    tree1.expandAll();
});

//判断结点是否存在未被勾选的下级结点
function isHaveChilden(th){
 	var fid = document.getElementById('tree');
	var box = fid.getElementsByTagName('input');
	for(var i=0;i<box.length;i++){
		if(box[i].name == th.id){
			if(box[i].checked==false){
				return true;
			}
		}
	}
	return false;
 }
 
 //判断结点是否存在已被勾选的下级结点
function isHaveChilden1(th){
 	var fid = document.getElementById('tree');
	var box = fid.getElementsByTagName('input');
	for(var i=0;i<box.length;i++){
		if(box[i].name == th.id){
			if(box[i].checked==true){
				return true;
			}
		}
	}
	return false;
 }

//勾选下级结点
function checkChild1(th){
	var fid = document.getElementById('tree');
	var box = fid.getElementsByTagName('input');
	for(var i=0;i<box.length;i++){
		if(box[i].name == th.id){
			//if(box[i].value == '2' || box[i].value == '3'){
				box[i].checked=true;
				box[i].value='1';
			//}
		}
	}
}

//取消勾选下级结点
function checkChild2(th){
	var fid = document.getElementById('tree');
	var box = fid.getElementsByTagName('input');
	for(var i=0;i<box.length;i++){
		if(box[i].name == th.id){
			if(box[i].value != '2' && box[i].value != '3'){
				box[i].checked=false;
				box[i].value='2';
			}
		}
	}
}
 
function getValue(){   
    var URLParams = new Array();    
    var aParams = document.location.search.substr(1).split('&'); 
    for (i=0; i < aParams.length;i++){    
        var aParam = aParams[i].split('=');   
        URLParams[aParam[0]] = aParam[1];    
    }   
    return URLParams["userid"];
}
var beginnum=-1;
var endnum=-1;
var index=0;
function checkhorizon(th){
	var name=th.name;
	var fid = document.getElementById('tree');
    var box = fid.getElementsByTagName('input');
    var type2 =  document.getElementById('type2');
    var type1 =  document.getElementById('type1');
    if(type2.checked){
    	 /* if((th.id.substr(th.id.length-1)=="1")&&th.checked==true){
    		 for (var i = 0; i < box.length; i++) {
	             if(box[i].id==th.id){
	           	 	box[i-1].checked=true;
	             }
	              if(box[i].id!=th.id&&(box[i].id.substr(box[i].id.length-1)=="1")){
	            	 box[i].checked=false;
	             } 
	         } 
    	 }else{ */
		for (var i = 0; i < box.length; i++) {
			if(box[i].id==th.id){
				if(index==0){
					beginnum=i;
				}
				if(index==1){
					endnum=i;
				}
				box[i].checked=true;
			}
		}
		index++;
		if(index==2){
			index=0;
		}
    	/*  } */
	}else{
		beginnum=-1;
    	endnum=-1;
    	index=0;
	}
	//先将“勾选下级”选中，在勾选机构时，将值设为1，未勾选“勾选下级”时勾选机构，机构对象值设为0
	if(th.checked==true){
		if(type1.checked==true){
			th.value='1';
		}else if(type1.checked==false){
			th.value='0';
		}
	}
	//先将“勾选下级”选中，取消机构时，值为2，未勾选“勾选下级”，取消机构时值为3
	if(th.checked==false){
		if(type1.checked==true){
			th.value='2';
		}else if(type1.checked==false){
			th.value='3';
		}
	}
	//处理子节点
	checkChild(th);
	
	var a = th.id;
	if(type1.checked==false&&type2.checked==false){
		if(a.substr(a.length-1)=="0"){
			if(th.checked==false){
				for (var i = 0; i < box.length; i++) {
					if(box[i].id==th.id){
						box[i+1].checked=false;
					}
				}
			}
		}
		if(a.substr(a.length-1)=="1"){
			if(th.checked==true){
				for (var i = 0; i < box.length; i++) {
					if(box[i].id==th.id){
						box[i-1].checked=true;
						box[i-1].value='0';
					}
		             /* if(box[i].id!=th.id&&(box[i].id.substr(box[i].id.length-1)=="1")){
		            	 box[i].checked=false;
		             } */
				}
			}
		} 
	}
}



 function checkChild(th){
	 var type1 =  document.getElementById('type1');
	 var type2 =  document.getElementById('type2');
	 var str = '';
	 var start=0;
	 var end=0;
	 if(type1.checked){
		var fid = document.getElementById('tree');
	    var box = fid.getElementsByTagName('input');
	    for (var i = 0; i < box.length; i++) {
	    	if (box[i].id ==th.id && box[i].name==th.name) {
	    		start=i;
	            for(var j = i+1; j < box.length; j++){
	            	if(box[j].name==th.name){
	            		end=j;
	            		break;
	            	}
	            }
	        }
	    }
		var id1 = th.id;
		var type = id1.substr(id1.length-1);
		var index=0;
		if(end==0){
			if(start<box.length){
				for(var i = start+2; i < box.length; i=i+2){
					for(var j = start-2; j >=0; j=j-2){
						if(box[i].name==box[j].name){
							if(type=='0'){
								end=i-2;
							}else{
								end=i-2; 
							}
							index=1;
							break;
						}
					}
					if(index==1){
						index=0;
						break;
					}
					if(i==box.length-1||i==box.length-2){
						end=box.length-1;
					}
				}
			}
			if(end!=0){
				if(type=="0"){
					end+=2;
		     	}else{
		     		end+=2;
		     	} 
	     	}
		}
		if(end==0){
			end=start+2;
		}
		
		//if(start==0||start==1){
		//	end=box.length+1;
		//}
		
		if(th.checked){
			for(var i = start; i < end; i++){
				var id1 = th.id;
				var type = id1.substr(id1.length-1);
				if(type=="0"){
					for(var i = start; i < end; i++){
						var childtype = box[i].id;
				 		if(childtype.substr(childtype.length-1)=="0" && !box[i].disabled){
					 		box[i].checked=true;
				 		}
					}
			 	}
			  	if(type=="1"){
					for(var i = start-1; i < end-1; i++){
						var childtype = box[i].id;
					// if(childtype.substr(childtype.length-1)=="1"){ 
					if(!box[i].disabled){
						box[i].checked=true;
						box[i].value='1';
					}
					 	//} 
					} 
					 /* for (var i = 0; i < box.length; i++) {
			             if(box[i].id==th.id){
			           	 	box[i-1].checked=true;
			             }
			             if(box[i].id!=th.id&&(box[i].id.substr(box[i].id.length-1)=="1")){
			            	 box[i].checked=false;
			             }
			         }  */
			 	} 
		 	}
	     }else{
			for(var i = start; i < end; i++){
				var id1 = th.id;
				var type = id1.substr(id1.length-1);
				if(type=="0"){
					for(var i = start; i < end; i++){
						box[i].checked=false;
						box[i].value='2';
					}
				}
				if(type=="1"){
					for(var i = start-1; i < end-1; i++){
						var childtype = box[i].id;
						if(childtype.substr(childtype.length-1)=="1"){
							box[i].checked=false;
							box[i].value='2';
						}
					}
				} 
			}
	    }
	}
	if(type2.checked){
		var fid = document.getElementById('tree');
	    var box = fid.getElementsByTagName('input');
		if(beginnum!=-1&&endnum!=-1){
			if(beginnum>endnum){
				var t =beginnum;
				beginnum=endnum;
				endnum=t;
			}

			var childtype1 = box[beginnum].id;
			var childtype2 = box[endnum].id;
			if(childtype1.substr(childtype1.length-1)==childtype2.substr(childtype2.length-1)){
				if(childtype1.substr(childtype1.length-1)=="0"){
					for(var i = beginnum; i < endnum+1; i++){
						var childtype = box[i].id;
						if(childtype.substr(childtype.length-1)=="0"){
							box[i].checked=true;
							if(i == endnum){
								box[i].value='0';
							}else{
								box[i].value='1';
							}
						} 
					}
				}
				if(childtype1.substr(childtype1.length-1)=="1"){
					for(var i = beginnum-1; i < endnum+1; i++){
						box[i].checked=true;
						if(i == endnum || i == endnum-1){
							box[i].value='0';
						}else{
							box[i].value='1';
						}
					}
				} 
				beginnum=-1;
				endnum=-1;
				index=0;
			}
			  /* if(th.checked=true&&(th.id.substr(th.id.length-1)=="1")){
				 for (var i = 0; i < box.length; i++) {
		             if(box[i].id==th.id){
		           	 	box[i-1].checked=true;
		             }
		             if(box[i].id!=th.id&&(box[i].id.substr(box[i].id.length-1)=="1")){
		            	 box[i].checked=false;
		             }
		         }  
			 }  */
		}
	}
	 
}
 


 function dogant(){
	 var fid = document.getElementById('tree');
     var box = fid.getElementsByTagName('input');
	 var count = 0;
     var result = '';
     for (var i = 0; i < box.length; i++) {
         if (box[i].type == 'checkbox') {
             result = result + box[i].id + ':'+box[i].checked+':'+box[i].value+',';
         }
     }
    ids=result;
	radow.doEvent('dogrant',ids);
 }
 function changeType1(){
	 var type1 =  document.getElementById('type1');
	 var type2 =  document.getElementById('type2');
	 if(type1.checked){
		 type2.checked=false;
	 }
 }
 function changeType2(){
	 var type1 =  document.getElementById('type1');
	 var type2 =  document.getElementById('type2');
	 if(type2.checked){
		 type1.checked=false;
	 }
 }