/**
 * BusiAddServerLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.ycii.fc.client;

public class BusiAddServerLocator extends org.apache.axis.client.Service
        implements BusiAddServer
{

    public BusiAddServerLocator()
    {
    }

    public BusiAddServerLocator( org.apache.axis.EngineConfiguration config )
    {
        super( config );
    }

    public BusiAddServerLocator( java.lang.String wsdlLoc,
            javax.xml.namespace.QName sName )
            throws javax.xml.rpc.ServiceException
    {
        super( wsdlLoc, sName );
    }

    // Use to get a proxy class for BusiAddServerHttpPort
    private java.lang.String BusiAddServerHttpPort_address = "http://127.0.0.1:8080/fcslsb/services/BusiAddServer";

    public java.lang.String getBusiAddServerHttpPortAddress()
    {
        return BusiAddServerHttpPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BusiAddServerHttpPortWSDDServiceName = "BusiAddServerHttpPort";

    public java.lang.String getBusiAddServerHttpPortWSDDServiceName()
    {
        return BusiAddServerHttpPortWSDDServiceName;
    }

    public void setBusiAddServerHttpPortWSDDServiceName( java.lang.String name )
    {
        BusiAddServerHttpPortWSDDServiceName = name;
    }

    public BusiAddServerPortType getBusiAddServerHttpPort()
            throws javax.xml.rpc.ServiceException
    {
        java.net.URL endpoint;
        try
        {
            endpoint = new java.net.URL( BusiAddServerHttpPort_address );
        } catch ( java.net.MalformedURLException e )
        {
            throw new javax.xml.rpc.ServiceException( e );
        }
        return getBusiAddServerHttpPort( endpoint );
    }

    public BusiAddServerPortType getBusiAddServerHttpPort(
            java.net.URL portAddress ) throws javax.xml.rpc.ServiceException
    {
        try
        {
            BusiAddServerHttpBindingStub _stub = new BusiAddServerHttpBindingStub(
                    portAddress, this );
            _stub.setPortName( getBusiAddServerHttpPortWSDDServiceName() );
            return _stub;
        } catch ( org.apache.axis.AxisFault e )
        {
            return null;
        }
    }

    public void setBusiAddServerHttpPortEndpointAddress(
            java.lang.String address )
    {
        BusiAddServerHttpPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation. If this service has
     * no port for the given interface, then ServiceException is thrown.
     */
    public java.rmi.Remote getPort( Class serviceEndpointInterface )
            throws javax.xml.rpc.ServiceException
    {
        try
        {
            if ( BusiAddServerPortType.class
                    .isAssignableFrom( serviceEndpointInterface ) )
            {
                BusiAddServerHttpBindingStub _stub = new BusiAddServerHttpBindingStub(
                        new java.net.URL( BusiAddServerHttpPort_address ), this );
                _stub.setPortName( getBusiAddServerHttpPortWSDDServiceName() );
                return _stub;
            }
        } catch ( java.lang.Throwable t )
        {
            throw new javax.xml.rpc.ServiceException( t );
        }
        throw new javax.xml.rpc.ServiceException(
                "There is no stub implementation for the interface:  "
                        + (serviceEndpointInterface == null ? "null"
                                : serviceEndpointInterface.getName()) );
    }

    /**
     * For the given interface, get the stub implementation. If this service has
     * no port for the given interface, then ServiceException is thrown.
     */
    public java.rmi.Remote getPort( javax.xml.namespace.QName portName,
            Class serviceEndpointInterface )
            throws javax.xml.rpc.ServiceException
    {
        if ( portName == null )
        {
            return getPort( serviceEndpointInterface );
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ( "BusiAddServerHttpPort".equals( inputPortName ) )
        {
            return getBusiAddServerHttpPort();
        } else
        {
            java.rmi.Remote _stub = getPort( serviceEndpointInterface );
            ((org.apache.axis.client.Stub)_stub).setPortName( portName );
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName()
    {
        return new javax.xml.namespace.QName(
                "http://localhost:8080/tz/services/BusiAddServer",
                "BusiAddServer" );
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts()
    {
        if ( ports == null )
        {
            ports = new java.util.HashSet();
            ports.add( new javax.xml.namespace.QName(
                    "http://localhost:8080/tz/services/BusiAddServer",
                    "BusiAddServerHttpPort" ) );
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress( java.lang.String portName,
            java.lang.String address ) throws javax.xml.rpc.ServiceException
    {

        if ( "BusiAddServerHttpPort".equals( portName ) )
        {
            setBusiAddServerHttpPortEndpointAddress( address );
        } else
        { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(
                    " Cannot set Endpoint Address for Unknown Port" + portName );
        }
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress( javax.xml.namespace.QName portName,
            java.lang.String address ) throws javax.xml.rpc.ServiceException
    {
        setEndpointAddress( portName.getLocalPart(), address );
    }

}
