package tr.com.innova.paymentcloud.oauth2.repository;

import org.springframework.data.repository.Repository;

/**
 * Created by mehme on 12.10.2015.
 */
public interface AppCredentialRepository extends Repository<AppCredential, Integer> {

    AppCredential findByApiKey(String apiKey);

}
