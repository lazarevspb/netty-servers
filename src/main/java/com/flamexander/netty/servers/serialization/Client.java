package com.flamexander.netty.servers.serialization;

import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        ObjectEncoderOutputStream oeos = null;
        ObjectDecoderInputStream odis = null;

        try (Socket socket = new Socket("localhost", 8189)) {
            oeos = new ObjectEncoderOutputStream(socket.getOutputStream());
            MyMessage textMessage = new MyMessage("Hello Server!!!");
            oeos.writeObject(textMessage);
            oeos.flush();
            odis = new ObjectDecoderInputStream(socket.getInputStream(), 100 * 1024 * 1024);
            MyMessage msgFromServer = (MyMessage)odis.readObject();
            System.out.println("Answer from server: " + msgFromServer.getText());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oeos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                odis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
