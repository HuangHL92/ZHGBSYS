/**
 * 获取单位内码
 * @author jinwei
 * @param {Object} userid
 */
function getAab001(userid){
	/***根据单位编号查单位内码***/
	var params = {};
	params.sqlType = "SQL";
	params.querySQL = "@_OzvAUCeqM5XYYpTcG7kG7E+5Gv9jdIir1rYI4VivnzIN7hKhsK4MeWfAsR5kFIPk_@"+userid+"@_mMxQSO+LfFw=_@";
	var req = odin.commonQuery(params,odin.ajaxSuccessFunc,null,false,false);
	var data = odin.ext.decode(req.responseText).data.data[0];
	return data.aab001; //单位内码
	/******结束单位内码查询******/
}
/**
 * 将时间字符串2008-4-16 9:20:04只显示到分
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
////////////判断身份证号码格式函数////////////
	function checkIDCard() 
	{
        var StrNumber=document.all.aae046.value;
		//公民身份号码是特征组合码，
		//排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码
		
		//身份证号码长度判断
		if(StrNumber.length<15||StrNumber.length==16||StrNumber.length==17||StrNumber.length>18)
		{
		alert("填写的身份证号码长度不正确，请重新填写!");
		return false;
		}
		//身份证号码最后一位可能是超过100岁老年人的X.X也可以代表是阿拉伯数字10的意思
		//所以排除掉最后一位数字进行数字格式测试，最后一位数字有最后一位数字的算法
		
		var Ai;
		if(StrNumber.length==18)
		{
		Ai = StrNumber.substring(0,17);
		}
		else
		{
		Ai =StrNumber.substring(0,6)+"19"+StrNumber.substring(6,9);
		}
		//调用数字判断函数IsNumeric()
		if(IsNumeric(Ai)==false)
		{
		alert("身份证号码数字字符串不正确，请重新填写!");
		return false;
		}
		return true;
	}
	/////////////数字判断函数IsNumeric()/////////////////
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

