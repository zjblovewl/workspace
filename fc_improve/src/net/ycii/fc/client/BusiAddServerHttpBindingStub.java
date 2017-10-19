/**
 * BusiAddServerHttpBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.ycii.fc.client;

import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.Service;

import net.ycii.fc.util.PropertiesUtil;

import org.apache.axis.AxisFault;
import org.apache.axis.client.Call;
import org.apache.axis.client.Stub;
import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ParameterDesc;
import org.apache.axis.utils.JavaUtils;

public class BusiAddServerHttpBindingStub extends Stub implements
        BusiAddServerPortType
{
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static OperationDesc[] _operations;

    static
    {
        _operations = new OperationDesc[1];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1()
    {
        OperationDesc oper;
        ParameterDesc param;
        oper = new OperationDesc();
        oper.setName( "addFormBusi" );
        param = new ParameterDesc( new QName( PropertiesUtil.getProperty(
                "set.properties", "slsbWebService" ), "in0" ),
                ParameterDesc.IN, new QName(
                        "http://www.w3.org/2001/XMLSchema", "string" ),
                java.lang.String.class, false, false );
        param.setNillable( true );
        oper.addParameter( param );
        oper.setReturnType( new QName( "http://www.w3.org/2001/XMLSchema",
                "string" ) );
        oper.setReturnClass( String.class );
        oper.setReturnQName( new QName( PropertiesUtil.getProperty(
                "set.properties", "slsbWebService" ), "out" ) );
        oper.setStyle( Style.WRAPPED );
        oper.setUse( Use.LITERAL );
        _operations[0] = oper;

    }

    public BusiAddServerHttpBindingStub() throws AxisFault
    {
        this( null );
    }

    public BusiAddServerHttpBindingStub( URL endpointURL,Service service )
            throws AxisFault
    {
        this( service );
        super.cachedEndpoint = endpointURL;
    }

    public BusiAddServerHttpBindingStub( Service service ) throws AxisFault
    {
        if ( service == null )
        {
            super.service = new org.apache.axis.client.Service();
        } else
        {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service)
                .setTypeMappingVersion( "1.2" );
    }

    protected org.apache.axis.client.Call createCall()
            throws java.rmi.RemoteException
    {
        try
        {
            org.apache.axis.client.Call _call = super._createCall();
            if ( super.maintainSessionSet )
            {
                _call.setMaintainSession( super.maintainSession );
            }
            if ( super.cachedUsername != null )
            {
                _call.setUsername( super.cachedUsername );
            }
            if ( super.cachedPassword != null )
            {
                _call.setPassword( super.cachedPassword );
            }
            if ( super.cachedEndpoint != null )
            {
                _call.setTargetEndpointAddress( super.cachedEndpoint );
            }
            if ( super.cachedTimeout != null )
            {
                _call.setTimeout( super.cachedTimeout );
            }
            if ( super.cachedPortName != null )
            {
                _call.setPortName( super.cachedPortName );
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while ( keys.hasMoreElements() )
            {
                java.lang.String key = (java.lang.String)keys.nextElement();
                _call.setProperty( key, super.cachedProperties.get( key ) );
            }
            return _call;
        } catch ( java.lang.Throwable _t )
        {
            throw new org.apache.axis.AxisFault(
                    "Failure trying to get the Call object", _t );
        }
    }

    public String dealreport( String in0 ) throws RemoteException
    {
        if ( super.cachedEndpoint == null )
        {
            throw new org.apache.axis.NoEndPointException();
        }
        Call _call = dealCall( createCall() );
        _call.setOperationName( new QName( PropertiesUtil.getProperty(
                "set.properties", "slsbWebService" ), "dealreport" ) );

        setRequestHeaders( _call );
        setAttachments( _call );
        try
        {
            java.lang.Object _resp = _call
                    .invoke( new java.lang.Object[] {in0} );

            if ( _resp instanceof RemoteException )
            {
                throw (RemoteException)_resp;
            } else
            {
                extractAttachments( _call );
                try
                {
                    return (java.lang.String)_resp;
                } catch ( java.lang.Exception _exception )
                {
                    return (java.lang.String)JavaUtils.convert( _resp,
                            java.lang.String.class );
                }
            }
        } catch ( AxisFault axisFaultException )
        {
            throw axisFaultException;
        }
    }

    public Call dealCall( Call _call )
    {
        _call.setOperation( _operations[0] );
        _call.setUseSOAPAction( true );
        _call.setSOAPActionURI( "" );
        _call.setEncodingStyle( null );
        _call.setProperty( org.apache.axis.client.Call.SEND_TYPE_ATTR,
                Boolean.FALSE );
        _call.setProperty( org.apache.axis.AxisEngine.PROP_DOMULTIREFS,
                Boolean.FALSE );
        _call.setSOAPVersion( org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS );
        return _call;
    }

    public java.lang.String addFormBusi( java.lang.String in0 )
            throws java.rmi.RemoteException
    {
        if ( super.cachedEndpoint == null )
        {
            throw new org.apache.axis.NoEndPointException();
        }
        Call _call = dealCall( createCall() );
        System.out.println( "=================="
                + PropertiesUtil.getProperty( "set.properties",
                        "slsbWebService" ) );
        _call.setOperationName( new QName( PropertiesUtil.getProperty(
                "set.properties", "slsbWebService" ), "addFormBusi" ) );

        setRequestHeaders( _call );
        setAttachments( _call );
        try
        {
            java.lang.Object _resp = _call
                    .invoke( new java.lang.Object[] {in0} );

            if ( _resp instanceof RemoteException )
            {
                throw (RemoteException)_resp;
            } else
            {
                extractAttachments( _call );
                try
                {
                    return (java.lang.String)_resp;
                } catch ( java.lang.Exception _exception )
                {
                    return (java.lang.String)JavaUtils.convert( _resp,
                            java.lang.String.class );
                }
            }
        } catch ( AxisFault axisFaultException )
        {
            throw axisFaultException;
        }
    }

}
