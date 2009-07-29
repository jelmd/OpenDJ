package org.opends.ldap.impl;



import java.util.concurrent.ExecutorService;

import org.opends.ldap.Connection;
import org.opends.ldap.ResponseHandler;
import org.opends.ldap.ResultCode;
import org.opends.ldap.requests.CompareRequest;
import org.opends.ldap.responses.CompareResult;
import org.opends.ldap.responses.CompareResultFuture;
import org.opends.ldap.responses.Responses;



/**
 * Created by IntelliJ IDEA. User: boli Date: Jul 8, 2009 Time: 2:11:10
 * PM To change this template use File | Settings | File Templates.
 */
class CompareResultFutureImpl extends
    AbstractResultFutureImpl<CompareResult> implements
    CompareResultFuture
{
  CompareResultFutureImpl(int messageID, CompareRequest request,
      ResponseHandler<CompareResult> handler, Connection connection,
      ExecutorService handlerExecutor)
  {
    super(messageID, handler, connection, handlerExecutor);
  }



  /**
   * {@inheritDoc}
   */
  CompareResult newErrorResult(ResultCode resultCode,
      String diagnosticMessage, Throwable cause)
  {
    return Responses.newCompareResult(resultCode).setDiagnosticMessage(
        diagnosticMessage).setCause(cause);
  }
}
