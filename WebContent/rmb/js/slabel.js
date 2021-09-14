function rzzyjlClick(){
    var name = document.getElementById("a0101").value;
    var Id = document.getElementById("a0000").value;
    $h.openPageModeWin('szA0193ZTags','pages.zj.slabel.A0193TagsAddPage','历任(含现任)重要职务重要经历标志',810,580,Id,ctxPath);
}

function sxlyClick(){
    var name = document.getElementById("a0101").value;
    var Id = document.getElementById("a0000").value;
    $h.openPageModeWin('szA0194ZTags','pages.zj.slabel.A0194TagsAddPage','熟悉领域',810,550,Id,ctxPath);
}

function sbjysjlClick(){
    var name = document.getElementById("a0101").value;
    var Id = document.getElementById("a0000").value;
    $h.openPageModeWin('szTagSbjysjlAddPage','pages.zj.slabel.TagSbjysjlAddPage','省部级以上奖励',380,360,Id,ctxPath);
}


function rclxClick(){
    var name = document.getElementById("a0101").value;
    var Id = document.getElementById("a0000").value;
    $h.openPageModeWin('szTagRclxAddPage','pages.zj.slabel.TagRclxAddPage','人才类型',580,410,Id,ctxPath);
}

function cjlxClick(){
    var name = document.getElementById("a0101").value;
    var Id = document.getElementById("a0000").value;
    $h.openPageModeWin('szTagCjlxAddPage','pages.zj.slabel.TagCjlxAddPage','惩戒类型',580,410,Id,ctxPath);
}


function kcclfjClick(){

    var name = document.getElementById("a0101").value;
    var Id = document.getElementById("a0000").value;
    $h.openPageModeWin('szTagKcclfjAddPage2','pages.zj.slabel.TagKcclfjAddPage2','考察材料附件',820,450,Id,ctxPath);
}
function ndkhdjfjClick(){

    var name = document.getElementById("a0101").value;
    var Id = document.getElementById("a0000").value;
    $h.openPageModeWin('szTagNdkhdjbfjAddPage','pages.zj.slabel.TagNdkhdjbfjAddPage','年度考核登记表附件',820,450,Id,ctxPath);
}

function dazsfjClick(){

    var name = document.getElementById("a0101").value;
    var Id = document.getElementById("a0000").value;
    $h.openPageModeWin('szTagDazsbfjAddPage','pages.zj.slabel.TagDazsbfjAddPage','档案专审表附件',820,450,Id,ctxPath);
}

Ext.onReady(function(){
	//标签用
    //var tagNdkhdjbfj0id = '<%=SV(tagNdkhdjbfj0.getTagid()) %>';
    //if(null != tagNdkhdjbfj0id && tagNdkhdjbfj0id != 'null' && tagNdkhdjbfj0id != ''){
    //    document.getElementById("tagNdkhdjbfj0id").style.display="block";
    //}
    //var tagNdkhdjbfj1id = '<%=SV(tagNdkhdjbfj1.getTagid()) %>';
    //if(null != tagNdkhdjbfj1id && tagNdkhdjbfj1id != 'null' && tagNdkhdjbfj1id != ''){
    //    document.getElementById("tagNdkhdjbfj1id").style.display="block";
    //}
    //var tagKcclfj20id = '<%=SV(tagKcclfj20.getTagid()) %>';
    //if(null != tagKcclfj20id && tagKcclfj20id != 'null' && tagKcclfj20id != ''){
    //    document.getElementById("tagKcclfj20id").style.display="block";
    //}
    //var tagKcclfj21id = '<%=SV(tagKcclfj21.getTagid()) %>';
    //if(null != tagKcclfj21id && tagKcclfj21id != 'null' && tagKcclfj21id != ''){
    //    document.getElementById("tagKcclfj21id").style.display="block";
    //}

    //var tagDazsbfj0id = '<%=SV(tagDazsbfj0.getTagid()) %>';
    //if(null != tagDazsbfj0id && tagDazsbfj0id != 'null' && tagDazsbfj0id != ''){
   //     document.getElementById("tagDazsbfj0id").style.display="block";
   // }
   // var tagDazsbfj1id = '<%=SV(tagDazsbfj1.getTagid()) %>';
   // if(null != tagDazsbfj1id && tagDazsbfj1id != 'null' && tagDazsbfj1id != ''){
   //     document.getElementById("tagDazsbfj1id").style.display="block";
   // }
})