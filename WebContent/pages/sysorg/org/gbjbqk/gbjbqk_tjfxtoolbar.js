////���ֵ����¼� 
function opensubowin(col1, row1){
	myMouseDClick(col1, row1);
}

//ȫ������ 
function def(){
	document.getElementById("wenzi").style.display='none';
	document.getElementById("bingzhuang").style.display='none';
	document.getElementById("fancha_div").style.display='none';
	document.getElementById("createtable").style.display='none';
}
//�������� 
var dcbz_flag=1;//��ǰ��ʾҳ�� 1 ���

function exportData(){
	if(dcbz_flag==1){//���
		if(tjfx_dj_flag==false){//û�е��ͳ�Ʒ���������Ϊ�գ���������
			return;
		}
		DCellWeb_tj.ExportExcelDlg();
	}else if(dcbz_flag==2){//����
		if(tjfx_dj_flag==false){//û�е��ͳ�Ʒ���������Ϊ�գ���������
			return;
		}
		 var tmp = document.createElement("form"); 
	     var action = "GbjbqkDownServlet?method=Gbjbqk"; 
	     tmp.action = action; 
	     tmp.method = "post"; 
	     tmp.target="_blank";
	     
	     var newElement = document.createElement("input");
	     newElement.setAttribute("name","wenzims_str");
	     newElement.setAttribute("type","hidden");
	     newElement.setAttribute("value",wenzi_str);
	     tmp.appendChild(newElement); 
	     
	     document.body.appendChild(tmp); 
	     tmp.submit();
	     return tmp;
	}else if(dcbz_flag==3){//��״ͼ
		
		/* window.open("ProblemDownServlet?method=downFiletj2"); */
	}else if(dcbz_flag==4){//�б�
		
	}
}
//��ʾ���� 
var flag_cwz=false;
function cwenzi(){
	def();
	document.getElementById("wenzi").style.display='block';
	dcbz_flag=2;//�� tab ��ʾ��־
	if(flag_cwz==true){
		return;
	}
	if(tjfx_dj_flag==false){//δͳ��
		return;
	}
	wenzi_func();
	flag_cwz=true;//�������־
}
var wenzi_str="";	// //����
function wenzi_func(){
	// //�ܼ�
	var obj=document.getElementById("wenzi");
	// //�ɲ������������
	wenzi_str="<br><p style='width:100%;text-align:center;'><b>�ɲ������������"+title_zj+"</b></p><br><b>�������:</b><br><br>"
	wenzi_str=wenzi_str+"������"+title_zj+"<font onclick='opensubowin(\"3\",\"4\")' style='cursor:hand;color:#75acd2;'> <u>"+DCellWeb_tj.GetCellString(3, 4, 0)+"</u> </font>�� ����: ";
	var num_wz=0;
	for(var i=col_xho_start;i<=col_xho_end;i++){//ѭ����
		   if(i%2==1){
				continue;
			}
		   num_wz=DCellWeb_tj.GetCellString2(i, 4, 0);
			if(num_wz==0||num_wz==""||num_wz==null||num_wz==" "||num_wz=="undefined"){
				
			}else{//#75acd2
				if(col_xho_end==i){
					wenzi_str=wenzi_str+(tjxm_arr[i]=="Ů"?"Ů��":tjxm_arr[i])+"<font onclick='opensubowin(\""+i+"\",\"4\")' style='cursor:hand;color:#75acd2;'> <u>"+num_wz+"</u> </font>"+"�� "
				}else{
					wenzi_str=wenzi_str+(tjxm_arr[i]=="Ů"?"Ů��":tjxm_arr[i])+"<font onclick='opensubowin(\""+i+"\",\"4\")' style='cursor:hand;color:#75acd2;'> <u>"+num_wz+"</u> </font>"+"�� "
				}
			}
	}
	for(var i=col_xhj_start;i<=col_xhj_end;i++){//ѭ����
		   if(i%2==0){
				continue;
			}
		   num_wz=DCellWeb_tj.GetCellString2(i, 4, 0);
			if(num_wz==0||num_wz==""||num_wz==null||num_wz==" "||num_wz=="undefined"){
				
			}else{//#75acd2
				wenzi_str=wenzi_str+(tjxm_arr[i]=="Ů"?"Ů��":tjxm_arr[i])+"<font onclick='opensubowin(\""+i+"\",\"4\")' style='cursor:hand;color:#75acd2;'> <u>"+num_wz+"</u> </font>"+"�� "
			}
	}
	wenzi_str=wenzi_str+"<br>";
	for(var i=0;i<array_row.length;i++){
		zwlb_fz_func(parseInt(array_row[i].split(",")[0]),parseInt(array_row[i].split(",")[1]));
	}
	// //����
	obj.innerHTML=wenzi_str;
}
////����ͳ�� ѭ����� 
function zwlb_fz_func(start,end){//start ��ʼ�� end ������
	var v_wz_zwlb="";////ְ��㼶���� 
	var v_temp_lb_zx=0;////cellС��ֵ 
	var num_wz=0;////cellֵ 
	
	//С�� ͳ��
	v_temp_lb_zx=DCellWeb_tj.GetCellString2(3, (parseInt(start)-1), 0);
	if(v_temp_lb_zx==0||v_temp_lb_zx==""||v_temp_lb_zx==null||v_temp_lb_zx==" "||v_temp_lb_zx=="undefined"){
		
	}else{
		var zwlb_mc=DCellWeb_tj.GetCellString2(1, parseInt(start), 0);//��������
		wenzi_str=wenzi_str+"<br><br><p style='width:100%;text-align:left;'><b>"+zwlb_mc+":</b></p><br>"
		wenzi_str=wenzi_str+"������<font onclick='opensubowin(\"3\",\""+(parseInt(start)-1)+"\")' style='cursor:hand;color:#75acd2;'> <u>"+v_temp_lb_zx+"</u> </font>�� ����: ";//С��
		
		for(var j=col_xho_start;j<=col_xho_end;j++){//����
			 if(j%2==1){
					continue;
			}
			num_wz=DCellWeb_tj.GetCellString2(j, parseInt(start-1), 0);
			if(num_wz==0||num_wz==""||num_wz==null||num_wz==" "||num_wz=="undefined"){
				//Ϊ0��ͳ��
			}else{//��Ϊ0ͳ��
				if(col_xho_end==j){
					wenzi_str=wenzi_str+(tjxm_arr[j]=="Ů"?"Ů��":tjxm_arr[j])+"<font onclick='opensubowin(\""+j+"\",\""+parseInt(start-1)+"\")' style='cursor:hand;color:#75acd2;'> <u>"+num_wz+"</u> </font>"+"�� "
				}else{
					wenzi_str=wenzi_str+(tjxm_arr[j]=="Ů"?"Ů��":tjxm_arr[j])+"<font onclick='opensubowin(\""+j+"\",\""+parseInt(start-1)+"\")' style='cursor:hand;color:#75acd2;'> <u>"+num_wz+"</u> </font>"+"�� "
				}
			}
		}
		for(var j=col_xhj_start;j<=col_xhj_end;j++){//ѭ����
			if(j%2==0){
				continue;
			}
			num_wz=DCellWeb_tj.GetCellString2(j, parseInt(start-1), 0);
			if(num_wz==0||num_wz==""||num_wz==null||num_wz==" "||num_wz=="undefined"){
				//Ϊ0��ͳ��
			}else{//��Ϊ0ͳ��
				wenzi_str=wenzi_str+(tjxm_arr[j]=="Ů"?"Ů��":tjxm_arr[j])+"<font onclick='opensubowin(\""+j+"\",\""+parseInt(start-1)+"\")' style='cursor:hand;color:#75acd2;'> <u>"+num_wz+"</u> </font>"+"�� "
			}
		}
		wenzi_str=wenzi_str+"<br><br>";
	}
	wenzi_str=wenzi_str+"";
	//����ͳ�� 
	var v_wz_zwlb="";////ְ��㼶���� 
	var v_temp_lb_zx=0;////cellС��ֵ 
	var num_wz=0;////cellֵ 
	for(var i=start;i<=end;i++){// //���� 
		v_temp_lb_zx=DCellWeb_tj.GetCellString2(3, i, 0);
		if(v_temp_lb_zx==0||v_temp_lb_zx==""||v_temp_lb_zx==null||v_temp_lb_zx==" "||v_temp_lb_zx=="undefined"){
			////С��Ϊ0,��ͳ�� 
		}else{////С�Ʋ�Ϊ�� 
			v_wz_zwlb="<b>"+DCellWeb_tj.GetCellString2(2, i, 0)+"</b>";//ְ��㼶���� 
			wenzi_str=wenzi_str+v_wz_zwlb+":<br><br>"
			wenzi_str=wenzi_str+"������<font onclick='opensubowin(\"3\",\""+i+"\")' style='cursor:hand;color:#75acd2;'> <u>"+v_temp_lb_zx+"</u> </font>�� ����: ";//С�� 
			for(var j=col_xho_start;j<=col_xho_end;j++){//����
				 if(j%2==1){
						continue;
				}
				num_wz=DCellWeb_tj.GetCellString2(j, i, 0);
				if(num_wz==0||num_wz==""||num_wz==null||num_wz==" "||num_wz=="undefined"){
					//Ϊ0��ͳ��
				}else{//��Ϊ0ͳ��
					if(col_xho_end==j){
						wenzi_str=wenzi_str+(tjxm_arr[j]=="Ů"?"Ů��":tjxm_arr[j])+"<font onclick='opensubowin(\""+j+"\",\""+i+"\")' style='cursor:hand;color:#75acd2;'> <u>"+num_wz+"</u> </font>"+"�� "
					}else{
						wenzi_str=wenzi_str+(tjxm_arr[j]=="Ů"?"Ů��":tjxm_arr[j])+"<font onclick='opensubowin(\""+j+"\",\""+i+"\")' style='cursor:hand;color:#75acd2;'> <u>"+num_wz+"</u> </font>"+"�� "
					}
				}
			}
			for(var j=col_xhj_start;j<=col_xhj_end;j++){//ѭ����
				if(j%2==0){
					continue;
				}
				num_wz=DCellWeb_tj.GetCellString2(j, i, 0);
				if(num_wz==0||num_wz==""||num_wz==null||num_wz==" "||num_wz=="undefined"){
					//Ϊ0��ͳ��
				}else{//��Ϊ0ͳ��
					wenzi_str=wenzi_str+(tjxm_arr[j]=="Ů"?"Ů��":tjxm_arr[j])+"<font onclick='opensubowin(\""+j+"\",\""+i+"\")' style='cursor:hand;color:#75acd2;'> <u>"+num_wz+"</u> </font>"+"�� "
				}
			}
			wenzi_str=wenzi_str+"<br><br>";
		}
		
	}
}
//��״ͼ��ʵ�� 
function setbingzhuang(ii){
	// // ����׼���õ�dom����ʼ��echartsʵ��
	try{
		var myChart = echarts.init(document.getElementById('bingzhuang'+ii));
		
		//��ȡ��ǰ��ַ���磺 http://localhost:8083/myproj/view/my.jsp
		var curWwwPath=window.document.location.href;   
		//��ȡ������ַ֮���Ŀ¼���磺 myproj/view/my.jsp 
		var pathName=window.document.location.pathname;  
		var pos=curWwwPath.indexOf(pathName);  
		//��ȡ������ַ���磺 http://localhost:8083  
		var localhostPaht=curWwwPath.substring(0,pos);
		 //��ȡ��"/"����Ŀ�����磺/myproj  
		 var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);  
		 //�õ��� http://localhost:8083/myproj  
		 var realPath=localhostPaht+projectName;  
		
		// // ʹ�ø�ָ�����������������ʾͼ�� 
		myChart.setOption({ 
			 
			title : {
		        text: title+title_zj,// //����
		        subtext: '',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        left: 'left',
		        data: zwlb_zhgll_mc// //ͳ�����
		    },
		    /*toolbox: {
            show: true,
            feature: {
            	myTool2: { 
            		show: true,
            		title: '�Զ�����չ����1',
            		icon:'image://'+realPath+'/images/user.png', 
            		onclick: function (){
            			

            			try{
            				//zhgll_func(start,end);
            				//alert(zhgll_json);
            				wenzi_str=document.getElementById('bingzhuang'+ii).outerHTML;
            				var tmp = document.createElement("form"); 
            			     var action = "GbjbqkDownServlet?method=Gbjbqk"; 
            			     tmp.action = action; 
            			     tmp.method = "post"; 
            			     tmp.target="_blank";
            			     
            			     var newElement = document.createElement("input");
            			     newElement.setAttribute("name","wenzims_str");
            			     newElement.setAttribute("type","hidden");
            			     newElement.setAttribute("value",wenzi_str);
            			     tmp.appendChild(newElement); 
            			     
            			     document.body.appendChild(tmp); 
            			     tmp.submit();
            			     return tmp;
            			}catch(err){
            				alert(err);
            			}
                	}
            	}
            }
        },*/
		    series : [
		        {
		            name: '����Ա����ϵͳ ',
		            type: 'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:zhgll_json,// //��ͼ����
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                },
				        normal:{ 
			                 label:{ 
			                     show: true, 
			                     formatter: '{b} : {c} ({d}%)' 
			                 }, 
			                 labelLine :{show:true} 
			             }
		            }
		        }
		    ]
			
			});
	}catch(err){
	}
}
//��ʾ��״ͼ�� 
var flaf_cbz=false;
function cbingzhuang(){
	def();
	document.getElementById("bingzhuang").style.display='block';
	dcbz_flag=3;//�� tab ��ʾ��־
	if(flaf_cbz==true){
		return;
	}
	if(tjfx_dj_flag==false){//δͳ��
		return;
	}
	for(var j=0;j<array_row.length;j++){
		zhgll_func(parseInt(array_row[j].split(",")[0]),parseInt(array_row[j].split(",")[1]));//�������� 
		
		setbingzhuang((j+1)+'');//���ɱ�ͼ
	}
	flaf_cbz=true;//�� tab�������־
}

var zwlb_zhgll_mc=[];// //ְ�������� 
var zhgll_json=[];// //ְ�������� �� ���� 
var title="";// //ְ����� 
function zhgll_func(start,end){// //start ��ʼ�� end������
	var value="";
	var name="";
	zhgll_json=[];
	zwlb_zhgll_mc=[];
	for(var i=start;i<=end;i++){
		 value=DCellWeb_tj.GetCellString(3,i,0);
		 name=DCellWeb_tj.GetCellString(2,i,0);
		if(value=="" || value==" " ||value=="undefined"||value==null){
			 value=0;
		 }
		zwlb_zhgll_mc.push(name);
		 zhgll_json.push(CreateJson(value,name)); 
	 }
	title=DCellWeb_tj.GetCellString(1,start,0);
}
////����json�� 
function CreateJson(value,name){// //JS �����ǲ���Ҫ�������Ե� 
	var jsonStr = {};
	jsonStr.value = value;
	jsonStr.name = name;
	return jsonStr;
}
//�б� 
function liebiao_l(){
	def();
	document.getElementById("fancha_div").style.display='block';
	dcbz_flag=4;//�� tab ��ʾ��־
}
