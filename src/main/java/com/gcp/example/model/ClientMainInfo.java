package com.gcp.example.model;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class ClientMainInfo extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
    private static final long serialVersionUID = 3300411673673428710L;
    public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"ClientMainInfo\",\"namespace\":\"example.gcp\",\"fields\":[{\"name\":\"id\",\"type\":\"long\"},{\"name\":\"name\",\"type\":\"string\"}]}");
    public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

    private static SpecificData MODEL$ = new SpecificData();

    private static final BinaryMessageEncoder<ClientMainInfo> ENCODER =
            new BinaryMessageEncoder<ClientMainInfo>(MODEL$, SCHEMA$);

    private static final BinaryMessageDecoder<ClientMainInfo> DECODER =
            new BinaryMessageDecoder<ClientMainInfo>(MODEL$, SCHEMA$);

    /**
     * Return the BinaryMessageEncoder instance used by this class.
     * @return the message encoder used by this class
     */
    public static BinaryMessageEncoder<ClientMainInfo> getEncoder() {
        return ENCODER;
    }

    /**
     * Return the BinaryMessageDecoder instance used by this class.
     * @return the message decoder used by this class
     */
    public static BinaryMessageDecoder<ClientMainInfo> getDecoder() {
        return DECODER;
    }

    /**
     * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
     * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
     * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
     */
    public static BinaryMessageDecoder<ClientMainInfo> createDecoder(SchemaStore resolver) {
        return new BinaryMessageDecoder<ClientMainInfo>(MODEL$, SCHEMA$, resolver);
    }

    /**
     * Serializes this ClientMainInfo to a ByteBuffer.
     * @return a buffer holding the serialized data for this instance
     * @throws java.io.IOException if this instance could not be serialized
     */
    public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
        return ENCODER.encode(this);
    }

    /**
     * Deserializes a ClientMainInfo from a ByteBuffer.
     * @param b a byte buffer holding serialized data for an instance of this class
     * @return a ClientMainInfo instance decoded from the given buffer
     * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
     */
    public static ClientMainInfo fromByteBuffer(
            java.nio.ByteBuffer b) throws java.io.IOException {
        return DECODER.decode(b);
    }

    private long id;
    private java.lang.CharSequence name;

    /**
     * Default constructor.  Note that this does not initialize fields
     * to their default values from the schema.  If that is desired then
     * one should use <code>newBuilder()</code>.
     */
    public ClientMainInfo() {}

    /**
     * All-args constructor.
     * @param id The new value for id
     * @param name The new value for name
     */
    public ClientMainInfo(java.lang.Long id, java.lang.CharSequence name) {
        this.id = id;
        this.name = name;
    }

    public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
    public org.apache.avro.Schema getSchema() { return SCHEMA$; }
    // Used by DatumWriter.  Applications should not call.
    public java.lang.Object get(int field$) {
        switch (field$) {
            case 0: return id;
            case 1: return name;
            default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
        }
    }

    // Used by DatumReader.  Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int field$, java.lang.Object value$) {
        switch (field$) {
            case 0: id = (java.lang.Long)value$; break;
            case 1: name = (java.lang.CharSequence)value$; break;
            default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
        }
    }

    /**
     * Gets the value of the 'id' field.
     * @return The value of the 'id' field.
     */
    public long getId() {
        return id;
    }


    /**
     * Sets the value of the 'id' field.
     * @param value the value to set.
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Gets the value of the 'name' field.
     * @return The value of the 'name' field.
     */
    public java.lang.CharSequence getName() {
        return name;
    }


    /**
     * Sets the value of the 'name' field.
     * @param value the value to set.
     */
    public void setName(java.lang.CharSequence value) {
        this.name = value;
    }

    /**
     * Creates a new ClientMainInfo RecordBuilder.
     * @return A new ClientMainInfo RecordBuilder
     */
    public static  ClientMainInfo.Builder newBuilder() {
        return new  ClientMainInfo.Builder();
    }

    /**
     * Creates a new ClientMainInfo RecordBuilder by copying an existing Builder.
     * @param other The existing builder to copy.
     * @return A new ClientMainInfo RecordBuilder
     */
    public static  ClientMainInfo.Builder newBuilder( ClientMainInfo.Builder other) {
        if (other == null) {
            return new  ClientMainInfo.Builder();
        } else {
            return new  ClientMainInfo.Builder(other);
        }
    }

    /**
     * Creates a new ClientMainInfo RecordBuilder by copying an existing ClientMainInfo instance.
     * @param other The existing instance to copy.
     * @return A new ClientMainInfo RecordBuilder
     */
    public static  ClientMainInfo.Builder newBuilder( ClientMainInfo other) {
        if (other == null) {
            return new  ClientMainInfo.Builder();
        } else {
            return new  ClientMainInfo.Builder(other);
        }
    }

    /**
     * RecordBuilder for ClientMainInfo instances.
     */
    @org.apache.avro.specific.AvroGenerated
    public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<ClientMainInfo>
            implements org.apache.avro.data.RecordBuilder<ClientMainInfo> {

        private long id;
        private java.lang.CharSequence name;

        /** Creates a new Builder */
        private Builder() {
            super(SCHEMA$);
        }

        /**
         * Creates a Builder by copying an existing Builder.
         * @param other The existing Builder to copy.
         */
        private Builder( ClientMainInfo.Builder other) {
            super(other);
            if (isValidValue(fields()[0], other.id)) {
                this.id = data().deepCopy(fields()[0].schema(), other.id);
                fieldSetFlags()[0] = other.fieldSetFlags()[0];
            }
            if (isValidValue(fields()[1], other.name)) {
                this.name = data().deepCopy(fields()[1].schema(), other.name);
                fieldSetFlags()[1] = other.fieldSetFlags()[1];
            }
        }

        /**
         * Creates a Builder by copying an existing ClientMainInfo instance
         * @param other The existing instance to copy.
         */
        private Builder( ClientMainInfo other) {
            super(SCHEMA$);
            if (isValidValue(fields()[0], other.id)) {
                this.id = data().deepCopy(fields()[0].schema(), other.id);
                fieldSetFlags()[0] = true;
            }
            if (isValidValue(fields()[1], other.name)) {
                this.name = data().deepCopy(fields()[1].schema(), other.name);
                fieldSetFlags()[1] = true;
            }
        }

        /**
         * Gets the value of the 'id' field.
         * @return The value.
         */
        public long getId() {
            return id;
        }


        /**
         * Sets the value of the 'id' field.
         * @param value The value of 'id'.
         * @return This builder.
         */
        public  ClientMainInfo.Builder setId(long value) {
            validate(fields()[0], value);
            this.id = value;
            fieldSetFlags()[0] = true;
            return this;
        }

        /**
         * Checks whether the 'id' field has been set.
         * @return True if the 'id' field has been set, false otherwise.
         */
        public boolean hasId() {
            return fieldSetFlags()[0];
        }


        /**
         * Clears the value of the 'id' field.
         * @return This builder.
         */
        public  ClientMainInfo.Builder clearId() {
            fieldSetFlags()[0] = false;
            return this;
        }

        /**
         * Gets the value of the 'name' field.
         * @return The value.
         */
        public java.lang.CharSequence getName() {
            return name;
        }


        /**
         * Sets the value of the 'name' field.
         * @param value The value of 'name'.
         * @return This builder.
         */
        public  ClientMainInfo.Builder setName(java.lang.CharSequence value) {
            validate(fields()[1], value);
            this.name = value;
            fieldSetFlags()[1] = true;
            return this;
        }

        /**
         * Checks whether the 'name' field has been set.
         * @return True if the 'name' field has been set, false otherwise.
         */
        public boolean hasName() {
            return fieldSetFlags()[1];
        }


        /**
         * Clears the value of the 'name' field.
         * @return This builder.
         */
        public  ClientMainInfo.Builder clearName() {
            name = null;
            fieldSetFlags()[1] = false;
            return this;
        }

        @Override
        @SuppressWarnings("unchecked")
        public ClientMainInfo build() {
            try {
                ClientMainInfo record = new ClientMainInfo();
                record.id = fieldSetFlags()[0] ? this.id : (java.lang.Long) defaultValue(fields()[0]);
                record.name = fieldSetFlags()[1] ? this.name : (java.lang.CharSequence) defaultValue(fields()[1]);
                return record;
            } catch (org.apache.avro.AvroMissingFieldException e) {
                throw e;
            } catch (java.lang.Exception e) {
                throw new org.apache.avro.AvroRuntimeException(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static final org.apache.avro.io.DatumWriter<ClientMainInfo>
            WRITER$ = (org.apache.avro.io.DatumWriter<ClientMainInfo>)MODEL$.createDatumWriter(SCHEMA$);

    @Override public void writeExternal(java.io.ObjectOutput out)
            throws java.io.IOException {
        WRITER$.write(this, SpecificData.getEncoder(out));
    }

    @SuppressWarnings("unchecked")
    private static final org.apache.avro.io.DatumReader<ClientMainInfo>
            READER$ = (org.apache.avro.io.DatumReader<ClientMainInfo>)MODEL$.createDatumReader(SCHEMA$);

    @Override public void readExternal(java.io.ObjectInput in)
            throws java.io.IOException {
        READER$.read(this, SpecificData.getDecoder(in));
    }

    @Override protected boolean hasCustomCoders() { return true; }

    @Override public void customEncode(org.apache.avro.io.Encoder out)
            throws java.io.IOException
    {
        out.writeLong(this.id);

        out.writeString(this.name);

    }

    @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
            throws java.io.IOException
    {
        org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
        if (fieldOrder == null) {
            this.id = in.readLong();

            this.name = in.readString(this.name instanceof Utf8 ? (Utf8)this.name : null);

        } else {
            for (int i = 0; i < 2; i++) {
                switch (fieldOrder[i].pos()) {
                    case 0:
                        this.id = in.readLong();
                        break;

                    case 1:
                        this.name = in.readString(this.name instanceof Utf8 ? (Utf8)this.name : null);
                        break;

                    default:
                        throw new java.io.IOException("Corrupt ResolvingDecoder.");
                }
            }
        }
    }
}










