<!--  盘片的读取与导出 author:周兆巍-->

<script>

	var expArray,expArrayYk;
	function getGridColumnHeader(gridName){
		var grid=Ext.getCmp(gridName);
		var column=grid.colModel;
		var counts=column.getColumnCount();
	    expArray = new Array();
	    var k=0;
		var gridStore=Ext.getCmp(gridName).store;
		for(var i=0;i<gridStore.data.length+1;i++){
			var colArray=new Array();
			for(var j=1;j<counts;j++){
				if(i==0&&column.isHidden(j)!=true){
	                colArray[k]=column.getColumnHeader(j);
					expArray[i]=colArray;
					k++;
				}					
				if(i>0&&column.isHidden(j)!=true){
					var flag=false;
	                var dataIndex=column.getDataIndex(j);
					try{
					  eval(dataIndex+'_select');
					}catch(e){
					    flag=true;
					}          
	                if(flag){
	                   colArray[k]=gridStore.data.itemAt(i-1).get(dataIndex);
					   expArray[i]=colArray;               	
	                }else{ 	
	                   var sel_data  = eval(dataIndex+"_select");
	                   var value=gridStore.data.itemAt(i-1).get(dataIndex);
	                   for(var m=0;m<sel_data.length;m++)
	                   {
		                   	if(sel_data[m][0] == value)
		                   	{
		                   		value = sel_data[m][1];
		                   		break;
		                   	}
	                   }                  
	                   colArray[k]=value;
					   expArray[i]=colArray;                	
	                }
					k++;
				}
				if(j==counts-1){
					k=0;
				}			
			} 		
		}
	
	}
	
	function getGridColumnHeaderForExpYk(gridName){
		var grid=Ext.getCmp(gridName);
		var column=grid.colModel;
		var counts=column.getColumnCount();
	    expArrayYk = new Array();
	    var k=0;
		var gridStore=Ext.getCmp(gridName).store;
		for(var i=0;i<gridStore.data.length+1;i++){
			var colArray=new Array();
			for(var j=1;j<counts;j++){
				if(i==0&&column.isHidden(j)!=true){
	                colArray[k]=column.getColumnHeader(j);
					expArray[i]=colArray;
					k++;
				}
				if(i>0&&column.isHidden(j)!=true){
					var flag=false;
	                var dataIndex=column.getDataIndex(j); 
	                if(dataIndex=="aae135"||dataIndex=="aac156"){
	                   colArray[k]=value;
					   expArray[i]=colArray;                	
					   k++;
	                }                     
				}									
				if(j==counts-1){
					k=0;
				}			
			} 		
		}
	}

	function expExcel(gridName){
	     getGridColumnHeader(gridName);
	     var charArray=["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"];
		 var xls = new ActiveXObject ( "Excel.Application" );
		 xls.visible = true;
		 var newBook = xls.Workbooks.Add;
		 newBook.Worksheets.Add;
		 newBook.Worksheets(1).Activate;
		 for(var i=0;i<expArray.length;i++){
		     for(var j=0;j<expArray[0].length;j++){
		       if(expArray[i][j].length>13){
		          newBook.Worksheets(1).Columns(charArray[j]).columnwidth=expArray[i][j].length;  // 设置列宽度为18px
		       }else{
		          newBook.Worksheets(1).Columns(charArray[j]).columnwidth=13;  // 设置列宽度为13px
		       }  
		       newBook.Worksheets(1).Cells(i+1,j+1).value=expArray[i][j];  
		       newBook.Worksheets(1).Columns(charArray[j]).WrapText = true;
		       newBook.Worksheets(1).Columns(charArray[j]).NumberFormatLocal = "@";
		     }
	         
		 }
	}


    ////////////////时间格式的控制////////////////////////
    function formatJsDate(date){ 	
        var date=new Date(""+date);
        var time= date.format('Y-m-d'); 
        return time;  	
    }

    //////////////////////////导出TXT//////////////////////////////////
    var cardRule=[20,5,18,2,8,60,18,55,20,21];
    function expText(gridName){
		var grid=Ext.getCmp(gridName);
		var column=grid.colModel;	
		var counts=column.getColumnCount();      
        var date = new Date();  
        var time2=date.format('Y-m-d'); 
        time2=time2.substr(0,4)+""+time2.substr(5,2)+""+time2.substr(8,2);
	    var   ForWriting   =   2;   	      
	    var   objFSO   =   new   ActiveXObject("Scripting.FileSystemObject");  
	    var gridStore=Ext.getCmp(gridName).store;
        var   eac109 = gridStore.data.itemAt(0).get("eac109");
        var name="k"+time2+""+eac109;
		var   strFile = "d:\\"+name+".yk";	  
		objFSO.CreateTextFile(strFile, true); 
		var   objStream   =   objFSO.OpenTextFile(strFile,ForWriting,true,false); 
		var value="";  
	    for(var i=1;i<gridStore.data.length+1;i++){
            var k=0;
            var check=gridStore.data.itemAt(i-1).get("check");
            if(check==true){      
				for(var j=1;j<counts;j++){
				    var dataIndex=column.getDataIndex(j);
				    if(dataIndex=="aae135"||dataIndex=="aac156"){
				        var value2 = gridStore.data.itemAt(i-1).get(dataIndex);
				        if(k<1){
	                     value=value+value2+",";
	                     k++;
	                    }else{
	                     value=value+value2;
	                    }
				    }
				    if(j==counts-1){
				        value=value+"\r\n";
				        continue;
				    }
				}
			} 
		}	
		objStream.Write(value);	
		objStream.Close();
		f2 = objFSO.GetFile(strFile);
	    f2.Move ("d:\\card\\"+name+".yk"); 

	    name="k"+time2+""+eac109;
	    strFile = "d:\\"+name+".xp";
        objFSO.CreateTextFile(strFile, true);
        objStream  =  objFSO.OpenTextFile(strFile,ForWriting,true,false); 
        var xpValue=""; 	    
	    for(var i=1;i<gridStore.data.length+1;i++){
	        var check=gridStore.data.itemAt(i-1).get("check");
            if(check==true){   
				var value=""+gridStore.data.itemAt(i-1).get("eac051");
				for(var k=0;k<cardRule.length;k++){
				    var length=0;
	                if(k==0){
	                    value=gridStore.data.itemAt(i-1).get("eac051");
	                    length=value.length;
	                }
	                if(k==1){
	                    value=gridStore.data.itemAt(i-1).get("aac003");
	                    length=value.length; 
	                    if(length==2){
	                       value=value+" ";
	                    }
	                    if(length==4){
	                       length=5;
	                    }              
	                }
	                if(k==2){
	                    value=gridStore.data.itemAt(i-1).get("aae135");
	                    length=value.length; 
	                }
	                if(k==3){
	                    value=gridStore.data.itemAt(i-1).get("aac004");
	                    length=value.length; 
	                }                
	                if(k==4){
	                    value=gridStore.data.itemAt(i-1).get("aac006");
	                    length=value.length; 
	                }
	                if(k==6){
	                    value=gridStore.data.itemAt(i-1).get("aae135");
	                    length=value.length; 
	                }
	                if(k==8){
	                    value="000000000";
	                    length=value.length;                
	                }                
	                if(k==9){
	                    value="000000000000000000000";
	                    length=value.length;                
	                }
	
	                if(value==""){
	                    length=0;
	                }
			        value=myfillRightForExp(value," ",cardRule[k]-length);
			        xpValue=xpValue+value;
			        if(k==cardRule.length-1){
				        xpValue=xpValue+"\r\n";
				        continue;
				    }
				}
			}

		}	
    	objStream.Write(xpValue);			  
		objStream.Close();
        f3 = objFSO.GetFile(strFile);
        f3.Move ("d:\\card\\"+name+".xp"); 	    
	    //odin.alert("exp success!!!!"); 
     }
     
     function myfillRightForExp(value,para,counts){
         for(var i=0;i<counts;i++){
             value=value+para;
         }
         return value;
     }
</script>