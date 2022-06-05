package server;

import interfaces.IBillboard;
import interfaces.IManager;
import interfaces.Order;

import java.rmi.RemoteException;
import java.time.Duration;
import java.util.HashMap;

public class Manager implements IManager {

    HashMap<Integer, IBillboard> bilboards;
    int orderCount = 0;
    int bilboardCount = 0;
    Duration period;

    public Manager(Duration period) {
        this.period = period;
        bilboards = new HashMap<>();
    }

    @Override
    public int bindBillboard(IBillboard billboard) throws RemoteException {
        billboard.setDisplayInterval(period);
        bilboards.put(bilboardCount, billboard);
        return bilboardCount++;
    }

    @Override
    public boolean unbindBillboard(int billboardId) throws RemoteException {
        return bilboards.remove(billboardId) != null;
    }

    @Override
    public boolean placeOrder(Order order) throws RemoteException {
        int orderID = orderCount++;
        order.client.setOrderId(orderID);

        for(IBillboard bilb : bilboards.values()){
            if(bilb.getCapacity()[1] > 0){
                bilb.addAdvertisement(order.advertText, order.displayPeriod, orderID);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean withdrawOrder(int orderId) throws RemoteException {
        for(IBillboard bilb : bilboards.values()){
            if(bilb.removeAdvertisement(orderId)) return true;
        }
        return false;
    }

    @Override
    public void increase() throws RemoteException {

    }

    @Override
    public int getOrderNumber() throws RemoteException {
        return 0;
    }
}
