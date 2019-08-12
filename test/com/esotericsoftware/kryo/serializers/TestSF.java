package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.SerializerFactory;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.minlog.Log;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Create by yuyang
 * 2019/7/25 14:20
 */
public class TestSF {

    public static final Kryo kryo = new Kryo();

    static {
        Log.Logger log = new Log.Logger();
        Log.setLogger(log);
        Log.TRACE();

        SerializerFactory.CompatibleFieldSerializerFactory compatibleFieldSerializerFactory = new SerializerFactory.CompatibleFieldSerializerFactory();
        CompatibleFieldSerializer.CompatibleFieldSerializerConfig config = compatibleFieldSerializerFactory.getConfig();
        config.setChunkedEncoding(true);
        kryo.setDefaultSerializer(compatibleFieldSerializerFactory);
        kryo.setRegistrationRequired(false);
    }


    @Test
    public void encode() throws IOException {
        String s = "Hello Kryo!";

        AnotherClass anotherClass = new AnotherClass();
        anotherClass.k = s;
        anotherClass.v = s;

        SomeClass someClass = new SomeClass();
        someClass.anotherClass = anotherClass;
        someClass.value = s;

        Output output = new Output(new FileOutputStream("target/file_y.bin"));
        kryo.writeObject(output, someClass);
        output.close();

        System.out.println(someClass);
    }


    @Test
    public void decode() throws IOException {
        Input input = new Input(new FileInputStream("target/file_y.bin"));
        SomeClass object2 = kryo.readObject(input, SomeClass.class);
        System.out.println(object2);
        input.close();
    }



    static public class SomeClass {
//        AnotherClass2 anotherClass2;
        AnotherClass anotherClass;
        String value;
        String value2;


    }


    static public class AnotherClass {

        String k;
        String v;
    }




}
