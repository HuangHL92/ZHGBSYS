//ODA Generated JavaScript Stub for CodeParameterVS.java.addParameter
//Insert following javascript code to invoke this method:
//<script type="text/javascript" src="addParameterStub.js"></script>
function addParameter(aaa100,aaa101,succfun,failfun){
  var ODA_TRANSMIT_OBJECT = {};
  ODA_TRANSMIT_OBJECT.aaa100 = aaa100;
  ODA_TRANSMIT_OBJECT.aaa101 = aaa101;
  odin.Ajax.request(contextPath+'/com/insigma/siis/local/module/common/codeparameter/AddParameterAction.do?method=addParameter',
	  			{_ODA_TRANSMIT_OBJECT:odin.encode(ODA_TRANSMIT_OBJECT)},succfun,failfun);
}