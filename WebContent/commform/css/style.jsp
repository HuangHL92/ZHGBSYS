<%@ page contentType="text/css; charset=GBK" language="java" %>

body
{
	PADDING-RIGHT: 0px; PADDING-LEFT: 0px; BACKGROUND: #fff; PADDING-BOTTOM: 0px;  PADDING-TOP: 0px; FONT-SIZE: 12px;
	margin-left: 4px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	SCROLLBAR-FACE-COLOR: #E9E6D3;
	SCROLLBAR-HIGHLIGHT-COLOR: #B4AB94;
	SCROLLBAR-SHADOW-COLOR: #C3C2A6;
	SCROLLBAR-ARROW-COLOR: #4C432E;
	SCROLLBAR-TRACK-COLOR: #F9F9EF;
	SCROLLBAR-DARKSHADOW-COLOR: #F1F1D8;
	SCROLLBAR-BASE-COLOR: #F5F4EB;
	STYLE: #ffcf60;
	background-color: #f5f4eb;
}

table
{
	font-size:9pt;
	font-family: "宋体","Arial Narrow", "Times New Roman";
	font-weight:normal;
	width:100%;
}

/*table*/
.tableDisplay{}
.tableHidden{display:none;}
table.hiddenTable td.hiddenTableHead
{
    color: #000000;
	text-align : center;
	height:16px;
    background-color: #DEE7CE;
    overflow : visible;
	text-overflow : ellipsis ;
}
/*在有表单元素的table中引用*/
.tableTitle
{
	border-width:0px 0px 0px 0px;
	border-style:solid;
	border-color:#BCBCBC #BCBCBC #BCBCBC #BCBCBC;
	text-align : right;
    word-wrap : break-word ;
    word-break : keep-all ;
	width:95px;
}
table.tableTitle td
{
	/*background-color: #E6EDFA;*/
    /*background-color: #EEEEEE;*/
	font-family: "Times New Roman","宋体","Arial Narrow";
	font-size: 8pt;
    font-weight:none ;
    height:16px;
	color:#0066BE;
	text-align :center;
	width:100%;
}
.tableInput
{
	/*background-color: #F5F5F5;*/
	font-size:9pt;
	border-width:1px 1px 1px 1px;
	border-style:solid;
	border-color:#BCBCBC #FFFFFF #FFFFFF #BCBCBC;
	font-family: "宋体";
	align:"left";
	width:100%;

}
table.tableInput td
{
	border-width:1px 1px 1px 1px;
	border-style:solid;
	border-color:#FFFFFF  #BCBCBC  #BCBCBC  #FFFFFF;
	font-family: "宋体";
	font-size: 9pt;
	text-align :right;
    padding:0px 3px 0px 3px;
    height:20px;
}
/*查询*/
.queryTableInput
{
	/*background-color: #F5F5F5;*/
	font-size:9pt;
	border-width:0px 0px 1px 0px;
	border-style:solid;
	border-color:#BCBCBC #FFFFFF #BCBCBC #BCBCBC;
	font-family: "宋体";
}

table.queryTableInput td
{
	border-width:0px 0px 0px 0px;
	border-style:solid;
	/*border-color:#FFFFFF  #BCBCBC  #BCBCBC  #FFFFFF;*/
	font-family: "宋体";
	font-size: 9pt;
	text-align :right;
    padding:0px 3px 0px 3px;
    height:20px;
}

.queryBlank{
	/*background-color: #F5F5F5;*/
	font-size:9pt;
	border-color:#BCBCBC #FFFFFF #FFFFFF #BCBCBC;
	font-family: "宋体";
}
label
{
    border-width:1px 1px 1px 1px;
	border-style:solid;
	border-color:#B7B7B7  #B7B7B7  #B7B7B7  #B7B7B7;
	font-family: "宋体";
	font-size: 9pt;
   	color:#2D65DC;
	text-align :left;
    padding:2px 2px 3px 2px;
    height:18px;
    width:100%;
    background-color:#F0F0F0;
    word-wrap : break-word ;
    word-break : keep-all ;
}
.colorButtonText {
	color:#000000;
	height:18px;
	background-repeat:no-repeat;
	font-size: 12px;
	padding-bottom: 13px;
    background-image:url(<%=request.getContextPath()%>/images/left0.gif);
   text-align :center;
}
/*页面的顶部使用，例如：单位基本信息*/
.titleLine {
	background-color: #F5F5F5;
	border-width:1px 1px 1px 1px;
	border-style:solid;
	border-color:#BCBCBC #FFFFFF #BCBCBC #FFFFFF;
	text-align : left;
}
table.titleLine td
{
  padding:0px 15px 0px 20px;
  FILTER: glow(color=ffffff,strength=0) shadow(color=cccccc,direction:135);
  POSITION: relative;
  font-family: "Times New Roman","宋体","Arial Narrow";
  font-size: 10pt;
  font-weight:none ;
  color:#0066BE;
}
/**/
.tableList
{
	background-color: #185090;
    padding:0.1px 0.1px 0.1px 0.1px;
	/*border-width:1px 1px 1px 1px;*/
	border-style:solid;
	/*border-color:#BCBCBC #BCBCBC #BCBCBC #BCBCBC;*/
	text-align : right;

}
table.tableList td.tableHead
{
	/*font-weight:bolder;*/
    color: #000000;
	text-align : center;
    text-valign : middle;
	height:24px;
	/*background-color: #CBD5F3;*/
	/*background-color: #ffffff;*/
    background-color: #5C9FEF;
	border-top-width: 0.1mm;
	border-right-width: 0.1mm;
	border-bottom-width: 0.1mm;
	border-left-width: 0.1mm;

    overflow : visible;
	text-overflow : ellipsis ;

	/*
    white-space : pre;
    overflow : hidden;
	text-overflow : ellipsis ;
    filter : progid:DXImageTransform.Microsoft.gradient(startColorStr=#550000FF,endColorStr=#00000000);
   */
}
/**/
table.tableList td
{
	border-top-width: 0px;
	border-right-width: 0px;
	border-bottom-width: 0px;
	border-left-width: 0px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #FFFFFF;
	border-right-color: #999999;
	border-bottom-color: #999999;
	border-left-color: #FFFFFF;

	text-align : left;
	overflow : hidden;
	text-overflow : ellipsis ;
	height:16px;
	/*white-space : pre;*/
}
/**/
.listColorA {
	background-color: #FFFFFF;
	font-family: "宋体";
	font-size: 9pt;
	font-color: #000000;
	height:20px;

	white-space : pre;

}
.listColorB {
    /*background-color: #E6EDFA;*/
	background-color: #F7f7f7;
	font-family: "宋体";
	font-size: 9pt;
	font-color: #000000;
	height:20px;

	white-space : pre;
}
/**/
table.tableList td.listCheckbox
{
	border-top-width: 0px;
	border-right-width: 0px;
	border-bottom-width: 0px;
	border-left-width: 0px;

	text-align : center;
	overflow : hidden;
	text-overflow : ellipsis ;

	/*white-space : pre;*/
}
/**/

.tdLeft
{
    text-align : left;
}

.tdRight
{
    text-align : right;
}

/*Input*/
table.tableInput input[type=text] { border: 1px #B7B7B7 solid;font-family: "宋体",font-size: 12px; Helvetica, sans-serif;width: 100%;}
table.tableInput   select  { border: 1px #B7B7B7 solid;font-family: "宋体",font-size: 12px;background: #ffffff;width: 100%}
table.tableInput  textarea { border: 1px #B7B7B7 solid;font-family: "宋体",font-size: 12px; Helvetica, sans-serif;width: 100%; overflow:hidden;}

.text{
    OVERFLOW: hidden ;
    border: 1px solid;
    font-family: "宋体",font-size: 12px; Helvetica, sans-serif;
    width: 100%;
    word-wrap:normal;
    word-wrap : break-word ;
    word-break : keep-all ;
    height:20px;
}
.textReadonly{
    border-width:1px 1px 1px 1px;
	border-style:solid;
	border-color:#B7B7B7  #B7B7B7  #B7B7B7  #B7B7B7;
	font-family: "宋体";
	font-size: 9pt;
   	color:#2D65DC;
	text-align :left;
    padding:2px 2px 3px 2px;
    height:18px;
    width:100%;
    background-color:#F0F0F0;
    word-wrap : break-word ;
    word-break : keep-all ;
}
.select  {OVERFLOW: hidden ;border: 1px #B7B7B7 solid;font-family: "宋体",font-size: 12px;background: #ffffff;width: 100%}
.textarea { border: 1px #B7B7B7 solid;font-family: "宋体",font-size: 12px; Helvetica, sans-serif;width: 100%; overflow:hidden;}
.toPage  { border: 1px #B7B7B7 solid;font-family: "宋体",font-size: 9px,font-color:#808080;background: #FBFCFE; border-color: #808080 #808080 #808080 #808080; width: 40px}

.grid {}
/*button*/
.buttonGray { font-size: 12px;height:21px; color: #333333; background: #5C9FEF; padding-top: 3px; border: 1px solid; border-color: #FFFFFF #A2A2A2 #A2A2A2 #FFFFFF;cursor: hand}
.buttonOnclick {font-size: 12px; width="100%"; text-align : center; height:20px;color: #000000; background:#F5F5F5;padding-top: 2px; padding-right: 0px;  padding-left: 0px;  border-width: 0px,1px,1px,0px border-style:solid; border-color: #ffffff #ffffff #ffffff #ffffff;  cursor: hand;}
.buttonRed {font-size: 12px; width="100%"; text-align : center; height:20px;color: #000000; background:#F5F5F5;background-image : url(<%=request.getContextPath()%>/images/redText.gif) ;padding-top: 2px; padding-right: 0px;  padding-left: 0px;  border-width: 0px,1px,1px,0px border-style:solid; border-color: #ffffff #ffffff #ffffff #ffffff;  cursor: hand;}
table.tableInput tr td input[type=submit] {font-size: 12px;height:21px; color: #333333; background: #CCCCCC; padding-top: 3px; border: 1px solid; border-color: #FFFFFF #A2A2A2 #A2A2A2 #FFFFFF;cursor: hand}

/*link*/
.ALink{  font-family: "宋体", "Arial Narrow", "Times New Roman"; font-size: 9pt;}
.ALink:link	{ text-decoration:none; font-size: 9pt; color: #000000}
.ALink:alink	{ text-decoration:none; font-size: 9pt; color: #000000}
.ALink:visited	{ text-decoration:none; font-size: 9pt; color: #000000}
.ALink:active	{ text-decoration:none; font-size: 9pt; color: #000000}
.ALink:hover	{ text-decoration: none; font-size: 9pt; color: #ff0000}

/*link*/
.SLink{  font-family: "宋体", "Arial Narrow", "Times New Roman"; font-size: 9pt;}
.SLink:link	{ text-decoration:none; font-size: 9pt; color: #000000}
.SLink:alink	{ text-decoration:none; font-size: 9pt; color: #000000}
.SLink:visited	{ text-decoration:none; font-size: 9pt; color: #000000}
.SLink:active	{ text-decoration:none; font-size: 9pt; color: #00f000}
.SLink:hover	{ text-decoration: none; font-size: 9pt; color: #ff0000}

.BLink{  font-family: "宋体", "Arial Narrow", "Times New Roman"; font-size: 9pt;}
.BLink:link	{ text-decoration:none; font-size: 9pt; color: #000000}
.BLink:alink	{ text-decoration:none; font-size: 9pt; color: #000000}
.BLink:visited	{ text-decoration:none; font-size: 9pt; color: #000000}
.BLink:active	{ text-decoration:none; font-size: 9pt; color: #000000}
.BLink:hover	{ text-decoration:none; font-size: 9pt; color: #000000}

.Clicked{  font-family: "宋体", "Arial Narrow", "Times New Roman"; font-size: 9pt;color: #ff0000}
/*table标签中连接的css*/
.TableLink{  font-family: "宋体", "Arial Narrow", "Times New Roman"; font-size: 9pt;}
.TableLink:link	{ text-decoration:none; font-size: 9pt; color: #000000}
.TableLink:alink	{ text-decoration:none; font-size: 9pt; color: #000000}
.TableLink:visited	{ text-decoration:none; font-size: 9pt; color: #000000}
.TableLink:active	{ text-decoration:none; font-size: 9pt; color: #000000}
.TableLink:hover	{ text-decoration: none; font-size: 9pt; color: #00bbbb}
/*turnPage*/
.bottom
{
	background-color: #F0F0F0;
	font-weight:bold ;
	border-width:0px 0px 0px 0px;
	border-style:solid;
	border-color:#F0F0F0 #F0F0F0 #F0F0F0 #F0F0F0;
	text-align : left;
}
.count
{
	font-family: "宋体";
    font-size: 9pt;
    font-color:#808080;
	text-align : left;
	width:120;
}
.trunPage
{
	text-align : center;
	width:255;
}
.action
{
	text-align : right;
}

/* set the font color */
.red
{
	font-family: "宋体";
    font-size: 9pt;
    font-color:#FF0000!important ;
}
.divClass{

    border-width:1px 1px 1px 1px;
    border-top-color: #E0E0E0;
	border-right-color: #999999;
	border-bottom-color: #999999;
	border-left-color: #FFFFFF;

    background-color: #A7D9E4;
    font-size:9pt;
    font-family: "宋体","Arial Narrow", "Times New Roman";
}
.divTable{
    padding:3px 10px 0% 10px;
    border-width:1px 1px 1px 1px;
    background-color: #FF9900;
    font-size:9pt;
    font-family: "宋体","Arial Narrow", "Times New Roman";
}

.navigation{
	font-family: "宋体";
    font-size: 9pt;
	font-weight:bold;
	background-image: url(<%=request.getContextPath()%>/images/bg-right-top.gif);
}

.ButtonA {
	background-image: url(<%=request.getContextPath()%>/images/button/buttonA.gif);
    text-align:center;

}
.ButtonA1 {
	background-image: url(<%=request.getContextPath()%>/images/button/buttonA1.gif);
	text-align:center;
}
.pageFooter{
  border-color:#B7B7B7;color:#2D65DC;background-color: #FBFCFE;
  }
.mask {
   OVERFLOW: hidden ;
   border: 1px #B7B7B7 solid;
   font-family: "宋体",font-size: 12px; Helvetica, sans-serif;
   width: 100%;
   word-wrap:normal;
   word-wrap : break-word ;
   word-break : keep-all ;
   height:20px;
}
.check {}
 TEXTAREA,input{  }


<!--------------------------group tree css----------------------------->
.BaseTree {
	padding:4px 5px;
	FONT-SIZE: 9pt; SCROLLBAR-ARROW-COLOR: #ffffff;  SCROLLBAR-BASE-COLOR:#D6D3CE;
}
.BaseTree div.clsNode {
	height:18px;
}
.BaseTree span {
	cursor:default; padding:2px 2px; position:relative;	display:inline; height:17px; border: solid 1px white;
	top:1px;
}
.BaseTree SPAN.clsLabel {

}
.BaseTree SPAN.clsCollapse {
	border:1px solid #999999; margin:0px; margin-right:2px; position:relative;
	height:11px; width:11px; font-family:verdana; FONT-SIZE: 9px; OVERFLOW: hidden;
	padding:1px; padding-top:2px; LINE-HEIGHT: 4px; backgroundImage:url(minus.gif);
}
.BaseTree SPAN.clsExpand {
	border:1px solid #999999; margin:0px; margin-right:2px; position:relative;
	height:11px; width:11px; font-family:verdana; FONT-SIZE: 13px; OVERFLOW: hidden;
	padding:1px; padding-top:1px;  LINE-HEIGHT: 6px; backgroundImage:url(plus.gif);
}
.BaseTree SPAN.clsLeaf {
	height:11px; width:11px;
	padding: 1px 0px 0px 3px; FONT-SIZE: 9px; OVERFLOW: visible; LINE-HEIGHT: 3px;
}
.BaseTree SPAN.clsMouseOver {
	border-bottom:1px solid #999999;
}
.BaseTree SPAN.clsMouseDown {
	background-color:#eeeeee;
}
.BaseTree SPAN.clsCurrentHasFocus {
	background-color:#dedede;
}
.BaseTree SPAN.clsCurrentNoFocus {
	background-color:#F1F1F1;
}
.BaseTree A {
cursor:default; COLOR: black; TEXT-DECORATION: none;
}
.BaseTree SPAN.clsCurrentHasFocus A {

}
.BaseTree SPAN.clsUnavailable {
	height:0px;	padding:0px; top:0px; border:medium none; color:#888888;
}
.BaseTree .hide {
	DISPLAY: none;
}
.BaseTree .shown {
	DISPLAY: block; MARGIN-LEFT: 14px;
}
.BaseTree IMG {
	position:relative; top:0px; margin-left:2px; padding:0px; width:12px; height:12px;
}
.BaseTree .treelabel {
	FONT-SIZE: 70.5%; COLOR: white; FONT-FAMILY: verdana
}
.BaseTree input.clsCheckbox {
	height:14px; width:14px; margin-left:2px;
}
<!-- wgw -->
.input {
	BORDER-RIGHT: #0865BC 1px solid; BORDER-TOP: #0865BC 1px solid; FONT-SIZE: 13px; BORDER-LEFT: #0865BC 1px solid; BORDER-BOTTOM: #0865BC 1px solid; font-face: ??;
}

.input-login {
	BACKGROUND-COLOR: #D8F8FF; BORDER-RIGHT: #0865BC 1px solid; BORDER-TOP: #0865BC 1px solid; FONT-SIZE: 13px; BORDER-LEFT: #0865BC 1px solid; BORDER-BOTTOM: #0865BC 1px solid; font-face: ??;
}

.input-num {
	BORDER-RIGHT: #4784c2 1px solid; BORDER-TOP: #4784c2 1px solid; FONT-SIZE: 13px; BORDER-LEFT: #4784c2 1px solid; BORDER-BOTTOM: #4784c2 1px solid; TEXT-ALIGN: right; font-face: ??; size: 15
}
.e {
	FONT-SIZE: 12px; COLOR: #000000; LINE-HEIGHT: 21px; TEXT-DECORATION: none
}

.red {
	FONT-SIZE: 12px; COLOR: #cc0000; TEXT-DECORATION: none
}
.red-cu {
	FONT-WEIGHT: bold; FONT-SIZE: 12px; COLOR: #cc0000; TEXT-DECORATION: none
}

.white-top {
	 FONT-SIZE: 12px; COLOR: #ffffff; LINE-HEIGHT: 21px; TEXT-DECORATION: none
}


.blue {
	FONT-SIZE: 12px; COLOR: #0F4D86; LINE-HEIGHT: 22px; TEXT-DECORATION: none
}

.blue1 {
	FONT-SIZE: 12px; COLOR: #0865BC; LINE-HEIGHT: 22px; TEXT-DECORATION: none
}

.hui {
	FONT-SIZE: 12px; COLOR: #999999; LINE-HEIGHT: 22px; TEXT-DECORATION: none
}

.groupbox
{
	padding:8px;
	width:100%;
	border:1px solid #ff9900;
	font-size:12px;
}
.groupboxtitle
{
	padding:8px;
	font-size:12px;
}
.tabletemp{
width:0px;
}