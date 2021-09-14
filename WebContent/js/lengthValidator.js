/*
 * BusinessInfo.jsp
 */
//录用时政治面貌
function a6003Length(value) {
	if (value.length > 10) {
		return "长度超过限制:10字以内！";
	} else {
		return true;
	}
}
// 录用时学历名称
function a6004Length(value) {
	if (value.length > 30) {
		return "长度超过限制：30字以内！";
	} else {
		return true;
	}
}
// 录用时学位名称
function a6006Length(value) {
	if (value.length > 10) {
		return "长度超过限制：10个字以内！";
	} else {
		return true;
	}
}

//报考准考证号
function a6401Length(value) {
	if (/[\u4E00-\u9FA5\uF900-\uFA2D]/.test(value)) {
		return "不能含有中文！";
	} else {
		return true;
	}
}

//选调生初始工作单位
function a2970bLength(value){
	if(value.length>50){
		return "长度超过限制：50字以内！";
	} else{
		return true;
	}
}
//选调时学历名称
function a6108Length(value){
	if(value.length>30){
		return "长度超过限制：30字以内！";
	} else{
		return true;
	}
}
//录用时学位名称
function a6110Length(value){
	if(value.length>10){
		return "长度超过限制：10字以内！";
	} else{
		return true;
	}
}
//选调时政治面貌
function a6107Length(value){
	if(value.length>10){
		return "长度超过限制：10字以内！";
	} else{
		return true;
	}
}
//报考准考证号
function a6401_1Length(value){
	if(/[\u4E00-\u9FA5\uF900-\uFA2D]/.test(value)){
		return "不能含有中文！";
	} else{
		return true;
	}
}
//培训班名称
function a1131Length(value){
	if(value.length>60){
		return "长度超过限制：60字以内！";
	} else{
		return true;
	}
}
//培训主办单位
function a1114Length(value){
	if(value.length>60){
		return "长度超过限制：60字以内！";
	} else{
		return true;
	}
}
//培训机构名称
function a1121aLength(value){
	if(value.length>60){
		return "长度超过限制：60字以内！";
	} else{
		return true;
	}
}
//海外工作年限

function a6_101716Length(value){
	if(value.length>20){
		return "长度超过限制：20数字以内！";
	} else if(!/^[0-9]*$/.test(value)){
		return "请输入数字！";
	}else{
		return true;
	}
}
/*
 * OrthersAddPage.jsp
 */
//进入本单位前工作单位名称
function a2921aLength(value){
	if(value.length>30){
		return "长度超过限制：30字以内！";
	} else{
		return true;
	}
}
//家庭地址
function a3711Length(value){
	if(value.length>60){
		return "长度超过限制：60字以内！";
	} else{
		return true;
	}
}
//调往单位
function a3007aLength(value){
	if(value.length>30){
		return "长度超过限制：30字以内！";
	} else{
		return true;
	}
}
//在原单位职务
function a2941Length(value){
	if(value.length>20){
		return "长度超过限制：20字以内！";
	} else{
		return true;
	}
}
//在原单位职务层次
function a2944Length(value){
	if(value.length>20){
		return "长度超过限制：20字以内！";
	} else{
		return true;
	}
}
//离退批准文号
function a3137Length(value){
	if(value.length>24){
		return "长度超过限制：24字以内！";
	} else{
		return true;
	}
}
//曾任最高职务
function a3118Length(value){
	if(value.length>35){
		return "长度超过限制：35字以内！";
	} else{
		return true;
	}
}
//离退后管理单位
function a3117aLength(value){
	if(value.length>30){
		return "长度超过限制：30字以内！";
	} else{
		return true;
	}
}
//备注
function a3034Length(value){
	if(value.length>250){
		return "长度超过限制：250字以内！";
	} else {
		return true;
	}
}
//拟任职务
function a5304Length(value){
	if(value.length>200){
		return "长度超过限制：200字以内！";
	} else {
		return true;
	}
}
//拟免职务
function a5315Length(value){
	if(value.length>200){
		return "长度超过限制：200字以内！";
	} else {
		return true;
	}
}
//任免理由
function a5317Length(value){
	if(value.length>200){
		return "长度超过限制：200字以内！";
	} else {
		return true;
	}
}
//呈报单位
function a5319Length(value){
	if(value.length>200){
		return "长度超过限制：200字以内！";
	} else {
		return true;
	}
}
//填报人
function a5327Length(value){
	if(value.length>18){
		return "长度超过限制：18字以内！";
	} else {
		return true;
	}
}
//办公地址
function a3701Length(value){
	if(value.length>60){
		return "长度超过限制：60字以内！";
	} else {
		return true;
	}
}

/*
 * 办公电话（仅验证数字和-）
 * 2017-4-19：fujun
 */
function a3707fLength(value){
	if(!/^[\d\-]+$/.test(value)){
		return "请输入正确的电话！";
	} else {
		return true;
	}
}

//办公电话
function a3707aLength(value){
	if(!/^0\d{2,3}-?\d{7,8}$/.test(value)){
		return "请输入正确的号码！";
	} else {
		return true;
	}
}
//移动电话
function a3707cLength(value){
	if(!/^1\d{10}$/.test(value)){
		return "请输入正确的号码！";
	} else {
		return true;
	}
}

//法人单位编码
function b0114Length(value) {
	if(value.length>50) {
		return "长度超过限制：50字以内";
	} else {
		return true;
	}
}

//机构名称
function b0101Length(value) {
	if(value.length>50) {
		return "长度超过限制：50字以内";
	} else {
		return true;
	}
}

//简称
function b0104Length(value) {
	if(value.length>50) {
		return "长度超过限制：50字以内";
	} else {
		return true;
	}
}

//参照公务员法管理审批文号
function b0239Length(value) {
	if(value.length>24) {
		return "长度超过限制：24字以内";
	} else {
		return true;
	}
}

//备注事项
function b0180Length(value) {
	if(value.length>150) {
		return "长度超过限制：150字以内";
	} else {
		return true;
	}
}


//机构名称
function b0101nameLength(value) {
	if(value.length>50) {
		return "长度超过限制：50字以内";
	} else {
		return true;
	}
}



//ProfessSkillAddPage.jsp
//专业技术任职资格名称
function a0602Length(value) {
	if(value.length>30) {
		return "长度超过限制：30字以内";
	} else {
		return true;
	}
}

//评委会或考试名称
function a0611Length(value) {
	if(value.length>50) {
		return "长度超过限制：50字以内";
	} else {
		return true;
	}
}



//DegreesAddPage.jsp
//学历名称
function a0801aLength(value) {
	if(value.length>60) {
		return "长度超过限制：60字以内";
	} else {
		return true;
	}
}

//学位名称
function a0901aLength(value) {
	if(value.length>20) {
		return "长度超过限制：20字以内";
	} else {
		return true;
	}
}

//学校（单位）名称
function a0814Length(value) {
	if(value.length>60) {
		return "长度超过限制：60字以内";
	} else {
		return true;
	}
}

//所学专业名称
function a0824Length(value) {
	if(value.length>20) {
		return "长度超过限制：20字以内";
	} else {
		return true;
	}
}


//WorkUnitsAddPage.jsp
//全称
function a0192aLength(value) {
	if(value.length>1000) {
		return "长度超过限制：1000字以内";
	} else {
		return true;
	}
}
//分管（从事）工作
function a0229Length(value) {
	if(value.length>50) {
		return "长度超过限制：50字以内";
	} else {
		return true;
	}
}
//任职文号
function a0245Length(value) {
	if(value.length>130) {
		return "长度超过限制：130字以内";
	} else {
		return true;
	}
}
//免职文号
function a0267Length(value) {
	if(value.length>60) {
		return "长度超过限制：12字以内";
	} else {
		return true;
	}
}

//简称
function a0192Length(value) {
	if(value.length>1000) {
		return "长度超过限制：1000字以内";
	} else {
		return true;
	}
}

//职务名称
function a0216aLength(value) {
	if(value.length>50) {
		return "长度超过限制：50字以内";
	} else {
		return true;
	}
}

//分管（从事）工作
function a0229Length(value) {
	if(value.length>50) {
		return "长度超过限制：50字以内";
	} else {
		return true;
	}
}


//RewardPunishAddPage.jsp
//文字描述
function a14z101Length(value) {
	if(value.length>1000) {
		return "长度超过限制：1000字以内";
	} else {
		return true;
	}
}


//AssessmentInfoAddPage.jsp
//文字描述
function a15z101Length(value) {
	if(value.length>1000) {
		return "长度超过限制：1000字以内";
	} else {
		return true;
	}
}

//RewardPunishAddPage
//文字描述
function a14z101Length(value) {
	if(value.length>1000) {
		return "长度超过限制：1000字以内";
	} else {
		return true;
	}
}
//OrthersAdd
//成长地
function a0115aLength(value) {
	if(value.length>60) {
		return "长度超过限制：60字以内";
	} else {
		return true;
	}
}
//workUnitsadd
//职务变动综述
function a0272Length(value) {
	if(value.length>50) {
		return "长度超过限制：50字以内";
	} else {
		return true;
	}
}

//rank
//批准文号
function a0511Length(value) {
	if(value.length>12) {
		return "长度超过限制：24字以内";
	} else {
		return true;
	}
}








