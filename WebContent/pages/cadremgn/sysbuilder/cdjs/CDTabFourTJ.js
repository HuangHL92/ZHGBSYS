var positionnum=0;//���λ��
var allDeleteFlag="1";//�������� �Ƿ�ɱ༭��־ 1 �� 0 ��
var leftBrakets="0";//����������ʼ��Ϊ0
var rightBrakets="0";//����������ʼ��Ϊ0
var areastr="";//��ǰѡ�е�����id
var txtareaarr=[];//�洢����������� ������ʾ����
var txtareaarrCode=[];//�洢����������� ����ƴ��sql����
var arrall=[];//�洢��������  
var arrselect=[];//�洢ѡ�е����� ���浽��������
var mapBtn={ 'btnn2':'(', 'btnn3':')', 'btnn4':'.��.', 'btnn5':'.����.', 'btnn6':'.����.' }
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
	
	document.getElementById("conditionName8").innerHTML=setAddCon(txtareaarr);//��ʾ�������
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
				if(conditionName5.indexOf('between'!=-1)){//��between
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
				if(conditionName5.indexOf('between')!=-1){//��between
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
	document.getElementById("conditionStr").value=createWhereCon();// ����ƴ�ӵ�where���� 
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
	zhtj=zhtj.replace(/����/g,' or ');
	zhtj=zhtj.replace(/����/g,' and ');
	zhtj=zhtj.replace(/��/g,' ! ');
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
//���tab4 ����ҳ����Ϣ
function cleanInfo(){
	leftBrakets=0;//����������ʼ��Ϊ0
	rightBrakets=0;//����������ʼ��Ϊ0
	//ȫ��ɾ���Ƿ�ɱ༭��־
	allDeleteFlag="1";//1 �� 0 ��
	areastr="";//��ǰѡ�е�����id
	txtareaarr=[];//�洢�������� ��ʾ����
	txtareaarrCode=[];//�洢�������� ƴ��sql����
	arrselect=[];
	arrall=[];
	document.getElementById("conditoionlist").value="";//������ص� ��ǰƴ�ӵ����� 
	document.getElementById("table_code").value="";//������ص� ��ǰѡ�е���Ϣ�� 
	document.getElementById("col_code").value="";//������ص� ��ǰѡ�е���Ϣ�� 
	document.getElementById("col_name").value="";//������ص� ��ǰѡ�е���Ϣ�� 
	document.getElementById("showcolname").value="";//������ص�������ص�  ��ǰѡ�е���Ϣ�� -
	document.getElementById("code_type").value="";//������ص� ��ǰѡ�е���Ϣ�� 
	document.getElementById("col_data_type_should").value="";//������ص� ��ǰѡ�е���Ϣ��  �ֶ����ͱ�ʾ
	document.getElementById("conditionStr").value="";//������ص� �������ת�� �ɵ� where����
	document.getElementById("querysql").value="";//������ص� �洢�Ѿ����ɵ�sql
	document.getElementById("conditionName8").innerHTML="";//����������
	document.getElementById("conditionName9").value="";//����������
	//document.getElementById("conditionName9").value="";//
	document.getElementById("zhtj").value="";
	positionnum=0;
}
function btnn7Funcc(){//��������
	document.getElementById("conditionName9").value="";//����������
	if(allDeleteFlag=='0'){
		allDeleteFlag='1';//����ȫ��ɾ�� ��־1 ��ɾ��
	}
	leftBrakets=0;//����������ʼ��Ϊ0
	rightBrakets=0;//����������ʼ��Ϊ0
	document.getElementById("conditionStr").value="";//���where����
	document.getElementById("querysql").value="";//��մ洢�Ѿ����ɵ�sql
	document.getElementById("zhtj").value="";
	arrselect=[];//��ѡ�е���������е�������� 
	radow.doEvent("refreshDis");//��ʼ�����������ť
}
function btnnFunc(obj){//ѡ������
	if(areastr==""){//
		alert("��ѡ������!");
		return;
	}
	var str=document.getElementById("conditionName9").value;//��ȡ������� 
	if("btnn1"==obj.id){
		var num=parseInt(areastr.substr(areastr.length-1,areastr.length))+1;//��λѡ�������
		document.getElementById("conditionName9").value=str+" "+num;//ƴ��������� 
		
		var tempCondition="";
		for(i=0;i<txtareaarrCode.length;i++){
			if(i==(num-1)){
				tempCondition=txtareaarrCode[i];//����where ���� 
			}
		}
		//����where ����
		document.getElementById("conditionStr").value=document.getElementById("conditionStr").value+tempCondition;
		//����ѡ�е������������� arrselect
		var tempQryUse="";
		for(i=0;i<arrall.length;i++){
			if(i==(num-1)){
				tempQryUse=arrall[i];
			}
		}
		arrselect.push(tempQryUse);
		//�����������
		document.getElementById("zhtj").value=document.getElementById("zhtj").value+' '+arrselect.length;
	}else{
		//map[a0221]
		document.getElementById("conditionName9").value=str+mapBtn[obj.id];//ƴ���������
		document.getElementById("zhtj").value=document.getElementById("zhtj").value+mapBtn[obj.id];
		//����where ����
		document.getElementById("conditionStr").value=document.getElementById("conditionStr").value+mapBtnCode[obj.id];
	}
	
	if(allDeleteFlag=='1'){//��������ȫ��ɾ�� ��־ 0
		allDeleteFlag='0';
	}
	if("btnn2"==obj.id){//��������
		leftBrakets=parseInt(leftBrakets)+1;
	}
	if("btnn3"==obj.id){//��������
		rightBrakets=parseInt(rightBrakets)+1;
	}
	radow.doEvent("setDisSelect",obj.id+","+leftBrakets+","+rightBrakets);//����sql 
}
function btnm4Func(){//ȫ��ɾ��
	txtareaarr=[];//��մ洢���������
	txtareaarrCode=[];//��մ洢��where����
	arrall=[];
	document.getElementById("conditionName8").innerHTML="";//����������
}
function editbtnFunc(obj){//�༭
	if(areastr==""||areastr.length==0){
		alert("��ѡ��Ҫ�༭������!");
		return;
	}
	var num=areastr.substr(areastr.length-1,areastr.length);//��ȡ������λ
	var typestr=txtareaarr[parseInt(num)];
	var typearr=typestr.split('@@');
	var type=typearr[1];
	if(type=='1'){//������ѯ
		odin.ext.getCmp('tabcondition').activate('tabcondition1'); 
		var str=arrall[parseInt(num)];
		var arr=str.split(',');
		var table_code=arr[3];
		var col_code=arr[2];
		var grid=Ext.getCmp('contentList6');
		var rowIndex=getRowNum(table_code,col_code);
		dbRowFLdCon(grid,rowIndex,'','');//����ѡ�е���Ϣ�������Ϣ��ҳ��
		setTowShowDis(arr);
		deleteAddCondition();//�Ƴ�
		radow.doEvent('arrToContab',str);	//��ֵ��ҳ��
	}else if(type=='2'){//�߼���ѯ
		odin.ext.getCmp('tabcondition').activate('tabcondition2');
		var str=arrall[parseInt(num)];
		var arr=str.split(',');
		document.getElementById('expressionid').value=arr[1];
		if(arr[2]){
			document.getElementById('expressionexplainid').value=arr[2];
		}
		deleteAddCondition();//�Ƴ�
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
function btnm3Func(){//�Ƴ�
	if(areastr==""||areastr.length==0){
		alert("��ѡ��Ҫɾ��������!");
		return;
	}
	deleteAddCondition();//�Ƴ�
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
	var num=areastr.substr(areastr.length-1,areastr.length);//��ȡ������λ
	txtareaarr.splice(num, 1);//�Ƴ�ѡ�е��������
	txtareaarrCode.splice(num, 1);//�Ƴ�ѡ�е��������������
	arrall.splice(num, 1);
	document.getElementById("conditionName8").innerHTML=setAddCon(txtareaarr);//��ʾ�������
	areastr="";//����������λΪ��
}
function textareadd(){//������б�
	
	
	var tabType=document.getElementById('tabType').value;
	if(tabType=='1'){
		var conditionName5=document.getElementById("conditionName5").value;//��ȡ��������
		if(tabType=='1'){
			if(conditionName5==null||conditionName5=="null"||conditionName5.trim()==''){
				alert("��ѡ������!");
				return;
			}
		}
		var conditoionlist=document.getElementById("conditoionlist").value;//��ȡƴ�ӵ��������
	var qryusestr=document.getElementById("qryusestr").value;
	txtareaarr.push(conditoionlist);//���ƴ�������������� ������ʾ
	arrall.push(qryusestr);//���ڱ���
	document.getElementById("conditionName8").innerHTML=setAddCon(txtareaarr);//��ʾ�������
		var conditionStr="";
		var table_code=document.getElementById("table_code").value;//��ȡ��Ϣ��
		var col_code=document.getElementById("col_code").value;//��ȡ��Ϣ��
		
		var col_data_type_should=document.getElementById("col_data_type_should").value;//��ȡ�洢���ֶ�����
		//lzl 0625
		if(col_data_type_should!=null){
			col_data_type_should=col_data_type_should.toLowerCase().trim();
		}
		var code_type=document.getElementById("code_type").value;//��ȡ��������
		if(code_type!=null){
			code_type=code_type.toLowerCase().trim();
		}
		var col_data_type=document.getElementById("col_data_type").value;//��ȡ��ʾ�Ŀؼ�����
		if(col_data_type!=null){
			col_data_type.toLowerCase().trim();
		}
		var tempright="";
		var templeft="";
		var temprightlike="";
		if(code_type!=null&&code_type!=""&&code_type!="null"&&code_type!=" "){//����ѡ ��between and  ��like  
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
		}else if("date"==col_data_type_should||col_data_type=='t'||col_data_type=='T'){//�������ֶ�   ��between and  ��like 
				if(col_data_type_should=='date'){
					if(conditionName5.indexOf('between'!=-1)){//��between
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
					if(conditionName5.indexOf('between')!=-1){//��between
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
				||col_data_type_should=="null"){//�ı� ��like ��between and
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
			
		}else if("number"==col_data_type_should){//���� ��like  ��between and
			if(conditionName5.indexOf('between'!=-1)){//��between
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
		var conditoionlist=document.getElementById("conditoionlist").value;//��ȡƴ�ӵ��������
		var qryusestr=document.getElementById("qryusestr").value;
		txtareaarr.push(conditoionlist);//���ƴ�������������� ������ʾ
		arrall.push(qryusestr);//���ڱ���
		document.getElementById("conditionName8").innerHTML=setAddCon(txtareaarr);//��ʾ�������
		var arr=qryusestr.split(',');
		txtareaarrCode.push(arr[1]);
	}
	
}
function addtolistfunc(){//��ӵ��б�
	var tabType=document.getElementById('tabType').value;
	if(tabType=='1'){
		var col_data_type_should=document.getElementById("col_data_type_should").value;
		var col_data_type=document.getElementById("col_data_type").value;
		var code_type=document.getElementById("code_type").value;
		var condition=document.getElementById("conditionName5").value;
		if(condition==null||condition==''){
			alert('��ѡ����������!');
			return;
		}
		
		if(condition.indexOf('null')==-1){
			if(code_type!=null&&code_type.trim()!=''&&code_type.trim()!='null'){
				var conditionName6=document.getElementById("conditionName6").value;
				var conditionName71=document.getElementById("conditionName71").value;
				if(conditionName6==null||conditionName6==''){
					alert('��ѡ��ֵһ!');
					return;
				}
				if(condition.indexOf('between')!=-1){
					if(conditionName71==null||conditionName71==''){
						alert('��ѡ��ֵ��!');
						return;
					}
				}
			} else if("date"==col_data_type_should||col_data_type=='t'||col_data_type=='T'){
				var conditionName61=document.getElementById("conditionName61").value;
				var conditionName7=document.getElementById("conditionName7").value;
				if(conditionName61==null||conditionName61==''){
					alert('��ѡ��ֵһ!');
					return;
				}
				if(condition.indexOf('between')!=-1){
					if(conditionName7==null||conditionName7==''){
						alert('��ѡ��ֵ��!');
						return;
					}
				}
			} else if("number"==col_data_type_should){
				var conditionName6111=document.getElementById("conditionName6111").value;
				var conditionName711=document.getElementById("conditionName711").value;
				if(conditionName6111==null||conditionName6111==''){
					alert('������ֵһ!');
					return;
				}
				if(condition.indexOf('between')!=-1){
					if(conditionName711==null||conditionName711==''){
						alert('������ֵ��!');
						return;
					}
				}
			}else{
				var conditionName611=document.getElementById("conditionName611").value;
				if(conditionName611==null||conditionName611==''){
					alert('������ֵһ!');
					return;
				}
			}
		}
	}
	radow.doEvent("addtolistfunc");
}
function conditionclear(){//�������b0194_combo
	document.getElementById("conditionName5_combo").value="";
	document.getElementById("conditionName5").value="";
	document.getElementById("conditionName6").value="";
	document.getElementById("conditionName6_combotree").value="";
	document.getElementById("conditionName71").value="";
	document.getElementById("conditionName71_combotree").value="";
	radow.doEvent("conditionclear");
}
function setconditionName4(){
	////������ָ���ֵ
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
function rowFldeDbClick(grid,rowIndex,colIndex,event){//˫��������Ϣ��
	dbRowFLdCon(grid,rowIndex,colIndex,event);
	var record = grid.store.getAt(rowIndex);//���˫���ĵ�ǰ�еļ�¼
	var col_data_type=record.get("col_data_type");//����������
	var col_data_type_should=record.get("col_data_type_should");//ָ���������2
	var code_type=record.get("code_type");//ָ���������
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
	if(code_type!=null&&code_type!=''&&code_type!='null'){//����ѡ�� between and  ��like  
		//��ѯ����
		radow.doEvent("code_type_value1",code_type);
	}else if(col_data_type_should=='date'||col_data_type=='t'){//�������ֶ�   ��between and  ��like 
		//��ѯ����
		radow.doEvent("selectValueList");
	}else if(col_data_type_should==""
			||col_data_type_should=="clob"||col_data_type_should=="varchar2"
			||col_data_type_should=="null"||col_data_type_should==null
			||col_data_type_should.length==""||col_data_type_should.length==" "){//�ı� ��like ��between and
		//��ѯ����
		radow.doEvent("selectValueListNobt");
	}else if(col_data_type_should=="number"){//���� ��like  ��between and
		//��ѯ����
		radow.doEvent("selectValueListLikeBt");
	}
}
function dbRowFLdCon(grid,rowIndex,colIndex,event){
	var record = grid.store.getAt(rowIndex);//���˫���ĵ�ǰ�еļ�¼
	var col_name1=record.get("showcolname");//ָ������
	var col_name=record.get("col_name");//ָ������
	var table_code=record.get("table_code");
	var col_code=record.get("col_code");//
	var col_data_type=record.get("col_data_type");//����������
	var col_data_type_should=record.get("col_data_type_should");//ָ���������2
	var code_type=record.get("code_type");//ָ���������
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
		&&code_type!=''&&code_type!='null'){//����ѡ ��between and  ��like  
		//��ѯ����
		document.getElementById("value1111").style.display='none';//number
		document.getElementById("value111").style.display='none';//text
		document.getElementById("value11").style.display='none';//date
		document.getElementById("value1").style.display='block';//select
		document.getElementById("value2").style.display='none';//date
		document.getElementById("value21").style.display='block';//text
		document.getElementById("value211").style.display='none';//number
		
	}else if(col_data_type_should=='date'||col_data_type=='t'||col_data_type=='T'){//�������ֶ�   ��between and  ��like 
		//��ѯ����
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
			||col_data_type_should.length==""||col_data_type_should.length==" "){//�ı� ��like ��between and
		//��ѯ����
		document.getElementById("value1").style.display='none';
		document.getElementById("value11").style.display='none';
		document.getElementById("value111").style.display='block';
		document.getElementById("value1111").style.display='none';
		document.getElementById("value2").style.display='none';
		document.getElementById("value21").style.display='none';
		document.getElementById("value211").style.display='block';
	}else if(col_data_type_should=="number"){//���� ��like  ��between and
		//��ѯ����
		document.getElementById("value1").style.display='none';
		document.getElementById("value11").style.display='none';
		document.getElementById("value111").style.display='none';
		document.getElementById("value1111").style.display='block';
		document.getElementById("value2").style.display='none';
		document.getElementById("value21").style.display='none';
		document.getElementById("value211").style.display='block';
	}
}
function backgroundFunc(obj){//���������¼�
	var spanarr=document.getElementById("conditionName8").children;//��ȡ���е��������
	for(i=0;i<spanarr.length;i++){//ѭ���������
		spanarr[i].style.background="";//������е������������
	}
	areastr=obj.id;//��ȡѡ�е���������
	obj.style.background="pink";//����ѡ�е���������
}

function valuefivefunc(){//�������Ÿı��¼� 
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
	if(conditionName5.indexOf('between')!=-1){//ѡ����between and����
		if(code_type!=null&&code_type.trim()!=''&&code_type.trim()!='null'){
			//����select�ؼ��ɱ༭
			document.getElementById("value2").style.display='none';//date
			document.getElementById("value21").style.display='block';//select
			document.getElementById("value211").style.display='none';//number
			//����select�ؼ��ɱ༭
			radow.doEvent("setValue21Disable",code_type);
		}else if(col_data_type=='T'||col_data_type=='t'){//����������date��
			//����date�ؼ��ɱ༭
			radow.doEvent("setValue2Disable");
		}else if(col_data_type_should==""
			||col_data_type_should=="clob"||col_data_type_should=="varchar2"
				||col_data_type_should=="null"||col_data_type_should==null
				||col_data_type_should.length==""||col_data_type_should.length==" "){
			radow.doEvent("setValue111Disable");
		}else{
			//����number�ؼ��ɱ༭
			radow.doEvent("setValue211Disable");
		}
	}else if(conditionName5.indexOf('null')!=-1){
		//���ò��ɱ༭
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
	//У������
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
