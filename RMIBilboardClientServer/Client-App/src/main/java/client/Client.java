package client;

import interfaces.IClient;

import java.rmi.RemoteException;

public class Client implements IClient {

    public int id;

    @Override
    public void setOrderId(int orderId) throws RemoteException {
        id = orderId;
    }
}
