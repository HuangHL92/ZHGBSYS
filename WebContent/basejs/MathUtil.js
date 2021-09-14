//浮点数相加

function dcmAdd(arg1,arg2){

    var r1,r2,m;

    try{r1=arg1.toString().split(".")[1].length;}catch(e){r1=0;}

    try{r2=arg2.toString().split(".")[1].length;}catch(e){r2=0;}

    m=Math.pow(10,Math.max(r1,r2));

    return (dcmMul(arg1,m)+dcmMul(arg2,m))/m;

}

//浮点数相减

function dcmSub(arg1,arg2){

     return dcmAdd(arg1,-arg2);

}

//浮点数相乘

function dcmMul(arg1,arg2){

    var m=0,s1=arg1.toString(),s2=arg2.toString();

    try{m+=s1.split(".")[1].length;}catch(e){}

    try{m+=s2.split(".")[1].length;}catch(e){}

    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);

}

//浮点数除

function dcmDiv(arg1,arg2){

    return dcmMul(arg1,1/arg2);

}

 

Number.prototype.add = function (arg){

                   return dcmAdd(this, arg);

}

Number.prototype.sub = function (arg){

                   return dcmSub(this, arg);

}

 

Number.prototype.mul = function (arg){

                   return dcmMul(this, arg);

}

Number.prototype.div = function (arg){

                   return dcmDiv(this, arg);

}