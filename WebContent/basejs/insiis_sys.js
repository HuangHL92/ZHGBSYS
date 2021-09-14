/**
 * ��֤18λ���֤�����һλУ�����Ƿ���ȷ
 * @author jinwei
 * @param {Object} pid
 */
function cardLastBitValidate(pid){
	var result = {};
	if(pid.length!=18){
		result.isRight = false;
		return result; //������18λ�����֤����
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
 * 15λ���֤ת18λ
 * @auther jinwei
 * @param {Object} pid
 */
function cardBitFrom15To18(pid){
	pid = pid.toString().substr(0,6) + '19' + pid.toString().substr(6);
	var validateRtn = cardLastBitValidate(pid+'0'); //17����һλ������У�麯������У��
	pid += validateRtn.rightValue;
	return pid;  
}
/**
 * У�����֤�Ƿ���ȷ
 * @auther jinwei
 * @date 2008-7-24  
 * @param {Object} pid
 */
function checkIDCard(pid){
	var result = {};
	pid = pid.toString();
	if(pid.length!=15 && pid.length!=18){
		alert('�������֤���볤������');
		result.isRight = false;
		return result;
	}
	if(pid.length==15){
		pid = cardBitFrom15To18(pid);
	}
	if(!isNumeric(pid.substr(0,6))){
		alert('�������֤����ǰ6λ������Ӧȫ�����֣�');
		result.isRight = false;
		return result;
	}
	var birthDay_year = pid.substr(6,4);
	var cueYear = new Date().getYear();
	if(birthDay_year>cueYear || birthDay_year<(cueYear-200)){
		alert('�������֤������ĳ�����ݲ��ԣ���Ϊ'+birthDay_year);
		result.isRight = false;
		return result;
	}
	var birthDay_month = pid.substr(10,2);
	if(birthDay_month<'01' || birthDay_month> '12'){
		alert('�������֤������ĳ����·ݲ��ԣ���Ϊ'+birthDay_month);
		result.isRight = false;
		return result;
	}
	var birthDay_day = pid.substr(12,2);
	if(birthDay_day<'01'|| birthDay_day>'31'){
		alert('�������֤������ĳ������ڲ��ԣ���Ϊ'+birthDay_year+'��'+birthDay_month+'��'+birthDay_day+'��');
		result.isRight = false;
		return result;
	}
    if(!isNumeric(pid.substr(14,3))){ //�ж����˳��λ�Ƿ�Ϊ����
		alert('�������֤���벻��!');
		result.isRight = false;
		return result;
	}
	if(cardLastBitValidate(pid).isRight==false){ //�ж�У��λ�Ƿ���ȷ
		alert('�������֤���벻��!');
		result.isRight = false;
		return result;
	}
	return true; 
}

/**
 * ��value���ݵļӸ���ʾ��ʾ����
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
 * �ж��Ƿ�������
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

/********���е�һ����ɫ��ʾ����********/
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







