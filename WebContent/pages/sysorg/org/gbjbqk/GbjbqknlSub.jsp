<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/echarts.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/sysorg/org/gbjbqk/gbjbqk.js" ></script>
<%@include file="/comOpenWinInit.jsp" %>

<%--ҳ�����Ϸ������� ����-���-����-��״ͼ --%>
<%@include file="/pages/sysorg/org/gbjbqk/Gbjbqk_ToolBar.jsp" %>

<!-- <div style="height:3px"></div> -->
<div id="createtable" style="overflow-y:hidden;overflow-x:hi;overflow: auto;margin-left:15px;display:none;">
	<table  style="width:100%;" border="0">
		<col width="5%">
		<col width="15%">
		<col width="80%">
		<tr>
			<odin:select property="zwlb" label="ְ�����" codeType="zwcelb" multiSelectWithAll="true" onchange="zwlb_func()"></odin:select>
			<td>
				<odin:checkbox property="xianyin" label="����ȫ����" value="1"></odin:checkbox>
			</td>
			
		</tr>
	</table>
	<table style="width:100%;" border="0">
		
		<tr>
			<td colspan="3">
				<div>
					<object id="DCellWeb1" style="left: 0px; width: 1250px; top: 0px; height: 400px" 
				    classid="clsid:3F166327-8030-4881-8BD2-EA25350E574A" 
				    codebase="softs/cellweb5.cab#version=5,3,8,0429">
				    <!-- #version=5.3.9.16 -->
				    <param name="_Version" value="65536" />
				    <param name="_ExtentX" value="10266" />
				    <param name="_ExtentY" value="7011" />
				    <param name="_StockProps" value="0" />
				    </object>
				</div>
			</td>
		</tr>
	</table>
</div>
<%--ͳ���б� --%>
<%@include file="/pages/sysorg/org/gbjbqk/Gbjbqk_List.jsp" %>
<%--ͳ�Ʊ�״ͼ --%>
<%@include file="/pages/sysorg/org/gbjbqk/Gbjbqk_Bzt.jsp" %>
<%--�����������ֶ� --%>
<%@include file="/pages/sysorg/org/gbjbqk/Gbjbqk_sub_WAndHT.jsp" %>
<odin:window src="/" id="gbjbqktjsubid" width="1300" height="550"></odin:window>
<script for="DCellWeb1" event="MouseDClick(col, row)" language="JavaScript">
myMouseDClick(col, row);
</script>
<script type="text/javascript" language="javascript">
try{
	setAuth(document.getElementById("DCellWeb1"));
}catch(err){
	
}
</script>
<script type="text/javascript">
var tjfx_dj_flag=false;
function initTj_func(){
	if(tjfx_dj_flag==false){
		radow.doEvent("initTj");
	}else{
		ctable();//��ʾ���
	}
}
////���˫���¼� 
function myMouseDClick(col1, row1) {
	//�������� 
	if(col1==0||col1==""||col1==" "||col1==null||col1=="undefined"||col1<3||col1>49){
		return;
	}
	if(row1==0||row1==""||row1==" "||row1==null||row1=="undefined"||row1<4||row1>parseInt(array_row[array_row.length-1].split(",")[1])){
		return;
	}
	//��ֵΪ0 �� ��
	var value=DCellWeb_tj.GetCellString(col1,row1,0);
	if(value==0||value==""||value==" "||value==null||value=="undefined"){
		return;
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
	
	//alert(dwid);
	var id_weiyi="GbjbqknlSub";//�����ظ�
	//�û�id
	var useridh=document.getElementById("userid_h").value;
	id_weiyi=id_weiyi+useridh;
	//ϵͳʱ��
	var date_time = new Date();
	var time=date_time.getHours()+ date_time.getMinutes()+date_time.getSeconds();
	id_weiyi=id_weiyi+time;
	$h.openWin(id_weiyi,'pages.sysorg.org.gbjbqk.GbjbqknlSub',title+'-��άͳ��',1300,550,dwid,'<%=request.getContextPath()%>');
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
////����json�� 
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
			// // ʹ�ø�ָ�����������������ʾͼ�� 
			myChart.setOption({ 
				 
				title : {
			        text: title+"(����)",// //����
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
	// ��ʾ��� 
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
		if(tjfx_dj_flag==false){
			return;
		}
		for(var j=0;j<array_row.length;j++){
			zhgll_func(parseInt(array_row[j].split(",")[0]),parseInt(array_row[j].split(",")[1]));//�������� 
			
			setbingzhuang((j+1)+'');//���ɱ�ͼ
		}
		flaf_cbz=true;
		
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
		if(tjfx_dj_flag==false){
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
			"20�꼰����",
			"21��",
			"22��",
			"23��",
			"24��",
			"25��",
			"26��",
			"27��",
			"28��",
			"29��",
			"30��",
			"31��",
			"32��",
			"33��",
			"34��",
			"35��",
			"36��",
			"37��",
			"38��",
			"39��",
			"40��",
			"41��",
			"42��",
			"43��",
			"44��",
			"45��",
			"46��",
			"47��",
			"48��",
			"49��",
			"50��",
			"51��",
			"52��",
			"53��",
			"54��",
			"55��",
			"56��",
			"57��",
			"58��",
			"59��",
			"60��",
			"61��",
			"62��",
			"63��",
			"64��",
			"65�꼰����",
			"ƽ������"
			);
	var wenzi_str="";
	// //����
	function wenzi_func(){
		// //�ܼ�
		var obj=document.getElementById("wenzi");
		// //�ɲ������������
		wenzi_str="<br><p style='width:100%;text-align:center;'><b>�ɲ������������(����)</b></p><br><b>�������:</b><br><br>"
		wenzi_str=wenzi_str+"������(����)<font onclick='opensubowin(\"3\",\"4\")' style='cursor:hand;color:#75acd2;'> <u>"+DCellWeb_tj.GetCellString(3, 4, 0)+"</u> </font>�� ����: ";
		var num_wz=0;
		for(var i=col_xho_start+1;i<=col_xho_end;i++){//ѭ����
			   num_wz=DCellWeb_tj.GetCellString2(i, 4, 0);
				if(num_wz==0||num_wz==""||num_wz==null||num_wz==" "||num_wz=="undefined"){
					
				}else{//#75acd2
					wenzi_str=wenzi_str+tjxm_arr[i]+"<font onclick='opensubowin(\""+i+"\",\"4\")' style='cursor:hand;color:#75acd2;'> <u>"+num_wz+"</u> </font>"+"�� "
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
			
			for(var j=col_xho_start+1;j<=col_xho_end;j++){//����
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
				for(var j=col_xho_start+1;j<=col_xho_end;j++){//����
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
		     var action = "GbjbqkDownServlet?method=Gbjbqknl"; 
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
				document.getElementById("DCellWeb1").openfile(ctpath+"\\template\\gbjbqktjnl.cll","");
		    }catch(err){
		    	//alert("��ʾ:�����ذ�װ������!��ʹ��IE�����(����ģʽ)��(����ϵϵͳ����ԱԶ��Э��)!");
		    }
	   });
	
	//���json����
	var json_obj="";
	//��ʼ������ 
	var DCellWeb_tj="";
	var col_xho_start=3;
	var col_xho_end=50;
	//json�ַ���
	
  function json_func(){
	   //jsonstr,lengthstr
	   var jsonstr=document.getElementById("jsonString_str").value;
	   DCellWeb_tj=document.getElementById("DCellWeb1");//��ȡ������
	   var obj ="";
	   try{
		   obj = eval('(' + jsonstr + ')');
	   }catch(err){
	   }
	   json_obj=obj;
	   DCellWeb_tj.SetFixedCol(1, 2); 
	   DCellWeb_tj.SetFixedRow(1, 3);
	   
		DCellWeb_tj.InsertCol (DCellWeb_tj.GetCols( 0 ), 1, 0);
		DCellWeb_tj.SetColHidden(DCellWeb_tj.GetCols( 0 )-1,DCellWeb_tj.GetCols( 0 )-1);
	   for(var i=0;i<obj.length;i++){////�������� ���� ѭ��
	   	   var a0221=obj[i].a0221;//����
	   	   if(a0221=='undefined' || a0221=="" || a0221==null||a0221==" "){
	   			continue;//������Ϊ��
	   	   }
	   	   a0221=map[a0221];//����
	   	   if(a0221=='undefined' || a0221=="" || a0221==null||a0221==" "){
	   			continue;//js û�����ô�������
	   	   }
	   	   var i_col=3;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].heji==0?"":obj[i].heji);//�ϼ�
		   i_col=i_col+1;		
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].xydy20==0?"":obj[i].xydy20);//20������
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].dy21==0?"":obj[i].dy21);//
  			i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].dy22==0?"":obj[i].dy22);//
  			i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].dy23==0?"":obj[i].dy23);//
  			i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].dy24==0?"":obj[i].dy24);//
  			i_col=i_col+1;
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,obj[i].dy25==0?"":obj[i].dy25);//
		   	i_col=i_col+1;
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,obj[i].dy26==0?"":obj[i].dy26);//
 		   	i_col=i_col+1;
 			DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].dy27==0?"":obj[i].dy27);//
  			i_col=i_col+1;	
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].dy28==0?"":obj[i].dy28);//
  			i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].dy29==0?"":obj[i].dy29);//
  			i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].dy30==0?"":obj[i].dy30);//
  			i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].dy31==0?"":obj[i].dy31);//
  			i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].dy32==0?"":obj[i].dy32);//
  			i_col=i_col+1;
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,obj[i].dy33==0?"":obj[i].dy33);//
 		   	i_col=i_col+1;
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,obj[i].dy34==0?"":obj[i].dy34);//
 		   	i_col=i_col+1;
 			DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].dy35==0?"":obj[i].dy35);//
  			i_col=i_col+1;		
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].dy36==0?"":obj[i].dy36);//
  			i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].dy37==0?"":obj[i].dy37);//
  			i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].dy38==0?"":obj[i].dy38);//
  			i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].dy39==0?"":obj[i].dy39);//
  			i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].dy40==0?"":obj[i].dy40);//
  			i_col=i_col+1;
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,obj[i].dy41==0?"":obj[i].dy41);//
 		   	i_col=i_col+1;
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,obj[i].dy42==0?"":obj[i].dy42);//
 		  	i_col=i_col+1;
 		   	DCellWeb_tj.SetCellString( i_col,a0221,0,obj[i].dy43==0?"":obj[i].dy43);//
 		   	i_col=i_col+1;
 		   	DCellWeb_tj.SetCellString( i_col,a0221,0,obj[i].dy44==0?"":obj[i].dy44);//
 		   	i_col=i_col+1;
 		   	DCellWeb_tj.SetCellString( i_col,a0221,0,obj[i].dy45==0?"":obj[i].dy45);//
 		   	i_col=i_col+1;
 		   	DCellWeb_tj.SetCellString( i_col,a0221,0,obj[i].dy46==0?"":obj[i].dy46);//
 		   	i_col=i_col+1;
 		   	DCellWeb_tj.SetCellString( i_col,a0221,0,obj[i].dy47==0?"":obj[i].dy47);//
  		   	i_col=i_col+1;
  		   	DCellWeb_tj.SetCellString( i_col,a0221,0,obj[i].dy48==0?"":obj[i].dy48);//
  		 	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString( i_col,a0221,0,obj[i].dy49==0?"":obj[i].dy49);//
  		   	i_col=i_col+1;
  		   	DCellWeb_tj.SetCellString(i_col,a0221,0,obj[i].dy50==0?"":obj[i].dy50);//
  		   	i_col=i_col+1;
  		   	DCellWeb_tj.SetCellString( i_col,a0221,0,obj[i].dy51==0?"":obj[i].dy51);//
  		   	i_col=i_col+1;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].dy52==0?"":obj[i].dy52);//
  		   	i_col=i_col+1;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].dy53==0?"":obj[i].dy53);//
 	    	  		   	
  		   		
  		   	i_col=i_col+1;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].dy54==0?"":obj[i].dy54);//
 	    	    		   	
	   		i_col=i_col+1;
  		   	DCellWeb_tj.SetCellString(
   		   		i_col,a0221,0,obj[i].dy55==0?"":obj[i].dy55);//
 	    	    		   	
	   		i_col=i_col+1;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].dy56==0?"":obj[i].dy56);//
  		   		
 		   	i_col=i_col+1;
 		   	DCellWeb_tj.SetCellString(
   		   		i_col,a0221,0,obj[i].dy57==0?"":obj[i].dy57);//
  		   	i_col=i_col+1;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].dy58==0?"":obj[i].dy58);//
  		   	i_col=i_col+1;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].dy59==0?"":obj[i].dy59);//
  		   	i_col=i_col+1;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].dy60==0?"":obj[i].dy60);//
  		   	i_col=i_col+1;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].dy61==0?"":obj[i].dy61);//
  		   	i_col=i_col+1;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].dy62==0?"":obj[i].dy62);//
  		   	i_col=i_col+1;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].dy63==0?"":obj[i].dy63);//
  		   	i_col=i_col+1;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].dy64==0?"":obj[i].dy64);//
  		   	i_col=i_col+1;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].dydy65==0?"":obj[i].dydy65);//
  		   	i_col=i_col+1;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].pjnl==0?"":obj[i].pjnl);//
  		   	i_col=i_col+1;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].heji_zs==0?"":obj[i].heji_zs);//
		}/// for end
		
		//С��
	   for(var i=0;i<array_row.length;i++){
			total_tj(parseInt(array_row[i].split(",")[0])-1,
					parseInt(array_row[i].split(",")[0]),
					parseInt(array_row[i].split(",")[1]));
		}
		//�ܼ� 
		total_zj();
		
		
		//���� ȫ��Ϊ0����
		displayzeroinit();
		//����ҳ�治�ɱ༭
		DCellWeb_tj.WorkbookReadonly=true;
	
		tjfx_dj_flag=true;//���ͳ�Ʒ�����־ 
		ctable();//��ʾ���
		document.getElementById( "bar_div").style.display= "block";//��ʾ������
		//��������grid�߶�
		try{
			//var grid = odin.ext.getCmp('gridfc');
			//grid.setHeight(487);
		}catch(err){
			
		}
		wenzi_func();
  }
 
  //�ܼ�
  function total_zj(){
	   //��3�е���ͳ�� 
	   var temp_zj_=parseInt(total_zj_ittt(3));
		DCellWeb_tj.SetCellString(
  			3,4,0,temp_zj_==0?"":temp_zj_ );
		
	   var _zj_total=0;
	   for(var i=col_xho_start;i<=col_xho_end;i++){
 			
 			_zj_total=total_zj_ittt(i);
		    
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
	 	
		if(i==col_xho_end){
		
			zj_total=pj_38_zj(i);
			
		}
		return zj_total;
  }
  
  //�ܼ�  ƽ������
  function pj_38_zj(i){
	   var zj_total=0;
	   var zj_temp_total=0;//��ǰ��ƽ������
	   var zj_temp_3=0;//��ǰ��������
	 
	   var zrs=0;//������
	   for(var j=0;j<array_row.length;j++){//ѭ��ְ�����
		   for(var n=parseInt(array_row[j].split(",")[0]);n<=parseInt(array_row[j].split(",")[1]);n++){//ѭ��ְ��ȼ�
				zj_temp_total=DCellWeb_tj.GetCellString2(i, n, 0);
				if(zj_temp_total=='undefined' || zj_temp_total=="" || zj_temp_total==null||zj_temp_total==" "){
					zj_temp_total=0;
				}
				zj_temp_3=r_total_r(n);
				//zj_temp_3=DCellWeb_tj.GetCellString2((i+1), n, 0);
				if(zj_temp_3=='undefined' || zj_temp_3=="" || zj_temp_3==null||zj_temp_3==" "){
					zj_temp_3=0;
				}
				zrs=zrs+parseInt(zj_temp_3);
				zj_total=zj_total+parseInt(zj_temp_total)*parseInt(zj_temp_3);
		   }
	    }
		if(zrs=='undefined' || zrs=="" || zrs==null||zrs==" "||zrs==0){
			zrs=1;
		}

		return Math.round(parseInt(zj_total)/parseInt(zrs)*100)/100;
  }
 
	//ͳ��С��
	function total_tj(row_xj,row_s,row_e){//row_xj С��ͳ���� row_s��ʼ�� row_e ������
		
		var temp_v="";
		for(var i=col_xho_start;i<=(col_xho_end+1);i++){//��ѭ��
  			
  			temp_v=col_total(i,row_s,row_e);
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
		if(col_xj==col_xho_end){//ƽ��ֵ
			var znl=0;
			var zrs=0; 
			var temp_pjnl=0;
			var dqhrs=0;
			for(var i=row_s;i<=row_e;i++){//��
				temp_pjnl=DCellWeb_tj.GetCellString2(col_xj, i, 0);//��ǰ��ƽ������
				if(temp_pjnl==""||temp_pjnl==" "||temp_pjnl=="undefined"||temp_pjnl==null){
					temp_pjnl=0;
	  			}
				dqhrs=DCellWeb_tj.GetCellString2((col_xho_end+1), i, 0);//����
				if(dqhrs==""||dqhrs==" "||dqhrs=="undefined"||dqhrs==null){
					dqhrs=0;
	  			}
				zrs=zrs+parseInt(dqhrs);
				znl=znl+parseInt(temp_pjnl)*parseInt(dqhrs);
			}
			if(zrs==""||zrs==" "||zrs=="undefined"||zrs==null||zrs==0){
				zrs=1;
 			}
			col_num=Math.round(parseInt(znl)/parseInt(zrs)*100)/100;
		}
	    return col_num;
	}
 
 //���������д�4�п�ʼ
 function yx_all(){
	  DCellWeb_tj.SetRowHidden(4, parseInt(array_row[array_row.length-1].split(",")[1]));
 }
 
 function displayzeroinit(){
	  var v=0;
		 for(var i=4;i<=parseInt(array_row[array_row.length-1].split(",")[1]);i++){//��
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
	  if(zwlb_data=='undefined' || zwlb_data=="" || zwlb_data==null || zwlb_data==" "){
		  if(xy_zwlb=='0'){
			  xsall();//��ʾ������
		  }else{
			  displayzeroinit();//����ȫ0��
		  }
		  total_zj_xy();//�ܼ�
		  
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
		 
		  DCellWeb_tj.SetRowUnhidden(4, 4);//��ʾ
		 
		  return;
	  }
	  var strs= new Array(); //����һ���� �洢ְ�������� 
	  var strs_m= new Array(); //����һ����  �洢���� 
	  strs=zwlb_data.split(","); //�ַ��ָ�
	  DCellWeb_tj.SetRowhidden(4, parseInt(array_row[array_row.length-1].split(",")[1]));//����������
	  for (var i=0;i<strs.length ;i++ ) { 
		  strs_m=map_zwlb[strs[i]].split(",");
		  DCellWeb_tj.SetRowUnhidden(strs_m[0], strs_m[1]);//��ʾ
		  //С��
		  DCellWeb_tj.SetRowUnhidden(parseInt(strs_m[0])-1, parseInt(strs_m[0])-1);//��ʾ
		  //����ͳ��
		  DCellWeb_tj.SetRowUnhidden(4, 4);//��ʾ
		  total_zj_xy();//�ܼ�
		  
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
			 DCellWeb_tj.SetRowHidden(parseInt(start)-1,parseInt(start)-1);
		 }
 }
 //��յ����е�����
 function clear4(){
	  for(var i=col_xho_start;i<=col_xho_end;i++){
		  DCellWeb_tj.SetCellString(i,4,0,'');
	  }
 }
 
//�ܼ�
 function total_zj_xy(){
		
	   var _zj_total=0;
	   for(var i=col_xho_start;i<=col_xho_end;i++){

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
				zj_temp_total=DCellWeb_tj.GetCellString(i, parseInt(array_row[j].split(",")[0])-1,0);
				if(zj_temp_total=='undefined' || zj_temp_total=="" || zj_temp_total==null||zj_temp_total==" "){
						zj_temp_total=0;
				}
				zj_total=zj_total+parseInt(zj_temp_total);
		 	}
	   }
	 
	 	if(i==col_xho_end){
	 		zj_total=total_zj_ittt_pjnl(i);
	 	}
		return zj_total;
 }
 
 function total_zj_ittt_pjnl(i){
	  var zj_total=0;
	   var zj_temp_total=0;//��ǰ��ƽ������
	   var zj_temp_3=0;//��ǰ��������
	 
	   var zrs=0;//������
	   for(var j=0;j<array_row.length;j++){
		   if(DCellWeb_tj.IsRowHidden(parseInt(array_row[j].split(",")[0])-1,0)==false){//����ʾ
			   for(var n=parseInt(array_row[j].split(",")[0]);n<=parseInt(array_row[j].split(",")[1]);n++){//ѭ��ְ��ȼ�
				   zj_temp_total=DCellWeb_tj.GetCellString2(i, n, 0);//��ǰ��ƽ������
					if(zj_temp_total=='undefined' || zj_temp_total=="" || zj_temp_total==null||zj_temp_total==" "){
						zj_temp_total=0;
					}
				    zj_temp_3=r_total_r(n);//��ǰ��������
					if(zj_temp_3=='undefined' || zj_temp_3=="" || zj_temp_3==null||zj_temp_3==" "){
						zj_temp_3=0;
					}
					zrs=zrs+parseInt(zj_temp_3);
					zj_total=zj_total+parseInt(zj_temp_total)*parseInt(zj_temp_3);
			   }
		 	}
	   }
	 	if(zrs=='undefined' || zrs=="" || zrs==null||zrs==" "||zrs==0){
			zrs=1;
		}

		return Math.round(parseInt(zj_total)/parseInt(zrs)*100)/100;
		
}
//ƽ�������Ӧ����
 function r_total_r(row){
 	   var zj_temp_3=0;
 	   var zj_temp_cell=0;
 	   for(var j=col_xho_start+1;j<=col_xho_end-1;j++){//��
 			zj_temp_cell=DCellWeb_tj.GetCellString2(j, row, 0);
 			if(zj_temp_cell=='undefined' || zj_temp_cell=="" || zj_temp_cell==null||zj_temp_cell==" "){
 				zj_temp_cell=0;
 			}
 			zj_temp_3=zj_temp_3+parseInt(zj_temp_cell);
 	 }
 	   return zj_temp_3;
 }
</script>
<odin:hidden property="tran_length"/>