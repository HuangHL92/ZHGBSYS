//ѧ��ѧλ��Ϣ��
function saveDegree(){
	//10��������𣺱�����д��
	var a0837_combo = document.getElementById("a0837_combo").value;
	if(!a0837_combo){
		odin.alert("������𲻿�Ϊ�գ�");
		return;
	}
	//2��ѧ�����룺������д��
	var a0801b_combo = document.getElementById("a0801b_combo").value;
	/*if(!a0801b_combo){
		odin.alert("ѧ�����벻��Ϊ�գ�");
		return;
	}*/
	//1��ѧ�����ƣ�������д�Ҳ������пո�������Ǻ����ַ���
	var a0801a = document.getElementById("a0801a").value;
	/*if(!a0801a){
		odin.alert("ѧ�����Ʋ���Ϊ�գ�");
		return;
	}*/
	if (a0801a.indexOf(" ") >=0){
		odin.alert("ѧ�����Ʋ��ܰ����ո�");
		return;
	}
   /* if(!(/^[\u3220-\uFA29]+$/.test(a0801a))&&a0801a!=""){
    	odin.alert("ѧ�����Ʋ��ܰ����Ǻ����ַ���");
		return;
    }*/
	//3��ѧλ���ƣ�����ʵ�������д�Ҳ������пո�������Ǻ����ַ���
	var a0901a = document.getElementById("a0901a").value;
	if(a0901a){
		if (a0901a.indexOf(" ") >=0){
			odin.alert("ѧλ���Ʋ��ܰ����ո�");
			return;
		}
	   /* if(!(/^[\u3220-\uFA29]+$/.test(a0901a))){
	    	odin.alert("ѧλ���Ʋ��ܰ����Ǻ����ַ���");
			return;
	    }*/
	}
	//4��ѧλ���룺���Ѿ���дѧλ���ƣ���ѧλ���������д��
    if(a0901a&&a0901a!='��'){
    	var a0901b_combo = document.getElementById("a0901b_combo").value;
    	if(!a0901b_combo){
    		odin.alert("������дѧλ���ƣ�ѧλ����Ҳ������д��");
    		return;
    	}
    }
    if(a0901a=='��'){
    	document.getElementById("a0901a").value='';
    }
	//5����ѧ���ڣ���������ڱȽϣ�Ӧ���ڳ������ڡ�(��̨�ж�)
	//6���ϣ��ޣ�ҵ���ڣ�����������ѧ���ڡ�(��̨�ж�)
	//7��ѧλ�������ڣ�����������ѧ���ڡ�(��̨�ж�)
	
	//8��ѧУ����λ�����ƣ����ѧ����дΪ��ר�����ϣ���ѧУ���Ʊ�����д��
    if(a0801b_combo!='��ר' && a0801b_combo!='�м�' && a0801b_combo!='����' && a0801b_combo!='����' && a0801b_combo!='Сѧ' && a0801b_combo!='����'&&a0801b_combo!=''){
    	var a0814 = document.getElementById("a0814").value;
    	if(!a0814){
    		odin.alert("����ѧ�����ڴ�ר�����ϣ����ѧУ����Ҳ������д��");
    		return;
    	}
    }
	//9����ѧרҵ���ƣ����ѧ����дΪ��ר�����ϣ�����ѧרҵ���Ʊ�����д��
    if(a0801b_combo!='��ר' && a0801b_combo!='�м�' && a0801b_combo!='����' && a0801b_combo!='����' && a0801b_combo!='Сѧ' && a0801b_combo!='����'&&a0801b_combo!=''){
    	var a0824 = document.getElementById("a0824").value;
    	if(!a0824){
    		odin.alert("����ѧ�����ڴ�ר�����ϣ������ѧרҵ����Ҳ������д��");
    		return;
    	}
    }
	//11��ѧ�����ޣ�������0.5�����������֡�
	var a0811 = document.getElementById("a0811").value;
	if(a0811%0.5!=0){
		odin.alert("ѧ�������������󣬼���Ƿ���0.5�ı�����");
		return;
	}
	//12���ж���ѧרҵ�������Ƿ���רҵ����
	var a0824_1 = document.getElementById("a0824").value;
	/*if(a0824_1.match("רҵ")){
		$h.confirm("��ѧרҵ�����к���'רҵ'����,�Ƿ��������?",400,function(id){
			if("ok"==id){
				radow.doEvent("saveA08")
			}else{
				return;
			}
		})
		
	}*/
	radow.doEvent("saveA08");
}

//������Ϣ��
function saveReward(){
	//1���������ƣ�������д�����û�н�������Ҫ��д���ޡ���
	
	//2�����ʹ��룺����Ҫ��д��������ʱ,��׼���ڡ�������׼�������ơ�����ʱְ���Ρ���׼���������������д��
	var a1404b_combo = document.getElementById("a1404b_combo").value;
	if(a1404b_combo){
		/*var a1415_combo = document.getElementById("a1415_combo").value;//����ʱְ����
		if(!a1415_combo){
			odin.alert("����ʱְ���β���Ϊ�գ�");
			return;
		}*/
		var a1428_combo = document.getElementById("a1428_combo").value;//��׼��������
		/*if(!a1428_combo){
			odin.alert("��׼�������ʲ���Ϊ�գ�");
			return;
		}*/
		var a1411a = document.getElementById("a1411a").value;//������׼��������
		if(!a1411a){
			odin.alert("��׼���ز���Ϊ�գ�");
			return;
		}
		var a1407_1 = document.getElementById("a1407_1").value;//��׼����
		if(!a1407_1){
			odin.alert("��׼���ڲ���Ϊ�գ�");
			return;
		}
		
		//�������ڵ�ǰʱ��
		var now = new Date();
		var nowTime = now.toLocaleDateString();
		var year = nowTime.substring(0 , 4);//��
		var MonthIndex = nowTime.indexOf("��");
		var mon = nowTime.substring(5 , MonthIndex);//��
		var day = nowTime.substring(MonthIndex+1,nowTime.length-1);//��
		if(mon.length == 1){
			mon = "0" + mon;
		}
		if(day.length == 1){
			day = "0" + day;
		}
		
		nowTime = year + mon + day;//��ȡ��λ����ʱ���ַ���
		
		var time = document.getElementById("a1407").value;//a1407
		if(time.length == 6){
			time = time + "01";
		}
		if(parseInt(time) > parseInt(nowTime)){
			odin.alert("��׼���ڲ�������ϵͳ��ǰʱ��");
			return;
		}
		
		var time2 = document.getElementById("a1424").value;;//a1424
		if(time2.length == 6){
			time2 = time2 + "01";
		}
		if(parseInt(time2) > parseInt(nowTime)){
			odin.alert("���ͳ������ڲ�������ϵͳ��ǰʱ��");
			return;
		}
	}
	//3��������׼���ڣ���μӹ���ʱ��Ƚϣ�Ӧ���ڲμӹ���ʱ�䡣
	//4������ʱְ���Σ���д����ʱְ����һ��Ҫ���ڻ���ڱ��˵���ְ���Σ�����ǳͷ�����Ը�����ְ���Ρ�
	
	//5�����ͳ������ڣ����ͳ�������Ӧ���ڽ�����׼���ڡ�
	
	
	//����������������������������Ӣ�ĵ�����
	var a14z101 = document.getElementById("a14z101").value;
	var index = a14z101.indexOf("'")
	if(index != -1){
		odin.alert("������������������Ӣ�ĵ�����");
		return;
	}
	
	
	radow.doEvent("saveA14");
}

//������Ϣ��
function saveAssess(){
	//1�����˽��ۣ�����д���˽������ʱ�����ѡȡ�����ʹ����ڼ���ȿ��˲�ȷ���ȴεģ�����Ҫ��ʵ������Ϣ���Ƿ��гͷ���¼��
	//2��������ȣ���д�������ʱ����Ҫ����Ϊ��λ�����ڲμӹ���ʱ�䡣
	radow.doEvent("save");
}

//���������Ϣ��
function saveEntry(){
	//1�����뱾��λ���ڣ���������ڽ��бȽϣ�һ��Ӧ����18���ꡣ
	
	//�������ڵ�ǰʱ��
	var now = new Date();
	var nowTime = now.toLocaleDateString();
	var year = nowTime.substring(0 , 4);//��
	var MonthIndex = nowTime.indexOf("��");
	var mon = nowTime.substring(5 , MonthIndex);//��
	var day = nowTime.substring(MonthIndex+1,nowTime.length-1);//��
	if(mon.length == 1){
		mon = "0" + mon;
	}
	if(day.length == 1){
		day = "0" + day;
	}
	
	nowTime = year + mon + day;//��ȡ��λ����ʱ���ַ���
	
	var time = document.getElementById("a2907").value;;
	if(time.length == 6){
		time = time + "01";
	}
	if(parseInt(time) > parseInt(nowTime)){
		odin.alert("���뱾��λ���ڲ�������ϵͳ��ǰʱ��");
		return;
	}
	
	var time2 = document.getElementById("a2949").value;;
	if(time2.length == 6){
		time2 = time2 + "01";
	}
	if(parseInt(time2) > parseInt(nowTime)){
		odin.alert("����Ա�Ǽ�ʱ�䲻������ϵͳ��ǰʱ��");
		return;
	}
	
	//2�����뱾��λ�䶯��𣺱�����д��
	var a2911_combo = document.getElementById("a2911_combo").value;
	if(!a2911_combo){
		odin.alert("���뱾��λ�䶯��𲻿�Ϊ�գ�");
		return;
	}
	radow.doEvent("save");
}

//�˳���Ϣ��
function saveLogout(){
	//1���˳�����ʽ��������д��
	var a3001_combo = document.getElementById("a3001_combo").value;
	if(!a3001_combo){
		odin.alert("�˳�����ʽ����Ϊ�գ�");
		return;
	}
	//2���˳�����λ���ڣ�Ӧ���ڲμӹ���ʱ�䡣
	
	//�������ڵ�ǰʱ��
	var now = new Date();
	var nowTime = now.toLocaleDateString();
	var year = nowTime.substring(0 , 4);//��
	var MonthIndex = nowTime.indexOf("��");
	var mon = nowTime.substring(5 , MonthIndex);//��
	var day = nowTime.substring(MonthIndex+1,nowTime.length-1);//��
	if(mon.length == 1){
		mon = "0" + mon;
	}
	if(day.length == 1){
		day = "0" + day;
	}
	
	nowTime = year + mon + day;//��ȡ��λ����ʱ���ַ���
	
	var time = document.getElementById("a3004").value;;
	if(time.length == 6){
		time = time + "01";
	}
	if(parseInt(time) > parseInt(nowTime)){
		odin.alert("�˳�����ʱ�䲻������ϵͳ��ǰʱ��");
		return;
	}
	
	
	radow.doEvent("save");

}