/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.xjeffrose.xio.marshall.thrift;

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
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-07-21")
public class Http1Rule implements org.apache.thrift.TBase<Http1Rule, Http1Rule._Fields>, java.io.Serializable, Cloneable, Comparable<Http1Rule> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Http1Rule");

  private static final org.apache.thrift.protocol.TField METHOD_FIELD_DESC = new org.apache.thrift.protocol.TField("method", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField URI_FIELD_DESC = new org.apache.thrift.protocol.TField("uri", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField VERSION_FIELD_DESC = new org.apache.thrift.protocol.TField("version", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField HEADERS_FIELD_DESC = new org.apache.thrift.protocol.TField("headers", org.apache.thrift.protocol.TType.LIST, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new Http1RuleStandardSchemeFactory());
    schemes.put(TupleScheme.class, new Http1RuleTupleSchemeFactory());
  }

  /**
   * 
   * @see Http1Method
   */
  public Http1Method method; // optional
  public String uri; // optional
  /**
   * 
   * @see Http1Version
   */
  public Http1Version version; // optional
  public List<Http1HeaderTuple> headers; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 
     * @see Http1Method
     */
    METHOD((short)1, "method"),
    URI((short)2, "uri"),
    /**
     * 
     * @see Http1Version
     */
    VERSION((short)3, "version"),
    HEADERS((short)4, "headers");

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
        case 1: // METHOD
          return METHOD;
        case 2: // URI
          return URI;
        case 3: // VERSION
          return VERSION;
        case 4: // HEADERS
          return HEADERS;
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
  private static final _Fields optionals[] = {_Fields.METHOD,_Fields.URI,_Fields.VERSION,_Fields.HEADERS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.METHOD, new org.apache.thrift.meta_data.FieldMetaData("method", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, Http1Method.class)));
    tmpMap.put(_Fields.URI, new org.apache.thrift.meta_data.FieldMetaData("uri", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.VERSION, new org.apache.thrift.meta_data.FieldMetaData("version", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, Http1Version.class)));
    tmpMap.put(_Fields.HEADERS, new org.apache.thrift.meta_data.FieldMetaData("headers", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Http1HeaderTuple.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Http1Rule.class, metaDataMap);
  }

  public Http1Rule() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Http1Rule(Http1Rule other) {
    if (other.isSetMethod()) {
      this.method = other.method;
    }
    if (other.isSetUri()) {
      this.uri = other.uri;
    }
    if (other.isSetVersion()) {
      this.version = other.version;
    }
    if (other.isSetHeaders()) {
      List<Http1HeaderTuple> __this__headers = new ArrayList<Http1HeaderTuple>(other.headers.size());
      for (Http1HeaderTuple other_element : other.headers) {
        __this__headers.add(new Http1HeaderTuple(other_element));
      }
      this.headers = __this__headers;
    }
  }

  public Http1Rule deepCopy() {
    return new Http1Rule(this);
  }

  @Override
  public void clear() {
    this.method = null;
    this.uri = null;
    this.version = null;
    this.headers = null;
  }

  /**
   * 
   * @see Http1Method
   */
  public Http1Method getMethod() {
    return this.method;
  }

  /**
   * 
   * @see Http1Method
   */
  public Http1Rule setMethod(Http1Method method) {
    this.method = method;
    return this;
  }

  public void unsetMethod() {
    this.method = null;
  }

  /** Returns true if field method is set (has been assigned a value) and false otherwise */
  public boolean isSetMethod() {
    return this.method != null;
  }

  public void setMethodIsSet(boolean value) {
    if (!value) {
      this.method = null;
    }
  }

  public String getUri() {
    return this.uri;
  }

  public Http1Rule setUri(String uri) {
    this.uri = uri;
    return this;
  }

  public void unsetUri() {
    this.uri = null;
  }

  /** Returns true if field uri is set (has been assigned a value) and false otherwise */
  public boolean isSetUri() {
    return this.uri != null;
  }

  public void setUriIsSet(boolean value) {
    if (!value) {
      this.uri = null;
    }
  }

  /**
   * 
   * @see Http1Version
   */
  public Http1Version getVersion() {
    return this.version;
  }

  /**
   * 
   * @see Http1Version
   */
  public Http1Rule setVersion(Http1Version version) {
    this.version = version;
    return this;
  }

  public void unsetVersion() {
    this.version = null;
  }

  /** Returns true if field version is set (has been assigned a value) and false otherwise */
  public boolean isSetVersion() {
    return this.version != null;
  }

  public void setVersionIsSet(boolean value) {
    if (!value) {
      this.version = null;
    }
  }

  public int getHeadersSize() {
    return (this.headers == null) ? 0 : this.headers.size();
  }

  public java.util.Iterator<Http1HeaderTuple> getHeadersIterator() {
    return (this.headers == null) ? null : this.headers.iterator();
  }

  public void addToHeaders(Http1HeaderTuple elem) {
    if (this.headers == null) {
      this.headers = new ArrayList<Http1HeaderTuple>();
    }
    this.headers.add(elem);
  }

  public List<Http1HeaderTuple> getHeaders() {
    return this.headers;
  }

  public Http1Rule setHeaders(List<Http1HeaderTuple> headers) {
    this.headers = headers;
    return this;
  }

  public void unsetHeaders() {
    this.headers = null;
  }

  /** Returns true if field headers is set (has been assigned a value) and false otherwise */
  public boolean isSetHeaders() {
    return this.headers != null;
  }

  public void setHeadersIsSet(boolean value) {
    if (!value) {
      this.headers = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case METHOD:
      if (value == null) {
        unsetMethod();
      } else {
        setMethod((Http1Method)value);
      }
      break;

    case URI:
      if (value == null) {
        unsetUri();
      } else {
        setUri((String)value);
      }
      break;

    case VERSION:
      if (value == null) {
        unsetVersion();
      } else {
        setVersion((Http1Version)value);
      }
      break;

    case HEADERS:
      if (value == null) {
        unsetHeaders();
      } else {
        setHeaders((List<Http1HeaderTuple>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case METHOD:
      return getMethod();

    case URI:
      return getUri();

    case VERSION:
      return getVersion();

    case HEADERS:
      return getHeaders();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case METHOD:
      return isSetMethod();
    case URI:
      return isSetUri();
    case VERSION:
      return isSetVersion();
    case HEADERS:
      return isSetHeaders();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Http1Rule)
      return this.equals((Http1Rule)that);
    return false;
  }

  public boolean equals(Http1Rule that) {
    if (that == null)
      return false;

    boolean this_present_method = true && this.isSetMethod();
    boolean that_present_method = true && that.isSetMethod();
    if (this_present_method || that_present_method) {
      if (!(this_present_method && that_present_method))
        return false;
      if (!this.method.equals(that.method))
        return false;
    }

    boolean this_present_uri = true && this.isSetUri();
    boolean that_present_uri = true && that.isSetUri();
    if (this_present_uri || that_present_uri) {
      if (!(this_present_uri && that_present_uri))
        return false;
      if (!this.uri.equals(that.uri))
        return false;
    }

    boolean this_present_version = true && this.isSetVersion();
    boolean that_present_version = true && that.isSetVersion();
    if (this_present_version || that_present_version) {
      if (!(this_present_version && that_present_version))
        return false;
      if (!this.version.equals(that.version))
        return false;
    }

    boolean this_present_headers = true && this.isSetHeaders();
    boolean that_present_headers = true && that.isSetHeaders();
    if (this_present_headers || that_present_headers) {
      if (!(this_present_headers && that_present_headers))
        return false;
      if (!this.headers.equals(that.headers))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_method = true && (isSetMethod());
    list.add(present_method);
    if (present_method)
      list.add(method.getValue());

    boolean present_uri = true && (isSetUri());
    list.add(present_uri);
    if (present_uri)
      list.add(uri);

    boolean present_version = true && (isSetVersion());
    list.add(present_version);
    if (present_version)
      list.add(version.getValue());

    boolean present_headers = true && (isSetHeaders());
    list.add(present_headers);
    if (present_headers)
      list.add(headers);

    return list.hashCode();
  }

  @Override
  public int compareTo(Http1Rule other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetMethod()).compareTo(other.isSetMethod());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMethod()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.method, other.method);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUri()).compareTo(other.isSetUri());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUri()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.uri, other.uri);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetVersion()).compareTo(other.isSetVersion());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVersion()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.version, other.version);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetHeaders()).compareTo(other.isSetHeaders());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHeaders()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.headers, other.headers);
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
    StringBuilder sb = new StringBuilder("Http1Rule(");
    boolean first = true;

    if (isSetMethod()) {
      sb.append("method:");
      if (this.method == null) {
        sb.append("null");
      } else {
        sb.append(this.method);
      }
      first = false;
    }
    if (isSetUri()) {
      if (!first) sb.append(", ");
      sb.append("uri:");
      if (this.uri == null) {
        sb.append("null");
      } else {
        sb.append(this.uri);
      }
      first = false;
    }
    if (isSetVersion()) {
      if (!first) sb.append(", ");
      sb.append("version:");
      if (this.version == null) {
        sb.append("null");
      } else {
        sb.append(this.version);
      }
      first = false;
    }
    if (isSetHeaders()) {
      if (!first) sb.append(", ");
      sb.append("headers:");
      if (this.headers == null) {
        sb.append("null");
      } else {
        sb.append(this.headers);
      }
      first = false;
    }
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

  private static class Http1RuleStandardSchemeFactory implements SchemeFactory {
    public Http1RuleStandardScheme getScheme() {
      return new Http1RuleStandardScheme();
    }
  }

  private static class Http1RuleStandardScheme extends StandardScheme<Http1Rule> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Http1Rule struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // METHOD
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.method = com.xjeffrose.xio.marshall.thrift.Http1Method.findByValue(iprot.readI32());
              struct.setMethodIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // URI
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.uri = iprot.readString();
              struct.setUriIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // VERSION
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.version = com.xjeffrose.xio.marshall.thrift.Http1Version.findByValue(iprot.readI32());
              struct.setVersionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // HEADERS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.headers = new ArrayList<Http1HeaderTuple>(_list0.size);
                Http1HeaderTuple _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = new Http1HeaderTuple();
                  _elem1.read(iprot);
                  struct.headers.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setHeadersIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Http1Rule struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.method != null) {
        if (struct.isSetMethod()) {
          oprot.writeFieldBegin(METHOD_FIELD_DESC);
          oprot.writeI32(struct.method.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.uri != null) {
        if (struct.isSetUri()) {
          oprot.writeFieldBegin(URI_FIELD_DESC);
          oprot.writeString(struct.uri);
          oprot.writeFieldEnd();
        }
      }
      if (struct.version != null) {
        if (struct.isSetVersion()) {
          oprot.writeFieldBegin(VERSION_FIELD_DESC);
          oprot.writeI32(struct.version.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.headers != null) {
        if (struct.isSetHeaders()) {
          oprot.writeFieldBegin(HEADERS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.headers.size()));
            for (Http1HeaderTuple _iter3 : struct.headers)
            {
              _iter3.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class Http1RuleTupleSchemeFactory implements SchemeFactory {
    public Http1RuleTupleScheme getScheme() {
      return new Http1RuleTupleScheme();
    }
  }

  private static class Http1RuleTupleScheme extends TupleScheme<Http1Rule> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Http1Rule struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetMethod()) {
        optionals.set(0);
      }
      if (struct.isSetUri()) {
        optionals.set(1);
      }
      if (struct.isSetVersion()) {
        optionals.set(2);
      }
      if (struct.isSetHeaders()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetMethod()) {
        oprot.writeI32(struct.method.getValue());
      }
      if (struct.isSetUri()) {
        oprot.writeString(struct.uri);
      }
      if (struct.isSetVersion()) {
        oprot.writeI32(struct.version.getValue());
      }
      if (struct.isSetHeaders()) {
        {
          oprot.writeI32(struct.headers.size());
          for (Http1HeaderTuple _iter4 : struct.headers)
          {
            _iter4.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Http1Rule struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.method = com.xjeffrose.xio.marshall.thrift.Http1Method.findByValue(iprot.readI32());
        struct.setMethodIsSet(true);
      }
      if (incoming.get(1)) {
        struct.uri = iprot.readString();
        struct.setUriIsSet(true);
      }
      if (incoming.get(2)) {
        struct.version = com.xjeffrose.xio.marshall.thrift.Http1Version.findByValue(iprot.readI32());
        struct.setVersionIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.headers = new ArrayList<Http1HeaderTuple>(_list5.size);
          Http1HeaderTuple _elem6;
          for (int _i7 = 0; _i7 < _list5.size; ++_i7)
          {
            _elem6 = new Http1HeaderTuple();
            _elem6.read(iprot);
            struct.headers.add(_elem6);
          }
        }
        struct.setHeadersIsSet(true);
      }
    }
  }

}

