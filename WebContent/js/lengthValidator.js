/*
 * BusinessInfo.jsp
 */
//¼��ʱ������ò
function a6003Length(value) {
	if (value.length > 10) {
		return "���ȳ�������:10�����ڣ�";
	} else {
		return true;
	}
}
// ¼��ʱѧ������
function a6004Length(value) {
	if (value.length > 30) {
		return "���ȳ������ƣ�30�����ڣ�";
	} else {
		return true;
	}
}
// ¼��ʱѧλ����
function a6006Length(value) {
	if (value.length > 10) {
		return "���ȳ������ƣ�10�������ڣ�";
	} else {
		return true;
	}
}

//����׼��֤��
function a6401Length(value) {
	if (/[\u4E00-\u9FA5\uF900-\uFA2D]/.test(value)) {
		return "���ܺ������ģ�";
	} else {
		return true;
	}
}

//ѡ������ʼ������λ
function a2970bLength(value){
	if(value.length>50){
		return "���ȳ������ƣ�50�����ڣ�";
	} else{
		return true;
	}
}
//ѡ��ʱѧ������
function a6108Length(value){
	if(value.length>30){
		return "���ȳ������ƣ�30�����ڣ�";
	} else{
		return true;
	}
}
//¼��ʱѧλ����
function a6110Length(value){
	if(value.length>10){
		return "���ȳ������ƣ�10�����ڣ�";
	} else{
		return true;
	}
}
//ѡ��ʱ������ò
function a6107Length(value){
	if(value.length>10){
		return "���ȳ������ƣ�10�����ڣ�";
	} else{
		return true;
	}
}
//����׼��֤��
function a6401_1Length(value){
	if(/[\u4E00-\u9FA5\uF900-\uFA2D]/.test(value)){
		return "���ܺ������ģ�";
	} else{
		return true;
	}
}
//��ѵ������
function a1131Length(value){
	if(value.length>60){
		return "���ȳ������ƣ�60�����ڣ�";
	} else{
		return true;
	}
}
//��ѵ���쵥λ
function a1114Length(value){
	if(value.length>60){
		return "���ȳ������ƣ�60�����ڣ�";
	} else{
		return true;
	}
}
//��ѵ��������
function a1121aLength(value){
	if(value.length>60){
		return "���ȳ������ƣ�60�����ڣ�";
	} else{
		return true;
	}
}
//���⹤������

function a6_101716Length(value){
	if(value.length>20){
		return "���ȳ������ƣ�20�������ڣ�";
	} else if(!/^[0-9]*$/.test(value)){
		return "���������֣�";
	}else{
		return true;
	}
}
/*
 * OrthersAddPage.jsp
 */
//���뱾��λǰ������λ����
function a2921aLength(value){
	if(value.length>30){
		return "���ȳ������ƣ�30�����ڣ�";
	} else{
		return true;
	}
}
//��ͥ��ַ
function a3711Length(value){
	if(value.length>60){
		return "���ȳ������ƣ�60�����ڣ�";
	} else{
		return true;
	}
}
//������λ
function a3007aLength(value){
	if(value.length>30){
		return "���ȳ������ƣ�30�����ڣ�";
	} else{
		return true;
	}
}
//��ԭ��λְ��
function a2941Length(value){
	if(value.length>20){
		return "���ȳ������ƣ�20�����ڣ�";
	} else{
		return true;
	}
}
//��ԭ��λְ����
function a2944Length(value){
	if(value.length>20){
		return "���ȳ������ƣ�20�����ڣ�";
	} else{
		return true;
	}
}
//������׼�ĺ�
function a3137Length(value){
	if(value.length>24){
		return "���ȳ������ƣ�24�����ڣ�";
	} else{
		return true;
	}
}
//�������ְ��
function a3118Length(value){
	if(value.length>35){
		return "���ȳ������ƣ�35�����ڣ�";
	} else{
		return true;
	}
}
//���˺����λ
function a3117aLength(value){
	if(value.length>30){
		return "���ȳ������ƣ�30�����ڣ�";
	} else{
		return true;
	}
}
//��ע
function a3034Length(value){
	if(value.length>250){
		return "���ȳ������ƣ�250�����ڣ�";
	} else {
		return true;
	}
}
//����ְ��
function a5304Length(value){
	if(value.length>200){
		return "���ȳ������ƣ�200�����ڣ�";
	} else {
		return true;
	}
}
//����ְ��
function a5315Length(value){
	if(value.length>200){
		return "���ȳ������ƣ�200�����ڣ�";
	} else {
		return true;
	}
}
//��������
function a5317Length(value){
	if(value.length>200){
		return "���ȳ������ƣ�200�����ڣ�";
	} else {
		return true;
	}
}
//�ʱ���λ
function a5319Length(value){
	if(value.length>200){
		return "���ȳ������ƣ�200�����ڣ�";
	} else {
		return true;
	}
}
//���
function a5327Length(value){
	if(value.length>18){
		return "���ȳ������ƣ�18�����ڣ�";
	} else {
		return true;
	}
}
//�칫��ַ
function a3701Length(value){
	if(value.length>60){
		return "���ȳ������ƣ�60�����ڣ�";
	} else {
		return true;
	}
}

/*
 * �칫�绰������֤���ֺ�-��
 * 2017-4-19��fujun
 */
function a3707fLength(value){
	if(!/^[\d\-]+$/.test(value)){
		return "��������ȷ�ĵ绰��";
	} else {
		return true;
	}
}

//�칫�绰
function a3707aLength(value){
	if(!/^0\d{2,3}-?\d{7,8}$/.test(value)){
		return "��������ȷ�ĺ��룡";
	} else {
		return true;
	}
}
//�ƶ��绰
function a3707cLength(value){
	if(!/^1\d{10}$/.test(value)){
		return "��������ȷ�ĺ��룡";
	} else {
		return true;
	}
}

//���˵�λ����
function b0114Length(value) {
	if(value.length>50) {
		return "���ȳ������ƣ�50������";
	} else {
		return true;
	}
}

//��������
function b0101Length(value) {
	if(value.length>50) {
		return "���ȳ������ƣ�50������";
	} else {
		return true;
	}
}

//���
function b0104Length(value) {
	if(value.length>50) {
		return "���ȳ������ƣ�50������";
	} else {
		return true;
	}
}

//���չ���Ա�����������ĺ�
function b0239Length(value) {
	if(value.length>24) {
		return "���ȳ������ƣ�24������";
	} else {
		return true;
	}
}

//��ע����
function b0180Length(value) {
	if(value.length>150) {
		return "���ȳ������ƣ�150������";
	} else {
		return true;
	}
}


//��������
function b0101nameLength(value) {
	if(value.length>50) {
		return "���ȳ������ƣ�50������";
	} else {
		return true;
	}
}



//ProfessSkillAddPage.jsp
//רҵ������ְ�ʸ�����
function a0602Length(value) {
	if(value.length>30) {
		return "���ȳ������ƣ�30������";
	} else {
		return true;
	}
}

//��ί���������
function a0611Length(value) {
	if(value.length>50) {
		return "���ȳ������ƣ�50������";
	} else {
		return true;
	}
}



//DegreesAddPage.jsp
//ѧ������
function a0801aLength(value) {
	if(value.length>60) {
		return "���ȳ������ƣ�60������";
	} else {
		return true;
	}
}

//ѧλ����
function a0901aLength(value) {
	if(value.length>20) {
		return "���ȳ������ƣ�20������";
	} else {
		return true;
	}
}

//ѧУ����λ������
function a0814Length(value) {
	if(value.length>60) {
		return "���ȳ������ƣ�60������";
	} else {
		return true;
	}
}

//��ѧרҵ����
function a0824Length(value) {
	if(value.length>20) {
		return "���ȳ������ƣ�20������";
	} else {
		return true;
	}
}


//WorkUnitsAddPage.jsp
//ȫ��
function a0192aLength(value) {
	if(value.length>1000) {
		return "���ȳ������ƣ�1000������";
	} else {
		return true;
	}
}
//�ֹܣ����£�����
function a0229Length(value) {
	if(value.length>50) {
		return "���ȳ������ƣ�50������";
	} else {
		return true;
	}
}
//��ְ�ĺ�
function a0245Length(value) {
	if(value.length>130) {
		return "���ȳ������ƣ�130������";
	} else {
		return true;
	}
}
//��ְ�ĺ�
function a0267Length(value) {
	if(value.length>60) {
		return "���ȳ������ƣ�12������";
	} else {
		return true;
	}
}

//���
function a0192Length(value) {
	if(value.length>1000) {
		return "���ȳ������ƣ�1000������";
	} else {
		return true;
	}
}

//ְ������
function a0216aLength(value) {
	if(value.length>50) {
		return "���ȳ������ƣ�50������";
	} else {
		return true;
	}
}

//�ֹܣ����£�����
function a0229Length(value) {
	if(value.length>50) {
		return "���ȳ������ƣ�50������";
	} else {
		return true;
	}
}


//RewardPunishAddPage.jsp
//��������
function a14z101Length(value) {
	if(value.length>1000) {
		return "���ȳ������ƣ�1000������";
	} else {
		return true;
	}
}


//AssessmentInfoAddPage.jsp
//��������
function a15z101Length(value) {
	if(value.length>1000) {
		return "���ȳ������ƣ�1000������";
	} else {
		return true;
	}
}

//RewardPunishAddPage
//��������
function a14z101Length(value) {
	if(value.length>1000) {
		return "���ȳ������ƣ�1000������";
	} else {
		return true;
	}
}
//OrthersAdd
//�ɳ���
function a0115aLength(value) {
	if(value.length>60) {
		return "���ȳ������ƣ�60������";
	} else {
		return true;
	}
}
//workUnitsadd
//ְ��䶯����
function a0272Length(value) {
	if(value.length>50) {
		return "���ȳ������ƣ�50������";
	} else {
		return true;
	}
}

//rank
//��׼�ĺ�
function a0511Length(value) {
	if(value.length>12) {
		return "���ȳ������ƣ�24������";
	} else {
		return true;
	}
}








