/**
 * 数字签名对象
 */

var signature ={

	CERT_ISSUSER : ['CN=Insigma Root CA, O="Insigma Group Co., Ltd."',
					'CN=Insigma Qifubao CA, OU=qifubao.com, O="Insigma Group Co., Ltd."',
					'CN=ROOTCA, O=OSCCA, C=CN',
					'CN=iTruschina CN Enterprise Individual Subscriber CA, OU=Terms of use at https://www.itrus.com.cn/ctnrpa (c)2007, OU=China Trust Network, O="iTruschina Co., Ltd.", C=CN'],
	
	CAPICOM_CURRENT_USER_STORE: 2,

	CAPICOM_LOCAL_MACHINE_STORE: 1,

	CAPICOM_MY_STORE: "My",

	CAPICOM_STORE_OPEN_READ_ONLY: 0,

	CAPICOM_STORE_OPEN_READ_WRITE: 1,

	CAPICOM_STORE_OPEN_EXISTING_ONLY: 128,

	CAPICOM_CERTIFICATE_FIND_KEY_USAGE: 12,

	CAPICOM_DIGITAL_SIGNATURE_KEY_USAGE: 128,

	CAPICOM_CERT_INFO_SUBJECT_EMAIL_NAME: 2,

	CAPICOM_HASH_ALGORITHM_SHA1: 0 ,

	CAPICOM_VERIFY_SIGNATURE_AND_CERTIFICATE: 1,

	CAPICOM_CERT_INFO_SUBJECT_SIMPLE_NAME: 0,

	CAPICOM_CERT_INFO_ISSUER_SIMPLE_NAME: 1,

	CAPICOM_CERT_INFO_ISSUER_EMAIL_NAME: 3,

	CAPICOM_ENCODE_BASE64: 0,

	CAPICOM_VERIFY_SIGNATURE_ONLY: 0,
	
	oStore: new ActiveXObject("CAPICOM.Store"),
	
	oSignedData: new ActiveXObject("CAPICOM.SignedData"),
	
	oSigner:new ActiveXObject("CAPICOM.Signer"),

	/**
	 * 判断test发行者是否在CERT_ISSUSER标示的发行者列表中
	 * @param {Object} text
	 */
	isInIssuserArray:function(text){
		var exist = false;
		for(var i=0;i<this.CERT_ISSUSER.length;i++){
			if(this.CERT_ISSUSER[i]==text){
				exist = true;
				break;
			}
		}
		return exist;
	},
	isCertExist:function(){
		var a=new Array();
		var k=-1;
		signature.oStore.Open(this.CAPICOM_CURRENT_USER_STORE , this.CAPICOM_MY_STORE,this.CAPICOM_STORE_OPEN_READ_WRITE);
		for(var i=1;i<=signature.oStore.Certificates.Count;i++){
			var tempCert = signature.oStore.Certificates(i);
			var text= tempCert.IssuerName;
			if (this.isInIssuserArray(text)) {
				k++;
				a[k]=tempCert;
			}
		}
		if(k>=0){
			return true;
		}else{
			return false;
		}
	},
	/***
	 * 根据证书序列号获取证书
	 * @param {Object} serialNumber
	 */
	getCertificateBySeriable:function(serialNumber){
		var cert ={};
		var a=new Array();
		var k=-1;
		cert.flag =false;
		signature.oStore.Open(this.CAPICOM_CURRENT_USER_STORE , this.CAPICOM_MY_STORE,this.CAPICOM_STORE_OPEN_READ_WRITE);
		for(var i=1;i<=signature.oStore.Certificates.Count;i++){
			var tempCert = signature.oStore.Certificates(i);
			var text= tempCert.SerialNumber;
			if (text == serialNumber) {
				cert.Certificate = tempCert;
				cert.flag =true;
				break;
			}
		}
		return cert;	
	},
	getCertificate:function(){
		var cert ={};
		var a=new Array();
		var k=-1;
		cert.flag =false;
		signature.oStore.Open(this.CAPICOM_CURRENT_USER_STORE , this.CAPICOM_MY_STORE,this.CAPICOM_STORE_OPEN_READ_WRITE);
		for(var i=1;i<=signature.oStore.Certificates.Count;i++){
			var tempCert = signature.oStore.Certificates(i);
			var text= tempCert.IssuerName;
			if (this.isInIssuserArray(text)) {
				k++;
				a[k]=tempCert;
			}
		}
		if(k>0){
			var oStoreCerts =signature.oStore.Certificates.Find(10,this.CAPICOM_DIGITAL_SIGNATURE_KEY_USAGE, true); 
			try{
				oStoreCerts = oStoreCerts.Select();//弹出证书选择框
			}catch(e){
				throw "请选择一个证书，并【确定】";
			}
			cert.Certificate = oStoreCerts(1);
			cert.flag = true;
		}else if(k==0){
			cert.Certificate = a[0];
			cert.flag =true;
		}
		return cert;
	},
	openSelect:function(){
		var cert ={};
		signature.oStore.Open(this.CAPICOM_CURRENT_USER_STORE , this.CAPICOM_MY_STORE,this.CAPICOM_STORE_OPEN_READ_WRITE);
		var oStoreCerts =signature.oStore.Certificates.Find(10,this.CAPICOM_DIGITAL_SIGNATURE_KEY_USAGE, true);
		try{
			oStoreCerts = oStoreCerts.Select();//弹出证书选择框
		}catch(e){
			throw "请选择一个证书，并【确定】";
		}
		
		cert.Certificate = oStoreCerts(1);
		return cert;
	},
	checkDate: function(cert){
		var now = new Date();
		if (now >= cert.Certificate.ValidFromDate && now <= cert.Certificate.ValidToDate) {
			return true;
		}
		return false;
	},
	sign: function(cert,bizdetail){
		var SignedResult ="";
		try{
			signature.oSigner.Certificate=cert.Certificate;
			signature.oSignedData.Content = bizdetail;
			SignedResult = signature.oSignedData.Sign(signature.oSigner,false, signature.CAPICOM_ENCODE_BASE64);
		}catch(e){
			throw "用户拒绝了程序访问证书库私钥";
		}
		return  SignedResult;
	},
	verify : function(cert,hashcode,signresult){
		var zhi = signature.sign(cert,hashcode);
		return (signresult==zhi);
//		signature.oSignedData.Content =hashcode;
//		try{
//			signature.oSignedData.Verify(signresult,false,CAPICOM_VERIFY_SIGNATURE_ONLY);
//			return true;
//		}catch(e){
//			alert(e);
//			return false;
//		}
	},
	getCACodeByIssuer:function(issuer){
		var caCode = "";
		if(issuer == 'CN=Insigma Qifubao CA, OU=qifubao.com, O="Insigma Group Co., Ltd."'){
			caCode = 'insigma';
		}else if(issuer == 'CN=iTruschina CN Enterprise Individual Subscriber CA, OU=Terms of use at https://www.itrus.com.cn/ctnrpa (c)2007, OU=China Trust Network, O="iTruschina Co., Ltd.", C=CN'){
			caCode = 'itrus';
		}
		return caCode;
	},
	/**
	 * 从DN中取出CN
	 */
	getCNFromDN : function(certDn) {
		var subject = certDn;
		var info = subject.split(",");
		var subInfo = new Array();
		for (var i = 0; i < info.length; i++) {
			if (info[i].indexOf('=') > 0) {
				var one = info[i].split("=");
				subInfo.push(one[0].trim());
				subInfo.push(one[1].trim());
			} else {
				subInfo.push("Over");
				subInfo.push(info[i].trim());
			}
		}
		for (var i = 0; subInfo != null && subInfo.length > 0 && i < subInfo.length; i++) {
			if ("CN" == subInfo[i] || "cn" == subInfo[i]) {
				return subInfo[i + 1];
			}
			i++;
		}
	}
}

