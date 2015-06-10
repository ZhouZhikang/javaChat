package NetworkDealer;

import Exception.BusinessException;
import Exception.DBOperatorException;
import Exception.SystemException;


public abstract class NetworkCommandDealer {
	public abstract Object dealCommand(String fromIp,Object param1,Object param2,Object param3) throws BusinessException, SystemException, DBOperatorException;
}
