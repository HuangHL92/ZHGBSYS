/**
 * ��ȡ��λ����
 * @author jinwei
 * @param {Object} userid
 */
function getAab001(userid){
	/***���ݵ�λ��Ų鵥λ����***/
	var params = {};
	params.sqlType = "SQL";
	params.querySQL = "@_OzvAUCeqM5XYYpTcG7kG7E+5Gv9jdIir1rYI4VivnzIN7hKhsK4MeWfAsR5kFIPk_@"+userid+"@_mMxQSO+LfFw=_@";
	var req = odin.commonQuery(params,odin.ajaxSuccessFunc,null,false,false);
	var data = odin.ext.decode(req.responseText).data.data[0];
	return data.aab001; //��λ����
	/******������λ�����ѯ******/
}
/**
 * ��ʱ���ַ���2008-4-16 9:20:04ֻ��ʾ����
 * @param {Object} value
 * @author jinwei
 */
function renderDate(value){
	if(value==null){
		return "";
	}
	return value.substring(0,16);
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
////////////�ж����֤�����ʽ����////////////
	function checkIDCard() 
	{
        var StrNumber=document.all.aae046.value;
		//������ݺ�������������룬
		//����˳�������������Ϊ����λ���ֵ�ַ�룬��λ���ֳ��������룬��λ����˳�����һλ����У����
		
		//���֤���볤���ж�
		if(StrNumber.length<15||StrNumber.length==16||StrNumber.length==17||StrNumber.length>18)
		{
		alert("��д�����֤���볤�Ȳ���ȷ����������д!");
		return false;
		}
		//���֤�������һλ�����ǳ���100�������˵�X.XҲ���Դ����ǰ���������10����˼
		//�����ų������һλ���ֽ������ָ�ʽ���ԣ����һλ���������һλ���ֵ��㷨
		
		var Ai;
		if(StrNumber.length==18)
		{
		Ai = StrNumber.substring(0,17);
		}
		else
		{
		Ai =StrNumber.substring(0,6)+"19"+StrNumber.substring(6,9);
		}
		//���������жϺ���IsNumeric()
		if(IsNumeric(Ai)==false)
		{
		alert("���֤���������ַ�������ȷ����������д!");
		return false;
		}
		return true;
	}
	/////////////�����жϺ���IsNumeric()/////////////////
	function IsNumeric(oNum) 
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

