package com.ambuj.domain;

import com.typesafe.config.Config;

/**
 * Created by Aj on 24-05-2016.
 */
public class GsLookUpDetails {
    private String envName;
    private String url;
    private String userName;
    private String password;
    private boolean secured;

    public String getEnvName() {
        return envName;
    }

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isSecured() {
        return secured;
    }

    public static final class GsLookUpDetailsBuilder {
        private String envName;
        private String url;
        private String userName;
        private String password;
        private boolean secured;

        public GsLookUpDetailsBuilder withEnvName(String envName) {
            this.envName = envName;
            return this;
        }

        public GsLookUpDetailsBuilder withUrl(String url) {
            this.url = url;
            return this;
        }

        public GsLookUpDetailsBuilder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public GsLookUpDetailsBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public GsLookUpDetailsBuilder withSecured(boolean secured) {
            this.secured = secured;
            return this;
        }

        public GsLookUpDetails buildWithConfig(Config config) {
            GsLookUpDetails gsLookUpDetails = new GsLookUpDetails();
            gsLookUpDetails.envName = config.getString(ConfigurationProperties.ENV_NAME);
            gsLookUpDetails.url = config.getString(ConfigurationProperties.ENV_SPACE_URL);
            gsLookUpDetails.userName = config.getString(ConfigurationProperties.ENV_USER_NAME);
            gsLookUpDetails.password = config.getString(ConfigurationProperties.ENV_USER_PASSWORD);
            gsLookUpDetails.secured = config.getBoolean(ConfigurationProperties.ENV_IS_SECURED);

            return gsLookUpDetails;
        }

        public GsLookUpDetails build() {
            GsLookUpDetails gsLookUpDetails = new GsLookUpDetails();
            gsLookUpDetails.envName = this.envName;
            gsLookUpDetails.url = this.url;
            gsLookUpDetails.userName = this.userName;
            gsLookUpDetails.password = this.password;
            gsLookUpDetails.secured = this.secured;

            return gsLookUpDetails;
        }
    }

    @Override
    public String toString() {
        return "GsLookUpDetails{" +
                "envName='" + envName + '\'' +
                ", url='" + url + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}