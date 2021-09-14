var BusPersonList = {
	personListInit : function(container,form,clickCallBack,callback){
		if(form == undefined){
			form = "#pageParames";
		}
		$(container).load(System.rootPath + "/common/bus-peronslist.jsp",function(){
			$("#personlist .listLi").on({
				mouseout:function(){
					$(this).removeClass('listLi-over');
				},
				click:function(){
					if($(this).is(".listLi-selected")){
						$(this).removeClass('listLi-selected');
					}else{
						$(this).addClass('listLi-selected').siblings().removeClass('listLi-selected');
						clickCallBack($("input:first-child",this).val());
					}
				}
			});
			$("#more").click(function(){
				var loading = "<span class='loading'>读取中...</span>";
				$(this).empty().append(loading);
				var pageno = parseInt($("#page").val(),10);
				$("#page").val(pageno + 1);
				BusPersonList.addMore(form,callback);
			});
			BusPersonList.addMoreFirst(form,callback);
		});
	},
	addMouserClass : function (obj){
		$(obj).on({
			mouseout:function(){
				$(this).removeClass('listLi-over');
			},
			click:function(){
				if($(this).is(".listLi-selected")){
					$(this).removeClass('listLi-selected');
				}else{
					$(this).addClass('listLi-selected').siblings().removeClass('listLi-selected');
				}
			}
		});
	},
	clearMouserClass:function(obj){
		$(obj).removeClass('listLi-over').removeClass('listLi-selected');
	},
	addOverClass : function addOverClass(obj){
		$(obj).addClass('listLi-over');
	},
	addMoreFirst :function(form,callback){
		var loading = "<span class='loading'>读取中...</span>";
		$("#more").empty().append(loading);
		var params = $(form).serialize(); 
		params = decodeURIComponent(params,true);
		$.ajax({
			type:'post',
			url:System.rootPath+"/common/exec-common-method!addMoreFirst.action",
			data:encodeURI(params),
			success: function(data, textStatus){
				BusPersonList.addPersons(data,callback);
			},
			error: function(){
				$("#more").empty().append("出现错误了(>_<)，稍后在试试!");
			}
		});
	},
	addMore : function (form,callback){
		var params = $(form).serialize(); 
		params = decodeURIComponent(params,true);
		$.ajax({
			type:'post',
			url:System.rootPath+"/common/exec-common-method!addMore.action",
			data:encodeURI(params),
			success: function(data, textStatus){
				BusPersonList.addPersons(data,callback);
			},
			error: function(){
				$("#more").empty().append("出现错误了(>_<)，稍后在试试!");
			}
		});
	},
	addPersons : function (data,callback){
		if(!data.rows || data.rows.length == 0){
			$("#more").empty().append("读取完毕!").unbind('click');
		}else{
			var listhtml = "";
			$.each(data.rows,function(i,v){
				listhtml += "<div id='listLi_"+v.personcode+"' class=\"listLi\" onmousemove=\"BusPersonList.addOverClass(this);\" >";
				listhtml += "<input type='hidden' value='"+v.personcode+"' />";
				listhtml += "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr>";
				listhtml += "<td valign=\"top\"><div><img src=\""+System.rootPath+"/lob/photo.action?personcode="+v.personcode+"&width=30&heiht=40&zoom=true\" width=\"30px\" height=\"40px\" /></div></td>";
				listhtml += "<td style=\"padding-left: 10px;font-size: 12px;\" valign=\"top\">";
				listhtml += "<p class=\"personname\"><a href=\"javascript:void(0);\" class=\"bulelink\">"+v.personname+"</a></p>";
				listhtml += "<p class=\"othermsg\">"+v.othermsg+"</p>";
				listhtml += "</td></tr></table></div>";	
				if(i == (data.rows.length - 1)){
					$("#personlist").append(listhtml);
					$("#more").empty().append("更多...");
					callback();
				}
			});
		}
	}	
};