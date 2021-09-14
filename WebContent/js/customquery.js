//学历学位信息集
function saveDegree(){
	//10、教育类别：必须填写。
	var a0837_combo = document.getElementById("a0837_combo").value;
	if(!a0837_combo){
		odin.alert("教育类别不可为空！");
		return;
	}
	//2、学历代码：必须填写。
	var a0801b_combo = document.getElementById("a0801b_combo").value;
	/*if(!a0801b_combo){
		odin.alert("学历代码不可为空！");
		return;
	}*/
	//1、学历名称：必须填写且不可以有空格或其它非汉字字符。
	var a0801a = document.getElementById("a0801a").value;
	/*if(!a0801a){
		odin.alert("学历名称不可为空！");
		return;
	}*/
	if (a0801a.indexOf(" ") >=0){
		odin.alert("学历名称不能包含空格！");
		return;
	}
   /* if(!(/^[\u3220-\uFA29]+$/.test(a0801a))&&a0801a!=""){
    	odin.alert("学历名称不能包含非汉字字符！");
		return;
    }*/
	//3、学位名称：根据实际情况填写且不可以有空格或其它非汉字字符。
	var a0901a = document.getElementById("a0901a").value;
	if(a0901a){
		if (a0901a.indexOf(" ") >=0){
			odin.alert("学位名称不能包含空格！");
			return;
		}
	   /* if(!(/^[\u3220-\uFA29]+$/.test(a0901a))){
	    	odin.alert("学位名称不能包含非汉字字符！");
			return;
	    }*/
	}
	//4、学位代码：若已经填写学位名称，则学位代码必须填写。
    if(a0901a&&a0901a!='无'){
    	var a0901b_combo = document.getElementById("a0901b_combo").value;
    	if(!a0901b_combo){
    		odin.alert("您已填写学位名称，学位代码也必须填写！");
    		return;
    	}
    }
    if(a0901a=='无'){
    	document.getElementById("a0901a").value='';
    }
	//5、入学日期：与出生日期比较，应晚于出生日期。(后台判断)
	//6、毕（肄）业日期：必须晚于入学日期。(后台判断)
	//7、学位授予日期：必须晚于入学日期。(后台判断)
	
	//8、学校（单位）名称：如果学历填写为中专及以上，则学校名称必须填写。
    if(a0801b_combo!='中专' && a0801b_combo!='中技' && a0801b_combo!='高中' && a0801b_combo!='初中' && a0801b_combo!='小学' && a0801b_combo!='其他'&&a0801b_combo!=''){
    	var a0814 = document.getElementById("a0814").value;
    	if(!a0814){
    		odin.alert("您的学历属于大专及以上，因此学校名称也必须填写！");
    		return;
    	}
    }
	//9、所学专业名称：如果学历填写为中专及以上，则所学专业名称必须填写。
    if(a0801b_combo!='中专' && a0801b_combo!='中技' && a0801b_combo!='高中' && a0801b_combo!='初中' && a0801b_combo!='小学' && a0801b_combo!='其他'&&a0801b_combo!=''){
    	var a0824 = document.getElementById("a0824").value;
    	if(!a0824){
    		odin.alert("您的学历属于大专及以上，因此所学专业名称也必须填写！");
    		return;
    	}
    }
	//11、学制年限：必须是0.5的整数倍数字。
	var a0811 = document.getElementById("a0811").value;
	if(a0811%0.5!=0){
		odin.alert("学制年限输入有误，检查是否是0.5的倍数！");
		return;
	}
	//12、判断所学专业名称中是否含有专业二字
	var a0824_1 = document.getElementById("a0824").value;
	/*if(a0824_1.match("专业")){
		$h.confirm("所学专业名称中含有'专业'二字,是否继续保存?",400,function(id){
			if("ok"==id){
				radow.doEvent("saveA08")
			}else{
				return;
			}
		})
		
	}*/
	radow.doEvent("saveA08");
}

//奖惩信息集
function saveReward(){
	//1、奖惩名称：必须填写，如果没有奖惩事项要填写“无”。
	
	//2、奖惩代码：若需要填写此项内容时,批准日期、奖惩批准机关名称、奖惩时职务层次、批准机关性质则必须填写。
	var a1404b_combo = document.getElementById("a1404b_combo").value;
	if(a1404b_combo){
		/*var a1415_combo = document.getElementById("a1415_combo").value;//奖惩时职务层次
		if(!a1415_combo){
			odin.alert("奖惩时职务层次不可为空！");
			return;
		}*/
		var a1428_combo = document.getElementById("a1428_combo").value;//批准机关性质
		/*if(!a1428_combo){
			odin.alert("批准机关性质不可为空！");
			return;
		}*/
		var a1411a = document.getElementById("a1411a").value;//奖惩批准机关名称
		if(!a1411a){
			odin.alert("批准机关不可为空！");
			return;
		}
		var a1407_1 = document.getElementById("a1407_1").value;//批准日期
		if(!a1407_1){
			odin.alert("批准日期不可为空！");
			return;
		}
		
		//不能晚于当前时间
		var now = new Date();
		var nowTime = now.toLocaleDateString();
		var year = nowTime.substring(0 , 4);//年
		var MonthIndex = nowTime.indexOf("月");
		var mon = nowTime.substring(5 , MonthIndex);//月
		var day = nowTime.substring(MonthIndex+1,nowTime.length-1);//日
		if(mon.length == 1){
			mon = "0" + mon;
		}
		if(day.length == 1){
			day = "0" + day;
		}
		
		nowTime = year + mon + day;//获取八位数的时间字符串
		
		var time = document.getElementById("a1407").value;//a1407
		if(time.length == 6){
			time = time + "01";
		}
		if(parseInt(time) > parseInt(nowTime)){
			odin.alert("批准日期不能晚于系统当前时间");
			return;
		}
		
		var time2 = document.getElementById("a1424").value;;//a1424
		if(time2.length == 6){
			time2 = time2 + "01";
		}
		if(parseInt(time2) > parseInt(nowTime)){
			odin.alert("奖惩撤销日期不能晚于系统当前时间");
			return;
		}
	}
	//3、奖惩批准日期：与参加工作时间比较，应晚于参加工作时间。
	//4、奖惩时职务层次：填写奖励时职务层次一般要低于或等于本人的现职务层次；如果是惩罚则可以高于现职务层次。
	
	//5、奖惩撤销日期：奖惩撤销日期应晚于奖惩批准日期。
	
	
	//奖惩综述，文字描述不可以输入英文单引号
	var a14z101 = document.getElementById("a14z101").value;
	var index = a14z101.indexOf("'")
	if(index != -1){
		odin.alert("文字描述不可以输入英文单引号");
		return;
	}
	
	
	radow.doEvent("saveA14");
}

//考试信息集
function saveAssess(){
	//1、考核结论：在填写考核结论类别时，如果选取受政纪处分期间年度考核不确定等次的，则需要核实奖惩信息中是否有惩罚记录。
	//2、考核年度：填写考核年度时，需要以年为单位且晚于参加工作时间。
	radow.doEvent("save");
}

//进入管理信息集
function saveEntry(){
	//1、进入本单位日期：与出生日期进行比较，一般应大于18周岁。
	
	//不能晚于当前时间
	var now = new Date();
	var nowTime = now.toLocaleDateString();
	var year = nowTime.substring(0 , 4);//年
	var MonthIndex = nowTime.indexOf("月");
	var mon = nowTime.substring(5 , MonthIndex);//月
	var day = nowTime.substring(MonthIndex+1,nowTime.length-1);//日
	if(mon.length == 1){
		mon = "0" + mon;
	}
	if(day.length == 1){
		day = "0" + day;
	}
	
	nowTime = year + mon + day;//获取八位数的时间字符串
	
	var time = document.getElementById("a2907").value;;
	if(time.length == 6){
		time = time + "01";
	}
	if(parseInt(time) > parseInt(nowTime)){
		odin.alert("进入本单位日期不能晚于系统当前时间");
		return;
	}
	
	var time2 = document.getElementById("a2949").value;;
	if(time2.length == 6){
		time2 = time2 + "01";
	}
	if(parseInt(time2) > parseInt(nowTime)){
		odin.alert("公务员登记时间不能晚于系统当前时间");
		return;
	}
	
	//2、进入本单位变动类别：必须填写。
	var a2911_combo = document.getElementById("a2911_combo").value;
	if(!a2911_combo){
		odin.alert("进入本单位变动类别不可为空！");
		return;
	}
	radow.doEvent("save");
}

//退出信息集
function saveLogout(){
	//1、退出管理方式：必须填写。
	var a3001_combo = document.getElementById("a3001_combo").value;
	if(!a3001_combo){
		odin.alert("退出管理方式不可为空！");
		return;
	}
	//2、退出本单位日期：应晚于参加工作时间。
	
	//不能晚于当前时间
	var now = new Date();
	var nowTime = now.toLocaleDateString();
	var year = nowTime.substring(0 , 4);//年
	var MonthIndex = nowTime.indexOf("月");
	var mon = nowTime.substring(5 , MonthIndex);//月
	var day = nowTime.substring(MonthIndex+1,nowTime.length-1);//日
	if(mon.length == 1){
		mon = "0" + mon;
	}
	if(day.length == 1){
		day = "0" + day;
	}
	
	nowTime = year + mon + day;//获取八位数的时间字符串
	
	var time = document.getElementById("a3004").value;;
	if(time.length == 6){
		time = time + "01";
	}
	if(parseInt(time) > parseInt(nowTime)){
		odin.alert("退出管理时间不能晚于系统当前时间");
		return;
	}
	
	
	radow.doEvent("save");

}