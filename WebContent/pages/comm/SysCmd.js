/**
*发送命令
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
*发送命令成功
*/
function succSendCmd(res){
	odin.info(res.mainMessage);
}
