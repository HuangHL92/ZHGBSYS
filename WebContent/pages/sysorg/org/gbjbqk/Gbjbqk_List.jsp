<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<div id="fancha_div" style="width:100%;height:100%;margin: 0 auto;">
	<odin:toolBar property="toorbartopgrid">
		<odin:textForToolBar text="��ʾ:��ͳ����ְ���Σ�ְ��ȼ�������ͳ�Ʊ���е���Ա"/>
		<odin:separator></odin:separator>
		<odin:fill/>
		<odin:buttonForToolBar text="ͳ�Ʒ���" icon="" id="tjfx" handler="initTj_func" isLast="true" ></odin:buttonForToolBar>
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
			<odin:gridEditColumn header="����"  editor="text" dataIndex="a0101" edited="false" width="70" />
			<odin:gridEditColumn header="�Ա�"  editor="select" dataIndex="a0104" codeType="a0104" edited="false" width="40"/>
			<odin:gridEditColumn header="����"  editor="select" dataIndex="a0117" codeType="a0117" edited="false" width="70"/>
			<odin:gridEditColumn header="���֤��"  editor="text" dataIndex="a0184" edited="false" width="150"/>
			<odin:gridEditColumn header="��ְ����"  editor="select" dataIndex="a0221" codeType="a0221" edited="false" width="150"/>
			<odin:gridEditColumn header="��λ����" editor="text" dataIndex="a0192a" edited="false" isLast="true" width="200"/>
		</odin:gridColumnModel>
		<odin:gridJsonData>
			{
		        data:[]
		    }
		</odin:gridJsonData>
	</odin:editgrid>
</div>