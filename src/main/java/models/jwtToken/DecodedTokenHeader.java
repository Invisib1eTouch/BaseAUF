package models.jwtToken;

import java.util.Objects;

public class DecodedTokenHeader {
    private String alg;
    private String typ;

    public static class Builder {
        private DecodedTokenHeader decodedTokenHeader;

        public Builder() {
            decodedTokenHeader = new DecodedTokenHeader();
        }

        public Builder addAlg(String alg) {
            decodedTokenHeader.alg = alg;
            return this;
        }

        public Builder addTyp(String typ) {
            decodedTokenHeader.typ = typ;
            return this;
        }

        public DecodedTokenHeader build() {
            return decodedTokenHeader;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DecodedTokenHeader that = (DecodedTokenHeader) o;
        return Objects.equals(alg, that.alg) && Objects.equals(typ, that.typ);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alg, typ);
    }
}
