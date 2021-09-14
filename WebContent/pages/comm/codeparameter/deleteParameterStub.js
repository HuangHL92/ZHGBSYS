//ODA Generated JavaScript Stub for CodeParameterVS.java.deleteParameter
//Insert following javascript code to invoke this method:
//<script type="text/javascript" src="deleteParameterStub.js"></script>
function deleteParameter(aaa100,succfun,failfun){
  var ODA_TRANSMIT_OBJECT = {};
  ODA_TRANSMIT_OBJECT.aaa100 = aaa100;
  odin.Ajax.request(contextPath+'/com/insigma/siis/local/module/common/codeparameter/DeleteParameterAction.do?method=deleteParameter',
	  			{_ODA_TRANSMIT_OBJECT:odin.encode(ODA_TRANSMIT_OBJECT)},succfun,failfun);
}