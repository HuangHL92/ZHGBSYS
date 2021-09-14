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
<title>家庭成员及社会关系</title>
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
							  	<tags:PublicTextIconEdit3 width="80" codetype="orgTreeJsonData"  onchange="a0201bChange" label="机构选择" property="dept" defaultValue="" />
							  </td>
							  <odin:textarea property="seachName" rows="1" style="width:120px; overflow: auto;"></odin:textarea>
								<td><odin:button text="搜索" property="searchOnePerson" handler="searchPersonByName" ></odin:button></td> 
							</tr>
						</table> 
					</div>
					<div id="search-btn" align="center" style="">
					
						<div id="">
						<odin:editgrid property="perGrid" title="人员信息" autoFill="false" width="330px" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/"  >
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
							    <odin:gridEditColumn header="人员主键"  dataIndex="a0000" align="center" editor="text" width="20" hidden="true" edited="false"/>
								<odin:gridEditColumn header="姓名" dataIndex="a0101" align="center" edited="false" editor="text" width="50" />
								<odin:gridEditColumn header="工作单位及职务" dataIndex="a0192" align="center" edited="false" editor="text" width="90"  />
								<odin:gridEditColumn header="审核状态" dataIndex="shzt" align="center" edited="false" editor="select"  width="70" renderer="shzt" />
								<odin:gridEditColumn header="出生日期" dataIndex="a0107" align="center" edited="false" editor="text" width="60" /> 
								<odin:gridEditColumn header="任职状态" dataIndex="rzzt" align="center" edited="false" editor="text" width="60" renderer="rzzt" isLast="true" /> 
							</odin:gridColumnModel>
						</odin:editgrid>
						</div>
						
					</div>
				</td>
				
				<td valign="top" id="right-td" style="padding-left: 10px"  >
					<div id="container-div" class="scrollbar overflow-y-hidden">
					
						<div id="fileDiv">
						
							<odin:groupBoxNew title="附件上传" property="perxx" contentEl="perxxDiv" collapsible="false" frame="true">
								<div id="perxxDiv">
									<table>
										<tr>
											<odin:textEdit property="a0101" label="&nbsp;&nbsp;&nbsp;干部姓名"  readonly="true"></odin:textEdit>
											<td><div id="popDiv"></div></td>
											<odin:dateEdit property="sbny" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;上报年月" format="Ym"  ></odin:dateEdit>
											<td colspan="1" align="left">
	       										<iframe id="frame"  name="frame" height="33px" width="370px" src="<%=request.getContextPath() %>/pages/FamilyMember/apfile.jsp" frameborder=”no” border=”0″ marginwidth=”0″ marginheight=”0″ scrolling=”no” allowtransparency=”yes”></iframe>
											</td>
										
										</tr>
										<tr>
										<odin:textEdit property="filename" label="&nbsp;已上传文件名"  readonly="true"></odin:textEdit>
										<td></td>
										<td></td>
										<td></td>
										
										<td>
								          
									                  <table class="bottom-btns">
										                   <tr>
										                       <td width="200px"></td>
											                  <td align="center" valign="middle"><odin:button text="附件提交"  property="dosub" handler="dosub" /></td>		
											                  <td width="10px"></td>
											                  <td align="center" valign="middle"><odin:button text="附件下载"  property="download" handler="download" /></td>
											
										                 </tr>
									                </table>		
								         
										</td>
										</tr>
										<tr>
											<td><span style="font-size: 12px">本人现任或曾任省部级及以上领导<br>干部（含已离退休）秘书的情况</span></td>
											<odin:textarea property="txtarea" colspan="5" rows="3" ></odin:textarea>
										</tr>
										
									</table>
								<div id="bottom-btns" align="center">
									<table class="bottom-btns">
										<tr>
											<td align="center" valign="middle"><odin:button text="保存"  property="saveMS" handler="saveMS" /></td>		
											
										</tr>
									</table>		
								</div>
								</div>

							
							
							</odin:groupBoxNew>
						
						
						</div>     
						<div id="fam_xxDiv">
							<odin:groupBoxNew title="添加家庭成员" property="famxx" contentEl="famDiv" collapsible="false" frame="true">
								<div id="famDiv">
									<table border="0" align="center" style="width: 100%" cellpadding="0" cellspacing="0">
										<tr>
											<odin:hidden   property="sysDate" />
											<odin:textEdit property="a3601" label="姓名"  />
											<odin:textEdit property="a3607" label="出生年月"   maxlength="6"  onblur="validateYearM()"  onkeyup="value=value.replace(/[^\d]/g,'')"   />
											<odin:textEdit property="a0184gz" label="身份证"  maxlength="18" onblur="validateIDCard()"/>
											<odin:textEdit property="a3611" label="工作单位及职务"></odin:textEdit>
										<tr>
											<odin:select2 property="a3604a" codeType="GB4761"  label="人员关系" />
											<td colspan="6">
												<odin:checkbox property="udcheck" label="&nbsp;&nbsp;是否为其他直系和三代以内旁系亲属中现任或曾任厅局级及以上职务，以及移居国（境）外的人员"/>
											</td>
											
										</tr>
										<tr>
											<odin:select2 property="a3627b" label="政治面貌" codeType="GB4762" />
											<tags:PublicTextIconEdit isLoadData="false"  property="a0111gz" label="籍贯" codename="code_name" codetype="ZB01" readonly="true"  />
											<tags:PublicTextIconEdit isLoadData="false"  property="a0115gz" label="居住地" codename="code_name" codetype="ZB01" readonly="true"  />
											<odin:select2 property="a0111gzb" label="国籍或地区"  codeType="GB2659"  />
										</tr>
										<tr>
											<odin:select2 property="a3621" label="民族"  codeType="GB3304"/>
											<tags:PublicTextIconEdit isLoadData="false"  property="a3631" label="人员身份" codename="code_name" codetype="ZB06" readonly="true"  />
											<odin:select2 property="a3641" label="人员现状" codeType="ZB56"/>
										</tr>
										
									</table>
								</div>
							</odin:groupBoxNew>
							
						<odin:toolBar property="famBtnToolBar" applyTo="famToolDiv">
							<odin:textForToolBar text=""/>
							
							<odin:fill/>
							<odin:separator/> 
							<odin:buttonForToolBar text="&nbsp;保存" id="save" icon="images/icon/save.gif" handler="doSave"/>
							<odin:separator></odin:separator>
							<odin:buttonForToolBar  text="&nbsp;新增"  id="add" icon="images/add.gif" handler="addpeople"    />
							<odin:separator></odin:separator>
							<odin:buttonForToolBar text="&nbsp;导出" id="exp" icon="images/icon/imp.gif"/>
							<odin:separator></odin:separator>
							<odin:buttonForToolBar text="批量导入" id="uploadbtn" handler="excelimp"  icon="images/icon/exp.png" />
							<odin:separator></odin:separator>
							<odin:buttonForToolBar text="数据恢复" id="recover" handler="dorecover"  icon="images/icon/exp.png" />
							<odin:separator></odin:separator>
						    <odin:buttonForToolBar  text="&nbsp;打印"  id="doPrint" icon="images/mylog.gif" isLast="true" />
						</odin:toolBar>

						</div>
						
						<div id="famToolDiv"></div>	
						<!-- 主列表 -->	     
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
							    <odin:gridEditColumn header="主键"  dataIndex="a3600" align="center" editor="text" width="100" hidden="true" edited="false"/>
								<odin:gridEditColumn header="称谓" dataIndex="a3604a" align="center" edited="false" editor="text" width="60" />
								<odin:gridEditColumn header="姓名" dataIndex="a3601" align="center" edited="false" editor="text" width="60" />
								<odin:gridEditColumn header="工作单位及职务" dataIndex="a3611" align="center" edited="false" editor="text" width="160" />
								<odin:gridEditColumn header="出生年月" dataIndex="a3607" align="center" edited="false" editor="text" width="60" />
								<odin:gridEditColumn header="政治面貌" dataIndex="a3627" align="center" edited="false" editor="text" width="80" />
								<odin:gridEditColumn header="身份证号" dataIndex="a0184gz" align="center" edited="false"  editor="text" width="120"/>
								<odin:gridEditColumn header="籍贯" dataIndex="a0111gz" align="center" edited="false" editor="text" width="70" />
								<odin:gridEditColumn header="居住地" dataIndex="a0115gz" align="center" edited="false" editor="text" width="70"/>
								<odin:gridEditColumn header="国籍或地区" dataIndex="a0111gzb" align="center" edited="false" editor="text" width="60" />
								<odin:gridEditColumn header="民族" dataIndex="a3621" align="center" edited="false" editor="text" width="60"  />
								<odin:gridEditColumn header="人员身份" dataIndex="a3631" align="center" edited="false" editor="text" width="60" />
								<odin:gridEditColumn header="人员现状" dataIndex="a3641" align="center" edited="false" editor="text"  width="80" />
								<odin:gridEditColumn header="审核状态" dataIndex="a3645" align="center" edited="false" editor="select"  width="80" renderer="shzt" />
								<odin:gridEditColumn editor="select" dataIndex="updated" hidden="true" edited="false" codeType="A36_GROUP" header="分类" width="40" />
								<odin:gridEditColumn header="操作" dataIndex="a3600" align="center" edited="false" editor="text" width="80" renderer="dodetail" isLast="true" />
							</odin:gridColumnModel>
						</odin:editgrid>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<!-- 该JS文件需在页面元素末尾加载 -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/RelateSearch.js"></script>
	<script type="text/javascript" src="<%=ctxPath%>/js/templete/templete-default.js"></script>
	<odin:hidden property="a0000" title="人员主键"></odin:hidden>
	<odin:hidden property="a0000s" title="附件上传人员主键" />
	<odin:hidden property="a3600" title="家庭成员主键"/>
	<odin:hidden property="usertype" title="用户登录名" />
	<odin:hidden property="b0111" title="单位"/>
	<odin:hidden property="zippath"  title="导出路径"/>
	<odin:hidden property="jtcy" title="家庭成员" />
	<odin:hidden property="userid" title="用户id" />
	<odin:hidden property="sql" title="sql"/>
	
 <script type="text/javascript">  
 	var maxHeight = window.parent.document.body.offsetHeight;
 
    Ext.onReady(function() {


      
		//自适应大小
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
 							    text:'设置每页条数',
 							    handler:setPageSize
 							}),
 							new Ext.Button({
 								icon : 'images/icon/arrowup.gif',
 								id:'UpBtn',
 							    text:'上移',
 							    cls :'inline',
 							    //renderTo:"btngroup",
 							    handler:UpBtn
 							}),
 							new Ext.Button({
 								icon : 'images/icon/arrowdown.gif',
 								id:'DownBtn',
 							    text:'下移',
 							    cls :'inline pl',
 							    //renderTo:"btngroup",
 							    handler:DownBtn
 							}),
 							new Ext.Button({
 								icon : 'images/icon/save.gif',
 								id:'saveSortBtn',
 							    text:'保存排序',
 							    cls :'inline pl',
 							    //renderTo:"btngroup",
 							    handler:function(){
 									radow.doEvent('worksort');
 							    }
 							}),

 							]);
    	
    });
    function dodetail(value, params, rs, rowIndex, colIndex, ds){
		return "<a href=\"javascript:deleteV('" + value + "')\">删除</a>&nbsp;"
		+"&nbsp;<a href=\"javascript:modifyV('" + value + "')\">修改</a>"
	}
    function shzt(value, params, rs, rowIndex, colIndex, ds){
		if(value == '0')
			return "未审核";
		else 
			return "已审核";
	}
    function rzzt(value, params, rs, rowIndex, colIndex, ds){
		if(value == '1')
			return "现职";
		else 
			return "非现职";
	}
    function a0201bChange() {
    	var dept=document.getElementById("dept").value;
    	if(dept=='')
    		{
    		$h.alert('系统提示','机构不能为空');
    		return;
    		}
    	radow.doEvent('queryByName');
    }
    
	
  //失去焦点验证身份证以及出生年月是否填写
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
	
	//失去焦点验证出生年月
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
			$h.alert('系统提示:','请先选择左边人员!',null,220);
			return false;
			}
		if(a3601==''){
			$h.alert('系统提示:','姓名不能为空!',null,220);
			document.getElementById('a3601').focus();
			return false;
		}
        var a3641 = $("#a3641").val();
        if (!checkA0184(a0184) && !feildIsNull(a3641) && (a3641 == '7' || a3641 == '8' || a3641 == '9'))//身份证
        {
            return false;
        }
		if(a3604a==''){
			$h.alert('系统提示:','人员关系不能为空!',null,220);
			//document.getElementById('a3604a').focus();
			return false;
		}
		if(a3604a!=''&&!(checkAge(a3604a,a0184,a3607))){//年龄
				return false;
		}
		if(!checkA3607(a3607)){//出生年月
			return false;
		}


		
		radow.doEvent('dosave', '');
	}

    //检测身份证合法性
   function checkA0184(a0184){
        // 已故 不详 不需要进行校验 5 已故 6 烈士 7 不详
       var a3641=$("#a3641").val();
       var a0111gzb = $("#a0111gzb").val();
       //alert(a0111gzb);
       if(a0184==''&& !feildIsNull(a3641)&& (a3641 == '7' || a3641 == '8' || a3641 == '9' || (a0111gzb != '156'&&!feildIsNull(a0111gzb))  )){
            return true;
       }else  if(a0184==''){
			$h.alert('系统提示：','身份证号不能为空!    人员状态为已故、烈士、不详时或者国籍为国外的可不录入身份证信息。',null,220);
			//document.getElementById('a0184gz').focus();
			return false;
		}
		var a0184YearM=a0184.substring(6,14);
		if(a0184!=''){
// 			var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/; 
			var reg = /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
			  if(reg.test(a0184) === false) 
			  { 
				$h.alert('系统提示：','身份证输入不合法',null,220);
				//document.getElementById('a0184gz').focus();
			    return false; 
			  }else if(!checkA3607(a0184YearM)){
				  $h.alert('系统提示：','身份证输入不合理',null,220);
				  //document.getElementById('a0184gz').focus();
				  return false;
			  }else{
				  return true;
			  }
		}
   }
    
 //检验出生年月格式
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

           //两种情况：1，校验出生年月框 2，校验身份证上的出生年月
           //获取参数长度
           var length=a3607.length;
           if(length==6){
               var inpYear  = a3607.substring(0,4);
               var inpMonth = a3607.substring(4);
               if(inpYear-nowYear>0||(inpYear-nowYear>=0&&inpMonth-nowMonth>0)){
                   $h.alert('系统提示:','出生年月不合理',null,220);
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
    //检测年龄合理性
   function checkAge(a3604a,a0184,a3607){

       // 已故 不详 不需要进行校验 5 已故 6 烈士 7 不详
       var a3641=$("#a3641").val();
       if(a0184==''&& !feildIsNull(a3641)&& (a3641 == '7' || a3641 == '8' || a3641 == '9')){
           return true;
       }else{
           if(!checkA0184(a0184))
               return false;
           if(!checkA3607(a3607))
               return false;
           var a0184Temp=a0184.substring(6,12);
           var manN = ['公公','长女公公','次女公公','三女公公','女儿公公','长子岳父','次子岳父','三子岳父','儿子岳父','祖父','曾祖父','外祖父','父亲','养父','继父','岳父','姨父','丈夫','表妹夫','姐夫','妹夫','女婿','长女婿','次女婿','三女婿'];
           var womanN = ['长女婆婆','婆婆','长子婆婆','次子婆婆','三子婆婆','女儿婆婆','长子岳母','次子岳母','三子岳母','儿子岳母','祖母','曾祖母','外祖母','母亲','养母','继母','岳母','舅母','伯母','婶母','嫂子','儿媳','长子媳','次子媳','三子媳'];
           //男性
           for(var i=0;i<manN.length;i++){
               if(a3604a==manN[i]){
                   //获取年龄
                   var manAgeOne=jsGetAge(a0184Temp);
                   var manAgeTwo=jsGetAge(a3607);
                   if(manAgeOne==-1){
                       $h.alert('系统提示：',a3604a+'身份证号出生年月不合实际',null,220);
                       return false;
                   }else if(manAgeOne<22){
                       $h.alert('系统提示：',a3604a+'身份证号年龄未满22岁',null,220);
                       return false;
                   }else if(manAgeTwo==-1){
                       $h.alert('系统提示：',a3604a+'出生年月不合实际',null,220);
                       return false;
                   }else if(manAgeTwo<22){
                       $h.alert('系统提示：',a3604a+'出生年月年龄未满22岁',null,220);
                       return false;
                   }
                   return true;
               }
           }
           //女性
           for(var i=0;i<womanN.length;i++){
               if(a3604a==womanN[i]){
                   //获取年龄
                   var womanAgeOne=jsGetAge(a0184Temp);
                   var womanAgeTwo=jsGetAge(a3607);
                   if(womanAgeOne==-1){
                       $h.alert('系统提示：',a3604a+'身份证号出生年月不合实际',null,220);
                       return false;
                   }else if(womanAgeOne<20){
                       $h.alert('系统提示：',a3604a+'身份证号年龄未满20岁',null,220);
                       return false;
                   }else if(womanAgeTwo==-1){
                       $h.alert('系统提示：',a3604a+'出生年月不合实际',null,220);
                       return false;
                   }else if(womanAgeTwo<20){
                       $h.alert('系统提示：',a3604a+'出生年月年龄未满20岁',null,220);
                       return false;
                   }
               }
           }
           return true;
       }

       //根据出生日期计算年龄
       function jsGetAge(a0184Temp){
           var returnAge;
           var sysDate=document.getElementById('sysDate').value;//2019-11-01
           var birthYear = a0184Temp.substring(0,4);
           var birthMonth =a0184Temp.substring(4);

           ////////
           var nowYear = sysDate.substring(0,4);
           var nowMonth =sysDate.substring(5,7);

           if(nowYear == birthYear){
               returnAge = 0;//同年 则为0岁
           }
           else{
               var ageDiff = nowYear - birthYear ; //年之差
               if(ageDiff > 0){
                   var monthDiff = nowMonth - birthMonth;//月之差
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
                   returnAge = -1;//返回-1 表示出生日期输入错误 晚于今天
               }
           }
           return returnAge;//返回周岁年龄
	   }

}
   
    
    
    function modifyV(value){
		
		radow.doEvent("perInfo",value);
	}

	function deleteV(value){
		$h.confirm('系统提示', '你确定要删除吗?',null, function(btn) {
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
		//window.open('/hzb/js/web/viewer.html?file=http://127.0.0.1:8080'+cxt_path+docpath,"家庭成员.Pdf","top=200,left=200,height=600,width=800,status=yes,toolbar=1,menubar=no,location=no,scrollbars=yes");
		//window.open(cxt_path+'/addFamServlet?method=printExcel&prid='+encodeURI(encodeURI(docpath)),"家庭成员.Pdf","top=200,left=200,height=600,width=800,status=yes,toolbar=1,menubar=no,location=no,scrollbars=yes");
		/* if (window.ActiveXObject || "ActiveXObject" in window) {
	          //判断是否为IE浏览器，"ActiveXObject" in window判断是否为IE11
	          //判断是否安装了adobe Reader
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

	            if (flag) {//支持
	            	document.getElementById('hidpdf').style.display='none';
                    $("#pdf").append('<iframe id="pdfview" style="height:100%;width:100%;" src="'+cxt_path+'/addFamServlet?method=downFileSys&prid='+encodeURI(encodeURI(docpath))+'"></iframe>');//调用显示的方法
                    $("#pdfview").contentWindow.print();
	            }else {//不支持
	              $("#pdfContent").append("对不起,您还没有安装PDF阅读器软件呢,为了方便预览PDF文档,请选择安装！");
	              alert("对不起,您还没有安装PDF阅读器软件呢,为了方便预览PDF文档,请选择安装！");
	              location = "http://ardownload.adobe.com/pub/adobe/reader/win/9.x/9.3/chs/AdbeRdr930_zh_CN.exe";
	            }

	        }else {
	                   
	        			document.getElementById('hidpdf').style.display='none';
	                    $("#pdf").append('<iframe style="height:100%;width:100%;" src="'+cxt_path+'/addFamServlet?method=downFileSys&prid='+encodeURI(encodeURI(docpath))+'"></iframe>');
	                    $("#pdfview").contentWindow.print();
	    		        } */
	


		
	}
	//信息导出
	function Expexcel(){
		
		var docpath = document.getElementById('zippath').value;
		window.location.href=ctx_path+'/addFamServlet?method=downFileSys&prid='+encodeURI(encodeURI(docpath));
	 			
	}
    //信息导出前的确认
    function ExpConfirm(count){
		$h.confirm('系统提示', '将一共导出'+count+'人，是否确定？',null, function(btn) {
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
			odin.error("要导出的grid不存在！gridId=" + gridId);
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
			doOpenPupWin(url, "设置每页条数", 300, 150);
		} else {
			odin.error("非分页grid不能使用此功能！");
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
		
		$h.alert('系统提示：','请选中需要排序的职务！',null,180);
		return;	
	}
	
	var selectdata = sm[0];  //选中行中的第一行
	var index = store.indexOf(selectdata);
	if (index==0){
		
		$h.alert('系统提示：','该职务已经排在最顶上！',null,180);
		return;
	}
	
	store.remove(selectdata);  //移除
	store.insert(index-1, selectdata);  //插入到上一行前面
	
	grid.getSelectionModel().selectRow(index-1,true);  //选中上移动后的行	
	
	grid.getView().refresh();
}

function DownBtn(){	
	var grid = odin.ext.getCmp('MGrid');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	if (sm.length<=0){
		
		$h.alert('系统提示：','请选中需要排序的职务！',null,180);
		return;	
	}
	
	var selectdata = sm[0];  //选中行中的第一行
	var index = store.indexOf(selectdata);
	var total = store.getCount();
	if (index==(total-1) ){
		
		$h.alert('系统提示：','该职务已经排在最底下！',null,180);
		return;
	}
	
	store.remove(selectdata);  //移除
	store.insert(index+1, selectdata);  //插入到上一行前面
	
	grid.getSelectionModel().selectRow(index+1,true);  //选中上移动后的行	
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
//附件上传
function dosub(){
	var a0000=$('#a0000s').val();
	if(a0000==''){
		$h.alert('系统提示:','干部姓名不能为空!',null,220);
		document.getElementById('a0101').focus();
		return false;
	}
	

	
	frame.window.imp(a0000);
}
//查询
function dosearch(){
	var a0000=$('#a0000s').val();
	if(a0000==''){
		$h.alert('系统提示:','干部姓名不能为空!',null,220);
		document.getElementById('a0101').focus();
		return false;
	}
	radow.doEvent("dosearch");
}
//附件下载
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

		$h.alert('系统提示:','干部姓名不能为空!',null,220);

	}
	
}
//保存秘书情况
function saveMS(){
	var a0000=$('#a0000s').val();
	var t_ime=$('#sbny').val();
	var txtarea=$('#txtarea').val();
	if(a0000==''){
		$h.alert('系统提示:','干部姓名不能为空!',null,220);
		document.getElementById('a0101').focus();
		return false;
	}else if(t_ime==''){
		$h.alert('系统提示:','上报年月不能为空',null,220);
		document.getElementById('sbny').focus();
		return false;
	}
	radow.doEvent('saveMS');
}
//姓名查询
function searchPersonByName(){
	var dept=document.getElementById("dept").value;
	if(dept=='')
		{
		$h.alert('系统提示','机构不能为空');
		return;
		}
	
	radow.doEvent('queryByName');
}

//批量导入页面
function excelimp() {
	$h.showWindowWithSrc2('FamExcelImp', contextPath+ "/pages/FamilyMember/FamExcelImp.jsp", '导入窗口', 600, 200, null,
			{maximizable:false,resizable:false,draggable:false}, true); 
}

	
	
	
function addexamine(){
	$h.openPageModeWin('addExamine','pages.FamilyMember.addExamine','审核',1440,800,null,contextPath,null,
			{maximizable:false,resizable:true,draggable:false}, true)
	
}	

function dorecover(){
	var a0101=$("#a0101").val();

	if(a0101!=''&&a0101!=null){
		$h.confirm('系统提示', '是否对'+a0101+'的家庭成员信息进行数据恢复',null, function(btn) {
			if ('ok' == btn) {
				radow.doEvent('dorecover');
			} else {
				return;
			}
		});

		}else{
			alert("数据恢复出错!!!")	
			
		}


	
}

function clearFile()  {
	document.getElementById('frame').contentWindow.location.reload(true);
	radow.doEvent('queryFile');
}


</script>
</body>
</html>
