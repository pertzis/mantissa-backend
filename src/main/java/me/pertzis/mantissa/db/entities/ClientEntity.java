package me.pertzis.mantissa.db.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="clients")
@Entity
public class ClientEntity {

    @Id
    private String id;
    private String hostname;
    private String username;
    private String ip;
    private boolean online = true;

    public ClientEntity() {}
    public ClientEntity(String id, String hostname, String username, String ip) {
        this.id = id;
        this.hostname = hostname;
        this.username = username;
        this.ip = ip;
    }

    public ClientEntity(String id, String hostname, String username, String ip, boolean online) {
        this.id = id;
        this.hostname = hostname;
        this.username = username;
        this.ip = ip;
        this.online = online;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
