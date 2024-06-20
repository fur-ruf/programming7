package common.organization;

import java.io.Serializable;

public enum OrganizationType implements Serializable {
    COMMERCIAL("commercial", 15),
    PUBLIC("public", 20),
    GOVERNMENT("government", 10),
    TRUST("trust", 16),
    PRIVATE_LIMITED_COMPANY("private_limited_company", 12);

    private String title;
    private int tax;
    private OrganizationType(String title, int tax) {
        this.title = title;
        this.tax = tax;
    }

    public static String getValues() {
        String result = "";
        for (OrganizationType type: OrganizationType.values()) {
            result = String.join(" ", result, type.name());
        }
        return result;
    }

    public String getTitle() {
        return title;
    }

    public int getTax() {
        return tax;
    }
}
