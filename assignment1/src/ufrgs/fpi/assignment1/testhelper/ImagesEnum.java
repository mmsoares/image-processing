package ufrgs.fpi.assignment1.testhelper;

enum ImagesEnum {
    GRAMADO_22K("Gramado_22k.jpg"),
    GRAMADO_72K("Gramado_72k.jpg"),
    SPACE_46K("Space_46k.jpg"),
    SPACE_187K("Space_187k.jpg"),
    UNDERWATER_53K("Underwater_53k.jpg");

    private final String fileName;

    ImagesEnum(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
