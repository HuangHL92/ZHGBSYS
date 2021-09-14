/**
 * ��������������
 * @param {Object} id
 */
var bigGrid = function(id,config,data){
	this.id = id + "_BG";   //����Ĭ��ID
	this.config = config;   // {width:800,title:"��Ա��Ϣ���",head:[{name:"name",title:"����",width:150}]}
	this.data = data;       // [{name:"jjjj",txt:""}]
	this.width = "100%";    //���Ĭ�Ͽ��
	this.height = null;     //���Ĭ�ϸ߶�  ��
	this.head = [];         //���ͷ��Ϣ
	this.applyTo = id;      //���Ĭ����ʾ���ڵ�Ԫ��ID
	this.maxlength = 10000, //֧�ֵ�������ݳ��� Ĭ��һ��
	this.init();
}
/**
 * ��������
 */
var BGComm = {
	show:function(){
		var container = document.getElementById(this.applyTo);
		var html = "<div style=\"width:"+this.width+" ;"+(this.height!=null?("height:"+this.height):"")+";overflow: auto\">";
		html += '<table id="'+this.id+'_bg" width="'+this.width+'"  cellpadding="0" cellspacing="1" \
					style="border: 1px #89C3E4 solid ; background-color: #FFFFFF;">';
		if(this.data && this.data.length>0){
			//head
			var l = this.head.length;
			if(l>0){
				html += "<tr>";
				for(var i = 0;i<l;i++){
					var h = this.head[i];
					html += "<td ";
					for(o in h){
						if(o!="title" && o!="name"){
							html += o+"='"+h[o]+"' ";
						}
					}
					html += " style='background-color: #89C3E4; color: #FFFFFF; font-size: 12px; \
						font-weight:bold; height: 25px; line-height:25px; letter-spacing:0.2mm;'>" + h.title + "</td>";
				}
				html += "</tr>";
			}
			var datalen = this.data.length;
			if(datalen>this.maxlength){
				datalen = this.maxlength;
			}
			for(var i=0;i<datalen;i++){
				html += "<tr>";
				var dataitem = this.data[i];
				for(var j = 0;j<l;j++){
					var h = this.head[j];
					html += "<td ";
					for(o in h){
						if(o!="title" && o!="name"){
							html += o+"='"+h[o]+"' ";
						}
					}
					html += " style='background-color:#E9F5F5; color:#333333'>" + dataitem[h["name"]] + "</td>";
				}
				html += "</tr>";
			}
		}
		html += "</table>";
		html += "</div>";
		container.innerHTML = html;
	},
	init:function(){
		if (this.config.width) {
			this.width = this.config.width;
		}
		if (this.config.height) {
			this.height = this.config.height;
		}
		if (this.config.maxlength) {
			this.maxlength = this.config.maxlength;
		}
		this.head = this.config.head;	
	}
}
odin.ext.override(bigGrid,BGComm);


