<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<style type="text/css">
/**/
.listColorA {
	background-color: #FBFCFE;
	font-family: "????";
	font-size: 9pt;
	font-color: #000000;
	height:20px;

	white-space : pre;

}
.listColorB {
    /*background-color: #E6EDFA;*/
	background-color: #EEEEEE;
	font-family: "????";
	font-size: 9pt;
	font-color: #000000;
	height:20px;

	white-space : pre;
}
</style>
<script>
    //全局变量
    //前页所传过来的值
    var formerobj=window.dialogArguments;
    var UserListValue=formerobj.value;
    var HiddenListValue=formerobj.ids;
    var HiddenListGroup=formerobj.groupids;
    //选中所有
    function ChoseAll(){
          for(i=0;i<tableChoice.rows.length;i++){
             if(tableChoice.cells(i,0).isChoice=='false'){
                setClickCss(tableChoice.cells(i,0),'clk');
             }
          }
    }
    //删除所有选中
    function ClearAll(){
          for(j=0;j<tableChoice.rows.length;j++){
             if(tableChoice.cells(j,0).isChoice=='true'){
                setClickCss(tableChoice.cells(j,0),'clk');
             }
          }
    }
    //使选中的人员颜色突出
    function renderTable(){
        var arry_UserListValue=UserListValue.split(",");
        if(arry_UserListValue.length>1){
          for(i=0;i<tableChoice.rows.length;i++){
            for(j=0;j<arry_UserListValue.length;j++){
               if(tableChoice.cells(i,0).operatorname==arry_UserListValue[j]){
                 //设置class
                 tableChoice.cells(i,0).className='listColorB';
                 //isChoice
                 tableChoice.cells(i,0).isChoice='true';
               }
            }
 	     }
       }
    }
    //tr按钮事件，并当是单击或双击的时候返回值
    function setClickCss(obj,action){
		  if(action=='clk'){
		       if(obj.isChoice=='false'){
    	          obj.className='listColorB';
				  obj.isChoice='true';
			      cal_backValue('add',obj.operatorname,obj.id,obj.groupid);
			   }else{
        	      obj.className='listColorA';
				  obj.isChoice='false';
			      cal_backValue('remove',obj.operatorname,obj.id,obj.groupid);
			   }
			   formerobj.value=UserListValue;
			   formerobj.ids=HiddenListValue;
			   formerobj.groupids=HiddenListGroup;
               window.returnValue = formerobj;
		  }
		  if(action=='mosover'){
        	      obj.className='listColorB';
		  }
		  if(action=='mosout'){
		      if(obj.isChoice=='false'){
       	          obj.className='listColorA';
          	  }
		  }
	  //计算返回值
	  function cal_backValue(action,operatorname,id,groupid){
          if(action=='add'){
     	     var arry_UserListValue=UserListValue.split(",");
     	     if(arry_UserListValue.length>1){
               UserListValue=UserListValue+operatorname+",";
               HiddenListValue=HiddenListValue+id+",";
               HiddenListGroup=HiddenListGroup+groupid+",";
     	     }else{
               UserListValue=""+operatorname+",";
               HiddenListValue=""+id+",";
               HiddenListGroup=""+groupid+",";
     	     }
 	      }
 	      if(action=='remove'){
     	     var arry_UserListValue=UserListValue.split(",");
	         var arry_HiddenListValue=HiddenListValue.split(",");
	         var arry_HiddenListGroup=HiddenListGroup.split(",");
	         //id次序
	         var operatornameno=-1;
	         for(i=0;i<arry_UserListValue.length;i++){
	            if(operatorname==arry_UserListValue[i]){
	              operatornameno=i;
	              break;
	            }
	         }
	         if(operatornameno>-1){
	              //给全局变量重新赋值
                  UserListValue="";
                  HiddenListValue="";
                  HiddenListGroup="";
	            for(i=0;i<arry_UserListValue.length;i++){
	              if(i==operatornameno){
                    continue;
	              }else{
	                if(null==arry_UserListValue[i]||""==arry_UserListValue[i]){
                      continue;
	                }
                    UserListValue=UserListValue+arry_UserListValue[i]+",";
                    HiddenListValue=HiddenListValue+arry_HiddenListValue[i]+",";
                    HiddenListGroup=HiddenListGroup+arry_HiddenListGroup[i]+",";
                  }
                }
             }
 	      }


	  }
	}
</script>
<sicp3:base/>
<sicp3:specialview mode="query"/>
<script>
    //渲染表（用户）
    renderTable();
</script>
