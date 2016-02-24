package com.esotericsoftware.kryo;

import com.esotericsoftware.kryo.io.ByteBufferInput;
import com.esotericsoftware.kryo.io.ByteBufferOutput;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteBufferInputOutputTest extends KryoTestCase {

	public void testByteBufferInputPosition() {
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4096);
		ByteBufferInput inputBuffer = new ByteBufferInput(byteBuffer);
		assertEquals(0, inputBuffer.position());
		assertEquals(0, inputBuffer.getByteBuffer().position());
		inputBuffer.setPosition(5);
		assertEquals(5, inputBuffer.position());
		assertEquals(5, inputBuffer.getByteBuffer().position());
	}

	public void testByteBufferInputLimit() {
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4096);
		ByteBufferInput inputBuffer = new ByteBufferInput(byteBuffer);
		assertEquals(4096, inputBuffer.limit());
		assertEquals(4096, inputBuffer.getByteBuffer().limit());
		inputBuffer.setLimit(1000);
		assertEquals(1000, inputBuffer.limit());
		assertEquals(1000, inputBuffer.getByteBuffer().limit());
	}

	public void testByteBufferInputSetBufferEndianness() {
		ByteBufferInput inputBuffer = new ByteBufferInput();
		assertEquals(ByteOrder.BIG_ENDIAN, inputBuffer.order());

		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4096);
		assertEquals(ByteOrder.BIG_ENDIAN, byteBuffer.order());
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		assertEquals(ByteOrder.LITTLE_ENDIAN, byteBuffer.order());

		inputBuffer.setBuffer(byteBuffer);
		assertEquals(byteBuffer.order(), inputBuffer.order());
	}

	public void testByteBufferInputSkip() {
		ByteBuffer buffer = ByteBuffer.allocateDirect(4096);
		ByteBufferInput inputBuffer = new ByteBufferInput(buffer);
		assertEquals(0, inputBuffer.getByteBuffer().position());
		inputBuffer.skip(5);
		assertEquals(5, inputBuffer.getByteBuffer().position());
	}

	public void testByteBufferOutputPosition() {
		ByteBufferOutput outputBuffer = new ByteBufferOutput(4096);
		assertEquals(0, outputBuffer.position());
		assertEquals(0, outputBuffer.getByteBuffer().position());
		outputBuffer.setPosition(5);
		assertEquals(5, outputBuffer.position());
		outputBuffer.writeInt(10);

		ByteBuffer byteBuffer = outputBuffer.getByteBuffer().duplicate();
		byteBuffer.flip();

		ByteBufferInput inputBuffer = new ByteBufferInput(byteBuffer);
		inputBuffer.skip(5);
		assertEquals(5, byteBuffer.position());
		assertEquals(10, inputBuffer.readInt());
		assertEquals(9, byteBuffer.position());
	}

	public void testByteBufferOutputSetOrder() {
		ByteBufferOutput outputBuffer = new ByteBufferOutput(4096);
		assertEquals(ByteOrder.BIG_ENDIAN, outputBuffer.order());
		assertEquals(ByteOrder.BIG_ENDIAN, outputBuffer.getByteBuffer().order());

		outputBuffer.order(ByteOrder.LITTLE_ENDIAN);
		assertEquals(ByteOrder.LITTLE_ENDIAN, outputBuffer.order());
		assertEquals(ByteOrder.LITTLE_ENDIAN, outputBuffer.getByteBuffer().order());
	}
}