var beginnum=-1;
var endnum=-1;
var index=0;
 function checkhorizon1(th){
	 var name=th.name;
	 var fid = document.getElementById('tree1');
     var box = fid.getElementsByTagName('input');
     var type2 =  document.getElementById('type4');
     var type1 =  document.getElementById('type3');
     if(type2.checked){
    	 for (var i = 0; i < box.length; i++) {
             if(box[i].id==th.id){
           	  if(index==0){
           		  beginnum=i;
           	  }
           	  if(index==1){
           		  endnum=i;
           	  }
           	index++;
           	box[i].checked=true;
             }
         }
     }else{
    	 beginnum=-1;
    	 endnum=-1;
    	 index=0;
     } 
	 checkChild(th);
	 var a = th.id;
	 if(type1.checked==false&&type2.checked==false){
		 if(a.substr(a.length-1)=="0"){
			 if(th.checked==false){
				 for (var i = 0; i < box.length; i++) {
		             if(box[i].id==th.id){
		           	 	box[i+1].checked=false;
		             }
		         }
			 }
		 }
		 if(a.substr(a.length-1)=="1"){
			 if(th.checked==true){
				 for (var i = 0; i < box.length; i++) {
		             if(box[i].id==th.id){
		           	 	box[i-1].checked=true;
		             }
		         }
			 }
		 }
	 }
	 
	  
}
 function checkChild(th){
	 var type1 =  document.getElementById('type3');
	 var type2 =  document.getElementById('type4');
	 var str = '';
	 var start=0;
	 var end=0;
	 if(type1.checked){
		 var fid = document.getElementById('tree1');
	      var box = fid.getElementsByTagName('input');
	      for (var i = 0; i < box.length; i++) {
	          if (box[i].id ==th.id && box[i].name==th.name) {
	              start=i;
	              for(var j = i+1; j < box.length; j++){
	            	  if(box[j].name==th.name){
	            		  end=j;
	            		  break;
	            	  }
	              }
	          }
	      }
	      if(end==0){
	    	  end=start+1;
	      }
	      if(th.checked){
		 	for(var i = start; i < end; i++){
			 var id1 = th.id;
			 var type = id1.substr(id1.length-1);
			 if(type=="0"){
				 for(var i = start; i < end; i++){
					 var childtype = box[i].id;
					 if(childtype.substr(childtype.length-1)=="0"){
						 box[i].checked=true;
					 	}
					 }
			 }
			 if(type=="1"){
				 for(var i = start-1; i < end-1; i++){
					 var childtype = box[i].id;
					/*  if(childtype.substr(childtype.length-1)=="1"){ */
						 box[i].checked=true;
					 /* 	} */
					 }
			 }
		 	}
	     }else{
	    	 for(var i = start; i < end; i++){
	    		 var id1 = th.id;
				 var type = id1.substr(id1.length-1);
				 if(type=="0"){
					 for(var i = start; i < end; i++){

							 box[i].checked=false;

						 }
				 }
				 if(type=="1"){
					 for(var i = start-1; i < end-1; i++){
						 var childtype = box[i].id;
						 if(childtype.substr(childtype.length-1)=="1"){
							 box[i].checked=false;
						 	}
						 }
				 }
			 }
	     }
	 }
	 if(type2.checked){
		 var fid = document.getElementById('tree1');
	     var box = fid.getElementsByTagName('input');
		 if(beginnum!=-1&&endnum!=-1){
			 if(beginnum>endnum){
				 var t =beginnum;
				 beginnum=endnum;
				 endnum=t;
			 }
			 var childtype1 = box[beginnum].id;
			 var childtype2 = box[endnum].id;
			 if(childtype1.substr(childtype1.length-1)==childtype2.substr(childtype2.length-1)){
				 if(childtype1.substr(childtype1.length-1)=="0"){
					 for(var i = beginnum; i < endnum+1; i++){
						 var childtype = box[i].id;
						 if(childtype.substr(childtype.length-1)=="0"){
							 box[i].checked=true;
						 	} 
					 }
				 }
				 if(childtype1.substr(childtype1.length-1)=="1"){
					 for(var i = beginnum-1; i < endnum+1; i++){
							 box[i].checked=true;
					 }
				 }
				 beginnum=-1;
				 endnum=-1;
				 index=0;
			 }
		 }
	 }
	 
 }
 function dogant(){
	 var fid = document.getElementById('tree1');
     var box = fid.getElementsByTagName('input');
	 var count = 0;
     var result = '';
     for (var i = 0; i < box.length; i++) {
         if (box[i].type == 'checkbox' && box[i].checked) {
             result = result + box[i].id + ',';
         }
     }
    ids=result;
	 radow.doEvent('dogrant',ids);
 }
 function changeType1(){
	 var type1 =  document.getElementById('type3');
	 var type2 =  document.getElementById('type4');
	 if(type1.checked){
		 type2.checked=false;
	 }
 }
 function changeType2(){
	 var type1 =  document.getElementById('type3');
	 var type2 =  document.getElementById('type4');
	 if(type2.checked){
		 type1.checked=false;
	 }
 }