/**
 * BusiAddServer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.ycii.fc.client;

public interface BusiAddServer extends javax.xml.rpc.Service
{
    public java.lang.String getBusiAddServerHttpPortAddress();

    public BusiAddServerPortType getBusiAddServerHttpPort()
            throws javax.xml.rpc.ServiceException;

    public BusiAddServerPortType getBusiAddServerHttpPort(
            java.net.URL portAddress ) throws javax.xml.rpc.ServiceException;
}
