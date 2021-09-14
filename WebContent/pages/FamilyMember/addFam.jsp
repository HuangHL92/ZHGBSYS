<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%
	String ctxPath = request.getContextPath();
	String groupID=request.getParameter("groupID");
%>
<!DOCTYPE html>
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" charset="GBK" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<title>��ͥ��Ա������ϵ</title>
<odin:head />
<script type="text/javascript">var ctx_path = "<%=ctxPath%>";</script>
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/odin.css" />
<%--<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/templete/templete-default.css" />--%>
<script type="text/javascript" src="<%=ctxPath%>/basejs/jquery/jquery-1.7.2.min.js"></script>
<%--<script type="text/javascript" src="<%=ctxPath%>/js/console.js"></script>--%>
<script type="text/javascript" src="<%=ctxPath%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/basejs/pageUtil.js"></script>
<style type="text/css">
.x-form-check-wrap{
	padding-top: 2px
}

#popDiv {
   display: none;
   width: 528px;
   height:60px;
   overflow-y:scroll;
   border: 1px #74c0f9 solid;
   background: #FFF;
   position: absolute;
   left: 108px;
   margin-top:25px;
   color: #323232;
   z-index:321;
}   

</style>
</head>
<body style="overflow: hidden;">
	
	<div id="container">
		<table id="container-table" border="0" cellspacing='0'>
			<tr>
				<td valign="top" style="position: relative;width: 330px" id="left-td">
					<div id="search-div" class="scrollbar" >
						 <table id="search-tbl" align="center" class="x-form-item2" style="width: 100%;background-color: rgb(209,223,245);border-right: 1px solid rgb(153,187,232);">
							<tr>
							  <td>
							  	<tags:PublicTextIconEdit3 width="80" codetype="orgTreeJsonData"  onchange="a0201bChange" label="����ѡ��" property="dept" defaultValue="" />
							  </td>
							  <odin:textarea property="seachName" rows="1" style="width:120px; overflow: auto;"></odin:textarea>
								<td><odin:button text="����" property="searchOnePerson" handler="searchPersonByName" ></odin:button></td> 
							</tr>
						</table> 
					</div>
					<div id="search-btn" align="center" style="">
					
						<div id="">
						<odin:editgrid property="perGrid" title="��Ա��Ϣ" autoFill="false" width="330px" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/"  >
							<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
								<odin:gridDataCol name="percheck" />
								<odin:gridDataCol name="a0000" />
								<odin:gridDataCol name="a0101" />
								<odin:gridDataCol name="a0192"   />
								<odin:gridDataCol name="shzt"   />
								<odin:gridDataCol name="a0107" />		
								<odin:gridDataCol name="rzzt" isLast="true"/>		
							</odin:gridJsonDataModel>
							<odin:gridColumnModel>
							    <odin:gridRowNumColumn></odin:gridRowNumColumn>
							    <odin:gridEditColumn2 header="selectall" width="40" editor="checkbox" dataIndex="percheck" edited="true" hideable="false" gridName="perGrid"/>
							    <odin:gridEditColumn header="��Ա����"  dataIndex="a0000" align="center" editor="text" width="20" hidden="true" edited="false"/>
								<odin:gridEditColumn header="����" dataIndex="a0101" align="center" edited="false" editor="text" width="50" />
								<odin:gridEditColumn header="������λ��ְ��" dataIndex="a0192" align="center" edited="false" editor="text" width="90"  />
								<odin:gridEditColumn header="���״̬" dataIndex="shzt" align="center" edited="false" editor="select"  width="70" renderer="shzt" />
								<odin:gridEditColumn header="��������" dataIndex="a0107" align="center" edited="false" editor="text" width="60" /> 
								<odin:gridEditColumn header="��ְ״̬" dataIndex="rzzt" align="center" edited="false" editor="text" width="60" renderer="rzzt" isLast="true" /> 
							</odin:gridColumnModel>
						</odin:editgrid>
						</div>
						
					</div>
				</td>
				
				<td valign="top" id="right-td" style="padding-left: 10px"  >
					<div id="container-div" class="scrollbar overflow-y-hidden">
					
						<div id="fileDiv">
						
							<odin:groupBoxNew title="�����ϴ�" property="perxx" contentEl="perxxDiv" collapsible="false" frame="true">
								<div id="perxxDiv">
									<table>
										<tr>
											<odin:textEdit property="a0101" label="&nbsp;&nbsp;&nbsp;�ɲ�����"  readonly="true"></odin:textEdit>
											<td><div id="popDiv"></div></td>
											<odin:dateEdit property="sbny" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�ϱ�����" format="Ym"  ></odin:dateEdit>
											<td colspan="1" align="left">
	       										<iframe id="frame"  name="frame" height="33px" width="370px" src="<%=request.getContextPath() %>/pages/FamilyMember/apfile.jsp" frameborder=��no�� border=��0�� marginwidth=��0�� marginheight=��0�� scrolling=��no�� allowtransparency=��yes��></iframe>
											</td>
										
										</tr>
										<tr>
										<odin:textEdit property="filename" label="&nbsp;���ϴ��ļ���"  readonly="true"></odin:textEdit>
										<td></td>
										<td></td>
										<td></td>
										
										<td>
								          
									                  <table class="bottom-btns">
										                   <tr>
										                       <td width="200px"></td>
											                  <td align="center" valign="middle"><odin:button text="�����ύ"  property="dosub" handler="dosub" /></td>		
											                  <td width="10px"></td>
											                  <td align="center" valign="middle"><odin:button text="��������"  property="download" handler="download" /></td>
											
										                 </tr>
									                </table>		
								         
										</td>
										</tr>
										<tr>
											<td><span style="font-size: 12px">�������λ�����ʡ�����������쵼<br>�ɲ������������ݣ���������</span></td>
											<odin:textarea property="txtarea" colspan="5" rows="3" ></odin:textarea>
										</tr>
										
									</table>
								<div id="bottom-btns" align="center">
									<table class="bottom-btns">
										<tr>
											<td align="center" valign="middle"><odin:button text="����"  property="saveMS" handler="saveMS" /></td>		
											
										</tr>
									</table>		
								</div>
								</div>

							
							
							</odin:groupBoxNew>
						
						
						</div>     
						<div id="fam_xxDiv">
							<odin:groupBoxNew title="��Ӽ�ͥ��Ա" property="famxx" contentEl="famDiv" collapsible="false" frame="true">
								<div id="famDiv">
									<table border="0" align="center" style="width: 100%" cellpadding="0" cellspacing="0">
										<tr>
											<odin:hidden   property="sysDate" />
											<odin:textEdit property="a3601" label="����"  />
											<odin:textEdit property="a3607" label="��������"   maxlength="6"  onblur="validateYearM()"  onkeyup="value=value.replace(/[^\d]/g,'')"   />
											<odin:textEdit property="a0184gz" label="���֤"  maxlength="18" onblur="validateIDCard()"/>
											<odin:textEdit property="a3611" label="������λ��ְ��"></odin:textEdit>
										<tr>
											<odin:select2 property="a3604a" codeType="GB4761"  label="��Ա��ϵ" />
											<td colspan="6">
												<odin:checkbox property="udcheck" label="&nbsp;&nbsp;�Ƿ�Ϊ����ֱϵ������������ϵ���������λ��������ּ�������ְ���Լ��ƾӹ������������Ա"/>
											</td>
											
										</tr>
										<tr>
											<odin:select2 property="a3627b" label="������ò" codeType="GB4762" />
											<tags:PublicTextIconEdit isLoadData="false"  property="a0111gz" label="����" codename="code_name" codetype="ZB01" readonly="true"  />
											<tags:PublicTextIconEdit isLoadData="false"  property="a0115gz" label="��ס��" codename="code_name" codetype="ZB01" readonly="true"  />
											<odin:select2 property="a0111gzb" label="���������"  codeType="GB2659"  />
										</tr>
										<tr>
											<odin:select2 property="a3621" label="����"  codeType="GB3304"/>
											<tags:PublicTextIconEdit isLoadData="false"  property="a3631" label="��Ա���" codename="code_name" codetype="ZB06" readonly="true"  />
											<odin:select2 property="a3641" label="��Ա��״" codeType="ZB56"/>
										</tr>
										
									</table>
								</div>
							</odin:groupBoxNew>
							
						<odin:toolBar property="famBtnToolBar" applyTo="famToolDiv">
							<odin:textForToolBar text=""/>
							
							<odin:fill/>
							<odin:separator/> 
							<odin:buttonForToolBar text="&nbsp;����" id="save" icon="images/icon/save.gif" handler="doSave"/>
							<odin:separator></odin:separator>
							<odin:buttonForToolBar  text="&nbsp;����"  id="add" icon="images/add.gif" handler="addpeople"    />
							<odin:separator></odin:separator>
							<odin:buttonForToolBar text="&nbsp;����" id="exp" icon="images/icon/imp.gif"/>
							<odin:separator></odin:separator>
							<odin:buttonForToolBar text="��������" id="uploadbtn" handler="excelimp"  icon="images/icon/exp.png" />
							<odin:separator></odin:separator>
							<odin:buttonForToolBar text="���ݻָ�" id="recover" handler="dorecover"  icon="images/icon/exp.png" />
							<odin:separator></odin:separator>
						    <odin:buttonForToolBar  text="&nbsp;��ӡ"  id="doPrint" icon="images/mylog.gif" isLast="true" />
						</odin:toolBar>

						</div>
						
						<div id="famToolDiv"></div>	
						<!-- ���б� -->	     
						<div id="girdDiv" style="width: 100%;margin:0px 0px 0px 0px;" >    
						<odin:editgrid property="MGrid" title="" autoFill="false" width="75%" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/" grouping="true" groupCol="updated" >
							<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
								<odin:gridDataCol name="a3600" />
								<odin:gridDataCol name="a3604a" />
								<odin:gridDataCol name="a3601" />
								<odin:gridDataCol name="a3611" />
								<odin:gridDataCol name="a3607" />
								<odin:gridDataCol name="a3627" />
								<odin:gridDataCol name="a0184gz" />
								<odin:gridDataCol name="a0111gz" />
								<odin:gridDataCol name="a0115gz" />
								<odin:gridDataCol name="a0111gzb" />
								<odin:gridDataCol name="a3621" />
								<odin:gridDataCol name="a3631" />
								<odin:gridDataCol name="a3645" />
								<odin:gridDataCol name="updated"></odin:gridDataCol>
								<odin:gridDataCol name="a3641" isLast="true" />		
							</odin:gridJsonDataModel>
							<odin:gridColumnModel>
							    <odin:gridRowNumColumn></odin:gridRowNumColumn>
							    <odin:gridEditColumn header="����"  dataIndex="a3600" align="center" editor="text" width="100" hidden="true" edited="false"/>
								<odin:gridEditColumn header="��ν" dataIndex="a3604a" align="center" edited="false" editor="text" width="60" />
								<odin:gridEditColumn header="����" dataIndex="a3601" align="center" edited="false" editor="text" width="60" />
								<odin:gridEditColumn header="������λ��ְ��" dataIndex="a3611" align="center" edited="false" editor="text" width="160" />
								<odin:gridEditColumn header="��������" dataIndex="a3607" align="center" edited="false" editor="text" width="60" />
								<odin:gridEditColumn header="������ò" dataIndex="a3627" align="center" edited="false" editor="text" width="80" />
								<odin:gridEditColumn header="���֤��" dataIndex="a0184gz" align="center" edited="false"  editor="text" width="120"/>
								<odin:gridEditColumn header="����" dataIndex="a0111gz" align="center" edited="false" editor="text" width="70" />
								<odin:gridEditColumn header="��ס��" dataIndex="a0115gz" align="center" edited="false" editor="text" width="70"/>
								<odin:gridEditColumn header="���������" dataIndex="a0111gzb" align="center" edited="false" editor="text" width="60" />
								<odin:gridEditColumn header="����" dataIndex="a3621" align="center" edited="false" editor="text" width="60"  />
								<odin:gridEditColumn header="��Ա���" dataIndex="a3631" align="center" edited="false" editor="text" width="60" />
								<odin:gridEditColumn header="��Ա��״" dataIndex="a3641" align="center" edited="false" editor="text"  width="80" />
								<odin:gridEditColumn header="���״̬" dataIndex="a3645" align="center" edited="false" editor="select"  width="80" renderer="shzt" />
								<odin:gridEditColumn editor="select" dataIndex="updated" hidden="true" edited="false" codeType="A36_GROUP" header="����" width="40" />
								<odin:gridEditColumn header="����" dataIndex="a3600" align="center" edited="false" editor="text" width="80" renderer="dodetail" isLast="true" />
							</odin:gridColumnModel>
						</odin:editgrid>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<!-- ��JS�ļ�����ҳ��Ԫ��ĩβ���� -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/RelateSearch.js"></script>
	<script type="text/javascript" src="<%=ctxPath%>/js/templete/templete-default.js"></script>
	<odin:hidden property="a0000" title="��Ա����"></odin:hidden>
	<odin:hidden property="a0000s" title="�����ϴ���Ա����" />
	<odin:hidden property="a3600" title="��ͥ��Ա����"/>
	<odin:hidden property="usertype" title="�û���¼��" />
	<odin:hidden property="b0111" title="��λ"/>
	<odin:hidden property="zippath"  title="����·��"/>
	<odin:hidden property="jtcy" title="��ͥ��Ա" />
	<odin:hidden property="userid" title="�û�id" />
	<odin:hidden property="sql" title="sql"/>
	
 <script type="text/javascript">  
 	var maxHeight = window.parent.document.body.offsetHeight;
 
    Ext.onReady(function() {


      
		//����Ӧ��С
		window.onresize=resizeframe;
		resizeframe();
		$("#searchName").width("120px");
		window.onresize=resizeframe1;
		resizeframe1() 

    	
    	var pgrid = Ext.getCmp('MGrid');
 		var bbar = pgrid.getBottomToolbar();
 		 bbar.insertButton(11,[
 							new Ext.menu.Separator({cls:'xtb-sep'}),
 							new Ext.Button({
 								icon : 'images/keyedit.gif',
 								id:'setPageSize',
 							    text:'����ÿҳ����',
 							    handler:setPageSize
 							}),
 							new Ext.Button({
 								icon : 'images/icon/arrowup.gif',
 								id:'UpBtn',
 							    text:'����',
 							    cls :'inline',
 							    //renderTo:"btngroup",
 							    handler:UpBtn
 							}),
 							new Ext.Button({
 								icon : 'images/icon/arrowdown.gif',
 								id:'DownBtn',
 							    text:'����',
 							    cls :'inline pl',
 							    //renderTo:"btngroup",
 							    handler:DownBtn
 							}),
 							new Ext.Button({
 								icon : 'images/icon/save.gif',
 								id:'saveSortBtn',
 							    text:'��������',
 							    cls :'inline pl',
 							    //renderTo:"btngroup",
 							    handler:function(){
 									radow.doEvent('worksort');
 							    }
 							}),

 							]);
    	
    });
    function dodetail(value, params, rs, rowIndex, colIndex, ds){
		return "<a href=\"javascript:deleteV('" + value + "')\">ɾ��</a>&nbsp;"
		+"&nbsp;<a href=\"javascript:modifyV('" + value + "')\">�޸�</a>"
	}
    function shzt(value, params, rs, rowIndex, colIndex, ds){
		if(value == '0')
			return "δ���";
		else 
			return "�����";
	}
    function rzzt(value, params, rs, rowIndex, colIndex, ds){
		if(value == '1')
			return "��ְ";
		else 
			return "����ְ";
	}
    function a0201bChange() {
    	var dept=document.getElementById("dept").value;
    	if(dept=='')
    		{
    		$h.alert('ϵͳ��ʾ','��������Ϊ��');
    		return;
    		}
    	radow.doEvent('queryByName');
    }
    
	
  //ʧȥ������֤���֤�Լ����������Ƿ���д
	function validateIDCard(){
		var a3607=document.getElementById('a3607').value;
		var a0184=document.getElementById('a0184gz').value;
		if(a0184!=''&&checkA0184(a0184)){
		if(a3607==''){
				var a0184Temp=a0184.substring(6,12);
				document.getElementById('a3607').value=a0184Temp;
			}
		}
	}
	
	//ʧȥ������֤��������
	function validateYearM(){
		var a3607=document.getElementById('a3607').value;
		checkA3607(a3607);
	}
    
    function doSave() {
		var a0184=document.getElementById('a0184gz').value;
		var a3604a=document.getElementById('a3604a').value;
		var a3601=document.getElementById('a3601').value;
		var a0000=document.getElementById('a0000').value;
		var a3607=document.getElementById('a3607').value;
		if(a0000=='')
			{
			$h.alert('ϵͳ��ʾ:','����ѡ�������Ա!',null,220);
			return false;
			}
		if(a3601==''){
			$h.alert('ϵͳ��ʾ:','��������Ϊ��!',null,220);
			document.getElementById('a3601').focus();
			return false;
		}
        var a3641 = $("#a3641").val();
        if (!checkA0184(a0184) && !feildIsNull(a3641) && (a3641 == '7' || a3641 == '8' || a3641 == '9'))//���֤
        {
            return false;
        }
		if(a3604a==''){
			$h.alert('ϵͳ��ʾ:','��Ա��ϵ����Ϊ��!',null,220);
			//document.getElementById('a3604a').focus();
			return false;
		}
		if(a3604a!=''&&!(checkAge(a3604a,a0184,a3607))){//����
				return false;
		}
		if(!checkA3607(a3607)){//��������
			return false;
		}


		
		radow.doEvent('dosave', '');
	}

    //������֤�Ϸ���
   function checkA0184(a0184){
        // �ѹ� ���� ����Ҫ����У�� 5 �ѹ� 6 ��ʿ 7 ����
       var a3641=$("#a3641").val();
       var a0111gzb = $("#a0111gzb").val();
       //alert(a0111gzb);
       if(a0184==''&& !feildIsNull(a3641)&& (a3641 == '7' || a3641 == '8' || a3641 == '9' || (a0111gzb != '156'&&!feildIsNull(a0111gzb))  )){
            return true;
       }else  if(a0184==''){
			$h.alert('ϵͳ��ʾ��','���֤�Ų���Ϊ��!    ��Ա״̬Ϊ�ѹʡ���ʿ������ʱ���߹���Ϊ����Ŀɲ�¼�����֤��Ϣ��',null,220);
			//document.getElementById('a0184gz').focus();
			return false;
		}
		var a0184YearM=a0184.substring(6,14);
		if(a0184!=''){
// 			var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/; 
			var reg = /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
			  if(reg.test(a0184) === false) 
			  { 
				$h.alert('ϵͳ��ʾ��','���֤���벻�Ϸ�',null,220);
				//document.getElementById('a0184gz').focus();
			    return false; 
			  }else if(!checkA3607(a0184YearM)){
				  $h.alert('ϵͳ��ʾ��','���֤���벻����',null,220);
				  //document.getElementById('a0184gz').focus();
				  return false;
			  }else{
				  return true;
			  }
		}
   }
    
 //����������¸�ʽ
   function checkA3607(a3607){
       var a3641=$("#a3641").val();
       var a0184=document.getElementById('a0184gz').value;
       if(a0184==''&& !feildIsNull(a3641)&& (a3641 == '7' || a3641 == '8' || a3641 == '9')) {
           return true;
       }else{
           var sysDate=document.getElementById('sysDate').value;//2019-11-01
           var nowYear  = sysDate.substring(0,4);
           var nowMonth = sysDate.substring(5,7);
           var nowDay   = sysDate.substring(8);

           //���������1��У��������¿� 2��У�����֤�ϵĳ�������
           //��ȡ��������
           var length=a3607.length;
           if(length==6){
               var inpYear  = a3607.substring(0,4);
               var inpMonth = a3607.substring(4);
               if(inpYear-nowYear>0||(inpYear-nowYear>=0&&inpMonth-nowMonth>0)){
                   $h.alert('ϵͳ��ʾ:','�������²�����',null,220);
                   return false;
               }else{
                   return true;
               }
           }else if(length==8){
               var inpYear  = a3607.substring(0,4);
               var inpMonth = a3607.substring(4,6);
               var inpDay = a3607.substring(6);
               if(inpYear-nowYear>0||(inpYear-nowYear>=0&&inpMonth-nowMonth>0)||(inpYear-nowYear>=0&&inpMonth-nowMonth>=0&&inpDay-nowDay>0)){
                   return false;
               }else{
                   return true;
               }
           }
	   }

   }
    //������������
   function checkAge(a3604a,a0184,a3607){

       // �ѹ� ���� ����Ҫ����У�� 5 �ѹ� 6 ��ʿ 7 ����
       var a3641=$("#a3641").val();
       if(a0184==''&& !feildIsNull(a3641)&& (a3641 == '7' || a3641 == '8' || a3641 == '9')){
           return true;
       }else{
           if(!checkA0184(a0184))
               return false;
           if(!checkA3607(a3607))
               return false;
           var a0184Temp=a0184.substring(6,12);
           var manN = ['����','��Ů����','��Ů����','��Ů����','Ů������','��������','��������','��������','��������','�游','���游','���游','����','����','�̸�','����','�̸�','�ɷ�','���÷�','���','�÷�','Ů��','��Ů��','��Ů��','��Ů��'];
           var womanN = ['��Ů����','����','��������','��������','��������','Ů������','������ĸ','������ĸ','������ĸ','������ĸ','��ĸ','����ĸ','����ĸ','ĸ��','��ĸ','��ĸ','��ĸ','��ĸ','��ĸ','��ĸ','ɩ��','��ϱ','����ϱ','����ϱ','����ϱ'];
           //����
           for(var i=0;i<manN.length;i++){
               if(a3604a==manN[i]){
                   //��ȡ����
                   var manAgeOne=jsGetAge(a0184Temp);
                   var manAgeTwo=jsGetAge(a3607);
                   if(manAgeOne==-1){
                       $h.alert('ϵͳ��ʾ��',a3604a+'���֤�ų������²���ʵ��',null,220);
                       return false;
                   }else if(manAgeOne<22){
                       $h.alert('ϵͳ��ʾ��',a3604a+'���֤������δ��22��',null,220);
                       return false;
                   }else if(manAgeTwo==-1){
                       $h.alert('ϵͳ��ʾ��',a3604a+'�������²���ʵ��',null,220);
                       return false;
                   }else if(manAgeTwo<22){
                       $h.alert('ϵͳ��ʾ��',a3604a+'������������δ��22��',null,220);
                       return false;
                   }
                   return true;
               }
           }
           //Ů��
           for(var i=0;i<womanN.length;i++){
               if(a3604a==womanN[i]){
                   //��ȡ����
                   var womanAgeOne=jsGetAge(a0184Temp);
                   var womanAgeTwo=jsGetAge(a3607);
                   if(womanAgeOne==-1){
                       $h.alert('ϵͳ��ʾ��',a3604a+'���֤�ų������²���ʵ��',null,220);
                       return false;
                   }else if(womanAgeOne<20){
                       $h.alert('ϵͳ��ʾ��',a3604a+'���֤������δ��20��',null,220);
                       return false;
                   }else if(womanAgeTwo==-1){
                       $h.alert('ϵͳ��ʾ��',a3604a+'�������²���ʵ��',null,220);
                       return false;
                   }else if(womanAgeTwo<20){
                       $h.alert('ϵͳ��ʾ��',a3604a+'������������δ��20��',null,220);
                       return false;
                   }
               }
           }
           return true;
       }

       //���ݳ������ڼ�������
       function jsGetAge(a0184Temp){
           var returnAge;
           var sysDate=document.getElementById('sysDate').value;//2019-11-01
           var birthYear = a0184Temp.substring(0,4);
           var birthMonth =a0184Temp.substring(4);

           ////////
           var nowYear = sysDate.substring(0,4);
           var nowMonth =sysDate.substring(5,7);

           if(nowYear == birthYear){
               returnAge = 0;//ͬ�� ��Ϊ0��
           }
           else{
               var ageDiff = nowYear - birthYear ; //��֮��
               if(ageDiff > 0){
                   var monthDiff = nowMonth - birthMonth;//��֮��
                   if(monthDiff < 0)
                   {
                       returnAge = ageDiff - 1;
                   }
                   else
                   {
                       returnAge = ageDiff ;
                   }
               }
               else
               {
                   returnAge = -1;//����-1 ��ʾ��������������� ���ڽ���
               }
           }
           return returnAge;//������������
	   }

}
   
    
    
    function modifyV(value){
		
		radow.doEvent("perInfo",value);
	}

	function deleteV(value){
		$h.confirm('ϵͳ��ʾ', '��ȷ��Ҫɾ����?',null, function(btn) {
			if ('ok' == btn) {
				radow.doEvent("doDel",value);
			} else {
				return;
			}
		});
		
		
	}

	function addpeople(){
	
		radow.doEvent("clearCondition");
	}


	function printExcel(){
		var docpath = document.getElementById('zippath').value;
		//alert(cxt_path+docpath);
		
		window.location.href=ctx_path+'/addFamServlet?method=downFileSys&prid='+encodeURI(encodeURI(docpath));
		//window.open('/hzb/js/web/viewer.html?file=http://127.0.0.1:8080'+cxt_path+docpath,"��ͥ��Ա.Pdf","top=200,left=200,height=600,width=800,status=yes,toolbar=1,menubar=no,location=no,scrollbars=yes");
		//window.open(cxt_path+'/addFamServlet?method=printExcel&prid='+encodeURI(encodeURI(docpath)),"��ͥ��Ա.Pdf","top=200,left=200,height=600,width=800,status=yes,toolbar=1,menubar=no,location=no,scrollbars=yes");
		/* if (window.ActiveXObject || "ActiveXObject" in window) {
	          //�ж��Ƿ�ΪIE�������"ActiveXObject" in window�ж��Ƿ�ΪIE11
	          //�ж��Ƿ�װ��adobe Reader
	          for (x = 2; x < 10; x++) {
	            try {
	              oAcro = eval("new ActiveXObject('PDF.PdfCtrl." + x + "');");
	              if (oAcro) {
	                flag = true;
	              }
	            } catch (e) {
	                flag = false;
	            }
	          }
	            try {
	              oAcro4 = new ActiveXObject('PDF.PdfCtrl.1');
	                if (oAcro4) {
	                  flag = true;
	                }
	            } catch (e) {
	                flag = false;
	            }

	            try {
	              oAcro7 = new ActiveXObject('AcroPDF.PDF.1');
	              if (oAcro7) {
	                flag = true;
	              }
	            } catch (e) {
	              flag = false;
	            }

	            if (flag) {//֧��
	            	document.getElementById('hidpdf').style.display='none';
                    $("#pdf").append('<iframe id="pdfview" style="height:100%;width:100%;" src="'+cxt_path+'/addFamServlet?method=downFileSys&prid='+encodeURI(encodeURI(docpath))+'"></iframe>');//������ʾ�ķ���
                    $("#pdfview").contentWindow.print();
	            }else {//��֧��
	              $("#pdfContent").append("�Բ���,����û�а�װPDF�Ķ��������,Ϊ�˷���Ԥ��PDF�ĵ�,��ѡ��װ��");
	              alert("�Բ���,����û�а�װPDF�Ķ��������,Ϊ�˷���Ԥ��PDF�ĵ�,��ѡ��װ��");
	              location = "http://ardownload.adobe.com/pub/adobe/reader/win/9.x/9.3/chs/AdbeRdr930_zh_CN.exe";
	            }

	        }else {
	                   
	        			document.getElementById('hidpdf').style.display='none';
	                    $("#pdf").append('<iframe style="height:100%;width:100%;" src="'+cxt_path+'/addFamServlet?method=downFileSys&prid='+encodeURI(encodeURI(docpath))+'"></iframe>');
	                    $("#pdfview").contentWindow.print();
	    		        } */
	


		
	}
	//��Ϣ����
	function Expexcel(){
		
		var docpath = document.getElementById('zippath').value;
		window.location.href=ctx_path+'/addFamServlet?method=downFileSys&prid='+encodeURI(encodeURI(docpath));
	 			
	}
    //��Ϣ����ǰ��ȷ��
    function ExpConfirm(count){
		$h.confirm('ϵͳ��ʾ', '��һ������'+count+'�ˣ��Ƿ�ȷ����',null, function(btn) {
			if ('ok' == btn) {
				radow.doEvent('expGrid');
			} else {
				return;
			}
		});
    }
    
    function setPageSize(){
		var gridId = 'MGrid';
		if (!Ext.getCmp(gridId)) {
			odin.error("Ҫ������grid�����ڣ�gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
		}
		var pageingToolbar = (grid.getBottomToolbar() || grid.getTopToolbar());
		if (pageingToolbar && pageingToolbar.pageSize) {
			
			gridIdForSeting = gridId;
			var url = contextPath + "/sys/comm/commSetGrid.jsp";
			doOpenPupWin(url, "����ÿҳ����", 300, 150);
		} else {
			odin.error("�Ƿ�ҳgrid����ʹ�ô˹��ܣ�");
			return;
		}
	}

function resizeframe(){
	var clientWidth = document.documentElement.clientWidth || document.body.clientWidth;
	var clientHeight = document.documentElement.clientHeight || document.body.clientHeight;
	
	var grid = Ext.getCmp('MGrid');
	var gbox=Ext.getCmp('famxx');
	var groupbox1=Ext.getCmp('perxx');
	grid.setHeight(clientHeight-document.getElementById("fam_xxDiv").offsetHeight-document.getElementById("famToolDiv").offsetHeight-document.getElementById("fileDiv").offsetHeight-14);
	grid.setWidth(clientWidth-document.getElementById("left-td").offsetWidth-10);
	//alert(clientWidth-document.getElementById("left-td").offsetWidth);
	gbox.setWidth(clientWidth-document.getElementById("left-td").offsetWidth-10);
	groupbox1.setWidth(clientWidth-document.getElementById("left-td").offsetWidth-10)
}

function resizeframe1(){
	var clientWidth = document.documentElement.clientWidth || document.body.clientWidth;
	var clientHeight = document.documentElement.clientHeight || document.body.clientHeight;
	var grid = Ext.getCmp('perGrid');
	grid.setHeight(clientHeight-document.getElementById("search-div").offsetHeight);
	
} 


function UpBtn(){	
	var grid = odin.ext.getCmp('MGrid');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	//alert(store.getCount());
	
	if (sm.length<=0){
		
		$h.alert('ϵͳ��ʾ��','��ѡ����Ҫ�����ְ��',null,180);
		return;	
	}
	
	var selectdata = sm[0];  //ѡ�����еĵ�һ��
	var index = store.indexOf(selectdata);
	if (index==0){
		
		$h.alert('ϵͳ��ʾ��','��ְ���Ѿ�������ϣ�',null,180);
		return;
	}
	
	store.remove(selectdata);  //�Ƴ�
	store.insert(index-1, selectdata);  //���뵽��һ��ǰ��
	
	grid.getSelectionModel().selectRow(index-1,true);  //ѡ�����ƶ������	
	
	grid.getView().refresh();
}

function DownBtn(){	
	var grid = odin.ext.getCmp('MGrid');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	if (sm.length<=0){
		
		$h.alert('ϵͳ��ʾ��','��ѡ����Ҫ�����ְ��',null,180);
		return;	
	}
	
	var selectdata = sm[0];  //ѡ�����еĵ�һ��
	var index = store.indexOf(selectdata);
	var total = store.getCount();
	if (index==(total-1) ){
		
		$h.alert('ϵͳ��ʾ��','��ְ���Ѿ���������£�',null,180);
		return;
	}
	
	store.remove(selectdata);  //�Ƴ�
	store.insert(index+1, selectdata);  //���뵽��һ��ǰ��
	
	grid.getSelectionModel().selectRow(index+1,true);  //ѡ�����ƶ������	
	grid.view.refresh();
}

$(function () {  
    $("#a0101").focus(function () {
    	var dept = document.getElementById("b0111").value;
    	var usertype = document.getElementById("usertype").value;  
        AutoComplete("popDiv", "a0101",'<%=request.getContextPath()%>',dept,usertype);
    });
   $("#a0101").keyup(function () {
    	var dept = document.getElementById("b0111").value;
    	var usertype = document.getElementById("usertype").value;  
        AutoComplete("popDiv", "a0101",'<%=request.getContextPath()%>',dept,usertype);
    });
   $("#seachName").keyup(function(){

	   if(event.keyCode == 13){

		   searchPersonByName();

	   }

	   });
});
//�����ϴ�
function dosub(){
	var a0000=$('#a0000s').val();
	if(a0000==''){
		$h.alert('ϵͳ��ʾ:','�ɲ���������Ϊ��!',null,220);
		document.getElementById('a0101').focus();
		return false;
	}
	

	
	frame.window.imp(a0000);
}
//��ѯ
function dosearch(){
	var a0000=$('#a0000s').val();
	if(a0000==''){
		$h.alert('ϵͳ��ʾ:','�ɲ���������Ϊ��!',null,220);
		document.getElementById('a0101').focus();
		return false;
	}
	radow.doEvent("dosearch");
}
//��������
function download(){
	var a0000=$("#a0000s").val();
	var t_ime=$("#sbny").val();
	if(a0000!=""){
		var path = '<%=request.getContextPath()%>/addFamServlet?method=downloadfile&a0000='+a0000+'&sbny='+t_ime;
		window.location.href=path;
		//alert(path);
		//console.log(path);
/* 		 var a = document.createElement('a');
		 a.href=path;
         a.click() */
	}else{

		$h.alert('ϵͳ��ʾ:','�ɲ���������Ϊ��!',null,220);

	}
	
}
//�����������
function saveMS(){
	var a0000=$('#a0000s').val();
	var t_ime=$('#sbny').val();
	var txtarea=$('#txtarea').val();
	if(a0000==''){
		$h.alert('ϵͳ��ʾ:','�ɲ���������Ϊ��!',null,220);
		document.getElementById('a0101').focus();
		return false;
	}else if(t_ime==''){
		$h.alert('ϵͳ��ʾ:','�ϱ����²���Ϊ��',null,220);
		document.getElementById('sbny').focus();
		return false;
	}
	radow.doEvent('saveMS');
}
//������ѯ
function searchPersonByName(){
	var dept=document.getElementById("dept").value;
	if(dept=='')
		{
		$h.alert('ϵͳ��ʾ','��������Ϊ��');
		return;
		}
	
	radow.doEvent('queryByName');
}

//��������ҳ��
function excelimp() {
	$h.showWindowWithSrc2('FamExcelImp', contextPath+ "/pages/FamilyMember/FamExcelImp.jsp", '���봰��', 600, 200, null,
			{maximizable:false,resizable:false,draggable:false}, true); 
}

	
	
	
function addexamine(){
	$h.openPageModeWin('addExamine','pages.FamilyMember.addExamine','���',1440,800,null,contextPath,null,
			{maximizable:false,resizable:true,draggable:false}, true)
	
}	

function dorecover(){
	var a0101=$("#a0101").val();

	if(a0101!=''&&a0101!=null){
		$h.confirm('ϵͳ��ʾ', '�Ƿ��'+a0101+'�ļ�ͥ��Ա��Ϣ�������ݻָ�',null, function(btn) {
			if ('ok' == btn) {
				radow.doEvent('dorecover');
			} else {
				return;
			}
		});

		}else{
			alert("���ݻָ�����!!!")	
			
		}


	
}

function clearFile()  {
	document.getElementById('frame').contentWindow.location.reload(true);
	radow.doEvent('queryFile');
}


</script>
</body>
</html>
