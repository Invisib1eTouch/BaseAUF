package models.jwtToken;

public class TokenViewModel {
    private String token;
    private String expires;
    private String status;
    private String result;

    public static class Builder {
        private TokenViewModel tokenViewModel;

        public Builder() {
            tokenViewModel = new TokenViewModel();
        }

        public Builder addToken(String token) {
            tokenViewModel.token = token;
            return this;
        }

        public Builder addExpires(String expires) {
            tokenViewModel.expires = expires;
            return this;
        }

        public Builder addStatus(String status) {
            tokenViewModel.status = status;
            return this;
        }

        public Builder addResult(String result) {
            tokenViewModel.result = result;
            return this;
        }

        public TokenViewModel build() {
            return tokenViewModel;
        }
    }

    public String getToken() {
        return token;
    }

    public String getExpires() {
        return expires;
    }

    public String getStatus() {
        return status;
    }

    public String getResult() {
        return result;
    }
}
