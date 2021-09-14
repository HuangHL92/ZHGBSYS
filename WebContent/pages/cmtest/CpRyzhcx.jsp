<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1">
	<odin:fill></odin:fill>
<ss:resetBtn></ss:resetBtn>
</ss:toolBar>
<ss:hlistDiv id="1" cols="4">
	<ss:query property="psquery" p="R" onchange="true"></ss:query>
	<ss:textEdit property="aac003_nonem" label="姓名"/>
	<ss:numberEdit property="aac001_nonem" label="人员身份ID" p="H"></ss:numberEdit>
	
</ss:hlistDiv>
<odin:tab id="tab">
   <odin:tabModel>
       <odin:tabItem title="基本信息" id="tab1"></odin:tabItem>
       <odin:tabItem title="月缴费信息" id="tab2"></odin:tabItem>
       <odin:tabItem title="变更信息" id="tab3"></odin:tabItem>
       <odin:tabItem title="业务属性" id="tab4"></odin:tabItem>
       <odin:tabItem title="身份属性" id="tab5" isLast="true"></odin:tabItem>
   </odin:tabModel>
   <odin:tabCont itemIndex="tab1">
       <ss:hlistDiv id="2" cols="3">
	     <ss:textEdit property="aae135" label="身份证号" p="D" required="ture"/>   
		 <ss:textEdit property="aac003" label="姓名" p="D"/>
		 <ss:select property="aab301" label="辖区" p="D"/>
		 <ss:select property="aaf015" label="街道" p="D"/>
		 <ss:select property="aac005" label="民族" p="D"/>
		 <ss:select property="eac026" label="人员状态" p="D"/>
		 <ss:textEdit property="aab004" label="单位名称" width="450" colspan="4" p="D"/>
	     <ss:textEdit property="aab001" label="单位编码" p="D"/>
	     <ss:select property="aac004" label="性别" p="D"/>
	     <ss:textEdit property="aic020" label="上报工资" p="D"/>
		 <ss:select property="aac009" label="户粮关系" p="D"/>
		 <ss:dateEdit property="aac006" label="出生日期" p="D"></ss:dateEdit>
		 <ss:select property="eac066" label="养老人员性质" p="D"/>
		 <ss:select property="eac238" label="外来务工标志" p="D"/>
		 <ss:select property="eac001" label="个人编码" p="D"/>
		 <ss:numberEdit property="ybsjjfnx" label="医保实际缴费年限" p="D"></ss:numberEdit>
		 <ss:numberEdit property="ybstnx" label="医保视同年限" p="D"></ss:numberEdit>						
		 <ss:select property="eac158" label="医保人员类型" p="D"/>	
		 <ss:select property="eac157" label="医保子类别" p="D"/>				
		 <ss:dateEdit property="aac007" label="参加工作时间" p="D"></ss:dateEdit>	
		 <ss:textEdit property="eab217" label="工号" p="D"/>
	     <ss:textEdit property="eab213" label="原养老单位编码" p="D"/>
		 <ss:textEdit property="aab030" label="纳税代码" p="D"/>
		 <ss:textEdit property="aae004" label="联系人" p="D"/>
		 <ss:textEdit property="aae006" label="联系地址" colspan="4" width="450" p="D"/>
		 <ss:textEdit property="aae005" label="联系电话" p="D"/>
		 <ss:textEdit property="aac010" label="户籍地" colspan="4" width="450" p="D"/>						
		 <ss:textEdit property="aae010" label="银行帐号" p="D"/>	
		 <ss:select property="eae363" label="临时账户标记" p="D"/>			
		 <ss:select property="aae008" label="委托机构" p="D"/>					
		 <ss:textEdit property="aae013" label="备注" p="D"/>
		 <ss:textEdit property="eac101" label="手机号码" p="D"/>		
		 <ss:textEdit property="aaz501" label="社保卡卡号" p="D"/>
		 <ss:textEdit property="aaz502" label="卡状态" p="D"/>			
		 <ss:textEdit property="aaz500" label="卡识别码" p="D"/>
	  </ss:hlistDiv>
	  
	  <odin:gridSelectColJs name="aae140"  codeType="AAE140" />
	  <odin:editgrid property="div_3" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="缴费基本信息" width="780" height="200">
		<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="aae140" />
			<odin:gridDataCol name="aab001" />
			<odin:gridDataCol name="aac008" />
			<odin:gridDataCol name="aae180" />
			<odin:gridDataCol name="aaa042" />
			<odin:gridDataCol name="aaa041" />
			<odin:gridDataCol name="aaa048" />
			<odin:gridDataCol name="aaa046" />
			<odin:gridDataCol name="aaz289" />
			<odin:gridDataCol name="aaa044" />
			<odin:gridDataCol name="zhzt" />
			<odin:gridDataCol name="aac049" />
			<odin:gridDataCol name="aaz001" isLast="true" />
	    </odin:gridJsonDataModel>
        <odin:gridColumnModel>
        <odin:gridRowNumColumn />
	  		<odin:gridColumn header="险种" width="60" dataIndex="aae140" editor="select" edited="true" codeType="AAE140"/>
	    	<odin:gridColumn header="单位编号" width="80" dataIndex="aab001" editor="text"/>
	    	<odin:gridColumn header="参保状态" width="91" dataIndex="aac008"  editor="text"/>
	    	<odin:gridColumn header="缴费基数" width="70" dataIndex="aae180" editor="number"/>
	    	<odin:gridColumn header="单位" width="50" dataIndex="aaa042" editor="number" />
	    	<odin:gridColumn header="个人" width="50" dataIndex="aaa041" editor="number" />
	    	<odin:gridColumn header="大病" width="50" dataIndex="aaa048" editor="number" />
	    	<odin:gridColumn header="公补" width="50" dataIndex="aaa046" editor="number" />
	    	<odin:gridColumn header="费率编码" width="70" dataIndex="aaz289" editor="text"/>
	    	<odin:gridColumn header="费率名称" width="200" dataIndex="aaa044" editor="text"/>
	    	<odin:gridColumn header="帐户状态" width="80" dataIndex="zhzt" editor="text"/>
	    	<odin:gridColumn header="建帐时间" width="70" dataIndex="aaa049" editor="date"/>
	    	<odin:gridColumn header="单位内码" width="60" dataIndex="aaz001" editor="number" isLast="true" />
       </odin:gridColumnModel>
      </odin:editgrid>
   </odin:tabCont>
   <odin:tabCont itemIndex="tab2">
       <ss:hlistDiv id="4" cols="6">
             <table>
             <tr>
             <td width="15%">
       		 <ss:select property="aae140" label="险种" codeType="AAE140" filter="aaa102 in ('01','02','03','04','05')" onchange="true"/>
       		 </td>
       		 <td width="40%">
       		 <ss:select property="eab204" label="账目处理标志" codeType="EAB204" allAsItem="true" onchange="true"/>
       		 </td>
       		 </tr>
       		 </table>
	   </ss:hlistDiv> 
	  <odin:gridSelectColJs name="aae140"  codeType="AAE140" />
	  <odin:gridSelectColJs name="eab205"  codeType="EAB205" />
	  <odin:gridSelectColJs name="eab206"  codeType="EAB206" />
	  <odin:gridSelectColJs name="eac158"  codeType="EAC158" />
	  <odin:gridSelectColJs name="eac157"  codeType="EAC157" />
	  <odin:gridSelectColJs name="eab204"  codeType="EAB204" />
	  <odin:gridSelectColJs name="aab033"  codeType="AAB033" />
	  <odin:gridSelectColJs name="aae078"  codeType="AAE078" />
	  <odin:gridSelectColJs name="eae249"  codeType="EAE249" />
	  <odin:gridSelectColJs name="eaz247"  codeType="EAZ247" />
	  <odin:editgrid property="div_5" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="月缴费信息" width="780" height="310">
		<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="aae140" />
			<odin:gridDataCol name="eae323" />
			<odin:gridDataCol name="eae324" />
			<odin:gridDataCol name="aae079" />
			<odin:gridDataCol name="aae002" />
			<odin:gridDataCol name="aae180" />
			<odin:gridDataCol name="aae058" />
			<odin:gridDataCol name="aae020" />
			<odin:gridDataCol name="aae022" />
			<odin:gridDataCol name="aae026" />
			<odin:gridDataCol name="aae024" />
			<odin:gridDataCol name="eac003" />
			<odin:gridDataCol name="aaz289" />
			<odin:gridDataCol name="eac158" />
			<odin:gridDataCol name="eac157" />
			<odin:gridDataCol name="eab202" />
			<odin:gridDataCol name="eab204" />
			<odin:gridDataCol name="aab033" />
			<odin:gridDataCol name="ead203" />
			<odin:gridDataCol name="eab203" />
			<odin:gridDataCol name="eae252" />
			<odin:gridDataCol name="aae078" />
			<odin:gridDataCol name="eae249" />
			<odin:gridDataCol name="eaz247" />
			<odin:gridDataCol name="aab004" />
			<odin:gridDataCol name="aab001" />
			<odin:gridDataCol name="eab213" />
			<odin:gridDataCol name="gzhr" />
			<odin:gridDataCol name="aae021" />
			<odin:gridDataCol name="aae023" />
			<odin:gridDataCol name="aae029" />
			<odin:gridDataCol name="eaz265" />
			<odin:gridDataCol name="eaz266" isLast="true" />
	    </odin:gridJsonDataModel>
        <odin:gridColumnModel>
        <odin:gridRowNumColumn />
	  		<ss:gridEditColumn header="险种" width="80" dataIndex="aae140" editor="select" codeType="AAE140"/>
	    	<ss:gridEditColumn header="应收年月" width="80" dataIndex="eae323" editor="number"/>
	    	<ss:gridEditColumn header="应收截止年月" width="100" dataIndex="eae324"  editor="number"/>
	    	<ss:gridEditColumn header="到帐年月" width="80" dataIndex="aae079" editor="number"/>
	    	<ss:gridEditColumn header="结算年月" width="80" dataIndex="aae002" editor="number" />
	    	<ss:gridEditColumn header="账目首次处理标志明细" width="140" dataIndex="eab205" editor="text" />
	    	<ss:gridEditColumn header="账目二次处理标志明细" width="140" dataIndex="eab206" editor="text" />
	    	<ss:gridEditColumn header="缴费基数" width="80" dataIndex="aae180" editor="number" />
	    	<ss:gridEditColumn header="缴费总额" width="80" dataIndex="aae058" editor="number"/>
	    	<ss:gridEditColumn header="单位缴费" width="80" dataIndex="aae020" editor="number"/>
	    	<ss:gridEditColumn header="个人缴费" width="80" dataIndex="aae022" editor="number"/>
	    	<ss:gridEditColumn header="大病缴费" width="80" dataIndex="aae026" editor="number"/>
	    	<ss:gridEditColumn header="公补缴费" width="80" dataIndex="aae024" editor="number"/>
	    	<ss:gridEditColumn header="记帐月数" width="80" dataIndex="eac003" editor="text"/>
	    	<ss:gridEditColumn header="费率编码" width="80" dataIndex="aaz289" editor="text"/>
	    	<ss:gridEditColumn header="医保类别" width="130" dataIndex="eac158" editor="select"/>
	    	<ss:gridEditColumn header="医保子类别" width="150" dataIndex="eac157" editor="select"/>
	        <ss:gridEditColumn header="应收类型" width="80" dataIndex="eab202" editor="text"/>
	    	<ss:gridEditColumn header="帐目处理标志" width="100" dataIndex="eab204" editor="select"/>
	    	<ss:gridEditColumn header="缴费方式" width="120" dataIndex="aab033" editor="select"/>
	    	<ss:gridEditColumn header="处理方式" width="80" dataIndex="ead203" editor="text"/>
	    	<ss:gridEditColumn header="应收类型明细" width="100" dataIndex="eab203" editor="text"/>
	    	<ss:gridEditColumn header="处理年月" width="80" dataIndex="eae252" editor="number"/>
	    	<ss:gridEditColumn header="到帐标志" width="80" dataIndex="aae078" editor="select"/>
	    	<ss:gridEditColumn header="到帐方式" width="80" dataIndex="eae249" editor="select"/>
	    	<ss:gridEditColumn header="低保标志" width="80" dataIndex="eaz247" editor="select"/>
	    	<ss:gridEditColumn header="单位名称" width="180" dataIndex="aab004" editor="text"/>
	    	<ss:gridEditColumn header="单位编码" width="80" dataIndex="aab001" editor="text"/>
	    	<ss:gridEditColumn header="原养老编码" width="80" dataIndex="eab213" editor="text"/>
	    	<ss:gridEditColumn header="个帐划入" width="80" dataIndex="gzhr" editor="text"/>
	    	<ss:gridEditColumn header="统筹划入" width="80" dataIndex="aae021" editor="number"/>
	    	<ss:gridEditColumn header="个人划入" width="80" dataIndex="aae023" editor="number"/>
	    	<ss:gridEditColumn header="公补划入" width="80" dataIndex="aae029" editor="number"/>
	    	<ss:gridEditColumn header="调整指数" width="70" dataIndex="eaz265" editor="number"/>
	    	<ss:gridEditColumn header="个人账目备注" width="60" dataIndex="eaz266" editor="text" isLast="true" />
       </odin:gridColumnModel>
      </odin:editgrid>
   </odin:tabCont>
   <odin:tabCont itemIndex="tab3">
       
	  <odin:gridSelectColJs name="eae202"  codeType="EAE202" />
	  <odin:gridSelectColJs name="eae204"  codeType="EAE204" />
	  <odin:gridSelectColJs name="aae140"  codeType="AAE140" />
	  <odin:gridSelectColJs name="aae011"  codeType="AAE011" />
	  <odin:gridSelectColJs name="eae224"  codeType="EAE224" />
	  <odin:editgrid property="div_7" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="变更信息" width="780" height="210">
		<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="eae202" />
			<odin:gridDataCol name="eae204" />
			<odin:gridDataCol name="aae140" />
			<odin:gridDataCol name="eae205" />
			<odin:gridDataCol name="aab001" />
			<odin:gridDataCol name="eab213" />
			<odin:gridDataCol name="eae206" />
			<odin:gridDataCol name="eae203" />
			<odin:gridDataCol name="aae011" />
			<odin:gridDataCol name="aae036" />
			<odin:gridDataCol name="aae037" />
			<odin:gridDataCol name="eae224" isLast="true" />
	    </odin:gridJsonDataModel>
        <odin:gridColumnModel>
        <odin:gridRowNumColumn />
	  		<ss:gridEditColumn header="业务类型" width="110" dataIndex="eae202" editor="select" codeType="EAE202"/>
	    	<ss:gridEditColumn header="变动原因" width="120" dataIndex="eae204" editor="select" codeType="EAE204"/>
	    	<ss:gridEditColumn header="险种" width="80" dataIndex="aae140"  editor="select" codeType="AAE140"/>
	    	<ss:gridEditColumn header="变动年月" width="80" dataIndex="eae205" editor="number"/>
	    	<ss:gridEditColumn header="原单位编码" width="80" dataIndex="eab213" editor="text" />
	    	<ss:gridEditColumn header="原养老单位编码" width="110" dataIndex="eab205" editor="text" />
	    	<ss:gridEditColumn header="变动备注" width="80" dataIndex="eae206" editor="text" />
	    	<ss:gridEditColumn header="业务备注" width="80" dataIndex="eae203" editor="text" />
	    	<ss:gridEditColumn header="操作员" width="80" dataIndex="aae011" editor="select"  codeType="AAE011"/>
	    	<ss:gridEditColumn header="操作时间" width="80" dataIndex="aae036" editor="text"/>
	    	<ss:gridEditColumn header="业务期" width="80" dataIndex="aae037" editor="number"/>
	    	<ss:gridEditColumn header="增减标志" width="80" dataIndex="eae224" editor="select"  isLast="true" />
       </odin:gridColumnModel>
      </odin:editgrid>
	  <odin:gridSelectColJs name="eae024"  codeType="EAE024" />
	  <odin:editgrid property="div_8" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="业务经办信息" width="780" height="210">
		<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="title" />
			<odin:gridDataCol name="aae002" />
			<odin:gridDataCol name="aae011" />
			<odin:gridDataCol name="aae036" />
			<odin:gridDataCol name="aae013" />
			<odin:gridDataCol name="eae024" />
			<odin:gridDataCol name="eae124" />
			<odin:gridDataCol name="eae125" />
			<odin:gridDataCol name="czcode" />
			<odin:gridDataCol name="czdate" />
			<odin:gridDataCol name="tbgyy" />
			<odin:gridDataCol name="frontver" />
			<odin:gridDataCol name="backver" isLast="true" />
	    </odin:gridJsonDataModel>
        <odin:gridColumnModel>
        <odin:gridRowNumColumn />
	  		<ss:gridEditColumn header="功能名称" width="110" dataIndex="title" editor="text"/>
	    	<ss:gridEditColumn header="业务年月" width="70" dataIndex="aae002" editor="number"/>
	    	<ss:gridEditColumn header="经办人" width="60" dataIndex="aae011"  editor="text"/>
	    	<ss:gridEditColumn header="经办日期" width="120" dataIndex="aae036" editor="text"/>
	    	<ss:gridEditColumn header="备注" width="80" dataIndex="aae013" editor="text" />
	    	<ss:gridEditColumn header="回退标志" width="70" dataIndex="eae024" editor="select" codeType="EAE024"/>
	    	<ss:gridEditColumn header="回退人" width="60" dataIndex="eae124" editor="text" />
	    	<ss:gridEditColumn header="回退日期" width="80" dataIndex="eae125" editor="text" />
	    	<ss:gridEditColumn header="审核人" width="60" dataIndex="czcode" editor="text"/>
	    	<ss:gridEditColumn header="审核日期" width="80" dataIndex="czdate" editor="text"/>
	    	<ss:gridEditColumn header="通不过原因" width="80" dataIndex="tbgyy" editor="text"/>
	    	<ss:gridEditColumn header="前台版本号" width="80" dataIndex="frontver" editor="number"/>
	    	<ss:gridEditColumn header="后台版本号" width="80" dataIndex="backver" editor="number"  isLast="true" />
       </odin:gridColumnModel>
      </odin:editgrid>
   </odin:tabCont>
   <odin:tabCont itemIndex="tab4">
	  <odin:gridSelectColJs name="aac155"  codeType="AAC155" />
	  <odin:gridSelectColJs name="aac156"  codeType="AAC156" />
	  <odin:gridSelectColJs name="aae100"  codeType="AAE100" />
	  <odin:editgrid property="div_9" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="变更信息" width="780" height="355">
		<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="aac155_z" />
			<odin:gridDataCol name="aac155" />
			<odin:gridDataCol name="aac156" />
			<odin:gridDataCol name="aae100" isLast="true" />
	    </odin:gridJsonDataModel>
        <odin:gridColumnModel>
        <odin:gridRowNumColumn />
	    	<ss:gridEditColumn header="属性编码" width="120" dataIndex="aac155_z" editor="text" />
	    	<ss:gridEditColumn header="属性编码值" width="150" dataIndex="aac155" editor="select" codeType="AAC155"/>
	    	<ss:gridEditColumn header="人员备注" width="150" dataIndex="aac156" editor="select"  codeType="AAC156"/>
	    	<ss:gridEditColumn header="有效标记" width="80" dataIndex="aae100" editor="select" codeType="AAE100" isLast="true"/>
       </odin:gridColumnModel>
      </odin:editgrid>
   </odin:tabCont>
     <odin:tabCont itemIndex="tab5">
	  <odin:gridSelectColJs name="eac202"  codeType="EAC202" />
	  <odin:gridSelectColJs name="eac203"  codeType="EAC203" />
	  <odin:gridSelectColJs name="aae100"  codeType="AAE100" />
	  <odin:editgrid property="div_10" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="变更信息" width="780" height="355">
		<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="eac202_z" />
			<odin:gridDataCol name="eac202" />
			<odin:gridDataCol name="eac203" />
			<odin:gridDataCol name="aae100" isLast="true" />
	    </odin:gridJsonDataModel>
        <odin:gridColumnModel>
        <odin:gridRowNumColumn />
    		<ss:gridEditColumn header="属性类型" width="120" dataIndex="eac202_z" editor="text" />
	    	<ss:gridEditColumn header="属性名称" width="150" dataIndex="eac202" editor="select" codeType="EAC202"/>
	    	<ss:gridEditColumn header="属性值" width="150" dataIndex="eac203" editor="select"  codeType="EAC203"/>
	    	<ss:gridEditColumn header="有效标记" width="80" dataIndex="aae100" editor="select" codeType="AAE100" isLast="true"/>
       </odin:gridColumnModel>
      </odin:editgrid>
   </odin:tabCont>
</odin:tab>