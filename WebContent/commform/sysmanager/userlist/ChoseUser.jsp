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
    //ȫ�ֱ���
    //ǰҳ����������ֵ
    var formerobj=window.dialogArguments;
    var UserListValue=formerobj.value;
    var HiddenListValue=formerobj.ids;
    var HiddenListGroup=formerobj.groupids;
    //ѡ������
    function ChoseAll(){
          for(i=0;i<tableChoice.rows.length;i++){
             if(tableChoice.cells(i,0).isChoice=='false'){
                setClickCss(tableChoice.cells(i,0),'clk');
             }
          }
    }
    //ɾ������ѡ��
    function ClearAll(){
          for(j=0;j<tableChoice.rows.length;j++){
             if(tableChoice.cells(j,0).isChoice=='true'){
                setClickCss(tableChoice.cells(j,0),'clk');
             }
          }
    }
    //ʹѡ�е���Ա��ɫͻ��
    function renderTable(){
        var arry_UserListValue=UserListValue.split(",");
        if(arry_UserListValue.length>1){
          for(i=0;i<tableChoice.rows.length;i++){
            for(j=0;j<arry_UserListValue.length;j++){
               if(tableChoice.cells(i,0).operatorname==arry_UserListValue[j]){
                 //����class
                 tableChoice.cells(i,0).className='listColorB';
                 //isChoice
                 tableChoice.cells(i,0).isChoice='true';
               }
            }
 	     }
       }
    }
    //tr��ť�¼��������ǵ�����˫����ʱ�򷵻�ֵ
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
	  //���㷵��ֵ
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
	         //id����
	         var operatornameno=-1;
	         for(i=0;i<arry_UserListValue.length;i++){
	            if(operatorname==arry_UserListValue[i]){
	              operatornameno=i;
	              break;
	            }
	         }
	         if(operatornameno>-1){
	              //��ȫ�ֱ������¸�ֵ
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
    //��Ⱦ���û���
    renderTable();
</script>
