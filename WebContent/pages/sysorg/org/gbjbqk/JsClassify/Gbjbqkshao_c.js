// //ͳ����Ŀ����
	var tjxm_arr = new Array(
			"",
			"",
			"",
			"",
			"Ů",
			"",
			/* "��������",
			"", */
			"�й���Ա",
			"",
			"���й���Ա",
			"",
			"�о���",
			"",
			"��ѧ����",
			"",
			"��ѧר��",
			"",
			"��ר",
			"",
			"���м�����",
			"",
			"��ʿ",
			"",
			"˶ʿ",
			"",
			"ѧʿ",
			"",
			"35�꼰����",
			"",
			"36����40��",
			"",
			"41����45��",
			"",
			"46����50��",
			"",
			"51����54��",
			"",
			"55����59��",
			"",
			"60�꼰����",
			"",
			"ƽ������",
			"����1��",
			"",
			"1��������3��",
			"",
			"3��������5��",
			"",
			"5��������10��",
			"",
			"10�꼰����",
			"",
			"����2�����ϻ��㹤������"
			);

	Ext.onReady(function openfile(){
		    try{
				document.getElementById("DCellWeb1").openfile(ctpath+"\\template\\gbjbqktjshao.cll","");
		    }catch(err){
		    	//alert("��ʾ:�����ذ�װ������!��ʹ��IE�����(����ģʽ)��(����ϵϵͳ����ԱԶ��Э��)!");
		    }
	   });
	//���json����
	var json_obj="";
	//��ʼ������ 
	var DCellWeb_tj="";
	var col_xho_start=4;
	var col_xho_end=36+4;
	var col_xhj_start=37+4;
	var col_xhj_end=47+4;
	//json�ַ���
   function json_func(){
	   //jsonstr,lengthstr
	   var jsonstr=document.getElementById("jsonString_str").value;
	   DCellWeb_tj=document.getElementById("DCellWeb1");//��ȡ������
	   var obj ="";
	   try{
		   obj = eval('(' + jsonstr + ')');
	   }catch(err){
	   }
	   json_obj=obj;
	   DCellWeb_tj.SetFixedCol(1, 2); 
	   DCellWeb_tj.SetFixedRow(1, 3);
	   for(var i=0;i<obj.length;i++){////�������� ���� ѭ��
	   	   var a0221=obj[i].a0221;//����
	   	   if(a0221=='undefined' || a0221=="" || a0221==null||a0221==" "){
	   			continue;//������Ϊ��
	   	   }
	   	   a0221=map[a0221];//����
	   	   if(a0221=='undefined' || a0221=="" || a0221==null||a0221==" "){
	   			continue;//js û�����ô�������
	   	   }
	   	   var i_col=4;
	   		DCellWeb_tj.SetCellString(
	   				i_col,a0221,0,obj[i].nv==0?"":obj[i].nv);//Ů
		   	/* DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].sm==0?"":obj[i].sm);//�������� */
		   i_col=i_col+2;		
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].zd==0?"":obj[i].zd);//�й���Ա
		   	i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].fzd==0?"":obj[i].fzd);//���й���Ա
		   	i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].yjs==0?"":obj[i].yjs);//�о���
		   	i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].dxbk==0?"":obj[i].dxbk);//��ѧ����
		   			i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].dxzz==0?"":obj[i].dxzz);//��ѧר��
  		   	i_col=i_col+2;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].zz==0?"":obj[i].zz);//��ר
  		   	i_col=i_col+2;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].gzjyx==0?"":obj[i].gzjyx);//���м�����
  		   	i_col=i_col+2;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].bs==0?"":obj[i].bs);//��ʿ
  		   	i_col=i_col+2;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].ss==0?"":obj[i].ss);//˶ʿ
  		   	i_col=i_col+2;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].xs==0?"":obj[i].xs);//ѧʿ
 		   	i_col=i_col+2;
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,obj[i].xydy35==0?"":obj[i].xydy35);//35�꼰����
			i_col=i_col+2;
			DCellWeb_tj.SetCellString(
					i_col,a0221,0,obj[i].dy36xy40==0?"":obj[i].dy36xy40);//36����40��
			i_col=i_col+2;
			DCellWeb_tj.SetCellString(
					i_col,a0221,0,obj[i].dy41xy45==0?"":obj[i].dy41xy45);//41����45��
			i_col=i_col+2;
			DCellWeb_tj.SetCellString(
					i_col,a0221,0,obj[i].dy46xy50==0?"":obj[i].dy46xy50);//46����50��
			i_col=i_col+2;
			DCellWeb_tj.SetCellString(
					i_col,a0221,0,obj[i].dy51xy54==0?"":obj[i].dy51xy54);//51����54��
			i_col=i_col+2;
			DCellWeb_tj.SetCellString(
					i_col,a0221,0,obj[i].dy55xy59==0?"":obj[i].dy55xy59);//55����59��
			i_col=i_col+2;
		    DCellWeb_tj.SetCellString(
		    		i_col,a0221,0,obj[i].dy60==0?"":obj[i].dy60);//60�꼰����
	   		i_col=i_col+2;
	   		DCellWeb_tj.SetCellString(
	   				i_col,a0221,0,obj[i].pjnl==0?"":obj[i].pjnl);//ƽ������
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].zhccxy1==0?"":obj[i].zhccxy1);//����1��
		   	i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].zhccxy3==0?"":obj[i].zhccxy3);//1��������3��
		   	i_col=i_col+2;
		    DCellWeb_tj.SetCellString(
		    		i_col,a0221,0,obj[i].zhccxy5==0?"":obj[i].zhccxy5);//3��������5��
		   	i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].zhccxy10==0?"":obj[i].zhccxy10);//5��������10��
		   	i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].zhccxy11==0?"":obj[i].zhccxy11);//10�꼰����
		   	i_col=i_col+2;		
		   			DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].a0197==0?"":obj[i].a0197);//�Ƿ�����������ϻ��㹤������
		   	//�ϼ�
		   	// �й���Ա
		   	var zg=obj[i].zd==0?"":obj[i].zd;
		   	if(zg=='undefined' || zg=="" || zg==null||zg==" "){
		   		zg=0;
		   	}
		   	//���й���Ա
		   	var fzg=obj[i].fzd==0?"":obj[i].fzd;
			if(fzg=='undefined' || fzg=="" || fzg==null||fzg==" "){
				fzg=0;
		   	}
			DCellWeb_tj.SetCellString(
		   			3,a0221,0, parseInt(zg)+parseInt(fzg));
		}/// for end
		//С��
	   for(var i=0;i<array_row.length;i++){
			total_tj(parseInt(array_row[i].split(",")[0])-1,
					parseInt(array_row[i].split(",")[0]),
					parseInt(array_row[i].split(",")[1]));
		}
		//�ܼ� 
		total_zj();
		
		//ռ��
		zhanbi();
		//����ռ��
		yincangzb();
		//���� ȫ��Ϊ0����
		displayzeroinit()
		//����ҳ�治�ɱ༭
		DCellWeb_tj.WorkbookReadonly=true;
		tjfx_dj_flag=true;//���ͳ�Ʒ�����־ 
		cwenzi();//���� 
		ctable();//��ʾ���
		document.getElementById( "bar_div").style.display= "block";//��ʾ������
		//��������grid�߶�
		try{
			//var grid = odin.ext.getCmp('gridfc');
			//grid.setHeight(487);
		}catch(err){
			
		}
		
		DCellWeb_tj.SetCellString(
				51,2,0,"����2�����ϻ��㹤������");
		//setTimeout(ctable,500);
   }