package services;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class HostNameVerifier implements HostnameVerifier
{
    public boolean verify(String string, SSLSession sslSession) {
        return true;
    }
}
