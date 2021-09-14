var positionnum=0;//光标位置
var allDeleteFlag="1";//重新设置 是否可编辑标志 1 是 0 否
var leftBrakets="0";//左括号数初始化为0
var rightBrakets="0";//右括号数初始化为0
var areastr="";//当前选中的条件id
var txtareaarr=[];//存储所有添加条件 用于显示条件
var txtareaarrCode=[];//存储所有添加条件 用于拼接sql条件
var arrall=[];//存储所有条件  
var arrselect=[];//存储选中的条件 保存到条件表中
var mapBtn={ 'btnn2':'(', 'btnn3':')', 'btnn4':'.非.', 'btnn5':'.并且.', 'btnn6':'.或者.' }
var mapBtnCode={ 'btnn2':'(', 'btnn3':')', 'btnn4':' not ', 'btnn5':' and ', 'btnn6':' or ' }
function initTab3Info(jsonstr){
	jsonInfo = eval('(' + jsonstr + ')');
	for(var i=0;i<jsonInfo.length;i++){
		obj=jsonInfo[i];
		var type=obj['tyle'];
		if(type=='1'){
			var tar='';
			var code_type=obj['code_type'];
			var conditionName5=obj['sign'];
			if(code_type=='S'){
				if(conditionName5.indexOf('between')!=-1){
					tar=obj['fldname']+' '+obj['signname']+' '+obj['valuename1']+' '+obj['valuename2'];
				}else if(conditionName5.indexOf('null')!=-1){
					tar=obj['fldname']+' '+obj['signname'];
				}else{
					tar=obj['fldname']+' '+obj['signname']+' '+obj['valuename1'];
				}
			}else if(code_type=='N'){
				if(conditionName5.indexOf('between')!=-1){
					tar=obj['fldname']+' '+obj['signname']+' '+obj['valuecode1']+' '+obj['valuecode2'];
				}else if(conditionName5.indexOf('null')!=-1){
					tar=obj['fldname']+' '+obj['signname'];
				}else{
					tar=obj['fldname']+' '+obj['signname']+' '+obj['valuecode1'];
				}
			}else if(code_type=='T'){
				if(conditionName5.indexOf('between')!=-1){
					tar=obj['fldname']+' '+obj['signname']+' '+obj['valuecode1']+' '+obj['valuecode2'];
				}else if(conditionName5.indexOf('null')!=-1){
					tar=obj['fldname']+' '+obj['signname'];
				}else{
					tar=obj['fldname']+' '+obj['signname']+' '+obj['valuecode1'];
				}
			}else if(code_type=='C'){
				if(conditionName5.indexOf('null')!=-1){
					tar=obj['fldname']+' '+obj['signname'];
				}else{
					tar=obj['fldname']+' '+obj['signname']+' '+obj['valuecode1'];
				}
			}
			tar=tar+"@@1"
			txtareaarr.push(tar)
		}else if(type=='2'){
			txtareaarr.push(obj['fldcode'].replace(/\$/g,'\'')+"@@2");
		}
	}
	
	document.getElementById("conditionName8").innerHTML=setAddCon(txtareaarr);//显示添加条件
	for(var i=0;i<jsonInfo.length;i++){
		obj=jsonInfo[i];
		var type=obj['tyle'];
		if(type=='1'){
			var conditionStr='';
			var code_type=obj['code_type'];
			var conditionName5=obj['sign'];
			var table_code=obj['tblname'];
			var col_code=obj['fldcode'];
			var valuecode1=obj['valuecode1'];
			var valuecode2=obj['valuecode2'];
			if(code_type=='S'){
				if(conditionName5.indexOf('between')!=-1){
					conditionStr=" ( "+table_code+"."+col_code+" "+" between "+" '"+valuecode1+"' AND '"+valuecode2+"'";
					conditionStr=conditionStr+" or "+table_code+"."+col_code+" "+" between "+" '"+valuecode2+"' AND '"+valuecode1+"' )";
				}else if(conditionName5.indexOf('like')!=-1){
					if(conditionName5.indexOf('%v%')!=-1){
						conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'%"+valuecode1+"%'"+" ";
					}else if(conditionName5.indexOf('%v')!=-1){
						conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'%"+valuecode1+"'"+" ";
					}else if(conditionName5.indexOf('v%')!=-1){
						conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'"+valuecode1+"%'"+" ";
					}
				}else if(conditionName5.indexOf('null')!=-1){
					conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" ";
				}else{
					conditionStr=" nvl("+table_code+"."+col_code+",'') "+conditionName5+" "+"'"+valuecode1+"'"+" ";
				}
			}else if(code_type=='N'){
				if(conditionName5.indexOf('between'!=-1)){//有between
					tempright=obj['valuecode1'];
					templeft=obj['valuecode2'];
					conditionStr=" ("+table_code+"."+col_code+" "+" between "+" "+tempright+" AND "+templeft;
					conditionStr=conditionStr+" or "+table_code+"."+col_code+" "+" between "+" "+templeft+" AND "+tempright+")";
				}else if(conditionName5.indexOf('null')!=-1){
					conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" ";
				}else{
					tempright=obj['valuecode1'];
					conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+tempright+" ";
				}
			}else if(code_type=='T'){
				if(conditionName5.indexOf('between')!=-1){//有between
					tempright="to_date('"+obj['valuecode1']+"','yyyy-mm-dd')";
					templeft="to_date('"+obj['valuecode2']+"','yyyy-mm-dd')";
					conditionStr=" ( to_date(decode(length("+table_code+"."+col_code+"),6,"+table_code+"."+col_code+"||'01',8,"+table_code+"."+col_code+","+table_code+"."+col_code+"),'yyyy-mm-dd') "+" between "+" "+tempright+" AND "+templeft;
					conditionStr=conditionStr+" or to_date(decode(length("+table_code+"."+col_code+"),6,"+table_code+"."+col_code+"||'01',8,"+table_code+"."+col_code+","+table_code+"."+col_code+"),'yyyy-mm-dd') "+" between "+" "+templeft+" AND "+tempright+")";
				}else if(conditionName5.indexOf('null')!=-1){
					conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" ";
				}else{
					tempright="to_date('"+obj['valuecode1']+"','yyyy-mm-dd')";
					conditionStr=" to_date(decode(length("+table_code+"."+col_code+"),6,"+table_code+"."+col_code+"||'01',8,"+table_code+"."+col_code+","+table_code+"."+col_code+"),'yyyy-mm-dd')"+" "+conditionName5+" "+tempright+" ";
				}
			}else if(code_type=='C'){
				if(conditionName5.indexOf('like')!=-1){
					if(conditionName5.indexOf('%v%')!=-1){
						conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'%"+obj['valuecode1']+"%'";
					}else if(conditionName5.indexOf('%v')!=-1){
						conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'%"+obj['valuecode']+"'";
					}else if(conditionName5.indexOf('v%')!=-1){
						conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'"+obj['valuecode1']+"%'";
					}
				}else if(conditionName5.indexOf('null')!=-1){
					conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" ";
				}else{
					conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'"+obj['valuecode1']+"'"+" ";
				}
			}
			txtareaarrCode.push(conditionStr);
		}else if(type=='2'){
			txtareaarrCode.push(obj['fldcode'].replace(/\$/g,'\''));
		}
		
	}
	document.getElementById("conditionStr").value=createWhereCon();// 隐藏拼接的where条件 
	for(var i=0;i<jsonInfo.length;i++){
		obj=jsonInfo[i];
		var type=obj['tyle'];
		if(type=='1'){
			var str=obj['fldname']+","+obj['fldcode']+","+obj['tblname']
			+","+obj['valuename1']+","+obj['valuecode1']+","+obj['code_type']
			+","+obj['valuecode2']+","+obj['valuename2']+","+obj['sign'];
			arrall.push("1,"+str);
			arrselect.push("1,"+str);
		}else if(type=='2'){
			var fldcodetemp=obj['fldcode'];
			var fldnametemp=obj['fldname'];
			if(fldnametemp==null){fldnametemp='';}
			arrall.push("2,"+fldcodetemp.replace(/\$/g,'\'')+","+fldnametemp.replace(/\$/g,'\''));
			arrselect.push("2,"+fldcodetemp.replace(/\$/g,'\'')+","+fldnametemp.replace(/\$/g,'\''));
		}
		
	}
}
function createWhereCon(){
	var ss='';
	var zhtj=document.getElementById("zhtj").value;
	zhtj=zhtj.replace(/\./g,'');
	zhtj=zhtj.replace(/或者/g,' or ');
	zhtj=zhtj.replace(/并且/g,' and ');
	zhtj=zhtj.replace(/非/g,' ! ');
	var arr=zhtj.split(/[0-9]{1,}/);
	for(var i=0;txtareaarrCode!=null&&i<txtareaarrCode.length;i++){
		ss=ss+arr[i]+txtareaarrCode[i]
		.replace(/{v}/g,' ')
		.replace(/{%v}/g,' ')
		.replace(/{v%}/g,' ')
		.replace(/{%v%}/g,' ');	
	}
	if(arr!=null&&txtareaarrCode!=null){
		if(arr.length>txtareaarrCode.length){
			ss=ss+arr[txtareaarrCode.length];
			return ss;
		}else{
			return ss;
		}
	}
	return ss;
}
function initTab4Button(){
	var conditionName8=document.getElementById("conditionName9").innerHTML;
		if(conditionName8!=null&&conditionName8.trim()!=""){
			allDeleteFlag='0';
			radow.doEvent('initTab4Button');
		}
}
//清空tab4 条件页面信息
function cleanInfo(){
	leftBrakets=0;//左括号数初始化为0
	rightBrakets=0;//右括号数初始化为0
	//全部删除是否可编辑标志
	allDeleteFlag="1";//1 是 0 否
	areastr="";//当前选中的条件id
	txtareaarr=[];//存储所有条件 显示条件
	txtareaarrCode=[];//存储所有条件 拼接sql条件
	arrselect=[];
	arrall=[];
	document.getElementById("conditoionlist").value="";//清空隐藏的 当前拼接的条件 
	document.getElementById("table_code").value="";//清空隐藏的 当前选中的信息项 
	document.getElementById("col_code").value="";//清空隐藏的 当前选中的信息项 
	document.getElementById("col_name").value="";//清空隐藏的 当前选中的信息项 
	document.getElementById("showcolname").value="";//清空隐藏的清空隐藏的  当前选中的信息项 -
	document.getElementById("code_type").value="";//清空隐藏的 当前选中的信息项 
	document.getElementById("col_data_type_should").value="";//清空隐藏的 当前选中的信息项  字段类型标示
	document.getElementById("conditionStr").value="";//清空隐藏的 组合条件转换 成的 where条件
	document.getElementById("querysql").value="";//清空隐藏的 存储已经生成的sql
	document.getElementById("conditionName8").innerHTML="";//清空添加条件
	document.getElementById("conditionName9").value="";//清空添加条件
	//document.getElementById("conditionName9").value="";//
	document.getElementById("zhtj").value="";
	positionnum=0;
}
function btnn7Funcc(){//重新设置
	document.getElementById("conditionName9").value="";//清空组合条件
	if(allDeleteFlag=='0'){
		allDeleteFlag='1';//重置全部删除 标志1 已删除
	}
	leftBrakets=0;//左括号数初始化为0
	rightBrakets=0;//右括号数初始化为0
	document.getElementById("conditionStr").value="";//清空where条件
	document.getElementById("querysql").value="";//清空存储已经生成的sql
	document.getElementById("zhtj").value="";
	arrselect=[];//被选中到组合条件中的添加条件 
	radow.doEvent("refreshDis");//初始化组合条件按钮
}
function btnnFunc(obj){//选择条件
	if(areastr==""){//
		alert("请选择条件!");
		return;
	}
	var str=document.getElementById("conditionName9").value;//获取组合条件 
	if("btnn1"==obj.id){
		var num=parseInt(areastr.substr(areastr.length-1,areastr.length))+1;//定位选择的条件
		document.getElementById("conditionName9").value=str+" "+num;//拼接组合条件 
		
		var tempCondition="";
		for(i=0;i<txtareaarrCode.length;i++){
			if(i==(num-1)){
				tempCondition=txtareaarrCode[i];//生成where 条件 
			}
		}
		//隐藏where 条件
		document.getElementById("conditionStr").value=document.getElementById("conditionStr").value+tempCondition;
		//增加选中的条件到数组中 arrselect
		var tempQryUse="";
		for(i=0;i<arrall.length;i++){
			if(i==(num-1)){
				tempQryUse=arrall[i];
			}
		}
		arrselect.push(tempQryUse);
		//隐藏组合条件
		document.getElementById("zhtj").value=document.getElementById("zhtj").value+' '+arrselect.length;
	}else{
		//map[a0221]
		document.getElementById("conditionName9").value=str+mapBtn[obj.id];//拼接组合条件
		document.getElementById("zhtj").value=document.getElementById("zhtj").value+mapBtn[obj.id];
		//隐藏where 条件
		document.getElementById("conditionStr").value=document.getElementById("conditionStr").value+mapBtnCode[obj.id];
	}
	
	if(allDeleteFlag=='1'){//重新设置全部删除 标志 0
		allDeleteFlag='0';
	}
	if("btnn2"==obj.id){//左括号数
		leftBrakets=parseInt(leftBrakets)+1;
	}
	if("btnn3"==obj.id){//有括号数
		rightBrakets=parseInt(rightBrakets)+1;
	}
	radow.doEvent("setDisSelect",obj.id+","+leftBrakets+","+rightBrakets);//生成sql 
}
function btnm4Func(){//全部删除
	txtareaarr=[];//清空存储的添加条件
	txtareaarrCode=[];//清空存储的where条件
	arrall=[];
	document.getElementById("conditionName8").innerHTML="";//清空添加条件
}
function editbtnFunc(obj){//编辑
	if(areastr==""||areastr.length==0){
		alert("请选中要编辑的条件!");
		return;
	}
	var num=areastr.substr(areastr.length-1,areastr.length);//获取条件定位
	var typestr=txtareaarr[parseInt(num)];
	var typearr=typestr.split('@@');
	var type=typearr[1];
	if(type=='1'){//条件查询
		odin.ext.getCmp('tabcondition').activate('tabcondition1'); 
		var str=arrall[parseInt(num)];
		var arr=str.split(',');
		var table_code=arr[3];
		var col_code=arr[2];
		var grid=Ext.getCmp('contentList6');
		var rowIndex=getRowNum(table_code,col_code);
		dbRowFLdCon(grid,rowIndex,'','');//设置选中的信息项，隐藏信息到页面
		setTowShowDis(arr);
		deleteAddCondition();//移除
		radow.doEvent('arrToContab',str);	//赋值到页面
	}else if(type=='2'){//高级查询
		odin.ext.getCmp('tabcondition').activate('tabcondition2');
		var str=arrall[parseInt(num)];
		var arr=str.split(',');
		document.getElementById('expressionid').value=arr[1];
		if(arr[2]){
			document.getElementById('expressionexplainid').value=arr[2];
		}
		deleteAddCondition();//移除
	}
}
function setTowShowDis(arr){
	var condition=arr[9];
	if(condition.indexOf('between')==-1){
		document.getElementById("value2").style.display='none';//date
		document.getElementById("value21").style.display='none';//text
		document.getElementById("value211").style.display='none';//number
	}
}
function getRowNum(table,col){
	var grid=Ext.getCmp('contentList6');
	var store = grid.getStore();
	var total=store.getCount();
	for(var i=0;i<total;i++){
		var record=store.getAt(i);
		var table_code=record.get("table_code");
		var col_code=record.get("col_code");
		if((table+col).trim().toLowerCase()==(table_code+col_code).trim().toLowerCase()){
			return i;
		}
	}
}
function btnm3Func(){//移除
	if(areastr==""||areastr.length==0){
		alert("请选中要删除的条件!");
		return;
	}
	deleteAddCondition();//移除
}
function setAddCon(txtareaarr){
	var arrstr="";
	for(i=0;i<txtareaarr.length;i++){
		var arr=txtareaarr[i].split('@@');
		arrstr=arrstr+"<span id='spanid"+i+"' style='cursor: pointer;font-size:13px;float:left; ' onclick='backgroundFunc(this);'>"+(i+1)+"."+arr[0]+"</span><br/>";
	}
	return arrstr;
}
function deleteAddCondition(){
	var num=areastr.substr(areastr.length-1,areastr.length);//获取条件定位
	txtareaarr.splice(num, 1);//移除选中的添加条件
	txtareaarrCode.splice(num, 1);//移除选中的添加条件带代码
	arrall.splice(num, 1);
	document.getElementById("conditionName8").innerHTML=setAddCon(txtareaarr);//显示添加条件
	areastr="";//重置条件定位为空
}
function textareadd(){//添加至列表
	
	
	var tabType=document.getElementById('tabType').value;
	if(tabType=='1'){
		var conditionName5=document.getElementById("conditionName5").value;//获取条件符号
		if(tabType=='1'){
			if(conditionName5==null||conditionName5=="null"||conditionName5.trim()==''){
				alert("请选择条件!");
				return;
			}
		}
		var conditoionlist=document.getElementById("conditoionlist").value;//获取拼接的添加条件
	var qryusestr=document.getElementById("qryusestr").value;
	txtareaarr.push(conditoionlist);//添加拼接条件到数组中 用于显示
	arrall.push(qryusestr);//用于保存
	document.getElementById("conditionName8").innerHTML=setAddCon(txtareaarr);//显示添加条件
		var conditionStr="";
		var table_code=document.getElementById("table_code").value;//获取信息集
		var col_code=document.getElementById("col_code").value;//获取信息项
		
		var col_data_type_should=document.getElementById("col_data_type_should").value;//获取存储的字段类型
		//lzl 0625
		if(col_data_type_should!=null){
			col_data_type_should=col_data_type_should.toLowerCase().trim();
		}
		var code_type=document.getElementById("code_type").value;//获取下拉参数
		if(code_type!=null){
			code_type=code_type.toLowerCase().trim();
		}
		var col_data_type=document.getElementById("col_data_type").value;//获取显示的控件类型
		if(col_data_type!=null){
			col_data_type.toLowerCase().trim();
		}
		var tempright="";
		var templeft="";
		var temprightlike="";
		if(code_type!=null&&code_type!=""&&code_type!="null"&&code_type!=" "){//下拉选 无between and  有like  
			if(conditionName5.indexOf('between')!=-1){
				conditionStr=" ( "+table_code+"."+col_code+" "+" between "+" '"+document.getElementById("conditionName6").value+"' AND '"+document.getElementById("conditionName71").value+"'";
				conditionStr=conditionStr+" or "+table_code+"."+col_code+" "+" between "+" '"+document.getElementById("conditionName71").value+"' AND '"+document.getElementById("conditionName6").value+"' )";
			}else if(conditionName5.indexOf('like')!=-1){
				if(conditionName5.indexOf('%v%')!=-1){
					conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'%"+document.getElementById("conditionName6").value+"%'"+" ";
				}else if(conditionName5.indexOf('%v')!=-1){
					conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'%"+document.getElementById("conditionName6").value+"'"+" ";
				}else if(conditionName5.indexOf('v%')!=-1){
					conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'"+document.getElementById("conditionName6").value+"%'"+" ";
				}
			}else if(conditionName5.indexOf('null')!=-1){
				conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" ";
			}else{
				conditionStr=" nvl("+table_code+"."+col_code+" ,'')"+conditionName5+" "+"'"+document.getElementById("conditionName6").value+""+"' ";
			}
		}else if("date"==col_data_type_should||col_data_type=='t'||col_data_type=='T'){//日期型字段   有between and  无like 
				if(col_data_type_should=='date'){
					if(conditionName5.indexOf('between'!=-1)){//有between
						tempright="to_date('"+document.getElementById("conditionName61").value+"','yyyy-mm-dd')";
						templeft="to_date('"+document.getElementById("conditionName7").value+"','yyyy-mm-dd')";
						conditionStr=" ( "+table_code+"."+col_code+" "+" between "+" "+tempright+" AND "+templeft;
						conditionStr=conditionStr+" or "+table_code+"."+col_code+" "+" between "+" "+templeft+" AND "+tempright+") ";
					}else if(conditionName5.indexOf('null')!=-1){
						conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" ";
					}else{
						tempright="to_date('"+document.getElementById("conditionName61").value+"','yyyy-mm-dd')";
						conditionStr=" "+table_code+"."+col_code+" "+" "+conditionName5+" "+tempright+" ";
					}
					
				}else if(col_data_type_should!='date'&&col_data_type=='t'){
					if(conditionName5.indexOf('between')!=-1){//有between
						tempright="to_date('"+document.getElementById("conditionName61").value+"','yyyy-mm-dd')";
						templeft="to_date('"+document.getElementById("conditionName7").value+"','yyyy-mm-dd')";
						conditionStr=" ( to_date(decode(length("+table_code+"."+col_code+"),6,"+table_code+"."+col_code+"||'01',8,"+table_code+"."+col_code+","+table_code+"."+col_code+"),'yyyy-mm-dd') "+" between "+" "+tempright+" AND "+templeft;
						conditionStr=conditionStr+" or to_date(decode(length("+table_code+"."+col_code+"),6,"+table_code+"."+col_code+"||'01',8,"+table_code+"."+col_code+","+table_code+"."+col_code+"),'yyyy-mm-dd') "+" between "+" "+templeft+" AND "+tempright+")";
					}else if(conditionName5.indexOf('null')!=-1){
						conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" ";
					}else{
						tempright="to_date('"+document.getElementById("conditionName61").value+"','yyyy-mm-dd')";
						conditionStr=" to_date(decode(length("+table_code+"."+col_code+"),6,"+table_code+"."+col_code+"||'01',8,"+table_code+"."+col_code+","+table_code+"."+col_code+"),'yyyy-mm-dd')"+" "+conditionName5+" "+tempright+" ";
					}
				}
		}else if("varchar2"==col_data_type_should
				||col_data_type_should=='clob'
				||col_data_type_should==""
				||col_data_type_should==" "
				||col_data_type_should=="null"){//文本 有like 无between and
			if(conditionName5.indexOf('like')!=-1){
				if(conditionName5.indexOf('%v%')!=-1){
					conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'%"+document.getElementById("conditionName611").value+"%'";
				}else if(conditionName5.indexOf('%v')!=-1){
					conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'%"+document.getElementById("conditionName611").value+"'";
				}else if(conditionName5.indexOf('v%')!=-1){
					conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'"+document.getElementById("conditionName611").value+"%'";
				}
			}else if(conditionName5.indexOf('null')!=-1){
				conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" ";
			}else{
				conditionStr=" nvl("+table_code+"."+col_code+",'') "+conditionName5+" "+"'"+document.getElementById("conditionName611").value+"'"+" ";
			}
			
		}else if("number"==col_data_type_should){//数字 有like  有between and
			if(conditionName5.indexOf('between'!=-1)){//有between
				tempright=document.getElementById("conditionName6111").value;
				templeft=document.getElementById("conditionName711").value;
				conditionStr=" ("+table_code+"."+col_code+" "+" between "+" "+tempright+" AND "+templeft;
				conditionStr=conditionStr+" or "+table_code+"."+col_code+" "+" between "+" "+templeft+" AND "+tempright+")";
			}else if(conditionName5.indexOf('null')!=-1){
				conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" ";
			}else{
				tempright=document.getElementById("conditionName611").value;
				conditionStr=" nvl("+table_code+"."+col_code+",'') "+conditionName5+" "+tempright+" ";
			}
		}
		txtareaarrCode.push(conditionStr);
	}else if(tabType=='2'){
		var conditoionlist=document.getElementById("conditoionlist").value;//获取拼接的添加条件
		var qryusestr=document.getElementById("qryusestr").value;
		txtareaarr.push(conditoionlist);//添加拼接条件到数组中 用于显示
		arrall.push(qryusestr);//用于保存
		document.getElementById("conditionName8").innerHTML=setAddCon(txtareaarr);//显示添加条件
		var arr=qryusestr.split(',');
		txtareaarrCode.push(arr[1]);
	}
	
}
function addtolistfunc(){//添加到列表
	var tabType=document.getElementById('tabType').value;
	if(tabType=='1'){
		var col_data_type_should=document.getElementById("col_data_type_should").value;
		var col_data_type=document.getElementById("col_data_type").value;
		var code_type=document.getElementById("code_type").value;
		var condition=document.getElementById("conditionName5").value;
		if(condition==null||condition==''){
			alert('请选择条件符号!');
			return;
		}
		
		if(condition.indexOf('null')==-1){
			if(code_type!=null&&code_type.trim()!=''&&code_type.trim()!='null'){
				var conditionName6=document.getElementById("conditionName6").value;
				var conditionName71=document.getElementById("conditionName71").value;
				if(conditionName6==null||conditionName6==''){
					alert('请选择值一!');
					return;
				}
				if(condition.indexOf('between')!=-1){
					if(conditionName71==null||conditionName71==''){
						alert('请选择值二!');
						return;
					}
				}
			} else if("date"==col_data_type_should||col_data_type=='t'||col_data_type=='T'){
				var conditionName61=document.getElementById("conditionName61").value;
				var conditionName7=document.getElementById("conditionName7").value;
				if(conditionName61==null||conditionName61==''){
					alert('请选择值一!');
					return;
				}
				if(condition.indexOf('between')!=-1){
					if(conditionName7==null||conditionName7==''){
						alert('请选择值二!');
						return;
					}
				}
			} else if("number"==col_data_type_should){
				var conditionName6111=document.getElementById("conditionName6111").value;
				var conditionName711=document.getElementById("conditionName711").value;
				if(conditionName6111==null||conditionName6111==''){
					alert('请输入值一!');
					return;
				}
				if(condition.indexOf('between')!=-1){
					if(conditionName711==null||conditionName711==''){
						alert('请输入值二!');
						return;
					}
				}
			}else{
				var conditionName611=document.getElementById("conditionName611").value;
				if(conditionName611==null||conditionName611==''){
					alert('请输入值一!');
					return;
				}
			}
		}
	}
	radow.doEvent("addtolistfunc");
}
function conditionclear(){//清除条件b0194_combo
	document.getElementById("conditionName5_combo").value="";
	document.getElementById("conditionName5").value="";
	document.getElementById("conditionName6").value="";
	document.getElementById("conditionName6_combotree").value="";
	document.getElementById("conditionName71").value="";
	document.getElementById("conditionName71_combotree").value="";
	radow.doEvent("conditionclear");
}
function setconditionName4(){
	////给条件指标项赋值
	document.getElementById("conditionName4").value=document.getElementById("showcolname").value;
}
var codecombox='';
function setTree(code_type){
	try{
		if(codecombox){
			codecombox.selectStore={};
			codecombox.codetype=code_type;
			codecombox.initComponent();//
			document.getElementById("conditionName6_treePanel").removeChild(document.getElementById("conditionName6_treePanel").children(0));
		}else{
			var codeStr = '<input type="hidden" id="conditionName6" name="conditionName6" /><input type="text" id="conditionName6_combotree" name="conditionName6_combotree" style="cursor:default;" required="false"  label="false" />';
			document.getElementById("codediv").innerHTML=codeStr; 
			codecombox = new Ext.ux.form.ComboBoxWidthTree({
			 	 selectStore:"",
				 property: 'conditionName6',
				 id:"conditionName6_combotree",
				 label : 'false',
				 applyTo:"conditionName6_combotree",
				 tpl: '<div style="height:200px;"><div id="conditionName6_treePanel"></div></div>',
				 width:160,
				 codetype:code_type,
				 codename:'code_name'
				 
			 });
		
			
		}
	}catch(err){
		
	}
}
var codecombox2='';
function setTree2(code_type){
	try{
		if(codecombox2){
			codecombox2.selectStore={};
			codecombox2.codetype=code_type;
			codecombox2.initComponent();//
			document.getElementById("conditionName71_treePanel").removeChild(document.getElementById("conditionName71_treePanel").children(0));
		}else{
			var codeStr = '<input type="hidden" id="conditionName71" name="conditionName71" /><input type="text" id="conditionName71_combotree" name="conditionName71_combotree" style="cursor:default;" required="false"  label="false" />';
			document.getElementById("codediv2").innerHTML=codeStr; 
			codecombox2 = new Ext.ux.form.ComboBoxWidthTree({
			 	 selectStore:"",
				 property: 'conditionName71',
				 id:"conditionName71_combotree",
				 label : 'false',
				 applyTo:"conditionName71_combotree",
				 tpl: '<div style="height:200px;"><div id="conditionName71_treePanel"></div></div>',
				 width:160,
				 codetype:code_type,
				 codename:'code_name'
				 
			 });
		}
	}catch(err){
		
	}
}
function rowFldeDbClick(grid,rowIndex,colIndex,event){//双击条件信息项
	dbRowFLdCon(grid,rowIndex,colIndex,event);
	var record = grid.store.getAt(rowIndex);//获得双击的当前行的记录
	var col_data_type=record.get("col_data_type");//列数据类型
	var col_data_type_should=record.get("col_data_type_should");//指标代码类型2
	var code_type=record.get("code_type");//指标代码类型
	if(col_data_type==null||col_data_type=="null"){
		col_data_type="";
	}else{
		col_data_type=col_data_type.toLowerCase();
	}
	if(code_type!=null){
		code_type=code_type.trim();
	}
	if(col_data_type_should!=null){
		col_data_type_should=col_data_type_should.trim().toLowerCase();
	}
	if(code_type!=null&&code_type!=''&&code_type!='null'){//下拉选有 between and  有like  
		//查询符号
		radow.doEvent("code_type_value1",code_type);
	}else if(col_data_type_should=='date'||col_data_type=='t'){//日期型字段   有between and  无like 
		//查询符号
		radow.doEvent("selectValueList");
	}else if(col_data_type_should==""
			||col_data_type_should=="clob"||col_data_type_should=="varchar2"
			||col_data_type_should=="null"||col_data_type_should==null
			||col_data_type_should.length==""||col_data_type_should.length==" "){//文本 有like 无between and
		//查询符号
		radow.doEvent("selectValueListNobt");
	}else if(col_data_type_should=="number"){//数字 有like  有between and
		//查询符号
		radow.doEvent("selectValueListLikeBt");
	}
}
function dbRowFLdCon(grid,rowIndex,colIndex,event){
	var record = grid.store.getAt(rowIndex);//获得双击的当前行的记录
	var col_name1=record.get("showcolname");//指标名称
	var col_name=record.get("col_name");//指标名称
	var table_code=record.get("table_code");
	var col_code=record.get("col_code");//
	var col_data_type=record.get("col_data_type");//列数据类型
	var col_data_type_should=record.get("col_data_type_should");//指标代码类型2
	var code_type=record.get("code_type");//指标代码类型
	document.getElementById("col_data_type").value=col_data_type;
	document.getElementById("table_code").value=table_code;
	document.getElementById("col_code").value=col_code;
	document.getElementById("col_name").value=col_name;
	document.getElementById("showcolname").value=col_name1;
	document.getElementById("col_data_type_should").value=col_data_type_should;
	document.getElementById("code_type").value=code_type;
	setDisOrShow(code_type,col_data_type_should,col_data_type);
}

function setDisOrShow(code_type,col_data_type_should,col_data_type){
	if(col_data_type==null||col_data_type=="null"){
		col_data_type="";
	}else{
		col_data_type=col_data_type.toLowerCase();
	}
	if(code_type!=null){
		code_type=code_type.trim();
	}
	if(col_data_type_should!=null){
		col_data_type_should=col_data_type_should.trim().toLowerCase();
	}
	if(code_type!=null
		&&code_type!=' '
		&&code_type!=''&&code_type!='null'){//下拉选 有between and  有like  
		//查询符号
		document.getElementById("value1111").style.display='none';//number
		document.getElementById("value111").style.display='none';//text
		document.getElementById("value11").style.display='none';//date
		document.getElementById("value1").style.display='block';//select
		document.getElementById("value2").style.display='none';//date
		document.getElementById("value21").style.display='block';//text
		document.getElementById("value211").style.display='none';//number
		
	}else if(col_data_type_should=='date'||col_data_type=='t'||col_data_type=='T'){//日期型字段   有between and  无like 
		//查询符号
		document.getElementById("value1").style.display='none';
		document.getElementById("value111").style.display='none';
		document.getElementById("value1111").style.display='none';
		document.getElementById("value11").style.display='block';
		document.getElementById("value2").style.display='block';
		document.getElementById("value21").style.display='none';
		document.getElementById("value211").style.display='none';
	}else if(col_data_type_should==""
			||col_data_type_should=="clob"||col_data_type_should=="varchar2"
			||col_data_type_should=="null"||col_data_type_should==null
			||col_data_type_should.length==""||col_data_type_should.length==" "){//文本 有like 无between and
		//查询符号
		document.getElementById("value1").style.display='none';
		document.getElementById("value11").style.display='none';
		document.getElementById("value111").style.display='block';
		document.getElementById("value1111").style.display='none';
		document.getElementById("value2").style.display='none';
		document.getElementById("value21").style.display='none';
		document.getElementById("value211").style.display='block';
	}else if(col_data_type_should=="number"){//数字 有like  有between and
		//查询符号
		document.getElementById("value1").style.display='none';
		document.getElementById("value11").style.display='none';
		document.getElementById("value111").style.display='none';
		document.getElementById("value1111").style.display='block';
		document.getElementById("value2").style.display='none';
		document.getElementById("value21").style.display='none';
		document.getElementById("value211").style.display='block';
	}
}
function backgroundFunc(obj){//条件单机事件
	var spanarr=document.getElementById("conditionName8").children;//获取所有的添加条件
	for(i=0;i<spanarr.length;i++){//循环添加条件
		spanarr[i].style.background="";//清空所有的添加条件背景
	}
	areastr=obj.id;//获取选中的条件条件
	obj.style.background="pink";//设置选中的条件背景
}

function valuefivefunc(){//条件符号改变事件 
	var conditionName5=document.getElementById("conditionName5").value;
	var col_data_type=document.getElementById("col_data_type").value;
	var code_type=document.getElementById("code_type").value;
	var col_data_type_should=document.getElementById("col_data_type_should").value;
	if(col_data_type==null||col_data_type=="null"){
		col_data_type="";
	}else{
		col_data_type=col_data_type.toLowerCase();
	}
	if(code_type!=null){
		code_type=code_type.trim();
	}
	if(col_data_type_should!=null){
		col_data_type_should=col_data_type_should.trim().toLowerCase();
	}
	setDisOrShow(code_type,col_data_type_should,col_data_type);
	if(conditionName5.indexOf('between')!=-1){//选择了between and条件
		if(code_type!=null&&code_type.trim()!=''&&code_type.trim()!='null'){
			//设置select控件可编辑
			document.getElementById("value2").style.display='none';//date
			document.getElementById("value21").style.display='block';//select
			document.getElementById("value211").style.display='none';//number
			//设置select控件可编辑
			radow.doEvent("setValue21Disable",code_type);
		}else if(col_data_type=='T'||col_data_type=='t'){//数据类型是date型
			//设置date控件可编辑
			radow.doEvent("setValue2Disable");
		}else if(col_data_type_should==""
			||col_data_type_should=="clob"||col_data_type_should=="varchar2"
				||col_data_type_should=="null"||col_data_type_should==null
				||col_data_type_should.length==""||col_data_type_should.length==" "){
			radow.doEvent("setValue111Disable");
		}else{
			//设置number控件可编辑
			radow.doEvent("setValue211Disable");
		}
	}else if(conditionName5.indexOf('null')!=-1){
		//设置不可编辑
		document.getElementById("value1").style.display='none';//select
		document.getElementById("value11").style.display='none';//date
		document.getElementById("value111").style.display='block';//text
		document.getElementById("value1111").style.display='none';//number
		
		document.getElementById("value2").style.display='none';//date
		document.getElementById("value21").style.display='none';//select
		document.getElementById("value211").style.display='block';//number
		radow.doEvent("setValue1And2Disable");
	}

}
function tableOnChange(obj){
	var tableinfo=document.getElementById('infosetfuncid').value;
	if(tableinfo!=null&&tableinfo.trim()!=''&&tableinfo.trim()!='null'){
		radow.doEvent('tableOnChange',tableinfo);
	}
}
function stringOnChange(obj){
	focuscreateRange('stringfuncid');
}

function dateOnChange(obj){
	focuscreateRange('datefuncid');
}
function databaseOnChange(obj){
	var value=document.getElementById('expressionid').value;
	if(value.length=0){
    	positionnum=0;
    }
	var str=document.getElementById('databasefuncid').value;
	var str1=document.getElementById('infosetfuncid').value;
	value=value.substr(0,positionnum)+str1+'.'+str+value.substr(positionnum,value.length);
	document.getElementById('expressionid').value=value;
}
function opratesymOnChange(obj){
	focuscreateRange('opratesymfuncid');
}
function numberOnChange(obj){
	focuscreateRange('numberfuncid');
}
function focuscreateRange(idstr){
	var value=document.getElementById('expressionid').value;
	if(value.length=0){
    	positionnum=0;
    }
	var str=document.getElementById(idstr).value;
	value=value.substr(0,positionnum)+str+value.substr(positionnum,value.length);
	document.getElementById('expressionid').value=value;
}

function expressOut(){
	 var el = document.getElementById('expressionid');
  if (el.selectionStart) { 
    	positionnum=el.selectionStart;
    	return;
  } else if (document.selection) { 
	    var r = document.selection.createRange(); 
	    if (r == null) { 
	      positionnum= 0; 
	      return;
	    } 
	    var re = el.createTextRange(), 
	    rc = re.duplicate(); 
	    re.moveToBookmark(r.getBookmark()); 
	    rc.setEndPoint('EndToStart', re); 
	    positionnum= rc.text.length; 
	    return;
  }  
  positionnum= 0; 
  return;
}

function cleanTab4High(obj){
	 document.getElementById('expressionid').value='';
	 positionnum= 0; 
}
function addListHigh(){
	//校验条件
	var value=document.getElementById('expressionid').value;
	if(value==null||value.trim()=='null'||value.trim()==''){
		return;
	}
	radow.doEvent("checkHighCon");
}
function tab4onchange(tabObj,item){
	if(item.getId()=='tabcondition1'){
		document.getElementById('tabType').value='1';
	}else{
		document.getElementById('tabType').value='2';
	}
}
