package bilboard;

import interfaces.IBillboard;

import java.rmi.RemoteException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

public class Bilboard implements IBillboard {

    ArrayList<Advert> adverts;
    int[] capacity;
    Duration seconds;

    public Bilboard(int maxPlacs) {
        this.adverts = new ArrayList<>();
        capacity = new int[2];
        capacity[0] = maxPlacs;
        capacity[1] = capacity[0];
    }

    @Override
    public boolean addAdvertisement(String advertText, Duration displayPeriod, int orderId) throws RemoteException {
        adverts.add(new Advert(orderId, advertText, displayPeriod));
        capacity[1] = capacity[0] - adverts.size();
        return true;
    }

    @Override
    public boolean removeAdvertisement(int orderId) throws RemoteException {
        ArrayList<Advert> c = new ArrayList<>(adverts);
        for (Advert ad : c){
            if(ad.id == orderId){
                adverts.remove(ad);
                capacity[1] = capacity[0] - adverts.size();
                return true;
            }
        }
        return false;
    }

    @Override
    public int[] getCapacity() throws RemoteException {
        return capacity;
    }

    @Override
    public void setDisplayInterval(Duration displayInterval) throws RemoteException {
        seconds = displayInterval;

    }

    @Override
    public boolean start() throws RemoteException {
        return true;
    }

    @Override
    public boolean stop() throws RemoteException {
        return false;
    }
}
