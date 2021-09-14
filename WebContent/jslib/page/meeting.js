function clearIframe(iframeObjId) {
	$("#" + iframeObjId).attr('src', System.rootPath + '/blank.html');
}

function textAreaFormatter(value,rowData,rowIndex) {
	if (value != null) {
		value = value.replace(/\n/g,"<br/>")
		value = value.replace(/\r/g,"")
		return value;
	}
	
	return value;
}
			
function getRemovalUrl(rowData) {
	var url = System.rootPath + '/removal/removal!detail.action' + getCommonParameters(rowData);
	return url;
}

function getGeneralUrl(rowData) {
	var url = System.rootPath + '/common/general!detail.action' + getCommonParameters(rowData);
	return url;
}

function getCommonParameters(rowData) {
	var personcode = rowData.personcode;
	var url = "?personcode=" + rowData.personcode;
	url = url + "&group=A&tables=A000&querySQL=A000.personcode in (";
	
	// 获取当前所有干部的person code
	var allRows = $('#personList').datagrid('getRows');
	var ids = "";
	for (var i = 0; i < allRows.length; i++) {
		if (ids == "") ids = allRows[i].personcode;
		else ids = ids + "," + allRows[i].personcode;
	}
	
	url = url + ids + ")";	
	return url;				
}

function onPrint(rows) {
	var url = System.rootPath + "/report/report-print!reportPrint.action?rtid=8&keys=";
	
	var key = "";
	for (var i = 0; i < rows.length; i++) {
		if (key == "") key = rows[i].personcode;
		else key = key + "," + rows[i].personcode;
	}
	
	url = url + key;
	window.open(url);
}

function onMeetingAccess() {
	$("#meetingAccessWindow").window('open');
	$("#meetingAccessContentIframe").attr('src', 'meeting!grantMeeting.action?meetingId=' + g_meetingId);							
}

function onMeetingAccessgCompleted() {
	$("#meetingAccessWindow").window('close');
}

function onSaveAs() {
	$.messager.confirm('确认',  '确定另存当前议题?<br/><br/>注意：系统将自动添加"副本"字样到议题结尾以示区别！', function(isOk) {
		if (isOk) {
			onSaveMeeting(true);
		}
	});
}

function onDeleteMeeting() {
	$.messager.confirm('确认',  '确定删除当前会议?', function(isOk) {
		if (isOk) {
			$.ajax({
				type: "POST",
				url: System.rootPath+ '/meeting/meeting!deleteMeeting.action',
				data: { 
					meetingId: g_meetingId
				},
				success:function(json){
					if (json.indexOf("删除成功") >= 0) {
						alert('删除成功!');
						//window.parent.closeMeetingDetails();
						System.closeCurrentTab();
					}
					else {
						$.messager.alert('提示', '删除失败!');								
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					$.messager.alert('提示', "删除失败,请重试!"+textStatus+errorThrown);
				}
			});		
		}
	});
}

function endPersonEdit() {
	$('#personList').datagrid('endEdit', lastIndex);
	lastIndex = -1;
}

function onSaveMeeting(isSaveAs) {
	if (!$("#meetingMasterForm").form('validate')) {
		$.messager.alert('提示', "请输入所有必填项!");
		return;
	}
	
	endPersonEdit();
	
	var allRows = $("#personList").datagrid('getRows');
	if (allRows == null || allRows.length == 0) {
		$.messager.alert('提示', '请选择干部!');
		return;
	}	
	
	var msg = checkPersonUpdateStatus();
	if (msg.indexOf('出现错误') >= 0) {
		alert(msg);
		return;
	}
	else if (msg != '') {
		var meetingAction = $("#meetingAction").val();
		if (meetingAction != '1') {
			alert('请注意：以下干部未进行维护确认，只有所有干部都进行了维护确认，才能将会议设置为上会议题！\r\n\r\n' + msg);
		}
		else {
			alert('请注意：本议题已经是上会议题，只有所有干部都进行了维护确认，才能进行保存！以下干部未进行维护确认：\r\n\r\n' + msg);
			return;
		}
	}
	
	meetdate = $("#meetdate").datebox('getValue');
	attend_meetdate = $("#attendMeetdate").datebox('getValue');
	
	if (meetdate > attend_meetdate){
		$.messager.alert('提示', "上会时间必须大于等于准备会议时间!");
		return;
	}
	
	System.openLoadMask("#meetingPanel", "正在保存...");
	
	// 保存前先更新下序号
	updateSortNo();
	
	for (var i = 0; i < allRows.length; i++) {
		// 重要：处理隐藏域，视乎Java的JSON程序会将null值转换为undefined.. 晕倒..
		if (typeof(allRows[i].nrzw) == 'undefined') allRows[i].nrzw = '';
		if (typeof(allRows[i].nmzw) == 'undefined') allRows[i].nmzw = '';
		if (typeof(allRows[i].kccl) == 'undefined') allRows[i].kccl = '';
		if (typeof(allRows[i].rmly) == 'undefined') allRows[i].rmly = '';
	}
								
	// 组合meeting master
	var meetingId = isSaveAs ? "0" : g_meetingId;				
	var meetcaption = g_meetingId <=0 ? $("#meetcaption").combobox('getText') : $("#meetcaption").val();
	if (isSaveAs) meetcaption = meetcaption + " - 副本";
	
	var master = {
		meetdate: $("#meetdate").datebox('getValue'),
		attend_meetdate: $("#attendMeetdate").datebox('getValue'),
		meetname: $("#meetname").val(),
		meetcaption: meetcaption,
		qihao: $("#qihao").val(),
		id: meetingId
	};
	
	var masterRow = [master];				
	var meetingMasterRows = encodeURI($.toJSON(masterRow));
	var meetingPersonRows = encodeURI($.toJSON(allRows));
	
	$.ajax({
		type: "POST",
		url: System.rootPath+ '/meeting/meeting!updateMeeting.action',
		data: { 
			meetingId: meetingId,
			meetingMasterRows: meetingMasterRows,
			meetingPersonRows: meetingPersonRows,
			meetingSaveAs: isSaveAs ? "1" : "0"
		},
		success:function(json){
			System.closeLoadMask("#meetingPanel");
			
			try {
				if (json.message.indexOf("保存成功") >= 0) {
					if (meetingId == 0) {
						if (json.saveAs == "1") {
							alert("另存成功!");														
						}
						else {									
							alert("保存成功!");														
						}
						
						window.location.href = 'meeting!showMeeting.action?meetingId=' + json.meetingId;
						//window.parent.reloadMeetingList();									
					}
					else {
						alert("保存成功!");	
						window.location.reload();
					}
				}
				else {
					$.messager.alert('提示', '保存失败!');						
				}
			}
			catch (ex) {
				$.messager.alert('提示', '保存失败!');						
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			System.closeLoadMask("#meetingPanel");
			$.messager.alert('提示', "保存失败,请重试!"+textStatus+errorThrown);
		}
	});				
}

function onSaveHistoryMeeting() {
	if (!$("#meetingMasterForm").form('validate')) {
		$.messager.alert('提示', "请输入所有必填项!");
		return;
	}
	
	meetdate = $("#meetdate").datebox('getValue');
	attend_meetdate = $("#attendMeetdate").datebox('getValue');
	
	if (meetdate > attend_meetdate){
		$.messager.alert('提示', "上会时间必须大于等于准备会议时间!");
		return;
	}
	
	System.openLoadMask("#meetingPanel", "正在保存...");
	
	// 组合meeting master
	var meetingId = g_meetingId;				
	var meetcaption = $("#meetcaption").val();
	
	var master = {
		meetdate: $("#meetdate").datebox('getValue'),
		attend_meetdate: $("#attendMeetdate").datebox('getValue'),
		meetname: $("#meetname").val(),
		meetcaption: meetcaption,
		qihao: $("#qihao").val(),
		id: meetingId
	};
	
	var masterRow = [master];				
	var meetingMasterRows = encodeURI($.toJSON(masterRow));
	
	$.ajax({
		type: "POST",
		url: System.rootPath+ '/meeting/meeting!updateHistoryMeeting.action',
		data: { 
			meetingId: meetingId,
			meetingMasterRows: meetingMasterRows
		},
		success:function(json){
			System.closeLoadMask("#meetingPanel");
			
			try {
				if (json.message.indexOf("保存成功") >= 0) {
					alert("保存成功!");	
					window.location.reload();
				}
				else {
					$.messager.alert('提示', '保存失败!');						
				}
			}
			catch (ex) {
				$.messager.alert('提示', '保存失败!');						
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			System.closeLoadMask("#meetingPanel");
			$.messager.alert('提示', "保存失败,请重试!"+textStatus+errorThrown);
		}
	});				
}

// ----------- 排序开始 
function geAllPersons() {
	 return $("#personList").datagrid('getRows');
}

function onSortPersonBySortNo() {
	endPersonEdit();
	
	var allRows = $("#personList").datagrid('getRows');
	if (allRows != null && allRows.length != 0) {
		allRows.sort(comparePerson);
		$('#personList').datagrid('loadData', {total:allRows.length, rows: allRows});
	}
	
	// 更新序号
	updateSortNo();
}
// ----------- 排序结束

function onSelectedPerson(selectedPerson) {
	System.openLoadMask($('#meetingPanel'), '正在添加干部，请稍后...');
	
	$("#personWindow").window('close');
	
	// 移除已添加的
	var newPersons = new Array();
	var currentSelectedPerson = $("#personList").datagrid('getRows');
	if (currentSelectedPerson == null || currentSelectedPerson.length == 0) {
		var index = 0;
		for (var i = 0; i < selectedPerson.length; i++) {
			newPersons[index++] = convertPerson(selectedPerson[i]);
		}
	}
	else {
		var index = 0;
		for (var i = 0; i < selectedPerson.length; i++) {
			var isNewPerson = true;
			for (var j = 0; j < currentSelectedPerson.length; j++) {
				if (currentSelectedPerson[j].personcode == selectedPerson[i].personcode) {
					isNewPerson = false;
					break;
				}
			}
			
			if (isNewPerson) {
				newPersons[index++] = convertPerson(selectedPerson[i]);
			}
		}
	}
	
	// 提高性能，直接reload数据, by YZQ on 2013/06/15
	var newRowData = newPersons.concat(currentSelectedPerson);
	for (var i = 0; i < newRowData.length; i++) {
		newRowData[i].pcnsort = i + 1;
	}
	
	$('#personList').datagrid('loadData', {total:newRowData.length, rows: newRowData});
	
	// 批处理学历学位, by YZQ on 2013/06/09
	var rowIndexArray = [];
	for (var i = 0; i < newPersons.length; i++) {
		var rowIndex = $('#personList').datagrid('getRowIndex', newPersons[i]);
		rowIndexArray.push(rowIndex);
	}
	
	getZgxlxwBatch(newPersons, rowIndexArray);
}

function updateSortNo() {
	// 更新序号
	var allRows = $('#personList').datagrid('getRows');
	if (allRows != null) {
		for (var i = 0; i < allRows.length; i++) {
			allRows[i].pcnsort = i + 1;
			$('#personList').datagrid('updateRow', { index: i, row: allRows[i]});					
		}
	}
}

function convertPerson(fromPersonSelection) {
	var person = {
					pcbh: g_meetingId,
					personcode: getSafeData(fromPersonSelection.personcode),
					pcnsort: '1',
					xingm: getSafeData(fromPersonSelection.xingm),
					birthday: getSafeData(fromPersonSelection.birthday),
					rxzjsj: getSafeData(fromPersonSelection.xianjsj),
					xlxw: '',
					zhiw: getSafeData(fromPersonSelection.zhiw),
					nrmzw: '',
					mztjqk: '', 
					kctjqk: '', 
					pxqk: '',
					bz: '',
					kccl: '',
					csyj: '',
					rmly: '',
					nrzw: '',
					nmzw: '',
					confirmed: 0,
					sex: getSex(fromPersonSelection.sexCode),
					isNewUser: true
				};
				
	return person;
}

function getSex(value) {
	var val = getSafeData(value);
	if(val == "2") return '女'; else return '男';
}

function getZgxlxw(rowData, rowIndex) {	
	$.ajax({
		async: false, // 同步
		type: "POST",
		url: System.rootPath+ '/meeting/meeting!getZgxlxw.action',
		data: { 
			personcode: rowData.personcode
		},
		success:function(json){			
			rowData.xlxw = json;
			$('#personList').datagrid('updateRow', { index : rowIndex, row: rowData });
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			return "";
		}
	});		
}

// 检查干部信息更新情况
function checkPersonUpdateStatus() {
	var personcodes = '';
		
	var rows = $('#personList').datagrid('getRows');
	if (rows) {
		for (var i = 0; i < rows.length; i++) {
			if (personcodes == '') personcodes = rows[i].personcode;
			else personcodes = personcodes + ',' + rows[i].personcode;
		}
	}
	
	if (personcodes == '') return '';
	
	var result = null;
	
	$.ajax({
		async: false, // 同步
		type: "POST",
		url: System.rootPath+ '/meeting/meeting!checkMeetingConfirmation.action',
		data: { 
			personcodes: personcodes,
			meetingId: g_meetingId
		},
		success:function(data){
			result = data;
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			result = '出现错误，错误信息：' + textStatus;
		}
	});	

	if (result) {
		if (result.length == 0) return '';
		var names = '';
		$.each(result, function(i, v) {
			if (names == '') names = v;
			else names = names + '\r\n' + v;
		});
		return names;
	}
	else {
		return '';
	}
}

function getZgxlxwBatch(rowDataArray, rowIndexArray) {
	var personcodes = '';
	for (var i = 0; i < rowDataArray.length; i++) {
		if (personcodes == '') personcodes = rowDataArray[i].personcode;
		else personcodes = personcodes + "," + rowDataArray[i].personcode;
	}
	
	$.ajax({
		async: false, // 同步
		type: "POST",
		url: System.rootPath+ '/meeting/meeting!getZgxlxwBatch.action',
		data: { 
			personcodes: personcodes,
			meetingId: g_meetingId
		},
		success:function(json){
			for (var i = 0; i < json.length; i++) {
				rowDataArray[i].xlxw = json[i].xlxw;
				rowDataArray[i].confirmed = json[i].confirmed;
				
				$('#personList').datagrid('updateRow', { index : rowIndexArray[i], row: rowDataArray[i] });
			}
			
			System.closeLoadMask($('#meetingPanel'));
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			System.closeLoadMask($('#meetingPanel'));
			return "";
		}
	});		
}

function getSafeData(value) {
	if (value == null || value == 'undefined') return '';
	else return value;
}

function onImport() {
	$("#importDialog").show();
	
	$("#importDialog").dialog({
		shadow:true,
		modal :true,
		resizable:true,
		width: 820,
		height: 450,
		buttons:[{
			text:'导入',
			iconCls:'icon-ok',
			handler:function(){			
				var editor = $("#importContent").cleditor()[0];
				editor.updateTextArea();
				
				$('#importDialog').dialog('close');
				var value = $("#importDialog textarea").val();	
				importData(value);
			}
		},{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				$('#importDialog').dialog('close');
			}
		}]
	});
}

function onNrmzw(rowIndex, rowData) {
	$("#nrmzwDialog").show();
	
	$("#nrmzwDialog").dialog({
		shadow:true,
		modal :true,
		resizable:true,
		width: 480,
		height: 240,
		title: rowData.xingm + '同志的拟任免职务',
		buttons:[{
			text:'确定',
			iconCls:'icon-ok',
			handler:function(){
				$('#nrmzwDialog').dialog('close');
				rowData.nrzw = $("#nrzw").val();
				rowData.nmzw = $("#nmzw").val();
				$('#personList').datagrid('updateRow', { index: rowIndex, row: rowData });
			}
		},{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				$('#nrmzwDialog').dialog('close');
			}
		}],
		onOpen : function () {
			$("#nrzw").val(rowData.nrzw);
			$("#nmzw").val(rowData.nmzw);
		}
	});
}

function onRmly(rowIndex, rowData) {
	$("#rmlyDialog").show();
	
	$("#rmlyDialog").dialog({
		shadow:true,
		modal :true,
		resizable:true,
		width: 560,
		height: 340,
		title: rowData.xingm + '同志的任免理由',
		buttons:[{
			text:'确定',
			iconCls:'icon-ok',
			handler:function(){
				$('#rmlyDialog').dialog('close');
				rowData.rmly = $("#rmlyDialog textarea").val();
				$('#personList').datagrid('updateRow', { index: rowIndex, row: rowData });
			}
		},{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				$('#rmlyDialog').dialog('close');
			}
		}],
		onOpen : function () {
			$('#rmlyDialog textarea').val(rowData.rmly);
		}
	});
}

function onKccl(rowIndex, rowData) {
	var editor = $("#kccl").cleditor()[0];
	$("#kccl").val(rowData.kccl);
	editor.updateFrame();
	
	$("#kcclDialog").show();
	
	$("#kcclDialog").dialog({
		shadow:true,
		modal :true,
		resizable:true,
		width: 740,
		height: 430,
		title: rowData.xingm + '同志的考察材料',
		buttons:[{
			text:'确定',
			iconCls:'icon-ok',
			handler:function(){
				var editor = $("#kccl").cleditor()[0];
				editor.updateTextArea();
				
				$('#kcclDialog').dialog('close');
				rowData.kccl = $("#kcclDialog textarea").val();
				$('#personList').datagrid('updateRow', { index: rowIndex, row: rowData });
			}
		},{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				$('#kcclDialog').dialog('close');
			}
		}],
		onOpen : function () {
			$('#kcclDialog textarea').val(rowData.kccl);
		}
	});
}

function onWorkPhoto(rowData) {
	$("#workPhotoWindow").window('setTitle', rowData.xingm + '同志的工作照');
	$("#workPhotoWindow").window('open');
	$("#workPhotoContentIframe").attr('src', System.rootPath + '/meeting/meeting!personWorkPhoto.action?personcode=' + rowData.personcode);
}

function onUploadWorkPhotoCompleted() {
	$("#workPhotoWindow").window('close');
}


function importData(value) {
					//alert(value);
	try {
		endPersonEdit();
	
		var persons = process(value);
		if (persons == null || persons.length == 0) return;
		
		var allRows = $('#personList').datagrid('getRows');
		
		for (var i = 0; i < allRows.length; i++) {
			for (var j = 0; j < persons.length; j++) {
				if (persons[j].xingm == allRows[i].xingm || 
												persons[j].xingm.indexOf(allRows[i].xingm) >= 0) {
					//allRows[i].pcnsort = persons[j].pcnsort;
					allRows[i].birthday = persons[j].birthday;
					allRows[i].xlxw = persons[j].xlxw;
					allRows[i].zhiw = persons[j].zhiw;
					allRows[i].nrmzw = persons[j].nrmzw;
					allRows[i].mztjqk = persons[j].mztjqk;
					allRows[i].kctjqk = persons[j].kctjqk; 
					allRows[i].pxqk = persons[j].pxqk;
					allRows[i].bz = persons[j].bz;
					allRows[i].rxzjsj = persons[j].rxzjsj;
					allRows[i].csyj = persons[j].csyj;
				}
			}
		}
		
		for (var i = 0; i < allRows.length; i++) {
			$('#personList').datagrid('updateRow', { index: i, row: allRows[i] });
		}
		
		// 重新排序
		//onSortPerson();
	}
	catch (ex) {
		$.messager.alert('错误', '导入时发生错误,请检查表格格式是否正确! 错误信息: ' + ex);
	}
}

// ----------- Import Start -------------
function process(value) {
	// 遍历表格
	var persons = new Array();
	var findHeader = false;
	var currentRow = 0;
	var personCount = 0;
	
	$("#importData").html(cleanWord(value));
	//alert($("#importData").find("table").length);
	$("#importData").find("table tr").each(function() {
							
		if (findHeader) {
			currentRow++;
			
			var columns = $(this).find("td");
			// 找到12行的列，那么认为是内容行
			if (columns.length == 12) {											
				persons[personCount++] = convertToPersonObject(columns);
			}
		}
		else {					
			$(this).find("td").each(function() {
				//alert($(this).html());
				if (!findHeader) {
					if ($(this).html().indexOf("序号") >=0 ) {
						findHeader = true;
					}
				}
			});
		}
	});
	
	return persons;
}

function convertToPersonObject(columns) {
	var person = new Object();
	
	for (var i = 0; i < 12; i++) {
		//var value = cleanWord($(columns[i]).html());
		var value = $(columns[i]).html();
		value = getData(value);
		//alert(value);
		
		if (i == 0) person.pcnsort = value;
		else if (i == 1) person.xingm = value;
		else if (i == 2) person.birthday = value;
		else if (i == 3) person.xlxw = value;
		else if (i == 4) person.zhiw = value;
		else if (i == 5) person.rxzjsj = value; // 任现职级时间
		else if (i == 6) person.nrmzw = value;
		else if (i == 7) person.mztjqk = value;
		else if (i == 8) person.kctjqk = value;
		else if (i == 9) person.pxqk = value;
		else if (i == 10) person.csyj = value;
		else if (i == 11) person.bz = value;
		else ;
	}
	
	return person;
}

function getData(value) {
	var result = '';
	if (value != "") {
		// remove all SPAN 
		var val = value.replace(/<SPAN\s*[^>]*>([\s\S]*?)<\/SPAN>/gi, '$1');
		val = val.replaceAll('<SPAN>', '').replaceAll('</SPAN>', '');
		val = val.replace(/<FONT\s*[^>]*>([\s\S]*?)<\/FONT>/gi, '$1');

		val = val.replace(/<P\s*[^>]*>([\s\S]*?)<\/P>/gi, '$1');
							//alert(val);
		return val;
	}
	
	return result;
}

function cleanWord( oNode ){
	var html = oNode;

	html = html.replace(/<o:p>\s*<\/o:p>/g, '') ;
	html = html.replace(/<o:p>[\s\S]*?<\/o:p>/g, '&nbsp;') ;
	
	// YZQ -- Start 
	// Remove </b>, <strong>
	html = html.replace(/<B\s*[^>]*>([\s\S]*?)<\/B>/gi, '$1');
	html = html.replace(/<STRONG\s*[^>]*>([\s\S]*?)<\/STRONG>/gi, '$1');
	// Remove <div>
	html = html.replace( /<DIV\s*[^>]*>([\s\S]*?)<\/DIV>/gi, '$1' ) ;
	// YZQ -- End

	// Remove mso-xxx styles.
	html = html.replace( /\s*mso-[^:]+:[^;"]+;?/gi, '' ) ;

	// Remove margin styles.
	html = html.replace( /\s*MARGIN: 0cm 0cm 0pt\s*;/gi, '' ) ;
	html = html.replace( /\s*MARGIN: 0cm 0cm 0pt\s*"/gi, "\"" ) ;

	html = html.replace( /\s*TEXT-INDENT: 0cm\s*;/gi, '' ) ;
	html = html.replace( /\s*TEXT-INDENT: 0cm\s*"/gi, "\"" ) ;

	html = html.replace( /\s*TEXT-ALIGN: [^\s;]+;?"/gi, "\"" ) ;

	html = html.replace( /\s*PAGE-BREAK-BEFORE: [^\s;]+;?"/gi, "\"" ) ;

	html = html.replace( /\s*FONT-VARIANT: [^\s;]+;?"/gi, "\"" ) ;

	html = html.replace( /\s*tab-stops:[^;"]*;?/gi, '' ) ;
	html = html.replace( /\s*tab-stops:[^"]*/gi, '' ) ;

	// Remove FONT face attributes.
	html = html.replace( /\s*face="[^"]*"/gi, '' ) ;
	html = html.replace( /\s*face=[^ >]*/gi, '' ) ;
	html = html.replace( /\s*FONT-FAMILY:[^;"]*;?/gi, '' ) ;				

	// Remove Class attributes
	html = html.replace(/<(\w[^>]*) class=([^ |>]*)([^>]*)/gi, "<$1$3") ;

	// Remove styles.
	html = html.replace( /<(\w[^>]*) style="([^\"]*)"([^>]*)/gi, "<$1$3" ) ;

	// Remove style, meta and link tags
	html = html.replace( /<STYLE[^>]*>[\s\S]*?<\/STYLE[^>]*>/gi, '' ) ;
	html = html.replace( /<(?:META|LINK)[^>]*>\s*/gi, '' ) ;

	// Remove empty styles.
	html =  html.replace( /\s*style="\s*"/gi, '' ) ;

	html = html.replace( /<SPAN\s*[^>]*>\s*&nbsp;\s*<\/SPAN>/gi, '&nbsp;' ) ;

	html = html.replace( /<SPAN\s*[^>]*><\/SPAN>/gi, '' ) ;

	// Remove Lang attributes
	html = html.replace(/<(\w[^>]*) lang=([^ |>]*)([^>]*)/gi, "<$1$3") ;

	html = html.replace( /<SPAN\s*>([\s\S]*?)<\/SPAN>/gi, '$1' ) ;

	html = html.replace( /<FONT\s*>([\s\S]*?)<\/FONT>/gi, '$1' ) ;

	// Remove XML elements and declarations
	html = html.replace(/<\\?\?xml[^>]*>/gi, '' ) ;

	// Remove w: tags with contents.
	html = html.replace( /<w:[^>]*>[\s\S]*?<\/w:[^>]*>/gi, '' ) ;

	// Remove Tags with XML namespace declarations: <o:p><\/o:p>
	html = html.replace(/<\/?\w+:[^>]*>/gi, '' ) ;

	// Remove comments [SF BUG-1481861].
	html = html.replace(/<\!--[\s\S]*?-->/g, '' ) ;

	html = html.replace( /<(U|I|STRIKE)>&nbsp;<\/\1>/g, '&nbsp;' ) ;

	html = html.replace( /<H\d>\s*<\/H\d>/gi, '' ) ;

	// Remove "display:none" tags.
	html = html.replace( /<(\w+)[^>]*\sstyle="[^"]*DISPLAY\s?:\s?none[\s\S]*?<\/\1>/ig, '' ) ;

	// Remove language tags
	html = html.replace( /<(\w[^>]*) language=([^ |>]*)([^>]*)/gi, "<$1$3") ;

	// Remove onmouseover and onmouseout events (from MS Word comments effect)
	html = html.replace( /<(\w[^>]*) onmouseover="([^\"]*)"([^>]*)/gi, "<$1$3") ;
	html = html.replace( /<(\w[^>]*) onmouseout="([^\"]*)"([^>]*)/gi, "<$1$3") ;

	var cleanWordKeepsStructure = true;
	if (cleanWordKeepsStructure)
	{
		// The original <Hn> tag send from Word is something like this: <Hn style="margin-top:0px;margin-bottom:0px">
		html = html.replace( /<H(\d)([^>]*)>/gi, '<h$1>' ) ;

		// Word likes to insert extra <font> tags, when using MSIE. (Wierd).
		html = html.replace( /<(H\d)><FONT[^>]*>([\s\S]*?)<\/FONT><\/\1>/gi, '<$1>$2<\/$1>' );
		html = html.replace( /<(H\d)><EM>([\s\S]*?)<\/EM><\/\1>/gi, '<$1>$2<\/$1>' );
	}
	else
	{
		html = html.replace( /<H1([^>]*)>/gi, '<div$1><b><font size="6">' ) ;
		html = html.replace( /<H2([^>]*)>/gi, '<div$1><b><font size="5">' ) ;
		html = html.replace( /<H3([^>]*)>/gi, '<div$1><b><font size="4">' ) ;
		html = html.replace( /<H4([^>]*)>/gi, '<div$1><b><font size="3">' ) ;
		html = html.replace( /<H5([^>]*)>/gi, '<div$1><b><font size="2">' ) ;
		html = html.replace( /<H6([^>]*)>/gi, '<div$1><b><font size="1">' ) ;

		html = html.replace( /<\/H\d>/gi, '<\/font><\/b><\/div>' ) ;

		// Transform <P> to <DIV>
		var re = new RegExp( '(<P)([^>]*>[\\s\\S]*?)(<\/P>)', 'gi' ) ;	// Different because of a IE 5.0 error
		html = html.replace( re, '<div$2<\/div>' ) ;

		// Remove empty tags (three times, just to be sure).
		// This also removes any empty anchor
		html = html.replace( /<([^\s>]+)(\s[^>]*)?>\s*<\/\1>/g, '' ) ;
		html = html.replace( /<([^\s>]+)(\s[^>]*)?>\s*<\/\1>/g, '' ) ;
		html = html.replace( /<([^\s>]+)(\s[^>]*)?>\s*<\/\1>/g, '' ) ;
	}
	
	// YZQ - Start
	// Remove empty tags (three times, just to be sure).
	// This also removes any empty anchor
	html = html.replace( /<([^\s>]+)(\s[^>]*)?>\s*<\/\1>/g, '' ) ;
	html = html.replace( /<([^\s>]+)(\s[^>]*)?>\s*<\/\1>/g, '' ) ;
	html = html.replace( /<([^\s>]+)(\s[^>]*)?>\s*<\/\1>/g, '' ) ;
		
	// remove all &nbsp;
	html = html.replace(/\&nbsp;/g, " ");
	// remove 
	// YZQ - End

	return html ;
}

// ----------- Import End ---------------