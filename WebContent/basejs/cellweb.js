var cell = document.getElementById("cellweb1");
var Startcol ;
var Startrow ;
var Endcol ;
var Endrow ;
//打开远程文档
function openBDWD(){
	cell.OpenFile("","");
}
/*//打印预览
function dayinyulan(){
	setAuth(cell);
	cell.PrintPreview(true,cell.GetCurSheet());
}
//打印
function dayin(){
	setAuth(cell);
	cell.PrintSheet(true,cell.GetCurSheet());
}*/
//撤销
function chexiao(){
	cell.EnableUndo(1);
	cell.Undo();
}

//重做
function chongzuo(){
	cell.EnableUndo(1);
	cell.Redo();
}

//剪切
function jianqie(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	cell.CutRange(Startcol, Startrow, Endcol, Endrow);
}
//复制
function fuzhi(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	cell.CopyRange(Startcol, Startrow, Endcol, Endrow);
}
//粘贴
function zhantie(){
	cell.Paste(cell.GetCurrentCol, cell.GetCurrentRow, 0, false, false);
}
//查找
function chazhao(){
	cell.FindDialog(0);
}
//不滚动区域
function IsFreezed(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	cell.GetFixedCol(Startcol,Endcol);
	cell.GetFixedRow(Startrow,Endrow);
	if((Endcol>0&&Startcol>0)||(Endrow>0&&Startrow>0)){
		return true;
	}else{
		return false;
	}
}
function BGDquyu(){
	if(IsFreezed()){
		cell.SetFixedCol(0,-1);
		cell.SetFixedRow(0,-1);
	}else{
		cell.SetFixedCol(1,cell.GetCurrentCol - 1);
		cell.SetFixedRow(1,cell.GetCurrentRow - 1);
	}
}
//格式刷
function geshishua(){
	cell.FormatPainter();
}
//升序排序
function shengxupaixu(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	if(Startrow==Endrow&&Startcol!=Endcol){
		cell.SortRow(1,Startrow,Startcol,Startrow,Endcol,Endrow);
	}else if(Startcol==Endcol&&Startrow!=Endrow){
		cell.SortCol(1,Startcol,Startcol,Startrow,Endcol,Endrow);
	}else if(Startcol!=Endcol&&Startrow!=Endrow){
		quyupaixu();
	}
}
//降序排序
function jiangxupaixu(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	if(Startrow==Endrow&&Startcol!=Endcol){
		cell.SortRow(0,Startrow,Startcol,Startrow,Endcol,Endrow);
	}else if(Startcol==Endcol&&Startrow!=Endrow){
		cell.SortCol(0,Startcol,Startcol,Startrow,Endcol,Endrow);
	}else if(Startcol!=Endcol&&Startrow!=Endrow){
		quyupaixu();
	}
}
//区域排序
function quyupaixu(){
	cell.RangeSortDlg();
}
//输入公式
function shurugongshi(){
	cell.FormulaWizard(cell.GetCurrentCol,cell.GetCurrentRow);
}
//填充公式序列
function GSxulie(){
	cell.FormulaFillSerial();
}
//水平求和
function shuipingQH(){
	var formula;
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	var i = Startrow;
	if(Startcol == Endcol){
		alert("请选择一个矩形区域！");
	}else{
		for(i ; i <= Endrow ; i++ ){
			formula = 'sum(' + cell.CellToLabel(Startcol, i) + ':' + cell.CellToLabel(Endcol - 1, i) + ')';
			cell.SetFormula(Endcol, i, cell.GetCurSheet, formula);
		}
	}
	cell.Invalidate();
}
//垂直求和
function chuizhiQH(){
	var formula;
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	var i = Startcol;
	if(Startrow == Endrow){
		alert("请选择一个矩形区域！");
	}else{
		for(i ; i <= Endcol ; i++ ){
			formula = 'sum(' + cell.CellToLabel(i, Startrow) + ':' + cell.CellToLabel(i, Endrow - 1) + ')';
			cell.SetFormula(i, Endrow, cell.GetCurSheet, formula);
		}
	}
	cell.Invalidate();
}
//双向求和
function SXQH(){
	var formula;
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	var i = Startrow;
	var j = Startcol;
	if(Startrow == Endrow||Startcol == Endcol){
		alert("请选择一个矩形区域！");
	}else{
		for(i ; i <= Endrow - 1 ; i++ ){
			formula = 'sum(' + cell.CellToLabel(Startcol, i) + ':' + cell.CellToLabel(Endcol - 1, i) + ')';
			cell.SetFormula(Endcol, i, cell.GetCurSheet, formula);
		}
		for(i ; i <= Endcol - 1; i++ ){
			formula = 'sum(' + cell.CellToLabel(i, Startrow) + ':' + cell.CellToLabel(i, Endrow - 1) + ')';
			cell.SetFormula(i, Endrow, cell.GetCurSheet, formula);
		}
		formula = 'sum(' + cell.CellToLabel(Startcol, Startrow) + ':' + cell.CellToLabel(Endcol - 1, Endrow - 1) + ')';
		cell.SetFormula(Endcol, Endrow, cell.GetCurSheet, formula);
	}
	cell.Invalidate();
}
//图标向导
function tubiaoXD(){
	cell.WzdChartDlg();
} 
//插入图片
function charutupian(){
	cell.SetCellImageDlg();
	cell.Invalidate();
}
//超级链接
function chaojilianjie(){
	cell.HyperlinkDlg();
}
//条形码向导
function TXMxiangdao(){
	cell.WzdBarCodeDlg();
}
//显示比例
function changeViewScale(value){
	var zoom = value/100.0;
	cell.SetScreenScale(cell.GetCurSheet,zoom);
}
//显示、隐藏背景网络线
function XSYCBJWLX(){
	cell.DrawLineDlg();
}
//垂直分割线
function chuizhiFGX(){
	if(cell.IsColPageBreak(cell.GetCurrentCol())){
		cell.SetColPageBreak(cell. GetCurrentCol(), 0);
	}else{
		cell.SetColPageBreak(cell. GetCurrentCol(), 1);
	}
}
//水平分割线
function shuipingFGX(){
	if(cell.IsRowPageBreak(cell.GetCurrentRow())){
		cell.SetRowPageBreak(cell.GetCurrentRow(),0);
	}else{
		cell.SetRowPageBreak(cell.GetCurrentRow(),1);
	}
}
//显示、隐藏分割线
function xsORycFGX(){
	cell.ShowPageBreak(!cell.GetPageBreakState());
	cell.Invalidate();
}
//字体设置
function changeFontName(value){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	var CurSheet = cell.GetCurSheet();
	var lFontName = cell.FindFontIndex(value,1);
	for(var i = Startcol; i <= Endcol; i++){
		for(var j = Startrow; j <= Endrow; j++){
			cell.SetCellFont(i,j,CurSheet,lFontName);
		}
		}
	cell.Invalidate();
}
//字号设置
function changeFontSize(value){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	var CurSheet = cell.GetCurSheet();
	var lFontSize = value;
	for(var i = Startcol; i <= Endcol; i++){
		for(var j = Startrow; j <= Endrow; j++){
			cell.SetCellFontSize(i,j,CurSheet,lFontSize);
		}
	}
	cell.Invalidate();
}
//加粗
function jiacu(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	var CurCol = cell.GetCurrentCol();
	var CurRow = cell.GetCurrentRow();
	var CurSheet = cell.GetCurSheet();
	var FontStyle = cell.GetCellFontStyle(CurCol,CurRow,CurSheet);
	if(FontStyle == 2){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellFontStyle(i,j,CurSheet,0);
			}
		}
	}if(FontStyle == 0){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellFontStyle(i,j,CurSheet,2);
			}
		}
	}if(FontStyle == 4){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellFontStyle(i,j,CurSheet,6);
			}
		}
	}if(FontStyle == 8){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellFontStyle(i,j,CurSheet,10);
			}
		}
	}if(FontStyle == 6){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellFontStyle(i,j,CurSheet,4);
			}
		}
	}if(FontStyle == 10){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellFontStyle(i,j,CurSheet,8);
			}
		}
	}if(FontStyle == 12){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellFontStyle(i,j,CurSheet,14);
			}
		}
	}if(FontStyle == 14 ){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellFontStyle(i,j,CurSheet,12);
			}
		}
	}
	cell.Invalidate();
}
//斜体
function xieti(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	var CurCol = cell.GetCurrentCol();
	var CurRow = cell.GetCurrentRow();
	var CurSheet = cell.GetCurSheet();
	var FontStyle = cell.GetCellFontStyle(CurCol,CurRow,CurSheet);
	if(FontStyle == 0){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellFontStyle(i,j,CurSheet,4);
			}
		}
	}if(FontStyle == 2 ){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellFontStyle(i,j,CurSheet,6);
			}
		}
	}if(FontStyle == 4 ){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellFontStyle(i,j,CurSheet,0);
			}
		}
	}if(FontStyle == 8 ){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellFontStyle(i,j,CurSheet,12);
			}
		}
	}if(FontStyle == 6 ){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellFontStyle(i,j,CurSheet,2);
			}
		}
	}if(FontStyle == 10 ){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellFontStyle(i,j,CurSheet,14);
			}
		}
	}if(FontStyle == 12 ){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellFontStyle(i,j,CurSheet,8);
			}
		}
	}if(FontStyle == 14 ){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellFontStyle(i,j,CurSheet,10);
			}
		}
	}
	cell.Invalidate();
}
//下划线
function xiahuaxian(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	var CurCol = cell.GetCurrentCol();
	var CurRow = cell.GetCurrentRow();
	var CurSheet = cell.GetCurSheet();
	var FontStyle = cell.GetCellFontStyle(CurCol,CurRow,CurSheet);
	if(FontStyle == 0){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellFontStyle(i,j,CurSheet,8);
			}
		}
	}if(FontStyle == 2 ){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellFontStyle(i,j,CurSheet,10);
			}
		}
	}if(FontStyle == 4 ){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellFontStyle(i,j,CurSheet,12);
			}
		}
	}if(FontStyle == 8 ){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellFontStyle(i,j,CurSheet,0);
			}
		}
	}if(FontStyle == 6 ){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellFontStyle(i,j,CurSheet,14);
			}
		}
	}if(FontStyle == 10 ){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellFontStyle(i,j,CurSheet,2);
			}
		}
	}if(FontStyle == 12 ){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellFontStyle(i,j,CurSheet,4);
			}
		}
	}if(FontStyle == 14 ){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellFontStyle(i,j,CurSheet,6);
			}
		}
	}
	cell.Invalidate();
}
//背景色
/*function beijingse(){
//	CommonDialog1.Flags = &H2 || &H8;
	var CommonDialog1 = document.getElementById('CommonDialog1');
	CommonDialog1.ShowColor();
}*/
//前景色

//自动折色
function zidongzhehang(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	var CurCol = cell.GetCurrentCol();
	var CurRow = cell.GetCurrentRow();
	var CurSheet = cell.GetCurSheet();
	if(cell.GetCellTextStyle(CurCol, CurRow, CurSheet) == 2){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellTextStyle(i,j,CurSheet,0);
			}
		}
	}else{
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellTextStyle(i,j,CurSheet,2);
			}
		}
	}
	cell.Invalidate();
}
//居左对齐
function juzuoduiqi(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	var CurSheet = cell.GetCurSheet();
	for(var i = Startcol; i <= Endcol;i++){
		for(var j = Startrow; j <= Endrow; j++){
			var vAlign = cell.GetCellAlign(i,j,CurSheet);
			if(vAlign == 1){
				cell.SetCellAlign(i,j,CurSheet,0);
			}
			if(vAlign == 0||vAlign == 2||vAlign == 4||vAlign == 64||vAlign == 128){
				cell.SetCellAlign(i,j,CurSheet,1);
			}
			if(vAlign == 8||vAlign == 16||vAlign == 32||vAlign == 64||vAlign == 128){
				cell.SetCellAlign(i,j,CurSheet,vAlign+1);
			}
			if(vAlign == 9||vAlign == 17||vAlign == 33||vAlign == 65||vAlign == 129||vAlign == 10||vAlign == 18||vAlign == 34||vAlign == 66||vAlign == 130){
				cell.SetCellAlign(i,j,CurSheet,vAlign-1);
			}
			if(vAlign == 12||vAlign == 20||vAlign == 36||vAlign == 68||vAlign == 132){
				cell.SetCellAlign(i,j,CurSheet,vAlign-3);
			}
		}
	}
	cell.Invalidate();
}
//居中对齐
function JZDQ(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	var CurSheet = cell.GetCurSheet();
	for(var i = Startcol; i <= Endcol;i++){
		for(var j = Startrow; j <= Endrow; j++){
			var vAlign = cell.GetCellAlign(i,j,CurSheet);
			if(vAlign == 4){
				cell.SetCellAlign(i,j,CurSheet,0);
			}
			if(vAlign == 0||vAlign == 1||vAlign == 2||vAlign == 64||vAlign == 128){
				cell.SetCellAlign(i,j,CurSheet,4);
			}
			if(vAlign == 8||vAlign == 16||vAlign == 32||vAlign == 64||vAlign == 128){
				cell.SetCellAlign(i,j,CurSheet,vAlign+4);
			}
			if(vAlign == 12||vAlign == 20||vAlign == 36||vAlign == 68||vAlign == 132){
				cell.SetCellAlign(i,j,CurSheet,vAlign-4);
			}
			if(vAlign == 9||vAlign == 17||vAlign == 33||vAlign == 65||vAlign == 129){
				cell.SetCellAlign(i,j,CurSheet,vAlign+3);
			}
			if(vAlign == 10||vAlign == 18||vAlign == 34||vAlign == 66||vAlign == 130){
				cell.SetCellAlign(i,j,CurSheet,vAlign+2);
			}
		}
	}
	cell.Invalidate();
}
//居右对齐
function JYDQ(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	var CurSheet = cell.GetCurSheet();
	for(var i = Startcol; i <= Endcol;i++){
		for(var j = Startrow; j <= Endrow; j++){
			var vAlign = cell.GetCellAlign(i,j,CurSheet);
			if(vAlign == 2){
				cell.SetCellAlign(i,j,CurSheet,0);
			}
			if(vAlign == 0||vAlign == 1||vAlign == 4||vAlign == 64||vAlign == 128){
				cell.SetCellAlign(i,j,CurSheet,2);
			}
			if(vAlign == 8||vAlign == 16||vAlign == 32||vAlign == 64||vAlign == 128){
				cell.SetCellAlign(i,j,CurSheet,vAlign+2);
			}
			if(vAlign == 10||vAlign == 18||vAlign == 34||vAlign == 66||vAlign == 130||vAlign == 12||vAlign == 20||vAlign == 36||vAlign == 68||vAlign == 132){
				cell.SetCellAlign(i,j,CurSheet,vAlign-2);
			}
			if(vAlign == 9||vAlign == 17||vAlign == 33||vAlign == 65||vAlign == 129){
				cell.SetCellAlign(i,j,CurSheet,vAlign+1);
			}
		}
	}
	cell.Invalidate();
}
//居上对其
function JSDQ(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	var CurSheet = cell.GetCurSheet();
	for(var i = Startcol; i <= Endcol;i++){
		for(var j = Startrow; j <= Endrow; j++){
			var vAlign = cell.GetCellAlign(i,j,CurSheet);
			if(vAlign == 8){
				cell.SetCellAlign(i,j,CurSheet,0);
			}
			if(vAlign == 0||vAlign == 16||vAlign == 32||vAlign == 64||vAlign == 128){
				cell.SetCellAlign(i,j,CurSheet,8);
			}
			if(vAlign == 1||vAlign == 2||vAlign == 4){
				cell.SetCellAlign(i,j,CurSheet,vAlign+8);
			}
			if(vAlign == 17||vAlign == 18||vAlign == 20){
				cell.SetCellAlign(i,j,CurSheet,vAlign-8);
			}
			if(vAlign == 33||vAlign == 34||vAlign == 36){
				cell.SetCellAlign(i,j,CurSheet,vAlign-24);
			}
		}
	}
	cell.Invalidate();
}
//垂直居中
function CZJZ(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	var CurSheet = cell.GetCurSheet();
	for(var i = Startcol; i <= Endcol;i++){
		for(var j = Startrow; j <= Endrow; j++){
			var vAlign = cell.GetCellAlign(i,j,CurSheet);
			if(vAlign == 32){
				cell.SetCellAlign(i,j,CurSheet,0);
			}
			if(vAlign == 0||vAlign == 16||vAlign == 8||vAlign == 64||vAlign == 128){
				cell.SetCellAlign(i,j,CurSheet,32);
			}
			if(vAlign == 1||vAlign == 2||vAlign == 4){
				cell.SetCellAlign(i,j,CurSheet,vAlign+32);
			}
			if(vAlign == 17||vAlign == 18||vAlign == 20){
				cell.SetCellAlign(i,j,CurSheet,vAlign+16);
			}
			if(vAlign == 9||vAlign == 10||vAlign == 12){
				cell.SetCellAlign(i,j,CurSheet,vAlign+24);
			}
		}
	}
	cell.Invalidate();
}
//居下对其
function JXDQ(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	var CurSheet = cell.GetCurSheet();
	for(var i = Startcol; i <= Endcol;i++){
		for(var j = Startrow; j <= Endrow; j++){
			var vAlign = cell.GetCellAlign(i,j,CurSheet);
			if(vAlign == 16){
				cell.SetCellAlign(i,j,CurSheet,0);
			}
			if(vAlign == 0||vAlign == 8||vAlign == 32||vAlign == 64||vAlign == 128){
				cell.SetCellAlign(i,j,CurSheet,16);
			}
			if(vAlign == 1||vAlign == 2||vAlign == 4){
				cell.SetCellAlign(i,j,CurSheet,vAlign+16);
			}
			if(vAlign == 9||vAlign == 10||vAlign == 12){
				cell.SetCellAlign(i,j,CurSheet,vAlign+8);
			}
			if(vAlign == 33||vAlign == 34||vAlign == 36){
				cell.SetCellAlign(i,j,CurSheet,vAlign-16);
			}
		}
	}
	cell.Invalidate();
}
//画边框线
function HBKX(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	var CurSheet = cell.GetCurSheet();
	var value = document.getElementById('TypeSelect').value;
	cell.DrawGridLine(Startcol,Startrow,Endcol,Endrow,0,value,-1);
}
//抹边框线
function MBKX(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	var CurSheet = cell.GetCurSheet();
	cell.ClearGridLine(Startcol,Startrow,Endcol,Endrow,0);
}
//货币符号
function HBFH(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	var CurSheet = cell.GetCurSheet();
	for(var i = Startcol; i <= Endcol;i++){
		for(var j = Startrow; j <= Endrow; j++){
			cell.SetCellCurrency(i,j,CurSheet,2);
		}
	}
	cell.Invalidate();
}
//百分号
function baifenhao(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	var CurCol = cell.GetCurrentCol();
	var CurRow = cell.GetCurrentRow();
	var CurSheet = cell.GetCurSheet();
	if(cell.GetCellNumType(CurCol,CurRow,CurSheet) == 5){
		for(var i = Startcol; i <= Endcol;i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellNumType(i,j,CurSheet,0);
			}
		}
	}else{
		for(var i = Startcol; i <= Endcol;i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellNumType(i,j,CurSheet,5);
			}
		}
	}
	cell.Invalidate();
}
//千分位
function qianfenwei(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	var CurCol = cell.GetCurrentCol();
	var CurRow = cell.GetCurrentRow();
	var CurSheet = cell.GetCurSheet();
	if(cell.GetCellSeparator(CurCol,CurRow,CurSheet) == 2){
		for(var i = Startcol; i <= Endcol;i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellNumType(i,j,CurSheet,1);
				cell.SetCellSeparator(i,j,CurSheet,1);
			}
		}
	}else{
		for(var i = Startcol; i <= Endcol;i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellNumType(i,j,CurSheet,1);
				cell.SetCellSeparator(i,j,CurSheet,2);
			}
		}
	}
	cell.Invalidate();
}
//插入列
function charuL(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	cell.InsertCol(Startcol,Endcol - Startcol + 1,cell.GetCurSheet());
}
//插入行
function charuH(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	cell.InsertRow(Startrow,Endrow - Startrow + 1,cell.GetCurSheet());
}
//追加列
function zhuijiaL(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	cell.InsertCol(cell.GetCols(cell.GetCurSheet()),Endcol - Startcol + 1,cell.GetCurSheet());
}
//追加行
function zhuijiaH(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	cell.InsertRow(cell.GetRows(cell.GetCurSheet()),Endrow - Startrow + 1,cell.GetCurSheet());
}
//删除列
function shanchuL(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	cell.DeleteCol(Startcol,Endcol - Startcol + 1,cell.GetCurSheet());
}
//删除行
function shanchuH(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	cell.DeleteRow(Startrow,Endrow - Startrow + 1,cell.GetCurSheet());
}
//表格尺寸
function biaoyeCC(){
	cell.SetCols(cell.GetCurrentCol + 1,cell.GetCurSheet());
	cell.SetRows(cell.GetCurrentRow() + 1,cell.GetCurSheet());
}
//组合单元格
function ZHDYG(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	cell.MergeCells(Startcol,Startrow,Endcol,Endrow);
}
//取消组合单元格
function QXDYGZH(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	var CurSheet = cell.GetCurSheet();
	for(var col = Startcol; col <= Endcol; col++){
		for(var row = Startrow; row <= Endrow; row++){
			var startcol = cell.GetMergeRangeJ(col,row,CurSheet,0);
			var startrow = cell.GetMergeRangeJ(col,row,CurSheet,1);
			var endcol = cell.GetMergeRangeJ(col,row,CurSheet,2);
			var endrow = cell.GetMergeRangeJ(col,row,CurSheet,3);
			cell.UnmergeCells(startcol,startrow,endcol,endrow);
		}
	}
}
//行组合
function hangzuhe(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	for(var i = Startrow; i <= Endrow; i++){
		cell.MergeCells(Startcol,i,Endcol,i);
	}
}
//列组合
function liezuhe(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	for(var i = Startcol; i <= Endcol; i++){
		cell.MergeCells(i,Startrow,i,Endrow);
	}
}
//重算全表
function CSQB(){
	cell.CalculateAll();
	alert('计算完毕！');
}
//设置汇总公式
function SZHZGS(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	if(cell.GetTotalSheets() > 1){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				if(cell.GetCurSheet() == 0){
					cell.SetFormula(i,j,cell.GetCurSheet,"Sum3D( CurCell() , loopsheet() >=" + CStr(2) + " AND loopsheet() <=" + CStr(cell.GetTotalSheets()) + ")");
				}else{
					cell.SetFormula(i,j,cell.GetCurSheet,"Sum3D( CurCell() , loopsheet() >= 1 AND loopsheet() <= " + CStr(cell.GetCurSheet()) + ")")
				}
			}
		}
	}else{
		alert('当前单元格只有一页，不能进行汇总!');
	}
	cell.Invalidate();
}
//单元格只读
function DYGZD(){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	var CurCol = cell.GetCurrentCol();
	var CurRow = cell.GetCurrentRow();
	var CurSheet = cell.GetCurSheet();
	if(cell.GetCellInput(CurCol,CurRow,CurSheet) == 5){
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellInput(i,j,cell.GetCurSheet(),1);
			}
		}
	}else{
		for(var i = Startcol; i <= Endcol; i++){
			for(var j = Startrow; j <= Endrow; j++){
				cell.SetCellInput(i,j,cell.GetCurSheet(),5);
			}
		}
	}
	cell.Invalidate();
}
//填充方式
function changeFillType(value){
	cell.Fill(value);
}
//获取当前日期
function getdate(){
	var date = new Date();
	var localOffset = date.getTimezoneOffset() * 60000; //系统时区偏移 1900/1/1 到 1970/1/1 的 25569 天    
	return (parseFloat(date.getTime()) - localOffset) / 24 / 3600 / 1000 + 25569;
}
//日期类型
function changeDateType(value){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	var CurSheet = cell.GetCurSheet();
	for(var i = Startcol; i <= Endcol; i++){
		for(var j = Startrow; j <= Endrow; j++){
			cell.SetCellDouble(i,j,CurSheet,getdate());
			cell.SetCellNumType(i,j,CurSheet,3);
			cell.SetCellDateStyle(i,j,CurSheet,value);
		}
	}
}
//时间类型
function changeTimeType(value){
	Startcol = cell.GetSelectRangeJ(0);
	Startrow = cell.GetSelectRangeJ(1);
	Endcol = cell.GetSelectRangeJ(2);
	Endrow = cell.GetSelectRangeJ(3);
	var CurSheet = cell.GetCurSheet();
	for(var i = Startcol; i <= Endcol; i++){
		for(var j = Startrow; j <= Endrow; j++){
			cell.SetCellDouble(i,j,CurSheet,getdate());
			cell.SetCellNumType(i,j,CurSheet,4);
			cell.SetCellTimeStyle(i,j,CurSheet,value);
		}
	}
}

//设置行高
function SZHG(){
	cell.RowHeightDlg();
}
//设置列宽
function SZLK(){
	cell.ColWidthDlg();
}