//ODA Generated JavaScript Stub for CodeParameterVS.java.loadBasicMessage
//Insert following javascript code to invoke this method:
//<script type="text/javascript" src="loadBasicMessageStub.js"></script>
function loadBasicMessage(succfun,failfun){
  var ODA_TRANSMIT_OBJECT = {};
  odin.Ajax.request(contextPath+'/com/insigma/siis/local/module/common/codeparameter/LoadBasicMessageAction.do?method=loadBasicMessage',
	  			{_ODA_TRANSMIT_OBJECT:odin.encode(ODA_TRANSMIT_OBJECT)},succfun,failfun);
}