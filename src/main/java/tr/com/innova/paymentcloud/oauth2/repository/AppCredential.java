package tr.com.innova.paymentcloud.oauth2.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by mehme on 12.10.2015.
 */
@Entity
@Table(name = "APP_CREDENTIALS")
public class AppCredential {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "OWNER_USER_ID")
    private String ownerUserId;

    @Column(name = "APP_NAME")
    private String appName;

    @Column(name = "API_KEY")
    private String apiKey;

    @Column(name = "API_SECRET")
    private String apiSecret;

    @Column(name = "ENABLED")
    private Boolean enabled;

    public AppCredential() {

    }

    public AppCredential(AppCredential credential) {
        super();
        this.id = credential.getId();
        this.ownerUserId = credential.getOwnerUserId();
        this.appName = credential.getAppName();
        this.apiKey = credential.getApiKey();
        this.apiSecret = credential.getApiSecret();
        this.enabled = credential.getEnabled();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
