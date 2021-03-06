/**
 * Autogenerated by Thrift Compiler (1.0.0-dev)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvokeParticipant {

  public interface Iface {

    public void wakeUPParticipant() throws SystemException, org.apache.thrift.TException;

  }

  public interface AsyncIface {

    public void wakeUPParticipant(org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException;

  }

  public static class Client extends org.apache.thrift.TServiceClient implements Iface {
    public static class Factory implements org.apache.thrift.TServiceClientFactory<Client> {
      public Factory() {}
      public Client getClient(org.apache.thrift.protocol.TProtocol prot) {
        return new Client(prot);
      }
      public Client getClient(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
        return new Client(iprot, oprot);
      }
    }

    public Client(org.apache.thrift.protocol.TProtocol prot)
    {
      super(prot, prot);
    }

    public Client(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
      super(iprot, oprot);
    }

    public void wakeUPParticipant() throws SystemException, org.apache.thrift.TException
    {
      send_wakeUPParticipant();
      recv_wakeUPParticipant();
    }

    public void send_wakeUPParticipant() throws org.apache.thrift.TException
    {
      wakeUPParticipant_args args = new wakeUPParticipant_args();
      sendBase("wakeUPParticipant", args);
    }

    public void recv_wakeUPParticipant() throws SystemException, org.apache.thrift.TException
    {
      wakeUPParticipant_result result = new wakeUPParticipant_result();
      receiveBase(result, "wakeUPParticipant");
      if (result.systemException != null) {
        throw result.systemException;
      }
      return;
    }

  }
  public static class AsyncClient extends org.apache.thrift.async.TAsyncClient implements AsyncIface {
    public static class Factory implements org.apache.thrift.async.TAsyncClientFactory<AsyncClient> {
      private org.apache.thrift.async.TAsyncClientManager clientManager;
      private org.apache.thrift.protocol.TProtocolFactory protocolFactory;
      public Factory(org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.protocol.TProtocolFactory protocolFactory) {
        this.clientManager = clientManager;
        this.protocolFactory = protocolFactory;
      }
      public AsyncClient getAsyncClient(org.apache.thrift.transport.TNonblockingTransport transport) {
        return new AsyncClient(protocolFactory, clientManager, transport);
      }
    }

    public AsyncClient(org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.transport.TNonblockingTransport transport) {
      super(protocolFactory, clientManager, transport);
    }

    public void wakeUPParticipant(org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException {
      checkReady();
      wakeUPParticipant_call method_call = new wakeUPParticipant_call(resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class wakeUPParticipant_call extends org.apache.thrift.async.TAsyncMethodCall {
      public wakeUPParticipant_call(org.apache.thrift.async.AsyncMethodCallback resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, false);
      }

      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("wakeUPParticipant", org.apache.thrift.protocol.TMessageType.CALL, 0));
        wakeUPParticipant_args args = new wakeUPParticipant_args();
        args.write(prot);
        prot.writeMessageEnd();
      }

      public void getResult() throws SystemException, org.apache.thrift.TException {
        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
          throw new IllegalStateException("Method call not finished!");
        }
        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
        (new Client(prot)).recv_wakeUPParticipant();
      }
    }

  }

  public static class Processor<I extends Iface> extends org.apache.thrift.TBaseProcessor<I> implements org.apache.thrift.TProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class.getName());
    public Processor(I iface) {
      super(iface, getProcessMap(new HashMap<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>>()));
    }

    protected Processor(I iface, Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends Iface> Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> getProcessMap(Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      processMap.put("wakeUPParticipant", new wakeUPParticipant());
      return processMap;
    }

    public static class wakeUPParticipant<I extends Iface> extends org.apache.thrift.ProcessFunction<I, wakeUPParticipant_args> {
      public wakeUPParticipant() {
        super("wakeUPParticipant");
      }

      public wakeUPParticipant_args getEmptyArgsInstance() {
        return new wakeUPParticipant_args();
      }

      protected boolean isOneway() {
        return false;
      }

      public wakeUPParticipant_result getResult(I iface, wakeUPParticipant_args args) throws org.apache.thrift.TException {
        wakeUPParticipant_result result = new wakeUPParticipant_result();
        try {
          iface.wakeUPParticipant();
        } catch (SystemException systemException) {
          result.systemException = systemException;
        }
        return result;
      }
    }

  }

  public static class AsyncProcessor<I extends AsyncIface> extends org.apache.thrift.TBaseAsyncProcessor<I> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncProcessor.class.getName());
    public AsyncProcessor(I iface) {
      super(iface, getProcessMap(new HashMap<String, org.apache.thrift.AsyncProcessFunction<I, ? extends org.apache.thrift.TBase, ?>>()));
    }

    protected AsyncProcessor(I iface, Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends AsyncIface> Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase,?>> getProcessMap(Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
      processMap.put("wakeUPParticipant", new wakeUPParticipant());
      return processMap;
    }

    public static class wakeUPParticipant<I extends AsyncIface> extends org.apache.thrift.AsyncProcessFunction<I, wakeUPParticipant_args, Void> {
      public wakeUPParticipant() {
        super("wakeUPParticipant");
      }

      public wakeUPParticipant_args getEmptyArgsInstance() {
        return new wakeUPParticipant_args();
      }

      public AsyncMethodCallback<Void> getResultHandler(final AsyncFrameBuffer fb, final int seqid) {
        final org.apache.thrift.AsyncProcessFunction fcall = this;
        return new AsyncMethodCallback<Void>() { 
          public void onComplete(Void o) {
            wakeUPParticipant_result result = new wakeUPParticipant_result();
            try {
              fcall.sendResponse(fb,result, org.apache.thrift.protocol.TMessageType.REPLY,seqid);
              return;
            } catch (Exception e) {
              LOGGER.error("Exception writing to internal frame buffer", e);
            }
            fb.close();
          }
          public void onError(Exception e) {
            byte msgType = org.apache.thrift.protocol.TMessageType.REPLY;
            org.apache.thrift.TBase msg;
            wakeUPParticipant_result result = new wakeUPParticipant_result();
            if (e instanceof SystemException) {
                        result.systemException = (SystemException) e;
                        result.setSystemExceptionIsSet(true);
                        msg = result;
            }
             else 
            {
              msgType = org.apache.thrift.protocol.TMessageType.EXCEPTION;
              msg = (org.apache.thrift.TBase)new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.INTERNAL_ERROR, e.getMessage());
            }
            try {
              fcall.sendResponse(fb,msg,msgType,seqid);
              return;
            } catch (Exception ex) {
              LOGGER.error("Exception writing to internal frame buffer", ex);
            }
            fb.close();
          }
        };
      }

      protected boolean isOneway() {
        return false;
      }

      public void start(I iface, wakeUPParticipant_args args, org.apache.thrift.async.AsyncMethodCallback<Void> resultHandler) throws TException {
        iface.wakeUPParticipant(resultHandler);
      }
    }

  }

  public static class wakeUPParticipant_args implements org.apache.thrift.TBase<wakeUPParticipant_args, wakeUPParticipant_args._Fields>, java.io.Serializable, Cloneable, Comparable<wakeUPParticipant_args>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("wakeUPParticipant_args");


    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new wakeUPParticipant_argsStandardSchemeFactory());
      schemes.put(TupleScheme.class, new wakeUPParticipant_argsTupleSchemeFactory());
    }


    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
;

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(wakeUPParticipant_args.class, metaDataMap);
    }

    public wakeUPParticipant_args() {
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public wakeUPParticipant_args(wakeUPParticipant_args other) {
    }

    public wakeUPParticipant_args deepCopy() {
      return new wakeUPParticipant_args(this);
    }

    @Override
    public void clear() {
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof wakeUPParticipant_args)
        return this.equals((wakeUPParticipant_args)that);
      return false;
    }

    public boolean equals(wakeUPParticipant_args that) {
      if (that == null)
        return false;

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    @Override
    public int compareTo(wakeUPParticipant_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("wakeUPParticipant_args(");
      boolean first = true;

      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      // check for sub-struct validity
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class wakeUPParticipant_argsStandardSchemeFactory implements SchemeFactory {
      public wakeUPParticipant_argsStandardScheme getScheme() {
        return new wakeUPParticipant_argsStandardScheme();
      }
    }

    private static class wakeUPParticipant_argsStandardScheme extends StandardScheme<wakeUPParticipant_args> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, wakeUPParticipant_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, wakeUPParticipant_args struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class wakeUPParticipant_argsTupleSchemeFactory implements SchemeFactory {
      public wakeUPParticipant_argsTupleScheme getScheme() {
        return new wakeUPParticipant_argsTupleScheme();
      }
    }

    private static class wakeUPParticipant_argsTupleScheme extends TupleScheme<wakeUPParticipant_args> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, wakeUPParticipant_args struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, wakeUPParticipant_args struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
      }
    }

  }

  public static class wakeUPParticipant_result implements org.apache.thrift.TBase<wakeUPParticipant_result, wakeUPParticipant_result._Fields>, java.io.Serializable, Cloneable, Comparable<wakeUPParticipant_result>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("wakeUPParticipant_result");

    private static final org.apache.thrift.protocol.TField SYSTEM_EXCEPTION_FIELD_DESC = new org.apache.thrift.protocol.TField("systemException", org.apache.thrift.protocol.TType.STRUCT, (short)1);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new wakeUPParticipant_resultStandardSchemeFactory());
      schemes.put(TupleScheme.class, new wakeUPParticipant_resultTupleSchemeFactory());
    }

    public SystemException systemException; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      SYSTEM_EXCEPTION((short)1, "systemException");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 1: // SYSTEM_EXCEPTION
            return SYSTEM_EXCEPTION;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.SYSTEM_EXCEPTION, new org.apache.thrift.meta_data.FieldMetaData("systemException", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(wakeUPParticipant_result.class, metaDataMap);
    }

    public wakeUPParticipant_result() {
    }

    public wakeUPParticipant_result(
      SystemException systemException)
    {
      this();
      this.systemException = systemException;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public wakeUPParticipant_result(wakeUPParticipant_result other) {
      if (other.isSetSystemException()) {
        this.systemException = new SystemException(other.systemException);
      }
    }

    public wakeUPParticipant_result deepCopy() {
      return new wakeUPParticipant_result(this);
    }

    @Override
    public void clear() {
      this.systemException = null;
    }

    public SystemException getSystemException() {
      return this.systemException;
    }

    public wakeUPParticipant_result setSystemException(SystemException systemException) {
      this.systemException = systemException;
      return this;
    }

    public void unsetSystemException() {
      this.systemException = null;
    }

    /** Returns true if field systemException is set (has been assigned a value) and false otherwise */
    public boolean isSetSystemException() {
      return this.systemException != null;
    }

    public void setSystemExceptionIsSet(boolean value) {
      if (!value) {
        this.systemException = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case SYSTEM_EXCEPTION:
        if (value == null) {
          unsetSystemException();
        } else {
          setSystemException((SystemException)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case SYSTEM_EXCEPTION:
        return getSystemException();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case SYSTEM_EXCEPTION:
        return isSetSystemException();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof wakeUPParticipant_result)
        return this.equals((wakeUPParticipant_result)that);
      return false;
    }

    public boolean equals(wakeUPParticipant_result that) {
      if (that == null)
        return false;

      boolean this_present_systemException = true && this.isSetSystemException();
      boolean that_present_systemException = true && that.isSetSystemException();
      if (this_present_systemException || that_present_systemException) {
        if (!(this_present_systemException && that_present_systemException))
          return false;
        if (!this.systemException.equals(that.systemException))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    @Override
    public int compareTo(wakeUPParticipant_result other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      lastComparison = Boolean.valueOf(isSetSystemException()).compareTo(other.isSetSystemException());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetSystemException()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.systemException, other.systemException);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
      }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("wakeUPParticipant_result(");
      boolean first = true;

      sb.append("systemException:");
      if (this.systemException == null) {
        sb.append("null");
      } else {
        sb.append(this.systemException);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      // check for sub-struct validity
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class wakeUPParticipant_resultStandardSchemeFactory implements SchemeFactory {
      public wakeUPParticipant_resultStandardScheme getScheme() {
        return new wakeUPParticipant_resultStandardScheme();
      }
    }

    private static class wakeUPParticipant_resultStandardScheme extends StandardScheme<wakeUPParticipant_result> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, wakeUPParticipant_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 1: // SYSTEM_EXCEPTION
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.systemException = new SystemException();
                struct.systemException.read(iprot);
                struct.setSystemExceptionIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, wakeUPParticipant_result struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.systemException != null) {
          oprot.writeFieldBegin(SYSTEM_EXCEPTION_FIELD_DESC);
          struct.systemException.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class wakeUPParticipant_resultTupleSchemeFactory implements SchemeFactory {
      public wakeUPParticipant_resultTupleScheme getScheme() {
        return new wakeUPParticipant_resultTupleScheme();
      }
    }

    private static class wakeUPParticipant_resultTupleScheme extends TupleScheme<wakeUPParticipant_result> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, wakeUPParticipant_result struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetSystemException()) {
          optionals.set(0);
        }
        oprot.writeBitSet(optionals, 1);
        if (struct.isSetSystemException()) {
          struct.systemException.write(oprot);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, wakeUPParticipant_result struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(1);
        if (incoming.get(0)) {
          struct.systemException = new SystemException();
          struct.systemException.read(iprot);
          struct.setSystemExceptionIsSet(true);
        }
      }
    }

  }

}
