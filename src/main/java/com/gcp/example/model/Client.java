package com.gcp.example.model;

import lombok.AllArgsConstructor;
import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class Client extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
    private static final long serialVersionUID = -5934832373623919201L;
    public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Client\",\"namespace\":\"example.gcp\",\"fields\":[{\"name\":\"id\",\"type\":\"long\"},{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"phone\",\"type\":[\"string\",\"null\"]},{\"name\":\"address\",\"type\":[\"string\",\"null\"]}]}");
    public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

    private static SpecificData MODEL$ = new SpecificData();

    private static final BinaryMessageEncoder<Client> ENCODER =
            new BinaryMessageEncoder<Client>(MODEL$, SCHEMA$);

    private static final BinaryMessageDecoder<Client> DECODER =
            new BinaryMessageDecoder<Client>(MODEL$, SCHEMA$);

    /**
     * Return the BinaryMessageEncoder instance used by this class.
     * @return the message encoder used by this class
     */
    public static BinaryMessageEncoder<Client> getEncoder() {
        return ENCODER;
    }

    /**
     * Return the BinaryMessageDecoder instance used by this class.
     * @return the message decoder used by this class
     */
    public static BinaryMessageDecoder<Client> getDecoder() {
        return DECODER;
    }

    /**
     * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
     * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
     * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
     */
    public static BinaryMessageDecoder<Client> createDecoder(SchemaStore resolver) {
        return new BinaryMessageDecoder<Client>(MODEL$, SCHEMA$, resolver);
    }

    /**
     * Serializes this Client to a ByteBuffer.
     * @return a buffer holding the serialized data for this instance
     * @throws java.io.IOException if this instance could not be serialized
     */
    public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
        return ENCODER.encode(this);
    }

    /**
     * Deserializes a Client from a ByteBuffer.
     * @param b a byte buffer holding serialized data for an instance of this class
     * @return a Client instance decoded from the given buffer
     * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
     */
    public static Client fromByteBuffer(
            java.nio.ByteBuffer b) throws java.io.IOException {
        return DECODER.decode(b);
    }

    private long id;
    private java.lang.CharSequence name;
    private java.lang.CharSequence phone;
    private java.lang.CharSequence address;

    /**
     * Default constructor.  Note that this does not initialize fields
     * to their default values from the schema.  If that is desired then
     * one should use <code>newBuilder()</code>.
     */
    public Client() {}

    /**
     * All-args constructor.
     * @param id The new value for id
     * @param name The new value for name
     * @param phone The new value for phone
     * @param address The new value for address
     */
    public Client(java.lang.Long id, java.lang.CharSequence name, java.lang.CharSequence phone, java.lang.CharSequence address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
    public org.apache.avro.Schema getSchema() { return SCHEMA$; }
    // Used by DatumWriter.  Applications should not call.
    public java.lang.Object get(int field$) {
        switch (field$) {
            case 0: return id;
            case 1: return name;
            case 2: return phone;
            case 3: return address;
            default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
        }
    }

    // Used by DatumReader.  Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int field$, java.lang.Object value$) {
        switch (field$) {
            case 0: id = (java.lang.Long)value$; break;
            case 1: name = (java.lang.CharSequence)value$; break;
            case 2: phone = (java.lang.CharSequence)value$; break;
            case 3: address = (java.lang.CharSequence)value$; break;
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
     * Gets the value of the 'phone' field.
     * @return The value of the 'phone' field.
     */
    public java.lang.CharSequence getPhone() {
        return phone;
    }


    /**
     * Sets the value of the 'phone' field.
     * @param value the value to set.
     */
    public void setPhone(java.lang.CharSequence value) {
        this.phone = value;
    }

    /**
     * Gets the value of the 'address' field.
     * @return The value of the 'address' field.
     */
    public java.lang.CharSequence getAddress() {
        return address;
    }


    /**
     * Sets the value of the 'address' field.
     * @param value the value to set.
     */
    public void setAddress(java.lang.CharSequence value) {
        this.address = value;
    }

    /**
     * Creates a new Client RecordBuilder.
     * @return A new Client RecordBuilder
     */
    public static   Client.Builder newBuilder() {
        return new   Client.Builder();
    }

    /**
     * Creates a new Client RecordBuilder by copying an existing Builder.
     * @param other The existing builder to copy.
     * @return A new Client RecordBuilder
     */
    public static   Client.Builder newBuilder(  Client.Builder other) {
        if (other == null) {
            return new   Client.Builder();
        } else {
            return new   Client.Builder(other);
        }
    }

    /**
     * Creates a new Client RecordBuilder by copying an existing Client instance.
     * @param other The existing instance to copy.
     * @return A new Client RecordBuilder
     */
    public static   Client.Builder newBuilder(  Client other) {
        if (other == null) {
            return new   Client.Builder();
        } else {
            return new   Client.Builder(other);
        }
    }

    /**
     * RecordBuilder for Client instances.
     */
    @org.apache.avro.specific.AvroGenerated
    public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Client>
            implements org.apache.avro.data.RecordBuilder<Client> {

        private long id;
        private java.lang.CharSequence name;
        private java.lang.CharSequence phone;
        private java.lang.CharSequence address;

        /** Creates a new Builder */
        private Builder() {
            super(SCHEMA$);
        }

        /**
         * Creates a Builder by copying an existing Builder.
         * @param other The existing Builder to copy.
         */
        private Builder(  Client.Builder other) {
            super(other);
            if (isValidValue(fields()[0], other.id)) {
                this.id = data().deepCopy(fields()[0].schema(), other.id);
                fieldSetFlags()[0] = other.fieldSetFlags()[0];
            }
            if (isValidValue(fields()[1], other.name)) {
                this.name = data().deepCopy(fields()[1].schema(), other.name);
                fieldSetFlags()[1] = other.fieldSetFlags()[1];
            }
            if (isValidValue(fields()[2], other.phone)) {
                this.phone = data().deepCopy(fields()[2].schema(), other.phone);
                fieldSetFlags()[2] = other.fieldSetFlags()[2];
            }
            if (isValidValue(fields()[3], other.address)) {
                this.address = data().deepCopy(fields()[3].schema(), other.address);
                fieldSetFlags()[3] = other.fieldSetFlags()[3];
            }
        }

        /**
         * Creates a Builder by copying an existing Client instance
         * @param other The existing instance to copy.
         */
        private Builder(  Client other) {
            super(SCHEMA$);
            if (isValidValue(fields()[0], other.id)) {
                this.id = data().deepCopy(fields()[0].schema(), other.id);
                fieldSetFlags()[0] = true;
            }
            if (isValidValue(fields()[1], other.name)) {
                this.name = data().deepCopy(fields()[1].schema(), other.name);
                fieldSetFlags()[1] = true;
            }
            if (isValidValue(fields()[2], other.phone)) {
                this.phone = data().deepCopy(fields()[2].schema(), other.phone);
                fieldSetFlags()[2] = true;
            }
            if (isValidValue(fields()[3], other.address)) {
                this.address = data().deepCopy(fields()[3].schema(), other.address);
                fieldSetFlags()[3] = true;
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
        public   Client.Builder setId(long value) {
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
        public   Client.Builder clearId() {
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
        public   Client.Builder setName(java.lang.CharSequence value) {
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
        public   Client.Builder clearName() {
            name = null;
            fieldSetFlags()[1] = false;
            return this;
        }

        /**
         * Gets the value of the 'phone' field.
         * @return The value.
         */
        public java.lang.CharSequence getPhone() {
            return phone;
        }


        /**
         * Sets the value of the 'phone' field.
         * @param value The value of 'phone'.
         * @return This builder.
         */
        public   Client.Builder setPhone(java.lang.CharSequence value) {
            validate(fields()[2], value);
            this.phone = value;
            fieldSetFlags()[2] = true;
            return this;
        }

        /**
         * Checks whether the 'phone' field has been set.
         * @return True if the 'phone' field has been set, false otherwise.
         */
        public boolean hasPhone() {
            return fieldSetFlags()[2];
        }


        /**
         * Clears the value of the 'phone' field.
         * @return This builder.
         */
        public   Client.Builder clearPhone() {
            phone = null;
            fieldSetFlags()[2] = false;
            return this;
        }

        /**
         * Gets the value of the 'address' field.
         * @return The value.
         */
        public java.lang.CharSequence getAddress() {
            return address;
        }


        /**
         * Sets the value of the 'address' field.
         * @param value The value of 'address'.
         * @return This builder.
         */
        public   Client.Builder setAddress(java.lang.CharSequence value) {
            validate(fields()[3], value);
            this.address = value;
            fieldSetFlags()[3] = true;
            return this;
        }

        /**
         * Checks whether the 'address' field has been set.
         * @return True if the 'address' field has been set, false otherwise.
         */
        public boolean hasAddress() {
            return fieldSetFlags()[3];
        }


        /**
         * Clears the value of the 'address' field.
         * @return This builder.
         */
        public   Client.Builder clearAddress() {
            address = null;
            fieldSetFlags()[3] = false;
            return this;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Client build() {
            try {
                Client record = new Client();
                record.id = fieldSetFlags()[0] ? this.id : (java.lang.Long) defaultValue(fields()[0]);
                record.name = fieldSetFlags()[1] ? this.name : (java.lang.CharSequence) defaultValue(fields()[1]);
                record.phone = fieldSetFlags()[2] ? this.phone : (java.lang.CharSequence) defaultValue(fields()[2]);
                record.address = fieldSetFlags()[3] ? this.address : (java.lang.CharSequence) defaultValue(fields()[3]);
                return record;
            } catch (org.apache.avro.AvroMissingFieldException e) {
                throw e;
            } catch (java.lang.Exception e) {
                throw new org.apache.avro.AvroRuntimeException(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static final org.apache.avro.io.DatumWriter<Client>
            WRITER$ = (org.apache.avro.io.DatumWriter<Client>)MODEL$.createDatumWriter(SCHEMA$);

    @Override public void writeExternal(java.io.ObjectOutput out)
            throws java.io.IOException {
        WRITER$.write(this, SpecificData.getEncoder(out));
    }

    @SuppressWarnings("unchecked")
    private static final org.apache.avro.io.DatumReader<Client>
            READER$ = (org.apache.avro.io.DatumReader<Client>)MODEL$.createDatumReader(SCHEMA$);

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

        if (this.phone == null) {
            out.writeIndex(1);
            out.writeNull();
        } else {
            out.writeIndex(0);
            out.writeString(this.phone);
        }

        if (this.address == null) {
            out.writeIndex(1);
            out.writeNull();
        } else {
            out.writeIndex(0);
            out.writeString(this.address);
        }

    }

    @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
            throws java.io.IOException
    {
        org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
        if (fieldOrder == null) {
            this.id = in.readLong();

            this.name = in.readString(this.name instanceof Utf8 ? (Utf8)this.name : null);

            if (in.readIndex() != 0) {
                in.readNull();
                this.phone = null;
            } else {
                this.phone = in.readString(this.phone instanceof Utf8 ? (Utf8)this.phone : null);
            }

            if (in.readIndex() != 0) {
                in.readNull();
                this.address = null;
            } else {
                this.address = in.readString(this.address instanceof Utf8 ? (Utf8)this.address : null);
            }

        } else {
            for (int i = 0; i < 4; i++) {
                switch (fieldOrder[i].pos()) {
                    case 0:
                        this.id = in.readLong();
                        break;

                    case 1:
                        this.name = in.readString(this.name instanceof Utf8 ? (Utf8)this.name : null);
                        break;

                    case 2:
                        if (in.readIndex() != 0) {
                            in.readNull();
                            this.phone = null;
                        } else {
                            this.phone = in.readString(this.phone instanceof Utf8 ? (Utf8)this.phone : null);
                        }
                        break;

                    case 3:
                        if (in.readIndex() != 0) {
                            in.readNull();
                            this.address = null;
                        } else {
                            this.address = in.readString(this.address instanceof Utf8 ? (Utf8)this.address : null);
                        }
                        break;

                    default:
                        throw new java.io.IOException("Corrupt ResolvingDecoder.");
                }
            }
        }
    }
}



