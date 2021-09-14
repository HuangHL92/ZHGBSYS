<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<script type="text/javascript" src="<%=request.getContextPath() %>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/echarts.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/sysorg/org/gbjbqk/gbjbqk.js" ></script>
<script src="<%=request.getContextPath()%>/js/cllauth.js"> </script>
<%@include file="/comOpenWinInit.jsp" %>


<%--ҳ�����Ϸ������� ����-����-����-��״ͼ --%>
<%@include file="/pages/sysorg/org/gbjbqk/Gbjbqk_ToolBar.jsp" %>

<%--ͳ�Ʊ��� --%>
<%@include file="/pages/sysorg/org/gbjbqk/Gbjbqk_form.jsp" %>

<%--ͳ���б� --%>
<%@include file="/pages/sysorg/org/gbjbqk/Gbjbqk_List.jsp" %>

<%--ͳ�Ʊ�״ͼ --%>
<%@include file="/pages/sysorg/org/gbjbqk/Gbjbqk_Bzt.jsp" %>

<%--�����������ֶ� --%>
<%@include file="/pages/sysorg/org/gbjbqk/Gbjbqk_WAndHT.jsp" %>

<odin:window src="/" id="gbjbqktjsubid" width="1300" height="550"></odin:window>
<script for="DCellWeb1" event="MouseDClick(col, row)" language="JavaScript">
	myMouseDClick(col, row);
</script>
<script type="text/javascript" language="javascript">
	setAuth(document.getElementById("DCellWeb1"));
</script>
<script type="text/javascript">
var tjfx_dj_flag=false;
function initTj_func(){//���ͳ�Ʒ���
	if(tjfx_dj_flag==false){
		radow.doEvent("initTj");
	}else{
		ctable();//��ʾ����
	}
}
// //����˫���¼� 
function myMouseDClick(col1, row1) {
	//�������� 
	if(col1==0||col1==""||col1==" "||col1==null||col1=="undefined"||col1<3){
		return;
	}
	if(row1==0||row1==""||row1==" "||row1==null||row1=="undefined"||row1<4){
		return;
	}
	//��ֵΪ0 �� ��
	var value=DCellWeb_tj.GetCellString(col1,row1,0);
	if(value==0||value==""||value==" "||value==null||value=="undefined"){
		return;
	}
	//ռ��
	if(col1>=col_xho_start&&col1<=col_xho_end){//�ٷֱ� ������
		if(col1%2==1){
			return;
		}
	}
	
	var title1="";
	var title2="";
	title1=DCellWeb_tj.GetCellString(2,row1,0);
	for(var i=0;i<array_row.length;i++){//С��
		if(row1==(parseInt(array_row[i].split(",")[0])-1)){
			title1=DCellWeb_tj.GetCellString(1,row1,0);
		}
	}
	if(row1==4){//�ܼ�
		title1=DCellWeb_tj.GetCellString(1,row1,0);
	}
	if(col1==3){
		title2=DCellWeb_tj.GetCellString(col1,2,0);
	}else{
		title2=DCellWeb_tj.GetCellString(col1,3,0);
	}
	var title=title1+"-"+title2;
	var dwid=document.getElementById("subWinIdBussessId2").value;
	if(dcbz_flag==2){//������ʾ��־
		dwid=dwid+"$"+''+"$"+col1+"$"+row1+"$"+title;
	}else{
		dwid=dwid+"$"+$('#zwlb_combo').attr("value")+"$"+col1+"$"+row1+"$"+title;
	}
	//dwid=dwid+"$"+col1+"$"+row1+"$"+title;
	$h.openWin('GbjbqkxzccSub','pages.sysorg.org.gbjbqk.GbjbqkxzccSub',title+'-��άͳ��',1300,550,dwid,'<%=request.getContextPath()%>');
}

 // //���ֵ����¼� 
function opensubowin(col1, row1){
	myMouseDClick(col1, row1);
}

 
 // //�ۺϹ����๫��Ա 
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
// //����json�� 
function CreateJson(value,name){// //JS �����ǲ���Ҫ�������Ե� 
	var jsonStr = {};
	jsonStr.value = value;
	jsonStr.name = name;
	return jsonStr;
}
 
	// ��״ͼ��ʵ�� 
	function setbingzhuang(ii){
		// // ����׼���õ�dom����ʼ��echartsʵ��
		try{
			var myChart = echarts.init(document.getElementById('bingzhuang'+ii));
			// // ʹ�ø�ָ�����������������ʾͼ���� 
			myChart.setOption({ 
				 
				title : {
			        text: title+"(��ְ���)",// //����
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
			    series : [
			        {
			            name: '����Ա����ϵͳ',
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

	// ȫ������ 
	function def(){
		document.getElementById("wenzi").style.display='none';
		document.getElementById("bingzhuang").style.display='none';
		document.getElementById("createtable").style.display='none';
		document.getElementById("fancha_div").style.display='none';
	}
	// �б� 
	function liebiao_l(){
		def();
		document.getElementById("fancha_div").style.display='block';
		dcbz_flag=4;//�� tab ��ʾ��־
	}
	
	function loding(){
		ctable();
	}
	// ��ʾ���� 
	function ctable(){
		def();
		document.getElementById("createtable").style.display='block';
		dcbz_flag=1;//�� tab ��ʾ��־
	}
	// ��ʾ��״ͼ�� 
	var flaf_cbz=false;
	function cbingzhuang(){
		def();
		document.getElementById("bingzhuang").style.display='block';
		dcbz_flag=3;//�� tab ��ʾ��־
		if(flaf_cbz==true){
			return;
		}
		if(tjfx_dj_flag==false){//Ϊ���ͳ�Ʒ���
			return;
		}
		for(var j=0;j<array_row.length;j++){
			zhgll_func(parseInt(array_row[j].split(",")[0]),parseInt(array_row[j].split(",")[1]));//�������� 
			
			setbingzhuang((j+1)+'');//���ɱ�ͼ
		}
		
		flaf_cbz=true;//�� tab�������־
		
		
	}
	// ��ʾ���� 
	var flag_cwz=false;
	function cwenzi(){
		def();
		document.getElementById("wenzi").style.display='block';
		dcbz_flag=2;//�� tab ��ʾ��־
		if(flag_cwz==true){
			return;
		}
		if(tjfx_dj_flag==false){//Ϊ���ͳ�Ʒ���
			return;
		}
		//wenzi_func();
		
		flag_cwz=true;//�������־
		
	}
	// //ͳ����Ŀ����
	var tjxm_arr = new Array(
			"",
			"",
			"",
			"",
			"����1��",
			"",
			"1��",
			"",
			"2��",
			"",
			"3��",
			"",
			"4��",
			"",
			"5��",
			"",
			"6��",
			"",
			"7��",
			"",
			"8��",
			"",
			"9��",
			"",
			"10��",
			"",
			"11��",
			"",
			"12��",
			"",
			"13��",
			"",
			"14��",
			"",
			"15��",
			"",
			"16��",
			"",
			"17��",
			"",
			"18��",
			"",
			"19��",
			"",
			"20�꼰����",
			""
			);
	var wenzi_str="";
	// //����
	function wenzi_func(){
		// //�ܼ�
		var obj=document.getElementById("wenzi");
		// //�ɲ������������
		wenzi_str="<br><p style='width:100%;text-align:center;'><b>�ɲ������������(��ְ���)</b></p><br><b>�������:</b><br><br>"
		wenzi_str=wenzi_str+"������(��ְ���)<font onclick='opensubowin(\"3\",\"4\")' style='cursor:hand;color:#75acd2;'> <u>"+DCellWeb_tj.GetCellString(3, 4, 0)+"</u> </font>�� ����: ";
		var num_wz=0;
		for(var i=col_xho_start;i<=col_xho_end;i++){//ѭ����
			   if(i%2==1){
					continue;
				}
			   num_wz=DCellWeb_tj.GetCellString2(i, 4, 0);
				if(num_wz==0||num_wz==""||num_wz==null||num_wz==" "||num_wz=="undefined"){
					
				}else{//#75acd2
					wenzi_str=wenzi_str+tjxm_arr[i]+"<font onclick='opensubowin(\""+i+"\",\"4\")' style='cursor:hand;color:#75acd2;'> <u>"+num_wz+"</u> </font>"+"�� "
				}
		}
		wenzi_str=wenzi_str+"<br>";
		//С��
		for(var i=0;i<array_row.length;i++){
			zwlb_fz_func(parseInt(array_row[i].split(",")[0]),parseInt(array_row[i].split(",")[1]));
		}
		// //����
		obj.innerHTML=wenzi_str;
	}
	////����ͳ�� ѭ����� 
	function zwlb_fz_func(start,end){// //start ��ʼ�� end ������
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
					wenzi_str=wenzi_str+tjxm_arr[j]+"<font onclick='opensubowin(\""+j+"\",\""+parseInt(start-1)+"\")' style='cursor:hand;color:#75acd2;'> <u>"+num_wz+"</u> </font>"+"�� "
				}
			}
			
			wenzi_str=wenzi_str+"<br><br>";
		}
		//wenzi_str=wenzi_str+"<br>";
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
						wenzi_str=wenzi_str+tjxm_arr[j]+"<font onclick='opensubowin(\""+j+"\",\""+i+"\")' style='cursor:hand;color:#75acd2;'> <u>"+num_wz+"</u> </font>"+"�� "
					}
				}
				wenzi_str=wenzi_str+"<br><br>";
			}
			
		}
	}

	// �������� 
	var dcbz_flag=1;//��ǰ��ʾҳ�� 1 ����
	
	function exportData(){
		if(dcbz_flag==1){//����
			if(tjfx_dj_flag==false){//û�е��ͳ�Ʒ���������Ϊ�գ���������
				return;
			}
			DCellWeb_tj.ExportExcelDlg();
		}else if(dcbz_flag==2){//����
			if(tjfx_dj_flag==false){//û�е��ͳ�Ʒ���������Ϊ�գ���������
				return;
			}
			//document.getElementById("myform").submit();  
			 var tmp = document.createElement("form"); 
		     var action = "GbjbqkDownServlet?method=Gbjbqkxzcc"; 
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
			//window.open("GbjbqkDownServlet?method=Gbjbqk");
		}else if(dcbz_flag==3){//��״ͼ
			/* window.open("ProblemDownServlet?method=downFiletj2"); */
		}else if(dcbz_flag==4){//�б�
			
		}
	}
	var ctpath="<%=request.getContextPath()%>";
	Ext.onReady(function openfile(){
		    try{
				document.getElementById("DCellWeb1").openfile(ctpath+"\\template\\gbjbqktj3xzcc.cll","");
		    }catch(err){
		    	//alert("��ʾ:�����ذ�װ�������!��ʹ��IE�����(����ģʽ)��(����ϵϵͳ����ԱԶ��Э��)!");
		    }
	   });
	
	//����json����
	var json_obj="";
	//��ʼ������ 
	var DCellWeb_tj="";
	var col_xho_start=4;
	var col_xho_end=45;
	//json�ַ���
   function json_func(){
	   //jsonstr,lengthstr
	   var jsonstr=document.getElementById("jsonString_str").value;
	   DCellWeb_tj=document.getElementById("DCellWeb1");//��ȡ�������
	   var obj ="";
	   try{
		   obj = eval('(' + jsonstr + ')');
	   }catch(err){
		   alert("json���ݸ�ʽ���󣬽���ʧ�ܣ�����ϵϵͳ����Ա�޸�����!");
	   }
	   json_obj=obj;
	   DCellWeb_tj.SetFixedCol(1, 2); 
	   DCellWeb_tj.SetFixedRow(1, 3);
	   for(var i=0;i<obj.length;i++){////�������� ���� ѭ��
	   	   var a0221=obj[i].a0221;//����
	   	   if(a0221=='undefined' || a0221=="" || a0221==null||a0221==" "){
	   			continue;//������Ϊ��
	   	   }
	   	   a0221=map[a0221];//����
	   	   if(a0221=='undefined' || a0221=="" || a0221==null||a0221==" "){
	   			continue;//js û�����ô�������
	   	   }
	   	   var i_col=4;
	   		DCellWeb_tj.SetCellString(
		   			4,a0221,0,obj[i].xzccxy1==0?"":obj[i].xzccxy1);//����1��
		   	i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].xzccdy1==0?"":obj[i].xzccdy1);//1��
		   i_col=i_col+2;		
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].xzccdy2==0?"":obj[i].xzccdy2);//2��
		   	i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].xzccdy3==0?"":obj[i].xzccdy3);//3��
		   	i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].xzccdy4==0?"":obj[i].xzccdy4);//4��
		   	i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].xzccdy5==0?"":obj[i].xzccdy5);//5��
		   			i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].xzccdy6==0?"":obj[i].xzccdy6);//6��
  		   	i_col=i_col+2;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].xzccdy7==0?"":obj[i].xzccdy7);//7��
  		   	i_col=i_col+2;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].xzccdy8==0?"":obj[i].xzccdy8);//8��
  		   	i_col=i_col+2;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].xzccdy9==0?"":obj[i].xzccdy9);//9��
  		   	i_col=i_col+2;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].xzccdy10==0?"":obj[i].xzccdy10);//10��
  		   	i_col=i_col+2;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].xzccdy11==0?"":obj[i].xzccdy11);//11��
 		   	i_col=i_col+2;
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,obj[i].xzccdy12==0?"":obj[i].xzccdy12);//12��
			i_col=i_col+2;
			DCellWeb_tj.SetCellString(
					i_col,a0221,0,obj[i].xzccdy13==0?"":obj[i].xzccdy13);//13��
			i_col=i_col+2;
			DCellWeb_tj.SetCellString(
					i_col,a0221,0,obj[i].xzccdy14==0?"":obj[i].xzccdy14);//14��
			i_col=i_col+2;
			DCellWeb_tj.SetCellString(
					i_col,a0221,0,obj[i].xzccdy15==0?"":obj[i].xzccdy15);//15��
		    i_col=i_col+2;
		    DCellWeb_tj.SetCellString(
		    		i_col,a0221,0,obj[i].xzccdy16==0?"":obj[i].xzccdy16);//16��
	   		i_col=i_col+2;
	   		DCellWeb_tj.SetCellString(
	   				i_col,a0221,0,obj[i].xzccdy17==0?"":obj[i].xzccdy17);//17��
		   	i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].xzccdy18==0?"":obj[i].xzccdy18);//18��
		   	i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].xzccdy19==0?"":obj[i].xzccdy19);//19��
		   	i_col=i_col+2;
		    DCellWeb_tj.SetCellString(
		    		i_col,a0221,0,obj[i].xzccdy20==0?"":obj[i].xzccdy20);//20��
		   	//�ϼ�
			DCellWeb_tj.SetCellString(
		   			3,a0221,0, obj[i].heji==0?"":obj[i].heji);
		}/// for end
		//С��
	   for(var i=0;i<array_row.length;i++){
			total_tj(parseInt(array_row[i].split(",")[0])-1,
					parseInt(array_row[i].split(",")[0]),
					parseInt(array_row[i].split(",")[1]));
		}
		//�ܼ� 
		total_zj();
		
		//ռ��
		zhanbi();
		//����ռ��
		yincangzb();
		//���� ȫ��Ϊ0����
		displayzeroinit()
		//����ҳ�治�ɱ༭
		DCellWeb_tj.WorkbookReadonly=true;
		//��״ͼ 
		//setTimeout('zwlb_zzt()',100);
		tjfx_dj_flag=true;//���ͳ�Ʒ�����־ 
		ctable();//��ʾ����
		document.getElementById( "bar_div").style.display= "block";//��ʾ������
		//��������grid�߶�
		try{
			//var grid = odin.ext.getCmp('gridfc');
			//grid.setHeight(487);
		}catch(err){
			
		}
		
		wenzi_func();
   }
   
   //����ռ��
   function yincangzb(){
	   for(var i=col_xho_start;i<=col_xho_end;i++){//ѭ����
		   if(i%2==0){
				continue;
			}
		   DCellWeb_tj.SetColHidden(i, i);
		}
	   
   }
   
   //��ʾռ��
   function xszhanbi(){
	   DCellWeb_tj.SetColUnhidden(col_xho_start, col_xho_end);
   }
   //ռ�� ����
   function zhanbi(){
	   var temp_hj=0;
	   var temp_col_num=0;
	   var biliz=0;
	   var j_v=parseInt(array_row[array_row.length-1].split(",")[1]);
	   for(var j=4;j<=j_v;j++){//ѭ����
		 	//�ϼ�
		   temp_hj=DCellWeb_tj.GetCellString2(3, j, 0);
		   if(temp_hj=='undefined' || temp_hj=="" || temp_hj==null||temp_hj==" "||temp_hj==0){
				temp_hj=1;
		   }
		   for(var i=col_xho_start;i<=col_xho_end;i++){//ѭ����
			   if(i%2==1){
					continue;
				}
			    temp_col_num=DCellWeb_tj.GetCellString2(i, j, 0);
				if(temp_col_num=='undefined' || temp_col_num=="" || temp_col_num==null||temp_col_num==" "){
					temp_col_num=0;
				}
				biliz=Math.round(temp_col_num/temp_hj*10000)/100;
				DCellWeb_tj.SetCellString(i+1,j,0,biliz==0?"":biliz+"%" );
		   }
	   }
	
   }
   
   function zhanbi_xy_zwlb(){
	   var temp_hj=0;
	   var temp_col_num=0;
	   var biliz=0;
	   for(var j=4;j<=4;j++){//ѭ����
		 	//�ϼ�
			temp_hj=DCellWeb_tj.GetCellString2(3, j, 0);
			if(temp_hj=='undefined' || temp_hj=="" || temp_hj==null||temp_hj==" "||temp_hj==0){
				temp_hj=1;
			}
			for(var i=col_xho_start;i<=col_xho_end;i++){//ѭ����
				if(i%2==1){
	 				continue;
	 			}
				temp_col_num=DCellWeb_tj.GetCellString2(i, j, 0);
 				if(temp_col_num=='undefined' || temp_col_num=="" || temp_col_num==null||temp_col_num==" "){
 					temp_col_num=0;
 				}
 				biliz=Math.round(temp_col_num/temp_hj*10000)/100;
 				DCellWeb_tj.SetCellString(i+1,j,0,biliz==0?"":biliz+"%" );
			}
	   }
	  
   }
   
   //�ܼ�
   function total_zj(){
	   //��3�е���ͳ�� 
	   var temp_zj_=parseInt(total_zj_ittt(3));
		DCellWeb_tj.SetCellString(
   			3,4,0,temp_zj_==0?"":temp_zj_ );
		
	   var _zj_total=0;
	   for(var i=col_xho_start;i<=col_xho_end;i++){
  			if(i%2==1){//������
  				continue;
  			}
  			_zj_total=total_zj_ittt(i);
		    //ż���� С��
  			DCellWeb_tj.SetCellString(i,4,0,_zj_total==0?"":_zj_total );
  		}
	  
   }
   
   function total_zj_ittt(i){
	   var zj_total=0;
	   var zj_temp_total=0;
	   for(var j=0;j<array_row.length;j++){
		   zj_temp_total=DCellWeb_tj.GetCellString2(i, parseInt(array_row[j].split(",")[0])-1, 0);
		   if(zj_temp_total=='undefined' || zj_temp_total=="" || zj_temp_total==null||zj_temp_total==" "){
				zj_temp_total=0;
		   }
		   zj_total=zj_total+parseInt(zj_temp_total);
	   }
		
		return zj_total;
   }
 
	//ͳ��С��
	function total_tj(row_xj,row_s,row_e){//row_xj С��ͳ���� row_s��ʼ�� row_e ������
		
		//��3�е�������
		var temp_v_r=parseInt(col_total(3,row_s,row_e));
		DCellWeb_tj.SetCellString(
   			3,row_xj,0,temp_v_r==0?"":temp_v_r );
		
		var temp_v="";
		for(var i=col_xho_start;i<=col_xho_end;i++){
   			if(i%2==1){//������
   				continue;
   			}
		    //ż���� С��
   			temp_v=parseInt(col_total(i,row_s,row_e));
   			DCellWeb_tj.SetCellString(
		   			i,row_xj,0,temp_v==0?"":temp_v );
   		}
	}
	
	function col_total(col_xj,row_s,row_e){
	    var col_num=0;
	    var v=0;
 	    for(var i=row_s;i<=row_e;i++){
 			v=DCellWeb_tj.GetCellString2(col_xj, i, 0);
			if(v=='undefined' || v=="" || v==null||v==" "){
				v=0;
			}
 			col_num=col_num+parseInt(v);
	    }
 	    return col_num;
	}
  
  //���������д�4�п�ʼ
  function yx_all(){
	  DCellWeb_tj.SetRowHidden(4, parseInt(array_row[array_row.length-1].split(",")[1]));
  }
  
  function displayzeroinit(){
	  var v=0;
	  var j_v=parseInt(array_row[array_row.length-1].split(",")[1]);
		 for(var i=4;i<=j_v;i++){//��
			 v=DCellWeb_tj.GetCellString(3, i, 0);//�жϺϼ��Ƿ�Ϊ��  ��Ϊ�� ��Ϊ��ȫ��
			 if(v=='undefined' || v=="" || v==null||v==" "||v==0){
				 DCellWeb_tj.SetRowHidden(i, i);
			 }else{
				 DCellWeb_tj.SetRowUnHidden(i, i);
			 }
		 }
  }
  
  //����ȫ����
  function displayzero(xy){
	 
	  zwlb_xy();
	
  }
  
  //��ʾ������
  function xsall(){
	DCellWeb_tj.SetRowUnhidden(4, parseInt(array_row[array_row.length-1].split(",")[1]));
  }
  function xs_zwlb_zero(){
	  zwlb_xy();
  }
  //����ѡ��ְλ����ʾ ͳ�� 
  var zwlb_data ="";
  var xy_zwlb="1";
  function zwlb_xy(){
	  xy_zwlb=document.getElementById("xy_zwlb").value;
	  zwlb_data=document.getElementById("zwlb_l").value;
	  clear4();
	  if(zwlb_data=='undefined' || zwlb_data=="" || zwlb_data==null || zwlb_data==" "){//û��ѡ�����
		  if(xy_zwlb=='0'){
			  xsall();//��ʾ������
		  }else{
			  displayzeroinit();//����ȫ0��
		  }
		  total_zj_xy();//�ܼ�����ͳ��
		  zhanbi_xy_zwlb();//ռ������ͳ��
		  DCellWeb_tj.SetRowUnhidden(4, 4);//��ʾ
		  return;
	  }
	  
	  if(zwlb_data=='all'||zwlb_data=='ALL'){
		  if(xy_zwlb=='0'){
		  	xsall();//��ʾ������
		  }else{
			  displayzeroinit();//����ȫ0��
		  }
		  total_zj();//����ͳ��
		  zhanbi_xy_zwlb();
		  DCellWeb_tj.SetRowUnhidden(4, 4);//��ʾ
		  //zhanbi();//����ͳ��
		  return;
	  }
	  var strs= new Array(); //����һ���� �洢ְ�������� 
	  var strs_m= new Array(); //����һ����  �洢���� 
	  strs=zwlb_data.split(","); //�ַ��ָ�
	  DCellWeb_tj.SetRowhidden(4, 163);//����������
	  for (var i=0;i<strs.length ;i++ ) { 
		  strs_m=map_zwlb[strs[i]].split(",");
		  DCellWeb_tj.SetRowUnhidden(strs_m[0], strs_m[1]);//��ʾ
		  //С��
		  DCellWeb_tj.SetRowUnhidden(parseInt(strs_m[0])-1, parseInt(strs_m[0])-1);//��ʾ
		  //����ͳ��
		  DCellWeb_tj.SetRowUnhidden(4, 4);//��ʾ
		  total_zj_xy();//�ܼ�
		  zhanbi_xy_zwlb();
		  //zhanbi();//����ͳ��ռ��
		  if(xy_zwlb=='1'){//������
			  displayzero_zwlb(strs_m[0], strs_m[1]);//���� 0��
		  }
	  } 
  }
  function displayzero_zwlb(start,end){
	  var v=0;
		 for(var i=parseInt(start);i<=parseInt(end);i++){//��
			 v=DCellWeb_tj.GetCellString(3, i, 0);//�жϺϼ��Ƿ�Ϊ��  ��Ϊ�� ��Ϊ��ȫ��
			 if(v=='undefined' || v=="" || v==null||v==" "||v==0){
				 DCellWeb_tj.SetRowHidden(i, i);
			 }
		 }
		 v=DCellWeb_tj.GetCellString(3, parseInt(start)-1, 0);//�жϺϼ��Ƿ�Ϊ��  ��Ϊ�� ��Ϊ��ȫ��
		 if(v=='undefined' || v=="" || v==null||v==" "||v==0){
			 DCellWeb_tj.SetRowHidden(parseInt(start)-1,parseInt(start)-1);//����С��
		 }
  }
  //��յ�4�е�����
  function clear4(){
	  for(var i=(col_xho_start-1);i<=col_xho_end;i++){
		  DCellWeb_tj.SetCellString(i,4,0,'');
	  }
  }
  
//�ܼ�
  function total_zj_xy(){
	   //��3�е���ͳ�� 
	   var temp_zj_=parseInt(total_zj_ittt_xy(3));
		DCellWeb_tj.SetCellString(
  			3,4,0,temp_zj_==0?"":temp_zj_ );
		
	   var _zj_total=0;
	   for(var i=col_xho_start;i<=col_xho_end;i++){
 			if(i%2==1){//������
 				continue;
 			}
 			_zj_total=total_zj_ittt_xy(i);
		    //ż���� С��
 			DCellWeb_tj.SetCellString(i,4,0,_zj_total==0?"":_zj_total );
 		}
	  
  }
  
  function total_zj_ittt_xy(i){
	   var zj_total=0;
	   var zj_temp_total=0;
	   for(var j=0;j<array_row.length;j++){
		   if(DCellWeb_tj.IsRowHidden(parseInt(array_row[j].split(",")[0])-1,0)==false){//����ʾ
				zj_temp_total=DCellWeb_tj.GetCellString2(i, parseInt(array_row[j].split(",")[0])-1, 0);
				if(zj_temp_total=='undefined' || zj_temp_total=="" || zj_temp_total==null||zj_temp_total==" "){
						zj_temp_total=0;
				}
				zj_total=zj_total+parseInt(zj_temp_total);
		 	}
	   }
	 	
		return zj_total;
  }

</script>