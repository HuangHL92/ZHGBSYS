function CellInfo(col,row,sheet,value, dval){                           
	this.col=col;
	this.row=row;
	this.sheet=sheet;
	this.value=value;
	this.dval = dval;
}      
		
var aryCell1=new Array(); 
var aryCell=new Array(); 
function openfile(){
//	document.getElementById("DCellWeb1").openfile("E:\\workspace\\workspacem\\celldir\\test2.cll",""); 
	document.getElementById("DCellWeb1").openfile("http://localhost:8080/hzb/template/J1603/newFile.cll",""); 
}
function FillData1(){
	
	for (i=0;i<aryCell1.length;i++){  
	   if ( aryCell1[i].col == 0 ) continue;
	   if ( aryCell1[i].dval == 1 ){
		   document.getElementById("DCellWeb1").SetCellDouble(
				   parseInt(aryCell1[i].col) ,parseInt(aryCell1[i].row),aryCell1[i].sheet,aryCell1[i].value);
	   }else{
		   document.getElementById("DCellWeb1").SetCellString(
				   parseInt(aryCell1[i].col) ,parseInt(aryCell1[i].row),aryCell1[i].sheet,aryCell1[i].value);
	   }
	}
}
function FillData(){
	var rows = aryCell[aryCell.length-1].row;
	DCellWeb1.InsertRow(rows,rows,0);
	for (i=0;i<aryCell.length;i++){  
	   if ( aryCell[i].col == 0 ) continue;
	   if ( aryCell[i].dval == 1 ){
		   document.getElementById("DCellWeb1").SetCellDouble(
				   parseInt(aryCell[i].col) ,parseInt(aryCell[i].row),aryCell[i].sheet,aryCell[i].value);
	   }else{
		   document.getElementById("DCellWeb1").SetCellString(
				   parseInt(aryCell[i].col) ,parseInt(aryCell[i].row)+1,aryCell[i].sheet,aryCell[i].value);
	   }
	}
	
	DCellWeb1.redraw();
}
 
function load_init(){
	openfile();
	FillData1();
	FillData();
}