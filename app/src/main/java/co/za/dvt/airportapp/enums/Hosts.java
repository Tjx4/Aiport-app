package co.za.dvt.airportapp.enums;

public enum Hosts {
    liveHost("http://aviation-edge.com/", "");

    private String url;
    private String ip;

    Hosts(String url, String ip) {
        this.url = url;
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public String getIp() {
        return ip;
    }
}
