/**
*��������
*/
function sendCmd(){
	var param = {};
	param.command = document.getElementById('command').value;
	param.url = document.getElementById('url').value;
	param.param = document.getElementById('param').value;
	odin.Ajax.request(contextPath+'/sysCmd?method=sendCommand',
		param,succSendCmd,null,true,true);
}
/**
*��������ɹ�
*/
function succSendCmd(res){
	odin.info(res.mainMessage);
}
