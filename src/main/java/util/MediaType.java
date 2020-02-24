package util;

public enum MediaType {
    GIT_HUB_COMMIT("application/vnd.github.cloak-preview");

    private String type;

    MediaType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
