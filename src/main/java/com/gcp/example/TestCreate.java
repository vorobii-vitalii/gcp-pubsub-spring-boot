package com.gcp.example;

import com.gcp.example.model.Client;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class TestCreate {

    public static void main(String[] args) throws IOException {
        Client client = Client.newBuilder()
                .setId(14)
                .setAddress("IF")
                .setName("Vitalii")
                .setPhone("102")
                .build();
        ByteBuffer buffer = client.toByteBuffer();
        System.out.println(buffer.toString());

        DatumWriter<Client> userDatumWriter = new SpecificDatumWriter<Client>(Client.class);
        DataFileWriter<Client> dataFileWriter = new DataFileWriter<Client>(userDatumWriter);
        dataFileWriter.create(client.getSchema(), new File("users14.avro"));
        dataFileWriter.append(client);
        dataFileWriter.close();

        DatumReader<Client> userDatumReader = new SpecificDatumReader<Client>(Client.class);
        DataFileReader<Client> dataFileReader = new DataFileReader<Client>(new File("users14.avro"), userDatumReader);
        Client user = null;
        while (dataFileReader.hasNext()) {
            user = dataFileReader.next(user);
            System.out.println(user);
        }


    }

}
