package com.insigma.odin.framework.sys.auth;

public class VerfyCodeAuthExtend
  extends AuthAbstract
{
  protected boolean validate(AuthParamConfig paramAuthParamConfig)
    throws Exception
  {
    /*if (!VerfyCodeUtil.isVerfyCodeValid(paramAuthParamConfig.getRequest(), true))
    {
      super.doError(paramAuthParamConfig.getRequest(), "验证码错误！");
      return false;
    }*/
	//不校验验证码
    return true;
  }
}