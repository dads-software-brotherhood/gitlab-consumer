package mx.dads.infotec.core.gitlab.consumer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Gitlab Rest.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private Gitlab gitlab;

    public Gitlab getGitlab() {
        return gitlab;
    }

    public void setGitlab(Gitlab gitlab) {
        this.gitlab = gitlab;
    }
    
    public static class Gitlab {
        
        private String apiUrl = "http://localhost";
        private Security security = new Security();

        public String getApiUrl() {
            return apiUrl;
        }

        public void setApiUrl(String apiUrl) {
            this.apiUrl = apiUrl;
        }

        public Security getSecurity() {
            return security;
        }

        public void setSecurity(Security security) {
            this.security = security;
        }
        
        public static class Security {
            
            private String token = "";
            private String tokenHeaderName = "private_token";

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getTokenHeaderName() {
                return tokenHeaderName;
            }

            public void setTokenHeaderName(String tokenHeaderName) {
                this.tokenHeaderName = tokenHeaderName;
            }
        }
    }
}
