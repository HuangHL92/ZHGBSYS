<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<div>
<div id="bar_div" style="margin-top:0px;display:none;"></div>
</div>
<odin:toolBar property="btnToolBar" applyTo="bar_div">
	<odin:textForToolBar text="<h3></h3>"/>
	<odin:separator></odin:separator>
	<odin:fill/>
	<odin:buttonForToolBar text="�б�" icon="" handler="liebiao_l" id="liebiao_tb"  ></odin:buttonForToolBar>
	<odin:separator/>
	<odin:buttonForToolBar text="���" icon="" handler="ctable" id="ctable"  ></odin:buttonForToolBar>
	<odin:separator/>
	<odin:buttonForToolBar text="����" icon="" handler="cwenzi" id="cwenzi" ></odin:buttonForToolBar>
	<odin:separator/>
	<odin:buttonForToolBar text="��״ͼ" handler="cbingzhuang" id="cbingzhuang"  ></odin:buttonForToolBar>
	<odin:separator/>
	<odin:buttonForToolBar text="����" icon="" handler="exportData" id="export" isLast="true"></odin:buttonForToolBar>
	
</odin:toolBar>