/**
 * 验证18位身份证其最后一位校验码是否正确
 * @author jinwei
 * @param {Object} pid
 */
function cardLastBitValidate(pid){
	var result = {};
	if(pid.length!=18){
		result.isRight = false;
		return result; //不等于18位的身份证号码
	}
	var wi = [7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1];
	var ai = ['1','0','X','9','8','7','6','5','4','3','2'];
	var sum = 0;
	for(i=0;i<(pid.length-1);i++){
		sum = sum + pid.charAt(i)*wi[i];
	}
	result.rightValue = ai[sum%11];
	if(pid.charAt(pid.length-1)==result.rightValue){
		result.isRight = true;
	}else{
		result.isRight = false;
	}
	return result;
}
/**
 * 15位身份证转18位
 * @auther jinwei
 * @param {Object} pid
 */
function cardBitFrom15To18(pid){
	pid = pid.toString().substr(0,6) + '19' + pid.toString().substr(6);
	var validateRtn = cardLastBitValidate(pid+'0'); //17随便加一位，调用校验函数进行校验
	pid += validateRtn.rightValue;
	return pid;  
}
/**
 * 校验身份证是否正确
 * @auther jinwei
 * @date 2008-7-24  
 * @param {Object} pid
 */
function checkIDCard(pid){
	var result = {};
	pid = pid.toString();
	if(pid.length!=15 && pid.length!=18){
		alert('您的身份证号码长度有误！');
		result.isRight = false;
		return result;
	}
	if(pid.length==15){
		pid = cardBitFrom15To18(pid);
	}
	if(!isNumeric(pid.substr(0,6))){
		alert('您的身份证号码前6位地域码应全是数字！');
		result.isRight = false;
		return result;
	}
	var birthDay_year = pid.substr(6,4);
	var cueYear = new Date().getYear();
	if(birthDay_year>cueYear || birthDay_year<(cueYear-200)){
		alert('您的身份证号码里的出生年份不对！其为'+birthDay_year);
		result.isRight = false;
		return result;
	}
	var birthDay_month = pid.substr(10,2);
	if(birthDay_month<'01' || birthDay_month> '12'){
		alert('您的身份证号码里的出生月份不对！其为'+birthDay_month);
		result.isRight = false;
		return result;
	}
	var birthDay_day = pid.substr(12,2);
	if(birthDay_day<'01'|| birthDay_day>'31'){
		alert('您的身份证号码里的出生日期不对！其为'+birthDay_year+'年'+birthDay_month+'月'+birthDay_day+'日');
		result.isRight = false;
		return result;
	}
    if(!isNumeric(pid.substr(14,3))){ //判断随机顺序位是否为数字
		alert('您的身份证号码不对!');
		result.isRight = false;
		return result;
	}
	if(cardLastBitValidate(pid).isRight==false){ //判断校验位是否正确
		alert('您的身份证号码不对!');
		result.isRight = false;
		return result;
	}
	return true; 
}

/**
 * 对value内容的加个提示显示处理
 * @param {Object} value
 * @param {Object} params
 * @param {Object} record
 * @param {Object} rowIndex
 * @param {Object} colIndex
 * @param {Object} ds
 * @author jinwei
 */
function doTipRender(value, params, record, rowIndex, colIndex, ds){
	return "<div title='"+value+"'>"+value+"</div>";
}

/**
 * 判断是否是数字
 * @param {Object} oNum
 */
function isNumeric(oNum) 
{ 
  if(!oNum) return false; 
  var strP= /^[0-9]+.?[0-9]*$/; 
  if(!strP.test(oNum)) return false; 
  try{ 
  if(parseFloat(oNum)!=oNum) return false; 
  } 
  catch(ex) 
  { 
   return false; 
  } 
  return true; 
}

/********对列的一个红色显示处理********/
/**
 * @author jinwei
 * @param {Object} value
 * @param {Object} params
 * @param {Object} record
 * @param {Object} rowIndex
 * @param {Object} colIndex
 * @param {Object} ds
 */
var renderRedDisplayCol = function(value, params, record, rowIndex, colIndex, ds){
	return "<font color=red>"+value+"</font>";
}







