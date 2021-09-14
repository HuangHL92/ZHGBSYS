<!--TAble标签页 图片新闻 -->
function setTab(name,cursel,n){
for(i=1;i<=n;i++){
var menu=document.getElementById(name+i);
var con=document.getElementById("con_"+name+"_"+i);
menu.className=i==cursel?"hover":"";
con.style.display=i==cursel?"block":"none";
}
}
//-->
function NavBarMouseOver(obj,strPic){
  
  if (obj.Selected == "False"){   
    obj.src = strPic;
  }
}

function NavBarMouseOut(obj,strPic){

  if (obj.Selected == "False"){
    obj.src = strPic;
  }
}
