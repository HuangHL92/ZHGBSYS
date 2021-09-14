<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<div id="fancha_div" style="width:100%;height:100%;margin: 0 auto;">
	<odin:toolBar property="toorbartopgrid">
		<odin:textForToolBar text="提示:仅统计现职务层次（职务等级）存在统计表格中的人员"/>
		<odin:separator></odin:separator>
		<odin:fill/>
		<odin:buttonForToolBar text="统计分析" icon="" id="tjfx" handler="initTj_func" isLast="true" ></odin:buttonForToolBar>
	</odin:toolBar>
	<odin:editgrid property="gridfc" title="" topBarId="toorbartopgrid" height="505"
		 autoFill="true" bbarId="pageToolBar">
		<odin:gridJsonDataModel>
			<odin:gridDataCol name="a0101" />
			<odin:gridDataCol name="a0104" />
			<odin:gridDataCol name="a0117" />
			<odin:gridDataCol name="a0184" />
			<odin:gridDataCol name="a0221" />
			<odin:gridDataCol name="a0192a" isLast="true" />
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn></odin:gridRowNumColumn>
			<odin:gridEditColumn header="姓名"  editor="text" dataIndex="a0101" edited="false" width="70" />
			<odin:gridEditColumn header="性别"  editor="select" dataIndex="a0104" codeType="a0104" edited="false" width="40"/>
			<odin:gridEditColumn header="民族"  editor="select" dataIndex="a0117" codeType="a0117" edited="false" width="70"/>
			<odin:gridEditColumn header="身份证号"  editor="text" dataIndex="a0184" edited="false" width="150"/>
			<odin:gridEditColumn header="现职务层次"  editor="select" dataIndex="a0221" codeType="a0221" edited="false" width="150"/>
			<odin:gridEditColumn header="单位名称" editor="text" dataIndex="a0192a" edited="false" isLast="true" width="200"/>
		</odin:gridColumnModel>
		<odin:gridJsonData>
			{
		        data:[]
		    }
		</odin:gridJsonData>
	</odin:editgrid>
</div>