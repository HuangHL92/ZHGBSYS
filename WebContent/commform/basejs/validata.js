function outcheck(check_value){
	if(check_value != ""){
		alert(check_value)
		return false; 
	}
	return true;
}

function checkvalue(obj, low, up, mode, lable){
/*
Mode = req ����Ƿ�Ϊ��   num�Ƿ������� int�Ƿ�����
eng�Ƿ���Ϊ���֡���ĸ��_.-

len ���ȼ��

*/  
    var temp,type;
    var length, i, base, str;
    
    str=getformvalue(obj);
    if(str==null){
		lenght=0;
		str="";
	}	
	else{	
		length = str.length
	}	
    temp=""
    if( mode.indexOf("req") != -1 ){
        if( !str.match(/\S/)&&(str == ""||str.match(/\s/))){
            temp = temp + "��" + lable + "��" + "����Ϊ�գ�" + "\n";
        }
    }
    
    if( mode.indexOf("num") != -1 ){
        base = "0123456789."
        for(i = 0;i<=length-1;i++)
            if( base.indexOf(str.substring(i, i+1)) == -1  ){
				temp = temp + "��" + lable + "��" + "���������֣�" + "\n";
				break;
            }    
    }
    
    if( mode.indexOf("tel") != -1 ){
       var pattern=/(^[0-9]{3,4}\-[0-9]{3,8}$)|(^[0-9]{3,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)|(^0{0,1}15[0-9]{9}$)/
       if(pattern.test(str)==false){
			temp = temp + "��" + lable + "��" + "�����ǵ绰��ʽ��" + "\n";
       }
       
		
            
    }
    
    if( mode.indexOf("int") != -1 ){
        base = "0123456789"
        for(i = 0;i<=length-1;i++)
            if( base.indexOf(str.substring(i, i+1)) == -1  ){
                temp = temp + "��" + lable + "��" + "������������" + "\n";
                break;
            }    
    }
    
    if( mode.indexOf("eng") != -1 ){
        base = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz0123456789_-.,.����������"
        for(i = 0;i<=length-1;i++)
            if( base.indexOf(str.substring(i, i+1)) == -1  ){
                temp = temp + "��" + lable + "��" + "�����Ƿ��ַ�����ֻ������ĸ�����ֺ͡�- _ .����" + "\n";
                break;
            }
    }
    
    if( mode.indexOf("gbk") != -1 ){
		base = "~!*#+=;'?@#$%\\/-[]{}";
		for(i = 0;i<=length-1;i++){
			if( base.indexOf(str.substring(i, i+1)) != -1  ){
				temp=temp+"��"+lable+"��"+"���ܺ���~!*#+=;'?@#$%\\/-[]{}�������ַ�!";
				break;
			}
		}
				
	}

	if(mode.indexOf("spe")!=-1){
		base = "~!*#+=;'?@#$%\\/-[]{}";
		for(i = 0;i<=length-1;i++){
			if( base.indexOf(str.substring(i, i+1)) != -1  ){
				temp=temp+"��"+lable+"��"+"���ܺ���~!*#+=;'?@#$%\\/-[]{}�������ַ�!";
				break;
			}
		}
				
	}
	 if( mode.indexOf("date") != -1 ){
		var reg = /\d{4}-\d{2}-\d{2}/;
		if(!reg.test(str)||length!=10)
			temp = temp + "��" + lable + "��" + "���ǺϷ������ڸ�ʽ!";
    }

	if( mode.indexOf("noch") != -1 ){
		if(str.match(/^[^\x00-\x7f]+$/))
			temp = temp + "��" + lable + "��" + "����������";
    }
    if( mode.indexOf("len") != -1 ){
        if( ! (length >= low && length <= up||length=="") ){
               temp = temp + "��" + lable + "��" + "�ĳ��ȱ�����" + low + "��" + up + "֮�䣡" + "\n";
        }
    }
    
     if( mode.indexOf("much") != -1 ){
        if( ! (parseInt(str) >= parseInt(low) && parseInt(str) <= parseInt(up)) ){
               temp = temp + "��" + lable + "��" + "������" + low + "��" + up + "֮�䣡" + "\n";
        }

    }
	if( mode.indexOf("eml") != -1 ){
		if(!validateEmail(str))
			temp = temp + "��" + lable + "��" + "�������ʼ���ʽ";
	}
	if( mode.indexOf("currency") != -1 ){
		var re=/^\-?[0-9]*\.+[0-9]{2}$/;
		if(!re.test(str))
			temp = temp + "��" + lable + "��" + "��ȷ���Ҹ�ʽΪС���������λ����($2345.00)";
	}
	if( mode.indexOf("float") != -1 ){
		var re=/^\-?[0-9]*\.+[0-9]*$/;
		if(!re.test(str))
			temp = temp + "��" + lable + "��" + "����Ϊ������";
	}
    if(temp!=""){
    	odin.alert(temp);
    	type=(getformtype(obj));
    	if(type!="radio" && type!="checkbox"){
    		obj.focus();
			if(type!="select-one")//���Ե�ʱ����89��
			obj.select();
    	}
	return false; 
   }
   return true;
    
}

function getformtype(obj){
	var type;
	type=obj.type;
	if(typeof(type)=="undefined"){

		type=obj[0].type;
	}
	return type;		
}
function getformvalue(input){
//ȡ�����ֵ
	var type,temp;
	temp="";
	
	type=getformtype(input);	

	switch(type){
		case "radio":	//��ѡ��
			n=input.length-1;

			if(isNaN(n)==true){
				if(input.checked == true){
					temp = input.value;
				}else{
					temp = "";
				}	
			}else{
				for(i=0;i<=n;i++){
					if(input[i].checked == true){
						return(input[i].value);
					}
				}
				break;
			}
			case "checkbox":	//��ѡ��
			n=input.length-1;
			if(isNaN(n)==true){
				if(input.checked == true){
					temp = input.value;
				}else{
					temp = "";
				}	
			}else{
				for(i=0;i<=n;i++){
					if(input[i].checked == true){
						if(temp!=""){
							temp += ",";
						}
						temp += input[i].value;

					}	
				}
			}
			return(temp);
			break;
			
		case "select-one" :	//��ѡ�б��
			n=input.length-1;	
			for(i=0;i<=n;i++){
				if(input.options[i].selected == true){
					temp = input.options[i].value;
					break;
				}			
			}
			return(temp);
			break;				
		case "select-multiple":	//��ѡ�б��
			n=input.length-1;	
			for(i=0;i<=n;i++){
				if(input.options[i].selected == true){
					if(temp!=""){
						temp+=",";
					}					
					temp+=input.options[i].value;
				}			
			}
			return(temp);
			break;			
		default:				//����
			return(input.value);
			break;
	
	}
	//alert("dd"+input.value);
	return(input.value);

}

function ischecked(group,value){
	var i,n;
	n=group.length-1;
	for(i=0;i<=n;i++){
		if(value==group[i]){
			return true;			
		}
	}
	return false;
}


function SetSelectedAndChecked(input,value){
//���ñ����ѡ��
	var type,temp,i,n;
	var split_value = new Array();
	temp="";
	
	type=input.type;
	
	if(typeof(type)=="undefined"){
		type=input[0].type;
	}
	

	switch(type){
		case "radio":	//��ѡ��
			n=input.length-1;

			if(isNaN(n)==true){
				if(input.value = value){
					input.checked = true;
				}else{
					input.checked = false;
				}	
			}else{
				for(i=0;i<=n;i++){
					if(input[i].value == value){
						input[i].checked = true;
					}else{
						input[i].checked = false;					
					}
				}
			}
			break;

		case "checkbox":	//��ѡ��
			n=input.length-1;
			split_value=value.split(",");
			if(isNaN(n)==true){
				if(ischecked(split_value,input.value)){
					input.checked = true;
				}else{
					input.checked = false;
				}	
			}else{
				for(i=0;i<=n;i++){
					if(ischecked(split_value,input[i].value)){
						input[i].checked = true;
					}else{
						input[i].checked = false;					
					}					
				}
				
			}
			break;
			
		case "select-one" :	//��ѡ�б��
			n=input.options.length-1;	
			for(i=0;i<=n;i++){
				if(input.options[i].value == value){
					input.options[i].selected = true;
				}else{
					input.options[i].selected = false;				
				}
						
			}
			break;				
		case "select-multiple":	//��ѡ�б��
			n=input.options.length-1;	
			split_value=value.split(",");
			for(i=0;i<=n;i++){
				if(ischecked(split_value,input.options[i].value)){
						input.options[i].selected = true;
				}else{
						input.options[i].selected = false;				
				}			
			}
			break;			
		default:				//����
			return false;
			break;
	
	}
	
	return true;

}

function validateEmail(email)
{
// a very simple email validation checking. 
// you can add more complex email checking if it helps 
    var splitted = email.match("^(.+)@(.+)$");
    if(splitted == null) return false;
    if(splitted[1] != null )
    {
      var regexp_user=/^\"?[\w-_\.]*\"?$/;
      if(splitted[1].match(regexp_user) == null) return false;
    }
    if(splitted[2] != null)
    {
      var regexp_domain=/^[\w-\.]*\.[A-Za-z]{2,4}$/;
      if(splitted[2].match(regexp_domain) == null) 
      {
	    var regexp_ip =/^\[\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\]$/;
	    if(splitted[2].match(regexp_ip) == null) return false;
      }// if
      return true;
    }
return false;
}



//	if (!checkvalue(form1.S1,0,300,"req+len","����Ҫ��")) return false;
//	//if (!checkvalue(form1.Tt1,0,0,"req","������ʼ����")) return false;
//	if (!checkvalue(form1.worksum,0,0,"num","����������")) return false;
//	if (!checkvalue(form1.Tt2,0,0,"req","Ҫ���������")) return;


//function add_onsubmit(add) {
///* ������֤��������ʱ��
//if(add.password.value!=add.repassword.value){
//	alert('�������벻��ͬ!');
//	return false;
//}
//*/
//
////�ͻ�����֤
////if (!checkvalue(add.name,0,0,1,"����")) return false;
////if (!checkvalue(add.password,0,0,1,"����")) return false;
////if (!checkvalue(add.unitname,0,0,1,"��λ����")) return false;
////if (!checkvalue(add.lianxiren,0,0,1,"��ϵ��")) return false;
////if (!checkvalue(add.tel,0,0,1,"�绰")) return false;
////if (!checkvalue(add.valid,0,0,1,"����")) return false;
//
//
//}
////-->
//</SCRIPT>