/*
 * $Id: AnyWrapper.java,v 1.1 2006/12/29 04:21:28 sp00fy69 Exp $
 *
 * @author Peter Spiro
 * @since Mar 24, 2006
 *
 * $Log: AnyWrapper.java,v $
 * Revision 1.1  2006/12/29 04:21:28  sp00fy69
 * clenaing up cvs
 *
 * Revision 1.4  2006/08/29 15:28:21  ygorodnitsky
 * EReaderChange
 *
 * Revision 1.3  2006/08/29 14:36:45  ygorodnitsky
 * TIServer compatability
 *
 * Revision 1.2  2006/08/28 22:19:06  pspiro
 * add support for indicator server
 *
 * Revision 1.1  2006/06/14 15:00:03  ygorodnitsky
 * New Indicator Framework merge
 *
 * Revision 1.1.2.1  2006/04/06 19:00:34  pspiro
 * indicator server, branch
 *
 */
package com.ib.client;


public interface AnyWrapper {
    void error(Exception e);

    void error(String str);

    void error(int id, int errorCode, String errorMsg);

    void connectionClosed();
}
