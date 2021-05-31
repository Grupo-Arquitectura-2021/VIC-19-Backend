package bo.ucb.edu.vic19.util.security;

import bo.ucb.edu.vic19.dao.AuthDao;
import bo.ucb.edu.vic19.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InfoAdditionalToken implements TokenEnhancer{

    @Autowired
    private AuthDao authDao;

    // Se genera los datos del AuthDao.
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> info = new HashMap<>();
        User user =authDao.findAdminByEmail(oAuth2Authentication.getName());
        if(user != null){
            info.put("adminId", user.getIdUser());
            info.put("email", user.getEmail());
            info.put("userName", user.getUserName());
            //info.put("role", 1);
        }
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(info);

        return oAuth2AccessToken;
    }
}
