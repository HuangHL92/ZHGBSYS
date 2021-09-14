$(document).ready(function () {
    $(".content-title li").on("click",function(){
        switchAction($(this));
    });
    $(".chart-box-tab li").on("click",function(){
        switchAction($(this));
        switchBar($(this).text());
    });
    $(".nav-bar-center li").on("click",function(){
        switchAction($(this));
    });
    var mouseJob,mouseOutJob;
    $(".nav-bar-hover").mouseenter(function(){
        clearTimeout(mouseOutJob);
        mouseJob = setTimeout(function(){
            $(".nav-bar").animate({
                left:"0%"
            },500);
            $(".content").animate({
                left:"5%"
            },500);
            if($(".content").width() > 1150){
                $("body").css("overflow-x","hidden");
            }
            //resizeChart();
            //setTimeout('resizeChart();',500);
        },500);
    });
    $(".nav-bar").mouseleave(function(){
        clearTimeout(mouseJob);
        mouseOutJob = setTimeout(function() {
            $(".nav-bar").animate({
                left: "-5%"
            }, 500);
            $(".content").animate({
                left: "0%"
            }, 500);
            //resizeChart();
            setTimeout('$("body").css("overflow-x","auto");', 500);
        });
    });
    initPage();
});
$(window).resize(function () {
    resizeChart();
});
$(".content").resize(function(){
    resizeChart();
});

function initPage() {
    showMap2(null,"100000");
    //showBar(null);
    //showLine1();
    //showLine2();
    //setPeopleNum();
}

function setPeopleNum(val){
    var value = val.toString(10);
    alert(value);
    for(var i=0;i<value.length;i++){
        $(".content-total-number ul").append('<li>' + value[i] + '</li>')
    }
}

function resizeChart(){
    $(".chart").each(function (index,ele) {
        var eleid = ele.getAttribute("id");
        if (!!echarts.getInstanceByDom(document.getElementById(eleid))) {
            getChart(eleid).resize();
        }
    });
}

function switchAction(el) {
    $(el).siblings().removeClass("action");
    $(el).addClass("action");
    return true;
}

function getChart(eleID) {
    if(!echarts.getInstanceByDom(document.getElementById(eleID))) {
        return echarts.init(document.getElementById(eleID));
    } else {
        return echarts.getInstanceByDom(document.getElementById(eleID));
    }
}

function getArrValue(data, key) {
    var key = key || "value";
    var res = [];
    if(data){
        for(var i=0;i<data.length;i++){
            if(data[i]){
                res.push(data[i][key]);
            }
        }
    }
    return res;
}

/**
 * 以name为操作对象的合并地图数据方法
 * @param data
 * @param mapJson
 * @returns {*}
 */
function convertMapData(data, mapJson) {
    var mapArray = mapJson.features;
    var city = {};
    try{
        mapArray.forEach(function (t) {
            var n = t.properties;
            city[n.adcode || t.id] = {
                id: n.adcode || t.id,
                name: n.name,
                center: n.center || n.cp,
                //parent: n.parent.adcode
            };
        });
    }catch(e){
        return [[],[],[]];
    }
    var res = (data); //已指定数据区域
    for (var x = 0; x < res.length; x++) {
        try {
            var relValue = isNaN(res[x]["value"])?0:Number(res[x]["value"]);
            res[x]["value"] = relValue;//city[res[x].id].center?city[res[x].id].center.concat(res[x]["num"]):city[res[x].id].cp.concat(res[x]["num"]);
            //res[x]["id"] = city[res[x].id].id;
        } catch (e) {
            //console.error(e + "\n气泡数据与地图不匹配，可能由于切换地图导致。");
            continue;
        }
    }
    var res2 = []; //未指定数据区域
    for (var y in city) {
        var exist = false;
        var obj = {};
        for (var z = 0; z < res.length; z++) {
            if (res[z].id === y) {
                exist = true;
            }
        }
        if (!exist) {
            obj["name"] = city[y].name;
            obj["id"] = y;
            obj["value"] = 0;//city[y].center?city[y].center.concat(0):(city[y].cp?city[y].cp.concat(0):[]);
            //obj["num"] = 0;
            res2.push(obj);
        }
    }
    return res.concat(res2);
}


/**
 * 以id为操作对象的合并地图数据方法
 * @param data
 * @param mapJson
 * @returns {*}
 */
function convertMapData2(data, mapJson) {
    var mapArray = mapJson.features;
    var city = {};
    try{
        mapArray.forEach(function (t) {
            var n = t.properties;
            city[n.adcode || t.id] = {
                id: n.adcode || t.id,
                name: n.name,
                center: n.center || n.cp,
                //parent: n.parent.adcode
            };
        });
    }catch(e){
        return [[],[],[]];
    }
    var res = (data); //已指定数据区域
    for (var x = 0; x < res.length; x++) {
        try {
            var obj = city[res[x].id];
            var relValue = isNaN(res[x]["value"])?0:Number(res[x]["value"]);
            res[x]["value"] = relValue;//city[res[x].id].center?city[res[x].id].center.concat(res[x]["num"]):city[res[x].id].cp.concat(res[x]["num"]);
            res[x]["name"] = obj.name;
            res[x]["id"] = obj.id;
            //res[x]["id"] = city[res[x].id].id;
        } catch (e) {
            //console.error(e + "\n气泡数据与地图不匹配，可能由于切换地图导致。");
            continue;
        }
    }
    var res2 = []; //未指定数据区域
    for (var y in city) {
        var exist = false;
        var obj = {};
        for (var z = 0; z < res.length; z++) {
            if (res[z] && res[z].id === y) {
                exist = true;
            }
        }
        if (!exist) {
            obj["name"] = city[y].name;
            obj["id"] = y;
            obj["value"] = 0;//city[y].center?city[y].center.concat(0):(city[y].cp?city[y].cp.concat(0):[]);
            //obj["num"] = 0;
            res2.push(obj);
        }
    }
    var resArray = [];
    var tempArray = res.concat(res2);
    for(var i=0;i<tempArray.length;i++){
        if(tempArray[i]){
            resArray.push(tempArray[i])
        }
    }
    return resArray;
}

function distinct(arr) {
    var result = [];
    arr.forEach(function (v, i, arr) { //这里利用map，filter方法也可以实现
        var bool = arr.indexOf(v, i + 1); //从传入参数的下一个索引值开始寻找是否存在重复
        if (bool === -1) {
            result.push(v);
        }
    });
    return result;
}

function switchBar(name){
    var method = "";
    switch (name) {
        case "综合":
            method = "zh";
            break;
        case "警察":
            method = "jc";
            break;
        case "法检":
            method = "fj";
            break;
        default:
            return false;
    }
    /* ajax */
    showBar(null);
}

/**
 * 切换地图
 * @param obj{id}
 */
function switchMap(obj){
    /*ajax getdata*/
    showMap2(null,obj.id);
}



if (!('bind' in Function.prototype)) {
    Function.prototype.bind= function(owner) {
        var that= this;
        if (arguments.length<=1) {
            return function() {
                return that.apply(owner, arguments);
            };
        } else {
            var args= Array.prototype.slice.call(arguments, 1);
            return function() {
                return that.apply(owner, arguments.length===0? args : args.concat(Array.prototype.slice.call(arguments)));
            };
        }
    };
}

// Add ECMA262-5 string trim if not supported natively
//
if (!('trim' in String.prototype)) {
    String.prototype.trim= function() {
        return this.replace(/^\s+/, '').replace(/\s+$/, '');
    };
}

// Add ECMA262-5 Array methods if not supported natively
//
if (!('indexOf' in Array.prototype)) {
    Array.prototype.indexOf= function(find, i /*opt*/) {
        if (i===undefined) i= 0;
        if (i<0) i+= this.length;
        if (i<0) i= 0;
        for (var n= this.length; i<n; i++)
            if (i in this && this[i]===find)
                return i;
        return -1;
    };
}
if (!('lastIndexOf' in Array.prototype)) {
    Array.prototype.lastIndexOf= function(find, i /*opt*/) {
        if (i===undefined) i= this.length-1;
        if (i<0) i+= this.length;
        if (i>this.length-1) i= this.length-1;
        for (i++; i-->0;) /* i++ because from-argument is sadly inclusive */
            if (i in this && this[i]===find)
                return i;
        return -1;
    };
}
if (!('forEach' in Array.prototype)) {
    Array.prototype.forEach= function(action, that /*opt*/) {
        for (var i= 0, n= this.length; i<n; i++)
            if (i in this)
                action.call(that, this[i], i, this);
    };
}
if (!('map' in Array.prototype)) {
    Array.prototype.map= function(mapper, that /*opt*/) {
        var other= new Array(this.length);
        for (var i= 0, n= this.length; i<n; i++)
            if (i in this)
                other[i]= mapper.call(that, this[i], i, this);
        return other;
    };
}
if (!('filter' in Array.prototype)) {
    Array.prototype.filter= function(filter, that /*opt*/) {
        var other= [], v;
        for (var i=0, n= this.length; i<n; i++)
            if (i in this && filter.call(that, v= this[i], i, this))
                other.push(v);
        return other;
    };
}
if (!('every' in Array.prototype)) {
    Array.prototype.every= function(tester, that /*opt*/) {
        for (var i= 0, n= this.length; i<n; i++)
            if (i in this && !tester.call(that, this[i], i, this))
                return false;
        return true;
    };
}
if (!('some' in Array.prototype)) {
    Array.prototype.some= function(tester, that /*opt*/) {
        for (var i= 0, n= this.length; i<n; i++)
            if (i in this && tester.call(that, this[i], i, this))
                return true;
        return false;
    };
}