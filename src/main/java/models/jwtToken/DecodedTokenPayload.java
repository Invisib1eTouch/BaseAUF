package models.jwtToken;

import java.util.Objects;

public class DecodedTokenPayload {
    private String userName;
    private String password;
    private Long iat;

    public static class Builder {
        private DecodedTokenPayload decodedTokenPayload;

        public Builder() {
            decodedTokenPayload = new DecodedTokenPayload();
        }

        public Builder addUserName(String username) {
            decodedTokenPayload.userName = username;
            return this;
        }

        public Builder addPassword(String password) {
            decodedTokenPayload.password = password;
            return this;
        }

        public Builder addIat(Long iat) {
            decodedTokenPayload.iat = iat;
            return this;
        }

        public DecodedTokenPayload build() {
            return decodedTokenPayload;
        }
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Long getIat() {
        return iat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DecodedTokenPayload that = (DecodedTokenPayload) o;
        return Objects.equals(userName, that.userName) && Objects.equals(password, that.password) && Objects.equals(iat, that.iat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password, iat);
    }
}
